package com.avaje.ebean.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface SqlSelect
{
  String name() default "default";
  
  String tableAlias() default "";
  
  String query() default "";
  
  String extend() default "";
  
  String where() default "";
  
  String having() default "";
  
  String columnMapping() default "";
  
  boolean debug() default false;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\annotation\SqlSelect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */