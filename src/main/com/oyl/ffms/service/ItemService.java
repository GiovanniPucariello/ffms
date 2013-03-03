package com.oyl.ffms.service;

import java.math.BigDecimal;
import java.util.List;

import com.oyl.ffms.holder.ItemHolder;

public interface ItemService
{
    public List<ItemHolder> getItems(ItemHolder param) throws Exception;
    
    public ItemHolder getItemByKey(BigDecimal itemOid) throws Exception;
    
    public BigDecimal getOid() throws Exception;
    
    public List<ItemHolder> getItemsByUserOid(BigDecimal userOid) throws Exception;
    
    public List<ItemHolder> getItemsByCategoryOid(BigDecimal categoryOid) throws Exception;
    
    public void confirmItem(ItemHolder param) throws Exception;
    
}
