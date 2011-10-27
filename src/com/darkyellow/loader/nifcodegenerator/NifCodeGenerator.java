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

import com.darkyellow.loader.niftypes.Version;
import com.darkyellow.loader.niftypes.Niobject;
import com.darkyellow.loader.niftypes.EnumItem;
import com.darkyellow.loader.niftypes.Compound;
import com.darkyellow.loader.niftypes.Basic;
import com.darkyellow.loader.util.TypeConverter;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * Main class which calls everything
 */
public class NifCodeGenerator
{

    public static void main(String[] args)
    {   
        try
        {
            NifXmlParser nxp = new NifXmlParser();
            nxp.loadXml();
            nxp.processXml();
        
            NifJavaCodeWriter njcw = new NifJavaCodeWriter ();
            njcw.writeEnumItem(NifDataHolder.getInstance().getEnumItemList());
            njcw.writeBitFlagItem(NifDataHolder.getInstance().getBitFlagItemList());
            njcw.writeCompound(NifDataHolder.getInstance().getCompoundList());
            njcw.writeNiobject(NifDataHolder.getInstance().getNiobjectList());
        
        } catch (IOException ex)
        {
            Logger.getLogger(NifCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
