package com.oyl.ffms.service.impl;

import java.util.List;

import com.oyl.base.holder.BaseHolder;
import com.oyl.base.holder.CommonParameterHolder;
import com.oyl.base.service.DBActionService;
import com.oyl.ffms.dao.CtlParamDao;
import com.oyl.ffms.holder.CtlParamHolder;
import com.oyl.ffms.service.CtlParamService;

public class CtlParamServiceImpl implements CtlParamService, DBActionService
{
    private CtlParamDao dao_;
    
    public CtlParamHolder getCtlParamByKey(String ctlGrp, String ctlKey)
            throws Exception
    {
        CtlParamHolder param = new CtlParamHolder();
        param.setCtlGrp(ctlGrp);
        param.setCtlKey(ctlKey);
        
        List<CtlParamHolder> list = this.getCtlParams(param);
        if(list!=null && list.size()>0)
            return list.get(0);
        
        return null;
    }


    public List<CtlParamHolder> getCtlParams(CtlParamHolder param)
            throws Exception
    {
        return dao_.getCtlParams(param);
    }


    public void delete(CommonParameterHolder commPara_, BaseHolder oldObj_)
            throws Exception
    {
        dao_.delete((CtlParamHolder)oldObj_);
    }


    public void insert(CommonParameterHolder commPara_, BaseHolder newObj_)
            throws Exception
    {
        dao_.insert((CtlParamHolder)newObj_);
    }


    public void update(CommonParameterHolder commPara_, BaseHolder newObj_,
            BaseHolder oldObj_) throws Exception
    {
        dao_.update((CtlParamHolder)newObj_);
    }

    //*****************************************************
    // setter and getter
    //*****************************************************

    public void setDao(CtlParamDao dao_)
    {
        this.dao_ = dao_;
    }
}
