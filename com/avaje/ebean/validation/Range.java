package com.avaje.ebean.validation;

import com.avaje.ebean.validation.factory.RangeValidatorFactory;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatorMeta(factory=RangeValidatorFactory.class)
@Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range
{
  long min() default Long.MIN_VALUE;
  
  long max() default Long.MAX_VALUE;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\Range.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */