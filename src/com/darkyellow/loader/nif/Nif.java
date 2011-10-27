/*
Copyright 2011 The darkyellow project at darkyellow.org 
All rights reserved

This file is part of Nif Loader

Nif Loader is free software: you can redistribute it 
and/or modify it under the terms of the GNU General Public License as 
published by the Free Software Foundation, either version 3 of the 
License, or (at your option) any later version.

Nif Loader is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Nif Loader.  If not, see 
<http://www.gnu.org/licenses/>.
 */
package com.darkyellow.loader.nif;

import com.darkyellow.loader.nif.Loader;
import com.darkyellow.loader.nif.niobject.*;
import com.darkyellow.loader.nif.compound.*;
import com.darkyellow.loader.nif.enums.*;

import java.io.*;
import java.nio.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Loads a nif from a file or from a passed byte buffer
 */
public class Nif extends Loader
{

    Header header = new Header();
    private Footer footer = new Footer();
    private ArrayList<NiObject> objectList = new ArrayList<NiObject>();

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            Logger.getLogger(Nif.class.getName()).log(Level.WARNING, "You must pass a single argument which contains the nif file name");
            System.exit(1);
        }
        Nif n = new Nif();
        try
        {
            n.load(args[0]);
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Nif.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Nif.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    public void load(ByteBuffer bbuf)
    {

        if (header.getEndianType() == EndianType.ENDIAN_LITTLE)
        {
            bbuf.order(ByteOrder.LITTLE_ENDIAN);
        } else
        {
            bbuf.order(ByteOrder.BIG_ENDIAN);
        }

        header.load(bbuf, header);

        ClassLoader classLoader = Nif.class.getClassLoader();

        for (int i = 0; i < header.getNumBlocks(); i++)
        {
            try
            {
                String recordType = "";
                if (Integer.parseInt(header.getVersion()) <= 20000005)
                {
                    int length = bbuf.getInt();
                    byte[] recordTypeByte = new byte[length];
                    bbuf.get(recordTypeByte);
                    recordType = new String(recordTypeByte);
                } else
                {
                    int blockIndex = header.getBlockTypeIndex().get(i).intValue();
                    recordType = header.getBlockTypes().get(blockIndex);
                }      
                Class c = classLoader.loadClass("com.darkyellow.loader.nif.niobject." + recordType);
                NiObject n = (NiObject) c.newInstance();
                n.load(bbuf, header);
                getObjectList().add(n);
            } catch (ClassNotFoundException ex)
            {
                Logger.getLogger(Nif.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex)
            {
                Logger.getLogger(Nif.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex)
            {
                Logger.getLogger(Nif.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        //set all the references to be correct
        getHeader().setNiObjects(getObjectList());
        for (int j = 0; j < getObjectList().size(); j++)
        {
            NiObject n = getObjectList().get(j);
            n.setNiObjects(getObjectList());
        }
   
        //Now load the footer
        getFooter().load(bbuf, header);
        getFooter().setNiObjects(getObjectList());
    }

    public String toString()
    {
        String s = "";
        s = s + "Header : [" + getHeader() + "]\n";

        for (int i = 0; i < getObjectList().size(); i++)
        {
            Object nio = getObjectList().get(i);
            s = s + "[" + nio.getClass().getName() + " ObjectNumber=" + i + "]\n";
            s = s + nio;
        }
        return s;
    }

    /**
     * @return the header
     */
    public Header getHeader()
    {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(Header header)
    {
        this.header = header;
    }

    /**
     * @return the footer
     */
    public Footer getFooter()
    {
        return footer;
    }

    /**
     * @param footer the footer to set
     */
    public void setFooter(Footer footer)
    {
        this.footer = footer;
    }

    /**
     * @return the objectList
     */
    public ArrayList<NiObject> getObjectList()
    {
        return objectList;
    }

    /**
     * @param objectList the objectList to set
     */
    public void setObjectList(ArrayList<NiObject> objectList)
    {
        this.objectList = objectList;
    }
}
