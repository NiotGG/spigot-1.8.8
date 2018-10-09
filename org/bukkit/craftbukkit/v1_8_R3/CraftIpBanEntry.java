/*    */ package org.bukkit.craftbukkit.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Date;
/*    */ import java.util.logging.Level;
/*    */ import net.minecraft.server.v1_8_R3.IpBanEntry;
/*    */ import net.minecraft.server.v1_8_R3.IpBanList;
/*    */ import org.bukkit.BanEntry;
/*    */ 
/*    */ public final class CraftIpBanEntry implements BanEntry
/*    */ {
/*    */   private final IpBanList list;
/*    */   private final String target;
/*    */   private Date created;
/*    */   private String source;
/*    */   private Date expiration;
/*    */   private String reason;
/*    */   
/*    */   public CraftIpBanEntry(String target, IpBanEntry entry, IpBanList list)
/*    */   {
/* 21 */     this.list = list;
/* 22 */     this.target = target;
/* 23 */     this.created = (entry.getCreated() != null ? new Date(entry.getCreated().getTime()) : null);
/* 24 */     this.source = entry.getSource();
/* 25 */     this.expiration = (entry.getExpires() != null ? new Date(entry.getExpires().getTime()) : null);
/* 26 */     this.reason = entry.getReason();
/*    */   }
/*    */   
/*    */   public String getTarget()
/*    */   {
/* 31 */     return this.target;
/*    */   }
/*    */   
/*    */   public Date getCreated()
/*    */   {
/* 36 */     return this.created == null ? null : (Date)this.created.clone();
/*    */   }
/*    */   
/*    */   public void setCreated(Date created)
/*    */   {
/* 41 */     this.created = created;
/*    */   }
/*    */   
/*    */   public String getSource()
/*    */   {
/* 46 */     return this.source;
/*    */   }
/*    */   
/*    */   public void setSource(String source)
/*    */   {
/* 51 */     this.source = source;
/*    */   }
/*    */   
/*    */   public Date getExpiration()
/*    */   {
/* 56 */     return this.expiration == null ? null : (Date)this.expiration.clone();
/*    */   }
/*    */   
/*    */   public void setExpiration(Date expiration)
/*    */   {
/* 61 */     if ((expiration != null) && (expiration.getTime() == new Date(0, 0, 0, 0, 0, 0).getTime())) {
/* 62 */       expiration = null;
/*    */     }
/*    */     
/* 65 */     this.expiration = expiration;
/*    */   }
/*    */   
/*    */   public String getReason()
/*    */   {
/* 70 */     return this.reason;
/*    */   }
/*    */   
/*    */   public void setReason(String reason)
/*    */   {
/* 75 */     this.reason = reason;
/*    */   }
/*    */   
/*    */   public void save()
/*    */   {
/* 80 */     IpBanEntry entry = new IpBanEntry(this.target, this.created, this.source, this.expiration, this.reason);
/* 81 */     this.list.add(entry);
/*    */     try {
/* 83 */       this.list.save();
/*    */     } catch (IOException ex) {
/* 85 */       org.bukkit.Bukkit.getLogger().log(Level.SEVERE, "Failed to save banned-ips.json, {0}", ex.getMessage());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftIpBanEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */