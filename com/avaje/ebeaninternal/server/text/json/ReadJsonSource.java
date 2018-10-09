package com.avaje.ebeaninternal.server.text.json;

public abstract interface ReadJsonSource
{
  public abstract char nextChar(String paramString);
  
  public abstract void ignoreWhiteSpace();
  
  public abstract void back();
  
  public abstract int pos();
  
  public abstract String getErrorHelp();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\ReadJsonSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */