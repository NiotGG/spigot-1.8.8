package com.google.common.io;

import java.io.DataOutput;

public abstract interface ByteArrayDataOutput
  extends DataOutput
{
  public abstract void write(int paramInt);
  
  public abstract void write(byte[] paramArrayOfByte);
  
  public abstract void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract void writeBoolean(boolean paramBoolean);
  
  public abstract void writeByte(int paramInt);
  
  public abstract void writeShort(int paramInt);
  
  public abstract void writeChar(int paramInt);
  
  public abstract void writeInt(int paramInt);
  
  public abstract void writeLong(long paramLong);
  
  public abstract void writeFloat(float paramFloat);
  
  public abstract void writeDouble(double paramDouble);
  
  public abstract void writeChars(String paramString);
  
  public abstract void writeUTF(String paramString);
  
  @Deprecated
  public abstract void writeBytes(String paramString);
  
  public abstract byte[] toByteArray();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\io\ByteArrayDataOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */