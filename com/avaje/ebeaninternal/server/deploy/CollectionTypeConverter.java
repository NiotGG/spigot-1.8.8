package com.avaje.ebeaninternal.server.deploy;

public abstract interface CollectionTypeConverter
{
  public abstract Object toUnderlying(Object paramObject);
  
  public abstract Object toWrapped(Object paramObject);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\CollectionTypeConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */