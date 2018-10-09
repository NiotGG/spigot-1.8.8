package com.avaje.ebean.config.dbplatform;

public abstract interface SqlLimiter
{
  public static final char NEW_LINE = '\n';
  public static final char CARRIAGE_RETURN = '\r';
  
  public abstract SqlLimitResponse limit(SqlLimitRequest paramSqlLimitRequest);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\SqlLimiter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */