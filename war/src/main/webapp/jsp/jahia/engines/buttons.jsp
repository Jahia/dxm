<%--

    
    This file is part of Jahia: An integrated WCM, DMS and Portal Solution
    Copyright (C) 2002-2009 Jahia Limited. All rights reserved.
    
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
    in Jahia's FLOSS exception. You should have recieved a copy of the text
    describing the FLOSS exception, and it is also available here:
    http://www.jahia.com/license
    
    Commercial and Supported Versions of the program
    Alternatively, commercial and supported versions of the program may be used
    in accordance with the terms contained in a separate written agreement
    between you and Jahia Limited. If you are unsure which license is appropriate
    for your use, please contact the sales department at sales@jahia.com.

--%>

<%@ page language="java" %>
<%@ page import="org.jahia.params.ProcessingContext" %>
<%@ page import="org.jahia.services.lock.LockKey" %>
<%@ page import="org.jahia.services.lock.LockPrerequisites" %>
<%@ page import="org.jahia.services.lock.LockPrerequisitesResult" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="internal" uri="http://www.jahia.org/tags/internalLib" %>
<%@ taglib prefix="utility" uri="http://www.jahia.org/tags/utilityLib" %>
<jsp:useBean id="jspSource" class="java.lang.String" scope="request"/>
<%
final ProcessingContext jParams = (ProcessingContext) request.getAttribute("org.jahia.params.ParamBean");
Map engineMap = (Map) request.getAttribute("org.jahia.engines.EngineHashMap");
if (engineMap == null) {
  engineMap = (Map) session.getAttribute("jahia_session_engineMap");
}
String engineName = (String) engineMap.get("engineName");
if (engineName == null) {
  engineName = "unknown";
}
final String theScreen = (String) engineMap.get("screen");

final String noApply = (String) engineMap.get("noApply");
final boolean showButtons = request.getAttribute("DisableButtons") == null;
LockKey lockKey = (LockKey) engineMap.get("LockKey");
final LockPrerequisitesResult results;
if (lockKey != null) {
  results = LockPrerequisites.getInstance().getLockPrerequisitesResult(lockKey);
} else {
  results = null;
}
LockKey engineLockKey = (LockKey)engineMap.get("lock");
%>
<!-- actionBar (start) -->
<div id="actionBar">
  <% if (showButtons) { %>
    <% if ("locks".equals(engineName)) { %>
      <span class="dex-PushButton">
        <span class="first-child">
          <a href="javascript:sendFormApply();" class="ico-ok" title="<utility:resourceBundle resourceBundle="JahiaInternalResources" resourceName="org.jahia.altApplyWithoutClose.label"/>" onclick="setWaitingCursor(1);">
            <utility:resourceBundle resourceBundle="JahiaInternalResources" resourceName="org.jahia.button.ok"/></a>
        </span>
      </span>
    <% } else if (!engineMap.containsKey("errorMessage") || (engineMap.get("errorMessage") == Boolean.FALSE)) { %>
      <% if (results != null) { %>
        <span class="dex-PushButton">
          <span class="first-child">
            <a href="#" onclick="return false;" class="ico-ok"><utility:resourceBundle resourceBundle="JahiaInternalResources" resourceName="org.jahia.button.ok"/></a>
          </span>
        </span>
      <% } else { %>
        <span class="dex-PushButton">
          <span class="first-child">
            <a href="javascript:sendFormSave();" class="ico-ok" title="<utility:resourceBundle resourceBundle="JahiaInternalResources" resourceName="org.jahia.altApplyAndClose.label"/>" onclick="setWaitingCursor(1);">
              <utility:resourceBundle resourceBundle="JahiaInternalResources" resourceName="org.jahia.button.ok"/></a>
          </span>
        </span>
      <% } %>
    <% } %>
    <% if (!"logs".equals(theScreen) && !"import".equals(theScreen) && !"workflow".equals(engineName) && !"deletecontainer".equals(engineName) && !"".equals(noApply)) { %>
      <% if (results != null) { %>
    <!--
    <span class="dex-PushButton">
          <span class="first-child">
            <utility:resourceBundle resourceBundle="JahiaInternalResources" resourceName="org.jahia.button.apply"/>
          </span>
        </span>
     -->
      <% } else { %>
        <span class="dex-PushButton">
          <span class="first-child">
            <a href="javascript:sendFormApply();" class="ico-apply" title="<utility:resourceBundle resourceBundle="JahiaInternalResources" resourceName="org.jahia.altApplyWithoutClose.label"/>" onclick="setWaitingCursor(1);">
              <utility:resourceBundle resourceBundle="JahiaInternalResources" resourceName="org.jahia.button.apply"/></a>
          </span>
        </span>
      <% } %>
    <% } %>
  <% } %>
  <span class="dex-PushButton">
    <span class="first-child">
      <a href="javascript:window.close();" class="ico-cancel" title="<utility:resourceBundle resourceBundle="JahiaInternalResources" resourceName="org.jahia.altCloseWithoutSave.label"/>">
        <utility:resourceBundle resourceBundle="JahiaInternalResources" resourceName="org.jahia.button.cancel"/></a>
    </span>
  </span>
</div>
<!-- actionBar (end) -->