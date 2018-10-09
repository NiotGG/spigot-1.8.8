/*    */ package org.apache.logging.log4j.util;
/*    */ 
/*    */ import java.util.Locale;
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
/*    */ public final class EnglishEnums
/*    */ {
/*    */   public static <T extends Enum<T>> T valueOf(Class<T> paramClass, String paramString)
/*    */   {
/* 46 */     return valueOf(paramClass, paramString, null);
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
/*    */ 
/*    */ 
/*    */   public static <T extends Enum<T>> T valueOf(Class<T> paramClass, String paramString, T paramT)
/*    */   {
/* 63 */     return paramString == null ? paramT : Enum.valueOf(paramClass, paramString.toUpperCase(Locale.ENGLISH));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\util\EnglishEnums.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */