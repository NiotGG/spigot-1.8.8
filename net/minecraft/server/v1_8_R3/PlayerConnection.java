/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.primitives.Doubles;
/*      */ import com.google.common.primitives.Floats;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.command.CommandException;
/*      */ import org.bukkit.command.ConsoleCommandSender;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftSign;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.LazyPlayerSet;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.Waitable;
/*      */ import org.bukkit.entity.HumanEntity;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.Event.Result;
/*      */ import org.bukkit.event.block.Action;
/*      */ import org.bukkit.event.block.SignChangeEvent;
/*      */ import org.bukkit.event.inventory.ClickType;
/*      */ import org.bukkit.event.inventory.CraftItemEvent;
/*      */ import org.bukkit.event.inventory.InventoryAction;
/*      */ import org.bukkit.event.inventory.InventoryClickEvent;
/*      */ import org.bukkit.event.inventory.InventoryCreativeEvent;
/*      */ import org.bukkit.event.inventory.InventoryType.SlotType;
/*      */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*      */ import org.bukkit.event.player.PlayerAnimationEvent;
/*      */ import org.bukkit.event.player.PlayerChatEvent;
/*      */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*      */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*      */ import org.bukkit.event.player.PlayerInteractEvent;
/*      */ import org.bukkit.event.player.PlayerItemHeldEvent;
/*      */ import org.bukkit.event.player.PlayerKickEvent;
/*      */ import org.bukkit.event.player.PlayerMoveEvent;
/*      */ import org.bukkit.event.player.PlayerTeleportEvent;
/*      */ import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
/*      */ import org.bukkit.event.player.PlayerToggleFlightEvent;
/*      */ import org.bukkit.event.player.PlayerToggleSneakEvent;
/*      */ import org.bukkit.event.player.PlayerToggleSprintEvent;
/*      */ import org.bukkit.inventory.CraftingInventory;
/*      */ import org.bukkit.inventory.Inventory;
/*      */ import org.bukkit.inventory.InventoryView;
/*      */ import org.bukkit.inventory.Recipe;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.bukkit.util.NumberConversions;
/*      */ import org.spigotmc.CustomTimingsHandler;
/*      */ import org.spigotmc.SpigotConfig;
/*      */ 
/*      */ public class PlayerConnection implements PacketListenerPlayIn, IUpdatePlayerListBox
/*      */ {
/*   67 */   private static final org.apache.logging.log4j.Logger c = ;
/*      */   
/*      */   public final NetworkManager networkManager;
/*      */   private final MinecraftServer minecraftServer;
/*      */   public EntityPlayer player;
/*      */   private int e;
/*      */   private int f;
/*      */   private int g;
/*      */   private boolean h;
/*      */   private int i;
/*      */   private long j;
/*      */   private long k;
/*      */   private volatile int chatThrottle;
/*   80 */   private static final AtomicIntegerFieldUpdater chatSpamField = AtomicIntegerFieldUpdater.newUpdater(PlayerConnection.class, "chatThrottle");
/*      */   
/*      */   private int m;
/*   83 */   private IntHashMap<Short> n = new IntHashMap();
/*      */   private double o;
/*      */   private double p;
/*      */   private double q;
/*   87 */   private boolean checkMovement = true;
/*      */   private boolean processedDisconnect;
/*      */   private final CraftServer server;
/*      */   
/*   91 */   public PlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) { this.minecraftServer = minecraftserver;
/*   92 */     this.networkManager = networkmanager;
/*   93 */     networkmanager.a(this);
/*   94 */     this.player = entityplayer;
/*   95 */     entityplayer.playerConnection = this;
/*      */     
/*      */ 
/*   98 */     this.server = minecraftserver.server;
/*      */   }
/*      */   
/*      */ 
/*  102 */   private int lastTick = MinecraftServer.currentTick;
/*  103 */   private int lastDropTick = MinecraftServer.currentTick;
/*  104 */   private int dropCount = 0;
/*      */   
/*      */   private static final int SURVIVAL_PLACE_DISTANCE_SQUARED = 36;
/*      */   
/*      */   private static final int CREATIVE_PLACE_DISTANCE_SQUARED = 49;
/*  109 */   private double lastPosX = Double.MAX_VALUE;
/*  110 */   private double lastPosY = Double.MAX_VALUE;
/*  111 */   private double lastPosZ = Double.MAX_VALUE;
/*  112 */   private float lastPitch = Float.MAX_VALUE;
/*  113 */   private float lastYaw = Float.MAX_VALUE;
/*  114 */   private boolean justTeleported = false;
/*      */   
/*      */   private boolean hasMoved;
/*      */   
/*  118 */   public CraftPlayer getPlayer() { return this.player == null ? null : this.player.getBukkitEntity(); }
/*      */   
/*  120 */   private static final HashSet<Integer> invalidItems = new HashSet(java.util.Arrays.asList(new Integer[] { Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(26), Integer.valueOf(34), Integer.valueOf(36), Integer.valueOf(43), Integer.valueOf(51), Integer.valueOf(52), Integer.valueOf(55), Integer.valueOf(59), Integer.valueOf(60), Integer.valueOf(62), Integer.valueOf(63), Integer.valueOf(64), Integer.valueOf(68), Integer.valueOf(71), Integer.valueOf(74), Integer.valueOf(75), Integer.valueOf(83), Integer.valueOf(90), Integer.valueOf(92), Integer.valueOf(93), Integer.valueOf(94), Integer.valueOf(104), Integer.valueOf(105), Integer.valueOf(115), Integer.valueOf(117), Integer.valueOf(118), Integer.valueOf(119), Integer.valueOf(125), Integer.valueOf(127), Integer.valueOf(132), Integer.valueOf(140), Integer.valueOf(141), Integer.valueOf(142), Integer.valueOf(144) }));
/*      */   
/*      */   public void c()
/*      */   {
/*  124 */     this.h = false;
/*  125 */     this.e += 1;
/*  126 */     this.minecraftServer.methodProfiler.a("keepAlive");
/*  127 */     if (this.e - this.k > 40L) {
/*  128 */       this.k = this.e;
/*  129 */       this.j = d();
/*  130 */       this.i = ((int)this.j);
/*  131 */       sendPacket(new PacketPlayOutKeepAlive(this.i));
/*      */     }
/*      */     
/*  134 */     this.minecraftServer.methodProfiler.b();
/*      */     int spam;
/*  136 */     while (((spam = this.chatThrottle) > 0) && (!chatSpamField.compareAndSet(this, spam, spam - 1))) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  144 */     if (this.m > 0) {
/*  145 */       this.m -= 1;
/*      */     }
/*      */     
/*  148 */     if ((this.player.D() > 0L) && (this.minecraftServer.getIdleTimeout() > 0) && (MinecraftServer.az() - this.player.D() > this.minecraftServer.getIdleTimeout() * 1000 * 60)) {
/*  149 */       this.player.resetIdleTimer();
/*  150 */       disconnect("You have been idle for too long!");
/*      */     }
/*      */   }
/*      */   
/*      */   public NetworkManager a()
/*      */   {
/*  156 */     return this.networkManager;
/*      */   }
/*      */   
/*      */   public void disconnect(String s)
/*      */   {
/*  161 */     String leaveMessage = EnumChatFormat.YELLOW + this.player.getName() + " left the game.";
/*      */     
/*  163 */     PlayerKickEvent event = new PlayerKickEvent(this.server.getPlayer(this.player), s, leaveMessage);
/*      */     
/*  165 */     if (this.server.getServer().isRunning()) {
/*  166 */       this.server.getPluginManager().callEvent(event);
/*      */     }
/*      */     
/*  169 */     if (event.isCancelled())
/*      */     {
/*  171 */       return;
/*      */     }
/*      */     
/*  174 */     s = event.getReason();
/*      */     
/*  176 */     final ChatComponentText chatcomponenttext = new ChatComponentText(s);
/*      */     
/*  178 */     this.networkManager.a(new PacketPlayOutKickDisconnect(chatcomponenttext), new GenericFutureListener() {
/*      */       public void operationComplete(Future future) throws Exception {
/*  180 */         PlayerConnection.this.networkManager.close(chatcomponenttext);
/*      */       }
/*  182 */     }, new GenericFutureListener[0]);
/*  183 */     a(chatcomponenttext);
/*  184 */     this.networkManager.k();
/*      */     
/*  186 */     this.minecraftServer.postToMainThread(new Runnable() {
/*      */       public void run() {
/*  188 */         PlayerConnection.this.networkManager.l();
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInSteerVehicle packetplayinsteervehicle) {
/*  194 */     PlayerConnectionUtils.ensureMainThread(packetplayinsteervehicle, this, this.player.u());
/*  195 */     this.player.a(packetplayinsteervehicle.a(), packetplayinsteervehicle.b(), packetplayinsteervehicle.c(), packetplayinsteervehicle.d());
/*      */   }
/*      */   
/*      */   private boolean b(PacketPlayInFlying packetplayinflying) {
/*  199 */     return (!Doubles.isFinite(packetplayinflying.a())) || (!Doubles.isFinite(packetplayinflying.b())) || (!Doubles.isFinite(packetplayinflying.c())) || (!Floats.isFinite(packetplayinflying.e())) || (!Floats.isFinite(packetplayinflying.d()));
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInFlying packetplayinflying) {
/*  203 */     PlayerConnectionUtils.ensureMainThread(packetplayinflying, this, this.player.u());
/*  204 */     if (b(packetplayinflying)) {
/*  205 */       disconnect("Invalid move packet received");
/*      */     } else {
/*  207 */       WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/*      */       
/*  209 */       this.h = true;
/*  210 */       if (!this.player.viewingCredits) {
/*  211 */         double d0 = this.player.locX;
/*  212 */         double d1 = this.player.locY;
/*  213 */         double d2 = this.player.locZ;
/*  214 */         double d3 = 0.0D;
/*  215 */         double d4 = packetplayinflying.a() - this.o;
/*  216 */         double d5 = packetplayinflying.b() - this.p;
/*  217 */         double d6 = packetplayinflying.c() - this.q;
/*      */         
/*  219 */         if (packetplayinflying.g()) {
/*  220 */           d3 = d4 * d4 + d5 * d5 + d6 * d6;
/*  221 */           if ((!this.checkMovement) && (d3 < 0.25D)) {
/*  222 */             this.checkMovement = true;
/*      */           }
/*      */         }
/*      */         
/*  226 */         Player player = getPlayer();
/*      */         
/*  228 */         if (!this.hasMoved)
/*      */         {
/*  230 */           Location curPos = player.getLocation();
/*  231 */           this.lastPosX = curPos.getX();
/*  232 */           this.lastPosY = curPos.getY();
/*  233 */           this.lastPosZ = curPos.getZ();
/*  234 */           this.lastYaw = curPos.getYaw();
/*  235 */           this.lastPitch = curPos.getPitch();
/*  236 */           this.hasMoved = true;
/*      */         }
/*      */         
/*  239 */         Location from = new Location(player.getWorld(), this.lastPosX, this.lastPosY, this.lastPosZ, this.lastYaw, this.lastPitch);
/*  240 */         Location to = player.getLocation().clone();
/*      */         
/*      */ 
/*  243 */         if ((packetplayinflying.hasPos) && ((!packetplayinflying.hasPos) || (packetplayinflying.y != -999.0D))) {
/*  244 */           to.setX(packetplayinflying.x);
/*  245 */           to.setY(packetplayinflying.y);
/*  246 */           to.setZ(packetplayinflying.z);
/*      */         }
/*      */         
/*      */ 
/*  250 */         if (packetplayinflying.hasLook) {
/*  251 */           to.setYaw(packetplayinflying.yaw);
/*  252 */           to.setPitch(packetplayinflying.pitch);
/*      */         }
/*      */         
/*      */ 
/*  256 */         double delta = Math.pow(this.lastPosX - to.getX(), 2.0D) + Math.pow(this.lastPosY - to.getY(), 2.0D) + Math.pow(this.lastPosZ - to.getZ(), 2.0D);
/*  257 */         float deltaAngle = Math.abs(this.lastYaw - to.getYaw()) + Math.abs(this.lastPitch - to.getPitch());
/*      */         
/*  259 */         if (((delta > 0.00390625D) || (deltaAngle > 10.0F)) && (this.checkMovement) && (!this.player.dead)) {
/*  260 */           this.lastPosX = to.getX();
/*  261 */           this.lastPosY = to.getY();
/*  262 */           this.lastPosZ = to.getZ();
/*  263 */           this.lastYaw = to.getYaw();
/*  264 */           this.lastPitch = to.getPitch();
/*      */           
/*      */ 
/*      */ 
/*  268 */           Location oldTo = to.clone();
/*  269 */           PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
/*  270 */           this.server.getPluginManager().callEvent(event);
/*      */           
/*      */ 
/*  273 */           if (event.isCancelled()) {
/*  274 */             this.player.playerConnection.sendPacket(new PacketPlayOutPosition(from.getX(), from.getY(), from.getZ(), from.getYaw(), from.getPitch(), Collections.emptySet()));
/*  275 */             return;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  281 */           if ((!oldTo.equals(event.getTo())) && (!event.isCancelled())) {
/*  282 */             this.player.getBukkitEntity().teleport(event.getTo(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
/*  283 */             return;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  288 */           if ((!from.equals(getPlayer().getLocation())) && (this.justTeleported)) {
/*  289 */             this.justTeleported = false;
/*  290 */             return;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  295 */         if ((this.checkMovement) && (!this.player.dead))
/*      */         {
/*  297 */           this.f = this.e;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  302 */           if (this.player.vehicle != null) {
/*  303 */             float f = this.player.yaw;
/*  304 */             float f1 = this.player.pitch;
/*      */             
/*  306 */             this.player.vehicle.al();
/*  307 */             double d7 = this.player.locX;
/*  308 */             double d8 = this.player.locY;
/*  309 */             double d9 = this.player.locZ;
/*  310 */             if (packetplayinflying.h()) {
/*  311 */               f = packetplayinflying.d();
/*  312 */               f1 = packetplayinflying.e();
/*      */             }
/*      */             
/*  315 */             this.player.onGround = packetplayinflying.f();
/*  316 */             this.player.l();
/*  317 */             this.player.setLocation(d7, d8, d9, f, f1);
/*  318 */             if (this.player.vehicle != null) {
/*  319 */               this.player.vehicle.al();
/*      */             }
/*      */             
/*  322 */             this.minecraftServer.getPlayerList().d(this.player);
/*  323 */             if (this.player.vehicle != null) {
/*  324 */               this.player.vehicle.ai = true;
/*  325 */               if (d3 > 4.0D) {
/*  326 */                 Entity entity = this.player.vehicle;
/*      */                 
/*  328 */                 this.player.playerConnection.sendPacket(new PacketPlayOutEntityTeleport(entity));
/*  329 */                 a(this.player.locX, this.player.locY, this.player.locZ, this.player.yaw, this.player.pitch);
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  335 */             if (this.checkMovement) {
/*  336 */               this.o = this.player.locX;
/*  337 */               this.p = this.player.locY;
/*  338 */               this.q = this.player.locZ;
/*      */             }
/*      */             
/*  341 */             worldserver.g(this.player);
/*  342 */             return;
/*      */           }
/*      */           
/*  345 */           if (this.player.isSleeping()) {
/*  346 */             this.player.l();
/*  347 */             this.player.setLocation(this.o, this.p, this.q, this.player.yaw, this.player.pitch);
/*  348 */             worldserver.g(this.player);
/*  349 */             return;
/*      */           }
/*      */           
/*  352 */           double d10 = this.player.locY;
/*      */           
/*  354 */           this.o = this.player.locX;
/*  355 */           this.p = this.player.locY;
/*  356 */           this.q = this.player.locZ;
/*  357 */           double d7 = this.player.locX;
/*  358 */           double d8 = this.player.locY;
/*  359 */           double d9 = this.player.locZ;
/*  360 */           float f2 = this.player.yaw;
/*  361 */           float f3 = this.player.pitch;
/*      */           
/*  363 */           if ((packetplayinflying.g()) && (packetplayinflying.b() == -999.0D)) {
/*  364 */             packetplayinflying.a(false);
/*      */           }
/*      */           
/*  367 */           if (packetplayinflying.g()) {
/*  368 */             d7 = packetplayinflying.a();
/*  369 */             d8 = packetplayinflying.b();
/*  370 */             d9 = packetplayinflying.c();
/*  371 */             if ((Math.abs(packetplayinflying.a()) > 3.0E7D) || (Math.abs(packetplayinflying.c()) > 3.0E7D)) {
/*  372 */               disconnect("Illegal position");
/*  373 */               return;
/*      */             }
/*      */           }
/*      */           
/*  377 */           if (packetplayinflying.h()) {
/*  378 */             f2 = packetplayinflying.d();
/*  379 */             f3 = packetplayinflying.e();
/*      */           }
/*      */           
/*  382 */           this.player.l();
/*  383 */           this.player.setLocation(this.o, this.p, this.q, f2, f3);
/*  384 */           if (!this.checkMovement) {
/*  385 */             return;
/*      */           }
/*      */           
/*  388 */           double d11 = d7 - this.player.locX;
/*  389 */           double d12 = d8 - this.player.locY;
/*  390 */           double d13 = d9 - this.player.locZ;
/*  391 */           double d14 = this.player.motX * this.player.motX + this.player.motY * this.player.motY + this.player.motZ * this.player.motZ;
/*  392 */           double d15 = d11 * d11 + d12 * d12 + d13 * d13;
/*      */           
/*      */ 
/*  395 */           if ((d15 - d14 > SpigotConfig.movedTooQuicklyThreshold) && (this.checkMovement) && ((!this.minecraftServer.T()) || (!this.minecraftServer.S().equals(this.player.getName())))) {
/*  396 */             c.warn(this.player.getName() + " moved too quickly! " + d11 + "," + d12 + "," + d13 + " (" + d11 + ", " + d12 + ", " + d13 + ")");
/*  397 */             a(this.o, this.p, this.q, this.player.yaw, this.player.pitch);
/*  398 */             return;
/*      */           }
/*      */           
/*  401 */           float f4 = 0.0625F;
/*  402 */           boolean flag = worldserver.getCubes(this.player, this.player.getBoundingBox().shrink(f4, f4, f4)).isEmpty();
/*      */           
/*  404 */           if ((this.player.onGround) && (!packetplayinflying.f()) && (d12 > 0.0D)) {
/*  405 */             this.player.bF();
/*      */           }
/*      */           
/*  408 */           this.player.move(d11, d12, d13);
/*  409 */           this.player.onGround = packetplayinflying.f();
/*  410 */           double d16 = d12;
/*      */           
/*  412 */           d11 = d7 - this.player.locX;
/*  413 */           d12 = d8 - this.player.locY;
/*  414 */           if ((d12 > -0.5D) || (d12 < 0.5D)) {
/*  415 */             d12 = 0.0D;
/*      */           }
/*      */           
/*  418 */           d13 = d9 - this.player.locZ;
/*  419 */           d15 = d11 * d11 + d12 * d12 + d13 * d13;
/*  420 */           boolean flag1 = false;
/*      */           
/*      */ 
/*  423 */           if ((d15 > SpigotConfig.movedWronglyThreshold) && (!this.player.isSleeping()) && (!this.player.playerInteractManager.isCreative())) {
/*  424 */             flag1 = true;
/*  425 */             c.warn(this.player.getName() + " moved wrongly!");
/*      */           }
/*      */           
/*  428 */           this.player.setLocation(d7, d8, d9, f2, f3);
/*  429 */           this.player.checkMovement(this.player.locX - d0, this.player.locY - d1, this.player.locZ - d2);
/*  430 */           if (!this.player.noclip) {
/*  431 */             boolean flag2 = worldserver.getCubes(this.player, this.player.getBoundingBox().shrink(f4, f4, f4)).isEmpty();
/*      */             
/*  433 */             if ((flag) && ((flag1) || (!flag2)) && (!this.player.isSleeping())) {
/*  434 */               a(this.o, this.p, this.q, f2, f3);
/*  435 */               return;
/*      */             }
/*      */           }
/*      */           
/*  439 */           AxisAlignedBB axisalignedbb = this.player.getBoundingBox().grow(f4, f4, f4).a(0.0D, -0.55D, 0.0D);
/*      */           
/*  441 */           if ((!this.minecraftServer.getAllowFlight()) && (!this.player.abilities.canFly) && (!worldserver.c(axisalignedbb))) {
/*  442 */             if (d16 >= -0.03125D) {
/*  443 */               this.g += 1;
/*  444 */               if (this.g > 80) {
/*  445 */                 c.warn(this.player.getName() + " was kicked for floating too long!");
/*  446 */                 disconnect("Flying is not enabled on this server");
/*      */               }
/*      */             }
/*      */           }
/*      */           else {
/*  451 */             this.g = 0;
/*      */           }
/*      */           
/*  454 */           this.player.onGround = packetplayinflying.f();
/*  455 */           this.minecraftServer.getPlayerList().d(this.player);
/*  456 */           this.player.a(this.player.locY - d10, packetplayinflying.f());
/*  457 */         } else if (this.e - this.f > 20) {
/*  458 */           a(this.o, this.p, this.q, this.player.yaw, this.player.pitch);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(double d0, double d1, double d2, float f, float f1)
/*      */   {
/*  466 */     a(d0, d1, d2, f, f1, Collections.emptySet());
/*      */   }
/*      */   
/*      */   public void a(double d0, double d1, double d2, float f, float f1, Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> set)
/*      */   {
/*  471 */     Player player = getPlayer();
/*  472 */     Location from = player.getLocation();
/*      */     
/*  474 */     double x = d0;
/*  475 */     double y = d1;
/*  476 */     double z = d2;
/*  477 */     float yaw = f;
/*  478 */     float pitch = f1;
/*  479 */     if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X)) {
/*  480 */       x += from.getX();
/*      */     }
/*  482 */     if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y)) {
/*  483 */       y += from.getY();
/*      */     }
/*  485 */     if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Z)) {
/*  486 */       z += from.getZ();
/*      */     }
/*  488 */     if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y_ROT)) {
/*  489 */       yaw += from.getYaw();
/*      */     }
/*  491 */     if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X_ROT)) {
/*  492 */       pitch += from.getPitch();
/*      */     }
/*      */     
/*      */ 
/*  496 */     Location to = new Location(getPlayer().getWorld(), x, y, z, yaw, pitch);
/*  497 */     PlayerTeleportEvent event = new PlayerTeleportEvent(player, from.clone(), to.clone(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
/*  498 */     this.server.getPluginManager().callEvent(event);
/*      */     
/*  500 */     if ((event.isCancelled()) || (to.equals(event.getTo()))) {
/*  501 */       set.clear();
/*  502 */       to = event.isCancelled() ? event.getFrom() : event.getTo();
/*  503 */       d0 = to.getX();
/*  504 */       d1 = to.getY();
/*  505 */       d2 = to.getZ();
/*  506 */       f = to.getYaw();
/*  507 */       f1 = to.getPitch();
/*      */     }
/*      */     
/*  510 */     internalTeleport(d0, d1, d2, f, f1, set);
/*      */   }
/*      */   
/*      */   public void teleport(Location dest) {
/*  514 */     internalTeleport(dest.getX(), dest.getY(), dest.getZ(), dest.getYaw(), dest.getPitch(), Collections.emptySet());
/*      */   }
/*      */   
/*      */   private void internalTeleport(double d0, double d1, double d2, float f, float f1, Set set) {
/*  518 */     if (Float.isNaN(f)) {
/*  519 */       f = 0.0F;
/*      */     }
/*      */     
/*  522 */     if (Float.isNaN(f1)) {
/*  523 */       f1 = 0.0F;
/*      */     }
/*  525 */     this.justTeleported = true;
/*      */     
/*  527 */     this.checkMovement = false;
/*  528 */     this.o = d0;
/*  529 */     this.p = d1;
/*  530 */     this.q = d2;
/*  531 */     if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X)) {
/*  532 */       this.o += this.player.locX;
/*      */     }
/*      */     
/*  535 */     if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y)) {
/*  536 */       this.p += this.player.locY;
/*      */     }
/*      */     
/*  539 */     if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Z)) {
/*  540 */       this.q += this.player.locZ;
/*      */     }
/*      */     
/*  543 */     float f2 = f;
/*  544 */     float f3 = f1;
/*      */     
/*  546 */     if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y_ROT)) {
/*  547 */       f2 = f + this.player.yaw;
/*      */     }
/*      */     
/*  550 */     if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X_ROT)) {
/*  551 */       f3 = f1 + this.player.pitch;
/*      */     }
/*      */     
/*      */ 
/*  555 */     this.lastPosX = this.o;
/*  556 */     this.lastPosY = this.p;
/*  557 */     this.lastPosZ = this.q;
/*  558 */     this.lastYaw = f2;
/*  559 */     this.lastPitch = f3;
/*      */     
/*      */ 
/*  562 */     this.player.setLocation(this.o, this.p, this.q, f2, f3);
/*  563 */     this.player.playerConnection.sendPacket(new PacketPlayOutPosition(d0, d1, d2, f, f1, set));
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInBlockDig packetplayinblockdig) {
/*  567 */     PlayerConnectionUtils.ensureMainThread(packetplayinblockdig, this, this.player.u());
/*  568 */     if (this.player.dead) return;
/*  569 */     WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/*  570 */     BlockPosition blockposition = packetplayinblockdig.a();
/*      */     
/*  572 */     this.player.resetIdleTimer();
/*      */     
/*  574 */     switch (SyntheticClass_1.a[packetplayinblockdig.c().ordinal()]) {
/*      */     case 1: 
/*  576 */       if (!this.player.isSpectator())
/*      */       {
/*      */ 
/*  579 */         if (this.lastDropTick != MinecraftServer.currentTick) {
/*  580 */           this.dropCount = 0;
/*  581 */           this.lastDropTick = MinecraftServer.currentTick;
/*      */         }
/*      */         else {
/*  584 */           this.dropCount += 1;
/*  585 */           if (this.dropCount >= 20) {
/*  586 */             c.warn(this.player.getName() + " dropped their items too quickly!");
/*  587 */             disconnect("You dropped your items too quickly (Hacking?)");
/*  588 */             return;
/*      */           }
/*      */         }
/*      */         
/*  592 */         this.player.a(false);
/*      */       }
/*      */       
/*  595 */       return;
/*      */     
/*      */     case 2: 
/*  598 */       if (!this.player.isSpectator()) {
/*  599 */         this.player.a(true);
/*      */       }
/*      */       
/*  602 */       return;
/*      */     
/*      */     case 3: 
/*  605 */       this.player.bU();
/*  606 */       return;
/*      */     
/*      */     case 4: 
/*      */     case 5: 
/*      */     case 6: 
/*  611 */       double d0 = this.player.locX - (blockposition.getX() + 0.5D);
/*  612 */       double d1 = this.player.locY - (blockposition.getY() + 0.5D) + 1.5D;
/*  613 */       double d2 = this.player.locZ - (blockposition.getZ() + 0.5D);
/*  614 */       double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */       
/*  616 */       if (d3 > 36.0D)
/*  617 */         return;
/*  618 */       if (blockposition.getY() >= this.minecraftServer.getMaxBuildHeight()) {
/*  619 */         return;
/*      */       }
/*  621 */       if (packetplayinblockdig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
/*  622 */         if ((!this.minecraftServer.a(worldserver, blockposition, this.player)) && (worldserver.getWorldBorder().a(blockposition))) {
/*  623 */           this.player.playerInteractManager.a(blockposition, packetplayinblockdig.b());
/*      */         }
/*      */         else {
/*  626 */           CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_BLOCK, blockposition, packetplayinblockdig.b(), this.player.inventory.getItemInHand());
/*  627 */           this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition));
/*      */           
/*  629 */           TileEntity tileentity = worldserver.getTileEntity(blockposition);
/*  630 */           if (tileentity != null) {
/*  631 */             this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/*  636 */         if (packetplayinblockdig.c() == PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK) {
/*  637 */           this.player.playerInteractManager.a(blockposition);
/*  638 */         } else if (packetplayinblockdig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
/*  639 */           this.player.playerInteractManager.e();
/*      */         }
/*      */         
/*  642 */         if (worldserver.getType(blockposition).getBlock().getMaterial() != Material.AIR) {
/*  643 */           this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition));
/*      */         }
/*      */       }
/*      */       
/*  647 */       return;
/*      */     }
/*      */     
/*      */     
/*  651 */     throw new IllegalArgumentException("Invalid player action");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  657 */   private long lastPlace = -1L;
/*  658 */   private int packets = 0;
/*      */   
/*      */   public void a(PacketPlayInBlockPlace packetplayinblockplace) {
/*  661 */     PlayerConnectionUtils.ensureMainThread(packetplayinblockplace, this, this.player.u());
/*  662 */     WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/*  663 */     boolean throttled = false;
/*  664 */     if ((this.lastPlace != -1L) && (packetplayinblockplace.timestamp - this.lastPlace < 30L) && (this.packets++ >= 4)) {
/*  665 */       throttled = true;
/*  666 */     } else if ((packetplayinblockplace.timestamp - this.lastPlace >= 30L) || (this.lastPlace == -1L))
/*      */     {
/*  668 */       this.lastPlace = packetplayinblockplace.timestamp;
/*  669 */       this.packets = 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  674 */     if (this.player.dead) { return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  679 */     boolean always = false;
/*      */     
/*      */ 
/*  682 */     ItemStack itemstack = this.player.inventory.getItemInHand();
/*  683 */     boolean flag = false;
/*  684 */     BlockPosition blockposition = packetplayinblockplace.a();
/*  685 */     EnumDirection enumdirection = EnumDirection.fromType1(packetplayinblockplace.getFace());
/*      */     
/*  687 */     this.player.resetIdleTimer();
/*  688 */     if (packetplayinblockplace.getFace() == 255) {
/*  689 */       if (itemstack == null) {
/*  690 */         return;
/*      */       }
/*      */       
/*      */ 
/*  694 */       int itemstackAmount = itemstack.count;
/*      */       
/*  696 */       if (!throttled)
/*      */       {
/*  698 */         float f1 = this.player.pitch;
/*  699 */         float f2 = this.player.yaw;
/*  700 */         double d0 = this.player.locX;
/*  701 */         double d1 = this.player.locY + this.player.getHeadHeight();
/*  702 */         double d2 = this.player.locZ;
/*  703 */         Vec3D vec3d = new Vec3D(d0, d1, d2);
/*      */         
/*  705 */         float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/*  706 */         float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/*  707 */         float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/*  708 */         float f6 = MathHelper.sin(-f1 * 0.017453292F);
/*  709 */         float f7 = f4 * f5;
/*  710 */         float f8 = f3 * f5;
/*  711 */         double d3 = this.player.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.CREATIVE ? 5.0D : 4.5D;
/*  712 */         Vec3D vec3d1 = vec3d.add(f7 * d3, f6 * d3, f8 * d3);
/*  713 */         MovingObjectPosition movingobjectposition = this.player.world.rayTrace(vec3d, vec3d1, false);
/*      */         
/*  715 */         boolean cancelled = false;
/*  716 */         if ((movingobjectposition == null) || (movingobjectposition.type != MovingObjectPosition.EnumMovingObjectType.BLOCK)) {
/*  717 */           PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.RIGHT_CLICK_AIR, itemstack);
/*  718 */           cancelled = event.useItemInHand() == Event.Result.DENY;
/*      */         }
/*  720 */         else if (this.player.playerInteractManager.firedInteract) {
/*  721 */           this.player.playerInteractManager.firedInteract = false;
/*  722 */           cancelled = this.player.playerInteractManager.interactResult;
/*      */         } else {
/*  724 */           PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.RIGHT_CLICK_BLOCK, movingobjectposition.a(), movingobjectposition.direction, itemstack, true);
/*  725 */           cancelled = event.useItemInHand() == Event.Result.DENY;
/*      */         }
/*      */         
/*      */ 
/*  729 */         if (!cancelled) {
/*  730 */           this.player.playerInteractManager.useItem(this.player, this.player.world, itemstack);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  738 */       always = (itemstack.count != itemstackAmount) || (itemstack.getItem() == Item.getItemOf(Blocks.WATERLILY));
/*      */     }
/*  740 */     else if ((blockposition.getY() >= this.minecraftServer.getMaxBuildHeight() - 1) && ((enumdirection == EnumDirection.UP) || (blockposition.getY() >= this.minecraftServer.getMaxBuildHeight()))) {
/*  741 */       ChatMessage chatmessage = new ChatMessage("build.tooHigh", new Object[] { Integer.valueOf(this.minecraftServer.getMaxBuildHeight()) });
/*      */       
/*  743 */       chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  744 */       this.player.playerConnection.sendPacket(new PacketPlayOutChat(chatmessage));
/*  745 */       flag = true;
/*      */     }
/*      */     else {
/*  748 */       Location eyeLoc = getPlayer().getEyeLocation();
/*  749 */       double reachDistance = NumberConversions.square(eyeLoc.getX() - blockposition.getX()) + NumberConversions.square(eyeLoc.getY() - blockposition.getY()) + NumberConversions.square(eyeLoc.getZ() - blockposition.getZ());
/*  750 */       if (reachDistance > (getPlayer().getGameMode() == org.bukkit.GameMode.CREATIVE ? 49 : 36)) {
/*  751 */         return;
/*      */       }
/*      */       
/*  754 */       if (!worldserver.getWorldBorder().a(blockposition)) {
/*  755 */         return;
/*      */       }
/*      */       
/*  758 */       if ((this.checkMovement) && (this.player.e(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D) < 64.0D) && (!this.minecraftServer.a(worldserver, blockposition, this.player)) && (worldserver.getWorldBorder().a(blockposition))) {
/*  759 */         always = (throttled) || (!this.player.playerInteractManager.interact(this.player, worldserver, itemstack, blockposition, enumdirection, packetplayinblockplace.d(), packetplayinblockplace.e(), packetplayinblockplace.f()));
/*      */       }
/*      */       
/*  762 */       flag = true;
/*      */     }
/*      */     
/*  765 */     if (flag) {
/*  766 */       this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition));
/*  767 */       this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(worldserver, blockposition.shift(enumdirection)));
/*      */     }
/*      */     
/*  770 */     itemstack = this.player.inventory.getItemInHand();
/*  771 */     if ((itemstack != null) && (itemstack.count == 0)) {
/*  772 */       this.player.inventory.items[this.player.inventory.itemInHandIndex] = null;
/*  773 */       itemstack = null;
/*      */     }
/*      */     
/*  776 */     if ((itemstack == null) || (itemstack.l() == 0)) {
/*  777 */       this.player.g = true;
/*  778 */       this.player.inventory.items[this.player.inventory.itemInHandIndex] = ItemStack.b(this.player.inventory.items[this.player.inventory.itemInHandIndex]);
/*  779 */       Slot slot = this.player.activeContainer.getSlot(this.player.inventory, this.player.inventory.itemInHandIndex);
/*      */       
/*  781 */       this.player.activeContainer.b();
/*  782 */       this.player.g = false;
/*      */       
/*  784 */       if ((!ItemStack.matches(this.player.inventory.getItemInHand(), packetplayinblockplace.getItemStack())) || (always)) {
/*  785 */         sendPacket(new PacketPlayOutSetSlot(this.player.activeContainer.windowId, slot.rawSlotIndex, this.player.inventory.getItemInHand()));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInSpectate packetplayinspectate)
/*      */   {
/*  792 */     PlayerConnectionUtils.ensureMainThread(packetplayinspectate, this, this.player.u());
/*  793 */     if (this.player.isSpectator()) {
/*  794 */       Entity entity = null;
/*  795 */       WorldServer[] aworldserver = this.minecraftServer.worldServer;
/*  796 */       aworldserver.length;
/*      */       
/*      */ 
/*  799 */       for (WorldServer worldserver : this.minecraftServer.worlds)
/*      */       {
/*  801 */         if (worldserver != null) {
/*  802 */           entity = packetplayinspectate.a(worldserver);
/*  803 */           if (entity != null) {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  809 */       if (entity != null) {
/*  810 */         this.player.setSpectatorTarget(this.player);
/*  811 */         this.player.mount(null);
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
/*  839 */         this.player.getBukkitEntity().teleport(entity.getBukkitEntity(), PlayerTeleportEvent.TeleportCause.SPECTATE);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void a(PacketPlayInResourcePackStatus packetplayinresourcepackstatus)
/*      */   {
/*  848 */     this.server.getPluginManager().callEvent(new org.bukkit.event.player.PlayerResourcePackStatusEvent(getPlayer(), org.bukkit.event.player.PlayerResourcePackStatusEvent.Status.values()[packetplayinresourcepackstatus.b.ordinal()]));
/*      */   }
/*      */   
/*      */ 
/*      */   public void a(IChatBaseComponent ichatbasecomponent)
/*      */   {
/*  854 */     if (this.processedDisconnect) {
/*  855 */       return;
/*      */     }
/*  857 */     this.processedDisconnect = true;
/*      */     
/*      */ 
/*  860 */     c.info(this.player.getName() + " lost connection: " + ichatbasecomponent.c());
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  870 */     this.player.q();
/*  871 */     String quitMessage = this.minecraftServer.getPlayerList().disconnect(this.player);
/*  872 */     if ((quitMessage != null) && (quitMessage.length() > 0)) {
/*  873 */       this.minecraftServer.getPlayerList().sendMessage(CraftChatMessage.fromString(quitMessage));
/*      */     }
/*      */     
/*  876 */     if ((this.minecraftServer.T()) && (this.player.getName().equals(this.minecraftServer.S()))) {
/*  877 */       c.info("Stopping singleplayer server as player logged out");
/*  878 */       this.minecraftServer.safeShutdown();
/*      */     }
/*      */   }
/*      */   
/*      */   public void sendPacket(final Packet packet)
/*      */   {
/*  884 */     if ((packet instanceof PacketPlayOutChat)) {
/*  885 */       PacketPlayOutChat packetplayoutchat = (PacketPlayOutChat)packet;
/*  886 */       EntityHuman.EnumChatVisibility entityhuman_enumchatvisibility = this.player.getChatFlags();
/*      */       
/*  888 */       if (entityhuman_enumchatvisibility == EntityHuman.EnumChatVisibility.HIDDEN) {
/*  889 */         return;
/*      */       }
/*      */       
/*  892 */       if ((entityhuman_enumchatvisibility == EntityHuman.EnumChatVisibility.SYSTEM) && (!packetplayoutchat.b())) {
/*  893 */         return;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  898 */     if ((packet == null) || (this.processedDisconnect))
/*  899 */       return;
/*  900 */     if ((packet instanceof PacketPlayOutSpawnPosition)) {
/*  901 */       PacketPlayOutSpawnPosition packet6 = (PacketPlayOutSpawnPosition)packet;
/*  902 */       this.player.compassTarget = new Location(getPlayer().getWorld(), packet6.position.getX(), packet6.position.getY(), packet6.position.getZ());
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  907 */       this.networkManager.handle(packet);
/*      */     } catch (Throwable throwable) {
/*  909 */       CrashReport crashreport = CrashReport.a(throwable, "Sending packet");
/*  910 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Packet being sent");
/*      */       
/*  912 */       crashreportsystemdetails.a("Packet class", new java.util.concurrent.Callable() {
/*      */         public String a() throws Exception {
/*  914 */           return packet.getClass().getCanonicalName();
/*      */         }
/*      */         
/*      */         public Object call() throws Exception {
/*  918 */           return a();
/*      */         }
/*  920 */       });
/*  921 */       throw new ReportedException(crashreport);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInHeldItemSlot packetplayinhelditemslot)
/*      */   {
/*  927 */     if (this.player.dead) return;
/*  928 */     PlayerConnectionUtils.ensureMainThread(packetplayinhelditemslot, this, this.player.u());
/*  929 */     if ((packetplayinhelditemslot.a() >= 0) && (packetplayinhelditemslot.a() < PlayerInventory.getHotbarSize())) {
/*  930 */       PlayerItemHeldEvent event = new PlayerItemHeldEvent(getPlayer(), this.player.inventory.itemInHandIndex, packetplayinhelditemslot.a());
/*  931 */       this.server.getPluginManager().callEvent(event);
/*  932 */       if (event.isCancelled()) {
/*  933 */         sendPacket(new PacketPlayOutHeldItemSlot(this.player.inventory.itemInHandIndex));
/*  934 */         this.player.resetIdleTimer();
/*  935 */         return;
/*      */       }
/*      */       
/*  938 */       this.player.inventory.itemInHandIndex = packetplayinhelditemslot.a();
/*  939 */       this.player.resetIdleTimer();
/*      */     } else {
/*  941 */       c.warn(this.player.getName() + " tried to set an invalid carried item");
/*  942 */       disconnect("Invalid hotbar selection (Hacking?)");
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInChat packetplayinchat)
/*      */   {
/*  948 */     boolean isSync = packetplayinchat.a().startsWith("/");
/*  949 */     if (packetplayinchat.a().startsWith("/")) {
/*  950 */       PlayerConnectionUtils.ensureMainThread(packetplayinchat, this, this.player.u());
/*      */     }
/*      */     
/*  953 */     if ((this.player.dead) || (this.player.getChatFlags() == EntityHuman.EnumChatVisibility.HIDDEN)) {
/*  954 */       ChatMessage chatmessage = new ChatMessage("chat.cannotSend", new Object[0]);
/*      */       
/*  956 */       chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  957 */       sendPacket(new PacketPlayOutChat(chatmessage));
/*      */     } else {
/*  959 */       this.player.resetIdleTimer();
/*  960 */       String s = packetplayinchat.a();
/*      */       
/*  962 */       s = org.apache.commons.lang3.StringUtils.normalizeSpace(s);
/*      */       
/*  964 */       for (i = 0; i < s.length(); i++) {
/*  965 */         if (!SharedConstants.isAllowedChatCharacter(s.charAt(i)))
/*      */         {
/*  967 */           if (!isSync) {
/*  968 */             Waitable waitable = new Waitable()
/*      */             {
/*      */               protected Object evaluate() {
/*  971 */                 PlayerConnection.this.disconnect("Illegal characters in chat");
/*  972 */                 return null;
/*      */               }
/*      */               
/*  975 */             };
/*  976 */             this.minecraftServer.processQueue.add(waitable);
/*      */             try
/*      */             {
/*  979 */               waitable.get();
/*      */             } catch (InterruptedException localInterruptedException1) {
/*  981 */               Thread.currentThread().interrupt();
/*      */             } catch (ExecutionException e) {
/*  983 */               throw new RuntimeException(e);
/*      */             }
/*      */           } else {
/*  986 */             disconnect("Illegal characters in chat");
/*      */           }
/*      */           
/*  989 */           return;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  994 */       if (isSync) {
/*      */         try {
/*  996 */           this.minecraftServer.server.playerCommandState = true;
/*  997 */           handleCommand(s);
/*      */         } finally {
/*  999 */           this.minecraftServer.server.playerCommandState = false;
/*      */         }
/* 1001 */       } else if (s.isEmpty()) {
/* 1002 */         c.warn(this.player.getName() + " tried to send an empty message");
/* 1003 */       } else if (getPlayer().isConversing())
/*      */       {
/* 1005 */         final String message = s;
/* 1006 */         this.minecraftServer.processQueue.add(new Waitable()
/*      */         {
/*      */ 
/*      */           protected Object evaluate()
/*      */           {
/* 1011 */             PlayerConnection.this.getPlayer().acceptConversationInput(message);
/* 1012 */             return null;
/*      */           }
/*      */         });
/*      */       }
/* 1016 */       else if (this.player.getChatFlags() == EntityHuman.EnumChatVisibility.SYSTEM) {
/* 1017 */         ChatMessage chatmessage = new ChatMessage("chat.cannotSend", new Object[0]);
/*      */         
/* 1019 */         chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
/* 1020 */         sendPacket(new PacketPlayOutChat(chatmessage));
/*      */       } else {
/* 1022 */         chat(s, true);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1031 */       boolean counted = true;
/* 1032 */       for (String exclude : SpigotConfig.spamExclusions)
/*      */       {
/* 1034 */         if ((exclude != null) && (s.startsWith(exclude)))
/*      */         {
/* 1036 */           counted = false;
/* 1037 */           break;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1043 */       if ((counted) && (chatSpamField.addAndGet(this, 20) > 200) && (!this.minecraftServer.getPlayerList().isOp(this.player.getProfile()))) {
/* 1044 */         if (!isSync) {
/* 1045 */           Waitable waitable = new Waitable()
/*      */           {
/*      */             protected Object evaluate() {
/* 1048 */               PlayerConnection.this.disconnect("disconnect.spam");
/* 1049 */               return null;
/*      */             }
/*      */             
/* 1052 */           };
/* 1053 */           this.minecraftServer.processQueue.add(waitable);
/*      */           try
/*      */           {
/* 1056 */             waitable.get();
/*      */           } catch (InterruptedException localInterruptedException2) {
/* 1058 */             Thread.currentThread().interrupt();
/*      */           } catch (ExecutionException e) {
/* 1060 */             throw new RuntimeException(e);
/*      */           }
/*      */         } else {
/* 1063 */           disconnect("disconnect.spam");
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void chat(String s, boolean async)
/*      */   {
/* 1073 */     if ((s.isEmpty()) || (this.player.getChatFlags() == EntityHuman.EnumChatVisibility.HIDDEN)) {
/* 1074 */       return;
/*      */     }
/*      */     
/* 1077 */     if ((!async) && (s.startsWith("/"))) {
/* 1078 */       handleCommand(s);
/* 1079 */     } else if (this.player.getChatFlags() != EntityHuman.EnumChatVisibility.SYSTEM)
/*      */     {
/*      */ 
/* 1082 */       Player player = getPlayer();
/* 1083 */       AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(async, player, s, new LazyPlayerSet());
/* 1084 */       this.server.getPluginManager().callEvent(event);
/*      */       Waitable waitable;
/* 1086 */       if (PlayerChatEvent.getHandlerList().getRegisteredListeners().length != 0)
/*      */       {
/* 1088 */         final PlayerChatEvent queueEvent = new PlayerChatEvent(player, event.getMessage(), event.getFormat(), event.getRecipients());
/* 1089 */         queueEvent.setCancelled(event.isCancelled());
/* 1090 */         waitable = new Waitable()
/*      */         {
/*      */           protected Object evaluate() {
/* 1093 */             org.bukkit.Bukkit.getPluginManager().callEvent(queueEvent);
/*      */             
/* 1095 */             if (queueEvent.isCancelled()) {
/* 1096 */               return null;
/*      */             }
/*      */             
/* 1099 */             String message = String.format(queueEvent.getFormat(), new Object[] { queueEvent.getPlayer().getDisplayName(), queueEvent.getMessage() });
/* 1100 */             PlayerConnection.this.minecraftServer.console.sendMessage(message);
/* 1101 */             if (((LazyPlayerSet)queueEvent.getRecipients()).isLazy()) {
/* 1102 */               for (Object player : PlayerConnection.this.minecraftServer.getPlayerList().players) {
/* 1103 */                 ((EntityPlayer)player).sendMessage(CraftChatMessage.fromString(message));
/*      */               }
/*      */             } else {
/* 1106 */               for (Player player : queueEvent.getRecipients()) {
/* 1107 */                 player.sendMessage(message);
/*      */               }
/*      */             }
/* 1110 */             return null;
/*      */           } };
/* 1112 */         if (async) {
/* 1113 */           this.minecraftServer.processQueue.add(waitable);
/*      */         } else {
/* 1115 */           waitable.run();
/*      */         }
/*      */         try {
/* 1118 */           waitable.get();
/*      */         } catch (InterruptedException localInterruptedException) {
/* 1120 */           Thread.currentThread().interrupt();
/*      */         } catch (ExecutionException e) {
/* 1122 */           throw new RuntimeException("Exception processing chat event", e.getCause());
/*      */         }
/*      */       } else {
/* 1125 */         if (event.isCancelled()) {
/* 1126 */           return;
/*      */         }
/*      */         
/* 1129 */         s = String.format(event.getFormat(), new Object[] { event.getPlayer().getDisplayName(), event.getMessage() });
/* 1130 */         this.minecraftServer.console.sendMessage(s);
/* 1131 */         if (((LazyPlayerSet)event.getRecipients()).isLazy()) {
/* 1132 */           for (Object recipient : this.minecraftServer.getPlayerList().players) {
/* 1133 */             ((EntityPlayer)recipient).sendMessage(CraftChatMessage.fromString(s));
/*      */           }
/*      */         } else {
/* 1136 */           for (Player recipient : event.getRecipients()) {
/* 1137 */             recipient.sendMessage(s);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void handleCommand(String s)
/*      */   {
/* 1146 */     SpigotTimings.playerCommandTimer.startTiming();
/*      */     
/* 1148 */     if (SpigotConfig.logCommands) {
/* 1149 */       c.info(this.player.getName() + " issued server command: " + s);
/*      */     }
/* 1151 */     CraftPlayer player = getPlayer();
/*      */     
/* 1153 */     PlayerCommandPreprocessEvent event = new PlayerCommandPreprocessEvent(player, s, new LazyPlayerSet());
/* 1154 */     this.server.getPluginManager().callEvent(event);
/*      */     
/* 1156 */     if (event.isCancelled()) {
/* 1157 */       SpigotTimings.playerCommandTimer.stopTiming();
/* 1158 */       return;
/*      */     }
/*      */     try
/*      */     {
/* 1162 */       if (this.server.dispatchCommand(event.getPlayer(), event.getMessage().substring(1))) {
/* 1163 */         SpigotTimings.playerCommandTimer.stopTiming();
/* 1164 */         return;
/*      */       }
/*      */     } catch (CommandException ex) {
/* 1167 */       player.sendMessage(org.bukkit.ChatColor.RED + "An internal error occurred while attempting to perform this command");
/* 1168 */       java.util.logging.Logger.getLogger(PlayerConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
/* 1169 */       SpigotTimings.playerCommandTimer.stopTiming();
/* 1170 */       return;
/*      */     }
/* 1172 */     SpigotTimings.playerCommandTimer.stopTiming();
/*      */   }
/*      */   
/*      */ 
/*      */   public void a(PacketPlayInArmAnimation packetplayinarmanimation)
/*      */   {
/* 1178 */     if (this.player.dead) return;
/* 1179 */     PlayerConnectionUtils.ensureMainThread(packetplayinarmanimation, this, this.player.u());
/* 1180 */     this.player.resetIdleTimer();
/*      */     
/* 1182 */     float f1 = this.player.pitch;
/* 1183 */     float f2 = this.player.yaw;
/* 1184 */     double d0 = this.player.locX;
/* 1185 */     double d1 = this.player.locY + this.player.getHeadHeight();
/* 1186 */     double d2 = this.player.locZ;
/* 1187 */     Vec3D vec3d = new Vec3D(d0, d1, d2);
/*      */     
/* 1189 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 1190 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 1191 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 1192 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/* 1193 */     float f7 = f4 * f5;
/* 1194 */     float f8 = f3 * f5;
/* 1195 */     double d3 = this.player.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.CREATIVE ? 5.0D : 4.5D;
/* 1196 */     Vec3D vec3d1 = vec3d.add(f7 * d3, f6 * d3, f8 * d3);
/* 1197 */     MovingObjectPosition movingobjectposition = this.player.world.rayTrace(vec3d, vec3d1, false);
/*      */     
/* 1199 */     if ((movingobjectposition == null) || (movingobjectposition.type != MovingObjectPosition.EnumMovingObjectType.BLOCK)) {
/* 1200 */       CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_AIR, this.player.inventory.getItemInHand());
/*      */     }
/*      */     
/*      */ 
/* 1204 */     PlayerAnimationEvent event = new PlayerAnimationEvent(getPlayer());
/* 1205 */     this.server.getPluginManager().callEvent(event);
/*      */     
/* 1207 */     if (event.isCancelled()) { return;
/*      */     }
/* 1209 */     this.player.bw();
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInEntityAction packetplayinentityaction) {
/* 1213 */     PlayerConnectionUtils.ensureMainThread(packetplayinentityaction, this, this.player.u());
/*      */     
/* 1215 */     if (this.player.dead) return;
/* 1216 */     switch (packetplayinentityaction.b()) {
/*      */     case OPEN_INVENTORY: 
/*      */     case RIDING_JUMP: 
/* 1219 */       PlayerToggleSneakEvent event = new PlayerToggleSneakEvent(getPlayer(), packetplayinentityaction.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING);
/* 1220 */       this.server.getPluginManager().callEvent(event);
/*      */       
/* 1222 */       if (event.isCancelled()) {
/*      */         return;
/*      */       }
/*      */       break;
/*      */     case START_SPRINTING: 
/*      */     case STOP_SLEEPING: 
/* 1228 */       PlayerToggleSprintEvent e2 = new PlayerToggleSprintEvent(getPlayer(), packetplayinentityaction.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SPRINTING);
/* 1229 */       this.server.getPluginManager().callEvent(e2);
/*      */       
/* 1231 */       if (e2.isCancelled()) {
/*      */         return;
/*      */       }
/*      */       break;
/*      */     }
/*      */     
/* 1237 */     this.player.resetIdleTimer();
/* 1238 */     switch (SyntheticClass_1.b[packetplayinentityaction.b().ordinal()]) {
/*      */     case 1: 
/* 1240 */       this.player.setSneaking(true);
/* 1241 */       break;
/*      */     
/*      */     case 2: 
/* 1244 */       this.player.setSneaking(false);
/* 1245 */       break;
/*      */     
/*      */     case 3: 
/* 1248 */       this.player.setSprinting(true);
/* 1249 */       break;
/*      */     
/*      */     case 4: 
/* 1252 */       this.player.setSprinting(false);
/* 1253 */       break;
/*      */     
/*      */     case 5: 
/* 1256 */       this.player.a(false, true, true);
/*      */       
/* 1258 */       break;
/*      */     
/*      */     case 6: 
/* 1261 */       if ((this.player.vehicle instanceof EntityHorse)) {
/* 1262 */         ((EntityHorse)this.player.vehicle).v(packetplayinentityaction.c());
/*      */       }
/* 1264 */       break;
/*      */     
/*      */     case 7: 
/* 1267 */       if ((this.player.vehicle instanceof EntityHorse)) {
/* 1268 */         ((EntityHorse)this.player.vehicle).g(this.player);
/*      */       }
/* 1270 */       break;
/*      */     
/*      */     default: 
/* 1273 */       throw new IllegalArgumentException("Invalid client command!");
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInUseEntity packetplayinuseentity)
/*      */   {
/* 1279 */     if (this.player.dead) return;
/* 1280 */     PlayerConnectionUtils.ensureMainThread(packetplayinuseentity, this, this.player.u());
/* 1281 */     WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/* 1282 */     Entity entity = packetplayinuseentity.a(worldserver);
/*      */     
/* 1284 */     if ((entity == this.player) && (!this.player.isSpectator()))
/*      */     {
/* 1286 */       disconnect("Cannot interact with self!");
/* 1287 */       return;
/*      */     }
/*      */     
/*      */ 
/* 1291 */     this.player.resetIdleTimer();
/* 1292 */     if (entity != null) {
/* 1293 */       boolean flag = this.player.hasLineOfSight(entity);
/* 1294 */       double d0 = 36.0D;
/*      */       
/* 1296 */       if (!flag) {
/* 1297 */         d0 = 9.0D;
/*      */       }
/*      */       
/* 1300 */       if (this.player.h(entity) < d0) {
/* 1301 */         ItemStack itemInHand = this.player.inventory.getItemInHand();
/*      */         
/* 1303 */         if ((packetplayinuseentity.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT) || 
/* 1304 */           (packetplayinuseentity.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT))
/*      */         {
/* 1306 */           boolean triggerLeashUpdate = (itemInHand != null) && (itemInHand.getItem() == Items.LEAD) && ((entity instanceof EntityInsentient));
/* 1307 */           Item origItem = this.player.inventory.getItemInHand() == null ? null : this.player.inventory.getItemInHand().getItem();
/*      */           PlayerInteractEntityEvent event;
/* 1309 */           PlayerInteractEntityEvent event; if (packetplayinuseentity.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT) {
/* 1310 */             event = new PlayerInteractEntityEvent(getPlayer(), entity.getBukkitEntity());
/*      */           } else {
/* 1312 */             Vec3D target = packetplayinuseentity.b();
/* 1313 */             event = new org.bukkit.event.player.PlayerInteractAtEntityEvent(getPlayer(), entity.getBukkitEntity(), new org.bukkit.util.Vector(target.a, target.b, target.c));
/*      */           }
/* 1315 */           this.server.getPluginManager().callEvent(event);
/*      */           
/* 1317 */           if ((triggerLeashUpdate) && ((event.isCancelled()) || (this.player.inventory.getItemInHand() == null) || (this.player.inventory.getItemInHand().getItem() != Items.LEAD)))
/*      */           {
/* 1319 */             sendPacket(new PacketPlayOutAttachEntity(1, entity, ((EntityInsentient)entity).getLeashHolder()));
/*      */           }
/*      */           
/* 1322 */           if ((event.isCancelled()) || (this.player.inventory.getItemInHand() == null) || (this.player.inventory.getItemInHand().getItem() != origItem))
/*      */           {
/* 1324 */             sendPacket(new PacketPlayOutEntityMetadata(entity.getId(), entity.datawatcher, true));
/*      */           }
/*      */           
/* 1327 */           if (event.isCancelled()) {
/* 1328 */             return;
/*      */           }
/*      */         }
/*      */         
/* 1332 */         if (packetplayinuseentity.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT) {
/* 1333 */           this.player.u(entity);
/*      */           
/*      */ 
/* 1336 */           if ((itemInHand != null) && (itemInHand.count <= -1)) {
/* 1337 */             this.player.updateInventory(this.player.activeContainer);
/*      */           }
/*      */         }
/* 1340 */         else if (packetplayinuseentity.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT) {
/* 1341 */           entity.a(this.player, packetplayinuseentity.b());
/*      */           
/*      */ 
/* 1344 */           if ((itemInHand != null) && (itemInHand.count <= -1)) {
/* 1345 */             this.player.updateInventory(this.player.activeContainer);
/*      */           }
/*      */         }
/* 1348 */         else if (packetplayinuseentity.a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
/* 1349 */           if (((entity instanceof EntityItem)) || ((entity instanceof EntityExperienceOrb)) || ((entity instanceof EntityArrow)) || ((entity == this.player) && (!this.player.isSpectator()))) {
/* 1350 */             disconnect("Attempting to attack an invalid entity");
/* 1351 */             this.minecraftServer.warning("Player " + this.player.getName() + " tried to attack an invalid entity");
/* 1352 */             return;
/*      */           }
/*      */           
/* 1355 */           this.player.attack(entity);
/*      */           
/*      */ 
/* 1358 */           if ((itemInHand != null) && (itemInHand.count <= -1)) {
/* 1359 */             this.player.updateInventory(this.player.activeContainer);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void a(PacketPlayInClientCommand packetplayinclientcommand)
/*      */   {
/* 1369 */     PlayerConnectionUtils.ensureMainThread(packetplayinclientcommand, this, this.player.u());
/* 1370 */     this.player.resetIdleTimer();
/* 1371 */     PacketPlayInClientCommand.EnumClientCommand packetplayinclientcommand_enumclientcommand = packetplayinclientcommand.a();
/*      */     
/* 1373 */     switch (SyntheticClass_1.c[packetplayinclientcommand_enumclientcommand.ordinal()]) {
/*      */     case 1: 
/* 1375 */       if (this.player.viewingCredits)
/*      */       {
/* 1377 */         this.minecraftServer.getPlayerList().changeDimension(this.player, 0, PlayerTeleportEvent.TeleportCause.END_PORTAL);
/* 1378 */       } else if (this.player.u().getWorldData().isHardcore()) {
/* 1379 */         if ((this.minecraftServer.T()) && (this.player.getName().equals(this.minecraftServer.S()))) {
/* 1380 */           this.player.playerConnection.disconnect("You have died. Game over, man, it's game over!");
/* 1381 */           this.minecraftServer.aa();
/*      */         } else {
/* 1383 */           GameProfileBanEntry gameprofilebanentry = new GameProfileBanEntry(this.player.getProfile(), null, "(You just lost the game)", null, "Death in Hardcore");
/*      */           
/* 1385 */           this.minecraftServer.getPlayerList().getProfileBans().add(gameprofilebanentry);
/* 1386 */           this.player.playerConnection.disconnect("You have died. Game over, man, it's game over!");
/*      */         }
/*      */       } else {
/* 1389 */         if (this.player.getHealth() > 0.0F) {
/* 1390 */           return;
/*      */         }
/*      */         
/* 1393 */         this.player = this.minecraftServer.getPlayerList().moveToWorld(this.player, 0, false);
/*      */       }
/* 1395 */       break;
/*      */     
/*      */     case 2: 
/* 1398 */       this.player.getStatisticManager().a(this.player);
/* 1399 */       break;
/*      */     
/*      */     case 3: 
/* 1402 */       this.player.b(AchievementList.f);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInCloseWindow packetplayinclosewindow)
/*      */   {
/* 1408 */     if (this.player.dead) return;
/* 1409 */     PlayerConnectionUtils.ensureMainThread(packetplayinclosewindow, this, this.player.u());
/*      */     
/* 1411 */     CraftEventFactory.handleInventoryCloseEvent(this.player);
/*      */     
/* 1413 */     this.player.p();
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInWindowClick packetplayinwindowclick) {
/* 1417 */     if (this.player.dead) return;
/* 1418 */     PlayerConnectionUtils.ensureMainThread(packetplayinwindowclick, this, this.player.u());
/* 1419 */     this.player.resetIdleTimer();
/* 1420 */     if ((this.player.activeContainer.windowId == packetplayinwindowclick.a()) && (this.player.activeContainer.c(this.player))) {
/* 1421 */       boolean cancelled = this.player.isSpectator();
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
/* 1433 */       if ((packetplayinwindowclick.b() < -1) && (packetplayinwindowclick.b() != 64537)) {
/* 1434 */         return;
/*      */       }
/*      */       
/* 1437 */       InventoryView inventory = this.player.activeContainer.getBukkitView();
/* 1438 */       InventoryType.SlotType type = CraftInventoryView.getSlotType(inventory, packetplayinwindowclick.b());
/*      */       
/* 1440 */       InventoryClickEvent event = null;
/* 1441 */       ClickType click = ClickType.UNKNOWN;
/* 1442 */       InventoryAction action = InventoryAction.UNKNOWN;
/*      */       
/* 1444 */       ItemStack itemstack = null;
/*      */       
/* 1446 */       if (packetplayinwindowclick.b() == -1) {
/* 1447 */         type = InventoryType.SlotType.OUTSIDE;
/* 1448 */         click = packetplayinwindowclick.c() == 0 ? ClickType.WINDOW_BORDER_LEFT : ClickType.WINDOW_BORDER_RIGHT;
/* 1449 */         action = InventoryAction.NOTHING;
/* 1450 */       } else if (packetplayinwindowclick.f() == 0) {
/* 1451 */         if (packetplayinwindowclick.c() == 0) {
/* 1452 */           click = ClickType.LEFT;
/* 1453 */         } else if (packetplayinwindowclick.c() == 1) {
/* 1454 */           click = ClickType.RIGHT;
/*      */         }
/* 1456 */         if ((packetplayinwindowclick.c() == 0) || (packetplayinwindowclick.c() == 1)) {
/* 1457 */           action = InventoryAction.NOTHING;
/* 1458 */           if (packetplayinwindowclick.b() == 64537) {
/* 1459 */             if (this.player.inventory.getCarried() != null) {
/* 1460 */               action = packetplayinwindowclick.c() == 0 ? InventoryAction.DROP_ALL_CURSOR : InventoryAction.DROP_ONE_CURSOR;
/*      */             }
/*      */           } else {
/* 1463 */             Slot slot = this.player.activeContainer.getSlot(packetplayinwindowclick.b());
/* 1464 */             if (slot != null) {
/* 1465 */               ItemStack clickedItem = slot.getItem();
/* 1466 */               ItemStack cursor = this.player.inventory.getCarried();
/* 1467 */               if (clickedItem == null) {
/* 1468 */                 if (cursor != null) {
/* 1469 */                   action = packetplayinwindowclick.c() == 0 ? InventoryAction.PLACE_ALL : InventoryAction.PLACE_ONE;
/*      */                 }
/* 1471 */               } else if (slot.isAllowed(this.player)) {
/* 1472 */                 if (cursor == null) {
/* 1473 */                   action = packetplayinwindowclick.c() == 0 ? InventoryAction.PICKUP_ALL : InventoryAction.PICKUP_HALF;
/* 1474 */                 } else if (slot.isAllowed(cursor)) {
/* 1475 */                   if ((clickedItem.doMaterialsMatch(cursor)) && (ItemStack.equals(clickedItem, cursor))) {
/* 1476 */                     int toPlace = packetplayinwindowclick.c() == 0 ? cursor.count : 1;
/* 1477 */                     toPlace = Math.min(toPlace, clickedItem.getMaxStackSize() - clickedItem.count);
/* 1478 */                     toPlace = Math.min(toPlace, slot.inventory.getMaxStackSize() - clickedItem.count);
/* 1479 */                     if (toPlace == 1) {
/* 1480 */                       action = InventoryAction.PLACE_ONE;
/* 1481 */                     } else if (toPlace == cursor.count) {
/* 1482 */                       action = InventoryAction.PLACE_ALL;
/* 1483 */                     } else if (toPlace < 0) {
/* 1484 */                       action = toPlace != -1 ? InventoryAction.PICKUP_SOME : InventoryAction.PICKUP_ONE;
/* 1485 */                     } else if (toPlace != 0) {
/* 1486 */                       action = InventoryAction.PLACE_SOME;
/*      */                     }
/* 1488 */                   } else if (cursor.count <= slot.getMaxStackSize()) {
/* 1489 */                     action = InventoryAction.SWAP_WITH_CURSOR;
/*      */                   }
/* 1491 */                 } else if ((cursor.getItem() == clickedItem.getItem()) && ((!cursor.usesData()) || (cursor.getData() == clickedItem.getData())) && (ItemStack.equals(cursor, clickedItem)) && 
/* 1492 */                   (clickedItem.count >= 0) && 
/* 1493 */                   (clickedItem.count + cursor.count <= cursor.getMaxStackSize()))
/*      */                 {
/* 1495 */                   action = InventoryAction.PICKUP_ALL;
/*      */                 }
/*      */                 
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1503 */       else if (packetplayinwindowclick.f() == 1) {
/* 1504 */         if (packetplayinwindowclick.c() == 0) {
/* 1505 */           click = ClickType.SHIFT_LEFT;
/* 1506 */         } else if (packetplayinwindowclick.c() == 1) {
/* 1507 */           click = ClickType.SHIFT_RIGHT;
/*      */         }
/* 1509 */         if ((packetplayinwindowclick.c() == 0) || (packetplayinwindowclick.c() == 1)) {
/* 1510 */           if (packetplayinwindowclick.b() < 0) {
/* 1511 */             action = InventoryAction.NOTHING;
/*      */           } else {
/* 1513 */             Slot slot = this.player.activeContainer.getSlot(packetplayinwindowclick.b());
/* 1514 */             if ((slot != null) && (slot.isAllowed(this.player)) && (slot.hasItem())) {
/* 1515 */               action = InventoryAction.MOVE_TO_OTHER_INVENTORY;
/*      */             } else {
/* 1517 */               action = InventoryAction.NOTHING;
/*      */             }
/*      */           }
/*      */         }
/* 1521 */       } else if (packetplayinwindowclick.f() == 2) {
/* 1522 */         if ((packetplayinwindowclick.c() >= 0) && (packetplayinwindowclick.c() < 9)) {
/* 1523 */           click = ClickType.NUMBER_KEY;
/* 1524 */           Slot clickedSlot = this.player.activeContainer.getSlot(packetplayinwindowclick.b());
/* 1525 */           if (clickedSlot.isAllowed(this.player)) {
/* 1526 */             ItemStack hotbar = this.player.inventory.getItem(packetplayinwindowclick.c());
/* 1527 */             boolean canCleanSwap = (hotbar == null) || ((clickedSlot.inventory == this.player.inventory) && (clickedSlot.isAllowed(hotbar)));
/* 1528 */             if (clickedSlot.hasItem()) {
/* 1529 */               if (canCleanSwap) {
/* 1530 */                 action = InventoryAction.HOTBAR_SWAP;
/*      */               } else {
/* 1532 */                 int firstEmptySlot = this.player.inventory.getFirstEmptySlotIndex();
/* 1533 */                 if (firstEmptySlot > -1) {
/* 1534 */                   action = InventoryAction.HOTBAR_MOVE_AND_READD;
/*      */                 } else {
/* 1536 */                   action = InventoryAction.NOTHING;
/*      */                 }
/*      */               }
/* 1539 */             } else if ((!clickedSlot.hasItem()) && (hotbar != null) && (clickedSlot.isAllowed(hotbar))) {
/* 1540 */               action = InventoryAction.HOTBAR_SWAP;
/*      */             } else {
/* 1542 */               action = InventoryAction.NOTHING;
/*      */             }
/*      */           } else {
/* 1545 */             action = InventoryAction.NOTHING;
/*      */           }
/*      */           
/* 1548 */           event = new InventoryClickEvent(inventory, type, packetplayinwindowclick.b(), click, action, packetplayinwindowclick.c());
/*      */         }
/* 1550 */       } else if (packetplayinwindowclick.f() == 3) {
/* 1551 */         if (packetplayinwindowclick.c() == 2) {
/* 1552 */           click = ClickType.MIDDLE;
/* 1553 */           if (packetplayinwindowclick.b() == 64537) {
/* 1554 */             action = InventoryAction.NOTHING;
/*      */           } else {
/* 1556 */             Slot slot = this.player.activeContainer.getSlot(packetplayinwindowclick.b());
/* 1557 */             if ((slot != null) && (slot.hasItem()) && (this.player.abilities.canInstantlyBuild) && (this.player.inventory.getCarried() == null)) {
/* 1558 */               action = InventoryAction.CLONE_STACK;
/*      */             } else {
/* 1560 */               action = InventoryAction.NOTHING;
/*      */             }
/*      */           }
/*      */         } else {
/* 1564 */           click = ClickType.UNKNOWN;
/* 1565 */           action = InventoryAction.UNKNOWN;
/*      */         }
/* 1567 */       } else if (packetplayinwindowclick.f() == 4) {
/* 1568 */         if (packetplayinwindowclick.b() >= 0) {
/* 1569 */           if (packetplayinwindowclick.c() == 0) {
/* 1570 */             click = ClickType.DROP;
/* 1571 */             Slot slot = this.player.activeContainer.getSlot(packetplayinwindowclick.b());
/* 1572 */             if ((slot != null) && (slot.hasItem()) && (slot.isAllowed(this.player)) && (slot.getItem() != null) && (slot.getItem().getItem() != Item.getItemOf(Blocks.AIR))) {
/* 1573 */               action = InventoryAction.DROP_ONE_SLOT;
/*      */             } else {
/* 1575 */               action = InventoryAction.NOTHING;
/*      */             }
/* 1577 */           } else if (packetplayinwindowclick.c() == 1) {
/* 1578 */             click = ClickType.CONTROL_DROP;
/* 1579 */             Slot slot = this.player.activeContainer.getSlot(packetplayinwindowclick.b());
/* 1580 */             if ((slot != null) && (slot.hasItem()) && (slot.isAllowed(this.player)) && (slot.getItem() != null) && (slot.getItem().getItem() != Item.getItemOf(Blocks.AIR))) {
/* 1581 */               action = InventoryAction.DROP_ALL_SLOT;
/*      */             } else {
/* 1583 */               action = InventoryAction.NOTHING;
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 1588 */           click = ClickType.LEFT;
/* 1589 */           if (packetplayinwindowclick.c() == 1) {
/* 1590 */             click = ClickType.RIGHT;
/*      */           }
/* 1592 */           action = InventoryAction.NOTHING;
/*      */         }
/* 1594 */       } else if (packetplayinwindowclick.f() == 5) {
/* 1595 */         itemstack = this.player.activeContainer.clickItem(packetplayinwindowclick.b(), packetplayinwindowclick.c(), 5, this.player);
/* 1596 */       } else if (packetplayinwindowclick.f() == 6) {
/* 1597 */         click = ClickType.DOUBLE_CLICK;
/* 1598 */         action = InventoryAction.NOTHING;
/* 1599 */         if ((packetplayinwindowclick.b() >= 0) && (this.player.inventory.getCarried() != null)) {
/* 1600 */           ItemStack cursor = this.player.inventory.getCarried();
/* 1601 */           action = InventoryAction.NOTHING;
/*      */           
/* 1603 */           if ((inventory.getTopInventory().contains(org.bukkit.Material.getMaterial(Item.getId(cursor.getItem())))) || (inventory.getBottomInventory().contains(org.bukkit.Material.getMaterial(Item.getId(cursor.getItem()))))) {
/* 1604 */             action = InventoryAction.COLLECT_TO_CURSOR;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1610 */       if (packetplayinwindowclick.f() != 5) {
/* 1611 */         if (click == ClickType.NUMBER_KEY) {
/* 1612 */           event = new InventoryClickEvent(inventory, type, packetplayinwindowclick.b(), click, action, packetplayinwindowclick.c());
/*      */         } else {
/* 1614 */           event = new InventoryClickEvent(inventory, type, packetplayinwindowclick.b(), click, action);
/*      */         }
/*      */         
/* 1617 */         Inventory top = inventory.getTopInventory();
/* 1618 */         if ((packetplayinwindowclick.b() == 0) && ((top instanceof CraftingInventory))) {
/* 1619 */           Recipe recipe = ((CraftingInventory)top).getRecipe();
/* 1620 */           if (recipe != null) {
/* 1621 */             if (click == ClickType.NUMBER_KEY) {
/* 1622 */               event = new CraftItemEvent(recipe, inventory, type, packetplayinwindowclick.b(), click, action, packetplayinwindowclick.c());
/*      */             } else {
/* 1624 */               event = new CraftItemEvent(recipe, inventory, type, packetplayinwindowclick.b(), click, action);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1629 */         event.setCancelled(cancelled);
/* 1630 */         this.server.getPluginManager().callEvent(event);
/*      */         
/* 1632 */         switch (event.getResult()) {
/*      */         case DEFAULT: 
/*      */         case DENY: 
/* 1635 */           itemstack = this.player.activeContainer.clickItem(packetplayinwindowclick.b(), packetplayinwindowclick.c(), packetplayinwindowclick.f(), this.player);
/* 1636 */           break;
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
/*      */         case ALLOW: 
/* 1649 */           switch (action)
/*      */           {
/*      */           case COLLECT_TO_CURSOR: 
/*      */           case PICKUP_SOME: 
/*      */           case PLACE_ALL: 
/*      */           case PLACE_ONE: 
/*      */           case SWAP_WITH_CURSOR: 
/*      */           case UNKNOWN: 
/* 1657 */             this.player.updateInventory(this.player.activeContainer);
/* 1658 */             break;
/*      */           
/*      */           case DROP_ALL_CURSOR: 
/*      */           case DROP_ALL_SLOT: 
/*      */           case DROP_ONE_CURSOR: 
/*      */           case DROP_ONE_SLOT: 
/*      */           case HOTBAR_MOVE_AND_READD: 
/*      */           case HOTBAR_SWAP: 
/*      */           case MOVE_TO_OTHER_INVENTORY: 
/* 1667 */             this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.player.inventory.getCarried()));
/* 1668 */             this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(this.player.activeContainer.windowId, packetplayinwindowclick.b(), this.player.activeContainer.getSlot(packetplayinwindowclick.b()).getItem()));
/* 1669 */             break;
/*      */           
/*      */           case PICKUP_HALF: 
/*      */           case PICKUP_ONE: 
/* 1673 */             this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(this.player.activeContainer.windowId, packetplayinwindowclick.b(), this.player.activeContainer.getSlot(packetplayinwindowclick.b()).getItem()));
/* 1674 */             break;
/*      */           
/*      */           case NOTHING: 
/*      */           case PICKUP_ALL: 
/*      */           case PLACE_SOME: 
/* 1679 */             this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.player.inventory.getCarried()));
/* 1680 */             break;
/*      */           }
/*      */           
/*      */           
/*      */ 
/* 1685 */           return;
/*      */         }
/*      */         
/* 1688 */         if ((event instanceof CraftItemEvent))
/*      */         {
/*      */ 
/* 1691 */           this.player.updateInventory(this.player.activeContainer);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1696 */       if (ItemStack.matches(packetplayinwindowclick.e(), itemstack)) {
/* 1697 */         this.player.playerConnection.sendPacket(new PacketPlayOutTransaction(packetplayinwindowclick.a(), packetplayinwindowclick.d(), true));
/* 1698 */         this.player.g = true;
/* 1699 */         this.player.activeContainer.b();
/* 1700 */         this.player.broadcastCarriedItem();
/* 1701 */         this.player.g = false;
/*      */       } else {
/* 1703 */         this.n.a(this.player.activeContainer.windowId, Short.valueOf(packetplayinwindowclick.d()));
/* 1704 */         this.player.playerConnection.sendPacket(new PacketPlayOutTransaction(packetplayinwindowclick.a(), packetplayinwindowclick.d(), false));
/* 1705 */         this.player.activeContainer.a(this.player, false);
/* 1706 */         ArrayList arraylist1 = Lists.newArrayList();
/*      */         
/* 1708 */         for (int j = 0; j < this.player.activeContainer.c.size(); j++) {
/* 1709 */           arraylist1.add(((Slot)this.player.activeContainer.c.get(j)).getItem());
/*      */         }
/*      */         
/* 1712 */         this.player.a(this.player.activeContainer, arraylist1);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void a(PacketPlayInEnchantItem packetplayinenchantitem)
/*      */   {
/* 1720 */     PlayerConnectionUtils.ensureMainThread(packetplayinenchantitem, this, this.player.u());
/* 1721 */     this.player.resetIdleTimer();
/* 1722 */     if ((this.player.activeContainer.windowId == packetplayinenchantitem.a()) && (this.player.activeContainer.c(this.player)) && (!this.player.isSpectator())) {
/* 1723 */       this.player.activeContainer.a(this.player, packetplayinenchantitem.b());
/* 1724 */       this.player.activeContainer.b();
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInSetCreativeSlot packetplayinsetcreativeslot)
/*      */   {
/* 1730 */     PlayerConnectionUtils.ensureMainThread(packetplayinsetcreativeslot, this, this.player.u());
/* 1731 */     if (this.player.playerInteractManager.isCreative()) {
/* 1732 */       boolean flag = packetplayinsetcreativeslot.a() < 0;
/* 1733 */       ItemStack itemstack = packetplayinsetcreativeslot.getItemStack();
/*      */       
/* 1735 */       if ((itemstack != null) && (itemstack.hasTag()) && (itemstack.getTag().hasKeyOfType("BlockEntityTag", 10))) {
/* 1736 */         NBTTagCompound nbttagcompound = itemstack.getTag().getCompound("BlockEntityTag");
/*      */         
/* 1738 */         if ((nbttagcompound.hasKey("x")) && (nbttagcompound.hasKey("y")) && (nbttagcompound.hasKey("z"))) {
/* 1739 */           BlockPosition blockposition = new BlockPosition(nbttagcompound.getInt("x"), nbttagcompound.getInt("y"), nbttagcompound.getInt("z"));
/* 1740 */           TileEntity tileentity = this.player.world.getTileEntity(blockposition);
/*      */           
/* 1742 */           if (tileentity != null) {
/* 1743 */             NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*      */             
/* 1745 */             tileentity.b(nbttagcompound1);
/* 1746 */             nbttagcompound1.remove("x");
/* 1747 */             nbttagcompound1.remove("y");
/* 1748 */             nbttagcompound1.remove("z");
/* 1749 */             itemstack.a("BlockEntityTag", nbttagcompound1);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1754 */       boolean flag1 = (packetplayinsetcreativeslot.a() >= 1) && (packetplayinsetcreativeslot.a() < 36 + PlayerInventory.getHotbarSize());
/*      */       
/* 1756 */       boolean flag2 = (itemstack == null) || ((itemstack.getItem() != null) && ((!invalidItems.contains(Integer.valueOf(Item.getId(itemstack.getItem())))) || (!SpigotConfig.filterCreativeItems)));
/* 1757 */       boolean flag3 = (itemstack == null) || ((itemstack.getData() >= 0) && (itemstack.count <= 64) && (itemstack.count > 0));
/*      */       
/* 1759 */       if ((flag) || ((flag1) && (!ItemStack.matches(this.player.defaultContainer.getSlot(packetplayinsetcreativeslot.a()).getItem(), packetplayinsetcreativeslot.getItemStack()))))
/*      */       {
/* 1761 */         HumanEntity player = this.player.getBukkitEntity();
/* 1762 */         InventoryView inventory = new CraftInventoryView(player, player.getInventory(), this.player.defaultContainer);
/* 1763 */         org.bukkit.inventory.ItemStack item = CraftItemStack.asBukkitCopy(packetplayinsetcreativeslot.getItemStack());
/*      */         
/* 1765 */         InventoryType.SlotType type = InventoryType.SlotType.QUICKBAR;
/* 1766 */         if (flag) {
/* 1767 */           type = InventoryType.SlotType.OUTSIDE;
/* 1768 */         } else if (packetplayinsetcreativeslot.a() < 36) {
/* 1769 */           if ((packetplayinsetcreativeslot.a() >= 5) && (packetplayinsetcreativeslot.a() < 9)) {
/* 1770 */             type = InventoryType.SlotType.ARMOR;
/*      */           } else {
/* 1772 */             type = InventoryType.SlotType.CONTAINER;
/*      */           }
/*      */         }
/* 1775 */         InventoryCreativeEvent event = new InventoryCreativeEvent(inventory, type, flag ? 64537 : packetplayinsetcreativeslot.a(), item);
/* 1776 */         this.server.getPluginManager().callEvent(event);
/*      */         
/* 1778 */         itemstack = CraftItemStack.asNMSCopy(event.getCursor());
/*      */         
/* 1780 */         switch (event.getResult())
/*      */         {
/*      */         case DENY: 
/* 1783 */           flag2 = flag3 = 1;
/* 1784 */           break;
/*      */         case DEFAULT: 
/*      */           break;
/*      */         
/*      */         case ALLOW: 
/* 1789 */           if (packetplayinsetcreativeslot.a() >= 0) {
/* 1790 */             this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(this.player.defaultContainer.windowId, packetplayinsetcreativeslot.a(), this.player.defaultContainer.getSlot(packetplayinsetcreativeslot.a()).getItem()));
/* 1791 */             this.player.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, null));
/*      */           }
/* 1793 */           return;
/*      */         }
/*      */         
/*      */       }
/*      */       
/* 1798 */       if ((flag1) && (flag2) && (flag3)) {
/* 1799 */         if (itemstack == null) {
/* 1800 */           this.player.defaultContainer.setItem(packetplayinsetcreativeslot.a(), null);
/*      */         } else {
/* 1802 */           this.player.defaultContainer.setItem(packetplayinsetcreativeslot.a(), itemstack);
/*      */         }
/*      */         
/* 1805 */         this.player.defaultContainer.a(this.player, true);
/* 1806 */       } else if ((flag) && (flag2) && (flag3) && (this.m < 200)) {
/* 1807 */         this.m += 20;
/* 1808 */         EntityItem entityitem = this.player.drop(itemstack, true);
/*      */         
/* 1810 */         if (entityitem != null) {
/* 1811 */           entityitem.j();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInTransaction packetplayintransaction)
/*      */   {
/* 1819 */     if (this.player.dead) return;
/* 1820 */     PlayerConnectionUtils.ensureMainThread(packetplayintransaction, this, this.player.u());
/* 1821 */     Short oshort = (Short)this.n.get(this.player.activeContainer.windowId);
/*      */     
/* 1823 */     if ((oshort != null) && (packetplayintransaction.b() == oshort.shortValue()) && (this.player.activeContainer.windowId == packetplayintransaction.a()) && (!this.player.activeContainer.c(this.player)) && (!this.player.isSpectator())) {
/* 1824 */       this.player.activeContainer.a(this.player, true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInUpdateSign packetplayinupdatesign)
/*      */   {
/* 1830 */     if (this.player.dead) return;
/* 1831 */     PlayerConnectionUtils.ensureMainThread(packetplayinupdatesign, this, this.player.u());
/* 1832 */     this.player.resetIdleTimer();
/* 1833 */     WorldServer worldserver = this.minecraftServer.getWorldServer(this.player.dimension);
/* 1834 */     BlockPosition blockposition = packetplayinupdatesign.a();
/*      */     
/* 1836 */     if (worldserver.isLoaded(blockposition)) {
/* 1837 */       TileEntity tileentity = worldserver.getTileEntity(blockposition);
/*      */       
/* 1839 */       if (!(tileentity instanceof TileEntitySign)) {
/* 1840 */         return;
/*      */       }
/*      */       
/* 1843 */       TileEntitySign tileentitysign = (TileEntitySign)tileentity;
/*      */       
/* 1845 */       if ((!tileentitysign.b()) || (tileentitysign.c() != this.player)) {
/* 1846 */         this.minecraftServer.warning("Player " + this.player.getName() + " just tried to change non-editable sign");
/* 1847 */         sendPacket(new PacketPlayOutUpdateSign(tileentity.world, packetplayinupdatesign.a(), tileentitysign.lines));
/* 1848 */         return;
/*      */       }
/*      */       
/* 1851 */       IChatBaseComponent[] aichatbasecomponent = packetplayinupdatesign.b();
/*      */       
/*      */ 
/* 1854 */       Player player = this.server.getPlayer(this.player);
/* 1855 */       int x = packetplayinupdatesign.a().getX();
/* 1856 */       int y = packetplayinupdatesign.a().getY();
/* 1857 */       int z = packetplayinupdatesign.a().getZ();
/* 1858 */       String[] lines = new String[4];
/*      */       
/* 1860 */       for (int i = 0; i < aichatbasecomponent.length; i++) {
/* 1861 */         lines[i] = EnumChatFormat.a(aichatbasecomponent[i].c());
/*      */       }
/* 1863 */       SignChangeEvent event = new SignChangeEvent((org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock)player.getWorld().getBlockAt(x, y, z), this.server.getPlayer(this.player), lines);
/* 1864 */       this.server.getPluginManager().callEvent(event);
/*      */       
/* 1866 */       if (!event.isCancelled()) {
/* 1867 */         System.arraycopy(CraftSign.sanitizeLines(event.getLines()), 0, tileentitysign.lines, 0, 4);
/* 1868 */         tileentitysign.isEditable = false;
/*      */       }
/*      */       
/*      */ 
/* 1872 */       tileentitysign.update();
/* 1873 */       worldserver.notify(blockposition);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInKeepAlive packetplayinkeepalive)
/*      */   {
/* 1879 */     if (packetplayinkeepalive.a() == this.i) {
/* 1880 */       int i = (int)(d() - this.j);
/*      */       
/* 1882 */       this.player.ping = ((this.player.ping * 3 + i) / 4);
/*      */     }
/*      */   }
/*      */   
/*      */   private long d()
/*      */   {
/* 1888 */     return System.nanoTime() / 1000000L;
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInAbilities packetplayinabilities) {
/* 1892 */     PlayerConnectionUtils.ensureMainThread(packetplayinabilities, this, this.player.u());
/*      */     
/* 1894 */     if ((this.player.abilities.canFly) && (this.player.abilities.isFlying != packetplayinabilities.isFlying())) {
/* 1895 */       PlayerToggleFlightEvent event = new PlayerToggleFlightEvent(this.server.getPlayer(this.player), packetplayinabilities.isFlying());
/* 1896 */       this.server.getPluginManager().callEvent(event);
/* 1897 */       if (!event.isCancelled()) {
/* 1898 */         this.player.abilities.isFlying = packetplayinabilities.isFlying();
/*      */       } else {
/* 1900 */         this.player.updateAbilities();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInTabComplete packetplayintabcomplete)
/*      */   {
/* 1907 */     PlayerConnectionUtils.ensureMainThread(packetplayintabcomplete, this, this.player.u());
/*      */     
/* 1909 */     if ((chatSpamField.addAndGet(this, 10) > 500) && (!this.minecraftServer.getPlayerList().isOp(this.player.getProfile()))) {
/* 1910 */       disconnect("disconnect.spam");
/* 1911 */       return;
/*      */     }
/*      */     
/* 1914 */     ArrayList arraylist = Lists.newArrayList();
/* 1915 */     Iterator iterator = this.minecraftServer.tabCompleteCommand(this.player, packetplayintabcomplete.a(), packetplayintabcomplete.b()).iterator();
/*      */     
/* 1917 */     while (iterator.hasNext()) {
/* 1918 */       String s = (String)iterator.next();
/*      */       
/* 1920 */       arraylist.add(s);
/*      */     }
/*      */     
/* 1923 */     this.player.playerConnection.sendPacket(new PacketPlayOutTabComplete((String[])arraylist.toArray(new String[arraylist.size()])));
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInSettings packetplayinsettings) {
/* 1927 */     PlayerConnectionUtils.ensureMainThread(packetplayinsettings, this, this.player.u());
/* 1928 */     this.player.a(packetplayinsettings);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public void a(PacketPlayInCustomPayload packetplayincustompayload)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: aload_0
/*      */     //   2: aload_0
/*      */     //   3: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   6: invokevirtual 428	net/minecraft/server/v1_8_R3/EntityPlayer:u	()Lnet/minecraft/server/v1_8_R3/WorldServer;
/*      */     //   9: invokestatic 434	net/minecraft/server/v1_8_R3/PlayerConnectionUtils:ensureMainThread	(Lnet/minecraft/server/v1_8_R3/Packet;Lnet/minecraft/server/v1_8_R3/PacketListener;Lnet/minecraft/server/v1_8_R3/IAsyncTaskHandler;)V
/*      */     //   12: ldc_w 2614
/*      */     //   15: aload_1
/*      */     //   16: invokevirtual 2617	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:a	()Ljava/lang/String;
/*      */     //   19: invokevirtual 723	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   22: ifeq +256 -> 278
/*      */     //   25: new 2619	net/minecraft/server/v1_8_R3/PacketDataSerializer
/*      */     //   28: dup
/*      */     //   29: aload_1
/*      */     //   30: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   33: invokestatic 2628	io/netty/buffer/Unpooled:wrappedBuffer	(Lio/netty/buffer/ByteBuf;)Lio/netty/buffer/ByteBuf;
/*      */     //   36: invokespecial 2631	net/minecraft/server/v1_8_R3/PacketDataSerializer:<init>	(Lio/netty/buffer/ByteBuf;)V
/*      */     //   39: astore_2
/*      */     //   40: aload_2
/*      */     //   41: invokevirtual 2633	net/minecraft/server/v1_8_R3/PacketDataSerializer:i	()Lnet/minecraft/server/v1_8_R3/ItemStack;
/*      */     //   44: astore_3
/*      */     //   45: aload_3
/*      */     //   46: ifnonnull +27 -> 73
/*      */     //   49: aload_2
/*      */     //   50: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   53: pop
/*      */     //   54: aload_1
/*      */     //   55: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   58: invokevirtual 2639	net/minecraft/server/v1_8_R3/PacketDataSerializer:refCnt	()I
/*      */     //   61: ifle +11 -> 72
/*      */     //   64: aload_1
/*      */     //   65: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   68: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   71: pop
/*      */     //   72: return
/*      */     //   73: aload_3
/*      */     //   74: invokevirtual 2351	net/minecraft/server/v1_8_R3/ItemStack:getTag	()Lnet/minecraft/server/v1_8_R3/NBTTagCompound;
/*      */     //   77: invokestatic 2644	net/minecraft/server/v1_8_R3/ItemBookAndQuill:b	(Lnet/minecraft/server/v1_8_R3/NBTTagCompound;)Z
/*      */     //   80: ifne +14 -> 94
/*      */     //   83: new 2646	java/io/IOException
/*      */     //   86: dup
/*      */     //   87: ldc_w 2648
/*      */     //   90: invokespecial 2649	java/io/IOException:<init>	(Ljava/lang/String;)V
/*      */     //   93: athrow
/*      */     //   94: aload_0
/*      */     //   95: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   98: getfield 986	net/minecraft/server/v1_8_R3/EntityPlayer:inventory	Lnet/minecraft/server/v1_8_R3/PlayerInventory;
/*      */     //   101: invokevirtual 992	net/minecraft/server/v1_8_R3/PlayerInventory:getItemInHand	()Lnet/minecraft/server/v1_8_R3/ItemStack;
/*      */     //   104: astore 4
/*      */     //   106: aload 4
/*      */     //   108: ifnull +146 -> 254
/*      */     //   111: aload_3
/*      */     //   112: invokevirtual 1171	net/minecraft/server/v1_8_R3/ItemStack:getItem	()Lnet/minecraft/server/v1_8_R3/Item;
/*      */     //   115: getstatic 2652	net/minecraft/server/v1_8_R3/Items:WRITABLE_BOOK	Lnet/minecraft/server/v1_8_R3/Item;
/*      */     //   118: if_acmpne +56 -> 174
/*      */     //   121: aload_3
/*      */     //   122: invokevirtual 1171	net/minecraft/server/v1_8_R3/ItemStack:getItem	()Lnet/minecraft/server/v1_8_R3/Item;
/*      */     //   125: aload 4
/*      */     //   127: invokevirtual 1171	net/minecraft/server/v1_8_R3/ItemStack:getItem	()Lnet/minecraft/server/v1_8_R3/Item;
/*      */     //   130: if_acmpne +44 -> 174
/*      */     //   133: new 1077	net/minecraft/server/v1_8_R3/ItemStack
/*      */     //   136: dup
/*      */     //   137: getstatic 2652	net/minecraft/server/v1_8_R3/Items:WRITABLE_BOOK	Lnet/minecraft/server/v1_8_R3/Item;
/*      */     //   140: invokespecial 2655	net/minecraft/server/v1_8_R3/ItemStack:<init>	(Lnet/minecraft/server/v1_8_R3/Item;)V
/*      */     //   143: astore 4
/*      */     //   145: aload 4
/*      */     //   147: ldc_w 2657
/*      */     //   150: aload_3
/*      */     //   151: invokevirtual 2351	net/minecraft/server/v1_8_R3/ItemStack:getTag	()Lnet/minecraft/server/v1_8_R3/NBTTagCompound;
/*      */     //   154: ldc_w 2657
/*      */     //   157: bipush 8
/*      */     //   159: invokevirtual 2661	net/minecraft/server/v1_8_R3/NBTTagCompound:getList	(Ljava/lang/String;I)Lnet/minecraft/server/v1_8_R3/NBTTagList;
/*      */     //   162: invokevirtual 2387	net/minecraft/server/v1_8_R3/ItemStack:a	(Ljava/lang/String;Lnet/minecraft/server/v1_8_R3/NBTBase;)V
/*      */     //   165: aload_0
/*      */     //   166: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   169: aload 4
/*      */     //   171: invokestatic 2665	org/bukkit/craftbukkit/v1_8_R3/event/CraftEventFactory:handleEditBookEvent	(Lnet/minecraft/server/v1_8_R3/EntityPlayer;Lnet/minecraft/server/v1_8_R3/ItemStack;)V
/*      */     //   174: aload_2
/*      */     //   175: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   178: pop
/*      */     //   179: aload_1
/*      */     //   180: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   183: invokevirtual 2639	net/minecraft/server/v1_8_R3/PacketDataSerializer:refCnt	()I
/*      */     //   186: ifle +11 -> 197
/*      */     //   189: aload_1
/*      */     //   190: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   193: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   196: pop
/*      */     //   197: return
/*      */     //   198: astore 5
/*      */     //   200: getstatic 158	net/minecraft/server/v1_8_R3/PlayerConnection:c	Lorg/apache/logging/log4j/Logger;
/*      */     //   203: ldc_w 2667
/*      */     //   206: aload 5
/*      */     //   208: invokeinterface 2670 3 0
/*      */     //   213: aload_0
/*      */     //   214: ldc_w 2672
/*      */     //   217: invokevirtual 325	net/minecraft/server/v1_8_R3/PlayerConnection:disconnect	(Ljava/lang/String;)V
/*      */     //   220: aload_2
/*      */     //   221: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   224: pop
/*      */     //   225: aload_1
/*      */     //   226: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   229: invokevirtual 2639	net/minecraft/server/v1_8_R3/PacketDataSerializer:refCnt	()I
/*      */     //   232: ifle +11 -> 243
/*      */     //   235: aload_1
/*      */     //   236: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   239: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   242: pop
/*      */     //   243: return
/*      */     //   244: astore 6
/*      */     //   246: aload_2
/*      */     //   247: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   250: pop
/*      */     //   251: aload 6
/*      */     //   253: athrow
/*      */     //   254: aload_2
/*      */     //   255: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   258: pop
/*      */     //   259: aload_1
/*      */     //   260: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   263: invokevirtual 2639	net/minecraft/server/v1_8_R3/PacketDataSerializer:refCnt	()I
/*      */     //   266: ifle +11 -> 277
/*      */     //   269: aload_1
/*      */     //   270: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   273: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   276: pop
/*      */     //   277: return
/*      */     //   278: ldc_w 2674
/*      */     //   281: aload_1
/*      */     //   282: invokevirtual 2617	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:a	()Ljava/lang/String;
/*      */     //   285: invokevirtual 723	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   288: ifeq +310 -> 598
/*      */     //   291: new 2619	net/minecraft/server/v1_8_R3/PacketDataSerializer
/*      */     //   294: dup
/*      */     //   295: aload_1
/*      */     //   296: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   299: invokestatic 2628	io/netty/buffer/Unpooled:wrappedBuffer	(Lio/netty/buffer/ByteBuf;)Lio/netty/buffer/ByteBuf;
/*      */     //   302: invokespecial 2631	net/minecraft/server/v1_8_R3/PacketDataSerializer:<init>	(Lio/netty/buffer/ByteBuf;)V
/*      */     //   305: astore_2
/*      */     //   306: aload_2
/*      */     //   307: invokevirtual 2633	net/minecraft/server/v1_8_R3/PacketDataSerializer:i	()Lnet/minecraft/server/v1_8_R3/ItemStack;
/*      */     //   310: astore_3
/*      */     //   311: aload_3
/*      */     //   312: ifnonnull +27 -> 339
/*      */     //   315: aload_2
/*      */     //   316: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   319: pop
/*      */     //   320: aload_1
/*      */     //   321: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   324: invokevirtual 2639	net/minecraft/server/v1_8_R3/PacketDataSerializer:refCnt	()I
/*      */     //   327: ifle +11 -> 338
/*      */     //   330: aload_1
/*      */     //   331: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   334: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   337: pop
/*      */     //   338: return
/*      */     //   339: aload_3
/*      */     //   340: invokevirtual 2351	net/minecraft/server/v1_8_R3/ItemStack:getTag	()Lnet/minecraft/server/v1_8_R3/NBTTagCompound;
/*      */     //   343: invokestatic 2677	net/minecraft/server/v1_8_R3/ItemWrittenBook:b	(Lnet/minecraft/server/v1_8_R3/NBTTagCompound;)Z
/*      */     //   346: ifne +14 -> 360
/*      */     //   349: new 2646	java/io/IOException
/*      */     //   352: dup
/*      */     //   353: ldc_w 2648
/*      */     //   356: invokespecial 2649	java/io/IOException:<init>	(Ljava/lang/String;)V
/*      */     //   359: athrow
/*      */     //   360: aload_0
/*      */     //   361: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   364: getfield 986	net/minecraft/server/v1_8_R3/EntityPlayer:inventory	Lnet/minecraft/server/v1_8_R3/PlayerInventory;
/*      */     //   367: invokevirtual 992	net/minecraft/server/v1_8_R3/PlayerInventory:getItemInHand	()Lnet/minecraft/server/v1_8_R3/ItemStack;
/*      */     //   370: astore 4
/*      */     //   372: aload 4
/*      */     //   374: ifnull +200 -> 574
/*      */     //   377: aload_3
/*      */     //   378: invokevirtual 1171	net/minecraft/server/v1_8_R3/ItemStack:getItem	()Lnet/minecraft/server/v1_8_R3/Item;
/*      */     //   381: getstatic 2680	net/minecraft/server/v1_8_R3/Items:WRITTEN_BOOK	Lnet/minecraft/server/v1_8_R3/Item;
/*      */     //   384: if_acmpne +110 -> 494
/*      */     //   387: aload 4
/*      */     //   389: invokevirtual 1171	net/minecraft/server/v1_8_R3/ItemStack:getItem	()Lnet/minecraft/server/v1_8_R3/Item;
/*      */     //   392: getstatic 2652	net/minecraft/server/v1_8_R3/Items:WRITABLE_BOOK	Lnet/minecraft/server/v1_8_R3/Item;
/*      */     //   395: if_acmpne +99 -> 494
/*      */     //   398: new 1077	net/minecraft/server/v1_8_R3/ItemStack
/*      */     //   401: dup
/*      */     //   402: getstatic 2680	net/minecraft/server/v1_8_R3/Items:WRITTEN_BOOK	Lnet/minecraft/server/v1_8_R3/Item;
/*      */     //   405: invokespecial 2655	net/minecraft/server/v1_8_R3/ItemStack:<init>	(Lnet/minecraft/server/v1_8_R3/Item;)V
/*      */     //   408: astore 4
/*      */     //   410: aload 4
/*      */     //   412: ldc_w 2682
/*      */     //   415: new 2684	net/minecraft/server/v1_8_R3/NBTTagString
/*      */     //   418: dup
/*      */     //   419: aload_0
/*      */     //   420: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   423: invokevirtual 344	net/minecraft/server/v1_8_R3/EntityPlayer:getName	()Ljava/lang/String;
/*      */     //   426: invokespecial 2685	net/minecraft/server/v1_8_R3/NBTTagString:<init>	(Ljava/lang/String;)V
/*      */     //   429: invokevirtual 2387	net/minecraft/server/v1_8_R3/ItemStack:a	(Ljava/lang/String;Lnet/minecraft/server/v1_8_R3/NBTBase;)V
/*      */     //   432: aload 4
/*      */     //   434: ldc_w 2687
/*      */     //   437: new 2684	net/minecraft/server/v1_8_R3/NBTTagString
/*      */     //   440: dup
/*      */     //   441: aload_3
/*      */     //   442: invokevirtual 2351	net/minecraft/server/v1_8_R3/ItemStack:getTag	()Lnet/minecraft/server/v1_8_R3/NBTTagCompound;
/*      */     //   445: ldc_w 2687
/*      */     //   448: invokevirtual 2690	net/minecraft/server/v1_8_R3/NBTTagCompound:getString	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   451: invokespecial 2685	net/minecraft/server/v1_8_R3/NBTTagString:<init>	(Ljava/lang/String;)V
/*      */     //   454: invokevirtual 2387	net/minecraft/server/v1_8_R3/ItemStack:a	(Ljava/lang/String;Lnet/minecraft/server/v1_8_R3/NBTBase;)V
/*      */     //   457: aload 4
/*      */     //   459: ldc_w 2657
/*      */     //   462: aload_3
/*      */     //   463: invokevirtual 2351	net/minecraft/server/v1_8_R3/ItemStack:getTag	()Lnet/minecraft/server/v1_8_R3/NBTTagCompound;
/*      */     //   466: ldc_w 2657
/*      */     //   469: bipush 8
/*      */     //   471: invokevirtual 2661	net/minecraft/server/v1_8_R3/NBTTagCompound:getList	(Ljava/lang/String;I)Lnet/minecraft/server/v1_8_R3/NBTTagList;
/*      */     //   474: invokevirtual 2387	net/minecraft/server/v1_8_R3/ItemStack:a	(Ljava/lang/String;Lnet/minecraft/server/v1_8_R3/NBTBase;)V
/*      */     //   477: aload 4
/*      */     //   479: getstatic 2680	net/minecraft/server/v1_8_R3/Items:WRITTEN_BOOK	Lnet/minecraft/server/v1_8_R3/Item;
/*      */     //   482: invokevirtual 2692	net/minecraft/server/v1_8_R3/ItemStack:setItem	(Lnet/minecraft/server/v1_8_R3/Item;)V
/*      */     //   485: aload_0
/*      */     //   486: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   489: aload 4
/*      */     //   491: invokestatic 2665	org/bukkit/craftbukkit/v1_8_R3/event/CraftEventFactory:handleEditBookEvent	(Lnet/minecraft/server/v1_8_R3/EntityPlayer;Lnet/minecraft/server/v1_8_R3/ItemStack;)V
/*      */     //   494: aload_2
/*      */     //   495: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   498: pop
/*      */     //   499: aload_1
/*      */     //   500: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   503: invokevirtual 2639	net/minecraft/server/v1_8_R3/PacketDataSerializer:refCnt	()I
/*      */     //   506: ifle +11 -> 517
/*      */     //   509: aload_1
/*      */     //   510: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   513: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   516: pop
/*      */     //   517: return
/*      */     //   518: astore 5
/*      */     //   520: getstatic 158	net/minecraft/server/v1_8_R3/PlayerConnection:c	Lorg/apache/logging/log4j/Logger;
/*      */     //   523: ldc_w 2694
/*      */     //   526: aload 5
/*      */     //   528: invokeinterface 2670 3 0
/*      */     //   533: aload_0
/*      */     //   534: ldc_w 2672
/*      */     //   537: invokevirtual 325	net/minecraft/server/v1_8_R3/PlayerConnection:disconnect	(Ljava/lang/String;)V
/*      */     //   540: aload_2
/*      */     //   541: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   544: pop
/*      */     //   545: aload_1
/*      */     //   546: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   549: invokevirtual 2639	net/minecraft/server/v1_8_R3/PacketDataSerializer:refCnt	()I
/*      */     //   552: ifle +11 -> 563
/*      */     //   555: aload_1
/*      */     //   556: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   559: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   562: pop
/*      */     //   563: return
/*      */     //   564: astore 6
/*      */     //   566: aload_2
/*      */     //   567: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   570: pop
/*      */     //   571: aload 6
/*      */     //   573: athrow
/*      */     //   574: aload_2
/*      */     //   575: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   578: pop
/*      */     //   579: aload_1
/*      */     //   580: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   583: invokevirtual 2639	net/minecraft/server/v1_8_R3/PacketDataSerializer:refCnt	()I
/*      */     //   586: ifle +11 -> 597
/*      */     //   589: aload_1
/*      */     //   590: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   593: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   596: pop
/*      */     //   597: return
/*      */     //   598: ldc_w 2696
/*      */     //   601: aload_1
/*      */     //   602: invokevirtual 2617	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:a	()Ljava/lang/String;
/*      */     //   605: invokevirtual 723	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   608: ifeq +67 -> 675
/*      */     //   611: aload_1
/*      */     //   612: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   615: invokevirtual 2699	net/minecraft/server/v1_8_R3/PacketDataSerializer:readInt	()I
/*      */     //   618: istore 5
/*      */     //   620: aload_0
/*      */     //   621: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   624: getfield 1260	net/minecraft/server/v1_8_R3/EntityPlayer:activeContainer	Lnet/minecraft/server/v1_8_R3/Container;
/*      */     //   627: astore 6
/*      */     //   629: aload 6
/*      */     //   631: instanceof 2701
/*      */     //   634: ifeq +839 -> 1473
/*      */     //   637: aload 6
/*      */     //   639: checkcast 2701	net/minecraft/server/v1_8_R3/ContainerMerchant
/*      */     //   642: iload 5
/*      */     //   644: invokevirtual 2703	net/minecraft/server/v1_8_R3/ContainerMerchant:d	(I)V
/*      */     //   647: goto +826 -> 1473
/*      */     //   650: astore 5
/*      */     //   652: getstatic 158	net/minecraft/server/v1_8_R3/PlayerConnection:c	Lorg/apache/logging/log4j/Logger;
/*      */     //   655: ldc_w 2705
/*      */     //   658: aload 5
/*      */     //   660: invokeinterface 2670 3 0
/*      */     //   665: aload_0
/*      */     //   666: ldc_w 2707
/*      */     //   669: invokevirtual 325	net/minecraft/server/v1_8_R3/PlayerConnection:disconnect	(Ljava/lang/String;)V
/*      */     //   672: goto +801 -> 1473
/*      */     //   675: ldc_w 2709
/*      */     //   678: aload_1
/*      */     //   679: invokevirtual 2617	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:a	()Ljava/lang/String;
/*      */     //   682: invokevirtual 723	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   685: ifeq +326 -> 1011
/*      */     //   688: aload_0
/*      */     //   689: getfield 231	net/minecraft/server/v1_8_R3/PlayerConnection:minecraftServer	Lnet/minecraft/server/v1_8_R3/MinecraftServer;
/*      */     //   692: invokevirtual 2712	net/minecraft/server/v1_8_R3/MinecraftServer:getEnableCommandBlock	()Z
/*      */     //   695: ifne +27 -> 722
/*      */     //   698: aload_0
/*      */     //   699: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   702: new 1188	net/minecraft/server/v1_8_R3/ChatMessage
/*      */     //   705: dup
/*      */     //   706: ldc_w 2714
/*      */     //   709: iconst_0
/*      */     //   710: anewarray 4	java/lang/Object
/*      */     //   713: invokespecial 1193	net/minecraft/server/v1_8_R3/ChatMessage:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   716: invokevirtual 2716	net/minecraft/server/v1_8_R3/EntityPlayer:sendMessage	(Lnet/minecraft/server/v1_8_R3/IChatBaseComponent;)V
/*      */     //   719: goto +754 -> 1473
/*      */     //   722: aload_0
/*      */     //   723: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   726: invokevirtual 259	net/minecraft/server/v1_8_R3/EntityPlayer:getBukkitEntity	()Lorg/bukkit/craftbukkit/v1_8_R3/entity/CraftPlayer;
/*      */     //   729: invokevirtual 2718	org/bukkit/craftbukkit/v1_8_R3/entity/CraftPlayer:isOp	()Z
/*      */     //   732: ifeq +255 -> 987
/*      */     //   735: aload_0
/*      */     //   736: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   739: getfield 809	net/minecraft/server/v1_8_R3/EntityPlayer:abilities	Lnet/minecraft/server/v1_8_R3/PlayerAbilities;
/*      */     //   742: getfield 2190	net/minecraft/server/v1_8_R3/PlayerAbilities:canInstantlyBuild	Z
/*      */     //   745: ifeq +242 -> 987
/*      */     //   748: aload_1
/*      */     //   749: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   752: astore_2
/*      */     //   753: aload_2
/*      */     //   754: invokevirtual 2722	net/minecraft/server/v1_8_R3/PacketDataSerializer:readByte	()B
/*      */     //   757: istore 5
/*      */     //   759: aconst_null
/*      */     //   760: astore 6
/*      */     //   762: iload 5
/*      */     //   764: ifne +55 -> 819
/*      */     //   767: aload_0
/*      */     //   768: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   771: getfield 1117	net/minecraft/server/v1_8_R3/EntityPlayer:world	Lnet/minecraft/server/v1_8_R3/World;
/*      */     //   774: new 928	net/minecraft/server/v1_8_R3/BlockPosition
/*      */     //   777: dup
/*      */     //   778: aload_2
/*      */     //   779: invokevirtual 2699	net/minecraft/server/v1_8_R3/PacketDataSerializer:readInt	()I
/*      */     //   782: aload_2
/*      */     //   783: invokevirtual 2699	net/minecraft/server/v1_8_R3/PacketDataSerializer:readInt	()I
/*      */     //   786: aload_2
/*      */     //   787: invokevirtual 2699	net/minecraft/server/v1_8_R3/PacketDataSerializer:readInt	()I
/*      */     //   790: invokespecial 2376	net/minecraft/server/v1_8_R3/BlockPosition:<init>	(III)V
/*      */     //   793: invokevirtual 2377	net/minecraft/server/v1_8_R3/World:getTileEntity	(Lnet/minecraft/server/v1_8_R3/BlockPosition;)Lnet/minecraft/server/v1_8_R3/TileEntity;
/*      */     //   796: astore 7
/*      */     //   798: aload 7
/*      */     //   800: instanceof 2724
/*      */     //   803: ifeq +56 -> 859
/*      */     //   806: aload 7
/*      */     //   808: checkcast 2724	net/minecraft/server/v1_8_R3/TileEntityCommand
/*      */     //   811: invokevirtual 2728	net/minecraft/server/v1_8_R3/TileEntityCommand:getCommandBlock	()Lnet/minecraft/server/v1_8_R3/CommandBlockListenerAbstract;
/*      */     //   814: astore 6
/*      */     //   816: goto +43 -> 859
/*      */     //   819: iload 5
/*      */     //   821: iconst_1
/*      */     //   822: if_icmpne +37 -> 859
/*      */     //   825: aload_0
/*      */     //   826: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   829: getfield 1117	net/minecraft/server/v1_8_R3/EntityPlayer:world	Lnet/minecraft/server/v1_8_R3/World;
/*      */     //   832: aload_2
/*      */     //   833: invokevirtual 2699	net/minecraft/server/v1_8_R3/PacketDataSerializer:readInt	()I
/*      */     //   836: invokevirtual 2733	net/minecraft/server/v1_8_R3/World:a	(I)Lnet/minecraft/server/v1_8_R3/Entity;
/*      */     //   839: astore 7
/*      */     //   841: aload 7
/*      */     //   843: instanceof 2735
/*      */     //   846: ifeq +13 -> 859
/*      */     //   849: aload 7
/*      */     //   851: checkcast 2735	net/minecraft/server/v1_8_R3/EntityMinecartCommandBlock
/*      */     //   854: invokevirtual 2736	net/minecraft/server/v1_8_R3/EntityMinecartCommandBlock:getCommandBlock	()Lnet/minecraft/server/v1_8_R3/CommandBlockListenerAbstract;
/*      */     //   857: astore 6
/*      */     //   859: aload_2
/*      */     //   860: aload_2
/*      */     //   861: invokevirtual 2739	net/minecraft/server/v1_8_R3/PacketDataSerializer:readableBytes	()I
/*      */     //   864: invokevirtual 2741	net/minecraft/server/v1_8_R3/PacketDataSerializer:c	(I)Ljava/lang/String;
/*      */     //   867: astore 7
/*      */     //   869: aload_2
/*      */     //   870: invokevirtual 2744	net/minecraft/server/v1_8_R3/PacketDataSerializer:readBoolean	()Z
/*      */     //   873: istore 8
/*      */     //   875: aload 6
/*      */     //   877: ifnull +102 -> 979
/*      */     //   880: aload 6
/*      */     //   882: aload 7
/*      */     //   884: invokevirtual 2747	net/minecraft/server/v1_8_R3/CommandBlockListenerAbstract:setCommand	(Ljava/lang/String;)V
/*      */     //   887: aload 6
/*      */     //   889: iload 8
/*      */     //   891: invokevirtual 2748	net/minecraft/server/v1_8_R3/CommandBlockListenerAbstract:a	(Z)V
/*      */     //   894: iload 8
/*      */     //   896: ifne +9 -> 905
/*      */     //   899: aload 6
/*      */     //   901: aconst_null
/*      */     //   902: invokevirtual 2750	net/minecraft/server/v1_8_R3/CommandBlockListenerAbstract:b	(Lnet/minecraft/server/v1_8_R3/IChatBaseComponent;)V
/*      */     //   905: aload 6
/*      */     //   907: invokevirtual 2752	net/minecraft/server/v1_8_R3/CommandBlockListenerAbstract:h	()V
/*      */     //   910: aload_0
/*      */     //   911: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   914: new 1188	net/minecraft/server/v1_8_R3/ChatMessage
/*      */     //   917: dup
/*      */     //   918: ldc_w 2754
/*      */     //   921: iconst_1
/*      */     //   922: anewarray 4	java/lang/Object
/*      */     //   925: dup
/*      */     //   926: iconst_0
/*      */     //   927: aload 7
/*      */     //   929: aastore
/*      */     //   930: invokespecial 1193	net/minecraft/server/v1_8_R3/ChatMessage:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   933: invokevirtual 2716	net/minecraft/server/v1_8_R3/EntityPlayer:sendMessage	(Lnet/minecraft/server/v1_8_R3/IChatBaseComponent;)V
/*      */     //   936: goto +43 -> 979
/*      */     //   939: astore 5
/*      */     //   941: getstatic 158	net/minecraft/server/v1_8_R3/PlayerConnection:c	Lorg/apache/logging/log4j/Logger;
/*      */     //   944: ldc_w 2756
/*      */     //   947: aload 5
/*      */     //   949: invokeinterface 2670 3 0
/*      */     //   954: aload_0
/*      */     //   955: ldc_w 2758
/*      */     //   958: invokevirtual 325	net/minecraft/server/v1_8_R3/PlayerConnection:disconnect	(Ljava/lang/String;)V
/*      */     //   961: aload_2
/*      */     //   962: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   965: pop
/*      */     //   966: goto +507 -> 1473
/*      */     //   969: astore 9
/*      */     //   971: aload_2
/*      */     //   972: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   975: pop
/*      */     //   976: aload 9
/*      */     //   978: athrow
/*      */     //   979: aload_2
/*      */     //   980: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   983: pop
/*      */     //   984: goto +489 -> 1473
/*      */     //   987: aload_0
/*      */     //   988: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   991: new 1188	net/minecraft/server/v1_8_R3/ChatMessage
/*      */     //   994: dup
/*      */     //   995: ldc_w 2760
/*      */     //   998: iconst_0
/*      */     //   999: anewarray 4	java/lang/Object
/*      */     //   1002: invokespecial 1193	net/minecraft/server/v1_8_R3/ChatMessage:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   1005: invokevirtual 2716	net/minecraft/server/v1_8_R3/EntityPlayer:sendMessage	(Lnet/minecraft/server/v1_8_R3/IChatBaseComponent;)V
/*      */     //   1008: goto +465 -> 1473
/*      */     //   1011: ldc_w 2762
/*      */     //   1014: aload_1
/*      */     //   1015: invokevirtual 2617	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:a	()Ljava/lang/String;
/*      */     //   1018: invokevirtual 723	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   1021: ifeq +130 -> 1151
/*      */     //   1024: aload_0
/*      */     //   1025: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   1028: getfield 1260	net/minecraft/server/v1_8_R3/EntityPlayer:activeContainer	Lnet/minecraft/server/v1_8_R3/Container;
/*      */     //   1031: instanceof 2764
/*      */     //   1034: ifeq +439 -> 1473
/*      */     //   1037: aload_1
/*      */     //   1038: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   1041: astore_2
/*      */     //   1042: aload_2
/*      */     //   1043: invokevirtual 2699	net/minecraft/server/v1_8_R3/PacketDataSerializer:readInt	()I
/*      */     //   1046: istore 5
/*      */     //   1048: aload_2
/*      */     //   1049: invokevirtual 2699	net/minecraft/server/v1_8_R3/PacketDataSerializer:readInt	()I
/*      */     //   1052: istore 6
/*      */     //   1054: aload_0
/*      */     //   1055: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   1058: getfield 1260	net/minecraft/server/v1_8_R3/EntityPlayer:activeContainer	Lnet/minecraft/server/v1_8_R3/Container;
/*      */     //   1061: checkcast 2764	net/minecraft/server/v1_8_R3/ContainerBeacon
/*      */     //   1064: astore 7
/*      */     //   1066: aload 7
/*      */     //   1068: iconst_0
/*      */     //   1069: invokevirtual 2765	net/minecraft/server/v1_8_R3/ContainerBeacon:getSlot	(I)Lnet/minecraft/server/v1_8_R3/Slot;
/*      */     //   1072: astore 8
/*      */     //   1074: aload 8
/*      */     //   1076: invokevirtual 2163	net/minecraft/server/v1_8_R3/Slot:hasItem	()Z
/*      */     //   1079: ifeq +394 -> 1473
/*      */     //   1082: aload 8
/*      */     //   1084: iconst_1
/*      */     //   1085: invokevirtual 2767	net/minecraft/server/v1_8_R3/Slot:a	(I)Lnet/minecraft/server/v1_8_R3/ItemStack;
/*      */     //   1088: pop
/*      */     //   1089: aload 7
/*      */     //   1091: invokevirtual 2770	net/minecraft/server/v1_8_R3/ContainerBeacon:e	()Lnet/minecraft/server/v1_8_R3/IInventory;
/*      */     //   1094: astore 9
/*      */     //   1096: aload 9
/*      */     //   1098: iconst_1
/*      */     //   1099: iload 5
/*      */     //   1101: invokeinterface 2773 3 0
/*      */     //   1106: aload 9
/*      */     //   1108: iconst_2
/*      */     //   1109: iload 6
/*      */     //   1111: invokeinterface 2773 3 0
/*      */     //   1116: aload 9
/*      */     //   1118: invokeinterface 2774 1 0
/*      */     //   1123: goto +350 -> 1473
/*      */     //   1126: astore 5
/*      */     //   1128: getstatic 158	net/minecraft/server/v1_8_R3/PlayerConnection:c	Lorg/apache/logging/log4j/Logger;
/*      */     //   1131: ldc_w 2776
/*      */     //   1134: aload 5
/*      */     //   1136: invokeinterface 2670 3 0
/*      */     //   1141: aload_0
/*      */     //   1142: ldc_w 2778
/*      */     //   1145: invokevirtual 325	net/minecraft/server/v1_8_R3/PlayerConnection:disconnect	(Ljava/lang/String;)V
/*      */     //   1148: goto +325 -> 1473
/*      */     //   1151: ldc_w 2780
/*      */     //   1154: aload_1
/*      */     //   1155: invokevirtual 2617	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:a	()Ljava/lang/String;
/*      */     //   1158: invokevirtual 723	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   1161: ifeq +92 -> 1253
/*      */     //   1164: aload_0
/*      */     //   1165: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   1168: getfield 1260	net/minecraft/server/v1_8_R3/EntityPlayer:activeContainer	Lnet/minecraft/server/v1_8_R3/Container;
/*      */     //   1171: instanceof 2782
/*      */     //   1174: ifeq +79 -> 1253
/*      */     //   1177: aload_0
/*      */     //   1178: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   1181: getfield 1260	net/minecraft/server/v1_8_R3/EntityPlayer:activeContainer	Lnet/minecraft/server/v1_8_R3/Container;
/*      */     //   1184: checkcast 2782	net/minecraft/server/v1_8_R3/ContainerAnvil
/*      */     //   1187: astore 5
/*      */     //   1189: aload_1
/*      */     //   1190: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   1193: ifnull +49 -> 1242
/*      */     //   1196: aload_1
/*      */     //   1197: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   1200: invokevirtual 2739	net/minecraft/server/v1_8_R3/PacketDataSerializer:readableBytes	()I
/*      */     //   1203: iconst_1
/*      */     //   1204: if_icmplt +38 -> 1242
/*      */     //   1207: aload_1
/*      */     //   1208: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   1211: sipush 32767
/*      */     //   1214: invokevirtual 2741	net/minecraft/server/v1_8_R3/PacketDataSerializer:c	(I)Ljava/lang/String;
/*      */     //   1217: invokestatic 2783	net/minecraft/server/v1_8_R3/SharedConstants:a	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1220: astore 6
/*      */     //   1222: aload 6
/*      */     //   1224: invokevirtual 1391	java/lang/String:length	()I
/*      */     //   1227: bipush 30
/*      */     //   1229: if_icmpgt +244 -> 1473
/*      */     //   1232: aload 5
/*      */     //   1234: aload 6
/*      */     //   1236: invokevirtual 2784	net/minecraft/server/v1_8_R3/ContainerAnvil:a	(Ljava/lang/String;)V
/*      */     //   1239: goto +234 -> 1473
/*      */     //   1242: aload 5
/*      */     //   1244: ldc_w 2786
/*      */     //   1247: invokevirtual 2784	net/minecraft/server/v1_8_R3/ContainerAnvil:a	(Ljava/lang/String;)V
/*      */     //   1250: goto +223 -> 1473
/*      */     //   1253: aload_1
/*      */     //   1254: invokevirtual 2617	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:a	()Ljava/lang/String;
/*      */     //   1257: ldc_w 2788
/*      */     //   1260: invokevirtual 723	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   1263: ifeq +64 -> 1327
/*      */     //   1266: aload_1
/*      */     //   1267: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   1270: getstatic 2794	com/google/common/base/Charsets:UTF_8	Ljava/nio/charset/Charset;
/*      */     //   1273: invokevirtual 2797	net/minecraft/server/v1_8_R3/PacketDataSerializer:toString	(Ljava/nio/charset/Charset;)Ljava/lang/String;
/*      */     //   1276: astore 5
/*      */     //   1278: aload 5
/*      */     //   1280: ldc_w 2799
/*      */     //   1283: invokevirtual 2803	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
/*      */     //   1286: dup
/*      */     //   1287: astore 9
/*      */     //   1289: arraylength
/*      */     //   1290: istore 8
/*      */     //   1292: iconst_0
/*      */     //   1293: istore 7
/*      */     //   1295: goto +22 -> 1317
/*      */     //   1298: aload 9
/*      */     //   1300: iload 7
/*      */     //   1302: aaload
/*      */     //   1303: astore 6
/*      */     //   1305: aload_0
/*      */     //   1306: invokevirtual 515	net/minecraft/server/v1_8_R3/PlayerConnection:getPlayer	()Lorg/bukkit/craftbukkit/v1_8_R3/entity/CraftPlayer;
/*      */     //   1309: aload 6
/*      */     //   1311: invokevirtual 2806	org/bukkit/craftbukkit/v1_8_R3/entity/CraftPlayer:addChannel	(Ljava/lang/String;)V
/*      */     //   1314: iinc 7 1
/*      */     //   1317: iload 7
/*      */     //   1319: iload 8
/*      */     //   1321: if_icmplt -23 -> 1298
/*      */     //   1324: goto +149 -> 1473
/*      */     //   1327: aload_1
/*      */     //   1328: invokevirtual 2617	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:a	()Ljava/lang/String;
/*      */     //   1331: ldc_w 2808
/*      */     //   1334: invokevirtual 723	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   1337: ifeq +64 -> 1401
/*      */     //   1340: aload_1
/*      */     //   1341: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   1344: getstatic 2794	com/google/common/base/Charsets:UTF_8	Ljava/nio/charset/Charset;
/*      */     //   1347: invokevirtual 2797	net/minecraft/server/v1_8_R3/PacketDataSerializer:toString	(Ljava/nio/charset/Charset;)Ljava/lang/String;
/*      */     //   1350: astore 5
/*      */     //   1352: aload 5
/*      */     //   1354: ldc_w 2799
/*      */     //   1357: invokevirtual 2803	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
/*      */     //   1360: dup
/*      */     //   1361: astore 9
/*      */     //   1363: arraylength
/*      */     //   1364: istore 8
/*      */     //   1366: iconst_0
/*      */     //   1367: istore 7
/*      */     //   1369: goto +22 -> 1391
/*      */     //   1372: aload 9
/*      */     //   1374: iload 7
/*      */     //   1376: aaload
/*      */     //   1377: astore 6
/*      */     //   1379: aload_0
/*      */     //   1380: invokevirtual 515	net/minecraft/server/v1_8_R3/PlayerConnection:getPlayer	()Lorg/bukkit/craftbukkit/v1_8_R3/entity/CraftPlayer;
/*      */     //   1383: aload 6
/*      */     //   1385: invokevirtual 2811	org/bukkit/craftbukkit/v1_8_R3/entity/CraftPlayer:removeChannel	(Ljava/lang/String;)V
/*      */     //   1388: iinc 7 1
/*      */     //   1391: iload 7
/*      */     //   1393: iload 8
/*      */     //   1395: if_icmplt -23 -> 1372
/*      */     //   1398: goto +75 -> 1473
/*      */     //   1401: aload_1
/*      */     //   1402: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   1405: invokevirtual 2739	net/minecraft/server/v1_8_R3/PacketDataSerializer:readableBytes	()I
/*      */     //   1408: newarray <illegal type>
/*      */     //   1410: astore 5
/*      */     //   1412: aload_1
/*      */     //   1413: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   1416: aload 5
/*      */     //   1418: invokevirtual 2815	net/minecraft/server/v1_8_R3/PacketDataSerializer:readBytes	([B)Lio/netty/buffer/ByteBuf;
/*      */     //   1421: pop
/*      */     //   1422: aload_0
/*      */     //   1423: getfield 250	net/minecraft/server/v1_8_R3/PlayerConnection:server	Lorg/bukkit/craftbukkit/v1_8_R3/CraftServer;
/*      */     //   1426: invokevirtual 2819	org/bukkit/craftbukkit/v1_8_R3/CraftServer:getMessenger	()Lorg/bukkit/plugin/messaging/Messenger;
/*      */     //   1429: aload_0
/*      */     //   1430: getfield 241	net/minecraft/server/v1_8_R3/PlayerConnection:player	Lnet/minecraft/server/v1_8_R3/EntityPlayer;
/*      */     //   1433: invokevirtual 259	net/minecraft/server/v1_8_R3/EntityPlayer:getBukkitEntity	()Lorg/bukkit/craftbukkit/v1_8_R3/entity/CraftPlayer;
/*      */     //   1436: aload_1
/*      */     //   1437: invokevirtual 2617	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:a	()Ljava/lang/String;
/*      */     //   1440: aload 5
/*      */     //   1442: invokeinterface 2825 4 0
/*      */     //   1447: goto +26 -> 1473
/*      */     //   1450: astore 10
/*      */     //   1452: aload_1
/*      */     //   1453: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   1456: invokevirtual 2639	net/minecraft/server/v1_8_R3/PacketDataSerializer:refCnt	()I
/*      */     //   1459: ifle +11 -> 1470
/*      */     //   1462: aload_1
/*      */     //   1463: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   1466: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   1469: pop
/*      */     //   1470: aload 10
/*      */     //   1472: athrow
/*      */     //   1473: aload_1
/*      */     //   1474: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   1477: invokevirtual 2639	net/minecraft/server/v1_8_R3/PacketDataSerializer:refCnt	()I
/*      */     //   1480: ifle +11 -> 1491
/*      */     //   1483: aload_1
/*      */     //   1484: invokevirtual 2622	net/minecraft/server/v1_8_R3/PacketPlayInCustomPayload:b	()Lnet/minecraft/server/v1_8_R3/PacketDataSerializer;
/*      */     //   1487: invokevirtual 2636	net/minecraft/server/v1_8_R3/PacketDataSerializer:release	()Z
/*      */     //   1490: pop
/*      */     //   1491: return
/*      */     // Line number table:
/*      */     //   Java source line #1932	-> byte code offset #0
/*      */     //   Java source line #1938	-> byte code offset #12
/*      */     //   Java source line #1939	-> byte code offset #25
/*      */     //   Java source line #1942	-> byte code offset #40
/*      */     //   Java source line #1943	-> byte code offset #45
/*      */     //   Java source line #1966	-> byte code offset #49
/*      */     //   Java source line #2119	-> byte code offset #54
/*      */     //   Java source line #2120	-> byte code offset #64
/*      */     //   Java source line #1944	-> byte code offset #72
/*      */     //   Java source line #1947	-> byte code offset #73
/*      */     //   Java source line #1948	-> byte code offset #83
/*      */     //   Java source line #1951	-> byte code offset #94
/*      */     //   Java source line #1952	-> byte code offset #106
/*      */     //   Java source line #1953	-> byte code offset #111
/*      */     //   Java source line #1954	-> byte code offset #133
/*      */     //   Java source line #1955	-> byte code offset #145
/*      */     //   Java source line #1956	-> byte code offset #165
/*      */     //   Java source line #1966	-> byte code offset #174
/*      */     //   Java source line #2119	-> byte code offset #179
/*      */     //   Java source line #2120	-> byte code offset #189
/*      */     //   Java source line #1959	-> byte code offset #197
/*      */     //   Java source line #1961	-> byte code offset #198
/*      */     //   Java source line #1962	-> byte code offset #200
/*      */     //   Java source line #1963	-> byte code offset #213
/*      */     //   Java source line #1966	-> byte code offset #220
/*      */     //   Java source line #2119	-> byte code offset #225
/*      */     //   Java source line #2120	-> byte code offset #235
/*      */     //   Java source line #1964	-> byte code offset #243
/*      */     //   Java source line #1965	-> byte code offset #244
/*      */     //   Java source line #1966	-> byte code offset #246
/*      */     //   Java source line #1967	-> byte code offset #251
/*      */     //   Java source line #1966	-> byte code offset #254
/*      */     //   Java source line #2119	-> byte code offset #259
/*      */     //   Java source line #2120	-> byte code offset #269
/*      */     //   Java source line #1969	-> byte code offset #277
/*      */     //   Java source line #1970	-> byte code offset #278
/*      */     //   Java source line #1971	-> byte code offset #291
/*      */     //   Java source line #1974	-> byte code offset #306
/*      */     //   Java source line #1975	-> byte code offset #311
/*      */     //   Java source line #2003	-> byte code offset #315
/*      */     //   Java source line #2119	-> byte code offset #320
/*      */     //   Java source line #2120	-> byte code offset #330
/*      */     //   Java source line #1976	-> byte code offset #338
/*      */     //   Java source line #1979	-> byte code offset #339
/*      */     //   Java source line #1980	-> byte code offset #349
/*      */     //   Java source line #1983	-> byte code offset #360
/*      */     //   Java source line #1984	-> byte code offset #372
/*      */     //   Java source line #1985	-> byte code offset #377
/*      */     //   Java source line #1987	-> byte code offset #398
/*      */     //   Java source line #1988	-> byte code offset #410
/*      */     //   Java source line #1989	-> byte code offset #432
/*      */     //   Java source line #1990	-> byte code offset #457
/*      */     //   Java source line #1991	-> byte code offset #477
/*      */     //   Java source line #1992	-> byte code offset #485
/*      */     //   Java source line #2003	-> byte code offset #494
/*      */     //   Java source line #2119	-> byte code offset #499
/*      */     //   Java source line #2120	-> byte code offset #509
/*      */     //   Java source line #1996	-> byte code offset #517
/*      */     //   Java source line #1998	-> byte code offset #518
/*      */     //   Java source line #1999	-> byte code offset #520
/*      */     //   Java source line #2000	-> byte code offset #533
/*      */     //   Java source line #2003	-> byte code offset #540
/*      */     //   Java source line #2119	-> byte code offset #545
/*      */     //   Java source line #2120	-> byte code offset #555
/*      */     //   Java source line #2001	-> byte code offset #563
/*      */     //   Java source line #2002	-> byte code offset #564
/*      */     //   Java source line #2003	-> byte code offset #566
/*      */     //   Java source line #2004	-> byte code offset #571
/*      */     //   Java source line #2003	-> byte code offset #574
/*      */     //   Java source line #2119	-> byte code offset #579
/*      */     //   Java source line #2120	-> byte code offset #589
/*      */     //   Java source line #2006	-> byte code offset #597
/*      */     //   Java source line #2007	-> byte code offset #598
/*      */     //   Java source line #2009	-> byte code offset #611
/*      */     //   Java source line #2010	-> byte code offset #620
/*      */     //   Java source line #2012	-> byte code offset #629
/*      */     //   Java source line #2013	-> byte code offset #637
/*      */     //   Java source line #2015	-> byte code offset #647
/*      */     //   Java source line #2016	-> byte code offset #652
/*      */     //   Java source line #2017	-> byte code offset #665
/*      */     //   Java source line #2019	-> byte code offset #672
/*      */     //   Java source line #2020	-> byte code offset #688
/*      */     //   Java source line #2021	-> byte code offset #698
/*      */     //   Java source line #2022	-> byte code offset #719
/*      */     //   Java source line #2023	-> byte code offset #748
/*      */     //   Java source line #2026	-> byte code offset #753
/*      */     //   Java source line #2027	-> byte code offset #759
/*      */     //   Java source line #2029	-> byte code offset #762
/*      */     //   Java source line #2030	-> byte code offset #767
/*      */     //   Java source line #2032	-> byte code offset #798
/*      */     //   Java source line #2033	-> byte code offset #806
/*      */     //   Java source line #2035	-> byte code offset #816
/*      */     //   Java source line #2036	-> byte code offset #825
/*      */     //   Java source line #2038	-> byte code offset #841
/*      */     //   Java source line #2039	-> byte code offset #849
/*      */     //   Java source line #2043	-> byte code offset #859
/*      */     //   Java source line #2044	-> byte code offset #869
/*      */     //   Java source line #2046	-> byte code offset #875
/*      */     //   Java source line #2047	-> byte code offset #880
/*      */     //   Java source line #2048	-> byte code offset #887
/*      */     //   Java source line #2049	-> byte code offset #894
/*      */     //   Java source line #2050	-> byte code offset #899
/*      */     //   Java source line #2053	-> byte code offset #905
/*      */     //   Java source line #2054	-> byte code offset #910
/*      */     //   Java source line #2056	-> byte code offset #936
/*      */     //   Java source line #2057	-> byte code offset #941
/*      */     //   Java source line #2058	-> byte code offset #954
/*      */     //   Java source line #2060	-> byte code offset #961
/*      */     //   Java source line #2059	-> byte code offset #969
/*      */     //   Java source line #2060	-> byte code offset #971
/*      */     //   Java source line #2061	-> byte code offset #976
/*      */     //   Java source line #2060	-> byte code offset #979
/*      */     //   Java source line #2062	-> byte code offset #984
/*      */     //   Java source line #2063	-> byte code offset #987
/*      */     //   Java source line #2065	-> byte code offset #1008
/*      */     //   Java source line #2066	-> byte code offset #1024
/*      */     //   Java source line #2068	-> byte code offset #1037
/*      */     //   Java source line #2069	-> byte code offset #1042
/*      */     //   Java source line #2070	-> byte code offset #1048
/*      */     //   Java source line #2071	-> byte code offset #1054
/*      */     //   Java source line #2072	-> byte code offset #1066
/*      */     //   Java source line #2074	-> byte code offset #1074
/*      */     //   Java source line #2075	-> byte code offset #1082
/*      */     //   Java source line #2076	-> byte code offset #1089
/*      */     //   Java source line #2078	-> byte code offset #1096
/*      */     //   Java source line #2079	-> byte code offset #1106
/*      */     //   Java source line #2080	-> byte code offset #1116
/*      */     //   Java source line #2082	-> byte code offset #1123
/*      */     //   Java source line #2083	-> byte code offset #1128
/*      */     //   Java source line #2084	-> byte code offset #1141
/*      */     //   Java source line #2087	-> byte code offset #1148
/*      */     //   Java source line #2088	-> byte code offset #1177
/*      */     //   Java source line #2090	-> byte code offset #1189
/*      */     //   Java source line #2091	-> byte code offset #1207
/*      */     //   Java source line #2093	-> byte code offset #1222
/*      */     //   Java source line #2094	-> byte code offset #1232
/*      */     //   Java source line #2096	-> byte code offset #1239
/*      */     //   Java source line #2097	-> byte code offset #1242
/*      */     //   Java source line #2099	-> byte code offset #1250
/*      */     //   Java source line #2101	-> byte code offset #1253
/*      */     //   Java source line #2102	-> byte code offset #1266
/*      */     //   Java source line #2103	-> byte code offset #1278
/*      */     //   Java source line #2104	-> byte code offset #1305
/*      */     //   Java source line #2103	-> byte code offset #1314
/*      */     //   Java source line #2106	-> byte code offset #1324
/*      */     //   Java source line #2107	-> byte code offset #1340
/*      */     //   Java source line #2108	-> byte code offset #1352
/*      */     //   Java source line #2109	-> byte code offset #1379
/*      */     //   Java source line #2108	-> byte code offset #1388
/*      */     //   Java source line #2111	-> byte code offset #1398
/*      */     //   Java source line #2112	-> byte code offset #1401
/*      */     //   Java source line #2113	-> byte code offset #1412
/*      */     //   Java source line #2114	-> byte code offset #1422
/*      */     //   Java source line #2118	-> byte code offset #1447
/*      */     //   Java source line #2119	-> byte code offset #1452
/*      */     //   Java source line #2120	-> byte code offset #1462
/*      */     //   Java source line #2122	-> byte code offset #1470
/*      */     //   Java source line #2119	-> byte code offset #1473
/*      */     //   Java source line #2120	-> byte code offset #1483
/*      */     //   Java source line #2124	-> byte code offset #1491
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	1492	0	this	PlayerConnection
/*      */     //   0	1492	1	packetplayincustompayload	PacketPlayInCustomPayload
/*      */     //   39	216	2	packetdataserializer	PacketDataSerializer
/*      */     //   305	270	2	packetdataserializer	PacketDataSerializer
/*      */     //   752	228	2	packetdataserializer	PacketDataSerializer
/*      */     //   1041	8	2	packetdataserializer	PacketDataSerializer
/*      */     //   44	107	3	itemstack	ItemStack
/*      */     //   254	1	3	itemstack	ItemStack
/*      */     //   310	153	3	itemstack	ItemStack
/*      */     //   574	1	3	itemstack	ItemStack
/*      */     //   104	66	4	itemstack1	ItemStack
/*      */     //   254	1	4	itemstack1	ItemStack
/*      */     //   370	120	4	itemstack1	ItemStack
/*      */     //   574	1	4	itemstack1	ItemStack
/*      */     //   198	9	5	exception	Exception
/*      */     //   518	9	5	exception1	Exception
/*      */     //   618	25	5	i	int
/*      */     //   650	9	5	exception2	Exception
/*      */     //   757	63	5	b0	byte
/*      */     //   939	9	5	exception3	Exception
/*      */     //   1046	54	5	j	int
/*      */     //   1126	9	5	exception4	Exception
/*      */     //   1187	56	5	containeranvil	ContainerAnvil
/*      */     //   1276	3	5	channels	String
/*      */     //   1350	3	5	channels	String
/*      */     //   1410	31	5	data	byte[]
/*      */     //   244	8	6	localObject1	Object
/*      */     //   564	8	6	localObject2	Object
/*      */     //   627	11	6	container	Container
/*      */     //   760	146	6	commandblocklistenerabstract	CommandBlockListenerAbstract
/*      */     //   1052	58	6	k	int
/*      */     //   1220	15	6	s1	String
/*      */     //   1303	7	6	channel	String
/*      */     //   1377	7	6	channel	String
/*      */     //   796	11	7	tileentity	TileEntity
/*      */     //   839	11	7	entity	Entity
/*      */     //   867	61	7	s	String
/*      */     //   1064	328	7	containerbeacon	ContainerBeacon
/*      */     //   873	22	8	flag	boolean
/*      */     //   1072	322	8	slot	Slot
/*      */     //   969	8	9	localObject3	Object
/*      */     //   1094	279	9	iinventory	Object
/*      */     //   1450	21	10	localObject4	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   40	49	198	java/lang/Exception
/*      */     //   73	174	198	java/lang/Exception
/*      */     //   40	49	244	finally
/*      */     //   73	174	244	finally
/*      */     //   198	220	244	finally
/*      */     //   306	315	518	java/lang/Exception
/*      */     //   339	494	518	java/lang/Exception
/*      */     //   306	315	564	finally
/*      */     //   339	494	564	finally
/*      */     //   518	540	564	finally
/*      */     //   611	647	650	java/lang/Exception
/*      */     //   753	936	939	java/lang/Exception
/*      */     //   753	961	969	finally
/*      */     //   1037	1123	1126	java/lang/Exception
/*      */     //   12	54	1450	finally
/*      */     //   73	179	1450	finally
/*      */     //   198	225	1450	finally
/*      */     //   244	259	1450	finally
/*      */     //   278	320	1450	finally
/*      */     //   339	499	1450	finally
/*      */     //   518	545	1450	finally
/*      */     //   564	579	1450	finally
/*      */     //   598	1450	1450	finally
/*      */   }
/*      */   
/*      */   public boolean isDisconnected()
/*      */   {
/* 2128 */     return (!this.player.joining) && (!this.networkManager.channel.config().isAutoRead());
/*      */   }
/*      */   
/*      */   static class SyntheticClass_1
/*      */   {
/*      */     static final int[] a;
/*      */     static final int[] b;
/* 2135 */     static final int[] c = new int[PacketPlayInClientCommand.EnumClientCommand.values().length];
/*      */     
/*      */     static {
/*      */       try {
/* 2139 */         c[PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN.ordinal()] = 1;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*      */       
/*      */       try
/*      */       {
/* 2145 */         c[PacketPlayInClientCommand.EnumClientCommand.REQUEST_STATS.ordinal()] = 2;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*      */       
/*      */       try
/*      */       {
/* 2151 */         c[PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*      */       
/*      */ 
/* 2156 */       b = new int[PacketPlayInEntityAction.EnumPlayerAction.values().length];
/*      */       try
/*      */       {
/* 2159 */         b[PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING.ordinal()] = 1;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*      */       
/*      */       try
/*      */       {
/* 2165 */         b[PacketPlayInEntityAction.EnumPlayerAction.STOP_SNEAKING.ordinal()] = 2;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/*      */       
/*      */       try
/*      */       {
/* 2171 */         b[PacketPlayInEntityAction.EnumPlayerAction.START_SPRINTING.ordinal()] = 3;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError6) {}
/*      */       
/*      */       try
/*      */       {
/* 2177 */         b[PacketPlayInEntityAction.EnumPlayerAction.STOP_SPRINTING.ordinal()] = 4;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError7) {}
/*      */       
/*      */       try
/*      */       {
/* 2183 */         b[PacketPlayInEntityAction.EnumPlayerAction.STOP_SLEEPING.ordinal()] = 5;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError8) {}
/*      */       
/*      */       try
/*      */       {
/* 2189 */         b[PacketPlayInEntityAction.EnumPlayerAction.RIDING_JUMP.ordinal()] = 6;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError9) {}
/*      */       
/*      */       try
/*      */       {
/* 2195 */         b[PacketPlayInEntityAction.EnumPlayerAction.OPEN_INVENTORY.ordinal()] = 7;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError10) {}
/*      */       
/*      */ 
/* 2200 */       a = new int[PacketPlayInBlockDig.EnumPlayerDigType.values().length];
/*      */       try
/*      */       {
/* 2203 */         a[PacketPlayInBlockDig.EnumPlayerDigType.DROP_ITEM.ordinal()] = 1;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError11) {}
/*      */       
/*      */       try
/*      */       {
/* 2209 */         a[PacketPlayInBlockDig.EnumPlayerDigType.DROP_ALL_ITEMS.ordinal()] = 2;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError12) {}
/*      */       
/*      */       try
/*      */       {
/* 2215 */         a[PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM.ordinal()] = 3;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError13) {}
/*      */       
/*      */       try
/*      */       {
/* 2221 */         a[PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK.ordinal()] = 4;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError14) {}
/*      */       
/*      */       try
/*      */       {
/* 2227 */         a[PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK.ordinal()] = 5;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError15) {}
/*      */       
/*      */       try
/*      */       {
/* 2233 */         a[PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK.ordinal()] = 6;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError16) {}
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PlayerConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */