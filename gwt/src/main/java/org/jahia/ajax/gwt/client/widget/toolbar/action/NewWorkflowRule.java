package org.jahia.ajax.gwt.client.widget.toolbar.action;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import org.jahia.ajax.gwt.client.data.node.GWTJahiaNode;
import org.jahia.ajax.gwt.client.messages.Messages;
import org.jahia.ajax.gwt.client.widget.LinkerSelectionContext;
import org.jahia.ajax.gwt.client.widget.edit.EditLinker;
import org.jahia.ajax.gwt.client.widget.form.FormQuickRemotePublication;
import org.jahia.ajax.gwt.client.widget.form.FormQuickWorkflowRule;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: May 19, 2010
 * Time: 4:33:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewWorkflowRule  extends BaseActionItem {
        public void onComponentSelection() {
        GWTJahiaNode parent = linker.getMainNode();
        if (parent == null) {
            final List<GWTJahiaNode> selectedItems = linker.getSelectedNodes();
            if (selectedItems != null && selectedItems.size() == 1) {
                parent = selectedItems.get(0);
            }
        }
        if (parent != null && !parent.isFile()) {
            com.extjs.gxt.ui.client.widget.Window w = new com.extjs.gxt.ui.client.widget.Window();
            w.setHeading(Messages.getNotEmptyResource("label.newworkflowrule", "New workflow rule"));
            w.setModal(true);
            w.setResizable(false);
            w.setBodyBorder(false);
            w.setLayout(new FillLayout());
            w.setWidth(350);
            w.add(new FormQuickWorkflowRule() {
                public void onWorkflowRuleCreated() {
                    linker.refresh(EditLinker.REFRESH_ALL);
                }
            });
            w.setScrollMode(Style.Scroll.AUTO);
            w.layout();
            w.show();
        }
    }

    public void handleNewLinkerSelection() {
        LinkerSelectionContext lh = linker.getSelectionContext();
        setEnabled(lh.isMainSelection() && lh.isParentWriteable() || lh.isTableSelection() && lh.isSingleFolder() && lh.isWriteable());
    }
}
