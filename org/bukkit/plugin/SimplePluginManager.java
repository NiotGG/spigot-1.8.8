/*     */ package org.bukkit.plugin;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.PluginCommandYamlParser;
/*     */ import org.bukkit.command.SimpleCommandMap;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.permissions.PermissionDefault;
/*     */ import org.bukkit.plugin.messaging.Messenger;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.util.FileUtil;
/*     */ 
/*     */ public final class SimplePluginManager implements PluginManager
/*     */ {
/*     */   private final Server server;
/*  42 */   private final Map<Pattern, PluginLoader> fileAssociations = new HashMap();
/*  43 */   private final List<Plugin> plugins = new ArrayList();
/*  44 */   private final Map<String, Plugin> lookupNames = new HashMap();
/*  45 */   private static File updateDirectory = null;
/*     */   private final SimpleCommandMap commandMap;
/*  47 */   private final Map<String, Permission> permissions = new HashMap();
/*  48 */   private final Map<Boolean, Set<Permission>> defaultPerms = new LinkedHashMap();
/*  49 */   private final Map<String, Map<Permissible, Boolean>> permSubs = new HashMap();
/*  50 */   private final Map<Boolean, Map<Permissible, Boolean>> defSubs = new HashMap();
/*  51 */   private boolean useTimings = false;
/*     */   
/*     */   public SimplePluginManager(Server instance, SimpleCommandMap commandMap) {
/*  54 */     this.server = instance;
/*  55 */     this.commandMap = commandMap;
/*     */     
/*  57 */     this.defaultPerms.put(Boolean.valueOf(true), new HashSet());
/*  58 */     this.defaultPerms.put(Boolean.valueOf(false), new HashSet());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void registerInterface(Class<? extends PluginLoader> loader)
/*     */     throws IllegalArgumentException
/*     */   {
/*  71 */     if (PluginLoader.class.isAssignableFrom(loader))
/*     */     {
/*     */       try
/*     */       {
/*  75 */         Constructor<? extends PluginLoader> constructor = loader.getConstructor(new Class[] { Server.class });
/*  76 */         instance = (PluginLoader)constructor.newInstance(new Object[] { this.server });
/*     */       } catch (NoSuchMethodException ex) { PluginLoader instance;
/*  78 */         String className = loader.getName();
/*     */         
/*  80 */         throw new IllegalArgumentException(String.format("Class %s does not have a public %s(Server) constructor", new Object[] { className, className }), ex);
/*     */       } catch (Exception ex) {
/*  82 */         throw new IllegalArgumentException(String.format("Unexpected exception %s while attempting to construct a new instance of %s", new Object[] { ex.getClass().getName(), loader.getName() }), ex);
/*     */       }
/*     */     } else {
/*  85 */       throw new IllegalArgumentException(String.format("Class %s does not implement interface PluginLoader", new Object[] { loader.getName() }));
/*     */     }
/*     */     PluginLoader instance;
/*  88 */     Pattern[] patterns = instance.getPluginFileFilters();
/*     */     
/*  90 */     synchronized (this) { Pattern[] arrayOfPattern1;
/*  91 */       int i = (arrayOfPattern1 = patterns).length; for (int j = 0; j < i; j++) { Pattern pattern = arrayOfPattern1[j];
/*  92 */         this.fileAssociations.put(pattern, instance);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Plugin[] loadPlugins(File directory)
/*     */   {
/* 104 */     Validate.notNull(directory, "Directory cannot be null");
/* 105 */     Validate.isTrue(directory.isDirectory(), "Directory must be a directory");
/*     */     
/* 107 */     List<Plugin> result = new ArrayList();
/* 108 */     Set<Pattern> filters = this.fileAssociations.keySet();
/*     */     
/* 110 */     if (!this.server.getUpdateFolder().equals("")) {
/* 111 */       updateDirectory = new File(directory, this.server.getUpdateFolder());
/*     */     }
/*     */     
/* 114 */     Map<String, File> plugins = new HashMap();
/* 115 */     Set<String> loadedPlugins = new HashSet();
/* 116 */     Map<String, Collection<String>> dependencies = new HashMap();
/* 117 */     Map<String, Collection<String>> softDependencies = new HashMap();
/*     */     
/*     */     File[] arrayOfFile;
/* 120 */     int i = (arrayOfFile = directory.listFiles()).length; for (int j = 0; j < i; j++) { File file = arrayOfFile[j];
/* 121 */       PluginLoader loader = null;
/* 122 */       for (Pattern filter : filters) {
/* 123 */         Matcher match = filter.matcher(file.getName());
/* 124 */         if (match.find()) {
/* 125 */           loader = (PluginLoader)this.fileAssociations.get(filter);
/*     */         }
/*     */       }
/*     */       
/* 129 */       if (loader != null)
/*     */       {
/* 131 */         PluginDescriptionFile description = null;
/*     */         try {
/* 133 */           description = loader.getPluginDescription(file);
/* 134 */           String name = description.getName();
/* 135 */           if ((name.equalsIgnoreCase("bukkit")) || (name.equalsIgnoreCase("minecraft")) || (name.equalsIgnoreCase("mojang"))) {
/* 136 */             this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "': Restricted Name");
/* 137 */             continue; }
/* 138 */           if (description.rawName.indexOf(' ') != -1) {
/* 139 */             this.server.getLogger().warning(String.format(
/* 140 */               "Plugin `%s' uses the space-character (0x20) in its name `%s' - this is discouraged", new Object[] {
/* 141 */               description.getFullName(), 
/* 142 */               description.rawName }));
/*     */           }
/*     */         }
/*     */         catch (InvalidDescriptionException ex) {
/* 146 */           this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex);
/* 147 */           continue;
/*     */         }
/*     */         
/* 150 */         File replacedFile = (File)plugins.put(description.getName(), file);
/* 151 */         if (replacedFile != null) {
/* 152 */           this.server.getLogger().severe(String.format(
/* 153 */             "Ambiguous plugin name `%s' for files `%s' and `%s' in `%s'", new Object[] {
/* 154 */             description.getName(), 
/* 155 */             file.getPath(), 
/* 156 */             replacedFile.getPath(), 
/* 157 */             directory.getPath() }));
/*     */         }
/*     */         
/*     */ 
/* 161 */         Collection<String> softDependencySet = description.getSoftDepend();
/* 162 */         if ((softDependencySet != null) && (!softDependencySet.isEmpty())) {
/* 163 */           if (softDependencies.containsKey(description.getName()))
/*     */           {
/* 165 */             ((Collection)softDependencies.get(description.getName())).addAll(softDependencySet);
/*     */           } else {
/* 167 */             softDependencies.put(description.getName(), new LinkedList(softDependencySet));
/*     */           }
/*     */         }
/*     */         
/* 171 */         Collection<String> dependencySet = description.getDepend();
/* 172 */         if ((dependencySet != null) && (!dependencySet.isEmpty())) {
/* 173 */           dependencies.put(description.getName(), new LinkedList(dependencySet));
/*     */         }
/*     */         
/* 176 */         Collection<String> loadBeforeSet = description.getLoadBefore();
/* 177 */         if ((loadBeforeSet != null) && (!loadBeforeSet.isEmpty())) {
/* 178 */           for (String loadBeforeTarget : loadBeforeSet) {
/* 179 */             if (softDependencies.containsKey(loadBeforeTarget)) {
/* 180 */               ((Collection)softDependencies.get(loadBeforeTarget)).add(description.getName());
/*     */             }
/*     */             else {
/* 183 */               Collection<String> shortSoftDependency = new LinkedList();
/* 184 */               shortSoftDependency.add(description.getName());
/* 185 */               softDependencies.put(loadBeforeTarget, shortSoftDependency);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 191 */     while (!plugins.isEmpty()) {
/* 192 */       boolean missingDependency = true;
/* 193 */       Object pluginIterator = plugins.keySet().iterator();
/*     */       
/* 195 */       while (((Iterator)pluginIterator).hasNext()) {
/* 196 */         String plugin = (String)((Iterator)pluginIterator).next();
/*     */         
/* 198 */         if (dependencies.containsKey(plugin)) {
/* 199 */           Object dependencyIterator = ((Collection)dependencies.get(plugin)).iterator();
/*     */           
/* 201 */           while (((Iterator)dependencyIterator).hasNext()) {
/* 202 */             String dependency = (String)((Iterator)dependencyIterator).next();
/*     */             
/*     */ 
/* 205 */             if (loadedPlugins.contains(dependency)) {
/* 206 */               ((Iterator)dependencyIterator).remove();
/*     */ 
/*     */             }
/* 209 */             else if (!plugins.containsKey(dependency)) {
/* 210 */               missingDependency = false;
/* 211 */               File file = (File)plugins.get(plugin);
/* 212 */               ((Iterator)pluginIterator).remove();
/* 213 */               softDependencies.remove(plugin);
/* 214 */               dependencies.remove(plugin);
/*     */               
/* 216 */               this.server.getLogger().log(
/* 217 */                 Level.SEVERE, 
/* 218 */                 "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", 
/* 219 */                 new UnknownDependencyException(dependency));
/* 220 */               break;
/*     */             }
/*     */           }
/*     */           
/* 224 */           if ((dependencies.containsKey(plugin)) && (((Collection)dependencies.get(plugin)).isEmpty())) {
/* 225 */             dependencies.remove(plugin);
/*     */           }
/*     */         }
/* 228 */         if (softDependencies.containsKey(plugin)) {
/* 229 */           Object softDependencyIterator = ((Collection)softDependencies.get(plugin)).iterator();
/*     */           
/* 231 */           while (((Iterator)softDependencyIterator).hasNext()) {
/* 232 */             String softDependency = (String)((Iterator)softDependencyIterator).next();
/*     */             
/*     */ 
/* 235 */             if (!plugins.containsKey(softDependency)) {
/* 236 */               ((Iterator)softDependencyIterator).remove();
/*     */             }
/*     */           }
/*     */           
/* 240 */           if (((Collection)softDependencies.get(plugin)).isEmpty()) {
/* 241 */             softDependencies.remove(plugin);
/*     */           }
/*     */         }
/* 244 */         if ((!dependencies.containsKey(plugin)) && (!softDependencies.containsKey(plugin)) && (plugins.containsKey(plugin)))
/*     */         {
/* 246 */           File file = (File)plugins.get(plugin);
/* 247 */           ((Iterator)pluginIterator).remove();
/* 248 */           missingDependency = false;
/*     */           try
/*     */           {
/* 251 */             result.add(loadPlugin(file));
/* 252 */             loadedPlugins.add(plugin);
/*     */           }
/*     */           catch (InvalidPluginException ex) {
/* 255 */             this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 260 */       if (missingDependency)
/*     */       {
/*     */ 
/* 263 */         pluginIterator = plugins.keySet().iterator();
/*     */         
/* 265 */         while (((Iterator)pluginIterator).hasNext()) {
/* 266 */           String plugin = (String)((Iterator)pluginIterator).next();
/*     */           
/* 268 */           if (!dependencies.containsKey(plugin)) {
/* 269 */             softDependencies.remove(plugin);
/* 270 */             missingDependency = false;
/* 271 */             File file = (File)plugins.get(plugin);
/* 272 */             ((Iterator)pluginIterator).remove();
/*     */             try
/*     */             {
/* 275 */               result.add(loadPlugin(file));
/* 276 */               loadedPlugins.add(plugin);
/*     */             }
/*     */             catch (InvalidPluginException ex) {
/* 279 */               this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "'", ex);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 284 */         if (missingDependency) {
/* 285 */           softDependencies.clear();
/* 286 */           dependencies.clear();
/* 287 */           Object failedPluginIterator = plugins.values().iterator();
/*     */           
/* 289 */           while (((Iterator)failedPluginIterator).hasNext()) {
/* 290 */             File file = (File)((Iterator)failedPluginIterator).next();
/* 291 */             ((Iterator)failedPluginIterator).remove();
/* 292 */             this.server.getLogger().log(Level.SEVERE, "Could not load '" + file.getPath() + "' in folder '" + directory.getPath() + "': circular dependency detected");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 298 */     org.bukkit.command.defaults.TimingsCommand.timingStart = System.nanoTime();
/* 299 */     return (Plugin[])result.toArray(new Plugin[result.size()]);
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
/*     */   public synchronized Plugin loadPlugin(File file)
/*     */     throws InvalidPluginException, UnknownDependencyException
/*     */   {
/* 315 */     Validate.notNull(file, "File cannot be null");
/*     */     
/* 317 */     checkUpdate(file);
/*     */     
/* 319 */     Set<Pattern> filters = this.fileAssociations.keySet();
/* 320 */     Plugin result = null;
/*     */     
/* 322 */     for (Pattern filter : filters) {
/* 323 */       String name = file.getName();
/* 324 */       Matcher match = filter.matcher(name);
/*     */       
/* 326 */       if (match.find()) {
/* 327 */         PluginLoader loader = (PluginLoader)this.fileAssociations.get(filter);
/*     */         
/* 329 */         result = loader.loadPlugin(file);
/*     */       }
/*     */     }
/*     */     
/* 333 */     if (result != null) {
/* 334 */       this.plugins.add(result);
/* 335 */       this.lookupNames.put(result.getDescription().getName(), result);
/*     */     }
/*     */     
/* 338 */     return result;
/*     */   }
/*     */   
/*     */   private void checkUpdate(File file) {
/* 342 */     if ((updateDirectory == null) || (!updateDirectory.isDirectory())) {
/* 343 */       return;
/*     */     }
/*     */     
/* 346 */     File updateFile = new File(updateDirectory, file.getName());
/* 347 */     if ((updateFile.isFile()) && (FileUtil.copy(updateFile, file))) {
/* 348 */       updateFile.delete();
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
/*     */   public synchronized Plugin getPlugin(String name)
/*     */   {
/* 361 */     return (Plugin)this.lookupNames.get(name.replace(' ', '_'));
/*     */   }
/*     */   
/*     */   public synchronized Plugin[] getPlugins() {
/* 365 */     return (Plugin[])this.plugins.toArray(new Plugin[0]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isPluginEnabled(String name)
/*     */   {
/* 377 */     Plugin plugin = getPlugin(name);
/*     */     
/* 379 */     return isPluginEnabled(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isPluginEnabled(Plugin plugin)
/*     */   {
/* 389 */     if ((plugin != null) && (this.plugins.contains(plugin))) {
/* 390 */       return plugin.isEnabled();
/*     */     }
/* 392 */     return false;
/*     */   }
/*     */   
/*     */   public void enablePlugin(Plugin plugin)
/*     */   {
/* 397 */     if (!plugin.isEnabled()) {
/* 398 */       List<Command> pluginCommands = PluginCommandYamlParser.parse(plugin);
/*     */       
/* 400 */       if (!pluginCommands.isEmpty()) {
/* 401 */         this.commandMap.registerAll(plugin.getDescription().getName(), pluginCommands);
/*     */       }
/*     */       try
/*     */       {
/* 405 */         plugin.getPluginLoader().enablePlugin(plugin);
/*     */       } catch (Throwable ex) {
/* 407 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       }
/*     */       
/* 410 */       HandlerList.bakeAll();
/*     */     }
/*     */   }
/*     */   
/*     */   public void disablePlugins() {
/* 415 */     Plugin[] plugins = getPlugins();
/* 416 */     for (int i = plugins.length - 1; i >= 0; i--) {
/* 417 */       disablePlugin(plugins[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   public void disablePlugin(Plugin plugin) {
/* 422 */     if (plugin.isEnabled()) {
/*     */       try {
/* 424 */         plugin.getPluginLoader().disablePlugin(plugin);
/*     */       } catch (Throwable ex) {
/* 426 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       }
/*     */       try
/*     */       {
/* 430 */         this.server.getScheduler().cancelTasks(plugin);
/*     */       } catch (Throwable ex) {
/* 432 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while cancelling tasks for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       }
/*     */       try
/*     */       {
/* 436 */         this.server.getServicesManager().unregisterAll(plugin);
/*     */       } catch (Throwable ex) {
/* 438 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering services for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       }
/*     */       try
/*     */       {
/* 442 */         HandlerList.unregisterAll(plugin);
/*     */       } catch (Throwable ex) {
/* 444 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering events for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       }
/*     */       try
/*     */       {
/* 448 */         this.server.getMessenger().unregisterIncomingPluginChannel(plugin);
/* 449 */         this.server.getMessenger().unregisterOutgoingPluginChannel(plugin);
/*     */       } catch (Throwable ex) {
/* 451 */         this.server.getLogger().log(Level.SEVERE, "Error occurred (in the plugin loader) while unregistering plugin channels for " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void clearPlugins() {
/* 457 */     synchronized (this) {
/* 458 */       disablePlugins();
/* 459 */       this.plugins.clear();
/* 460 */       this.lookupNames.clear();
/* 461 */       HandlerList.unregisterAll();
/* 462 */       this.fileAssociations.clear();
/* 463 */       this.permissions.clear();
/* 464 */       ((Set)this.defaultPerms.get(Boolean.valueOf(true))).clear();
/* 465 */       ((Set)this.defaultPerms.get(Boolean.valueOf(false))).clear();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void callEvent(Event event)
/*     */   {
/* 477 */     if (event.isAsynchronous()) {
/* 478 */       if (Thread.holdsLock(this)) {
/* 479 */         throw new IllegalStateException(event.getEventName() + " cannot be triggered asynchronously from inside synchronized code.");
/*     */       }
/* 481 */       if (this.server.isPrimaryThread()) {
/* 482 */         throw new IllegalStateException(event.getEventName() + " cannot be triggered asynchronously from primary server thread.");
/*     */       }
/* 484 */       fireEvent(event);
/*     */     } else {
/* 486 */       synchronized (this) {
/* 487 */         fireEvent(event);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void fireEvent(Event event) {
/* 493 */     HandlerList handlers = event.getHandlers();
/* 494 */     RegisteredListener[] listeners = handlers.getRegisteredListeners();
/*     */     RegisteredListener[] arrayOfRegisteredListener1;
/* 496 */     int i = (arrayOfRegisteredListener1 = listeners).length; for (int j = 0; j < i; j++) { RegisteredListener registration = arrayOfRegisteredListener1[j];
/* 497 */       if (registration.getPlugin().isEnabled())
/*     */       {
/*     */         try
/*     */         {
/*     */ 
/* 502 */           registration.callEvent(event);
/*     */         } catch (AuthorNagException ex) {
/* 504 */           Plugin plugin = registration.getPlugin();
/*     */           
/* 506 */           if (plugin.isNaggable()) {
/* 507 */             plugin.setNaggable(false);
/*     */             
/* 509 */             this.server.getLogger().log(Level.SEVERE, String.format(
/* 510 */               "Nag author(s): '%s' of '%s' about the following: %s", new Object[] {
/* 511 */               plugin.getDescription().getAuthors(), 
/* 512 */               plugin.getDescription().getFullName(), 
/* 513 */               ex.getMessage() }));
/*     */           }
/*     */         }
/*     */         catch (Throwable ex) {
/* 517 */           this.server.getLogger().log(Level.SEVERE, "Could not pass event " + event.getEventName() + " to " + registration.getPlugin().getDescription().getFullName(), ex);
/*     */         } }
/*     */     }
/*     */   }
/*     */   
/*     */   public void registerEvents(Listener listener, Plugin plugin) {
/* 523 */     if (!plugin.isEnabled()) {
/* 524 */       throw new IllegalPluginAccessException("Plugin attempted to register " + listener + " while not enabled");
/*     */     }
/*     */     
/* 527 */     for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : plugin.getPluginLoader().createRegisteredListeners(listener, plugin).entrySet()) {
/* 528 */       getEventListeners(getRegistrationClass((Class)entry.getKey())).registerAll((Collection)entry.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin)
/*     */   {
/* 534 */     registerEvent(event, listener, priority, executor, plugin, false);
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
/*     */   public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin, boolean ignoreCancelled)
/*     */   {
/* 550 */     Validate.notNull(listener, "Listener cannot be null");
/* 551 */     Validate.notNull(priority, "Priority cannot be null");
/* 552 */     Validate.notNull(executor, "Executor cannot be null");
/* 553 */     Validate.notNull(plugin, "Plugin cannot be null");
/*     */     
/* 555 */     if (!plugin.isEnabled()) {
/* 556 */       throw new IllegalPluginAccessException("Plugin attempted to register " + event + " while not enabled");
/*     */     }
/*     */     
/* 559 */     if (this.useTimings) {
/* 560 */       getEventListeners(event).register(new TimedRegisteredListener(listener, executor, priority, plugin, ignoreCancelled));
/*     */     } else {
/* 562 */       getEventListeners(event).register(new RegisteredListener(listener, executor, priority, plugin, ignoreCancelled));
/*     */     }
/*     */   }
/*     */   
/*     */   private HandlerList getEventListeners(Class<? extends Event> type) {
/*     */     try {
/* 568 */       Method method = getRegistrationClass(type).getDeclaredMethod("getHandlerList", new Class[0]);
/* 569 */       method.setAccessible(true);
/* 570 */       return (HandlerList)method.invoke(null, new Object[0]);
/*     */     } catch (Exception e) {
/* 572 */       throw new IllegalPluginAccessException(e.toString());
/*     */     }
/*     */   }
/*     */   
/*     */   private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
/*     */     try {
/* 578 */       clazz.getDeclaredMethod("getHandlerList", new Class[0]);
/* 579 */       return clazz;
/*     */     } catch (NoSuchMethodException localNoSuchMethodException) {
/* 581 */       if ((clazz.getSuperclass() != null) && 
/* 582 */         (!clazz.getSuperclass().equals(Event.class)) && 
/* 583 */         (Event.class.isAssignableFrom(clazz.getSuperclass()))) {
/* 584 */         return getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
/*     */       }
/* 586 */       throw new IllegalPluginAccessException("Unable to find handler list for event " + clazz.getName() + ". Static getHandlerList method required!");
/*     */     }
/*     */   }
/*     */   
/*     */   public Permission getPermission(String name)
/*     */   {
/* 592 */     return (Permission)this.permissions.get(name.toLowerCase());
/*     */   }
/*     */   
/*     */   public void addPermission(Permission perm) {
/* 596 */     String name = perm.getName().toLowerCase();
/*     */     
/* 598 */     if (this.permissions.containsKey(name)) {
/* 599 */       throw new IllegalArgumentException("The permission " + name + " is already defined!");
/*     */     }
/*     */     
/* 602 */     this.permissions.put(name, perm);
/* 603 */     calculatePermissionDefault(perm);
/*     */   }
/*     */   
/*     */   public Set<Permission> getDefaultPermissions(boolean op) {
/* 607 */     return ImmutableSet.copyOf((Collection)this.defaultPerms.get(Boolean.valueOf(op)));
/*     */   }
/*     */   
/*     */   public void removePermission(Permission perm) {
/* 611 */     removePermission(perm.getName());
/*     */   }
/*     */   
/*     */   public void removePermission(String name) {
/* 615 */     this.permissions.remove(name.toLowerCase());
/*     */   }
/*     */   
/*     */   public void recalculatePermissionDefaults(Permission perm) {
/* 619 */     if ((perm != null) && (this.permissions.containsKey(perm.getName().toLowerCase()))) {
/* 620 */       ((Set)this.defaultPerms.get(Boolean.valueOf(true))).remove(perm);
/* 621 */       ((Set)this.defaultPerms.get(Boolean.valueOf(false))).remove(perm);
/*     */       
/* 623 */       calculatePermissionDefault(perm);
/*     */     }
/*     */   }
/*     */   
/*     */   private void calculatePermissionDefault(Permission perm) {
/* 628 */     if ((perm.getDefault() == PermissionDefault.OP) || (perm.getDefault() == PermissionDefault.TRUE)) {
/* 629 */       ((Set)this.defaultPerms.get(Boolean.valueOf(true))).add(perm);
/* 630 */       dirtyPermissibles(true);
/*     */     }
/* 632 */     if ((perm.getDefault() == PermissionDefault.NOT_OP) || (perm.getDefault() == PermissionDefault.TRUE)) {
/* 633 */       ((Set)this.defaultPerms.get(Boolean.valueOf(false))).add(perm);
/* 634 */       dirtyPermissibles(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private void dirtyPermissibles(boolean op) {
/* 639 */     Set<Permissible> permissibles = getDefaultPermSubscriptions(op);
/*     */     
/* 641 */     for (Permissible p : permissibles) {
/* 642 */       p.recalculatePermissions();
/*     */     }
/*     */   }
/*     */   
/*     */   public void subscribeToPermission(String permission, Permissible permissible) {
/* 647 */     String name = permission.toLowerCase();
/* 648 */     Map<Permissible, Boolean> map = (Map)this.permSubs.get(name);
/*     */     
/* 650 */     if (map == null) {
/* 651 */       map = new WeakHashMap();
/* 652 */       this.permSubs.put(name, map);
/*     */     }
/*     */     
/* 655 */     map.put(permissible, Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public void unsubscribeFromPermission(String permission, Permissible permissible) {
/* 659 */     String name = permission.toLowerCase();
/* 660 */     Map<Permissible, Boolean> map = (Map)this.permSubs.get(name);
/*     */     
/* 662 */     if (map != null) {
/* 663 */       map.remove(permissible);
/*     */       
/* 665 */       if (map.isEmpty()) {
/* 666 */         this.permSubs.remove(name);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<Permissible> getPermissionSubscriptions(String permission) {
/* 672 */     String name = permission.toLowerCase();
/* 673 */     Map<Permissible, Boolean> map = (Map)this.permSubs.get(name);
/*     */     
/* 675 */     if (map == null) {
/* 676 */       return ImmutableSet.of();
/*     */     }
/* 678 */     return ImmutableSet.copyOf(map.keySet());
/*     */   }
/*     */   
/*     */   public void subscribeToDefaultPerms(boolean op, Permissible permissible)
/*     */   {
/* 683 */     Map<Permissible, Boolean> map = (Map)this.defSubs.get(Boolean.valueOf(op));
/*     */     
/* 685 */     if (map == null) {
/* 686 */       map = new WeakHashMap();
/* 687 */       this.defSubs.put(Boolean.valueOf(op), map);
/*     */     }
/*     */     
/* 690 */     map.put(permissible, Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public void unsubscribeFromDefaultPerms(boolean op, Permissible permissible) {
/* 694 */     Map<Permissible, Boolean> map = (Map)this.defSubs.get(Boolean.valueOf(op));
/*     */     
/* 696 */     if (map != null) {
/* 697 */       map.remove(permissible);
/*     */       
/* 699 */       if (map.isEmpty()) {
/* 700 */         this.defSubs.remove(Boolean.valueOf(op));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<Permissible> getDefaultPermSubscriptions(boolean op) {
/* 706 */     Map<Permissible, Boolean> map = (Map)this.defSubs.get(Boolean.valueOf(op));
/*     */     
/* 708 */     if (map == null) {
/* 709 */       return ImmutableSet.of();
/*     */     }
/* 711 */     return ImmutableSet.copyOf(map.keySet());
/*     */   }
/*     */   
/*     */   public Set<Permission> getPermissions()
/*     */   {
/* 716 */     return new HashSet(this.permissions.values());
/*     */   }
/*     */   
/*     */   public boolean useTimings() {
/* 720 */     return this.useTimings;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void useTimings(boolean use)
/*     */   {
/* 729 */     this.useTimings = use;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\SimplePluginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */