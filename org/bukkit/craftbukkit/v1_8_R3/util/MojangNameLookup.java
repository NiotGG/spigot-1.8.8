/*    */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*    */ 
/*    */ import com.google.common.base.Charsets;
/*    */ import com.google.gson.Gson;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.util.UUID;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class MojangNameLookup
/*    */ {
/* 17 */   private static final Logger logger = LogManager.getFormatterLogger(MojangNameLookup.class);
/*    */   
/*    */   public static String lookupName(UUID id) {
/* 20 */     if (id == null) {
/* 21 */       return null;
/*    */     }
/*    */     
/* 24 */     InputStream inputStream = null;
/*    */     try {
/* 26 */       URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + id.toString().replace("-", ""));
/* 27 */       URLConnection connection = url.openConnection();
/* 28 */       connection.setConnectTimeout(15000);
/* 29 */       connection.setReadTimeout(15000);
/* 30 */       connection.setUseCaches(false);
/* 31 */       inputStream = connection.getInputStream();
/* 32 */       String result = IOUtils.toString(inputStream, Charsets.UTF_8);
/* 33 */       Gson gson = new Gson();
/* 34 */       Response response = (Response)gson.fromJson(result, Response.class);
/* 35 */       if ((response == null) || (response.name == null)) {
/* 36 */         logger.warn("Failed to lookup name from UUID");
/* 37 */         return null;
/*    */       }
/*    */       
/* 40 */       if ((response.cause != null) && (response.cause.length() > 0)) {
/* 41 */         logger.warn("Failed to lookup name from UUID: %s", new Object[] { response.errorMessage });
/* 42 */         return null;
/*    */       }
/*    */       
/* 45 */       return response.name;
/*    */     } catch (MalformedURLException localMalformedURLException) {
/* 47 */       logger.warn("Malformed URL in UUID lookup");
/* 48 */       return null;
/*    */     } catch (IOException localIOException) {
/* 50 */       IOUtils.closeQuietly(inputStream);
/*    */     } finally {
/* 52 */       IOUtils.closeQuietly(inputStream);
/*    */     }
/*    */     
/* 55 */     return null;
/*    */   }
/*    */   
/*    */   private class Response
/*    */   {
/*    */     String errorMessage;
/*    */     String cause;
/*    */     String name;
/*    */     
/*    */     private Response() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\MojangNameLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */