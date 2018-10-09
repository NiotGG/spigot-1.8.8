/*    */ package org.apache.logging.log4j.core.appender.db.jpa.converter;
/*    */ 
/*    */ import javax.persistence.AttributeConverter;
/*    */ import javax.persistence.Converter;
/*    */ import org.apache.logging.log4j.ThreadContext.ContextStack;
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
/*    */ 
/*    */ 
/*    */ @Converter(autoApply=false)
/*    */ public class ContextStackAttributeConverter
/*    */   implements AttributeConverter<ThreadContext.ContextStack, String>
/*    */ {
/*    */   public String convertToDatabaseColumn(ThreadContext.ContextStack paramContextStack)
/*    */   {
/* 36 */     if (paramContextStack == null) {
/* 37 */       return null;
/*    */     }
/*    */     
/* 40 */     StringBuilder localStringBuilder = new StringBuilder();
/* 41 */     for (String str : paramContextStack.asList()) {
/* 42 */       if (localStringBuilder.length() > 0) {
/* 43 */         localStringBuilder.append('\n');
/*    */       }
/* 45 */       localStringBuilder.append(str);
/*    */     }
/* 47 */     return localStringBuilder.toString();
/*    */   }
/*    */   
/*    */   public ThreadContext.ContextStack convertToEntityAttribute(String paramString)
/*    */   {
/* 52 */     throw new UnsupportedOperationException("Log events can only be persisted, not extracted.");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\converter\ContextStackAttributeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */