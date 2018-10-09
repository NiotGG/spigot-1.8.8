package com.avaje.ebeaninternal.api;

import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.ArrayList;

public abstract interface SpiExpressionRequest
{
  public abstract String parseDeploy(String paramString);
  
  public abstract BeanDescriptor<?> getBeanDescriptor();
  
  public abstract SpiOrmQueryRequest<?> getQueryRequest();
  
  public abstract SpiExpressionRequest append(String paramString);
  
  public abstract void addBindValue(Object paramObject);
  
  public abstract String getSql();
  
  public abstract ArrayList<Object> getBindValues();
  
  public abstract int nextParameter();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiExpressionRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */