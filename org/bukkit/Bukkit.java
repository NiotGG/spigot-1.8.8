/*      */ package org.bukkit;
/*      */ 
/*      */ import com.avaje.ebean.config.ServerConfig;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.logging.Logger;
/*      */ import org.bukkit.command.CommandException;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.command.ConsoleCommandSender;
/*      */ import org.bukkit.command.PluginCommand;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.inventory.InventoryType;
/*      */ import org.bukkit.generator.ChunkGenerator.ChunkData;
/*      */ import org.bukkit.help.HelpMap;
/*      */ import org.bukkit.inventory.Inventory;
/*      */ import org.bukkit.inventory.InventoryHolder;
/*      */ import org.bukkit.inventory.ItemFactory;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.inventory.Recipe;
/*      */ import org.bukkit.map.MapView;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.bukkit.plugin.ServicesManager;
/*      */ import org.bukkit.plugin.messaging.Messenger;
/*      */ import org.bukkit.scheduler.BukkitScheduler;
/*      */ import org.bukkit.scoreboard.ScoreboardManager;
/*      */ import org.bukkit.util.CachedServerIcon;
/*      */ import org.spigotmc.CustomTimingsHandler;
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
/*      */ public final class Bukkit
/*      */ {
/*      */   private static Server server;
/*      */   
/*      */   public static Server getServer()
/*      */   {
/*   62 */     return server;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setServer(Server server)
/*      */   {
/*   73 */     if (server != null) {
/*   74 */       throw new UnsupportedOperationException("Cannot redefine singleton Server");
/*      */     }
/*      */     
/*   77 */     server = server;
/*   78 */     server.getLogger().info("This server is running " + getName() + " version " + getVersion() + " (Implementing API version " + getBukkitVersion() + ")");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getName()
/*      */   {
/*   87 */     return server.getName();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getVersion()
/*      */   {
/*   96 */     return server.getVersion();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getBukkitVersion()
/*      */   {
/*  105 */     return server.getBukkitVersion();
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
/*      */ 
/*      */   public static Collection<? extends Player> getOnlinePlayers()
/*      */   {
/*  151 */     return server.getOnlinePlayers();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getMaxPlayers()
/*      */   {
/*  160 */     return server.getMaxPlayers();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getPort()
/*      */   {
/*  169 */     return server.getPort();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getViewDistance()
/*      */   {
/*  178 */     return server.getViewDistance();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getIp()
/*      */   {
/*  189 */     return server.getIp();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getServerName()
/*      */   {
/*  198 */     return server.getServerName();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getServerId()
/*      */   {
/*  208 */     return server.getServerId();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getWorldType()
/*      */   {
/*  217 */     return server.getWorldType();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean getGenerateStructures()
/*      */   {
/*  226 */     return server.getGenerateStructures();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean getAllowEnd()
/*      */   {
/*  235 */     return server.getAllowEnd();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean getAllowNether()
/*      */   {
/*  244 */     return server.getAllowNether();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean hasWhitelist()
/*      */   {
/*  253 */     return server.hasWhitelist();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setWhitelist(boolean value)
/*      */   {
/*  262 */     server.setWhitelist(value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Set<OfflinePlayer> getWhitelistedPlayers()
/*      */   {
/*  271 */     return server.getWhitelistedPlayers();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void reloadWhitelist()
/*      */   {
/*  278 */     server.reloadWhitelist();
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
/*      */   public static int broadcastMessage(String message)
/*      */   {
/*  291 */     return server.broadcastMessage(message);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getUpdateFolder()
/*      */   {
/*  303 */     return server.getUpdateFolder();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static File getUpdateFolderFile()
/*      */   {
/*  313 */     return server.getUpdateFolderFile();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getConnectionThrottle()
/*      */   {
/*  322 */     return server.getConnectionThrottle();
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
/*      */   public static int getTicksPerAnimalSpawns()
/*      */   {
/*  345 */     return server.getTicksPerAnimalSpawns();
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
/*      */   public static int getTicksPerMonsterSpawns()
/*      */   {
/*  368 */     return server.getTicksPerMonsterSpawns();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Player getPlayer(String name)
/*      */   {
/*  380 */     return server.getPlayer(name);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Player getPlayerExact(String name)
/*      */   {
/*  390 */     return server.getPlayerExact(name);
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
/*      */   public static List<Player> matchPlayer(String name)
/*      */   {
/*  404 */     return server.matchPlayer(name);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Player getPlayer(UUID id)
/*      */   {
/*  414 */     return server.getPlayer(id);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static PluginManager getPluginManager()
/*      */   {
/*  423 */     return server.getPluginManager();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static BukkitScheduler getScheduler()
/*      */   {
/*  432 */     return server.getScheduler();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ServicesManager getServicesManager()
/*      */   {
/*  441 */     return server.getServicesManager();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<World> getWorlds()
/*      */   {
/*  450 */     return server.getWorlds();
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
/*      */   public static World createWorld(WorldCreator creator)
/*      */   {
/*  464 */     return server.createWorld(creator);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean unloadWorld(String name, boolean save)
/*      */   {
/*  475 */     return server.unloadWorld(name, save);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean unloadWorld(World world, boolean save)
/*      */   {
/*  486 */     return server.unloadWorld(world, save);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static World getWorld(String name)
/*      */   {
/*  496 */     return server.getWorld(name);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static World getWorld(UUID uid)
/*      */   {
/*  506 */     return server.getWorld(uid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public static MapView getMap(short id)
/*      */   {
/*  518 */     return server.getMap(id);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static MapView createMap(World world)
/*      */   {
/*  528 */     return server.createMap(world);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void reload()
/*      */   {
/*  535 */     server.reload();
/*  536 */     CustomTimingsHandler.reload();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Logger getLogger()
/*      */   {
/*  545 */     return server.getLogger();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static PluginCommand getPluginCommand(String name)
/*      */   {
/*  555 */     return server.getPluginCommand(name);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void savePlayers()
/*      */   {
/*  562 */     server.savePlayers();
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
/*      */   public static boolean dispatchCommand(CommandSender sender, String commandLine)
/*      */     throws CommandException
/*      */   {
/*  576 */     return server.dispatchCommand(sender, commandLine);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void configureDbConfig(ServerConfig config)
/*      */   {
/*  586 */     server.configureDbConfig(config);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean addRecipe(Recipe recipe)
/*      */   {
/*  597 */     return server.addRecipe(recipe);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<Recipe> getRecipesFor(ItemStack result)
/*      */   {
/*  608 */     return server.getRecipesFor(result);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Iterator<Recipe> recipeIterator()
/*      */   {
/*  617 */     return server.recipeIterator();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void clearRecipes()
/*      */   {
/*  624 */     server.clearRecipes();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void resetRecipes()
/*      */   {
/*  631 */     server.resetRecipes();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Map<String, String[]> getCommandAliases()
/*      */   {
/*  640 */     return server.getCommandAliases();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getSpawnRadius()
/*      */   {
/*  649 */     return server.getSpawnRadius();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setSpawnRadius(int value)
/*      */   {
/*  658 */     server.setSpawnRadius(value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean getOnlineMode()
/*      */   {
/*  667 */     return server.getOnlineMode();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean getAllowFlight()
/*      */   {
/*  676 */     return server.getAllowFlight();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isHardcore()
/*      */   {
/*  685 */     return server.isHardcore();
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
/*      */   @Deprecated
/*      */   public static boolean useExactLoginLocation()
/*      */   {
/*  704 */     return server.useExactLoginLocation();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void shutdown()
/*      */   {
/*  711 */     server.shutdown();
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
/*      */   public static int broadcast(String message, String permission)
/*      */   {
/*  724 */     return server.broadcast(message, permission);
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
/*      */   @Deprecated
/*      */   public static OfflinePlayer getOfflinePlayer(String name)
/*      */   {
/*  745 */     return server.getOfflinePlayer(name);
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
/*      */   public static OfflinePlayer getOfflinePlayer(UUID id)
/*      */   {
/*  759 */     return server.getOfflinePlayer(id);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Set<String> getIPBans()
/*      */   {
/*  768 */     return server.getIPBans();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void banIP(String address)
/*      */   {
/*  777 */     server.banIP(address);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void unbanIP(String address)
/*      */   {
/*  786 */     server.unbanIP(address);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Set<OfflinePlayer> getBannedPlayers()
/*      */   {
/*  795 */     return server.getBannedPlayers();
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
/*      */   public static BanList getBanList(BanList.Type type)
/*      */   {
/*  808 */     return server.getBanList(type);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Set<OfflinePlayer> getOperators()
/*      */   {
/*  817 */     return server.getOperators();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static GameMode getDefaultGameMode()
/*      */   {
/*  826 */     return server.getDefaultGameMode();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setDefaultGameMode(GameMode mode)
/*      */   {
/*  835 */     server.setDefaultGameMode(mode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ConsoleCommandSender getConsoleSender()
/*      */   {
/*  845 */     return server.getConsoleSender();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static File getWorldContainer()
/*      */   {
/*  854 */     return server.getWorldContainer();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static OfflinePlayer[] getOfflinePlayers()
/*      */   {
/*  863 */     return server.getOfflinePlayers();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Messenger getMessenger()
/*      */   {
/*  872 */     return server.getMessenger();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static HelpMap getHelpMap()
/*      */   {
/*  881 */     return server.getHelpMap();
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
/*      */   public static Inventory createInventory(InventoryHolder owner, InventoryType type)
/*      */   {
/*  894 */     return server.createInventory(owner, type);
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
/*      */   public static Inventory createInventory(InventoryHolder owner, InventoryType type, String title)
/*      */   {
/*  910 */     return server.createInventory(owner, type, title);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Inventory createInventory(InventoryHolder owner, int size)
/*      */     throws IllegalArgumentException
/*      */   {
/*  923 */     return server.createInventory(owner, size);
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
/*      */   public static Inventory createInventory(InventoryHolder owner, int size, String title)
/*      */     throws IllegalArgumentException
/*      */   {
/*  938 */     return server.createInventory(owner, size, title);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getMonsterSpawnLimit()
/*      */   {
/*  948 */     return server.getMonsterSpawnLimit();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getAnimalSpawnLimit()
/*      */   {
/*  958 */     return server.getAnimalSpawnLimit();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getWaterAnimalSpawnLimit()
/*      */   {
/*  968 */     return server.getWaterAnimalSpawnLimit();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getAmbientSpawnLimit()
/*      */   {
/*  978 */     return server.getAmbientSpawnLimit();
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
/*      */   public static boolean isPrimaryThread()
/*      */   {
/*  994 */     return server.isPrimaryThread();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getMotd()
/*      */   {
/* 1003 */     return server.getMotd();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getShutdownMessage()
/*      */   {
/* 1012 */     return server.getShutdownMessage();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Warning.WarningState getWarningState()
/*      */   {
/* 1021 */     return server.getWarningState();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ItemFactory getItemFactory()
/*      */   {
/* 1031 */     return server.getItemFactory();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ScoreboardManager getScoreboardManager()
/*      */   {
/* 1042 */     return server.getScoreboardManager();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static CachedServerIcon getServerIcon()
/*      */   {
/* 1053 */     return server.getServerIcon();
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
/*      */   public static CachedServerIcon loadServerIcon(File file)
/*      */     throws IllegalArgumentException, Exception
/*      */   {
/* 1071 */     return server.loadServerIcon(file);
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
/*      */   public static CachedServerIcon loadServerIcon(BufferedImage image)
/*      */     throws IllegalArgumentException, Exception
/*      */   {
/* 1088 */     return server.loadServerIcon(image);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setIdleTimeout(int threshold)
/*      */   {
/* 1100 */     server.setIdleTimeout(threshold);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getIdleTimeout()
/*      */   {
/* 1109 */     return server.getIdleTimeout();
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
/*      */   public static ChunkGenerator.ChunkData createChunkData(World world)
/*      */   {
/* 1122 */     return server.createChunkData(world);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public static UnsafeValues getUnsafe()
/*      */   {
/* 1131 */     return server.getUnsafe();
/*      */   }
/*      */   
/*      */   public static Server.Spigot spigot()
/*      */   {
/* 1136 */     return server.spigot();
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Bukkit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */