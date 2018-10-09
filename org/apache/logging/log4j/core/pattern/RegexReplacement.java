/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(name="replace", category="Core", printObject=true)
/*    */ public final class RegexReplacement
/*    */ {
/* 33 */   private static final Logger LOGGER = ;
/*    */   
/*    */ 
/*    */ 
/*    */   private final Pattern pattern;
/*    */   
/*    */ 
/*    */   private final String substitution;
/*    */   
/*    */ 
/*    */ 
/*    */   private RegexReplacement(Pattern paramPattern, String paramString)
/*    */   {
/* 46 */     this.pattern = paramPattern;
/* 47 */     this.substitution = paramString;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String format(String paramString)
/*    */   {
/* 56 */     return this.pattern.matcher(paramString).replaceAll(this.substitution);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 61 */     return "replace(regex=" + this.pattern.pattern() + ", replacement=" + this.substitution + ")";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @PluginFactory
/*    */   public static RegexReplacement createRegexReplacement(@PluginAttribute("regex") String paramString1, @PluginAttribute("replacement") String paramString2)
/*    */   {
/* 74 */     if (paramString1 == null) {
/* 75 */       LOGGER.error("A regular expression is required for replacement");
/* 76 */       return null;
/*    */     }
/* 78 */     if (paramString2 == null) {
/* 79 */       LOGGER.error("A replacement string is required to perform replacement");
/*    */     }
/* 81 */     Pattern localPattern = Pattern.compile(paramString1);
/* 82 */     return new RegexReplacement(localPattern, paramString2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\RegexReplacement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */