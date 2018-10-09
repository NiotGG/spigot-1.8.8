/*    */ package org.apache.logging.log4j.core.appender.db.jpa.converter;
/*    */ 
/*    */ import javax.persistence.AttributeConverter;
/*    */ import javax.persistence.Converter;
/*    */ import org.apache.logging.log4j.core.helpers.Strings;
/*    */ import org.apache.logging.log4j.message.Message;
/*    */ import org.apache.logging.log4j.message.MessageFactory;
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
/*    */ @Converter(autoApply=false)
/*    */ public class MessageAttributeConverter
/*    */   implements AttributeConverter<Message, String>
/*    */ {
/* 32 */   private static final StatusLogger LOGGER = ;
/*    */   
/*    */   public String convertToDatabaseColumn(Message paramMessage)
/*    */   {
/* 36 */     if (paramMessage == null) {
/* 37 */       return null;
/*    */     }
/*    */     
/* 40 */     return paramMessage.getFormattedMessage();
/*    */   }
/*    */   
/*    */   public Message convertToEntityAttribute(String paramString)
/*    */   {
/* 45 */     if (Strings.isEmpty(paramString)) {
/* 46 */       return null;
/*    */     }
/*    */     
/* 49 */     return LOGGER.getMessageFactory().newMessage(paramString);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\converter\MessageAttributeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */