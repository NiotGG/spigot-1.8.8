package com.avaje.ebean.config;

public abstract interface ScalarTypeConverter<B, S>
{
  public abstract B getNullValue();
  
  public abstract B wrapValue(S paramS);
  
  public abstract S unwrapValue(B paramB);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\ScalarTypeConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */