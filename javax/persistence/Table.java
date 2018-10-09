package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table
{
  String name() default "";
  
  String catalog() default "";
  
  String schema() default "";
  
  UniqueConstraint[] uniqueConstraints() default {};
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\javax\persistence\Table.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */