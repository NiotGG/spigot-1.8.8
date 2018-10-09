/*     */ package org.apache.commons.codec.binary;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class BaseNCodecInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private final BaseNCodec baseNCodec;
/*     */   private final boolean doEncode;
/*  40 */   private final byte[] singleByte = new byte[1];
/*     */   
/*  42 */   private final BaseNCodec.Context context = new BaseNCodec.Context();
/*     */   
/*     */   protected BaseNCodecInputStream(InputStream paramInputStream, BaseNCodec paramBaseNCodec, boolean paramBoolean) {
/*  45 */     super(paramInputStream);
/*  46 */     this.doEncode = paramBoolean;
/*  47 */     this.baseNCodec = paramBaseNCodec;
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
/*     */   public int available()
/*     */     throws IOException
/*     */   {
/*  64 */     return this.context.eof ? 0 : 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void mark(int paramInt) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/*  85 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*  97 */     int i = read(this.singleByte, 0, 1);
/*  98 */     while (i == 0) {
/*  99 */       i = read(this.singleByte, 0, 1);
/*     */     }
/* 101 */     if (i > 0) {
/* 102 */       int j = this.singleByte[0];
/* 103 */       return j < 0 ? 256 + j : j;
/*     */     }
/* 105 */     return -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 129 */     if (paramArrayOfByte == null)
/* 130 */       throw new NullPointerException();
/* 131 */     if ((paramInt1 < 0) || (paramInt2 < 0))
/* 132 */       throw new IndexOutOfBoundsException();
/* 133 */     if ((paramInt1 > paramArrayOfByte.length) || (paramInt1 + paramInt2 > paramArrayOfByte.length))
/* 134 */       throw new IndexOutOfBoundsException();
/* 135 */     if (paramInt2 == 0) {
/* 136 */       return 0;
/*     */     }
/* 138 */     int i = 0;
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
/* 155 */     while (i == 0) {
/* 156 */       if (!this.baseNCodec.hasData(this.context)) {
/* 157 */         byte[] arrayOfByte = new byte[this.doEncode ? 'က' : ' '];
/* 158 */         int j = this.in.read(arrayOfByte);
/* 159 */         if (this.doEncode) {
/* 160 */           this.baseNCodec.encode(arrayOfByte, 0, j, this.context);
/*     */         } else {
/* 162 */           this.baseNCodec.decode(arrayOfByte, 0, j, this.context);
/*     */         }
/*     */       }
/* 165 */       i = this.baseNCodec.readResults(paramArrayOfByte, paramInt1, paramInt2, this.context);
/*     */     }
/* 167 */     return i;
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
/*     */   public synchronized void reset()
/*     */     throws IOException
/*     */   {
/* 181 */     throw new IOException("mark/reset not supported");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long skip(long paramLong)
/*     */     throws IOException
/*     */   {
/* 192 */     if (paramLong < 0L) {
/* 193 */       throw new IllegalArgumentException("Negative skip length: " + paramLong);
/*     */     }
/*     */     
/*     */ 
/* 197 */     byte[] arrayOfByte = new byte['Ȁ'];
/* 198 */     long l = paramLong;
/*     */     
/* 200 */     while (l > 0L) {
/* 201 */       int i = (int)Math.min(arrayOfByte.length, l);
/* 202 */       i = read(arrayOfByte, 0, i);
/* 203 */       if (i == -1) {
/*     */         break;
/*     */       }
/* 206 */       l -= i;
/*     */     }
/*     */     
/* 209 */     return paramLong - l;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\binary\BaseNCodecInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */