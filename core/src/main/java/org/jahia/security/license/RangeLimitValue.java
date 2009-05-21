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
 * Created on Sep 14, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.jahia.security.license;

/**
 * Simple range limit
 * @author loom
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RangeLimitValue extends LimitValue {
    
    private final static String RANGE_SEPERATOR = " to ";

    private String fromValue = null;
    private String toValue = null;
    
    public RangeLimitValue(String value) {
        super(value);
        int rangeSepPos = value.indexOf(RANGE_SEPERATOR);
        if (rangeSepPos == -1) {
            this.fromValue = value;
            this.toValue = null;
        } else {
            this.fromValue = value.substring(0, rangeSepPos).trim();
            this.toValue = value.substring(rangeSepPos + RANGE_SEPERATOR.length()).trim();
        }
    }
    
    public static boolean isRangeValue(String value) {
        return (value.indexOf(RANGE_SEPERATOR) != -1);
    }
    
    /**
     * @return
     */
    public String getFromValue() {
        return fromValue;
    }

    /**
     * @return
     */
    public String getToValue() {
        return toValue;
    }

    public boolean check(Validator validator) {
        return validator.assertInRange(fromValue, toValue);
    }
    
}
