package org.apache.logging.log4j.core.pattern;

public abstract interface ArrayPatternConverter
  extends PatternConverter
{
  public abstract void format(StringBuilder paramStringBuilder, Object... paramVarArgs);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\ArrayPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */