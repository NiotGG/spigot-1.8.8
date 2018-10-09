/*     */ package org.apache.commons.codec.binary;
/*     */ 
/*     */ import java.io.FilterOutputStream;
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
/*     */ 
/*     */ 
/*     */ public class BaseNCodecOutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   private final boolean doEncode;
/*     */   private final BaseNCodec baseNCodec;
/*  40 */   private final byte[] singleByte = new byte[1];
/*     */   
/*  42 */   private final BaseNCodec.Context context = new BaseNCodec.Context();
/*     */   
/*     */   public BaseNCodecOutputStream(OutputStream paramOutputStream, BaseNCodec paramBaseNCodec, boolean paramBoolean)
/*     */   {
/*  46 */     super(paramOutputStream);
/*  47 */     this.baseNCodec = paramBaseNCodec;
/*  48 */     this.doEncode = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/*  61 */     this.singleByte[0] = ((byte)paramInt);
/*  62 */     write(this.singleByte, 0, 1);
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
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/*  85 */     if (paramArrayOfByte == null)
/*  86 */       throw new NullPointerException();
/*  87 */     if ((paramInt1 < 0) || (paramInt2 < 0))
/*  88 */       throw new IndexOutOfBoundsException();
/*  89 */     if ((paramInt1 > paramArrayOfByte.length) || (paramInt1 + paramInt2 > paramArrayOfByte.length))
/*  90 */       throw new IndexOutOfBoundsException();
/*  91 */     if (paramInt2 > 0) {
/*  92 */       if (this.doEncode) {
/*  93 */         this.baseNCodec.encode(paramArrayOfByte, paramInt1, paramInt2, this.context);
/*     */       } else {
/*  95 */         this.baseNCodec.decode(paramArrayOfByte, paramInt1, paramInt2, this.context);
/*     */       }
/*  97 */       flush(false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void flush(boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 111 */     int i = this.baseNCodec.available(this.context);
/* 112 */     if (i > 0) {
/* 113 */       byte[] arrayOfByte = new byte[i];
/* 114 */       int j = this.baseNCodec.readResults(arrayOfByte, 0, i, this.context);
/* 115 */       if (j > 0) {
/* 116 */         this.out.write(arrayOfByte, 0, j);
/*     */       }
/*     */     }
/* 119 */     if (paramBoolean) {
/* 120 */       this.out.flush();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 132 */     flush(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 144 */     if (this.doEncode) {
/* 145 */       this.baseNCodec.encode(this.singleByte, 0, -1, this.context);
/*     */     } else {
/* 147 */       this.baseNCodec.decode(this.singleByte, 0, -1, this.context);
/*     */     }
/* 149 */     flush();
/* 150 */     this.out.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\binary\BaseNCodecOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */