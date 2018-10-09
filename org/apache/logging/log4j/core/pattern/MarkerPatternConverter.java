/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
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
/*    */ @Plugin(name="MarkerPatternConverter", category="Converter")
/*    */ @ConverterKeys({"marker"})
/*    */ public final class MarkerPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/*    */   private MarkerPatternConverter(String[] paramArrayOfString)
/*    */   {
/* 35 */     super("Marker", "marker");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static MarkerPatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 45 */     return new MarkerPatternConverter(paramArrayOfString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 53 */     Marker localMarker = paramLogEvent.getMarker();
/* 54 */     if (localMarker != null) {
/* 55 */       paramStringBuilder.append(localMarker.toString());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\MarkerPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */