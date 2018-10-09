/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.nio.charset.Charset;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Charsets
/*    */ {
/* 31 */   public static final Charset UTF_8 = Charset.forName("UTF-8");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static Charset getSupportedCharset(String paramString)
/*    */   {
/* 42 */     return getSupportedCharset(paramString, Charset.defaultCharset());
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
/*    */   public static Charset getSupportedCharset(String paramString, Charset paramCharset)
/*    */   {
/* 56 */     Charset localCharset = null;
/* 57 */     if ((paramString != null) && 
/* 58 */       (Charset.isSupported(paramString))) {
/* 59 */       localCharset = Charset.forName(paramString);
/*    */     }
/*    */     
/* 62 */     if (localCharset == null) {
/* 63 */       localCharset = paramCharset;
/* 64 */       if (paramString != null) {
/* 65 */         StatusLogger.getLogger().error("Charset " + paramString + " is not supported for layout, using " + localCharset.displayName());
/*    */       }
/*    */     }
/*    */     
/* 69 */     return localCharset;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\Charsets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */