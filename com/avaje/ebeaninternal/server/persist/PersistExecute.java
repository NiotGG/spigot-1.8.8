package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;

public abstract interface PersistExecute
{
  public abstract BatchControl createBatchControl(SpiTransaction paramSpiTransaction);
  
  public abstract <T> void executeInsertBean(PersistRequestBean<T> paramPersistRequestBean);
  
  public abstract <T> void executeUpdateBean(PersistRequestBean<T> paramPersistRequestBean);
  
  public abstract <T> void executeDeleteBean(PersistRequestBean<T> paramPersistRequestBean);
  
  public abstract int executeOrmUpdate(PersistRequestOrmUpdate paramPersistRequestOrmUpdate);
  
  public abstract int executeSqlCallable(PersistRequestCallableSql paramPersistRequestCallableSql);
  
  public abstract int executeSqlUpdate(PersistRequestUpdateSql paramPersistRequestUpdateSql);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\PersistExecute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */