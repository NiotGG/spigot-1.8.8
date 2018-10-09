/*    */ package org.apache.logging.log4j.core.filter;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.core.Filter.Result;
/*    */ import org.apache.logging.log4j.core.LogEvent;
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
/*    */ @Plugin(name="MarkerFilter", category="Core", elementType="filter", printObject=true)
/*    */ public final class MarkerFilter
/*    */   extends AbstractFilter
/*    */ {
/*    */   private final String name;
/*    */   
/*    */   private MarkerFilter(String paramString, Filter.Result paramResult1, Filter.Result paramResult2)
/*    */   {
/* 39 */     super(paramResult1, paramResult2);
/* 40 */     this.name = paramString;
/*    */   }
/*    */   
/*    */ 
/*    */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*    */   {
/* 46 */     return filter(paramMarker);
/*    */   }
/*    */   
/*    */ 
/*    */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*    */   {
/* 52 */     return filter(paramMarker);
/*    */   }
/*    */   
/*    */ 
/*    */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*    */   {
/* 58 */     return filter(paramMarker);
/*    */   }
/*    */   
/*    */   public Filter.Result filter(LogEvent paramLogEvent)
/*    */   {
/* 63 */     return filter(paramLogEvent.getMarker());
/*    */   }
/*    */   
/*    */   private Filter.Result filter(Marker paramMarker) {
/* 67 */     return (paramMarker != null) && (paramMarker.isInstanceOf(this.name)) ? this.onMatch : this.onMismatch;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 72 */     return this.name;
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
/*    */ 
/*    */   @PluginFactory
/*    */   public static MarkerFilter createFilter(@PluginAttribute("marker") String paramString1, @PluginAttribute("onMatch") String paramString2, @PluginAttribute("onMismatch") String paramString3)
/*    */   {
/* 88 */     if (paramString1 == null) {
/* 89 */       LOGGER.error("A marker must be provided for MarkerFilter");
/* 90 */       return null;
/*    */     }
/* 92 */     Filter.Result localResult1 = Filter.Result.toResult(paramString2);
/* 93 */     Filter.Result localResult2 = Filter.Result.toResult(paramString3);
/* 94 */     return new MarkerFilter(paramString1, localResult1, localResult2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\MarkerFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */