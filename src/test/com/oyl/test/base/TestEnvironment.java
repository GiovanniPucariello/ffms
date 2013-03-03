package com.oyl.test.base;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestEnvironment
{
	private static Logger logger = Logger.getLogger(TestEnvironment.class) ;
    private static ApplicationContext ctx = null ;
    
    private static final String TEST_CONFIG_FILE = "test_applicationContext.xml";
    
    public static ApplicationContext getApplicationContext()
    {
        if (ctx == null || !validCtx(ctx))
        {
            try
            {
                ctx = new ClassPathXmlApplicationContext(TEST_CONFIG_FILE);
            }
            catch(Exception e)
            {
            	logger.error(e) ;
                return null;
            }
        }
        
        return ctx;
    }
    
    
    private static boolean validCtx(ApplicationContext ctx)
    {
        try
        {
            if (ctx.getBean("jdbcTemplate") != null) 
                return true;
            else 
                return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
}
