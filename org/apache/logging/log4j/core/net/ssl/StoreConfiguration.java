/*    */ package org.apache.logging.log4j.core.net.ssl;
/*    */ 
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
/*    */ public class StoreConfiguration
/*    */ {
/* 25 */   protected static final StatusLogger LOGGER = ;
/*    */   private String location;
/*    */   private String password;
/*    */   
/*    */   public StoreConfiguration(String paramString1, String paramString2)
/*    */   {
/* 31 */     this.location = paramString1;
/* 32 */     this.password = paramString2;
/*    */   }
/*    */   
/*    */   public String getLocation() {
/* 36 */     return this.location;
/*    */   }
/*    */   
/*    */   public void setLocation(String paramString) {
/* 40 */     this.location = paramString;
/*    */   }
/*    */   
/*    */   public String getPassword() {
/* 44 */     return this.password;
/*    */   }
/*    */   
/*    */   public char[] getPasswordAsCharArray() {
/* 48 */     if (this.password == null) {
/* 49 */       return null;
/*    */     }
/* 51 */     return this.password.toCharArray();
/*    */   }
/*    */   
/*    */   public void setPassword(String paramString) {
/* 55 */     this.password = paramString;
/*    */   }
/*    */   
/*    */   public boolean equals(StoreConfiguration paramStoreConfiguration) {
/* 59 */     if (paramStoreConfiguration == null) {
/* 60 */       return false;
/*    */     }
/* 62 */     boolean bool1 = false;
/* 63 */     boolean bool2 = false;
/*    */     
/* 65 */     if (this.location != null) {
/* 66 */       bool1 = this.location.equals(paramStoreConfiguration.location);
/*    */     } else {
/* 68 */       bool1 = this.location == paramStoreConfiguration.location;
/*    */     }
/* 70 */     if (this.password != null) {
/* 71 */       bool2 = this.password.equals(paramStoreConfiguration.password);
/*    */     } else {
/* 73 */       bool2 = this.password == paramStoreConfiguration.password;
/*    */     }
/* 75 */     return (bool1) && (bool2);
/*    */   }
/*    */   
/*    */   protected void load()
/*    */     throws StoreConfigurationException
/*    */   {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\ssl\StoreConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */