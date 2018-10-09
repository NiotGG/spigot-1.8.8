package com.mysql.jdbc;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

public abstract interface SocketFactory
{
  public abstract Socket afterHandshake()
    throws SocketException, IOException;
  
  public abstract Socket beforeHandshake()
    throws SocketException, IOException;
  
  public abstract Socket connect(String paramString, int paramInt, Properties paramProperties)
    throws SocketException, IOException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\SocketFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */