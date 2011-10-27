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

/**
 *
 * Contains the details of each item in an bitflag
 */
public class BitFlagItemOption
{
    private String name = "";
    private String description = "";
    private int value = -1;

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
        this.name = name.toUpperCase().replaceAll(" ", "_");
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
     * @return the value
     */
    public int getValue()
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value)
    {
        this.value = value;
    }

    /**
     * @return a string representation of the version object
     */
    public String toString()
    {
        String s = "";
        s = s + "Name [" + getName() + "] Description [" + getDescription() + "] Value [ " + getValue() + "]";
        return s;
    }
}
