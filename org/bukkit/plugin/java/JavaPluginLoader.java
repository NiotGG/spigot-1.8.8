/*     */ package org.bukkit.plugin.java;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.Warning;
/*     */ import org.bukkit.Warning.WarningState;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerialization;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventException;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.server.PluginDisableEvent;
/*     */ import org.bukkit.event.server.PluginEnableEvent;
/*     */ import org.bukkit.plugin.AuthorNagException;
/*     */ import org.bukkit.plugin.EventExecutor;
/*     */ import org.bukkit.plugin.InvalidDescriptionException;
/*     */ import org.bukkit.plugin.InvalidPluginException;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginLoader;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.RegisteredListener;
/*     */ import org.bukkit.plugin.UnknownDependencyException;
/*     */ import org.spigotmc.CustomTimingsHandler;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ 
/*     */ public final class JavaPluginLoader implements PluginLoader
/*     */ {
/*     */   final Server server;
/*  50 */   private final Pattern[] fileFilters = { Pattern.compile("\\.jar$") };
/*  51 */   private final Map<String, Class<?>> classes = new ConcurrentHashMap();
/*  52 */   private final Map<String, PluginClassLoader> loaders = new LinkedHashMap();
/*  53 */   public static final CustomTimingsHandler pluginParentTimer = new CustomTimingsHandler("** Plugins");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public JavaPluginLoader(Server instance)
/*     */   {
/*  62 */     Validate.notNull(instance, "Server cannot be null");
/*  63 */     this.server = instance;
/*     */   }
/*     */   
/*     */   public Plugin loadPlugin(File file) throws InvalidPluginException {
/*  67 */     Validate.notNull(file, "File cannot be null");
/*     */     
/*  69 */     if (!file.exists()) {
/*  70 */       throw new InvalidPluginException(new FileNotFoundException(file.getPath() + " does not exist"));
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  75 */       description = getPluginDescription(file);
/*     */     } catch (InvalidDescriptionException ex) { PluginDescriptionFile description;
/*  77 */       throw new InvalidPluginException(ex);
/*     */     }
/*     */     PluginDescriptionFile description;
/*  80 */     File parentFile = file.getParentFile();
/*  81 */     File dataFolder = new File(parentFile, description.getName());
/*     */     
/*  83 */     File oldDataFolder = new File(parentFile, description.getRawName());
/*     */     
/*     */ 
/*  86 */     if (!dataFolder.equals(oldDataFolder))
/*     */     {
/*  88 */       if ((dataFolder.isDirectory()) && (oldDataFolder.isDirectory())) {
/*  89 */         this.server.getLogger().warning(String.format(
/*  90 */           "While loading %s (%s) found old-data folder: `%s' next to the new one `%s'", new Object[] {
/*  91 */           description.getFullName(), 
/*  92 */           file, 
/*  93 */           oldDataFolder, 
/*  94 */           dataFolder }));
/*     */       }
/*  96 */       else if ((oldDataFolder.isDirectory()) && (!dataFolder.exists())) {
/*  97 */         if (!oldDataFolder.renameTo(dataFolder)) {
/*  98 */           throw new InvalidPluginException("Unable to rename old data folder: `" + oldDataFolder + "' to: `" + dataFolder + "'");
/*     */         }
/* 100 */         this.server.getLogger().log(Level.INFO, String.format(
/* 101 */           "While loading %s (%s) renamed data folder: `%s' to `%s'", new Object[] {
/* 102 */           description.getFullName(), 
/* 103 */           file, 
/* 104 */           oldDataFolder, 
/* 105 */           dataFolder }));
/*     */       }
/*     */     }
/*     */     
/* 109 */     if ((dataFolder.exists()) && (!dataFolder.isDirectory())) {
/* 110 */       throw new InvalidPluginException(String.format(
/* 111 */         "Projected datafolder: `%s' for %s (%s) exists and is not a directory", new Object[] {
/* 112 */         dataFolder, 
/* 113 */         description.getFullName(), 
/* 114 */         file }));
/*     */     }
/*     */     
/*     */ 
/* 118 */     for (String pluginName : description.getDepend()) {
/* 119 */       if (this.loaders == null) {
/* 120 */         throw new UnknownDependencyException(pluginName);
/*     */       }
/* 122 */       PluginClassLoader current = (PluginClassLoader)this.loaders.get(pluginName);
/*     */       
/* 124 */       if (current == null) {
/* 125 */         throw new UnknownDependencyException(pluginName);
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 131 */       loader = new PluginClassLoader(this, getClass().getClassLoader(), description, dataFolder, file);
/*     */     } catch (InvalidPluginException ex) { PluginClassLoader loader;
/* 133 */       throw ex;
/*     */     } catch (Throwable ex) {
/* 135 */       throw new InvalidPluginException(ex);
/*     */     }
/*     */     PluginClassLoader loader;
/* 138 */     this.loaders.put(description.getName(), loader);
/*     */     
/* 140 */     return loader.plugin;
/*     */   }
/*     */   
/*     */   public PluginDescriptionFile getPluginDescription(File file) throws InvalidDescriptionException {
/* 144 */     Validate.notNull(file, "File cannot be null");
/*     */     
/* 146 */     JarFile jar = null;
/* 147 */     InputStream stream = null;
/*     */     try
/*     */     {
/* 150 */       jar = new JarFile(file);
/* 151 */       JarEntry entry = jar.getJarEntry("plugin.yml");
/*     */       
/* 153 */       if (entry == null) {
/* 154 */         throw new InvalidDescriptionException(new FileNotFoundException("Jar does not contain plugin.yml"));
/*     */       }
/*     */       
/* 157 */       stream = jar.getInputStream(entry);
/*     */       
/* 159 */       return new PluginDescriptionFile(stream);
/*     */     }
/*     */     catch (IOException ex) {
/* 162 */       throw new InvalidDescriptionException(ex);
/*     */     } catch (YAMLException ex) {
/* 164 */       throw new InvalidDescriptionException(ex);
/*     */     } finally {
/* 166 */       if (jar != null) {
/*     */         try {
/* 168 */           jar.close();
/*     */         }
/*     */         catch (IOException localIOException3) {}
/*     */       }
/* 172 */       if (stream != null) {
/*     */         try {
/* 174 */           stream.close();
/*     */         }
/*     */         catch (IOException localIOException4) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Pattern[] getPluginFileFilters() {
/* 182 */     return (Pattern[])this.fileFilters.clone();
/*     */   }
/*     */   
/*     */   Class<?> getClassByName(String name) {
/* 186 */     Class<?> cachedClass = (Class)this.classes.get(name);
/*     */     
/* 188 */     if (cachedClass != null) {
/* 189 */       return cachedClass;
/*     */     }
/* 191 */     for (String current : this.loaders.keySet()) {
/* 192 */       PluginClassLoader loader = (PluginClassLoader)this.loaders.get(current);
/*     */       try
/*     */       {
/* 195 */         cachedClass = loader.findClass(name, false);
/*     */       } catch (ClassNotFoundException localClassNotFoundException) {}
/* 197 */       if (cachedClass != null) {
/* 198 */         return cachedClass;
/*     */       }
/*     */     }
/*     */     
/* 202 */     return null;
/*     */   }
/*     */   
/*     */   void setClass(String name, Class<?> clazz) {
/* 206 */     if (!this.classes.containsKey(name)) {
/* 207 */       this.classes.put(name, clazz);
/*     */       
/* 209 */       if (ConfigurationSerializable.class.isAssignableFrom(clazz)) {
/* 210 */         Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
/* 211 */         ConfigurationSerialization.registerClass(serializable);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void removeClass(String name) {
/* 217 */     Class<?> clazz = (Class)this.classes.remove(name);
/*     */     try
/*     */     {
/* 220 */       if ((clazz != null) && (ConfigurationSerializable.class.isAssignableFrom(clazz))) {
/* 221 */         Class<? extends ConfigurationSerializable> serializable = clazz.asSubclass(ConfigurationSerializable.class);
/* 222 */         ConfigurationSerialization.unregisterClass(serializable);
/*     */       }
/*     */     }
/*     */     catch (NullPointerException localNullPointerException) {}
/*     */   }
/*     */   
/*     */ 
/*     */   public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, Plugin plugin)
/*     */   {
/* 231 */     Validate.notNull(plugin, "Plugin can not be null");
/* 232 */     Validate.notNull(listener, "Listener can not be null");
/*     */     
/* 234 */     this.server.getPluginManager().useTimings();
/* 235 */     Map<Class<? extends Event>, Set<RegisteredListener>> ret = new HashMap();
/*     */     Method[] privateMethods;
/*     */     try {
/* 238 */       Method[] publicMethods = listener.getClass().getMethods();
/* 239 */       privateMethods = listener.getClass().getDeclaredMethods();
/* 240 */       Set<Method> methods = new HashSet(publicMethods.length + privateMethods.length, 1.0F);
/* 241 */       Method[] arrayOfMethod1; int i = (arrayOfMethod1 = publicMethods).length; for (int j = 0; j < i; j++) { Method method = arrayOfMethod1[j];
/* 242 */         methods.add(method);
/*     */       }
/* 244 */       i = (arrayOfMethod1 = privateMethods).length; for (j = 0; j < i; j++) { Method method = arrayOfMethod1[j];
/* 245 */         methods.add(method);
/*     */       }
/*     */     } catch (NoClassDefFoundError e) {
/* 248 */       plugin.getLogger().severe("Plugin " + plugin.getDescription().getFullName() + " has failed to register events for " + listener.getClass() + " because " + e.getMessage() + " does not exist.");
/* 249 */       return ret;
/*     */     }
/*     */     Set<Method> methods;
/* 252 */     for (final Method method : methods) {
/* 253 */       EventHandler eh = (EventHandler)method.getAnnotation(EventHandler.class);
/* 254 */       if (eh != null)
/*     */       {
/*     */ 
/* 257 */         if ((!method.isBridge()) && (!method.isSynthetic()))
/*     */         {
/*     */           Object checkClass;
/*     */           
/* 261 */           if ((method.getParameterTypes().length != 1) || (!Event.class.isAssignableFrom(checkClass = method.getParameterTypes()[0]))) {
/* 262 */             plugin.getLogger().severe(plugin.getDescription().getFullName() + " attempted to register an invalid EventHandler method signature \"" + method.toGenericString() + "\" in " + listener.getClass());
/*     */           } else {
/*     */             Class<?> checkClass;
/* 265 */             final Object eventClass = checkClass.asSubclass(Event.class);
/* 266 */             method.setAccessible(true);
/* 267 */             Object eventSet = (Set)ret.get(eventClass);
/* 268 */             if (eventSet == null) {
/* 269 */               eventSet = new HashSet();
/* 270 */               ret.put(eventClass, eventSet);
/*     */             }
/*     */             
/* 273 */             for (Class<?> clazz = (Class<?>)eventClass; Event.class.isAssignableFrom(clazz); clazz = clazz.getSuperclass())
/*     */             {
/* 275 */               if (clazz.getAnnotation(Deprecated.class) != null) {
/* 276 */                 Warning warning = (Warning)clazz.getAnnotation(Warning.class);
/* 277 */                 Warning.WarningState warningState = this.server.getWarningState();
/* 278 */                 if (!warningState.printFor(warning)) {
/*     */                   break;
/*     */                 }
/* 281 */                 plugin.getLogger().log(
/* 282 */                   Level.WARNING, 
/* 283 */                   String.format(
/* 284 */                   "\"%s\" has registered a listener for %s on method \"%s\", but the event is Deprecated. \"%s\"; please notify the authors %s.", new Object[] {
/*     */                   
/* 286 */                   plugin.getDescription().getFullName(), 
/* 287 */                   clazz.getName(), 
/* 288 */                   method.toGenericString(), 
/* 289 */                   (warning != null) && (warning.reason().length() != 0) ? warning.reason() : "Server performance will be affected", 
/* 290 */                   Arrays.toString(plugin.getDescription().getAuthors().toArray()) }), 
/* 291 */                   warningState == Warning.WarningState.ON ? new AuthorNagException(null) : null);
/* 292 */                 break;
/*     */               }
/*     */             }
/*     */             
/* 296 */             final CustomTimingsHandler timings = new CustomTimingsHandler("Plugin: " + plugin.getDescription().getFullName() + " Event: " + listener.getClass().getName() + "::" + method.getName() + "(" + ((Class)eventClass).getSimpleName() + ")", pluginParentTimer);
/* 297 */             EventExecutor executor = new EventExecutor() {
/*     */               public void execute(Listener listener, Event event) throws EventException {
/*     */                 try {
/* 300 */                   if (!eventClass.isAssignableFrom(event.getClass())) {
/* 301 */                     return;
/*     */                   }
/*     */                   
/* 304 */                   boolean isAsync = event.isAsynchronous();
/* 305 */                   if (!isAsync) timings.startTiming();
/* 306 */                   method.invoke(listener, new Object[] { event });
/* 307 */                   if (!isAsync) timings.stopTiming();
/*     */                 }
/*     */                 catch (InvocationTargetException ex) {
/* 310 */                   throw new EventException(ex.getCause());
/*     */                 } catch (Throwable t) {
/* 312 */                   throw new EventException(t);
/*     */                 }
/*     */                 
/*     */               }
/*     */               
/*     */ 
/* 318 */             };
/* 319 */             ((Set)eventSet).add(new RegisteredListener(listener, executor, eh.priority(), plugin, eh.ignoreCancelled()));
/*     */           }
/*     */         } } }
/* 322 */     return ret;
/*     */   }
/*     */   
/*     */   public void enablePlugin(Plugin plugin) {
/* 326 */     Validate.isTrue(plugin instanceof JavaPlugin, "Plugin is not associated with this PluginLoader");
/*     */     
/* 328 */     if (!plugin.isEnabled()) {
/* 329 */       plugin.getLogger().info("Enabling " + plugin.getDescription().getFullName());
/*     */       
/* 331 */       JavaPlugin jPlugin = (JavaPlugin)plugin;
/*     */       
/* 333 */       String pluginName = jPlugin.getDescription().getName();
/*     */       
/* 335 */       if (!this.loaders.containsKey(pluginName)) {
/* 336 */         this.loaders.put(pluginName, (PluginClassLoader)jPlugin.getClassLoader());
/*     */       }
/*     */       try
/*     */       {
/* 340 */         jPlugin.setEnabled(true);
/*     */       } catch (Throwable ex) {
/* 342 */         this.server.getLogger().log(Level.SEVERE, "Error occurred while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 347 */       this.server.getPluginManager().callEvent(new PluginEnableEvent(plugin));
/*     */     }
/*     */   }
/*     */   
/*     */   public void disablePlugin(Plugin plugin) {
/* 352 */     Validate.isTrue(plugin instanceof JavaPlugin, "Plugin is not associated with this PluginLoader");
/*     */     
/* 354 */     if (plugin.isEnabled()) {
/* 355 */       String message = String.format("Disabling %s", new Object[] { plugin.getDescription().getFullName() });
/* 356 */       plugin.getLogger().info(message);
/*     */       
/* 358 */       this.server.getPluginManager().callEvent(new PluginDisableEvent(plugin));
/*     */       
/* 360 */       JavaPlugin jPlugin = (JavaPlugin)plugin;
/* 361 */       ClassLoader cloader = jPlugin.getClassLoader();
/*     */       try
/*     */       {
/* 364 */         jPlugin.setEnabled(false);
/*     */       } catch (Throwable ex) {
/* 366 */         this.server.getLogger().log(Level.SEVERE, "Error occurred while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
/*     */       }
/*     */       
/* 369 */       this.loaders.remove(jPlugin.getDescription().getName());
/*     */       
/* 371 */       if ((cloader instanceof PluginClassLoader)) {
/* 372 */         PluginClassLoader loader = (PluginClassLoader)cloader;
/* 373 */         Set<String> names = loader.getClasses();
/*     */         
/* 375 */         for (String name : names) {
/* 376 */           removeClass(name);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\java\JavaPluginLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */