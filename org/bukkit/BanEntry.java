package org.bukkit;

import java.util.Date;

public abstract interface BanEntry
{
  public abstract String getTarget();
  
  public abstract Date getCreated();
  
  public abstract void setCreated(Date paramDate);
  
  public abstract String getSource();
  
  public abstract void setSource(String paramString);
  
  public abstract Date getExpiration();
  
  public abstract void setExpiration(Date paramDate);
  
  public abstract String getReason();
  
  public abstract void setReason(String paramString);
  
  public abstract void save();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\BanEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */