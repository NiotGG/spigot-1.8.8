package com.avaje.ebean.validation;

import com.avaje.ebean.validation.factory.LengthValidatorFactory;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatorMeta(factory=LengthValidatorFactory.class)
@Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Length
{
  int min() default 0;
  
  int max() default Integer.MAX_VALUE;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\Length.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */