package com.avaje.ebean.event;

public abstract interface BeanQueryAdapter
{
  public abstract boolean isRegisterFor(Class<?> paramClass);
  
  public abstract int getExecutionOrder();
  
  public abstract void preQuery(BeanQueryRequest<?> paramBeanQueryRequest);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BeanQueryAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */