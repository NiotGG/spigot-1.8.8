package com.avaje.ebeaninternal.server.lib.resource;

import java.io.IOException;
import java.io.InputStream;

public abstract interface ResourceContent
{
  public abstract String getName();
  
  public abstract long size();
  
  public abstract long lastModified();
  
  public abstract InputStream getInputStream()
    throws IOException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\resource\ResourceContent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */