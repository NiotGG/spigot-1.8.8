package com.avaje.ebean.config;

public abstract interface EncryptKeyManager
{
  public abstract void initialise();
  
  public abstract EncryptKey getEncryptKey(String paramString1, String paramString2);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\EncryptKeyManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */