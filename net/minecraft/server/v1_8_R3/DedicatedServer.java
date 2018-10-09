/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Handler;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.LoggerOutputStream;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.Waitable;
/*     */ import org.bukkit.event.server.RemoteServerCommandEvent;
/*     */ import org.bukkit.event.server.ServerCommandEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.spigotmc.CustomTimingsHandler;
/*     */ import org.spigotmc.SpigotConfig;
/*     */ 
/*     */ public class DedicatedServer extends MinecraftServer implements IMinecraftServer
/*     */ {
/*  31 */   private static final org.apache.logging.log4j.Logger LOGGER = ;
/*  32 */   private final List<ServerCommand> l = java.util.Collections.synchronizedList(com.google.common.collect.Lists.newArrayList());
/*     */   private RemoteStatusListener m;
/*     */   private RemoteControlListener n;
/*     */   public PropertyManager propertyManager;
/*     */   private EULA p;
/*     */   private boolean generateStructures;
/*     */   private WorldSettings.EnumGamemode r;
/*     */   private boolean s;
/*     */   
/*     */   public DedicatedServer(OptionSet options)
/*     */   {
/*  43 */     super(options, java.net.Proxy.NO_PROXY, a);
/*     */     
/*  45 */     new Thread("Server Infinisleeper");
/*     */     {
/*     */ 
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/*     */           for (;;)
/*     */           {
/*  54 */             Thread.sleep(2147483647L);
/*     */           }
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean init() throws IOException
/*     */   {
/*  64 */     Thread thread = new Thread("Server console handler")
/*     */     {
/*     */       public void run() {
/*  67 */         if (!org.bukkit.craftbukkit.Main.useConsole) {
/*  68 */           return;
/*     */         }
/*     */         
/*     */ 
/*  72 */         ConsoleReader bufferedreader = DedicatedServer.this.reader;
/*     */         try
/*     */         {
/*     */           do {
/*     */             String s;
/*     */             String s;
/*  78 */             if (org.bukkit.craftbukkit.Main.useJline) {
/*  79 */               s = bufferedreader.readLine(">", null);
/*     */             } else {
/*  81 */               s = bufferedreader.readLine();
/*     */             }
/*  83 */             if ((s != null) && (s.trim().length() > 0)) {
/*  84 */               DedicatedServer.this.issueCommand(s, DedicatedServer.this);
/*     */             }
/*  77 */             if (DedicatedServer.this.isStopped()) break; } while (DedicatedServer.this.isRunning());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*     */         catch (IOException ioexception)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  89 */           DedicatedServer.LOGGER.error("Exception handling console input", ioexception);
/*     */         }
/*     */         
/*     */       }
/*     */       
/*     */ 
/*  95 */     };
/*  96 */     java.util.logging.Logger global = java.util.logging.Logger.getLogger("");
/*  97 */     global.setUseParentHandlers(false);
/*  98 */     Handler[] arrayOfHandler; int i = (arrayOfHandler = global.getHandlers()).length; for (int j = 0; j < i; j++) { Handler handler = arrayOfHandler[j];
/*  99 */       global.removeHandler(handler);
/*     */     }
/* 101 */     global.addHandler(new org.bukkit.craftbukkit.v1_8_R3.util.ForwardLogHandler());
/*     */     
/* 103 */     org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger)LogManager.getRootLogger();
/* 104 */     for (org.apache.logging.log4j.core.Appender appender : logger.getAppenders().values()) {
/* 105 */       if ((appender instanceof org.apache.logging.log4j.core.appender.ConsoleAppender)) {
/* 106 */         logger.removeAppender(appender);
/*     */       }
/*     */     }
/*     */     
/* 110 */     new Thread(new org.bukkit.craftbukkit.v1_8_R3.util.TerminalConsoleWriterThread(System.out, this.reader)).start();
/*     */     
/* 112 */     System.setOut(new PrintStream(new LoggerOutputStream(logger, Level.INFO), true));
/* 113 */     System.setErr(new PrintStream(new LoggerOutputStream(logger, Level.WARN), true));
/*     */     
/*     */ 
/* 116 */     thread.setDaemon(true);
/* 117 */     thread.start();
/* 118 */     LOGGER.info("Starting minecraft server version 1.8.8");
/* 119 */     if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
/* 120 */       LOGGER.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
/*     */     }
/*     */     
/* 123 */     LOGGER.info("Loading properties");
/* 124 */     this.propertyManager = new PropertyManager(this.options);
/* 125 */     this.p = new EULA(new File("eula.txt"));
/*     */     
/* 127 */     boolean eulaAgreed = Boolean.getBoolean("com.mojang.eula.agree");
/* 128 */     if (eulaAgreed)
/*     */     {
/* 130 */       System.err.println("You have used the Spigot command line EULA agreement flag.");
/* 131 */       System.err.println("By using this setting you are indicating your agreement to Mojang's EULA (https://account.mojang.com/documents/minecraft_eula).");
/* 132 */       System.err.println("If you do not agree to the above EULA please stop your server and remove this flag immediately.");
/*     */     }
/*     */     
/* 135 */     if ((!this.p.a()) && (!eulaAgreed)) {
/* 136 */       LOGGER.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
/* 137 */       this.p.b();
/* 138 */       return false;
/*     */     }
/* 140 */     if (T()) {
/* 141 */       c("127.0.0.1");
/*     */     } else {
/* 143 */       setOnlineMode(this.propertyManager.getBoolean("online-mode", true));
/* 144 */       c(this.propertyManager.getString("server-ip", ""));
/*     */     }
/*     */     
/* 147 */     setSpawnAnimals(this.propertyManager.getBoolean("spawn-animals", true));
/* 148 */     setSpawnNPCs(this.propertyManager.getBoolean("spawn-npcs", true));
/* 149 */     setPVP(this.propertyManager.getBoolean("pvp", true));
/* 150 */     setAllowFlight(this.propertyManager.getBoolean("allow-flight", false));
/* 151 */     setResourcePack(this.propertyManager.getString("resource-pack", ""), this.propertyManager.getString("resource-pack-hash", ""));
/* 152 */     setMotd(this.propertyManager.getString("motd", "A Minecraft Server"));
/* 153 */     setForceGamemode(this.propertyManager.getBoolean("force-gamemode", false));
/* 154 */     setIdleTimeout(this.propertyManager.getInt("player-idle-timeout", 0));
/* 155 */     if (this.propertyManager.getInt("difficulty", 1) < 0) {
/* 156 */       this.propertyManager.setProperty("difficulty", Integer.valueOf(0));
/* 157 */     } else if (this.propertyManager.getInt("difficulty", 1) > 3) {
/* 158 */       this.propertyManager.setProperty("difficulty", Integer.valueOf(3));
/*     */     }
/*     */     
/* 161 */     this.generateStructures = this.propertyManager.getBoolean("generate-structures", true);
/* 162 */     int i = this.propertyManager.getInt("gamemode", WorldSettings.EnumGamemode.SURVIVAL.getId());
/*     */     
/* 164 */     this.r = WorldSettings.a(i);
/* 165 */     LOGGER.info("Default game type: " + this.r);
/* 166 */     InetAddress inetaddress = null;
/*     */     
/* 168 */     if (getServerIp().length() > 0) {
/* 169 */       inetaddress = InetAddress.getByName(getServerIp());
/*     */     }
/*     */     
/* 172 */     if (R() < 0) {
/* 173 */       setPort(this.propertyManager.getInt("server-port", 25565));
/*     */     }
/*     */     
/* 176 */     a(new DedicatedPlayerList(this));
/* 177 */     SpigotConfig.init((File)this.options.valueOf("spigot-settings"));
/* 178 */     SpigotConfig.registerCommands();
/*     */     
/*     */ 
/* 181 */     LOGGER.info("Generating keypair");
/* 182 */     a(MinecraftEncryption.b());
/* 183 */     LOGGER.info("Starting Minecraft server on " + (getServerIp().length() == 0 ? "*" : getServerIp()) + ":" + R());
/*     */     
/* 185 */     if (!SpigotConfig.lateBind) {
/*     */       try {
/* 187 */         aq().a(inetaddress, R());
/*     */       } catch (IOException ioexception) {
/* 189 */         LOGGER.warn("**** FAILED TO BIND TO PORT!");
/* 190 */         LOGGER.warn("The exception was: {}", new Object[] { ioexception.toString() });
/* 191 */         LOGGER.warn("Perhaps a server is already running on that port?");
/* 192 */         return false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 198 */     this.server.loadPlugins();
/* 199 */     this.server.enablePlugins(org.bukkit.plugin.PluginLoadOrder.STARTUP);
/*     */     
/*     */ 
/* 202 */     if (!getOnlineMode()) {
/* 203 */       LOGGER.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
/* 204 */       LOGGER.warn("The server will make no attempt to authenticate usernames. Beware.");
/*     */       
/* 206 */       if (SpigotConfig.bungee) {
/* 207 */         LOGGER.warn("Whilst this makes it possible to use BungeeCord, unless access to your server is properly restricted, it also opens up the ability for hackers to connect with any username they choose.");
/* 208 */         LOGGER.warn("Please see http://www.spigotmc.org/wiki/firewall-guide/ for further information.");
/*     */       } else {
/* 210 */         LOGGER.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
/*     */       }
/*     */       
/* 213 */       LOGGER.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
/*     */     }
/*     */     
/* 216 */     if (aR()) {
/* 217 */       getUserCache().c();
/*     */     }
/*     */     
/* 220 */     if (!NameReferencingFileConverter.a(this.propertyManager)) {
/* 221 */       return false;
/*     */     }
/* 223 */     this.convertable = new WorldLoaderServer(this.server.getWorldContainer());
/* 224 */     long j = System.nanoTime();
/*     */     
/* 226 */     if (U() == null) {
/* 227 */       setWorld(this.propertyManager.getString("level-name", "world"));
/*     */     }
/*     */     
/* 230 */     String s = this.propertyManager.getString("level-seed", "");
/* 231 */     String s1 = this.propertyManager.getString("level-type", "DEFAULT");
/* 232 */     String s2 = this.propertyManager.getString("generator-settings", "");
/* 233 */     long k = new Random().nextLong();
/*     */     
/* 235 */     if (s.length() > 0) {
/*     */       try {
/* 237 */         long l = Long.parseLong(s);
/*     */         
/* 239 */         if (l != 0L) {
/* 240 */           k = l;
/*     */         }
/*     */       } catch (NumberFormatException localNumberFormatException) {
/* 243 */         k = s.hashCode();
/*     */       }
/*     */     }
/*     */     
/* 247 */     WorldType worldtype = WorldType.getType(s1);
/*     */     
/* 249 */     if (worldtype == null) {
/* 250 */       worldtype = WorldType.NORMAL;
/*     */     }
/*     */     
/* 253 */     aB();
/* 254 */     getEnableCommandBlock();
/* 255 */     p();
/* 256 */     getSnooperEnabled();
/* 257 */     aK();
/* 258 */     c(this.propertyManager.getInt("max-build-height", 256));
/* 259 */     c((getMaxBuildHeight() + 8) / 16 * 16);
/* 260 */     c(MathHelper.clamp(getMaxBuildHeight(), 64, 256));
/* 261 */     this.propertyManager.setProperty("max-build-height", Integer.valueOf(getMaxBuildHeight()));
/* 262 */     LOGGER.info("Preparing level \"" + U() + "\"");
/* 263 */     a(U(), U(), k, worldtype, s2);
/* 264 */     long i1 = System.nanoTime() - j;
/* 265 */     String s3 = String.format("%.3fs", new Object[] { Double.valueOf(i1 / 1.0E9D) });
/*     */     
/* 267 */     LOGGER.info("Done (" + s3 + ")! For help, type \"help\" or \"?\"");
/* 268 */     if (this.propertyManager.getBoolean("enable-query", false)) {
/* 269 */       LOGGER.info("Starting GS4 status listener");
/* 270 */       this.m = new RemoteStatusListener(this);
/* 271 */       this.m.a();
/*     */     }
/*     */     
/* 274 */     if (this.propertyManager.getBoolean("enable-rcon", false)) {
/* 275 */       LOGGER.info("Starting remote control listener");
/* 276 */       this.n = new RemoteControlListener(this);
/* 277 */       this.n.a();
/* 278 */       this.remoteConsole = new org.bukkit.craftbukkit.v1_8_R3.command.CraftRemoteConsoleCommandSender();
/*     */     }
/*     */     
/*     */ 
/* 282 */     if (this.server.getBukkitSpawnRadius() > -1) {
/* 283 */       LOGGER.info("'settings.spawn-radius' in bukkit.yml has been moved to 'spawn-protection' in server.properties. I will move your config for you.");
/* 284 */       this.propertyManager.properties.remove("spawn-protection");
/* 285 */       this.propertyManager.getInt("spawn-protection", this.server.getBukkitSpawnRadius());
/* 286 */       this.server.removeBukkitSpawnRadius();
/* 287 */       this.propertyManager.savePropertiesFile();
/*     */     }
/*     */     
/*     */ 
/* 291 */     if (SpigotConfig.lateBind) {
/*     */       try {
/* 293 */         aq().a(inetaddress, R());
/*     */       } catch (IOException ioexception) {
/* 295 */         LOGGER.warn("**** FAILED TO BIND TO PORT!");
/* 296 */         LOGGER.warn("The exception was: {}", new Object[] { ioexception.toString() });
/* 297 */         LOGGER.warn("Perhaps a server is already running on that port?");
/* 298 */         return false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 310 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public PropertyManager getPropertyManager()
/*     */   {
/* 317 */     return this.propertyManager;
/*     */   }
/*     */   
/*     */   public void setGamemode(WorldSettings.EnumGamemode worldsettings_enumgamemode)
/*     */   {
/* 322 */     super.setGamemode(worldsettings_enumgamemode);
/* 323 */     this.r = worldsettings_enumgamemode;
/*     */   }
/*     */   
/*     */   public boolean getGenerateStructures() {
/* 327 */     return this.generateStructures;
/*     */   }
/*     */   
/*     */   public WorldSettings.EnumGamemode getGamemode() {
/* 331 */     return this.r;
/*     */   }
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 335 */     return EnumDifficulty.getById(this.propertyManager.getInt("difficulty", EnumDifficulty.NORMAL.a()));
/*     */   }
/*     */   
/*     */   public boolean isHardcore() {
/* 339 */     return this.propertyManager.getBoolean("hardcore", false);
/*     */   }
/*     */   
/*     */   protected void a(CrashReport crashreport) {}
/*     */   
/*     */   public CrashReport b(CrashReport crashreport) {
/* 345 */     crashreport = super.b(crashreport);
/* 346 */     crashreport.g().a("Is Modded", new Callable() {
/*     */       public String a() throws Exception {
/* 348 */         String s = DedicatedServer.this.getServerModName();
/*     */         
/* 350 */         return !s.equals("vanilla") ? "Definitely; Server brand changed to '" + s + "'" : "Unknown (can't tell)";
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 354 */         return a();
/*     */       }
/* 356 */     });
/* 357 */     crashreport.g().a("Type", new Callable() {
/*     */       public String a() throws Exception {
/* 359 */         return "Dedicated Server (map_server.txt)";
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 363 */         return a();
/*     */       }
/* 365 */     });
/* 366 */     return crashreport;
/*     */   }
/*     */   
/*     */   protected void z() {
/* 370 */     System.exit(0);
/*     */   }
/*     */   
/*     */   public void B() {
/* 374 */     super.B();
/* 375 */     aO();
/*     */   }
/*     */   
/*     */   public boolean getAllowNether() {
/* 379 */     return this.propertyManager.getBoolean("allow-nether", true);
/*     */   }
/*     */   
/*     */   public boolean getSpawnMonsters() {
/* 383 */     return this.propertyManager.getBoolean("spawn-monsters", true);
/*     */   }
/*     */   
/*     */   public void a(MojangStatisticsGenerator mojangstatisticsgenerator) {
/* 387 */     mojangstatisticsgenerator.a("whitelist_enabled", Boolean.valueOf(aP().getHasWhitelist()));
/* 388 */     mojangstatisticsgenerator.a("whitelist_count", Integer.valueOf(aP().getWhitelisted().length));
/* 389 */     super.a(mojangstatisticsgenerator);
/*     */   }
/*     */   
/*     */   public boolean getSnooperEnabled() {
/* 393 */     return this.propertyManager.getBoolean("snooper-enabled", true);
/*     */   }
/*     */   
/*     */   public void issueCommand(String s, ICommandListener icommandlistener) {
/* 397 */     this.l.add(new ServerCommand(s, icommandlistener));
/*     */   }
/*     */   
/*     */   public void aO() {
/* 401 */     SpigotTimings.serverCommandTimer.startTiming();
/* 402 */     while (!this.l.isEmpty()) {
/* 403 */       ServerCommand servercommand = (ServerCommand)this.l.remove(0);
/*     */       
/*     */ 
/* 406 */       ServerCommandEvent event = new ServerCommandEvent(this.console, servercommand.command);
/* 407 */       this.server.getPluginManager().callEvent(event);
/* 408 */       if (!event.isCancelled()) {
/* 409 */         servercommand = new ServerCommand(event.getCommand(), servercommand.source);
/*     */         
/*     */ 
/* 412 */         this.server.dispatchServerCommand(this.console, servercommand);
/*     */       }
/*     */     }
/*     */     
/* 416 */     SpigotTimings.serverCommandTimer.stopTiming();
/*     */   }
/*     */   
/*     */   public boolean ae() {
/* 420 */     return true;
/*     */   }
/*     */   
/*     */   public boolean ai() {
/* 424 */     return this.propertyManager.getBoolean("use-native-transport", true);
/*     */   }
/*     */   
/*     */   public DedicatedPlayerList aP() {
/* 428 */     return (DedicatedPlayerList)super.getPlayerList();
/*     */   }
/*     */   
/*     */   public int a(String s, int i) {
/* 432 */     return this.propertyManager.getInt(s, i);
/*     */   }
/*     */   
/*     */   public String a(String s, String s1) {
/* 436 */     return this.propertyManager.getString(s, s1);
/*     */   }
/*     */   
/*     */   public boolean a(String s, boolean flag) {
/* 440 */     return this.propertyManager.getBoolean(s, flag);
/*     */   }
/*     */   
/*     */   public void a(String s, Object object) {
/* 444 */     this.propertyManager.setProperty(s, object);
/*     */   }
/*     */   
/*     */   public void a() {
/* 448 */     this.propertyManager.savePropertiesFile();
/*     */   }
/*     */   
/*     */   public String b() {
/* 452 */     File file = this.propertyManager.c();
/*     */     
/* 454 */     return file != null ? file.getAbsolutePath() : "No settings file";
/*     */   }
/*     */   
/*     */   public void aQ() {
/* 458 */     ServerGUI.a(this);
/* 459 */     this.s = true;
/*     */   }
/*     */   
/*     */   public boolean as() {
/* 463 */     return this.s;
/*     */   }
/*     */   
/*     */   public String a(WorldSettings.EnumGamemode worldsettings_enumgamemode, boolean flag) {
/* 467 */     return "";
/*     */   }
/*     */   
/*     */   public boolean getEnableCommandBlock() {
/* 471 */     return this.propertyManager.getBoolean("enable-command-block", false);
/*     */   }
/*     */   
/*     */   public int getSpawnProtection() {
/* 475 */     return this.propertyManager.getInt("spawn-protection", super.getSpawnProtection());
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition, EntityHuman entityhuman) {
/* 479 */     if (world.worldProvider.getDimension() != 0)
/* 480 */       return false;
/* 481 */     if (aP().getOPs().isEmpty())
/* 482 */       return false;
/* 483 */     if (aP().isOp(entityhuman.getProfile()))
/* 484 */       return false;
/* 485 */     if (getSpawnProtection() <= 0) {
/* 486 */       return false;
/*     */     }
/* 488 */     BlockPosition blockposition1 = world.getSpawn();
/* 489 */     int i = MathHelper.a(blockposition.getX() - blockposition1.getX());
/* 490 */     int j = MathHelper.a(blockposition.getZ() - blockposition1.getZ());
/* 491 */     int k = Math.max(i, j);
/*     */     
/* 493 */     return k <= getSpawnProtection();
/*     */   }
/*     */   
/*     */   public int p()
/*     */   {
/* 498 */     return this.propertyManager.getInt("op-permission-level", 4);
/*     */   }
/*     */   
/*     */   public void setIdleTimeout(int i) {
/* 502 */     super.setIdleTimeout(i);
/* 503 */     this.propertyManager.setProperty("player-idle-timeout", Integer.valueOf(i));
/* 504 */     a();
/*     */   }
/*     */   
/*     */   public boolean q() {
/* 508 */     return this.propertyManager.getBoolean("broadcast-rcon-to-ops", true);
/*     */   }
/*     */   
/*     */   public boolean r() {
/* 512 */     return this.propertyManager.getBoolean("broadcast-console-to-ops", true);
/*     */   }
/*     */   
/*     */   public boolean aB() {
/* 516 */     return this.propertyManager.getBoolean("announce-player-achievements", true);
/*     */   }
/*     */   
/*     */   public int aI() {
/* 520 */     int i = this.propertyManager.getInt("max-world-size", super.aI());
/*     */     
/* 522 */     if (i < 1) {
/* 523 */       i = 1;
/* 524 */     } else if (i > super.aI()) {
/* 525 */       i = super.aI();
/*     */     }
/*     */     
/* 528 */     return i;
/*     */   }
/*     */   
/*     */   public int aK() {
/* 532 */     return this.propertyManager.getInt("network-compression-threshold", super.aK());
/*     */   }
/*     */   
/*     */   protected boolean aR() {
/* 536 */     this.server.getLogger().info("**** Beginning UUID conversion, this may take A LONG time ****");
/* 537 */     boolean flag = false;
/*     */     
/*     */ 
/*     */ 
/* 541 */     for (int i = 0; (!flag) && (i <= 2); i++) {
/* 542 */       if (i > 0) {
/* 543 */         LOGGER.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
/* 544 */         aU();
/*     */       }
/*     */       
/* 547 */       flag = NameReferencingFileConverter.a(this);
/*     */     }
/*     */     
/* 550 */     boolean flag1 = false;
/*     */     
/* 552 */     for (i = 0; (!flag1) && (i <= 2); i++) {
/* 553 */       if (i > 0) {
/* 554 */         LOGGER.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
/* 555 */         aU();
/*     */       }
/*     */       
/* 558 */       flag1 = NameReferencingFileConverter.b(this);
/*     */     }
/*     */     
/* 561 */     boolean flag2 = false;
/*     */     
/* 563 */     for (i = 0; (!flag2) && (i <= 2); i++) {
/* 564 */       if (i > 0) {
/* 565 */         LOGGER.warn("Encountered a problem while converting the op list, retrying in a few seconds");
/* 566 */         aU();
/*     */       }
/*     */       
/* 569 */       flag2 = NameReferencingFileConverter.c(this);
/*     */     }
/*     */     
/* 572 */     boolean flag3 = false;
/*     */     
/* 574 */     for (i = 0; (!flag3) && (i <= 2); i++) {
/* 575 */       if (i > 0) {
/* 576 */         LOGGER.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
/* 577 */         aU();
/*     */       }
/*     */       
/* 580 */       flag3 = NameReferencingFileConverter.d(this);
/*     */     }
/*     */     
/* 583 */     boolean flag4 = false;
/*     */     
/* 585 */     for (i = 0; (!flag4) && (i <= 2); i++) {
/* 586 */       if (i > 0) {
/* 587 */         LOGGER.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
/* 588 */         aU();
/*     */       }
/*     */       
/* 591 */       flag4 = NameReferencingFileConverter.a(this, this.propertyManager);
/*     */     }
/*     */     
/* 594 */     return (flag) || (flag1) || (flag2) || (flag3) || (flag4);
/*     */   }
/*     */   
/*     */   private void aU() {
/*     */     try {
/* 599 */       Thread.sleep(5000L);
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {}
/*     */   }
/*     */   
/*     */   public long aS()
/*     */   {
/* 606 */     return this.propertyManager.getLong("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
/*     */   }
/*     */   
/*     */   public String getPlugins()
/*     */   {
/* 611 */     StringBuilder result = new StringBuilder();
/* 612 */     Plugin[] plugins = this.server.getPluginManager().getPlugins();
/*     */     
/* 614 */     result.append(this.server.getName());
/* 615 */     result.append(" on Bukkit ");
/* 616 */     result.append(this.server.getBukkitVersion());
/*     */     
/* 618 */     if ((plugins.length > 0) && (this.server.getQueryPlugins())) {
/* 619 */       result.append(": ");
/*     */       
/* 621 */       for (int i = 0; i < plugins.length; i++) {
/* 622 */         if (i > 0) {
/* 623 */           result.append("; ");
/*     */         }
/*     */         
/* 626 */         result.append(plugins[i].getDescription().getName());
/* 627 */         result.append(" ");
/* 628 */         result.append(plugins[i].getDescription().getVersion().replaceAll(";", ","));
/*     */       }
/*     */     }
/*     */     
/* 632 */     return result.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public String executeRemoteCommand(final String s)
/*     */   {
/* 638 */     Waitable<String> waitable = new Waitable()
/*     */     {
/*     */       protected String evaluate() {
/* 641 */         RemoteControlCommandListener.getInstance().i();
/*     */         
/* 643 */         RemoteServerCommandEvent event = new RemoteServerCommandEvent(DedicatedServer.this.remoteConsole, s);
/* 644 */         DedicatedServer.this.server.getPluginManager().callEvent(event);
/* 645 */         if (event.isCancelled()) {
/* 646 */           return "";
/*     */         }
/*     */         
/* 649 */         ServerCommand serverCommand = new ServerCommand(event.getCommand(), RemoteControlCommandListener.getInstance());
/* 650 */         DedicatedServer.this.server.dispatchServerCommand(DedicatedServer.this.remoteConsole, serverCommand);
/* 651 */         return RemoteControlCommandListener.getInstance().j();
/*     */       }
/* 653 */     };
/* 654 */     this.processQueue.add(waitable);
/*     */     try {
/* 656 */       return (String)waitable.get();
/*     */     } catch (ExecutionException e) {
/* 658 */       throw new RuntimeException("Exception processing rcon command " + s, e.getCause());
/*     */     } catch (InterruptedException e) {
/* 660 */       Thread.currentThread().interrupt();
/* 661 */       throw new RuntimeException("Interrupted processing rcon command " + s, e);
/*     */     }
/*     */   }
/*     */   
/*     */   public PlayerList getPlayerList()
/*     */   {
/* 667 */     return aP();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\DedicatedServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */