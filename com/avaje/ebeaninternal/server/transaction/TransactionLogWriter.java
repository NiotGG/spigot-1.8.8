package com.avaje.ebeaninternal.server.transaction;

public abstract interface TransactionLogWriter
{
  public abstract void log(TransactionLogBuffer paramTransactionLogBuffer);
  
  public abstract void shutdown();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\TransactionLogWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */