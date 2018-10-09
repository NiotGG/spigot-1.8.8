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
/*     */ public class TeeOutputStream
/*     */   extends ProxyOutputStream
/*     */ {
/*     */   protected OutputStream branch;
/*     */   
/*     */   public TeeOutputStream(OutputStream paramOutputStream1, OutputStream paramOutputStream2)
/*     */   {
/*  40 */     super(paramOutputStream1);
/*  41 */     this.branch = paramOutputStream2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void write(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/*  51 */     super.write(paramArrayOfByte);
/*  52 */     this.branch.write(paramArrayOfByte);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/*  64 */     super.write(paramArrayOfByte, paramInt1, paramInt2);
/*  65 */     this.branch.write(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void write(int paramInt)
/*     */     throws IOException
/*     */   {
/*  75 */     super.write(paramInt);
/*  76 */     this.branch.write(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/*  85 */     super.flush();
/*  86 */     this.branch.flush();
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
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 103 */       super.close();
/*     */     } finally {
/* 105 */       this.branch.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\TeeOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */