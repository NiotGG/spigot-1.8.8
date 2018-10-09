package com.avaje.ebean.event;

import com.avaje.ebean.Transaction;

public abstract interface TransactionEventListener
{
  public abstract void postTransactionCommit(Transaction paramTransaction);
  
  public abstract void postTransactionRollback(Transaction paramTransaction, Throwable paramThrowable);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\TransactionEventListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */