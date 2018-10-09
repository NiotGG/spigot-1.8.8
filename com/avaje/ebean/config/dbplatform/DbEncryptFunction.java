package com.avaje.ebean.config.dbplatform;

public abstract interface DbEncryptFunction
{
  public abstract String getDecryptSql(String paramString);
  
  public abstract String getEncryptBindSql();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\DbEncryptFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */