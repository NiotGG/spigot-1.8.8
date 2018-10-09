package com.avaje.ebeaninternal.server.persist;

import java.sql.SQLException;

public abstract interface BatchPostExecute
{
  public abstract void checkRowCount(int paramInt)
    throws SQLException;
  
  public abstract void setGeneratedKey(Object paramObject);
  
  public abstract void postExecute()
    throws SQLException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BatchPostExecute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */