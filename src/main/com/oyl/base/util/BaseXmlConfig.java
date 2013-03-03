package com.oyl.base.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.configuration.ConversionException;

public class BaseXmlConfig
{
    public static final String MODE_DEV = "development";
    protected static final String ERR_NOELEMENT = "###NO SUCH ELEMENT###";
    protected static final String ERR_CONVERT = "###CONVERSION ERROR###";
    protected static final String ERR_GENERAL = "###ERROR###";
    protected static final String NUMERIC_ERR_IND = "-1";
    protected static final String BLANKS = "                                        "; //40 spaces
    protected static final String NL = System.getProperty("line.separator");
    protected static final List EMPTY_LIST = new ArrayList();
    protected static Map configReg = new HashMap();
    
    protected Map configMap;
    protected Configuration config;
    
    //------------------      CONSTRUCTORS     --------------------*/
    protected BaseXmlConfig(File cfgFile) throws ConfigurationException, MalformedURLException
    {
        ConfigurationFactory factory = new ConfigurationFactory();
        URL configURL = cfgFile.toURL();
        factory.setConfigurationURL(configURL);
        config = factory.getConfiguration();
        configMap = new HashMap();
    }  
    
    public static BaseXmlConfig getInstance(File cfgFile)
            throws ConfigurationException, MalformedURLException
    {
        BaseXmlConfig mySiteConfig;
        String fileName = cfgFile.getAbsolutePath();

        if (!configReg.containsKey(fileName))
        {
            synchronized (BaseXmlConfig.class)
            {
                if (!configReg.containsKey(fileName))
                {
                    mySiteConfig = new BaseXmlConfig(cfgFile);
                    configReg.put(fileName, mySiteConfig);
                    mySiteConfig.initConfigMap();
                }
                else
                    mySiteConfig = (BaseXmlConfig)configReg.get(fileName);
            }
        }
        else
            mySiteConfig = (BaseXmlConfig)configReg.get(fileName);

        return mySiteConfig; 
    }
    
    protected void initConfigMap()
    {
        
    }
    
    protected final String stringValue_(String key)
    {
        try
        {
            return config.getString(key);
        }
        catch (NoSuchElementException ex)
        {
            return ERR_NOELEMENT;
        }
        catch (ConversionException ex)
        {
            return ERR_CONVERT;
        }
        catch (Exception ex)
        {
            return ERR_GENERAL;
        }
    }

    protected final List listValue_(String key)
    {
        try
        {
            return config.getList(key);
        }
        catch (Exception ex)
        {
            return EMPTY_LIST;
        }
    }

    protected final String numericValue_(String key)
    {
        try
        { 
            return config.getString(key);
        }
        catch (Exception ex)
        {
            return NUMERIC_ERR_IND;
        }
    }
    
}
