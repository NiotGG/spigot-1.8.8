/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.io.filefilter.FileFilterUtils;
/*     */ import org.apache.commons.io.filefilter.IOFileFilter;
/*     */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DirectoryWalker<T>
/*     */ {
/*     */   private final FileFilter filter;
/*     */   private final int depthLimit;
/*     */   
/*     */   protected DirectoryWalker()
/*     */   {
/* 266 */     this(null, -1);
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
/*     */   protected DirectoryWalker(FileFilter paramFileFilter, int paramInt)
/*     */   {
/* 282 */     this.filter = paramFileFilter;
/* 283 */     this.depthLimit = paramInt;
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
/*     */   protected DirectoryWalker(IOFileFilter paramIOFileFilter1, IOFileFilter paramIOFileFilter2, int paramInt)
/*     */   {
/* 301 */     if ((paramIOFileFilter1 == null) && (paramIOFileFilter2 == null)) {
/* 302 */       this.filter = null;
/*     */     } else {
/* 304 */       paramIOFileFilter1 = paramIOFileFilter1 != null ? paramIOFileFilter1 : TrueFileFilter.TRUE;
/* 305 */       paramIOFileFilter2 = paramIOFileFilter2 != null ? paramIOFileFilter2 : TrueFileFilter.TRUE;
/* 306 */       paramIOFileFilter1 = FileFilterUtils.makeDirectoryOnly(paramIOFileFilter1);
/* 307 */       paramIOFileFilter2 = FileFilterUtils.makeFileOnly(paramIOFileFilter2);
/* 308 */       this.filter = FileFilterUtils.or(new IOFileFilter[] { paramIOFileFilter1, paramIOFileFilter2 });
/*     */     }
/* 310 */     this.depthLimit = paramInt;
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
/*     */   protected final void walk(File paramFile, Collection<T> paramCollection)
/*     */     throws IOException
/*     */   {
/* 330 */     if (paramFile == null) {
/* 331 */       throw new NullPointerException("Start Directory is null");
/*     */     }
/*     */     try {
/* 334 */       handleStart(paramFile, paramCollection);
/* 335 */       walk(paramFile, 0, paramCollection);
/* 336 */       handleEnd(paramCollection);
/*     */     } catch (CancelException localCancelException) {
/* 338 */       handleCancelled(paramFile, paramCollection, localCancelException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void walk(File paramFile, int paramInt, Collection<T> paramCollection)
/*     */     throws IOException
/*     */   {
/* 351 */     checkIfCancelled(paramFile, paramInt, paramCollection);
/* 352 */     if (handleDirectory(paramFile, paramInt, paramCollection)) {
/* 353 */       handleDirectoryStart(paramFile, paramInt, paramCollection);
/* 354 */       int i = paramInt + 1;
/* 355 */       if ((this.depthLimit < 0) || (i <= this.depthLimit)) {
/* 356 */         checkIfCancelled(paramFile, paramInt, paramCollection);
/* 357 */         File[] arrayOfFile1 = this.filter == null ? paramFile.listFiles() : paramFile.listFiles(this.filter);
/* 358 */         arrayOfFile1 = filterDirectoryContents(paramFile, paramInt, arrayOfFile1);
/* 359 */         if (arrayOfFile1 == null) {
/* 360 */           handleRestricted(paramFile, i, paramCollection);
/*     */         } else {
/* 362 */           for (File localFile : arrayOfFile1) {
/* 363 */             if (localFile.isDirectory()) {
/* 364 */               walk(localFile, i, paramCollection);
/*     */             } else {
/* 366 */               checkIfCancelled(localFile, i, paramCollection);
/* 367 */               handleFile(localFile, i, paramCollection);
/* 368 */               checkIfCancelled(localFile, i, paramCollection);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 373 */       handleDirectoryEnd(paramFile, paramInt, paramCollection);
/*     */     }
/* 375 */     checkIfCancelled(paramFile, paramInt, paramCollection);
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
/*     */   protected final void checkIfCancelled(File paramFile, int paramInt, Collection<T> paramCollection)
/*     */     throws IOException
/*     */   {
/* 394 */     if (handleIsCancelled(paramFile, paramInt, paramCollection)) {
/* 395 */       throw new CancelException(paramFile, paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean handleIsCancelled(File paramFile, int paramInt, Collection<T> paramCollection)
/*     */     throws IOException
/*     */   {
/* 437 */     return false;
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
/*     */   protected void handleCancelled(File paramFile, Collection<T> paramCollection, CancelException paramCancelException)
/*     */     throws IOException
/*     */   {
/* 456 */     throw paramCancelException;
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
/*     */   protected void handleStart(File paramFile, Collection<T> paramCollection)
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
/*     */   protected boolean handleDirectory(File paramFile, int paramInt, Collection<T> paramCollection)
/*     */     throws IOException
/*     */   {
/* 490 */     return true;
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
/*     */   protected void handleDirectoryStart(File paramFile, int paramInt, Collection<T> paramCollection)
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
/*     */   protected File[] filterDirectoryContents(File paramFile, int paramInt, File[] paramArrayOfFile)
/*     */     throws IOException
/*     */   {
/* 520 */     return paramArrayOfFile;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void handleFile(File paramFile, int paramInt, Collection<T> paramCollection)
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
/*     */   protected void handleRestricted(File paramFile, int paramInt, Collection<T> paramCollection)
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
/*     */   protected void handleDirectoryEnd(File paramFile, int paramInt, Collection<T> paramCollection)
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
/*     */   protected void handleEnd(Collection<T> paramCollection)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class CancelException
/*     */     extends IOException
/*     */   {
/*     */     private static final long serialVersionUID = 1347339620135041008L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private final File file;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private final int depth;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public CancelException(File paramFile, int paramInt)
/*     */     {
/* 600 */       this("Operation Cancelled", paramFile, paramInt);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public CancelException(String paramString, File paramFile, int paramInt)
/*     */     {
/* 613 */       super();
/* 614 */       this.file = paramFile;
/* 615 */       this.depth = paramInt;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public File getFile()
/*     */     {
/* 624 */       return this.file;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getDepth()
/*     */     {
/* 633 */       return this.depth;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\DirectoryWalker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */