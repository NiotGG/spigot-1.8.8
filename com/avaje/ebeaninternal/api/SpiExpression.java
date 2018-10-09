package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Expression;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public abstract interface SpiExpression
  extends Expression
{
  public abstract void containsMany(BeanDescriptor<?> paramBeanDescriptor, ManyWhereJoins paramManyWhereJoins);
  
  public abstract int queryAutoFetchHash();
  
  public abstract int queryPlanHash(BeanQueryRequest<?> paramBeanQueryRequest);
  
  public abstract int queryBindHash();
  
  public abstract void addSql(SpiExpressionRequest paramSpiExpressionRequest);
  
  public abstract void addBindValues(SpiExpressionRequest paramSpiExpressionRequest);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */