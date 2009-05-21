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
package org.jahia.ajax.gwt.client.data.toolbar.analytics;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: jahia
 * Date: 11 d�c. 2008
 * Time: 10:35:38
 * To change this template use File | Settings | File Templates.
 */
public class GWTJahiaAnalyticsParameter implements Serializable {
    private String gaLogin;
    private String gaPassword;
    private String gaProfile;
    private String jahiaGAprofile;
    private String dateRange;
    private String statType;
    private String siteORpage;


    public String getJahiaGAprofile() {
        return jahiaGAprofile;
    }

    public void setJahiaGAprofile(String jahiaGAProfile) {
        this.jahiaGAprofile = jahiaGAProfile;
    }

    public String getSiteORpage() {
        return siteORpage;
    }

    public void setSiteORpage(String siteORpage) {
        this.siteORpage = siteORpage;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    private String chartType;

    public GWTJahiaAnalyticsParameter() {
    }

    public GWTJahiaAnalyticsParameter(String gaLogin, String gaPassword, String gaProfile, String dateRange, String statType, String chartType, String siteORpage) {
        this.gaLogin = gaLogin;
        this.gaPassword = gaPassword;
        this.gaProfile = gaProfile;
        
        this.dateRange = dateRange;
        this.statType = statType;
        this.chartType = chartType;
        this.siteORpage = siteORpage;
    }

    public String getgaLogin() {
        return gaLogin;
    }

    public void setgaLogin(String gaLogin) {
        this.gaLogin = gaLogin;
    }

    public String getgaPassword() {
        return gaPassword;
    }

    public void setgaPassword(String gaPassword) {
        this.gaPassword = gaPassword;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getgaProfile() {
        return gaProfile;
    }

    public void setgaProfile(String gaProfile) {
        this.gaProfile = gaProfile;
    }

    public String getStatType() {
        return statType;
    }

    public void setStatType(String statType) {
        this.statType = statType;
    }
}
