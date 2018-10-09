package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanFinder;
import java.util.List;

public abstract interface BeanFinderManager
{
  public abstract int getRegisterCount();
  
  public abstract int createBeanFinders(List<Class<?>> paramList);
  
  public abstract <T> BeanFinder<T> getBeanFinder(Class<T> paramClass);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanFinderManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */