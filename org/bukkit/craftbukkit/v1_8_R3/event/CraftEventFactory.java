/*     */ package org.bukkit.craftbukkit.v1_8_R3.event;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*     */ import net.minecraft.server.v1_8_R3.ChatMessage;
/*     */ import net.minecraft.server.v1_8_R3.Container;
/*     */ import net.minecraft.server.v1_8_R3.DamageSource;
/*     */ import net.minecraft.server.v1_8_R3.EntityFireworks;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.EntityInsentient;
/*     */ import net.minecraft.server.v1_8_R3.EntityItem;
/*     */ import net.minecraft.server.v1_8_R3.EntityLiving;
/*     */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*     */ import net.minecraft.server.v1_8_R3.EnumDirection;
/*     */ import net.minecraft.server.v1_8_R3.Explosion;
/*     */ import net.minecraft.server.v1_8_R3.IChatBaseComponent;
/*     */ import net.minecraft.server.v1_8_R3.Items;
/*     */ import net.minecraft.server.v1_8_R3.PlayerInventory;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftStatistic;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlockState;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCrafting;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.BlockIgniteEvent;
/*     */ import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.EntityChangeBlockEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByBlockEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*     */ import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
/*     */ import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
/*     */ import org.bukkit.event.entity.ItemSpawnEvent;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.entity.PlayerLeashEntityEvent;
/*     */ import org.bukkit.event.entity.ProjectileLaunchEvent;
/*     */ import org.bukkit.event.inventory.InventoryOpenEvent;
/*     */ import org.bukkit.event.inventory.PrepareItemCraftEvent;
/*     */ import org.bukkit.event.player.PlayerBucketEmptyEvent;
/*     */ import org.bukkit.event.player.PlayerBucketFillEvent;
/*     */ import org.bukkit.event.player.PlayerEditBookEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class CraftEventFactory
/*     */ {
/*  65 */   public static final DamageSource MELTING = org.bukkit.craftbukkit.v1_8_R3.util.CraftDamageSource.copyOf(DamageSource.BURN);
/*  66 */   public static final DamageSource POISON = org.bukkit.craftbukkit.v1_8_R3.util.CraftDamageSource.copyOf(DamageSource.MAGIC);
/*     */   public static org.bukkit.block.Block blockDamage;
/*     */   public static net.minecraft.server.v1_8_R3.Entity entityDamage;
/*     */   
/*     */   private static boolean canBuild(CraftWorld world, Player player, int x, int z)
/*     */   {
/*  72 */     net.minecraft.server.v1_8_R3.WorldServer worldServer = world.getHandle();
/*  73 */     int spawnSize = Bukkit.getServer().getSpawnRadius();
/*     */     
/*  75 */     if (world.getHandle().dimension != 0) return true;
/*  76 */     if (spawnSize <= 0) return true;
/*  77 */     if (((CraftServer)Bukkit.getServer()).getHandle().getOPs().isEmpty()) return true;
/*  78 */     if (player.isOp()) { return true;
/*     */     }
/*  80 */     BlockPosition chunkcoordinates = worldServer.getSpawn();
/*     */     
/*  82 */     int distanceFromSpawn = Math.max(Math.abs(x - chunkcoordinates.getX()), Math.abs(z - chunkcoordinates.getY()));
/*  83 */     return distanceFromSpawn > spawnSize;
/*     */   }
/*     */   
/*     */   public static <T extends Event> T callEvent(T event) {
/*  87 */     Bukkit.getServer().getPluginManager().callEvent(event);
/*  88 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static org.bukkit.event.block.BlockMultiPlaceEvent callBlockMultiPlaceEvent(net.minecraft.server.v1_8_R3.World world, EntityHuman who, List<BlockState> blockStates, int clickedX, int clickedY, int clickedZ)
/*     */   {
/*  95 */     CraftWorld craftWorld = world.getWorld();
/*  96 */     CraftServer craftServer = world.getServer();
/*  97 */     Player player = who == null ? null : (Player)who.getBukkitEntity();
/*     */     
/*  99 */     org.bukkit.block.Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
/*     */     
/* 101 */     boolean canBuild = true;
/* 102 */     for (int i = 0; i < blockStates.size(); i++) {
/* 103 */       if (!canBuild(craftWorld, player, ((BlockState)blockStates.get(i)).getX(), ((BlockState)blockStates.get(i)).getZ())) {
/* 104 */         canBuild = false;
/* 105 */         break;
/*     */       }
/*     */     }
/*     */     
/* 109 */     org.bukkit.event.block.BlockMultiPlaceEvent event = new org.bukkit.event.block.BlockMultiPlaceEvent(blockStates, blockClicked, player.getItemInHand(), player, canBuild);
/* 110 */     craftServer.getPluginManager().callEvent(event);
/*     */     
/* 112 */     return event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.block.BlockPlaceEvent callBlockPlaceEvent(net.minecraft.server.v1_8_R3.World world, EntityHuman who, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ) {
/* 116 */     CraftWorld craftWorld = world.getWorld();
/* 117 */     CraftServer craftServer = world.getServer();
/*     */     
/* 119 */     Player player = who == null ? null : (Player)who.getBukkitEntity();
/*     */     
/* 121 */     org.bukkit.block.Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
/* 122 */     org.bukkit.block.Block placedBlock = replacedBlockState.getBlock();
/*     */     
/* 124 */     boolean canBuild = canBuild(craftWorld, player, placedBlock.getX(), placedBlock.getZ());
/*     */     
/* 126 */     org.bukkit.event.block.BlockPlaceEvent event = new org.bukkit.event.block.BlockPlaceEvent(placedBlock, replacedBlockState, blockClicked, player.getItemInHand(), player, canBuild);
/* 127 */     craftServer.getPluginManager().callEvent(event);
/*     */     
/* 129 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static org.bukkit.event.entity.SpawnerSpawnEvent callSpawnerSpawnEvent(net.minecraft.server.v1_8_R3.Entity spawnee, int spawnerX, int spawnerY, int spawnerZ)
/*     */   {
/* 136 */     CraftEntity entity = spawnee.getBukkitEntity();
/* 137 */     BlockState state = entity.getWorld().getBlockAt(spawnerX, spawnerY, spawnerZ).getState();
/*     */     
/* 139 */     if (!(state instanceof org.bukkit.block.CreatureSpawner)) {
/* 140 */       state = null;
/*     */     }
/*     */     
/* 143 */     org.bukkit.event.entity.SpawnerSpawnEvent event = new org.bukkit.event.entity.SpawnerSpawnEvent(entity, (org.bukkit.block.CreatureSpawner)state);
/* 144 */     entity.getServer().getPluginManager().callEvent(event);
/* 145 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(EntityHuman who, int clickedX, int clickedY, int clickedZ, EnumDirection clickedFace, net.minecraft.server.v1_8_R3.ItemStack itemInHand)
/*     */   {
/* 152 */     return (PlayerBucketEmptyEvent)getPlayerBucketEvent(false, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, Items.BUCKET);
/*     */   }
/*     */   
/*     */   public static PlayerBucketFillEvent callPlayerBucketFillEvent(EntityHuman who, int clickedX, int clickedY, int clickedZ, EnumDirection clickedFace, net.minecraft.server.v1_8_R3.ItemStack itemInHand, net.minecraft.server.v1_8_R3.Item bucket) {
/* 156 */     return (PlayerBucketFillEvent)getPlayerBucketEvent(true, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, bucket);
/*     */   }
/*     */   
/*     */   private static org.bukkit.event.player.PlayerEvent getPlayerBucketEvent(boolean isFilling, EntityHuman who, int clickedX, int clickedY, int clickedZ, EnumDirection clickedFace, net.minecraft.server.v1_8_R3.ItemStack itemstack, net.minecraft.server.v1_8_R3.Item item) {
/* 160 */     Player player = who == null ? null : (Player)who.getBukkitEntity();
/* 161 */     CraftItemStack itemInHand = CraftItemStack.asNewCraftStack(item);
/* 162 */     Material bucket = org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getMaterial(itemstack.getItem());
/*     */     
/* 164 */     CraftWorld craftWorld = (CraftWorld)player.getWorld();
/* 165 */     CraftServer craftServer = (CraftServer)player.getServer();
/*     */     
/* 167 */     org.bukkit.block.Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
/* 168 */     org.bukkit.block.BlockFace blockFace = org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock.notchToBlockFace(clickedFace);
/*     */     
/* 170 */     org.bukkit.event.player.PlayerEvent event = null;
/* 171 */     if (isFilling) {
/* 172 */       event = new PlayerBucketFillEvent(player, blockClicked, blockFace, bucket, itemInHand);
/* 173 */       ((PlayerBucketFillEvent)event).setCancelled(!canBuild(craftWorld, player, clickedX, clickedZ));
/*     */     } else {
/* 175 */       event = new PlayerBucketEmptyEvent(player, blockClicked, blockFace, bucket, itemInHand);
/* 176 */       ((PlayerBucketEmptyEvent)event).setCancelled(!canBuild(craftWorld, player, clickedX, clickedZ));
/*     */     }
/*     */     
/* 179 */     craftServer.getPluginManager().callEvent(event);
/*     */     
/* 181 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, net.minecraft.server.v1_8_R3.ItemStack itemstack)
/*     */   {
/* 188 */     if ((action != Action.LEFT_CLICK_AIR) && (action != Action.RIGHT_CLICK_AIR)) {
/* 189 */       throw new IllegalArgumentException(String.format("%s performing %s with %s", new Object[] { who, action, itemstack }));
/*     */     }
/* 191 */     return callPlayerInteractEvent(who, action, new BlockPosition(0, 256, 0), EnumDirection.SOUTH, itemstack);
/*     */   }
/*     */   
/*     */   public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, BlockPosition position, EnumDirection direction, net.minecraft.server.v1_8_R3.ItemStack itemstack) {
/* 195 */     return callPlayerInteractEvent(who, action, position, direction, itemstack, false);
/*     */   }
/*     */   
/*     */   public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, BlockPosition position, EnumDirection direction, net.minecraft.server.v1_8_R3.ItemStack itemstack, boolean cancelledBlock) {
/* 199 */     Player player = who == null ? null : (Player)who.getBukkitEntity();
/* 200 */     CraftItemStack itemInHand = CraftItemStack.asCraftMirror(itemstack);
/*     */     
/* 202 */     CraftWorld craftWorld = (CraftWorld)player.getWorld();
/* 203 */     CraftServer craftServer = (CraftServer)player.getServer();
/*     */     
/* 205 */     org.bukkit.block.Block blockClicked = craftWorld.getBlockAt(position.getX(), position.getY(), position.getZ());
/* 206 */     org.bukkit.block.BlockFace blockFace = org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock.notchToBlockFace(direction);
/*     */     
/* 208 */     if (position.getY() > 255) {
/* 209 */       blockClicked = null;
/* 210 */       switch (action) {
/*     */       case LEFT_CLICK_AIR: 
/* 212 */         action = Action.LEFT_CLICK_AIR;
/* 213 */         break;
/*     */       case LEFT_CLICK_BLOCK: 
/* 215 */         action = Action.RIGHT_CLICK_AIR;
/*     */       }
/*     */       
/*     */     }
/*     */     
/* 220 */     if ((itemInHand.getType() == Material.AIR) || (itemInHand.getAmount() == 0)) {
/* 221 */       itemInHand = null;
/*     */     }
/*     */     
/* 224 */     PlayerInteractEvent event = new PlayerInteractEvent(player, action, itemInHand, blockClicked, blockFace);
/* 225 */     if (cancelledBlock) {
/* 226 */       event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
/*     */     }
/* 228 */     craftServer.getPluginManager().callEvent(event);
/*     */     
/* 230 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static org.bukkit.event.entity.EntityShootBowEvent callEntityShootBowEvent(EntityLiving who, net.minecraft.server.v1_8_R3.ItemStack itemstack, net.minecraft.server.v1_8_R3.EntityArrow entityArrow, float force)
/*     */   {
/* 237 */     LivingEntity shooter = (LivingEntity)who.getBukkitEntity();
/* 238 */     CraftItemStack itemInHand = CraftItemStack.asCraftMirror(itemstack);
/* 239 */     org.bukkit.entity.Arrow arrow = (org.bukkit.entity.Arrow)entityArrow.getBukkitEntity();
/*     */     
/* 241 */     if ((itemInHand != null) && ((itemInHand.getType() == Material.AIR) || (itemInHand.getAmount() == 0))) {
/* 242 */       itemInHand = null;
/*     */     }
/*     */     
/* 245 */     org.bukkit.event.entity.EntityShootBowEvent event = new org.bukkit.event.entity.EntityShootBowEvent(shooter, itemInHand, arrow, force);
/* 246 */     Bukkit.getPluginManager().callEvent(event);
/*     */     
/* 248 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static org.bukkit.event.block.BlockDamageEvent callBlockDamageEvent(EntityHuman who, int x, int y, int z, net.minecraft.server.v1_8_R3.ItemStack itemstack, boolean instaBreak)
/*     */   {
/* 255 */     Player player = who == null ? null : (Player)who.getBukkitEntity();
/* 256 */     CraftItemStack itemInHand = CraftItemStack.asCraftMirror(itemstack);
/*     */     
/* 258 */     CraftWorld craftWorld = (CraftWorld)player.getWorld();
/* 259 */     CraftServer craftServer = (CraftServer)player.getServer();
/*     */     
/* 261 */     org.bukkit.block.Block blockClicked = craftWorld.getBlockAt(x, y, z);
/*     */     
/* 263 */     org.bukkit.event.block.BlockDamageEvent event = new org.bukkit.event.block.BlockDamageEvent(player, blockClicked, itemInHand, instaBreak);
/* 264 */     craftServer.getPluginManager().callEvent(event);
/*     */     
/* 266 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static CreatureSpawnEvent callCreatureSpawnEvent(EntityLiving entityliving, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason spawnReason)
/*     */   {
/* 273 */     LivingEntity entity = (LivingEntity)entityliving.getBukkitEntity();
/* 274 */     CraftServer craftServer = (CraftServer)entity.getServer();
/*     */     
/* 276 */     CreatureSpawnEvent event = new CreatureSpawnEvent(entity, spawnReason);
/* 277 */     craftServer.getPluginManager().callEvent(event);
/* 278 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static org.bukkit.event.entity.EntityTameEvent callEntityTameEvent(EntityInsentient entity, EntityHuman tamer)
/*     */   {
/* 285 */     org.bukkit.entity.Entity bukkitEntity = entity.getBukkitEntity();
/* 286 */     org.bukkit.entity.AnimalTamer bukkitTamer = tamer != null ? tamer.getBukkitEntity() : null;
/* 287 */     CraftServer craftServer = (CraftServer)bukkitEntity.getServer();
/*     */     
/* 289 */     entity.persistent = true;
/*     */     
/* 291 */     org.bukkit.event.entity.EntityTameEvent event = new org.bukkit.event.entity.EntityTameEvent((LivingEntity)bukkitEntity, bukkitTamer);
/* 292 */     craftServer.getPluginManager().callEvent(event);
/* 293 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ItemSpawnEvent callItemSpawnEvent(EntityItem entityitem)
/*     */   {
/* 300 */     org.bukkit.entity.Item entity = (org.bukkit.entity.Item)entityitem.getBukkitEntity();
/* 301 */     CraftServer craftServer = (CraftServer)entity.getServer();
/*     */     
/* 303 */     ItemSpawnEvent event = new ItemSpawnEvent(entity, entity.getLocation());
/*     */     
/* 305 */     craftServer.getPluginManager().callEvent(event);
/* 306 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static org.bukkit.event.entity.ItemDespawnEvent callItemDespawnEvent(EntityItem entityitem)
/*     */   {
/* 313 */     org.bukkit.entity.Item entity = (org.bukkit.entity.Item)entityitem.getBukkitEntity();
/*     */     
/* 315 */     org.bukkit.event.entity.ItemDespawnEvent event = new org.bukkit.event.entity.ItemDespawnEvent(entity, entity.getLocation());
/*     */     
/* 317 */     entity.getServer().getPluginManager().callEvent(event);
/* 318 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static org.bukkit.event.entity.ItemMergeEvent callItemMergeEvent(EntityItem merging, EntityItem mergingWith)
/*     */   {
/* 325 */     org.bukkit.entity.Item entityMerging = (org.bukkit.entity.Item)merging.getBukkitEntity();
/* 326 */     org.bukkit.entity.Item entityMergingWith = (org.bukkit.entity.Item)mergingWith.getBukkitEntity();
/*     */     
/* 328 */     org.bukkit.event.entity.ItemMergeEvent event = new org.bukkit.event.entity.ItemMergeEvent(entityMerging, entityMergingWith);
/*     */     
/* 330 */     Bukkit.getPluginManager().callEvent(event);
/* 331 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static org.bukkit.event.entity.PotionSplashEvent callPotionSplashEvent(net.minecraft.server.v1_8_R3.EntityPotion potion, Map<LivingEntity, Double> affectedEntities)
/*     */   {
/* 338 */     org.bukkit.entity.ThrownPotion thrownPotion = (org.bukkit.entity.ThrownPotion)potion.getBukkitEntity();
/*     */     
/* 340 */     org.bukkit.event.entity.PotionSplashEvent event = new org.bukkit.event.entity.PotionSplashEvent(thrownPotion, affectedEntities);
/* 341 */     Bukkit.getPluginManager().callEvent(event);
/* 342 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static org.bukkit.event.block.BlockFadeEvent callBlockFadeEvent(org.bukkit.block.Block block, net.minecraft.server.v1_8_R3.Block type)
/*     */   {
/* 349 */     BlockState state = block.getState();
/* 350 */     state.setTypeId(net.minecraft.server.v1_8_R3.Block.getId(type));
/*     */     
/* 352 */     org.bukkit.event.block.BlockFadeEvent event = new org.bukkit.event.block.BlockFadeEvent(block, state);
/* 353 */     Bukkit.getPluginManager().callEvent(event);
/* 354 */     return event;
/*     */   }
/*     */   
/*     */   public static void handleBlockSpreadEvent(org.bukkit.block.Block block, org.bukkit.block.Block source, net.minecraft.server.v1_8_R3.Block type, int data) {
/* 358 */     BlockState state = block.getState();
/* 359 */     state.setTypeId(net.minecraft.server.v1_8_R3.Block.getId(type));
/* 360 */     state.setRawData((byte)data);
/*     */     
/* 362 */     org.bukkit.event.block.BlockSpreadEvent event = new org.bukkit.event.block.BlockSpreadEvent(block, source, state);
/* 363 */     Bukkit.getPluginManager().callEvent(event);
/*     */     
/* 365 */     if (!event.isCancelled()) {
/* 366 */       state.update(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public static EntityDeathEvent callEntityDeathEvent(EntityLiving victim) {
/* 371 */     return callEntityDeathEvent(victim, new java.util.ArrayList(0));
/*     */   }
/*     */   
/*     */   public static EntityDeathEvent callEntityDeathEvent(EntityLiving victim, List<org.bukkit.inventory.ItemStack> drops) {
/* 375 */     CraftLivingEntity entity = (CraftLivingEntity)victim.getBukkitEntity();
/* 376 */     EntityDeathEvent event = new EntityDeathEvent(entity, drops, victim.getExpReward());
/* 377 */     CraftWorld world = (CraftWorld)entity.getWorld();
/* 378 */     Bukkit.getServer().getPluginManager().callEvent(event);
/*     */     
/* 380 */     victim.expToDrop = event.getDroppedExp();
/*     */     
/* 382 */     for (org.bukkit.inventory.ItemStack stack : event.getDrops()) {
/* 383 */       if ((stack != null) && (stack.getType() != Material.AIR) && (stack.getAmount() != 0))
/*     */       {
/* 385 */         world.dropItemNaturally(entity.getLocation(), stack);
/*     */       }
/*     */     }
/* 388 */     return event;
/*     */   }
/*     */   
/*     */   public static PlayerDeathEvent callPlayerDeathEvent(EntityPlayer victim, List<org.bukkit.inventory.ItemStack> drops, String deathMessage, boolean keepInventory) {
/* 392 */     CraftPlayer entity = victim.getBukkitEntity();
/* 393 */     PlayerDeathEvent event = new PlayerDeathEvent(entity, drops, victim.getExpReward(), 0, deathMessage);
/* 394 */     event.setKeepInventory(keepInventory);
/* 395 */     org.bukkit.World world = entity.getWorld();
/* 396 */     Bukkit.getServer().getPluginManager().callEvent(event);
/*     */     
/* 398 */     victim.keepLevel = event.getKeepLevel();
/* 399 */     victim.newLevel = event.getNewLevel();
/* 400 */     victim.newTotalExp = event.getNewTotalExp();
/* 401 */     victim.expToDrop = event.getDroppedExp();
/* 402 */     victim.newExp = event.getNewExp();
/*     */     
/* 404 */     if (event.getKeepInventory()) {
/* 405 */       return event;
/*     */     }
/*     */     
/* 408 */     for (org.bukkit.inventory.ItemStack stack : event.getDrops()) {
/* 409 */       if ((stack != null) && (stack.getType() != Material.AIR))
/*     */       {
/* 411 */         world.dropItemNaturally(entity.getLocation(), stack);
/*     */       }
/*     */     }
/* 414 */     return event;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static org.bukkit.event.server.ServerListPingEvent callServerListPingEvent(Server craftServer, java.net.InetAddress address, String motd, int numPlayers, int maxPlayers)
/*     */   {
/* 421 */     org.bukkit.event.server.ServerListPingEvent event = new org.bukkit.event.server.ServerListPingEvent(address, motd, numPlayers, maxPlayers);
/* 422 */     craftServer.getPluginManager().callEvent(event);
/* 423 */     return event;
/*     */   }
/*     */   
/*     */   private static EntityDamageEvent handleEntityDamageEvent(net.minecraft.server.v1_8_R3.Entity entity, DamageSource source, Map<EntityDamageEvent.DamageModifier, Double> modifiers, Map<EntityDamageEvent.DamageModifier, Function<? super Double, Double>> modifierFunctions) {
/* 427 */     if (source.isExplosion())
/*     */     {
/* 429 */       net.minecraft.server.v1_8_R3.Entity damager = entityDamage;
/* 430 */       entityDamage = null;
/*     */       EntityDamageEvent event;
/* 432 */       EntityDamageEvent event; if (damager == null) {
/* 433 */         event = new EntityDamageByBlockEvent(null, entity.getBukkitEntity(), EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, modifiers, modifierFunctions); } else { EntityDamageEvent event;
/* 434 */         if (((entity instanceof net.minecraft.server.v1_8_R3.EntityEnderDragon)) && (((net.minecraft.server.v1_8_R3.EntityEnderDragon)entity).target == damager)) {
/* 435 */           event = new EntityDamageEvent(entity.getBukkitEntity(), EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, modifiers, modifierFunctions); } else { EntityDamageEvent.DamageCause damageCause;
/*     */           EntityDamageEvent.DamageCause damageCause;
/* 437 */           if ((damager instanceof org.bukkit.entity.TNTPrimed)) {
/* 438 */             damageCause = EntityDamageEvent.DamageCause.BLOCK_EXPLOSION;
/*     */           } else {
/* 440 */             damageCause = EntityDamageEvent.DamageCause.ENTITY_EXPLOSION;
/*     */           }
/* 442 */           event = new org.bukkit.event.entity.EntityDamageByEntityEvent(damager.getBukkitEntity(), entity.getBukkitEntity(), damageCause, modifiers, modifierFunctions);
/*     */         }
/*     */       }
/* 445 */       callEvent(event);
/*     */       
/* 447 */       if (!event.isCancelled()) {
/* 448 */         event.getEntity().setLastDamageCause(event);
/*     */       }
/* 450 */       return event; }
/* 451 */     if ((source instanceof net.minecraft.server.v1_8_R3.EntityDamageSource)) {
/* 452 */       net.minecraft.server.v1_8_R3.Entity damager = source.getEntity();
/* 453 */       EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.ENTITY_ATTACK;
/*     */       
/* 455 */       if ((source instanceof net.minecraft.server.v1_8_R3.EntityDamageSourceIndirect)) {
/* 456 */         damager = ((net.minecraft.server.v1_8_R3.EntityDamageSourceIndirect)source).getProximateDamageSource();
/* 457 */         if ((damager.getBukkitEntity() instanceof org.bukkit.entity.ThrownPotion)) {
/* 458 */           cause = EntityDamageEvent.DamageCause.MAGIC;
/* 459 */         } else if ((damager.getBukkitEntity() instanceof Projectile)) {
/* 460 */           cause = EntityDamageEvent.DamageCause.PROJECTILE;
/*     */         }
/* 462 */       } else if ("thorns".equals(source.translationIndex)) {
/* 463 */         cause = EntityDamageEvent.DamageCause.THORNS;
/*     */       }
/*     */       
/* 466 */       return callEntityDamageEvent(damager, entity, cause, modifiers, modifierFunctions); }
/* 467 */     if (source == DamageSource.OUT_OF_WORLD) {
/* 468 */       EntityDamageEvent event = (EntityDamageEvent)callEvent(new EntityDamageByBlockEvent(null, entity.getBukkitEntity(), EntityDamageEvent.DamageCause.VOID, modifiers, modifierFunctions));
/* 469 */       if (!event.isCancelled()) {
/* 470 */         event.getEntity().setLastDamageCause(event);
/*     */       }
/* 472 */       return event; }
/* 473 */     if (source == DamageSource.LAVA) {
/* 474 */       EntityDamageEvent event = (EntityDamageEvent)callEvent(new EntityDamageByBlockEvent(null, entity.getBukkitEntity(), EntityDamageEvent.DamageCause.LAVA, modifiers, modifierFunctions));
/* 475 */       if (!event.isCancelled()) {
/* 476 */         event.getEntity().setLastDamageCause(event);
/*     */       }
/* 478 */       return event; }
/* 479 */     if (blockDamage != null) {
/* 480 */       EntityDamageEvent.DamageCause cause = null;
/* 481 */       org.bukkit.block.Block damager = blockDamage;
/* 482 */       blockDamage = null;
/* 483 */       if (source == DamageSource.CACTUS) {
/* 484 */         cause = EntityDamageEvent.DamageCause.CONTACT;
/*     */       } else {
/* 486 */         throw new RuntimeException(String.format("Unhandled damage of %s by %s from %s", new Object[] { entity, damager, source.translationIndex }));
/*     */       }
/* 488 */       EntityDamageEvent event = (EntityDamageEvent)callEvent(new EntityDamageByBlockEvent(damager, entity.getBukkitEntity(), cause, modifiers, modifierFunctions));
/* 489 */       if (!event.isCancelled()) {
/* 490 */         event.getEntity().setLastDamageCause(event);
/*     */       }
/* 492 */       return event; }
/* 493 */     if (entityDamage != null) {
/* 494 */       EntityDamageEvent.DamageCause cause = null;
/* 495 */       CraftEntity damager = entityDamage.getBukkitEntity();
/* 496 */       entityDamage = null;
/* 497 */       if ((source == DamageSource.ANVIL) || (source == DamageSource.FALLING_BLOCK)) {
/* 498 */         cause = EntityDamageEvent.DamageCause.FALLING_BLOCK;
/* 499 */       } else if ((damager instanceof org.bukkit.entity.LightningStrike)) {
/* 500 */         cause = EntityDamageEvent.DamageCause.LIGHTNING;
/* 501 */       } else if (source == DamageSource.FALL) {
/* 502 */         cause = EntityDamageEvent.DamageCause.FALL;
/*     */       } else {
/* 504 */         throw new RuntimeException(String.format("Unhandled damage of %s by %s from %s", new Object[] { entity, damager.getHandle(), source.translationIndex }));
/*     */       }
/* 506 */       EntityDamageEvent event = (EntityDamageEvent)callEvent(new org.bukkit.event.entity.EntityDamageByEntityEvent(damager, entity.getBukkitEntity(), cause, modifiers, modifierFunctions));
/* 507 */       if (!event.isCancelled()) {
/* 508 */         event.getEntity().setLastDamageCause(event);
/*     */       }
/* 510 */       return event;
/*     */     }
/*     */     
/* 513 */     EntityDamageEvent.DamageCause cause = null;
/* 514 */     if (source == DamageSource.FIRE) {
/* 515 */       cause = EntityDamageEvent.DamageCause.FIRE;
/* 516 */     } else if (source == DamageSource.STARVE) {
/* 517 */       cause = EntityDamageEvent.DamageCause.STARVATION;
/* 518 */     } else if (source == DamageSource.WITHER) {
/* 519 */       cause = EntityDamageEvent.DamageCause.WITHER;
/* 520 */     } else if (source == DamageSource.STUCK) {
/* 521 */       cause = EntityDamageEvent.DamageCause.SUFFOCATION;
/* 522 */     } else if (source == DamageSource.DROWN) {
/* 523 */       cause = EntityDamageEvent.DamageCause.DROWNING;
/* 524 */     } else if (source == DamageSource.BURN) {
/* 525 */       cause = EntityDamageEvent.DamageCause.FIRE_TICK;
/* 526 */     } else if (source == MELTING) {
/* 527 */       cause = EntityDamageEvent.DamageCause.MELTING;
/* 528 */     } else if (source == POISON) {
/* 529 */       cause = EntityDamageEvent.DamageCause.POISON;
/* 530 */     } else if (source == DamageSource.MAGIC) {
/* 531 */       cause = EntityDamageEvent.DamageCause.MAGIC;
/* 532 */     } else if (source == DamageSource.FALL) {
/* 533 */       cause = EntityDamageEvent.DamageCause.FALL;
/* 534 */     } else if (source == DamageSource.GENERIC) {
/* 535 */       return new EntityDamageEvent(entity.getBukkitEntity(), null, modifiers, modifierFunctions);
/*     */     }
/*     */     
/* 538 */     if (cause != null) {
/* 539 */       return callEntityDamageEvent(null, entity, cause, modifiers, modifierFunctions);
/*     */     }
/*     */     
/* 542 */     throw new RuntimeException(String.format("Unhandled damage of %s from %s", new Object[] { entity, source.translationIndex }));
/*     */   }
/*     */   
/*     */   private static EntityDamageEvent callEntityDamageEvent(net.minecraft.server.v1_8_R3.Entity damager, net.minecraft.server.v1_8_R3.Entity damagee, EntityDamageEvent.DamageCause cause, Map<EntityDamageEvent.DamageModifier, Double> modifiers, Map<EntityDamageEvent.DamageModifier, Function<? super Double, Double>> modifierFunctions) { EntityDamageEvent event;
/*     */     EntityDamageEvent event;
/* 547 */     if (damager != null) {
/* 548 */       event = new org.bukkit.event.entity.EntityDamageByEntityEvent(damager.getBukkitEntity(), damagee.getBukkitEntity(), cause, modifiers, modifierFunctions);
/*     */     } else {
/* 550 */       event = new EntityDamageEvent(damagee.getBukkitEntity(), cause, modifiers, modifierFunctions);
/*     */     }
/*     */     
/* 553 */     callEvent(event);
/*     */     
/* 555 */     if (!event.isCancelled()) {
/* 556 */       event.getEntity().setLastDamageCause(event);
/*     */     }
/*     */     
/* 559 */     return event;
/*     */   }
/*     */   
/* 562 */   private static final Function<? super Double, Double> ZERO = com.google.common.base.Functions.constant(Double.valueOf(-0.0D));
/*     */   
/*     */   public static EntityDamageEvent handleLivingEntityDamageEvent(net.minecraft.server.v1_8_R3.Entity damagee, DamageSource source, double rawDamage, double hardHatModifier, double blockingModifier, double armorModifier, double resistanceModifier, double magicModifier, double absorptionModifier, Function<Double, Double> hardHat, Function<Double, Double> blocking, Function<Double, Double> armor, Function<Double, Double> resistance, Function<Double, Double> magic, Function<Double, Double> absorption) {
/* 565 */     Map<EntityDamageEvent.DamageModifier, Double> modifiers = new EnumMap(EntityDamageEvent.DamageModifier.class);
/* 566 */     Map<EntityDamageEvent.DamageModifier, Function<? super Double, Double>> modifierFunctions = new EnumMap(EntityDamageEvent.DamageModifier.class);
/* 567 */     modifiers.put(EntityDamageEvent.DamageModifier.BASE, Double.valueOf(rawDamage));
/* 568 */     modifierFunctions.put(EntityDamageEvent.DamageModifier.BASE, ZERO);
/* 569 */     if ((source == DamageSource.FALLING_BLOCK) || (source == DamageSource.ANVIL)) {
/* 570 */       modifiers.put(EntityDamageEvent.DamageModifier.HARD_HAT, Double.valueOf(hardHatModifier));
/* 571 */       modifierFunctions.put(EntityDamageEvent.DamageModifier.HARD_HAT, hardHat);
/*     */     }
/* 573 */     if ((damagee instanceof EntityHuman)) {
/* 574 */       modifiers.put(EntityDamageEvent.DamageModifier.BLOCKING, Double.valueOf(blockingModifier));
/* 575 */       modifierFunctions.put(EntityDamageEvent.DamageModifier.BLOCKING, blocking);
/*     */     }
/* 577 */     modifiers.put(EntityDamageEvent.DamageModifier.ARMOR, Double.valueOf(armorModifier));
/* 578 */     modifierFunctions.put(EntityDamageEvent.DamageModifier.ARMOR, armor);
/* 579 */     modifiers.put(EntityDamageEvent.DamageModifier.RESISTANCE, Double.valueOf(resistanceModifier));
/* 580 */     modifierFunctions.put(EntityDamageEvent.DamageModifier.RESISTANCE, resistance);
/* 581 */     modifiers.put(EntityDamageEvent.DamageModifier.MAGIC, Double.valueOf(magicModifier));
/* 582 */     modifierFunctions.put(EntityDamageEvent.DamageModifier.MAGIC, magic);
/* 583 */     modifiers.put(EntityDamageEvent.DamageModifier.ABSORPTION, Double.valueOf(absorptionModifier));
/* 584 */     modifierFunctions.put(EntityDamageEvent.DamageModifier.ABSORPTION, absorption);
/* 585 */     return handleEntityDamageEvent(damagee, source, modifiers, modifierFunctions);
/*     */   }
/*     */   
/*     */   public static boolean handleNonLivingEntityDamageEvent(net.minecraft.server.v1_8_R3.Entity entity, DamageSource source, double damage)
/*     */   {
/* 590 */     return handleNonLivingEntityDamageEvent(entity, source, damage, true);
/*     */   }
/*     */   
/*     */   public static boolean handleNonLivingEntityDamageEvent(net.minecraft.server.v1_8_R3.Entity entity, DamageSource source, double damage, boolean cancelOnZeroDamage) {
/* 594 */     if (((entity instanceof net.minecraft.server.v1_8_R3.EntityEnderCrystal)) && (!(source instanceof net.minecraft.server.v1_8_R3.EntityDamageSource))) {
/* 595 */       return false;
/*     */     }
/*     */     
/* 598 */     EnumMap<EntityDamageEvent.DamageModifier, Double> modifiers = new EnumMap(EntityDamageEvent.DamageModifier.class);
/* 599 */     EnumMap<EntityDamageEvent.DamageModifier, Function<? super Double, Double>> functions = new EnumMap(EntityDamageEvent.DamageModifier.class);
/*     */     
/* 601 */     modifiers.put(EntityDamageEvent.DamageModifier.BASE, Double.valueOf(damage));
/* 602 */     functions.put(EntityDamageEvent.DamageModifier.BASE, ZERO);
/*     */     
/* 604 */     EntityDamageEvent event = handleEntityDamageEvent(entity, source, modifiers, functions);
/* 605 */     if (event == null) {
/* 606 */       return false;
/*     */     }
/* 608 */     return (event.isCancelled()) || ((cancelOnZeroDamage) && (event.getDamage() == 0.0D));
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.player.PlayerLevelChangeEvent callPlayerLevelChangeEvent(Player player, int oldLevel, int newLevel) {
/* 612 */     org.bukkit.event.player.PlayerLevelChangeEvent event = new org.bukkit.event.player.PlayerLevelChangeEvent(player, oldLevel, newLevel);
/* 613 */     Bukkit.getPluginManager().callEvent(event);
/* 614 */     return event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.player.PlayerExpChangeEvent callPlayerExpChangeEvent(EntityHuman entity, int expAmount) {
/* 618 */     Player player = (Player)entity.getBukkitEntity();
/* 619 */     org.bukkit.event.player.PlayerExpChangeEvent event = new org.bukkit.event.player.PlayerExpChangeEvent(player, expAmount);
/* 620 */     Bukkit.getPluginManager().callEvent(event);
/* 621 */     return event;
/*     */   }
/*     */   
/*     */   public static void handleBlockGrowEvent(net.minecraft.server.v1_8_R3.World world, int x, int y, int z, net.minecraft.server.v1_8_R3.Block type, int data) {
/* 625 */     org.bukkit.block.Block block = world.getWorld().getBlockAt(x, y, z);
/* 626 */     CraftBlockState state = (CraftBlockState)block.getState();
/* 627 */     state.setTypeId(net.minecraft.server.v1_8_R3.Block.getId(type));
/* 628 */     state.setRawData((byte)data);
/*     */     
/* 630 */     org.bukkit.event.block.BlockGrowEvent event = new org.bukkit.event.block.BlockGrowEvent(block, state);
/* 631 */     Bukkit.getPluginManager().callEvent(event);
/*     */     
/* 633 */     if (!event.isCancelled()) {
/* 634 */       state.update(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.entity.FoodLevelChangeEvent callFoodLevelChangeEvent(EntityHuman entity, int level) {
/* 639 */     org.bukkit.event.entity.FoodLevelChangeEvent event = new org.bukkit.event.entity.FoodLevelChangeEvent(entity.getBukkitEntity(), level);
/* 640 */     entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
/* 641 */     return event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.entity.PigZapEvent callPigZapEvent(net.minecraft.server.v1_8_R3.Entity pig, net.minecraft.server.v1_8_R3.Entity lightning, net.minecraft.server.v1_8_R3.Entity pigzombie) {
/* 645 */     org.bukkit.event.entity.PigZapEvent event = new org.bukkit.event.entity.PigZapEvent((org.bukkit.entity.Pig)pig.getBukkitEntity(), (org.bukkit.entity.LightningStrike)lightning.getBukkitEntity(), (org.bukkit.entity.PigZombie)pigzombie.getBukkitEntity());
/* 646 */     pig.getBukkitEntity().getServer().getPluginManager().callEvent(event);
/* 647 */     return event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.entity.HorseJumpEvent callHorseJumpEvent(net.minecraft.server.v1_8_R3.Entity horse, float power) {
/* 651 */     org.bukkit.event.entity.HorseJumpEvent event = new org.bukkit.event.entity.HorseJumpEvent((org.bukkit.entity.Horse)horse.getBukkitEntity(), power);
/* 652 */     horse.getBukkitEntity().getServer().getPluginManager().callEvent(event);
/* 653 */     return event;
/*     */   }
/*     */   
/*     */   public static EntityChangeBlockEvent callEntityChangeBlockEvent(org.bukkit.entity.Entity entity, org.bukkit.block.Block block, Material material) {
/* 657 */     return callEntityChangeBlockEvent(entity, block, material, 0);
/*     */   }
/*     */   
/*     */   public static EntityChangeBlockEvent callEntityChangeBlockEvent(net.minecraft.server.v1_8_R3.Entity entity, org.bukkit.block.Block block, Material material) {
/* 661 */     return callEntityChangeBlockEvent(entity.getBukkitEntity(), block, material, 0);
/*     */   }
/*     */   
/*     */   public static EntityChangeBlockEvent callEntityChangeBlockEvent(net.minecraft.server.v1_8_R3.Entity entity, org.bukkit.block.Block block, Material material, boolean cancelled) {
/* 665 */     return callEntityChangeBlockEvent(entity.getBukkitEntity(), block, material, 0, cancelled);
/*     */   }
/*     */   
/*     */   public static EntityChangeBlockEvent callEntityChangeBlockEvent(net.minecraft.server.v1_8_R3.Entity entity, int x, int y, int z, net.minecraft.server.v1_8_R3.Block type, int data) {
/* 669 */     org.bukkit.block.Block block = entity.world.getWorld().getBlockAt(x, y, z);
/* 670 */     Material material = org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getMaterial(type);
/*     */     
/* 672 */     return callEntityChangeBlockEvent(entity.getBukkitEntity(), block, material, data);
/*     */   }
/*     */   
/*     */   public static EntityChangeBlockEvent callEntityChangeBlockEvent(org.bukkit.entity.Entity entity, org.bukkit.block.Block block, Material material, int data) {
/* 676 */     return callEntityChangeBlockEvent(entity, block, material, data, false);
/*     */   }
/*     */   
/*     */   public static EntityChangeBlockEvent callEntityChangeBlockEvent(org.bukkit.entity.Entity entity, org.bukkit.block.Block block, Material material, int data, boolean cancelled) {
/* 680 */     EntityChangeBlockEvent event = new EntityChangeBlockEvent(entity, block, material, (byte)data);
/* 681 */     event.setCancelled(cancelled);
/* 682 */     entity.getServer().getPluginManager().callEvent(event);
/* 683 */     return event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.entity.CreeperPowerEvent callCreeperPowerEvent(net.minecraft.server.v1_8_R3.Entity creeper, net.minecraft.server.v1_8_R3.Entity lightning, org.bukkit.event.entity.CreeperPowerEvent.PowerCause cause) {
/* 687 */     org.bukkit.event.entity.CreeperPowerEvent event = new org.bukkit.event.entity.CreeperPowerEvent((org.bukkit.entity.Creeper)creeper.getBukkitEntity(), (org.bukkit.entity.LightningStrike)lightning.getBukkitEntity(), cause);
/* 688 */     creeper.getBukkitEntity().getServer().getPluginManager().callEvent(event);
/* 689 */     return event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.entity.EntityTargetEvent callEntityTargetEvent(net.minecraft.server.v1_8_R3.Entity entity, net.minecraft.server.v1_8_R3.Entity target, EntityTargetEvent.TargetReason reason) {
/* 693 */     org.bukkit.event.entity.EntityTargetEvent event = new org.bukkit.event.entity.EntityTargetEvent(entity.getBukkitEntity(), target == null ? null : target.getBukkitEntity(), reason);
/* 694 */     entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
/* 695 */     return event;
/*     */   }
/*     */   
/*     */   public static EntityTargetLivingEntityEvent callEntityTargetLivingEvent(net.minecraft.server.v1_8_R3.Entity entity, EntityLiving target, EntityTargetEvent.TargetReason reason) {
/* 699 */     EntityTargetLivingEntityEvent event = new EntityTargetLivingEntityEvent(entity.getBukkitEntity(), (LivingEntity)target.getBukkitEntity(), reason);
/* 700 */     entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
/* 701 */     return event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.entity.EntityBreakDoorEvent callEntityBreakDoorEvent(net.minecraft.server.v1_8_R3.Entity entity, int x, int y, int z) {
/* 705 */     org.bukkit.entity.Entity entity1 = entity.getBukkitEntity();
/* 706 */     org.bukkit.block.Block block = entity1.getWorld().getBlockAt(x, y, z);
/*     */     
/* 708 */     org.bukkit.event.entity.EntityBreakDoorEvent event = new org.bukkit.event.entity.EntityBreakDoorEvent((LivingEntity)entity1, block);
/* 709 */     entity1.getServer().getPluginManager().callEvent(event);
/*     */     
/* 711 */     return event;
/*     */   }
/*     */   
/*     */   public static Container callInventoryOpenEvent(EntityPlayer player, Container container) {
/* 715 */     return callInventoryOpenEvent(player, container, false);
/*     */   }
/*     */   
/*     */   public static Container callInventoryOpenEvent(EntityPlayer player, Container container, boolean cancelled) {
/* 719 */     if (player.activeContainer != player.defaultContainer) {
/* 720 */       player.playerConnection.a(new net.minecraft.server.v1_8_R3.PacketPlayInCloseWindow(player.activeContainer.windowId));
/*     */     }
/*     */     
/* 723 */     CraftServer server = player.world.getServer();
/* 724 */     CraftPlayer craftPlayer = player.getBukkitEntity();
/* 725 */     player.activeContainer.transferTo(container, craftPlayer);
/*     */     
/* 727 */     InventoryOpenEvent event = new InventoryOpenEvent(container.getBukkitView());
/* 728 */     event.setCancelled(cancelled);
/* 729 */     server.getPluginManager().callEvent(event);
/*     */     
/* 731 */     if (event.isCancelled()) {
/* 732 */       container.transferTo(player.activeContainer, craftPlayer);
/* 733 */       return null;
/*     */     }
/*     */     
/* 736 */     return container;
/*     */   }
/*     */   
/*     */   public static net.minecraft.server.v1_8_R3.ItemStack callPreCraftEvent(net.minecraft.server.v1_8_R3.InventoryCrafting matrix, net.minecraft.server.v1_8_R3.ItemStack result, org.bukkit.inventory.InventoryView lastCraftView, boolean isRepair) {
/* 740 */     CraftInventoryCrafting inventory = new CraftInventoryCrafting(matrix, matrix.resultInventory);
/* 741 */     inventory.setResult(CraftItemStack.asCraftMirror(result));
/*     */     
/* 743 */     PrepareItemCraftEvent event = new PrepareItemCraftEvent(inventory, lastCraftView, isRepair);
/* 744 */     Bukkit.getPluginManager().callEvent(event);
/*     */     
/* 746 */     org.bukkit.inventory.ItemStack bitem = event.getInventory().getResult();
/*     */     
/* 748 */     return CraftItemStack.asNMSCopy(bitem);
/*     */   }
/*     */   
/*     */   public static ProjectileLaunchEvent callProjectileLaunchEvent(net.minecraft.server.v1_8_R3.Entity entity) {
/* 752 */     Projectile bukkitEntity = (Projectile)entity.getBukkitEntity();
/* 753 */     ProjectileLaunchEvent event = new ProjectileLaunchEvent(bukkitEntity);
/* 754 */     Bukkit.getPluginManager().callEvent(event);
/* 755 */     return event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.entity.ProjectileHitEvent callProjectileHitEvent(net.minecraft.server.v1_8_R3.Entity entity) {
/* 759 */     org.bukkit.event.entity.ProjectileHitEvent event = new org.bukkit.event.entity.ProjectileHitEvent((Projectile)entity.getBukkitEntity());
/* 760 */     entity.world.getServer().getPluginManager().callEvent(event);
/* 761 */     return event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.entity.ExpBottleEvent callExpBottleEvent(net.minecraft.server.v1_8_R3.Entity entity, int exp) {
/* 765 */     org.bukkit.entity.ThrownExpBottle bottle = (org.bukkit.entity.ThrownExpBottle)entity.getBukkitEntity();
/* 766 */     org.bukkit.event.entity.ExpBottleEvent event = new org.bukkit.event.entity.ExpBottleEvent(bottle, exp);
/* 767 */     Bukkit.getPluginManager().callEvent(event);
/* 768 */     return event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.block.BlockRedstoneEvent callRedstoneChange(net.minecraft.server.v1_8_R3.World world, int x, int y, int z, int oldCurrent, int newCurrent) {
/* 772 */     org.bukkit.event.block.BlockRedstoneEvent event = new org.bukkit.event.block.BlockRedstoneEvent(world.getWorld().getBlockAt(x, y, z), oldCurrent, newCurrent);
/* 773 */     world.getServer().getPluginManager().callEvent(event);
/* 774 */     return event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.block.NotePlayEvent callNotePlayEvent(net.minecraft.server.v1_8_R3.World world, int x, int y, int z, byte instrument, byte note) {
/* 778 */     org.bukkit.event.block.NotePlayEvent event = new org.bukkit.event.block.NotePlayEvent(world.getWorld().getBlockAt(x, y, z), org.bukkit.Instrument.getByType(instrument), new org.bukkit.Note(note));
/* 779 */     world.getServer().getPluginManager().callEvent(event);
/* 780 */     return event;
/*     */   }
/*     */   
/*     */   public static void callPlayerItemBreakEvent(EntityHuman human, net.minecraft.server.v1_8_R3.ItemStack brokenItem) {
/* 784 */     CraftItemStack item = CraftItemStack.asCraftMirror(brokenItem);
/* 785 */     org.bukkit.event.player.PlayerItemBreakEvent event = new org.bukkit.event.player.PlayerItemBreakEvent((Player)human.getBukkitEntity(), item);
/* 786 */     Bukkit.getPluginManager().callEvent(event);
/*     */   }
/*     */   
/*     */   public static BlockIgniteEvent callBlockIgniteEvent(net.minecraft.server.v1_8_R3.World world, int x, int y, int z, int igniterX, int igniterY, int igniterZ) {
/* 790 */     org.bukkit.World bukkitWorld = world.getWorld();
/* 791 */     org.bukkit.block.Block igniter = bukkitWorld.getBlockAt(igniterX, igniterY, igniterZ);
/*     */     BlockIgniteEvent.IgniteCause cause;
/* 793 */     BlockIgniteEvent.IgniteCause cause; BlockIgniteEvent.IgniteCause cause; switch (igniter.getType()) {
/*     */     case ARROW: 
/*     */     case BAKED_POTATO: 
/* 796 */       cause = BlockIgniteEvent.IgniteCause.LAVA;
/* 797 */       break;
/*     */     case BLAZE_POWDER: 
/* 799 */       cause = BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL;
/* 800 */       break;
/*     */     case CHAINMAIL_HELMET: 
/*     */     default: 
/* 803 */       cause = BlockIgniteEvent.IgniteCause.SPREAD;
/*     */     }
/*     */     
/* 806 */     BlockIgniteEvent event = new BlockIgniteEvent(bukkitWorld.getBlockAt(x, y, z), cause, igniter);
/* 807 */     world.getServer().getPluginManager().callEvent(event);
/* 808 */     return event;
/*     */   }
/*     */   
/*     */   public static BlockIgniteEvent callBlockIgniteEvent(net.minecraft.server.v1_8_R3.World world, int x, int y, int z, net.minecraft.server.v1_8_R3.Entity igniter) {
/* 812 */     org.bukkit.World bukkitWorld = world.getWorld();
/* 813 */     org.bukkit.entity.Entity bukkitIgniter = igniter.getBukkitEntity();
/*     */     BlockIgniteEvent.IgniteCause cause;
/* 815 */     BlockIgniteEvent.IgniteCause cause; BlockIgniteEvent.IgniteCause cause; BlockIgniteEvent.IgniteCause cause; switch (bukkitIgniter.getType()) {
/*     */     case THROWN_EXP_BOTTLE: 
/* 817 */       cause = BlockIgniteEvent.IgniteCause.ENDER_CRYSTAL;
/* 818 */       break;
/*     */     case WITCH: 
/* 820 */       cause = BlockIgniteEvent.IgniteCause.LIGHTNING;
/* 821 */       break;
/*     */     case CHICKEN: 
/*     */     case COMPLEX_PART: 
/* 824 */       cause = BlockIgniteEvent.IgniteCause.FIREBALL;
/* 825 */       break;
/*     */     default: 
/* 827 */       cause = BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL;
/*     */     }
/*     */     
/* 830 */     BlockIgniteEvent event = new BlockIgniteEvent(bukkitWorld.getBlockAt(x, y, z), cause, bukkitIgniter);
/* 831 */     world.getServer().getPluginManager().callEvent(event);
/* 832 */     return event;
/*     */   }
/*     */   
/*     */   public static BlockIgniteEvent callBlockIgniteEvent(net.minecraft.server.v1_8_R3.World world, int x, int y, int z, Explosion explosion) {
/* 836 */     org.bukkit.World bukkitWorld = world.getWorld();
/* 837 */     org.bukkit.entity.Entity igniter = explosion.source == null ? null : explosion.source.getBukkitEntity();
/*     */     
/* 839 */     BlockIgniteEvent event = new BlockIgniteEvent(bukkitWorld.getBlockAt(x, y, z), BlockIgniteEvent.IgniteCause.EXPLOSION, igniter);
/* 840 */     world.getServer().getPluginManager().callEvent(event);
/* 841 */     return event;
/*     */   }
/*     */   
/*     */   public static BlockIgniteEvent callBlockIgniteEvent(net.minecraft.server.v1_8_R3.World world, int x, int y, int z, BlockIgniteEvent.IgniteCause cause, net.minecraft.server.v1_8_R3.Entity igniter) {
/* 845 */     BlockIgniteEvent event = new BlockIgniteEvent(world.getWorld().getBlockAt(x, y, z), cause, igniter.getBukkitEntity());
/* 846 */     world.getServer().getPluginManager().callEvent(event);
/* 847 */     return event;
/*     */   }
/*     */   
/*     */   public static void handleInventoryCloseEvent(EntityHuman human) {
/* 851 */     org.bukkit.event.inventory.InventoryCloseEvent event = new org.bukkit.event.inventory.InventoryCloseEvent(human.activeContainer.getBukkitView());
/* 852 */     human.world.getServer().getPluginManager().callEvent(event);
/* 853 */     human.activeContainer.transferTo(human.defaultContainer, human.getBukkitEntity());
/*     */   }
/*     */   
/*     */   public static void handleEditBookEvent(EntityPlayer player, net.minecraft.server.v1_8_R3.ItemStack newBookItem) {
/* 857 */     int itemInHandIndex = player.inventory.itemInHandIndex;
/*     */     
/* 859 */     PlayerEditBookEvent editBookEvent = new PlayerEditBookEvent(player.getBukkitEntity(), player.inventory.itemInHandIndex, (org.bukkit.inventory.meta.BookMeta)CraftItemStack.getItemMeta(player.inventory.getItemInHand()), (org.bukkit.inventory.meta.BookMeta)CraftItemStack.getItemMeta(newBookItem), newBookItem.getItem() == Items.WRITTEN_BOOK);
/* 860 */     player.world.getServer().getPluginManager().callEvent(editBookEvent);
/* 861 */     net.minecraft.server.v1_8_R3.ItemStack itemInHand = player.inventory.getItem(itemInHandIndex);
/*     */     
/*     */ 
/* 864 */     if ((itemInHand != null) && (itemInHand.getItem() == Items.WRITABLE_BOOK)) {
/* 865 */       if (!editBookEvent.isCancelled()) {
/* 866 */         if (editBookEvent.isSigning()) {
/* 867 */           itemInHand.setItem(Items.WRITTEN_BOOK);
/*     */         }
/* 869 */         org.bukkit.craftbukkit.v1_8_R3.inventory.CraftMetaBook meta = (org.bukkit.craftbukkit.v1_8_R3.inventory.CraftMetaBook)editBookEvent.getNewBookMeta();
/* 870 */         List<IChatBaseComponent> pages = meta.pages;
/* 871 */         for (int i = 0; i < pages.size(); i++) {
/* 872 */           pages.set(i, stripEvents((IChatBaseComponent)pages.get(i)));
/*     */         }
/* 874 */         CraftItemStack.setItemMeta(itemInHand, meta);
/*     */       }
/*     */       
/*     */ 
/* 878 */       net.minecraft.server.v1_8_R3.Slot slot = player.activeContainer.getSlot(player.inventory, itemInHandIndex);
/* 879 */       player.playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot(player.activeContainer.windowId, slot.rawSlotIndex, itemInHand));
/*     */     }
/*     */   }
/*     */   
/*     */   private static IChatBaseComponent stripEvents(IChatBaseComponent c) {
/* 884 */     net.minecraft.server.v1_8_R3.ChatModifier modi = c.getChatModifier();
/* 885 */     if (modi != null) {
/* 886 */       modi.setChatClickable(null);
/* 887 */       modi.setChatHoverable(null);
/*     */     }
/* 889 */     c.setChatModifier(modi);
/* 890 */     if ((c instanceof ChatMessage)) {
/* 891 */       ChatMessage cm = (ChatMessage)c;
/* 892 */       Object[] oo = cm.j();
/* 893 */       for (int i = 0; i < oo.length; i++) {
/* 894 */         Object o = oo[i];
/* 895 */         if ((o instanceof IChatBaseComponent)) {
/* 896 */           oo[i] = stripEvents((IChatBaseComponent)o);
/*     */         }
/*     */       }
/*     */     }
/* 900 */     List<IChatBaseComponent> ls = c.a();
/* 901 */     if (ls != null) {
/* 902 */       for (int i = 0; i < ls.size(); i++) {
/* 903 */         ls.set(i, stripEvents((IChatBaseComponent)ls.get(i)));
/*     */       }
/*     */     }
/* 906 */     return c;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.player.PlayerUnleashEntityEvent callPlayerUnleashEntityEvent(EntityInsentient entity, EntityHuman player) {
/* 910 */     org.bukkit.event.player.PlayerUnleashEntityEvent event = new org.bukkit.event.player.PlayerUnleashEntityEvent(entity.getBukkitEntity(), (Player)player.getBukkitEntity());
/* 911 */     entity.world.getServer().getPluginManager().callEvent(event);
/* 912 */     return event;
/*     */   }
/*     */   
/*     */   public static PlayerLeashEntityEvent callPlayerLeashEntityEvent(EntityInsentient entity, net.minecraft.server.v1_8_R3.Entity leashHolder, EntityHuman player) {
/* 916 */     PlayerLeashEntityEvent event = new PlayerLeashEntityEvent(entity.getBukkitEntity(), leashHolder.getBukkitEntity(), (Player)player.getBukkitEntity());
/* 917 */     entity.world.getServer().getPluginManager().callEvent(event);
/* 918 */     return event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.Cancellable handleStatisticsIncrease(EntityHuman entityHuman, net.minecraft.server.v1_8_R3.Statistic statistic, int current, int incrementation) {
/* 922 */     Player player = ((EntityPlayer)entityHuman).getBukkitEntity();
/*     */     Event event;
/* 924 */     Event event; if ((statistic instanceof net.minecraft.server.v1_8_R3.Achievement)) {
/* 925 */       if (current != 0) {
/* 926 */         return null;
/*     */       }
/* 928 */       event = new org.bukkit.event.player.PlayerAchievementAwardedEvent(player, CraftStatistic.getBukkitAchievement((net.minecraft.server.v1_8_R3.Achievement)statistic));
/*     */     } else {
/* 930 */       org.bukkit.Statistic stat = CraftStatistic.getBukkitStatistic(statistic);
/* 931 */       if (stat == null) {
/* 932 */         System.err.println("Unhandled statistic: " + statistic);
/* 933 */         return null;
/*     */       }
/* 935 */       switch (stat)
/*     */       {
/*     */       case CRAFTING_TABLE_INTERACTION: 
/*     */       case CRAFT_ITEM: 
/*     */       case CROUCH_ONE_CM: 
/*     */       case DAMAGE_DEALT: 
/*     */       case DAMAGE_TAKEN: 
/*     */       case DEATHS: 
/*     */       case DISPENSER_INSPECTED: 
/*     */       case DIVE_ONE_CM: 
/*     */       case DROP: 
/*     */       case DROPPER_INSPECTED: 
/*     */       case ENDERCHEST_OPENED: 
/*     */       case ENTITY_KILLED_BY: 
/*     */       case FALL_ONE_CM: 
/*     */       case ITEM_ENCHANTED: 
/* 951 */         return null;
/*     */       }
/*     */       Event event;
/* 954 */       if (stat.getType() == org.bukkit.Statistic.Type.UNTYPED) {
/* 955 */         event = new org.bukkit.event.player.PlayerStatisticIncrementEvent(player, stat, current, current + incrementation); } else { Event event;
/* 956 */         if (stat.getType() == org.bukkit.Statistic.Type.ENTITY) {
/* 957 */           org.bukkit.entity.EntityType entityType = CraftStatistic.getEntityTypeFromStatistic(statistic);
/* 958 */           event = new org.bukkit.event.player.PlayerStatisticIncrementEvent(player, stat, current, current + incrementation, entityType);
/*     */         } else {
/* 960 */           Material material = CraftStatistic.getMaterialFromStatistic(statistic);
/* 961 */           event = new org.bukkit.event.player.PlayerStatisticIncrementEvent(player, stat, current, current + incrementation, material);
/*     */         }
/*     */       } }
/* 964 */     entityHuman.world.getServer().getPluginManager().callEvent(event);
/* 965 */     return (org.bukkit.event.Cancellable)event;
/*     */   }
/*     */   
/*     */   public static org.bukkit.event.entity.FireworkExplodeEvent callFireworkExplodeEvent(EntityFireworks firework) {
/* 969 */     org.bukkit.event.entity.FireworkExplodeEvent event = new org.bukkit.event.entity.FireworkExplodeEvent((org.bukkit.entity.Firework)firework.getBukkitEntity());
/* 970 */     firework.world.getServer().getPluginManager().callEvent(event);
/* 971 */     return event;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\event\CraftEventFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */