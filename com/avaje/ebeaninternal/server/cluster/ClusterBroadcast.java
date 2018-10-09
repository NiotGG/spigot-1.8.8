package com.avaje.ebeaninternal.server.cluster;

import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;

public abstract interface ClusterBroadcast
{
  public abstract void startup(ClusterManager paramClusterManager);
  
  public abstract void shutdown();
  
  public abstract void broadcast(RemoteTransactionEvent paramRemoteTransactionEvent);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\ClusterBroadcast.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */