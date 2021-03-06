/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import java.net.URLClassLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClassPathClassBytesReader
/*    */   implements ClassBytesReader
/*    */ {
/*    */   private final URL[] urls;
/*    */   
/*    */   public ClassPathClassBytesReader(URL[] urls)
/*    */   {
/* 18 */     this.urls = (urls == null ? new URL[0] : urls);
/*    */   }
/*    */   
/*    */   public byte[] getClassBytes(String className, ClassLoader classLoader)
/*    */   {
/* 23 */     URLClassLoader cl = new URLClassLoader(this.urls, classLoader);
/*    */     
/* 25 */     String resource = className.replace('.', '/') + ".class";
/*    */     
/* 27 */     InputStream is = null;
/*    */     
/*    */     try
/*    */     {
/* 31 */       URL url = cl.getResource(resource);
/* 32 */       if (url == null) {
/* 33 */         throw new RuntimeException("Class Resource not found for " + resource);
/*    */       }
/*    */       
/* 36 */       is = url.openStream();
/* 37 */       byte[] classBytes = InputStreamTransform.readBytes(is);
/*    */       
/* 39 */       return classBytes;
/*    */     }
/*    */     catch (IOException e) {
/* 42 */       throw new RuntimeException("IOException reading bytes for " + className, e);
/*    */     }
/*    */     finally {
/* 45 */       if (is != null) {
/*    */         try {
/* 47 */           is.close();
/*    */         } catch (IOException e) {
/* 49 */           throw new RuntimeException("Error closing InputStream for " + className, e);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\ClassPathClassBytesReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */