package com.avaje.ebeaninternal.server.lib.sql;

public abstract interface DataSourceNotify
{
  public abstract void notifyDataSourceUp(String paramString);
  
  public abstract void notifyDataSourceDown(String paramString);
  
  public abstract void notifyWarning(String paramString1, String paramString2);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceNotify.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */