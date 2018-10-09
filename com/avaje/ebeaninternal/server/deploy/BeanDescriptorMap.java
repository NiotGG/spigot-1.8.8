package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;

public abstract interface BeanDescriptorMap
{
  public abstract String getServerName();
  
  public abstract ServerCacheManager getCacheManager();
  
  public abstract <T> BeanDescriptor<T> getBeanDescriptor(Class<T> paramClass);
  
  public abstract EncryptKey getEncryptKey(String paramString1, String paramString2);
  
  public abstract IdBinder createIdBinder(BeanProperty[] paramArrayOfBeanProperty);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanDescriptorMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */