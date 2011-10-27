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
 * Basic class to hold basic information from nif.xml. Not currently used 
 * for anything
 */
public class Version
{
    private String version = "0";
    private String implementations = "";

    /**
     * @return the version
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version)
    {
        this.version = version;
    }

    /**
     * @return the implementations
     */
    public String getImplementations()
    {
        return implementations;
    }

    /**
     * @param implementations the implementations to set
     */
    public void setImplementations(String implementations)
    {
        this.implementations = implementations;
    }
    
    /**
     * @return a string representation of the version object
     */
    public String toString ()
    {
        String s = "";
        s = s + "Version [" + getVersion () + "] Implementations [" + getImplementations() + "]";
        return s;
    }
}
