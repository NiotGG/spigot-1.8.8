package org.apache.commons.lang3.concurrent;

public abstract interface ConcurrentInitializer<T>
{
  public abstract T get()
    throws ConcurrentException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\concurrent\ConcurrentInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */