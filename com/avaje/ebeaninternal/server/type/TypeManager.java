package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.reflect.CheckImmutableResponse;

public abstract interface TypeManager
{
  public abstract CheckImmutableResponse checkImmutable(Class<?> paramClass);
  
  public abstract ScalarDataReader<?> recursiveCreateScalarDataReader(Class<?> paramClass);
  
  public abstract ScalarType<?> recursiveCreateScalarTypes(Class<?> paramClass);
  
  public abstract void add(ScalarType<?> paramScalarType);
  
  public abstract CtCompoundType<?> getCompoundType(Class<?> paramClass);
  
  public abstract ScalarType<?> getScalarType(int paramInt);
  
  public abstract <T> ScalarType<T> getScalarType(Class<T> paramClass);
  
  public abstract <T> ScalarType<T> getScalarType(Class<T> paramClass, int paramInt);
  
  public abstract ScalarType<?> createEnumScalarType(Class<?> paramClass);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\TypeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */