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

import com.darkyellow.loader.niftypes.BitFlagItem;
import com.darkyellow.loader.niftypes.BitFlagItemOption;
import com.darkyellow.loader.util.ByteReader;
import com.darkyellow.loader.niftypes.Add;
import com.darkyellow.loader.niftypes.Niobject;
import com.darkyellow.loader.niftypes.EnumItemOption;
import com.darkyellow.loader.niftypes.EnumItem;
import com.darkyellow.loader.niftypes.Compound;
import com.darkyellow.loader.util.FindItemsHelper;
import com.darkyellow.loader.util.SimplePropertiesReader;
import com.darkyellow.loader.util.TypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * This is the main class to write the java code, it includes locations of where
 * to write the data
 * 
 * Each class has a load method and a toString method and full heirachy is used
 * to complete each load and toString
 * 
 * There is also a method written which sets all the Ref and Ptr types 
 * afterwards
 * 
 * Ptr and Ref are really integers so a new variable is created to hold this and
 * the method mentioned above uses this and and sets it properly later
 */
public class NifJavaCodeWriter
{

    private String rootDirectoryForFiles = SimplePropertiesReader.getInstance().getProperties().getProperty("ROOT_DIRECTORY_FOR_FILES");
    private String enumItemPath = rootDirectoryForFiles + SimplePropertiesReader.getInstance().getProperties().getProperty("ENUM_ITEM_PATH");
    private String enumItemPackage = SimplePropertiesReader.getInstance().getProperties().getProperty("ENUM_ITEM_PACKAGE");
    private String bitFlagItemPath = rootDirectoryForFiles + SimplePropertiesReader.getInstance().getProperties().getProperty("BIT_FLAG_ITEM_PATH");
    private String bitFlagItemPackage = SimplePropertiesReader.getInstance().getProperties().getProperty("BIT_FLAG_ITEM_PACKAGE");
    private String compoundPath = rootDirectoryForFiles + SimplePropertiesReader.getInstance().getProperties().getProperty("COMPOUND_PATH");
    private String compoundPackage = SimplePropertiesReader.getInstance().getProperties().getProperty("COMPUOND_PACKAGE");
    private String niobjectPath = rootDirectoryForFiles + SimplePropertiesReader.getInstance().getProperties().getProperty("NIOBJECT_PATH");
    private String niobjectPackage = SimplePropertiesReader.getInstance().getProperties().getProperty("NIOBJECT_PACKAGE");

    public void writeEnumItem(HashMap<String, EnumItem> enumItemList) throws IOException
    {
        Iterator enumIterator = enumItemList.keySet().iterator();

        File fdir = new File(enumItemPath);
        fdir.mkdirs();

        while (enumIterator.hasNext())
        {
            EnumItem e = enumItemList.get(enumIterator.next());

            File enumItemFile = new File(enumItemPath + e.getName().trim() + ".java");
            if (!enumItemFile.exists())
            {
                enumItemFile.createNewFile();
            }
            Writer out = new OutputStreamWriter(new FileOutputStream(enumItemFile));

            out.write("package " + enumItemPackage + ";\n");
            out.write("\n");
            out.write("public class " + e.getName() + "\n");
            out.write("{\n");
            Iterator optionIterator = e.getOptionList().iterator();
            while (optionIterator.hasNext())
            {
                EnumItemOption o = (EnumItemOption) optionIterator.next();
                out.write("\tpublic static final " + TypeConverter.convertReturnType(e.getStorage(), "", false) + " " + o.getName() + " = " + o.getValue() + "; // " + o.getDescription() + "\n");
            }
            out.write("\n}");
            out.flush();
            out.close();
        }
    }

    public void writeBitFlagItem(HashMap<String, BitFlagItem> bitFlagItemList) throws IOException
    {
        Iterator bitFlagIterator = bitFlagItemList.keySet().iterator();

        File fdir = new File(bitFlagItemPath);
        fdir.mkdirs();

        while (bitFlagIterator.hasNext())
        {
            BitFlagItem b = bitFlagItemList.get(bitFlagIterator.next());

            File bitFlagItemFile = new File(bitFlagItemPath + b.getName().trim() + ".java");

            if (!bitFlagItemFile.exists())
            {
                bitFlagItemFile.createNewFile();
            }

            Writer out = new OutputStreamWriter(new FileOutputStream(bitFlagItemFile));

            out.write("package " + bitFlagItemPackage + ";\n");
            out.write("\n");
            out.write("public class " + b.getName() + "\n");
            out.write("{\n");
            Iterator optionIterator = b.getOptionList().iterator();
            while (optionIterator.hasNext())
            {
                BitFlagItemOption o = (BitFlagItemOption) optionIterator.next();
                out.write("\tpublic static final " + TypeConverter.convertReturnType(b.getStorage(), "", false) + " " + o.getName() + " = " + o.getValue() + "; // " + o.getDescription() + "\n");
            }
            out.write("\n}");
            out.flush();
            out.close();
        }
    }

    public void writeCompound(HashMap<String, Compound> compoundList) throws IOException
    {
        Iterator compoundIterator = compoundList.keySet().iterator();

        File fdir = new File(compoundPath);
        fdir.mkdirs();

        while (compoundIterator.hasNext())
        {
            Compound c = compoundList.get(compoundIterator.next());

            // we don't want to create these as strings are strings in java
            if (c.getName().trim().compareTo("SizedString") != 0 && c.getName().trim().compareTo("String") != 0)
            //if (c.getName().trim().compareTo("AVObject") == 0)
            {
                File compoundFile = new File(compoundPath + c.getName().trim() + ".java");
                if (!compoundFile.exists())
                {
                    compoundFile.createNewFile();
                }
                Writer out = new OutputStreamWriter(new FileOutputStream(compoundFile));

                out.write("package " + compoundPackage + ";\n");
                out.write("\n");
                out.write("import " + enumItemPackage + ".*;\n");
                out.write("import " + niobjectPackage + ".*;\n");
                out.write("import " + bitFlagItemPackage + ".*;\n");
                out.write("\n");
                out.write("import java.io.*;\n");
                out.write("import java.nio.*;\n");
                out.write("import java.util.*;\n");
                out.write("\n");
                out.write("public class " + c.getName() + "\n");
                out.write("{\n");

                Iterator addIterator = c.getAddList().iterator();
                HashMap compoundAddItems = new HashMap();

                // use this to save generated code as we parse through the variables            
                StringBuffer readCode = new StringBuffer();

                // use this so we can build a toSring method
                StringBuffer toStringCode = new StringBuffer();

                // use this so we can build a setNiObjects method
                StringBuffer setNiObjects = new StringBuffer();

                while (addIterator.hasNext())
                {
                    Add a = (Add) addIterator.next();

                    if (compoundAddItems.get(a.getName()) != null)
                    {
                        String oldName = a.getName();
                        a.setNameIndex(a.getName() + (Integer) compoundAddItems.get(a.getName()));
                        compoundAddItems.put(oldName, ((Integer) compoundAddItems.get(oldName)).intValue() + 1);
                    } else
                    {
                        compoundAddItems.put(a.getName(), new Integer(1));
                    }

                    processAdds(out, a, readCode, toStringCode, c.getName(), setNiObjects);

                }

                out.write("\n");

                out.write("\tprotected int aRG = 0;\n\n");

                out.write("\tpublic " + c.getName() + "(int aRG)");
                out.write("\t{\n");
                out.write("\t\tthis.aRG = aRG;\n");
                out.write("\t}\n\n");

                out.write("\tpublic " + c.getName() + "()");
                out.write("\t{\n");
                out.write("\t}\n");

                out.write("\tpublic void load (ByteBuffer bbuf, Header header)\n");
                out.write("\t{\n");
                if (c.getName().compareTo("Header") == 0)
                {
                    out.write("\t\tsetVersion(\"4000002\");\n");
                }
                out.write(readCode.toString());
                out.write("\t}\n");

                out.write("\n\tpublic String toString()\n");
                out.write("\t{\n");
                out.write("\t\tString s = \"\";\n");
                out.write(toStringCode.toString());
                out.write("\t\treturn s;\n");
                out.write("\t}\n");

                out.write("\n\tpublic String getString(int stringIndex)\n");
                out.write("\t{\n");
                out.write("\t\tString s = \"\";\n");
                out.write("\t\treturn s;\n");
                out.write("\t}\n");

                out.write("\n\tpublic void setNiObjects(ArrayList loadedObjects)\n");
                out.write("\t{\n");
                out.write(setNiObjects.toString());
                out.write("\t}\n");

                out.write("\n\tpublic String getObjectName()\n");
                out.write("\t{\n");
                out.write("\t\t return \"" + c.getName() + "\";\n");
                out.write("\t}\n");

                out.write("}\n");
                out.flush();
                out.close();
            }
        }
    }

    public void writeNiobject(HashMap<String, Niobject> niobjectList) throws IOException
    {
        Iterator niobjectIterator = niobjectList.keySet().iterator();

        File fdir = new File(niobjectPath);
        fdir.mkdirs();

        while (niobjectIterator.hasNext())
        {
            Niobject n = niobjectList.get(niobjectIterator.next());

            File niobjectFile = new File(niobjectPath + n.getName().trim() + ".java");
            if (!niobjectFile.exists())
            {
                niobjectFile.createNewFile();
            }
            Writer out = new OutputStreamWriter(new FileOutputStream(niobjectFile));

            out.write("package " + niobjectPackage + ";\n");
            out.write("\n");
            out.write("import " + enumItemPackage + ".*;\n");
            out.write("import " + compoundPackage + ".*;\n");
            out.write("import " + bitFlagItemPackage + ".*;\n");
            out.write("\n");
            out.write("import java.io.*;\n");
            out.write("import java.nio.*;\n");
            out.write("import java.util.*;\n");
            out.write("\n");

            if (n.getIsAbstract().compareTo("1") == 0)
            {
                out.write("public abstract class " + n.getName());
            } else
            {
                out.write("public class " + n.getName());
            }

            if (n.getInherit().compareTo("") != 0)
            {
                out.write(" extends " + n.getInherit());
            }

            out.write("\n");

            out.write("{\n");

            Iterator addIterator = n.getAddList().iterator();
            HashMap niobjectAddItems = new HashMap();

            // use this to save generated code as we parse through the variables            
            StringBuffer readCode = new StringBuffer();

            // use this so we can build a toSring method
            StringBuffer toStringCode = new StringBuffer();

            // use this so we can build a setNiObjects method
            StringBuffer setNiObjects = new StringBuffer();

            while (addIterator.hasNext())
            {

                // write out the variables            
                Add a = (Add) addIterator.next();

                // write out the default variables                                
                // check to see if we have done this one already, if we have then alter the variable name
                if (niobjectAddItems.get(a.getName()) != null)
                {
                    String oldName = a.getName();
                    a.setNameIndex(a.getName() + (Integer) niobjectAddItems.get(a.getName()));
                    niobjectAddItems.put(oldName, ((Integer) niobjectAddItems.get(oldName)).intValue() + 1);
                } else
                {
                    niobjectAddItems.put(a.getName(), new Integer(1));
                }

                processAdds(out, a, readCode, toStringCode, n.getName(), setNiObjects);

            }

            out.write("\n");

            out.write("\tprotected int aRG = 0;\n\n");

            out.write("\tpublic " + n.getName() + "(int aRG)");
            out.write("\t{\n");
            out.write("\t\tthis.aRG = aRG;\n");
            out.write("\t}\n\n");

            out.write("\tpublic " + n.getName() + "()");
            out.write("\t{\n");
            out.write("\t}\n");

            out.write("\tpublic void load (ByteBuffer bbuf, Header header)\n");
            out.write("\t{\n");
            if (n.getName().compareTo("NiObject") != 0)
            {
                out.write("\t\tsuper.load (bbuf,header);");
            }
            out.write(readCode.toString());
            out.write("\t}\n");

            out.write("\n\tpublic String toString()\n");
            out.write("\t{\n");
            out.write("\t\tString s = \"\";\n");
            if (n.getName().compareTo("NiObject") != 0)
            {
                out.write("\t\ts = s + super.toString();\n");
            }
            out.write(toStringCode.toString());
            out.write("\t\treturn s;\n");
            out.write("\t}\n");


            out.write("\n\tpublic void setNiObjects(ArrayList loadedObjects)\n");
            out.write("\t{\n");
            if (n.getName().compareTo("NiObject") != 0)
            {
                if (n.getName().compareTo("NiObject") != 0)
                {
                    out.write("\t\tsuper.setNiObjects(loadedObjects);\n");
                }
            }
            out.write(setNiObjects.toString());
            out.write("\t}\n");

            out.write("\n\tpublic String getObjectName()\n");
            out.write("\t{\n");
            out.write("\t\t return \"" + n.getName() + "\";\n");
            out.write("\t}\n");

            out.write("}\n");

            out.flush();
            out.close();
        }

    }

    private void processAdds(Writer out, Add a, StringBuffer readCode, StringBuffer toStringCode, String objectName, StringBuffer setNiobjects) throws IOException
    {

        // get the code to read this variable
        readCode.append("\t\t" + ByteReader.getReadCode(a.getOriginalType(), a.getVersion1(), a.getVersion2(), a.getName(), a.getCondition(), a.getArray1(), a.getArray2(), "", a.getReturnType(), a.isArray2Array(), false, a.getArguement()) + "\n");

        // only write one of the variables, the name index is different if it sees
        // another one
        if (a.getName().compareTo(a.getNameIndex()) == 0)
        {
            if (a.getReturnType().compareTo("ArrayList") == 0)
            {
                toStringCode.append("\t\tfor (int i = 0;i<" + a.getName() + ".size ();i++)\n");
                toStringCode.append("\t\t{\n");
                toStringCode.append("\t\t\ts = s + \"[" + objectName + "][" + a.getName() + "[\"+i+\"]] : [\" + " + a.getName() + ".get(i) + \"]\\n\";\n");
                toStringCode.append("\t\t}\n");
            } else
            {
                if (a.getOriginalType().compareTo("Ptr") != 0)
                {
                    toStringCode.append("\t\ts = s + \"[" + objectName + "][" + a.getName() + "] : [\" + " + a.getName() + " + \"]\\n\";\n");
                }
            }

            if (a.getDefaultValue().compareTo("") != 0 && a.getReturnType().compareTo("ArrayList") != 0)
            {

                if (a.getType().compareTo("byte") == 0)
                {
                    out.write("\tprotected " + a.getReturnType() + " " + a.getName() + " = (byte)" + a.getDefaultValue() + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                } else if (a.getType().compareTo("float") == 0)
                {
                    out.write("\tprotected " + a.getReturnType() + " " + a.getName() + " = (float)" + a.getDefaultValue() + "f" + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                } else if (a.getType().compareTo("long") == 0)
                {
                    out.write("\tprotected " + a.getReturnType() + " " + a.getName() + " = (long)" + a.getDefaultValue() + "L" + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                } else if (a.getType().compareTo("int") == 0)
                {
                    out.write("\tprotected " + a.getReturnType() + " " + a.getName() + " = (int)" + a.getDefaultValue() + "" + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                } else if (a.getType().compareTo("double") == 0)
                {
                    out.write("\tprotected " + a.getReturnType() + " " + a.getName() + " = (double)" + a.getDefaultValue() + "d" + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                } else if (a.getType().compareTo("String") == 0)
                {
                    out.write("\tprotected " + a.getReturnType() + " " + a.getName() + " = \"" + a.getDefaultValue() + "\"" + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                } else if (a.getType().compareTo("char") == 0)
                {
                    out.write("\tprotected " + a.getReturnType() + " " + a.getName() + " = \'" + a.getDefaultValue() + "\'" + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                } else if (a.getType().compareTo("short") == 0 || a.getType().compareTo("int") == 0 || a.getType().compareTo("long") == 0 || a.getType().compareTo("byte") == 0)
                {
                    out.write("\tprotected " + a.getReturnType() + " " + a.getName() + " = " + a.getDefaultValue() + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                } else if (a.getType().compareTo("boolean") == 0)
                {
                    out.write("\tprotected " + a.getReturnType() + " " + a.getName() + " = " + (a.getDefaultValue().compareTo("0") == 0 ? "false" : "true") + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                } else // we have an enum and need to prefix with enum class name
                {
                    if (!FindItemsHelper.findCompound(a.getOriginalType()) && !FindItemsHelper.findBitFlag(a.getOriginalType()))
                    {

                        out.write("\tprotected " + a.getReturnType() + " " + a.getName() + " = " + a.getType() + "." + a.getDefaultValue() + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                    } else if (FindItemsHelper.findCompound(a.getOriginalType()))
                    {
                        // we have a compound
                        if (a.getReturnType().compareTo("String") != 0)
                        {
                            setNiobjects.append("\t\t\tif (" + a.getName() + "!=null)\n");
                            setNiobjects.append("\t\t\t{");
                            setNiobjects.append("\t\t\t\t" + a.getName() + ".setNiObjects (loadedObjects);\n");
                            setNiobjects.append("\t\t\t}");
                        }
                        out.write("\tprotected " + a.getReturnType() + " " + a.getName() + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                    } else // bitflag
                    {
                        out.write("\tprotected " + a.getReturnType() + " " + a.getName() + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                    }


                }

            } else
            {
                if ((a.getOriginalType().compareTo("Ptr") == 0 || a.getOriginalType().compareTo("Ref") == 0) && a.getReturnType().compareTo("ArrayList") != 0)
                {
                    // write out the class but also write out one with an int as this is how they are referenced
                    // we will have to do a second pass to put the write variables in when we load
                    out.write("\tprotected " + a.getReturnType() + " " + a.getName() + " = null; // " + a.getDescription().replace("\n", ". ") + "\n");
                    out.write("\tprotected int " + a.getName() + "IntegerRef = -1; // " + a.getDescription().replace("\n", ". ") + "\n");
                    toStringCode.append("\t\ts = s + \"[" + objectName + "][" + a.getName() + "IntegerRef] : [\" + " + a.getName() + "IntegerRef + \"]\\n\";\n");                                    //write out a the setNiObjects code for this reference                                
                    setNiobjects.append("\t\tif (" + a.getName() + "IntegerRef != -1)");
                    setNiobjects.append("\t\t{");
                    setNiobjects.append("\t\t\t" + a.getName() + " = (" + a.getType() + ")loadedObjects.get (" + a.getName() + "IntegerRef);\n");
                    setNiobjects.append("\t\t}");

                } else if ((a.getOriginalType().compareTo("Ptr") == 0 || a.getOriginalType().compareTo("Ref") == 0) && a.getReturnType().compareTo("ArrayList") == 0)
                {
                    // write out the class but also write out one with an int as this is how they are referenced
                    // we will have to do a second pass to put the write variables in when we load
                    out.write("\tprotected ArrayList<" + a.getType() + "> " + a.getName() + " = new ArrayList (); // " + a.getDescription().replace("\n", ". ") + "\n");
                    out.write("\tprotected ArrayList<Integer> " + a.getName() + "IntegerRef = new ArrayList (); // " + a.getDescription().replace("\n", ". ") + "\n");
                    toStringCode.append("\t\tfor (int i = 0;i<" + a.getName() + "IntegerRef.size ();i++)\n");
                    toStringCode.append("\t\t{\n");
                    toStringCode.append("\t\t\ts = s + \"[" + objectName + "][" + a.getName() + "IntegerRef[\"+i+\"]] : [\" + " + a.getName() + "IntegerRef.get(i) + \"]\\n\";\n");
                    toStringCode.append("\t\t}\n");

                    setNiobjects.append("\t\tfor (int i = 0;i<" + a.getName() + "IntegerRef.size ();i++)\n");
                    setNiobjects.append("\t\t{\n");
                    setNiobjects.append("\t\t\tif (((Integer)" + a.getName() + "IntegerRef.get(i)).intValue () != -1)");
                    setNiobjects.append("\t\t\t{");
                    setNiobjects.append("\t\t\t\t" + a.getName() + ".add ((" + a.getType() + ")loadedObjects.get (((Integer)" + a.getName() + "IntegerRef.get(i)).intValue ()));\n");
                    setNiobjects.append("\t\t\t}\n");
                    setNiobjects.append("\t\t}\n");

                } else if (a.getReturnType().compareTo("ArrayList") == 0)
                {
                    out.write("\tprotected ArrayList<" + a.getArrayType() + "> " + a.getName() + " = new ArrayList(); // " + a.getDescription().replace("\n", ". ") + "\n");
                } else if (FindItemsHelper.findCompound(a.getOriginalType()))
                {
                    if (a.getReturnType().compareTo("String") != 0)
                    {
                        setNiobjects.append("\t\t\tif (" + a.getName() + "!=null)\n");
                        setNiobjects.append("\t\t\t{");
                        setNiobjects.append("\t\t\t\t" + a.getName() + ".setNiObjects (loadedObjects);\n");
                        setNiobjects.append("\t\t\t}");
                    }
                    out.write("\tprotected " + a.getReturnType() + " " + a.getName() + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                } else
                {
                    out.write("\tprotected " + a.getReturnType() + " " + a.getName() + "; // " + a.getDescription().replace("\n", ". ") + "\n");
                }
            }


            //write out the get method
            out.write("\n");
            out.write("\tpublic " + ((a.getReturnType().compareTo("ArrayList") == 0) ? "ArrayList<" + a.getArrayType() + ">" : a.getReturnType()) + " get" + a.getNameFunction() + "()\n");
            out.write("\t{\n");
            out.write("\t\treturn " + a.getName() + ";\n");
            out.write("\t}\n");
            //write out the set method
            out.write("\n");
            out.write("\tpublic void set" + a.getNameFunction() + "(" + ((a.getReturnType().compareTo("ArrayList") == 0) ? "ArrayList<" + a.getArrayType() + ">" : a.getReturnType()) + " " + a.getName() + ")\n");
            out.write("\t{\n");
            out.write("\t\tthis." + a.getName() + " = " + a.getName() + ";\n");
            out.write("\t}\n");
            out.write("\n");



            if (a.getOriginalType().compareTo("Ptr") == 0 || a.getOriginalType().compareTo("Ref") == 0)
            {
                // create a method for our integer
                //write out the get method
                out.write("\n");
                out.write("\tpublic " + ((a.getReturnType().compareTo("ArrayList") == 0) ? "ArrayList<Integer>" : "int") + " get" + a.getNameFunction() + "IntegerRef()\n");
                out.write("\t{\n");
                out.write("\t\treturn " + a.getName() + "IntegerRef;\n");
                out.write("\t}\n");
                //write out the set method
                out.write("\n");
                out.write("\tpublic void set" + a.getNameFunction() + "IntegerRef(" + ((a.getReturnType().compareTo("ArrayList") == 0) ? "ArrayList<Integer>" : "int") + " " + a.getName() + "IntegerRef)\n");
                out.write("\t{\n");
                out.write("\t\tthis." + a.getName() + "IntegerRef = " + a.getName() + "IntegerRef;\n");
                out.write("\t}\n");
                out.write("\n");


            }

        }

    }
}
