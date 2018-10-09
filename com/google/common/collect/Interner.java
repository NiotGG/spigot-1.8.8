package com.google.common.collect;

import com.google.common.annotations.Beta;

@Beta
public abstract interface Interner<E>
{
  public abstract E intern(E paramE);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\Interner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */