package com.avaje.ebean.text.json;

import java.util.Map;

public abstract interface JsonReadBeanVisitor<T>
{
  public abstract void visit(T paramT, Map<String, JsonElement> paramMap);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonReadBeanVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */