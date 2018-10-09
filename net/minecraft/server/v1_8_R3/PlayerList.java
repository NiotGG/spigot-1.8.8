/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import java.io.File;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.UUID;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Server;
/*      */ import org.bukkit.TravelAgent;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.chunkio.ChunkIOExecutor;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.player.PlayerChangedWorldEvent;
/*      */ import org.bukkit.event.player.PlayerJoinEvent;
/*      */ import org.bukkit.event.player.PlayerLoginEvent;
/*      */ import org.bukkit.event.player.PlayerLoginEvent.Result;
/*      */ import org.bukkit.event.player.PlayerPortalEvent;
/*      */ import org.bukkit.event.player.PlayerQuitEvent;
/*      */ import org.bukkit.event.player.PlayerRespawnEvent;
/*      */ import org.bukkit.event.player.PlayerTeleportEvent;
/*      */ import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.bukkit.util.Vector;
/*      */ import org.spigotmc.SpigotConfig;
/*      */ import org.spigotmc.event.player.PlayerSpawnLocationEvent;
/*      */ 
/*      */ public abstract class PlayerList
/*      */ {
/*   44 */   public static final File a = new File("banned-players.json");
/*   45 */   public static final File b = new File("banned-ips.json");
/*   46 */   public static final File c = new File("ops.json");
/*   47 */   public static final File d = new File("whitelist.json");
/*   48 */   private static final Logger f = org.apache.logging.log4j.LogManager.getLogger();
/*   49 */   private static final SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
/*      */   private final MinecraftServer server;
/*   51 */   public final List<EntityPlayer> players = new java.util.concurrent.CopyOnWriteArrayList();
/*   52 */   private final Map<UUID, EntityPlayer> j = Maps.newHashMap();
/*      */   
/*      */   private final GameProfileBanList k;
/*      */   
/*      */   private final IpBanList l;
/*      */   private final OpList operators;
/*      */   private final WhiteList whitelist;
/*      */   private final Map<UUID, ServerStatisticManager> o;
/*      */   public IPlayerFileData playerFileData;
/*      */   private boolean hasWhitelist;
/*      */   protected int maxPlayers;
/*      */   private int r;
/*      */   private WorldSettings.EnumGamemode s;
/*      */   private boolean t;
/*      */   private int u;
/*      */   private CraftServer cserver;
/*   68 */   private final Map<String, EntityPlayer> playersByName = new org.spigotmc.CaseInsensitiveMap();
/*      */   
/*      */   public PlayerList(MinecraftServer minecraftserver) {
/*   71 */     this.cserver = (minecraftserver.server = new CraftServer(minecraftserver, this));
/*   72 */     minecraftserver.console = org.bukkit.craftbukkit.v1_8_R3.command.ColouredConsoleSender.getInstance();
/*   73 */     minecraftserver.reader.addCompleter(new org.bukkit.craftbukkit.v1_8_R3.command.ConsoleCommandCompleter(minecraftserver.server));
/*      */     
/*      */ 
/*   76 */     this.k = new GameProfileBanList(a);
/*   77 */     this.l = new IpBanList(b);
/*   78 */     this.operators = new OpList(c);
/*   79 */     this.whitelist = new WhiteList(d);
/*   80 */     this.o = Maps.newHashMap();
/*   81 */     this.server = minecraftserver;
/*   82 */     this.k.a(false);
/*   83 */     this.l.a(false);
/*   84 */     this.maxPlayers = 8;
/*      */   }
/*      */   
/*      */   public void a(NetworkManager networkmanager, EntityPlayer entityplayer) {
/*   88 */     GameProfile gameprofile = entityplayer.getProfile();
/*   89 */     UserCache usercache = this.server.getUserCache();
/*   90 */     GameProfile gameprofile1 = usercache.a(gameprofile.getId());
/*   91 */     String s = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();
/*      */     
/*   93 */     usercache.a(gameprofile);
/*   94 */     NBTTagCompound nbttagcompound = a(entityplayer);
/*      */     
/*   96 */     if ((nbttagcompound != null) && (nbttagcompound.hasKey("bukkit"))) {
/*   97 */       NBTTagCompound bukkit = nbttagcompound.getCompound("bukkit");
/*   98 */       s = bukkit.hasKeyOfType("lastKnownName", 8) ? bukkit.getString("lastKnownName") : s;
/*      */     }
/*      */     
/*      */ 
/*  102 */     entityplayer.spawnIn(this.server.getWorldServer(entityplayer.dimension));
/*  103 */     entityplayer.playerInteractManager.a((WorldServer)entityplayer.world);
/*  104 */     String s1 = "local";
/*      */     
/*  106 */     if (networkmanager.getSocketAddress() != null) {
/*  107 */       s1 = networkmanager.getSocketAddress().toString();
/*      */     }
/*      */     
/*      */ 
/*  111 */     Player bukkitPlayer = entityplayer.getBukkitEntity();
/*  112 */     PlayerSpawnLocationEvent ev = new PlayerSpawnLocationEvent(bukkitPlayer, bukkitPlayer.getLocation());
/*  113 */     Bukkit.getPluginManager().callEvent(ev);
/*      */     
/*  115 */     Location loc = ev.getSpawnLocation();
/*  116 */     WorldServer world = ((CraftWorld)loc.getWorld()).getHandle();
/*      */     
/*  118 */     entityplayer.spawnIn(world);
/*  119 */     entityplayer.setPosition(loc.getX(), loc.getY(), loc.getZ());
/*  120 */     entityplayer.setYawPitch(loc.getYaw(), loc.getPitch());
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  125 */     WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
/*  126 */     WorldData worlddata = worldserver.getWorldData();
/*  127 */     BlockPosition blockposition = worldserver.getSpawn();
/*      */     
/*  129 */     a(entityplayer, null, worldserver);
/*  130 */     PlayerConnection playerconnection = new PlayerConnection(this.server, networkmanager, entityplayer);
/*      */     
/*  132 */     playerconnection.sendPacket(new PacketPlayOutLogin(entityplayer.getId(), entityplayer.playerInteractManager.getGameMode(), worlddata.isHardcore(), worldserver.worldProvider.getDimension(), worldserver.getDifficulty(), Math.min(getMaxPlayers(), 60), worlddata.getType(), worldserver.getGameRules().getBoolean("reducedDebugInfo")));
/*  133 */     entityplayer.getBukkitEntity().sendSupportedChannels();
/*  134 */     playerconnection.sendPacket(new PacketPlayOutCustomPayload("MC|Brand", new PacketDataSerializer(io.netty.buffer.Unpooled.buffer()).a(getServer().getServerModName())));
/*  135 */     playerconnection.sendPacket(new PacketPlayOutServerDifficulty(worlddata.getDifficulty(), worlddata.isDifficultyLocked()));
/*  136 */     playerconnection.sendPacket(new PacketPlayOutSpawnPosition(blockposition));
/*  137 */     playerconnection.sendPacket(new PacketPlayOutAbilities(entityplayer.abilities));
/*  138 */     playerconnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
/*  139 */     entityplayer.getStatisticManager().d();
/*  140 */     entityplayer.getStatisticManager().updateStatistics(entityplayer);
/*  141 */     sendScoreboard((ScoreboardServer)worldserver.getScoreboard(), entityplayer);
/*  142 */     this.server.aH();
/*      */     
/*      */     String joinMessage;
/*      */     
/*      */     String joinMessage;
/*  147 */     if (!entityplayer.getName().equalsIgnoreCase(s))
/*      */     {
/*  149 */       joinMessage = "§e" + LocaleI18n.a("multiplayer.player.joined.renamed", new Object[] { entityplayer.getName(), s });
/*      */     }
/*      */     else {
/*  152 */       joinMessage = "§e" + LocaleI18n.a("multiplayer.player.joined", new Object[] { entityplayer.getName() });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  157 */     onPlayerJoin(entityplayer, joinMessage);
/*      */     
/*  159 */     worldserver = this.server.getWorldServer(entityplayer.dimension);
/*  160 */     playerconnection.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
/*  161 */     b(entityplayer, worldserver);
/*  162 */     if (this.server.getResourcePack().length() > 0) {
/*  163 */       entityplayer.setResourcePack(this.server.getResourcePack(), this.server.getResourcePackHash());
/*      */     }
/*      */     
/*  166 */     Iterator iterator = entityplayer.getEffects().iterator();
/*      */     
/*  168 */     while (iterator.hasNext()) {
/*  169 */       MobEffect mobeffect = (MobEffect)iterator.next();
/*      */       
/*  171 */       playerconnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer.getId(), mobeffect));
/*      */     }
/*      */     
/*  174 */     entityplayer.syncInventory();
/*  175 */     if ((nbttagcompound != null) && (nbttagcompound.hasKeyOfType("Riding", 10))) {
/*  176 */       Entity entity = EntityTypes.a(nbttagcompound.getCompound("Riding"), worldserver);
/*      */       
/*  178 */       if (entity != null) {
/*  179 */         entity.attachedToPlayer = true;
/*  180 */         worldserver.addEntity(entity);
/*  181 */         entityplayer.mount(entity);
/*  182 */         entity.attachedToPlayer = false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  187 */     f.info(entityplayer.getName() + "[" + s1 + "] logged in with entity id " + entityplayer.getId() + " at ([" + entityplayer.world.worldData.getName() + "]" + entityplayer.locX + ", " + entityplayer.locY + ", " + entityplayer.locZ + ")");
/*      */   }
/*      */   
/*      */   public void sendScoreboard(ScoreboardServer scoreboardserver, EntityPlayer entityplayer) {
/*  191 */     HashSet hashset = com.google.common.collect.Sets.newHashSet();
/*  192 */     Iterator iterator = scoreboardserver.getTeams().iterator();
/*      */     
/*  194 */     while (iterator.hasNext()) {
/*  195 */       ScoreboardTeam scoreboardteam = (ScoreboardTeam)iterator.next();
/*      */       
/*  197 */       entityplayer.playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(scoreboardteam, 0));
/*      */     }
/*      */     
/*  200 */     for (int i = 0; i < 19; i++) {
/*  201 */       ScoreboardObjective scoreboardobjective = scoreboardserver.getObjectiveForSlot(i);
/*      */       
/*  203 */       if ((scoreboardobjective != null) && (!hashset.contains(scoreboardobjective))) {
/*  204 */         List list = scoreboardserver.getScoreboardScorePacketsForObjective(scoreboardobjective);
/*  205 */         Iterator iterator1 = list.iterator();
/*      */         
/*  207 */         while (iterator1.hasNext()) {
/*  208 */           Packet packet = (Packet)iterator1.next();
/*      */           
/*  210 */           entityplayer.playerConnection.sendPacket(packet);
/*      */         }
/*      */         
/*  213 */         hashset.add(scoreboardobjective);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void setPlayerFileData(WorldServer[] aworldserver)
/*      */   {
/*  220 */     if (this.playerFileData != null) return;
/*  221 */     this.playerFileData = aworldserver[0].getDataManager().getPlayerFileData();
/*  222 */     aworldserver[0].getWorldBorder().a(new IWorldBorderListener() {
/*      */       public void a(WorldBorder worldborder, double d0) {
/*  224 */         PlayerList.this.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_SIZE));
/*      */       }
/*      */       
/*      */       public void a(WorldBorder worldborder, double d0, double d1, long i) {
/*  228 */         PlayerList.this.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.LERP_SIZE));
/*      */       }
/*      */       
/*      */       public void a(WorldBorder worldborder, double d0, double d1) {
/*  232 */         PlayerList.this.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_CENTER));
/*      */       }
/*      */       
/*      */       public void a(WorldBorder worldborder, int i) {
/*  236 */         PlayerList.this.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_TIME));
/*      */       }
/*      */       
/*      */       public void b(WorldBorder worldborder, int i) {
/*  240 */         PlayerList.this.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS));
/*      */       }
/*      */       
/*      */       public void b(WorldBorder worldborder, double d0) {}
/*      */       
/*      */       public void c(WorldBorder worldborder, double d0) {}
/*      */     });
/*      */   }
/*      */   
/*      */   public void a(EntityPlayer entityplayer, WorldServer worldserver) {
/*  250 */     WorldServer worldserver1 = entityplayer.u();
/*      */     
/*  252 */     if (worldserver != null) {
/*  253 */       worldserver.getPlayerChunkMap().removePlayer(entityplayer);
/*      */     }
/*      */     
/*  256 */     worldserver1.getPlayerChunkMap().addPlayer(entityplayer);
/*  257 */     worldserver1.chunkProviderServer.getChunkAt((int)entityplayer.locX >> 4, (int)entityplayer.locZ >> 4);
/*      */   }
/*      */   
/*      */   public int d() {
/*  261 */     return PlayerChunkMap.getFurthestViewableBlock(s());
/*      */   }
/*      */   
/*      */   public NBTTagCompound a(EntityPlayer entityplayer) {
/*  265 */     NBTTagCompound nbttagcompound = ((WorldServer)this.server.worlds.get(0)).getWorldData().i();
/*      */     
/*      */     NBTTagCompound nbttagcompound1;
/*  268 */     if ((entityplayer.getName().equals(this.server.S())) && (nbttagcompound != null)) {
/*  269 */       entityplayer.f(nbttagcompound);
/*  270 */       NBTTagCompound nbttagcompound1 = nbttagcompound;
/*  271 */       f.debug("loading single player");
/*      */     } else {
/*  273 */       nbttagcompound1 = this.playerFileData.load(entityplayer);
/*      */     }
/*      */     
/*  276 */     return nbttagcompound1;
/*      */   }
/*      */   
/*      */   protected void savePlayerFile(EntityPlayer entityplayer) {
/*  280 */     this.playerFileData.save(entityplayer);
/*  281 */     ServerStatisticManager serverstatisticmanager = (ServerStatisticManager)this.o.get(entityplayer.getUniqueID());
/*      */     
/*  283 */     if (serverstatisticmanager != null) {
/*  284 */       serverstatisticmanager.b();
/*      */     }
/*      */   }
/*      */   
/*      */   public void onPlayerJoin(EntityPlayer entityplayer, String joinMessage)
/*      */   {
/*  290 */     this.players.add(entityplayer);
/*  291 */     this.playersByName.put(entityplayer.getName(), entityplayer);
/*  292 */     this.j.put(entityplayer.getUniqueID(), entityplayer);
/*      */     
/*  294 */     WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
/*      */     
/*      */ 
/*  297 */     PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(this.cserver.getPlayer(entityplayer), joinMessage);
/*  298 */     this.cserver.getPluginManager().callEvent(playerJoinEvent);
/*      */     
/*  300 */     joinMessage = playerJoinEvent.getJoinMessage();
/*      */     
/*  302 */     if ((joinMessage != null) && (joinMessage.length() > 0)) { IChatBaseComponent[] arrayOfIChatBaseComponent;
/*  303 */       int i = (arrayOfIChatBaseComponent = CraftChatMessage.fromString(joinMessage)).length; for (int m = 0; m < i; m++) { IChatBaseComponent line = arrayOfIChatBaseComponent[m];
/*  304 */         this.server.getPlayerList().sendAll(new PacketPlayOutChat(line));
/*      */       }
/*      */     }
/*      */     
/*  308 */     ChunkIOExecutor.adjustPoolSize(getPlayerCount());
/*      */     
/*      */ 
/*      */ 
/*  312 */     PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { entityplayer });
/*      */     
/*  314 */     for (int i = 0; i < this.players.size(); i++) {
/*  315 */       EntityPlayer entityplayer1 = (EntityPlayer)this.players.get(i);
/*      */       
/*  317 */       if (entityplayer1.getBukkitEntity().canSee(entityplayer.getBukkitEntity())) {
/*  318 */         entityplayer1.playerConnection.sendPacket(packet);
/*      */       }
/*      */       
/*  321 */       if (entityplayer.getBukkitEntity().canSee(entityplayer1.getBukkitEntity()))
/*      */       {
/*      */ 
/*      */ 
/*  325 */         entityplayer.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { entityplayer1 }));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  330 */     if ((entityplayer.world == worldserver) && (!worldserver.players.contains(entityplayer))) {
/*  331 */       worldserver.addEntity(entityplayer);
/*  332 */       a(entityplayer, null);
/*      */     }
/*      */   }
/*      */   
/*      */   public void d(EntityPlayer entityplayer)
/*      */   {
/*  338 */     entityplayer.u().getPlayerChunkMap().movePlayer(entityplayer);
/*      */   }
/*      */   
/*      */   public String disconnect(EntityPlayer entityplayer) {
/*  342 */     entityplayer.b(StatisticList.f);
/*      */     
/*      */ 
/*  345 */     org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.handleInventoryCloseEvent(entityplayer);
/*      */     
/*  347 */     PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent(this.cserver.getPlayer(entityplayer), "§e" + entityplayer.getName() + " left the game.");
/*  348 */     this.cserver.getPluginManager().callEvent(playerQuitEvent);
/*  349 */     entityplayer.getBukkitEntity().disconnect(playerQuitEvent.getQuitMessage());
/*      */     
/*      */ 
/*  352 */     savePlayerFile(entityplayer);
/*  353 */     WorldServer worldserver = entityplayer.u();
/*      */     
/*  355 */     if ((entityplayer.vehicle != null) && (!(entityplayer.vehicle instanceof EntityPlayer))) {
/*  356 */       worldserver.removeEntity(entityplayer.vehicle);
/*  357 */       f.debug("removing player mount");
/*      */     }
/*      */     
/*  360 */     worldserver.kill(entityplayer);
/*  361 */     worldserver.getPlayerChunkMap().removePlayer(entityplayer);
/*  362 */     this.players.remove(entityplayer);
/*  363 */     this.playersByName.remove(entityplayer.getName());
/*  364 */     UUID uuid = entityplayer.getUniqueID();
/*  365 */     EntityPlayer entityplayer1 = (EntityPlayer)this.j.get(uuid);
/*      */     
/*  367 */     if (entityplayer1 == entityplayer) {
/*  368 */       this.j.remove(uuid);
/*  369 */       this.o.remove(uuid);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  374 */     PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { entityplayer });
/*  375 */     for (int i = 0; i < this.players.size(); i++) {
/*  376 */       EntityPlayer entityplayer2 = (EntityPlayer)this.players.get(i);
/*      */       
/*  378 */       if (entityplayer2.getBukkitEntity().canSee(entityplayer.getBukkitEntity())) {
/*  379 */         entityplayer2.playerConnection.sendPacket(packet);
/*      */       } else {
/*  381 */         entityplayer2.getBukkitEntity().removeDisconnectingPlayer(entityplayer.getBukkitEntity());
/*      */       }
/*      */     }
/*      */     
/*  385 */     this.cserver.getScoreboardManager().removePlayer(entityplayer.getBukkitEntity());
/*      */     
/*      */ 
/*  388 */     ChunkIOExecutor.adjustPoolSize(getPlayerCount());
/*      */     
/*  390 */     return playerQuitEvent.getQuitMessage();
/*      */   }
/*      */   
/*      */ 
/*      */   public EntityPlayer attemptLogin(LoginListener loginlistener, GameProfile gameprofile, String hostname)
/*      */   {
/*  396 */     UUID uuid = EntityHuman.a(gameprofile);
/*  397 */     ArrayList arraylist = Lists.newArrayList();
/*      */     
/*      */ 
/*      */ 
/*  401 */     for (int i = 0; i < this.players.size(); i++) {
/*  402 */       EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
/*  403 */       if (entityplayer.getUniqueID().equals(uuid)) {
/*  404 */         arraylist.add(entityplayer);
/*      */       }
/*      */     }
/*      */     
/*  408 */     Iterator iterator = arraylist.iterator();
/*      */     
/*  410 */     while (iterator.hasNext()) {
/*  411 */       EntityPlayer entityplayer = (EntityPlayer)iterator.next();
/*  412 */       savePlayerFile(entityplayer);
/*  413 */       entityplayer.playerConnection.disconnect("You logged in from another location");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  419 */     java.net.SocketAddress socketaddress = loginlistener.networkManager.getSocketAddress();
/*      */     
/*  421 */     EntityPlayer entity = new EntityPlayer(this.server, this.server.getWorldServer(0), gameprofile, new PlayerInteractManager(this.server.getWorldServer(0)));
/*  422 */     Player player = entity.getBukkitEntity();
/*  423 */     PlayerLoginEvent event = new PlayerLoginEvent(player, hostname, ((InetSocketAddress)socketaddress).getAddress(), ((InetSocketAddress)loginlistener.networkManager.getRawAddress()).getAddress());
/*      */     
/*      */ 
/*  426 */     if ((getProfileBans().isBanned(gameprofile)) && (!((GameProfileBanEntry)getProfileBans().get(gameprofile)).hasExpired())) {
/*  427 */       GameProfileBanEntry gameprofilebanentry = (GameProfileBanEntry)this.k.get(gameprofile);
/*      */       
/*  429 */       String s = "You are banned from this server!\nReason: " + gameprofilebanentry.getReason();
/*  430 */       if (gameprofilebanentry.getExpires() != null) {
/*  431 */         s = s + "\nYour ban will be removed on " + g.format(gameprofilebanentry.getExpires());
/*      */       }
/*      */       
/*      */ 
/*  435 */       if (!gameprofilebanentry.hasExpired()) event.disallow(PlayerLoginEvent.Result.KICK_BANNED, s);
/*  436 */     } else if (!isWhitelisted(gameprofile))
/*      */     {
/*  438 */       event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, SpigotConfig.whitelistMessage);
/*  439 */     } else if ((getIPBans().isBanned(socketaddress)) && (!getIPBans().get(socketaddress).hasExpired())) {
/*  440 */       IpBanEntry ipbanentry = this.l.get(socketaddress);
/*      */       
/*  442 */       String s = "Your IP address is banned from this server!\nReason: " + ipbanentry.getReason();
/*  443 */       if (ipbanentry.getExpires() != null) {
/*  444 */         s = s + "\nYour ban will be removed on " + g.format(ipbanentry.getExpires());
/*      */       }
/*      */       
/*      */ 
/*  448 */       event.disallow(PlayerLoginEvent.Result.KICK_BANNED, s);
/*      */ 
/*      */     }
/*  451 */     else if ((this.players.size() >= this.maxPlayers) && (!f(gameprofile))) {
/*  452 */       event.disallow(PlayerLoginEvent.Result.KICK_FULL, SpigotConfig.serverFullMessage);
/*      */     }
/*      */     
/*      */ 
/*  456 */     this.cserver.getPluginManager().callEvent(event);
/*  457 */     if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
/*  458 */       loginlistener.disconnect(event.getKickMessage());
/*  459 */       return null;
/*      */     }
/*  461 */     return entity;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public EntityPlayer processLogin(GameProfile gameprofile, EntityPlayer player)
/*      */   {
/*  501 */     return player;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  507 */   public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag) { return moveToWorld(entityplayer, i, flag, null, true); }
/*      */   
/*      */   public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag, Location location, boolean avoidSuffocation) {
/*  510 */     entityplayer.u().getTracker().untrackPlayer(entityplayer);
/*      */     
/*  512 */     entityplayer.u().getPlayerChunkMap().removePlayer(entityplayer);
/*  513 */     this.players.remove(entityplayer);
/*  514 */     this.playersByName.remove(entityplayer.getName());
/*  515 */     this.server.getWorldServer(entityplayer.dimension).removeEntity(entityplayer);
/*  516 */     BlockPosition blockposition = entityplayer.getBed();
/*  517 */     boolean flag1 = entityplayer.isRespawnForced();
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
/*  531 */     EntityPlayer entityplayer1 = entityplayer;
/*  532 */     org.bukkit.World fromWorld = entityplayer.getBukkitEntity().getWorld();
/*  533 */     entityplayer.viewingCredits = false;
/*      */     
/*      */ 
/*  536 */     entityplayer1.playerConnection = entityplayer.playerConnection;
/*  537 */     entityplayer1.copyTo(entityplayer, flag);
/*  538 */     entityplayer1.d(entityplayer.getId());
/*  539 */     entityplayer1.o(entityplayer);
/*      */     
/*      */ 
/*      */ 
/*      */     PlayerRespawnEvent respawnEvent;
/*      */     
/*      */ 
/*  546 */     if (location == null) {
/*  547 */       boolean isBedSpawn = false;
/*  548 */       CraftWorld cworld = (CraftWorld)this.server.server.getWorld(entityplayer.spawnWorld);
/*  549 */       if ((cworld != null) && (blockposition != null)) {
/*  550 */         BlockPosition blockposition1 = EntityHuman.getBed(cworld.getHandle(), blockposition, flag1);
/*  551 */         if (blockposition1 != null) {
/*  552 */           isBedSpawn = true;
/*  553 */           location = new Location(cworld, blockposition1.getX() + 0.5D, blockposition1.getY(), blockposition1.getZ() + 0.5D);
/*      */         } else {
/*  555 */           entityplayer1.setRespawnPosition(null, true);
/*  556 */           entityplayer1.playerConnection.sendPacket(new PacketPlayOutGameStateChange(0, 0.0F));
/*      */         }
/*      */       }
/*      */       
/*  560 */       if (location == null) {
/*  561 */         cworld = (CraftWorld)this.server.server.getWorlds().get(0);
/*  562 */         blockposition = cworld.getHandle().getSpawn();
/*  563 */         location = new Location(cworld, blockposition.getX() + 0.5D, blockposition.getY(), blockposition.getZ() + 0.5D);
/*      */       }
/*      */       
/*  566 */       Player respawnPlayer = this.cserver.getPlayer(entityplayer1);
/*  567 */       respawnEvent = new PlayerRespawnEvent(respawnPlayer, location, isBedSpawn);
/*  568 */       this.cserver.getPluginManager().callEvent(respawnEvent);
/*      */       
/*  570 */       if (entityplayer.playerConnection.isDisconnected()) {
/*  571 */         return entityplayer;
/*      */       }
/*      */       
/*      */ 
/*  575 */       location = respawnEvent.getRespawnLocation();
/*  576 */       entityplayer.reset();
/*      */     } else {
/*  578 */       location.setWorld(this.server.getWorldServer(i).getWorld());
/*      */     }
/*  580 */     WorldServer worldserver = ((CraftWorld)location.getWorld()).getHandle();
/*  581 */     entityplayer1.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
/*      */     
/*      */ 
/*  584 */     worldserver.chunkProviderServer.getChunkAt((int)entityplayer1.locX >> 4, (int)entityplayer1.locZ >> 4);
/*      */     
/*  586 */     while ((avoidSuffocation) && (!worldserver.getCubes(entityplayer1, entityplayer1.getBoundingBox()).isEmpty()) && (entityplayer1.locY < 256.0D)) {
/*  587 */       entityplayer1.setPosition(entityplayer1.locX, entityplayer1.locY + 1.0D, entityplayer1.locZ);
/*      */     }
/*      */     
/*  590 */     byte actualDimension = (byte)worldserver.getWorld().getEnvironment().getId();
/*      */     
/*  592 */     if (fromWorld.getEnvironment() == worldserver.getWorld().getEnvironment()) {
/*  593 */       entityplayer1.playerConnection.sendPacket(new PacketPlayOutRespawn((byte)(actualDimension >= 0 ? -1 : 0), worldserver.getDifficulty(), worldserver.getWorldData().getType(), entityplayer.playerInteractManager.getGameMode()));
/*      */     }
/*  595 */     entityplayer1.playerConnection.sendPacket(new PacketPlayOutRespawn(actualDimension, worldserver.getDifficulty(), worldserver.getWorldData().getType(), entityplayer1.playerInteractManager.getGameMode()));
/*  596 */     entityplayer1.spawnIn(worldserver);
/*  597 */     entityplayer1.dead = false;
/*  598 */     entityplayer1.playerConnection.teleport(new Location(worldserver.getWorld(), entityplayer1.locX, entityplayer1.locY, entityplayer1.locZ, entityplayer1.yaw, entityplayer1.pitch));
/*  599 */     entityplayer1.setSneaking(false);
/*  600 */     BlockPosition blockposition1 = worldserver.getSpawn();
/*      */     
/*  602 */     entityplayer1.playerConnection.sendPacket(new PacketPlayOutSpawnPosition(blockposition1));
/*  603 */     entityplayer1.playerConnection.sendPacket(new PacketPlayOutExperience(entityplayer1.exp, entityplayer1.expTotal, entityplayer1.expLevel));
/*  604 */     b(entityplayer1, worldserver);
/*      */     
/*  606 */     if (!entityplayer.playerConnection.isDisconnected()) {
/*  607 */       worldserver.getPlayerChunkMap().addPlayer(entityplayer1);
/*  608 */       worldserver.addEntity(entityplayer1);
/*  609 */       this.players.add(entityplayer1);
/*  610 */       this.playersByName.put(entityplayer1.getName(), entityplayer1);
/*  611 */       this.j.put(entityplayer1.getUniqueID(), entityplayer1);
/*      */     }
/*      */     
/*  614 */     updateClient(entityplayer);
/*  615 */     entityplayer.updateAbilities();
/*  616 */     for (Object o1 : entityplayer.getEffects()) {
/*  617 */       MobEffect mobEffect = (MobEffect)o1;
/*  618 */       entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer.getId(), mobEffect));
/*      */     }
/*      */     
/*      */ 
/*  622 */     entityplayer1.setHealth(entityplayer1.getHealth());
/*      */     
/*      */ 
/*      */ 
/*  626 */     if (fromWorld != location.getWorld()) {
/*  627 */       PlayerChangedWorldEvent event = new PlayerChangedWorldEvent(entityplayer.getBukkitEntity(), fromWorld);
/*  628 */       this.server.server.getPluginManager().callEvent(event);
/*      */     }
/*      */     
/*      */ 
/*  632 */     if (entityplayer.playerConnection.isDisconnected()) {
/*  633 */       savePlayerFile(entityplayer);
/*      */     }
/*      */     
/*  636 */     return entityplayer1;
/*      */   }
/*      */   
/*      */   public void changeDimension(EntityPlayer entityplayer, int i, PlayerTeleportEvent.TeleportCause cause)
/*      */   {
/*  641 */     WorldServer exitWorld = null;
/*  642 */     if (entityplayer.dimension < 10)
/*      */     {
/*  644 */       for (WorldServer world : this.server.worlds) {
/*  645 */         if (world.dimension == i) {
/*  646 */           exitWorld = world;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  651 */     Location enter = entityplayer.getBukkitEntity().getLocation();
/*  652 */     Location exit = null;
/*  653 */     boolean useTravelAgent = false;
/*  654 */     if (exitWorld != null) {
/*  655 */       if ((cause == PlayerTeleportEvent.TeleportCause.END_PORTAL) && (i == 0))
/*      */       {
/*  657 */         exit = entityplayer.getBukkitEntity().getBedSpawnLocation();
/*  658 */         if ((exit == null) || (((CraftWorld)exit.getWorld()).getHandle().dimension != 0)) {
/*  659 */           exit = exitWorld.getWorld().getSpawnLocation();
/*      */         }
/*      */       }
/*      */       else {
/*  663 */         exit = calculateTarget(enter, exitWorld);
/*  664 */         useTravelAgent = true;
/*      */       }
/*      */     }
/*      */     
/*  668 */     TravelAgent agent = exit != null ? (TravelAgent)((CraftWorld)exit.getWorld()).getHandle().getTravelAgent() : org.bukkit.craftbukkit.v1_8_R3.CraftTravelAgent.DEFAULT;
/*  669 */     PlayerPortalEvent event = new PlayerPortalEvent(entityplayer.getBukkitEntity(), enter, exit, agent, cause);
/*  670 */     event.useTravelAgent(useTravelAgent);
/*  671 */     Bukkit.getServer().getPluginManager().callEvent(event);
/*  672 */     if ((event.isCancelled()) || (event.getTo() == null)) {
/*  673 */       return;
/*      */     }
/*      */     
/*  676 */     exit = event.useTravelAgent() ? event.getPortalTravelAgent().findOrCreate(event.getTo()) : event.getTo();
/*  677 */     if (exit == null) {
/*  678 */       return;
/*      */     }
/*  680 */     exitWorld = ((CraftWorld)exit.getWorld()).getHandle();
/*      */     
/*  682 */     PlayerTeleportEvent tpEvent = new PlayerTeleportEvent(entityplayer.getBukkitEntity(), enter, exit, cause);
/*  683 */     Bukkit.getServer().getPluginManager().callEvent(tpEvent);
/*  684 */     if ((tpEvent.isCancelled()) || (tpEvent.getTo() == null)) {
/*  685 */       return;
/*      */     }
/*      */     
/*  688 */     Vector velocity = entityplayer.getBukkitEntity().getVelocity();
/*  689 */     boolean before = exitWorld.chunkProviderServer.forceChunkLoad;
/*  690 */     exitWorld.chunkProviderServer.forceChunkLoad = true;
/*  691 */     exitWorld.getTravelAgent().adjustExit(entityplayer, exit, velocity);
/*  692 */     exitWorld.chunkProviderServer.forceChunkLoad = before;
/*      */     
/*  694 */     moveToWorld(entityplayer, exitWorld.dimension, true, exit, false);
/*  695 */     if ((entityplayer.motX != velocity.getX()) || (entityplayer.motY != velocity.getY()) || (entityplayer.motZ != velocity.getZ())) {
/*  696 */       entityplayer.getBukkitEntity().setVelocity(velocity);
/*      */     }
/*      */   }
/*      */   
/*      */   public void changeWorld(Entity entity, int i, WorldServer worldserver, WorldServer worldserver1)
/*      */   {
/*  702 */     Location exit = calculateTarget(entity.getBukkitEntity().getLocation(), worldserver1);
/*  703 */     repositionEntity(entity, exit, true);
/*      */   }
/*      */   
/*      */   public Location calculateTarget(Location enter, World target)
/*      */   {
/*  708 */     WorldServer worldserver = ((CraftWorld)enter.getWorld()).getHandle();
/*  709 */     WorldServer worldserver1 = target.getWorld().getHandle();
/*  710 */     int i = worldserver.dimension;
/*      */     
/*  712 */     double y = enter.getY();
/*  713 */     float yaw = enter.getYaw();
/*  714 */     float pitch = enter.getPitch();
/*  715 */     double d0 = enter.getX();
/*  716 */     double d1 = enter.getZ();
/*  717 */     double d2 = 8.0D;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  726 */     if (worldserver1.dimension == -1) {
/*  727 */       d0 = MathHelper.a(d0 / d2, worldserver1.getWorldBorder().b() + 16.0D, worldserver1.getWorldBorder().d() - 16.0D);
/*  728 */       d1 = MathHelper.a(d1 / d2, worldserver1.getWorldBorder().c() + 16.0D, worldserver1.getWorldBorder().e() - 16.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*  735 */     else if (worldserver1.dimension == 0) {
/*  736 */       d0 = MathHelper.a(d0 * d2, worldserver1.getWorldBorder().b() + 16.0D, worldserver1.getWorldBorder().d() - 16.0D);
/*  737 */       d1 = MathHelper.a(d1 * d2, worldserver1.getWorldBorder().c() + 16.0D, worldserver1.getWorldBorder().e() - 16.0D);
/*      */     }
/*      */     else
/*      */     {
/*      */       BlockPosition blockposition;
/*      */       
/*      */ 
/*      */       BlockPosition blockposition;
/*      */       
/*      */ 
/*  747 */       if (i == 1)
/*      */       {
/*  749 */         worldserver1 = (WorldServer)this.server.worlds.get(0);
/*  750 */         blockposition = worldserver1.getSpawn();
/*      */       } else {
/*  752 */         blockposition = worldserver1.getDimensionSpawn();
/*      */       }
/*      */       
/*  755 */       d0 = blockposition.getX();
/*  756 */       y = blockposition.getY();
/*  757 */       d1 = blockposition.getZ();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  767 */     if (i != 1) {
/*  768 */       worldserver.methodProfiler.a("placing");
/*  769 */       d0 = MathHelper.clamp((int)d0, -29999872, 29999872);
/*  770 */       d1 = MathHelper.clamp((int)d1, -29999872, 29999872);
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
/*  784 */     return new Location(worldserver1.getWorld(), d0, y, d1, yaw, pitch);
/*      */   }
/*      */   
/*      */   public void repositionEntity(Entity entity, Location exit, boolean portal)
/*      */   {
/*  789 */     WorldServer worldserver = (WorldServer)entity.world;
/*  790 */     WorldServer worldserver1 = ((CraftWorld)exit.getWorld()).getHandle();
/*  791 */     int i = worldserver.dimension;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  801 */     entity.setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
/*  802 */     if (entity.isAlive()) {
/*  803 */       worldserver.entityJoinedWorld(entity, false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  841 */     worldserver.methodProfiler.b();
/*  842 */     if (i != 1) {
/*  843 */       worldserver.methodProfiler.a("placing");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  848 */       if (entity.isAlive())
/*      */       {
/*      */ 
/*  851 */         if (portal) {
/*  852 */           Vector velocity = entity.getBukkitEntity().getVelocity();
/*  853 */           worldserver1.getTravelAgent().adjustExit(entity, exit, velocity);
/*  854 */           entity.setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
/*  855 */           if ((entity.motX != velocity.getX()) || (entity.motY != velocity.getY()) || (entity.motZ != velocity.getZ())) {
/*  856 */             entity.getBukkitEntity().setVelocity(velocity);
/*      */           }
/*      */         }
/*  859 */         worldserver1.addEntity(entity);
/*  860 */         worldserver1.entityJoinedWorld(entity, false);
/*      */       }
/*      */       
/*  863 */       worldserver.methodProfiler.b();
/*      */     }
/*      */     
/*  866 */     entity.spawnIn(worldserver1);
/*      */   }
/*      */   
/*      */   public void tick()
/*      */   {
/*  871 */     if (++this.u > 600) {
/*  872 */       sendAll(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_LATENCY, this.players));
/*  873 */       this.u = 0;
/*      */     }
/*      */   }
/*      */   
/*      */   public void sendAll(Packet packet)
/*      */   {
/*  879 */     for (int i = 0; i < this.players.size(); i++) {
/*  880 */       ((EntityPlayer)this.players.get(i)).playerConnection.sendPacket(packet);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void sendAll(Packet packet, EntityHuman entityhuman)
/*      */   {
/*  887 */     for (int i = 0; i < this.players.size(); i++) {
/*  888 */       EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
/*  889 */       if ((entityhuman == null) || (!(entityhuman instanceof EntityPlayer)) || (entityplayer.getBukkitEntity().canSee(((EntityPlayer)entityhuman).getBukkitEntity())))
/*      */       {
/*      */ 
/*  892 */         ((EntityPlayer)this.players.get(i)).playerConnection.sendPacket(packet); }
/*      */     }
/*      */   }
/*      */   
/*      */   public void sendAll(Packet packet, World world) {
/*  897 */     for (int i = 0; i < world.players.size(); i++) {
/*  898 */       ((EntityPlayer)this.players.get(i)).playerConnection.sendPacket(packet);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void a(Packet packet, int i)
/*      */   {
/*  905 */     for (int j = 0; j < this.players.size(); j++) {
/*  906 */       EntityPlayer entityplayer = (EntityPlayer)this.players.get(j);
/*      */       
/*  908 */       if (entityplayer.dimension == i) {
/*  909 */         entityplayer.playerConnection.sendPacket(packet);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(EntityHuman entityhuman, IChatBaseComponent ichatbasecomponent)
/*      */   {
/*  916 */     ScoreboardTeamBase scoreboardteambase = entityhuman.getScoreboardTeam();
/*      */     
/*  918 */     if (scoreboardteambase != null) {
/*  919 */       Collection collection = scoreboardteambase.getPlayerNameSet();
/*  920 */       Iterator iterator = collection.iterator();
/*      */       
/*  922 */       while (iterator.hasNext()) {
/*  923 */         String s = (String)iterator.next();
/*  924 */         EntityPlayer entityplayer = getPlayer(s);
/*      */         
/*  926 */         if ((entityplayer != null) && (entityplayer != entityhuman)) {
/*  927 */           entityplayer.sendMessage(ichatbasecomponent);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void b(EntityHuman entityhuman, IChatBaseComponent ichatbasecomponent)
/*      */   {
/*  935 */     ScoreboardTeamBase scoreboardteambase = entityhuman.getScoreboardTeam();
/*      */     
/*  937 */     if (scoreboardteambase == null) {
/*  938 */       sendMessage(ichatbasecomponent);
/*      */     } else {
/*  940 */       for (int i = 0; i < this.players.size(); i++) {
/*  941 */         EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
/*      */         
/*  943 */         if (entityplayer.getScoreboardTeam() != scoreboardteambase) {
/*  944 */           entityplayer.sendMessage(ichatbasecomponent);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String b(boolean flag)
/*      */   {
/*  952 */     String s = "";
/*  953 */     ArrayList arraylist = Lists.newArrayList(this.players);
/*      */     
/*  955 */     for (int i = 0; i < arraylist.size(); i++) {
/*  956 */       if (i > 0) {
/*  957 */         s = s + ", ";
/*      */       }
/*      */       
/*  960 */       s = s + ((EntityPlayer)arraylist.get(i)).getName();
/*  961 */       if (flag) {
/*  962 */         s = s + " (" + ((EntityPlayer)arraylist.get(i)).getUniqueID().toString() + ")";
/*      */       }
/*      */     }
/*      */     
/*  966 */     return s;
/*      */   }
/*      */   
/*      */   public String[] f() {
/*  970 */     String[] astring = new String[this.players.size()];
/*      */     
/*  972 */     for (int i = 0; i < this.players.size(); i++) {
/*  973 */       astring[i] = ((EntityPlayer)this.players.get(i)).getName();
/*      */     }
/*      */     
/*  976 */     return astring;
/*      */   }
/*      */   
/*      */   public GameProfile[] g() {
/*  980 */     GameProfile[] agameprofile = new GameProfile[this.players.size()];
/*      */     
/*  982 */     for (int i = 0; i < this.players.size(); i++) {
/*  983 */       agameprofile[i] = ((EntityPlayer)this.players.get(i)).getProfile();
/*      */     }
/*      */     
/*  986 */     return agameprofile;
/*      */   }
/*      */   
/*      */   public GameProfileBanList getProfileBans() {
/*  990 */     return this.k;
/*      */   }
/*      */   
/*      */   public IpBanList getIPBans() {
/*  994 */     return this.l;
/*      */   }
/*      */   
/*      */   public void addOp(GameProfile gameprofile) {
/*  998 */     this.operators.add(new OpListEntry(gameprofile, this.server.p(), this.operators.b(gameprofile)));
/*      */     
/*      */ 
/* 1001 */     Player player = this.server.server.getPlayer(gameprofile.getId());
/* 1002 */     if (player != null) {
/* 1003 */       player.recalculatePermissions();
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeOp(GameProfile gameprofile)
/*      */   {
/* 1009 */     this.operators.remove(gameprofile);
/*      */     
/*      */ 
/* 1012 */     Player player = this.server.server.getPlayer(gameprofile.getId());
/* 1013 */     if (player != null) {
/* 1014 */       player.recalculatePermissions();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isWhitelisted(GameProfile gameprofile)
/*      */   {
/* 1020 */     return (!this.hasWhitelist) || (this.operators.d(gameprofile)) || (this.whitelist.d(gameprofile));
/*      */   }
/*      */   
/*      */   public boolean isOp(GameProfile gameprofile) {
/* 1024 */     return (this.operators.d(gameprofile)) || ((this.server.T()) && (((WorldServer)this.server.worlds.get(0)).getWorldData().v()) && (this.server.S().equalsIgnoreCase(gameprofile.getName()))) || (this.t);
/*      */   }
/*      */   
/*      */   public EntityPlayer getPlayer(String s) {
/* 1028 */     return (EntityPlayer)this.playersByName.get(s);
/*      */   }
/*      */   
/*      */   public void sendPacketNearby(double d0, double d1, double d2, double d3, int i, Packet packet) {
/* 1032 */     sendPacketNearby(null, d0, d1, d2, d3, i, packet);
/*      */   }
/*      */   
/*      */   public void sendPacketNearby(EntityHuman entityhuman, double d0, double d1, double d2, double d3, int i, Packet packet) {
/* 1036 */     for (int j = 0; j < this.players.size(); j++) {
/* 1037 */       EntityPlayer entityplayer = (EntityPlayer)this.players.get(j);
/*      */       
/*      */ 
/* 1040 */       if ((entityhuman == null) || (!(entityhuman instanceof EntityPlayer)) || (entityplayer.getBukkitEntity().canSee(((EntityPlayer)entityhuman).getBukkitEntity())))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1045 */         if ((entityplayer != entityhuman) && (entityplayer.dimension == i)) {
/* 1046 */           double d4 = d0 - entityplayer.locX;
/* 1047 */           double d5 = d1 - entityplayer.locY;
/* 1048 */           double d6 = d2 - entityplayer.locZ;
/*      */           
/* 1050 */           if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3) {
/* 1051 */             entityplayer.playerConnection.sendPacket(packet);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void savePlayers() {
/* 1059 */     for (int i = 0; i < this.players.size(); i++) {
/* 1060 */       savePlayerFile((EntityPlayer)this.players.get(i));
/*      */     }
/*      */   }
/*      */   
/*      */   public void addWhitelist(GameProfile gameprofile)
/*      */   {
/* 1066 */     this.whitelist.add(new WhiteListEntry(gameprofile));
/*      */   }
/*      */   
/*      */   public void removeWhitelist(GameProfile gameprofile) {
/* 1070 */     this.whitelist.remove(gameprofile);
/*      */   }
/*      */   
/*      */   public WhiteList getWhitelist() {
/* 1074 */     return this.whitelist;
/*      */   }
/*      */   
/*      */   public String[] getWhitelisted() {
/* 1078 */     return this.whitelist.getEntries();
/*      */   }
/*      */   
/*      */   public OpList getOPs() {
/* 1082 */     return this.operators;
/*      */   }
/*      */   
/*      */   public String[] n() {
/* 1086 */     return this.operators.getEntries();
/*      */   }
/*      */   
/*      */   public void reloadWhitelist() {}
/*      */   
/*      */   public void b(EntityPlayer entityplayer, WorldServer worldserver) {
/* 1092 */     WorldBorder worldborder = entityplayer.world.getWorldBorder();
/*      */     
/* 1094 */     entityplayer.playerConnection.sendPacket(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE));
/* 1095 */     entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateTime(worldserver.getTime(), worldserver.getDayTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")));
/* 1096 */     if (worldserver.S())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 1101 */       entityplayer.setPlayerWeather(org.bukkit.WeatherType.DOWNFALL, false);
/* 1102 */       entityplayer.updateWeather(-worldserver.p, worldserver.p, -worldserver.r, worldserver.r);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void updateClient(EntityPlayer entityplayer)
/*      */   {
/* 1109 */     entityplayer.updateInventory(entityplayer.defaultContainer);
/*      */     
/* 1111 */     entityplayer.getBukkitEntity().updateScaledHealth();
/* 1112 */     entityplayer.playerConnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
/*      */   }
/*      */   
/*      */   public int getPlayerCount() {
/* 1116 */     return this.players.size();
/*      */   }
/*      */   
/*      */   public int getMaxPlayers() {
/* 1120 */     return this.maxPlayers;
/*      */   }
/*      */   
/*      */   public String[] getSeenPlayers() {
/* 1124 */     return ((WorldServer)this.server.worlds.get(0)).getDataManager().getPlayerFileData().getSeenPlayers();
/*      */   }
/*      */   
/*      */   public boolean getHasWhitelist() {
/* 1128 */     return this.hasWhitelist;
/*      */   }
/*      */   
/*      */   public void setHasWhitelist(boolean flag) {
/* 1132 */     this.hasWhitelist = flag;
/*      */   }
/*      */   
/*      */   public List<EntityPlayer> b(String s) {
/* 1136 */     ArrayList arraylist = Lists.newArrayList();
/* 1137 */     Iterator iterator = this.players.iterator();
/*      */     
/* 1139 */     while (iterator.hasNext()) {
/* 1140 */       EntityPlayer entityplayer = (EntityPlayer)iterator.next();
/*      */       
/* 1142 */       if (entityplayer.w().equals(s)) {
/* 1143 */         arraylist.add(entityplayer);
/*      */       }
/*      */     }
/*      */     
/* 1147 */     return arraylist;
/*      */   }
/*      */   
/*      */   public int s() {
/* 1151 */     return this.r;
/*      */   }
/*      */   
/*      */   public MinecraftServer getServer() {
/* 1155 */     return this.server;
/*      */   }
/*      */   
/*      */   public NBTTagCompound t() {
/* 1159 */     return null;
/*      */   }
/*      */   
/*      */   private void a(EntityPlayer entityplayer, EntityPlayer entityplayer1, World world) {
/* 1163 */     if (entityplayer1 != null) {
/* 1164 */       entityplayer.playerInteractManager.setGameMode(entityplayer1.playerInteractManager.getGameMode());
/* 1165 */     } else if (this.s != null) {
/* 1166 */       entityplayer.playerInteractManager.setGameMode(this.s);
/*      */     }
/*      */     
/* 1169 */     entityplayer.playerInteractManager.b(world.getWorldData().getGameType());
/*      */   }
/*      */   
/*      */   public void u() {
/* 1173 */     for (int i = 0; i < this.players.size(); i++) {
/* 1174 */       ((EntityPlayer)this.players.get(i)).playerConnection.disconnect(this.server.server.getShutdownMessage());
/*      */     }
/*      */   }
/*      */   
/*      */   public void sendMessage(IChatBaseComponent[] iChatBaseComponents)
/*      */   {
/*      */     IChatBaseComponent[] arrayOfIChatBaseComponent;
/* 1181 */     int i = (arrayOfIChatBaseComponent = iChatBaseComponents).length; for (int m = 0; m < i; m++) { IChatBaseComponent component = arrayOfIChatBaseComponent[m];
/* 1182 */       sendMessage(component, true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void sendMessage(IChatBaseComponent ichatbasecomponent, boolean flag)
/*      */   {
/* 1188 */     this.server.sendMessage(ichatbasecomponent);
/* 1189 */     int i = flag ? 1 : 0;
/*      */     
/*      */ 
/* 1192 */     sendAll(new PacketPlayOutChat(CraftChatMessage.fixComponent(ichatbasecomponent), (byte)i));
/*      */   }
/*      */   
/*      */   public void sendMessage(IChatBaseComponent ichatbasecomponent)
/*      */   {
/* 1197 */     sendMessage(ichatbasecomponent, true);
/*      */   }
/*      */   
/*      */   public ServerStatisticManager a(EntityHuman entityhuman) {
/* 1201 */     UUID uuid = entityhuman.getUniqueID();
/* 1202 */     ServerStatisticManager serverstatisticmanager = uuid == null ? null : (ServerStatisticManager)this.o.get(uuid);
/*      */     
/* 1204 */     if (serverstatisticmanager == null) {
/* 1205 */       File file = new File(this.server.getWorldServer(0).getDataManager().getDirectory(), "stats");
/* 1206 */       File file1 = new File(file, uuid.toString() + ".json");
/*      */       
/* 1208 */       if (!file1.exists()) {
/* 1209 */         File file2 = new File(file, entityhuman.getName() + ".json");
/*      */         
/* 1211 */         if ((file2.exists()) && (file2.isFile())) {
/* 1212 */           file2.renameTo(file1);
/*      */         }
/*      */       }
/*      */       
/* 1216 */       serverstatisticmanager = new ServerStatisticManager(this.server, file1);
/* 1217 */       serverstatisticmanager.a();
/* 1218 */       this.o.put(uuid, serverstatisticmanager);
/*      */     }
/*      */     
/* 1221 */     return serverstatisticmanager;
/*      */   }
/*      */   
/*      */   public void a(int i) {
/* 1225 */     this.r = i;
/* 1226 */     if (this.server.worldServer != null) {
/* 1227 */       WorldServer[] aworldserver = this.server.worldServer;
/* 1228 */       aworldserver.length;
/*      */       
/*      */ 
/* 1231 */       for (int k = 0; k < this.server.worlds.size(); k++) {
/* 1232 */         WorldServer worldserver = (WorldServer)this.server.worlds.get(0);
/*      */         
/*      */ 
/* 1235 */         if (worldserver != null) {
/* 1236 */           worldserver.getPlayerChunkMap().a(i);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public List<EntityPlayer> v()
/*      */   {
/* 1244 */     return this.players;
/*      */   }
/*      */   
/*      */   public EntityPlayer a(UUID uuid) {
/* 1248 */     return (EntityPlayer)this.j.get(uuid);
/*      */   }
/*      */   
/*      */   public boolean f(GameProfile gameprofile) {
/* 1252 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PlayerList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */