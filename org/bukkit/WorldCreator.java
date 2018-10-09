/*     */ package org.bukkit;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.generator.ChunkGenerator;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class WorldCreator
/*     */ {
/*     */   private final String name;
/*     */   private long seed;
/*  14 */   private World.Environment environment = World.Environment.NORMAL;
/*  15 */   private ChunkGenerator generator = null;
/*  16 */   private WorldType type = WorldType.NORMAL;
/*  17 */   private boolean generateStructures = true;
/*  18 */   private String generatorSettings = "";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldCreator(String name)
/*     */   {
/*  26 */     if (name == null) {
/*  27 */       throw new IllegalArgumentException("World name cannot be null");
/*     */     }
/*     */     
/*  30 */     this.name = name;
/*  31 */     this.seed = new Random().nextLong();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldCreator copy(World world)
/*     */   {
/*  41 */     if (world == null) {
/*  42 */       throw new IllegalArgumentException("World cannot be null");
/*     */     }
/*     */     
/*  45 */     this.seed = world.getSeed();
/*  46 */     this.environment = world.getEnvironment();
/*  47 */     this.generator = world.getGenerator();
/*     */     
/*  49 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldCreator copy(WorldCreator creator)
/*     */   {
/*  59 */     if (creator == null) {
/*  60 */       throw new IllegalArgumentException("Creator cannot be null");
/*     */     }
/*     */     
/*  63 */     this.seed = creator.seed();
/*  64 */     this.environment = creator.environment();
/*  65 */     this.generator = creator.generator();
/*     */     
/*  67 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String name()
/*     */   {
/*  76 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long seed()
/*     */   {
/*  85 */     return this.seed;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldCreator seed(long seed)
/*     */   {
/*  95 */     this.seed = seed;
/*     */     
/*  97 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public World.Environment environment()
/*     */   {
/* 106 */     return this.environment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldCreator environment(World.Environment env)
/*     */   {
/* 116 */     this.environment = env;
/*     */     
/* 118 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldType type()
/*     */   {
/* 127 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldCreator type(WorldType type)
/*     */   {
/* 137 */     this.type = type;
/*     */     
/* 139 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkGenerator generator()
/*     */   {
/* 151 */     return this.generator;
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
/*     */   public WorldCreator generator(ChunkGenerator generator)
/*     */   {
/* 164 */     this.generator = generator;
/*     */     
/* 166 */     return this;
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
/*     */   public WorldCreator generator(String generator)
/*     */   {
/* 183 */     this.generator = getGeneratorForName(this.name, generator, Bukkit.getConsoleSender());
/*     */     
/* 185 */     return this;
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
/*     */   public WorldCreator generator(String generator, CommandSender output)
/*     */   {
/* 204 */     this.generator = getGeneratorForName(this.name, generator, output);
/*     */     
/* 206 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldCreator generatorSettings(String generatorSettings)
/*     */   {
/* 216 */     this.generatorSettings = generatorSettings;
/*     */     
/* 218 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String generatorSettings()
/*     */   {
/* 227 */     return this.generatorSettings;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldCreator generateStructures(boolean generate)
/*     */   {
/* 238 */     this.generateStructures = generate;
/*     */     
/* 240 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean generateStructures()
/*     */   {
/* 249 */     return this.generateStructures;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public World createWorld()
/*     */   {
/* 261 */     return Bukkit.createWorld(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static WorldCreator name(String name)
/*     */   {
/* 271 */     return new WorldCreator(name);
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
/*     */   public static ChunkGenerator getGeneratorForName(String world, String name, CommandSender output)
/*     */   {
/* 291 */     ChunkGenerator result = null;
/*     */     
/* 293 */     if (world == null) {
/* 294 */       throw new IllegalArgumentException("World name must be specified");
/*     */     }
/*     */     
/* 297 */     if (output == null) {
/* 298 */       output = Bukkit.getConsoleSender();
/*     */     }
/*     */     
/* 301 */     if (name != null) {
/* 302 */       String[] split = name.split(":", 2);
/* 303 */       String id = split.length > 1 ? split[1] : null;
/* 304 */       Plugin plugin = Bukkit.getPluginManager().getPlugin(split[0]);
/*     */       
/* 306 */       if (plugin == null) {
/* 307 */         output.sendMessage("Could not set generator for world '" + world + "': Plugin '" + split[0] + "' does not exist");
/* 308 */       } else if (!plugin.isEnabled()) {
/* 309 */         output.sendMessage("Could not set generator for world '" + world + "': Plugin '" + plugin.getDescription().getFullName() + "' is not enabled");
/*     */       } else {
/* 311 */         result = plugin.getDefaultWorldGenerator(world, id);
/*     */       }
/*     */     }
/*     */     
/* 315 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\WorldCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */