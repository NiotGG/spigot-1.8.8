/*     */ package org.apache.logging.log4j.core.config.plugins;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.helpers.Closer;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PluginManager
/*     */ {
/*     */   private static final long NANOS_PER_SECOND = 1000000000L;
/*  48 */   private static ConcurrentMap<String, ConcurrentMap<String, PluginType<?>>> pluginTypeMap = new ConcurrentHashMap();
/*     */   
/*     */ 
/*  51 */   private static final CopyOnWriteArrayList<String> PACKAGES = new CopyOnWriteArrayList();
/*     */   
/*     */   private static final String PATH = "org/apache/logging/log4j/core/config/plugins/";
/*     */   private static final String FILENAME = "Log4j2Plugins.dat";
/*     */   private static final String LOG4J_PACKAGES = "org.apache.logging.log4j.core";
/*  56 */   private static final Logger LOGGER = StatusLogger.getLogger();
/*     */   
/*     */   private static String rootDir;
/*     */   
/*  60 */   private Map<String, PluginType<?>> plugins = new HashMap();
/*     */   
/*     */   private final String type;
/*     */   
/*     */   private final Class<?> clazz;
/*     */   
/*     */ 
/*     */   public PluginManager(String paramString)
/*     */   {
/*  69 */     this.type = paramString;
/*  70 */     this.clazz = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PluginManager(String paramString, Class<?> paramClass)
/*     */   {
/*  79 */     this.type = paramString;
/*  80 */     this.clazz = paramClass;
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws Exception {
/*  84 */     if ((paramArrayOfString == null) || (paramArrayOfString.length < 1)) {
/*  85 */       System.err.println("A target directory must be specified");
/*  86 */       System.exit(-1);
/*     */     }
/*  88 */     rootDir = paramArrayOfString[0] + "/";
/*     */     
/*  90 */     PluginManager localPluginManager = new PluginManager("Core");
/*  91 */     String str = paramArrayOfString.length == 2 ? paramArrayOfString[1] : null;
/*     */     
/*  93 */     localPluginManager.collectPlugins(false, str);
/*  94 */     encode(pluginTypeMap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void addPackage(String paramString)
/*     */   {
/* 102 */     if (PACKAGES.addIfAbsent(paramString))
/*     */     {
/*     */ 
/* 105 */       pluginTypeMap.clear();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PluginType<?> getPluginType(String paramString)
/*     */   {
/* 115 */     return (PluginType)this.plugins.get(paramString.toLowerCase());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, PluginType<?>> getPlugins()
/*     */   {
/* 123 */     return this.plugins;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void collectPlugins()
/*     */   {
/* 130 */     collectPlugins(true, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void collectPlugins(boolean paramBoolean, String paramString)
/*     */   {
/* 141 */     if (pluginTypeMap.containsKey(this.type)) {
/* 142 */       this.plugins = ((Map)pluginTypeMap.get(this.type));
/* 143 */       paramBoolean = false;
/*     */     }
/* 145 */     long l1 = System.nanoTime();
/* 146 */     ResolverUtil localResolverUtil = new ResolverUtil();
/* 147 */     ClassLoader localClassLoader = Loader.getClassLoader();
/* 148 */     if (localClassLoader != null) {
/* 149 */       localResolverUtil.setClassLoader(localClassLoader);
/*     */     }
/* 151 */     if (paramBoolean) {
/* 152 */       localObject1 = decode(localClassLoader);
/* 153 */       if (localObject1 != null) {
/* 154 */         pluginTypeMap = (ConcurrentMap)localObject1;
/* 155 */         this.plugins = ((Map)((ConcurrentMap)localObject1).get(this.type));
/*     */       } else {
/* 157 */         LOGGER.warn("Plugin preloads not available from class loader {}", new Object[] { localClassLoader });
/*     */       }
/*     */     }
/* 160 */     if ((this.plugins == null) || (this.plugins.size() == 0)) {
/* 161 */       if (paramString == null) {
/* 162 */         if (!PACKAGES.contains("org.apache.logging.log4j.core")) {
/* 163 */           PACKAGES.add("org.apache.logging.log4j.core");
/*     */         }
/*     */       } else {
/* 166 */         localObject1 = paramString.split(",");
/* 167 */         for (localObject5 : localObject1) {
/* 168 */           PACKAGES.add(localObject5);
/*     */         }
/*     */       }
/*     */     }
/* 172 */     Object localObject1 = new PluginTest(this.clazz);
/* 173 */     for (??? = PACKAGES.iterator(); ((Iterator)???).hasNext();) { localObject3 = (String)((Iterator)???).next();
/* 174 */       localResolverUtil.findInPackage((ResolverUtil.Test)localObject1, (String)localObject3); }
/*     */     Object localObject3;
/* 176 */     for (??? = localResolverUtil.getClasses().iterator(); ((Iterator)???).hasNext();) { localObject3 = (Class)((Iterator)???).next();
/* 177 */       localObject4 = (Plugin)((Class)localObject3).getAnnotation(Plugin.class);
/* 178 */       localObject5 = ((Plugin)localObject4).category();
/* 179 */       if (!pluginTypeMap.containsKey(localObject5)) {
/* 180 */         pluginTypeMap.putIfAbsent(localObject5, new ConcurrentHashMap());
/*     */       }
/* 182 */       Map localMap = (Map)pluginTypeMap.get(localObject5);
/* 183 */       String str1 = ((Plugin)localObject4).elementType().equals("") ? ((Plugin)localObject4).name() : ((Plugin)localObject4).elementType();
/* 184 */       PluginType localPluginType = new PluginType((Class)localObject3, str1, ((Plugin)localObject4).printObject(), ((Plugin)localObject4).deferChildren());
/* 185 */       localMap.put(((Plugin)localObject4).name().toLowerCase(), localPluginType);
/* 186 */       PluginAliases localPluginAliases = (PluginAliases)((Class)localObject3).getAnnotation(PluginAliases.class);
/* 187 */       if (localPluginAliases != null) {
/* 188 */         for (String str2 : localPluginAliases.value()) {
/* 189 */           str1 = ((Plugin)localObject4).elementType().equals("") ? str2 : ((Plugin)localObject4).elementType();
/* 190 */           localPluginType = new PluginType((Class)localObject3, str1, ((Plugin)localObject4).printObject(), ((Plugin)localObject4).deferChildren());
/* 191 */           localMap.put(str2.trim().toLowerCase(), localPluginType);
/*     */         }
/*     */       }
/*     */     }
/* 195 */     long l2 = System.nanoTime() - l1;
/* 196 */     this.plugins = ((Map)pluginTypeMap.get(this.type));
/* 197 */     Object localObject4 = new StringBuilder("Generated plugins");
/* 198 */     ((StringBuilder)localObject4).append(" in ");
/* 199 */     Object localObject5 = new DecimalFormat("#0");
/* 200 */     long l3 = l2 / 1000000000L;
/* 201 */     l2 %= 1000000000L;
/* 202 */     ((StringBuilder)localObject4).append(((DecimalFormat)localObject5).format(l3)).append('.');
/* 203 */     localObject5 = new DecimalFormat("000000000");
/* 204 */     ((StringBuilder)localObject4).append(((DecimalFormat)localObject5).format(l2)).append(" seconds");
/* 205 */     LOGGER.debug(((StringBuilder)localObject4).toString());
/*     */   }
/*     */   
/*     */   private static ConcurrentMap<String, ConcurrentMap<String, PluginType<?>>> decode(ClassLoader paramClassLoader)
/*     */   {
/*     */     Enumeration localEnumeration;
/*     */     try {
/* 212 */       localEnumeration = paramClassLoader.getResources("org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat");
/*     */     } catch (IOException localIOException) {
/* 214 */       LOGGER.warn("Unable to preload plugins", localIOException);
/* 215 */       return null;
/*     */     }
/* 217 */     ConcurrentHashMap localConcurrentHashMap = new ConcurrentHashMap();
/*     */     
/* 219 */     while (localEnumeration.hasMoreElements()) {
/* 220 */       DataInputStream localDataInputStream = null;
/*     */       try {
/* 222 */         URL localURL = (URL)localEnumeration.nextElement();
/* 223 */         LOGGER.debug("Found Plugin Map at {}", new Object[] { localURL.toExternalForm() });
/* 224 */         localInputStream = localURL.openStream();
/* 225 */         BufferedInputStream localBufferedInputStream = new BufferedInputStream(localInputStream);
/* 226 */         localDataInputStream = new DataInputStream(localBufferedInputStream);
/* 227 */         int i = localDataInputStream.readInt();
/* 228 */         for (int j = 0; j < i; j++) {
/* 229 */           String str1 = localDataInputStream.readUTF();
/* 230 */           int k = localDataInputStream.readInt();
/* 231 */           Object localObject1 = (ConcurrentMap)localConcurrentHashMap.get(str1);
/* 232 */           if (localObject1 == null) {
/* 233 */             localObject1 = new ConcurrentHashMap(i);
/*     */           }
/* 235 */           for (int m = 0; m < k; m++) {
/* 236 */             String str2 = localDataInputStream.readUTF();
/* 237 */             String str3 = localDataInputStream.readUTF();
/* 238 */             String str4 = localDataInputStream.readUTF();
/* 239 */             boolean bool1 = localDataInputStream.readBoolean();
/* 240 */             boolean bool2 = localDataInputStream.readBoolean();
/* 241 */             Class localClass = Class.forName(str3);
/* 242 */             ((ConcurrentMap)localObject1).put(str2, new PluginType(localClass, str4, bool1, bool2));
/*     */           }
/* 244 */           localConcurrentHashMap.putIfAbsent(str1, localObject1);
/*     */         }
/*     */       } catch (Exception localException) { InputStream localInputStream;
/* 247 */         LOGGER.warn("Unable to preload plugins", localException);
/* 248 */         return null;
/*     */       } finally {
/* 250 */         Closer.closeSilent(localDataInputStream);
/*     */       }
/*     */     }
/* 253 */     return localConcurrentHashMap.size() == 0 ? null : localConcurrentHashMap;
/*     */   }
/*     */   
/*     */   private static void encode(ConcurrentMap<String, ConcurrentMap<String, PluginType<?>>> paramConcurrentMap) {
/* 257 */     String str = rootDir + "org/apache/logging/log4j/core/config/plugins/" + "Log4j2Plugins.dat";
/* 258 */     DataOutputStream localDataOutputStream = null;
/*     */     try {
/* 260 */       File localFile = new File(rootDir + "org/apache/logging/log4j/core/config/plugins/");
/* 261 */       localFile.mkdirs();
/* 262 */       FileOutputStream localFileOutputStream = new FileOutputStream(str);
/* 263 */       BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localFileOutputStream);
/* 264 */       localDataOutputStream = new DataOutputStream(localBufferedOutputStream);
/* 265 */       localDataOutputStream.writeInt(paramConcurrentMap.size());
/* 266 */       for (Map.Entry localEntry1 : paramConcurrentMap.entrySet()) {
/* 267 */         localDataOutputStream.writeUTF((String)localEntry1.getKey());
/* 268 */         localDataOutputStream.writeInt(((ConcurrentMap)localEntry1.getValue()).size());
/* 269 */         for (Map.Entry localEntry2 : ((ConcurrentMap)localEntry1.getValue()).entrySet()) {
/* 270 */           localDataOutputStream.writeUTF((String)localEntry2.getKey());
/* 271 */           PluginType localPluginType = (PluginType)localEntry2.getValue();
/* 272 */           localDataOutputStream.writeUTF(localPluginType.getPluginClass().getName());
/* 273 */           localDataOutputStream.writeUTF(localPluginType.getElementName());
/* 274 */           localDataOutputStream.writeBoolean(localPluginType.isObjectPrintable());
/* 275 */           localDataOutputStream.writeBoolean(localPluginType.isDeferChildren());
/*     */         }
/*     */       }
/*     */     } catch (Exception localException) {
/* 279 */       localException.printStackTrace();
/*     */     } finally {
/* 281 */       Closer.closeSilent(localDataOutputStream);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class PluginTest
/*     */     extends ResolverUtil.ClassTest
/*     */   {
/*     */     private final Class<?> isA;
/*     */     
/*     */ 
/*     */ 
/*     */     public PluginTest(Class<?> paramClass)
/*     */     {
/* 297 */       this.isA = paramClass;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean matches(Class<?> paramClass)
/*     */     {
/* 307 */       return (paramClass != null) && (paramClass.isAnnotationPresent(Plugin.class)) && ((this.isA == null) || (this.isA.isAssignableFrom(paramClass)));
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 313 */       StringBuilder localStringBuilder = new StringBuilder("annotated with @" + Plugin.class.getSimpleName());
/* 314 */       if (this.isA != null) {
/* 315 */         localStringBuilder.append(" is assignable to " + this.isA.getSimpleName());
/*     */       }
/* 317 */       return localStringBuilder.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\plugins\PluginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */