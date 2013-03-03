package com.oyl.base.email;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import com.oyl.base.util.BaseXmlConfig;

public class EmailConfigHelper extends BaseXmlConfig
{
    protected final Log log = LogFactory.getLog(getClass());
    private static final String EMAIL_CONFIG_HOLDER_KEY = "EMAIL_CONFIG_HOLDER";
    private static final String SENDER_PREFIX = "mailSender.sender";
    private static final String DEFAULT_SENDER_NO = "1";

    public EmailConfigHelper(File cfgFile) throws ConfigurationException,
            MalformedURLException
    {
        super(cfgFile);
    }

    public static EmailConfigHelper getBeanInstance(Resource configPath)
        throws ConfigurationException, MalformedURLException, IOException
    {
        EmailConfigHelper cfg = new EmailConfigHelper(
                configPath.getFile());
        
        cfg.initConfigMap();
        
        return cfg;
    }
    
    public EmailConfigHolder getEmailConfig()
    {
        return (EmailConfigHolder)this.configMap.get(EMAIL_CONFIG_HOLDER_KEY);
    }
    
    public EmailConfigHolder getEmailConfig(String sendNo)
    {
        return getEmailConfigHolder_(sendNo);
    }
    
    public String getSmtpHost(String sendNo)
    {
        return getEmailConfig(sendNo).getHost();
    }
    
    public String getUserName(String sendNo)
    {
        return getEmailConfig(sendNo).getUserName();
    }
    
    public String getPassword(String sendNo)
    {
        return getEmailConfig(sendNo).getPassword();
    }
    
    public String getProtocol(String sendNo)
    {
        return getEmailConfig(sendNo).getProtocol();
    }
    
    public int getPort(String sendNo)
    {
        return getEmailConfig(sendNo).getPort();
    }
    
    public Properties getProperties(String sendNo)
    {
        return getEmailConfig(sendNo).getProperties();
    }
    
    public String getSmtpHost()
    {
        return getEmailConfig().getHost();
    }
    
    public String getUserName()
    {
        return getEmailConfig().getUserName();
    }
    
    public String getPassword()
    {
        return getEmailConfig().getPassword();
    }
    
    public String getProtocol()
    {
        return getEmailConfig().getProtocol();
    }
    
    public int getPort()
    {
        return getEmailConfig().getPort();
    }
    
    public Properties getProperties()
    {
        return getEmailConfig().getProperties();
    }
    
    protected void initConfigMap()
    {
        super.initConfigMap();
        
        this.configMap.put(EMAIL_CONFIG_HOLDER_KEY, getEmailConfigHolder_());   
    }
    
    //----------- private method --------------//
    private EmailConfigHolder getEmailConfigHolder_()
    {
        EmailConfigHolder config = new EmailConfigHolder();
        
        String siteNode = SENDER_PREFIX+DEFAULT_SENDER_NO+".";
        
        config.setHost(this.stringValue_(siteNode + "smtpHost"));
        config.setUserName(this.stringValue_(siteNode+"smtpUser"));
        config.setPassword(this.stringValue_(siteNode+"smtpPassword"));
        config.setProtocol(this.stringValue_(siteNode+"smtpProtocol"));
        
        String port_ = this.stringValue_(siteNode+"smtpPort");
        try
        {
            config.setPort((new Integer(port_)).intValue());
        }
        catch(Exception e)
        {
            config.setPort(EmailConfigHolder.DEFAULT_SMTP_PORT);
        }
        config.setSocketFactoryClass(this.stringValue_(siteNode+"socketFactoryClass"));
        config.setSocketFactoryFullback(this.stringValue_(siteNode+"socketFactoryFallback"));
        config.setNeedAuth(this.stringValue_(siteNode + "needAuth"));
        config.setViaSSL(this.stringValue_(siteNode+"viaSSL"));
        
        return config;
    }
    
    private EmailConfigHolder getEmailConfigHolder_(String senderNo)
    {
        EmailConfigHolder config = new EmailConfigHolder();
        
        String siteNode = SENDER_PREFIX+senderNo+".";
        
        config.setHost(this.stringValue_(siteNode + "smtpHost"));
        config.setUserName(this.stringValue_(siteNode+"smtpUser"));
        config.setPassword(this.stringValue_(siteNode+"smtpPassword"));
        config.setProtocol(this.stringValue_(siteNode+"smtpProtocol"));
        
        String port_ = this.stringValue_(siteNode+"smtpPort");
        try
        {
            config.setPort((new Integer(port_)).intValue());
        }
        catch(Exception e)
        {
            config.setPort(EmailConfigHolder.DEFAULT_SMTP_PORT);
        }
        config.setSocketFactoryClass(this.stringValue_(siteNode+"socketFactoryClass"));
        config.setSocketFactoryFullback(this.stringValue_(siteNode+"socketFactoryFallback"));
        config.setNeedAuth(this.stringValue_(siteNode + "needAuth"));
        config.setViaSSL(this.stringValue_(siteNode+"viaSSL"));
        
        return config;
    }
}
