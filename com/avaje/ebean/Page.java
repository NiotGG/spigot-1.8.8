package com.avaje.ebean;

import java.util.List;

public abstract interface Page<T>
{
  public abstract List<T> getList();
  
  public abstract int getTotalRowCount();
  
  public abstract int getTotalPageCount();
  
  public abstract int getPageIndex();
  
  public abstract boolean hasNext();
  
  public abstract boolean hasPrev();
  
  public abstract Page<T> next();
  
  public abstract Page<T> prev();
  
  public abstract String getDisplayXtoYofZ(String paramString1, String paramString2);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\Page.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */