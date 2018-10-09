/*    */ package org.bukkit.craftbukkit.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.ImmutableSet.Builder;
/*    */ import java.io.IOException;
/*    */ import java.util.Date;
/*    */ import java.util.Set;
/*    */ import java.util.logging.Level;
/*    */ import net.minecraft.server.v1_8_R3.IpBanEntry;
/*    */ import net.minecraft.server.v1_8_R3.IpBanList;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.BanEntry;
/*    */ import org.bukkit.BanList;
/*    */ 
/*    */ public class CraftIpBanList implements BanList
/*    */ {
/*    */   private final IpBanList list;
/*    */   
/*    */   public CraftIpBanList(IpBanList list)
/*    */   {
/* 22 */     this.list = list;
/*    */   }
/*    */   
/*    */   public BanEntry getBanEntry(String target)
/*    */   {
/* 27 */     Validate.notNull(target, "Target cannot be null");
/*    */     
/* 29 */     IpBanEntry entry = (IpBanEntry)this.list.get(target);
/* 30 */     if (entry == null) {
/* 31 */       return null;
/*    */     }
/*    */     
/* 34 */     return new CraftIpBanEntry(target, entry, this.list);
/*    */   }
/*    */   
/*    */   public BanEntry addBan(String target, String reason, Date expires, String source)
/*    */   {
/* 39 */     Validate.notNull(target, "Ban target cannot be null");
/*    */     
/* 41 */     IpBanEntry entry = new IpBanEntry(target, new Date(), 
/* 42 */       StringUtils.isBlank(source) ? null : source, expires, 
/* 43 */       StringUtils.isBlank(reason) ? null : reason);
/*    */     
/* 45 */     this.list.add(entry);
/*    */     try
/*    */     {
/* 48 */       this.list.save();
/*    */     } catch (IOException ex) {
/* 50 */       org.bukkit.Bukkit.getLogger().log(Level.SEVERE, "Failed to save banned-ips.json, {0}", ex.getMessage());
/*    */     }
/*    */     
/* 53 */     return new CraftIpBanEntry(target, entry, this.list);
/*    */   }
/*    */   
/*    */   public Set<BanEntry> getBanEntries()
/*    */   {
/* 58 */     ImmutableSet.Builder<BanEntry> builder = ImmutableSet.builder();
/* 59 */     String[] arrayOfString; int i = (arrayOfString = this.list.getEntries()).length; for (int j = 0; j < i; j++) { String target = arrayOfString[j];
/* 60 */       builder.add(new CraftIpBanEntry(target, (IpBanEntry)this.list.get(target), this.list));
/*    */     }
/*    */     
/* 63 */     return builder.build();
/*    */   }
/*    */   
/*    */   public boolean isBanned(String target)
/*    */   {
/* 68 */     Validate.notNull(target, "Target cannot be null");
/*    */     
/* 70 */     return this.list.isBanned(java.net.InetSocketAddress.createUnresolved(target, 0));
/*    */   }
/*    */   
/*    */   public void pardon(String target)
/*    */   {
/* 75 */     Validate.notNull(target, "Target cannot be null");
/*    */     
/* 77 */     this.list.remove(target);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftIpBanList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */