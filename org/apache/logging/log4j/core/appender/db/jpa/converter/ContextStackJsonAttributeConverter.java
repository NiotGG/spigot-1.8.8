/*    */ package org.apache.logging.log4j.core.appender.db.jpa.converter;
/*    */ 
/*    */ import com.fasterxml.jackson.core.type.TypeReference;
/*    */ import com.fasterxml.jackson.databind.ObjectMapper;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import javax.persistence.AttributeConverter;
/*    */ import javax.persistence.Converter;
/*    */ import javax.persistence.PersistenceException;
/*    */ import org.apache.logging.log4j.ThreadContext.ContextStack;
/*    */ import org.apache.logging.log4j.core.helpers.Strings;
/*    */ import org.apache.logging.log4j.spi.DefaultThreadContextStack;
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
/*    */ 
/*    */ 
/*    */ @Converter(autoApply=false)
/*    */ public class ContextStackJsonAttributeConverter
/*    */   implements AttributeConverter<ThreadContext.ContextStack, String>
/*    */ {
/*    */   public String convertToDatabaseColumn(ThreadContext.ContextStack paramContextStack)
/*    */   {
/* 45 */     if (paramContextStack == null) {
/* 46 */       return null;
/*    */     }
/*    */     try
/*    */     {
/* 50 */       return ContextMapJsonAttributeConverter.OBJECT_MAPPER.writeValueAsString(paramContextStack.asList());
/*    */     } catch (IOException localIOException) {
/* 52 */       throw new PersistenceException("Failed to convert stack list to JSON string.", localIOException);
/*    */     }
/*    */   }
/*    */   
/*    */   public ThreadContext.ContextStack convertToEntityAttribute(String paramString)
/*    */   {
/* 58 */     if (Strings.isEmpty(paramString)) {
/* 59 */       return null;
/*    */     }
/*    */     List localList;
/*    */     try
/*    */     {
/* 64 */       localList = (List)ContextMapJsonAttributeConverter.OBJECT_MAPPER.readValue(paramString, new TypeReference() {});
/*    */     } catch (IOException localIOException) {
/* 66 */       throw new PersistenceException("Failed to convert JSON string to list for stack.", localIOException);
/*    */     }
/*    */     
/* 69 */     DefaultThreadContextStack localDefaultThreadContextStack = new DefaultThreadContextStack(true);
/* 70 */     localDefaultThreadContextStack.addAll(localList);
/* 71 */     return localDefaultThreadContextStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\converter\ContextStackJsonAttributeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */