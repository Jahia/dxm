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
 package org.jahia.content;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.jahia.exceptions.JahiaException;

/**
 * Title:        Jahia
 * Description:  The purpose of this class is to provide a cross reference
 * system between fields and pages, in order to know which pages reference
 * a field. This is necessary since currently this information is defined
 * only by template developpers and is useful for the HTML cache system when a
 * field modification is done. This class allows lookups such as : "which
 * pages access this field via the absolute field reference system?"
 * Copyright:    Copyright (c) 2002
 * Company:      Jahia Ltd
 * @author Serge Huber
 * @version 1.0
 */

public class ContentFieldXRefManager {

    private static org.apache.log4j.Logger logger =
        org.apache.log4j.Logger.getLogger(ContentFieldXRefManager.class);

    private static ContentFieldXRefManager theObject = null;

    protected ContentFieldXRefManager() {
        logger.debug("Initializing...");
    }

    /**
     * Returns a singleton instance of this service
     * Note : this is a synchronized access method so it might affect performance
     * if a lot of threads must access it simultaneously.
     * @returns the singleton instance.
     */
    public static synchronized ContentFieldXRefManager getInstance()
    {
        if (theObject == null) {
            theObject = new ContentFieldXRefManager();
        }
        return theObject;
    } // end getInstance

    /**
     * Retrieves a list of pageIDs that contain references to a specific
     * absolute field.
     * @param fieldID the field identifier
     * @returns a Set of Integers containing pageIDs
     * @throws JahiaException is thrown in case there was a problem
     * communicating with the database that stores the link persistently
     */
    public Set getAbsoluteFieldPageIDs(int fieldID)
        throws JahiaException {

        ContentFieldKey fieldKey = new ContentFieldKey(fieldID);

        Set objectRefs = CrossReferenceManager.getInstance().getObjectXRefs(fieldKey);
        Set pageIDs = new TreeSet();
        if (objectRefs == null) {
            return pageIDs;
        }
        Iterator objectRefIter = objectRefs.iterator();
        while (objectRefIter.hasNext()) {
            Object ref = objectRefIter.next();
            if (ref instanceof ObjectKey) {
                ObjectKey refKey = (ObjectKey) ref;
                if (ContentPageKey.PAGE_TYPE.equals(refKey.getType())) {
                    ContentPageKey pageRefKey = (ContentPageKey) refKey;
                    Integer pageID = new Integer(pageRefKey.getPageID());
                    pageIDs.add(pageID);
                } else {
                    logger.debug("Expected page type in cross reference list, ignoring value...  ");
                }
            } else {
                logger.debug("Invalid key object in cross reference list, ignoring... ");
            }
        }
        return pageIDs;
    }

    /**
     * Retrieves a set of field keys that represent all the absolute fields
     * used on a page.
     * @param pageID the identifier of the page
     * @return a Set of FieldKey objects that contain identifier to absolute
     * fields used in the page.
     * @throws JahiaException in case there was a problem retrieving the
     * references from the database
     */
    public Set getAbsoluteFieldsFromPageID(int pageID)
        throws JahiaException {

        ContentPageKey pageKey = new ContentPageKey(pageID);

        Set objectRefs = CrossReferenceManager.getInstance().getReverseObjectXRefs(pageKey);
        Set fieldKeys = new TreeSet();
        if (objectRefs == null) {
            return fieldKeys;
        }
        Iterator objectRefIter = objectRefs.iterator();
        while (objectRefIter.hasNext()) {
            Object source = objectRefIter.next();
            if (source instanceof ObjectKey) {
                ObjectKey sourceKey = (ObjectKey) source;
                if (ContentFieldKey.FIELD_TYPE.equals(sourceKey.getType())) {
                    ContentFieldKey fieldKey = (ContentFieldKey) sourceKey;
                    fieldKeys.add(fieldKey);
                } else {
                    //JahiaConsole.println("FieldXRefManager.getAbsoluteFieldPageIDs",
                    //                     "Expected page type in cross reference list, ignoring value...  ");
                }
            } else {
                logger.debug("Invalid key object in cross reference list, ignoring... ");
            }
        }
        return fieldKeys;

    }

    /**
     * Adds or updates a cross reference between an absolute field and
     * a page
     * @param fieldID the identifier for the field
     * @param referencePageID the page identifier for the page that references
     * the field
     * @throws JahiaException is thrown in case there was a problem
     * communicating with the database that stores the link persistently
     */
    public void setAbsoluteFieldPageID(int fieldID,
                                       int referencePageID)
        throws JahiaException {
        ContentFieldKey fieldKey = new ContentFieldKey(fieldID);
        ContentPageKey pageKey = new ContentPageKey(referencePageID);
        CrossReferenceManager.getInstance().setObjectXRef(fieldKey, pageKey);
    }

    /**
     * Removes all the cross references for a specified absolute field
     * @param fieldID the identifier for the field
     * @throws JahiaException is thrown in case there was a problem
     * communicating with the database that stores the link persistently
     */
    public void removeAbsoluteField(int fieldID)
        throws JahiaException {
        ContentFieldKey fieldKey = new ContentFieldKey(fieldID);
        CrossReferenceManager.getInstance().removeObjectXRefs(fieldKey);
    }

    /**
     * Removes a specific page cross reference for an absolute field
     * @param fieldID the identifier for the field addressed absolutely
     * @param referencePageID the page identifier that is referencing the
     * field absolutely
     * @throws JahiaException is thrown in case there was a problem
     * communicating with the database that stores the link persistently
     */
    public void removeAbsoluteFieldPageID(int fieldID, int referencePageID)
        throws JahiaException {
        ContentFieldKey fieldKey = new ContentFieldKey(fieldID);
        ContentPageKey pageKey = new ContentPageKey(referencePageID);
        CrossReferenceManager.getInstance().removeObjectXRef(fieldKey, pageKey);
    }

}
