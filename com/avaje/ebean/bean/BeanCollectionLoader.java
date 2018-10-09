package com.avaje.ebean.bean;

public abstract interface BeanCollectionLoader
{
  public abstract String getName();
  
  public abstract void loadMany(BeanCollection<?> paramBeanCollection, boolean paramBoolean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\bean\BeanCollectionLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */