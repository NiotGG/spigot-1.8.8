/*    */ package org.bukkit.configuration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvalidConfigurationException
/*    */   extends Exception
/*    */ {
/*    */   public InvalidConfigurationException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public InvalidConfigurationException(String msg)
/*    */   {
/* 22 */     super(msg);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public InvalidConfigurationException(Throwable cause)
/*    */   {
/* 32 */     super(cause);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public InvalidConfigurationException(String msg, Throwable cause)
/*    */   {
/* 43 */     super(msg, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\InvalidConfigurationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */