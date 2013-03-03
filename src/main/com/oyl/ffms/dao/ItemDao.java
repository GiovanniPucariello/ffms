package com.oyl.ffms.dao;

import java.util.List;

import com.oyl.ffms.holder.ItemHolder;

public interface ItemDao
{
    public List<ItemHolder> getItems(ItemHolder param);
    
    public int getCountOfSummary(ItemHolder param);    
    
    public List<ItemHolder> getListOfSummary(ItemHolder param);
    
    public int insert(ItemHolder param);
    
    public int delete(ItemHolder param);
    
    public int update(ItemHolder param);
}
