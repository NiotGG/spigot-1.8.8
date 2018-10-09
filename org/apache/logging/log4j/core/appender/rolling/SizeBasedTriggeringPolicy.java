/*     */ package org.apache.logging.log4j.core.appender.rolling;
/*     */ 
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
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
/*     */ 
/*     */ @Plugin(name="SizeBasedTriggeringPolicy", category="Core", printObject=true)
/*     */ public class SizeBasedTriggeringPolicy
/*     */   implements TriggeringPolicy
/*     */ {
/*  40 */   protected static final Logger LOGGER = ;
/*     */   
/*     */ 
/*     */   private static final long KB = 1024L;
/*     */   
/*     */ 
/*     */   private static final long MB = 1048576L;
/*     */   
/*     */ 
/*     */   private static final long GB = 1073741824L;
/*     */   
/*     */ 
/*     */   private static final long MAX_FILE_SIZE = 10485760L;
/*     */   
/*     */ 
/*  55 */   private static final Pattern VALUE_PATTERN = Pattern.compile("([0-9]+([\\.,][0-9]+)?)\\s*(|K|M|G)B?", 2);
/*     */   
/*     */ 
/*     */   private final long maxFileSize;
/*     */   
/*     */ 
/*     */   private RollingFileManager manager;
/*     */   
/*     */ 
/*     */   protected SizeBasedTriggeringPolicy()
/*     */   {
/*  66 */     this.maxFileSize = 10485760L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SizeBasedTriggeringPolicy(long paramLong)
/*     */   {
/*  75 */     this.maxFileSize = paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initialize(RollingFileManager paramRollingFileManager)
/*     */   {
/*  84 */     this.manager = paramRollingFileManager;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isTriggeringEvent(LogEvent paramLogEvent)
/*     */   {
/*  95 */     return this.manager.getFileSize() > this.maxFileSize;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 100 */     return "SizeBasedTriggeringPolicy(size=" + this.maxFileSize + ")";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static SizeBasedTriggeringPolicy createPolicy(@PluginAttribute("size") String paramString)
/*     */   {
/* 111 */     long l = paramString == null ? 10485760L : valueOf(paramString);
/* 112 */     return new SizeBasedTriggeringPolicy(l);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static long valueOf(String paramString)
/*     */   {
/* 124 */     Matcher localMatcher = VALUE_PATTERN.matcher(paramString);
/*     */     
/*     */ 
/* 127 */     if (localMatcher.matches()) {
/*     */       try
/*     */       {
/* 130 */         long l = NumberFormat.getNumberInstance(Locale.getDefault()).parse(localMatcher.group(1)).longValue();
/*     */         
/*     */ 
/*     */ 
/* 134 */         String str = localMatcher.group(3);
/*     */         
/* 136 */         if (str.equalsIgnoreCase(""))
/* 137 */           return l;
/* 138 */         if (str.equalsIgnoreCase("K"))
/* 139 */           return l * 1024L;
/* 140 */         if (str.equalsIgnoreCase("M"))
/* 141 */           return l * 1048576L;
/* 142 */         if (str.equalsIgnoreCase("G")) {
/* 143 */           return l * 1073741824L;
/*     */         }
/* 145 */         LOGGER.error("Units not recognized: " + paramString);
/* 146 */         return 10485760L;
/*     */       }
/*     */       catch (ParseException localParseException) {
/* 149 */         LOGGER.error("Unable to parse numeric part: " + paramString, localParseException);
/* 150 */         return 10485760L;
/*     */       }
/*     */     }
/* 153 */     LOGGER.error("Unable to parse bytes: " + paramString);
/* 154 */     return 10485760L;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\SizeBasedTriggeringPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */