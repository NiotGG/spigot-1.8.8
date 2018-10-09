package com.avaje.ebean.event;

import java.util.Set;

public abstract interface BeanPersistListener<T>
{
  public abstract boolean inserted(T paramT);
  
  public abstract boolean updated(T paramT, Set<String> paramSet);
  
  public abstract boolean deleted(T paramT);
  
  public abstract void remoteInsert(Object paramObject);
  
  public abstract void remoteUpdate(Object paramObject);
  
  public abstract void remoteDelete(Object paramObject);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BeanPersistListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */