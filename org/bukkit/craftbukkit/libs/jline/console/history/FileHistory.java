/*     */ package org.bukkit.craftbukkit.libs.jline.console.history;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.Flushable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Preconditions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileHistory
/*     */   extends MemoryHistory
/*     */   implements PersistentHistory, Flushable
/*     */ {
/*     */   private final File file;
/*     */   
/*     */   public FileHistory(File file)
/*     */     throws IOException
/*     */   {
/*  43 */     this.file = ((File)Preconditions.checkNotNull(file));
/*  44 */     load(file);
/*     */   }
/*     */   
/*     */   public File getFile() {
/*  48 */     return this.file;
/*     */   }
/*     */   
/*     */   public void load(File file) throws IOException {
/*  52 */     Preconditions.checkNotNull(file);
/*  53 */     if (file.exists()) {
/*  54 */       Log.trace(new Object[] { "Loading history from: ", file });
/*  55 */       load(new FileReader(file));
/*     */     }
/*     */   }
/*     */   
/*     */   public void load(InputStream input) throws IOException {
/*  60 */     Preconditions.checkNotNull(input);
/*  61 */     load(new InputStreamReader(input));
/*     */   }
/*     */   
/*     */   public void load(Reader reader) throws IOException {
/*  65 */     Preconditions.checkNotNull(reader);
/*  66 */     BufferedReader input = new BufferedReader(reader);
/*     */     
/*     */     String item;
/*  69 */     while ((item = input.readLine()) != null) {
/*  70 */       internalAdd(item);
/*     */     }
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/*  75 */     Log.trace(new Object[] { "Flushing history" });
/*     */     
/*  77 */     if (!this.file.exists()) {
/*  78 */       File dir = this.file.getParentFile();
/*  79 */       if ((!dir.exists()) && (!dir.mkdirs())) {
/*  80 */         Log.warn(new Object[] { "Failed to create directory: ", dir });
/*     */       }
/*  82 */       if (!this.file.createNewFile()) {
/*  83 */         Log.warn(new Object[] { "Failed to create file: ", this.file });
/*     */       }
/*     */     }
/*     */     
/*  87 */     PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(this.file)));
/*     */     try {
/*  89 */       for (History.Entry entry : this) {
/*  90 */         out.println(entry.value());
/*     */       }
/*     */     }
/*     */     finally {
/*  94 */       out.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void purge() throws IOException {
/*  99 */     Log.trace(new Object[] { "Purging history" });
/*     */     
/* 101 */     clear();
/*     */     
/* 103 */     if (!this.file.delete()) {
/* 104 */       Log.warn(new Object[] { "Failed to delete history file: ", this.file });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\history\FileHistory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */