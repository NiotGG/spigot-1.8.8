/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class TeeInputStream
/*     */   extends ProxyInputStream
/*     */ {
/*     */   private final OutputStream branch;
/*     */   private final boolean closeBranch;
/*     */   
/*     */   public TeeInputStream(InputStream paramInputStream, OutputStream paramOutputStream)
/*     */   {
/*  60 */     this(paramInputStream, paramOutputStream, false);
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
/*     */   public TeeInputStream(InputStream paramInputStream, OutputStream paramOutputStream, boolean paramBoolean)
/*     */   {
/*  76 */     super(paramInputStream);
/*  77 */     this.branch = paramOutputStream;
/*  78 */     this.closeBranch = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  91 */       super.close();
/*     */     } finally {
/*  93 */       if (this.closeBranch) {
/*  94 */         this.branch.close();
/*     */       }
/*     */     }
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
/* 108 */     int i = super.read();
/* 109 */     if (i != -1) {
/* 110 */       this.branch.write(i);
/*     */     }
/* 112 */     return i;
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
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 127 */     int i = super.read(paramArrayOfByte, paramInt1, paramInt2);
/* 128 */     if (i != -1) {
/* 129 */       this.branch.write(paramArrayOfByte, paramInt1, i);
/*     */     }
/* 131 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 144 */     int i = super.read(paramArrayOfByte);
/* 145 */     if (i != -1) {
/* 146 */       this.branch.write(paramArrayOfByte, 0, i);
/*     */     }
/* 148 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\TeeInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */