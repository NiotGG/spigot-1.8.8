/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Random;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.event.block.BlockDispenseEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class DispenserRegistry
/*     */ {
/*  17 */   private static final PrintStream a = System.out;
/*  18 */   private static boolean b = false;
/*  19 */   private static final Logger c = LogManager.getLogger();
/*     */   
/*     */   public static boolean a() {
/*  22 */     return b;
/*     */   }
/*     */   
/*     */   static void b() {
/*  26 */     BlockDispenser.REGISTRY.a(Items.ARROW, new DispenseBehaviorProjectile() {
/*     */       protected IProjectile a(World world, IPosition iposition) {
/*  28 */         EntityArrow entityarrow = new EntityArrow(world, iposition.getX(), iposition.getY(), iposition.getZ());
/*     */         
/*  30 */         entityarrow.fromPlayer = 1;
/*  31 */         return entityarrow;
/*     */       }
/*  33 */     });
/*  34 */     BlockDispenser.REGISTRY.a(Items.EGG, new DispenseBehaviorProjectile() {
/*     */       protected IProjectile a(World world, IPosition iposition) {
/*  36 */         return new EntityEgg(world, iposition.getX(), iposition.getY(), iposition.getZ());
/*     */       }
/*  38 */     });
/*  39 */     BlockDispenser.REGISTRY.a(Items.SNOWBALL, new DispenseBehaviorProjectile() {
/*     */       protected IProjectile a(World world, IPosition iposition) {
/*  41 */         return new EntitySnowball(world, iposition.getX(), iposition.getY(), iposition.getZ());
/*     */       }
/*  43 */     });
/*  44 */     BlockDispenser.REGISTRY.a(Items.EXPERIENCE_BOTTLE, new DispenseBehaviorProjectile() {
/*     */       protected IProjectile a(World world, IPosition iposition) {
/*  46 */         return new EntityThrownExpBottle(world, iposition.getX(), iposition.getY(), iposition.getZ());
/*     */       }
/*     */       
/*     */       protected float a() {
/*  50 */         return super.a() * 0.5F;
/*     */       }
/*     */       
/*     */       protected float getPower() {
/*  54 */         return super.getPower() * 1.25F;
/*     */       }
/*  56 */     });
/*  57 */     BlockDispenser.REGISTRY.a(Items.POTION, new IDispenseBehavior() {
/*  58 */       private final DispenseBehaviorItem b = new DispenseBehaviorItem();
/*     */       
/*     */       public ItemStack a(ISourceBlock isourceblock, final ItemStack itemstack) {
/*  61 */         ItemPotion.f(itemstack.getData()) ? new DispenseBehaviorProjectile() {
/*     */           protected IProjectile a(World world, IPosition iposition) {
/*  63 */             return new EntityPotion(world, iposition.getX(), iposition.getY(), iposition.getZ(), itemstack.cloneItemStack());
/*     */           }
/*     */           
/*     */           protected float a() {
/*  67 */             return super.a() * 0.5F;
/*     */           }
/*     */           
/*     */           protected float getPower() {
/*  71 */             return super.getPower() * 1.25F;
/*     */           }
/*  73 */         }.a(isourceblock, itemstack) : this.b.a(isourceblock, itemstack);
/*     */       }
/*  75 */     });
/*  76 */     BlockDispenser.REGISTRY.a(Items.SPAWN_EGG, new DispenseBehaviorItem() {
/*     */       public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/*  78 */         EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
/*  79 */         double d0 = isourceblock.getX() + enumdirection.getAdjacentX();
/*  80 */         double d1 = isourceblock.getBlockPosition().getY() + 0.2F;
/*  81 */         double d2 = isourceblock.getZ() + enumdirection.getAdjacentZ();
/*     */         
/*     */ 
/*     */ 
/*  85 */         World world = isourceblock.getWorld();
/*  86 */         ItemStack itemstack1 = itemstack.cloneAndSubtract(1);
/*  87 */         org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
/*  88 */         CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*     */         
/*  90 */         BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(d0, d1, d2));
/*  91 */         if (!BlockDispenser.eventFired) {
/*  92 */           world.getServer().getPluginManager().callEvent(event);
/*     */         }
/*     */         
/*  95 */         if (event.isCancelled()) {
/*  96 */           itemstack.count += 1;
/*  97 */           return itemstack;
/*     */         }
/*     */         
/* 100 */         if (!event.getItem().equals(craftItem)) {
/* 101 */           itemstack.count += 1;
/*     */           
/* 103 */           ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 104 */           IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(eventStack.getItem());
/* 105 */           if ((idispensebehavior != IDispenseBehavior.NONE) && (idispensebehavior != this)) {
/* 106 */             idispensebehavior.a(isourceblock, eventStack);
/* 107 */             return itemstack;
/*     */           }
/*     */         }
/*     */         
/* 111 */         itemstack1 = CraftItemStack.asNMSCopy(event.getItem());
/*     */         
/* 113 */         Entity entity = ItemMonsterEgg.spawnCreature(isourceblock.getWorld(), itemstack.getData(), event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.DISPENSE_EGG);
/*     */         
/* 115 */         if (((entity instanceof EntityLiving)) && (itemstack.hasName())) {
/* 116 */           ((EntityInsentient)entity).setCustomName(itemstack.getName());
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 121 */         return itemstack;
/*     */       }
/* 123 */     });
/* 124 */     BlockDispenser.REGISTRY.a(Items.FIREWORKS, new DispenseBehaviorItem() {
/*     */       public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 126 */         EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
/* 127 */         double d0 = isourceblock.getX() + enumdirection.getAdjacentX();
/* 128 */         double d1 = isourceblock.getBlockPosition().getY() + 0.2F;
/* 129 */         double d2 = isourceblock.getZ() + enumdirection.getAdjacentZ();
/*     */         
/* 131 */         World world = isourceblock.getWorld();
/* 132 */         ItemStack itemstack1 = itemstack.cloneAndSubtract(1);
/* 133 */         org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
/* 134 */         CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*     */         
/* 136 */         BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(d0, d1, d2));
/* 137 */         if (!BlockDispenser.eventFired) {
/* 138 */           world.getServer().getPluginManager().callEvent(event);
/*     */         }
/*     */         
/* 141 */         if (event.isCancelled()) {
/* 142 */           itemstack.count += 1;
/* 143 */           return itemstack;
/*     */         }
/*     */         
/* 146 */         if (!event.getItem().equals(craftItem)) {
/* 147 */           itemstack.count += 1;
/*     */           
/* 149 */           ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 150 */           IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(eventStack.getItem());
/* 151 */           if ((idispensebehavior != IDispenseBehavior.NONE) && (idispensebehavior != this)) {
/* 152 */             idispensebehavior.a(isourceblock, eventStack);
/* 153 */             return itemstack;
/*     */           }
/*     */         }
/*     */         
/* 157 */         itemstack1 = CraftItemStack.asNMSCopy(event.getItem());
/* 158 */         EntityFireworks entityfireworks = new EntityFireworks(isourceblock.getWorld(), event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), itemstack1);
/*     */         
/* 160 */         isourceblock.getWorld().addEntity(entityfireworks);
/*     */         
/*     */ 
/* 163 */         return itemstack;
/*     */       }
/*     */       
/*     */       protected void a(ISourceBlock isourceblock) {
/* 167 */         isourceblock.getWorld().triggerEffect(1002, isourceblock.getBlockPosition(), 0);
/*     */       }
/* 169 */     });
/* 170 */     BlockDispenser.REGISTRY.a(Items.FIRE_CHARGE, new DispenseBehaviorItem() {
/*     */       public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 172 */         EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
/* 173 */         IPosition iposition = BlockDispenser.a(isourceblock);
/* 174 */         double d0 = iposition.getX() + enumdirection.getAdjacentX() * 0.3F;
/* 175 */         double d1 = iposition.getY() + enumdirection.getAdjacentY() * 0.3F;
/* 176 */         double d2 = iposition.getZ() + enumdirection.getAdjacentZ() * 0.3F;
/* 177 */         World world = isourceblock.getWorld();
/* 178 */         Random random = world.random;
/* 179 */         double d3 = random.nextGaussian() * 0.05D + enumdirection.getAdjacentX();
/* 180 */         double d4 = random.nextGaussian() * 0.05D + enumdirection.getAdjacentY();
/* 181 */         double d5 = random.nextGaussian() * 0.05D + enumdirection.getAdjacentZ();
/*     */         
/*     */ 
/* 184 */         ItemStack itemstack1 = itemstack.cloneAndSubtract(1);
/* 185 */         org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
/* 186 */         CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*     */         
/* 188 */         BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(d3, d4, d5));
/* 189 */         if (!BlockDispenser.eventFired) {
/* 190 */           world.getServer().getPluginManager().callEvent(event);
/*     */         }
/*     */         
/* 193 */         if (event.isCancelled()) {
/* 194 */           itemstack.count += 1;
/* 195 */           return itemstack;
/*     */         }
/*     */         
/* 198 */         if (!event.getItem().equals(craftItem)) {
/* 199 */           itemstack.count += 1;
/*     */           
/* 201 */           ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 202 */           IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(eventStack.getItem());
/* 203 */           if ((idispensebehavior != IDispenseBehavior.NONE) && (idispensebehavior != this)) {
/* 204 */             idispensebehavior.a(isourceblock, eventStack);
/* 205 */             return itemstack;
/*     */           }
/*     */         }
/*     */         
/* 209 */         EntitySmallFireball entitysmallfireball = new EntitySmallFireball(world, d0, d1, d2, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ());
/* 210 */         entitysmallfireball.projectileSource = new org.bukkit.craftbukkit.v1_8_R3.projectiles.CraftBlockProjectileSource((TileEntityDispenser)isourceblock.getTileEntity());
/*     */         
/* 212 */         world.addEntity(entitysmallfireball);
/*     */         
/*     */ 
/* 215 */         return itemstack;
/*     */       }
/*     */       
/*     */       protected void a(ISourceBlock isourceblock) {
/* 219 */         isourceblock.getWorld().triggerEffect(1009, isourceblock.getBlockPosition(), 0);
/*     */       }
/* 221 */     });
/* 222 */     BlockDispenser.REGISTRY.a(Items.BOAT, new DispenseBehaviorItem() {
/* 223 */       private final DispenseBehaviorItem b = new DispenseBehaviorItem();
/*     */       
/*     */       public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 226 */         EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
/* 227 */         World world = isourceblock.getWorld();
/* 228 */         double d0 = isourceblock.getX() + enumdirection.getAdjacentX() * 1.125F;
/* 229 */         double d1 = isourceblock.getY() + enumdirection.getAdjacentY() * 1.125F;
/* 230 */         double d2 = isourceblock.getZ() + enumdirection.getAdjacentZ() * 1.125F;
/* 231 */         BlockPosition blockposition = isourceblock.getBlockPosition().shift(enumdirection);
/* 232 */         Material material = world.getType(blockposition).getBlock().getMaterial();
/*     */         double d3;
/*     */         double d3;
/* 235 */         if (Material.WATER.equals(material)) {
/* 236 */           d3 = 1.0D;
/*     */         } else {
/* 238 */           if ((!Material.AIR.equals(material)) || (!Material.WATER.equals(world.getType(blockposition.down()).getBlock().getMaterial()))) {
/* 239 */             return this.b.a(isourceblock, itemstack);
/*     */           }
/*     */           
/* 242 */           d3 = 0.0D;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 247 */         ItemStack itemstack1 = itemstack.cloneAndSubtract(1);
/* 248 */         org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
/* 249 */         CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*     */         
/* 251 */         BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(d0, d1 + d3, d2));
/* 252 */         if (!BlockDispenser.eventFired) {
/* 253 */           world.getServer().getPluginManager().callEvent(event);
/*     */         }
/*     */         
/* 256 */         if (event.isCancelled()) {
/* 257 */           itemstack.count += 1;
/* 258 */           return itemstack;
/*     */         }
/*     */         
/* 261 */         if (!event.getItem().equals(craftItem)) {
/* 262 */           itemstack.count += 1;
/*     */           
/* 264 */           ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 265 */           IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(eventStack.getItem());
/* 266 */           if ((idispensebehavior != IDispenseBehavior.NONE) && (idispensebehavior != this)) {
/* 267 */             idispensebehavior.a(isourceblock, eventStack);
/* 268 */             return itemstack;
/*     */           }
/*     */         }
/*     */         
/* 272 */         EntityBoat entityboat = new EntityBoat(world, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ());
/*     */         
/*     */ 
/* 275 */         world.addEntity(entityboat);
/*     */         
/* 277 */         return itemstack;
/*     */       }
/*     */       
/*     */       protected void a(ISourceBlock isourceblock) {
/* 281 */         isourceblock.getWorld().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
/*     */       }
/* 283 */     });
/* 284 */     DispenseBehaviorItem dispensebehavioritem = new DispenseBehaviorItem() {
/* 285 */       private final DispenseBehaviorItem b = new DispenseBehaviorItem();
/*     */       
/*     */       public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 288 */         ItemBucket itembucket = (ItemBucket)itemstack.getItem();
/* 289 */         BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));
/*     */         
/*     */ 
/* 292 */         World world = isourceblock.getWorld();
/* 293 */         int x = blockposition.getX();
/* 294 */         int y = blockposition.getY();
/* 295 */         int z = blockposition.getZ();
/* 296 */         if ((world.isEmpty(blockposition)) || (!world.getType(blockposition).getBlock().getMaterial().isBuildable())) {
/* 297 */           org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
/* 298 */           CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
/*     */           
/* 300 */           BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(x, y, z));
/* 301 */           if (!BlockDispenser.eventFired) {
/* 302 */             world.getServer().getPluginManager().callEvent(event);
/*     */           }
/*     */           
/* 305 */           if (event.isCancelled()) {
/* 306 */             return itemstack;
/*     */           }
/*     */           
/* 309 */           if (!event.getItem().equals(craftItem))
/*     */           {
/* 311 */             ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 312 */             IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(eventStack.getItem());
/* 313 */             if ((idispensebehavior != IDispenseBehavior.NONE) && (idispensebehavior != this)) {
/* 314 */               idispensebehavior.a(isourceblock, eventStack);
/* 315 */               return itemstack;
/*     */             }
/*     */           }
/*     */           
/* 319 */           itembucket = (ItemBucket)CraftItemStack.asNMSCopy(event.getItem()).getItem();
/*     */         }
/*     */         
/*     */ 
/* 323 */         if (itembucket.a(isourceblock.getWorld(), blockposition))
/*     */         {
/* 325 */           Item item = Items.BUCKET;
/* 326 */           if (--itemstack.count == 0) {
/* 327 */             itemstack.setItem(Items.BUCKET);
/* 328 */             itemstack.count = 1;
/* 329 */           } else if (((TileEntityDispenser)isourceblock.getTileEntity()).addItem(new ItemStack(item)) < 0) {
/* 330 */             this.b.a(isourceblock, new ItemStack(item));
/*     */           }
/*     */           
/* 333 */           return itemstack;
/*     */         }
/* 335 */         return this.b.a(isourceblock, itemstack);
/*     */       }
/*     */       
/*     */ 
/* 339 */     };
/* 340 */     BlockDispenser.REGISTRY.a(Items.LAVA_BUCKET, dispensebehavioritem);
/* 341 */     BlockDispenser.REGISTRY.a(Items.WATER_BUCKET, dispensebehavioritem);
/* 342 */     BlockDispenser.REGISTRY.a(Items.BUCKET, new DispenseBehaviorItem() {
/* 343 */       private final DispenseBehaviorItem b = new DispenseBehaviorItem();
/*     */       
/*     */       public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 346 */         World world = isourceblock.getWorld();
/* 347 */         BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));
/* 348 */         IBlockData iblockdata = world.getType(blockposition);
/* 349 */         Block block = iblockdata.getBlock();
/* 350 */         Material material = block.getMaterial();
/*     */         Item item;
/*     */         Item item;
/* 353 */         if ((Material.WATER.equals(material)) && ((block instanceof BlockFluids)) && (((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() == 0)) {
/* 354 */           item = Items.WATER_BUCKET;
/*     */         } else {
/* 356 */           if ((!Material.LAVA.equals(material)) || (!(block instanceof BlockFluids)) || (((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() != 0)) {
/* 357 */             return super.b(isourceblock, itemstack);
/*     */           }
/*     */           
/* 360 */           item = Items.LAVA_BUCKET;
/*     */         }
/*     */         
/*     */ 
/* 364 */         org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
/* 365 */         CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
/*     */         
/* 367 */         BlockDispenseEvent event = new BlockDispenseEvent(bukkitBlock, craftItem.clone(), new Vector(blockposition.getX(), blockposition.getY(), blockposition.getZ()));
/* 368 */         if (!BlockDispenser.eventFired) {
/* 369 */           world.getServer().getPluginManager().callEvent(event);
/*     */         }
/*     */         
/* 372 */         if (event.isCancelled()) {
/* 373 */           return itemstack;
/*     */         }
/*     */         
/* 376 */         if (!event.getItem().equals(craftItem))
/*     */         {
/* 378 */           ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 379 */           IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(eventStack.getItem());
/* 380 */           if ((idispensebehavior != IDispenseBehavior.NONE) && (idispensebehavior != this)) {
/* 381 */             idispensebehavior.a(isourceblock, eventStack);
/* 382 */             return itemstack;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 387 */         world.setAir(blockposition);
/* 388 */         if (--itemstack.count == 0) {
/* 389 */           itemstack.setItem(item);
/* 390 */           itemstack.count = 1;
/* 391 */         } else if (((TileEntityDispenser)isourceblock.getTileEntity()).addItem(new ItemStack(item)) < 0) {
/* 392 */           this.b.a(isourceblock, new ItemStack(item));
/*     */         }
/*     */         
/* 395 */         return itemstack;
/*     */       }
/* 397 */     });
/* 398 */     BlockDispenser.REGISTRY.a(Items.FLINT_AND_STEEL, new DispenseBehaviorItem() {
/* 399 */       private boolean b = true;
/*     */       
/*     */       protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 402 */         World world = isourceblock.getWorld();
/* 403 */         BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));
/*     */         
/*     */ 
/* 406 */         org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
/* 407 */         CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
/*     */         
/* 409 */         BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(0, 0, 0));
/* 410 */         if (!BlockDispenser.eventFired) {
/* 411 */           world.getServer().getPluginManager().callEvent(event);
/*     */         }
/*     */         
/* 414 */         if (event.isCancelled()) {
/* 415 */           return itemstack;
/*     */         }
/*     */         
/* 418 */         if (!event.getItem().equals(craftItem))
/*     */         {
/* 420 */           ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 421 */           IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(eventStack.getItem());
/* 422 */           if ((idispensebehavior != IDispenseBehavior.NONE) && (idispensebehavior != this)) {
/* 423 */             idispensebehavior.a(isourceblock, eventStack);
/* 424 */             return itemstack;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 429 */         if (world.isEmpty(blockposition))
/*     */         {
/* 431 */           if (!org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callBlockIgniteEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ()).isCancelled()) {
/* 432 */             world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
/* 433 */             if (itemstack.isDamaged(1, world.random)) {
/* 434 */               itemstack.count = 0;
/*     */             }
/*     */           }
/*     */         }
/* 438 */         else if (world.getType(blockposition).getBlock() == Blocks.TNT) {
/* 439 */           Blocks.TNT.postBreak(world, blockposition, Blocks.TNT.getBlockData().set(BlockTNT.EXPLODE, Boolean.valueOf(true)));
/* 440 */           world.setAir(blockposition);
/*     */         } else {
/* 442 */           this.b = false;
/*     */         }
/*     */         
/* 445 */         return itemstack;
/*     */       }
/*     */       
/*     */       protected void a(ISourceBlock isourceblock) {
/* 449 */         if (this.b) {
/* 450 */           isourceblock.getWorld().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
/*     */         } else {
/* 452 */           isourceblock.getWorld().triggerEffect(1001, isourceblock.getBlockPosition(), 0);
/*     */         }
/*     */         
/*     */       }
/* 456 */     });
/* 457 */     BlockDispenser.REGISTRY.a(Items.DYE, new DispenseBehaviorItem() {
/* 458 */       private boolean b = true;
/*     */       
/*     */       protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 461 */         if (EnumColor.WHITE == EnumColor.fromInvColorIndex(itemstack.getData())) {
/* 462 */           World world = isourceblock.getWorld();
/* 463 */           BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));
/*     */           
/*     */ 
/* 466 */           org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
/* 467 */           CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
/*     */           
/* 469 */           BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(0, 0, 0));
/* 470 */           if (!BlockDispenser.eventFired) {
/* 471 */             world.getServer().getPluginManager().callEvent(event);
/*     */           }
/*     */           
/* 474 */           if (event.isCancelled()) {
/* 475 */             return itemstack;
/*     */           }
/*     */           
/* 478 */           if (!event.getItem().equals(craftItem))
/*     */           {
/* 480 */             ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 481 */             IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(eventStack.getItem());
/* 482 */             if ((idispensebehavior != IDispenseBehavior.NONE) && (idispensebehavior != this)) {
/* 483 */               idispensebehavior.a(isourceblock, eventStack);
/* 484 */               return itemstack;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 489 */           if (ItemDye.a(itemstack, world, blockposition)) {
/* 490 */             if (!world.isClientSide) {
/* 491 */               world.triggerEffect(2005, blockposition, 0);
/*     */             }
/*     */           } else {
/* 494 */             this.b = false;
/*     */           }
/*     */           
/* 497 */           return itemstack;
/*     */         }
/* 499 */         return super.b(isourceblock, itemstack);
/*     */       }
/*     */       
/*     */       protected void a(ISourceBlock isourceblock)
/*     */       {
/* 504 */         if (this.b) {
/* 505 */           isourceblock.getWorld().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
/*     */         } else {
/* 507 */           isourceblock.getWorld().triggerEffect(1001, isourceblock.getBlockPosition(), 0);
/*     */         }
/*     */         
/*     */       }
/* 511 */     });
/* 512 */     BlockDispenser.REGISTRY.a(Item.getItemOf(Blocks.TNT), new DispenseBehaviorItem() {
/*     */       protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 514 */         World world = isourceblock.getWorld();
/* 515 */         BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));
/*     */         
/*     */ 
/*     */ 
/* 519 */         ItemStack itemstack1 = itemstack.cloneAndSubtract(1);
/* 520 */         org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
/* 521 */         CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*     */         
/* 523 */         BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D));
/* 524 */         if (!BlockDispenser.eventFired) {
/* 525 */           world.getServer().getPluginManager().callEvent(event);
/*     */         }
/*     */         
/* 528 */         if (event.isCancelled()) {
/* 529 */           itemstack.count += 1;
/* 530 */           return itemstack;
/*     */         }
/*     */         
/* 533 */         if (!event.getItem().equals(craftItem)) {
/* 534 */           itemstack.count += 1;
/*     */           
/* 536 */           ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/* 537 */           IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(eventStack.getItem());
/* 538 */           if ((idispensebehavior != IDispenseBehavior.NONE) && (idispensebehavior != this)) {
/* 539 */             idispensebehavior.a(isourceblock, eventStack);
/* 540 */             return itemstack;
/*     */           }
/*     */         }
/*     */         
/* 544 */         EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), null);
/*     */         
/*     */ 
/* 547 */         world.addEntity(entitytntprimed);
/* 548 */         world.makeSound(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
/*     */         
/* 550 */         return itemstack;
/*     */       }
/* 552 */     });
/* 553 */     BlockDispenser.REGISTRY.a(Items.SKULL, new DispenseBehaviorItem() {
/* 554 */       private boolean b = true;
/*     */       
/*     */       protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 557 */         World world = isourceblock.getWorld();
/* 558 */         EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
/* 559 */         BlockPosition blockposition = isourceblock.getBlockPosition().shift(enumdirection);
/* 560 */         BlockSkull blockskull = Blocks.SKULL;
/*     */         
/* 562 */         if ((world.isEmpty(blockposition)) && (blockskull.b(world, blockposition, itemstack))) {
/* 563 */           if (!world.isClientSide) {
/* 564 */             world.setTypeAndData(blockposition, blockskull.getBlockData().set(BlockSkull.FACING, EnumDirection.UP), 3);
/* 565 */             TileEntity tileentity = world.getTileEntity(blockposition);
/*     */             
/* 567 */             if ((tileentity instanceof TileEntitySkull)) {
/* 568 */               if (itemstack.getData() == 3) {
/* 569 */                 GameProfile gameprofile = null;
/*     */                 
/* 571 */                 if (itemstack.hasTag()) {
/* 572 */                   NBTTagCompound nbttagcompound = itemstack.getTag();
/*     */                   
/* 574 */                   if (nbttagcompound.hasKeyOfType("SkullOwner", 10)) {
/* 575 */                     gameprofile = GameProfileSerializer.deserialize(nbttagcompound.getCompound("SkullOwner"));
/* 576 */                   } else if (nbttagcompound.hasKeyOfType("SkullOwner", 8)) {
/* 577 */                     String s = nbttagcompound.getString("SkullOwner");
/*     */                     
/* 579 */                     if (!UtilColor.b(s)) {
/* 580 */                       gameprofile = new GameProfile(null, s);
/*     */                     }
/*     */                   }
/*     */                 }
/*     */                 
/* 585 */                 ((TileEntitySkull)tileentity).setGameProfile(gameprofile);
/*     */               } else {
/* 587 */                 ((TileEntitySkull)tileentity).setSkullType(itemstack.getData());
/*     */               }
/*     */               
/* 590 */               ((TileEntitySkull)tileentity).setRotation(enumdirection.opposite().b() * 4);
/* 591 */               Blocks.SKULL.a(world, blockposition, (TileEntitySkull)tileentity);
/*     */             }
/*     */             
/* 594 */             itemstack.count -= 1;
/*     */           }
/*     */         } else {
/* 597 */           this.b = false;
/*     */         }
/*     */         
/* 600 */         return itemstack;
/*     */       }
/*     */       
/*     */       protected void a(ISourceBlock isourceblock) {
/* 604 */         if (this.b) {
/* 605 */           isourceblock.getWorld().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
/*     */         } else {
/* 607 */           isourceblock.getWorld().triggerEffect(1001, isourceblock.getBlockPosition(), 0);
/*     */         }
/*     */         
/*     */       }
/* 611 */     });
/* 612 */     BlockDispenser.REGISTRY.a(Item.getItemOf(Blocks.PUMPKIN), new DispenseBehaviorItem() {
/* 613 */       private boolean b = true;
/*     */       
/*     */       protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/* 616 */         World world = isourceblock.getWorld();
/* 617 */         BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));
/* 618 */         BlockPumpkin blockpumpkin = (BlockPumpkin)Blocks.PUMPKIN;
/*     */         
/* 620 */         if ((world.isEmpty(blockposition)) && (blockpumpkin.e(world, blockposition))) {
/* 621 */           if (!world.isClientSide) {
/* 622 */             world.setTypeAndData(blockposition, blockpumpkin.getBlockData(), 3);
/*     */           }
/*     */           
/* 625 */           itemstack.count -= 1;
/*     */         } else {
/* 627 */           this.b = false;
/*     */         }
/*     */         
/* 630 */         return itemstack;
/*     */       }
/*     */       
/*     */       protected void a(ISourceBlock isourceblock) {
/* 634 */         if (this.b) {
/* 635 */           isourceblock.getWorld().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
/*     */         } else {
/* 637 */           isourceblock.getWorld().triggerEffect(1001, isourceblock.getBlockPosition(), 0);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public static void c()
/*     */   {
/* 645 */     if (!b) {
/* 646 */       b = true;
/* 647 */       if (c.isDebugEnabled()) {
/* 648 */         d();
/*     */       }
/*     */       
/* 651 */       Block.S();
/* 652 */       BlockFire.l();
/* 653 */       Item.t();
/* 654 */       StatisticList.a();
/* 655 */       b();
/*     */     }
/*     */   }
/*     */   
/*     */   private static void d() {
/* 660 */     System.setErr(new RedirectStream("STDERR", System.err));
/* 661 */     System.setOut(new RedirectStream("STDOUT", a));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\DispenserRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */