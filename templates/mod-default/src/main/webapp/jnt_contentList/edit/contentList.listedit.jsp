<%@ taglib prefix="template" uri="http://www.jahia.org/tags/templateLib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jcr" uri="http://www.jahia.org/tags/jcr" %>
<%@ taglib prefix="workflow" uri="http://www.jahia.org/tags/workflow" %>
<template:addResources type="javascript" resources="jquery.min.js"/>
<template:addResources type="javascript" resources="ajaxreplace.js"/>
<template:addResources type="javascript" resources="contributedefault.js"/>

<template:include templateType="html" template="hidden.header"/>
<hr/>
<c:forEach items="${currentList}" var="child" begin="${begin}" end="${end}" varStatus="status">

    <%-- buttons --%>
    <div style="border:1px solid;">
        <input type="button" value="Edit"
               onclick="replace('edit-${child.identifier}', '${url.base}${child.path}.edit.edit?ajaxcall=true', 'initEditFields()')"/>

        <input type="button" value="Preview"
               onclick="replace('edit-${child.identifier}', '${url.base}${child.path}.html?ajaxcall=true', '')"/>

        <c:if test="${currentNode.properties['j:canOrderInContribution'].boolean}">
            <c:if test="${status.index gt 0}">
                <input id="moveUp-${currentNode.identifier}-${status.index}" type="button" value="move up"
                       onclick="invert('${child.path}','${previousChild.path}', '${url.base}', '${currentNode.UUID}', '${url.current}?ajaxcall=true')"/>
            </c:if>
            <c:if test="${status.index lt listTotalSize-1}">
                <input type="button" value="move down"
                       onclick="document.getElementById('moveUp-${currentNode.identifier}-${status.index+1}').onclick()"/>
            </c:if>
        </c:if>
        <c:if test="${currentNode.properties['j:canDeleteInContribution'].boolean}">
            <workflow:input type="button" value="delete" onclick="deleteNode('${child.path}', '${url.base}', '${currentNode.UUID}', '${url.current}?ajaxcall=true')"/>
        </c:if>

        <c:set var="previousChild" value="${child}"/>
    </div>

    <div id="edit-${child.identifier}">
        <template:module templateType="html" node="${child}"/>
    </div>
</c:forEach>
<div class="clear"></div>
<c:if test="${editable and renderContext.editMode}">
    <template:module path="*"/>
</c:if>
<template:include templateType="html" template="hidden.footer"/>

<c:if test="${empty param.ajaxcall}">
    <%-- include add nodes forms --%>
    <jcr:nodeProperty node="${currentNode}" name="j:allowedTypes" var="types"/>

    <script type="text/javascript">
        function hideAdd(id, index) {
        <c:forEach items="${types}" var="type" varStatus="status">
            if (index == ${status.index}) {
                document.getElementById('add' + id + '-${status.index}').style.display = 'block';
            } else {
                document.getElementById('add' + id + '-${status.index}').style.display = 'none';
            }
        </c:forEach>
        }
    </script>
    <c:if test="${types != null}">
        <a name="add" id="add"></a>Add :
        <c:forEach items="${types}" var="type" varStatus="status">
            <jcr:nodeType name="${type.string}" var="nodeType"/>
            <a href="#add"
               onclick="hideAdd('${currentNode.identifier}',${status.index})">${jcr:labelForLocale(nodeType, renderContext.mainResourceLocale)}</a>
        </c:forEach>

        <c:forEach items="${types}" var="type" varStatus="status">
            <div style="display:none;" id="add${currentNode.identifier}-${status.index}">
            <template:module node="${currentNode}" templateType="edit" template="add">
                <template:param name="resourceNodeType" value="${type.string}"/>
            </template:module>
            </div>
        </c:forEach>
    </c:if>
</c:if>
