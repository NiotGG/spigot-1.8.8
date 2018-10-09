package com.mysql.jdbc;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract interface Statement
  extends java.sql.Statement
{
  public abstract void enableStreamingResults()
    throws SQLException;
  
  public abstract void disableStreamingResults()
    throws SQLException;
  
  public abstract void setLocalInfileInputStream(InputStream paramInputStream);
  
  public abstract InputStream getLocalInfileInputStream();
  
  public abstract void setPingTarget(PingTarget paramPingTarget);
  
  public abstract ExceptionInterceptor getExceptionInterceptor();
  
  public abstract void removeOpenResultSet(ResultSet paramResultSet);
  
  public abstract int getOpenResultSetCount();
  
  public abstract void setHoldResultsOpenOverClose(boolean paramBoolean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\Statement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */