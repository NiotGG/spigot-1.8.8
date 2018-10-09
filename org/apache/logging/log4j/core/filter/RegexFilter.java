/*     */ package org.apache.logging.log4j.core.filter;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.core.Filter.Result;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.message.Message;
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
/*     */ @Plugin(name="RegexFilter", category="Core", elementType="filter", printObject=true)
/*     */ public final class RegexFilter
/*     */   extends AbstractFilter
/*     */ {
/*     */   private final Pattern pattern;
/*     */   private final boolean useRawMessage;
/*     */   
/*     */   private RegexFilter(boolean paramBoolean, Pattern paramPattern, Filter.Result paramResult1, Filter.Result paramResult2)
/*     */   {
/*  46 */     super(paramResult1, paramResult2);
/*  47 */     this.pattern = paramPattern;
/*  48 */     this.useRawMessage = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*     */   {
/*  54 */     return filter(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*     */   {
/*  60 */     if (paramObject == null) {
/*  61 */       return this.onMismatch;
/*     */     }
/*  63 */     return filter(paramObject.toString());
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*     */   {
/*  69 */     if (paramMessage == null) {
/*  70 */       return this.onMismatch;
/*     */     }
/*  72 */     String str = this.useRawMessage ? paramMessage.getFormat() : paramMessage.getFormattedMessage();
/*  73 */     return filter(str);
/*     */   }
/*     */   
/*     */   public Filter.Result filter(LogEvent paramLogEvent)
/*     */   {
/*  78 */     String str = this.useRawMessage ? paramLogEvent.getMessage().getFormat() : paramLogEvent.getMessage().getFormattedMessage();
/*  79 */     return filter(str);
/*     */   }
/*     */   
/*     */   private Filter.Result filter(String paramString) {
/*  83 */     if (paramString == null) {
/*  84 */       return this.onMismatch;
/*     */     }
/*  86 */     Matcher localMatcher = this.pattern.matcher(paramString);
/*  87 */     return localMatcher.matches() ? this.onMatch : this.onMismatch;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  92 */     StringBuilder localStringBuilder = new StringBuilder();
/*  93 */     localStringBuilder.append("useRaw=").append(this.useRawMessage);
/*  94 */     localStringBuilder.append(", pattern=").append(this.pattern.toString());
/*  95 */     return localStringBuilder.toString();
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
/*     */   @PluginFactory
/*     */   public static RegexFilter createFilter(@PluginAttribute("regex") String paramString1, @PluginAttribute("useRawMsg") String paramString2, @PluginAttribute("onMatch") String paramString3, @PluginAttribute("onMismatch") String paramString4)
/*     */   {
/* 113 */     if (paramString1 == null) {
/* 114 */       LOGGER.error("A regular expression must be provided for RegexFilter");
/* 115 */       return null;
/*     */     }
/* 117 */     boolean bool = Boolean.parseBoolean(paramString2);
/*     */     Pattern localPattern;
/*     */     try {
/* 120 */       localPattern = Pattern.compile(paramString1);
/*     */     } catch (Exception localException) {
/* 122 */       LOGGER.error("RegexFilter caught exception compiling pattern: " + paramString1 + " cause: " + localException.getMessage());
/* 123 */       return null;
/*     */     }
/* 125 */     Filter.Result localResult1 = Filter.Result.toResult(paramString3);
/* 126 */     Filter.Result localResult2 = Filter.Result.toResult(paramString4);
/*     */     
/* 128 */     return new RegexFilter(bool, localPattern, localResult1, localResult2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\RegexFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */