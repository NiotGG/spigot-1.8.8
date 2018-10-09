package com.avaje.ebeaninternal.server.cluster.mcast;

import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import java.io.IOException;

public abstract interface Message
{
  public abstract void writeBinaryMessage(BinaryMessageList paramBinaryMessageList)
    throws IOException;
  
  public abstract boolean isControlMessage();
  
  public abstract String getToHostPort();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\mcast\Message.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */