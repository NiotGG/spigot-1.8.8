package com.avaje.ebeaninternal.server.type;

import java.sql.SQLException;

public abstract interface ScalarDataReader<T>
{
  public abstract T read(DataReader paramDataReader)
    throws SQLException;
  
  public abstract void loadIgnore(DataReader paramDataReader);
  
  public abstract void bind(DataBind paramDataBind, T paramT)
    throws SQLException;
  
  public abstract void accumulateScalarTypes(String paramString, CtCompoundTypeScalarList paramCtCompoundTypeScalarList);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarDataReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */