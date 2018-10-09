/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.FilterReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.nio.CharBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ProxyReader
/*     */   extends FilterReader
/*     */ {
/*     */   public ProxyReader(Reader paramReader)
/*     */   {
/*  43 */     super(paramReader);
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
/*  55 */       beforeRead(1);
/*  56 */       int i = this.in.read();
/*  57 */       afterRead(i != -1 ? 1 : -1);
/*  58 */       return i;
/*     */     } catch (IOException localIOException) {
/*  60 */       handleIOException(localIOException); }
/*  61 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(char[] paramArrayOfChar)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  74 */       beforeRead(paramArrayOfChar != null ? paramArrayOfChar.length : 0);
/*  75 */       int i = this.in.read(paramArrayOfChar);
/*  76 */       afterRead(i);
/*  77 */       return i;
/*     */     } catch (IOException localIOException) {
/*  79 */       handleIOException(localIOException); }
/*  80 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  95 */       beforeRead(paramInt2);
/*  96 */       int i = this.in.read(paramArrayOfChar, paramInt1, paramInt2);
/*  97 */       afterRead(i);
/*  98 */       return i;
/*     */     } catch (IOException localIOException) {
/* 100 */       handleIOException(localIOException); }
/* 101 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(CharBuffer paramCharBuffer)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 115 */       beforeRead(paramCharBuffer != null ? paramCharBuffer.length() : 0);
/* 116 */       int i = this.in.read(paramCharBuffer);
/* 117 */       afterRead(i);
/* 118 */       return i;
/*     */     } catch (IOException localIOException) {
/* 120 */       handleIOException(localIOException); }
/* 121 */     return -1;
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
/* 134 */       return this.in.skip(paramLong);
/*     */     } catch (IOException localIOException) {
/* 136 */       handleIOException(localIOException); }
/* 137 */     return 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean ready()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 149 */       return this.in.ready();
/*     */     } catch (IOException localIOException) {
/* 151 */       handleIOException(localIOException); }
/* 152 */     return false;
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
/* 163 */       this.in.close();
/*     */     } catch (IOException localIOException) {
/* 165 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void mark(int paramInt)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 177 */       this.in.mark(paramInt);
/*     */     } catch (IOException localIOException) {
/* 179 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void reset()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 190 */       this.in.reset();
/*     */     } catch (IOException localIOException) {
/* 192 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 202 */     return this.in.markSupported();
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
/* 256 */     throw paramIOException;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\ProxyReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */