/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeferredFileOutputStream
/*     */   extends ThresholdingOutputStream
/*     */ {
/*     */   private ByteArrayOutputStream memoryOutputStream;
/*     */   private OutputStream currentOutputStream;
/*     */   private File outputFile;
/*     */   private final String prefix;
/*     */   private final String suffix;
/*     */   private final File directory;
/*  87 */   private boolean closed = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeferredFileOutputStream(int paramInt, File paramFile)
/*     */   {
/* 101 */     this(paramInt, paramFile, null, null, null);
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
/*     */   public DeferredFileOutputStream(int paramInt, String paramString1, String paramString2, File paramFile)
/*     */   {
/* 118 */     this(paramInt, null, paramString1, paramString2, paramFile);
/* 119 */     if (paramString1 == null) {
/* 120 */       throw new IllegalArgumentException("Temporary file prefix is missing");
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
/*     */   private DeferredFileOutputStream(int paramInt, File paramFile1, String paramString1, String paramString2, File paramFile2)
/*     */   {
/* 135 */     super(paramInt);
/* 136 */     this.outputFile = paramFile1;
/*     */     
/* 138 */     this.memoryOutputStream = new ByteArrayOutputStream();
/* 139 */     this.currentOutputStream = this.memoryOutputStream;
/* 140 */     this.prefix = paramString1;
/* 141 */     this.suffix = paramString2;
/* 142 */     this.directory = paramFile2;
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
/*     */   protected OutputStream getStream()
/*     */     throws IOException
/*     */   {
/* 160 */     return this.currentOutputStream;
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
/*     */   protected void thresholdReached()
/*     */     throws IOException
/*     */   {
/* 175 */     if (this.prefix != null) {
/* 176 */       this.outputFile = File.createTempFile(this.prefix, this.suffix, this.directory);
/*     */     }
/* 178 */     FileOutputStream localFileOutputStream = new FileOutputStream(this.outputFile);
/* 179 */     this.memoryOutputStream.writeTo(localFileOutputStream);
/* 180 */     this.currentOutputStream = localFileOutputStream;
/* 181 */     this.memoryOutputStream = null;
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
/*     */   public boolean isInMemory()
/*     */   {
/* 197 */     return !isThresholdExceeded();
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
/*     */   public byte[] getData()
/*     */   {
/* 211 */     if (this.memoryOutputStream != null)
/*     */     {
/* 213 */       return this.memoryOutputStream.toByteArray();
/*     */     }
/* 215 */     return null;
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
/*     */ 
/*     */   public File getFile()
/*     */   {
/* 235 */     return this.outputFile;
/*     */   }
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
/* 247 */     super.close();
/* 248 */     this.closed = true;
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
/*     */   public void writeTo(OutputStream paramOutputStream)
/*     */     throws IOException
/*     */   {
/* 264 */     if (!this.closed)
/*     */     {
/* 266 */       throw new IOException("Stream not closed");
/*     */     }
/*     */     
/* 269 */     if (isInMemory())
/*     */     {
/* 271 */       this.memoryOutputStream.writeTo(paramOutputStream);
/*     */     }
/*     */     else
/*     */     {
/* 275 */       FileInputStream localFileInputStream = new FileInputStream(this.outputFile);
/*     */       try {
/* 277 */         IOUtils.copy(localFileInputStream, paramOutputStream);
/*     */       } finally {
/* 279 */         IOUtils.closeQuietly(localFileInputStream);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\DeferredFileOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */