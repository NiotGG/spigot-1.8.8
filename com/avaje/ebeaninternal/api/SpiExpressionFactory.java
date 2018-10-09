package com.avaje.ebeaninternal.api;

import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;

public abstract interface SpiExpressionFactory
  extends ExpressionFactory
{
  public abstract ExpressionFactory createExpressionFactory(FilterExprPath paramFilterExprPath);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiExpressionFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */