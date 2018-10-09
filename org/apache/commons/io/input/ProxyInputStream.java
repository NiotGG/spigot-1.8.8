/*     */ package org.apache.commons.io.input;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ProxyInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   public ProxyInputStream(InputStream paramInputStream)
/*     */   {
/*  45 */     super(paramInputStream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  57 */       beforeRead(1);
/*  58 */       int i = this.in.read();
/*  59 */       afterRead(i != -1 ? 1 : -1);
/*  60 */       return i;
/*     */     } catch (IOException localIOException) {
/*  62 */       handleIOException(localIOException); }
/*  63 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  76 */       beforeRead(paramArrayOfByte != null ? paramArrayOfByte.length : 0);
/*  77 */       int i = this.in.read(paramArrayOfByte);
/*  78 */       afterRead(i);
/*  79 */       return i;
/*     */     } catch (IOException localIOException) {
/*  81 */       handleIOException(localIOException); }
/*  82 */     return -1;
/*     */   }
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
/*     */     try
/*     */     {
/*  97 */       beforeRead(paramInt2);
/*  98 */       int i = this.in.read(paramArrayOfByte, paramInt1, paramInt2);
/*  99 */       afterRead(i);
/* 100 */       return i;
/*     */     } catch (IOException localIOException) {
/* 102 */       handleIOException(localIOException); }
/* 103 */     return -1;
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
/*     */     try
/*     */     {
/* 116 */       return this.in.skip(paramLong);
/*     */     } catch (IOException localIOException) {
/* 118 */       handleIOException(localIOException); }
/* 119 */     return 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int available()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 131 */       return super.available();
/*     */     } catch (IOException localIOException) {
/* 133 */       handleIOException(localIOException); }
/* 134 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 145 */       this.in.close();
/*     */     } catch (IOException localIOException) {
/* 147 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void mark(int paramInt)
/*     */   {
/* 157 */     this.in.mark(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void reset()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 167 */       this.in.reset();
/*     */     } catch (IOException localIOException) {
/* 169 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 179 */     return this.in.markSupported();
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
/*     */   protected void beforeRead(int paramInt)
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
/*     */ 
/*     */ 
/*     */   protected void afterRead(int paramInt)
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
/*     */ 
/*     */ 
/*     */   protected void handleIOException(IOException paramIOException)
/*     */     throws IOException
/*     */   {
/* 233 */     throw paramIOException;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\ProxyInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */