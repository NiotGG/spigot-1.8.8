/*     */ package com.avaje.ebean.config;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import javax.servlet.ServletContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GlobalProperties
/*     */ {
/*     */   private static volatile PropertyMap globalMap;
/*     */   private static boolean skipPrimaryServer;
/*     */   
/*     */   public static synchronized void setSkipPrimaryServer(boolean skip)
/*     */   {
/*  23 */     skipPrimaryServer = skip;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static synchronized boolean isSkipPrimaryServer()
/*     */   {
/*  30 */     return skipPrimaryServer;
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
/*     */   public static String evaluateExpressions(String val)
/*     */   {
/*  45 */     return getPropertyMap().eval(val);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized void evaluateExpressions()
/*     */   {
/*  53 */     getPropertyMap().evaluateProperties();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized void setServletContext(ServletContext servletContext)
/*     */   {
/*  62 */     PropertyMapLoader.setServletContext(servletContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized ServletContext getServletContext()
/*     */   {
/*  70 */     return PropertyMapLoader.getServletContext();
/*     */   }
/*     */   
/*     */   private static void initPropertyMap()
/*     */   {
/*  75 */     String fileName = System.getenv("EBEAN_PROPS_FILE");
/*  76 */     if (fileName == null) {
/*  77 */       fileName = System.getProperty("ebean.props.file");
/*  78 */       if (fileName == null) {
/*  79 */         fileName = "ebean.properties";
/*     */       }
/*     */     }
/*     */     
/*  83 */     globalMap = PropertyMapLoader.load(null, fileName);
/*  84 */     if (globalMap == null)
/*     */     {
/*     */ 
/*  87 */       globalMap = new PropertyMap();
/*     */     }
/*     */     
/*  90 */     String loaderCn = globalMap.get("ebean.properties.loader");
/*  91 */     if (loaderCn != null)
/*     */     {
/*     */       try
/*     */       {
/*  95 */         Runnable r = (Runnable)ClassUtil.newInstance(loaderCn);
/*  96 */         r.run();
/*     */       } catch (Exception e) {
/*  98 */         String m = "Error creating or running properties loader " + loaderCn;
/*  99 */         throw new RuntimeException(m, e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static synchronized PropertyMap getPropertyMap()
/*     */   {
/* 109 */     if (globalMap == null) {
/* 110 */       initPropertyMap();
/*     */     }
/*     */     
/* 113 */     return globalMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static synchronized String get(String key, String defaultValue)
/*     */   {
/* 120 */     return getPropertyMap().get(key, defaultValue);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static synchronized int getInt(String key, int defaultValue)
/*     */   {
/* 127 */     return getPropertyMap().getInt(key, defaultValue);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static synchronized boolean getBoolean(String key, boolean defaultValue)
/*     */   {
/* 134 */     return getPropertyMap().getBoolean(key, defaultValue);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized String put(String key, String value)
/*     */   {
/* 142 */     return getPropertyMap().putEval(key, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static synchronized void putAll(Map<String, String> keyValueMap)
/*     */   {
/* 149 */     for (Map.Entry<String, String> e : keyValueMap.entrySet()) {
/* 150 */       getPropertyMap().putEval((String)e.getKey(), (String)e.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public static PropertySource getPropertySource(String name) {
/* 155 */     return new ConfigPropertyMap(name);
/*     */   }
/*     */   
/*     */   public static abstract interface PropertySource
/*     */   {
/*     */     public abstract String getServerName();
/*     */     
/*     */     public abstract String get(String paramString1, String paramString2);
/*     */     
/*     */     public abstract int getInt(String paramString, int paramInt);
/*     */     
/*     */     public abstract boolean getBoolean(String paramString, boolean paramBoolean);
/*     */     
/*     */     public abstract <T extends Enum<T>> T getEnum(Class<T> paramClass, String paramString, T paramT);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\GlobalProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */