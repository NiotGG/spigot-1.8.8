/*     */ package org.bukkit.plugin.java;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.EbeanServerFactory;
/*     */ import com.avaje.ebean.config.DataSourceConfig;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.io.ByteStreams;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.Warning.WarningState;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.configuration.InvalidConfigurationException;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ import org.bukkit.plugin.AuthorNagException;
/*     */ import org.bukkit.plugin.PluginAwareness.Flags;
/*     */ import org.bukkit.plugin.PluginBase;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginLoader;
/*     */ import org.bukkit.plugin.PluginLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JavaPlugin
/*     */   extends PluginBase
/*     */ {
/*  48 */   private boolean isEnabled = false;
/*  49 */   private PluginLoader loader = null;
/*  50 */   private Server server = null;
/*  51 */   private File file = null;
/*  52 */   private PluginDescriptionFile description = null;
/*  53 */   private File dataFolder = null;
/*  54 */   private ClassLoader classLoader = null;
/*  55 */   private boolean naggable = true;
/*  56 */   private EbeanServer ebean = null;
/*  57 */   private FileConfiguration newConfig = null;
/*  58 */   private File configFile = null;
/*  59 */   private PluginLogger logger = null;
/*     */   
/*     */   public JavaPlugin() {
/*  62 */     ClassLoader classLoader = getClass().getClassLoader();
/*  63 */     if (!(classLoader instanceof PluginClassLoader)) {
/*  64 */       throw new IllegalStateException("JavaPlugin requires " + PluginClassLoader.class.getName());
/*     */     }
/*  66 */     ((PluginClassLoader)classLoader).initialize(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected JavaPlugin(PluginLoader loader, Server server, PluginDescriptionFile description, File dataFolder, File file)
/*     */   {
/*  83 */     ClassLoader classLoader = getClass().getClassLoader();
/*  84 */     if ((classLoader instanceof PluginClassLoader)) {
/*  85 */       throw new IllegalStateException("Cannot use initialization constructor at runtime");
/*     */     }
/*  87 */     init(loader, server, description, dataFolder, file, classLoader);
/*     */   }
/*     */   
/*     */   protected JavaPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
/*  91 */     ClassLoader classLoader = getClass().getClassLoader();
/*  92 */     if ((classLoader instanceof PluginClassLoader)) {
/*  93 */       throw new IllegalStateException("Cannot use initialization constructor at runtime");
/*     */     }
/*  95 */     init(loader, loader.server, description, dataFolder, file, classLoader);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final File getDataFolder()
/*     */   {
/* 106 */     return this.dataFolder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final PluginLoader getPluginLoader()
/*     */   {
/* 116 */     return this.loader;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Server getServer()
/*     */   {
/* 126 */     return this.server;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isEnabled()
/*     */   {
/* 137 */     return this.isEnabled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected File getFile()
/*     */   {
/* 146 */     return this.file;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final PluginDescriptionFile getDescription()
/*     */   {
/* 156 */     return this.description;
/*     */   }
/*     */   
/*     */   public FileConfiguration getConfig()
/*     */   {
/* 161 */     if (this.newConfig == null) {
/* 162 */       reloadConfig();
/*     */     }
/* 164 */     return this.newConfig;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Reader getTextResource(String file)
/*     */   {
/* 180 */     InputStream in = getResource(file);
/*     */     
/* 182 */     return in == null ? null : new InputStreamReader(in, (isStrictlyUTF8()) || (FileConfiguration.UTF8_OVERRIDE) ? Charsets.UTF_8 : Charset.defaultCharset());
/*     */   }
/*     */   
/*     */ 
/*     */   public void reloadConfig()
/*     */   {
/* 188 */     this.newConfig = YamlConfiguration.loadConfiguration(this.configFile);
/*     */     
/* 190 */     InputStream defConfigStream = getResource("config.yml");
/* 191 */     if (defConfigStream == null) {
/*     */       return;
/*     */     }
/*     */     YamlConfiguration defConfig;
/*     */     YamlConfiguration defConfig;
/* 196 */     if ((isStrictlyUTF8()) || (FileConfiguration.UTF8_OVERRIDE)) {
/* 197 */       defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
/*     */     }
/*     */     else {
/* 200 */       defConfig = new YamlConfiguration();
/*     */       try {
/* 202 */         contents = ByteStreams.toByteArray(defConfigStream);
/*     */       } catch (IOException e) { byte[] contents;
/* 204 */         getLogger().log(Level.SEVERE, "Unexpected failure reading config.yml", e); return;
/*     */       }
/*     */       
/*     */       byte[] contents;
/* 208 */       String text = new String(contents, Charset.defaultCharset());
/* 209 */       if (!text.equals(new String(contents, Charsets.UTF_8))) {
/* 210 */         getLogger().warning("Default system encoding may have misread config.yml from plugin jar");
/*     */       }
/*     */       try
/*     */       {
/* 214 */         defConfig.loadFromString(text);
/*     */       } catch (InvalidConfigurationException e) {
/* 216 */         getLogger().log(Level.SEVERE, "Cannot load configuration from jar", e);
/*     */       }
/*     */     }
/*     */     
/* 220 */     this.newConfig.setDefaults(defConfig);
/*     */   }
/*     */   
/*     */   private boolean isStrictlyUTF8() {
/* 224 */     return getDescription().getAwareness().contains(PluginAwareness.Flags.UTF8);
/*     */   }
/*     */   
/*     */   public void saveConfig()
/*     */   {
/*     */     try {
/* 230 */       getConfig().save(this.configFile);
/*     */     } catch (IOException ex) {
/* 232 */       this.logger.log(Level.SEVERE, "Could not save config to " + this.configFile, ex);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveDefaultConfig()
/*     */   {
/* 238 */     if (!this.configFile.exists()) {
/* 239 */       saveResource("config.yml", false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveResource(String resourcePath, boolean replace)
/*     */   {
/* 245 */     if ((resourcePath == null) || (resourcePath.equals(""))) {
/* 246 */       throw new IllegalArgumentException("ResourcePath cannot be null or empty");
/*     */     }
/*     */     
/* 249 */     resourcePath = resourcePath.replace('\\', '/');
/* 250 */     InputStream in = getResource(resourcePath);
/* 251 */     if (in == null) {
/* 252 */       throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + this.file);
/*     */     }
/*     */     
/* 255 */     File outFile = new File(this.dataFolder, resourcePath);
/* 256 */     int lastIndex = resourcePath.lastIndexOf('/');
/* 257 */     File outDir = new File(this.dataFolder, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
/*     */     
/* 259 */     if (!outDir.exists()) {
/* 260 */       outDir.mkdirs();
/*     */     }
/*     */     try
/*     */     {
/* 264 */       if ((!outFile.exists()) || (replace)) {
/* 265 */         OutputStream out = new FileOutputStream(outFile);
/* 266 */         byte[] buf = new byte['Ѐ'];
/*     */         int len;
/* 268 */         while ((len = in.read(buf)) > 0) { int len;
/* 269 */           out.write(buf, 0, len);
/*     */         }
/* 271 */         out.close();
/* 272 */         in.close();
/*     */       } else {
/* 274 */         this.logger.log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
/*     */       }
/*     */     } catch (IOException ex) {
/* 277 */       this.logger.log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
/*     */     }
/*     */   }
/*     */   
/*     */   public InputStream getResource(String filename)
/*     */   {
/* 283 */     if (filename == null) {
/* 284 */       throw new IllegalArgumentException("Filename cannot be null");
/*     */     }
/*     */     try
/*     */     {
/* 288 */       URL url = getClassLoader().getResource(filename);
/*     */       
/* 290 */       if (url == null) {
/* 291 */         return null;
/*     */       }
/*     */       
/* 294 */       URLConnection connection = url.openConnection();
/* 295 */       connection.setUseCaches(false);
/* 296 */       return connection.getInputStream();
/*     */     } catch (IOException localIOException) {}
/* 298 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final ClassLoader getClassLoader()
/*     */   {
/* 308 */     return this.classLoader;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void setEnabled(boolean enabled)
/*     */   {
/* 317 */     if (this.isEnabled != enabled) {
/* 318 */       this.isEnabled = enabled;
/*     */       
/* 320 */       if (this.isEnabled) {
/* 321 */         onEnable();
/*     */       } else {
/* 323 */         onDisable();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected final void initialize(PluginLoader loader, Server server, PluginDescriptionFile description, File dataFolder, File file, ClassLoader classLoader)
/*     */   {
/* 340 */     if (server.getWarningState() == Warning.WarningState.OFF) {
/* 341 */       return;
/*     */     }
/* 343 */     getLogger().log(Level.WARNING, getClass().getName() + " is already initialized", server.getWarningState() == Warning.WarningState.DEFAULT ? null : new AuthorNagException("Explicit initialization"));
/*     */   }
/*     */   
/*     */   final void init(PluginLoader loader, Server server, PluginDescriptionFile description, File dataFolder, File file, ClassLoader classLoader) {
/* 347 */     this.loader = loader;
/* 348 */     this.server = server;
/* 349 */     this.file = file;
/* 350 */     this.description = description;
/* 351 */     this.dataFolder = dataFolder;
/* 352 */     this.classLoader = classLoader;
/* 353 */     this.configFile = new File(dataFolder, "config.yml");
/* 354 */     this.logger = new PluginLogger(this);
/*     */     
/* 356 */     if (description.isDatabaseEnabled()) {
/* 357 */       ServerConfig db = new ServerConfig();
/*     */       
/* 359 */       db.setDefaultServer(false);
/* 360 */       db.setRegister(false);
/* 361 */       db.setClasses(getDatabaseClasses());
/* 362 */       db.setName(description.getName());
/* 363 */       server.configureDbConfig(db);
/*     */       
/* 365 */       DataSourceConfig ds = db.getDataSourceConfig();
/*     */       
/* 367 */       ds.setUrl(replaceDatabaseString(ds.getUrl()));
/* 368 */       dataFolder.mkdirs();
/*     */       
/* 370 */       ClassLoader previous = Thread.currentThread().getContextClassLoader();
/*     */       
/* 372 */       Thread.currentThread().setContextClassLoader(classLoader);
/* 373 */       this.ebean = EbeanServerFactory.create(db);
/* 374 */       Thread.currentThread().setContextClassLoader(previous);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Class<?>> getDatabaseClasses()
/*     */   {
/* 384 */     return new ArrayList();
/*     */   }
/*     */   
/*     */   private String replaceDatabaseString(String input) {
/* 388 */     input = input.replaceAll("\\{DIR\\}", this.dataFolder.getPath().replaceAll("\\\\", "/") + "/");
/* 389 */     input = input.replaceAll("\\{NAME\\}", this.description.getName().replaceAll("[^\\w_-]", ""));
/* 390 */     return input;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public final boolean isInitialized()
/*     */   {
/* 402 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
/*     */   {
/* 410 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
/*     */   {
/* 418 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PluginCommand getCommand(String name)
/*     */   {
/* 430 */     String alias = name.toLowerCase();
/* 431 */     PluginCommand command = getServer().getPluginCommand(alias);
/*     */     
/* 433 */     if ((command == null) || (command.getPlugin() != this)) {
/* 434 */       command = getServer().getPluginCommand(this.description.getName().toLowerCase() + ":" + alias);
/*     */     }
/*     */     
/* 437 */     if ((command != null) && (command.getPlugin() == this)) {
/* 438 */       return command;
/*     */     }
/* 440 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onLoad() {}
/*     */   
/*     */ 
/*     */   public void onDisable() {}
/*     */   
/*     */ 
/*     */   public void onEnable() {}
/*     */   
/*     */ 
/*     */   public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
/*     */   {
/* 455 */     return null;
/*     */   }
/*     */   
/*     */   public final boolean isNaggable()
/*     */   {
/* 460 */     return this.naggable;
/*     */   }
/*     */   
/*     */   public final void setNaggable(boolean canNag)
/*     */   {
/* 465 */     this.naggable = canNag;
/*     */   }
/*     */   
/*     */   public EbeanServer getDatabase()
/*     */   {
/* 470 */     return this.ebean;
/*     */   }
/*     */   
/*     */   protected void installDDL() {
/* 474 */     SpiEbeanServer serv = (SpiEbeanServer)getDatabase();
/* 475 */     DdlGenerator gen = serv.getDdlGenerator();
/*     */     
/* 477 */     gen.runScript(false, gen.generateCreateDdl());
/*     */   }
/*     */   
/*     */   protected void removeDDL() {
/* 481 */     SpiEbeanServer serv = (SpiEbeanServer)getDatabase();
/* 482 */     DdlGenerator gen = serv.getDdlGenerator();
/*     */     
/* 484 */     gen.runScript(true, gen.generateDropDdl());
/*     */   }
/*     */   
/*     */   public final Logger getLogger()
/*     */   {
/* 489 */     return this.logger;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 494 */     return this.description.getFullName();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T extends JavaPlugin> T getPlugin(Class<T> clazz)
/*     */   {
/* 521 */     Validate.notNull(clazz, "Null class cannot have a plugin");
/* 522 */     if (!JavaPlugin.class.isAssignableFrom(clazz)) {
/* 523 */       throw new IllegalArgumentException(clazz + " does not extend " + JavaPlugin.class);
/*     */     }
/* 525 */     ClassLoader cl = clazz.getClassLoader();
/* 526 */     if (!(cl instanceof PluginClassLoader)) {
/* 527 */       throw new IllegalArgumentException(clazz + " is not initialized by " + PluginClassLoader.class);
/*     */     }
/* 529 */     JavaPlugin plugin = ((PluginClassLoader)cl).plugin;
/* 530 */     if (plugin == null) {
/* 531 */       throw new IllegalStateException("Cannot get plugin for " + clazz + " from a static initializer");
/*     */     }
/* 533 */     return (JavaPlugin)clazz.cast(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JavaPlugin getProvidingPlugin(Class<?> clazz)
/*     */   {
/* 549 */     Validate.notNull(clazz, "Null class cannot have a plugin");
/* 550 */     ClassLoader cl = clazz.getClassLoader();
/* 551 */     if (!(cl instanceof PluginClassLoader)) {
/* 552 */       throw new IllegalArgumentException(clazz + " is not provided by " + PluginClassLoader.class);
/*     */     }
/* 554 */     JavaPlugin plugin = ((PluginClassLoader)cl).plugin;
/* 555 */     if (plugin == null) {
/* 556 */       throw new IllegalStateException("Cannot get plugin for " + clazz + " from a static initializer");
/*     */     }
/* 558 */     return plugin;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\java\JavaPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */