package com.avaje.ebean.event;

import java.util.Set;

public abstract interface BeanPersistController
{
  public abstract int getExecutionOrder();
  
  public abstract boolean isRegisterFor(Class<?> paramClass);
  
  public abstract boolean preInsert(BeanPersistRequest<?> paramBeanPersistRequest);
  
  public abstract boolean preUpdate(BeanPersistRequest<?> paramBeanPersistRequest);
  
  public abstract boolean preDelete(BeanPersistRequest<?> paramBeanPersistRequest);
  
  public abstract void postInsert(BeanPersistRequest<?> paramBeanPersistRequest);
  
  public abstract void postUpdate(BeanPersistRequest<?> paramBeanPersistRequest);
  
  public abstract void postDelete(BeanPersistRequest<?> paramBeanPersistRequest);
  
  public abstract void postLoad(Object paramObject, Set<String> paramSet);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BeanPersistController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */