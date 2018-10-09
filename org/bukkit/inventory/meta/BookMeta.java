package org.bukkit.inventory.meta;

import java.util.List;

public abstract interface BookMeta
  extends ItemMeta
{
  public abstract boolean hasTitle();
  
  public abstract String getTitle();
  
  public abstract boolean setTitle(String paramString);
  
  public abstract boolean hasAuthor();
  
  public abstract String getAuthor();
  
  public abstract void setAuthor(String paramString);
  
  public abstract boolean hasPages();
  
  public abstract String getPage(int paramInt);
  
  public abstract void setPage(int paramInt, String paramString);
  
  public abstract List<String> getPages();
  
  public abstract void setPages(List<String> paramList);
  
  public abstract void setPages(String... paramVarArgs);
  
  public abstract void addPage(String... paramVarArgs);
  
  public abstract int getPageCount();
  
  public abstract BookMeta clone();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\BookMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */