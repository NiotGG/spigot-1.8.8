/*     */ package org.apache.logging.log4j.core.helpers;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OptionConverter
/*     */ {
/*  31 */   private static final Logger LOGGER = ;
/*     */   
/*     */   private static final String DELIM_START = "${";
/*     */   
/*     */   private static final char DELIM_STOP = '}';
/*     */   
/*     */   private static final int DELIM_START_LEN = 2;
/*     */   
/*     */   private static final int DELIM_STOP_LEN = 1;
/*     */   
/*     */   private static final int ONE_K = 1024;
/*     */   
/*     */ 
/*     */   public static String[] concatenateArrays(String[] paramArrayOfString1, String[] paramArrayOfString2)
/*     */   {
/*  46 */     int i = paramArrayOfString1.length + paramArrayOfString2.length;
/*  47 */     String[] arrayOfString = new String[i];
/*     */     
/*  49 */     System.arraycopy(paramArrayOfString1, 0, arrayOfString, 0, paramArrayOfString1.length);
/*  50 */     System.arraycopy(paramArrayOfString2, 0, arrayOfString, paramArrayOfString1.length, paramArrayOfString2.length);
/*     */     
/*  52 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   public static String convertSpecialChars(String paramString)
/*     */   {
/*  57 */     int i = paramString.length();
/*  58 */     StringBuilder localStringBuilder = new StringBuilder(i);
/*     */     
/*  60 */     int j = 0;
/*  61 */     while (j < i) {
/*  62 */       char c = paramString.charAt(j++);
/*  63 */       if (c == '\\') {
/*  64 */         c = paramString.charAt(j++);
/*  65 */         if (c == 'n') {
/*  66 */           c = '\n';
/*  67 */         } else if (c == 'r') {
/*  68 */           c = '\r';
/*  69 */         } else if (c == 't') {
/*  70 */           c = '\t';
/*  71 */         } else if (c == 'f') {
/*  72 */           c = '\f';
/*  73 */         } else if (c == '\b') {
/*  74 */           c = '\b';
/*  75 */         } else if (c == '"') {
/*  76 */           c = '"';
/*  77 */         } else if (c == '\'') {
/*  78 */           c = '\'';
/*  79 */         } else if (c == '\\') {
/*  80 */           c = '\\';
/*     */         }
/*     */       }
/*  83 */       localStringBuilder.append(c);
/*     */     }
/*  85 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static Object instantiateByKey(Properties paramProperties, String paramString, Class<?> paramClass, Object paramObject)
/*     */   {
/*  92 */     String str = findAndSubst(paramString, paramProperties);
/*  93 */     if (str == null) {
/*  94 */       LOGGER.error("Could not find value for key " + paramString);
/*  95 */       return paramObject;
/*     */     }
/*     */     
/*  98 */     return instantiateByClassName(str.trim(), paramClass, paramObject);
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
/*     */   public static boolean toBoolean(String paramString, boolean paramBoolean)
/*     */   {
/* 114 */     if (paramString == null) {
/* 115 */       return paramBoolean;
/*     */     }
/* 117 */     String str = paramString.trim();
/* 118 */     if ("true".equalsIgnoreCase(str)) {
/* 119 */       return true;
/*     */     }
/* 121 */     if ("false".equalsIgnoreCase(str)) {
/* 122 */       return false;
/*     */     }
/* 124 */     return paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int toInt(String paramString, int paramInt)
/*     */   {
/* 134 */     if (paramString != null) {
/* 135 */       String str = paramString.trim();
/*     */       try {
/* 137 */         return Integer.parseInt(str);
/*     */       } catch (NumberFormatException localNumberFormatException) {
/* 139 */         LOGGER.error("[" + str + "] is not in proper int form.");
/* 140 */         localNumberFormatException.printStackTrace();
/*     */       }
/*     */     }
/* 143 */     return paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long toFileSize(String paramString, long paramLong)
/*     */   {
/* 153 */     if (paramString == null) {
/* 154 */       return paramLong;
/*     */     }
/*     */     
/* 157 */     String str = paramString.trim().toUpperCase(Locale.ENGLISH);
/* 158 */     long l = 1L;
/*     */     
/*     */     int i;
/* 161 */     if ((i = str.indexOf("KB")) != -1) {
/* 162 */       l = 1024L;
/* 163 */       str = str.substring(0, i);
/* 164 */     } else if ((i = str.indexOf("MB")) != -1) {
/* 165 */       l = 1048576L;
/* 166 */       str = str.substring(0, i);
/* 167 */     } else if ((i = str.indexOf("GB")) != -1) {
/* 168 */       l = 1073741824L;
/* 169 */       str = str.substring(0, i);
/*     */     }
/* 171 */     if (str != null) {
/*     */       try {
/* 173 */         return Long.parseLong(str) * l;
/*     */       } catch (NumberFormatException localNumberFormatException) {
/* 175 */         LOGGER.error("[" + str + "] is not in proper int form.");
/* 176 */         LOGGER.error("[" + paramString + "] not in expected format.", localNumberFormatException);
/*     */       }
/*     */     }
/* 179 */     return paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String findAndSubst(String paramString, Properties paramProperties)
/*     */   {
/* 191 */     String str = paramProperties.getProperty(paramString);
/* 192 */     if (str == null) {
/* 193 */       return null;
/*     */     }
/*     */     try
/*     */     {
/* 197 */       return substVars(str, paramProperties);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {
/* 199 */       LOGGER.error("Bad option value [" + str + "].", localIllegalArgumentException); }
/* 200 */     return str;
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
/*     */   public static Object instantiateByClassName(String paramString, Class<?> paramClass, Object paramObject)
/*     */   {
/* 217 */     if (paramString != null) {
/*     */       try {
/* 219 */         Class localClass = Loader.loadClass(paramString);
/* 220 */         if (!paramClass.isAssignableFrom(localClass)) {
/* 221 */           LOGGER.error("A \"" + paramString + "\" object is not assignable to a \"" + paramClass.getName() + "\" variable.");
/*     */           
/* 223 */           LOGGER.error("The class \"" + paramClass.getName() + "\" was loaded by ");
/* 224 */           LOGGER.error("[" + paramClass.getClassLoader() + "] whereas object of type ");
/* 225 */           LOGGER.error("\"" + localClass.getName() + "\" was loaded by [" + localClass.getClassLoader() + "].");
/*     */           
/* 227 */           return paramObject;
/*     */         }
/* 229 */         return localClass.newInstance();
/*     */       } catch (ClassNotFoundException localClassNotFoundException) {
/* 231 */         LOGGER.error("Could not instantiate class [" + paramString + "].", localClassNotFoundException);
/*     */       } catch (IllegalAccessException localIllegalAccessException) {
/* 233 */         LOGGER.error("Could not instantiate class [" + paramString + "].", localIllegalAccessException);
/*     */       } catch (InstantiationException localInstantiationException) {
/* 235 */         LOGGER.error("Could not instantiate class [" + paramString + "].", localInstantiationException);
/*     */       } catch (RuntimeException localRuntimeException) {
/* 237 */         LOGGER.error("Could not instantiate class [" + paramString + "].", localRuntimeException);
/*     */       }
/*     */     }
/* 240 */     return paramObject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String substVars(String paramString, Properties paramProperties)
/*     */     throws IllegalArgumentException
/*     */   {
/* 283 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/* 285 */     int i = 0;
/*     */     
/*     */ 
/*     */     for (;;)
/*     */     {
/* 290 */       int j = paramString.indexOf("${", i);
/* 291 */       if (j == -1)
/*     */       {
/* 293 */         if (i == 0) {
/* 294 */           return paramString;
/*     */         }
/*     */         
/* 297 */         localStringBuilder.append(paramString.substring(i, paramString.length()));
/* 298 */         return localStringBuilder.toString();
/*     */       }
/* 300 */       localStringBuilder.append(paramString.substring(i, j));
/* 301 */       int k = paramString.indexOf('}', j);
/* 302 */       if (k == -1) {
/* 303 */         throw new IllegalArgumentException('"' + paramString + "\" has no closing brace. Opening brace at position " + j + '.');
/*     */       }
/*     */       
/*     */ 
/* 307 */       j += 2;
/* 308 */       String str1 = paramString.substring(j, k);
/*     */       
/* 310 */       String str2 = PropertiesUtil.getProperties().getStringProperty(str1, null);
/*     */       
/* 312 */       if ((str2 == null) && (paramProperties != null)) {
/* 313 */         str2 = paramProperties.getProperty(str1);
/*     */       }
/*     */       
/* 316 */       if (str2 != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 322 */         String str3 = substVars(str2, paramProperties);
/* 323 */         localStringBuilder.append(str3);
/*     */       }
/* 325 */       i = k + 1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\OptionConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */