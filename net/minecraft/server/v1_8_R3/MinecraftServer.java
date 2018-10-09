/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.io.Files;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListenableFutureTask;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.GameProfileRepository;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*      */ import io.netty.util.ResourceLeakDetector;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.net.Proxy;
/*      */ import java.security.KeyPair;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Queue;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.ConcurrentLinkedQueue;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.bukkit.World.Environment;
/*      */ import org.bukkit.command.ConsoleCommandSender;
/*      */ import org.bukkit.command.RemoteConsoleCommandSender;
/*      */ import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
/*      */ import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings.WorldTimingsHandler;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.chunkio.ChunkIOExecutor;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.scheduler.CraftScheduler;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.ServerShutdownThread;
/*      */ import org.bukkit.event.world.WorldInitEvent;
/*      */ import org.bukkit.event.world.WorldLoadEvent;
/*      */ import org.bukkit.plugin.PluginLoadOrder;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.spigotmc.CustomTimingsHandler;
/*      */ import org.spigotmc.SpigotConfig;
/*      */ 
/*      */ public abstract class MinecraftServer implements Runnable, ICommandListener, IAsyncTaskHandler, IMojangStatistics
/*      */ {
/*   53 */   public static final Logger LOGGER = ;
/*   54 */   public static final File a = new File("usercache.json");
/*      */   private static MinecraftServer l;
/*      */   public Convertable convertable;
/*   57 */   private final MojangStatisticsGenerator n = new MojangStatisticsGenerator("server", this, az());
/*      */   public File universe;
/*   59 */   private final List<IUpdatePlayerListBox> p = Lists.newArrayList();
/*      */   protected final ICommandHandler b;
/*   61 */   public final MethodProfiler methodProfiler = new MethodProfiler();
/*      */   private ServerConnection q;
/*   63 */   private final ServerPing r = new ServerPing();
/*   64 */   private final Random s = new Random();
/*      */   private String serverIp;
/*   66 */   private int u = -1;
/*      */   public WorldServer[] worldServer;
/*      */   private PlayerList v;
/*   69 */   private boolean isRunning = true;
/*      */   private boolean isStopped;
/*      */   private int ticks;
/*      */   protected final Proxy e;
/*      */   public String f;
/*      */   public int g;
/*      */   private boolean onlineMode;
/*      */   private boolean spawnAnimals;
/*      */   private boolean spawnNPCs;
/*      */   private boolean pvpMode;
/*      */   private boolean allowFlight;
/*      */   private String motd;
/*      */   private int F;
/*   82 */   private int G = 0;
/*   83 */   public final long[] h = new long[100];
/*      */   public long[][] i;
/*      */   private KeyPair H;
/*      */   private String I;
/*      */   private String J;
/*      */   private boolean demoMode;
/*      */   private boolean M;
/*      */   private boolean N;
/*   91 */   private String O = "";
/*   92 */   private String P = "";
/*      */   private boolean Q;
/*      */   private long R;
/*      */   private String S;
/*      */   private boolean T;
/*      */   private boolean U;
/*      */   private final YggdrasilAuthenticationService V;
/*      */   private final MinecraftSessionService W;
/*  100 */   private long X = 0L;
/*      */   private final GameProfileRepository Y;
/*      */   private final UserCache Z;
/*  103 */   protected final Queue<FutureTask<?>> j = new ConcurrentLinkedQueue();
/*      */   private Thread serverThread;
/*  105 */   private long ab = az();
/*      */   
/*      */ 
/*  108 */   public List<WorldServer> worlds = new ArrayList();
/*      */   public CraftServer server;
/*      */   public OptionSet options;
/*      */   public ConsoleCommandSender console;
/*      */   public RemoteConsoleCommandSender remoteConsole;
/*      */   public ConsoleReader reader;
/*  114 */   public static int currentTick = (int)(System.currentTimeMillis() / 50L);
/*      */   public final Thread primaryThread;
/*  116 */   public Queue<Runnable> processQueue = new ConcurrentLinkedQueue();
/*      */   
/*      */   public int autosavePeriod;
/*      */   
/*      */   private static final int TPS = 20;
/*      */   private static final int TICK_TIME = 50000000;
/*      */   private static final int SAMPLE_INTERVAL = 100;
/*  123 */   public final double[] recentTps = new double[3];
/*      */   
/*      */   public MinecraftServer(OptionSet options, Proxy proxy, File file1)
/*      */   {
/*  127 */     ResourceLeakDetector.setEnabled(false);
/*  128 */     this.e = proxy;
/*  129 */     l = this;
/*      */     
/*      */ 
/*  132 */     this.Z = new UserCache(this, file1);
/*  133 */     this.b = h();
/*      */     
/*  135 */     this.V = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
/*  136 */     this.W = this.V.createMinecraftSessionService();
/*  137 */     this.Y = this.V.createProfileRepository();
/*      */     
/*  139 */     this.options = options;
/*      */     
/*  141 */     if ((System.console() == null) && (System.getProperty("org.bukkit.craftbukkit.libs.jline.terminal") == null)) {
/*  142 */       System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", "org.bukkit.craftbukkit.libs.jline.UnsupportedTerminal");
/*  143 */       org.bukkit.craftbukkit.Main.useJline = false;
/*      */     }
/*      */     try
/*      */     {
/*  147 */       this.reader = new ConsoleReader(System.in, System.out);
/*  148 */       this.reader.setExpandEvents(false);
/*      */     }
/*      */     catch (Throwable localThrowable) {
/*      */       try {
/*  152 */         System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", "org.bukkit.craftbukkit.libs.jline.UnsupportedTerminal");
/*  153 */         System.setProperty("user.language", "en");
/*  154 */         org.bukkit.craftbukkit.Main.useJline = false;
/*  155 */         this.reader = new ConsoleReader(System.in, System.out);
/*  156 */         this.reader.setExpandEvents(false);
/*      */       } catch (IOException ex) {
/*  158 */         LOGGER.warn(null, ex);
/*      */       }
/*      */     }
/*  161 */     Runtime.getRuntime().addShutdownHook(new ServerShutdownThread(this));
/*      */     
/*  163 */     this.serverThread = (this.primaryThread = new Thread(this, "Server thread"));
/*      */   }
/*      */   
/*      */   public abstract PropertyManager getPropertyManager();
/*      */   
/*      */   protected CommandDispatcher h()
/*      */   {
/*  170 */     return new CommandDispatcher();
/*      */   }
/*      */   
/*      */   protected abstract boolean init() throws IOException;
/*      */   
/*      */   protected void a(String s) {
/*  176 */     if (getConvertable().isConvertable(s)) {
/*  177 */       LOGGER.info("Converting map!");
/*  178 */       b("menu.convertingLevel");
/*  179 */       getConvertable().convert(s, new IProgressUpdate() {
/*  180 */         private long b = System.currentTimeMillis();
/*      */         
/*      */         public void a(String s) {}
/*      */         
/*      */         public void a(int i) {
/*  185 */           if (System.currentTimeMillis() - this.b >= 1000L) {
/*  186 */             this.b = System.currentTimeMillis();
/*  187 */             MinecraftServer.LOGGER.info("Converting... " + i + "%");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */         public void c(String s) {}
/*      */       });
/*      */     }
/*      */   }
/*      */   
/*      */   protected synchronized void b(String s)
/*      */   {
/*  199 */     this.S = s;
/*      */   }
/*      */   
/*      */   protected void a(String s, String s1, long i, WorldType worldtype, String s2) {
/*  203 */     a(s);
/*  204 */     b("menu.loadingLevel");
/*  205 */     this.worldServer = new WorldServer[3];
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
/*  231 */     int worldCount = 3;
/*      */     
/*  233 */     for (int j = 0; j < worldCount; j++)
/*      */     {
/*  235 */       byte dimension = 0;
/*      */       
/*  237 */       if (j == 1) {
/*  238 */         if (getAllowNether()) {
/*  239 */           dimension = -1;
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*  245 */       else if (j == 2) {
/*  246 */         if (this.server.getAllowEnd()) {
/*  247 */           dimension = 1;
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/*  253 */         String worldType = World.Environment.getEnvironment(dimension).toString().toLowerCase();
/*  254 */         String name = s + "_" + worldType;
/*      */         
/*  256 */         org.bukkit.generator.ChunkGenerator gen = this.server.getGenerator(name);
/*  257 */         WorldSettings worldsettings = new WorldSettings(i, getGamemode(), getGenerateStructures(), isHardcore(), worldtype);
/*  258 */         worldsettings.setGeneratorSettings(s2);
/*      */         WorldServer world;
/*  260 */         if (j == 0) {
/*  261 */           IDataManager idatamanager = new ServerNBTManager(this.server.getWorldContainer(), s1, true);
/*  262 */           WorldData worlddata = idatamanager.getWorldData();
/*  263 */           if (worlddata == null) {
/*  264 */             worlddata = new WorldData(worldsettings, s1);
/*      */           }
/*  266 */           worlddata.checkName(s1);
/*  267 */           WorldServer world; WorldServer world; if (X()) {
/*  268 */             world = (WorldServer)new DemoWorldServer(this, idatamanager, worlddata, dimension, this.methodProfiler).b();
/*      */           } else {
/*  270 */             world = (WorldServer)new WorldServer(this, idatamanager, worlddata, dimension, this.methodProfiler, World.Environment.getEnvironment(dimension), gen).b();
/*      */           }
/*      */           
/*  273 */           world.a(worldsettings);
/*  274 */           this.server.scoreboardManager = new org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboardManager(this, world.getScoreboard());
/*      */         } else {
/*  276 */           String dim = "DIM" + dimension;
/*      */           
/*  278 */           File newWorld = new File(new File(name), dim);
/*  279 */           File oldWorld = new File(new File(s), dim);
/*      */           
/*  281 */           if ((!newWorld.isDirectory()) && (oldWorld.isDirectory())) {
/*  282 */             LOGGER.info("---- Migration of old " + worldType + " folder required ----");
/*  283 */             LOGGER.info("Unfortunately due to the way that Minecraft implemented multiworld support in 1.6, Bukkit requires that you move your " + worldType + " folder to a new location in order to operate correctly.");
/*  284 */             LOGGER.info("We will move this folder for you, but it will mean that you need to move it back should you wish to stop using Bukkit in the future.");
/*  285 */             LOGGER.info("Attempting to move " + oldWorld + " to " + newWorld + "...");
/*      */             
/*  287 */             if (newWorld.exists()) {
/*  288 */               LOGGER.warn("A file or folder already exists at " + newWorld + "!");
/*  289 */               LOGGER.info("---- Migration of old " + worldType + " folder failed ----");
/*  290 */             } else if (newWorld.getParentFile().mkdirs()) {
/*  291 */               if (oldWorld.renameTo(newWorld)) {
/*  292 */                 LOGGER.info("Success! To restore " + worldType + " in the future, simply move " + newWorld + " to " + oldWorld);
/*      */                 try
/*      */                 {
/*  295 */                   Files.copy(new File(new File(s), "level.dat"), new File(new File(name), "level.dat"));
/*      */                 } catch (IOException localIOException) {
/*  297 */                   LOGGER.warn("Unable to migrate world data.");
/*      */                 }
/*  299 */                 LOGGER.info("---- Migration of old " + worldType + " folder complete ----");
/*      */               } else {
/*  301 */                 LOGGER.warn("Could not move folder " + oldWorld + " to " + newWorld + "!");
/*  302 */                 LOGGER.info("---- Migration of old " + worldType + " folder failed ----");
/*      */               }
/*      */             } else {
/*  305 */               LOGGER.warn("Could not create path for " + newWorld + "!");
/*  306 */               LOGGER.info("---- Migration of old " + worldType + " folder failed ----");
/*      */             }
/*      */           }
/*      */           
/*  310 */           IDataManager idatamanager = new ServerNBTManager(this.server.getWorldContainer(), name, true);
/*      */           
/*  312 */           WorldData worlddata = idatamanager.getWorldData();
/*  313 */           if (worlddata == null) {
/*  314 */             worlddata = new WorldData(worldsettings, name);
/*      */           }
/*  316 */           worlddata.checkName(name);
/*  317 */           world = (WorldServer)new SecondaryWorldServer(this, idatamanager, dimension, (WorldServer)this.worlds.get(0), this.methodProfiler, worlddata, World.Environment.getEnvironment(dimension), gen).b();
/*      */         }
/*      */         
/*  320 */         this.server.getPluginManager().callEvent(new WorldInitEvent(world.getWorld()));
/*      */         
/*  322 */         world.addIWorldAccess(new WorldManager(this, world));
/*  323 */         if (!T()) {
/*  324 */           world.getWorldData().setGameType(getGamemode());
/*      */         }
/*      */         
/*  327 */         this.worlds.add(world);
/*  328 */         getPlayerList().setPlayerFileData((WorldServer[])this.worlds.toArray(new WorldServer[this.worlds.size()]));
/*      */       }
/*      */     }
/*      */     
/*  332 */     a(getDifficulty());
/*  333 */     k();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void k()
/*      */   {
/*  341 */     int i = 0;
/*      */     
/*  343 */     b("menu.generatingTerrain");
/*      */     
/*      */     WorldServer worldserver;
/*      */     
/*  347 */     for (int m = 0; m < this.worlds.size(); m++) {
/*  348 */       worldserver = (WorldServer)this.worlds.get(m);
/*  349 */       LOGGER.info("Preparing start region for level " + m + " (Seed: " + worldserver.getSeed() + ")");
/*      */       
/*  351 */       if (worldserver.getWorld().getKeepSpawnInMemory())
/*      */       {
/*      */ 
/*      */ 
/*  355 */         BlockPosition blockposition = worldserver.getSpawn();
/*  356 */         long j = az();
/*  357 */         i = 0;
/*      */         
/*  359 */         for (int k = 65344; (k <= 192) && (isRunning()); k += 16) {
/*  360 */           for (int l = 65344; (l <= 192) && (isRunning()); l += 16) {
/*  361 */             long i1 = az();
/*      */             
/*  363 */             if (i1 - j > 1000L) {
/*  364 */               a_("Preparing spawn area", i * 100 / 625);
/*  365 */               j = i1;
/*      */             }
/*      */             
/*  368 */             i++;
/*  369 */             worldserver.chunkProviderServer.getChunkAt(blockposition.getX() + k >> 4, blockposition.getZ() + l >> 4);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  374 */     for (WorldServer world : this.worlds) {
/*  375 */       this.server.getPluginManager().callEvent(new WorldLoadEvent(world.getWorld()));
/*      */     }
/*      */     
/*  378 */     s();
/*      */   }
/*      */   
/*      */   protected void a(String s, IDataManager idatamanager) {
/*  382 */     File file = new File(idatamanager.getDirectory(), "resources.zip");
/*      */     
/*  384 */     if (file.isFile()) {
/*  385 */       setResourcePack("level://" + s + "/" + file.getName(), "");
/*      */     }
/*      */   }
/*      */   
/*      */   public abstract boolean getGenerateStructures();
/*      */   
/*      */   public abstract WorldSettings.EnumGamemode getGamemode();
/*      */   
/*      */   public abstract EnumDifficulty getDifficulty();
/*      */   
/*      */   public abstract boolean isHardcore();
/*      */   
/*      */   public abstract int p();
/*      */   
/*      */   public abstract boolean q();
/*      */   
/*      */   public abstract boolean r();
/*      */   
/*      */   protected void a_(String s, int i)
/*      */   {
/*  405 */     this.f = s;
/*  406 */     this.g = i;
/*  407 */     LOGGER.info(s + ": " + i + "%");
/*      */   }
/*      */   
/*      */   protected void s() {
/*  411 */     this.f = null;
/*  412 */     this.g = 0;
/*      */     
/*  414 */     this.server.enablePlugins(PluginLoadOrder.POSTWORLD);
/*      */   }
/*      */   
/*      */   protected void saveChunks(boolean flag) throws ExceptionWorldConflict {
/*  418 */     if (!this.N) {
/*  419 */       WorldServer[] aworldserver = this.worldServer;
/*  420 */       aworldserver.length;
/*      */       
/*      */ 
/*  423 */       for (int j = 0; j < this.worlds.size(); j++) {
/*  424 */         WorldServer worldserver = (WorldServer)this.worlds.get(j);
/*      */         
/*      */ 
/*  427 */         if (worldserver != null) {
/*  428 */           if (!flag) {
/*  429 */             LOGGER.info("Saving chunks for level '" + worldserver.getWorldData().getName() + "'/" + worldserver.worldProvider.getName());
/*      */           }
/*      */           try
/*      */           {
/*  433 */             worldserver.save(true, null);
/*  434 */             worldserver.saveLevel();
/*      */           } catch (ExceptionWorldConflict exceptionworldconflict) {
/*  436 */             LOGGER.warn(exceptionworldconflict.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*  445 */   private boolean hasStopped = false;
/*  446 */   private final Object stopLock = new Object();
/*      */   
/*      */   public void stop()
/*      */     throws ExceptionWorldConflict
/*      */   {
/*  451 */     synchronized (this.stopLock) {
/*  452 */       if (this.hasStopped) return;
/*  453 */       this.hasStopped = true;
/*      */     }
/*      */     
/*  456 */     if (!this.N) {
/*  457 */       LOGGER.info("Stopping server");
/*      */       
/*  459 */       if (this.server != null) {
/*  460 */         this.server.disablePlugins();
/*      */       }
/*      */       
/*  463 */       if (aq() != null) {
/*  464 */         aq().b();
/*      */       }
/*      */       
/*  467 */       if (this.v != null) {
/*  468 */         LOGGER.info("Saving players");
/*  469 */         this.v.savePlayers();
/*  470 */         this.v.u();
/*  471 */         try { Thread.sleep(100L);
/*      */         } catch (InterruptedException localInterruptedException) {}
/*      */       }
/*  474 */       if (this.worldServer != null) {
/*  475 */         LOGGER.info("Saving worlds");
/*  476 */         saveChunks(false);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  487 */       if (this.n.d()) {
/*  488 */         this.n.e();
/*      */       }
/*      */       
/*  491 */       if (SpigotConfig.saveUserCacheOnStopOnly)
/*      */       {
/*  493 */         LOGGER.info("Saving usercache.json");
/*  494 */         this.Z.c();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getServerIp()
/*      */   {
/*  501 */     return this.serverIp;
/*      */   }
/*      */   
/*      */   public void c(String s) {
/*  505 */     this.serverIp = s;
/*      */   }
/*      */   
/*      */   public boolean isRunning() {
/*  509 */     return this.isRunning;
/*      */   }
/*      */   
/*      */   public void safeShutdown() {
/*  513 */     this.isRunning = false;
/*      */   }
/*      */   
/*      */ 
/*      */   private static double calcTps(double avg, double exp, double tps)
/*      */   {
/*  519 */     return avg * exp + tps * (1.0D - exp);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public void run()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual 931	net/minecraft/server/v1_8_R3/MinecraftServer:init	()Z
/*      */     //   4: ifeq +244 -> 248
/*      */     //   7: aload_0
/*      */     //   8: invokestatic 197	net/minecraft/server/v1_8_R3/MinecraftServer:az	()J
/*      */     //   11: putfield 246	net/minecraft/server/v1_8_R3/MinecraftServer:ab	J
/*      */     //   14: aload_0
/*      */     //   15: getfield 218	net/minecraft/server/v1_8_R3/MinecraftServer:r	Lnet/minecraft/server/v1_8_R3/ServerPing;
/*      */     //   18: new 933	net/minecraft/server/v1_8_R3/ChatComponentText
/*      */     //   21: dup
/*      */     //   22: aload_0
/*      */     //   23: getfield 935	net/minecraft/server/v1_8_R3/MinecraftServer:motd	Ljava/lang/String;
/*      */     //   26: invokespecial 936	net/minecraft/server/v1_8_R3/ChatComponentText:<init>	(Ljava/lang/String;)V
/*      */     //   29: invokevirtual 940	net/minecraft/server/v1_8_R3/ServerPing:setMOTD	(Lnet/minecraft/server/v1_8_R3/IChatBaseComponent;)V
/*      */     //   32: aload_0
/*      */     //   33: getfield 218	net/minecraft/server/v1_8_R3/MinecraftServer:r	Lnet/minecraft/server/v1_8_R3/ServerPing;
/*      */     //   36: new 26	net/minecraft/server/v1_8_R3/ServerPing$ServerData
/*      */     //   39: dup
/*      */     //   40: ldc_w 942
/*      */     //   43: bipush 47
/*      */     //   45: invokespecial 944	net/minecraft/server/v1_8_R3/ServerPing$ServerData:<init>	(Ljava/lang/String;I)V
/*      */     //   48: invokevirtual 948	net/minecraft/server/v1_8_R3/ServerPing:setServerInfo	(Lnet/minecraft/server/v1_8_R3/ServerPing$ServerData;)V
/*      */     //   51: aload_0
/*      */     //   52: aload_0
/*      */     //   53: getfield 218	net/minecraft/server/v1_8_R3/MinecraftServer:r	Lnet/minecraft/server/v1_8_R3/ServerPing;
/*      */     //   56: invokespecial 951	net/minecraft/server/v1_8_R3/MinecraftServer:a	(Lnet/minecraft/server/v1_8_R3/ServerPing;)V
/*      */     //   59: aload_0
/*      */     //   60: getfield 255	net/minecraft/server/v1_8_R3/MinecraftServer:recentTps	[D
/*      */     //   63: ldc2_w 952
/*      */     //   66: invokestatic 959	java/util/Arrays:fill	([DD)V
/*      */     //   69: invokestatic 962	java/lang/System:nanoTime	()J
/*      */     //   72: lstore_1
/*      */     //   73: lconst_0
/*      */     //   74: lstore_3
/*      */     //   75: lload_1
/*      */     //   76: lstore 5
/*      */     //   78: goto +160 -> 238
/*      */     //   81: invokestatic 962	java/lang/System:nanoTime	()J
/*      */     //   84: lstore 7
/*      */     //   86: ldc2_w 963
/*      */     //   89: lload 7
/*      */     //   91: lload_1
/*      */     //   92: lsub
/*      */     //   93: lsub
/*      */     //   94: lload_3
/*      */     //   95: lsub
/*      */     //   96: lstore 9
/*      */     //   98: lload 9
/*      */     //   100: lconst_0
/*      */     //   101: lcmp
/*      */     //   102: ifle +17 -> 119
/*      */     //   105: lload 9
/*      */     //   107: ldc2_w 965
/*      */     //   110: ldiv
/*      */     //   111: invokestatic 897	java/lang/Thread:sleep	(J)V
/*      */     //   114: lconst_0
/*      */     //   115: lstore_3
/*      */     //   116: goto +122 -> 238
/*      */     //   119: ldc2_w 967
/*      */     //   122: lload 9
/*      */     //   124: invokestatic 974	java/lang/Math:abs	(J)J
/*      */     //   127: invokestatic 978	java/lang/Math:min	(JJ)J
/*      */     //   130: lstore_3
/*      */     //   131: getstatic 184	net/minecraft/server/v1_8_R3/MinecraftServer:currentTick	I
/*      */     //   134: dup
/*      */     //   135: iconst_1
/*      */     //   136: iadd
/*      */     //   137: putstatic 184	net/minecraft/server/v1_8_R3/MinecraftServer:currentTick	I
/*      */     //   140: bipush 100
/*      */     //   142: irem
/*      */     //   143: ifne +83 -> 226
/*      */     //   146: ldc2_w 979
/*      */     //   149: lload 7
/*      */     //   151: lload 5
/*      */     //   153: lsub
/*      */     //   154: l2d
/*      */     //   155: ddiv
/*      */     //   156: ldc2_w 981
/*      */     //   159: dmul
/*      */     //   160: dstore 11
/*      */     //   162: aload_0
/*      */     //   163: getfield 255	net/minecraft/server/v1_8_R3/MinecraftServer:recentTps	[D
/*      */     //   166: iconst_0
/*      */     //   167: aload_0
/*      */     //   168: getfield 255	net/minecraft/server/v1_8_R3/MinecraftServer:recentTps	[D
/*      */     //   171: iconst_0
/*      */     //   172: daload
/*      */     //   173: ldc2_w 983
/*      */     //   176: dload 11
/*      */     //   178: invokestatic 986	net/minecraft/server/v1_8_R3/MinecraftServer:calcTps	(DDD)D
/*      */     //   181: dastore
/*      */     //   182: aload_0
/*      */     //   183: getfield 255	net/minecraft/server/v1_8_R3/MinecraftServer:recentTps	[D
/*      */     //   186: iconst_1
/*      */     //   187: aload_0
/*      */     //   188: getfield 255	net/minecraft/server/v1_8_R3/MinecraftServer:recentTps	[D
/*      */     //   191: iconst_1
/*      */     //   192: daload
/*      */     //   193: ldc2_w 987
/*      */     //   196: dload 11
/*      */     //   198: invokestatic 986	net/minecraft/server/v1_8_R3/MinecraftServer:calcTps	(DDD)D
/*      */     //   201: dastore
/*      */     //   202: aload_0
/*      */     //   203: getfield 255	net/minecraft/server/v1_8_R3/MinecraftServer:recentTps	[D
/*      */     //   206: iconst_2
/*      */     //   207: aload_0
/*      */     //   208: getfield 255	net/minecraft/server/v1_8_R3/MinecraftServer:recentTps	[D
/*      */     //   211: iconst_2
/*      */     //   212: daload
/*      */     //   213: ldc2_w 989
/*      */     //   216: dload 11
/*      */     //   218: invokestatic 986	net/minecraft/server/v1_8_R3/MinecraftServer:calcTps	(DDD)D
/*      */     //   221: dastore
/*      */     //   222: lload 7
/*      */     //   224: lstore 5
/*      */     //   226: lload 7
/*      */     //   228: lstore_1
/*      */     //   229: aload_0
/*      */     //   230: invokevirtual 993	net/minecraft/server/v1_8_R3/MinecraftServer:A	()V
/*      */     //   233: aload_0
/*      */     //   234: iconst_1
/*      */     //   235: putfield 995	net/minecraft/server/v1_8_R3/MinecraftServer:Q	Z
/*      */     //   238: aload_0
/*      */     //   239: getfield 227	net/minecraft/server/v1_8_R3/MinecraftServer:isRunning	Z
/*      */     //   242: ifne -161 -> 81
/*      */     //   245: goto +424 -> 669
/*      */     //   248: aload_0
/*      */     //   249: aconst_null
/*      */     //   250: invokevirtual 998	net/minecraft/server/v1_8_R3/MinecraftServer:a	(Lnet/minecraft/server/v1_8_R3/CrashReport;)V
/*      */     //   253: goto +416 -> 669
/*      */     //   256: astore 13
/*      */     //   258: getstatic 164	net/minecraft/server/v1_8_R3/MinecraftServer:LOGGER	Lorg/apache/logging/log4j/Logger;
/*      */     //   261: ldc_w 1000
/*      */     //   264: aload 13
/*      */     //   266: invokeinterface 1003 3 0
/*      */     //   271: aload 13
/*      */     //   273: invokevirtual 1007	java/lang/Throwable:getCause	()Ljava/lang/Throwable;
/*      */     //   276: ifnull +19 -> 295
/*      */     //   279: getstatic 164	net/minecraft/server/v1_8_R3/MinecraftServer:LOGGER	Lorg/apache/logging/log4j/Logger;
/*      */     //   282: ldc_w 1009
/*      */     //   285: aload 13
/*      */     //   287: invokevirtual 1007	java/lang/Throwable:getCause	()Ljava/lang/Throwable;
/*      */     //   290: invokeinterface 1003 3 0
/*      */     //   295: aconst_null
/*      */     //   296: astore 14
/*      */     //   298: aload 13
/*      */     //   300: instanceof 1011
/*      */     //   303: ifeq +20 -> 323
/*      */     //   306: aload_0
/*      */     //   307: aload 13
/*      */     //   309: checkcast 1011	net/minecraft/server/v1_8_R3/ReportedException
/*      */     //   312: invokevirtual 1014	net/minecraft/server/v1_8_R3/ReportedException:a	()Lnet/minecraft/server/v1_8_R3/CrashReport;
/*      */     //   315: invokevirtual 1017	net/minecraft/server/v1_8_R3/MinecraftServer:b	(Lnet/minecraft/server/v1_8_R3/CrashReport;)Lnet/minecraft/server/v1_8_R3/CrashReport;
/*      */     //   318: astore 14
/*      */     //   320: goto +21 -> 341
/*      */     //   323: aload_0
/*      */     //   324: new 1019	net/minecraft/server/v1_8_R3/CrashReport
/*      */     //   327: dup
/*      */     //   328: ldc_w 1021
/*      */     //   331: aload 13
/*      */     //   333: invokespecial 1023	net/minecraft/server/v1_8_R3/CrashReport:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/*      */     //   336: invokevirtual 1017	net/minecraft/server/v1_8_R3/MinecraftServer:b	(Lnet/minecraft/server/v1_8_R3/CrashReport;)Lnet/minecraft/server/v1_8_R3/CrashReport;
/*      */     //   339: astore 14
/*      */     //   341: new 166	java/io/File
/*      */     //   344: dup
/*      */     //   345: new 166	java/io/File
/*      */     //   348: dup
/*      */     //   349: aload_0
/*      */     //   350: invokevirtual 1026	net/minecraft/server/v1_8_R3/MinecraftServer:y	()Ljava/io/File;
/*      */     //   353: ldc_w 1028
/*      */     //   356: invokespecial 557	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
/*      */     //   359: new 459	java/lang/StringBuilder
/*      */     //   362: dup
/*      */     //   363: ldc_w 1030
/*      */     //   366: invokespecial 464	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*      */     //   369: new 1032	java/text/SimpleDateFormat
/*      */     //   372: dup
/*      */     //   373: ldc_w 1034
/*      */     //   376: invokespecial 1035	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
/*      */     //   379: new 1037	java/util/Date
/*      */     //   382: dup
/*      */     //   383: invokespecial 1038	java/util/Date:<init>	()V
/*      */     //   386: invokevirtual 1042	java/text/SimpleDateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
/*      */     //   389: invokevirtual 470	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   392: ldc_w 1044
/*      */     //   395: invokevirtual 470	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   398: invokevirtual 471	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   401: invokespecial 557	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
/*      */     //   404: astore 15
/*      */     //   406: aload 14
/*      */     //   408: aload 15
/*      */     //   410: invokevirtual 1046	net/minecraft/server/v1_8_R3/CrashReport:a	(Ljava/io/File;)Z
/*      */     //   413: ifeq +35 -> 448
/*      */     //   416: getstatic 164	net/minecraft/server/v1_8_R3/MinecraftServer:LOGGER	Lorg/apache/logging/log4j/Logger;
/*      */     //   419: new 459	java/lang/StringBuilder
/*      */     //   422: dup
/*      */     //   423: ldc_w 1048
/*      */     //   426: invokespecial 464	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*      */     //   429: aload 15
/*      */     //   431: invokevirtual 1051	java/io/File:getAbsolutePath	()Ljava/lang/String;
/*      */     //   434: invokevirtual 470	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   437: invokevirtual 471	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   440: invokeinterface 1053 2 0
/*      */     //   445: goto +14 -> 459
/*      */     //   448: getstatic 164	net/minecraft/server/v1_8_R3/MinecraftServer:LOGGER	Lorg/apache/logging/log4j/Logger;
/*      */     //   451: ldc_w 1055
/*      */     //   454: invokeinterface 1053 2 0
/*      */     //   459: aload_0
/*      */     //   460: aload 14
/*      */     //   462: invokevirtual 998	net/minecraft/server/v1_8_R3/MinecraftServer:a	(Lnet/minecraft/server/v1_8_R3/CrashReport;)V
/*      */     //   465: invokestatic 1060	org/spigotmc/WatchdogThread:doStop	()V
/*      */     //   468: aload_0
/*      */     //   469: iconst_1
/*      */     //   470: putfield 1062	net/minecraft/server/v1_8_R3/MinecraftServer:isStopped	Z
/*      */     //   473: aload_0
/*      */     //   474: invokevirtual 1064	net/minecraft/server/v1_8_R3/MinecraftServer:stop	()V
/*      */     //   477: goto +66 -> 543
/*      */     //   480: astore 16
/*      */     //   482: getstatic 164	net/minecraft/server/v1_8_R3/MinecraftServer:LOGGER	Lorg/apache/logging/log4j/Logger;
/*      */     //   485: ldc_w 1066
/*      */     //   488: aload 16
/*      */     //   490: invokeinterface 1003 3 0
/*      */     //   495: aload_0
/*      */     //   496: getfield 351	net/minecraft/server/v1_8_R3/MinecraftServer:reader	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader;
/*      */     //   499: invokevirtual 1070	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getTerminal	()Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   502: invokeinterface 1075 1 0
/*      */     //   507: goto +4 -> 511
/*      */     //   510: pop
/*      */     //   511: aload_0
/*      */     //   512: invokevirtual 1078	net/minecraft/server/v1_8_R3/MinecraftServer:z	()V
/*      */     //   515: goto +252 -> 767
/*      */     //   518: astore 17
/*      */     //   520: aload_0
/*      */     //   521: getfield 351	net/minecraft/server/v1_8_R3/MinecraftServer:reader	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader;
/*      */     //   524: invokevirtual 1070	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getTerminal	()Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   527: invokeinterface 1075 1 0
/*      */     //   532: goto +4 -> 536
/*      */     //   535: pop
/*      */     //   536: aload_0
/*      */     //   537: invokevirtual 1078	net/minecraft/server/v1_8_R3/MinecraftServer:z	()V
/*      */     //   540: aload 17
/*      */     //   542: athrow
/*      */     //   543: aload_0
/*      */     //   544: getfield 351	net/minecraft/server/v1_8_R3/MinecraftServer:reader	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader;
/*      */     //   547: invokevirtual 1070	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getTerminal	()Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   550: invokeinterface 1075 1 0
/*      */     //   555: goto +4 -> 559
/*      */     //   558: pop
/*      */     //   559: aload_0
/*      */     //   560: invokevirtual 1078	net/minecraft/server/v1_8_R3/MinecraftServer:z	()V
/*      */     //   563: goto +204 -> 767
/*      */     //   566: astore 18
/*      */     //   568: invokestatic 1060	org/spigotmc/WatchdogThread:doStop	()V
/*      */     //   571: aload_0
/*      */     //   572: iconst_1
/*      */     //   573: putfield 1062	net/minecraft/server/v1_8_R3/MinecraftServer:isStopped	Z
/*      */     //   576: aload_0
/*      */     //   577: invokevirtual 1064	net/minecraft/server/v1_8_R3/MinecraftServer:stop	()V
/*      */     //   580: goto +66 -> 646
/*      */     //   583: astore 16
/*      */     //   585: getstatic 164	net/minecraft/server/v1_8_R3/MinecraftServer:LOGGER	Lorg/apache/logging/log4j/Logger;
/*      */     //   588: ldc_w 1066
/*      */     //   591: aload 16
/*      */     //   593: invokeinterface 1003 3 0
/*      */     //   598: aload_0
/*      */     //   599: getfield 351	net/minecraft/server/v1_8_R3/MinecraftServer:reader	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader;
/*      */     //   602: invokevirtual 1070	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getTerminal	()Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   605: invokeinterface 1075 1 0
/*      */     //   610: goto +4 -> 614
/*      */     //   613: pop
/*      */     //   614: aload_0
/*      */     //   615: invokevirtual 1078	net/minecraft/server/v1_8_R3/MinecraftServer:z	()V
/*      */     //   618: goto +48 -> 666
/*      */     //   621: astore 17
/*      */     //   623: aload_0
/*      */     //   624: getfield 351	net/minecraft/server/v1_8_R3/MinecraftServer:reader	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader;
/*      */     //   627: invokevirtual 1070	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getTerminal	()Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   630: invokeinterface 1075 1 0
/*      */     //   635: goto +4 -> 639
/*      */     //   638: pop
/*      */     //   639: aload_0
/*      */     //   640: invokevirtual 1078	net/minecraft/server/v1_8_R3/MinecraftServer:z	()V
/*      */     //   643: aload 17
/*      */     //   645: athrow
/*      */     //   646: aload_0
/*      */     //   647: getfield 351	net/minecraft/server/v1_8_R3/MinecraftServer:reader	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader;
/*      */     //   650: invokevirtual 1070	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getTerminal	()Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   653: invokeinterface 1075 1 0
/*      */     //   658: goto +4 -> 662
/*      */     //   661: pop
/*      */     //   662: aload_0
/*      */     //   663: invokevirtual 1078	net/minecraft/server/v1_8_R3/MinecraftServer:z	()V
/*      */     //   666: aload 18
/*      */     //   668: athrow
/*      */     //   669: invokestatic 1060	org/spigotmc/WatchdogThread:doStop	()V
/*      */     //   672: aload_0
/*      */     //   673: iconst_1
/*      */     //   674: putfield 1062	net/minecraft/server/v1_8_R3/MinecraftServer:isStopped	Z
/*      */     //   677: aload_0
/*      */     //   678: invokevirtual 1064	net/minecraft/server/v1_8_R3/MinecraftServer:stop	()V
/*      */     //   681: goto +66 -> 747
/*      */     //   684: astore 16
/*      */     //   686: getstatic 164	net/minecraft/server/v1_8_R3/MinecraftServer:LOGGER	Lorg/apache/logging/log4j/Logger;
/*      */     //   689: ldc_w 1066
/*      */     //   692: aload 16
/*      */     //   694: invokeinterface 1003 3 0
/*      */     //   699: aload_0
/*      */     //   700: getfield 351	net/minecraft/server/v1_8_R3/MinecraftServer:reader	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader;
/*      */     //   703: invokevirtual 1070	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getTerminal	()Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   706: invokeinterface 1075 1 0
/*      */     //   711: goto +4 -> 715
/*      */     //   714: pop
/*      */     //   715: aload_0
/*      */     //   716: invokevirtual 1078	net/minecraft/server/v1_8_R3/MinecraftServer:z	()V
/*      */     //   719: goto +48 -> 767
/*      */     //   722: astore 17
/*      */     //   724: aload_0
/*      */     //   725: getfield 351	net/minecraft/server/v1_8_R3/MinecraftServer:reader	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader;
/*      */     //   728: invokevirtual 1070	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getTerminal	()Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   731: invokeinterface 1075 1 0
/*      */     //   736: goto +4 -> 740
/*      */     //   739: pop
/*      */     //   740: aload_0
/*      */     //   741: invokevirtual 1078	net/minecraft/server/v1_8_R3/MinecraftServer:z	()V
/*      */     //   744: aload 17
/*      */     //   746: athrow
/*      */     //   747: aload_0
/*      */     //   748: getfield 351	net/minecraft/server/v1_8_R3/MinecraftServer:reader	Lorg/bukkit/craftbukkit/libs/jline/console/ConsoleReader;
/*      */     //   751: invokevirtual 1070	org/bukkit/craftbukkit/libs/jline/console/ConsoleReader:getTerminal	()Lorg/bukkit/craftbukkit/libs/jline/Terminal;
/*      */     //   754: invokeinterface 1075 1 0
/*      */     //   759: goto +4 -> 763
/*      */     //   762: pop
/*      */     //   763: aload_0
/*      */     //   764: invokevirtual 1078	net/minecraft/server/v1_8_R3/MinecraftServer:z	()V
/*      */     //   767: return
/*      */     // Line number table:
/*      */     //   Java source line #525	-> byte code offset #0
/*      */     //   Java source line #526	-> byte code offset #7
/*      */     //   Java source line #529	-> byte code offset #14
/*      */     //   Java source line #530	-> byte code offset #32
/*      */     //   Java source line #531	-> byte code offset #51
/*      */     //   Java source line #534	-> byte code offset #59
/*      */     //   Java source line #535	-> byte code offset #69
/*      */     //   Java source line #536	-> byte code offset #78
/*      */     //   Java source line #537	-> byte code offset #81
/*      */     //   Java source line #538	-> byte code offset #86
/*      */     //   Java source line #539	-> byte code offset #98
/*      */     //   Java source line #540	-> byte code offset #105
/*      */     //   Java source line #541	-> byte code offset #114
/*      */     //   Java source line #542	-> byte code offset #116
/*      */     //   Java source line #544	-> byte code offset #119
/*      */     //   Java source line #547	-> byte code offset #131
/*      */     //   Java source line #549	-> byte code offset #146
/*      */     //   Java source line #550	-> byte code offset #162
/*      */     //   Java source line #551	-> byte code offset #182
/*      */     //   Java source line #552	-> byte code offset #202
/*      */     //   Java source line #553	-> byte code offset #222
/*      */     //   Java source line #555	-> byte code offset #226
/*      */     //   Java source line #557	-> byte code offset #229
/*      */     //   Java source line #558	-> byte code offset #233
/*      */     //   Java source line #536	-> byte code offset #238
/*      */     //   Java source line #561	-> byte code offset #245
/*      */     //   Java source line #562	-> byte code offset #248
/*      */     //   Java source line #564	-> byte code offset #253
/*      */     //   Java source line #565	-> byte code offset #258
/*      */     //   Java source line #567	-> byte code offset #271
/*      */     //   Java source line #569	-> byte code offset #279
/*      */     //   Java source line #572	-> byte code offset #295
/*      */     //   Java source line #574	-> byte code offset #298
/*      */     //   Java source line #575	-> byte code offset #306
/*      */     //   Java source line #576	-> byte code offset #320
/*      */     //   Java source line #577	-> byte code offset #323
/*      */     //   Java source line #580	-> byte code offset #341
/*      */     //   Java source line #582	-> byte code offset #406
/*      */     //   Java source line #583	-> byte code offset #416
/*      */     //   Java source line #584	-> byte code offset #445
/*      */     //   Java source line #585	-> byte code offset #448
/*      */     //   Java source line #588	-> byte code offset #459
/*      */     //   Java source line #591	-> byte code offset #465
/*      */     //   Java source line #592	-> byte code offset #468
/*      */     //   Java source line #593	-> byte code offset #473
/*      */     //   Java source line #594	-> byte code offset #477
/*      */     //   Java source line #595	-> byte code offset #482
/*      */     //   Java source line #599	-> byte code offset #495
/*      */     //   Java source line #600	-> byte code offset #507
/*      */     //   Java source line #603	-> byte code offset #511
/*      */     //   Java source line #596	-> byte code offset #518
/*      */     //   Java source line #599	-> byte code offset #520
/*      */     //   Java source line #600	-> byte code offset #532
/*      */     //   Java source line #603	-> byte code offset #536
/*      */     //   Java source line #604	-> byte code offset #540
/*      */     //   Java source line #599	-> byte code offset #543
/*      */     //   Java source line #600	-> byte code offset #555
/*      */     //   Java source line #603	-> byte code offset #559
/*      */     //   Java source line #604	-> byte code offset #563
/*      */     //   Java source line #589	-> byte code offset #566
/*      */     //   Java source line #591	-> byte code offset #568
/*      */     //   Java source line #592	-> byte code offset #571
/*      */     //   Java source line #593	-> byte code offset #576
/*      */     //   Java source line #594	-> byte code offset #580
/*      */     //   Java source line #595	-> byte code offset #585
/*      */     //   Java source line #599	-> byte code offset #598
/*      */     //   Java source line #600	-> byte code offset #610
/*      */     //   Java source line #603	-> byte code offset #614
/*      */     //   Java source line #596	-> byte code offset #621
/*      */     //   Java source line #599	-> byte code offset #623
/*      */     //   Java source line #600	-> byte code offset #635
/*      */     //   Java source line #603	-> byte code offset #639
/*      */     //   Java source line #604	-> byte code offset #643
/*      */     //   Java source line #599	-> byte code offset #646
/*      */     //   Java source line #600	-> byte code offset #658
/*      */     //   Java source line #603	-> byte code offset #662
/*      */     //   Java source line #606	-> byte code offset #666
/*      */     //   Java source line #591	-> byte code offset #669
/*      */     //   Java source line #592	-> byte code offset #672
/*      */     //   Java source line #593	-> byte code offset #677
/*      */     //   Java source line #594	-> byte code offset #681
/*      */     //   Java source line #595	-> byte code offset #686
/*      */     //   Java source line #599	-> byte code offset #699
/*      */     //   Java source line #600	-> byte code offset #711
/*      */     //   Java source line #603	-> byte code offset #715
/*      */     //   Java source line #596	-> byte code offset #722
/*      */     //   Java source line #599	-> byte code offset #724
/*      */     //   Java source line #600	-> byte code offset #736
/*      */     //   Java source line #603	-> byte code offset #740
/*      */     //   Java source line #604	-> byte code offset #744
/*      */     //   Java source line #599	-> byte code offset #747
/*      */     //   Java source line #600	-> byte code offset #759
/*      */     //   Java source line #603	-> byte code offset #763
/*      */     //   Java source line #608	-> byte code offset #767
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	768	0	this	MinecraftServer
/*      */     //   72	157	1	lastTick	long
/*      */     //   74	57	3	catchupTime	long
/*      */     //   76	149	5	tickSection	long
/*      */     //   84	143	7	curTime	long
/*      */     //   96	27	9	wait	long
/*      */     //   160	57	11	currentTps	double
/*      */     //   256	76	13	throwable	Throwable
/*      */     //   296	165	14	crashreport	CrashReport
/*      */     //   404	26	15	file	File
/*      */     //   480	9	16	throwable1	Throwable
/*      */     //   583	9	16	throwable1	Throwable
/*      */     //   684	9	16	throwable1	Throwable
/*      */     //   510	1	17	localException1	Exception
/*      */     //   518	23	17	localObject1	Object
/*      */     //   621	23	17	localObject2	Object
/*      */     //   722	23	17	localObject3	Object
/*      */     //   535	1	18	localException2	Exception
/*      */     //   566	101	18	localObject4	Object
/*      */     //   558	1	19	localException3	Exception
/*      */     //   613	1	20	localException4	Exception
/*      */     //   638	1	21	localException5	Exception
/*      */     //   661	1	22	localException6	Exception
/*      */     //   714	1	23	localException7	Exception
/*      */     //   739	1	24	localException8	Exception
/*      */     //   762	1	25	localException9	Exception
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   0	253	256	java/lang/Throwable
/*      */     //   465	477	480	java/lang/Throwable
/*      */     //   495	507	510	java/lang/Exception
/*      */     //   465	495	518	finally
/*      */     //   520	532	535	java/lang/Exception
/*      */     //   543	555	558	java/lang/Exception
/*      */     //   0	465	566	finally
/*      */     //   568	580	583	java/lang/Throwable
/*      */     //   598	610	613	java/lang/Exception
/*      */     //   568	598	621	finally
/*      */     //   623	635	638	java/lang/Exception
/*      */     //   646	658	661	java/lang/Exception
/*      */     //   669	681	684	java/lang/Throwable
/*      */     //   699	711	714	java/lang/Exception
/*      */     //   669	699	722	finally
/*      */     //   724	736	739	java/lang/Exception
/*      */     //   747	759	762	java/lang/Exception
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void a(ServerPing serverping)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: ldc_w 1091
/*      */     //   4: invokevirtual 1094	net/minecraft/server/v1_8_R3/MinecraftServer:d	(Ljava/lang/String;)Ljava/io/File;
/*      */     //   7: astore_2
/*      */     //   8: aload_2
/*      */     //   9: invokevirtual 805	java/io/File:isFile	()Z
/*      */     //   12: ifeq +155 -> 167
/*      */     //   15: invokestatic 1100	io/netty/buffer/Unpooled:buffer	()Lio/netty/buffer/ByteBuf;
/*      */     //   18: astore_3
/*      */     //   19: aload_2
/*      */     //   20: invokestatic 1106	javax/imageio/ImageIO:read	(Ljava/io/File;)Ljava/awt/image/BufferedImage;
/*      */     //   23: astore 4
/*      */     //   25: aload 4
/*      */     //   27: invokevirtual 1111	java/awt/image/BufferedImage:getWidth	()I
/*      */     //   30: bipush 64
/*      */     //   32: if_icmpne +7 -> 39
/*      */     //   35: iconst_1
/*      */     //   36: goto +4 -> 40
/*      */     //   39: iconst_0
/*      */     //   40: ldc_w 1115
/*      */     //   43: iconst_0
/*      */     //   44: anewarray 4	java/lang/Object
/*      */     //   47: invokestatic 1121	org/apache/commons/lang3/Validate:validState	(ZLjava/lang/String;[Ljava/lang/Object;)V
/*      */     //   50: aload 4
/*      */     //   52: invokevirtual 1124	java/awt/image/BufferedImage:getHeight	()I
/*      */     //   55: bipush 64
/*      */     //   57: if_icmpne +7 -> 64
/*      */     //   60: iconst_1
/*      */     //   61: goto +4 -> 65
/*      */     //   64: iconst_0
/*      */     //   65: ldc_w 1126
/*      */     //   68: iconst_0
/*      */     //   69: anewarray 4	java/lang/Object
/*      */     //   72: invokestatic 1121	org/apache/commons/lang3/Validate:validState	(ZLjava/lang/String;[Ljava/lang/Object;)V
/*      */     //   75: aload 4
/*      */     //   77: ldc_w 1128
/*      */     //   80: new 1130	io/netty/buffer/ByteBufOutputStream
/*      */     //   83: dup
/*      */     //   84: aload_3
/*      */     //   85: invokespecial 1133	io/netty/buffer/ByteBufOutputStream:<init>	(Lio/netty/buffer/ByteBuf;)V
/*      */     //   88: invokestatic 1137	javax/imageio/ImageIO:write	(Ljava/awt/image/RenderedImage;Ljava/lang/String;Ljava/io/OutputStream;)Z
/*      */     //   91: pop
/*      */     //   92: aload_3
/*      */     //   93: invokestatic 1143	io/netty/handler/codec/base64/Base64:encode	(Lio/netty/buffer/ByteBuf;)Lio/netty/buffer/ByteBuf;
/*      */     //   96: astore 5
/*      */     //   98: aload_1
/*      */     //   99: new 459	java/lang/StringBuilder
/*      */     //   102: dup
/*      */     //   103: ldc_w 1145
/*      */     //   106: invokespecial 464	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*      */     //   109: aload 5
/*      */     //   111: getstatic 1151	com/google/common/base/Charsets:UTF_8	Ljava/nio/charset/Charset;
/*      */     //   114: invokevirtual 1154	io/netty/buffer/ByteBuf:toString	(Ljava/nio/charset/Charset;)Ljava/lang/String;
/*      */     //   117: invokevirtual 470	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   120: invokevirtual 471	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   123: invokevirtual 1157	net/minecraft/server/v1_8_R3/ServerPing:setFavicon	(Ljava/lang/String;)V
/*      */     //   126: goto +36 -> 162
/*      */     //   129: astore 4
/*      */     //   131: getstatic 164	net/minecraft/server/v1_8_R3/MinecraftServer:LOGGER	Lorg/apache/logging/log4j/Logger;
/*      */     //   134: ldc_w 1159
/*      */     //   137: aload 4
/*      */     //   139: invokeinterface 1003 3 0
/*      */     //   144: aload_3
/*      */     //   145: invokevirtual 1162	io/netty/buffer/ByteBuf:release	()Z
/*      */     //   148: pop
/*      */     //   149: goto +18 -> 167
/*      */     //   152: astore 6
/*      */     //   154: aload_3
/*      */     //   155: invokevirtual 1162	io/netty/buffer/ByteBuf:release	()Z
/*      */     //   158: pop
/*      */     //   159: aload 6
/*      */     //   161: athrow
/*      */     //   162: aload_3
/*      */     //   163: invokevirtual 1162	io/netty/buffer/ByteBuf:release	()Z
/*      */     //   166: pop
/*      */     //   167: return
/*      */     // Line number table:
/*      */     //   Java source line #611	-> byte code offset #0
/*      */     //   Java source line #613	-> byte code offset #8
/*      */     //   Java source line #614	-> byte code offset #15
/*      */     //   Java source line #617	-> byte code offset #19
/*      */     //   Java source line #619	-> byte code offset #25
/*      */     //   Java source line #620	-> byte code offset #50
/*      */     //   Java source line #621	-> byte code offset #75
/*      */     //   Java source line #622	-> byte code offset #92
/*      */     //   Java source line #624	-> byte code offset #98
/*      */     //   Java source line #625	-> byte code offset #126
/*      */     //   Java source line #626	-> byte code offset #131
/*      */     //   Java source line #628	-> byte code offset #144
/*      */     //   Java source line #627	-> byte code offset #152
/*      */     //   Java source line #628	-> byte code offset #154
/*      */     //   Java source line #629	-> byte code offset #159
/*      */     //   Java source line #628	-> byte code offset #162
/*      */     //   Java source line #632	-> byte code offset #167
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	168	0	this	MinecraftServer
/*      */     //   0	168	1	serverping	ServerPing
/*      */     //   7	13	2	file	File
/*      */     //   18	145	3	bytebuf	io.netty.buffer.ByteBuf
/*      */     //   23	53	4	bufferedimage	java.awt.image.BufferedImage
/*      */     //   129	9	4	exception	Exception
/*      */     //   96	14	5	bytebuf1	io.netty.buffer.ByteBuf
/*      */     //   152	8	6	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   19	126	129	java/lang/Exception
/*      */     //   19	144	152	finally
/*      */   }
/*      */   
/*      */   public File y()
/*      */   {
/*  635 */     return new File(".");
/*      */   }
/*      */   
/*      */   protected void a(CrashReport crashreport) {}
/*      */   
/*      */   protected void z() {}
/*      */   
/*      */   protected void A() throws ExceptionWorldConflict {
/*  643 */     SpigotTimings.serverTickTimer.startTiming();
/*  644 */     long i = System.nanoTime();
/*      */     
/*  646 */     this.ticks += 1;
/*  647 */     if (this.T) {
/*  648 */       this.T = false;
/*  649 */       this.methodProfiler.a = true;
/*  650 */       this.methodProfiler.a();
/*      */     }
/*      */     
/*  653 */     this.methodProfiler.a("root");
/*  654 */     B();
/*  655 */     int j; if (i - this.X >= 5000000000L) {
/*  656 */       this.X = i;
/*  657 */       this.r.setPlayerSample(new ServerPing.ServerPingPlayerSample(J(), I()));
/*  658 */       GameProfile[] agameprofile = new GameProfile[Math.min(I(), 12)];
/*  659 */       j = MathHelper.nextInt(this.s, 0, I() - agameprofile.length);
/*      */       
/*  661 */       for (int k = 0; k < agameprofile.length; k++) {
/*  662 */         agameprofile[k] = ((EntityPlayer)this.v.v().get(j + k)).getProfile();
/*      */       }
/*      */       
/*  665 */       Collections.shuffle(Arrays.asList(agameprofile));
/*  666 */       this.r.b().a(agameprofile);
/*      */     }
/*      */     
/*  669 */     if ((this.autosavePeriod > 0) && (this.ticks % this.autosavePeriod == 0)) {
/*  670 */       SpigotTimings.worldSaveTimer.startTiming();
/*  671 */       this.methodProfiler.a("save");
/*  672 */       this.v.savePlayers();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  677 */       this.server.playerCommandState = true;
/*  678 */       for (World world : this.worlds) {
/*  679 */         world.getWorld().save(false);
/*      */       }
/*  681 */       this.server.playerCommandState = false;
/*      */       
/*      */ 
/*  684 */       this.methodProfiler.b();
/*  685 */       SpigotTimings.worldSaveTimer.stopTiming();
/*      */     }
/*      */     
/*  688 */     this.methodProfiler.a("tallying");
/*  689 */     this.h[(this.ticks % 100)] = (System.nanoTime() - i);
/*  690 */     this.methodProfiler.b();
/*  691 */     this.methodProfiler.a("snooper");
/*  692 */     if ((getSnooperEnabled()) && (!this.n.d()) && (this.ticks > 100)) {
/*  693 */       this.n.a();
/*      */     }
/*      */     
/*  696 */     if ((getSnooperEnabled()) && (this.ticks % 6000 == 0)) {
/*  697 */       this.n.b();
/*      */     }
/*      */     
/*  700 */     this.methodProfiler.b();
/*  701 */     this.methodProfiler.b();
/*  702 */     org.spigotmc.WatchdogThread.tick();
/*  703 */     SpigotTimings.serverTickTimer.stopTiming();
/*  704 */     CustomTimingsHandler.tick();
/*      */   }
/*      */   
/*      */   public void B() {
/*  708 */     this.methodProfiler.a("jobs");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  713 */     int count = this.j.size();
/*  714 */     FutureTask<?> entry; while ((count-- > 0) && ((entry = (FutureTask)this.j.poll()) != null)) { FutureTask<?> entry;
/*  715 */       SystemUtils.a(entry, LOGGER);
/*      */     }
/*      */     
/*      */ 
/*  719 */     this.methodProfiler.c("levels");
/*      */     
/*  721 */     SpigotTimings.schedulerTimer.startTiming();
/*      */     
/*  723 */     this.server.getScheduler().mainThreadHeartbeat(this.ticks);
/*  724 */     SpigotTimings.schedulerTimer.stopTiming();
/*      */     
/*      */ 
/*  727 */     SpigotTimings.processQueueTimer.startTiming();
/*  728 */     while (!this.processQueue.isEmpty()) {
/*  729 */       ((Runnable)this.processQueue.remove()).run();
/*      */     }
/*  731 */     SpigotTimings.processQueueTimer.stopTiming();
/*      */     
/*  733 */     SpigotTimings.chunkIOTickTimer.startTiming();
/*  734 */     ChunkIOExecutor.tick();
/*  735 */     SpigotTimings.chunkIOTickTimer.stopTiming();
/*      */     
/*  737 */     SpigotTimings.timeUpdateTimer.startTiming();
/*      */     
/*  739 */     if (this.ticks % 20 == 0) {
/*  740 */       for (int i = 0; i < getPlayerList().players.size(); i++) {
/*  741 */         EntityPlayer entityplayer = (EntityPlayer)getPlayerList().players.get(i);
/*  742 */         entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateTime(entityplayer.world.getTime(), entityplayer.getPlayerTime(), entityplayer.world.getGameRules().getBoolean("doDaylightCycle")));
/*      */       }
/*      */     }
/*  745 */     SpigotTimings.timeUpdateTimer.stopTiming();
/*      */     
/*      */ 
/*      */ 
/*  749 */     for (int i = 0; i < this.worlds.size(); i++) {
/*  750 */       System.nanoTime();
/*      */       
/*      */ 
/*  753 */       WorldServer worldserver = (WorldServer)this.worlds.get(i);
/*      */       
/*  755 */       this.methodProfiler.a(worldserver.getWorldData().getName());
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  764 */       this.methodProfiler.a("tick");
/*      */       
/*      */ 
/*      */       try
/*      */       {
/*  769 */         worldserver.timings.doTick.startTiming();
/*  770 */         worldserver.doTick();
/*  771 */         worldserver.timings.doTick.stopTiming();
/*      */       }
/*      */       catch (Throwable throwable) {
/*      */         try {
/*  775 */           crashreport = CrashReport.a(throwable, "Exception ticking world");
/*      */         } catch (Throwable t) { CrashReport crashreport;
/*  777 */           throw new RuntimeException("Error generating crash report", t);
/*      */         }
/*      */         CrashReport crashreport;
/*  780 */         worldserver.a(crashreport);
/*  781 */         throw new ReportedException(crashreport);
/*      */       }
/*      */       try
/*      */       {
/*  785 */         worldserver.timings.tickEntities.startTiming();
/*  786 */         worldserver.tickEntities();
/*  787 */         worldserver.timings.tickEntities.stopTiming();
/*      */       }
/*      */       catch (Throwable throwable1) {
/*      */         try {
/*  791 */           crashreport = CrashReport.a(throwable1, "Exception ticking world entities");
/*      */         } catch (Throwable t) { CrashReport crashreport;
/*  793 */           throw new RuntimeException("Error generating crash report", t);
/*      */         }
/*      */         CrashReport crashreport;
/*  796 */         worldserver.a(crashreport);
/*  797 */         throw new ReportedException(crashreport);
/*      */       }
/*      */       
/*  800 */       this.methodProfiler.b();
/*  801 */       this.methodProfiler.a("tracker");
/*  802 */       worldserver.timings.tracker.startTiming();
/*  803 */       worldserver.getTracker().updatePlayers();
/*  804 */       worldserver.timings.tracker.stopTiming();
/*  805 */       this.methodProfiler.b();
/*  806 */       this.methodProfiler.b();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  812 */     this.methodProfiler.c("connection");
/*  813 */     SpigotTimings.connectionTimer.startTiming();
/*  814 */     aq().c();
/*  815 */     SpigotTimings.connectionTimer.stopTiming();
/*  816 */     this.methodProfiler.c("players");
/*  817 */     SpigotTimings.playerListTimer.startTiming();
/*  818 */     this.v.tick();
/*  819 */     SpigotTimings.playerListTimer.stopTiming();
/*  820 */     this.methodProfiler.c("tickables");
/*      */     
/*  822 */     SpigotTimings.tickablesTimer.startTiming();
/*  823 */     for (i = 0; i < this.p.size(); i++) {
/*  824 */       ((IUpdatePlayerListBox)this.p.get(i)).c();
/*      */     }
/*  826 */     SpigotTimings.tickablesTimer.stopTiming();
/*      */     
/*  828 */     this.methodProfiler.b();
/*      */   }
/*      */   
/*      */   public boolean getAllowNether() {
/*  832 */     return true;
/*      */   }
/*      */   
/*      */   public void a(IUpdatePlayerListBox iupdateplayerlistbox) {
/*  836 */     this.p.add(iupdateplayerlistbox);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void main(OptionSet options)
/*      */   {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  923 */       DedicatedServer dedicatedserver = new DedicatedServer(options);
/*      */       
/*  925 */       if (options.has("port")) {
/*  926 */         int port = ((Integer)options.valueOf("port")).intValue();
/*  927 */         if (port > 0) {
/*  928 */           dedicatedserver.setPort(port);
/*      */         }
/*      */       }
/*      */       
/*  932 */       if (options.has("universe")) {
/*  933 */         dedicatedserver.universe = ((File)options.valueOf("universe"));
/*      */       }
/*      */       
/*  936 */       if (options.has("world")) {
/*  937 */         dedicatedserver.setWorld((String)options.valueOf("world"));
/*      */       }
/*      */       
/*  940 */       dedicatedserver.primaryThread.start();
/*      */     }
/*      */     catch (Exception exception) {
/*  943 */       LOGGER.fatal("Failed to start the minecraft server", exception);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void C() {}
/*      */   
/*      */ 
/*      */ 
/*      */   public File d(String s)
/*      */   {
/*  956 */     return new File(y(), s);
/*      */   }
/*      */   
/*      */   public void info(String s) {
/*  960 */     LOGGER.info(s);
/*      */   }
/*      */   
/*      */   public void warning(String s) {
/*  964 */     LOGGER.warn(s);
/*      */   }
/*      */   
/*      */   public WorldServer getWorldServer(int i)
/*      */   {
/*  969 */     for (WorldServer world : this.worlds) {
/*  970 */       if (world.dimension == i) {
/*  971 */         return world;
/*      */       }
/*      */     }
/*  974 */     return (WorldServer)this.worlds.get(0);
/*      */   }
/*      */   
/*      */   public String E()
/*      */   {
/*  979 */     return this.serverIp;
/*      */   }
/*      */   
/*      */   public int F() {
/*  983 */     return this.u;
/*      */   }
/*      */   
/*      */   public String G() {
/*  987 */     return this.motd;
/*      */   }
/*      */   
/*      */   public String getVersion() {
/*  991 */     return "1.8.8";
/*      */   }
/*      */   
/*      */   public int I() {
/*  995 */     return this.v.getPlayerCount();
/*      */   }
/*      */   
/*      */   public int J() {
/*  999 */     return this.v.getMaxPlayers();
/*      */   }
/*      */   
/*      */   public String[] getPlayers() {
/* 1003 */     return this.v.f();
/*      */   }
/*      */   
/*      */   public GameProfile[] L() {
/* 1007 */     return this.v.g();
/*      */   }
/*      */   
/*      */   public boolean isDebugging() {
/* 1011 */     return getPropertyManager().getBoolean("debug", false);
/*      */   }
/*      */   
/*      */   public void g(String s) {
/* 1015 */     LOGGER.error(s);
/*      */   }
/*      */   
/*      */   public void h(String s) {
/* 1019 */     if (isDebugging()) {
/* 1020 */       LOGGER.info(s);
/*      */     }
/*      */   }
/*      */   
/*      */   public String getServerModName()
/*      */   {
/* 1026 */     return "Spigot";
/*      */   }
/*      */   
/*      */   public CrashReport b(CrashReport crashreport) {
/* 1030 */     crashreport.g().a("Profiler Position", new Callable() {
/*      */       public String a() throws Exception {
/* 1032 */         return MinecraftServer.this.methodProfiler.a ? MinecraftServer.this.methodProfiler.c() : "N/A (disabled)";
/*      */       }
/*      */       
/*      */       public Object call() throws Exception {
/* 1036 */         return a();
/*      */       }
/*      */     });
/* 1039 */     if (this.v != null) {
/* 1040 */       crashreport.g().a("Player Count", new Callable() {
/*      */         public String a() {
/* 1042 */           return MinecraftServer.this.v.getPlayerCount() + " / " + MinecraftServer.this.v.getMaxPlayers() + "; " + MinecraftServer.this.v.v();
/*      */         }
/*      */         
/*      */         public Object call() throws Exception {
/* 1046 */           return a();
/*      */         }
/*      */       });
/*      */     }
/*      */     
/* 1051 */     return crashreport;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<String> tabCompleteCommand(ICommandListener icommandlistener, String s, BlockPosition blockposition)
/*      */   {
/* 1095 */     return this.server.tabComplete(icommandlistener, s);
/*      */   }
/*      */   
/*      */   public static MinecraftServer getServer()
/*      */   {
/* 1100 */     return l;
/*      */   }
/*      */   
/*      */   public boolean O() {
/* 1104 */     return true;
/*      */   }
/*      */   
/*      */   public String getName() {
/* 1108 */     return "Server";
/*      */   }
/*      */   
/*      */   public void sendMessage(IChatBaseComponent ichatbasecomponent) {
/* 1112 */     LOGGER.info(ichatbasecomponent.c());
/*      */   }
/*      */   
/*      */   public boolean a(int i, String s) {
/* 1116 */     return true;
/*      */   }
/*      */   
/*      */   public ICommandHandler getCommandHandler() {
/* 1120 */     return this.b;
/*      */   }
/*      */   
/*      */   public KeyPair Q() {
/* 1124 */     return this.H;
/*      */   }
/*      */   
/*      */   public int R() {
/* 1128 */     return this.u;
/*      */   }
/*      */   
/*      */   public void setPort(int i) {
/* 1132 */     this.u = i;
/*      */   }
/*      */   
/*      */   public String S() {
/* 1136 */     return this.I;
/*      */   }
/*      */   
/*      */   public void i(String s) {
/* 1140 */     this.I = s;
/*      */   }
/*      */   
/*      */   public boolean T() {
/* 1144 */     return this.I != null;
/*      */   }
/*      */   
/*      */   public String U() {
/* 1148 */     return this.J;
/*      */   }
/*      */   
/*      */   public void setWorld(String s) {
/* 1152 */     this.J = s;
/*      */   }
/*      */   
/*      */   public void a(KeyPair keypair) {
/* 1156 */     this.H = keypair;
/*      */   }
/*      */   
/*      */   public void a(EnumDifficulty enumdifficulty)
/*      */   {
/* 1161 */     for (int i = 0; i < this.worlds.size(); i++) {
/* 1162 */       WorldServer worldserver = (WorldServer)this.worlds.get(i);
/*      */       
/*      */ 
/* 1165 */       if (worldserver != null) {
/* 1166 */         if (worldserver.getWorldData().isHardcore()) {
/* 1167 */           worldserver.getWorldData().setDifficulty(EnumDifficulty.HARD);
/* 1168 */           worldserver.setSpawnFlags(true, true);
/* 1169 */         } else if (T()) {
/* 1170 */           worldserver.getWorldData().setDifficulty(enumdifficulty);
/* 1171 */           worldserver.setSpawnFlags(worldserver.getDifficulty() != EnumDifficulty.PEACEFUL, true);
/*      */         } else {
/* 1173 */           worldserver.getWorldData().setDifficulty(enumdifficulty);
/* 1174 */           worldserver.setSpawnFlags(getSpawnMonsters(), this.spawnAnimals);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected boolean getSpawnMonsters()
/*      */   {
/* 1182 */     return true;
/*      */   }
/*      */   
/*      */   public boolean X() {
/* 1186 */     return this.demoMode;
/*      */   }
/*      */   
/*      */   public void b(boolean flag) {
/* 1190 */     this.demoMode = flag;
/*      */   }
/*      */   
/*      */   public void c(boolean flag) {
/* 1194 */     this.M = flag;
/*      */   }
/*      */   
/*      */   public Convertable getConvertable() {
/* 1198 */     return this.convertable;
/*      */   }
/*      */   
/*      */   public void aa() {
/* 1202 */     this.N = true;
/* 1203 */     getConvertable().d();
/*      */     
/*      */ 
/* 1206 */     for (int i = 0; i < this.worlds.size(); i++) {
/* 1207 */       WorldServer worldserver = (WorldServer)this.worlds.get(i);
/*      */       
/*      */ 
/* 1210 */       if (worldserver != null) {
/* 1211 */         worldserver.saveLevel();
/*      */       }
/*      */     }
/*      */     
/* 1215 */     getConvertable().e(((WorldServer)this.worlds.get(0)).getDataManager().g());
/* 1216 */     safeShutdown();
/*      */   }
/*      */   
/*      */   public String getResourcePack() {
/* 1220 */     return this.O;
/*      */   }
/*      */   
/*      */   public String getResourcePackHash() {
/* 1224 */     return this.P;
/*      */   }
/*      */   
/*      */   public void setResourcePack(String s, String s1) {
/* 1228 */     this.O = s;
/* 1229 */     this.P = s1;
/*      */   }
/*      */   
/*      */   public void a(MojangStatisticsGenerator mojangstatisticsgenerator) {
/* 1233 */     mojangstatisticsgenerator.a("whitelist_enabled", Boolean.valueOf(false));
/* 1234 */     mojangstatisticsgenerator.a("whitelist_count", Integer.valueOf(0));
/* 1235 */     if (this.v != null) {
/* 1236 */       mojangstatisticsgenerator.a("players_current", Integer.valueOf(I()));
/* 1237 */       mojangstatisticsgenerator.a("players_max", Integer.valueOf(J()));
/* 1238 */       mojangstatisticsgenerator.a("players_seen", Integer.valueOf(this.v.getSeenPlayers().length));
/*      */     }
/*      */     
/* 1241 */     mojangstatisticsgenerator.a("uses_auth", Boolean.valueOf(this.onlineMode));
/* 1242 */     mojangstatisticsgenerator.a("gui_state", as() ? "enabled" : "disabled");
/* 1243 */     mojangstatisticsgenerator.a("run_time", Long.valueOf((az() - mojangstatisticsgenerator.g()) / 60L * 1000L));
/* 1244 */     mojangstatisticsgenerator.a("avg_tick_ms", Integer.valueOf((int)(MathHelper.a(this.h) * 1.0E-6D)));
/* 1245 */     int i = 0;
/*      */     
/* 1247 */     if (this.worldServer != null)
/*      */     {
/* 1249 */       for (int j = 0; j < this.worlds.size(); j++) {
/* 1250 */         WorldServer worldserver = (WorldServer)this.worlds.get(j);
/* 1251 */         if (worldserver != null)
/*      */         {
/* 1253 */           WorldData worlddata = worldserver.getWorldData();
/*      */           
/* 1255 */           mojangstatisticsgenerator.a("world[" + i + "][dimension]", Integer.valueOf(worldserver.worldProvider.getDimension()));
/* 1256 */           mojangstatisticsgenerator.a("world[" + i + "][mode]", worlddata.getGameType());
/* 1257 */           mojangstatisticsgenerator.a("world[" + i + "][difficulty]", worldserver.getDifficulty());
/* 1258 */           mojangstatisticsgenerator.a("world[" + i + "][hardcore]", Boolean.valueOf(worlddata.isHardcore()));
/* 1259 */           mojangstatisticsgenerator.a("world[" + i + "][generator_name]", worlddata.getType().name());
/* 1260 */           mojangstatisticsgenerator.a("world[" + i + "][generator_version]", Integer.valueOf(worlddata.getType().getVersion()));
/* 1261 */           mojangstatisticsgenerator.a("world[" + i + "][height]", Integer.valueOf(this.F));
/* 1262 */           mojangstatisticsgenerator.a("world[" + i + "][chunks_loaded]", Integer.valueOf(worldserver.N().getLoadedChunks()));
/* 1263 */           i++;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1268 */     mojangstatisticsgenerator.a("worlds", Integer.valueOf(i));
/*      */   }
/*      */   
/*      */   public void b(MojangStatisticsGenerator mojangstatisticsgenerator) {
/* 1272 */     mojangstatisticsgenerator.b("singleplayer", Boolean.valueOf(T()));
/* 1273 */     mojangstatisticsgenerator.b("server_brand", getServerModName());
/* 1274 */     mojangstatisticsgenerator.b("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
/* 1275 */     mojangstatisticsgenerator.b("dedicated", Boolean.valueOf(ae()));
/*      */   }
/*      */   
/*      */   public boolean getSnooperEnabled() {
/* 1279 */     return true;
/*      */   }
/*      */   
/*      */   public abstract boolean ae();
/*      */   
/*      */   public boolean getOnlineMode() {
/* 1285 */     return this.server.getOnlineMode();
/*      */   }
/*      */   
/*      */   public void setOnlineMode(boolean flag) {
/* 1289 */     this.onlineMode = flag;
/*      */   }
/*      */   
/*      */   public boolean getSpawnAnimals() {
/* 1293 */     return this.spawnAnimals;
/*      */   }
/*      */   
/*      */   public void setSpawnAnimals(boolean flag) {
/* 1297 */     this.spawnAnimals = flag;
/*      */   }
/*      */   
/*      */   public boolean getSpawnNPCs() {
/* 1301 */     return this.spawnNPCs;
/*      */   }
/*      */   
/*      */   public abstract boolean ai();
/*      */   
/*      */   public void setSpawnNPCs(boolean flag) {
/* 1307 */     this.spawnNPCs = flag;
/*      */   }
/*      */   
/*      */   public boolean getPVP() {
/* 1311 */     return this.pvpMode;
/*      */   }
/*      */   
/*      */   public void setPVP(boolean flag) {
/* 1315 */     this.pvpMode = flag;
/*      */   }
/*      */   
/*      */   public boolean getAllowFlight() {
/* 1319 */     return this.allowFlight;
/*      */   }
/*      */   
/*      */   public void setAllowFlight(boolean flag) {
/* 1323 */     this.allowFlight = flag;
/*      */   }
/*      */   
/*      */   public abstract boolean getEnableCommandBlock();
/*      */   
/*      */   public String getMotd() {
/* 1329 */     return this.motd;
/*      */   }
/*      */   
/*      */   public void setMotd(String s) {
/* 1333 */     this.motd = s;
/*      */   }
/*      */   
/*      */   public int getMaxBuildHeight() {
/* 1337 */     return this.F;
/*      */   }
/*      */   
/*      */   public void c(int i) {
/* 1341 */     this.F = i;
/*      */   }
/*      */   
/*      */   public boolean isStopped() {
/* 1345 */     return this.isStopped;
/*      */   }
/*      */   
/*      */   public PlayerList getPlayerList() {
/* 1349 */     return this.v;
/*      */   }
/*      */   
/*      */   public void a(PlayerList playerlist) {
/* 1353 */     this.v = playerlist;
/*      */   }
/*      */   
/*      */   public void setGamemode(WorldSettings.EnumGamemode worldsettings_enumgamemode)
/*      */   {
/* 1358 */     for (int i = 0; i < this.worlds.size(); i++) {
/* 1359 */       ((WorldServer)getServer().worlds.get(i)).getWorldData().setGameType(worldsettings_enumgamemode);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ServerConnection getServerConnection()
/*      */   {
/* 1367 */     return this.q;
/*      */   }
/*      */   
/*      */   public ServerConnection aq() {
/* 1371 */     return this.q == null ? (this.q = new ServerConnection(this)) : this.q;
/*      */   }
/*      */   
/*      */   public boolean as() {
/* 1375 */     return false;
/*      */   }
/*      */   
/*      */   public abstract String a(WorldSettings.EnumGamemode paramEnumGamemode, boolean paramBoolean);
/*      */   
/*      */   public int at() {
/* 1381 */     return this.ticks;
/*      */   }
/*      */   
/*      */   public void au() {
/* 1385 */     this.T = true;
/*      */   }
/*      */   
/*      */   public BlockPosition getChunkCoordinates() {
/* 1389 */     return BlockPosition.ZERO;
/*      */   }
/*      */   
/*      */   public Vec3D d() {
/* 1393 */     return new Vec3D(0.0D, 0.0D, 0.0D);
/*      */   }
/*      */   
/*      */   public World getWorld() {
/* 1397 */     return (World)this.worlds.get(0);
/*      */   }
/*      */   
/*      */   public Entity f() {
/* 1401 */     return null;
/*      */   }
/*      */   
/*      */   public int getSpawnProtection() {
/* 1405 */     return 16;
/*      */   }
/*      */   
/*      */   public boolean a(World world, BlockPosition blockposition, EntityHuman entityhuman) {
/* 1409 */     return false;
/*      */   }
/*      */   
/*      */   public void setForceGamemode(boolean flag) {
/* 1413 */     this.U = flag;
/*      */   }
/*      */   
/*      */   public boolean getForceGamemode() {
/* 1417 */     return this.U;
/*      */   }
/*      */   
/*      */   public Proxy ay() {
/* 1421 */     return this.e;
/*      */   }
/*      */   
/*      */   public static long az() {
/* 1425 */     return System.currentTimeMillis();
/*      */   }
/*      */   
/*      */   public int getIdleTimeout() {
/* 1429 */     return this.G;
/*      */   }
/*      */   
/*      */   public void setIdleTimeout(int i) {
/* 1433 */     this.G = i;
/*      */   }
/*      */   
/*      */   public IChatBaseComponent getScoreboardDisplayName() {
/* 1437 */     return new ChatComponentText(getName());
/*      */   }
/*      */   
/*      */   public boolean aB() {
/* 1441 */     return true;
/*      */   }
/*      */   
/*      */   public MinecraftSessionService aD() {
/* 1445 */     return this.W;
/*      */   }
/*      */   
/*      */   public GameProfileRepository getGameProfileRepository() {
/* 1449 */     return this.Y;
/*      */   }
/*      */   
/*      */   public UserCache getUserCache() {
/* 1453 */     return this.Z;
/*      */   }
/*      */   
/*      */   public ServerPing aG() {
/* 1457 */     return this.r;
/*      */   }
/*      */   
/*      */   public void aH() {
/* 1461 */     this.X = 0L;
/*      */   }
/*      */   
/*      */   public Entity a(UUID uuid) {
/* 1465 */     WorldServer[] aworldserver = this.worldServer;
/* 1466 */     aworldserver.length;
/*      */     
/*      */ 
/* 1469 */     for (int j = 0; j < this.worlds.size(); j++) {
/* 1470 */       WorldServer worldserver = (WorldServer)this.worlds.get(j);
/*      */       
/*      */ 
/* 1473 */       if (worldserver != null) {
/* 1474 */         Entity entity = worldserver.getEntity(uuid);
/*      */         
/* 1476 */         if (entity != null) {
/* 1477 */           return entity;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1482 */     return null;
/*      */   }
/*      */   
/*      */   public boolean getSendCommandFeedback() {
/* 1486 */     return ((WorldServer)getServer().worlds.get(0)).getGameRules().getBoolean("sendCommandFeedback");
/*      */   }
/*      */   
/*      */   public void a(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor_enumcommandresult, int i) {}
/*      */   
/*      */   public int aI() {
/* 1492 */     return 29999984;
/*      */   }
/*      */   
/*      */   public <V> ListenableFuture<V> a(Callable<V> callable) {
/* 1496 */     Validate.notNull(callable);
/* 1497 */     if (!isMainThread()) {
/* 1498 */       ListenableFutureTask listenablefuturetask = ListenableFutureTask.create(callable);
/*      */       
/*      */ 
/*      */ 
/* 1502 */       this.j.add(listenablefuturetask);
/* 1503 */       return listenablefuturetask;
/*      */     }
/*      */     try
/*      */     {
/* 1507 */       return Futures.immediateFuture(callable.call());
/*      */     } catch (Exception exception) {
/* 1509 */       return Futures.immediateFailedCheckedFuture(exception);
/*      */     }
/*      */   }
/*      */   
/*      */   public ListenableFuture<Object> postToMainThread(Runnable runnable)
/*      */   {
/* 1515 */     Validate.notNull(runnable);
/* 1516 */     return a(Executors.callable(runnable));
/*      */   }
/*      */   
/*      */   public boolean isMainThread() {
/* 1520 */     return Thread.currentThread() == this.serverThread;
/*      */   }
/*      */   
/*      */   public int aK() {
/* 1524 */     return 256;
/*      */   }
/*      */   
/*      */   public long aL() {
/* 1528 */     return this.ab;
/*      */   }
/*      */   
/*      */   public Thread aM() {
/* 1532 */     return this.serverThread;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MinecraftServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */