/**
 * 
 * This file is part of Jahia: An integrated WCM, DMS and Portal Solution
 * Copyright (C) 2002-2009 Jahia Limited. All rights reserved.
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
 * between you and Jahia Limited. If you are unsure which license is appropriate
 * for your use, please contact the sales department at sales@jahia.com.
 */

package org.jahia.taglibs.uicomponents.portlets;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import org.jahia.data.beans.RequestBean;
import org.jahia.data.beans.portlets.PortletWindowBean;
import org.jahia.gui.GuiBean;
import org.jahia.gui.HTMLToolBox;
import org.jahia.params.ProcessingContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <p>Title: Renders list of portlet modes</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Jahia Ltd</p>
 * @author Serge Huber
 * @version 1.0
 *
 *
 *  Bean that contains all information relative to a portlet
 * window.</p>
 * <p>Description: Used to build user interfaces for template developers when
 * using portlets</p>
 *
 *
 * @jsp:tag name="portletModes" body-content="empty"
 * description="Renders list of portlet modes.
 *
 * <p><attriInfo>Use this tag to display the portlet's supported modes which are dependant on each portlet. Examples include Edit, View,
 * Preview etc...
 *
 * <p>This tag is typically used in conjunction with <a href='windowStates.html' target='tagFrame'>jahiaHtml:windowStates</a>.
 *
 * <p><b>Example :</b> Go to <a href='windowStates.html' target='tagFrame'>jahiaHtml:windowStates</a>.
 *
 *
 * <p>Introduced as from Jahia version 4.5
 *
 * </attriInfo>"
 *
 *
 */

public class PortletModesTag extends TagSupport {

    private static org.apache.log4j.Logger logger =
        org.apache.log4j.Logger.getLogger(PortletModesTag.class);

    private String namePostFix = "";
    private String name = null;
    private RequestBean requestBean = null;
    private ProcessingContext processingContext = null;
    private GuiBean guiBean = null;
    private HTMLToolBox htmlToolBox = null;
    private String resourceBundle = "JahiaInternalResources";
    private String listCSSClass = "portletModes";
    private String currentCSSClass = "current";

    /**
     * @jsp:attribute name="name" required="true" rtexprvalue="true"
     * description="name of the pageContext attribute holding the PortletWindowBean
     *
     * <p><attriInfo>
     * </attriInfo>"
     */
    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public PortletModesTag () {
    }

    /**
     * @jsp:attribute name="namePostFix" required="false" rtexprvalue="true"
     * description="String to append to portlet name's resource bundle key
     *
     * <p><attriInfo>This corresponds to the following code in HTMLToolbox.drawWindowStateList() :
     * <br>getResource(resourceBundle,\"org.jahia.taglibs.html.portlets.portletmodes.\" + curPortletModeBean.getName()
     * + \".label\" + namePostFix  + \"</span></a>\")
     * <p>Default is \"\".
     * </attriInfo>"
     */
    public String getNamePostFix () {
        return namePostFix;
    }

    public void setNamePostFix (String namePostFix) {
        this.namePostFix = namePostFix;
    }

    /**
     * @jsp:attribute name="resourceBundle" required="false" rtexprvalue="true"
     * description="resource bundle to use to find portlet mode labels.
     *
     * <p><attriInfo>Default is 'JahiaInternalResources'.
     * </attriInfo>"
     */
    public String getResourceBundle () {
        return resourceBundle;
    }

    public void setResourceBundle (String resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    /**
     * @jsp:attribute name="listCSSClass" required="false" rtexprvalue="true"
     * description="CSS class to use to display portlet mode labels.
     *
     * <p><attriInfo>Default is 'portletModes'.
     * </attriInfo>"
     */
    public String getListCSSClass () {
        return listCSSClass;
    }

    public void setListCSSClass (String listCSSClass) {
        this.listCSSClass = listCSSClass;
    }

    /**
     * @jsp:attribute name="currentCSSClass" required="false" rtexprvalue="true"
     * description="CSS class to use to display the currently selected portlet mode label.
     *
     * <p><attriInfo>Default is 'current'.
     * </attriInfo>"
     */
    public String getCurrentCSSClass () {
        return currentCSSClass;
    }

    public void setCurrentCSSClass (String currentCSSClass) {
        this.currentCSSClass = currentCSSClass;
    }

    public int doStartTag () {

        requestBean = (RequestBean) pageContext.findAttribute("currentRequest");
        processingContext = requestBean.getParamBean();
        guiBean = new GuiBean(processingContext);
        htmlToolBox = new HTMLToolBox(guiBean, processingContext);
        PortletWindowBean portletWindowBean = (PortletWindowBean) pageContext.
                                              findAttribute(name);
        if (portletWindowBean == null) {
            logger.error("Couldn't find any PortletWindowBean with name " +
                         name);
            return SKIP_BODY;
        }

        JspWriter out = pageContext.getOut();
        try {

            htmlToolBox.drawPortletModeList(portletWindowBean, namePostFix,
                                            resourceBundle, listCSSClass,
                                            currentCSSClass, out);

        } catch (IOException ioe) {
            logger.error(
                "IO exception while trying to display action menu for object " +
                name, ioe);
        }

        return SKIP_BODY;
    }

    public int doEndTag ()
        throws JspException {
        // let's reinitialize the tag variables to allow tag object reuse in
        // pooling.
        namePostFix = "";
        name = null;
        processingContext = null;
        requestBean = null;
        guiBean = null;
        htmlToolBox = null;
        resourceBundle = "JahiaInternalResources";
        listCSSClass = "portletModes";
        currentCSSClass = "current";
        return EVAL_PAGE;
    }

}
