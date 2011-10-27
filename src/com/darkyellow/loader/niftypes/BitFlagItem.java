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
 * Contains details of the enum like what data types it contains
 */
public class BitFlagItem
{

    private String name = "";
    private String description = "";
    private String storage = "";
    private ArrayList<BitFlagItemOption> optionList = new ArrayList();

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
     * @return the storage
     */
    public String getStorage()
    {
        return storage;
    }

    /**
     * @param storage the storage to set
     */
    public void setStorage(String storage)
    {
        this.storage = storage;
    }

    /**
     * @return the optionList
     */
    public ArrayList<BitFlagItemOption> getOptionList()
    {
        return optionList;
    }

    /**
     * @param optionList the optionList to set
     */
    public void setOptionList(ArrayList<BitFlagItemOption> optionList)
    {
        this.optionList = optionList;
    }
    
    /**
     * @param optionList the optionList to set
     */
    public void addOption(String name, String description, int value)
    {
        BitFlagItemOption o = new BitFlagItemOption ();
        o.setName(name);
        o.setDescription(description);
        o.setValue(value);
        
        this.optionList.add(o);
    }

    /**
     * @return a string representation of the version object
     */
    public String toString()
    {
        String s = "";
        s = s + "Name [" + getName() + "] Description [" + getDescription() + "] Storage [ " + getStorage() + "]";
        
        Iterator i = getOptionList ().iterator();
        int j = 0;
        while (i.hasNext())
        {
          BitFlagItemOption o = (BitFlagItemOption)i.next();
          s = s + "Option [" + j + "] [" + o + "]";
          j++;
        }
        
        return s;
    }

    
}
