/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.helpers.UUIDUtil;
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
/*    */ @Plugin(name="UUIDPatternConverter", category="Converter")
/*    */ @ConverterKeys({"u", "uuid"})
/*    */ public final class UUIDPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/*    */   private final boolean isRandom;
/*    */   
/*    */   private UUIDPatternConverter(boolean paramBoolean)
/*    */   {
/* 38 */     super("u", "uuid");
/* 39 */     this.isRandom = paramBoolean;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static UUIDPatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 49 */     if (paramArrayOfString.length == 0) {
/* 50 */       return new UUIDPatternConverter(false);
/*    */     }
/*    */     
/* 53 */     if ((paramArrayOfString.length > 1) || ((!paramArrayOfString[0].equalsIgnoreCase("RANDOM")) && (!paramArrayOfString[0].equalsIgnoreCase("Time")))) {
/* 54 */       LOGGER.error("UUID Pattern Converter only accepts a single option with the value \"RANDOM\" or \"TIME\"");
/*    */     }
/* 56 */     return new UUIDPatternConverter(paramArrayOfString[0].equalsIgnoreCase("RANDOM"));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 64 */     UUID localUUID = this.isRandom ? UUID.randomUUID() : UUIDUtil.getTimeBasedUUID();
/* 65 */     paramStringBuilder.append(localUUID.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\UUIDPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */