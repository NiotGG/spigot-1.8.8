package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
@Beta
public abstract interface MapConstraint<K, V>
{
  public abstract void checkKeyValue(@Nullable K paramK, @Nullable V paramV);
  
  public abstract String toString();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\MapConstraint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */