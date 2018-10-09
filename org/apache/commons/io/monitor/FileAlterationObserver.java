/*     */ package org.apache.commons.io.monitor;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOCase;
/*     */ import org.apache.commons.io.comparator.NameFileComparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileAlterationObserver
/*     */   implements Serializable
/*     */ {
/* 124 */   private final List<FileAlterationListener> listeners = new CopyOnWriteArrayList();
/*     */   
/*     */   private final FileEntry rootEntry;
/*     */   
/*     */   private final FileFilter fileFilter;
/*     */   
/*     */   private final Comparator<File> comparator;
/*     */   
/*     */ 
/*     */   public FileAlterationObserver(String paramString)
/*     */   {
/* 135 */     this(new File(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileAlterationObserver(String paramString, FileFilter paramFileFilter)
/*     */   {
/* 145 */     this(new File(paramString), paramFileFilter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileAlterationObserver(String paramString, FileFilter paramFileFilter, IOCase paramIOCase)
/*     */   {
/* 157 */     this(new File(paramString), paramFileFilter, paramIOCase);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileAlterationObserver(File paramFile)
/*     */   {
/* 166 */     this(paramFile, (FileFilter)null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileAlterationObserver(File paramFile, FileFilter paramFileFilter)
/*     */   {
/* 176 */     this(paramFile, paramFileFilter, (IOCase)null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileAlterationObserver(File paramFile, FileFilter paramFileFilter, IOCase paramIOCase)
/*     */   {
/* 188 */     this(new FileEntry(paramFile), paramFileFilter, paramIOCase);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected FileAlterationObserver(FileEntry paramFileEntry, FileFilter paramFileFilter, IOCase paramIOCase)
/*     */   {
/* 200 */     if (paramFileEntry == null) {
/* 201 */       throw new IllegalArgumentException("Root entry is missing");
/*     */     }
/* 203 */     if (paramFileEntry.getFile() == null) {
/* 204 */       throw new IllegalArgumentException("Root directory is missing");
/*     */     }
/* 206 */     this.rootEntry = paramFileEntry;
/* 207 */     this.fileFilter = paramFileFilter;
/* 208 */     if ((paramIOCase == null) || (paramIOCase.equals(IOCase.SYSTEM))) {
/* 209 */       this.comparator = NameFileComparator.NAME_SYSTEM_COMPARATOR;
/* 210 */     } else if (paramIOCase.equals(IOCase.INSENSITIVE)) {
/* 211 */       this.comparator = NameFileComparator.NAME_INSENSITIVE_COMPARATOR;
/*     */     } else {
/* 213 */       this.comparator = NameFileComparator.NAME_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public File getDirectory()
/*     */   {
/* 223 */     return this.rootEntry.getFile();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileFilter getFileFilter()
/*     */   {
/* 233 */     return this.fileFilter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addListener(FileAlterationListener paramFileAlterationListener)
/*     */   {
/* 242 */     if (paramFileAlterationListener != null) {
/* 243 */       this.listeners.add(paramFileAlterationListener);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeListener(FileAlterationListener paramFileAlterationListener)
/*     */   {
/* 253 */     while ((paramFileAlterationListener != null) && 
/* 254 */       (this.listeners.remove(paramFileAlterationListener))) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterable<FileAlterationListener> getListeners()
/*     */   {
/* 265 */     return this.listeners;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initialize()
/*     */     throws Exception
/*     */   {
/* 274 */     this.rootEntry.refresh(this.rootEntry.getFile());
/* 275 */     File[] arrayOfFile = listFiles(this.rootEntry.getFile());
/* 276 */     FileEntry[] arrayOfFileEntry = arrayOfFile.length > 0 ? new FileEntry[arrayOfFile.length] : FileEntry.EMPTY_ENTRIES;
/* 277 */     for (int i = 0; i < arrayOfFile.length; i++) {
/* 278 */       arrayOfFileEntry[i] = createFileEntry(this.rootEntry, arrayOfFile[i]);
/*     */     }
/* 280 */     this.rootEntry.setChildren(arrayOfFileEntry);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void destroy()
/*     */     throws Exception
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void checkAndNotify()
/*     */   {
/* 297 */     for (Object localObject1 = this.listeners.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (FileAlterationListener)((Iterator)localObject1).next();
/* 298 */       ((FileAlterationListener)localObject2).onStart(this);
/*     */     }
/*     */     
/*     */ 
/* 302 */     localObject1 = this.rootEntry.getFile();
/* 303 */     if (((File)localObject1).exists()) {
/* 304 */       checkAndNotify(this.rootEntry, this.rootEntry.getChildren(), listFiles((File)localObject1));
/* 305 */     } else if (this.rootEntry.isExists()) {
/* 306 */       checkAndNotify(this.rootEntry, this.rootEntry.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 312 */     for (Object localObject2 = this.listeners.iterator(); ((Iterator)localObject2).hasNext();) { FileAlterationListener localFileAlterationListener = (FileAlterationListener)((Iterator)localObject2).next();
/* 313 */       localFileAlterationListener.onStop(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void checkAndNotify(FileEntry paramFileEntry, FileEntry[] paramArrayOfFileEntry, File[] paramArrayOfFile)
/*     */   {
/* 325 */     int i = 0;
/* 326 */     FileEntry[] arrayOfFileEntry1 = paramArrayOfFile.length > 0 ? new FileEntry[paramArrayOfFile.length] : FileEntry.EMPTY_ENTRIES;
/* 327 */     for (FileEntry localFileEntry : paramArrayOfFileEntry) {
/* 328 */       while ((i < paramArrayOfFile.length) && (this.comparator.compare(localFileEntry.getFile(), paramArrayOfFile[i]) > 0)) {
/* 329 */         arrayOfFileEntry1[i] = createFileEntry(paramFileEntry, paramArrayOfFile[i]);
/* 330 */         doCreate(arrayOfFileEntry1[i]);
/* 331 */         i++;
/*     */       }
/* 333 */       if ((i < paramArrayOfFile.length) && (this.comparator.compare(localFileEntry.getFile(), paramArrayOfFile[i]) == 0)) {
/* 334 */         doMatch(localFileEntry, paramArrayOfFile[i]);
/* 335 */         checkAndNotify(localFileEntry, localFileEntry.getChildren(), listFiles(paramArrayOfFile[i]));
/* 336 */         arrayOfFileEntry1[i] = localFileEntry;
/* 337 */         i++;
/*     */       } else {
/* 339 */         checkAndNotify(localFileEntry, localFileEntry.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
/* 340 */         doDelete(localFileEntry);
/*     */       }
/*     */     }
/* 343 */     for (; i < paramArrayOfFile.length; i++) {
/* 344 */       arrayOfFileEntry1[i] = createFileEntry(paramFileEntry, paramArrayOfFile[i]);
/* 345 */       doCreate(arrayOfFileEntry1[i]);
/*     */     }
/* 347 */     paramFileEntry.setChildren(arrayOfFileEntry1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private FileEntry createFileEntry(FileEntry paramFileEntry, File paramFile)
/*     */   {
/* 358 */     FileEntry localFileEntry = paramFileEntry.newChildInstance(paramFile);
/* 359 */     localFileEntry.refresh(paramFile);
/* 360 */     File[] arrayOfFile = listFiles(paramFile);
/* 361 */     FileEntry[] arrayOfFileEntry = arrayOfFile.length > 0 ? new FileEntry[arrayOfFile.length] : FileEntry.EMPTY_ENTRIES;
/* 362 */     for (int i = 0; i < arrayOfFile.length; i++) {
/* 363 */       arrayOfFileEntry[i] = createFileEntry(localFileEntry, arrayOfFile[i]);
/*     */     }
/* 365 */     localFileEntry.setChildren(arrayOfFileEntry);
/* 366 */     return localFileEntry;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void doCreate(FileEntry paramFileEntry)
/*     */   {
/* 375 */     for (Object localObject1 = this.listeners.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (FileAlterationListener)((Iterator)localObject1).next();
/* 376 */       if (paramFileEntry.isDirectory()) {
/* 377 */         ((FileAlterationListener)localObject2).onDirectoryCreate(paramFileEntry.getFile());
/*     */       } else
/* 379 */         ((FileAlterationListener)localObject2).onFileCreate(paramFileEntry.getFile());
/*     */     }
/*     */     Object localObject2;
/* 382 */     localObject1 = paramFileEntry.getChildren();
/* 383 */     for (FileEntry localFileEntry : localObject1) {
/* 384 */       doCreate(localFileEntry);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void doMatch(FileEntry paramFileEntry, File paramFile)
/*     */   {
/* 395 */     if (paramFileEntry.refresh(paramFile)) {
/* 396 */       for (FileAlterationListener localFileAlterationListener : this.listeners) {
/* 397 */         if (paramFileEntry.isDirectory()) {
/* 398 */           localFileAlterationListener.onDirectoryChange(paramFile);
/*     */         } else {
/* 400 */           localFileAlterationListener.onFileChange(paramFile);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void doDelete(FileEntry paramFileEntry)
/*     */   {
/* 412 */     for (FileAlterationListener localFileAlterationListener : this.listeners) {
/* 413 */       if (paramFileEntry.isDirectory()) {
/* 414 */         localFileAlterationListener.onDirectoryDelete(paramFileEntry.getFile());
/*     */       } else {
/* 416 */         localFileAlterationListener.onFileDelete(paramFileEntry.getFile());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private File[] listFiles(File paramFile)
/*     */   {
/* 429 */     File[] arrayOfFile = null;
/* 430 */     if (paramFile.isDirectory()) {
/* 431 */       arrayOfFile = this.fileFilter == null ? paramFile.listFiles() : paramFile.listFiles(this.fileFilter);
/*     */     }
/* 433 */     if (arrayOfFile == null) {
/* 434 */       arrayOfFile = FileUtils.EMPTY_FILE_ARRAY;
/*     */     }
/* 436 */     if ((this.comparator != null) && (arrayOfFile.length > 1)) {
/* 437 */       Arrays.sort(arrayOfFile, this.comparator);
/*     */     }
/* 439 */     return arrayOfFile;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 449 */     StringBuilder localStringBuilder = new StringBuilder();
/* 450 */     localStringBuilder.append(getClass().getSimpleName());
/* 451 */     localStringBuilder.append("[file='");
/* 452 */     localStringBuilder.append(getDirectory().getPath());
/* 453 */     localStringBuilder.append('\'');
/* 454 */     if (this.fileFilter != null) {
/* 455 */       localStringBuilder.append(", ");
/* 456 */       localStringBuilder.append(this.fileFilter.toString());
/*     */     }
/* 458 */     localStringBuilder.append(", listeners=");
/* 459 */     localStringBuilder.append(this.listeners.size());
/* 460 */     localStringBuilder.append("]");
/* 461 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\monitor\FileAlterationObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */