package org.bukkit.craftbukkit.libs.joptsimple;

public abstract interface ValueConverter<V>
{
  public abstract V convert(String paramString);
  
  public abstract Class<V> valueType();
  
  public abstract String valuePattern();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\ValueConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */