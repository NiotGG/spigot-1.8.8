/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileWriterWithEncoding
/*     */   extends Writer
/*     */ {
/*     */   private final Writer out;
/*     */   
/*     */   public FileWriterWithEncoding(String paramString1, String paramString2)
/*     */     throws IOException
/*     */   {
/*  66 */     this(new File(paramString1), paramString2, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileWriterWithEncoding(String paramString1, String paramString2, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/*  79 */     this(new File(paramString1), paramString2, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileWriterWithEncoding(String paramString, Charset paramCharset)
/*     */     throws IOException
/*     */   {
/*  91 */     this(new File(paramString), paramCharset, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileWriterWithEncoding(String paramString, Charset paramCharset, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 104 */     this(new File(paramString), paramCharset, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileWriterWithEncoding(String paramString, CharsetEncoder paramCharsetEncoder)
/*     */     throws IOException
/*     */   {
/* 116 */     this(new File(paramString), paramCharsetEncoder, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileWriterWithEncoding(String paramString, CharsetEncoder paramCharsetEncoder, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 129 */     this(new File(paramString), paramCharsetEncoder, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileWriterWithEncoding(File paramFile, String paramString)
/*     */     throws IOException
/*     */   {
/* 141 */     this(paramFile, paramString, false);
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
/*     */   public FileWriterWithEncoding(File paramFile, String paramString, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 155 */     this.out = initWriter(paramFile, paramString, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileWriterWithEncoding(File paramFile, Charset paramCharset)
/*     */     throws IOException
/*     */   {
/* 167 */     this(paramFile, paramCharset, false);
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
/*     */   public FileWriterWithEncoding(File paramFile, Charset paramCharset, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 181 */     this.out = initWriter(paramFile, paramCharset, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileWriterWithEncoding(File paramFile, CharsetEncoder paramCharsetEncoder)
/*     */     throws IOException
/*     */   {
/* 193 */     this(paramFile, paramCharsetEncoder, false);
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
/*     */   public FileWriterWithEncoding(File paramFile, CharsetEncoder paramCharsetEncoder, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 207 */     this.out = initWriter(paramFile, paramCharsetEncoder, paramBoolean);
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
/*     */   private static Writer initWriter(File paramFile, Object paramObject, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 223 */     if (paramFile == null) {
/* 224 */       throw new NullPointerException("File is missing");
/*     */     }
/* 226 */     if (paramObject == null) {
/* 227 */       throw new NullPointerException("Encoding is missing");
/*     */     }
/* 229 */     boolean bool = paramFile.exists();
/* 230 */     FileOutputStream localFileOutputStream = null;
/* 231 */     OutputStreamWriter localOutputStreamWriter = null;
/*     */     try {
/* 233 */       localFileOutputStream = new FileOutputStream(paramFile, paramBoolean);
/* 234 */       if ((paramObject instanceof Charset)) {
/* 235 */         localOutputStreamWriter = new OutputStreamWriter(localFileOutputStream, (Charset)paramObject);
/* 236 */       } else if ((paramObject instanceof CharsetEncoder)) {
/* 237 */         localOutputStreamWriter = new OutputStreamWriter(localFileOutputStream, (CharsetEncoder)paramObject);
/*     */       } else {
/* 239 */         localOutputStreamWriter = new OutputStreamWriter(localFileOutputStream, (String)paramObject);
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 242 */       IOUtils.closeQuietly(localOutputStreamWriter);
/* 243 */       IOUtils.closeQuietly(localFileOutputStream);
/* 244 */       if (!bool) {
/* 245 */         FileUtils.deleteQuietly(paramFile);
/*     */       }
/* 247 */       throw localIOException;
/*     */     } catch (RuntimeException localRuntimeException) {
/* 249 */       IOUtils.closeQuietly(localOutputStreamWriter);
/* 250 */       IOUtils.closeQuietly(localFileOutputStream);
/* 251 */       if (!bool) {
/* 252 */         FileUtils.deleteQuietly(paramFile);
/*     */       }
/* 254 */       throw localRuntimeException;
/*     */     }
/* 256 */     return localOutputStreamWriter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/* 267 */     this.out.write(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(char[] paramArrayOfChar)
/*     */     throws IOException
/*     */   {
/* 277 */     this.out.write(paramArrayOfChar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 289 */     this.out.write(paramArrayOfChar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(String paramString)
/*     */     throws IOException
/*     */   {
/* 299 */     this.out.write(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(String paramString, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 311 */     this.out.write(paramString, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 320 */     this.out.flush();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 329 */     this.out.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\FileWriterWithEncoding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */