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

import com.extjs.gxt.ui.client.data.BaseModelData;

import java.io.Serializable;

/**
 *
 *
 * User: toto
 * Date: Nov 6, 2008 - 4:53:00 PM
 */
public class GWTJahiaNodeOperationResultItem extends BaseModelData implements Serializable {
    public static final int WARNING = 1;
    public static final int ERROR = 2;

    public static final int VALIDATION = 1;
    public static final int WAI = 2;
    public static final int URL = 3;
    
    public GWTJahiaNodeOperationResultItem() {}

    public GWTJahiaNodeOperationResultItem(int type, String message) {
        setLevel(type);
        setMessage(message);
    }

    public Integer getLevel() {
        return get("level");
    }

    public void setLevel(int level) {
        set("level",Integer.valueOf(level));
    }

    public Integer getType() {
        return get("type");
    }

    public void setType(int type) {
        set("type",Integer.valueOf(type));
    }

    public String getMessage() {
        return get("message");
    }

    public void setMessage(String message) {
        set("message", message);
    }


    public String getUrl() {
        return get("url");
    }

    public void setUrl(String url) {
        set("url", url);
    }

    public String getComment() {
        return get("comment");
    }

    public void setComment(String comment) {
        set("comment", comment);
    }

    public boolean isBlocker() {
        return (Boolean) get("blocker");
    }

    public void setBlocker(boolean blocker) {
        set("blocker", Boolean.valueOf(blocker));
    }
}
