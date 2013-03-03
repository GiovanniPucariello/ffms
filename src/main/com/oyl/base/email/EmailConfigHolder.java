package com.oyl.base.email;

import java.util.Properties;

import com.oyl.base.holder.BaseHolder;

public class EmailConfigHolder extends BaseHolder
{
    public static final int DEFAULT_SMTP_PORT = 25;
    private final String MAIL_SMTP_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";
    private final String MAIL_SMTP_SOCKETFACTORY_CLASS_FALLBACK = "mail.smtp.socketFactory.fallback";
    
    private String host;
    
    private String userName;
    
    private String password;
    
    private String protocol;
    
    private int port;
    
    private String needAuth;
    
    private String viaSSL;
    
    private String socketFactoryClass;
    
    private String socketFactoryFullback;

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getNeedAuth()
    {
        return needAuth;
    }

    public void setNeedAuth(String needAuth)
    {
        this.needAuth = needAuth;
    }

    public String getViaSSL()
    {
        return viaSSL;
    }

    public void setViaSSL(String viaSSL)
    {
        this.viaSSL = viaSSL;
    }

    public String getSocketFactoryClass()
    {
        return socketFactoryClass;
    }

    public void setSocketFactoryClass(String socketFactoryClass)
    {
        this.socketFactoryClass = socketFactoryClass;
    }

    public String getSocketFactoryFullback()
    {
        return socketFactoryFullback;
    }

    public void setSocketFactoryFullback(String socketFactoryFullback)
    {
        this.socketFactoryFullback = socketFactoryFullback;
    }
    
    public Properties getProperties()
    {
        Properties pro = new Properties();
        pro.put("mail.smtp.auth", this.getNeedAuth().trim().toLowerCase());

        if (getViaSSL().trim().toLowerCase().equals("true"))
        {
            pro.put(MAIL_SMTP_SOCKETFACTORY_CLASS,getSocketFactoryClass());
            pro.put(MAIL_SMTP_SOCKETFACTORY_CLASS_FALLBACK, getSocketFactoryFullback().trim().toLowerCase());
        }
        
        return pro;
    }
}
