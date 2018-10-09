package org.apache.logging.log4j.core.jmx;

public abstract interface AppenderAdminMBean
{
  public static final String PATTERN = "org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=Appender,name=%s";
  
  public abstract String getName();
  
  public abstract String getLayout();
  
  public abstract boolean isExceptionSuppressed();
  
  public abstract String getErrorHandler();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\AppenderAdminMBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */