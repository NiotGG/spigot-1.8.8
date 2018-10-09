/*    */ package org.apache.logging.log4j.core.helpers;
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
/*    */ public class Booleans
/*    */ {
/*    */   public static boolean parseBoolean(String paramString, boolean paramBoolean)
/*    */   {
/* 34 */     return ("true".equalsIgnoreCase(paramString)) || ((paramBoolean) && (!"false".equalsIgnoreCase(paramString)));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\Booleans.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */