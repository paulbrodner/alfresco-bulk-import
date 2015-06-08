/*
 * Copyright (C) 2007-2015 Peter Monks.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This file is part of an unsupported extension to Alfresco.
 * 
 */


package org.alfresco.extension.bulkimport.source.sample;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.alfresco.extension.bulkimport.BulkImportCallback;

import org.alfresco.extension.bulkimport.source.BulkImportItem;
import org.alfresco.extension.bulkimport.source.BulkImportSource;
import org.alfresco.extension.bulkimport.source.BulkImportSourceStatus;


/**
 * This class implements a sample custom bulk import source.
 *
 * @author Peter Monks (pmonks@gmail.com)
 *
 */
public final class SampleSource
    implements BulkImportSource
{
    private final static Log log = LogFactory.getLog(SampleSource.class);
    
    public  final static String IMPORT_SOURCE_NAME = "Sample";
    
    private final static String IMPORT_SOURCE_DESCRIPTION   = "This import source is a sample that synthesises a small amount of hardcoded content. It primarily exists as a working example source code for a custom import source.";
    private final static String IMPORT_SOURCE_CONFIG_UI_URI = "/bulk/import/samplesource/config";
    
    final static String SOURCE_COUNTER_NAME_FOLDERS_SYNTHESISED = "Folders synthesised";
    final static String SOURCE_COUNTER_NAME_FILES_SYNTHESISED   = "Files synthesised";
    
    private final static String[] SOURCE_COUNTERS = { SOURCE_COUNTER_NAME_FOLDERS_SYNTHESISED, SOURCE_COUNTER_NAME_FILES_SYNTHESISED };

    /**
     * @see org.alfresco.extension.bulkimport.source.BulkImportSource#getName()
     */
    @Override
    public String getName()
    {
        return(IMPORT_SOURCE_NAME);
    }


    /**
     * @see org.alfresco.extension.bulkimport.source.BulkImportSource#getDescription()
     */
    @Override
    public String getDescription()
    {
        return(IMPORT_SOURCE_DESCRIPTION);
    }


    /**
     * @see org.alfresco.extension.bulkimport.source.BulkImportSource#getParameters()
     */
    @Override
    public Map<String, String> getParameters()
    {
        return(null);
    }


    /**
     * @see org.alfresco.extension.bulkimport.source.BulkImportSource#getConfigWebScriptURI()
     */
    @Override
    public String getConfigWebScriptURI()
    {
        return(IMPORT_SOURCE_CONFIG_UI_URI);
    }


    /**
     * @see org.alfresco.extension.bulkimport.source.BulkImportSource#init(java.util.Map)
     */
    @Override
    public void init(Map<String, List<String>> parameters)
    {
        // The sample source doesn't have any parameters, so there's nothing to do here.
    }


    /**
     * @see org.alfresco.extension.bulkimport.source.BulkImportSource#inPlaceImportPossible()
     */
    @Override
    public boolean inPlaceImportPossible()
    {
        // The Sample Source doesn't support in-place content
        return(false);
    }


    /**
     * @see org.alfresco.extension.bulkimport.source.BulkImportSource#scanFolders(org.alfresco.extension.bulkimport.source.BulkImportSourceStatus, org.alfresco.extension.bulkimport.BulkImportCallback)
     */
    @Override
    public void scanFolders(final BulkImportSourceStatus status, final BulkImportCallback callback)
        throws InterruptedException
    {
        status.preregisterSourceCounters(SOURCE_COUNTERS);
        
        callback.submit(synthesiseFolder(status, null,      "folder1"));
        callback.submit(synthesiseFolder(status, null,      "folder2"));
        callback.submit(synthesiseFolder(status, null,      "folder3"));
        callback.submit(synthesiseFolder(status, "folder1", "folder1.1"));
    }


    /**
     * @see org.alfresco.extension.bulkimport.source.BulkImportSource#scanFiles(org.alfresco.extension.bulkimport.source.BulkImportSourceStatus, org.alfresco.extension.bulkimport.BulkImportCallback)
     */
    @Override
    public void scanFiles(final BulkImportSourceStatus status, final BulkImportCallback callback)
        throws InterruptedException
    {
        callback.submit(synthesiseFile(status, null,      "file1.txt", 1));
        callback.submit(synthesiseFile(status, "folder1", "file1.1.txt", 1));
        callback.submit(synthesiseFile(status, "folder1", "file1.2.txt", 10));
    }
    
    
    private final BulkImportItem synthesiseFolder(final BulkImportSourceStatus status, final String parentPath, final String name)
    {
        final BulkImportItem result = new SampleSourceImportItem(parentPath, name, true, 1);
        status.incrementSourceCounter(SOURCE_COUNTER_NAME_FOLDERS_SYNTHESISED);
        
        return(result);
    }
    
    
    private final BulkImportItem synthesiseFile(final BulkImportSourceStatus status, final String parentPath, final String name, final int numVersions)
    {
        final BulkImportItem result = new SampleSourceImportItem(parentPath, name, false, numVersions);
        status.incrementSourceCounter(SOURCE_COUNTER_NAME_FILES_SYNTHESISED);
        
        return(result);
    }
    
    
}