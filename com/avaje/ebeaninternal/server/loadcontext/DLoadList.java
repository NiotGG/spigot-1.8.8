package com.avaje.ebeaninternal.server.loadcontext;

import java.util.List;

public abstract interface DLoadList<T>
{
  public abstract int add(T paramT);
  
  public abstract List<T> getNextBatch(int paramInt);
  
  public abstract void removeEntry(int paramInt);
  
  public abstract List<T> getLoadBatch(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\loadcontext\DLoadList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */