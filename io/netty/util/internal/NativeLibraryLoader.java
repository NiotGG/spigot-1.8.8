/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NativeLibraryLoader
/*     */ {
/*  35 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NativeLibraryLoader.class);
/*     */   
/*     */ 
/*     */ 
/*     */   private static final String NATIVE_RESOURCE_HOME = "META-INF/native/";
/*     */   
/*     */ 
/*  42 */   private static final String OSNAME = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
/*     */   
/*  44 */   static { String str = SystemPropertyUtil.get("io.netty.native.workdir");
/*  45 */     if (str != null) {
/*  46 */       File localFile = new File(str);
/*  47 */       localFile.mkdirs();
/*     */       try
/*     */       {
/*  50 */         localFile = localFile.getAbsoluteFile();
/*     */       }
/*     */       catch (Exception localException) {}
/*     */       
/*     */ 
/*  55 */       WORKDIR = localFile;
/*  56 */       logger.debug("-Dio.netty.netty.workdir: " + WORKDIR);
/*     */     } else {
/*  58 */       WORKDIR = tmpdir();
/*  59 */       logger.debug("-Dio.netty.netty.workdir: " + WORKDIR + " (io.netty.tmpdir)");
/*     */     }
/*     */   }
/*     */   
/*     */   private static File tmpdir() {
/*     */     File localFile;
/*     */     try {
/*  66 */       localFile = toDirectory(SystemPropertyUtil.get("io.netty.tmpdir"));
/*  67 */       if (localFile != null) {
/*  68 */         logger.debug("-Dio.netty.tmpdir: " + localFile);
/*  69 */         return localFile;
/*     */       }
/*     */       
/*  72 */       localFile = toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
/*  73 */       if (localFile != null) {
/*  74 */         logger.debug("-Dio.netty.tmpdir: " + localFile + " (java.io.tmpdir)");
/*  75 */         return localFile;
/*     */       }
/*     */       
/*     */ 
/*  79 */       if (isWindows()) {
/*  80 */         localFile = toDirectory(System.getenv("TEMP"));
/*  81 */         if (localFile != null) {
/*  82 */           logger.debug("-Dio.netty.tmpdir: " + localFile + " (%TEMP%)");
/*  83 */           return localFile;
/*     */         }
/*     */         
/*  86 */         String str = System.getenv("USERPROFILE");
/*  87 */         if (str != null) {
/*  88 */           localFile = toDirectory(str + "\\AppData\\Local\\Temp");
/*  89 */           if (localFile != null) {
/*  90 */             logger.debug("-Dio.netty.tmpdir: " + localFile + " (%USERPROFILE%\\AppData\\Local\\Temp)");
/*  91 */             return localFile;
/*     */           }
/*     */           
/*  94 */           localFile = toDirectory(str + "\\Local Settings\\Temp");
/*  95 */           if (localFile != null) {
/*  96 */             logger.debug("-Dio.netty.tmpdir: " + localFile + " (%USERPROFILE%\\Local Settings\\Temp)");
/*  97 */             return localFile;
/*     */           }
/*     */         }
/*     */       } else {
/* 101 */         localFile = toDirectory(System.getenv("TMPDIR"));
/* 102 */         if (localFile != null) {
/* 103 */           logger.debug("-Dio.netty.tmpdir: " + localFile + " ($TMPDIR)");
/* 104 */           return localFile;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/*     */ 
/*     */ 
/* 112 */     if (isWindows()) {
/* 113 */       localFile = new File("C:\\Windows\\Temp");
/*     */     } else {
/* 115 */       localFile = new File("/tmp");
/*     */     }
/*     */     
/* 118 */     logger.warn("Failed to get the temporary directory; falling back to: " + localFile);
/* 119 */     return localFile;
/*     */   }
/*     */   
/*     */   private static File toDirectory(String paramString)
/*     */   {
/* 124 */     if (paramString == null) {
/* 125 */       return null;
/*     */     }
/*     */     
/* 128 */     File localFile = new File(paramString);
/* 129 */     localFile.mkdirs();
/*     */     
/* 131 */     if (!localFile.isDirectory()) {
/* 132 */       return null;
/*     */     }
/*     */     try
/*     */     {
/* 136 */       return localFile.getAbsoluteFile();
/*     */     } catch (Exception localException) {}
/* 138 */     return localFile;
/*     */   }
/*     */   
/*     */   private static boolean isWindows()
/*     */   {
/* 143 */     return OSNAME.startsWith("windows");
/*     */   }
/*     */   
/*     */   private static boolean isOSX() {
/* 147 */     return (OSNAME.startsWith("macosx")) || (OSNAME.startsWith("osx"));
/*     */   }
/*     */   
/*     */ 
/*     */   private static final File WORKDIR;
/*     */   public static void load(String paramString, ClassLoader paramClassLoader)
/*     */   {
/* 154 */     String str1 = System.mapLibraryName(paramString);
/* 155 */     String str2 = "META-INF/native/" + str1;
/*     */     
/* 157 */     URL localURL = paramClassLoader.getResource(str2);
/* 158 */     if ((localURL == null) && (isOSX())) {
/* 159 */       if (str2.endsWith(".jnilib")) {
/* 160 */         localURL = paramClassLoader.getResource("META-INF/native/lib" + paramString + ".dynlib");
/*     */       } else {
/* 162 */         localURL = paramClassLoader.getResource("META-INF/native/lib" + paramString + ".jnilib");
/*     */       }
/*     */     }
/*     */     
/* 166 */     if (localURL == null)
/*     */     {
/* 168 */       System.loadLibrary(paramString);
/* 169 */       return;
/*     */     }
/*     */     
/* 172 */     int i = str1.lastIndexOf('.');
/* 173 */     String str3 = str1.substring(0, i);
/* 174 */     String str4 = str1.substring(i, str1.length());
/* 175 */     InputStream localInputStream = null;
/* 176 */     FileOutputStream localFileOutputStream = null;
/* 177 */     File localFile = null;
/* 178 */     int j = 0;
/*     */     try {
/* 180 */       localFile = File.createTempFile(str3, str4, WORKDIR);
/* 181 */       localInputStream = localURL.openStream();
/* 182 */       localFileOutputStream = new FileOutputStream(localFile);
/*     */       
/* 184 */       byte[] arrayOfByte = new byte[' '];
/*     */       int k;
/* 186 */       while ((k = localInputStream.read(arrayOfByte)) > 0) {
/* 187 */         localFileOutputStream.write(arrayOfByte, 0, k);
/*     */       }
/* 189 */       localFileOutputStream.flush();
/* 190 */       localFileOutputStream.close();
/* 191 */       localFileOutputStream = null;
/*     */       
/* 193 */       System.load(localFile.getPath());
/* 194 */       j = 1;
/*     */     } catch (Exception localException) {
/* 196 */       throw ((UnsatisfiedLinkError)new UnsatisfiedLinkError("could not load a native library: " + paramString).initCause(localException));
/*     */     }
/*     */     finally {
/* 199 */       if (localInputStream != null) {
/*     */         try {
/* 201 */           localInputStream.close();
/*     */         }
/*     */         catch (IOException localIOException3) {}
/*     */       }
/*     */       
/* 206 */       if (localFileOutputStream != null) {
/*     */         try {
/* 208 */           localFileOutputStream.close();
/*     */         }
/*     */         catch (IOException localIOException4) {}
/*     */       }
/*     */       
/* 213 */       if (localFile != null) {
/* 214 */         if (j != 0) {
/* 215 */           localFile.deleteOnExit();
/*     */         }
/* 217 */         else if (!localFile.delete()) {
/* 218 */           localFile.deleteOnExit();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\NativeLibraryLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */