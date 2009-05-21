/**
 * This file is part of Jahia: An integrated WCM, DMS and Portal Solution
 * Copyright (C) 2002-2009 Jahia Solutions Group SA. All rights reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * As a special exception to the terms and conditions of version 2.0 of
 * the GPL (or any later version), you may redistribute this Program in connection
 * with Free/Libre and Open Source Software ("FLOSS") applications as described
 * in Jahia's FLOSS exception. You should have received a copy of the text
 * describing the FLOSS exception, and it is also available here:
 * http://www.jahia.com/license
 *
 * Commercial and Supported Versions of the program
 * Alternatively, commercial and supported versions of the program may be used
 * in accordance with the terms contained in a separate written agreement
 * between you and Jahia Solutions Group SA. If you are unsure which license is appropriate
 * for your use, please contact the sales department at sales@jahia.com.
 */
/*
 * Copyright (c) 2004 Your Corporation. All Rights Reserved.
 */

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jahia.services.search.lucene.fs;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;


/**
 * @version $Id: LRUCache.java 555343 2007-07-11 17:46:25Z hossman $
 */
public class LRUCache implements SearcherCache {
    
    private static Logger log = Logger.getLogger(LRUCache.class.getName());

  /* An instance of this class will be shared across multiple instances
   * of an LRUCache at the same time.  Make sure everything is thread safe.
   */
  private static class CumulativeStats {
    AtomicLong lookups = new AtomicLong();
    AtomicLong hits = new AtomicLong();
    AtomicLong inserts = new AtomicLong();
    AtomicLong evictions = new AtomicLong();
  }

  private CumulativeStats stats;

  // per instance stats.  The synchronization used for the map will also be
  // used for updating these statistics (and hence they are not AtomicLongs
  private long lookups;
  private long hits;
  private long inserts;
  private long evictions;

  private Map<Object, Object> map;
  private String name;
  private int autowarmCount;
  private State state;
  private CacheRegenerator regenerator;
  private String description="LRU Cache";

  public Object init(Properties args, Object persistence, CacheRegenerator regenerator) {
    state=State.CREATED;
    this.regenerator = regenerator;
    name = args.getProperty("name");
    String str = args.getProperty("size");
    final int limit = str==null ? 1024 : Integer.parseInt(str);
    str = args.getProperty("initialSize");
    final int initialSize = Math.min(str==null ? 1024 : Integer.parseInt(str), limit);
    str = args.getProperty("autowarmCount");
    autowarmCount = str==null ? 0 : Integer.parseInt(str);

    description = "LRU Cache(maxSize=" + limit + ", initialSize=" + initialSize;
    if (autowarmCount>0) {
      description += ", autowarmCount=" + autowarmCount
              + ", regenerator=" + regenerator;
    }
    description += ')';

    map = new LinkedHashMap<Object, Object>(initialSize, 0.75f, true) {

        private static final long serialVersionUID = 1L;

        protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
          if (size() > limit) {
            // increment evictions regardless of state.
            // this doesn't need to be synchronized because it will
            // only be called in the context of a higher level synchronized block.
            evictions++;
            stats.evictions.incrementAndGet();
            return true;
          }
          return false;
        }
      };

    if (persistence==null) {
      // must be the first time a cache of this type is being created
      persistence = new CumulativeStats();
    }

    stats = (CumulativeStats)persistence;

    return persistence;
  }

  public String name() {
    return name;
  }

  public int size() {
    synchronized(map) {
      return map.size();
    }
  }

  public synchronized Object put(Object key, Object value) {
    if (state == State.LIVE) {
      stats.inserts.incrementAndGet();
    }

    synchronized (map) {
      // increment local inserts regardless of state???
      // it does make it more consistent with the current size...
      inserts++;
      return map.put(key,value);
    }
  }

  public Object get(Object key) {
    synchronized (map) {
      Object val = map.get(key);
      if (state == State.LIVE) {
        // only increment lookups and hits if we are live.
        lookups++;
        stats.lookups.incrementAndGet();
        if (val!=null) {
          hits++;
          stats.hits.incrementAndGet();
        }
      }
      return val;
    }
  }

  public void clear() {
    synchronized(map) {
      map.clear();
    }
  }

  public void setState(State state) {
    this.state = state;
  }

  public State getState() {
    return state;
  }

  public void warm(JahiaIndexSearcher searcher, SearcherCache old) throws IOException {
    if (regenerator==null) return;

    LRUCache other = (LRUCache)old;

    // warm entries
    if (autowarmCount != 0) {
      Object[] keys,vals = null;

      // Don't do the autowarming in the synchronized block, just pull out the keys and values.
      synchronized (other.map) {
        int sz = other.map.size();
        if (autowarmCount!=-1) sz = Math.min(sz,autowarmCount);
        keys = new Object[sz];
        vals = new Object[sz];

        Iterator<Map.Entry<Object, Object>> iter = other.map.entrySet().iterator();

        // iteration goes from oldest (least recently used) to most recently used,
        // so we need to skip over the oldest entries.
        int skip = other.map.size() - sz;
        for (int i=0; i<skip; i++) iter.next();


        for (int i=0; i<sz; i++) {
          Map.Entry<Object, Object> entry = iter.next();
          keys[i]=entry.getKey();
          vals[i]=entry.getValue();
        }
      }

      // autowarm from the oldest to the newest entries so that the ordering will be
      // correct in the new cache.
      for (int i=0; i<keys.length; i++) {
        try {
          boolean continueRegen = regenerator.regenerateItem(searcher, this, old, keys[i], vals[i]);
          if (!continueRegen) break;
        }
        catch (Exception e) {
          log.warn("Error during auto-warming of key:" + keys[i], e);
        }
      }
    }
  }


  public void close() {
  }


  //////////////////////// SolrInfoMBeans methods //////////////////////


  public String getName() {
    return LRUCache.class.getName();
  }

  public String getVersion() {
    return LuceneCoreSearcher.version;
  }

  public String getDescription() {
    return description;
  }

  public String getSourceId() {
    return "$Id: LRUCache.java 555343 2007-07-11 17:46:25Z hossman $";
  }

  public String getSource() {
    return "$URL: http://svn.apache.org/repos/asf/lucene/solr/trunk/src/java/org/apache/solr/search/LRUCache.java $";
  }

  public URL[] getDocs() {
    return null;
  }


  // returns a ratio, not a percent.
  private static String calcHitRatio(long lookups, long hits) {
    if (lookups==0) return "0.00";
    if (lookups==hits) return "1.00";
    int hundredths = (int)(hits*100/lookups);   // rounded down
    if (hundredths < 10) return "0.0" + hundredths;
    return "0." + hundredths;

    /*** code to produce a percent, if we want it...
    int ones = (int)(hits*100 / lookups);
    int tenths = (int)(hits*1000 / lookups) - ones*10;
    return Integer.toString(ones) + '.' + tenths;
    ***/
  }

  public Map<String, Object> getStatistics() {
    Map<String, Object> lst = new HashMap<String, Object>();
    synchronized (map) {
      lst.put("lookups", lookups);
      lst.put("hits", hits);
      lst.put("hitratio", calcHitRatio(lookups,hits));
      lst.put("inserts", inserts);
      lst.put("evictions", evictions);
      lst.put("size", map.size());
    }

    long clookups = stats.lookups.get();
    long chits = stats.hits.get();
    lst.put("cumulative_lookups", clookups);
    lst.put("cumulative_hits", chits);
    lst.put("cumulative_hitratio", calcHitRatio(clookups,chits));
    lst.put("cumulative_inserts", stats.inserts.get());
    lst.put("cumulative_evictions", stats.evictions.get());

    return lst;
  }

  public String toString() {
    return name + getStatistics().toString();
  }
}
