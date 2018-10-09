/*     */ package org.bukkit.craftbukkit.v1_8_R3;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.server.v1_8_R3.DedicatedPlayerList;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import net.minecraft.server.v1_8_R3.WorldNBTStorage;
/*     */ import net.minecraft.server.v1_8_R3.WorldServer;
/*     */ import org.bukkit.BanList;
/*     */ import org.bukkit.BanList.Type;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.metadata.PlayerMetadataStore;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ @org.bukkit.configuration.serialization.SerializableAs("Player")
/*     */ public class CraftOfflinePlayer implements OfflinePlayer, org.bukkit.configuration.serialization.ConfigurationSerializable
/*     */ {
/*     */   private final GameProfile profile;
/*     */   private final CraftServer server;
/*     */   private final WorldNBTStorage storage;
/*     */   
/*     */   protected CraftOfflinePlayer(CraftServer server, GameProfile profile)
/*     */   {
/*  32 */     this.server = server;
/*  33 */     this.profile = profile;
/*  34 */     this.storage = ((WorldNBTStorage)((WorldServer)server.console.worlds.get(0)).getDataManager());
/*     */   }
/*     */   
/*     */   public GameProfile getProfile()
/*     */   {
/*  39 */     return this.profile;
/*     */   }
/*     */   
/*     */   public boolean isOnline() {
/*  43 */     return getPlayer() != null;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  47 */     Player player = getPlayer();
/*  48 */     if (player != null) {
/*  49 */       return player.getName();
/*     */     }
/*     */     
/*     */ 
/*  53 */     if (this.profile.getName() != null) {
/*  54 */       return this.profile.getName();
/*     */     }
/*     */     
/*  57 */     NBTTagCompound data = getBukkitData();
/*     */     
/*  59 */     if ((data != null) && 
/*  60 */       (data.hasKey("lastKnownName"))) {
/*  61 */       return data.getString("lastKnownName");
/*     */     }
/*     */     
/*     */ 
/*  65 */     return null;
/*     */   }
/*     */   
/*     */   public UUID getUniqueId() {
/*  69 */     return this.profile.getId();
/*     */   }
/*     */   
/*     */   public Server getServer() {
/*  73 */     return this.server;
/*     */   }
/*     */   
/*     */   public boolean isOp() {
/*  77 */     return this.server.getHandle().isOp(this.profile);
/*     */   }
/*     */   
/*     */   public void setOp(boolean value) {
/*  81 */     if (value == isOp()) {
/*  82 */       return;
/*     */     }
/*     */     
/*  85 */     if (value) {
/*  86 */       this.server.getHandle().addOp(this.profile);
/*     */     } else {
/*  88 */       this.server.getHandle().removeOp(this.profile);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isBanned() {
/*  93 */     if (getName() == null) {
/*  94 */       return false;
/*     */     }
/*     */     
/*  97 */     return this.server.getBanList(BanList.Type.NAME).isBanned(getName());
/*     */   }
/*     */   
/*     */   public void setBanned(boolean value) {
/* 101 */     if (getName() == null) {
/* 102 */       return;
/*     */     }
/*     */     
/* 105 */     if (value) {
/* 106 */       this.server.getBanList(BanList.Type.NAME).addBan(getName(), null, null, null);
/*     */     } else {
/* 108 */       this.server.getBanList(BanList.Type.NAME).pardon(getName());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isWhitelisted() {
/* 113 */     return this.server.getHandle().getWhitelist().isWhitelisted(this.profile);
/*     */   }
/*     */   
/*     */   public void setWhitelisted(boolean value) {
/* 117 */     if (value) {
/* 118 */       this.server.getHandle().addWhitelist(this.profile);
/*     */     } else {
/* 120 */       this.server.getHandle().removeWhitelist(this.profile);
/*     */     }
/*     */   }
/*     */   
/*     */   public Map<String, Object> serialize() {
/* 125 */     Map<String, Object> result = new java.util.LinkedHashMap();
/*     */     
/* 127 */     result.put("UUID", this.profile.getId().toString());
/*     */     
/* 129 */     return result;
/*     */   }
/*     */   
/*     */   public static OfflinePlayer deserialize(Map<String, Object> args)
/*     */   {
/* 134 */     if (args.get("name") != null) {
/* 135 */       return Bukkit.getServer().getOfflinePlayer((String)args.get("name"));
/*     */     }
/*     */     
/* 138 */     return Bukkit.getServer().getOfflinePlayer(UUID.fromString((String)args.get("UUID")));
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 143 */     return getClass().getSimpleName() + "[UUID=" + this.profile.getId() + "]";
/*     */   }
/*     */   
/*     */   public Player getPlayer() {
/* 147 */     return this.server.getPlayer(getUniqueId());
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 152 */     if ((obj == null) || (!(obj instanceof OfflinePlayer))) {
/* 153 */       return false;
/*     */     }
/*     */     
/* 156 */     OfflinePlayer other = (OfflinePlayer)obj;
/* 157 */     if ((getUniqueId() == null) || (other.getUniqueId() == null)) {
/* 158 */       return false;
/*     */     }
/*     */     
/* 161 */     return getUniqueId().equals(other.getUniqueId());
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 166 */     int hash = 5;
/* 167 */     hash = 97 * hash + (getUniqueId() != null ? getUniqueId().hashCode() : 0);
/* 168 */     return hash;
/*     */   }
/*     */   
/*     */   private NBTTagCompound getData() {
/* 172 */     return this.storage.getPlayerData(getUniqueId().toString());
/*     */   }
/*     */   
/*     */   private NBTTagCompound getBukkitData() {
/* 176 */     NBTTagCompound result = getData();
/*     */     
/* 178 */     if (result != null) {
/* 179 */       if (!result.hasKey("bukkit")) {
/* 180 */         result.set("bukkit", new NBTTagCompound());
/*     */       }
/* 182 */       result = result.getCompound("bukkit");
/*     */     }
/*     */     
/* 185 */     return result;
/*     */   }
/*     */   
/*     */   private File getDataFile() {
/* 189 */     return new File(this.storage.getPlayerDir(), getUniqueId() + ".dat");
/*     */   }
/*     */   
/*     */   public long getFirstPlayed() {
/* 193 */     Player player = getPlayer();
/* 194 */     if (player != null) { return player.getFirstPlayed();
/*     */     }
/* 196 */     NBTTagCompound data = getBukkitData();
/*     */     
/* 198 */     if (data != null) {
/* 199 */       if (data.hasKey("firstPlayed")) {
/* 200 */         return data.getLong("firstPlayed");
/*     */       }
/* 202 */       File file = getDataFile();
/* 203 */       return file.lastModified();
/*     */     }
/*     */     
/* 206 */     return 0L;
/*     */   }
/*     */   
/*     */   public long getLastPlayed()
/*     */   {
/* 211 */     Player player = getPlayer();
/* 212 */     if (player != null) { return player.getLastPlayed();
/*     */     }
/* 214 */     NBTTagCompound data = getBukkitData();
/*     */     
/* 216 */     if (data != null) {
/* 217 */       if (data.hasKey("lastPlayed")) {
/* 218 */         return data.getLong("lastPlayed");
/*     */       }
/* 220 */       File file = getDataFile();
/* 221 */       return file.lastModified();
/*     */     }
/*     */     
/* 224 */     return 0L;
/*     */   }
/*     */   
/*     */   public boolean hasPlayedBefore()
/*     */   {
/* 229 */     return getData() != null;
/*     */   }
/*     */   
/*     */   public Location getBedSpawnLocation() {
/* 233 */     NBTTagCompound data = getData();
/* 234 */     if (data == null) { return null;
/*     */     }
/* 236 */     if ((data.hasKey("SpawnX")) && (data.hasKey("SpawnY")) && (data.hasKey("SpawnZ"))) {
/* 237 */       String spawnWorld = data.getString("SpawnWorld");
/* 238 */       if (spawnWorld.equals("")) {
/* 239 */         spawnWorld = ((org.bukkit.World)this.server.getWorlds().get(0)).getName();
/*     */       }
/* 241 */       return new Location(this.server.getWorld(spawnWorld), data.getInt("SpawnX"), data.getInt("SpawnY"), data.getInt("SpawnZ"));
/*     */     }
/* 243 */     return null;
/*     */   }
/*     */   
/*     */   public void setMetadata(String metadataKey, MetadataValue metadataValue) {
/* 247 */     this.server.getPlayerMetadata().setMetadata(this, metadataKey, metadataValue);
/*     */   }
/*     */   
/*     */   public List<MetadataValue> getMetadata(String metadataKey) {
/* 251 */     return this.server.getPlayerMetadata().getMetadata(this, metadataKey);
/*     */   }
/*     */   
/*     */   public boolean hasMetadata(String metadataKey) {
/* 255 */     return this.server.getPlayerMetadata().hasMetadata(this, metadataKey);
/*     */   }
/*     */   
/*     */   public void removeMetadata(String metadataKey, Plugin plugin) {
/* 259 */     this.server.getPlayerMetadata().removeMetadata(this, metadataKey, plugin);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftOfflinePlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */