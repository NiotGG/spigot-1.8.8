package com.avaje.ebean;

import java.io.Closeable;
import java.util.Iterator;

public abstract interface QueryIterator<T>
  extends Iterator<T>, Closeable
{
  public abstract boolean hasNext();
  
  public abstract T next();
  
  public abstract void remove();
  
  public abstract void close();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\QueryIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */