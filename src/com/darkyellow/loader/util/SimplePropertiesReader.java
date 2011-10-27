/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darkyellow.loader.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author psteele
 */
public class SimplePropertiesReader
{

    private static SimplePropertiesReader instance = null;
    private Properties properties = new Properties();
    
    private SimplePropertiesReader()
    {        
        try
        {
            properties.load(getClass().getResourceAsStream("/NifReaderCodeGenerator.properties"));
        } catch (IOException e)
        {
        }
    }
    
    

    public synchronized static SimplePropertiesReader getInstance()
    {
        if (instance == null)
        {
            instance = new SimplePropertiesReader();
        }

        return instance;
    }

    /**
     * @return the properties
     */
    public Properties getProperties()
    {
        return properties;
    }
}
