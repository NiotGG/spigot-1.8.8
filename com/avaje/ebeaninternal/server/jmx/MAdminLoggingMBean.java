package com.avaje.ebeaninternal.server.jmx;

import com.avaje.ebean.LogLevel;

public abstract interface MAdminLoggingMBean
{
  public abstract LogLevel getLogLevel();
  
  public abstract void setLogLevel(LogLevel paramLogLevel);
  
  public abstract boolean isDebugGeneratedSql();
  
  public abstract void setDebugGeneratedSql(boolean paramBoolean);
  
  public abstract boolean isDebugLazyLoad();
  
  public abstract void setDebugLazyLoad(boolean paramBoolean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\jmx\MAdminLoggingMBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */