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
package org.jahia.taglibs.query;

import org.jahia.taglibs.AbstractJahiaTag;

import javax.servlet.jsp.JspException;

/**
 * Created by IntelliJ IDEA.
 * User: hollis
 * Date: 7 nov. 2007
 * Time: 15:33:24
 * To change this template use File | Settings | File Templates.
 */
public class SortByTag extends AbstractJahiaTag {

    private static final long serialVersionUID = 7747723525104918964L;

    private static org.apache.log4j.Logger logger =
        org.apache.log4j.Logger.getLogger(SortByTag.class);

    private QueryDefinitionTag queryModelDefTag = null;

    private String propertyName;

    private String numberFormat;
    private String numberValue;
    private String valueProviderClass;
    private String isMetadata;
    private String aliasNames;    

    private String order;
    private String localeSensitive = "false";

    public int doStartTag() throws JspException {
        queryModelDefTag = (QueryDefinitionTag) findAncestorWithClass(this, QueryDefinitionTag.class);
        if (queryModelDefTag == null || queryModelDefTag.getQueryFactory()==null) {
            return SKIP_BODY;
        }
        if (this.propertyName == null || this.propertyName.trim().equals("")){
            return EVAL_BODY_BUFFERED;
        }
        try {
            this.queryModelDefTag.addOrdering(getPropertyName(), "true".equals(getNumberValue()), getNumberFormat(), "true".equals(getMetadata()), getValueProviderClass(), getOrder(), "true".equals(getLocaleSensitive()), getAliasNames());
        } catch ( Exception t ){
            logger.debug("Error creating ordering clause",t);
            throw new JspException("Error creating Ordering node in SortBy Tag",t);
        }
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
        queryModelDefTag = null;
        propertyName = null;
        propertyName = null;
        numberFormat = null;
        numberValue = null;
        isMetadata = null;
        valueProviderClass = null;
        order = null;
        localeSensitive = null;
        aliasNames = null;        
        return EVAL_PAGE;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

    public String getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(String numberValue) {
        this.numberValue = numberValue;
    }

    public String getMetadata() {
        return isMetadata;
    }

    public void setMetadata(String metadata) {
        isMetadata = metadata;
    }

    public String getValueProviderClass() {
        return valueProviderClass;
    }

    public void setValueProviderClass(String valueProviderClass) {
        this.valueProviderClass = valueProviderClass;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getLocaleSensitive() {
        return localeSensitive;
    }

    public void setLocaleSensitive(String localeSensitive) {
        this.localeSensitive = localeSensitive;
    }

    public void setAliasNames(String aliasNames) {
        this.aliasNames = aliasNames;
    }

    public String getAliasNames() {
        return aliasNames;
    }

}