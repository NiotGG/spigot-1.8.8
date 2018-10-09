/*     */ package org.apache.logging.log4j.core.lookup;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginManager;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
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
/*     */ 
/*     */ public class Interpolator
/*     */   implements StrLookup
/*     */ {
/*  33 */   private static final Logger LOGGER = ;
/*     */   
/*     */ 
/*     */   private static final char PREFIX_SEPARATOR = ':';
/*     */   
/*  38 */   private final Map<String, StrLookup> lookups = new HashMap();
/*     */   private final StrLookup defaultLookup;
/*     */   
/*     */   public Interpolator(StrLookup paramStrLookup)
/*     */   {
/*  43 */     this.defaultLookup = (paramStrLookup == null ? new MapLookup(new HashMap()) : paramStrLookup);
/*  44 */     PluginManager localPluginManager = new PluginManager("Lookup");
/*  45 */     localPluginManager.collectPlugins();
/*  46 */     Map localMap = localPluginManager.getPlugins();
/*     */     
/*  48 */     for (Map.Entry localEntry : localMap.entrySet())
/*     */     {
/*  50 */       Class localClass = ((PluginType)localEntry.getValue()).getPluginClass();
/*     */       try {
/*  52 */         this.lookups.put(localEntry.getKey(), localClass.newInstance());
/*     */       } catch (Exception localException) {
/*  54 */         LOGGER.error("Unable to create Lookup for " + (String)localEntry.getKey(), localException);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Interpolator()
/*     */   {
/*  63 */     this.defaultLookup = new MapLookup(new HashMap());
/*  64 */     this.lookups.put("sys", new SystemPropertiesLookup());
/*  65 */     this.lookups.put("env", new EnvironmentLookup());
/*  66 */     this.lookups.put("jndi", new JndiLookup());
/*     */     try {
/*  68 */       if (Class.forName("javax.servlet.ServletContext") != null) {
/*  69 */         this.lookups.put("web", new WebLookup());
/*     */       }
/*     */     } catch (ClassNotFoundException localClassNotFoundException) {
/*  72 */       LOGGER.debug("ServletContext not present - WebLookup not added");
/*     */     } catch (Exception localException) {
/*  74 */       LOGGER.error("Unable to locate ServletContext", localException);
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
/*     */   public String lookup(String paramString)
/*     */   {
/*  92 */     return lookup(null, paramString);
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
/*     */   public String lookup(LogEvent paramLogEvent, String paramString)
/*     */   {
/* 110 */     if (paramString == null) {
/* 111 */       return null;
/*     */     }
/*     */     
/* 114 */     int i = paramString.indexOf(':');
/* 115 */     if (i >= 0) {
/* 116 */       String str1 = paramString.substring(0, i);
/* 117 */       String str2 = paramString.substring(i + 1);
/* 118 */       StrLookup localStrLookup = (StrLookup)this.lookups.get(str1);
/* 119 */       String str3 = null;
/* 120 */       if (localStrLookup != null) {
/* 121 */         str3 = paramLogEvent == null ? localStrLookup.lookup(str2) : localStrLookup.lookup(paramLogEvent, str2);
/*     */       }
/*     */       
/* 124 */       if (str3 != null) {
/* 125 */         return str3;
/*     */       }
/* 127 */       paramString = paramString.substring(i + 1);
/*     */     }
/* 129 */     if (this.defaultLookup != null) {
/* 130 */       return paramLogEvent == null ? this.defaultLookup.lookup(paramString) : this.defaultLookup.lookup(paramLogEvent, paramString);
/*     */     }
/* 132 */     return null;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 137 */     StringBuilder localStringBuilder = new StringBuilder();
/* 138 */     for (String str : this.lookups.keySet()) {
/* 139 */       if (localStringBuilder.length() == 0) {
/* 140 */         localStringBuilder.append("{");
/*     */       } else {
/* 142 */         localStringBuilder.append(", ");
/*     */       }
/*     */       
/* 145 */       localStringBuilder.append(str);
/*     */     }
/* 147 */     if (localStringBuilder.length() > 0) {
/* 148 */       localStringBuilder.append("}");
/*     */     }
/* 150 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\Interpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */