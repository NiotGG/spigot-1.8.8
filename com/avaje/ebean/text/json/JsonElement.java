package com.avaje.ebean.text.json;

public abstract interface JsonElement
{
  public abstract boolean isPrimitive();
  
  public abstract String toPrimitiveString();
  
  public abstract Object eval(String paramString);
  
  public abstract int evalInt(String paramString);
  
  public abstract String evalString(String paramString);
  
  public abstract boolean evalBoolean(String paramString);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */