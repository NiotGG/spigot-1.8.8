/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.FilterWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProxyWriter
/*     */   extends FilterWriter
/*     */ {
/*     */   public ProxyWriter(Writer paramWriter)
/*     */   {
/*  41 */     super(paramWriter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Writer append(char paramChar)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  55 */       beforeWrite(1);
/*  56 */       this.out.append(paramChar);
/*  57 */       afterWrite(1);
/*     */     } catch (IOException localIOException) {
/*  59 */       handleIOException(localIOException);
/*     */     }
/*  61 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Writer append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  76 */       beforeWrite(paramInt2 - paramInt1);
/*  77 */       this.out.append(paramCharSequence, paramInt1, paramInt2);
/*  78 */       afterWrite(paramInt2 - paramInt1);
/*     */     } catch (IOException localIOException) {
/*  80 */       handleIOException(localIOException);
/*     */     }
/*  82 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Writer append(CharSequence paramCharSequence)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  95 */       int i = 0;
/*  96 */       if (paramCharSequence != null) {
/*  97 */         i = paramCharSequence.length();
/*     */       }
/*     */       
/* 100 */       beforeWrite(i);
/* 101 */       this.out.append(paramCharSequence);
/* 102 */       afterWrite(i);
/*     */     } catch (IOException localIOException) {
/* 104 */       handleIOException(localIOException);
/*     */     }
/* 106 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 117 */       beforeWrite(1);
/* 118 */       this.out.write(paramInt);
/* 119 */       afterWrite(1);
/*     */     } catch (IOException localIOException) {
/* 121 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(char[] paramArrayOfChar)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 133 */       int i = 0;
/* 134 */       if (paramArrayOfChar != null) {
/* 135 */         i = paramArrayOfChar.length;
/*     */       }
/*     */       
/* 138 */       beforeWrite(i);
/* 139 */       this.out.write(paramArrayOfChar);
/* 140 */       afterWrite(i);
/*     */     } catch (IOException localIOException) {
/* 142 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 156 */       beforeWrite(paramInt2);
/* 157 */       this.out.write(paramArrayOfChar, paramInt1, paramInt2);
/* 158 */       afterWrite(paramInt2);
/*     */     } catch (IOException localIOException) {
/* 160 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(String paramString)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 172 */       int i = 0;
/* 173 */       if (paramString != null) {
/* 174 */         i = paramString.length();
/*     */       }
/*     */       
/* 177 */       beforeWrite(i);
/* 178 */       this.out.write(paramString);
/* 179 */       afterWrite(i);
/*     */     } catch (IOException localIOException) {
/* 181 */       handleIOException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(String paramString, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 195 */       beforeWrite(paramInt2);
/* 196 */       this.out.write(paramString, paramInt1, paramInt2);
/* 197 */       afterWrite(paramInt2);
/*     */     } catch (IOException localIOException) {
/* 199 */       handleIOException(localIOException);
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
/* 210 */       this.out.flush();
/*     */     } catch (IOException localIOException) {
/* 212 */       handleIOException(localIOException);
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
/* 223 */       this.out.close();
/*     */     } catch (IOException localIOException) {
/* 225 */       handleIOException(localIOException);
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
/* 272 */     throw paramIOException;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\ProxyWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */