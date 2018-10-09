package com.avaje.ebean.event;

import java.util.Set;

public abstract interface BulkTableEventListener
{
  public abstract Set<String> registeredTables();
  
  public abstract void process(BulkTableEvent paramBulkTableEvent);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BulkTableEventListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */