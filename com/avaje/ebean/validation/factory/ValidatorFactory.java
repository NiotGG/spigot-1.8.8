package com.avaje.ebean.validation.factory;

import java.lang.annotation.Annotation;

public abstract interface ValidatorFactory
{
  public abstract Validator create(Annotation paramAnnotation, Class<?> paramClass);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\ValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */