package com.avaje.ebean.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LdapAttribute
{
  String name() default "";
  
  Class<?> adapter() default void.class;
  
  boolean insertable() default true;
  
  boolean updatable() default true;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\annotation\LdapAttribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */