package com.avaje.ebean.event;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Query;
import com.avaje.ebean.Transaction;

public abstract interface BeanQueryRequest<T>
{
  public abstract EbeanServer getEbeanServer();
  
  public abstract Transaction getTransaction();
  
  public abstract Query<T> getQuery();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BeanQueryRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */