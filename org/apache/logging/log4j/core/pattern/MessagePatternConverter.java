/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.Configuration;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*    */ import org.apache.logging.log4j.message.Message;
/*    */ import org.apache.logging.log4j.message.MultiformatMessage;
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
/*    */ @Plugin(name="MessagePatternConverter", category="Converter")
/*    */ @ConverterKeys({"m", "msg", "message"})
/*    */ public final class MessagePatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/*    */   private final String[] formats;
/*    */   private final Configuration config;
/*    */   
/*    */   private MessagePatternConverter(Configuration paramConfiguration, String[] paramArrayOfString)
/*    */   {
/* 41 */     super("Message", "message");
/* 42 */     this.formats = paramArrayOfString;
/* 43 */     this.config = paramConfiguration;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static MessagePatternConverter newInstance(Configuration paramConfiguration, String[] paramArrayOfString)
/*    */   {
/* 54 */     return new MessagePatternConverter(paramConfiguration, paramArrayOfString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 62 */     Message localMessage = paramLogEvent.getMessage();
/* 63 */     if (localMessage != null) {
/*    */       String str;
/* 65 */       if ((localMessage instanceof MultiformatMessage)) {
/* 66 */         str = ((MultiformatMessage)localMessage).getFormattedMessage(this.formats);
/*    */       } else {
/* 68 */         str = localMessage.getFormattedMessage();
/*    */       }
/* 70 */       if (str != null) {
/* 71 */         paramStringBuilder.append((this.config != null) && (str.contains("${")) ? this.config.getStrSubstitutor().replace(paramLogEvent, str) : str);
/*    */       }
/*    */       else {
/* 74 */         paramStringBuilder.append("null");
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\MessagePatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */