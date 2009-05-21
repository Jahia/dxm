<%--

    This file is part of Jahia: An integrated WCM, DMS and Portal Solution
    Copyright (C) 2002-2009 Jahia Solutions Group SA. All rights reserved.

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

    As a special exception to the terms and conditions of version 2.0 of
    the GPL (or any later version), you may redistribute this Program in connection
    with Free/Libre and Open Source Software ("FLOSS") applications as described
    in Jahia's FLOSS exception. You should have received a copy of the text
    describing the FLOSS exception, and it is also available here:
    http://www.jahia.com/license

    Commercial and Supported Versions of the program
    Alternatively, commercial and supported versions of the program may be used
    in accordance with the terms contained in a separate written agreement
    between you and Jahia Solutions Group SA. If you are unsure which license is appropriate
    for your use, please contact the sales department at sales@jahia.com.

--%>
<%@ tag body-content="empty"
        description="Exposes the page path for the search results page into a page scope variable. Here the JSP page is used, which is configured in the search-results element of the template deployment descriptor (templates.xml) for the current template set. If not present the default search results page is used."
        %>
<%@ attribute name="var" required="true" rtexprvalue="false" type="java.lang.String"
              description="The name of the page scope attribute to expose the URL value."
        %>
<%@ variable name-from-attribute="var" variable-class="java.lang.String" alias="varName" scope="AT_END"
        %>
<%@ tag import="org.jahia.data.templates.JahiaTemplatesPackage"
        %>
<%@ tag import="org.jahia.registries.ServicesRegistry"
        %>
<%@ tag import="org.jahia.services.templates.JahiaTemplateManagerService"
        %>
<%@ tag import="org.jahia.taglibs.utility.Utils"
        %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
        %>
<%
    String path = null;
    JahiaTemplateManagerService templateMgr = ServicesRegistry.getInstance().getJahiaTemplateManagerService();

    JahiaTemplatesPackage templatePackage = templateMgr
            .getTemplatePackage(Utils.getProcessingContext((PageContext) jspContext).getSite().getTemplatePackageName());

    if (templatePackage.getSearchResultsPageName() != null) {
        path = templateMgr.resolveResourcePath(templatePackage.getSearchResultsPageName(), templatePackage.getName());
    }
    path = path != null ? path : "/engines/search/searchresult_v2.jsp";
%><c:set var="varName"><%= path %></c:set>