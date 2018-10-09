/*    */ package com.avaje.ebeaninternal.server.lib.resource;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.lib.util.GeneralException;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import javax.servlet.ServletContext;
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
/*    */ public class UrlResourceSource
/*    */   extends AbstractResourceSource
/*    */   implements ResourceSource
/*    */ {
/*    */   ServletContext sc;
/*    */   String basePath;
/*    */   String realPath;
/*    */   
/*    */   public UrlResourceSource(ServletContext sc, String basePath)
/*    */   {
/* 43 */     this.sc = sc;
/* 44 */     if (basePath == null) {
/* 45 */       this.basePath = "/";
/*    */     } else {
/* 47 */       this.basePath = ("/" + basePath + "/");
/*    */     }
/* 49 */     this.realPath = sc.getRealPath(basePath);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getRealPath()
/*    */   {
/* 59 */     return this.realPath;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ResourceContent getContent(String entry)
/*    */   {
/*    */     try
/*    */     {
/* 71 */       URL url = this.sc.getResource(this.basePath + entry);
/* 72 */       if (url != null) {
/* 73 */         return new UrlResourceContent(url, entry);
/*    */       }
/* 75 */       return null;
/*    */     }
/*    */     catch (MalformedURLException ex) {
/* 78 */       throw new GeneralException(ex);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\resource\UrlResourceSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */