/*     */ package org.apache.commons.io.monitor;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileEntry
/*     */   implements Serializable
/*     */ {
/*  44 */   static final FileEntry[] EMPTY_ENTRIES = new FileEntry[0];
/*     */   
/*     */   private final FileEntry parent;
/*     */   
/*     */   private FileEntry[] children;
/*     */   
/*     */   private final File file;
/*     */   
/*     */   private String name;
/*     */   
/*     */   private boolean exists;
/*     */   private boolean directory;
/*     */   private long lastModified;
/*     */   private long length;
/*     */   
/*     */   public FileEntry(File paramFile)
/*     */   {
/*  61 */     this((FileEntry)null, paramFile);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileEntry(FileEntry paramFileEntry, File paramFile)
/*     */   {
/*  71 */     if (paramFile == null) {
/*  72 */       throw new IllegalArgumentException("File is missing");
/*     */     }
/*  74 */     this.file = paramFile;
/*  75 */     this.parent = paramFileEntry;
/*  76 */     this.name = paramFile.getName();
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
/*     */   public boolean refresh(File paramFile)
/*     */   {
/*  96 */     boolean bool1 = this.exists;
/*  97 */     long l1 = this.lastModified;
/*  98 */     boolean bool2 = this.directory;
/*  99 */     long l2 = this.length;
/*     */     
/*     */ 
/* 102 */     this.name = paramFile.getName();
/* 103 */     this.exists = paramFile.exists();
/* 104 */     this.directory = (this.exists ? paramFile.isDirectory() : false);
/* 105 */     this.lastModified = (this.exists ? paramFile.lastModified() : 0L);
/* 106 */     this.length = ((this.exists) && (!this.directory) ? paramFile.length() : 0L);
/*     */     
/*     */ 
/* 109 */     return (this.exists != bool1) || (this.lastModified != l1) || (this.directory != bool2) || (this.length != l2);
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
/*     */   public FileEntry newChildInstance(File paramFile)
/*     */   {
/* 125 */     return new FileEntry(this, paramFile);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileEntry getParent()
/*     */   {
/* 134 */     return this.parent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLevel()
/*     */   {
/* 143 */     return this.parent == null ? 0 : this.parent.getLevel() + 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileEntry[] getChildren()
/*     */   {
/* 154 */     return this.children != null ? this.children : EMPTY_ENTRIES;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setChildren(FileEntry[] paramArrayOfFileEntry)
/*     */   {
/* 163 */     this.children = paramArrayOfFileEntry;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public File getFile()
/*     */   {
/* 172 */     return this.file;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 181 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setName(String paramString)
/*     */   {
/* 190 */     this.name = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getLastModified()
/*     */   {
/* 200 */     return this.lastModified;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLastModified(long paramLong)
/*     */   {
/* 210 */     this.lastModified = paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getLength()
/*     */   {
/* 219 */     return this.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLength(long paramLong)
/*     */   {
/* 228 */     this.length = paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isExists()
/*     */   {
/* 238 */     return this.exists;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setExists(boolean paramBoolean)
/*     */   {
/* 248 */     this.exists = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isDirectory()
/*     */   {
/* 257 */     return this.directory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDirectory(boolean paramBoolean)
/*     */   {
/* 266 */     this.directory = paramBoolean;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\monitor\FileEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */