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

package com.darkyellow.loader.niftypes;

import com.darkyellow.loader.util.*;
import java.util.Scanner;

/**
 *
 * Contains the items data from the compound and niobject 
 * some preliminary parsing and formatting of the data is done
 * on the conditions it may need to enhance certain logic statements to 
 * make them logic statements in java
 * 
 * All things with a ? mark in are not confirmed, the ? is converted to TEST
 * to make it compile
 * 
 * Some items can contain numbers or text so a test is done and some arrays 
 * contain arrays so the logic to work this out is done up front. 
 */
public class Add
{

    private String name = "";
    private String nameIndex = "";
    private String description = "";
    private int version1 = 0;
    private int version2 = 0;
    private String versionCondition = "";
    private String userVersion = "";
    private String type = "";
    private String originalType = "";
    private String returnType = "";
    private String array1 = "";
    private String array2 = "";
    private boolean array2Array = false;
    private String array3 = "";
    private boolean array3Array = false;
    private String defaultValue = "";
    private String template = "";
    private String condition = "";
    private String arguement = "";

    public Add cloneAdd ()
    {
      Add a = new Add ();
      a.name  = name;
      a.nameIndex = nameIndex;
      a.description = description;
      a.version1 = version1;
      a.version2 = version2;
      a.versionCondition = versionCondition;
      a.userVersion = userVersion;
      a.type = type;
      a.originalType = originalType;
      a.returnType = returnType;
      a.array1 = array1;
      a.array2 = array2;
      a.array3 = array3;
      a.array2Array = array2Array;
      a.array3Array = array3Array;
      a.defaultValue = defaultValue;
      a.template = template;
      a.condition = condition;
      a.arguement = arguement;
      return a;
      
    }
    
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name.substring(0, 1).toLowerCase() + name.substring(1).replaceAll(" ", "").replaceAll("\\?", "TEST");
    }

    /**
     * @return the name with first letter capitalised
     */
    public String getNameFunction()
    {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return a string representation of the version object
     */
    public String toString()
    {
        String s = "";
        s = s + "Name [" + getName() + "] Description [" + getDescription() + "] Type [" + getType() + "] Version1 [" + getVersion1() + "] Version2 [" + getVersion2() + "] Version Condition [" + getVersionCondition() + "] User Version [" + getUserVersion() + "] Array1 [" + getArray1() + "] Array2 [" + getArray2() + "] Array3 [" + getArray3() + "] Template [" + getTemplate() + "] DefaultValue [" + getDefaultValue() + "] Condition [" + getCondition() + "]";
        return s;
    }

    /**
     * @return the version1
     */
    public int getVersion1()
    {
        return version1;
    }

    /**
     * @param version1 the version1 to set
     */
    public void setVersion1(String version1)
    {
        this.version1 = versionConvert(version1);
    }

    /**
     * @return the version2
     */
    public int getVersion2()
    {
        return version2;
    }

    /**
     * @param version2 the version2 to set
     */
    public void setVersion2(String version2)
    {
        this.version2 = versionConvert(version2);
    }

    private int versionConvert(String version)
    {
        int ver = 0;
        String[] versionSplit = version.split("\\.");
        String versionString = "";
        for (int i = 0; i < versionSplit.length; i++)
        {
            String bitOfString = versionSplit[i];
            if (bitOfString.length() == 1)
            {
                bitOfString = "0" + bitOfString;
            }
            versionString = versionString + bitOfString;
        }

        if (versionString.trim().length() != 0)
        {
            ver = Integer.parseInt(versionString.trim());
        }

        return ver;
    }

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }
    
    /**
     * @return the type and capitalise the first letter for Array's
     */
    public String getArrayType()
    {
        return TypeConverter.convertToArrayType (originalType,template);
        
    }

    /**
     * @return the type
     */
    public String getOriginalType()
    {
        return originalType;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type)
    {
        this.originalType = type;
        this.type = TypeConverter.convertType(type, this.getTemplate());
    }

    /**
     * @return the array1
     */
    public String getArray1()
    {
        return array1;
    }

    /**
     * @param array1 the array1 to set
     */
    public void setArray1(String array1)
    {
        if (array1.length() == 0)
        {
            return;
        }

        Scanner s = new Scanner(array1);

        if (s.hasNextInt())
        {
            this.array1 = array1;
        } else
        {
            this.array1 = findVariableAndConvert(array1,false);
        }

    }

    /**
     * @return the array2
     */
    public String getArray2()
    {
        return array2;
    }

    /**
     * @param array2 the array2 to set
     */
    public void setArray2(String array2)
    {
        if (array2.length() == 0)
        {
            return;
        }

        Scanner s = new Scanner(array2);

        if (s.hasNextInt())
        {
            this.array2 = array2;
        } else
        {
            this.array2 = findVariableAndConvert(array2,false);
        }
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue()
    {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the template
     */
    public String getTemplate()
    {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(String template)
    {
        this.template = template;
    }

    /**
     * @return the condition
     */
    public String getCondition()
    {
        if (condition.length() != 0 && versionCondition.length() != 0 && userVersion.length() == 0)
        {
            return condition + " && " + versionCondition;
        } else if (condition.length() != 0 && versionCondition.length() == 0 && userVersion.length() != 0)
        {
            return condition + " && " + userVersion;
        } else if (condition.length() != 0 && versionCondition.length() != 0 && userVersion.length() != 0)
        {
            return condition + " && " + userVersion + " && " + versionCondition;
        } else if (condition.length() == 0 && versionCondition.length() != 0 && userVersion.length() == 0)
        {
            return versionCondition;
        } else if (condition.length() == 0 && versionCondition.length() == 0 && userVersion.length() != 0)
        {
            return userVersion;
        } else if (condition.length() == 0 && versionCondition.length() != 0 && userVersion.length() != 0)
        {
            return userVersion + " && " + versionCondition;
        } else if (condition.length() != 0 && versionCondition.length() == 0 && userVersion.length() == 0)
        {
            return condition;
        } else
        {
            return "";
        }


    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition)
    {

        if (condition.length() == 0)
        {
            this.condition = "";
        } else
        {
            this.condition = findVariableAndConvert(condition,true);
        }
    }

    /**
     * @return the array3
     */
    public String getArray3()
    {
        return array3;
    }

    /**
     * @param array3 the array3 to set
     */
    public void setArray3(String array3)
    {
        if (array3.length() == 0)
        {
            return;
        }

        Scanner s = new Scanner(array3);

        if (s.hasNextInt())
        {
            this.array3 = array3;
        } else
        {
            this.array3 = findVariableAndConvert(array3,false);
        }
    }

    private String findVariableAndConvert(String original, boolean makeBoolean)
    {
        // this tries to find variables and make them lower case to match the 
        // way we store variables

        // first remove all the spaces
        original = original.replaceAll(" ", "");

        int bracket = original.indexOf("(");

        if (bracket == -1)
        {
            original = original.substring(0, 1).toLowerCase() + original.substring(1);
            if (original.indexOf('&') != -1)
            {
                // we hava a bit wise operation, lets equate to not zero for true
                original = "(" + original + ")!=0";
            }
        }

        while (bracket != -1)
        {

            if (original.substring(bracket + 1, bracket + 2).compareTo("(") == 0)
            {
                bracket = original.indexOf("(", bracket + 1);
            } else
            {
                original = original.substring(0, bracket + 1) + original.substring(bracket + 1, bracket + 2).toLowerCase() + original.substring(bracket + 2);
                int openBracket = original.indexOf("(", bracket + 1);
                int closeBracket = original.indexOf(")", bracket + 1);

                if (closeBracket < openBracket || (openBracket == -1 && closeBracket != -1))
                {
                    // we are at a root part of the brackets (ie where a boolean expression should be
                    if (original.substring(bracket + 1, closeBracket).indexOf('&') != -1 && makeBoolean)
                    {
                        // we hava a bit wise operation, lets equate to not zero for true
                        original = original.substring(0, bracket + 1) + "(" + original.substring(bracket + 1, closeBracket) + ")!=0" + original.substring(closeBracket);
                        bracket = original.indexOf("(", bracket + 1);
                    }

                    bracket = original.indexOf("(", bracket + 1);
                } else
                {
                    bracket = original.indexOf("(", bracket + 1);
                }
            }

        }

        return original.replaceAll("\\?", "TEST");
    }

    /**
     * @return the versionCondition
     */
    public String getVersionCondition()
    {
        return versionCondition;
    }

    /**
     * @param versionCondition the versionCondition to set
     */
    public void setVersionCondition(String versionCondition)
    {
        if (versionCondition.length() == 0)
        {
            return;
        } else
        {
            versionCondition = versionCondition.replaceAll("User Version 2", "header.getUserVersion2()");
            versionCondition = versionCondition.replaceAll("User Version", "header.getUserVersion()");

            int locationVersionGreaterEqual = versionCondition.indexOf("Version >=");

            while (locationVersionGreaterEqual != -1)
            {
                // we have a version condition with a Version, its in a non standard format so we need to get it and convert it
                // find the ) after
                // add 10 to move past the whole text
                locationVersionGreaterEqual = locationVersionGreaterEqual + 10;
                int bracket = versionCondition.indexOf(")", locationVersionGreaterEqual);
                versionCondition = versionCondition.replaceFirst(versionCondition.substring(locationVersionGreaterEqual, bracket), "" + versionConvert(versionCondition.substring(locationVersionGreaterEqual, bracket)));
                versionCondition = versionCondition.replaceFirst("Version >=", "Integer.parseInt(header.getVersion ()) >=");
                locationVersionGreaterEqual = versionCondition.indexOf("Version >=");
            }

            int locationVersionEqual = versionCondition.indexOf("Version ==");

            while (locationVersionEqual != -1)
            {
                // we have a version condition with a Version, its in a non standard format so we need to get it and convert it
                // find the ) after
                // add 10 to move past the whole text
                locationVersionEqual = locationVersionEqual + 10;
                int bracket = versionCondition.indexOf(")", locationVersionEqual);
                versionCondition = versionCondition.replaceFirst(versionCondition.substring(locationVersionEqual, bracket), "" + versionConvert(versionCondition.substring(locationVersionEqual, bracket)));
                versionCondition = versionCondition.replaceFirst("Version ==", "Integer.parseInt(header.getVersion ()) ==");
                locationVersionEqual = versionCondition.indexOf("Version ==");
            }

            this.versionCondition = versionCondition;
        }
    }

    /**
     * @return the userVersion
     */
    public String getUserVersion()
    {
        return userVersion;
    }

    /**
     * @param userVersion the userVersion to set
     */
    public void setUserVersion(String userVersion)
    {
        if (userVersion.length() == 0)
        {
            this.userVersion = userVersion;
        } else
        {
            this.userVersion = "header.getUserVersion () == " + userVersion;
        }

    }

    /**
     * @return the returnType
     */
    public String getReturnType()
    {
        return returnType;
    }

    /**
     * @param returnType the returnType to set
     */
    public void setReturnType(String returnType)
    {
        this.returnType = TypeConverter.convertReturnType(returnType, this.getTemplate(), array1.trim().length() != 0);;
    }

    /**
     * @return the array2Array
     */
    public boolean isArray2Array()
    {
        return array2Array;
    }

    /**
     * @param array2Array the array2Array to set
     */
    public void setArray2Array(boolean array2Array)
    {
        this.array2Array = array2Array;
    }

    /**
     * @return the array3Array
     */
    public boolean isArray3Array()
    {
        return array3Array;
    }

    /**
     * @param array3Array the array3Array to set
     */
    public void setArray3Array(boolean array3Array)
    {
        this.array3Array = array3Array;
    }

    /**
     * @return the nameIndex
     */
    public String getNameIndex()
    {
        return nameIndex;
    }

    /**
     * @param nameIndex the nameIndex to set
     */
    public void setNameIndex(String nameIndex)
    {

        this.nameIndex = nameIndex.substring(0, 1).toLowerCase() + nameIndex.substring(1).replaceAll(" ", "").replaceAll("\\?", "TEST");
    }

    /**
     * @return the argument
     */
    public String getArguement()
    {
        return arguement;
    }

    /**
     * @param argument the argument to set
     */
    public void setArguement(String arguement)
    {
        if (arguement.length() != 0)
        {
            Scanner s = new Scanner(arguement);

            if (s.hasNextInt())
            {
                this.arguement = arguement;
            } else
            {
                this.arguement = arguement.substring(0, 1).toLowerCase() + arguement.substring(1).replaceAll(" ", "").replaceAll("\\?", "TEST");
            }
        }
    }
}
