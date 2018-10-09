package com.avaje.ebean;

import java.util.List;
import java.util.concurrent.Future;

public abstract interface FutureIds<T>
  extends Future<List<Object>>
{
  public abstract Query<T> getQuery();
  
  public abstract List<Object> getPartialIds();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\FutureIds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */