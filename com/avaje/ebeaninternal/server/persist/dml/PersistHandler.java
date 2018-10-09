package com.avaje.ebeaninternal.server.persist.dml;

import java.sql.SQLException;

public abstract interface PersistHandler
{
  public abstract String getBindLog();
  
  public abstract void bind()
    throws SQLException;
  
  public abstract void addBatch()
    throws SQLException;
  
  public abstract void execute()
    throws SQLException;
  
  public abstract void close()
    throws SQLException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dml\PersistHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */