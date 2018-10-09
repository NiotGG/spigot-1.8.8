package com.avaje.ebean.text.json;

import java.sql.Date;
import java.sql.Timestamp;

public abstract interface JsonValueAdapter
{
  public abstract String jsonFromDate(Date paramDate);
  
  public abstract String jsonFromTimestamp(Timestamp paramTimestamp);
  
  public abstract Date jsonToDate(String paramString);
  
  public abstract Timestamp jsonToTimestamp(String paramString);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonValueAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */