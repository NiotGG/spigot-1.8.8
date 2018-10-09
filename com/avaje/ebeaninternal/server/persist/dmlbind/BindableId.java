package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;

public abstract interface BindableId
  extends Bindable
{
  public abstract boolean isEmpty();
  
  public abstract boolean isConcatenated();
  
  public abstract String getIdentityColumn();
  
  public abstract boolean deriveConcatenatedId(PersistRequestBean<?> paramPersistRequestBean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableId.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */