/*    */ package org.apache.logging.log4j.core.filter;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.core.Filter.Result;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.Logger;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.message.Message;
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
/*    */ 
/*    */ 
/*    */ @Plugin(name="ThresholdFilter", category="Core", elementType="filter", printObject=true)
/*    */ public final class ThresholdFilter
/*    */   extends AbstractFilter
/*    */ {
/*    */   private final Level level;
/*    */   
/*    */   private ThresholdFilter(Level paramLevel, Filter.Result paramResult1, Filter.Result paramResult2)
/*    */   {
/* 42 */     super(paramResult1, paramResult2);
/* 43 */     this.level = paramLevel;
/*    */   }
/*    */   
/*    */ 
/*    */   public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*    */   {
/* 49 */     return filter(paramLevel);
/*    */   }
/*    */   
/*    */ 
/*    */   public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*    */   {
/* 55 */     return filter(paramLevel);
/*    */   }
/*    */   
/*    */ 
/*    */   public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*    */   {
/* 61 */     return filter(paramLevel);
/*    */   }
/*    */   
/*    */   public Filter.Result filter(LogEvent paramLogEvent)
/*    */   {
/* 66 */     return filter(paramLogEvent.getLevel());
/*    */   }
/*    */   
/*    */   private Filter.Result filter(Level paramLevel) {
/* 70 */     return paramLevel.isAtLeastAsSpecificAs(this.level) ? this.onMatch : this.onMismatch;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 75 */     return this.level.toString();
/*    */   }
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
/*    */   @PluginFactory
/*    */   public static ThresholdFilter createFilter(@PluginAttribute("level") String paramString1, @PluginAttribute("onMatch") String paramString2, @PluginAttribute("onMismatch") String paramString3)
/*    */   {
/* 90 */     Level localLevel = Level.toLevel(paramString1, Level.ERROR);
/* 91 */     Filter.Result localResult1 = Filter.Result.toResult(paramString2, Filter.Result.NEUTRAL);
/* 92 */     Filter.Result localResult2 = Filter.Result.toResult(paramString3, Filter.Result.DENY);
/* 93 */     return new ThresholdFilter(localLevel, localResult1, localResult2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\ThresholdFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */