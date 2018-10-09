/*     */ package org.bukkit.craftbukkit.libs.jline.console.completer;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileNameCompleter
/*     */   implements Completer
/*     */ {
/*     */   private static final boolean OS_IS_WINDOWS;
/*     */   
/*     */   static
/*     */   {
/*  46 */     String os = Configuration.getOsName();
/*  47 */     OS_IS_WINDOWS = os.contains("windows");
/*     */   }
/*     */   
/*     */   public int complete(String buffer, int cursor, List<CharSequence> candidates)
/*     */   {
/*  52 */     Preconditions.checkNotNull(candidates);
/*     */     
/*  54 */     if (buffer == null) {
/*  55 */       buffer = "";
/*     */     }
/*     */     
/*  58 */     if (OS_IS_WINDOWS) {
/*  59 */       buffer = buffer.replace('/', '\\');
/*     */     }
/*     */     
/*  62 */     String translated = buffer;
/*     */     
/*  64 */     File homeDir = getUserHome();
/*     */     
/*     */ 
/*  67 */     if (translated.startsWith("~" + separator())) {
/*  68 */       translated = homeDir.getPath() + translated.substring(1);
/*     */     }
/*  70 */     else if (translated.startsWith("~")) {
/*  71 */       translated = homeDir.getParentFile().getAbsolutePath();
/*     */     }
/*  73 */     else if (!new File(translated).isAbsolute()) {
/*  74 */       String cwd = getUserDir().getAbsolutePath();
/*  75 */       translated = cwd + separator() + translated;
/*     */     }
/*     */     
/*  78 */     File file = new File(translated);
/*     */     File dir;
/*     */     File dir;
/*  81 */     if (translated.endsWith(separator())) {
/*  82 */       dir = file;
/*     */     }
/*     */     else {
/*  85 */       dir = file.getParentFile();
/*     */     }
/*     */     
/*  88 */     File[] entries = dir == null ? new File[0] : dir.listFiles();
/*     */     
/*  90 */     return matchFiles(buffer, translated, entries, candidates);
/*     */   }
/*     */   
/*     */   protected String separator() {
/*  94 */     return File.separator;
/*     */   }
/*     */   
/*     */   protected File getUserHome() {
/*  98 */     return Configuration.getUserHome();
/*     */   }
/*     */   
/*     */   protected File getUserDir() {
/* 102 */     return new File(".");
/*     */   }
/*     */   
/*     */   protected int matchFiles(String buffer, String translated, File[] files, List<CharSequence> candidates) {
/* 106 */     if (files == null) {
/* 107 */       return -1;
/*     */     }
/*     */     
/* 110 */     int matches = 0;
/*     */     
/*     */ 
/* 113 */     for (File file : files) {
/* 114 */       if (file.getAbsolutePath().startsWith(translated)) {
/* 115 */         matches++;
/*     */       }
/*     */     }
/* 118 */     for (File file : files) {
/* 119 */       if (file.getAbsolutePath().startsWith(translated)) {
/* 120 */         CharSequence name = file.getName() + ((matches == 1) && (file.isDirectory()) ? separator() : " ");
/* 121 */         candidates.add(render(file, name).toString());
/*     */       }
/*     */     }
/*     */     
/* 125 */     int index = buffer.lastIndexOf(separator());
/*     */     
/* 127 */     return index + separator().length();
/*     */   }
/*     */   
/*     */   protected CharSequence render(File file, CharSequence name) {
/* 131 */     return name;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\FileNameCompleter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */