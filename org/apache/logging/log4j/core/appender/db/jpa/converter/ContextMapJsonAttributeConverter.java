/*    */ package org.apache.logging.log4j.core.appender.db.jpa.converter;
/*    */ 
/*    */ import com.fasterxml.jackson.core.type.TypeReference;
/*    */ import com.fasterxml.jackson.databind.ObjectMapper;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import javax.persistence.AttributeConverter;
/*    */ import javax.persistence.Converter;
/*    */ import javax.persistence.PersistenceException;
/*    */ import org.apache.logging.log4j.core.helpers.Strings;
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
/*    */ public class ContextMapJsonAttributeConverter
/*    */   implements AttributeConverter<Map<String, String>, String>
/*    */ {
/* 41 */   static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
/*    */   
/*    */   public String convertToDatabaseColumn(Map<String, String> paramMap)
/*    */   {
/* 45 */     if (paramMap == null) {
/* 46 */       return null;
/*    */     }
/*    */     try
/*    */     {
/* 50 */       return OBJECT_MAPPER.writeValueAsString(paramMap);
/*    */     } catch (IOException localIOException) {
/* 52 */       throw new PersistenceException("Failed to convert map to JSON string.", localIOException);
/*    */     }
/*    */   }
/*    */   
/*    */   public Map<String, String> convertToEntityAttribute(String paramString)
/*    */   {
/* 58 */     if (Strings.isEmpty(paramString)) {
/* 59 */       return null;
/*    */     }
/*    */     try {
/* 62 */       (Map)OBJECT_MAPPER.readValue(paramString, new TypeReference() {});
/*    */     } catch (IOException localIOException) {
/* 64 */       throw new PersistenceException("Failed to convert JSON string to map.", localIOException);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\converter\ContextMapJsonAttributeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */