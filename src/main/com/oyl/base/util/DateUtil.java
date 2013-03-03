package com.oyl.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

public class DateUtil
{

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    protected static Logger log = Logger.getLogger(DateUtil.class);
        
    
    public static String convertDateToString(Date inDate)
    {
        if (inDate == null) return "";

        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

        return df.format(inDate);
    }
    
    public static String convertDateToString(Date inDate, String dateFormat)
    {
        String resultValue = "";
        
        try
        {
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            resultValue = df.format(inDate);
        }
        catch(Exception e)
        {
            log.error("convertDateToString failed [inDate :::: "+inDate + " && dateFormat :::: "+dateFormat+"]", e.getCause());
        }
        
        return resultValue;
    }

    
    public static Date convertStringToDate(String inDate, String dateFormat)
    {
        SimpleDateFormat df;
        Date resultDate = null;
        
        if (dateFormat == null || dateFormat.trim().equals("")) dateFormat = DEFAULT_DATE_FORMAT;
        
        try
        {
            df = new SimpleDateFormat(dateFormat);
            df.setLenient(false);
            resultDate = df.parse(inDate);
        }
        catch (Exception e)
        {
            log.error(e);
            log.error("convertStringToDate failed [inDate :::: "+inDate + " && dateFormat :::: "+dateFormat+"]", e.getCause());
        }

        return resultDate;
    }
    
    
    public static Date convertStringToDate(String inDate)
    {
        SimpleDateFormat df;
        Date resultDate = null;
        
        try
        {
            df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            df.setLenient(false);
            resultDate = df.parse(inDate);
        }
        catch (Exception e)
        {
            log.error(e);
            log.error("convertStringToDate failed [inDate :::: "+inDate + " && dateFormat :::: "+DEFAULT_DATE_FORMAT+"]", e.getCause());
        }

        return resultDate;
    }
    
    
    public static long getCurrentTimeStamp()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        
        return calendar.getTimeInMillis();
    }

    
    public static boolean isSameDay(Date d1, Date d2)
    {
        return convertDateToString(d1).equals(convertDateToString(d2));
    }


    public static boolean isSameMonth(Date d1, Date d2)
    {
        return convertDateToString(d1).substring(0, 7).equals(
                convertDateToString(d2).substring(0, 7));
    }
    
    
    public static boolean isSameYear(Date d1, Date d2)
    {
        return convertDateToString(d1).substring(0, 4).equals(
                convertDateToString(d2).substring(0, 4));
    }
    
    
    public static void main(String[] args)
    {
        
    }

}
