package com.avaje.ebean.event;

public abstract interface BulkTableEvent
{
  public abstract String getTableName();
  
  public abstract boolean isInsert();
  
  public abstract boolean isUpdate();
  
  public abstract boolean isDelete();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BulkTableEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */