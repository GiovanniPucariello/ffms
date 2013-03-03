package com.oyl.ffms.dao;

import java.math.BigDecimal;

public interface OidDao
{
    public BigDecimal getOid(String procedureName);

    public BigDecimal getOid();
}
