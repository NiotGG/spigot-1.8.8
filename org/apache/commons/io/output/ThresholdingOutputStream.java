/*     */ package org.apache.commons.io.output;
/*     */ 
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
/*     */ public abstract class ThresholdingOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private final int threshold;
/*     */   private long written;
/*     */   private boolean thresholdExceeded;
/*     */   
/*     */   public ThresholdingOutputStream(int paramInt)
/*     */   {
/*  75 */     this.threshold = paramInt;
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
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/*  92 */     checkThreshold(1);
/*  93 */     getStream().write(paramInt);
/*  94 */     this.written += 1L;
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
/*     */   public void write(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 109 */     checkThreshold(paramArrayOfByte.length);
/* 110 */     getStream().write(paramArrayOfByte);
/* 111 */     this.written += paramArrayOfByte.length;
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
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 128 */     checkThreshold(paramInt2);
/* 129 */     getStream().write(paramArrayOfByte, paramInt1, paramInt2);
/* 130 */     this.written += paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 143 */     getStream().flush();
/*     */   }
/*     */   
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
/*     */     try
/*     */     {
/* 158 */       flush();
/*     */     }
/*     */     catch (IOException localIOException) {}
/*     */     
/*     */ 
/*     */ 
/* 164 */     getStream().close();
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
/*     */   public int getThreshold()
/*     */   {
/* 178 */     return this.threshold;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getByteCount()
/*     */   {
/* 189 */     return this.written;
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
/*     */   public boolean isThresholdExceeded()
/*     */   {
/* 202 */     return this.written > this.threshold;
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
/*     */   protected void checkThreshold(int paramInt)
/*     */     throws IOException
/*     */   {
/* 221 */     if ((!this.thresholdExceeded) && (this.written + paramInt > this.threshold))
/*     */     {
/* 223 */       this.thresholdExceeded = true;
/* 224 */       thresholdReached();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void resetByteCount()
/*     */   {
/* 234 */     this.thresholdExceeded = false;
/* 235 */     this.written = 0L;
/*     */   }
/*     */   
/*     */   protected abstract OutputStream getStream()
/*     */     throws IOException;
/*     */   
/*     */   protected abstract void thresholdReached()
/*     */     throws IOException;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\ThresholdingOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */