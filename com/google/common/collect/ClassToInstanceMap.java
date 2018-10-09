package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public abstract interface ClassToInstanceMap<B>
  extends Map<Class<? extends B>, B>
{
  public abstract <T extends B> T getInstance(Class<T> paramClass);
  
  public abstract <T extends B> T putInstance(Class<T> paramClass, @Nullable T paramT);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ClassToInstanceMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */