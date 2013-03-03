package com.oyl.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtil
{ 
    private final static String NUMBER_FORMAT_DEFAULT_AMOUNT = "#,##0.00";
    private final static String NUMBER_FORMAT_DEFAULT_QUARTITY = "0";
    
    public static String formatBigDecimal(BigDecimal obj, String pattern)
    {
        if (obj == null) return null;
        
        DecimalFormat df = new DecimalFormat(pattern);
        
        return df.format(obj.doubleValue());
    }
    
    
    public static String formatAmount(BigDecimal obj) 
    {
        if(obj == null)
        {
            return "";
        }
        
        return formatBigDecimal(obj, NUMBER_FORMAT_DEFAULT_AMOUNT);
    }
    
    
    public static String formatQuantity(BigDecimal obj) 
    {
        if(obj == null)
        {
            return "";
        }
        
        return formatBigDecimal(obj, NUMBER_FORMAT_DEFAULT_QUARTITY);
    }
    
}