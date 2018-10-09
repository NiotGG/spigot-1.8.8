package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKeyJoinColumn
{
  String name() default "";
  
  String referencedColumnName() default "";
  
  String columnDefinition() default "";
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\javax\persistence\PrimaryKeyJoinColumn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */