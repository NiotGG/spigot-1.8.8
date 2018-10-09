package org.apache.commons.lang.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract interface Nestable
{
  public abstract Throwable getCause();
  
  public abstract String getMessage();
  
  public abstract String getMessage(int paramInt);
  
  public abstract String[] getMessages();
  
  public abstract Throwable getThrowable(int paramInt);
  
  public abstract int getThrowableCount();
  
  public abstract Throwable[] getThrowables();
  
  public abstract int indexOfThrowable(Class paramClass);
  
  public abstract int indexOfThrowable(Class paramClass, int paramInt);
  
  public abstract void printStackTrace(PrintWriter paramPrintWriter);
  
  public abstract void printStackTrace(PrintStream paramPrintStream);
  
  public abstract void printPartialStackTrace(PrintWriter paramPrintWriter);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang\exception\Nestable.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */