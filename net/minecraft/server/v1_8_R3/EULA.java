/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.util.Properties;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class EULA
/*    */ {
/* 13 */   private static final Logger a = ;
/*    */   private final File b;
/*    */   private final boolean c;
/*    */   
/*    */   public EULA(File paramFile) {
/* 18 */     this.b = paramFile;
/* 19 */     this.c = a(paramFile);
/*    */   }
/*    */   
/*    */   private boolean a(File paramFile) {
/* 23 */     FileInputStream localFileInputStream = null;
/* 24 */     boolean bool = false;
/*    */     try {
/* 26 */       Properties localProperties = new Properties();
/* 27 */       localFileInputStream = new FileInputStream(paramFile);
/* 28 */       localProperties.load(localFileInputStream);
/* 29 */       bool = Boolean.parseBoolean(localProperties.getProperty("eula", "false"));
/*    */     } catch (Exception localException) {
/* 31 */       a.warn("Failed to load " + paramFile);
/* 32 */       b();
/*    */     } finally {
/* 34 */       IOUtils.closeQuietly(localFileInputStream);
/*    */     }
/* 36 */     return bool;
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 40 */     return this.c;
/*    */   }
/*    */   
/*    */   public void b() {
/* 44 */     FileOutputStream localFileOutputStream = null;
/*    */     try {
/* 46 */       Properties localProperties = new Properties();
/* 47 */       localFileOutputStream = new FileOutputStream(this.b);
/* 48 */       localProperties.setProperty("eula", "false");
/* 49 */       localProperties.store(localFileOutputStream, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula).");
/*    */     } catch (Exception localException) {
/* 51 */       a.warn("Failed to save " + this.b, localException);
/*    */     } finally {
/* 53 */       IOUtils.closeQuietly(localFileOutputStream);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EULA.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */