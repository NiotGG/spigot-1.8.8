package org.apache.commons.io.input;

public abstract interface TailerListener
{
  public abstract void init(Tailer paramTailer);
  
  public abstract void fileNotFound();
  
  public abstract void fileRotated();
  
  public abstract void handle(String paramString);
  
  public abstract void handle(Exception paramException);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\TailerListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */