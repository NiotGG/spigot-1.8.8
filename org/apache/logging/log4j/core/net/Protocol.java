/*    */ package org.apache.logging.log4j.core.net;
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
/*    */ public enum Protocol
/*    */ {
/* 24 */   TCP, 
/*    */   
/* 26 */   UDP;
/*    */   
/*    */ 
/*    */   private Protocol() {}
/*    */   
/*    */ 
/*    */   public boolean isEqual(String paramString)
/*    */   {
/* 34 */     return name().equalsIgnoreCase(paramString);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\Protocol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */