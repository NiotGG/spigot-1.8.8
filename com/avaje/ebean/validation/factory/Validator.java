package com.avaje.ebean.validation.factory;

public abstract interface Validator
{
  public abstract String getKey();
  
  public abstract Object[] getAttributes();
  
  public abstract boolean isValid(Object paramObject);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\Validator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */