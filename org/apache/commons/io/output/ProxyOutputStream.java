/*     */ package org.apache.commons.io.output;
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
/*     */ 
/*     */ 
/*     */ public class ProxyOutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   public ProxyOutputStream(OutputStream paramOutputStream)
/*     */   {
/*  42 */     super(paramOutputStream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  54 */       beforeWrite(1);
/*  55 */       this.out.write(paramInt);
/*  56 */       afterWrite(1);
/*     */     } catch (IOException localIOException) {
/*  58 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  70 */       int i = paramArrayOfByte != null ? paramArrayOfByte.length : 0;
/*  71 */       beforeWrite(i);
/*  72 */       this.out.write(paramArrayOfByte);
/*  73 */       afterWrite(i);
/*     */     } catch (IOException localIOException) {
/*  75 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  89 */       beforeWrite(paramInt2);
/*  90 */       this.out.write(paramArrayOfByte, paramInt1, paramInt2);
/*  91 */       afterWrite(paramInt2);
/*     */     } catch (IOException localIOException) {
/*  93 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 104 */       this.out.flush();
/*     */     } catch (IOException localIOException) {
/* 106 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 117 */       this.out.close();
/*     */     } catch (IOException localIOException) {
/* 119 */       handleIOException(localIOException);
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
/*     */ 
/*     */   protected void beforeWrite(int paramInt)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void afterWrite(int paramInt)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void handleIOException(IOException paramIOException)
/*     */     throws IOException
/*     */   {
/* 166 */     throw paramIOException;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\ProxyOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */