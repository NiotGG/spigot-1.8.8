package com.avaje.ebean.config;

public abstract interface CompoundType<V>
{
  public abstract V create(Object[] paramArrayOfObject);
  
  public abstract CompoundTypeProperty<V, ?>[] getProperties();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\CompoundType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */