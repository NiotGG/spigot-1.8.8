/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.commons.io.EndianUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SwappedDataInputStream
/*     */   extends ProxyInputStream
/*     */   implements DataInput
/*     */ {
/*     */   public SwappedDataInputStream(InputStream paramInputStream)
/*     */   {
/*  46 */     super(paramInputStream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean readBoolean()
/*     */     throws IOException, EOFException
/*     */   {
/*  58 */     return 0 != readByte();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte readByte()
/*     */     throws IOException, EOFException
/*     */   {
/*  70 */     return (byte)this.in.read();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char readChar()
/*     */     throws IOException, EOFException
/*     */   {
/*  82 */     return (char)readShort();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double readDouble()
/*     */     throws IOException, EOFException
/*     */   {
/*  94 */     return EndianUtils.readSwappedDouble(this.in);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float readFloat()
/*     */     throws IOException, EOFException
/*     */   {
/* 106 */     return EndianUtils.readSwappedFloat(this.in);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readFully(byte[] paramArrayOfByte)
/*     */     throws IOException, EOFException
/*     */   {
/* 119 */     readFully(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException, EOFException
/*     */   {
/* 135 */     int i = paramInt2;
/*     */     
/* 137 */     while (i > 0)
/*     */     {
/* 139 */       int j = paramInt1 + paramInt2 - i;
/* 140 */       int k = read(paramArrayOfByte, j, i);
/*     */       
/* 142 */       if (-1 == k)
/*     */       {
/* 144 */         throw new EOFException();
/*     */       }
/*     */       
/* 147 */       i -= k;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int readInt()
/*     */     throws IOException, EOFException
/*     */   {
/* 160 */     return EndianUtils.readSwappedInteger(this.in);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String readLine()
/*     */     throws IOException, EOFException
/*     */   {
/* 172 */     throw new UnsupportedOperationException("Operation not supported: readLine()");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long readLong()
/*     */     throws IOException, EOFException
/*     */   {
/* 185 */     return EndianUtils.readSwappedLong(this.in);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short readShort()
/*     */     throws IOException, EOFException
/*     */   {
/* 197 */     return EndianUtils.readSwappedShort(this.in);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int readUnsignedByte()
/*     */     throws IOException, EOFException
/*     */   {
/* 209 */     return this.in.read();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int readUnsignedShort()
/*     */     throws IOException, EOFException
/*     */   {
/* 221 */     return EndianUtils.readSwappedUnsignedShort(this.in);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String readUTF()
/*     */     throws IOException, EOFException
/*     */   {
/* 233 */     throw new UnsupportedOperationException("Operation not supported: readUTF()");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int skipBytes(int paramInt)
/*     */     throws IOException, EOFException
/*     */   {
/* 247 */     return (int)this.in.skip(paramInt);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\SwappedDataInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */