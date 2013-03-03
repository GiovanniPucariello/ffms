package com.oyl.ffms.holder;

import java.util.List;

import com.oyl.base.holder.BaseHolder;

public class ModuleHolder extends BaseHolder
{
    private String moduleId;
    private String moduleName;
    private int moduleLevel;
    private String parentId;
    private String showAble;
    private String showOrder;
    private String moduleLink;
    
    private List<ModuleHolder> subModules;


    public String getModuleId()
    {
        return moduleId;
    }


    public void setModuleId(String moduleId)
    {
        this.moduleId = moduleId;
    }


    public String getModuleName()
    {
        return moduleName;
    }


    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }


    public int getModuleLevel()
    {
        return moduleLevel;
    }


    public void setModuleLevel(int moduleLevel)
    {
        this.moduleLevel = moduleLevel;
    }


    public String getParentId()
    {
        return parentId;
    }


    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }


    public String getShowAble()
    {
        return showAble;
    }


    public void setShowAble(String showAble)
    {
        this.showAble = showAble;
    }


    public String getShowOrder()
    {
        return showOrder;
    }


    public void setShowOrder(String showOrder)
    {
        this.showOrder = showOrder;
    }


    public String getModuleLink()
    {
        return moduleLink;
    }


    public void setModuleLink(String moduleLink)
    {
        this.moduleLink = moduleLink;
    }


    public List<ModuleHolder> getSubModules()
    {
        return subModules;
    }


    public void setSubModules(List<ModuleHolder> subModules)
    {
        this.subModules = subModules;
    }
}
