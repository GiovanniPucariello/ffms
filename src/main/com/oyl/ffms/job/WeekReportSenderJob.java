package com.oyl.ffms.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.oyl.base.email.EmailEngine;

public class WeekReportSenderJob extends QuartzJobBean
{
    private static boolean isRunning = false;
    
    private static final Logger LOG = Logger.getLogger(WeekReportSenderJob.class);
    
    
    private EmailEngine emailEngine;

    @Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException
    {
        if (isRunning)
        {
            LOG.info(":::: Previous job is still running.");
            return;
        }

        isRunning = true;

        try
        {
            process();
        }
        finally
        {
            isRunning = false;
        }
    }
    
    
    private void process()
    {
        emailEngine.sendHtmlEmail(new String[]{"jiangming@pracbiz.com", "jkge@pracbiz.com", "ymzhou@pracbiz.com", "wwyou@pracbiz.com", "sjchen@pracbiz.com", "cdjiang@pracbiz.com", "louyang2@pracbiz.com"},
                "Please deliver Week Report.", "week_report.vm", null);
    }


    public void setEmailEngine(EmailEngine emailEngine)
    {
        this.emailEngine = emailEngine;
    }
    
    
    
}
