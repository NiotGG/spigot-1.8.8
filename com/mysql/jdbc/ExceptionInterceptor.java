package com.mysql.jdbc;

import java.sql.SQLException;

public abstract interface ExceptionInterceptor
  extends Extension
{
  public abstract SQLException interceptException(SQLException paramSQLException, Connection paramConnection);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\ExceptionInterceptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */