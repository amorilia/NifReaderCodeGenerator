/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darkyellow.loader.nif;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author psteele
 */
public abstract class Loader
{
    private String name = "";

    public void save(ByteBuffer bbuf, String filename) throws IOException
    {
        File file = new File(filename);

        file.getParentFile().mkdirs();

        if (!file.exists())
        {
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bbuf.array());
        fos.close();
    }

    public void load(String filename) throws FileNotFoundException, IOException 
    {
        name = filename;
        InputStream is;
        byte[] byteData = new byte[1];

            // try in the class path first
            is = this.getClass().getClassLoader().getResourceAsStream(filename);
            if (is == null)
            {
                // try actual file next
                is = new FileInputStream(filename);
            }


            int MTU = 65536 * 2;
            /* direct buffers*/
            ByteBuffer bbuf = ByteBuffer.allocateDirect(is.available()).order(ByteOrder.nativeOrder());
            byte[] buf = new byte[MTU];
            int r = 0;
            while ((r = is.read(buf)) != -1)
            {
                /* the 0, r ensures that only the read bytes are written */
                bbuf.put(buf, 0, r);
            }
            bbuf.flip();

            is.close();

            //load the data in to a byte buffer;            
            load(bbuf);
            bbuf = null;
                       
    }
    
    public abstract void load(ByteBuffer bbuf);

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
}
