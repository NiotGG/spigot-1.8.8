package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
import java.sql.SQLException;
import java.util.List;

public abstract interface Bindable
{
  public abstract void addChanged(PersistRequestBean<?> paramPersistRequestBean, List<Bindable> paramList);
  
  public abstract void dmlInsert(GenerateDmlRequest paramGenerateDmlRequest, boolean paramBoolean);
  
  public abstract void dmlAppend(GenerateDmlRequest paramGenerateDmlRequest, boolean paramBoolean);
  
  public abstract void dmlWhere(GenerateDmlRequest paramGenerateDmlRequest, boolean paramBoolean, Object paramObject);
  
  public abstract void dmlBind(BindableRequest paramBindableRequest, boolean paramBoolean, Object paramObject)
    throws SQLException;
  
  public abstract void dmlBindWhere(BindableRequest paramBindableRequest, boolean paramBoolean, Object paramObject)
    throws SQLException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\Bindable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */