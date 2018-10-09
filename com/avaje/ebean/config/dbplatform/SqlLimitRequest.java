package com.avaje.ebean.config.dbplatform;

import com.avaje.ebeaninternal.api.SpiQuery;

public abstract interface SqlLimitRequest
{
  public abstract boolean isDistinct();
  
  public abstract int getFirstRow();
  
  public abstract int getMaxRows();
  
  public abstract String getDbSql();
  
  public abstract String getDbOrderBy();
  
  public abstract SpiQuery<?> getOrmQuery();
  
  public abstract DatabasePlatform getDbPlatform();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\SqlLimitRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */