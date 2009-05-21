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
package org.jahia.ajax.gwt.client.widget.category;

import com.extjs.gxt.ui.client.data.ModelComparer;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.Style;
import org.jahia.ajax.gwt.client.data.category.GWTJahiaCategoryNode;
import org.jahia.ajax.gwt.client.widget.tripanel.TopRightComponent;
import org.jahia.ajax.gwt.client.messages.Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ktlili
 * Date: 9 oct. 2008
 * Time: 17:00:44
 */
public class PickedCategoriesGrid extends TopRightComponent {
    private ContentPanel m_component = new ContentPanel();
    private List<GWTJahiaCategoryNode> selectedCategories;
    private ListStore<GWTJahiaCategoryNode> store;
    private Grid<GWTJahiaCategoryNode> grid ;
    private boolean readOnly;
    private boolean multiple;

    public PickedCategoriesGrid(final List<GWTJahiaCategoryNode> selectedCategories, final boolean readonly, final boolean multiple) {
        this.selectedCategories = selectedCategories;
        this.readOnly = readonly;
        this.multiple = multiple;
        createUI();
    }

    public void setContent(Object root) {}

    public void clearTable() {
        store.removeAll();
    }

    public Object getSelection() {
        return store.getModels();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void refresh() {}

    public Component getComponent() {
        return m_component;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void createUI() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        //name
        ColumnConfig column = new ColumnConfig();
        column.setRenderer(new GridCellRenderer<GWTJahiaCategoryNode>() {
            public String render(GWTJahiaCategoryNode categoryNode, String property, ColumnData columnData, int rowIndex, int colIndex, ListStore<GWTJahiaCategoryNode> categoryNodeListStore) {
                return "<input type=\"hidden\" name=\"category_" + categoryNode.getCategoryId() + "\"/> " + categoryNode.getExtendedName();
            }


        });
        column.setId("extendedName");
        column.setHeader(Messages.getResource("categories_selected"));
        configs.add(column);

        // path
//        column = new ColumnConfig();
//        column.setId("path");
//        column.setHeader(Messages.getResource("path"));
//        column.setWidth(300);
//        configs.add(column);

        // list loader
        store = new ListStore<GWTJahiaCategoryNode>();
        store.add(selectedCategories);
        store.setModelComparer(new ModelComparer<GWTJahiaCategoryNode>() {
            public boolean equals(GWTJahiaCategoryNode gwtJahiaCategoryNode, GWTJahiaCategoryNode gwtJahiaCategoryNode1) {
                String key1 = gwtJahiaCategoryNode.getKey();
                String key2 = gwtJahiaCategoryNode1.getKey();
                if (key1 == null && key2 == null) {
                    return true;
                } else if (key1 == null) {
                    return false;
                }
                return key1.equalsIgnoreCase(key2);
            }
        });
        ColumnModel columnModel = new ColumnModel(configs);
        //store.setDefaultSort("name", Style.SortDir.ASC);
        store.sort("extendedName", Style.SortDir.ASC);

        // main component
        m_component.setHeading(Messages.getResource("categories"));
        m_component.setFrame(false);
        m_component.setHeaderVisible(false);
        m_component.setLayout(new FitLayout());
        m_component.setSize(600, 300);

        // grid
        grid = new Grid<GWTJahiaCategoryNode>(store, columnModel);
        grid.setBorders(true);
        grid.getView().setForceFit(true);

//        if (!readonly) {
//            TextToolItem unselectToolItem = new TextToolItem(Messages.getResource("remove"));
//            unselectToolItem.setIconStyle("remove");
//            unselectToolItem.addSelectionListener(new SelectionListener<ComponentEvent>() {
//                public void componentSelected(ComponentEvent event) {
//                    List<GWTJahiaCategoryNode> categoryNodes = selectionModel.getSelectedItems();
//                    if (categoryNodes != null && !categoryNodes.isEmpty()) {
//                        Element ele = DOM.getElementById("removedCategories");
//                        if (ele != null) {
//                            String newValue = ele.getAttribute("value");
//                            for (GWTJahiaCategoryNode categoryNode : categoryNodes) {
//                                store.remove(categoryNode);
//                                // update hidden value
//
//                                newValue = newValue + "," + "category_" + categoryNode.getCategoryId();
//                            }
//
//                            ele.setAttribute("value", newValue);
//                        }
//                    }
//                }
//            });
//
//            ToolBar toolBar = new ToolBar();
//            toolBar.add(unselectToolItem);
//            m_component.setTopComponent(toolBar);
//        }
        m_component.add(grid);
    }

    public void addCategories(List<GWTJahiaCategoryNode> gwtJahiaCategoryNodes) {
        if (readOnly) {
            return;
        }
        if (!multiple && gwtJahiaCategoryNodes.size() > 0) {
            store.removeAll();
            store.add(gwtJahiaCategoryNodes.get(0));
        } else {
            List<GWTJahiaCategoryNode> toAdd = new ArrayList<GWTJahiaCategoryNode>();
            for (GWTJahiaCategoryNode gwtJahiaCategoryNode : gwtJahiaCategoryNodes) {
                boolean add = true ;
                for (GWTJahiaCategoryNode n: store.getModels()) {
                    if (gwtJahiaCategoryNode.getPath().equals(n.getPath())) {
                        add = false ;
                        break;
                    }
                }
                if (add) {
                    toAdd.add(gwtJahiaCategoryNode);
                }
            }
            store.add(toAdd);
            store.sort("extendedName", Style.SortDir.ASC);
        }
    }

    public void removeSelectedCategories() {
        if (readOnly) {
            return;
        }
        List<GWTJahiaCategoryNode> toremove = new ArrayList<GWTJahiaCategoryNode>();
        for (GWTJahiaCategoryNode selectedNode: grid.getSelectionModel().getSelection()) {
            toremove.add(selectedNode);
        }
        for (GWTJahiaCategoryNode n: toremove) {
            store.remove(n);
        }
    }

    public List<GWTJahiaCategoryNode> getCategories() {
        return store.getModels();
    }

}
