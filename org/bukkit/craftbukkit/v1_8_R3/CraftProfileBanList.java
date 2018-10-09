/*    */ package org.bukkit.craftbukkit.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.ImmutableSet.Builder;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.IOException;
/*    */ import java.util.Date;
/*    */ import java.util.logging.Level;
/*    */ import net.minecraft.server.v1_8_R3.GameProfileBanEntry;
/*    */ import net.minecraft.server.v1_8_R3.GameProfileBanList;
/*    */ import net.minecraft.server.v1_8_R3.JsonListEntry;
/*    */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*    */ import net.minecraft.server.v1_8_R3.UserCache;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.BanEntry;
/*    */ 
/*    */ public class CraftProfileBanList implements org.bukkit.BanList
/*    */ {
/*    */   private final GameProfileBanList list;
/*    */   
/*    */   public CraftProfileBanList(GameProfileBanList list)
/*    */   {
/* 24 */     this.list = list;
/*    */   }
/*    */   
/*    */   public BanEntry getBanEntry(String target)
/*    */   {
/* 29 */     Validate.notNull(target, "Target cannot be null");
/*    */     
/* 31 */     GameProfile profile = MinecraftServer.getServer().getUserCache().getProfile(target);
/* 32 */     if (profile == null) {
/* 33 */       return null;
/*    */     }
/*    */     
/* 36 */     GameProfileBanEntry entry = (GameProfileBanEntry)this.list.get(profile);
/* 37 */     if (entry == null) {
/* 38 */       return null;
/*    */     }
/*    */     
/* 41 */     return new CraftProfileBanEntry(profile, entry, this.list);
/*    */   }
/*    */   
/*    */   public BanEntry addBan(String target, String reason, Date expires, String source)
/*    */   {
/* 46 */     Validate.notNull(target, "Ban target cannot be null");
/*    */     
/* 48 */     GameProfile profile = MinecraftServer.getServer().getUserCache().getProfile(target);
/* 49 */     if (profile == null) {
/* 50 */       return null;
/*    */     }
/*    */     
/* 53 */     GameProfileBanEntry entry = new GameProfileBanEntry(profile, new Date(), 
/* 54 */       StringUtils.isBlank(source) ? null : source, expires, 
/* 55 */       StringUtils.isBlank(reason) ? null : reason);
/*    */     
/* 57 */     this.list.add(entry);
/*    */     try
/*    */     {
/* 60 */       this.list.save();
/*    */     } catch (IOException ex) {
/* 62 */       org.bukkit.Bukkit.getLogger().log(Level.SEVERE, "Failed to save banned-players.json, {0}", ex.getMessage());
/*    */     }
/*    */     
/* 65 */     return new CraftProfileBanEntry(profile, entry, this.list);
/*    */   }
/*    */   
/*    */   public java.util.Set<BanEntry> getBanEntries()
/*    */   {
/* 70 */     ImmutableSet.Builder<BanEntry> builder = ImmutableSet.builder();
/*    */     
/* 72 */     for (JsonListEntry entry : this.list.getValues()) {
/* 73 */       GameProfile profile = (GameProfile)entry.getKey();
/* 74 */       builder.add(new CraftProfileBanEntry(profile, (GameProfileBanEntry)entry, this.list));
/*    */     }
/*    */     
/* 77 */     return builder.build();
/*    */   }
/*    */   
/*    */   public boolean isBanned(String target)
/*    */   {
/* 82 */     Validate.notNull(target, "Target cannot be null");
/*    */     
/* 84 */     GameProfile profile = MinecraftServer.getServer().getUserCache().getProfile(target);
/* 85 */     if (profile == null) {
/* 86 */       return false;
/*    */     }
/*    */     
/* 89 */     return this.list.isBanned(profile);
/*    */   }
/*    */   
/*    */   public void pardon(String target)
/*    */   {
/* 94 */     Validate.notNull(target, "Target cannot be null");
/*    */     
/* 96 */     GameProfile profile = MinecraftServer.getServer().getUserCache().getProfile(target);
/* 97 */     this.list.remove(profile);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftProfileBanList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */