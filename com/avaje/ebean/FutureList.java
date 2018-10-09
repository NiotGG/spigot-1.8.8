package com.avaje.ebean;

import java.util.List;
import java.util.concurrent.Future;

public abstract interface FutureList<T>
  extends Future<List<T>>
{
  public abstract Query<T> getQuery();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\FutureList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */