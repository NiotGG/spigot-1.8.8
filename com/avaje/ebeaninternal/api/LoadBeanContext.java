package com.avaje.ebeaninternal.api;

import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public abstract interface LoadBeanContext
  extends LoadSecondaryQuery
{
  public abstract void configureQuery(SpiQuery<?> paramSpiQuery, String paramString);
  
  public abstract String getFullPath();
  
  public abstract PersistenceContext getPersistenceContext();
  
  public abstract BeanDescriptor<?> getBeanDescriptor();
  
  public abstract int getBatchSize();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\LoadBeanContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */