package com.avaje.ebeaninternal.server.lib.resource;

import java.io.IOException;

public abstract interface ResourceSource
{
  public abstract String getRealPath();
  
  public abstract ResourceContent getContent(String paramString);
  
  public abstract String readString(ResourceContent paramResourceContent, int paramInt)
    throws IOException;
  
  public abstract byte[] readBytes(ResourceContent paramResourceContent, int paramInt)
    throws IOException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\resource\ResourceSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */