package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinTable
{
  String name() default "";
  
  String catalog() default "";
  
  String schema() default "";
  
  JoinColumn[] joinColumns() default {};
  
  JoinColumn[] inverseJoinColumns() default {};
  
  UniqueConstraint[] uniqueConstraints() default {};
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\javax\persistence\JoinTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */