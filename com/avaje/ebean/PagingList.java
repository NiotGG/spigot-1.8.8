package com.avaje.ebean;

import java.util.List;
import java.util.concurrent.Future;

public abstract interface PagingList<T>
{
  public abstract void refresh();
  
  public abstract PagingList<T> setFetchAhead(boolean paramBoolean);
  
  public abstract Future<Integer> getFutureRowCount();
  
  public abstract List<T> getAsList();
  
  public abstract int getPageSize();
  
  public abstract int getTotalRowCount();
  
  public abstract int getTotalPageCount();
  
  public abstract Page<T> getPage(int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\PagingList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */