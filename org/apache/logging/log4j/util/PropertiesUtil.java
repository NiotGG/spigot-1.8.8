/*     */ package org.apache.logging.log4j.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ 
/*     */ 
/*     */ public class PropertiesUtil
/*     */ {
/*  31 */   private static final PropertiesUtil LOG4J_PROPERTIES = new PropertiesUtil("log4j2.component.properties");
/*     */   
/*  33 */   private static final Logger LOGGER = StatusLogger.getLogger();
/*     */   private final Properties props;
/*     */   
/*     */   public PropertiesUtil(Properties paramProperties)
/*     */   {
/*  38 */     this.props = paramProperties;
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
/*     */   static Properties loadClose(InputStream paramInputStream, Object paramObject)
/*     */   {
/*  53 */     localProperties = new Properties();
/*  54 */     if (null != paramInputStream) {
/*     */       try {
/*  56 */         localProperties.load(paramInputStream);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  67 */         return localProperties;
/*     */       }
/*     */       catch (IOException localIOException2)
/*     */       {
/*  58 */         LOGGER.error("Unable to read " + paramObject, localIOException2);
/*     */       } finally {
/*     */         try {
/*  61 */           paramInputStream.close();
/*     */         } catch (IOException localIOException4) {
/*  63 */           LOGGER.error("Unable to close " + paramObject, localIOException4);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public PropertiesUtil(String paramString)
/*     */   {
/*  71 */     ClassLoader localClassLoader = ProviderUtil.findClassLoader();
/*  72 */     InputStream localInputStream = localClassLoader.getResourceAsStream(paramString);
/*  73 */     this.props = loadClose(localInputStream, paramString);
/*     */   }
/*     */   
/*     */   public static PropertiesUtil getProperties() {
/*  77 */     return LOG4J_PROPERTIES;
/*     */   }
/*     */   
/*     */   public String getStringProperty(String paramString) {
/*  81 */     String str = null;
/*     */     try {
/*  83 */       str = System.getProperty(paramString);
/*     */     }
/*     */     catch (SecurityException localSecurityException) {}
/*     */     
/*  87 */     return str == null ? this.props.getProperty(paramString) : str;
/*     */   }
/*     */   
/*     */   public int getIntegerProperty(String paramString, int paramInt)
/*     */   {
/*  92 */     String str = null;
/*     */     try {
/*  94 */       str = System.getProperty(paramString);
/*     */     }
/*     */     catch (SecurityException localSecurityException) {}
/*     */     
/*  98 */     if (str == null) {
/*  99 */       str = this.props.getProperty(paramString);
/*     */     }
/* 101 */     if (str != null) {
/*     */       try {
/* 103 */         return Integer.parseInt(str);
/*     */       } catch (Exception localException) {
/* 105 */         return paramInt;
/*     */       }
/*     */     }
/* 108 */     return paramInt;
/*     */   }
/*     */   
/*     */   public long getLongProperty(String paramString, long paramLong)
/*     */   {
/* 113 */     String str = null;
/*     */     try {
/* 115 */       str = System.getProperty(paramString);
/*     */     }
/*     */     catch (SecurityException localSecurityException) {}
/*     */     
/* 119 */     if (str == null) {
/* 120 */       str = this.props.getProperty(paramString);
/*     */     }
/* 122 */     if (str != null) {
/*     */       try {
/* 124 */         return Long.parseLong(str);
/*     */       } catch (Exception localException) {
/* 126 */         return paramLong;
/*     */       }
/*     */     }
/* 129 */     return paramLong;
/*     */   }
/*     */   
/*     */   public String getStringProperty(String paramString1, String paramString2) {
/* 133 */     String str = getStringProperty(paramString1);
/* 134 */     return str == null ? paramString2 : str;
/*     */   }
/*     */   
/*     */   public boolean getBooleanProperty(String paramString) {
/* 138 */     return getBooleanProperty(paramString, false);
/*     */   }
/*     */   
/*     */   public boolean getBooleanProperty(String paramString, boolean paramBoolean) {
/* 142 */     String str = getStringProperty(paramString);
/* 143 */     return str == null ? paramBoolean : "true".equalsIgnoreCase(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static Properties getSystemProperties()
/*     */   {
/*     */     try
/*     */     {
/* 152 */       return new Properties(System.getProperties());
/*     */     } catch (SecurityException localSecurityException) {
/* 154 */       StatusLogger.getLogger().error("Unable to access system properties.");
/*     */     }
/* 156 */     return new Properties();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\util\PropertiesUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */