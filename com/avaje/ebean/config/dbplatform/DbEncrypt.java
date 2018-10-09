package com.avaje.ebean.config.dbplatform;

public abstract interface DbEncrypt
{
  public abstract DbEncryptFunction getDbEncryptFunction(int paramInt);
  
  public abstract int getEncryptDbType();
  
  public abstract boolean isBindEncryptDataFirst();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\DbEncrypt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */