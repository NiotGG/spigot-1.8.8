package com.avaje.ebean.bean;

public abstract interface PersistenceContext
{
  public abstract void put(Object paramObject1, Object paramObject2);
  
  public abstract Object putIfAbsent(Object paramObject1, Object paramObject2);
  
  public abstract Object get(Class<?> paramClass, Object paramObject);
  
  public abstract void clear();
  
  public abstract void clear(Class<?> paramClass);
  
  public abstract void clear(Class<?> paramClass, Object paramObject);
  
  public abstract int size(Class<?> paramClass);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\bean\PersistenceContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */