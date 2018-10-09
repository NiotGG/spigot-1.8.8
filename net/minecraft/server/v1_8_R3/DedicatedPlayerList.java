/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class DedicatedPlayerList extends PlayerList
/*     */ {
/*  11 */   private static final Logger f = ;
/*     */   
/*     */   public DedicatedPlayerList(DedicatedServer paramDedicatedServer) {
/*  14 */     super(paramDedicatedServer);
/*     */     
/*  16 */     a(paramDedicatedServer.a("view-distance", 10));
/*  17 */     this.maxPlayers = paramDedicatedServer.a("max-players", 20);
/*  18 */     setHasWhitelist(paramDedicatedServer.a("white-list", false));
/*     */     
/*  20 */     if (!paramDedicatedServer.T()) {
/*  21 */       getProfileBans().a(true);
/*  22 */       getIPBans().a(true);
/*     */     }
/*     */     
/*  25 */     z();
/*  26 */     x();
/*  27 */     y();
/*  28 */     w();
/*  29 */     A();
/*  30 */     C();
/*  31 */     B();
/*  32 */     if (!getWhitelist().c().exists()) {
/*  33 */       D();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setHasWhitelist(boolean paramBoolean)
/*     */   {
/*  39 */     super.setHasWhitelist(paramBoolean);
/*  40 */     getServer().a("white-list", Boolean.valueOf(paramBoolean));
/*  41 */     getServer().a();
/*     */   }
/*     */   
/*     */   public void addOp(GameProfile paramGameProfile)
/*     */   {
/*  46 */     super.addOp(paramGameProfile);
/*  47 */     B();
/*     */   }
/*     */   
/*     */   public void removeOp(GameProfile paramGameProfile)
/*     */   {
/*  52 */     super.removeOp(paramGameProfile);
/*  53 */     B();
/*     */   }
/*     */   
/*     */   public void removeWhitelist(GameProfile paramGameProfile)
/*     */   {
/*  58 */     super.removeWhitelist(paramGameProfile);
/*  59 */     D();
/*     */   }
/*     */   
/*     */   public void addWhitelist(GameProfile paramGameProfile)
/*     */   {
/*  64 */     super.addWhitelist(paramGameProfile);
/*  65 */     D();
/*     */   }
/*     */   
/*     */   public void reloadWhitelist()
/*     */   {
/*  70 */     C();
/*     */   }
/*     */   
/*     */   private void w() {
/*     */     try {
/*  75 */       getIPBans().save();
/*     */     } catch (IOException localIOException) {
/*  77 */       f.warn("Failed to save ip banlist: ", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   private void x() {
/*     */     try {
/*  83 */       getProfileBans().save();
/*     */     } catch (IOException localIOException) {
/*  85 */       f.warn("Failed to save user banlist: ", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   private void y() {
/*     */     try {
/*  91 */       getIPBans().load();
/*     */     } catch (IOException localIOException) {
/*  93 */       f.warn("Failed to load ip banlist: ", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   private void z() {
/*     */     try {
/*  99 */       getProfileBans().load();
/*     */     } catch (IOException localIOException) {
/* 101 */       f.warn("Failed to load user banlist: ", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   private void A() {
/*     */     try {
/* 107 */       getOPs().load();
/*     */     } catch (Exception localException) {
/* 109 */       f.warn("Failed to load operators list: ", localException);
/*     */     }
/*     */   }
/*     */   
/*     */   private void B() {
/*     */     try {
/* 115 */       getOPs().save();
/*     */     } catch (Exception localException) {
/* 117 */       f.warn("Failed to save operators list: ", localException);
/*     */     }
/*     */   }
/*     */   
/*     */   private void C() {
/*     */     try {
/* 123 */       getWhitelist().load();
/*     */     } catch (Exception localException) {
/* 125 */       f.warn("Failed to load white-list: ", localException);
/*     */     }
/*     */   }
/*     */   
/*     */   private void D() {
/*     */     try {
/* 131 */       getWhitelist().save();
/*     */     } catch (Exception localException) {
/* 133 */       f.warn("Failed to save white-list: ", localException);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isWhitelisted(GameProfile paramGameProfile)
/*     */   {
/* 139 */     return (!getHasWhitelist()) || (isOp(paramGameProfile)) || (getWhitelist().isWhitelisted(paramGameProfile));
/*     */   }
/*     */   
/*     */   public DedicatedServer getServer()
/*     */   {
/* 144 */     return (DedicatedServer)super.getServer();
/*     */   }
/*     */   
/*     */   public boolean f(GameProfile paramGameProfile)
/*     */   {
/* 149 */     return getOPs().b(paramGameProfile);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\DedicatedPlayerList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */