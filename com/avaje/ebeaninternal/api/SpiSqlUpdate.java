package com.avaje.ebeaninternal.api;

import com.avaje.ebean.SqlUpdate;

public abstract interface SpiSqlUpdate
  extends SqlUpdate
{
  public abstract BindParams getBindParams();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiSqlUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */