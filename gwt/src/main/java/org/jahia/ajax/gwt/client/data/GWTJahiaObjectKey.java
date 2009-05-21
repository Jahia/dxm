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
package org.jahia.ajax.gwt.client.data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: hollis
 * Date: 29 ao�t 2008
 * Time: 10:56:17
 * To change this template use File | Settings | File Templates.
 */
public class GWTJahiaObjectKey implements Serializable {

    static final String KEY_SEPARATOR = "_";

    private String key;
    private String type;
    private int idInType;

    public GWTJahiaObjectKey(String type,String idInType) {
        StringBuffer buf = new StringBuffer(50);
        buf.append(type);
        buf.append(KEY_SEPARATOR);
        buf.append(idInType);
        this.key = buf.toString();
        try {
            this.idInType = Integer.parseInt(idInType);
        } catch (NumberFormatException e) {
            this.idInType = -1;
        }
    }

    public GWTJahiaObjectKey(String type,int idInType) {
        StringBuffer buf = new StringBuffer(50);
        buf.append(type);
        buf.append(KEY_SEPARATOR);
        buf.append(idInType);
        this.key = buf.toString();
        this.idInType = idInType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdInType() {
        return idInType;
    }

    public void setIdInType(int idInType) {
        this.idInType = idInType;
    }
}
