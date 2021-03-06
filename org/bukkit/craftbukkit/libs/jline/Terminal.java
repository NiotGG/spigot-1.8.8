package org.bukkit.craftbukkit.libs.jline;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract interface Terminal
{
  public abstract void init()
    throws Exception;
  
  public abstract void restore()
    throws Exception;
  
  public abstract void reset()
    throws Exception;
  
  public abstract boolean isSupported();
  
  public abstract int getWidth();
  
  public abstract int getHeight();
  
  public abstract boolean isAnsiSupported();
  
  public abstract OutputStream wrapOutIfNeeded(OutputStream paramOutputStream);
  
  public abstract InputStream wrapInIfNeeded(InputStream paramInputStream)
    throws IOException;
  
  public abstract boolean hasWeirdWrap();
  
  public abstract boolean isEchoEnabled();
  
  public abstract void setEchoEnabled(boolean paramBoolean);
  
  public abstract String getOutputEncoding();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\Terminal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */