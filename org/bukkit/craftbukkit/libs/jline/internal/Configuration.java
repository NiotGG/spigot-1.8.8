/*     */ package org.bukkit.craftbukkit.libs.jline.internal;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Properties;
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
/*     */ public class Configuration
/*     */ {
/*     */   public static final String JLINE_CONFIGURATION = "org.bukkit.craftbukkit.libs.jline.configuration";
/*     */   public static final String JLINE_RC = ".org.bukkit.craftbukkit.libs.jline.rc";
/*     */   private static volatile Properties properties;
/*     */   
/*     */   private static Properties initProperties()
/*     */   {
/*  46 */     URL url = determineUrl();
/*  47 */     Properties props = new Properties();
/*     */     try {
/*  49 */       loadProperties(url, props);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  53 */       Log.debug(new Object[] { "Unable to read configuration from: ", url, e });
/*     */     }
/*  55 */     return props;
/*     */   }
/*     */   
/*     */   private static void loadProperties(URL url, Properties props) throws IOException {
/*  59 */     Log.debug(new Object[] { "Loading properties from: ", url });
/*  60 */     InputStream input = url.openStream();
/*     */     try {
/*  62 */       props.load(new BufferedInputStream(input));
/*     */       
/*     */       try
/*     */       {
/*  66 */         input.close();
/*     */       }
/*     */       catch (IOException e) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  73 */       if (!Log.DEBUG) {
/*     */         return;
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  66 */         input.close();
/*     */       }
/*     */       catch (IOException e) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  74 */     Log.debug(new Object[] { "Loaded properties:" });
/*  75 */     for (Object entry : props.entrySet()) {
/*  76 */       Log.debug(new Object[] { "  ", ((Map.Entry)entry).getKey(), "=", ((Map.Entry)entry).getValue() });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static URL determineUrl()
/*     */   {
/*  83 */     String tmp = System.getProperty("org.bukkit.craftbukkit.libs.jline.configuration");
/*  84 */     if (tmp != null) {
/*  85 */       return Urls.create(tmp);
/*     */     }
/*     */     
/*     */ 
/*  89 */     File file = new File(getUserHome(), ".org.bukkit.craftbukkit.libs.jline.rc");
/*  90 */     return Urls.create(file);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void reset()
/*     */   {
/*  98 */     Log.debug(new Object[] { "Resetting" });
/*  99 */     properties = null;
/*     */     
/*     */ 
/* 102 */     getProperties();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Properties getProperties()
/*     */   {
/* 110 */     if (properties == null) {
/* 111 */       properties = initProperties();
/*     */     }
/* 113 */     return properties;
/*     */   }
/*     */   
/*     */   public static String getString(String name, String defaultValue) {
/* 117 */     Preconditions.checkNotNull(name);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 122 */     String value = System.getProperty(name);
/*     */     
/* 124 */     if (value == null)
/*     */     {
/* 126 */       value = getProperties().getProperty(name);
/*     */       
/* 128 */       if (value == null)
/*     */       {
/* 130 */         value = defaultValue;
/*     */       }
/*     */     }
/*     */     
/* 134 */     return value;
/*     */   }
/*     */   
/*     */   public static String getString(String name) {
/* 138 */     return getString(name, null);
/*     */   }
/*     */   
/*     */   public static boolean getBoolean(String name, boolean defaultValue) {
/* 142 */     String value = getString(name);
/* 143 */     if (value == null) {
/* 144 */       return defaultValue;
/*     */     }
/* 146 */     return (value.length() == 0) || (value.equalsIgnoreCase("1")) || (value.equalsIgnoreCase("on")) || (value.equalsIgnoreCase("true"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getInteger(String name, int defaultValue)
/*     */   {
/* 156 */     String str = getString(name);
/* 157 */     if (str == null) {
/* 158 */       return defaultValue;
/*     */     }
/* 160 */     return Integer.parseInt(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static long getLong(String name, long defaultValue)
/*     */   {
/* 167 */     String str = getString(name);
/* 168 */     if (str == null) {
/* 169 */       return defaultValue;
/*     */     }
/* 171 */     return Long.parseLong(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getLineSeparator()
/*     */   {
/* 182 */     return System.getProperty("line.separator");
/*     */   }
/*     */   
/*     */   public static File getUserHome() {
/* 186 */     return new File(System.getProperty("user.home"));
/*     */   }
/*     */   
/*     */   public static String getOsName() {
/* 190 */     return System.getProperty("os.name").toLowerCase();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static boolean isWindows()
/*     */   {
/* 197 */     return getOsName().startsWith("windows");
/*     */   }
/*     */   
/*     */ 
/*     */   public static String getFileEncoding()
/*     */   {
/* 203 */     return System.getProperty("file.encoding");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getEncoding()
/*     */   {
/* 214 */     String envEncoding = extractEncodingFromCtype(System.getenv("LC_CTYPE"));
/* 215 */     if (envEncoding != null) {
/* 216 */       return envEncoding;
/*     */     }
/* 218 */     return System.getProperty("input.encoding", Charset.defaultCharset().name());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static String extractEncodingFromCtype(String ctype)
/*     */   {
/* 229 */     if ((ctype != null) && (ctype.indexOf('.') > 0)) {
/* 230 */       String encodingAndModifier = ctype.substring(ctype.indexOf('.') + 1);
/* 231 */       if (encodingAndModifier.indexOf('@') > 0) {
/* 232 */         return encodingAndModifier.substring(0, encodingAndModifier.indexOf('@'));
/*     */       }
/* 234 */       return encodingAndModifier;
/*     */     }
/*     */     
/* 237 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\internal\Configuration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */