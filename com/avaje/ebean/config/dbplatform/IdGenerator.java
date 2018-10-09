package com.avaje.ebean.config.dbplatform;

import com.avaje.ebean.Transaction;

public abstract interface IdGenerator
{
  public static final String AUTO_UUID = "auto.uuid";
  
  public abstract String getName();
  
  public abstract boolean isDbSequence();
  
  public abstract Object nextId(Transaction paramTransaction);
  
  public abstract void preAllocateIds(int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\IdGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */