package com.avaje.ebean.text.json;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

public abstract interface JsonContext
{
  public abstract <T> T toBean(Class<T> paramClass, String paramString);
  
  public abstract <T> T toBean(Class<T> paramClass, Reader paramReader);
  
  public abstract <T> T toBean(Class<T> paramClass, String paramString, JsonReadOptions paramJsonReadOptions);
  
  public abstract <T> T toBean(Class<T> paramClass, Reader paramReader, JsonReadOptions paramJsonReadOptions);
  
  public abstract <T> List<T> toList(Class<T> paramClass, String paramString);
  
  public abstract <T> List<T> toList(Class<T> paramClass, String paramString, JsonReadOptions paramJsonReadOptions);
  
  public abstract <T> List<T> toList(Class<T> paramClass, Reader paramReader);
  
  public abstract <T> List<T> toList(Class<T> paramClass, Reader paramReader, JsonReadOptions paramJsonReadOptions);
  
  public abstract Object toObject(Type paramType, Reader paramReader, JsonReadOptions paramJsonReadOptions);
  
  public abstract Object toObject(Type paramType, String paramString, JsonReadOptions paramJsonReadOptions);
  
  public abstract void toJsonWriter(Object paramObject, Writer paramWriter);
  
  public abstract void toJsonWriter(Object paramObject, Writer paramWriter, boolean paramBoolean);
  
  public abstract void toJsonWriter(Object paramObject, Writer paramWriter, boolean paramBoolean, JsonWriteOptions paramJsonWriteOptions);
  
  public abstract void toJsonWriter(Object paramObject, Writer paramWriter, boolean paramBoolean, JsonWriteOptions paramJsonWriteOptions, String paramString);
  
  public abstract String toJsonString(Object paramObject);
  
  public abstract String toJsonString(Object paramObject, boolean paramBoolean);
  
  public abstract String toJsonString(Object paramObject, boolean paramBoolean, JsonWriteOptions paramJsonWriteOptions);
  
  public abstract String toJsonString(Object paramObject, boolean paramBoolean, JsonWriteOptions paramJsonWriteOptions, String paramString);
  
  public abstract boolean isSupportedType(Type paramType);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */