package com.avaje.ebean;

public abstract interface ExampleExpression
  extends Expression
{
  public abstract ExampleExpression includeZeros();
  
  public abstract ExampleExpression caseInsensitive();
  
  public abstract ExampleExpression useStartsWith();
  
  public abstract ExampleExpression useContains();
  
  public abstract ExampleExpression useEndsWith();
  
  public abstract ExampleExpression useEqualTo();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\ExampleExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */