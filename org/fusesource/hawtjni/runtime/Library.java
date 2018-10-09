/*     */ package org.fusesource.hawtjni.runtime;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Library
/*     */ {
/*  67 */   static final String SLASH = System.getProperty("file.separator");
/*     */   private final String name;
/*     */   private final String version;
/*     */   private final ClassLoader classLoader;
/*     */   private boolean loaded;
/*     */   
/*     */   public Library(String name)
/*     */   {
/*  75 */     this(name, null, null);
/*     */   }
/*     */   
/*     */   public Library(String name, Class<?> clazz) {
/*  79 */     this(name, version(clazz), clazz.getClassLoader());
/*     */   }
/*     */   
/*     */   public Library(String name, String version) {
/*  83 */     this(name, version, null);
/*     */   }
/*     */   
/*     */   public Library(String name, String version, ClassLoader classLoader) {
/*  87 */     if (name == null) {
/*  88 */       throw new IllegalArgumentException("name cannot be null");
/*     */     }
/*  90 */     this.name = name;
/*  91 */     this.version = version;
/*  92 */     this.classLoader = classLoader;
/*     */   }
/*     */   
/*     */   private static String version(Class<?> clazz) {
/*     */     try {
/*  97 */       return clazz.getPackage().getImplementationVersion();
/*     */     }
/*     */     catch (Throwable e) {}
/* 100 */     return null;
/*     */   }
/*     */   
/*     */   public static String getOperatingSystem() {
/* 104 */     String name = System.getProperty("os.name").toLowerCase().trim();
/* 105 */     if (name.startsWith("linux")) {
/* 106 */       return "linux";
/*     */     }
/* 108 */     if (name.startsWith("mac os x")) {
/* 109 */       return "osx";
/*     */     }
/* 111 */     if (name.startsWith("win")) {
/* 112 */       return "windows";
/*     */     }
/* 114 */     return name.replaceAll("\\W+", "_");
/*     */   }
/*     */   
/*     */   public static String getPlatform()
/*     */   {
/* 119 */     return getOperatingSystem() + getBitModel();
/*     */   }
/*     */   
/*     */   public static int getBitModel() {
/* 123 */     String prop = System.getProperty("sun.arch.data.model");
/* 124 */     if (prop == null) {
/* 125 */       prop = System.getProperty("com.ibm.vm.bitmode");
/*     */     }
/* 127 */     if (prop != null) {
/* 128 */       return Integer.parseInt(prop);
/*     */     }
/* 130 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void load()
/*     */   {
/* 137 */     if (this.loaded) {
/* 138 */       return;
/*     */     }
/* 140 */     doLoad();
/* 141 */     this.loaded = true;
/*     */   }
/*     */   
/*     */   private void doLoad()
/*     */   {
/* 146 */     String version = System.getProperty("library." + this.name + ".version");
/* 147 */     if (version == null) {
/* 148 */       version = this.version;
/*     */     }
/* 150 */     ArrayList<String> errors = new ArrayList();
/*     */     
/*     */ 
/* 153 */     String customPath = System.getProperty("library." + this.name + ".path");
/* 154 */     if (customPath != null) {
/* 155 */       if (version != null) if (load(errors, file(new String[] { customPath, map(this.name + "-" + version) })))
/* 156 */           return;
/* 157 */       if (load(errors, file(new String[] { customPath, map(this.name) }))) {
/* 158 */         return;
/*     */       }
/*     */     }
/*     */     
/* 162 */     if ((version != null) && (load(errors, this.name + getBitModel() + "-" + version)))
/* 163 */       return;
/* 164 */     if ((version != null) && (load(errors, this.name + "-" + version)))
/* 165 */       return;
/* 166 */     if (load(errors, this.name)) {
/* 167 */       return;
/*     */     }
/*     */     
/*     */ 
/* 171 */     if (this.classLoader != null) {
/* 172 */       if (exractAndLoad(errors, version, customPath, getPlatformSpecifcResourcePath()))
/* 173 */         return;
/* 174 */       if (exractAndLoad(errors, version, customPath, getOperatingSystemSpecifcResourcePath())) {
/* 175 */         return;
/*     */       }
/* 177 */       if (exractAndLoad(errors, version, customPath, getResorucePath())) {
/* 178 */         return;
/*     */       }
/*     */     }
/*     */     
/* 182 */     throw new UnsatisfiedLinkError("Could not load library. Reasons: " + errors.toString());
/*     */   }
/*     */   
/*     */   public final String getOperatingSystemSpecifcResourcePath() {
/* 186 */     return getPlatformSpecifcResourcePath(getOperatingSystem());
/*     */   }
/*     */   
/* 189 */   public final String getPlatformSpecifcResourcePath() { return getPlatformSpecifcResourcePath(getPlatform()); }
/*     */   
/*     */   public final String getPlatformSpecifcResourcePath(String platform) {
/* 192 */     return "META-INF/native/" + platform + "/" + map(this.name);
/*     */   }
/*     */   
/*     */   public final String getResorucePath() {
/* 196 */     return "META-INF/native/" + map(this.name);
/*     */   }
/*     */   
/*     */   public final String getLibraryFileName() {
/* 200 */     return map(this.name);
/*     */   }
/*     */   
/*     */   private boolean exractAndLoad(ArrayList<String> errors, String version, String customPath, String resourcePath)
/*     */   {
/* 205 */     URL resource = this.classLoader.getResource(resourcePath);
/* 206 */     if (resource != null)
/*     */     {
/* 208 */       String libName = this.name + "-" + getBitModel();
/* 209 */       if (version != null) {
/* 210 */         libName = libName + "-" + version;
/*     */       }
/* 212 */       String[] libNameParts = map(libName).split("\\.");
/* 213 */       String prefix = libNameParts[0] + "-";
/* 214 */       String suffix = "." + libNameParts[1];
/*     */       
/* 216 */       if (customPath != null)
/*     */       {
/* 218 */         File target = extract(errors, resource, prefix, suffix, file(new String[] { customPath }));
/* 219 */         if ((target != null) && 
/* 220 */           (load(errors, target))) {
/* 221 */           return true;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 227 */       customPath = System.getProperty("java.io.tmpdir");
/* 228 */       File target = extract(errors, resource, prefix, suffix, file(new String[] { customPath }));
/* 229 */       if ((target != null) && 
/* 230 */         (load(errors, target))) {
/* 231 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 235 */     return false;
/*     */   }
/*     */   
/*     */   private File file(String... paths) {
/* 239 */     File rc = null;
/* 240 */     for (String path : paths) {
/* 241 */       if (rc == null) {
/* 242 */         rc = new File(path);
/*     */       } else {
/* 244 */         rc = new File(rc, path);
/*     */       }
/*     */     }
/* 247 */     return rc;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private String map(String libName)
/*     */   {
/* 255 */     libName = System.mapLibraryName(libName);
/* 256 */     String ext = ".dylib";
/* 257 */     if (libName.endsWith(ext)) {
/* 258 */       libName = libName.substring(0, libName.length() - ext.length()) + ".jnilib";
/*     */     }
/* 260 */     return libName;
/*     */   }
/*     */   
/*     */   private File extract(ArrayList<String> errors, URL source, String prefix, String suffix, File directory) {
/* 264 */     File target = null;
/*     */     try {
/* 266 */       FileOutputStream os = null;
/* 267 */       InputStream is = null;
/*     */       try {
/* 269 */         target = File.createTempFile(prefix, suffix, directory);
/* 270 */         is = source.openStream();
/* 271 */         byte[] buffer; if (is != null) {
/* 272 */           buffer = new byte['က'];
/* 273 */           os = new FileOutputStream(target);
/*     */           int read;
/* 275 */           while ((read = is.read(buffer)) != -1) {
/* 276 */             os.write(buffer, 0, read);
/*     */           }
/* 278 */           chmod("755", target);
/*     */         }
/* 280 */         target.deleteOnExit();
/* 281 */         return target;
/*     */       } finally {
/* 283 */         close(os);
/* 284 */         close(is);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 292 */       return null;
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 287 */       if (target != null) {
/* 288 */         target.delete();
/*     */       }
/* 290 */       errors.add(e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void close(Closeable file)
/*     */   {
/* 296 */     if (file != null) {
/*     */       try {
/* 298 */         file.close();
/*     */       }
/*     */       catch (Exception ignore) {}
/*     */     }
/*     */   }
/*     */   
/*     */   private void chmod(String permision, File path) {
/* 305 */     if (getPlatform().startsWith("windows"))
/* 306 */       return;
/*     */     try {
/* 308 */       Runtime.getRuntime().exec(new String[] { "chmod", permision, path.getCanonicalPath() }).waitFor();
/*     */     }
/*     */     catch (Throwable e) {}
/*     */   }
/*     */   
/*     */   private boolean load(ArrayList<String> errors, File lib) {
/*     */     try {
/* 315 */       System.load(lib.getPath());
/* 316 */       return true;
/*     */     } catch (UnsatisfiedLinkError e) {
/* 318 */       errors.add(e.getMessage());
/*     */     }
/* 320 */     return false;
/*     */   }
/*     */   
/*     */   private boolean load(ArrayList<String> errors, String lib) {
/*     */     try {
/* 325 */       System.loadLibrary(lib);
/* 326 */       return true;
/*     */     } catch (UnsatisfiedLinkError e) {
/* 328 */       errors.add(e.getMessage());
/*     */     }
/* 330 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\fusesource\hawtjni\runtime\Library.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */