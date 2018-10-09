package com.avaje.ebean.config;

public abstract interface CompoundTypeProperty<V, P>
{
  public abstract String getName();
  
  public abstract P getValue(V paramV);
  
  public abstract int getDbType();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\CompoundTypeProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */