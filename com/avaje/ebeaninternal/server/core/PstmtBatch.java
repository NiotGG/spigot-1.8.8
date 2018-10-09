package com.avaje.ebeaninternal.server.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract interface PstmtBatch
{
  public abstract void setBatchSize(PreparedStatement paramPreparedStatement, int paramInt);
  
  public abstract void addBatch(PreparedStatement paramPreparedStatement)
    throws SQLException;
  
  public abstract int executeBatch(PreparedStatement paramPreparedStatement, int paramInt, String paramString, boolean paramBoolean)
    throws SQLException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\PstmtBatch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */