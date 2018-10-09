/*     */ package org.apache.commons.io.output;
/*     */ 
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
/*     */ public class CountingOutputStream
/*     */   extends ProxyOutputStream
/*     */ {
/*  33 */   private long count = 0L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CountingOutputStream(OutputStream paramOutputStream)
/*     */   {
/*  41 */     super(paramOutputStream);
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
/*     */   protected synchronized void beforeWrite(int paramInt)
/*     */   {
/*  54 */     this.count += paramInt;
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
/*  69 */     long l = getByteCount();
/*  70 */     if (l > 2147483647L) {
/*  71 */       throw new ArithmeticException("The byte count " + l + " is too large to be converted to an int");
/*     */     }
/*  73 */     return (int)l;
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
/*  87 */     long l = resetByteCount();
/*  88 */     if (l > 2147483647L) {
/*  89 */       throw new ArithmeticException("The byte count " + l + " is too large to be converted to an int");
/*     */     }
/*  91 */     return (int)l;
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
/* 105 */     return this.count;
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
/* 119 */     long l = this.count;
/* 120 */     this.count = 0L;
/* 121 */     return l;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\CountingOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */