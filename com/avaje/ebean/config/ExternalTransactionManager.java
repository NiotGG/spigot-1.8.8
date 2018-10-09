package com.avaje.ebean.config;

public abstract interface ExternalTransactionManager
{
  public abstract void setTransactionManager(Object paramObject);
  
  public abstract Object getCurrentTransaction();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\ExternalTransactionManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */