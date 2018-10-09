package com.avaje.ebean.event;

import com.avaje.ebean.bean.BeanCollection;

public abstract interface BeanFinder<T>
{
  public abstract T find(BeanQueryRequest<T> paramBeanQueryRequest);
  
  public abstract BeanCollection<T> findMany(BeanQueryRequest<T> paramBeanQueryRequest);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BeanFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */