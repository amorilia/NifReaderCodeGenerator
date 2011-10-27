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
 * Class to hold Compound information, this contains add items which 
 * contain the real data
 */
public class Compound
{

    private String name = "";
    private String niflibtype = "";
    private String nifskopetype = "";
    private String description = "";
    private String isTemplate = "";
    private ArrayList<Add> addList = new ArrayList();

    
    public Compound cloneCompound ()
    {
        Compound c = new Compound ();
        c.description = description;
        c.isTemplate = isTemplate;
        c.name = name;
        c.niflibtype = niflibtype;
        c.nifskopetype = nifskopetype;
        
        c.addList = new ArrayList ();
        
        Iterator i = addList.iterator();
        
        while (i.hasNext())
        {
            Add a = (Add)i.next();            
            c.addList.add(a.cloneAdd());
        }
        
        return c;
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
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).replaceAll(" ", "");
    }

    /**
     * @return the niflibtype
     */
    public String getNiflibtype()
    {
        return niflibtype;
    }

    /**
     * @param niflibtype the niflibtype to set
     */
    public void setNiflibtype(String niflibtype)
    {
        this.niflibtype = niflibtype;
    }

    /**
     * @return the nifskopetype
     */
    public String getNifskopetype()
    {
        return nifskopetype;
    }

    /**
     * @param nifskopetype the nifskopetype to set
     */
    public void setNifskopetype(String nifskopetype)
    {
        this.nifskopetype = nifskopetype;
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
    public void addAdd(String name, String description, String version1, String version2, String type, String array1, String array2, String defaultValue, String template, String userVersion, String condition, String arguement)
    {
        Add a = new Add();
        a.setName(name);
        a.setNameIndex(name);
        a.setTemplate(template);
        a.setDescription(description);
        a.setArray1(array1);
        a.setArray2(array2);
        a.setVersion1(version1);
        a.setVersion2(version2);
        a.setType(type);
        a.setReturnType(type);
        a.setTemplate(template);
        a.setDefaultValue(defaultValue);
        a.setUserVersion(userVersion);
        a.setCondition(condition);
        a.setArguement(arguement);

        if (array2.length() != 0)
        {
            // check to see whether array2 relates to a previous array
            Iterator i = addList.iterator();

            while (i.hasNext())
            {
                Add ca = (Add) i.next();
                if (ca.getName().compareTo(a.getArray2 ()) == 0)
                {
                    if (ca.getArray1().length() != 0)
                    {
                        a.setArray2Array(true);
                    }
                }
            }
        }

        this.addList.add(a);
    }

    /**
     * @return a string representation of the version object
     */
    public String toString()
    {
        String s = "";
        s = s + "Name [" + getName() + "] Niflibtype [" + getNiflibtype() + "] Nifskopetype [" + getNifskopetype() + "] Description [" + getDescription() + "]";

        Iterator i = getAddList().iterator();
        int j = 0;
        while (i.hasNext())
        {
            Add a = (Add) i.next();
            s = s + "Add [" + j + "] [" + a + "]";
            j++;
        }

        return s;
    }

    /**
     * @return the isTemplate
     */
    public boolean getIsTemplate()
    {
        return (isTemplate.compareTo("1") == 0?true:false);
    }

    /**
     * @param isTemplate the isTemplate to set
     */
    public void setIsTemplate(String isTemplate)
    {
        this.isTemplate = isTemplate;
    }
    
    public void setAddTypesForTemplate (String typeToChange)
    {
        Iterator i = addList.iterator();
        
        while (i.hasNext())
        {
            Add a = (Add)i.next();
            if (a.getType ().compareTo("TEMPLATE") == 0)
            {
                a.setType (typeToChange);
                a.setReturnType (typeToChange);
            }
            
        }
    }
}
