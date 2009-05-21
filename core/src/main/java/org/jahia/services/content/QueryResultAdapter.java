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
package org.jahia.services.content;

import org.apache.jackrabbit.commons.iterator.RowIteratorAdapter;
import org.apache.jackrabbit.commons.iterator.NodeIteratorAdapter;

import javax.jcr.query.QueryResult;
import javax.jcr.query.RowIterator;
import javax.jcr.query.Row;
import javax.jcr.RepositoryException;
import javax.jcr.NodeIterator;
import javax.jcr.Node;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: hollis
 * Date: 13 nov. 2008
 * Time: 12:58:03
 * To change this template use File | Settings | File Templates.
 */
public class QueryResultAdapter implements QueryResult {

    private List<QueryResult> queryResults;
    private RowIteratorAdapter rowIterator;
    private NodeIteratorAdapter nodeIterator;

    /**
     * Wrapped query results that comes from different store
     *
     * @param queryResults
     */
    public QueryResultAdapter(List<QueryResult> queryResults) {
        this.queryResults = queryResults;
    }

    public String[] getColumnNames() throws RepositoryException {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RowIterator getRows() throws RepositoryException {
        if (this.rowIterator == null){
            List<Row> rows = new ArrayList<Row>();
            for (QueryResult queryResult: queryResults){
                RowIterator rowIterator = queryResult.getRows();
                while(rowIterator.hasNext()){
                    rows.add(rowIterator.nextRow());
                }
            }
            this.rowIterator = new RowIteratorAdapter(rows);
        }
        return this.rowIterator;
    }

    public NodeIterator getNodes() throws RepositoryException {
        if (this.nodeIterator == null){
            List<Node> nodes = new ArrayList<Node>();
            for (QueryResult queryResult: queryResults){
                NodeIterator nodeIterator = queryResult.getNodes();
                while(nodeIterator.hasNext()){
                    nodes.add(nodeIterator.nextNode());
                }
            }
            this.nodeIterator = new NodeIteratorAdapter(nodes);
        }
        return this.nodeIterator;
    }
}
