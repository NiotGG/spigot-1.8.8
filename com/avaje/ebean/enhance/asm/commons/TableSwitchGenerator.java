package com.avaje.ebean.enhance.asm.commons;

import com.avaje.ebean.enhance.asm.Label;

public abstract interface TableSwitchGenerator
{
  public abstract void generateCase(int paramInt, Label paramLabel);
  
  public abstract void generateDefault();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\asm\commons\TableSwitchGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */