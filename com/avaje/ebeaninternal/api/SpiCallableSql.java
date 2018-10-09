package com.avaje.ebeaninternal.api;

import com.avaje.ebean.CallableSql;

public abstract interface SpiCallableSql
  extends CallableSql
{
  public abstract BindParams getBindParams();
  
  public abstract TransactionEventTable getTransactionEventTable();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiCallableSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */