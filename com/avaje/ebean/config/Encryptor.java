package com.avaje.ebean.config;

public abstract interface Encryptor
{
  public abstract byte[] encrypt(byte[] paramArrayOfByte, EncryptKey paramEncryptKey);
  
  public abstract byte[] decrypt(byte[] paramArrayOfByte, EncryptKey paramEncryptKey);
  
  public abstract byte[] encryptString(String paramString, EncryptKey paramEncryptKey);
  
  public abstract String decryptString(byte[] paramArrayOfByte, EncryptKey paramEncryptKey);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\Encryptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */