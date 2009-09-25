package org.jahia.ajax.gwt.client.widget.toolbar.action;

import org.jahia.ajax.gwt.client.widget.toolbar.handler.ManagerSelectionHandler;
import org.jahia.ajax.gwt.client.util.content.actions.ContentActions;
import org.jahia.ajax.gwt.client.core.JahiaGWTParameters;

/**
 * Created by IntelliJ IDEA.
* User: toto
* Date: Sep 25, 2009
* Time: 6:57:58 PM
* To change this template use File | Settings | File Templates.
*/
public class MountActionItem extends BaseActionItem  implements ManagerSelectionHandler {
    public void onSelection() {
        ContentActions.mountFolder(linker);
    }

    public void enableOnConditions(boolean treeSelection, boolean tableSelection, boolean writable, boolean deleteable, boolean parentWritable, boolean singleFile, boolean singleFolder, boolean pasteAllowed, boolean lockable, boolean locked, boolean isZip, boolean isImage, boolean isMount) {
        setEnabled("root".equals(JahiaGWTParameters.getCurrentUser())); // TODO dirty code (to refactor using server side configuration and roles)
    }
}
