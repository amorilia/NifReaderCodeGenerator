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
 * This class is used to take the types presented in the nif.xml file and c
 * convert them for use in the java language
 * 
 * All variables have their spaces removed and first character set to lowercase
 * 
 * The enum classes are not really implementable classes so they are treated 
 * specially
 * 
 * It also differentiates between return types and types of the variable.
 */
public class TypeConverter
{

    public static String convertReturnType(String originalType, String template, boolean isArray)
    {
        originalType = originalType.trim();
        String returnType = originalType;

        if (originalType.compareTo("bool") == 0)
        {
            returnType = "boolean";
        } else if (originalType.compareTo("Ptr") == 0 || originalType.compareTo("Ref") == 0)
        {
            returnType = template.substring(0, 1).toUpperCase() + template.substring(1).replaceAll(" ", "");
        } else if (originalType.compareTo("int") == 0)
        {
            returnType = "int";
        } else if (originalType.compareTo("uint") == 0 || originalType.compareTo("long") == 0 || originalType.compareTo("ulittle32") == 0)
        {
            returnType = "long";
        } else if (originalType.compareTo("string") == 0 || originalType.compareTo("StringOffset") == 0 || originalType.compareTo("HeaderString") == 0 || originalType.compareTo("LineString") == 0 || originalType.compareTo("FileVersion") == 0 || originalType.compareTo("SizedString") == 0 || originalType.compareTo("StringIndex") == 0)
        {
            returnType = "String";
        } else if (originalType.compareTo("short") == 0 || originalType.compareTo("Flags") == 0 || originalType.compareTo("BlockTypeIndex") == 0)
        {
            returnType = "short";
        } else if (originalType.compareTo("ushort") == 0)
        {
            returnType = "int";
        } else if (originalType.compareTo("float") == 0)
        {
            returnType = "float";
        } else if (originalType.compareTo("char") == 0)
        {
            returnType = "char";
        } else if (originalType.compareTo("byte") == 0)
        {
            returnType = "byte";
        } else if (originalType.compareTo("TEMPLATE") == 0)
        {
            returnType = "TEMPLATE";
        } else
        {
            // we could have a compound type or enum type
            // check compound first, if it is then just use the originaltype
            if (!FindItemsHelper.findCompound(originalType) && !FindItemsHelper.findBitFlag(originalType))
            {
                // must be an enum return type, we need to find the enum then find its type
                returnType = FindItemsHelper.findEnumType(originalType);
            } else if (!FindItemsHelper.findCompound(originalType) && FindItemsHelper.findBitFlag(originalType))
            {
                // must be an bitFlag return type, we need to find the bitflag then find its type
                returnType = FindItemsHelper.findBitFlagType(originalType);
            } else
            {
                returnType = returnType.substring(0, 1).toUpperCase() + returnType.substring(1).replaceAll(" ", "");
            }

        }

        if (isArray)
        {
            returnType = "ArrayList";
        }
        return returnType;
    }

    public static String convertType(String originalType, String template)
    {
        originalType = originalType.trim();
        String type = originalType;

        if (originalType.compareTo("bool") == 0)
        {
            type = "boolean";
        } else if (originalType.compareTo("Ptr") == 0 || originalType.compareTo("Ref") == 0)
        {
            type = template.substring(0, 1).toUpperCase() + template.substring(1).replaceAll(" ", "");
        } else if (originalType.compareTo("int") == 0 || originalType.compareTo("ushort") == 0)
        {
            type = "int";
        } else if (originalType.compareTo("uint") == 0 || originalType.compareTo("ulittle32") == 0)
        {
            type = "long";
        } else if (originalType.compareTo("string") == 0 || originalType.compareTo("StringOffset") == 0 || originalType.compareTo("HeaderString") == 0 || originalType.compareTo("LineString") == 0 || originalType.compareTo("FileVersion") == 0 || originalType.compareTo("SizedString") == 0 || originalType.compareTo("StringIndex") == 0)
        {
            type = "String";
        } else if (originalType.compareTo("short") == 0 || originalType.compareTo("Flags") == 0 || originalType.compareTo("BlockTypeIndex") == 0)
        {
            type = "short";
        } else if (originalType.compareTo("float") == 0)
        {
            type = "float";
        } else if (originalType.compareTo("char") == 0)
        {
            type = "char";
        } else if (originalType.compareTo("byte") == 0)
        {
            type = "byte";
        } else if (originalType.compareTo("TEMPLATE") == 0)
        {
            type = "TEMPLATE";
        } else if (FindItemsHelper.findBitFlag(originalType))
        {
            type = FindItemsHelper.findBitFlagType(originalType);
        } else
        {
            // we could have a compound type or enum type, doesn't matter for type      
            type = type.substring(0, 1).toUpperCase() + type.substring(1).replaceAll(" ", "");
        }

        return type;
    }

    public static String convertToArrayType(String originalType, String template)
    {

        String arrayType = originalType;

        if (originalType.compareTo("bool") == 0)
        {
            arrayType = "Boolean";
        } else if (originalType.compareTo("Ptr") == 0 || originalType.compareTo("Ref") == 0)
        {
            arrayType = template.substring(0, 1).toUpperCase() + template.substring(1).replaceAll(" ", "");
        } else if (originalType.compareTo("int") == 0 || originalType.compareTo("ushort") == 0)
        {
            arrayType = "Integer";
        } else if (originalType.compareTo("long") == 0 || originalType.compareTo("uint") == 0 || originalType.compareTo("ulittle32") == 0)
        {
            arrayType = "Long";
        } else if (originalType.compareTo("string") == 0 || originalType.compareTo("StringOffset") == 0 || originalType.compareTo("HeaderString") == 0 || originalType.compareTo("LineString") == 0 || originalType.compareTo("FileVersion") == 0 || originalType.compareTo("SizedString") == 0 || originalType.compareTo("StringIndex") == 0)
        {
            arrayType = "String";
        } else if (originalType.compareTo("short") == 0 || originalType.compareTo("Flags") == 0 || originalType.compareTo("BlockTypeIndex") == 0)
        {
            arrayType = "Short";
        } else if (originalType.compareTo("float") == 0)
        {
            arrayType = "Float";
        } else if (originalType.compareTo("char") == 0)
        {
            arrayType = "Character";
        } else if (originalType.compareTo("byte") == 0)
        {
            arrayType = "Byte";
        } else if (originalType.compareTo("TEMPLATE") == 0)
        {
            arrayType = "TEMPLATE";
        } else
        {
            // we could have a compound type or enum type
            // check compound first, if it is then just use the originaltype
            if (!FindItemsHelper.findCompound(originalType) && !FindItemsHelper.findBitFlag(originalType))
            {
                // must be an enum return type, we need to find the enum then find its type
                arrayType = convertToArrayType(FindItemsHelper.findEnumType(originalType),"");
            } else if (!FindItemsHelper.findCompound(originalType) && FindItemsHelper.findBitFlag(originalType))
            {
                // must be an bitFlag return type, we need to find the bitflag then find its type
                arrayType = convertToArrayType(FindItemsHelper.findBitFlagType(originalType),"");
            } else
            {
                arrayType = arrayType.substring(0, 1).toUpperCase() + arrayType.substring(1).replaceAll(" ", "");
            }

        }

        return arrayType;
    }
}
