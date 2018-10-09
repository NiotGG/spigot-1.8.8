package com.avaje.ebeaninternal.server.lib.sql;

import java.sql.Connection;

public abstract interface DataSourcePoolListener
{
  public abstract void onAfterBorrowConnection(Connection paramConnection);
  
  public abstract void onBeforeReturnConnection(Connection paramConnection);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourcePoolListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */