<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jcr" uri="http://www.jahia.org/tags/jcr" %>
<%@ taglib prefix="utility" uri="http://www.jahia.org/tags/utilityLib" %>
<%@ taglib prefix="template" uri="http://www.jahia.org/tags/templateLib" %>

<template:addResources type="css" resources="960.css,userProfile.css"/>

<c:set var="fields" value="${currentNode.propertiesAsString}"/>
<c:set var="person" value="${fields['j:title']} ${fields['j:firstName']} ${fields['j:lastName']}"/>
<%--map all display values --%>
<jsp:useBean id="userProperties" class="java.util.HashMap"/>
<div class="container container_16"> <!--start container_16-->
<div class='grid_4'><!--start grid_4-->
<div class="image">
		<div class="itemImage itemImageRight"><a href="#"><img alt="" src="img-text/user.gif"/></a></div>
</div>

<div class="box"><!--start box -->
  <div class="boxshadow boxpadding16 boxmarginbottom16">
                <div class="box-inner">
                    <div class="box-inner-border">

                    <h3 class="boxtitleh3"><c:out value="${person}"/></h3>
                    <div class="list3 user-profile-list">
                    <ul class="list3 user-profile-list">
                    <li><span class="label">Age : </span> 45ans <span class="visibility">Visible | <a title="" href="#" class="main">Changer</a></span></li>
                    <li><span class="label">Sexe : </span> 27 rue d'Hauteville 75001 Paris <span class="visibility">Visible | <a title="" href="#" class="main">Changer</a></span></li>

                    <li><span class="label">Email Perso: </span> jhon.james@intranet.com <span class="visibility">Visible | <a title="" href="#" class="main">Changer</a></span></li>
                    </ul>
                    </div>
                    <div class="AddItemForm">
                      <!--start AddItemForm -->
                      <form method="post" action="#">

                        <fieldset>
                          <legend>AddItemForm</legend>
                        <p class="field">
                          <label for="label">Label :</label>

                          <input type="text" name="label" id="label" class="AddItemFormLabel" value="Label" tabindex="9" /><span> : </span>
                            <label for="value" >Value :</label>

                            <input type="text" name="value" id="value" class="AddItemFormValue" value="Value" tabindex="10" />
                                                        <input class="png gobutton" type="image" src="img/more.png" alt="Sidentifier" tabindex="11"/>
                          </p>

                        </fieldset>
                      </form>
                    </div>
                    <div class="divButton">
	<a class="aButton" href="#"><span>Sauvegarder</span></a>

    <a class="aButton" href="#"><span>Annuler</span></a>
<div class="clear"></div></div>
                    <!--stop sendMailForm -->

                        <div class="clear"></div>
                  </div>
			</div>
		</div>
</div><!--stop box -->


		<h3 class="titleIcon">Friends<img title="" alt="" src="img-text/friends.png"/></h3>
		<ul class="list2 friends-list">
<li>
        	<div class="thumbnail">
              <a href="#"><img src="img-text/friend.png" alt="friend" border="0"/></a>            </div>
            <h4><a href="#"> Follower</a></h4>
<div class='clear'></div></li>
<li><div class="thumbnail"><a href="#"><img src="img-text/friend.png" alt="friend" border="0"/></a></div>

        	<h4><a href="#">Follower</a></h4>
        	<div class='clear'></div></li>
            <li><div class="thumbnail"><a href="#"><img src="img-text/friend.png" alt="friend" border="0"/></a></div>
        	<h4><a href="#">Follower</a></h4>
        	<div class='clear'></div></li>
            <li><div class="thumbnail"><a href="#"><img src="img-text/friend.png" alt="friend" border="0"/></a></div>
        	<h4><a href="#">Follower</a></h4>

        	<div class='clear'></div></li>
            <li><div class="thumbnail"><a href="#"><img src="img-text/friend.png" alt="friend" border="0"/></a></div>
        	<h4><a href="#">Follower</a></h4>
        	<div class='clear'></div></li>
            <li><div class="thumbnail"><a href="#"><img src="img-text/friend.png" alt="friend" border="0"/></a></div>
        	<h4><a href="#">Follower</a></h4>
        	<div class='clear'></div></li>
<li class="last">

        	<div class="thumbnail">
            <a href="#"><img src="img-text/friend.png" alt="friend" border="0"/></a>            </div><h4><a href="#"> Follower</a></h4>
            <div class='clear'></div></li>
        </ul>



<!--stop box -->
<div class='clear'></div></div><!--stop grid_4-->


	<div class='grid_8'><!--start grid_8-->

<div class="box"><!--start box -->
<div class="arrow-white-shadow-left"></div>
            <div class="boxshadow boxpadding16 boxmarginbottom16">
                <div class="box-inner">
                    <div class="box-inner-border">
                        <template:module node="${currentNode}" template="detailNew"/>
                  <!--stop box -->
                        <div class="clear"></div>
                  </div>
			</div>
		</div>
</div><!--stop box -->
<div class="box"><!--start box -->
  <div class="boxshadow boxpadding16 boxmarginbottom16">
                <div class="box-inner">

                    <div class="box-inner-border">
                    <h3 class="boxtitleh3">Preferences</h3>

                <div class="preferencesForm"><!--start preferencesForm -->

                      <form method="post" action="#">
                        <fieldset>
                          <legend>Preferences Form</legend>
                 <p><label for="languages" class="left">Modifier la langue par default:</label>

                 <select name="languages" id="languages" class="combo" tabindex="9">
                     <option value="choose"> Langue par default </option>
                     <option value="langue1" selected="selected">Langue 1</option>
                     <option value="langue2">Langue 2</option>
                <option value="langue3">Langue 3</option>
                 </select>

                </p>
                </fieldset>
                </form>
                </div><!--stop preferencesForm -->
                    <div class="divButton">
	<a class="aButton" href="#"><span>Sauvegarder</span></a>
    <a class="aButton" href="#"><span>Annuler</span></a>
<div class="clear"></div></div>

                    <!--stop sendMailForm -->

                        <div class="clear"></div>
                  </div>
			</div>
		</div>
</div><!--stop box -->
<div class="box">
            <div class="boxpadding16 boxmarginbottom16">
                <div class="box-inner">
                    <div class="box-inner-border"><!--start box -->

                    <h3 class="boxtitleh3">About Me</h3>
                        <p>dolor sit amet, consectetuer adipiscing elit. Morbi adipiscing, metus non ultricies pharetra, libero ipsum placerat diam, eu varius enim enim id metus. Fusce tincidunt semper tellus. Morbi hendrerit. In sit amet libero. Curabitur ultricies. Nulla at nunc tristique sem venenatis tristique. Integer nunc erat, vehicula quis, varius non, bibendum eget, ligula.</p>
                        <h4>Titre de  niveau 4 (h4)</h4>
                <p>Introduction dolor sit amet, consectetuer adipiscing elit. Morbi adipiscing, metus non ultricies pharetra, libero ipsum placerat diam, eu varius enim enim id metus. Fusce tincidunt semper tellus. Morbi hendrerit. In sit amet libero. Curabitur ultricies. Nulla at nunc tristique sem venenatis tristique. Integer nunc erat, vehicula quis, varius non, bibendum eget, ligula. Sed pede enim, sagittis non, pulvinar sit amet, consectetuer in, dui. In ac ligula. Praesent vitae lacus. Curabitur quis purus.</p>
                <ul>
                  <!--start generic list -->
                  <li><a href="#" title="generic list">Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</a></li>

                  <li><a href="#" title="generic list">In sit amet libero. Curabitur ultricies.</a></li>
                  <li><a href="#" title="generic list">Nulla at nunc tristique sem venenatis tristique.</a></li>
                  <li><a href="#" title="generic list">In sit amet libero. Curabitur ultricies. Nulla at nunc tristique sem venenatis tristique. Integer nunc erat, vehicula quis, varius non, bibendum eget, ligula. Sed pede enim, sagittis non, pulvinar sit amet, consectetuer in, dui. In ac ligula. Praesent vitae lacus. Curabitur quis purus.</a></li>
                  <li><a href="#" title="generic list">Praesent vitae lacus. Curabitur quis purus.</a></li>
                </ul>
                <!--generic list -->
                <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi adipiscing, metus non ultricies pharetra, libero ipsum placerat diam, eu varius enim enim id metus. Fusce tincidunt semper tellus. Morbi hendrerit. In sit amet libero. Curabitur ultricies. Nulla at nunc tristique sem venenatis tristique. Integer nunc erat, vehicula quis, varius non, bibendum eget, ligula. Sed pede enim, sagittis non, pulvinar sit amet, consectetuer in, dui. In ac ligula. Praesent vitae lacus. Curabitur quis purus.</p>



                        <div class="clear"></div>
                    </div>
			</div>
		</div>
</div><!--stop box -->


	</div><!--stop grid_8-->
<div class='grid_4'><!--start grid_4-->
<div class="box">
            <div class="boxshadow boxgrey boxpadding16 boxmarginbottom16">

                <div class="box-inner">
                    <div class="box-inner-border"><!--start box -->







            <div class="thumbnail">
              <a href="#"><img src="img-text/rss.png" alt="" border="0"/></a>
            <div class='clear'></div></div>
            <h3 class="boxtitleh3"><a href="#">Follow me</a></h3>
            <p>dolor sit amet, consectetuer adipiscing elit. Morbi adipiscing, metus non ultricies pharetra</p>

                        <div class="clear"></div>

                  </div>
			</div>
		</div>
</div><!--stop box -->

		<h3 class="titleIcon"><a href="#">Groupes<img title="" alt="" src="img-text/groups.png"/></a></h3>
		<ul class="list2 group-list">
<li>
        	<div class="thumbnail">
              <a href="#"><img src="img-text/group.png" alt="group" border="0"/></a>

            </div>
            <h4><a href="#">Nom de mon Groupe</a></h4><div class='clear'></div>
            </li>


<li><div class="thumbnail"><a href="#"><img src="img-text/group.png" alt="group" border="0"/></a></div>
        	<h4><a href="#">Nom de mon Groupe</a></h4><div class='clear'></div></li>
            <li><div class="thumbnail"><a href="#"><img src="img-text/group.png" alt="group" border="0"/></a></div>
        	<h4><a href="#">Nom de mon Groupe</a></h4><div class='clear'></div></li>

            <li><div class="thumbnail"><a href="#"><img src="img-text/group.png" alt="group" border="0"/></a></div>
        	<h4><a href="#">Nom de mon Groupe</a></h4><div class='clear'></div></li>
<li class="last">
        	<div class="thumbnail">
              <a href="#">
            <img src="img-text/group.png" alt="group" border="0"/>              </a>            </div><h4><a href="#">Nom de mon Groupe</a></h4><div class='clear'></div></li>
        </ul>



<!--stop box -->
<div class="box">
            <div class="boxpadding16 boxmarginbottom16">
                <div class="box-inner">
                    <div class="box-inner-border"><!--start box -->
                    <h3 class="boxtitleh3">M’envoyer un email</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ut sapien at nulla ultrices volutpat vel nec velit. Ut vel tortor tellus.
                      </p>

                      <div class="sendMailForm">
                      <!--start sendMailForm -->
                      <form method="post" action="#">
                        <fieldset>
                          <legend>Send mail</legend>
                        <p class="field">
                            <label for="from">From :</label>
                            <input type="text" name="from" id="from" class="sendMailFormFrom" value="Votre email" tabindex="6" />

                          </p>
                          <p class="field">
                            <label for="message" >Message :</label>
                            <textarea rows="7" cols="35" id="message" name="message" tabindex="7">Votre message</textarea>
                          </p>
                        <div class="divButton">
	<a class="aButton" tabindex="8" href="#"><span >Send mail</span></a>

<div class="clear"></div></div>
                        </fieldset>
                      </form>
                    </div>
                    <!--stop sendMailForm -->
                    <div class="clear"></div>
                    </div>
			</div>
		</div>

</div><!--stop box -->
	  <div class='clear'></div></div><!--stop grid_4-->


	<div class='clear'></div>
</div><!--stop container_16-->

<div class="clear"></div>
