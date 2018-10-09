package com.avaje.ebean;

public abstract interface AdminAutofetch
{
  public abstract boolean isProfiling();
  
  public abstract void setProfiling(boolean paramBoolean);
  
  public abstract boolean isQueryTuning();
  
  public abstract void setQueryTuning(boolean paramBoolean);
  
  public abstract double getProfilingRate();
  
  public abstract void setProfilingRate(double paramDouble);
  
  public abstract int getProfilingBase();
  
  public abstract void setProfilingBase(int paramInt);
  
  public abstract int getProfilingMin();
  
  public abstract void setProfilingMin(int paramInt);
  
  public abstract String collectUsageViaGC();
  
  public abstract String updateTunedQueryInfo();
  
  public abstract int clearTunedQueryInfo();
  
  public abstract int clearProfilingInfo();
  
  public abstract void clearQueryStatistics();
  
  public abstract int getTotalTunedQueryCount();
  
  public abstract int getTotalTunedQuerySize();
  
  public abstract int getTotalProfileSize();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\AdminAutofetch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */