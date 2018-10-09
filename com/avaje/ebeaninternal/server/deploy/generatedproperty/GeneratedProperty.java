package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public abstract interface GeneratedProperty
{
  public abstract Object getInsertValue(BeanProperty paramBeanProperty, Object paramObject);
  
  public abstract Object getUpdateValue(BeanProperty paramBeanProperty, Object paramObject);
  
  public abstract boolean includeInUpdate();
  
  public abstract boolean includeInInsert();
  
  public abstract boolean isDDLNotNullable();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */