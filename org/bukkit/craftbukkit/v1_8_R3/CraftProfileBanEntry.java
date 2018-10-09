/*    */ package org.bukkit.craftbukkit.v1_8_R3;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.IOException;
/*    */ import java.util.Date;
/*    */ import java.util.logging.Level;
/*    */ import net.minecraft.server.v1_8_R3.GameProfileBanEntry;
/*    */ import net.minecraft.server.v1_8_R3.GameProfileBanList;
/*    */ import org.bukkit.BanEntry;
/*    */ 
/*    */ public final class CraftProfileBanEntry implements BanEntry
/*    */ {
/*    */   private final GameProfileBanList list;
/*    */   private final GameProfile profile;
/*    */   private Date created;
/*    */   private String source;
/*    */   private Date expiration;
/*    */   private String reason;
/*    */   
/*    */   public CraftProfileBanEntry(GameProfile profile, GameProfileBanEntry entry, GameProfileBanList list)
/*    */   {
/* 22 */     this.list = list;
/* 23 */     this.profile = profile;
/* 24 */     this.created = (entry.getCreated() != null ? new Date(entry.getCreated().getTime()) : null);
/* 25 */     this.source = entry.getSource();
/* 26 */     this.expiration = (entry.getExpires() != null ? new Date(entry.getExpires().getTime()) : null);
/* 27 */     this.reason = entry.getReason();
/*    */   }
/*    */   
/*    */   public String getTarget()
/*    */   {
/* 32 */     return this.profile.getName();
/*    */   }
/*    */   
/*    */   public Date getCreated()
/*    */   {
/* 37 */     return this.created == null ? null : (Date)this.created.clone();
/*    */   }
/*    */   
/*    */   public void setCreated(Date created)
/*    */   {
/* 42 */     this.created = created;
/*    */   }
/*    */   
/*    */   public String getSource()
/*    */   {
/* 47 */     return this.source;
/*    */   }
/*    */   
/*    */   public void setSource(String source)
/*    */   {
/* 52 */     this.source = source;
/*    */   }
/*    */   
/*    */   public Date getExpiration()
/*    */   {
/* 57 */     return this.expiration == null ? null : (Date)this.expiration.clone();
/*    */   }
/*    */   
/*    */   public void setExpiration(Date expiration)
/*    */   {
/* 62 */     if ((expiration != null) && (expiration.getTime() == new Date(0, 0, 0, 0, 0, 0).getTime())) {
/* 63 */       expiration = null;
/*    */     }
/*    */     
/* 66 */     this.expiration = expiration;
/*    */   }
/*    */   
/*    */   public String getReason()
/*    */   {
/* 71 */     return this.reason;
/*    */   }
/*    */   
/*    */   public void setReason(String reason)
/*    */   {
/* 76 */     this.reason = reason;
/*    */   }
/*    */   
/*    */   public void save()
/*    */   {
/* 81 */     GameProfileBanEntry entry = new GameProfileBanEntry(this.profile, this.created, this.source, this.expiration, this.reason);
/* 82 */     this.list.add(entry);
/*    */     try {
/* 84 */       this.list.save();
/*    */     } catch (IOException ex) {
/* 86 */       org.bukkit.Bukkit.getLogger().log(Level.SEVERE, "Failed to save banned-players.json, {0}", ex.getMessage());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftProfileBanEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */