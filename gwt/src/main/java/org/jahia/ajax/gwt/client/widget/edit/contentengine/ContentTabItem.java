package org.jahia.ajax.gwt.client.widget.edit.contentengine;

import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import org.jahia.ajax.gwt.client.data.definition.GWTJahiaItemDefinition;
import org.jahia.ajax.gwt.client.messages.Messages;
import org.jahia.ajax.gwt.client.util.icons.ContentModelIconProvider;

/**
 * Created by IntelliJ IDEA.
 * User: toto
 * Date: Jan 6, 2010
 * Time: 8:10:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContentTabItem extends PropertiesTabItem {
    private boolean isNodeNameFieldDisplayed = false;

    public ContentTabItem(AbstractContentEngine engine) {
        super(Messages.get("ece_content", "Content"), engine, GWTJahiaItemDefinition.CONTENT);
        setIcon(ContentModelIconProvider.CONTENT_ICONS.engineTabContent());
        setMultiLang(true);
    }

    @Override
    public void postCreate() {
        if (!propertiesEditor.getFieldsMap().containsKey("jcr:title")) {
            VerticalPanel p = new VerticalPanel();
            FormPanel formPanel = getNamePanel();
            p.add(formPanel);
            isNodeNameFieldDisplayed = true;
            p.add(propertiesEditor);
            add(p);
            return;
        }
        super.postCreate();
    }

    /**
     * Get Form panel that contains the name of the nodes
     * @return
     */
    private FormPanel getNamePanel() {
        FormPanel formPanel = new FormPanel();
        formPanel.setFieldWidth(550);
        formPanel.setLabelWidth(180);
        formPanel.setFrame(false);
        formPanel.setBorders(false);
        formPanel.setBodyBorder(false);
        formPanel.setHeaderVisible(false);
        TextField<String> name = new TextField<String>();
        name.setFieldLabel("Name");
        name.setName("name");
        if (engine.isExistingNode()) {
            name.setValue(engine.getNode().getName());
            setData("NodeName", engine.getNode().getName());
        } else {
            name.setValue("Automatically Created (you can type your name here if you want)");
        }
        formPanel.add(name);
        return formPanel;
    }

    public boolean isNodeNameFieldDisplayed() {
        return isNodeNameFieldDisplayed;
    }
}
