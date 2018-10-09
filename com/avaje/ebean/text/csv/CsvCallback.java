package com.avaje.ebean.text.csv;

import com.avaje.ebean.EbeanServer;

public abstract interface CsvCallback<T>
{
  public abstract void begin(EbeanServer paramEbeanServer);
  
  public abstract void readHeader(String[] paramArrayOfString);
  
  public abstract boolean processLine(int paramInt, String[] paramArrayOfString);
  
  public abstract void processBean(int paramInt, String[] paramArrayOfString, T paramT);
  
  public abstract void end(int paramInt);
  
  public abstract void endWithError(int paramInt, Exception paramException);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\csv\CsvCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */