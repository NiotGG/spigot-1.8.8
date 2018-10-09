/*    */ package org.apache.logging.log4j.core.appender.db.jpa.converter;
/*    */ 
/*    */ import javax.persistence.AttributeConverter;
/*    */ import javax.persistence.Converter;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.MarkerManager;
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
/*    */ @Converter(autoApply=false)
/*    */ public class MarkerAttributeConverter
/*    */   implements AttributeConverter<Marker, String>
/*    */ {
/*    */   public String convertToDatabaseColumn(Marker paramMarker)
/*    */   {
/* 34 */     if (paramMarker == null) {
/* 35 */       return null;
/*    */     }
/*    */     
/* 38 */     StringBuilder localStringBuilder = new StringBuilder(paramMarker.getName());
/* 39 */     Marker localMarker = paramMarker.getParent();
/* 40 */     int i = 0;
/* 41 */     int j = 0;
/* 42 */     while (localMarker != null) {
/* 43 */       i++;
/* 44 */       j = 1;
/* 45 */       localStringBuilder.append("[ ").append(localMarker.getName());
/* 46 */       localMarker = localMarker.getParent();
/*    */     }
/* 48 */     for (int k = 0; k < i; k++) {
/* 49 */       localStringBuilder.append(" ]");
/*    */     }
/* 51 */     if (j != 0) {
/* 52 */       localStringBuilder.append(" ]");
/*    */     }
/* 54 */     return localStringBuilder.toString();
/*    */   }
/*    */   
/*    */   public Marker convertToEntityAttribute(String paramString)
/*    */   {
/* 59 */     if (Strings.isEmpty(paramString)) {
/* 60 */       return null;
/*    */     }
/*    */     
/* 63 */     int i = paramString.indexOf("[");
/*    */     
/* 65 */     return i < 1 ? MarkerManager.getMarker(paramString) : MarkerManager.getMarker(paramString.substring(0, i));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\converter\MarkerAttributeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */