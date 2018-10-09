package org.apache.logging.log4j.core.pattern;

public abstract interface PatternConverter
{
  public abstract void format(Object paramObject, StringBuilder paramStringBuilder);
  
  public abstract String getName();
  
  public abstract String getStyleClass(Object paramObject);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\PatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */