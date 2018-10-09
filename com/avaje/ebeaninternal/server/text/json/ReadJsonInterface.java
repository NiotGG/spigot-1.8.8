package com.avaje.ebeaninternal.server.text.json;

public abstract interface ReadJsonInterface
{
  public abstract void ignoreWhiteSpace();
  
  public abstract char nextChar();
  
  public abstract String getTokenKey();
  
  public abstract boolean readKeyNext();
  
  public abstract boolean readValueNext();
  
  public abstract boolean readArrayNext();
  
  public abstract String readQuotedValue();
  
  public abstract String readUnquotedValue(char paramChar);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\ReadJsonInterface.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */