/*      */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.logging.Logger;
/*      */ import net.md_5.bungee.api.chat.BaseComponent;
/*      */ import net.minecraft.server.v1_8_R3.AttributeRanged;
/*      */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*      */ import net.minecraft.server.v1_8_R3.DedicatedPlayerList;
/*      */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*      */ import net.minecraft.server.v1_8_R3.EntityTracker;
/*      */ import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
/*      */ import net.minecraft.server.v1_8_R3.EnumParticle;
/*      */ import net.minecraft.server.v1_8_R3.FoodMetaData;
/*      */ import net.minecraft.server.v1_8_R3.IChatBaseComponent;
/*      */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*      */ import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
/*      */ import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
/*      */ import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
/*      */ import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
/*      */ import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
/*      */ import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
/*      */ import net.minecraft.server.v1_8_R3.PlayerAbilities;
/*      */ import net.minecraft.server.v1_8_R3.PlayerConnection;
/*      */ import net.minecraft.server.v1_8_R3.ServerStatisticManager;
/*      */ import net.minecraft.server.v1_8_R3.WorldServer;
/*      */ import org.apache.commons.lang.Validate;
/*      */ import org.bukkit.Achievement;
/*      */ import org.bukkit.BanList;
/*      */ import org.bukkit.BanList.Type;
/*      */ import org.bukkit.Effect;
/*      */ import org.bukkit.GameMode;
/*      */ import org.bukkit.Instrument;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.Note;
/*      */ import org.bukkit.OfflinePlayer;
/*      */ import org.bukkit.Server;
/*      */ import org.bukkit.Statistic.Type;
/*      */ import org.bukkit.WeatherType;
/*      */ import org.bukkit.conversations.Conversation;
/*      */ import org.bukkit.conversations.ConversationAbandonedEvent;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftStatistic;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.conversations.ConversationTracker;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.map.RenderData;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.metadata.PlayerMetadataStore;
/*      */ import org.bukkit.entity.EntityType;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.entity.Player.Spigot;
/*      */ import org.bukkit.event.player.PlayerGameModeChangeEvent;
/*      */ import org.bukkit.event.player.PlayerTeleportEvent;
/*      */ import org.bukkit.inventory.InventoryView.Property;
/*      */ import org.bukkit.map.MapCursor;
/*      */ import org.bukkit.map.MapView;
/*      */ import org.bukkit.metadata.MetadataValue;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ 
/*      */ @org.bukkit.configuration.serialization.DelegateDeserialization(org.bukkit.craftbukkit.v1_8_R3.CraftOfflinePlayer.class)
/*      */ public class CraftPlayer extends CraftHumanEntity implements Player
/*      */ {
/*   69 */   private long firstPlayed = 0L;
/*   70 */   private long lastPlayed = 0L;
/*   71 */   private boolean hasPlayedBefore = false;
/*   72 */   private final ConversationTracker conversationTracker = new ConversationTracker();
/*   73 */   private final Set<String> channels = new HashSet();
/*   74 */   private final Set<UUID> hiddenPlayers = new HashSet();
/*   75 */   private int hash = 0;
/*   76 */   private double health = 20.0D;
/*   77 */   private boolean scaledHealth = false;
/*   78 */   private double healthScale = 20.0D;
/*      */   
/*      */   public CraftPlayer(CraftServer server, EntityPlayer entity) {
/*   81 */     super(server, entity);
/*      */     
/*   83 */     this.firstPlayed = System.currentTimeMillis();
/*      */   }
/*      */   
/*      */   public com.mojang.authlib.GameProfile getProfile() {
/*   87 */     return getHandle().getProfile();
/*      */   }
/*      */   
/*      */   public boolean isOp()
/*      */   {
/*   92 */     return this.server.getHandle().isOp(getProfile());
/*      */   }
/*      */   
/*      */   public void setOp(boolean value)
/*      */   {
/*   97 */     if (value == isOp()) { return;
/*      */     }
/*   99 */     if (value) {
/*  100 */       this.server.getHandle().addOp(getProfile());
/*      */     } else {
/*  102 */       this.server.getHandle().removeOp(getProfile());
/*      */     }
/*      */     
/*  105 */     this.perm.recalculatePermissions();
/*      */   }
/*      */   
/*      */   public boolean isOnline() {
/*  109 */     return this.server.getPlayer(getUniqueId()) != null;
/*      */   }
/*      */   
/*      */   public InetSocketAddress getAddress() {
/*  113 */     if (getHandle().playerConnection == null) { return null;
/*      */     }
/*  115 */     java.net.SocketAddress addr = getHandle().playerConnection.networkManager.getSocketAddress();
/*  116 */     if ((addr instanceof InetSocketAddress)) {
/*  117 */       return (InetSocketAddress)addr;
/*      */     }
/*  119 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public double getEyeHeight()
/*      */   {
/*  125 */     return getEyeHeight(false);
/*      */   }
/*      */   
/*      */   public double getEyeHeight(boolean ignoreSneaking)
/*      */   {
/*  130 */     if (ignoreSneaking) {
/*  131 */       return 1.62D;
/*      */     }
/*  133 */     if (isSneaking()) {
/*  134 */       return 1.54D;
/*      */     }
/*  136 */     return 1.62D;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void sendRawMessage(String message)
/*      */   {
/*  143 */     if (getHandle().playerConnection == null) return;
/*      */     IChatBaseComponent[] arrayOfIChatBaseComponent;
/*  145 */     int i = (arrayOfIChatBaseComponent = org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage.fromString(message)).length; for (int j = 0; j < i; j++) { IChatBaseComponent component = arrayOfIChatBaseComponent[j];
/*  146 */       getHandle().playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutChat(component));
/*      */     }
/*      */   }
/*      */   
/*      */   public void sendMessage(String message)
/*      */   {
/*  152 */     if (!this.conversationTracker.isConversingModaly()) {
/*  153 */       sendRawMessage(message);
/*      */     }
/*      */   }
/*      */   
/*      */   public void sendMessage(String[] messages) {
/*      */     String[] arrayOfString;
/*  159 */     int i = (arrayOfString = messages).length; for (int j = 0; j < i; j++) { String message = arrayOfString[j];
/*  160 */       sendMessage(message);
/*      */     }
/*      */   }
/*      */   
/*      */   public String getDisplayName()
/*      */   {
/*  166 */     return getHandle().displayName;
/*      */   }
/*      */   
/*      */   public void setDisplayName(String name)
/*      */   {
/*  171 */     getHandle().displayName = (name == null ? getName() : name);
/*      */   }
/*      */   
/*      */   public String getPlayerListName()
/*      */   {
/*  176 */     return getHandle().listName == null ? getName() : org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage.fromComponent(getHandle().listName);
/*      */   }
/*      */   
/*      */   public void setPlayerListName(String name)
/*      */   {
/*  181 */     if (name == null) {
/*  182 */       name = getName();
/*      */     }
/*  184 */     getHandle().listName = (name.equals(getName()) ? null : org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage.fromString(name)[0]);
/*  185 */     for (EntityPlayer player : this.server.getHandle().players) {
/*  186 */       if (player.getBukkitEntity().canSee(this)) {
/*  187 */         player.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, new EntityPlayer[] { getHandle() }));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean equals(Object obj)
/*      */   {
/*  194 */     if (!(obj instanceof OfflinePlayer)) {
/*  195 */       return false;
/*      */     }
/*  197 */     OfflinePlayer other = (OfflinePlayer)obj;
/*  198 */     if ((getUniqueId() == null) || (other.getUniqueId() == null)) {
/*  199 */       return false;
/*      */     }
/*      */     
/*  202 */     boolean uuidEquals = getUniqueId().equals(other.getUniqueId());
/*  203 */     boolean idEquals = true;
/*      */     
/*  205 */     if ((other instanceof CraftPlayer)) {
/*  206 */       idEquals = getEntityId() == ((CraftPlayer)other).getEntityId();
/*      */     }
/*      */     
/*  209 */     return (uuidEquals) && (idEquals);
/*      */   }
/*      */   
/*      */   public void kickPlayer(String message)
/*      */   {
/*  214 */     org.spigotmc.AsyncCatcher.catchOp("player kick");
/*  215 */     if (getHandle().playerConnection == null) { return;
/*      */     }
/*  217 */     getHandle().playerConnection.disconnect(message == null ? "" : message);
/*      */   }
/*      */   
/*      */   public void setCompassTarget(Location loc)
/*      */   {
/*  222 */     if (getHandle().playerConnection == null) { return;
/*      */     }
/*      */     
/*  225 */     getHandle().playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutSpawnPosition(new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())));
/*      */   }
/*      */   
/*      */   public Location getCompassTarget()
/*      */   {
/*  230 */     return getHandle().compassTarget;
/*      */   }
/*      */   
/*      */   public void chat(String msg)
/*      */   {
/*  235 */     if (getHandle().playerConnection == null) { return;
/*      */     }
/*  237 */     getHandle().playerConnection.chat(msg, false);
/*      */   }
/*      */   
/*      */   public boolean performCommand(String command)
/*      */   {
/*  242 */     return this.server.dispatchCommand(this, command);
/*      */   }
/*      */   
/*      */   public void playNote(Location loc, byte instrument, byte note)
/*      */   {
/*  247 */     if (getHandle().playerConnection == null) { return;
/*      */     }
/*  249 */     String instrumentName = null;
/*  250 */     switch (instrument) {
/*      */     case 0: 
/*  252 */       instrumentName = "harp";
/*  253 */       break;
/*      */     case 1: 
/*  255 */       instrumentName = "bd";
/*  256 */       break;
/*      */     case 2: 
/*  258 */       instrumentName = "snare";
/*  259 */       break;
/*      */     case 3: 
/*  261 */       instrumentName = "hat";
/*  262 */       break;
/*      */     case 4: 
/*  264 */       instrumentName = "bassattack";
/*      */     }
/*      */     
/*      */     
/*  268 */     float f = (float)Math.pow(2.0D, (note - 12.0D) / 12.0D);
/*  269 */     getHandle().playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect("note." + instrumentName, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 3.0F, f));
/*      */   }
/*      */   
/*      */   public void playNote(Location loc, Instrument instrument, Note note)
/*      */   {
/*  274 */     if (getHandle().playerConnection == null) { return;
/*      */     }
/*  276 */     String instrumentName = null;
/*  277 */     switch (instrument.ordinal()) {
/*      */     case 0: 
/*  279 */       instrumentName = "harp";
/*  280 */       break;
/*      */     case 1: 
/*  282 */       instrumentName = "bd";
/*  283 */       break;
/*      */     case 2: 
/*  285 */       instrumentName = "snare";
/*  286 */       break;
/*      */     case 3: 
/*  288 */       instrumentName = "hat";
/*  289 */       break;
/*      */     case 4: 
/*  291 */       instrumentName = "bassattack";
/*      */     }
/*      */     
/*  294 */     float f = (float)Math.pow(2.0D, (note.getId() - 12.0D) / 12.0D);
/*  295 */     getHandle().playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect("note." + instrumentName, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 3.0F, f));
/*      */   }
/*      */   
/*      */   public void playSound(Location loc, org.bukkit.Sound sound, float volume, float pitch)
/*      */   {
/*  300 */     if (sound == null) {
/*  301 */       return;
/*      */     }
/*  303 */     playSound(loc, org.bukkit.craftbukkit.v1_8_R3.CraftSound.getSound(sound), volume, pitch);
/*      */   }
/*      */   
/*      */   public void playSound(Location loc, String sound, float volume, float pitch)
/*      */   {
/*  308 */     if ((loc == null) || (sound == null) || (getHandle().playerConnection == null)) { return;
/*      */     }
/*  310 */     double x = loc.getBlockX() + 0.5D;
/*  311 */     double y = loc.getBlockY() + 0.5D;
/*  312 */     double z = loc.getBlockZ() + 0.5D;
/*      */     
/*  314 */     PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect(sound, x, y, z, volume, pitch);
/*  315 */     getHandle().playerConnection.sendPacket(packet);
/*      */   }
/*      */   
/*      */   public void playEffect(Location loc, Effect effect, int data)
/*      */   {
/*  320 */     if (getHandle().playerConnection == null) { return;
/*      */     }
/*  322 */     spigot().playEffect(loc, effect, data, 0, 0.0F, 0.0F, 0.0F, 1.0F, 1, 64);
/*      */   }
/*      */   
/*      */   public <T> void playEffect(Location loc, Effect effect, T data)
/*      */   {
/*  327 */     if (data != null) {
/*  328 */       Validate.isTrue(data.getClass().isAssignableFrom(effect.getData()), "Wrong kind of data for this effect!");
/*      */     } else {
/*  330 */       Validate.isTrue(effect.getData() == null, "Wrong kind of data for this effect!");
/*      */     }
/*      */     
/*  333 */     int datavalue = data == null ? 0 : org.bukkit.craftbukkit.v1_8_R3.CraftEffect.getDataValue(effect, data);
/*  334 */     playEffect(loc, effect, datavalue);
/*      */   }
/*      */   
/*      */   public void sendBlockChange(Location loc, Material material, byte data)
/*      */   {
/*  339 */     sendBlockChange(loc, material.getId(), data);
/*      */   }
/*      */   
/*      */   public void sendBlockChange(Location loc, int material, byte data)
/*      */   {
/*  344 */     if (getHandle().playerConnection == null) { return;
/*      */     }
/*  346 */     net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange packet = new net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange(((CraftWorld)loc.getWorld()).getHandle(), new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
/*      */     
/*  348 */     packet.block = org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getBlock(material).fromLegacyData(data);
/*  349 */     getHandle().playerConnection.sendPacket(packet);
/*      */   }
/*      */   
/*      */   public void sendSignChange(Location loc, String[] lines)
/*      */   {
/*  354 */     if (getHandle().playerConnection == null) {
/*  355 */       return;
/*      */     }
/*      */     
/*  358 */     if (lines == null) {
/*  359 */       lines = new String[4];
/*      */     }
/*      */     
/*  362 */     Validate.notNull(loc, "Location can not be null");
/*  363 */     if (lines.length < 4) {
/*  364 */       throw new IllegalArgumentException("Must have at least 4 lines");
/*      */     }
/*      */     
/*  367 */     IChatBaseComponent[] components = org.bukkit.craftbukkit.v1_8_R3.block.CraftSign.sanitizeLines(lines);
/*      */     
/*  369 */     getHandle().playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutUpdateSign(getHandle().world, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), components));
/*      */   }
/*      */   
/*      */   public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data)
/*      */   {
/*  374 */     if (getHandle().playerConnection == null) { return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  403 */     throw new org.apache.commons.lang.NotImplementedException("Chunk changes do not yet work");
/*      */   }
/*      */   
/*      */   public void sendMap(MapView map)
/*      */   {
/*  408 */     if (getHandle().playerConnection == null) { return;
/*      */     }
/*  410 */     RenderData data = ((org.bukkit.craftbukkit.v1_8_R3.map.CraftMapView)map).render(this);
/*  411 */     Collection<net.minecraft.server.v1_8_R3.MapIcon> icons = new java.util.ArrayList();
/*  412 */     for (MapCursor cursor : data.cursors) {
/*  413 */       if (cursor.isVisible()) {
/*  414 */         icons.add(new net.minecraft.server.v1_8_R3.MapIcon(cursor.getRawType(), cursor.getX(), cursor.getY(), cursor.getDirection()));
/*      */       }
/*      */     }
/*      */     
/*  418 */     net.minecraft.server.v1_8_R3.PacketPlayOutMap packet = new net.minecraft.server.v1_8_R3.PacketPlayOutMap(map.getId(), map.getScale().getValue(), icons, data.buffer, 0, 0, 0, 0);
/*  419 */     getHandle().playerConnection.sendPacket(packet);
/*      */   }
/*      */   
/*      */   public boolean teleport(Location location, org.bukkit.event.player.PlayerTeleportEvent.TeleportCause cause)
/*      */   {
/*  424 */     EntityPlayer entity = getHandle();
/*      */     
/*  426 */     if ((getHealth() == 0.0D) || (entity.dead)) {
/*  427 */       return false;
/*      */     }
/*      */     
/*  430 */     if ((entity.playerConnection == null) || (entity.playerConnection.isDisconnected())) {
/*  431 */       return false;
/*      */     }
/*      */     
/*  434 */     if (entity.passenger != null) {
/*  435 */       return false;
/*      */     }
/*      */     
/*      */ 
/*  439 */     Location from = getLocation();
/*      */     
/*  441 */     Location to = location;
/*      */     
/*  443 */     PlayerTeleportEvent event = new PlayerTeleportEvent(this, from, to, cause);
/*  444 */     this.server.getPluginManager().callEvent(event);
/*      */     
/*      */ 
/*  447 */     if (event.isCancelled()) {
/*  448 */       return false;
/*      */     }
/*      */     
/*      */ 
/*  452 */     entity.mount(null);
/*      */     
/*      */ 
/*  455 */     from = event.getFrom();
/*      */     
/*  457 */     to = event.getTo();
/*      */     
/*  459 */     WorldServer fromWorld = ((CraftWorld)from.getWorld()).getHandle();
/*  460 */     WorldServer toWorld = ((CraftWorld)to.getWorld()).getHandle();
/*      */     
/*      */ 
/*  463 */     if (getHandle().activeContainer != getHandle().defaultContainer) {
/*  464 */       getHandle().closeInventory();
/*      */     }
/*      */     
/*      */ 
/*  468 */     if (fromWorld == toWorld) {
/*  469 */       entity.playerConnection.teleport(to);
/*      */     } else {
/*  471 */       this.server.getHandle().moveToWorld(entity, toWorld.dimension, true, to, true);
/*      */     }
/*  473 */     return true;
/*      */   }
/*      */   
/*      */   public void setSneaking(boolean sneak)
/*      */   {
/*  478 */     getHandle().setSneaking(sneak);
/*      */   }
/*      */   
/*      */   public boolean isSneaking()
/*      */   {
/*  483 */     return getHandle().isSneaking();
/*      */   }
/*      */   
/*      */   public boolean isSprinting()
/*      */   {
/*  488 */     return getHandle().isSprinting();
/*      */   }
/*      */   
/*      */   public void setSprinting(boolean sprinting)
/*      */   {
/*  493 */     getHandle().setSprinting(sprinting);
/*      */   }
/*      */   
/*      */   public void loadData()
/*      */   {
/*  498 */     this.server.getHandle().playerFileData.load(getHandle());
/*      */   }
/*      */   
/*      */   public void saveData()
/*      */   {
/*  503 */     this.server.getHandle().playerFileData.save(getHandle());
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public void updateInventory()
/*      */   {
/*  509 */     getHandle().updateInventory(getHandle().activeContainer);
/*      */   }
/*      */   
/*      */   public void setSleepingIgnored(boolean isSleeping)
/*      */   {
/*  514 */     getHandle().fauxSleeping = isSleeping;
/*  515 */     ((CraftWorld)getWorld()).getHandle().checkSleepStatus();
/*      */   }
/*      */   
/*      */   public boolean isSleepingIgnored()
/*      */   {
/*  520 */     return getHandle().fauxSleeping;
/*      */   }
/*      */   
/*      */   public void awardAchievement(Achievement achievement)
/*      */   {
/*  525 */     Validate.notNull(achievement, "Achievement cannot be null");
/*  526 */     if ((achievement.hasParent()) && (!hasAchievement(achievement.getParent()))) {
/*  527 */       awardAchievement(achievement.getParent());
/*      */     }
/*  529 */     getHandle().getStatisticManager().setStatistic(getHandle(), CraftStatistic.getNMSAchievement(achievement), 1);
/*  530 */     getHandle().getStatisticManager().updateStatistics(getHandle());
/*      */   }
/*      */   
/*      */   public void removeAchievement(Achievement achievement)
/*      */   {
/*  535 */     Validate.notNull(achievement, "Achievement cannot be null");
/*  536 */     Achievement[] arrayOfAchievement; int i = (arrayOfAchievement = Achievement.values()).length; for (int j = 0; j < i; j++) { Achievement achieve = arrayOfAchievement[j];
/*  537 */       if ((achieve.getParent() == achievement) && (hasAchievement(achieve))) {
/*  538 */         removeAchievement(achieve);
/*      */       }
/*      */     }
/*  541 */     getHandle().getStatisticManager().setStatistic(getHandle(), CraftStatistic.getNMSAchievement(achievement), 0);
/*      */   }
/*      */   
/*      */   public boolean hasAchievement(Achievement achievement)
/*      */   {
/*  546 */     Validate.notNull(achievement, "Achievement cannot be null");
/*  547 */     return getHandle().getStatisticManager().hasAchievement(CraftStatistic.getNMSAchievement(achievement));
/*      */   }
/*      */   
/*      */   public void incrementStatistic(org.bukkit.Statistic statistic)
/*      */   {
/*  552 */     incrementStatistic(statistic, 1);
/*      */   }
/*      */   
/*      */   public void decrementStatistic(org.bukkit.Statistic statistic)
/*      */   {
/*  557 */     decrementStatistic(statistic, 1);
/*      */   }
/*      */   
/*      */   public int getStatistic(org.bukkit.Statistic statistic)
/*      */   {
/*  562 */     Validate.notNull(statistic, "Statistic cannot be null");
/*  563 */     Validate.isTrue(statistic.getType() == Statistic.Type.UNTYPED, "Must supply additional paramater for this statistic");
/*  564 */     return getHandle().getStatisticManager().getStatisticValue(CraftStatistic.getNMSStatistic(statistic));
/*      */   }
/*      */   
/*      */   public void incrementStatistic(org.bukkit.Statistic statistic, int amount)
/*      */   {
/*  569 */     Validate.isTrue(amount > 0, "Amount must be greater than 0");
/*  570 */     setStatistic(statistic, getStatistic(statistic) + amount);
/*      */   }
/*      */   
/*      */   public void decrementStatistic(org.bukkit.Statistic statistic, int amount)
/*      */   {
/*  575 */     Validate.isTrue(amount > 0, "Amount must be greater than 0");
/*  576 */     setStatistic(statistic, getStatistic(statistic) - amount);
/*      */   }
/*      */   
/*      */   public void setStatistic(org.bukkit.Statistic statistic, int newValue)
/*      */   {
/*  581 */     Validate.notNull(statistic, "Statistic cannot be null");
/*  582 */     Validate.isTrue(statistic.getType() == Statistic.Type.UNTYPED, "Must supply additional paramater for this statistic");
/*  583 */     Validate.isTrue(newValue >= 0, "Value must be greater than or equal to 0");
/*  584 */     net.minecraft.server.v1_8_R3.Statistic nmsStatistic = CraftStatistic.getNMSStatistic(statistic);
/*  585 */     getHandle().getStatisticManager().setStatistic(getHandle(), nmsStatistic, newValue);
/*      */   }
/*      */   
/*      */   public void incrementStatistic(org.bukkit.Statistic statistic, Material material)
/*      */   {
/*  590 */     incrementStatistic(statistic, material, 1);
/*      */   }
/*      */   
/*      */   public void decrementStatistic(org.bukkit.Statistic statistic, Material material)
/*      */   {
/*  595 */     decrementStatistic(statistic, material, 1);
/*      */   }
/*      */   
/*      */   public int getStatistic(org.bukkit.Statistic statistic, Material material)
/*      */   {
/*  600 */     Validate.notNull(statistic, "Statistic cannot be null");
/*  601 */     Validate.notNull(material, "Material cannot be null");
/*  602 */     Validate.isTrue((statistic.getType() == Statistic.Type.BLOCK) || (statistic.getType() == Statistic.Type.ITEM), "This statistic does not take a Material parameter");
/*  603 */     net.minecraft.server.v1_8_R3.Statistic nmsStatistic = CraftStatistic.getMaterialStatistic(statistic, material);
/*  604 */     Validate.notNull(nmsStatistic, "The supplied Material does not have a corresponding statistic");
/*  605 */     return getHandle().getStatisticManager().getStatisticValue(nmsStatistic);
/*      */   }
/*      */   
/*      */   public void incrementStatistic(org.bukkit.Statistic statistic, Material material, int amount)
/*      */   {
/*  610 */     Validate.isTrue(amount > 0, "Amount must be greater than 0");
/*  611 */     setStatistic(statistic, material, getStatistic(statistic, material) + amount);
/*      */   }
/*      */   
/*      */   public void decrementStatistic(org.bukkit.Statistic statistic, Material material, int amount)
/*      */   {
/*  616 */     Validate.isTrue(amount > 0, "Amount must be greater than 0");
/*  617 */     setStatistic(statistic, material, getStatistic(statistic, material) - amount);
/*      */   }
/*      */   
/*      */   public void setStatistic(org.bukkit.Statistic statistic, Material material, int newValue)
/*      */   {
/*  622 */     Validate.notNull(statistic, "Statistic cannot be null");
/*  623 */     Validate.notNull(material, "Material cannot be null");
/*  624 */     Validate.isTrue(newValue >= 0, "Value must be greater than or equal to 0");
/*  625 */     Validate.isTrue((statistic.getType() == Statistic.Type.BLOCK) || (statistic.getType() == Statistic.Type.ITEM), "This statistic does not take a Material parameter");
/*  626 */     net.minecraft.server.v1_8_R3.Statistic nmsStatistic = CraftStatistic.getMaterialStatistic(statistic, material);
/*  627 */     Validate.notNull(nmsStatistic, "The supplied Material does not have a corresponding statistic");
/*  628 */     getHandle().getStatisticManager().setStatistic(getHandle(), nmsStatistic, newValue);
/*      */   }
/*      */   
/*      */   public void incrementStatistic(org.bukkit.Statistic statistic, EntityType entityType)
/*      */   {
/*  633 */     incrementStatistic(statistic, entityType, 1);
/*      */   }
/*      */   
/*      */   public void decrementStatistic(org.bukkit.Statistic statistic, EntityType entityType)
/*      */   {
/*  638 */     decrementStatistic(statistic, entityType, 1);
/*      */   }
/*      */   
/*      */   public int getStatistic(org.bukkit.Statistic statistic, EntityType entityType)
/*      */   {
/*  643 */     Validate.notNull(statistic, "Statistic cannot be null");
/*  644 */     Validate.notNull(entityType, "EntityType cannot be null");
/*  645 */     Validate.isTrue(statistic.getType() == Statistic.Type.ENTITY, "This statistic does not take an EntityType parameter");
/*  646 */     net.minecraft.server.v1_8_R3.Statistic nmsStatistic = CraftStatistic.getEntityStatistic(statistic, entityType);
/*  647 */     Validate.notNull(nmsStatistic, "The supplied EntityType does not have a corresponding statistic");
/*  648 */     return getHandle().getStatisticManager().getStatisticValue(nmsStatistic);
/*      */   }
/*      */   
/*      */   public void incrementStatistic(org.bukkit.Statistic statistic, EntityType entityType, int amount)
/*      */   {
/*  653 */     Validate.isTrue(amount > 0, "Amount must be greater than 0");
/*  654 */     setStatistic(statistic, entityType, getStatistic(statistic, entityType) + amount);
/*      */   }
/*      */   
/*      */   public void decrementStatistic(org.bukkit.Statistic statistic, EntityType entityType, int amount)
/*      */   {
/*  659 */     Validate.isTrue(amount > 0, "Amount must be greater than 0");
/*  660 */     setStatistic(statistic, entityType, getStatistic(statistic, entityType) - amount);
/*      */   }
/*      */   
/*      */   public void setStatistic(org.bukkit.Statistic statistic, EntityType entityType, int newValue)
/*      */   {
/*  665 */     Validate.notNull(statistic, "Statistic cannot be null");
/*  666 */     Validate.notNull(entityType, "EntityType cannot be null");
/*  667 */     Validate.isTrue(newValue >= 0, "Value must be greater than or equal to 0");
/*  668 */     Validate.isTrue(statistic.getType() == Statistic.Type.ENTITY, "This statistic does not take an EntityType parameter");
/*  669 */     net.minecraft.server.v1_8_R3.Statistic nmsStatistic = CraftStatistic.getEntityStatistic(statistic, entityType);
/*  670 */     Validate.notNull(nmsStatistic, "The supplied EntityType does not have a corresponding statistic");
/*  671 */     getHandle().getStatisticManager().setStatistic(getHandle(), nmsStatistic, newValue);
/*      */   }
/*      */   
/*      */   public void setPlayerTime(long time, boolean relative)
/*      */   {
/*  676 */     getHandle().timeOffset = time;
/*  677 */     getHandle().relativeTime = relative;
/*      */   }
/*      */   
/*      */   public long getPlayerTimeOffset()
/*      */   {
/*  682 */     return getHandle().timeOffset;
/*      */   }
/*      */   
/*      */   public long getPlayerTime()
/*      */   {
/*  687 */     return getHandle().getPlayerTime();
/*      */   }
/*      */   
/*      */   public boolean isPlayerTimeRelative()
/*      */   {
/*  692 */     return getHandle().relativeTime;
/*      */   }
/*      */   
/*      */   public void resetPlayerTime()
/*      */   {
/*  697 */     setPlayerTime(0L, true);
/*      */   }
/*      */   
/*      */   public void setPlayerWeather(WeatherType type)
/*      */   {
/*  702 */     getHandle().setPlayerWeather(type, true);
/*      */   }
/*      */   
/*      */   public WeatherType getPlayerWeather()
/*      */   {
/*  707 */     return getHandle().getPlayerWeather();
/*      */   }
/*      */   
/*      */   public void resetPlayerWeather()
/*      */   {
/*  712 */     getHandle().resetPlayerWeather();
/*      */   }
/*      */   
/*      */   public boolean isBanned()
/*      */   {
/*  717 */     return this.server.getBanList(BanList.Type.NAME).isBanned(getName());
/*      */   }
/*      */   
/*      */   public void setBanned(boolean value)
/*      */   {
/*  722 */     if (value) {
/*  723 */       this.server.getBanList(BanList.Type.NAME).addBan(getName(), null, null, null);
/*      */     } else {
/*  725 */       this.server.getBanList(BanList.Type.NAME).pardon(getName());
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isWhitelisted()
/*      */   {
/*  731 */     return this.server.getHandle().getWhitelist().isWhitelisted(getProfile());
/*      */   }
/*      */   
/*      */   public void setWhitelisted(boolean value)
/*      */   {
/*  736 */     if (value) {
/*  737 */       this.server.getHandle().addWhitelist(getProfile());
/*      */     } else {
/*  739 */       this.server.getHandle().removeWhitelist(getProfile());
/*      */     }
/*      */   }
/*      */   
/*      */   public void setGameMode(GameMode mode)
/*      */   {
/*  745 */     if (getHandle().playerConnection == null) { return;
/*      */     }
/*  747 */     if (mode == null) {
/*  748 */       throw new IllegalArgumentException("Mode cannot be null");
/*      */     }
/*      */     
/*  751 */     if (mode != getGameMode()) {
/*  752 */       PlayerGameModeChangeEvent event = new PlayerGameModeChangeEvent(this, mode);
/*  753 */       this.server.getPluginManager().callEvent(event);
/*  754 */       if (event.isCancelled()) {
/*  755 */         return;
/*      */       }
/*      */       
/*  758 */       getHandle().setSpectatorTarget(getHandle());
/*  759 */       getHandle().playerInteractManager.setGameMode(net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode.getById(mode.getValue()));
/*  760 */       getHandle().fallDistance = 0.0F;
/*  761 */       getHandle().playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange(3, mode.getValue()));
/*      */     }
/*      */   }
/*      */   
/*      */   public GameMode getGameMode()
/*      */   {
/*  767 */     return GameMode.getByValue(getHandle().playerInteractManager.getGameMode().getId());
/*      */   }
/*      */   
/*      */   public void giveExp(int exp)
/*      */   {
/*  772 */     getHandle().giveExp(exp);
/*      */   }
/*      */   
/*      */   public void giveExpLevels(int levels)
/*      */   {
/*  777 */     getHandle().levelDown(levels);
/*      */   }
/*      */   
/*      */   public float getExp()
/*      */   {
/*  782 */     return getHandle().exp;
/*      */   }
/*      */   
/*      */   public void setExp(float exp)
/*      */   {
/*  787 */     getHandle().exp = exp;
/*  788 */     getHandle().lastSentExp = -1;
/*      */   }
/*      */   
/*      */   public int getLevel()
/*      */   {
/*  793 */     return getHandle().expLevel;
/*      */   }
/*      */   
/*      */   public void setLevel(int level)
/*      */   {
/*  798 */     getHandle().expLevel = level;
/*  799 */     getHandle().lastSentExp = -1;
/*      */   }
/*      */   
/*      */   public int getTotalExperience()
/*      */   {
/*  804 */     return getHandle().expTotal;
/*      */   }
/*      */   
/*      */   public void setTotalExperience(int exp)
/*      */   {
/*  809 */     getHandle().expTotal = exp;
/*      */   }
/*      */   
/*      */   public float getExhaustion()
/*      */   {
/*  814 */     return getHandle().getFoodData().exhaustionLevel;
/*      */   }
/*      */   
/*      */   public void setExhaustion(float value)
/*      */   {
/*  819 */     getHandle().getFoodData().exhaustionLevel = value;
/*      */   }
/*      */   
/*      */   public float getSaturation()
/*      */   {
/*  824 */     return getHandle().getFoodData().saturationLevel;
/*      */   }
/*      */   
/*      */   public void setSaturation(float value)
/*      */   {
/*  829 */     getHandle().getFoodData().saturationLevel = value;
/*      */   }
/*      */   
/*      */   public int getFoodLevel()
/*      */   {
/*  834 */     return getHandle().getFoodData().foodLevel;
/*      */   }
/*      */   
/*      */   public void setFoodLevel(int value)
/*      */   {
/*  839 */     getHandle().getFoodData().foodLevel = value;
/*      */   }
/*      */   
/*      */   public Location getBedSpawnLocation()
/*      */   {
/*  844 */     org.bukkit.World world = getServer().getWorld(getHandle().spawnWorld);
/*  845 */     BlockPosition bed = getHandle().getBed();
/*      */     
/*  847 */     if ((world != null) && (bed != null)) {
/*  848 */       bed = net.minecraft.server.v1_8_R3.EntityHuman.getBed(((CraftWorld)world).getHandle(), bed, getHandle().isRespawnForced());
/*  849 */       if (bed != null) {
/*  850 */         return new Location(world, bed.getX(), bed.getY(), bed.getZ());
/*      */       }
/*      */     }
/*  853 */     return null;
/*      */   }
/*      */   
/*      */   public void setBedSpawnLocation(Location location)
/*      */   {
/*  858 */     setBedSpawnLocation(location, false);
/*      */   }
/*      */   
/*      */   public void setBedSpawnLocation(Location location, boolean override)
/*      */   {
/*  863 */     if (location == null) {
/*  864 */       getHandle().setRespawnPosition(null, override);
/*      */     } else {
/*  866 */       getHandle().setRespawnPosition(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()), override);
/*  867 */       getHandle().spawnWorld = location.getWorld().getName();
/*      */     }
/*      */   }
/*      */   
/*      */   public void hidePlayer(Player player)
/*      */   {
/*  873 */     Validate.notNull(player, "hidden player cannot be null");
/*  874 */     if (getHandle().playerConnection == null) return;
/*  875 */     if (equals(player)) return;
/*  876 */     if (this.hiddenPlayers.contains(player.getUniqueId())) return;
/*  877 */     this.hiddenPlayers.add(player.getUniqueId());
/*      */     
/*      */ 
/*  880 */     EntityTracker tracker = ((WorldServer)this.entity.world).tracker;
/*  881 */     EntityPlayer other = ((CraftPlayer)player).getHandle();
/*  882 */     EntityTrackerEntry entry = (EntityTrackerEntry)tracker.trackedEntities.get(other.getId());
/*  883 */     if (entry != null) {
/*  884 */       entry.clear(getHandle());
/*      */     }
/*      */     
/*      */ 
/*  888 */     getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { other }));
/*      */   }
/*      */   
/*      */   public void showPlayer(Player player)
/*      */   {
/*  893 */     Validate.notNull(player, "shown player cannot be null");
/*  894 */     if (getHandle().playerConnection == null) return;
/*  895 */     if (equals(player)) return;
/*  896 */     if (!this.hiddenPlayers.contains(player.getUniqueId())) return;
/*  897 */     this.hiddenPlayers.remove(player.getUniqueId());
/*      */     
/*  899 */     EntityTracker tracker = ((WorldServer)this.entity.world).tracker;
/*  900 */     EntityPlayer other = ((CraftPlayer)player).getHandle();
/*      */     
/*  902 */     getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { other }));
/*      */     
/*  904 */     EntityTrackerEntry entry = (EntityTrackerEntry)tracker.trackedEntities.get(other.getId());
/*  905 */     if ((entry != null) && (!entry.trackedPlayers.contains(getHandle()))) {
/*  906 */       entry.updatePlayer(getHandle());
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeDisconnectingPlayer(Player player) {
/*  911 */     this.hiddenPlayers.remove(player.getUniqueId());
/*      */   }
/*      */   
/*      */   public boolean canSee(Player player)
/*      */   {
/*  916 */     return !this.hiddenPlayers.contains(player.getUniqueId());
/*      */   }
/*      */   
/*      */   public Map<String, Object> serialize()
/*      */   {
/*  921 */     Map<String, Object> result = new java.util.LinkedHashMap();
/*      */     
/*  923 */     result.put("name", getName());
/*      */     
/*  925 */     return result;
/*      */   }
/*      */   
/*      */   public Player getPlayer()
/*      */   {
/*  930 */     return this;
/*      */   }
/*      */   
/*      */   public EntityPlayer getHandle()
/*      */   {
/*  935 */     return (EntityPlayer)this.entity;
/*      */   }
/*      */   
/*      */   public void setHandle(EntityPlayer entity) {
/*  939 */     super.setHandle(entity);
/*      */   }
/*      */   
/*      */   public String toString()
/*      */   {
/*  944 */     return "CraftPlayer{name=" + getName() + '}';
/*      */   }
/*      */   
/*      */   public int hashCode()
/*      */   {
/*  949 */     if ((this.hash == 0) || (this.hash == 485)) {
/*  950 */       this.hash = ('ǥ' + (getUniqueId() != null ? getUniqueId().hashCode() : 0));
/*      */     }
/*  952 */     return this.hash;
/*      */   }
/*      */   
/*      */   public long getFirstPlayed()
/*      */   {
/*  957 */     return this.firstPlayed;
/*      */   }
/*      */   
/*      */   public long getLastPlayed()
/*      */   {
/*  962 */     return this.lastPlayed;
/*      */   }
/*      */   
/*      */   public boolean hasPlayedBefore()
/*      */   {
/*  967 */     return this.hasPlayedBefore;
/*      */   }
/*      */   
/*      */   public void setFirstPlayed(long firstPlayed) {
/*  971 */     this.firstPlayed = firstPlayed;
/*      */   }
/*      */   
/*      */   public void readExtraData(NBTTagCompound nbttagcompound) {
/*  975 */     this.hasPlayedBefore = true;
/*  976 */     if (nbttagcompound.hasKey("bukkit")) {
/*  977 */       NBTTagCompound data = nbttagcompound.getCompound("bukkit");
/*      */       
/*  979 */       if (data.hasKey("firstPlayed")) {
/*  980 */         this.firstPlayed = data.getLong("firstPlayed");
/*  981 */         this.lastPlayed = data.getLong("lastPlayed");
/*      */       }
/*      */       
/*  984 */       if (data.hasKey("newExp")) {
/*  985 */         EntityPlayer handle = getHandle();
/*  986 */         handle.newExp = data.getInt("newExp");
/*  987 */         handle.newTotalExp = data.getInt("newTotalExp");
/*  988 */         handle.newLevel = data.getInt("newLevel");
/*  989 */         handle.expToDrop = data.getInt("expToDrop");
/*  990 */         handle.keepLevel = data.getBoolean("keepLevel");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void setExtraData(NBTTagCompound nbttagcompound) {
/*  996 */     if (!nbttagcompound.hasKey("bukkit")) {
/*  997 */       nbttagcompound.set("bukkit", new NBTTagCompound());
/*      */     }
/*      */     
/* 1000 */     NBTTagCompound data = nbttagcompound.getCompound("bukkit");
/* 1001 */     EntityPlayer handle = getHandle();
/* 1002 */     data.setInt("newExp", handle.newExp);
/* 1003 */     data.setInt("newTotalExp", handle.newTotalExp);
/* 1004 */     data.setInt("newLevel", handle.newLevel);
/* 1005 */     data.setInt("expToDrop", handle.expToDrop);
/* 1006 */     data.setBoolean("keepLevel", handle.keepLevel);
/* 1007 */     data.setLong("firstPlayed", getFirstPlayed());
/* 1008 */     data.setLong("lastPlayed", System.currentTimeMillis());
/* 1009 */     data.setString("lastKnownName", handle.getName());
/*      */   }
/*      */   
/*      */   public boolean beginConversation(Conversation conversation)
/*      */   {
/* 1014 */     return this.conversationTracker.beginConversation(conversation);
/*      */   }
/*      */   
/*      */   public void abandonConversation(Conversation conversation)
/*      */   {
/* 1019 */     this.conversationTracker.abandonConversation(conversation, new ConversationAbandonedEvent(conversation, new org.bukkit.conversations.ManuallyAbandonedConversationCanceller()));
/*      */   }
/*      */   
/*      */   public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details)
/*      */   {
/* 1024 */     this.conversationTracker.abandonConversation(conversation, details);
/*      */   }
/*      */   
/*      */   public void acceptConversationInput(String input)
/*      */   {
/* 1029 */     this.conversationTracker.acceptConversationInput(input);
/*      */   }
/*      */   
/*      */   public boolean isConversing()
/*      */   {
/* 1034 */     return this.conversationTracker.isConversing();
/*      */   }
/*      */   
/*      */   public void sendPluginMessage(Plugin source, String channel, byte[] message)
/*      */   {
/* 1039 */     org.bukkit.plugin.messaging.StandardMessenger.validatePluginMessage(this.server.getMessenger(), source, channel, message);
/* 1040 */     if (getHandle().playerConnection == null) { return;
/*      */     }
/* 1042 */     if (this.channels.contains(channel)) {
/* 1043 */       PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(channel, new net.minecraft.server.v1_8_R3.PacketDataSerializer(io.netty.buffer.Unpooled.wrappedBuffer(message)));
/* 1044 */       getHandle().playerConnection.sendPacket(packet);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setTexturePack(String url)
/*      */   {
/* 1050 */     setResourcePack(url);
/*      */   }
/*      */   
/*      */   public void setResourcePack(String url)
/*      */   {
/* 1055 */     Validate.notNull(url, "Resource pack URL cannot be null");
/*      */     
/* 1057 */     getHandle().setResourcePack(url, "null");
/*      */   }
/*      */   
/*      */   public void addChannel(String channel) {
/* 1061 */     com.google.common.base.Preconditions.checkState(this.channels.size() < 128, "Too many channels registered");
/* 1062 */     if (this.channels.add(channel)) {
/* 1063 */       this.server.getPluginManager().callEvent(new org.bukkit.event.player.PlayerRegisterChannelEvent(this, channel));
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeChannel(String channel) {
/* 1068 */     if (this.channels.remove(channel)) {
/* 1069 */       this.server.getPluginManager().callEvent(new org.bukkit.event.player.PlayerUnregisterChannelEvent(this, channel));
/*      */     }
/*      */   }
/*      */   
/*      */   public Set<String> getListeningPluginChannels()
/*      */   {
/* 1075 */     return com.google.common.collect.ImmutableSet.copyOf(this.channels);
/*      */   }
/*      */   
/*      */   public void sendSupportedChannels() {
/* 1079 */     if (getHandle().playerConnection == null) return;
/* 1080 */     Set<String> listening = this.server.getMessenger().getIncomingChannels();
/*      */     
/* 1082 */     if (!listening.isEmpty()) {
/* 1083 */       ByteArrayOutputStream stream = new ByteArrayOutputStream();
/*      */       
/* 1085 */       for (String channel : listening) {
/*      */         try {
/* 1087 */           stream.write(channel.getBytes("UTF8"));
/* 1088 */           stream.write(0);
/*      */         } catch (java.io.IOException ex) {
/* 1090 */           Logger.getLogger(CraftPlayer.class.getName()).log(java.util.logging.Level.SEVERE, "Could not send Plugin Channel REGISTER to " + getName(), ex);
/*      */         }
/*      */       }
/*      */       
/* 1094 */       getHandle().playerConnection.sendPacket(new PacketPlayOutCustomPayload("REGISTER", new net.minecraft.server.v1_8_R3.PacketDataSerializer(io.netty.buffer.Unpooled.wrappedBuffer(stream.toByteArray()))));
/*      */     }
/*      */   }
/*      */   
/*      */   public EntityType getType()
/*      */   {
/* 1100 */     return EntityType.PLAYER;
/*      */   }
/*      */   
/*      */   public void setMetadata(String metadataKey, MetadataValue newMetadataValue)
/*      */   {
/* 1105 */     this.server.getPlayerMetadata().setMetadata(this, metadataKey, newMetadataValue);
/*      */   }
/*      */   
/*      */   public java.util.List<MetadataValue> getMetadata(String metadataKey)
/*      */   {
/* 1110 */     return this.server.getPlayerMetadata().getMetadata(this, metadataKey);
/*      */   }
/*      */   
/*      */   public boolean hasMetadata(String metadataKey)
/*      */   {
/* 1115 */     return this.server.getPlayerMetadata().hasMetadata(this, metadataKey);
/*      */   }
/*      */   
/*      */   public void removeMetadata(String metadataKey, Plugin owningPlugin)
/*      */   {
/* 1120 */     this.server.getPlayerMetadata().removeMetadata(this, metadataKey, owningPlugin);
/*      */   }
/*      */   
/*      */   public boolean setWindowProperty(InventoryView.Property prop, int value)
/*      */   {
/* 1125 */     net.minecraft.server.v1_8_R3.Container container = getHandle().activeContainer;
/* 1126 */     if (container.getBukkitView().getType() != prop.getType()) {
/* 1127 */       return false;
/*      */     }
/* 1129 */     getHandle().setContainerData(container, prop.getId(), value);
/* 1130 */     return true;
/*      */   }
/*      */   
/*      */   public void disconnect(String reason) {
/* 1134 */     this.conversationTracker.abandonAllConversations();
/* 1135 */     this.perm.clearPermissions();
/*      */   }
/*      */   
/*      */   public boolean isFlying()
/*      */   {
/* 1140 */     return getHandle().abilities.isFlying;
/*      */   }
/*      */   
/*      */   public void setFlying(boolean value)
/*      */   {
/* 1145 */     if ((!getAllowFlight()) && (value)) {
/* 1146 */       throw new IllegalArgumentException("Cannot make player fly if getAllowFlight() is false");
/*      */     }
/*      */     
/* 1149 */     getHandle().abilities.isFlying = value;
/* 1150 */     getHandle().updateAbilities();
/*      */   }
/*      */   
/*      */   public boolean getAllowFlight()
/*      */   {
/* 1155 */     return getHandle().abilities.canFly;
/*      */   }
/*      */   
/*      */   public void setAllowFlight(boolean value)
/*      */   {
/* 1160 */     if ((isFlying()) && (!value)) {
/* 1161 */       getHandle().abilities.isFlying = false;
/*      */     }
/*      */     
/* 1164 */     getHandle().abilities.canFly = value;
/* 1165 */     getHandle().updateAbilities();
/*      */   }
/*      */   
/*      */   public int getNoDamageTicks()
/*      */   {
/* 1170 */     if (getHandle().invulnerableTicks > 0) {
/* 1171 */       return Math.max(getHandle().invulnerableTicks, getHandle().noDamageTicks);
/*      */     }
/* 1173 */     return getHandle().noDamageTicks;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setFlySpeed(float value)
/*      */   {
/* 1179 */     validateSpeed(value);
/* 1180 */     EntityPlayer player = getHandle();
/* 1181 */     player.abilities.flySpeed = (Math.max(value, 1.0E-4F) / 2.0F);
/* 1182 */     player.updateAbilities();
/*      */   }
/*      */   
/*      */ 
/*      */   public void setWalkSpeed(float value)
/*      */   {
/* 1188 */     validateSpeed(value);
/* 1189 */     EntityPlayer player = getHandle();
/* 1190 */     player.abilities.walkSpeed = (Math.max(value, 1.0E-4F) / 2.0F);
/* 1191 */     player.updateAbilities();
/*      */   }
/*      */   
/*      */   public float getFlySpeed()
/*      */   {
/* 1196 */     return getHandle().abilities.flySpeed * 2.0F;
/*      */   }
/*      */   
/*      */   public float getWalkSpeed()
/*      */   {
/* 1201 */     return getHandle().abilities.walkSpeed * 2.0F;
/*      */   }
/*      */   
/*      */   private void validateSpeed(float value) {
/* 1205 */     if (value < 0.0F) {
/* 1206 */       if (value < -1.0F) {
/* 1207 */         throw new IllegalArgumentException(value + " is too low");
/*      */       }
/*      */     }
/* 1210 */     else if (value > 1.0F) {
/* 1211 */       throw new IllegalArgumentException(value + " is too high");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void setMaxHealth(double amount)
/*      */   {
/* 1218 */     super.setMaxHealth(amount);
/* 1219 */     this.health = Math.min(this.health, this.health);
/* 1220 */     getHandle().triggerHealthUpdate();
/*      */   }
/*      */   
/*      */   public void resetMaxHealth()
/*      */   {
/* 1225 */     super.resetMaxHealth();
/* 1226 */     getHandle().triggerHealthUpdate();
/*      */   }
/*      */   
/*      */   public org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard getScoreboard()
/*      */   {
/* 1231 */     return this.server.getScoreboardManager().getPlayerBoard(this);
/*      */   }
/*      */   
/*      */   public void setScoreboard(org.bukkit.scoreboard.Scoreboard scoreboard)
/*      */   {
/* 1236 */     Validate.notNull(scoreboard, "Scoreboard cannot be null");
/* 1237 */     PlayerConnection playerConnection = getHandle().playerConnection;
/* 1238 */     if (playerConnection == null) {
/* 1239 */       throw new IllegalStateException("Cannot set scoreboard yet");
/*      */     }
/* 1241 */     playerConnection.isDisconnected();
/*      */     
/*      */ 
/*      */ 
/* 1245 */     this.server.getScoreboardManager().setPlayerBoard(this, scoreboard);
/*      */   }
/*      */   
/*      */   public void setHealthScale(double value)
/*      */   {
/* 1250 */     Validate.isTrue((float)value > 0.0F, "Must be greater than 0");
/* 1251 */     this.healthScale = value;
/* 1252 */     this.scaledHealth = true;
/* 1253 */     updateScaledHealth();
/*      */   }
/*      */   
/*      */   public double getHealthScale()
/*      */   {
/* 1258 */     return this.healthScale;
/*      */   }
/*      */   
/*      */   public void setHealthScaled(boolean scale)
/*      */   {
/* 1263 */     if (this.scaledHealth != (this.scaledHealth = scale)) {
/* 1264 */       updateScaledHealth();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isHealthScaled()
/*      */   {
/* 1270 */     return this.scaledHealth;
/*      */   }
/*      */   
/*      */   public float getScaledHealth() {
/* 1274 */     return (float)(isHealthScaled() ? getHealth() * getHealthScale() / getMaxHealth() : getHealth());
/*      */   }
/*      */   
/*      */   public double getHealth()
/*      */   {
/* 1279 */     return this.health;
/*      */   }
/*      */   
/*      */   public void setRealHealth(double health) {
/* 1283 */     this.health = health;
/*      */   }
/*      */   
/*      */   public void updateScaledHealth() {
/* 1287 */     net.minecraft.server.v1_8_R3.AttributeMapServer attributemapserver = (net.minecraft.server.v1_8_R3.AttributeMapServer)getHandle().getAttributeMap();
/* 1288 */     Set set = attributemapserver.getAttributes();
/*      */     
/* 1290 */     injectScaledMaxHealth(set, true);
/*      */     
/* 1292 */     getHandle().getDataWatcher().watch(6, Float.valueOf(getScaledHealth()));
/* 1293 */     getHandle().playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutUpdateHealth(getScaledHealth(), getHandle().getFoodData().getFoodLevel(), getHandle().getFoodData().getSaturationLevel()));
/* 1294 */     getHandle().playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutUpdateAttributes(getHandle().getId(), set));
/*      */     
/* 1296 */     set.clear();
/* 1297 */     getHandle().maxHealthCache = getMaxHealth();
/*      */   }
/*      */   
/*      */   public void injectScaledMaxHealth(Collection collection, boolean force) {
/* 1301 */     if ((!this.scaledHealth) && (!force)) {
/* 1302 */       return;
/*      */     }
/* 1304 */     for (Object genericInstance : collection) {
/* 1305 */       net.minecraft.server.v1_8_R3.IAttribute attribute = ((net.minecraft.server.v1_8_R3.AttributeInstance)genericInstance).getAttribute();
/* 1306 */       if (attribute.getName().equals("generic.maxHealth")) {
/* 1307 */         collection.remove(genericInstance);
/* 1308 */         break;
/*      */       }
/*      */     }
/*      */     
/* 1312 */     double healthMod = this.scaledHealth ? this.healthScale : getMaxHealth();
/* 1313 */     if ((healthMod >= 3.4028234663852886E38D) || (healthMod <= 0.0D))
/*      */     {
/* 1315 */       healthMod = 20.0D;
/* 1316 */       getServer().getLogger().warning(getName() + " tried to crash the server with a large health attribute");
/*      */     }
/* 1318 */     collection.add(new net.minecraft.server.v1_8_R3.AttributeModifiable(getHandle().getAttributeMap(), new AttributeRanged(null, "generic.maxHealth", healthMod, 0.0D, 3.4028234663852886E38D).a("Max Health").a(true)));
/*      */   }
/*      */   
/*      */ 
/*      */   public org.bukkit.entity.Entity getSpectatorTarget()
/*      */   {
/* 1324 */     net.minecraft.server.v1_8_R3.Entity followed = getHandle().C();
/* 1325 */     return followed == getHandle() ? null : followed.getBukkitEntity();
/*      */   }
/*      */   
/*      */   public void setSpectatorTarget(org.bukkit.entity.Entity entity)
/*      */   {
/* 1330 */     com.google.common.base.Preconditions.checkArgument(getGameMode() == GameMode.SPECTATOR, "Player must be in spectator mode");
/* 1331 */     getHandle().setSpectatorTarget(entity == null ? null : ((CraftEntity)entity).getHandle());
/*      */   }
/*      */   
/*      */   public void sendTitle(String title, String subtitle)
/*      */   {
/* 1336 */     if (title != null) {
/* 1337 */       PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage.fromString(title)[0]);
/* 1338 */       getHandle().playerConnection.sendPacket(packetTitle);
/*      */     }
/*      */     
/* 1341 */     if (subtitle != null) {
/* 1342 */       PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage.fromString(subtitle)[0]);
/* 1343 */       getHandle().playerConnection.sendPacket(packetSubtitle);
/*      */     }
/*      */   }
/*      */   
/*      */   public void resetTitle()
/*      */   {
/* 1349 */     PacketPlayOutTitle packetReset = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, null);
/* 1350 */     getHandle().playerConnection.sendPacket(packetReset);
/*      */   }
/*      */   
/*      */ 
/* 1354 */   private final Player.Spigot spigot = new Player.Spigot()
/*      */   {
/*      */ 
/*      */     public InetSocketAddress getRawAddress()
/*      */     {
/*      */ 
/* 1360 */       return (InetSocketAddress)CraftPlayer.this.getHandle().playerConnection.networkManager.getRawAddress();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean getCollidesWithEntities()
/*      */     {
/* 1366 */       return CraftPlayer.this.getHandle().collidesWithEntities;
/*      */     }
/*      */     
/*      */ 
/*      */     public void setCollidesWithEntities(boolean collides)
/*      */     {
/* 1372 */       CraftPlayer.this.getHandle().collidesWithEntities = collides;
/* 1373 */       CraftPlayer.this.getHandle().k = collides;
/*      */     }
/*      */     
/*      */ 
/*      */     public void respawn()
/*      */     {
/* 1379 */       if ((CraftPlayer.this.getHealth() <= 0.0D) && (CraftPlayer.this.isOnline()))
/*      */       {
/* 1381 */         CraftPlayer.this.server.getServer().getPlayerList().moveToWorld(CraftPlayer.this.getHandle(), 0, false);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     public void playEffect(Location location, Effect effect, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius)
/*      */     {
/* 1388 */       Validate.notNull(location, "Location cannot be null");
/* 1389 */       Validate.notNull(effect, "Effect cannot be null");
/* 1390 */       Validate.notNull(location.getWorld(), "World cannot be null");
/*      */       net.minecraft.server.v1_8_R3.Packet packet;
/* 1392 */       net.minecraft.server.v1_8_R3.Packet packet; if (effect.getType() != org.bukkit.Effect.Type.PARTICLE)
/*      */       {
/* 1394 */         int packetData = effect.getId();
/* 1395 */         packet = new net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent(packetData, new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()), id, false);
/*      */       }
/*      */       else {
/* 1398 */         EnumParticle particle = null;
/* 1399 */         int[] extra = null;
/* 1400 */         EnumParticle[] arrayOfEnumParticle; int i = (arrayOfEnumParticle = EnumParticle.values()).length; for (int j = 0; j < i; j++) { EnumParticle p = arrayOfEnumParticle[j];
/*      */           
/* 1402 */           if (effect.getName().startsWith(p.b().replace("_", "")))
/*      */           {
/* 1404 */             particle = p;
/* 1405 */             if (effect.getData() == null)
/*      */               break;
/* 1407 */             if (effect.getData().equals(Material.class))
/*      */             {
/* 1409 */               extra = new int[] { id };
/* 1410 */               break;
/*      */             }
/* 1412 */             extra = new int[] { data << 12 | id & 0xFFF };
/*      */             
/*      */ 
/* 1415 */             break;
/*      */           }
/*      */         }
/* 1418 */         if (extra == null)
/*      */         {
/* 1420 */           extra = new int[0];
/*      */         }
/* 1422 */         packet = new net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles(particle, true, (float)location.getX(), (float)location.getY(), (float)location.getZ(), offsetX, offsetY, offsetZ, speed, particleCount, extra);
/*      */       }
/*      */       
/* 1425 */       radius *= radius;
/* 1426 */       if (CraftPlayer.this.getHandle().playerConnection == null)
/*      */       {
/* 1428 */         return;
/*      */       }
/* 1430 */       if (!location.getWorld().equals(CraftPlayer.this.getWorld()))
/*      */       {
/* 1432 */         return;
/*      */       }
/*      */       
/* 1435 */       int distance = (int)CraftPlayer.this.getLocation().distanceSquared(location);
/* 1436 */       if (distance <= radius)
/*      */       {
/* 1438 */         CraftPlayer.this.getHandle().playerConnection.sendPacket(packet);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     public String getLocale()
/*      */     {
/* 1445 */       return CraftPlayer.this.getHandle().locale;
/*      */     }
/*      */     
/*      */ 
/*      */     public Set<Player> getHiddenPlayers()
/*      */     {
/* 1451 */       Set<Player> ret = new HashSet();
/* 1452 */       for (UUID u : CraftPlayer.this.hiddenPlayers)
/*      */       {
/* 1454 */         ret.add(CraftPlayer.this.getServer().getPlayer(u));
/*      */       }
/*      */       
/* 1457 */       return java.util.Collections.unmodifiableSet(ret);
/*      */     }
/*      */     
/*      */     public void sendMessage(BaseComponent component)
/*      */     {
/* 1462 */       sendMessage(new BaseComponent[] { component });
/*      */     }
/*      */     
/*      */     public void sendMessage(BaseComponent... components)
/*      */     {
/* 1467 */       if (CraftPlayer.this.getHandle().playerConnection == null) { return;
/*      */       }
/* 1469 */       net.minecraft.server.v1_8_R3.PacketPlayOutChat packet = new net.minecraft.server.v1_8_R3.PacketPlayOutChat();
/* 1470 */       packet.components = components;
/* 1471 */       CraftPlayer.this.getHandle().playerConnection.sendPacket(packet);
/*      */     }
/*      */   };
/*      */   
/*      */   public Player.Spigot spigot()
/*      */   {
/* 1477 */     return this.spigot;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */