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
package org.jahia.views.engines.coreengine;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jahia.bin.errors.ErrorHandler;
import org.jahia.data.JahiaData;
import org.jahia.exceptions.JahiaException;
import org.jahia.params.ParamBean;
import org.jahia.views.engines.JahiaEngineCommonData;

/**
 * A struts action that display a Jahia Page ( as Jahia Engine Core )
 *
 */
public final class CoreAction extends Action {

    public static final String ENGINE_NAME = "CoreAction";

    /**
    * Process the specified HTTP request, and create the corresponding HTTP
    * response (or forward to another web component that will create it).
    * Return an <code>ActionForward</code> instance describing where and how
    * control should be forwarded, or <code>null</code> if the response has
    * already been completed.
    *
    * @param mapping The ActionMapping used to select this instance
    * @param actionForm The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    *
    * @exception IOException if an input/output error occurs
    * @exception ServletException if a servlet exception occurs
    */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                                 throws IOException, ServletException{

        try {
			// business process here

            // engines helpers
            JahiaEngineCommonData engineCommonData =
                    new JahiaEngineCommonData(request);

            engineCommonData.setEngineTitle(ENGINE_NAME);

            request.setAttribute(JahiaEngineCommonData.JAHIA_ENGINE_COMMON_DATA,
                                 engineCommonData);

            JahiaData jData = null;
            ParamBean jParams = (ParamBean)
                                request.getAttribute(
                "org.jahia.params.ParamBean");
            if (jParams.getPage() != null) {
                jData = new JahiaData(jParams); // loaded with fields and
            }
            // for JSP
            jParams.getRequest().setAttribute("org.jahia.data.JahiaData", jData);

        } catch ( JahiaException je){
            ErrorHandler.getInstance().handle(je, request, response);
            return null;
        }

        //Forward to default core Engine that will display the page with its template
        //return (mapping.getInputForward());
        return (mapping.findForward("coreEngine"));
    }

}
