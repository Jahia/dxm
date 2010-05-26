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
package org.jahia.ajax.gwt.commons.server;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.impl.LegacySerializationPolicy;
import org.jahia.registries.ServicesRegistry;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.apache.log4j.Logger;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Spring MVC controller implementation to dispatch requests to GWT services.
 * 
 * @author Sergiy Shyrkov
 */
public class GWTController extends RemoteServiceServlet implements Controller,
        ServletContextAware, ApplicationContextAware {

    private final static Logger logger = Logger.getLogger(GWTController.class);

    private String remoteServiceName;

    private Integer sessionExpiryTime = null;

    private ServletContext servletContext;
    
    private ApplicationContext applicationContext;

    public void setSessionExpiryTime(int sessionExpiryTime) {
        this.sessionExpiryTime = sessionExpiryTime;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
     * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        long startTime = System.currentTimeMillis();
        final HttpSession session = request.getSession(false);
        if (session != null) {
            if (sessionExpiryTime != null && session.getMaxInactiveInterval() != sessionExpiryTime * 60) {
                session.setMaxInactiveInterval(sessionExpiryTime * 60);
            }
        }
        doPost(request, response);
        if (logger.isDebugEnabled()) {
            logger.debug("Handled request to GWT service '" + remoteServiceName
                    + "' in " + (System.currentTimeMillis() - startTime)
                    + " ms");
        }
        return null;
    }

    @Override
    public String processCall(String payload) throws SerializationException {
        RemoteService remoteService = null;
        try {
            remoteService = (RemoteService) applicationContext.getBean(remoteServiceName);
            setServiceData(remoteService, false);

            RPCRequest rpcRequest = RPC.decodeRequest(payload, remoteService
                    .getClass(), this);

            return RPC.invokeAndEncodeResponse(remoteService, rpcRequest
                    .getMethod(), rpcRequest.getParameters(), rpcRequest
                    .getSerializationPolicy());
        } catch (Throwable e) {
            logger.error("An error occurred calling the GWT service " + remoteServiceName + ". Cause: " + e.getMessage(), e);
            return RPC.encodeResponseForFailure(null, e);
        } finally {
            if (remoteService != null) {
                setServiceData(remoteService, true);
            }
            
        }
    }

    @Override protected SerializationPolicy doGetSerializationPolicy(HttpServletRequest request, String moduleBaseURL,
                                                                     String strongName) {
        SerializationPolicy policy = super.doGetSerializationPolicy(request, moduleBaseURL, strongName);
        if (policy == null) {
            // NEVER use or cache a legacy serializer
            return new SerializationPolicy() {
                @Override public boolean shouldDeserializeFields(Class<?> clazz) {
                    return true;
                }

                @Override public boolean shouldSerializeFields(Class<?> clazz) {
                    return true;
                }

                @Override public void validateDeserialize(Class<?> clazz) throws SerializationException {
                }

                @Override public void validateSerialize(Class<?> clazz) throws SerializationException {
                }
            };
//            throw new UnsupportedOperationException("Bad id, javascript is probably not uptodate - flush your browser cache");
        }
        return policy;
    }

    /**
     * Injects the target GWT service to be called.
     * 
     * @param remoteServiceName
     *            the Spring bean ID for the target GWT service to be called
     */
    public void setRemoteServiceName(String remoteServiceName) {
        this.remoteServiceName = remoteServiceName;
    }

    private void setServiceData(RemoteService remoteService, boolean cleanUp) {
        if (remoteService instanceof RequestResponseAware) {
            RequestResponseAware service = (RequestResponseAware) remoteService;
            service.setRequest(cleanUp ? null : getThreadLocalRequest());
            service.setResponse(cleanUp ? null : getThreadLocalResponse());
        }
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext; 
    }

    public void log(String message, Throwable t) {
        logger.error(message, t);
    }

    public void log(String msg) {
        logger.info(msg);
    }
}
