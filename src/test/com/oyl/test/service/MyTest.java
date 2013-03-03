package com.oyl.test.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.oyl.ffms.holder.FamilyHolder;
import com.oyl.ffms.service.FamilyService;
import com.oyl.test.base.BaseServiceTestCase;


public class MyTest extends BaseServiceTestCase
{
    Set s = new HashSet();
    
    int count = 0;
    
    public void testInsert() throws Exception
    {
        int num = 2500;
        
        Runnable u = new Runnable(){

            @Override
            public void run()
            {
                FamilyService f = (FamilyService) ctx.getBean("familyService");
                
                try
                {
                    s.add(f.getOid());
                    
                    count ++;
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        };
        
        
        Thread[] t = new Thread[num];
        
        for(int i=0; i<num; i++)
        {
            t[i] = new Thread(u);
        }
        
        for(int i=0; i<num; i++)
        {
            t[i].start();
        }
        
        while(count != num)
        {
            Thread.sleep(500);
        }
        
        assertEquals(s.size(), num);
        
    }
}
