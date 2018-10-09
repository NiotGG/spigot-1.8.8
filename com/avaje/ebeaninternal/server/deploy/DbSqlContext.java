package com.avaje.ebeaninternal.server.deploy;

public abstract interface DbSqlContext
{
  public abstract void addJoin(String paramString1, String paramString2, TableJoinColumn[] paramArrayOfTableJoinColumn, String paramString3, String paramString4);
  
  public abstract void pushSecondaryTableAlias(String paramString);
  
  public abstract void pushTableAlias(String paramString);
  
  public abstract void popTableAlias();
  
  public abstract void addEncryptedProp(BeanProperty paramBeanProperty);
  
  public abstract BeanProperty[] getEncryptedProps();
  
  public abstract DbSqlContext append(char paramChar);
  
  public abstract DbSqlContext append(String paramString);
  
  public abstract String peekTableAlias();
  
  public abstract void appendRawColumn(String paramString);
  
  public abstract void appendColumn(String paramString1, String paramString2);
  
  public abstract void appendColumn(String paramString);
  
  public abstract void appendFormulaSelect(String paramString);
  
  public abstract void appendFormulaJoin(String paramString, boolean paramBoolean);
  
  public abstract int length();
  
  public abstract String getContent();
  
  public abstract String peekJoin();
  
  public abstract void pushJoin(String paramString);
  
  public abstract void popJoin();
  
  public abstract String getTableAlias(String paramString);
  
  public abstract String getTableAliasManyWhere(String paramString);
  
  public abstract String getRelativePrefix(String paramString);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\DbSqlContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */