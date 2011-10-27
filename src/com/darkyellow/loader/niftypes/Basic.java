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
 * Basic class containing basic information
 */
public class Basic
{
    private String name = "";
    private int count = 0;
    private String niflibtype = "";
    private String nifskopetype  = "";
    private String description = "";

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
        this.name = name;
    }

    /**
     * @return the count
     */
    public int getCount()
    {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count)
    {
        this.count = count;
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
     * @return a string representation of the version object
     */
    public String toString ()
    {
        String s = "";
        s = s + "Name [" + getName () + "] Count [" + getCount() + "] Niflibtype [" + getNiflibtype() + "] Nifskopetype [" + getNifskopetype() + "] Description [" + getDescription() + "]";
        return s;
    }

}
