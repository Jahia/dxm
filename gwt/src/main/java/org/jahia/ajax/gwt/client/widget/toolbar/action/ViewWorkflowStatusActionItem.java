package org.jahia.ajax.gwt.client.widget.toolbar.action;

import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.ScrollListener;
import com.extjs.gxt.ui.client.util.Point;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.RootPanel;
import org.jahia.ajax.gwt.client.data.workflow.GWTJahiaWorkflowInfo;
import org.jahia.ajax.gwt.client.widget.Linker;
import org.jahia.ajax.gwt.client.widget.edit.EditLinker;
import org.jahia.ajax.gwt.client.widget.edit.mainarea.Module;
import org.jahia.ajax.gwt.client.widget.edit.mainarea.ModuleHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: toto
 * Date: Sep 25, 2009
 * Time: 6:59:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewWorkflowStatusActionItem extends ViewStatusActionItem {

    @Override
    public void viewStatus(final Linker linker) {
        if (!containers.isEmpty()) {
            for (LayoutContainer ctn : containers.keySet()) {
                RootPanel.get().remove(ctn);
            }
            containers.clear();
            return;
        }
        List<Module> modules = ModuleHelper.getModules();
        List<Module> list = new ArrayList<Module>();
        for (Module m : modules) {
            if (!m.getPath().endsWith("*")) {
                list.add(m);
            }
        }

        final LayoutContainer mainPanel = modules.iterator().next().getContainer();
        Point p = mainPanel.el().getXY();
        Size s = mainPanel.el().getSize();
        final int left = p.x;
        final int top = p.y;
        final int right = left + s.width;
        final int bottom = top + s.height;

        Listener<ComponentEvent> removeListener = new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent ce) {
                for (LayoutContainer ctn : containers.keySet()) {
                    RootPanel.get().remove(ctn);
                }
                containers.clear();
                if (button != null) {
                    button.toggle(false);
                }
            }
        };

        String lastUnpublished = null;
        boolean allPublished = true;
        for (Module module : list) {
            if (module.getNode() != null) {
                GWTJahiaWorkflowInfo info = module.getNode().getWorkflowInfo();
                if (info.getAvailableActions().size()>0) {
                    String current = info.getAvailableActions().get(0).getName();
                    allPublished = false;
                    addInfoLayer(module, "Workflow(s) started : "+current, "red", "red", left, top, right, bottom, removeListener, true,
                            "0.7");
                }
                else if (info.getDuedate()!=null) {
                    allPublished = false;
                    addInfoLayer(module, "Workflow(s) is waiting for timer.<br/>Will be triggered at : "+ DateTimeFormat.getMediumDateTimeFormat().format(info.getDuedate()), 
                                 "red", "red", left, top, right, bottom, removeListener, true, "0.7");
                }
            }
        }

        if (allPublished) {
            addInfoLayer(modules.iterator().next(), "No actual worflow(s) started", "black", "white", left,top,right,bottom,removeListener, false,
                    "0.7");
        }

        ((EditLinker) linker).getMainModule().getScrollContainer().addScrollListener(new ScrollListener() {
            @Override
            public void widgetScrolled(ComponentEvent ce) {
                for (LayoutContainer infoLayer : containers.keySet()) {
                    El el = containers.get(infoLayer);
                    if (el != mainPanel.el()) {
                        position(infoLayer, el, top, bottom, left, right);
                    }
                }
                super.widgetScrolled(ce);
            }
        });
    }

}