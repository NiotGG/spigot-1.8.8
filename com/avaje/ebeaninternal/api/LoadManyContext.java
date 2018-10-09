package com.avaje.ebeaninternal.api;

import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;

public abstract interface LoadManyContext
  extends LoadSecondaryQuery
{
  public abstract void configureQuery(SpiQuery<?> paramSpiQuery);
  
  public abstract String getFullPath();
  
  public abstract ObjectGraphNode getObjectGraphNode();
  
  public abstract PersistenceContext getPersistenceContext();
  
  public abstract int getBatchSize();
  
  public abstract BeanDescriptor<?> getBeanDescriptor();
  
  public abstract BeanPropertyAssocMany<?> getBeanProperty();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\LoadManyContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */