package org.apache.logging.log4j.core.jmx;

public abstract interface ContextSelectorAdminMBean
{
  public static final String NAME = "org.apache.logging.log4j2:type=ContextSelector";
  
  public abstract String getImplementationClassName();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\ContextSelectorAdminMBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */