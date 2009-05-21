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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @hibernate.class table="jahia_sites" lazy="false"
 * @hibernate.cache usage="nonstrict-read-write"
 */
public class JahiaSite implements Serializable {

    /**
     * identifier field
     */
    private Integer id;

    /**
     * nullable persistent field
     */
    private String title;

    /**
     * nullable persistent field
     */
    private String servername;

    /**
     * nullable persistent field
     */
    private String key;

    /**
     * nullable persistent field
     */
    private Integer active;

    /**
     * nullable persistent field
     */
    private Integer defaultpageid;

    /**
     * nullable persistent field
     */
    private Integer defaulttemplateid;

    /**
     * nullable persistent field
     */
    private Integer tplDeploymode;

    /**
     * nullable persistent field
     */
    private Integer webappsDeploymode;

    /**
     * nullable persistent field
     */
    private Integer rights;

    /**
     * nullable persistent field
     */
    private String descr;

    /**
     * persistent field
     */

//    private Set settings;

    /**
     * persistent field
     */
    private Boolean defaultSite;

    /**
     * full constructor
     */
    public JahiaSite(Integer id, String title, String servername, String key, Integer active, Integer defaultpageid,
                     Integer defaulttemplateid, Integer tplDeploymode, Integer webappsDeploymode, Integer rights,
                     String descr) {
        this.id = id;
        this.title = title;
        this.servername = servername;
        this.key = key;
        this.active = active;
        this.defaultpageid = defaultpageid;
        this.defaulttemplateid = defaulttemplateid;
        this.tplDeploymode = tplDeploymode;
        this.webappsDeploymode = webappsDeploymode;
        this.rights = rights;
        this.descr = descr;
//        this.settings = settings;
    }

    /**
     * default constructor
     */
    public JahiaSite() {
//        settings = new HashSet();
    }

    /**
     * minimal constructor
     */
    public JahiaSite(Integer id) {
        this.id = id;
//        settings = new HashSet();
    }

    /**
     * @hibernate.id generator-class="assigned"
     * column="id_jahia_sites"
     *
     */
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @hibernate.property column="title_jahia_sites"
     * length="100"
     */
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @hibernate.property column="servername_jahia_sites"
     * length="200"
     */
    public String getServername() {
        return this.servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    /**
     * @hibernate.property column="key_jahia_sites"
     * unique="true"
     * length="50"
     */
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @hibernate.property column="active_jahia_sites"
     * length="11"
     */
    public Integer getActive() {
        return this.active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    /**
     * @hibernate.property column="defaultpageid_jahia_sites"
     * length="11"
     */
    public Integer getDefaultpageid() {
        return this.defaultpageid;
    }

    public void setDefaultpageid(Integer defaultpageid) {
        this.defaultpageid = defaultpageid;
    }

    /**
     * @hibernate.property column="defaulttemplateid_jahia_sites"
     * length="11"
     */
    public Integer getDefaulttemplateid() {
        return this.defaulttemplateid;
    }

    public void setDefaulttemplateid(Integer defaulttemplateid) {
        this.defaulttemplateid = defaulttemplateid;
    }

    /**
     * @hibernate.property column="tpl_deploymode_jahia_sites"
     * length="11"
     */
    public Integer getTplDeploymode() {
        return this.tplDeploymode;
    }

    public void setTplDeploymode(Integer tplDeploymode) {
        this.tplDeploymode = tplDeploymode;
    }

    /**
     * @hibernate.property column="webapps_deploymode_jahia_sites"
     * length="11"
     */
    public Integer getWebappsDeploymode() {
        return this.webappsDeploymode;
    }

    public void setWebappsDeploymode(Integer webappsDeploymode) {
        this.webappsDeploymode = webappsDeploymode;
    }

    /**
     * @hibernate.property column="rights_jahia_sites"
     * length="11"
     */
    public Integer getRights() {
        return this.rights;
    }

    public void setRights(Integer rights) {
        this.rights = rights;
    }

    /**
     * @hibernate.property column="descr_jahia_sites"
     * length="250"
     */
    public String getDescr() {
        return this.descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

//    /**
//     * @hibernate.set inverse="true" cascade="none"
//     * @hibernate.collection-key column="id_jahia_site"
//     * @hibernate.collection-one-to-many class="org.jahia.hibernate.model.JahiaSiteProp"
//     */

//    public Set getSettings() {
//        return settings;
//    }
//
//    public void setSettings(Set settings) {
//        this.settings = settings;
//    }

    /**
     * @hibernate.property column="default_site_jahia_sites"
     */
    public Boolean isDefaultSite() {
        return defaultSite;
    }

    public void setDefaultSite(Boolean defaultSite) {
        this.defaultSite = defaultSite;
    }
//
//    /**
//     * @hibernate.one-to-one outer-join="auto"
//     */

//    public JahiaTemplatesSet getJahiaTemplatesSet() {
//        return this.jahiaTemplatesSet;
//    }
//
//    public void setJahiaTemplatesSet(JahiaTemplatesSet jahiaTemplatesSet) {
//        this.jahiaTemplatesSet = jahiaTemplatesSet;
//    }
//
//    /**
//     * @hibernate.set lazy="true"
//     * inverse="true"
//     * cascade="none"
//     * @hibernate.collection-key column="jahiaid_jahia_fields_def"
//     * @hibernate.collection-one-to-many class="org.jahia.hibernate.model.JahiaFieldsDef"
//     */

//    public Set getJahiaFieldsDefs() {
//        return this.jahiaFieldsDefs;
//    }
//
//    public void setJahiaFieldsDefs(Set jahiaFieldsDefs) {
//        this.jahiaFieldsDefs = jahiaFieldsDefs;
//    }
//
//    /**
//     * @hibernate.set lazy="true"
//     * inverse="true"
//     * cascade="none"
//     * @hibernate.collection-key column="site_id"
//     * @hibernate.collection-one-to-many class="org.jahia.hibernate.model.JahiaMarkupSetSite"
//     */

//    public Set getJahiaMarkupSetSites() {
//        return this.jahiaMarkupSetSites;
//    }
//
//    public void setJahiaMarkupSetSites(Set jahiaMarkupSetSites) {
//        this.jahiaMarkupSetSites = jahiaMarkupSetSites;
//    }
//
//    /**
//     * @hibernate.set lazy="true"
//     * inverse="true"
//     * cascade="none"
//     * @hibernate.collection-key column="jahiaid_jahia_ctn_def"
//     * @hibernate.collection-one-to-many class="org.jahia.hibernate.model.JahiaCtnDef"
//     */

//    public Set getJahiaCtnDefs() {
//        return this.jahiaCtnDefs;
//    }
//
//    public void setJahiaCtnDefs(Set jahiaCtnDefs) {
//        this.jahiaCtnDefs = jahiaCtnDefs;
//    }

    public String toString() {
        return new StringBuffer(getClass().getName()).append("id=").append(getId()).toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (obj != null && this.getClass() == obj.getClass()) {
            final JahiaSite castOther = (JahiaSite) obj;
            return new EqualsBuilder()
                .append(this.getId(), castOther.getId())
                .isEquals();
        }
        return false;
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

}
