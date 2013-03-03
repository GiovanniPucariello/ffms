package com.oyl.ffms.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.core.io.Resource;
import com.oyl.base.util.BaseXmlConfig;


public class CustomAppConfigHelper extends BaseXmlConfig
{
        
    private CustomAppConfigHelper(File cfgFile) throws ConfigurationException,
            MalformedURLException
    {
        super(cfgFile);
    }


    public static CustomAppConfigHelper getBeanInstance(Resource configPath)
            throws ConfigurationException, MalformedURLException, IOException
    {
        
        CustomAppConfigHelper theSiteConfig = new CustomAppConfigHelper(
                configPath.getFile());

        theSiteConfig.initConfigMap();
        return theSiteConfig;
    }


    protected void initConfigMap()
    {
        super.initConfigMap();
    }

    //*****************************************************
    // custom methods
    //*****************************************************
    
    public static void main(String[] args) throws ConfigurationException, MalformedURLException
    {
        
    }
}
