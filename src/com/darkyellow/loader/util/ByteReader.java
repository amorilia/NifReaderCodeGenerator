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
import com.darkyellow.loader.niftypes.Compound;
import com.darkyellow.loader.niftypes.EnumItem;
import com.darkyellow.loader.util.TypeConverter;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * This class creates the java code for reading nif code and is used to generate
 * code.
 * 
 * It has version and condition code and array looping code
 */
public class ByteReader
{
    private static String getVersionCode(int version1, int version2)
    {
        if (version1 == 0 && version2 == 0)
        {
            return "";
        } else if (version1 == 0 && version2 != 0)
        {
            return "\t\tif (Integer.parseInt(header.getVersion ()) <= " + version2;
        } else if (version1 != 0 && version2 == 0)
        {

            return "\t\tif (Integer.parseInt(header.getVersion ()) >= " + version1;
        } else
        {
            return "\t\tif (Integer.parseInt(header.getVersion ()) >= " + version1 + "&& Integer.parseInt(header.getVersion ()) <= " + version2;
        }
    }

    public static String getReadCode(String originalType, int version1, int version2, String name, String condition, String arrayLoop1, String arrayLoop2, String arrayLoop3, String type, boolean isArray2Array, boolean isArray3Array, String argument)
    {
        String s = "";
        // this will be a multiphase approach
        // 1. Work out what to do for a particular type
        // 2. Work out how man times times to read this particular  variable using arr1 and arr2
        // 3. Work out that if statements to use using verison1, version2 and condition
        s = getTypeCode(type, originalType, name, argument);
        s = getForLoopCode(s, arrayLoop1, arrayLoop2, arrayLoop3, isArray2Array, isArray3Array);
        s = getIfCode(s, version1, version2, condition);
        return s;
    }

    private static String getForLoopCode(String currentString, String arrayLoop1, String arrayLoop2, String arrayLoop3, boolean isArray2Array, boolean isArray3Array)
    {
        String s = "";

        if (arrayLoop1.length() != 0)
        {
            s = s + "\t\tfor (int i = 0;i < (" + arrayLoop1 + ") ;i++)\n";
            s = s + "\t\t{\n";
        }
        if (arrayLoop2.length() != 0)
        {
            s = s + "\t\tfor (int j = 0;j < (" + (isArray2Array?"((Number)" + arrayLoop2 + ".get(i)).intValue()":arrayLoop2) + ");j++)\n";
            s = s + "\t\t{\n";
        }
        if (arrayLoop3.length() != 0)
        {
            s = s + "\t\tfor (int k = 0;k < (" + (isArray2Array?"((Number)" + arrayLoop2 + ".get(j)).intValue()":arrayLoop2) + ");k++)\n";
            s = s + "\t\t{\n";
        }



        s = s + currentString;
        
        if (arrayLoop1.length() != 0)
        {
            s = s + "\t\t}\n";
        }
        if (arrayLoop2.length() != 0)
        {
            s = s + "\t\t}\n";
        }
        if (arrayLoop3.length() != 0)
        {
            s = s + "\t\t}\n";
        }

        return s;
    }

    private static String getIfCode(String currentString, int version1, int version2, String condition)
    {
        String s = "";

        if (getVersionCode(version1, version2).compareTo("") != 0 || condition.trim().length() != 0)
        {
            if (getVersionCode(version1, version2).compareTo("") != 0 && condition.trim().length() == 0)
            {
                s = getVersionCode(version1, version2) + ")\n";
            } else if (getVersionCode(version1, version2).compareTo("") != 0 && condition.trim().length() != 0)
            {
                s = getVersionCode(version1, version2) + " && " + condition + ")\n";
            } else
            {
                s = "if (" + condition + ")\n";
            }

            s = s + "\t\t{\n";
            s = s + "\t\t" + currentString;
            s = s + "\t\t}\n";
        } else
        {
            s = currentString;
        }

        return s;
    }

    private static String getTypeCode(String type, String originalType, String name, String arguement)
    {
        String s = "";
        if (originalType.compareTo("bool") == 0)
        {
            s = s + "\tif (Integer.parseInt(header.getVersion ()) <= 4010001)\n";
            s = s + "\t{\n";
            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t\t" + name + ".add(new Boolean((bbuf.getInt()==0?false:true)));";
            } else
            {
                s = s + name + "\t\t\t" + " = (bbuf.getInt()==0?false:true);\n";
            }
            s = s + "\t} else\n";
            s = s + "\t{\n";
            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t\t" + name + ".add(new Boolean((bbuf.get()==0?false:true)));";
            } else
            {
                s = s + name + "\t\t\t" + " = (bbuf.get()==0?false:true);\n";
            }
            s = s + "\t}\n";
        } else if (originalType.compareTo("Ptr") == 0 || originalType.compareTo("Ref") == 0)
        {
            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t" + name + "IntegerRef.add(new Integer(bbuf.getInt()));";
            } else
            {
                s = s + "\t\t\t" + name + "IntegerRef = bbuf.getInt();\n";
            }
        } else if (originalType.compareTo("int") == 0)
        {
            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t\t" + name + ".add(new Integer(bbuf.getInt ()));";
            } else
            {
                s = s + name + "\t\t\t" + " = bbuf.getInt();\n";
            }
        } else if (originalType.compareTo("uint") == 0 || originalType.compareTo("long") == 0 || originalType.compareTo("ulittle32") == 0)
        {
            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t\t" + name + ".add(new Long((long)(bbuf.getInt() & 0xffffffffL)));";
            } else
            {
                s = s + name + "\t\t\t" + " = (long)(bbuf.getInt() & 0xffffffffL);\n";
            }
        } else if (originalType.compareTo("string") == 0)
        {
            // version read code changes so we will write the code out
            // doesn't look great here but writes readable code to use
            s = s + "\tif (Integer.parseInt(header.getVersion ()) <= 20000005)\n";
            s = s + "\t\t{\n";
            s = s + getTypeCode(type, "SizedString", name,arguement);
            s = s + "\t} else\n";
            s = s + "\t{\n";
            s = s + getTypeCode(type, "StringIndex", name,arguement);
            s = s + "\t}\n";
        } else if (originalType.compareTo("StringOffset") == 0)
        {
            s = s + "\t\t{\n";
            s = "//TODO string offset;";
            s = s + "\t\t}\n";
        } else if (originalType.compareTo("SizedString") == 0)
        {
            s = s + "\t\t\tint stringSize" + name + " = bbuf.getInt ();\n";
            s = s + "\t\t\tbyte[] byteBuffer" + name + " = new byte[" + "stringSize" + name + "];\n";
            s = s + "\t\t\tbbuf.get (byteBuffer" + name + ");\n";

            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t\t" + name + ".add(new String(byteBuffer" + name + "));";
            } else
            {
                s = s + "\t\t\t" + name + " = new String(byteBuffer" + name + ");";
            }


        } else if (originalType.compareTo("StringIndex") == 0)
        {
            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t\t" + name + ".add(header.getString (bbuf.getInt ()));";
            } else
            {
                s = s + "\t\t\t" + name + " = header.getString (bbuf.getInt ());";
            }

        } else if (originalType.compareTo("HeaderString") == 0 || originalType.compareTo("LineString") == 0)
        {
            s = s + "\t\t\tString " + name + "Ls = \"\";\n";
            s = s + "\t\t\tchar " + name + "c = ' ';\n";
            s = s + "\t\t\tbyte[] " + name + "b = new byte[1];\n";
            s = s + "\t\t\twhile (" + name + "b[0] != 0x0A)\n";
            s = s + "\t\t\t{\n";
            s = s + "\t\t\t\tbbuf.get(" + name + "b);\n";
            s = s + "\t\t\t\t" + name + "c = new String (" + name + "b).charAt(0);\n";
            s = s + "\t\t\t\t" + name + "Ls = " + name + "Ls + new String (" + name + "b);\n";
            s = s + "\t\t\t}\n";

            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t\t" + name + ".add(" + name + "Ls.substring(0, " + name + "Ls.length()-1));";
            } else
            {

                s = s + "\t\t\t" + name + "=" + name + "Ls.substring(0, " + name + "Ls.length()-1);\n";
            }


        } else if (originalType.compareTo("FileVersion") == 0)
        {
            s = s + "\t\t\t" + name + " = Integer.toHexString(bbuf.getInt());\n";
            s = s + "\t\t\t" + "\t\t\t" + "if (" + name + ".length() == 7)\n";
            s = s + "\t\t\t" + "{\n";
            s = s + "\t\t\t" + name + " = \"0\" + " + name + ";\n";
            s = s + "\t\t\t" + "}\n";
            s = s + "\t\t\t" + name + " = (Integer.valueOf(" + name + ".substring(0,2),16).toString().length()==1?\"0\" + Integer.valueOf(" + name + ".substring(0,2),16).toString():\"\" + Integer.valueOf(" + name + ".substring(0,2),16).toString()) + (Integer.valueOf(" + name + ".substring(2,4),16).toString().length()==1?\"0\" + Integer.valueOf(" + name + ".substring(2,4),16).toString():\"\"+Integer.valueOf(" + name + ".substring(2,4),16).toString())+(Integer.valueOf(" + name + ".substring(4,6),16).toString().length()==1?\"0\" + Integer.valueOf(" + name + ".substring(4,6),16).toString():\"\" + Integer.valueOf(" + name + ".substring(4,6),16).toString())+(Integer.valueOf(" + name + ".substring(6,8),16).toString().length()==1?\"0\" + Integer.valueOf(" + name + ".substring(6,8),16).toString():\"\"+Integer.valueOf(" + name + ".substring(6,8),16).toString());\n";
        } else if (originalType.compareTo("SizedString") == 0)
        {
            s = "//TODO sized string;";
        } else if (originalType.compareTo("short") == 0 || originalType.compareTo("Flags") == 0 || originalType.compareTo("BlockTypeIndex") == 0)
        {
            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t\t" + name + ".add(new Short(bbuf.getShort()));";
            } else
            {
                s = s + "\t\t\t" + name + " = bbuf.getShort();\n";
            }

        } else if (originalType.compareTo("ushort") == 0)
        {
            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t\t" + name + ".add(new Integer((int)(bbuf.getShort() & 0xffff)));";
            } else
            {
                s = s + "\t\t\t" + name + " = (int)(bbuf.getShort() & 0xffff);\n";
            }

        } else if (originalType.compareTo("float") == 0)
        {
            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t\t" + name + ".add(new Float (bbuf.getFloat()));";
            } else
            {
                s = s + "\t\t\t" + name + " = bbuf.getFloat();\n";
            }

        } else if (originalType.compareTo("char") == 0)
        {
            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t\t" + name + ".add(new Character (bbuf.getChar()));";
            } else
            {
                s = s + "\t\t\t" + name + " = bbuf.getChar();\n";
            }

        } else if (originalType.compareTo("byte") == 0)
        {
            if (type.compareTo("ArrayList") == 0)
            {
                s = s + "\t\t\t" + name + ".add(new Byte (bbuf.get()));";
            } else
            {
                s = s + "\t\t\t" + name + " = bbuf.get();\n";
            }
        } else
        {
            // we could have a compound type or enum type
            // check compound first, if it is then just use the originaltype
            
            if (!FindItemsHelper.findCompound(originalType) && !FindItemsHelper.findBitFlag (originalType))
            {                
                // must be an enum return type, we need to find the enum type 
                // then get the code for that type
                s = getTypeCode(type, FindItemsHelper.findEnumTypeOriginal(originalType), name, arguement);
            } 
            else if (!FindItemsHelper.findCompound(originalType) && FindItemsHelper.findBitFlag (originalType))
            {
                // must be an enum return type, we need to find the enum type 
                // then get the code for that type
                s = getTypeCode(type, FindItemsHelper.findBitFlagTypeOriginal(originalType), name, arguement);
            } 
            else
            {
                if (type.compareTo("ArrayList") == 0)
                {

                    s = s + "\t\t" + TypeConverter.convertReturnType(originalType, type, false) + " " + name + "toLoad = new " + TypeConverter.convertToArrayType(originalType,type) + "(" + (arguement.length() != 0?"(int)" + arguement:"") + ");\n";
                    s = s + "\t\t" + name + "toLoad.load (bbuf,header);";
                    s = s + "\t\t\t" + name + ".add(" + name + "toLoad);";
                } else
                {
                    s = s + "\t\t" + name + " = new " + originalType.substring(0, 1).toUpperCase() + originalType.substring(1).replaceAll(" ", "") + "(" + (arguement.length() != 0?"(int)" + arguement:"") + ");\n";
                    s = s + "\t\t" + name + ".load (bbuf,header);";
                }
            }

        }
        return s;
    }
}
