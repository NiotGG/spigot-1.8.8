package com.avaje.ebeaninternal.server.lib.sql;

public abstract interface DataSourceAlertListener
{
  public abstract void dataSourceDown(String paramString);
  
  public abstract void dataSourceUp(String paramString);
  
  public abstract void warning(String paramString1, String paramString2);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceAlertListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */