package com.avaje.ebean.event;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Transaction;
import java.util.Set;

public abstract interface BeanPersistRequest<T>
{
  public abstract EbeanServer getEbeanServer();
  
  public abstract Transaction getTransaction();
  
  public abstract Set<String> getLoadedProperties();
  
  public abstract Set<String> getUpdatedProperties();
  
  public abstract T getBean();
  
  public abstract T getOldValues();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BeanPersistRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */