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
<%@page import="org.jahia.bin.*" %>
<%
String  processMessage   = (String)  request.getAttribute("processMessage");
Boolean keeprecentlogs   = (Boolean) session.getAttribute("keeprecentlogs");
Integer maxlogsdays      = (Integer) session.getAttribute("maxlogsdays");
String  engineMsg        = (String)  request.getAttribute("engineMessage"); 
%>
<%@include file="/admin/include/header.inc" %>
<%
stretcherToOpen = 0;
%>
<div id="topTitle">
  <h1>Jahia</h1>
  <h2 class="edit"><fmt:message key="org.jahia.admin.audit.ManageLogs.flushLogs.label"/></h2>
</div>
<div id="main">
  <table style="width: 100%;" class="dex-TabPanel" cellpadding="0" cellspacing="0">
    <tbody>
      <tr>
        <td style="vertical-align: top;" align="left">
          <%@include file="/admin/include/tab_menu.inc" %>
        </td>
      </tr>
      <tr>
        <td style="vertical-align: top;" align="left" height="100%">
        <div class="dex-TabPanelBottom">
        <div class="tabContent">
            <jsp:include page="/admin/include/left_menu.jsp">
                <jsp:param name="mode" value="server"/>
            </jsp:include>
        
        <div id="content" class="fit">
          <p>
          <fmt:message key="org.jahia.admin.audit.ManageLogs.reallyWantFlushAll.label"/><%=engineMsg %>?
		  </p>
        </div>
		</div>
        </td>
      </tr>
    </tbody>
  </table>
</div>
<div id="actionBar">
  <span class="dex-PushButton">
    <span class="first-child">
      <a class="ico-cancel" href='<%=JahiaAdministration.composeActionURL(request,response,"logs","&sub=view")%>'><fmt:message key="org.jahia.admin.cancel.label"/></a>
    </span>
  </span>
  <span class="dex-PushButton">
    <span class="first-child">
      <a class="ico-ok" href='<%=JahiaAdministration.composeActionURL(request,response,"logs","&sub=flush")%>'><fmt:message key="org.jahia.admin.ok.label"/></a>
    </span>
  </span>
</div>
</div>
<%@include file="/admin/include/footer.inc" %>