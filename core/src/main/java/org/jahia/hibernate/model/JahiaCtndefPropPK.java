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
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package org.jahia.hibernate.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import java.io.Serializable;

/**
 * @author Hibernate CodeGenerator
 */
public class JahiaCtndefPropPK extends CachedPK implements Serializable {

    /**
     * identifier field
     */
    private Integer idJahiaCtnDef;

    /**
     * identifier field
     */
    private String nameJahiaCtndefProp;

    /**
     * full constructor
     */
    public JahiaCtndefPropPK(Integer idJahiaCtnDef, String nameJahiaCtndefProp) {
        this.idJahiaCtnDef = idJahiaCtnDef;
        this.nameJahiaCtndefProp = nameJahiaCtndefProp;
    }

    /**
     * default constructor
     */
    public JahiaCtndefPropPK() {
    }

    /**
     * @hibernate.property column="id_jahia_ctn_def"
     * length="11"
     */
    public Integer getIdJahiaCtnDef() {
        return this.idJahiaCtnDef;
    }

    public void setIdJahiaCtnDef(Integer idJahiaCtnDef) {
        updated();
        this.idJahiaCtnDef = idJahiaCtnDef;
    }

    /**
     * @hibernate.property column="name_jahia_ctndef_prop"
     * length="255"
     */
    public String getNameJahiaCtndefProp() {
        return this.nameJahiaCtndefProp;
    }

    public void setNameJahiaCtndefProp(String nameJahiaCtndefProp) {
        updated();
        this.nameJahiaCtndefProp = nameJahiaCtndefProp;
    }

    public String effectiveToString() {
        return new StringBuffer(getClass().getName())
                .append("idJahiaCtnDef="+getIdJahiaCtnDef())
                .append("nameJahiaCtndefProp="+getNameJahiaCtndefProp())
                .toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj != null && this.getClass() == obj.getClass()) {
            final JahiaCtndefPropPK castOther = (JahiaCtndefPropPK) obj;
            return new EqualsBuilder()
                .append(this.getIdJahiaCtnDef(), castOther.getIdJahiaCtnDef())
                .append(this.getNameJahiaCtndefProp(), castOther.getNameJahiaCtndefProp())
                .isEquals();
        }
        return false;
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getIdJahiaCtnDef())
                .append(getNameJahiaCtndefProp())
                .toHashCode();
    }

}
