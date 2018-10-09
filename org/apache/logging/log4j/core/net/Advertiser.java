package org.apache.logging.log4j.core.net;

import java.util.Map;

public abstract interface Advertiser
{
  public abstract Object advertise(Map<String, String> paramMap);
  
  public abstract void unadvertise(Object paramObject);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\Advertiser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */