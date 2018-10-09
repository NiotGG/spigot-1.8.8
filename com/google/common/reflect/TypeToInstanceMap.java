package com.google.common.reflect;

import com.google.common.annotations.Beta;
import java.util.Map;
import javax.annotation.Nullable;

@Beta
public abstract interface TypeToInstanceMap<B>
  extends Map<TypeToken<? extends B>, B>
{
  @Nullable
  public abstract <T extends B> T getInstance(Class<T> paramClass);
  
  @Nullable
  public abstract <T extends B> T putInstance(Class<T> paramClass, @Nullable T paramT);
  
  @Nullable
  public abstract <T extends B> T getInstance(TypeToken<T> paramTypeToken);
  
  @Nullable
  public abstract <T extends B> T putInstance(TypeToken<T> paramTypeToken, @Nullable T paramT);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\reflect\TypeToInstanceMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */