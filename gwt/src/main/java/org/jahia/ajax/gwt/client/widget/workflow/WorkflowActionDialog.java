/**
 * This file is part of Jahia: An integrated WCM, DMS and Portal Solution
 * Copyright (C) 2002-2011 Jahia Solutions Group SA. All rights reserved.
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

package org.jahia.ajax.gwt.client.widget.workflow;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.layout.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import org.jahia.ajax.gwt.client.core.BaseAsyncCallback;
import org.jahia.ajax.gwt.client.data.GWTJahiaCreateEngineInitBean;
import org.jahia.ajax.gwt.client.data.definition.*;
import org.jahia.ajax.gwt.client.data.workflow.*;
import org.jahia.ajax.gwt.client.messages.Messages;
import org.jahia.ajax.gwt.client.service.content.JahiaContentManagementService;
import org.jahia.ajax.gwt.client.service.content.JahiaContentManagementServiceAsync;
import org.jahia.ajax.gwt.client.service.definition.JahiaContentDefinitionService;
import org.jahia.ajax.gwt.client.service.definition.JahiaContentDefinitionServiceAsync;
import org.jahia.ajax.gwt.client.util.icons.ToolbarIconProvider;
import org.jahia.ajax.gwt.client.widget.Linker;
import org.jahia.ajax.gwt.client.widget.contentengine.EngineContainer;
import org.jahia.ajax.gwt.client.widget.definition.PropertiesEditor;
import org.jahia.ajax.gwt.client.widget.toolbar.action.WorkInProgressActionItem;

import java.util.*;

/**
 * Represent the workflow action dialog widget.
 *
 * @author : rincevent
 * @since : JAHIA 6.1
 *        Created : 28 avr. 2010
 */
public class WorkflowActionDialog extends LayoutContainer {
// ------------------------------ FIELDS ------------------------------

    private JahiaContentManagementServiceAsync contentManagement;
    private JahiaContentDefinitionServiceAsync contentDefinition;
    private WorkflowDashboardEngine workflowDashboard;
    private TabPanel tabPanel;
    private TabItem actionTab;
    private TabItem commentsTab;
    private EngineContainer container;
    private String title;
    private List<String> comments;

    private String nodePath;
    private GWTJahiaWorkflow workflow;

    private CustomWorkflow custom;

    private Linker linker;
    private PropertiesEditor propertiesEditor;
    private ButtonBar bar;

// --------------------------- CONSTRUCTORS ---------------------------


    public WorkflowActionDialog(final String nodePath, final String title, final GWTJahiaWorkflowDefinition workflow,
                                final Linker linker, CustomWorkflow custom, EngineContainer container) {
        this(linker, custom, container);
        this.nodePath = nodePath;
        this.title = title;
        initStartWorkflowDialog(workflow);
    }

    public WorkflowActionDialog(final GWTJahiaWorkflow workflow, final GWTJahiaWorkflowTask task, final Linker linker,
                                CustomWorkflow custom, EngineContainer container) {
        this(linker, custom, container);
        this.workflow = workflow;
        GWTJahiaNodeProperty prop = workflow.getVariables().get("jcr:title");
        if (prop != null && prop.getValues().size() == 1) {
            title = prop.getValues().get(0).getString();
        }
        initExecuteActionDialog(task);
    }

    private WorkflowActionDialog(Linker linker, CustomWorkflow custom, EngineContainer container) {
        super();
        contentManagement = JahiaContentManagementService.App.getInstance();
        contentDefinition = JahiaContentDefinitionService.App.getInstance();
        this.linker = linker;
        this.custom = custom;
        tabPanel = new TabPanel();
        setLayout(new FitLayout());
        add(tabPanel);

        bar = new ButtonBar();
        bar.setAlignment(Style.HorizontalAlignment.CENTER);

        actionTab = new TabItem();

        comments = new ArrayList<String>();

        this.container = container;
        String header = title;
        container.setEngine(this, header, bar, this.linker);
    }

    public EngineContainer getContainer() {
        return container;
    }

    public void setCustom(CustomWorkflow custom) {
        this.custom = custom;
    }

    public Linker getLinker() {
        return linker;
    }

    public PropertiesEditor getPropertiesEditor() {
        return propertiesEditor;
    }

    public void initStartWorkflowDialog(final GWTJahiaWorkflowDefinition workflowDefinition) {
        initTabs(workflowDefinition.getFormResourceName());
        Button button = null;
        Button bypassButton = null;
        if (custom != null) {
            button = custom.getStartWorkflowButton(workflowDefinition, this);
            bypassButton = custom.getBypassWorkflowButton(workflowDefinition, this);
        }
        if (button == null) {
            button = generateStartWorkflowButton(workflowDefinition);
        }

        bar.add(button);
        if (bypassButton != null) {
            bar.add(bypassButton);
        }

        Button cancel = new Button(Messages.get("label.cancel"), new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent event) {
                container.closeEngine();
            }
        });
        bar.add(cancel);
    }

    public void initExecuteActionDialog(final GWTJahiaWorkflowTask task) {
        initTabs(task.getFormResourceName());
        List<Button> buttons = generateActionButtons(task);

        for (Button button : buttons) {
            bar.add(button);
        }

        Button cancel = new Button(Messages.get("label.cancel"), new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent event) {
                container.closeEngine();
            }
        });
        bar.add(cancel);
    }

    private void initTabs(final String formResourceName) {
        TabItem action = initActionTab(formResourceName);
        tabPanel.add(action);
        TabItem comments = initCommentTab();
        tabPanel.add(comments);
        if (custom != null) {
            for (TabItem item : custom.getAdditionalTabs()) {
                tabPanel.add(item);
            }
        }
    }


    private TabItem initActionTab(String formResourceName) {
        actionTab = new TabItem(Messages.get("label.action", "Action"));
        actionTab.setLayout(new FitLayout());
        if (formResourceName != null && !"".equals(formResourceName)) {
            contentDefinition.getWFFormForNodeAndNodeType(formResourceName, new BaseAsyncCallback<GWTJahiaNodeType>() {
                public void onSuccess(final GWTJahiaNodeType result) {
                    final Map<String, GWTJahiaNodeProperty> variables;
                    if (workflow != null) {
                        variables = workflow.getVariables();
                    } else {
                        variables = new HashMap<String, GWTJahiaNodeProperty>();
                        if (title != null) {
                            variables.put("jcr:title", new GWTJahiaNodeProperty("jcr:title",
                                    new GWTJahiaNodePropertyValue(title, GWTJahiaNodePropertyType.STRING)));
                        }
                    }
                    JahiaContentManagementService.App.getInstance().initializeCreateEngine(result.getName(),
                            linker.getSelectionContext().getMultipleSelection().size()>1?linker.getSelectionContext().getMultipleSelection().get(0).getPath():linker.getSelectionContext().getSingleSelection().getPath(),
                            new BaseAsyncCallback<GWTJahiaCreateEngineInitBean>() {
                                public void onSuccess(GWTJahiaCreateEngineInitBean result2) {
                                    propertiesEditor = new PropertiesEditor(Arrays.asList(result), variables,
                                            Arrays.asList(GWTJahiaItemDefinition.CONTENT));
                                    propertiesEditor.setInitializersValues(result2.getInitializersValues());
                                    propertiesEditor.setViewInheritedItems(true);
                                    propertiesEditor.renderNewFormPanel();
                                    propertiesEditor.setFrame(true);
                                    propertiesEditor.setBorders(false);
                                    propertiesEditor.setBodyBorder(false);
                                    actionTab.add(propertiesEditor);
                                    actionTab.layout();
                                }
                            });
                }
            });
        }
        return actionTab;
    }

    private TabItem initCommentTab() {
        commentsTab = new TabItem(Messages.get("label.comments", "Comments"));

        commentsTab.setLayout(new BorderLayout());

        final LayoutContainer commentsContainer = new LayoutContainer(new RowLayout(Style.Orientation.VERTICAL));
        commentsTab.add(commentsContainer, new BorderLayoutData(Style.LayoutRegion.CENTER));

        commentsContainer.setScrollMode(Style.Scroll.AUTOY);
        commentsContainer.setBorders(false);

        if (workflow != null) {
            contentManagement.getWorkflowComments(workflow, new BaseAsyncCallback<List<GWTJahiaWorkflowComment>>() {
                public void onSuccess(List<GWTJahiaWorkflowComment> result) {
                    displayComments(result, commentsContainer);
                }
            });
        }

        // Display add a comment
        FormPanel formPanel = new FormPanel();
        formPanel.setHeaderVisible(false);
        formPanel.setBorders(false);
        formPanel.setBodyBorder(false);
        formPanel.setLayout(new FormLayout(FormPanel.LabelAlign.LEFT));
        final TextArea textArea = new TextArea();
        textArea.setFieldLabel(Messages.get("label.comment", "Comment"));
        textArea.setPreventScrollbars(false);
        textArea.setHeight(50);
        textArea.setWidth(750);
        textArea.setAllowBlank(false);
        FormData data = new FormData("-20");
        formPanel.add(textArea, data);
        Button button = new Button(Messages.get("label.addComment", "Add comment"));
        button.addSelectionListener(new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent buttonEvent) {
                if (workflow != null) {
                    contentManagement.addCommentToWorkflow(workflow, textArea.getValue(),
                            new BaseAsyncCallback<List<GWTJahiaWorkflowComment>>() {
                                public void onSuccess(List<GWTJahiaWorkflowComment> result) {
                                    commentsContainer.removeAll();
                                    displayComments(result, commentsContainer);
                                    Info.display(Messages.get("label.commentAdded", "Comment Added"), Messages.get(
                                            "label.commentAdded", "Comment Added"));
                                }

                                public void onApplicationFailure(Throwable caught) {
                                    Info.display("Adding comment failed", "Adding comment failed");
                                }
                            });
                } else {
                    comments.add(textArea.getValue());
                    commentsContainer.removeAll();
                    displayStringComments(comments, commentsContainer);
                }
            }
        });
        formPanel.setButtonAlign(Style.HorizontalAlignment.CENTER);
        formPanel.add(button, data);
        FormButtonBinding buttonBinding = new FormButtonBinding(formPanel);
        buttonBinding.addButton(button);
        commentsTab.add(formPanel, new BorderLayoutData(Style.LayoutRegion.SOUTH, 100));

        return commentsTab;
    }

    private void displayStringComments(List<String> comments, final LayoutContainer commentsPanel) {
        int i = 0;
        for (String comment : comments) {
            Text text = new Text(comment);
            text.setWidth(450);
            HorizontalPanel commentPanel = new HorizontalPanel();
            commentPanel.setBorders(false);
            commentPanel.setWidth("100%");
            TableData data = new TableData(Style.HorizontalAlignment.LEFT, Style.VerticalAlignment.MIDDLE);
            data.setPadding(5);
            commentPanel.add(text, data);
            commentPanel.setScrollMode(Style.Scroll.NONE);
            commentPanel.setStyleAttribute("background-color", i % 2 == 0 ? "#e9eff3" : "white");
            VerticalPanel verticalPanel = new VerticalPanel();
            verticalPanel.setWidth(250);
            verticalPanel.setBorders(false);
            commentPanel.add(verticalPanel);
            commentsPanel.add(commentPanel);
            i++;
        }
        commentsPanel.layout();
    }

    private void displayComments(List<GWTJahiaWorkflowComment> comments, final LayoutContainer commentsPanel) {
        int i = 0;
        for (GWTJahiaWorkflowComment comment : comments) {
            Text text = new Text(comment.getComment());
            text.setWidth(450);
            Text time = new Text(Messages.get("label.at", "at") + " " + DateTimeFormat.getMediumDateTimeFormat().format(
                    comment.getTime()));
            Text user = new Text("by " + comment.getUser());
            HorizontalPanel commentPanel = new HorizontalPanel();
            commentPanel.setBorders(false);
            commentPanel.setWidth("100%");
            TableData data = new TableData(Style.HorizontalAlignment.LEFT, Style.VerticalAlignment.MIDDLE);
            data.setPadding(5);
            commentPanel.add(text, data);
            commentPanel.setScrollMode(Style.Scroll.NONE);
            commentPanel.setStyleAttribute("background-color", i % 2 == 0 ? "#e9eff3" : "white");
            VerticalPanel verticalPanel = new VerticalPanel();
            verticalPanel.add(time, data);
            verticalPanel.add(user);
            verticalPanel.setWidth(250);
            verticalPanel.setBorders(false);
            commentPanel.add(verticalPanel);
            commentsPanel.add(commentPanel);
            i++;
        }
        commentsPanel.layout();
    }

    public void disableButtons() {
        for (Component component : bar.getItems()) {
            if (component instanceof Button) {
                ((Button) component).setEnabled(false);
            }
        }
    }

    private Button generateStartWorkflowButton(final GWTJahiaWorkflowDefinition wf) {
        final Button button = new Button(Messages.get("label.workflow.start", "Start Workflow") + ":" + wf.getName());
        button.addSelectionListener(new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent buttonEvent) {
                disableButtons();
                List<GWTJahiaNodeProperty> nodeProperties = new ArrayList<GWTJahiaNodeProperty>();
                if (propertiesEditor != null) {
                    nodeProperties = propertiesEditor.getProperties();
                }
                container.closeEngine();
                final String status = Messages.get("label.workflow.task", "Executing workflow task");
                Info.display(status, status);
                WorkInProgressActionItem.setStatus(status);
                contentManagement.startWorkflow(nodePath, wf, nodeProperties, comments, new BaseAsyncCallback() {
                    public void onSuccess(Object result) {
                        Info.display(status, Messages.get("message.workflow.task.success", "Workflow task executed successfully"));
                        WorkInProgressActionItem.removeStatus(status);
                    }

                    public void onApplicationFailure(Throwable caught) {
                        Info.display(status, Messages.get("message.workflow.task.failure", "Workflow task failed to execute"));
                        WorkInProgressActionItem.removeStatus(status);
                    }
                });
            }
        });
        return button;
    }

    private List<Button> generateActionButtons(final GWTJahiaWorkflowTask task) {
        List<GWTJahiaWorkflowOutcome> outcomes = task.getOutcomes();
        final List<Button> allButtons = new ArrayList<Button>();
        for (final GWTJahiaWorkflowOutcome outcome : outcomes) {
            Button button = new Button(outcome.getLabel());
            if (outcome.getIcon() != null) {
                button.setIcon(ToolbarIconProvider.getInstance().getIcon(outcome.getIcon()));
            }
            allButtons.add(button);
            button.addSelectionListener(new SelectionListener<ButtonEvent>() {
                @Override
                public void componentSelected(ButtonEvent buttonEvent) {
                    disableButtons();
                    List<GWTJahiaNodeProperty> nodeProperties = new ArrayList<GWTJahiaNodeProperty>();
                    if (propertiesEditor != null) {
                        nodeProperties = propertiesEditor.getProperties();
                    }
                    container.closeEngine();
                    final String status = Messages.get("label.workflow.task", "Executing workflow task");
                    Info.display(status, status);
                    WorkInProgressActionItem.setStatus(status);
                    contentManagement.assignAndCompleteTask(task, outcome, nodeProperties, new BaseAsyncCallback() {
                        public void onSuccess(Object result) {
                            WorkInProgressActionItem.removeStatus(status);
                            Info.display("Workflow executed", "Workflow executed");
                            linker.refresh(Linker.REFRESH_MAIN + Linker.REFRESH_PAGES);
                        }

                        public void onApplicationFailure(Throwable caught) {
                            WorkInProgressActionItem.removeStatus(status);
                            Info.display("Workflow failed", "Workflow failed");
                        }
                    });
                }
            });
        }
        return allButtons;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public void setWorkflowDashboard(WorkflowDashboardEngine workflowDashboard) {
        this.workflowDashboard = workflowDashboard;
    }

    public List<String> getComments() {
        return comments;
    }

    // -------------------------- OTHER METHODS --------------------------

    @Override
    protected void onHide() {
        super.onHide();
        if (workflowDashboard != null) {
            workflowDashboard.hide();
        }
    }
}
