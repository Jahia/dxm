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
 package org.jahia.hibernate.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @hibernate.class table="jahia_ctn_lists"
 * @hibernate.cache usage="nonstrict-read-write"
 */
public class JahiaContainerList implements Serializable,Cloneable {

    /**
     * identifier field
     */
    private org.jahia.hibernate.model.JahiaCtnListPK comp_id;

    /**
     * nullable persistent field
     */
    private Integer pageid;

    /**
     * nullable persistent field
     */
    private Integer parentId;

    /**
     * nullable persistent field
     */
    private JahiaCtnDef containerDefinition;

    /**
     * nullable persistent field
     */
    private Integer jahiaAclId;

    /**
     * default constructor
     */
    public JahiaContainerList() {
    }

    /**
     * @hibernate.id generator-class="assigned"
     */
    public org.jahia.hibernate.model.JahiaCtnListPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(org.jahia.hibernate.model.JahiaCtnListPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     * @hibernate.property column="pageid_jahia_ctn_lists"
     * length="11"
     */
    public Integer getPageid() {
        return this.pageid;
    }

    public void setPageid(Integer pageid) {
        this.pageid = pageid;
    }

    /**
     * @hibernate.property column="parententryid_jahia_ctn_lists"
     * length="11"
     */
    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    /**
     * @hibernate.many-to-one not-null="true"
     * @hibernate.column name="ctndefid_jahia_ctn_lists"
     */
    public JahiaCtnDef getContainerDefinition() {
        return this.containerDefinition;
    }

    public void setContainerDefinition(JahiaCtnDef containerDefinition) {
        this.containerDefinition = containerDefinition;
    }


    /**
     * @hibernate.property name="jahiaAclId"
     * column="rights_jahia_ctn_lists"
     */
    public Integer getJahiaAclId() {
        return this.jahiaAclId;
    }

    public void setJahiaAclId(Integer jahiaAclId) {
        this.jahiaAclId = jahiaAclId;
    }

    public String toString() {
        return new StringBuffer(getClass().getName())
                .append("comp_id="+getComp_id())
                .toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (obj != null && this.getClass() == obj.getClass()) {
            final JahiaContainerList castOther = (JahiaContainerList) obj;
            return new EqualsBuilder()
                .append(this.getComp_id(), castOther.getComp_id())
                .isEquals();
        }
        return false;
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getComp_id())
                .toHashCode();
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object <tt>x</tt>, the expression:
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be <tt>true</tt>, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be <tt>true</tt>, this is not an absolute requirement.
     * <p/>
     * By convention, the returned object should be obtained by calling
     * <tt>super.clone</tt>.  If a class and all of its superclasses (except
     * <tt>Object</tt>) obey this convention, it will be the case that
     * <tt>x.clone().getClass() == x.getClass()</tt>.
     * <p/>
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by <tt>super.clone</tt> before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by <tt>super.clone</tt>
     * need to be modified.
     * <p/>
     * The method <tt>clone</tt> for class <tt>Object</tt> performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface <tt>Cloneable</tt>, then a
     * <tt>CloneNotSupportedException</tt> is thrown. Note that all arrays
     * are considered to implement the interface <tt>Cloneable</tt>.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     * <p/>
     * The class <tt>Object</tt> does not itself implement the interface
     * <tt>Cloneable</tt>, so calling the <tt>clone</tt> method on an object
     * whose class is <tt>Object</tt> will result in throwing an
     * exception at run time.
     *
     * @return a clone of this instance.
     *
     * @throws CloneNotSupportedException if the object's class does not
     *                                    support the <code>Cloneable</code> interface. Subclasses
     *                                    that override the <code>clone</code> method can also
     *                                    throw this exception to indicate that an instance cannot
     *                                    be cloned.
     * @see Cloneable
     */
    public Object clone() throws CloneNotSupportedException {
        JahiaContainerList containerList = new JahiaContainerList();
        containerList.setComp_id((JahiaCtnListPK) this.getComp_id().clone());
        containerList.setContainerDefinition(this.getContainerDefinition());
        containerList.setPageid(this.getPageid());
        containerList.setParentId(this.getParentId());
        containerList.setJahiaAclId(this.getJahiaAclId());
        return containerList;
    }
}
