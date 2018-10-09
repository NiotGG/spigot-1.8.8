package com.avaje.ebean;

import java.util.List;
import java.util.concurrent.Future;

public abstract interface SqlFutureList
  extends Future<List<SqlRow>>
{
  public abstract SqlQuery getQuery();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\SqlFutureList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */