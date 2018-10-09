/*     */ package io.netty.buffer;
/*     */ 
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ public class ByteBufOutputStream
/*     */   extends OutputStream
/*     */   implements DataOutput
/*     */ {
/*     */   private final ByteBuf buffer;
/*     */   private final int startIndex;
/*  40 */   private final DataOutputStream utf8out = new DataOutputStream(this);
/*     */   
/*     */ 
/*     */ 
/*     */   public ByteBufOutputStream(ByteBuf paramByteBuf)
/*     */   {
/*  46 */     if (paramByteBuf == null) {
/*  47 */       throw new NullPointerException("buffer");
/*     */     }
/*  49 */     this.buffer = paramByteBuf;
/*  50 */     this.startIndex = paramByteBuf.writerIndex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int writtenBytes()
/*     */   {
/*  57 */     return this.buffer.writerIndex() - this.startIndex;
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */   {
/*  62 */     if (paramInt2 == 0) {
/*  63 */       return;
/*     */     }
/*     */     
/*  66 */     this.buffer.writeBytes(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfByte) throws IOException
/*     */   {
/*  71 */     this.buffer.writeBytes(paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public void write(int paramInt) throws IOException
/*     */   {
/*  76 */     this.buffer.writeByte((byte)paramInt);
/*     */   }
/*     */   
/*     */   public void writeBoolean(boolean paramBoolean) throws IOException
/*     */   {
/*  81 */     write(paramBoolean ? 1 : 0);
/*     */   }
/*     */   
/*     */   public void writeByte(int paramInt) throws IOException
/*     */   {
/*  86 */     write(paramInt);
/*     */   }
/*     */   
/*     */   public void writeBytes(String paramString) throws IOException
/*     */   {
/*  91 */     int i = paramString.length();
/*  92 */     for (int j = 0; j < i; j++) {
/*  93 */       write((byte)paramString.charAt(j));
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeChar(int paramInt) throws IOException
/*     */   {
/*  99 */     writeShort((short)paramInt);
/*     */   }
/*     */   
/*     */   public void writeChars(String paramString) throws IOException
/*     */   {
/* 104 */     int i = paramString.length();
/* 105 */     for (int j = 0; j < i; j++) {
/* 106 */       writeChar(paramString.charAt(j));
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeDouble(double paramDouble) throws IOException
/*     */   {
/* 112 */     writeLong(Double.doubleToLongBits(paramDouble));
/*     */   }
/*     */   
/*     */   public void writeFloat(float paramFloat) throws IOException
/*     */   {
/* 117 */     writeInt(Float.floatToIntBits(paramFloat));
/*     */   }
/*     */   
/*     */   public void writeInt(int paramInt) throws IOException
/*     */   {
/* 122 */     this.buffer.writeInt(paramInt);
/*     */   }
/*     */   
/*     */   public void writeLong(long paramLong) throws IOException
/*     */   {
/* 127 */     this.buffer.writeLong(paramLong);
/*     */   }
/*     */   
/*     */   public void writeShort(int paramInt) throws IOException
/*     */   {
/* 132 */     this.buffer.writeShort((short)paramInt);
/*     */   }
/*     */   
/*     */   public void writeUTF(String paramString) throws IOException
/*     */   {
/* 137 */     this.utf8out.writeUTF(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ByteBuf buffer()
/*     */   {
/* 144 */     return this.buffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\ByteBufOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */