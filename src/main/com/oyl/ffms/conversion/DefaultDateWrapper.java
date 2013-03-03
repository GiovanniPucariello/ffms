package com.oyl.ffms.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class DefaultDateWrapper implements DisplaytagColumnDecorator
{
    public Object decorate(Object arg0, PageContext arg1, MediaTypeEnum arg2)
            throws DecoratorException
    {   
        if(arg0 == null)
            return null;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        return sdf.format((Date) arg0);
     }
    
}
