package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.api.BeanIdList;

public abstract interface OrmQueryEngine
{
  public abstract <T> T findId(OrmQueryRequest<T> paramOrmQueryRequest);
  
  public abstract <T> BeanCollection<T> findMany(OrmQueryRequest<T> paramOrmQueryRequest);
  
  public abstract <T> QueryIterator<T> findIterate(OrmQueryRequest<T> paramOrmQueryRequest);
  
  public abstract <T> int findRowCount(OrmQueryRequest<T> paramOrmQueryRequest);
  
  public abstract <T> BeanIdList findIds(OrmQueryRequest<T> paramOrmQueryRequest);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\OrmQueryEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */