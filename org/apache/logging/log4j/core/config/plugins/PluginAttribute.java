package org.apache.logging.log4j.core.config.plugins;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.PARAMETER})
public @interface PluginAttribute
{
  String value();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\plugins\PluginAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */