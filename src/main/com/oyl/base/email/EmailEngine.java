package com.oyl.base.email;


import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.oyl.base.util.ErrorMsgHelper;

public class EmailEngine
{
    protected final Log log = LogFactory.getLog(getClass());
    private JavaMailSenderImpl mailSender;
    private VelocityEngine velocityEngine;
    private EmailConfigHelper cfg;
    
    public boolean sendEmailWithoutAttachment(String [] mailTo_,
            String templateNameSubject_, Map fillValuesSubject_,
            String templateNameContent_, Map fillValuesContent_)
    {
        boolean rlt = false;
        
        StringBuffer sbMail = new StringBuffer();
        
        try
        {
            sbMail.append("[Mail To :::: ").append(mailTo_.toString()).append("],");
            sbMail.append("[With Sender :::: ").append(cfg.getEmailConfig()).append("]");
            sbMail.append("[Template Subject File Name :::: ").append(templateNameSubject_).append("],");
            sbMail.append("[Fill Template Values for Subject :::: ").append(fillValuesSubject_).append("],");
            sbMail.append("[Temmplate Content File Name :::: ").append(templateNameContent_).append("] ");
            sbMail.append("[Fill Template Values for Content :::: ").append(fillValuesContent_).append("] ");
            
            String subject_ = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, templateNameSubject_, fillValuesSubject_);
            
            if (log.isDebugEnabled()) 
            {
                log.debug("Debug In :::: sendEmailWithoutAttachment(mailTo_, templateNameSubject_, fillValuesSubject_, templateNameContent_, fillValuesContent_) ");
                log.debug("Debug Ref Info :::: " + sbMail.toString());
                log.debug("Generate email subject ["+subject_+"] successfully through velocity.");
            }            
            
            String content_  = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, templateNameContent_, fillValuesContent_);
            
            if (log.isDebugEnabled()) 
            {
                log.debug("Debug In :::: sendEmailWithoutAttachment(mailTo_, templateNameSubject_, fillValuesSubject_, templateNameContent_, fillValuesContent_) ");
                log.debug("Debug Ref Info :::: " + sbMail.toString());
                log.debug("Generate email content ["+content_+"] successfully through velocity.");
            }
            
            rlt = sendEmailWithoutAttachment(mailTo_, subject_, content_);
        }
        catch(Exception e)
        {
            rlt = false;
            
            log.error("Err In :::: sendEmailWithoutAttachment(mailTo_, templateNameSubject_, fillValuesSubject_, templateNameContent_, fillValuesContent_) ");
            log.error("Err Ref Info :::: " + sbMail.toString());
            log.error(ErrorMsgHelper.getStackTrace(e));  
        }
        
        return rlt;
    }
        
    public boolean sendEmailWithoutAttachment(String [] mailTo_, String subject_, String templateNameContent_, Map fillValuesContent_)
    {
        boolean rlt = false;
        
        StringBuffer sbMail = new StringBuffer();
        
        try
        {
            sbMail.append("[Mail To :::: ").append(mailTo_.toString()).append("],");
            sbMail.append("[Mail Subject :::: ").append(subject_).append("],");
            sbMail.append("[With Sender :::: ").append(cfg.getEmailConfig()).append("]");
            sbMail.append("[Temmplate Name :::: ").append(templateNameContent_).append("] ");
            sbMail.append("[Fill Template Values :::: ").append(fillValuesContent_).append("] ");
            
            String content_  = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, templateNameContent_, fillValuesContent_);
            
            if (log.isDebugEnabled()) 
            {
                log.debug("Debug In :::: sendEmailWithoutAttachment(mailTo_, subject_, templateNameContent_, fillValuesContent_) ");
                log.debug("Debug Ref Info :::: " + sbMail.toString());
                log.debug("Generate email content ["+content_+"] successfully through velocity.");
            }
            
            rlt = sendEmailWithoutAttachment(mailTo_, subject_, content_);
        }
        catch(Exception e)
        {
            rlt = false;
            
            log.error("Err In :::: sendEmailWithoutAttachment(mailTo_, subject_, templateNameContent_, fillValuesContent_) ");
            log.error("Err Ref Info :::: " + sbMail.toString());
            log.error(ErrorMsgHelper.getStackTrace(e));  
        }
        
        return rlt;
    }
    
    public boolean sendEmailWithoutAttachment(String [] mailTo_, String subject_, String content_)
    {
        boolean rlt = false;
        
        StringBuffer sbMail = new StringBuffer();
        
        try
        {
            sbMail.append("[Mail To :::: ").append(mailTo_.toString()).append("],");
            sbMail.append("[Mail Subject :::: ").append(subject_).append("],");
            sbMail.append("[With Sender :::: ").append(cfg.getEmailConfig()).append("]");
            sbMail.append("[Mail Content :::: ").append(content_).append("] ");
            
            initMailSender();
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(cfg.getUserName());
            message.setTo(mailTo_);
            message.setSubject(subject_);
            message.setText(content_);
            
            mailSender.send(message);
            
            if (log.isInfoEnabled()) log.info("The simple mail has been send successful."+sbMail.toString());
            
            rlt = true;
        }
        catch(Exception e)
        {
            rlt = false;
            
            log.error("Err In :::: sendEmailWithoutAttachment(mailTo_, subject_, content_) ");
            log.error("Err Ref Info :::: " + sbMail.toString());
            log.error(ErrorMsgHelper.getStackTrace(e));
        }
        
        return rlt; 
    }
    
    public boolean sendHtmlEmail(final String [] mailTo_, final String subject_, final String templateNameContent_, final Map fillValuesContent_)
    {
        boolean rlt = false;

        try
        {
            initMailSender();
            
            MimeMessagePreparator preparator = new MimeMessagePreparator() {
                public void prepare(MimeMessage mimeMessage)
                        throws Exception
                {
                    MimeMessageHelper message = new MimeMessageHelper(
                            mimeMessage);

                    message.setTo(mailTo_);

                    message.setFrom(cfg.getUserName()); // could be
                                                        // parameterized...

                    String text = VelocityEngineUtils
                            .mergeTemplateIntoString(velocityEngine,
                                    templateNameContent_,
                                    fillValuesContent_);

                    message.setSubject(subject_);

                    message.setText(text, true);
                }
            };
             
             this.mailSender.send(preparator);
             
             rlt = true;
        }
        catch(Exception e)
        {
            log.error("Err In :::: sendEmailWithoutAttachment(mailTo_, subject_, templateNameContent_, fillValuesContent_) ");
            log.error(ErrorMsgHelper.getStackTrace(e));  
        }
        
        return rlt; 
    }
    
    private void initMailSender() throws Exception
    {
        mailSender.setHost(cfg.getSmtpHost());
        mailSender.setUsername(cfg.getUserName());
        mailSender.setPassword(cfg.getPassword());
        mailSender.setProtocol(cfg.getProtocol());
        mailSender.setPort(cfg.getPort());
        mailSender.setJavaMailProperties(cfg.getProperties());
    }
    
    private void initMailSender(String sendNo) throws Exception
    {
        mailSender.setHost(cfg.getSmtpHost(sendNo));
        mailSender.setUsername(cfg.getUserName(sendNo));
        mailSender.setPassword(cfg.getPassword(sendNo));
        mailSender.setProtocol(cfg.getProtocol(sendNo));
        mailSender.setPort(cfg.getPort(sendNo));
        mailSender.setJavaMailProperties(cfg.getProperties(sendNo));
    }
    
    //----------------- getter and setter ------------------//
    public JavaMailSenderImpl getMailSender()
    {
        return mailSender;
    }
    
    public void setMailSender(JavaMailSenderImpl mailSender)
    {
        this.mailSender = mailSender;
    }
    
    public VelocityEngine getVelocityEngine()
    {
        return velocityEngine;
    }
    
    public void setVelocityEngine(VelocityEngine velocityEngine)
    {
        this.velocityEngine = velocityEngine;
    }

    public EmailConfigHelper getCfg()
    {
        return cfg;
    }

    public void setCfg(EmailConfigHelper cfg)
    {
        this.cfg = cfg;
    }
}
