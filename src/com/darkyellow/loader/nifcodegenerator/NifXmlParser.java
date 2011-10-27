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

import com.darkyellow.loader.niftypes.Add;
import com.darkyellow.loader.niftypes.Version;
import com.darkyellow.loader.niftypes.Niobject;
import com.darkyellow.loader.niftypes.EnumItem;
import com.darkyellow.loader.niftypes.BitFlagItem;
import com.darkyellow.loader.niftypes.Compound;
import com.darkyellow.loader.niftypes.Basic;
import com.darkyellow.loader.util.SimplePropertiesReader;
import java.io.IOException;
import java.net.*;

import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * Main xml parser, also processes templates and creates a version required for 
 * every template class
 */
public class NifXmlParser
{

    private String nifXmlLoader = SimplePropertiesReader.getInstance().getProperties().getProperty("NIF_XML");
    
    private Document dom;

    public void loadXml()
    {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try
        {
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            dom = db.parse(new URL(nifXmlLoader).openStream());



        } catch (ParserConfigurationException pce)
        {
            pce.printStackTrace();
        } catch (SAXException se)
        {
            se.printStackTrace();
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void processXml()
    {
        //get the root element
        Element docEle = dom.getDocumentElement();

        //get a nodelist of  elements
        NodeList nodeListVersion = docEle.getElementsByTagName("version");
        if (nodeListVersion != null && nodeListVersion.getLength() > 0)
        {
            for (int i = 0; i < nodeListVersion.getLength(); i++)
            {

                //get the version element
                Element version = (Element) nodeListVersion.item(i);

                //get the version object
                Version v = getVersion(version);

                //add it to list
                NifDataHolder.getInstance().getVersionList().put(v.getVersion(), v);
            }
        }

        //get a nodelist of  elements
        NodeList nodeListBasic = docEle.getElementsByTagName("basic");
        if (nodeListBasic != null && nodeListBasic.getLength() > 0)
        {
            for (int i = 0; i < nodeListBasic.getLength(); i++)
            {

                //get the basic element
                Element basic = (Element) nodeListBasic.item(i);

                //get the basic object
                Basic b = getBasic(basic);

                //add it to list
                NifDataHolder.getInstance().getBasicList().put(b.getName(), b);
            }
        }

        //get a nodelist of  elements
        NodeList nodeListEnumItem = docEle.getElementsByTagName("enum");
        if (nodeListEnumItem != null && nodeListEnumItem.getLength() > 0)
        {
            for (int i = 0; i < nodeListEnumItem.getLength(); i++)
            {

                //get the enumItem element
                Element enumItem = (Element) nodeListEnumItem.item(i);

                //get the enumItem object
                EnumItem e = getEnumItem(enumItem);

                //add it to list
                NifDataHolder.getInstance().getEnumItemList().put(e.getName(), e);
            }
        }


        //get a nodelist of  elements
        NodeList nodeListBitFlags = docEle.getElementsByTagName("bitflags");
        if (nodeListBitFlags != null && nodeListBitFlags.getLength() > 0)
        {
            for (int i = 0; i < nodeListBitFlags.getLength(); i++)
            {

                //get the bitflags element
                Element bitflags = (Element) nodeListBitFlags.item(i);


                //get the bitflags object
                BitFlagItem b = getBitFlagItem(bitflags);

                //add it to list
                NifDataHolder.getInstance().getBitFlagItemList().put(b.getName(), b);

            }
        }

        //get a nodelist of  elements
        NodeList nodeListCompound = docEle.getElementsByTagName("compound");
        if (nodeListCompound != null && nodeListCompound.getLength() > 0)
        {
            for (int i = 0; i < nodeListCompound.getLength(); i++)
            {

                //get the compound element
                Element compound = (Element) nodeListCompound.item(i);


                //get the compound object
                Compound c = getCompound(compound);

                //add it to list
                if (c.getIsTemplate())
                {
                    NifDataHolder.getInstance().getCompoundTemplateList().put(c.getName(), c);
                } else
                {
                    //Lets see if we need to create some template clones and also 
                    // change the name of the type

                    Iterator addIter = c.getAddList().iterator();

                    while (addIter.hasNext())
                    {
                        Add a = (Add) addIter.next();
                        checkForTemplatesAndProcess(a);
                    }

                    NifDataHolder.getInstance().getCompoundList().put(c.getName(), c);
                }

            }
        }

 
        //get a nodelist of  elements
        NodeList nodeListNiObject = docEle.getElementsByTagName("niobject");
        if (nodeListNiObject != null && nodeListNiObject.getLength() > 0)
        {
            for (int i = 0; i < nodeListNiObject.getLength(); i++)
            {

                //get the niobject element
                Element niobject = (Element) nodeListNiObject.item(i);

                //get the niobject object
                Niobject n = getNiobject(niobject);

                //add it to list
                NifDataHolder.getInstance().getNiobjectList().put(n.getName(), n);

                //Lets see if we need to create some template clones and also 
                // change the name of the type

                Iterator addIter = n.getAddList().iterator();

                while (addIter.hasNext())
                {
                    Add a = (Add) addIter.next();
                    checkForTemplatesAndProcess(a);
                }

            }
        }
    }

    private void checkForTemplatesAndProcess(Add a)
    {
        // we aren't interested in templates for NiObjects or ones with TEMPLATE
        // the TEMPLATE ones we will process recursively

        if (a.getTemplate().length() != 0 && a.getOriginalType().compareTo("Ref") != 0 && a.getOriginalType().compareTo("Ptr") != 0 && a.getOriginalType().compareTo("TEMPLATE") != 0 && a.getTemplate().compareTo("TEMPLATE") != 0)
        {
            // check to see if we have created a new compound already
            if (NifDataHolder.getInstance().getCompoundList().get(a.getType() + a.getTemplate().substring(0, 1).toUpperCase() + a.getTemplate().substring(1)) != null)
            {
                String type = a.getType();
                a.setType(type + a.getTemplate().substring(0, 1).toUpperCase() + a.getTemplate().substring(1));
                a.setReturnType(type + a.getTemplate().substring(0, 1).toUpperCase() + a.getTemplate().substring(1));
            } else
            {
                Compound toClone = NifDataHolder.getInstance().getCompoundTemplateList().get(a.getType());
                Compound cloned = toClone.cloneCompound();
                cloned.setName(cloned.getName() + a.getTemplate().substring(0, 1).toUpperCase() + a.getTemplate().substring(1));
                cloned.setAddTypesForTemplate(a.getTemplate());

                // lets check the clone for TEMPLATE template, if find change to newType and recursively process
                Iterator cloneIter = cloned.getAddList().iterator();

                while (cloneIter.hasNext())
                {
                    Add cloneAdd = (Add) cloneIter.next();
                    if (cloneAdd.getTemplate().compareTo("TEMPLATE") == 0)
                    {
                        // found a template, set the actual template and process
                        cloneAdd.setTemplate(a.getTemplate());
                        checkForTemplatesAndProcess(cloneAdd);
                    }
                }

                NifDataHolder.getInstance().getCompoundList().put(cloned.getName(), cloned);
                String type = a.getType();
                a.setType(type + a.getTemplate().substring(0, 1).toUpperCase() + a.getTemplate().substring(1));
                a.setReturnType(type + a.getTemplate().substring(0, 1).toUpperCase() + a.getTemplate().substring(1));
            }

        }
    }

    private Version getVersion(Element version)
    {

        // for each <version> element get version details 
        // and what implements it

        Version v = new Version();
        v.setVersion(version.getAttribute("num"));
        v.setImplementations(version.getFirstChild().getNodeValue().trim());

        return v;
    }

    private Basic getBasic(Element basic)
    {

        // for each <version> element get basic details 
        // and what implements it

        Basic b = new Basic();
        b.setCount(Integer.parseInt(basic.getAttribute("count")));
        b.setName(basic.getAttribute("name"));
        b.setNiflibtype(basic.getAttribute("niflibtype"));
        b.setNifskopetype(basic.getAttribute("nifskopetype"));
        b.setDescription(basic.getFirstChild().getNodeValue().trim());

        return b;
    }

    private EnumItem getEnumItem(Element enumItem)
    {

        // for each <version> element get basic details 
        // and what implements it

        EnumItem e = new EnumItem();
        e.setName(enumItem.getAttribute("name"));
        e.setStorage(enumItem.getAttribute("storage"));
        e.setDescription(enumItem.getFirstChild().getNodeValue().trim());

        //get a nodelist of  elements
        NodeList nodeListOption = enumItem.getElementsByTagName("option");
        if (nodeListOption != null && nodeListOption.getLength() > 0)
        {
            for (int i = 0; i < nodeListOption.getLength(); i++)
            {

                //get the option element
                Element option = (Element) nodeListOption.item(i);

                //get the option object
                //value is mostly an integer but for some it is hex, try int and 
                // if fails try hex
                int value = -1;

                try
                {
                    value = Integer.parseInt(option.getAttribute("value"));
                } catch (NumberFormatException numberFormatException)
                {
                    // must be a hex                    
                    value = Integer.valueOf(option.getAttribute("value").substring(2), 16).intValue();
                }

                e.addOption(option.getAttribute("name"), option.getTextContent().trim(), value);
            }
        }

        return e;
    }

    private BitFlagItem getBitFlagItem(Element bitFlagItem)
    {

        // for each <version> element get basic details 
        // and what implements it

        BitFlagItem b = new BitFlagItem();
        b.setName(bitFlagItem.getAttribute("name"));
        b.setStorage(bitFlagItem.getAttribute("storage"));
        b.setDescription(bitFlagItem.getFirstChild().getNodeValue().trim());

        //get a nodelist of  elements
        NodeList nodeListOption = bitFlagItem.getElementsByTagName("option");
        if (nodeListOption != null && nodeListOption.getLength() > 0)
        {
            for (int i = 0; i < nodeListOption.getLength(); i++)
            {

                //get the option element
                Element option = (Element) nodeListOption.item(i);

                //get the option object
                //value is mostly an integer but for some it is hex, try int and 
                // if fails try hex
                int value = -1;

                try
                {
                    value = Integer.parseInt(option.getAttribute("value"));
                } catch (NumberFormatException numberFormatException)
                {
                    // must be a hex                    
                    value = Integer.valueOf(option.getAttribute("value").substring(2), 16).intValue();
                }

                b.addOption(option.getAttribute("name"), option.getTextContent().trim(), value);
            }
        }

        return b;
    }

    private Compound getCompound(Element compound)
    {

        // for each <version> element get basic details 
        // and what implements it


        Compound c = new Compound();
        c.setName(compound.getAttribute("name"));
        c.setIsTemplate(compound.getAttribute(("istemplate")));
        c.setNiflibtype(compound.getAttribute("niflibtype"));
        c.setNifskopetype(compound.getAttribute("nifskopetype"));
        c.setDescription(compound.getFirstChild().getNodeValue().trim());

        //get a nodelist of  elements
        NodeList nodeListAdd = compound.getElementsByTagName("add");
        if (nodeListAdd != null && nodeListAdd.getLength() > 0)
        {
            for (int i = 0; i < nodeListAdd.getLength(); i++)
            {


                //get the add element
                Element add = (Element) nodeListAdd.item(i);

                //get the add object

                c.addAdd(add.getAttribute("name"), add.getTextContent().trim(), add.getAttribute("ver1"), add.getAttribute("ver2"), add.getAttribute("type"), add.getAttribute("arr1"), add.getAttribute("arr2"), add.getAttribute("default"), add.getAttribute("template"), add.getAttribute("userver"), add.getAttribute("cond"), add.getAttribute("arg"));
            }
        }

        return c;
    }

    private Niobject getNiobject(Element niobject)
    {

        // for each <version> element get basic details 
        // and what implements it

        Niobject n = new Niobject();
        n.setName(niobject.getAttribute("name"));
        n.setIsAbstract(niobject.getAttribute("abstract"));
        n.setInherit(niobject.getAttribute("inherit"));
        n.setDescription(niobject.getFirstChild().getNodeValue().trim());

        //get a nodelist of  elements
        NodeList nodeListAdd = niobject.getElementsByTagName("add");
        if (nodeListAdd != null && nodeListAdd.getLength() > 0)
        {
            for (int i = 0; i < nodeListAdd.getLength(); i++)
            {

                //get the add element
                Element add = (Element) nodeListAdd.item(i);

                //get the add object
                n.addAdd(add.getAttribute("name"), add.getTextContent().trim(), add.getAttribute("ver1"), add.getAttribute("ver2"), add.getAttribute("vercond"), add.getAttribute("userver"), add.getAttribute("type"), add.getAttribute("arr1"), add.getAttribute("arr2"), add.getAttribute("arr3"), add.getAttribute("default"), add.getAttribute("template"), add.getAttribute("cond"), add.getAttribute("arg"));
            }
        }

        return n;
    }
}
