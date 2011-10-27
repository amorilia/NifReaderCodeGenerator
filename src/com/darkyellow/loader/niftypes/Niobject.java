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

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * Class to hold NiObject information, this contains add items which 
 * contain the real data
 */
public class Niobject
{
    private String name = "";
    private String isAbstract = "";
    private String inherit  = "";
    private String description = "";
    private ArrayList<Add> addList = new ArrayList();

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
        this.name = name.substring(0,1).toUpperCase() + name.substring(1).replaceAll(" ", "");
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
     * @return the addList
     */
    public ArrayList<Add> getAddList()
    {
        return addList;
    }

    /**
     * @param addList the addList to set
     */
    public void setAddList(ArrayList<Add> addList)
    {
        this.addList = addList;
    }
    
    /**
     * @param optionList the optionList to set
     */
    public void addAdd(String name, String description, String version1, String version2, String versionCondition, String userVersion, String type, String array1, String array2,String array3, String defaultValue, String template, String condition, String arguement)
    {
        Add a = new Add ();
        a.setName(name);
        a.setNameIndex (name);
        a.setTemplate(template);
        a.setDescription(description);
        a.setArray1(array1);
        a.setArray2(array2);
        a.setArray3(array3);
        a.setVersion1(version1);
        a.setVersion2(version2);
        a.setVersionCondition(versionCondition);
        a.setUserVersion(userVersion);
        a.setType(type);
        a.setReturnType(type);
        a.setDefaultValue(defaultValue);
        a.setCondition(condition);       
        a.setArguement(arguement);
        
        if (array2.length()!=0)
        {
            // check to see whether array2 relates to a previous array
            Iterator i = addList.iterator();
            
            
            while (i.hasNext())
            {
                Add add = (Add)i.next();
               
                if (add.getName ().compareTo (a.getArray2()) == 0)
                {
                    if (add.getArray1().length() != 0)
                    {
                        a.setArray2Array(true);
                    }
                }
            }
        }
        
        if (array3.length()!=0)
        {
            // check to see whether array2 relates to a previous array
            Iterator i = addList.iterator();
            
            while (i.hasNext())
            {
                Add add = (Add)i.next();
                if (add.getName ().compareTo (a.getArray3()) == 0)
                {
                    if (add.getArray1().length() != 0)
                    {
                        a.setArray3Array(true);
                    }
                }
            }
        }
        this.addList.add(a);
    }

    
    /**
     * @return a string representation of the version object
     */
    public String toString ()
    {
        String s = "";
        s = s + "Name [" + getName () + "] IsAbstract [" + getIsAbstract() + "] Inherit [" + getInherit() + "] Description [" + getDescription() + "]";
        
        Iterator i = getAddList ().iterator();
        int j = 0;
        while (i.hasNext())
        {
          Add a = (Add)i.next();
          s = s + "Add [" + j + "] [" + a + "]";
          j++;
        }
        
        return s;
    }

    /**
     * @return the isAbstract
     */
    public String getIsAbstract()
    {
        return isAbstract;
    }

    /**
     * @param isAbstract the isAbstract to set
     */
    public void setIsAbstract(String isAbstract)
    {
        this.isAbstract = isAbstract;
    }

    /**
     * @return the inherit
     */
    public String getInherit()
    {
        return inherit;
    }

    /**
     * @param inherit the inherit to set
     */
    public void setInherit(String inherit)
    {
        if (inherit.length() > 0)
        {
            this.inherit = inherit.substring(0,1).toUpperCase() + inherit.substring(1).replaceAll(" ", "");
        }
        else
        {
            this.inherit = inherit;
        }
    }

}
