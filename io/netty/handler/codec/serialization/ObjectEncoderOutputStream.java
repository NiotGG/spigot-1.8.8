/*     */ package io.netty.handler.codec.serialization;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufOutputStream;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.ObjectOutputStream;
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
/*     */ public class ObjectEncoderOutputStream
/*     */   extends OutputStream
/*     */   implements ObjectOutput
/*     */ {
/*     */   private final DataOutputStream out;
/*     */   private final int estimatedLength;
/*     */   
/*     */   public ObjectEncoderOutputStream(OutputStream paramOutputStream)
/*     */   {
/*  47 */     this(paramOutputStream, 512);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectEncoderOutputStream(OutputStream paramOutputStream, int paramInt)
/*     */   {
/*  66 */     if (paramOutputStream == null) {
/*  67 */       throw new NullPointerException("out");
/*     */     }
/*  69 */     if (paramInt < 0) {
/*  70 */       throw new IllegalArgumentException("estimatedLength: " + paramInt);
/*     */     }
/*     */     
/*  73 */     if ((paramOutputStream instanceof DataOutputStream)) {
/*  74 */       this.out = ((DataOutputStream)paramOutputStream);
/*     */     } else {
/*  76 */       this.out = new DataOutputStream(paramOutputStream);
/*     */     }
/*  78 */     this.estimatedLength = paramInt;
/*     */   }
/*     */   
/*     */   public void writeObject(Object paramObject) throws IOException
/*     */   {
/*  83 */     ByteBufOutputStream localByteBufOutputStream = new ByteBufOutputStream(Unpooled.buffer(this.estimatedLength));
/*  84 */     CompactObjectOutputStream localCompactObjectOutputStream = new CompactObjectOutputStream(localByteBufOutputStream);
/*  85 */     localCompactObjectOutputStream.writeObject(paramObject);
/*  86 */     localCompactObjectOutputStream.flush();
/*  87 */     localCompactObjectOutputStream.close();
/*     */     
/*  89 */     ByteBuf localByteBuf = localByteBufOutputStream.buffer();
/*  90 */     int i = localByteBuf.readableBytes();
/*  91 */     writeInt(i);
/*  92 */     localByteBuf.getBytes(0, this, i);
/*     */   }
/*     */   
/*     */   public void write(int paramInt) throws IOException
/*     */   {
/*  97 */     this.out.write(paramInt);
/*     */   }
/*     */   
/*     */   public void close() throws IOException
/*     */   {
/* 102 */     this.out.close();
/*     */   }
/*     */   
/*     */   public void flush() throws IOException
/*     */   {
/* 107 */     this.out.flush();
/*     */   }
/*     */   
/*     */   public final int size() {
/* 111 */     return this.out.size();
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */   {
/* 116 */     this.out.write(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfByte) throws IOException
/*     */   {
/* 121 */     this.out.write(paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public final void writeBoolean(boolean paramBoolean) throws IOException
/*     */   {
/* 126 */     this.out.writeBoolean(paramBoolean);
/*     */   }
/*     */   
/*     */   public final void writeByte(int paramInt) throws IOException
/*     */   {
/* 131 */     this.out.writeByte(paramInt);
/*     */   }
/*     */   
/*     */   public final void writeBytes(String paramString) throws IOException
/*     */   {
/* 136 */     this.out.writeBytes(paramString);
/*     */   }
/*     */   
/*     */   public final void writeChar(int paramInt) throws IOException
/*     */   {
/* 141 */     this.out.writeChar(paramInt);
/*     */   }
/*     */   
/*     */   public final void writeChars(String paramString) throws IOException
/*     */   {
/* 146 */     this.out.writeChars(paramString);
/*     */   }
/*     */   
/*     */   public final void writeDouble(double paramDouble) throws IOException
/*     */   {
/* 151 */     this.out.writeDouble(paramDouble);
/*     */   }
/*     */   
/*     */   public final void writeFloat(float paramFloat) throws IOException
/*     */   {
/* 156 */     this.out.writeFloat(paramFloat);
/*     */   }
/*     */   
/*     */   public final void writeInt(int paramInt) throws IOException
/*     */   {
/* 161 */     this.out.writeInt(paramInt);
/*     */   }
/*     */   
/*     */   public final void writeLong(long paramLong) throws IOException
/*     */   {
/* 166 */     this.out.writeLong(paramLong);
/*     */   }
/*     */   
/*     */   public final void writeShort(int paramInt) throws IOException
/*     */   {
/* 171 */     this.out.writeShort(paramInt);
/*     */   }
/*     */   
/*     */   public final void writeUTF(String paramString) throws IOException
/*     */   {
/* 176 */     this.out.writeUTF(paramString);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\serialization\ObjectEncoderOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */