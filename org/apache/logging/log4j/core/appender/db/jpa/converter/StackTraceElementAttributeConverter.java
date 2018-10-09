/*    */ package org.apache.logging.log4j.core.appender.db.jpa.converter;
/*    */ 
/*    */ import javax.persistence.AttributeConverter;
/*    */ import javax.persistence.Converter;
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
/*    */ @Converter(autoApply=false)
/*    */ public class StackTraceElementAttributeConverter
/*    */   implements AttributeConverter<StackTraceElement, String>
/*    */ {
/*    */   private static final int UNKNOWN_SOURCE = -1;
/*    */   private static final int NATIVE_METHOD = -2;
/*    */   
/*    */   public String convertToDatabaseColumn(StackTraceElement paramStackTraceElement)
/*    */   {
/* 36 */     if (paramStackTraceElement == null) {
/* 37 */       return null;
/*    */     }
/*    */     
/* 40 */     return paramStackTraceElement.toString();
/*    */   }
/*    */   
/*    */   public StackTraceElement convertToEntityAttribute(String paramString)
/*    */   {
/* 45 */     if (Strings.isEmpty(paramString)) {
/* 46 */       return null;
/*    */     }
/*    */     
/* 49 */     return convertString(paramString);
/*    */   }
/*    */   
/*    */   static StackTraceElement convertString(String paramString) {
/* 53 */     int i = paramString.indexOf("(");
/*    */     
/* 55 */     String str1 = paramString.substring(0, i);
/* 56 */     String str2 = str1.substring(0, str1.lastIndexOf("."));
/* 57 */     String str3 = str1.substring(str1.lastIndexOf(".") + 1);
/*    */     
/* 59 */     String str4 = paramString.substring(i + 1, paramString.indexOf(")"));
/*    */     
/* 61 */     String str5 = null;
/* 62 */     int j = -1;
/* 63 */     if ("Native Method".equals(str4)) {
/* 64 */       j = -2;
/* 65 */     } else if (!"Unknown Source".equals(str4)) {
/* 66 */       int k = str4.indexOf(":");
/* 67 */       if (k > -1) {
/* 68 */         str5 = str4.substring(0, k);
/*    */         try {
/* 70 */           j = Integer.parseInt(str4.substring(k + 1));
/*    */         }
/*    */         catch (NumberFormatException localNumberFormatException) {}
/*    */       }
/*    */       else {
/* 75 */         str5 = str4.substring(0);
/*    */       }
/*    */     }
/*    */     
/* 79 */     return new StackTraceElement(str2, str3, str5, j);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\converter\StackTraceElementAttributeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */