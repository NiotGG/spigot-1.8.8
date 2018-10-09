/*    */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Properties;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public final class Versioning
/*    */ {
/*    */   public static String getBukkitVersion()
/*    */   {
/* 12 */     String result = "Unknown-Version";
/*    */     
/* 14 */     InputStream stream = org.bukkit.Bukkit.class.getClassLoader().getResourceAsStream("META-INF/maven/org.spigotmc/spigot-api/pom.properties");
/* 15 */     Properties properties = new Properties();
/*    */     
/* 17 */     if (stream != null) {
/*    */       try {
/* 19 */         properties.load(stream);
/*    */         
/* 21 */         result = properties.getProperty("version");
/*    */       } catch (IOException ex) {
/* 23 */         Logger.getLogger(Versioning.class.getName()).log(java.util.logging.Level.SEVERE, "Could not get Bukkit version!", ex);
/*    */       }
/*    */     }
/*    */     
/* 27 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\Versioning.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */