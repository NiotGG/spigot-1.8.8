package com.avaje.ebean.common;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;

public abstract interface BootupEbeanManager
{
  public abstract EbeanServer createServer(ServerConfig paramServerConfig);
  
  public abstract EbeanServer createServer(String paramString);
  
  public abstract void shutdown();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\common\BootupEbeanManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */