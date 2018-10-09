package org.bukkit.configuration.serialization;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface DelegateDeserialization
{
  Class<? extends ConfigurationSerializable> value();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\serialization\DelegateDeserialization.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */