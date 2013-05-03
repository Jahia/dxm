/**
 * This file is part of Jahia, next-generation open source CMS:
 * Jahia's next-generation, open source CMS stems from a widely acknowledged vision
 * of enterprise application convergence - web, search, document, social and portal -
 * unified by the simplicity of web content management.
 *
 * For more information, please visit http://www.jahia.com.
 *
 * Copyright (C) 2002-2013 Jahia Solutions Group SA. All rights reserved.
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
 * Commercial and Supported Versions of the program (dual licensing):
 * alternatively, commercial and supported versions of the program may be used
 * in accordance with the terms and conditions contained in a separate
 * written agreement between you and Jahia Solutions Group SA.
 *
 * If you are unsure which license is appropriate for your use,
 * please contact the sales department at sales@jahia.com.
 */

package org.jahia.ajax.gwt.client.widget.edit.mainarea;

import com.extjs.gxt.ui.client.dnd.DND;
import com.extjs.gxt.ui.client.event.DNDEvent;
import com.google.gwt.user.client.DOM;
import org.jahia.ajax.gwt.client.data.node.GWTJahiaNode;
import org.jahia.ajax.gwt.client.util.security.PermissionsUtils;
import org.jahia.ajax.gwt.client.widget.edit.EditModeDNDListener;
import org.jahia.ajax.gwt.client.widget.edit.EditModeDragSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * GWT Drag source for simple modules
 *
 * @see SimpleModule
 *
 * User: toto
 * Date: Aug 21, 2009
 * Time: 4:16:42 PM
 */
public class SimpleModuleDragSource extends EditModeDragSource {

    private Module module;

    public SimpleModuleDragSource(Module target) {
        super(target.getContainer());
        this.module = target;
    }

    public Module getModule() {
        return module;
    }

    protected void onDragEnd(DNDEvent e) {
        if (e.getStatus().getData("operationCalled") == null) {
            DOM.setStyleAttribute(module.getHtml().getElement(), "display", "block");
        }
        super.onDragEnd(e);
    }

    @Override
    protected void onDragCancelled(DNDEvent dndEvent) {
        DOM.setStyleAttribute(module.getHtml().getElement(), "display", "block");
        super.onDragCancelled(dndEvent);
    }

    @Override
    protected void onDragStart(DNDEvent e) {
        super.onDragStart(e);

        List<GWTJahiaNode> l = new ArrayList<GWTJahiaNode>();
        MainModule mainModule = getModule().getMainModule();
        Set<Module> moduleSet = new HashSet<Module>();
        if (e.isControlKey() || mainModule.getSelections().containsKey(module)) {
            moduleSet.addAll(mainModule.getSelections().keySet());
        }
        moduleSet.add(module);
        for (Module m : moduleSet) {
            if (m.isDraggable()) {
                if (PermissionsUtils.isPermitted("jcr:removeNode", m.getNode()) && !m.getNode().isLocked()) {
                    e.setCancelled(false);
                    e.setData(this);
                    e.setOperation(DND.Operation.COPY);
                    if (getStatusText() == null) {
                        e.getStatus().update(DOM.clone(m.getHtml().getElement(), true));

                        e.getStatus().setData("element", m.getHtml().getElement());
                        DOM.setStyleAttribute(m.getHtml().getElement(), "display", "none");

                    }
                } else {
                    e.setCancelled(true);
                }
                Selection selection = mainModule.getSelections().get(m);
                if (selection != null) {
                    selection.hide();
                }
                l.add(m.getNode());
            }
        }
        if (!l.isEmpty()) {
            e.getStatus().setData(EditModeDNDListener.SOURCE_TYPE, EditModeDNDListener.SIMPLEMODULE_TYPE);
            e.getStatus().setData(EditModeDNDListener.SOURCE_MODULES, moduleSet);
            e.getStatus().setData(EditModeDNDListener.SOURCE_NODES, l);
        }
    }

}
