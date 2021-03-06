package com.avaje.ebean.config;

import com.avaje.ebean.config.dbplatform.DatabasePlatform;

public abstract interface NamingConvention
{
  public abstract void setDatabasePlatform(DatabasePlatform paramDatabasePlatform);
  
  public abstract TableName getTableName(Class<?> paramClass);
  
  public abstract TableName getM2MJoinTableName(TableName paramTableName1, TableName paramTableName2);
  
  public abstract String getColumnFromProperty(Class<?> paramClass, String paramString);
  
  public abstract String getPropertyFromColumn(Class<?> paramClass, String paramString);
  
  public abstract String getSequenceName(String paramString1, String paramString2);
  
  public abstract boolean isUseForeignKeyPrefix();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\NamingConvention.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */