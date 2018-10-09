/*    */ package org.bukkit.craftbukkit.libs.jline.internal;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URI;
/*    */ import java.net.URL;
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
/*    */ public class Urls
/*    */ {
/*    */   public static URL create(String input)
/*    */   {
/* 25 */     if (input == null) {
/* 26 */       return null;
/*    */     }
/*    */     try {
/* 29 */       return new URL(input);
/*    */     }
/*    */     catch (MalformedURLException e) {}
/* 32 */     return create(new File(input));
/*    */   }
/*    */   
/*    */   public static URL create(File file)
/*    */   {
/*    */     try {
/* 38 */       return file != null ? file.toURI().toURL() : null;
/*    */     }
/*    */     catch (MalformedURLException e) {
/* 41 */       throw new IllegalStateException(e);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\internal\Urls.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */