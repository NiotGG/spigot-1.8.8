package org.apache.logging.log4j.core.jmx;

public abstract interface LoggerConfigAdminMBean
{
  public static final String PATTERN = "org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=LoggerConfig,name=%s";
  
  public abstract String getName();
  
  public abstract String getLevel();
  
  public abstract void setLevel(String paramString);
  
  public abstract boolean isAdditive();
  
  public abstract void setAdditive(boolean paramBoolean);
  
  public abstract boolean isIncludeLocation();
  
  public abstract String getFilter();
  
  public abstract String[] getAppenderRefs();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\LoggerConfigAdminMBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */