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

package com.darkyellow.loader.util;

import com.darkyellow.loader.nifcodegenerator.NifDataHolder;
import java.util.Iterator;

/**
 *
 * This class contains some useful utilities to find item in lists and to find 
 * types of enum and bit flags
 */

public class FindItemsHelper
{

    public static boolean findCompound(String compoundname)
    {
        boolean found = false;
        compoundname = compoundname.substring(0, 1).toUpperCase() + compoundname.substring(1).replaceAll(" ", "");

        Iterator i = NifDataHolder.getInstance().getCompoundList().keySet().iterator();

        while (i.hasNext())
        {
            String compoundToCompare = (String) i.next();
            if (compoundToCompare.compareTo(compoundname) == 0)
            {
                found = true;
            }
        }

        return found;
    }

    public static boolean findBitFlag(String bitflagname)
    {
        boolean found = false;
        bitflagname = bitflagname.substring(0, 1).toUpperCase() + bitflagname.substring(1).replaceAll(" ", "");

        Iterator i = NifDataHolder.getInstance().getBitFlagItemList().keySet().iterator();

        while (i.hasNext())
        {
            String bitFlagToCompare = (String) i.next();
            if (bitFlagToCompare.compareTo(bitflagname) == 0)
            {
                found = true;
            }
        }

        return found;
    }

    public static String findEnumType(String enumname)
    {
        String enumItemtype = enumname;
        enumname = enumname.substring(0, 1).toUpperCase() + enumname.substring(1).replaceAll(" ", "");
        Iterator i = NifDataHolder.getInstance().getEnumItemList().keySet().iterator();

        while (i.hasNext())
        {
            String enumItemToCompare = (String) i.next();
            if (enumItemToCompare.compareTo(enumname) == 0)
            {
                enumItemtype = TypeConverter.convertReturnType(NifDataHolder.getInstance().getEnumItemList().get(enumItemToCompare).getStorage(), "", false);
            }
        }

        return enumItemtype;
    }
    
    public static String findEnumTypeOriginal(String enumname)
    {
        String enumItemtype = enumname;
        enumname = enumname.substring(0, 1).toUpperCase() + enumname.substring(1).replaceAll(" ", "");
        Iterator i = NifDataHolder.getInstance().getEnumItemList().keySet().iterator();

        while (i.hasNext())
        {
            String enumItemToCompare = (String) i.next();
            if (enumItemToCompare.compareTo(enumname) == 0)
            {
                enumItemtype = NifDataHolder.getInstance().getEnumItemList().get(enumItemToCompare).getStorage();
            }
        }

        return enumItemtype;
    }

    public static String findBitFlagType(String bitFlagName)
    {
        String bitFlagItemtype = "";
        bitFlagName = bitFlagName.substring(0, 1).toUpperCase() + bitFlagName.substring(1).replaceAll(" ", "");
        Iterator i = NifDataHolder.getInstance().getBitFlagItemList().keySet().iterator();

        while (i.hasNext())
        {
            String bitFlagItemToCompare = (String) i.next();
            if (bitFlagItemToCompare.compareTo(bitFlagName) == 0)
            {
                bitFlagItemtype = TypeConverter.convertReturnType(NifDataHolder.getInstance().getBitFlagItemList().get(bitFlagItemToCompare).getStorage(), "", false);
            }
        }

        return bitFlagItemtype;
    }
    
    public static String findBitFlagTypeOriginal(String bitFlagName)
    {
        String bitFlagItemtype = "";
        bitFlagName = bitFlagName.substring(0, 1).toUpperCase() + bitFlagName.substring(1).replaceAll(" ", "");
        Iterator i = NifDataHolder.getInstance().getBitFlagItemList().keySet().iterator();

        while (i.hasNext())
        {
            String bitFlagItemToCompare = (String) i.next();
            if (bitFlagItemToCompare.compareTo(bitFlagName) == 0)
            {
                bitFlagItemtype = NifDataHolder.getInstance().getBitFlagItemList().get(bitFlagItemToCompare).getStorage();
            }
        }

        return bitFlagItemtype;
    }
}
