/**
 * This file is part of Jahia: An integrated WCM, DMS and Portal Solution
 * Copyright (C) 2002-2010 Jahia Solutions Group SA. All rights reserved.
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

package org.jahia.services.content.impl.vfs;

import org.jahia.services.content.nodetypes.Name;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileObject;

import javax.jcr.*;
import javax.jcr.retention.RetentionManager;
import javax.jcr.security.AccessControlManager;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import java.security.AccessControlException;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: toto
 * Date: Apr 23, 2008
 * Time: 11:56:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class VFSSessionImpl implements Session {
    private VFSRepositoryImpl repository;
    private VFSWorkspaceImpl workspace;
    private Credentials credentials;

    public VFSSessionImpl(VFSRepositoryImpl repository, Credentials credentials) {
        this.repository = repository;
        this.workspace = new VFSWorkspaceImpl(this);
        this.credentials = credentials;
    }

    public Repository getRepository() {
        return repository;
    }

    public String getUserID() {
        return ((SimpleCredentials)credentials).getUserID();
    }

    public Object getAttribute(String s) {
        return null;
    }

    public String[] getAttributeNames() {
        return new String[0];
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public Session impersonate(Credentials credentials) throws LoginException, RepositoryException {
        return this;
    }

    public Node getRootNode() throws RepositoryException {
        try {
            FileObject rootFileObject = repository.getFile("/");
            if (rootFileObject.exists()) {
                return new VFSNodeImpl(rootFileObject, this);
            } else {
                throw new PathNotFoundException("File " + rootFileObject.getURL() + " does not exist");
            }
        } catch (FileSystemException e) {
            throw new RepositoryException(e);

        }
    }

    public Node getNodeByUUID(String s) throws ItemNotFoundException, RepositoryException {
        throw new UnsupportedRepositoryOperationException();
    }

    public Item getItem(String s) throws PathNotFoundException, RepositoryException {
        try {
            FileObject object = repository.getFile(s);
            if (!object.exists()) {
                /*
                // if it's a property, let's try to find the file by removing the last part of the path.
                int lastSlashPos = s.lastIndexOf("/");
                if (lastSlashPos > -1) {
                    String nodePath = s.substring(0, lastSlashPos);
                    object = repository.getFile(nodePath);
                    if (!object.exists()) {
                        throw new PathNotFoundException(s);
                    }
                    // we found the node, let's build it and return it.
                    String propertyName = s.substring(lastSlashPos+1);
                    Name propertyQName = null;
                    int prefixSeparatorPos = propertyName.indexOf(":");
                    if (prefixSeparatorPos > -1) {
                        propertyQName = new Name(propertyName.substring(prefixSeparatorPos+1), propertyName.substring(0, prefixSeparatorPos), "");
                    } else {
                        propertyQName = new Name(propertyName, "", "");
                    }
                    return new VFSPropertyImpl(propertyQName, new VFSNodeImpl(object, this), this);
                } else {
                    throw new PathNotFoundException(s);
                }
                */
                throw new PathNotFoundException(s);
            }
            return new VFSNodeImpl(object, this);
        } catch (FileSystemException e) {
            throw new RepositoryException(e);
        }
    }

    public boolean itemExists(String s) throws RepositoryException {
        return false;
    }

    public void move(String source, String dest) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, RepositoryException {
        try {
            FileObject object1 = repository.getFile(source);
            if (!object1.exists()) {
                throw new PathNotFoundException(source);
            }
            FileObject object2 = repository.getFile(dest);
            if (object2.exists()) {
                throw new ItemExistsException(dest);
            }
            object1.moveTo(object2);
        } catch (FileSystemException e) {
            throw new RepositoryException(e);
        }

    }

    public void save() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException {
      
    }

    public void refresh(boolean b) throws RepositoryException {
      
    }

    public boolean hasPendingChanges() throws RepositoryException {
        return false;
    }

    public ValueFactory getValueFactory() throws UnsupportedRepositoryOperationException, RepositoryException {
        return new VFSValueFactoryImpl();
    }

    public void checkPermission(String s, String s1) throws AccessControlException, RepositoryException {
      
    }

    public ContentHandler getImportContentHandler(String s, int i) throws PathNotFoundException, ConstraintViolationException, VersionException, LockException, RepositoryException {
        return null;
    }

    public void importXML(String s, InputStream inputStream, int i) throws IOException, PathNotFoundException, ItemExistsException, ConstraintViolationException, VersionException, InvalidSerializedDataException, LockException, RepositoryException {
      
    }

    public void exportSystemView(String s, ContentHandler contentHandler, boolean b, boolean b1) throws PathNotFoundException, SAXException, RepositoryException {
      
    }

    public void exportSystemView(String s, OutputStream outputStream, boolean b, boolean b1) throws IOException, PathNotFoundException, RepositoryException {
      
    }

    public void exportDocumentView(String s, ContentHandler contentHandler, boolean b, boolean b1) throws PathNotFoundException, SAXException, RepositoryException {
      
    }

    public void exportDocumentView(String s, OutputStream outputStream, boolean b, boolean b1) throws IOException, PathNotFoundException, RepositoryException {
      
    }

    public void setNamespacePrefix(String s, String s1) throws NamespaceException, RepositoryException {
      
    }

    public String[] getNamespacePrefixes() throws RepositoryException {
        return new String[0];
    }

    public String getNamespaceURI(String s) throws NamespaceException, RepositoryException {
        return null;
    }

    public String getNamespacePrefix(String s) throws NamespaceException, RepositoryException {
        return null;
    }

    public void logout() {
      
    }

    public boolean isLive() {
        return false;
    }

    public void addLockToken(String s) {
      
    }

    public String[] getLockTokens() {
        return new String[0];
    }

    public void removeLockToken(String s) {
      
    }

    public Node getNodeByIdentifier(String id) throws ItemNotFoundException, RepositoryException {
        return getNodeByUUID(id);
    }

    public Node getNode(String absPath) throws PathNotFoundException, RepositoryException {
        return (Node) getItem(absPath);
    }

    public Property getProperty(String absPath) throws PathNotFoundException, RepositoryException {
        return (Property) getItem(absPath);
    }

    public boolean nodeExists(String absPath) throws RepositoryException {
        return itemExists(absPath);
    }

    public boolean propertyExists(String absPath) throws RepositoryException {
        return itemExists(absPath);
    }

    public void removeItem(String absPath) throws VersionException, LockException, ConstraintViolationException, AccessDeniedException, RepositoryException {
        getItem(absPath).remove();
    }

    public boolean hasPermission(String absPath, String actions) throws RepositoryException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasCapability(String s, Object o, Object[] objects) throws RepositoryException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public AccessControlManager getAccessControlManager() throws UnsupportedRepositoryOperationException, RepositoryException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RetentionManager getRetentionManager() throws UnsupportedRepositoryOperationException, RepositoryException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
