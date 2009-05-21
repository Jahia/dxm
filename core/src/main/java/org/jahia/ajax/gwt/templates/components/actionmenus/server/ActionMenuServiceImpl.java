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
package org.jahia.ajax.gwt.templates.components.actionmenus.server;

import org.jahia.ajax.gwt.client.service.actionmenu.ActionMenuService;
import org.jahia.ajax.gwt.client.data.actionmenu.timebasedpublishing.GWTJahiaTimebasedPublishingDetails;
import org.jahia.ajax.gwt.client.data.actionmenu.timebasedpublishing.GWTJahiaTimebasedPublishingState;
import org.jahia.ajax.gwt.client.data.actionmenu.actions.*;
import org.jahia.ajax.gwt.client.data.actionmenu.workflow.GWTJahiaWorkflowState;
import org.jahia.ajax.gwt.client.data.actionmenu.GWTJahiaGlobalState;
import org.jahia.ajax.gwt.client.data.actionmenu.GWTJahiaIntegrityState;
import org.jahia.ajax.gwt.client.data.actionmenu.acldiff.GWTJahiaAclDiffState;
import org.jahia.ajax.gwt.client.data.actionmenu.acldiff.GWTJahiaAclDiffDetails;
import org.jahia.ajax.gwt.templates.components.actionmenus.server.helper.*;
import org.jahia.ajax.gwt.client.data.config.GWTJahiaPageContext;
import org.jahia.ajax.gwt.commons.server.AbstractJahiaGWTServiceImpl;
import org.jahia.params.ProcessingContext;
import org.jahia.services.preferences.user.UserPreferencesHelper;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Implementation of GWT service for action menus and extras such as ACL, workflow and time-based publishing.
 *
 * @author rfelden
 * @version 22 janv. 2008 - 12:00:25
 */
public class ActionMenuServiceImpl extends AbstractJahiaGWTServiceImpl implements ActionMenuService {

    private final static Logger logger = Logger.getLogger(ActionMenuServiceImpl.class) ;

    public GWTJahiaGlobalState getGlobalStateForObject(GWTJahiaPageContext page, String objectKey, String wfKey, String languageCode) {
        final ProcessingContext jParams = retrieveParamBean(page) ;
        GWTJahiaWorkflowState wf = null ;
        if (wfKey != null && wfKey.length() > 0) {
            wf = getWorkflowStateForObject(page, objectKey, wfKey,  languageCode) ;
        }
        GWTJahiaAclDiffState acl = null ;
        boolean aclDiff = UserPreferencesHelper.isDisplayAclDiffState(jParams.getUser()) ;
        if (aclDiff) {
            acl = getAclDiffState(page, objectKey) ;
        }
        GWTJahiaTimebasedPublishingState tbp = null ;
        boolean timebasepublishing = UserPreferencesHelper.isDisplayTbpState(jParams.getUser()) ;
        if (timebasepublishing) {
            tbp = getTimebasedPublishingState(page, objectKey) ;
        }
        GWTJahiaIntegrityState integrityState = null ;
        boolean integrity = UserPreferencesHelper.isDisplayIntegrityState(jParams.getUser()) ;
        if (integrity) {
            integrityState = getIntegrityState(page, objectKey) ;
        }
        
        if (logger.isDebugEnabled()) {
            logger.debug(new StringBuilder("Server call details :\n")
                    .append("\t\t\t\t\tObject key : ").append(objectKey).append(" - Workflow key : ").append(wfKey).append("\n")
                    .append("\t\t\t\t\tWorkflow state (").append(String.valueOf(wfKey != null)).append(") : ").append((wf != null ? wf.getExtendedWorkflowState() : "null")).append("\n")
                    .append("\t\t\t\t\tACL diff state (").append(aclDiff).append(") : ").append(acl != null ? acl.getObjectKey() : "null").append("\n")
                    .append("\t\t\t\t\tTBP state (").append(timebasepublishing).append(") : ").append((tbp != null ? tbp.getState() : "null"))
                    .append("\t\t\t\t\tIntegrity state (").append(integrity).append(") : ").append((integrityState != null ? "not OK" : "OK")).toString());
        }
        GWTJahiaGlobalState state = new GWTJahiaGlobalState(acl, tbp, wf);
        state.setIntegrityState(integrityState);
        
        return state;
    }

    private GWTJahiaIntegrityState getIntegrityState(GWTJahiaPageContext page,
            String objectKey) {
        return IntegrityHelper.getState(objectKey, retrieveParamBean(page));
    }

    public GWTJahiaWorkflowState getWorkflowStateForObject(GWTJahiaPageContext page, String objectKey, String wfKey, String languageCode) {
        final ProcessingContext jParams = retrieveParamBean(page) ;
        return WorkflowHelper.getWorkflowStateForObject(jParams, wfKey, languageCode) ;
    }

    public GWTJahiaTimebasedPublishingState getTimebasedPublishingState(GWTJahiaPageContext page, String objectKey) {
        return TimebasedPublishingHelper.getTimebasePublishingState(getThreadLocalRequest(), retrieveParamBean(page), objectKey) ;
    }

    public GWTJahiaTimebasedPublishingDetails getTimebasedPublishingDetails(GWTJahiaPageContext page, GWTJahiaTimebasedPublishingState state) {
        return TimebasedPublishingHelper.getTimebasedPublishingDetails(retrieveParamBean(page), state) ;
    }

    public GWTJahiaAclDiffState getAclDiffState(GWTJahiaPageContext page, String objectKey) {
        GWTJahiaAclDiffState state = null;
        try {
            state = AclDiffHelper.getAclDiffState(getThreadLocalRequest(), retrieveParamBean(page), objectKey) ;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return state ;
    }

    public GWTJahiaAclDiffDetails getAclDiffDetails(GWTJahiaPageContext page, String objectKey) {
        GWTJahiaAclDiffDetails details = null;
        try {
            details = AclDiffHelper.getAclDiffDetails(retrieveParamBean(page), objectKey) ;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return details ;
    }

    public String isActionMenuAvailable(GWTJahiaPageContext page, String objectKey, String bundleName, String labelKey) {
        ProcessingContext processingContext = retrieveParamBean(page) ;
        return ActionMenuHelper.isActionMenuAvailable(processingContext, page, objectKey, bundleName, labelKey) ;
    }

    public List<GWTJahiaAction> getAvailableActions(final GWTJahiaPageContext page, final String objectKey, final String bundleName, final String namePostFix) {
        final ProcessingContext jParams = retrieveParamBean(page) ;
        HttpSession session = getThreadLocalRequest().getSession() ;
        return ActionMenuHelper.getAvailableActions(session, jParams, page, objectKey, bundleName, namePostFix) ;
    }

    public Boolean clipboardIsEmpty() {
        Object clipboardContent = getThreadLocalRequest().getSession().getAttribute(GWTJahiaAction.CLIPBOARD_CONTENT) ;
        return clipboardContent == null ;
    }

    public Boolean clipboardCopy(GWTJahiaPageContext page, String objectKey) {
        HttpSession session = getThreadLocalRequest().getSession() ;
        final ProcessingContext jParams = retrieveParamBean(page) ;
        return ClipboardHelper.clipboardCopy(session, jParams, objectKey) ;
    }

    public Boolean clipboardPaste(GWTJahiaPageContext page, String destObjectKey) {
        final HttpSession session = getThreadLocalRequest().getSession() ;
        final ProcessingContext processingContext  = retrieveParamBean(page) ;
        return ClipboardHelper.clipboardPaste(session, processingContext,  destObjectKey, false) ;
    }

    public Boolean clipboardPasteReference(GWTJahiaPageContext page, String destObjectKey) {
        final HttpSession session = getThreadLocalRequest().getSession() ;
        final ProcessingContext processingContext  = retrieveParamBean(page) ;
        return ClipboardHelper.clipboardPaste(session, processingContext,  destObjectKey, true) ;
    }

    public void hack(GWTJahiaAction action) {
        // this is only a blank method to avoid a bug when a serializable type is unrecognized by gwt using a collection.
    }

}



