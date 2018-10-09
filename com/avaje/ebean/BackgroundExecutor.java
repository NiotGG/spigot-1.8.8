package com.avaje.ebean;

import java.util.concurrent.TimeUnit;

public abstract interface BackgroundExecutor
{
  public abstract void execute(Runnable paramRunnable);
  
  public abstract void executePeriodically(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\BackgroundExecutor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */