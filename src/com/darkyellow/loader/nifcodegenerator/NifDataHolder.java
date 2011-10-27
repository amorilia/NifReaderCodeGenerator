/*
    Copyright 2011 The darkyellow project at darkyellow.org 
    All rights reserved
  
    This file is part of Nif Reader Code Generator

    Nif Reader Code Generator is free software: you can redistribute it 
    and/or modify it under the terms of the GNU General Public License as 
    published by the Free Software Foundation, either version 3 of the 
    License, or (at your option) any later version.

    Nif Reader Code Generator is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Nif Reader Code Generator.  If not, see 
    <http://www.gnu.org/licenses/>.
 */

package com.darkyellow.loader.nifcodegenerator;

import com.darkyellow.loader.util.ByteReader;
import com.darkyellow.loader.niftypes.Basic;
import com.darkyellow.loader.niftypes.Compound;
import com.darkyellow.loader.niftypes.EnumItem;
import com.darkyellow.loader.niftypes.BitFlagItem;
import com.darkyellow.loader.niftypes.Niobject;
import com.darkyellow.loader.niftypes.Version;
import com.darkyellow.loader.util.TypeConverter;

import java.util.HashMap;

/**
 *
 * Holds the data for easy retrieval
 */
public class NifDataHolder
{
    private HashMap<String,Version> versionList = new HashMap();
    private HashMap<String,Basic> basicList = new HashMap();
    private HashMap<String,EnumItem> enumItemList = new HashMap();
    private HashMap<String,BitFlagItem> bitFlagItemList = new HashMap();
    private HashMap<String,Compound> compoundList = new HashMap();
    private HashMap<String,Compound> compoundTemplateList = new HashMap();
    private HashMap<String,Niobject> niobjectList = new HashMap();
    
    private static NifDataHolder instance = null;
    
    private NifDataHolder()
    {
    }
    
    public static synchronized NifDataHolder getInstance ()
    {
        if (instance == null)
        {
            instance = new NifDataHolder ();
        }
        
        return instance;
    }

    /**
     * @return the versionList
     */
    public HashMap <String,Version> getVersionList()
    {
        return versionList;
    }

    /**
     * @return the basicList
     */
    public HashMap<String,Basic> getBasicList()
    {
        return basicList;
    }

    /**
     * @return the enumItemList
     */
    public HashMap<String,EnumItem> getEnumItemList()
    {
        return enumItemList;
    }
    
    /**
     * @return the bitFlagItemList
     */
    public HashMap<String,BitFlagItem> getBitFlagItemList()
    {
        return bitFlagItemList;
    }

    /**
     * @return the compoundList
     */
    public HashMap<String,Compound> getCompoundList()
    {
        return compoundList;
    }
    
    /**
     * @return the compoundList
     */
    public HashMap<String,Compound> getCompoundTemplateList()
    {
        return compoundTemplateList;
    }

    /**
     * @return the niobjectList
     */
    public HashMap<String,Niobject> getNiobjectList()
    {
        return niobjectList;
    }
}
