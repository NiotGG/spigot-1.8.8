package com.avaje.ebean;

public abstract interface Update<T>
{
  public abstract String getName();
  
  public abstract Update<T> setNotifyCache(boolean paramBoolean);
  
  public abstract Update<T> setTimeout(int paramInt);
  
  public abstract int execute();
  
  public abstract Update<T> set(int paramInt, Object paramObject);
  
  public abstract Update<T> setParameter(int paramInt, Object paramObject);
  
  public abstract Update<T> setNull(int paramInt1, int paramInt2);
  
  public abstract Update<T> setNullParameter(int paramInt1, int paramInt2);
  
  public abstract Update<T> set(String paramString, Object paramObject);
  
  public abstract Update<T> setParameter(String paramString, Object paramObject);
  
  public abstract Update<T> setNull(String paramString, int paramInt);
  
  public abstract Update<T> setNullParameter(String paramString, int paramInt);
  
  public abstract String getGeneratedSql();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\Update.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */