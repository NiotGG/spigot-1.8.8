package org.bukkit.craftbukkit.libs.jline.console.history;

import java.util.Iterator;
import java.util.ListIterator;

public abstract interface History
  extends Iterable<Entry>
{
  public abstract int size();
  
  public abstract boolean isEmpty();
  
  public abstract int index();
  
  public abstract void clear();
  
  public abstract CharSequence get(int paramInt);
  
  public abstract void add(CharSequence paramCharSequence);
  
  public abstract void set(int paramInt, CharSequence paramCharSequence);
  
  public abstract CharSequence remove(int paramInt);
  
  public abstract CharSequence removeFirst();
  
  public abstract CharSequence removeLast();
  
  public abstract void replace(CharSequence paramCharSequence);
  
  public abstract ListIterator<Entry> entries(int paramInt);
  
  public abstract ListIterator<Entry> entries();
  
  public abstract Iterator<Entry> iterator();
  
  public abstract CharSequence current();
  
  public abstract boolean previous();
  
  public abstract boolean next();
  
  public abstract boolean moveToFirst();
  
  public abstract boolean moveToLast();
  
  public abstract boolean moveTo(int paramInt);
  
  public abstract void moveToEnd();
  
  public static abstract interface Entry
  {
    public abstract int index();
    
    public abstract CharSequence value();
  }
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\history\History.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */