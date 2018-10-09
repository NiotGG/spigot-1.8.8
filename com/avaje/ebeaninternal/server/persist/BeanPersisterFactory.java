package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public abstract interface BeanPersisterFactory
{
  public abstract BeanPersister create(BeanDescriptor<?> paramBeanDescriptor);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BeanPersisterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */