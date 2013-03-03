package com.oyl.ffms.conversion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;


public class GlobalDateConversion extends StrutsTypeConverter
{
    protected static final Log log = LogFactory.getLog(GlobalDateConversion.class);


    public Object convertFromString(Map context, String[] values, Class toType)
    {
        if (log.isDebugEnabled())
        {
            log.debug("For global date conversion, convertFromString called.");
        }

        String[] vs = (String[]) values;
        if (vs == null || vs.length == 0)
        {
            return null;
        }

        Date toDate = null;
        try
        {
            String fromDate = vs[0];

            if (log.isDebugEnabled())
            {
                log.debug("convertFromString, in values=" + fromDate);
            }
            if (fromDate == null || fromDate.trim().length() == 0)
            {
                return null;
            }

            String dateFormat = "yyyy-MM-dd";
            DateFormat df = new SimpleDateFormat(dateFormat);
            df.setLenient(false);
            toDate = df.parse(fromDate);
            log.debug("DateFormat is :"+dateFormat +" width date :"+toDate);
        }
        catch (Exception exception)
        {
            log.error("Convert date failed from string to date.", exception);
            throw new TypeConversionException(exception);
        }

        return toDate;
    }


    public String convertToString(Map context, Object object)
    {
        log.debug("For global date conversion, convertToString called.");
        String toString = null;
        try
        {
            if (object != null)
            {
                String dateFormat = "yyyy-MM-dd";
                DateFormat df = new SimpleDateFormat(dateFormat);
                toString = df.format((Date) object);
            }
        }
        catch (Exception exception)
        {
            log.error("Convert date failed from date to string.", exception);
            throw new TypeConversionException(exception);
        }
        return toString;
    }

}