/*     */ package org.apache.commons.io.input;
/*     */ 
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
/*     */ 
/*     */ public class CountingInputStream
/*     */   extends ProxyInputStream
/*     */ {
/*     */   private long count;
/*     */   
/*     */   public CountingInputStream(InputStream paramInputStream)
/*     */   {
/*  42 */     super(paramInputStream);
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
/*     */   public synchronized long skip(long paramLong)
/*     */     throws IOException
/*     */   {
/*  58 */     long l = super.skip(paramLong);
/*  59 */     this.count += l;
/*  60 */     return l;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected synchronized void afterRead(int paramInt)
/*     */   {
/*  71 */     if (paramInt != -1) {
/*  72 */       this.count += paramInt;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getCount()
/*     */   {
/*  88 */     long l = getByteCount();
/*  89 */     if (l > 2147483647L) {
/*  90 */       throw new ArithmeticException("The byte count " + l + " is too large to be converted to an int");
/*     */     }
/*  92 */     return (int)l;
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
/*     */   public int resetCount()
/*     */   {
/* 106 */     long l = resetByteCount();
/* 107 */     if (l > 2147483647L) {
/* 108 */       throw new ArithmeticException("The byte count " + l + " is too large to be converted to an int");
/*     */     }
/* 110 */     return (int)l;
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
/*     */   public synchronized long getByteCount()
/*     */   {
/* 124 */     return this.count;
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
/*     */   public synchronized long resetByteCount()
/*     */   {
/* 138 */     long l = this.count;
/* 139 */     this.count = 0L;
/* 140 */     return l;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\CountingInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */