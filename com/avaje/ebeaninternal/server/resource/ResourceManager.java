/*    */ package com.avaje.ebeaninternal.server.resource;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.lib.resource.ResourceSource;
/*    */ import java.io.File;
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
/*    */ public class ResourceManager
/*    */ {
/*    */   final ResourceSource resourceSource;
/*    */   final File autofetchDir;
/*    */   
/*    */   public ResourceManager(ResourceSource resourceSource, File autofetchDir)
/*    */   {
/* 36 */     this.resourceSource = resourceSource;
/* 37 */     this.autofetchDir = autofetchDir;
/*    */   }
/*    */   
/*    */   public ResourceSource getResourceSource() {
/* 41 */     return this.resourceSource;
/*    */   }
/*    */   
/*    */   public File getAutofetchDirectory() {
/* 45 */     return this.autofetchDir;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\resource\ResourceManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */