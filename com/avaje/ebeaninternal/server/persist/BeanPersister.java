package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import javax.persistence.PersistenceException;

public abstract interface BeanPersister
{
  public abstract void insert(PersistRequestBean<?> paramPersistRequestBean)
    throws PersistenceException;
  
  public abstract void update(PersistRequestBean<?> paramPersistRequestBean)
    throws PersistenceException;
  
  public abstract void delete(PersistRequestBean<?> paramPersistRequestBean)
    throws PersistenceException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BeanPersister.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */