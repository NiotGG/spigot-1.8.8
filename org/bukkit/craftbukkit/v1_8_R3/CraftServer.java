/*      */ package org.bukkit.craftbukkit.v1_8_R3;
/*      */ 
/*      */ import com.avaje.ebean.config.DataSourceConfig;
/*      */ import com.avaje.ebean.config.ServerConfig;
/*      */ import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
/*      */ import com.google.common.base.Charsets;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.MapMaker;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.logging.Level;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.md_5.bungee.api.chat.BaseComponent;
/*      */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*      */ import net.minecraft.server.v1_8_R3.CommandDispatcher;
/*      */ import net.minecraft.server.v1_8_R3.Convertable;
/*      */ import net.minecraft.server.v1_8_R3.CraftingManager;
/*      */ import net.minecraft.server.v1_8_R3.DedicatedPlayerList;
/*      */ import net.minecraft.server.v1_8_R3.DedicatedServer;
/*      */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*      */ import net.minecraft.server.v1_8_R3.EnumDifficulty;
/*      */ import net.minecraft.server.v1_8_R3.ExceptionWorldConflict;
/*      */ import net.minecraft.server.v1_8_R3.GameProfileBanList;
/*      */ import net.minecraft.server.v1_8_R3.ICommand;
/*      */ import net.minecraft.server.v1_8_R3.ICommandListener;
/*      */ import net.minecraft.server.v1_8_R3.IDataManager;
/*      */ import net.minecraft.server.v1_8_R3.IpBanList;
/*      */ import net.minecraft.server.v1_8_R3.Items;
/*      */ import net.minecraft.server.v1_8_R3.JsonListEntry;
/*      */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*      */ import net.minecraft.server.v1_8_R3.PersistentCollection;
/*      */ import net.minecraft.server.v1_8_R3.PlayerList;
/*      */ import net.minecraft.server.v1_8_R3.PropertyManager;
/*      */ import net.minecraft.server.v1_8_R3.RecipesFurnace;
/*      */ import net.minecraft.server.v1_8_R3.RegionFile;
/*      */ import net.minecraft.server.v1_8_R3.RegionFileCache;
/*      */ import net.minecraft.server.v1_8_R3.ServerCommand;
/*      */ import net.minecraft.server.v1_8_R3.ServerNBTManager;
/*      */ import net.minecraft.server.v1_8_R3.WorldData;
/*      */ import net.minecraft.server.v1_8_R3.WorldMap;
/*      */ import net.minecraft.server.v1_8_R3.WorldNBTStorage;
/*      */ import net.minecraft.server.v1_8_R3.WorldServer;
/*      */ import net.minecraft.server.v1_8_R3.WorldSettings;
/*      */ import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;
/*      */ import org.apache.commons.lang.Validate;
/*      */ import org.bukkit.BanList;
/*      */ import org.bukkit.BanList.Type;
/*      */ import org.bukkit.GameMode;
/*      */ import org.bukkit.OfflinePlayer;
/*      */ import org.bukkit.Server.Spigot;
/*      */ import org.bukkit.Warning.WarningState;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.World.Environment;
/*      */ import org.bukkit.WorldCreator;
/*      */ import org.bukkit.command.CommandException;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.command.PluginCommand;
/*      */ import org.bukkit.command.SimpleCommandMap;
/*      */ import org.bukkit.configuration.ConfigurationSection;
/*      */ import org.bukkit.configuration.file.YamlConfiguration;
/*      */ import org.bukkit.configuration.file.YamlConfigurationOptions;
/*      */ import org.bukkit.configuration.serialization.ConfigurationSerialization;
/*      */ import org.bukkit.conversations.Conversable;
/*      */ import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.command.VanillaCommandWrapper;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.help.SimpleHelpMap;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemFactory;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftRecipe;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftShapedRecipe;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.map.CraftMapView;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.metadata.EntityMetadataStore;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.metadata.PlayerMetadataStore;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.metadata.WorldMetadataStore;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.scheduler.CraftScheduler;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboardManager;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftIconCache;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.permissions.CraftDefaultPermissions;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.entity.Player.Spigot;
/*      */ import org.bukkit.event.inventory.InventoryType;
/*      */ import org.bukkit.event.player.PlayerChatTabCompleteEvent;
/*      */ import org.bukkit.event.world.WorldUnloadEvent;
/*      */ import org.bukkit.generator.ChunkGenerator;
/*      */ import org.bukkit.inventory.FurnaceRecipe;
/*      */ import org.bukkit.inventory.Inventory;
/*      */ import org.bukkit.inventory.InventoryHolder;
/*      */ import org.bukkit.inventory.Recipe;
/*      */ import org.bukkit.inventory.ShapedRecipe;
/*      */ import org.bukkit.inventory.ShapelessRecipe;
/*      */ import org.bukkit.permissions.Permissible;
/*      */ import org.bukkit.permissions.Permission;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.plugin.PluginDescriptionFile;
/*      */ import org.bukkit.plugin.PluginLoadOrder;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.bukkit.plugin.ServicesManager;
/*      */ import org.bukkit.plugin.SimplePluginManager;
/*      */ import org.bukkit.plugin.messaging.Messenger;
/*      */ import org.bukkit.plugin.messaging.StandardMessenger;
/*      */ import org.bukkit.scheduler.BukkitWorker;
/*      */ import org.spigotmc.SpigotConfig;
/*      */ import org.yaml.snakeyaml.Yaml;
/*      */ import org.yaml.snakeyaml.error.MarkedYAMLException;
/*      */ 
/*      */ public final class CraftServer implements org.bukkit.Server
/*      */ {
/*  128 */   private static final Player[] EMPTY_PLAYER_ARRAY = new Player[0];
/*  129 */   private final String serverName = "CraftBukkit";
/*      */   private final String serverVersion;
/*  131 */   private final String bukkitVersion = org.bukkit.craftbukkit.v1_8_R3.util.Versioning.getBukkitVersion();
/*  132 */   private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("Minecraft");
/*  133 */   private final ServicesManager servicesManager = new org.bukkit.plugin.SimpleServicesManager();
/*  134 */   private final CraftScheduler scheduler = new CraftScheduler();
/*  135 */   private final SimpleCommandMap commandMap = new SimpleCommandMap(this);
/*  136 */   private final SimpleHelpMap helpMap = new SimpleHelpMap(this);
/*  137 */   private final StandardMessenger messenger = new StandardMessenger();
/*  138 */   private final PluginManager pluginManager = new SimplePluginManager(this, this.commandMap);
/*      */   protected final MinecraftServer console;
/*      */   protected final DedicatedPlayerList playerList;
/*  141 */   private final Map<String, World> worlds = new LinkedHashMap();
/*      */   private YamlConfiguration configuration;
/*      */   private YamlConfiguration commandsConfiguration;
/*  144 */   private final Yaml yaml = new Yaml(new org.yaml.snakeyaml.constructor.SafeConstructor());
/*  145 */   private final Map<UUID, OfflinePlayer> offlinePlayers = new MapMaker().softValues().makeMap();
/*  146 */   private final EntityMetadataStore entityMetadata = new EntityMetadataStore();
/*  147 */   private final PlayerMetadataStore playerMetadata = new PlayerMetadataStore();
/*  148 */   private final WorldMetadataStore worldMetadata = new WorldMetadataStore();
/*  149 */   private int monsterSpawn = -1;
/*  150 */   private int animalSpawn = -1;
/*  151 */   private int waterAnimalSpawn = -1;
/*  152 */   private int ambientSpawn = -1;
/*  153 */   public int chunkGCPeriod = -1;
/*  154 */   public int chunkGCLoadThresh = 0;
/*      */   private File container;
/*  156 */   private Warning.WarningState warningState = Warning.WarningState.DEFAULT;
/*  157 */   private final BooleanWrapper online = new BooleanWrapper(null);
/*      */   public CraftScoreboardManager scoreboardManager;
/*      */   public boolean playerCommandState;
/*      */   private boolean printSaveWarning;
/*      */   private CraftIconCache icon;
/*  162 */   private boolean overrideAllCommandBlockCommands = false;
/*  163 */   private final Pattern validUserPattern = Pattern.compile("^[a-zA-Z0-9_]{2,16}$");
/*  164 */   private final UUID invalidUserUUID = UUID.nameUUIDFromBytes("InvalidUsername".getBytes(Charsets.UTF_8));
/*      */   private final List<CraftPlayer> playerView;
/*      */   public int reloadCount;
/*      */   
/*      */   private final class BooleanWrapper {
/*  169 */     private boolean value = true;
/*      */     
/*      */     private BooleanWrapper() {} }
/*      */   
/*  173 */   static { ConfigurationSerialization.registerClass(CraftOfflinePlayer.class);
/*  174 */     CraftItemFactory.instance();
/*      */   }
/*      */   
/*      */   public CraftServer(MinecraftServer console, PlayerList playerList) {
/*  178 */     this.console = console;
/*  179 */     this.playerList = ((DedicatedPlayerList)playerList);
/*  180 */     this.playerView = Collections.unmodifiableList(com.google.common.collect.Lists.transform(playerList.players, new com.google.common.base.Function()
/*      */     {
/*      */       public CraftPlayer apply(EntityPlayer player) {
/*  183 */         return player.getBukkitEntity();
/*      */       }
/*  185 */     }));
/*  186 */     this.serverVersion = CraftServer.class.getPackage().getImplementationVersion();
/*  187 */     this.online.value = console.getPropertyManager().getBoolean("online-mode", true);
/*      */     
/*  189 */     org.bukkit.Bukkit.setServer(this);
/*      */     
/*      */ 
/*  192 */     net.minecraft.server.v1_8_R3.Enchantment.DAMAGE_ALL.getClass();
/*  193 */     org.bukkit.enchantments.Enchantment.stopAcceptingRegistrations();
/*      */     
/*  195 */     org.bukkit.potion.Potion.setPotionBrewer(new org.bukkit.craftbukkit.v1_8_R3.potion.CraftPotionBrewer());
/*  196 */     net.minecraft.server.v1_8_R3.MobEffectList.BLINDNESS.getClass();
/*  197 */     org.bukkit.potion.PotionEffectType.stopAcceptingRegistrations();
/*      */     
/*      */ 
/*  200 */     if (!org.bukkit.craftbukkit.Main.useConsole) {
/*  201 */       getLogger().info("Console input is disabled due to --noconsole command argument");
/*      */     }
/*      */     
/*  204 */     this.configuration = YamlConfiguration.loadConfiguration(getConfigFile());
/*  205 */     this.configuration.options().copyDefaults(true);
/*  206 */     this.configuration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("configurations/bukkit.yml"), Charsets.UTF_8)));
/*  207 */     ConfigurationSection legacyAlias = null;
/*  208 */     if (!this.configuration.isString("aliases")) {
/*  209 */       legacyAlias = this.configuration.getConfigurationSection("aliases");
/*  210 */       this.configuration.set("aliases", "now-in-commands.yml");
/*      */     }
/*  212 */     saveConfig();
/*  213 */     if (getCommandsConfigFile().isFile()) {
/*  214 */       legacyAlias = null;
/*      */     }
/*  216 */     this.commandsConfiguration = YamlConfiguration.loadConfiguration(getCommandsConfigFile());
/*  217 */     this.commandsConfiguration.options().copyDefaults(true);
/*  218 */     this.commandsConfiguration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("configurations/commands.yml"), Charsets.UTF_8)));
/*  219 */     saveCommandsConfig();
/*      */     
/*      */ 
/*  222 */     if (legacyAlias != null) {
/*  223 */       ConfigurationSection aliases = this.commandsConfiguration.createSection("aliases");
/*  224 */       for (String key : legacyAlias.getKeys(false)) {
/*  225 */         ArrayList<String> commands = new ArrayList();
/*      */         
/*  227 */         if (legacyAlias.isList(key)) {
/*  228 */           for (String command : legacyAlias.getStringList(key)) {
/*  229 */             commands.add(command + " $1-");
/*      */           }
/*      */         } else {
/*  232 */           commands.add(legacyAlias.getString(key) + " $1-");
/*      */         }
/*      */         
/*  235 */         aliases.set(key, commands);
/*      */       }
/*      */     }
/*      */     
/*  239 */     saveCommandsConfig();
/*  240 */     this.overrideAllCommandBlockCommands = this.commandsConfiguration.getStringList("command-block-overrides").contains("*");
/*  241 */     ((SimplePluginManager)this.pluginManager).useTimings(this.configuration.getBoolean("settings.plugin-profiling"));
/*  242 */     this.monsterSpawn = this.configuration.getInt("spawn-limits.monsters");
/*  243 */     this.animalSpawn = this.configuration.getInt("spawn-limits.animals");
/*  244 */     this.waterAnimalSpawn = this.configuration.getInt("spawn-limits.water-animals");
/*  245 */     this.ambientSpawn = this.configuration.getInt("spawn-limits.ambient");
/*  246 */     console.autosavePeriod = this.configuration.getInt("ticks-per.autosave");
/*  247 */     this.warningState = Warning.WarningState.value(this.configuration.getString("settings.deprecated-verbose"));
/*  248 */     this.chunkGCPeriod = this.configuration.getInt("chunk-gc.period-in-ticks");
/*  249 */     this.chunkGCLoadThresh = this.configuration.getInt("chunk-gc.load-threshold");
/*  250 */     loadIcon();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getCommandBlockOverride(String command)
/*      */   {
/*  259 */     return (this.overrideAllCommandBlockCommands) || (this.commandsConfiguration.getStringList("command-block-overrides").contains(command));
/*      */   }
/*      */   
/*      */   private File getConfigFile() {
/*  263 */     return (File)this.console.options.valueOf("bukkit-settings");
/*      */   }
/*      */   
/*      */   private File getCommandsConfigFile() {
/*  267 */     return (File)this.console.options.valueOf("commands-settings");
/*      */   }
/*      */   
/*      */   private void saveConfig() {
/*      */     try {
/*  272 */       this.configuration.save(getConfigFile());
/*      */     } catch (IOException ex) {
/*  274 */       java.util.logging.Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + getConfigFile(), ex);
/*      */     }
/*      */   }
/*      */   
/*      */   private void saveCommandsConfig() {
/*      */     try {
/*  280 */       this.commandsConfiguration.save(getCommandsConfigFile());
/*      */     } catch (IOException ex) {
/*  282 */       java.util.logging.Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + getCommandsConfigFile(), ex);
/*      */     }
/*      */   }
/*      */   
/*      */   public void loadPlugins() {
/*  287 */     this.pluginManager.registerInterface(org.bukkit.plugin.java.JavaPluginLoader.class);
/*      */     
/*  289 */     File pluginFolder = (File)this.console.options.valueOf("plugins");
/*      */     
/*  291 */     if (pluginFolder.exists()) {
/*  292 */       Plugin[] plugins = this.pluginManager.loadPlugins(pluginFolder);
/*  293 */       Plugin[] arrayOfPlugin1; int i = (arrayOfPlugin1 = plugins).length; for (int j = 0; j < i; j++) { Plugin plugin = arrayOfPlugin1[j];
/*      */         try {
/*  295 */           String message = String.format("Loading %s", new Object[] { plugin.getDescription().getFullName() });
/*  296 */           plugin.getLogger().info(message);
/*  297 */           plugin.onLoad();
/*      */         } catch (Throwable ex) {
/*  299 */           java.util.logging.Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex.getMessage() + " initializing " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*      */         }
/*      */       }
/*      */     } else {
/*  303 */       pluginFolder.mkdir();
/*      */     }
/*      */   }
/*      */   
/*      */   public void enablePlugins(PluginLoadOrder type) {
/*  308 */     if (type == PluginLoadOrder.STARTUP) {
/*  309 */       this.helpMap.clear();
/*  310 */       this.helpMap.initializeGeneralTopics();
/*      */     }
/*      */     
/*  313 */     Plugin[] plugins = this.pluginManager.getPlugins();
/*      */     Plugin[] arrayOfPlugin1;
/*  315 */     int i = (arrayOfPlugin1 = plugins).length; for (int j = 0; j < i; j++) { Plugin plugin = arrayOfPlugin1[j];
/*  316 */       if ((!plugin.isEnabled()) && (plugin.getDescription().getLoad() == type)) {
/*  317 */         loadPlugin(plugin);
/*      */       }
/*      */     }
/*      */     
/*  321 */     if (type == PluginLoadOrder.POSTWORLD)
/*      */     {
/*  323 */       setVanillaCommands(true);
/*  324 */       this.commandMap.setFallbackCommands();
/*  325 */       setVanillaCommands(false);
/*      */       
/*  327 */       this.commandMap.registerServerAliases();
/*  328 */       loadCustomPermissions();
/*  329 */       org.bukkit.util.permissions.DefaultPermissions.registerCorePermissions();
/*  330 */       CraftDefaultPermissions.registerCorePermissions();
/*  331 */       this.helpMap.initializeCommands();
/*      */     }
/*      */   }
/*      */   
/*      */   public void disablePlugins() {
/*  336 */     this.pluginManager.disablePlugins();
/*      */   }
/*      */   
/*      */   private void setVanillaCommands(boolean first) {
/*  340 */     Map<String, ICommand> commands = new CommandDispatcher().getCommands();
/*  341 */     for (ICommand cmd : commands.values())
/*      */     {
/*  343 */       VanillaCommandWrapper wrapper = new VanillaCommandWrapper((net.minecraft.server.v1_8_R3.CommandAbstract)cmd, net.minecraft.server.v1_8_R3.LocaleI18n.get(cmd.getUsage(null)));
/*  344 */       if (SpigotConfig.replaceCommands.contains(wrapper.getName())) {
/*  345 */         if (first) {
/*  346 */           this.commandMap.register("minecraft", wrapper);
/*      */         }
/*  348 */       } else if (!first) {
/*  349 */         this.commandMap.register("minecraft", wrapper);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void loadPlugin(Plugin plugin)
/*      */   {
/*      */     try {
/*  357 */       this.pluginManager.enablePlugin(plugin);
/*      */       
/*  359 */       List<Permission> perms = plugin.getDescription().getPermissions();
/*      */       
/*  361 */       for (Permission perm : perms) {
/*      */         try {
/*  363 */           this.pluginManager.addPermission(perm);
/*      */         } catch (IllegalArgumentException ex) {
/*  365 */           getLogger().log(Level.WARNING, "Plugin " + plugin.getDescription().getFullName() + " tried to register permission '" + perm.getName() + "' but it's already registered", ex);
/*      */         }
/*      */       }
/*      */     } catch (Throwable ex) {
/*  369 */       java.util.logging.Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex.getMessage() + " loading " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*      */     }
/*      */   }
/*      */   
/*      */   public String getName()
/*      */   {
/*  375 */     return "CraftBukkit";
/*      */   }
/*      */   
/*      */   public String getVersion()
/*      */   {
/*  380 */     return this.serverVersion + " (MC: " + this.console.getVersion() + ")";
/*      */   }
/*      */   
/*      */   public String getBukkitVersion()
/*      */   {
/*  385 */     return this.bukkitVersion;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<CraftPlayer> getOnlinePlayers()
/*      */   {
/*  397 */     return this.playerView;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public Player getPlayer(String name)
/*      */   {
/*  403 */     Validate.notNull(name, "Name cannot be null");
/*      */     
/*  405 */     Player found = getPlayerExact(name);
/*      */     
/*  407 */     if (found != null) {
/*  408 */       return found;
/*      */     }
/*      */     
/*  411 */     String lowerName = name.toLowerCase();
/*  412 */     int delta = Integer.MAX_VALUE;
/*  413 */     for (Player player : getOnlinePlayers())
/*  414 */       if (player.getName().toLowerCase().startsWith(lowerName)) {
/*  415 */         int curDelta = Math.abs(player.getName().length() - lowerName.length());
/*  416 */         if (curDelta < delta) {
/*  417 */           found = player;
/*  418 */           delta = curDelta;
/*      */         }
/*  420 */         if (curDelta == 0)
/*      */           break;
/*      */       }
/*  423 */     return found;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public Player getPlayerExact(String name)
/*      */   {
/*  429 */     Validate.notNull(name, "Name cannot be null");
/*      */     
/*  431 */     EntityPlayer player = this.playerList.getPlayer(name);
/*  432 */     return player != null ? player.getBukkitEntity() : null;
/*      */   }
/*      */   
/*      */   public Player getPlayer(UUID id)
/*      */   {
/*  437 */     EntityPlayer player = this.playerList.a(id);
/*      */     
/*  439 */     if (player != null) {
/*  440 */       return player.getBukkitEntity();
/*      */     }
/*      */     
/*  443 */     return null;
/*      */   }
/*      */   
/*      */   public int broadcastMessage(String message)
/*      */   {
/*  448 */     return broadcast(message, "bukkit.broadcast.user");
/*      */   }
/*      */   
/*      */   public Player getPlayer(EntityPlayer entity) {
/*  452 */     return entity.getBukkitEntity();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public List<Player> matchPlayer(String partialName)
/*      */   {
/*  458 */     Validate.notNull(partialName, "PartialName cannot be null");
/*      */     
/*  460 */     List<Player> matchedPlayers = new ArrayList();
/*      */     
/*  462 */     for (Player iterPlayer : getOnlinePlayers()) {
/*  463 */       String iterPlayerName = iterPlayer.getName();
/*      */       
/*  465 */       if (partialName.equalsIgnoreCase(iterPlayerName))
/*      */       {
/*  467 */         matchedPlayers.clear();
/*  468 */         matchedPlayers.add(iterPlayer);
/*  469 */         break;
/*      */       }
/*  471 */       if (iterPlayerName.toLowerCase().contains(partialName.toLowerCase()))
/*      */       {
/*  473 */         matchedPlayers.add(iterPlayer);
/*      */       }
/*      */     }
/*      */     
/*  477 */     return matchedPlayers;
/*      */   }
/*      */   
/*      */   public int getMaxPlayers()
/*      */   {
/*  482 */     return this.playerList.getMaxPlayers();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getPort()
/*      */   {
/*  489 */     return getConfigInt("server-port", 25565);
/*      */   }
/*      */   
/*      */   public int getViewDistance()
/*      */   {
/*  494 */     return getConfigInt("view-distance", 10);
/*      */   }
/*      */   
/*      */   public String getIp()
/*      */   {
/*  499 */     return getConfigString("server-ip", "");
/*      */   }
/*      */   
/*      */   public String getServerName()
/*      */   {
/*  504 */     return getConfigString("server-name", "Unknown Server");
/*      */   }
/*      */   
/*      */   public String getServerId()
/*      */   {
/*  509 */     return getConfigString("server-id", "unnamed");
/*      */   }
/*      */   
/*      */   public String getWorldType()
/*      */   {
/*  514 */     return getConfigString("level-type", "DEFAULT");
/*      */   }
/*      */   
/*      */   public boolean getGenerateStructures()
/*      */   {
/*  519 */     return getConfigBoolean("generate-structures", true);
/*      */   }
/*      */   
/*      */   public boolean getAllowEnd()
/*      */   {
/*  524 */     return this.configuration.getBoolean("settings.allow-end");
/*      */   }
/*      */   
/*      */   public boolean getAllowNether()
/*      */   {
/*  529 */     return getConfigBoolean("allow-nether", true);
/*      */   }
/*      */   
/*      */   public boolean getWarnOnOverload() {
/*  533 */     return this.configuration.getBoolean("settings.warn-on-overload");
/*      */   }
/*      */   
/*      */   public boolean getQueryPlugins() {
/*  537 */     return this.configuration.getBoolean("settings.query-plugins");
/*      */   }
/*      */   
/*      */   public boolean hasWhitelist()
/*      */   {
/*  542 */     return getConfigBoolean("white-list", false);
/*      */   }
/*      */   
/*      */   private String getConfigString(String variable, String defaultValue)
/*      */   {
/*  547 */     return this.console.getPropertyManager().getString(variable, defaultValue);
/*      */   }
/*      */   
/*      */   private int getConfigInt(String variable, int defaultValue) {
/*  551 */     return this.console.getPropertyManager().getInt(variable, defaultValue);
/*      */   }
/*      */   
/*      */   private boolean getConfigBoolean(String variable, boolean defaultValue) {
/*  555 */     return this.console.getPropertyManager().getBoolean(variable, defaultValue);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getUpdateFolder()
/*      */   {
/*  562 */     return this.configuration.getString("settings.update-folder", "update");
/*      */   }
/*      */   
/*      */   public File getUpdateFolderFile()
/*      */   {
/*  567 */     return new File((File)this.console.options.valueOf("plugins"), this.configuration.getString("settings.update-folder", "update"));
/*      */   }
/*      */   
/*      */ 
/*      */   public long getConnectionThrottle()
/*      */   {
/*  573 */     if (SpigotConfig.bungee) {
/*  574 */       return -1L;
/*      */     }
/*  576 */     return this.configuration.getInt("settings.connection-throttle");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getTicksPerAnimalSpawns()
/*      */   {
/*  583 */     return this.configuration.getInt("ticks-per.animal-spawns");
/*      */   }
/*      */   
/*      */   public int getTicksPerMonsterSpawns()
/*      */   {
/*  588 */     return this.configuration.getInt("ticks-per.monster-spawns");
/*      */   }
/*      */   
/*      */   public PluginManager getPluginManager()
/*      */   {
/*  593 */     return this.pluginManager;
/*      */   }
/*      */   
/*      */   public CraftScheduler getScheduler()
/*      */   {
/*  598 */     return this.scheduler;
/*      */   }
/*      */   
/*      */   public ServicesManager getServicesManager()
/*      */   {
/*  603 */     return this.servicesManager;
/*      */   }
/*      */   
/*      */   public List<World> getWorlds()
/*      */   {
/*  608 */     return new ArrayList(this.worlds.values());
/*      */   }
/*      */   
/*      */   public DedicatedPlayerList getHandle() {
/*  612 */     return this.playerList;
/*      */   }
/*      */   
/*      */   public boolean dispatchServerCommand(CommandSender sender, ServerCommand serverCommand)
/*      */   {
/*  617 */     if ((sender instanceof Conversable)) {
/*  618 */       Conversable conversable = (Conversable)sender;
/*      */       
/*  620 */       if (conversable.isConversing()) {
/*  621 */         conversable.acceptConversationInput(serverCommand.command);
/*  622 */         return true;
/*      */       }
/*      */     }
/*      */     try {
/*  626 */       this.playerCommandState = true;
/*  627 */       return dispatchCommand(sender, serverCommand.command);
/*      */     } catch (Exception ex) {
/*  629 */       getLogger().log(Level.WARNING, "Unexpected exception while parsing console command \"" + serverCommand.command + '"', ex);
/*  630 */       return false;
/*      */     } finally {
/*  632 */       this.playerCommandState = false;
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean dispatchCommand(CommandSender sender, String commandLine)
/*      */   {
/*  638 */     Validate.notNull(sender, "Sender cannot be null");
/*  639 */     Validate.notNull(commandLine, "CommandLine cannot be null");
/*      */     
/*  641 */     if (this.commandMap.dispatch(sender, commandLine)) {
/*  642 */       return true;
/*      */     }
/*      */     
/*  645 */     sender.sendMessage(SpigotConfig.unknownCommandMessage);
/*      */     
/*  647 */     return false;
/*      */   }
/*      */   
/*      */   public void reload()
/*      */   {
/*  652 */     this.reloadCount += 1;
/*  653 */     this.configuration = YamlConfiguration.loadConfiguration(getConfigFile());
/*  654 */     this.commandsConfiguration = YamlConfiguration.loadConfiguration(getCommandsConfigFile());
/*  655 */     PropertyManager config = new PropertyManager(this.console.options);
/*      */     
/*  657 */     ((DedicatedServer)this.console).propertyManager = config;
/*      */     
/*  659 */     boolean animals = config.getBoolean("spawn-animals", this.console.getSpawnAnimals());
/*  660 */     boolean monsters = config.getBoolean("spawn-monsters", ((WorldServer)this.console.worlds.get(0)).getDifficulty() != EnumDifficulty.PEACEFUL);
/*  661 */     EnumDifficulty difficulty = EnumDifficulty.getById(config.getInt("difficulty", ((WorldServer)this.console.worlds.get(0)).getDifficulty().ordinal()));
/*      */     
/*  663 */     this.online.value = config.getBoolean("online-mode", this.console.getOnlineMode());
/*  664 */     this.console.setSpawnAnimals(config.getBoolean("spawn-animals", this.console.getSpawnAnimals()));
/*  665 */     this.console.setPVP(config.getBoolean("pvp", this.console.getPVP()));
/*  666 */     this.console.setAllowFlight(config.getBoolean("allow-flight", this.console.getAllowFlight()));
/*  667 */     this.console.setMotd(config.getString("motd", this.console.getMotd()));
/*  668 */     this.monsterSpawn = this.configuration.getInt("spawn-limits.monsters");
/*  669 */     this.animalSpawn = this.configuration.getInt("spawn-limits.animals");
/*  670 */     this.waterAnimalSpawn = this.configuration.getInt("spawn-limits.water-animals");
/*  671 */     this.ambientSpawn = this.configuration.getInt("spawn-limits.ambient");
/*  672 */     this.warningState = Warning.WarningState.value(this.configuration.getString("settings.deprecated-verbose"));
/*  673 */     this.printSaveWarning = false;
/*  674 */     this.console.autosavePeriod = this.configuration.getInt("ticks-per.autosave");
/*  675 */     this.chunkGCPeriod = this.configuration.getInt("chunk-gc.period-in-ticks");
/*  676 */     this.chunkGCLoadThresh = this.configuration.getInt("chunk-gc.load-threshold");
/*  677 */     loadIcon();
/*      */     try
/*      */     {
/*  680 */       this.playerList.getIPBans().load();
/*      */     } catch (IOException ex) {
/*  682 */       this.logger.log(Level.WARNING, "Failed to load banned-ips.json, " + ex.getMessage());
/*      */     }
/*      */     try {
/*  685 */       this.playerList.getProfileBans().load();
/*      */     } catch (IOException ex) {
/*  687 */       this.logger.log(Level.WARNING, "Failed to load banned-players.json, " + ex.getMessage());
/*      */     }
/*      */     
/*  690 */     SpigotConfig.init((File)this.console.options.valueOf("spigot-settings"));
/*  691 */     for (WorldServer world : this.console.worlds) {
/*  692 */       world.worldData.setDifficulty(difficulty);
/*  693 */       world.setSpawnFlags(monsters, animals);
/*  694 */       if (getTicksPerAnimalSpawns() < 0) {
/*  695 */         world.ticksPerAnimalSpawns = 400L;
/*      */       } else {
/*  697 */         world.ticksPerAnimalSpawns = getTicksPerAnimalSpawns();
/*      */       }
/*      */       
/*  700 */       if (getTicksPerMonsterSpawns() < 0) {
/*  701 */         world.ticksPerMonsterSpawns = 1L;
/*      */       } else {
/*  703 */         world.ticksPerMonsterSpawns = getTicksPerMonsterSpawns();
/*      */       }
/*  705 */       world.spigotConfig.init();
/*      */     }
/*      */     
/*  708 */     this.pluginManager.clearPlugins();
/*  709 */     this.commandMap.clearCommands();
/*  710 */     resetRecipes();
/*  711 */     SpigotConfig.registerCommands();
/*      */     
/*  713 */     this.overrideAllCommandBlockCommands = this.commandsConfiguration.getStringList("command-block-overrides").contains("*");
/*      */     
/*  715 */     int pollCount = 0;
/*      */     
/*      */ 
/*  718 */     while ((pollCount < 50) && (getScheduler().getActiveWorkers().size() > 0)) {
/*      */       try {
/*  720 */         Thread.sleep(50L);
/*      */       } catch (InterruptedException localInterruptedException) {}
/*  722 */       pollCount++;
/*      */     }
/*      */     
/*  725 */     Object overdueWorkers = getScheduler().getActiveWorkers();
/*  726 */     for (BukkitWorker worker : (List)overdueWorkers) {
/*  727 */       Plugin plugin = worker.getOwner();
/*  728 */       String author = "<NoAuthorGiven>";
/*  729 */       if (plugin.getDescription().getAuthors().size() > 0) {
/*  730 */         author = (String)plugin.getDescription().getAuthors().get(0);
/*      */       }
/*  732 */       getLogger().log(Level.SEVERE, String.format(
/*  733 */         "Nag author: '%s' of '%s' about the following: %s", new Object[] {
/*  734 */         author, 
/*  735 */         plugin.getDescription().getName(), 
/*  736 */         "This plugin is not properly shutting down its async tasks when it is being reloaded.  This may cause conflicts with the newly loaded version of the plugin" }));
/*      */     }
/*      */     
/*  739 */     loadPlugins();
/*  740 */     enablePlugins(PluginLoadOrder.STARTUP);
/*  741 */     enablePlugins(PluginLoadOrder.POSTWORLD);
/*      */   }
/*      */   
/*      */   private void loadIcon() {
/*  745 */     this.icon = new CraftIconCache(null);
/*      */     try {
/*  747 */       File file = new File(new File("."), "server-icon.png");
/*  748 */       if (file.isFile()) {
/*  749 */         this.icon = loadServerIcon0(file);
/*      */       }
/*      */     } catch (Exception ex) {
/*  752 */       getLogger().log(Level.WARNING, "Couldn't load server icon", ex);
/*      */     }
/*      */   }
/*      */   
/*      */   private void loadCustomPermissions()
/*      */   {
/*  758 */     File file = new File(this.configuration.getString("settings.permissions-file"));
/*      */     FileInputStream stream;
/*      */     try
/*      */     {
/*  762 */       stream = new FileInputStream(file);
/*      */     } catch (FileNotFoundException localFileNotFoundException) {}
/*      */     FileInputStream stream;
/*  765 */     try { file.createNewFile();
/*      */     } finally {
/*  767 */       return;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/*  774 */         perms = (Map)this.yaml.load(stream);
/*      */       } catch (MarkedYAMLException ex) { Map<String, Map<String, Object>> perms;
/*  776 */         getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML: " + ex.toString());
/*  777 */         return;
/*      */       } catch (Throwable ex) {
/*  779 */         getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML.", ex);
/*  780 */         return;
/*      */       } finally {
/*      */         try {
/*  783 */           stream.close(); } catch (IOException localIOException3) {} } } Map<String, Map<String, Object>> perms; try { stream.close();
/*      */     }
/*      */     catch (IOException localIOException4) {}
/*      */     
/*  787 */     if (perms == null) {
/*  788 */       getLogger().log(Level.INFO, "Server permissions file " + file + " is empty, ignoring it");
/*  789 */       return;
/*      */     }
/*      */     
/*  792 */     List<Permission> permsList = Permission.loadPermissions(perms, "Permission node '%s' in " + file + " is invalid", Permission.DEFAULT_PERMISSION);
/*      */     
/*  794 */     for (Permission perm : permsList) {
/*      */       try {
/*  796 */         this.pluginManager.addPermission(perm);
/*      */       } catch (IllegalArgumentException ex) {
/*  798 */         getLogger().log(Level.SEVERE, "Permission in " + file + " was already defined", ex);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String toString()
/*      */   {
/*  805 */     return "CraftServer{serverName=CraftBukkit,serverVersion=" + this.serverVersion + ",minecraftVersion=" + this.console.getVersion() + '}';
/*      */   }
/*      */   
/*      */   public World createWorld(String name, World.Environment environment) {
/*  809 */     return WorldCreator.name(name).environment(environment).createWorld();
/*      */   }
/*      */   
/*      */   public World createWorld(String name, World.Environment environment, long seed) {
/*  813 */     return WorldCreator.name(name).environment(environment).seed(seed).createWorld();
/*      */   }
/*      */   
/*      */   public World createWorld(String name, World.Environment environment, ChunkGenerator generator) {
/*  817 */     return WorldCreator.name(name).environment(environment).generator(generator).createWorld();
/*      */   }
/*      */   
/*      */   public World createWorld(String name, World.Environment environment, long seed, ChunkGenerator generator) {
/*  821 */     return WorldCreator.name(name).environment(environment).seed(seed).generator(generator).createWorld();
/*      */   }
/*      */   
/*      */   public World createWorld(WorldCreator creator)
/*      */   {
/*  826 */     Validate.notNull(creator, "Creator may not be null");
/*      */     
/*  828 */     String name = creator.name();
/*  829 */     ChunkGenerator generator = creator.generator();
/*  830 */     File folder = new File(getWorldContainer(), name);
/*  831 */     World world = getWorld(name);
/*  832 */     net.minecraft.server.v1_8_R3.WorldType type = net.minecraft.server.v1_8_R3.WorldType.getType(creator.type().getName());
/*  833 */     boolean generateStructures = creator.generateStructures();
/*      */     
/*  835 */     if (world != null) {
/*  836 */       return world;
/*      */     }
/*      */     
/*  839 */     if ((folder.exists()) && (!folder.isDirectory())) {
/*  840 */       throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
/*      */     }
/*      */     
/*  843 */     if (generator == null) {
/*  844 */       generator = getGenerator(name);
/*      */     }
/*      */     
/*  847 */     Convertable converter = new net.minecraft.server.v1_8_R3.WorldLoaderServer(getWorldContainer());
/*  848 */     if (converter.isConvertable(name)) {
/*  849 */       getLogger().info("Converting world '" + name + "'");
/*  850 */       converter.convert(name, new net.minecraft.server.v1_8_R3.IProgressUpdate() {
/*  851 */         private long b = System.currentTimeMillis();
/*      */         
/*      */         public void a(String s) {}
/*      */         
/*      */         public void a(int i) {
/*  856 */           if (System.currentTimeMillis() - this.b >= 1000L) {
/*  857 */             this.b = System.currentTimeMillis();
/*  858 */             MinecraftServer.LOGGER.info("Converting... " + i + "%");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */         public void c(String s) {}
/*      */       });
/*      */     }
/*      */     
/*  867 */     int dimension = 10 + this.console.worlds.size();
/*  868 */     boolean used = false;
/*      */     do {
/*  870 */       for (WorldServer server : this.console.worlds) {
/*  871 */         used = server.dimension == dimension;
/*  872 */         if (used) {
/*  873 */           dimension++;
/*  874 */           break;
/*      */         }
/*      */       }
/*  877 */     } while (used);
/*  878 */     boolean hardcore = false;
/*      */     
/*  880 */     Object sdm = new ServerNBTManager(getWorldContainer(), name, true);
/*  881 */     WorldData worlddata = ((IDataManager)sdm).getWorldData();
/*  882 */     if (worlddata == null) {
/*  883 */       WorldSettings worldSettings = new WorldSettings(creator.seed(), WorldSettings.EnumGamemode.getById(getDefaultGameMode().getValue()), generateStructures, hardcore, type);
/*  884 */       worldSettings.setGeneratorSettings(creator.generatorSettings());
/*  885 */       worlddata = new WorldData(worldSettings, name);
/*      */     }
/*  887 */     worlddata.checkName(name);
/*  888 */     WorldServer internal = (WorldServer)new WorldServer(this.console, (IDataManager)sdm, worlddata, dimension, this.console.methodProfiler, creator.environment(), generator).b();
/*      */     
/*  890 */     if (!this.worlds.containsKey(name.toLowerCase())) {
/*  891 */       return null;
/*      */     }
/*      */     
/*  894 */     internal.scoreboard = getScoreboardManager().getMainScoreboard().getHandle();
/*      */     
/*  896 */     internal.tracker = new net.minecraft.server.v1_8_R3.EntityTracker(internal);
/*  897 */     internal.addIWorldAccess(new net.minecraft.server.v1_8_R3.WorldManager(this.console, internal));
/*  898 */     internal.worldData.setDifficulty(EnumDifficulty.EASY);
/*  899 */     internal.setSpawnFlags(true, true);
/*  900 */     this.console.worlds.add(internal);
/*      */     
/*  902 */     if (generator != null) {
/*  903 */       internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));
/*      */     }
/*      */     
/*  906 */     this.pluginManager.callEvent(new org.bukkit.event.world.WorldInitEvent(internal.getWorld()));
/*  907 */     System.out.print("Preparing start region for level " + (this.console.worlds.size() - 1) + " (Seed: " + internal.getSeed() + ")");
/*      */     
/*  909 */     if (internal.getWorld().getKeepSpawnInMemory()) {
/*  910 */       short short1 = 196;
/*  911 */       long i = System.currentTimeMillis();
/*  912 */       for (int j = -short1; j <= short1; j += 16) {
/*  913 */         for (int k = -short1; k <= short1; k += 16) {
/*  914 */           long l = System.currentTimeMillis();
/*      */           
/*  916 */           if (l < i) {
/*  917 */             i = l;
/*      */           }
/*      */           
/*  920 */           if (l > i + 1000L) {
/*  921 */             int i1 = (short1 * 2 + 1) * (short1 * 2 + 1);
/*  922 */             int j1 = (j + short1) * (short1 * 2 + 1) + k + 1;
/*      */             
/*  924 */             System.out.println("Preparing spawn area for " + name + ", " + j1 * 100 / i1 + "%");
/*  925 */             i = l;
/*      */           }
/*      */           
/*  928 */           BlockPosition chunkcoordinates = internal.getSpawn();
/*  929 */           internal.chunkProviderServer.getChunkAt(chunkcoordinates.getX() + j >> 4, chunkcoordinates.getZ() + k >> 4);
/*      */         }
/*      */       }
/*      */     }
/*  933 */     this.pluginManager.callEvent(new org.bukkit.event.world.WorldLoadEvent(internal.getWorld()));
/*  934 */     return internal.getWorld();
/*      */   }
/*      */   
/*      */   public boolean unloadWorld(String name, boolean save)
/*      */   {
/*  939 */     return unloadWorld(getWorld(name), save);
/*      */   }
/*      */   
/*      */   public boolean unloadWorld(World world, boolean save)
/*      */   {
/*  944 */     if (world == null) {
/*  945 */       return false;
/*      */     }
/*      */     
/*  948 */     WorldServer handle = ((CraftWorld)world).getHandle();
/*      */     
/*  950 */     if (!this.console.worlds.contains(handle)) {
/*  951 */       return false;
/*      */     }
/*      */     
/*  954 */     if (handle.dimension <= 1) {
/*  955 */       return false;
/*      */     }
/*      */     
/*  958 */     if (handle.players.size() > 0) {
/*  959 */       return false;
/*      */     }
/*      */     
/*  962 */     WorldUnloadEvent e = new WorldUnloadEvent(handle.getWorld());
/*  963 */     this.pluginManager.callEvent(e);
/*      */     
/*  965 */     if (e.isCancelled()) {
/*  966 */       return false;
/*      */     }
/*      */     
/*  969 */     if (save) {
/*      */       try {
/*  971 */         handle.save(true, null);
/*  972 */         handle.saveLevel();
/*      */       } catch (ExceptionWorldConflict ex) {
/*  974 */         getLogger().log(Level.SEVERE, null, ex);
/*      */       }
/*      */     }
/*      */     
/*  978 */     this.worlds.remove(world.getName().toLowerCase());
/*  979 */     this.console.worlds.remove(this.console.worlds.indexOf(handle));
/*      */     
/*  981 */     File parentFolder = world.getWorldFolder().getAbsoluteFile();
/*      */     
/*      */ 
/*  984 */     synchronized (RegionFileCache.class)
/*      */     {
/*  986 */       Iterator<Map.Entry<File, RegionFile>> i = RegionFileCache.a.entrySet().iterator();
/*  987 */       File child; for (; i.hasNext(); 
/*      */           
/*      */ 
/*  990 */           child != null)
/*      */       {
/*  988 */         Map.Entry<File, RegionFile> entry = (Map.Entry)i.next();
/*  989 */         child = ((File)entry.getKey()).getAbsoluteFile();
/*  990 */         continue;
/*  991 */         if (child.equals(parentFolder)) {
/*  992 */           i.remove();
/*      */           try {
/*  994 */             ((RegionFile)entry.getValue()).c();
/*      */           } catch (IOException ex) {
/*  996 */             getLogger().log(Level.SEVERE, null, ex);
/*      */           }
/*      */         }
/*      */         
/* 1000 */         child = child.getParentFile();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1005 */     return true;
/*      */   }
/*      */   
/*      */   public MinecraftServer getServer() {
/* 1009 */     return this.console;
/*      */   }
/*      */   
/*      */   public World getWorld(String name)
/*      */   {
/* 1014 */     Validate.notNull(name, "Name cannot be null");
/*      */     
/* 1016 */     return (World)this.worlds.get(name.toLowerCase());
/*      */   }
/*      */   
/*      */   public World getWorld(UUID uid)
/*      */   {
/* 1021 */     for (World world : this.worlds.values()) {
/* 1022 */       if (world.getUID().equals(uid)) {
/* 1023 */         return world;
/*      */       }
/*      */     }
/* 1026 */     return null;
/*      */   }
/*      */   
/*      */   public void addWorld(World world)
/*      */   {
/* 1031 */     if (getWorld(world.getUID()) != null) {
/* 1032 */       System.out.println("World " + world.getName() + " is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from " + world.getName() + "'s world directory if you want to be able to load the duplicate world.");
/* 1033 */       return;
/*      */     }
/* 1035 */     this.worlds.put(world.getName().toLowerCase(), world);
/*      */   }
/*      */   
/*      */   public java.util.logging.Logger getLogger()
/*      */   {
/* 1040 */     return this.logger;
/*      */   }
/*      */   
/*      */   public org.bukkit.craftbukkit.libs.jline.console.ConsoleReader getReader() {
/* 1044 */     return this.console.reader;
/*      */   }
/*      */   
/*      */   public PluginCommand getPluginCommand(String name)
/*      */   {
/* 1049 */     org.bukkit.command.Command command = this.commandMap.getCommand(name);
/*      */     
/* 1051 */     if ((command instanceof PluginCommand)) {
/* 1052 */       return (PluginCommand)command;
/*      */     }
/* 1054 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public void savePlayers()
/*      */   {
/* 1060 */     checkSaveState();
/* 1061 */     this.playerList.savePlayers();
/*      */   }
/*      */   
/*      */   public void configureDbConfig(ServerConfig config)
/*      */   {
/* 1066 */     Validate.notNull(config, "Config cannot be null");
/*      */     
/* 1068 */     DataSourceConfig ds = new DataSourceConfig();
/* 1069 */     ds.setDriver(this.configuration.getString("database.driver"));
/* 1070 */     ds.setUrl(this.configuration.getString("database.url"));
/* 1071 */     ds.setUsername(this.configuration.getString("database.username"));
/* 1072 */     ds.setPassword(this.configuration.getString("database.password"));
/* 1073 */     ds.setIsolationLevel(com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation.getLevel(this.configuration.getString("database.isolation")));
/*      */     
/* 1075 */     if (ds.getDriver().contains("sqlite")) {
/* 1076 */       config.setDatabasePlatform(new com.avaje.ebean.config.dbplatform.SQLitePlatform());
/* 1077 */       config.getDatabasePlatform().getDbDdlSyntax().setIdentity("");
/*      */     }
/*      */     
/* 1080 */     config.setDataSourceConfig(ds);
/*      */   }
/*      */   
/*      */   public boolean addRecipe(Recipe recipe)
/*      */   {
/*      */     CraftRecipe toAdd;
/* 1086 */     if ((recipe instanceof CraftRecipe)) {
/* 1087 */       toAdd = (CraftRecipe)recipe;
/*      */     } else { CraftRecipe toAdd;
/* 1089 */       if ((recipe instanceof ShapedRecipe)) {
/* 1090 */         toAdd = CraftShapedRecipe.fromBukkitRecipe((ShapedRecipe)recipe); } else { CraftRecipe toAdd;
/* 1091 */         if ((recipe instanceof ShapelessRecipe)) {
/* 1092 */           toAdd = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftShapelessRecipe.fromBukkitRecipe((ShapelessRecipe)recipe); } else { CraftRecipe toAdd;
/* 1093 */           if ((recipe instanceof FurnaceRecipe)) {
/* 1094 */             toAdd = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftFurnaceRecipe.fromBukkitRecipe((FurnaceRecipe)recipe);
/*      */           } else
/* 1096 */             return false;
/*      */         } } }
/*      */     CraftRecipe toAdd;
/* 1099 */     toAdd.addToCraftingManager();
/* 1100 */     CraftingManager.getInstance().sort();
/* 1101 */     return true;
/*      */   }
/*      */   
/*      */   public List<Recipe> getRecipesFor(org.bukkit.inventory.ItemStack result)
/*      */   {
/* 1106 */     Validate.notNull(result, "Result cannot be null");
/*      */     
/* 1108 */     List<Recipe> results = new ArrayList();
/* 1109 */     Iterator<Recipe> iter = recipeIterator();
/* 1110 */     while (iter.hasNext()) {
/* 1111 */       Recipe recipe = (Recipe)iter.next();
/* 1112 */       org.bukkit.inventory.ItemStack stack = recipe.getResult();
/* 1113 */       if (stack.getType() == result.getType())
/*      */       {
/*      */ 
/* 1116 */         if ((result.getDurability() == -1) || (result.getDurability() == stack.getDurability()))
/* 1117 */           results.add(recipe);
/*      */       }
/*      */     }
/* 1120 */     return results;
/*      */   }
/*      */   
/*      */   public Iterator<Recipe> recipeIterator()
/*      */   {
/* 1125 */     return new org.bukkit.craftbukkit.v1_8_R3.inventory.RecipeIterator();
/*      */   }
/*      */   
/*      */   public void clearRecipes()
/*      */   {
/* 1130 */     CraftingManager.getInstance().recipes.clear();
/* 1131 */     RecipesFurnace.getInstance().recipes.clear();
/* 1132 */     RecipesFurnace.getInstance().customRecipes.clear();
/*      */   }
/*      */   
/*      */   public void resetRecipes()
/*      */   {
/* 1137 */     CraftingManager.getInstance().recipes = new CraftingManager().recipes;
/* 1138 */     RecipesFurnace.getInstance().recipes = new RecipesFurnace().recipes;
/* 1139 */     RecipesFurnace.getInstance().customRecipes.clear();
/*      */   }
/*      */   
/*      */   public Map<String, String[]> getCommandAliases()
/*      */   {
/* 1144 */     ConfigurationSection section = this.commandsConfiguration.getConfigurationSection("aliases");
/* 1145 */     Map<String, String[]> result = new LinkedHashMap();
/*      */     
/* 1147 */     if (section != null) {
/* 1148 */       for (String key : section.getKeys(false)) {
/*      */         List<String> commands;
/*      */         List<String> commands;
/* 1151 */         if (section.isList(key)) {
/* 1152 */           commands = section.getStringList(key);
/*      */         } else {
/* 1154 */           commands = ImmutableList.of(section.getString(key));
/*      */         }
/*      */         
/* 1157 */         result.put(key, (String[])commands.toArray(new String[commands.size()]));
/*      */       }
/*      */     }
/*      */     
/* 1161 */     return result;
/*      */   }
/*      */   
/*      */   public void removeBukkitSpawnRadius() {
/* 1165 */     this.configuration.set("settings.spawn-radius", null);
/* 1166 */     saveConfig();
/*      */   }
/*      */   
/*      */   public int getBukkitSpawnRadius() {
/* 1170 */     return this.configuration.getInt("settings.spawn-radius", -1);
/*      */   }
/*      */   
/*      */   public String getShutdownMessage()
/*      */   {
/* 1175 */     return this.configuration.getString("settings.shutdown-message");
/*      */   }
/*      */   
/*      */   public int getSpawnRadius()
/*      */   {
/* 1180 */     return ((DedicatedServer)this.console).propertyManager.getInt("spawn-protection", 16);
/*      */   }
/*      */   
/*      */   public void setSpawnRadius(int value)
/*      */   {
/* 1185 */     this.configuration.set("settings.spawn-radius", Integer.valueOf(value));
/* 1186 */     saveConfig();
/*      */   }
/*      */   
/*      */   public boolean getOnlineMode()
/*      */   {
/* 1191 */     return this.online.value;
/*      */   }
/*      */   
/*      */   public boolean getAllowFlight()
/*      */   {
/* 1196 */     return this.console.getAllowFlight();
/*      */   }
/*      */   
/*      */   public boolean isHardcore()
/*      */   {
/* 1201 */     return this.console.isHardcore();
/*      */   }
/*      */   
/*      */   public boolean useExactLoginLocation()
/*      */   {
/* 1206 */     return this.configuration.getBoolean("settings.use-exact-login-location");
/*      */   }
/*      */   
/*      */   public ChunkGenerator getGenerator(String world) {
/* 1210 */     ConfigurationSection section = this.configuration.getConfigurationSection("worlds");
/* 1211 */     ChunkGenerator result = null;
/*      */     
/* 1213 */     if (section != null) {
/* 1214 */       section = section.getConfigurationSection(world);
/*      */       
/* 1216 */       if (section != null) {
/* 1217 */         String name = section.getString("generator");
/*      */         
/* 1219 */         if ((name != null) && (!name.equals(""))) {
/* 1220 */           String[] split = name.split(":", 2);
/* 1221 */           String id = split.length > 1 ? split[1] : null;
/* 1222 */           Plugin plugin = this.pluginManager.getPlugin(split[0]);
/*      */           
/* 1224 */           if (plugin == null) {
/* 1225 */             getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
/* 1226 */           } else if (!plugin.isEnabled()) {
/* 1227 */             getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' is not enabled yet (is it load:STARTUP?)");
/*      */           } else {
/*      */             try {
/* 1230 */               result = plugin.getDefaultWorldGenerator(world, id);
/* 1231 */               if (result == null) {
/* 1232 */                 getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' lacks a default world generator");
/*      */               }
/*      */             } catch (Throwable t) {
/* 1235 */               plugin.getLogger().log(Level.SEVERE, "Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName(), t);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1242 */     return result;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public CraftMapView getMap(short id)
/*      */   {
/* 1248 */     PersistentCollection collection = ((WorldServer)this.console.worlds.get(0)).worldMaps;
/* 1249 */     WorldMap worldmap = (WorldMap)collection.get(WorldMap.class, "map_" + id);
/* 1250 */     if (worldmap == null) {
/* 1251 */       return null;
/*      */     }
/* 1253 */     return worldmap.mapView;
/*      */   }
/*      */   
/*      */   public CraftMapView createMap(World world)
/*      */   {
/* 1258 */     Validate.notNull(world, "World cannot be null");
/*      */     
/* 1260 */     net.minecraft.server.v1_8_R3.ItemStack stack = new net.minecraft.server.v1_8_R3.ItemStack(Items.MAP, 1, -1);
/* 1261 */     WorldMap worldmap = Items.FILLED_MAP.getSavedMap(stack, ((CraftWorld)world).getHandle());
/* 1262 */     return worldmap.mapView;
/*      */   }
/*      */   
/*      */   public void shutdown()
/*      */   {
/* 1267 */     this.console.safeShutdown();
/*      */   }
/*      */   
/*      */   public int broadcast(String message, String permission)
/*      */   {
/* 1272 */     int count = 0;
/* 1273 */     Set<Permissible> permissibles = getPluginManager().getPermissionSubscriptions(permission);
/*      */     
/* 1275 */     for (Permissible permissible : permissibles) {
/* 1276 */       if (((permissible instanceof CommandSender)) && (permissible.hasPermission(permission))) {
/* 1277 */         CommandSender user = (CommandSender)permissible;
/* 1278 */         user.sendMessage(message);
/* 1279 */         count++;
/*      */       }
/*      */     }
/*      */     
/* 1283 */     return count;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public OfflinePlayer getOfflinePlayer(String name)
/*      */   {
/* 1289 */     Validate.notNull(name, "Name cannot be null");
/* 1290 */     com.google.common.base.Preconditions.checkArgument(!org.apache.commons.lang.StringUtils.isBlank(name), "Name cannot be blank");
/*      */     
/* 1292 */     OfflinePlayer result = getPlayerExact(name);
/* 1293 */     if (result == null)
/*      */     {
/* 1295 */       GameProfile profile = null;
/*      */       
/* 1297 */       if ((MinecraftServer.getServer().getOnlineMode()) || (SpigotConfig.bungee))
/*      */       {
/* 1299 */         profile = MinecraftServer.getServer().getUserCache().getProfile(name);
/*      */       }
/*      */       
/* 1302 */       if (profile == null)
/*      */       {
/* 1304 */         result = getOfflinePlayer(new GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8)), name));
/*      */       }
/*      */       else {
/* 1307 */         result = getOfflinePlayer(profile);
/*      */       }
/*      */     } else {
/* 1310 */       this.offlinePlayers.remove(result.getUniqueId());
/*      */     }
/*      */     
/* 1313 */     return result;
/*      */   }
/*      */   
/*      */   public OfflinePlayer getOfflinePlayer(UUID id)
/*      */   {
/* 1318 */     Validate.notNull(id, "UUID cannot be null");
/*      */     
/* 1320 */     OfflinePlayer result = getPlayer(id);
/* 1321 */     if (result == null) {
/* 1322 */       result = (OfflinePlayer)this.offlinePlayers.get(id);
/* 1323 */       if (result == null) {
/* 1324 */         result = new CraftOfflinePlayer(this, new GameProfile(id, null));
/* 1325 */         this.offlinePlayers.put(id, result);
/*      */       }
/*      */     } else {
/* 1328 */       this.offlinePlayers.remove(id);
/*      */     }
/*      */     
/* 1331 */     return result;
/*      */   }
/*      */   
/*      */   public OfflinePlayer getOfflinePlayer(GameProfile profile) {
/* 1335 */     OfflinePlayer player = new CraftOfflinePlayer(this, profile);
/* 1336 */     this.offlinePlayers.put(profile.getId(), player);
/* 1337 */     return player;
/*      */   }
/*      */   
/*      */ 
/*      */   public Set<String> getIPBans()
/*      */   {
/* 1343 */     return new HashSet(java.util.Arrays.asList(this.playerList.getIPBans().getEntries()));
/*      */   }
/*      */   
/*      */   public void banIP(String address)
/*      */   {
/* 1348 */     Validate.notNull(address, "Address cannot be null.");
/*      */     
/* 1350 */     getBanList(BanList.Type.IP).addBan(address, null, null, null);
/*      */   }
/*      */   
/*      */   public void unbanIP(String address)
/*      */   {
/* 1355 */     Validate.notNull(address, "Address cannot be null.");
/*      */     
/* 1357 */     getBanList(BanList.Type.IP).pardon(address);
/*      */   }
/*      */   
/*      */   public Set<OfflinePlayer> getBannedPlayers()
/*      */   {
/* 1362 */     Set<OfflinePlayer> result = new HashSet();
/*      */     
/* 1364 */     for (JsonListEntry entry : this.playerList.getProfileBans().getValues()) {
/* 1365 */       result.add(getOfflinePlayer((GameProfile)entry.getKey()));
/*      */     }
/*      */     
/* 1368 */     return result;
/*      */   }
/*      */   
/*      */   public BanList getBanList(BanList.Type type)
/*      */   {
/* 1373 */     Validate.notNull(type, "Type cannot be null");
/*      */     
/* 1375 */     switch (type) {
/*      */     case NAME: 
/* 1377 */       return new CraftIpBanList(this.playerList.getIPBans());
/*      */     }
/*      */     
/* 1380 */     return new CraftProfileBanList(this.playerList.getProfileBans());
/*      */   }
/*      */   
/*      */ 
/*      */   public void setWhitelist(boolean value)
/*      */   {
/* 1386 */     this.playerList.setHasWhitelist(value);
/* 1387 */     this.console.getPropertyManager().setProperty("white-list", Boolean.valueOf(value));
/*      */   }
/*      */   
/*      */   public Set<OfflinePlayer> getWhitelistedPlayers()
/*      */   {
/* 1392 */     Set<OfflinePlayer> result = new java.util.LinkedHashSet();
/*      */     
/* 1394 */     for (JsonListEntry entry : this.playerList.getWhitelist().getValues()) {
/* 1395 */       result.add(getOfflinePlayer((GameProfile)entry.getKey()));
/*      */     }
/*      */     
/* 1398 */     return result;
/*      */   }
/*      */   
/*      */   public Set<OfflinePlayer> getOperators()
/*      */   {
/* 1403 */     Set<OfflinePlayer> result = new HashSet();
/*      */     
/* 1405 */     for (JsonListEntry entry : this.playerList.getOPs().getValues()) {
/* 1406 */       result.add(getOfflinePlayer((GameProfile)entry.getKey()));
/*      */     }
/*      */     
/* 1409 */     return result;
/*      */   }
/*      */   
/*      */   public void reloadWhitelist()
/*      */   {
/* 1414 */     this.playerList.reloadWhitelist();
/*      */   }
/*      */   
/*      */   public GameMode getDefaultGameMode()
/*      */   {
/* 1419 */     return GameMode.getByValue(((WorldServer)this.console.worlds.get(0)).getWorldData().getGameType().getId());
/*      */   }
/*      */   
/*      */   public void setDefaultGameMode(GameMode mode)
/*      */   {
/* 1424 */     Validate.notNull(mode, "Mode cannot be null");
/*      */     
/* 1426 */     for (World world : getWorlds()) {
/* 1427 */       ((CraftWorld)world).getHandle().worldData.setGameType(WorldSettings.EnumGamemode.getById(mode.getValue()));
/*      */     }
/*      */   }
/*      */   
/*      */   public org.bukkit.command.ConsoleCommandSender getConsoleSender()
/*      */   {
/* 1433 */     return this.console.console;
/*      */   }
/*      */   
/*      */   public EntityMetadataStore getEntityMetadata() {
/* 1437 */     return this.entityMetadata;
/*      */   }
/*      */   
/*      */   public PlayerMetadataStore getPlayerMetadata() {
/* 1441 */     return this.playerMetadata;
/*      */   }
/*      */   
/*      */   public WorldMetadataStore getWorldMetadata() {
/* 1445 */     return this.worldMetadata;
/*      */   }
/*      */   
/*      */   public File getWorldContainer()
/*      */   {
/* 1450 */     if (getServer().universe != null) {
/* 1451 */       return getServer().universe;
/*      */     }
/*      */     
/* 1454 */     if (this.container == null) {
/* 1455 */       this.container = new File(this.configuration.getString("settings.world-container", "."));
/*      */     }
/*      */     
/* 1458 */     return this.container;
/*      */   }
/*      */   
/*      */   public OfflinePlayer[] getOfflinePlayers()
/*      */   {
/* 1463 */     WorldNBTStorage storage = (WorldNBTStorage)((WorldServer)this.console.worlds.get(0)).getDataManager();
/* 1464 */     String[] files = storage.getPlayerDir().list(new org.bukkit.craftbukkit.v1_8_R3.util.DatFileFilter());
/* 1465 */     Set<OfflinePlayer> players = new HashSet();
/*      */     String[] arrayOfString1;
/* 1467 */     int i = (arrayOfString1 = files).length; for (int j = 0; j < i; j++) { String file = arrayOfString1[j];
/*      */       try {
/* 1469 */         players.add(getOfflinePlayer(UUID.fromString(file.substring(0, file.length() - 4))));
/*      */       }
/*      */       catch (IllegalArgumentException localIllegalArgumentException) {}
/*      */     }
/*      */     
/*      */ 
/* 1475 */     players.addAll(getOnlinePlayers());
/*      */     
/* 1477 */     return (OfflinePlayer[])players.toArray(new OfflinePlayer[players.size()]);
/*      */   }
/*      */   
/*      */   public Messenger getMessenger()
/*      */   {
/* 1482 */     return this.messenger;
/*      */   }
/*      */   
/*      */   public void sendPluginMessage(Plugin source, String channel, byte[] message)
/*      */   {
/* 1487 */     StandardMessenger.validatePluginMessage(getMessenger(), source, channel, message);
/*      */     
/* 1489 */     for (Player player : getOnlinePlayers()) {
/* 1490 */       player.sendPluginMessage(source, channel, message);
/*      */     }
/*      */   }
/*      */   
/*      */   public Set<String> getListeningPluginChannels()
/*      */   {
/* 1496 */     Set<String> result = new HashSet();
/*      */     
/* 1498 */     for (Player player : getOnlinePlayers()) {
/* 1499 */       result.addAll(player.getListeningPluginChannels());
/*      */     }
/*      */     
/* 1502 */     return result;
/*      */   }
/*      */   
/*      */ 
/*      */   public Inventory createInventory(InventoryHolder owner, InventoryType type)
/*      */   {
/* 1508 */     return new CraftInventoryCustom(owner, type);
/*      */   }
/*      */   
/*      */   public Inventory createInventory(InventoryHolder owner, InventoryType type, String title)
/*      */   {
/* 1513 */     return new CraftInventoryCustom(owner, type, title);
/*      */   }
/*      */   
/*      */   public Inventory createInventory(InventoryHolder owner, int size) throws IllegalArgumentException
/*      */   {
/* 1518 */     Validate.isTrue(size % 9 == 0, "Chests must have a size that is a multiple of 9!");
/* 1519 */     return new CraftInventoryCustom(owner, size);
/*      */   }
/*      */   
/*      */   public Inventory createInventory(InventoryHolder owner, int size, String title) throws IllegalArgumentException
/*      */   {
/* 1524 */     Validate.isTrue(size % 9 == 0, "Chests must have a size that is a multiple of 9!");
/* 1525 */     return new CraftInventoryCustom(owner, size, title);
/*      */   }
/*      */   
/*      */   public org.bukkit.help.HelpMap getHelpMap()
/*      */   {
/* 1530 */     return this.helpMap;
/*      */   }
/*      */   
/*      */   public SimpleCommandMap getCommandMap() {
/* 1534 */     return this.commandMap;
/*      */   }
/*      */   
/*      */   public int getMonsterSpawnLimit()
/*      */   {
/* 1539 */     return this.monsterSpawn;
/*      */   }
/*      */   
/*      */   public int getAnimalSpawnLimit()
/*      */   {
/* 1544 */     return this.animalSpawn;
/*      */   }
/*      */   
/*      */   public int getWaterAnimalSpawnLimit()
/*      */   {
/* 1549 */     return this.waterAnimalSpawn;
/*      */   }
/*      */   
/*      */   public int getAmbientSpawnLimit()
/*      */   {
/* 1554 */     return this.ambientSpawn;
/*      */   }
/*      */   
/*      */   public boolean isPrimaryThread()
/*      */   {
/* 1559 */     return Thread.currentThread().equals(this.console.primaryThread);
/*      */   }
/*      */   
/*      */   public String getMotd()
/*      */   {
/* 1564 */     return this.console.getMotd();
/*      */   }
/*      */   
/*      */   public Warning.WarningState getWarningState()
/*      */   {
/* 1569 */     return this.warningState;
/*      */   }
/*      */   
/*      */   public List<String> tabComplete(ICommandListener sender, String message) {
/* 1573 */     if (!(sender instanceof EntityPlayer)) {
/* 1574 */       return ImmutableList.of();
/*      */     }
/*      */     
/* 1577 */     Player player = ((EntityPlayer)sender).getBukkitEntity();
/* 1578 */     if (message.startsWith("/")) {
/* 1579 */       return tabCompleteCommand(player, message);
/*      */     }
/* 1581 */     return tabCompleteChat(player, message);
/*      */   }
/*      */   
/*      */ 
/*      */   public List<String> tabCompleteCommand(Player player, String message)
/*      */   {
/* 1587 */     if (((SpigotConfig.tabComplete < 0) || (message.length() <= SpigotConfig.tabComplete)) && (!message.contains(" ")))
/*      */     {
/* 1589 */       return ImmutableList.of();
/*      */     }
/*      */     
/*      */ 
/* 1593 */     List<String> completions = null;
/*      */     try {
/* 1595 */       completions = getCommandMap().tabComplete(player, message.substring(1));
/*      */     } catch (CommandException ex) {
/* 1597 */       player.sendMessage(org.bukkit.ChatColor.RED + "An internal error occurred while attempting to tab-complete this command");
/* 1598 */       getLogger().log(Level.SEVERE, "Exception when " + player.getName() + " attempted to tab complete " + message, ex);
/*      */     }
/*      */     
/* 1601 */     return completions == null ? ImmutableList.of() : completions;
/*      */   }
/*      */   
/*      */   public List<String> tabCompleteChat(Player player, String message) {
/* 1605 */     List<String> completions = new ArrayList();
/* 1606 */     PlayerChatTabCompleteEvent event = new PlayerChatTabCompleteEvent(player, message, completions);
/* 1607 */     String token = event.getLastToken();
/* 1608 */     for (Player p : getOnlinePlayers()) {
/* 1609 */       if ((player.canSee(p)) && (org.bukkit.util.StringUtil.startsWithIgnoreCase(p.getName(), token))) {
/* 1610 */         completions.add(p.getName());
/*      */       }
/*      */     }
/* 1613 */     this.pluginManager.callEvent(event);
/*      */     
/* 1615 */     Iterator<?> it = completions.iterator();
/* 1616 */     while (it.hasNext()) {
/* 1617 */       Object current = it.next();
/* 1618 */       if (!(current instanceof String))
/*      */       {
/* 1620 */         it.remove();
/*      */       }
/*      */     }
/* 1623 */     Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
/* 1624 */     return completions;
/*      */   }
/*      */   
/*      */   public CraftItemFactory getItemFactory()
/*      */   {
/* 1629 */     return CraftItemFactory.instance();
/*      */   }
/*      */   
/*      */   public CraftScoreboardManager getScoreboardManager()
/*      */   {
/* 1634 */     return this.scoreboardManager;
/*      */   }
/*      */   
/*      */   public void checkSaveState() {
/* 1638 */     if ((this.playerCommandState) || (this.printSaveWarning) || (this.console.autosavePeriod <= 0)) {
/* 1639 */       return;
/*      */     }
/* 1641 */     this.printSaveWarning = true;
/* 1642 */     getLogger().log(Level.WARNING, "A manual (plugin-induced) save has been detected while server is configured to auto-save. This may affect performance.", this.warningState == Warning.WarningState.ON ? new Throwable() : null);
/*      */   }
/*      */   
/*      */   public CraftIconCache getServerIcon()
/*      */   {
/* 1647 */     return this.icon;
/*      */   }
/*      */   
/*      */   public CraftIconCache loadServerIcon(File file) throws Exception
/*      */   {
/* 1652 */     Validate.notNull(file, "File cannot be null");
/* 1653 */     if (!file.isFile()) {
/* 1654 */       throw new IllegalArgumentException(file + " is not a file");
/*      */     }
/* 1656 */     return loadServerIcon0(file);
/*      */   }
/*      */   
/*      */   static CraftIconCache loadServerIcon0(File file) throws Exception {
/* 1660 */     return loadServerIcon0(ImageIO.read(file));
/*      */   }
/*      */   
/*      */   public CraftIconCache loadServerIcon(BufferedImage image) throws Exception
/*      */   {
/* 1665 */     Validate.notNull(image, "Image cannot be null");
/* 1666 */     return loadServerIcon0(image);
/*      */   }
/*      */   
/*      */   static CraftIconCache loadServerIcon0(BufferedImage image) throws Exception {
/* 1670 */     ByteBuf bytebuf = io.netty.buffer.Unpooled.buffer();
/*      */     
/* 1672 */     Validate.isTrue(image.getWidth() == 64, "Must be 64 pixels wide");
/* 1673 */     Validate.isTrue(image.getHeight() == 64, "Must be 64 pixels high");
/* 1674 */     ImageIO.write(image, "PNG", new io.netty.buffer.ByteBufOutputStream(bytebuf));
/* 1675 */     ByteBuf bytebuf1 = io.netty.handler.codec.base64.Base64.encode(bytebuf);
/*      */     
/* 1677 */     return new CraftIconCache("data:image/png;base64," + bytebuf1.toString(Charsets.UTF_8));
/*      */   }
/*      */   
/*      */   public void setIdleTimeout(int threshold)
/*      */   {
/* 1682 */     this.console.setIdleTimeout(threshold);
/*      */   }
/*      */   
/*      */   public int getIdleTimeout()
/*      */   {
/* 1687 */     return this.console.getIdleTimeout();
/*      */   }
/*      */   
/*      */   public org.bukkit.generator.ChunkGenerator.ChunkData createChunkData(World world)
/*      */   {
/* 1692 */     return new org.bukkit.craftbukkit.v1_8_R3.generator.CraftChunkData(world);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public org.bukkit.UnsafeValues getUnsafe()
/*      */   {
/* 1698 */     return CraftMagicNumbers.INSTANCE;
/*      */   }
/*      */   
/* 1701 */   private final Server.Spigot spigot = new Server.Spigot()
/*      */   {
/*      */ 
/*      */     public YamlConfiguration getConfig()
/*      */     {
/*      */ 
/* 1707 */       return SpigotConfig.config;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void restart() {}
/*      */     
/*      */ 
/*      */     public void broadcast(BaseComponent component)
/*      */     {
/* 1717 */       for (Player player : CraftServer.this.getOnlinePlayers()) {
/* 1718 */         player.spigot().sendMessage(component);
/*      */       }
/*      */     }
/*      */     
/*      */     public void broadcast(BaseComponent... components)
/*      */     {
/* 1724 */       for (Player player : CraftServer.this.getOnlinePlayers()) {
/* 1725 */         player.spigot().sendMessage(components);
/*      */       }
/*      */     }
/*      */   };
/*      */   
/*      */   public Server.Spigot spigot()
/*      */   {
/* 1732 */     return this.spigot;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */