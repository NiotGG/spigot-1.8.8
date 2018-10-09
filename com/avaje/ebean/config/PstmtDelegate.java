package com.avaje.ebean.config;

import java.sql.PreparedStatement;

public abstract interface PstmtDelegate
{
  public abstract PreparedStatement unwrap(PreparedStatement paramPreparedStatement);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\PstmtDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */