/*    */ package org.apache.logging.log4j.message;
/*    */ 
/*    */ import java.util.ResourceBundle;
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
/*    */ public class LocalizedMessageFactory
/*    */   extends AbstractMessageFactory
/*    */ {
/*    */   private final ResourceBundle bundle;
/*    */   private final String bundleId;
/*    */   
/*    */   public LocalizedMessageFactory(ResourceBundle paramResourceBundle)
/*    */   {
/* 33 */     this.bundle = paramResourceBundle;
/* 34 */     this.bundleId = null;
/*    */   }
/*    */   
/*    */   public LocalizedMessageFactory(String paramString)
/*    */   {
/* 39 */     this.bundle = null;
/* 40 */     this.bundleId = paramString;
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
/*    */   public Message newMessage(String paramString, Object... paramVarArgs)
/*    */   {
/* 55 */     if (this.bundle == null) {
/* 56 */       return new LocalizedMessage(this.bundleId, paramString, paramVarArgs);
/*    */     }
/* 58 */     return new LocalizedMessage(this.bundle, paramString, paramVarArgs);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\LocalizedMessageFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */