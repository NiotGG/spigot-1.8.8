package org.apache.logging.log4j.message;

abstract interface ThreadInformation
{
  public abstract void printThreadInfo(StringBuilder paramStringBuilder);
  
  public abstract void printStack(StringBuilder paramStringBuilder, StackTraceElement[] paramArrayOfStackTraceElement);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\ThreadInformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */