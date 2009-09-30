<%@ taglib prefix="jcr" uri="http://www.jahia.org/tags/jcr" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="utility" uri="http://www.jahia.org/tags/utilityLib" %>
<%@ taglib prefix="template" uri="http://www.jahia.org/tags/templateLib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jcr:nodeProperty node="${currentNode}" name="insertPosition" var="insertPosition"/>
<jcr:nodeProperty node="${currentNode}" name="insertType" var="insertType"/>
<jcr:nodeProperty node="${currentNode}" name="mainContentTitle" var="mainContentTitle"/>
<jcr:nodeProperty node="${currentNode}" name="insertWidth" var="insertWidth"/>
<jcr:nodeProperty node="${currentNode}" name="insertText" var="insertText"/>
<jcr:nodeProperty node="${currentNode}" name="mainContentImage" var="mainContentImage"/>
<jcr:nodeProperty node="${currentNode}" name="mainContentBody" var="mainContentBody"/>
<jcr:nodeProperty node="${currentNode}" name="mainContentAlign" var="mainContentAlign"/>

<h3>${mainContentTitle.string}</h3>

<div class='${insertType.string}-top float${insertPosition.string}'
     style='width:${insertWidth.string}px'>

    <div class="${insertType.string}-bottom">
        ${insertText.string}
    </div>
</div>
<div class="float${mainContentAlign.string}"><img src="${mainContentImage.node.url}" alt="${mainContentImage.node.url}"/></div>
<div>
    ${mainContentBody.string}
</div>
<div class="clear"></div>