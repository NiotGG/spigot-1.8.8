/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import org.apache.commons.io.Charsets;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LockableFileWriter
/*     */   extends Writer
/*     */ {
/*     */   private static final String LCK = ".lck";
/*     */   private final Writer out;
/*     */   private final File lockFile;
/*     */   
/*     */   public LockableFileWriter(String paramString)
/*     */     throws IOException
/*     */   {
/*  74 */     this(paramString, false, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LockableFileWriter(String paramString, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/*  86 */     this(paramString, paramBoolean, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LockableFileWriter(String paramString1, boolean paramBoolean, String paramString2)
/*     */     throws IOException
/*     */   {
/*  99 */     this(new File(paramString1), paramBoolean, paramString2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LockableFileWriter(File paramFile)
/*     */     throws IOException
/*     */   {
/* 111 */     this(paramFile, false, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LockableFileWriter(File paramFile, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 123 */     this(paramFile, paramBoolean, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LockableFileWriter(File paramFile, boolean paramBoolean, String paramString)
/*     */     throws IOException
/*     */   {
/* 136 */     this(paramFile, Charset.defaultCharset(), paramBoolean, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LockableFileWriter(File paramFile, Charset paramCharset)
/*     */     throws IOException
/*     */   {
/* 149 */     this(paramFile, paramCharset, false, null);
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
/*     */   public LockableFileWriter(File paramFile, String paramString)
/*     */     throws IOException
/*     */   {
/* 164 */     this(paramFile, paramString, false, null);
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
/*     */   public LockableFileWriter(File paramFile, Charset paramCharset, boolean paramBoolean, String paramString)
/*     */     throws IOException
/*     */   {
/* 182 */     paramFile = paramFile.getAbsoluteFile();
/* 183 */     if (paramFile.getParentFile() != null) {
/* 184 */       FileUtils.forceMkdir(paramFile.getParentFile());
/*     */     }
/* 186 */     if (paramFile.isDirectory()) {
/* 187 */       throw new IOException("File specified is a directory");
/*     */     }
/*     */     
/*     */ 
/* 191 */     if (paramString == null) {
/* 192 */       paramString = System.getProperty("java.io.tmpdir");
/*     */     }
/* 194 */     File localFile = new File(paramString);
/* 195 */     FileUtils.forceMkdir(localFile);
/* 196 */     testLockDir(localFile);
/* 197 */     this.lockFile = new File(localFile, paramFile.getName() + ".lck");
/*     */     
/*     */ 
/* 200 */     createLock();
/*     */     
/*     */ 
/* 203 */     this.out = initWriter(paramFile, paramCharset, paramBoolean);
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
/*     */   public LockableFileWriter(File paramFile, String paramString1, boolean paramBoolean, String paramString2)
/*     */     throws IOException
/*     */   {
/* 221 */     this(paramFile, Charsets.toCharset(paramString1), paramBoolean, paramString2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void testLockDir(File paramFile)
/*     */     throws IOException
/*     */   {
/* 233 */     if (!paramFile.exists()) {
/* 234 */       throw new IOException("Could not find lockDir: " + paramFile.getAbsolutePath());
/*     */     }
/*     */     
/* 237 */     if (!paramFile.canWrite()) {
/* 238 */       throw new IOException("Could not write to lockDir: " + paramFile.getAbsolutePath());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void createLock()
/*     */     throws IOException
/*     */   {
/* 249 */     synchronized (LockableFileWriter.class) {
/* 250 */       if (!this.lockFile.createNewFile()) {
/* 251 */         throw new IOException("Can't write file, lock " + this.lockFile.getAbsolutePath() + " exists");
/*     */       }
/*     */       
/* 254 */       this.lockFile.deleteOnExit();
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
/*     */   private Writer initWriter(File paramFile, Charset paramCharset, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 269 */     boolean bool = paramFile.exists();
/* 270 */     FileOutputStream localFileOutputStream = null;
/* 271 */     OutputStreamWriter localOutputStreamWriter = null;
/*     */     try {
/* 273 */       localFileOutputStream = new FileOutputStream(paramFile.getAbsolutePath(), paramBoolean);
/* 274 */       localOutputStreamWriter = new OutputStreamWriter(localFileOutputStream, Charsets.toCharset(paramCharset));
/*     */     } catch (IOException localIOException) {
/* 276 */       IOUtils.closeQuietly(localOutputStreamWriter);
/* 277 */       IOUtils.closeQuietly(localFileOutputStream);
/* 278 */       FileUtils.deleteQuietly(this.lockFile);
/* 279 */       if (!bool) {
/* 280 */         FileUtils.deleteQuietly(paramFile);
/*     */       }
/* 282 */       throw localIOException;
/*     */     } catch (RuntimeException localRuntimeException) {
/* 284 */       IOUtils.closeQuietly(localOutputStreamWriter);
/* 285 */       IOUtils.closeQuietly(localFileOutputStream);
/* 286 */       FileUtils.deleteQuietly(this.lockFile);
/* 287 */       if (!bool) {
/* 288 */         FileUtils.deleteQuietly(paramFile);
/*     */       }
/* 290 */       throw localRuntimeException;
/*     */     }
/* 292 */     return localOutputStreamWriter;
/*     */   }
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
/* 304 */       this.out.close();
/*     */     } finally {
/* 306 */       this.lockFile.delete();
/*     */     }
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
/* 318 */     this.out.write(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(char[] paramArrayOfChar)
/*     */     throws IOException
/*     */   {
/* 328 */     this.out.write(paramArrayOfChar);
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
/* 340 */     this.out.write(paramArrayOfChar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(String paramString)
/*     */     throws IOException
/*     */   {
/* 350 */     this.out.write(paramString);
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
/* 362 */     this.out.write(paramString, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 371 */     this.out.flush();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\LockableFileWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */