package com.oyl.base.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;

import com.oyl.base.action.BaseAction;
import com.oyl.base.holder.MessageHolder;

public class ErrorMsgHelper implements MessageCodeConstants
{
    public static String getStackTrace(Exception ex)
    {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static void logError(Log log, String key, Exception ex)
    {
        StringBuffer strBuffer = new StringBuffer();
        
        String ticket = String.valueOf(DateUtil.getCurrentTimeStamp());
        
        strBuffer.append("Exception occured. ");
        strBuffer.append("[TICKET-" + ticket + "],");
        strBuffer.append("[KEY-" + key + "].");
        
        log.error(strBuffer, ex);
    }
    
    public static void logError(Log log, Exception ex, BaseAction action, MessageHolder msg)
    {
        StringBuffer strBuffer = new StringBuffer();
        
        String tickNumber = String.valueOf(DateUtil.getCurrentTimeStamp());
        action.setTicket(tickNumber);
        
        strBuffer.append("Exception occured. ");
        strBuffer.append("[TICKET-" + tickNumber + "],");
        strBuffer.append("[KEY-" + action.toString() + "].");
        
        log.error(strBuffer, ex);
                
        msg.saveError(action.getText(MSG_ERROR_GENERAL_TICKET_NUMBER, new String[]{tickNumber}));
    }    
}
