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
 * in Jahia's FLOSS exception. You should have recieved a copy of the text
 * describing the FLOSS exception, and it is also available here:
 * http://www.jahia.com/license"
 * 
 * Commercial and Supported Versions of the program
 * Alternatively, commercial and supported versions of the program may be used
 * in accordance with the terms contained in a separate written agreement
 * between you and Jahia Limited. If you are unsure which license is appropriate
 * for your use, please contact the sales department at sales@jahia.com.
 */

package org.jahia.ajax.gwt.commons.server.rpc;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import org.jahia.ajax.gwt.client.data.GWTJahiaGroup;
import org.jahia.ajax.gwt.client.data.GWTJahiaUser;
import org.jahia.ajax.gwt.client.service.UserManagerService;
import org.jahia.params.ParamBean;
import org.jahia.registries.ServicesRegistry;
import org.jahia.services.sites.JahiaSite;
import org.jahia.services.sites.JahiaSitesService;
import org.jahia.services.usermanager.*;
import org.jahia.data.viewhelper.principal.PrincipalViewHelper;
import org.jahia.resourcebundle.JahiaResourceBundle;

import java.security.Principal;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: hollis
 * Date: 25 juil. 2008
 * Time: 12:53:39
 * To change this template use File | Settings | File Templates.
 */
public class UserManagerServiceImpl extends SessionManagerServiceImpl implements UserManagerService {

    private JahiaUserManagerService userMgrServ = ServicesRegistry.getInstance().getJahiaUserManagerService();
    private JahiaGroupManagerService groupMgrServ = ServicesRegistry.getInstance().getJahiaGroupManagerService();
    private JahiaSitesService sitesService = ServicesRegistry.getInstance().getJahiaSitesService();
    public PagingLoadResult<GWTJahiaUser> searchUsers(String match, int offset, int limit, List<Integer> siteIds) {
        try {
            Properties criterias = new Properties();
            criterias.setProperty(JahiaUserManagerDBProvider.USERNAME_PROPERTY_NAME,match);

            ParamBean jParams = retrieveParamBean();
            List<Integer> sites = siteIds;
            Set<Principal> users;
            List<GWTJahiaUser> result = new ArrayList<GWTJahiaUser>();
            if (sites== null || sites.size() == 0) {
                sites = new ArrayList<Integer>();
                sites.add(jParams.getSiteID());
            }
            for (Integer siteId : sites) {
                JahiaSite jahiaSite = sitesService.getSite(siteId);
                users = userMgrServ.searchUsers(siteId, criterias);
                if (users != null) {
                    Iterator iterator = users.iterator();
                    JahiaUser user;
                    GWTJahiaUser data;
                    while (iterator.hasNext()) {
                        user = (JahiaUser) iterator.next();
                        data = new GWTJahiaUser(user.getUsername(), user.getUserKey());
                        Properties p = user.getProperties();
                        for (Object o : p.keySet()) {
                            data.set((String) o, p.get(o));
                        }
                        data.setSiteName(jahiaSite.getServerName());
                        data.setSiteId(siteId);
                        data.setProvider(user.getProviderName());
                        result.add(data);
                    }
                }
            }
            Collections.sort(result, new Comparator<GWTJahiaUser>() {
                public int compare(GWTJahiaUser o1, GWTJahiaUser o2) {
                    return o1.getUsername().compareTo(o2.getUsername());
                }
            });
            int size = result.size();
            result = new ArrayList<GWTJahiaUser>(result.subList(offset, Math.min(size,offset + limit)));
            return new BasePagingLoadResult<GWTJahiaUser>(result, offset, size);
        } catch (Throwable e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public PagingLoadResult<GWTJahiaGroup> searchGroups(String match, int offset, int limit) {
        try {
            Properties criterias = new Properties();
            criterias.setProperty(JahiaGroupManagerDBProvider.GROUPNAME_PROPERTY_NAME,match);

            ParamBean jParams = retrieveParamBean();
            Set groups = groupMgrServ.searchGroups(jParams.getSiteID(),criterias);
            List<GWTJahiaGroup> result = new ArrayList<GWTJahiaGroup>();
            if (groups != null){
                Iterator iterator = groups.iterator();
                JahiaGroup group ;
                GWTJahiaGroup data ;
                while (iterator.hasNext()){
                    group = (JahiaGroup)iterator.next();
                    data = new GWTJahiaGroup(group.getGroupname(), group.getGroupKey());
                    Collection<Principal> members = group.getMembers();
                    Collection<String> memberStrings = new ArrayList<String>();
                    String displayMembers = "";
                    for (Principal member : members) {
                        memberStrings.add(member.getName());
                        displayMembers += ", " + member.getName();
                    }
                    if (displayMembers.length()>0) displayMembers = displayMembers.substring(2);
                    data.setMembers(memberStrings);
                    if (group instanceof UsersGroup) {
                        displayMembers = JahiaResourceBundle.getEngineResource("org.jahia.engines.groups.users.label", retrieveParamBean(), retrieveParamBean().getLocale());
                    }
                    if (group instanceof GuestGroup) {
                        displayMembers = JahiaResourceBundle.getEngineResource("org.jahia.engines.groups.guest.label", retrieveParamBean(), retrieveParamBean().getLocale());
                    }
                    data.setDisplayMembers(displayMembers);
                    result.add(data);
                }
            }
            Collections.sort(result, new Comparator<GWTJahiaGroup>() {
                public int compare(GWTJahiaGroup o1, GWTJahiaGroup o2) {
                    return o1.getGroupname().compareTo(o2.getGroupname());
                }
            });
            int size = result.size();
            result = new ArrayList(result.subList(offset, Math.min(size,offset + limit)));
            BasePagingLoadResult<GWTJahiaGroup> pagingLoadResult = new BasePagingLoadResult<GWTJahiaGroup>(result, offset, size);
            return pagingLoadResult;
        } catch (Throwable e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }


    public String[] getFormattedPrincipal(String userkey, char type, String[] textpattern) {
        PrincipalViewHelper pvh = new PrincipalViewHelper(textpattern);
        Principal p;

        if (type =='u') {
            p = ServicesRegistry.getInstance().getJahiaUserManagerService().lookupUserByKey(userkey);
        } else {
            p = ServicesRegistry.getInstance().getJahiaGroupManagerService().lookupGroup(userkey);
        }

        String principalTextOption = pvh.getPrincipalTextOption(p);
        principalTextOption = principalTextOption.replace("&nbsp;"," ");
        return new String[] {principalTextOption,pvh.getPrincipalValueOption(p)};
    }
}
