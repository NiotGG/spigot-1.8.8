/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.world.PortalCreateEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockPortal extends BlockHalfTransparent
/*     */ {
/*  11 */   public static final BlockStateEnum<EnumDirection.EnumAxis> AXIS = BlockStateEnum.of("axis", EnumDirection.EnumAxis.class, new EnumDirection.EnumAxis[] { EnumDirection.EnumAxis.X, EnumDirection.EnumAxis.Z });
/*     */   
/*     */   public BlockPortal() {
/*  14 */     super(Material.PORTAL, false);
/*  15 */     j(this.blockStateList.getBlockData().set(AXIS, EnumDirection.EnumAxis.X));
/*  16 */     a(true);
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  20 */     super.b(world, blockposition, iblockdata, random);
/*  21 */     if ((world.spigotConfig.enableZombiePigmenPortalSpawns) && (world.worldProvider.d()) && (world.getGameRules().getBoolean("doMobSpawning")) && (random.nextInt(2000) < world.getDifficulty().a())) {
/*  22 */       int i = blockposition.getY();
/*     */       
/*     */ 
/*     */ 
/*  26 */       for (BlockPosition blockposition1 = blockposition; (!World.a(world, blockposition1)) && (blockposition1.getY() > 0); blockposition1 = blockposition1.down()) {}
/*     */       
/*     */ 
/*     */ 
/*  30 */       if ((i > 0) && (!world.getType(blockposition1.up()).getBlock().isOccluding()))
/*     */       {
/*  32 */         Entity entity = ItemMonsterEgg.spawnCreature(world, 57, blockposition1.getX() + 0.5D, blockposition1.getY() + 1.1D, blockposition1.getZ() + 0.5D, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.NETHER_PORTAL);
/*     */         
/*  34 */         if (entity != null) {
/*  35 */           entity.portalCooldown = entity.aq();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  43 */     return null;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  47 */     EnumDirection.EnumAxis enumdirection_enumaxis = (EnumDirection.EnumAxis)iblockaccess.getType(blockposition).get(AXIS);
/*  48 */     float f = 0.125F;
/*  49 */     float f1 = 0.125F;
/*     */     
/*  51 */     if (enumdirection_enumaxis == EnumDirection.EnumAxis.X) {
/*  52 */       f = 0.5F;
/*     */     }
/*     */     
/*  55 */     if (enumdirection_enumaxis == EnumDirection.EnumAxis.Z) {
/*  56 */       f1 = 0.5F;
/*     */     }
/*     */     
/*  59 */     a(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
/*     */   }
/*     */   
/*     */   public static int a(EnumDirection.EnumAxis enumdirection_enumaxis) {
/*  63 */     return enumdirection_enumaxis == EnumDirection.EnumAxis.Z ? 2 : enumdirection_enumaxis == EnumDirection.EnumAxis.X ? 1 : 0;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  67 */     return false;
/*     */   }
/*     */   
/*     */   public boolean e(World world, BlockPosition blockposition) {
/*  71 */     Shape blockportal_shape = new Shape(world, blockposition, EnumDirection.EnumAxis.X);
/*     */     
/*  73 */     if ((blockportal_shape.d()) && (blockportal_shape.e == 0))
/*     */     {
/*  75 */       return blockportal_shape.createPortal();
/*     */     }
/*     */     
/*  78 */     Shape blockportal_shape1 = new Shape(world, blockposition, EnumDirection.EnumAxis.Z);
/*     */     
/*  80 */     if ((blockportal_shape1.d()) && (blockportal_shape1.e == 0)) {
/*  81 */       return blockportal_shape1.createPortal();
/*     */     }
/*     */     
/*     */ 
/*  85 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/*  91 */     EnumDirection.EnumAxis enumdirection_enumaxis = (EnumDirection.EnumAxis)iblockdata.get(AXIS);
/*     */     
/*     */ 
/*  94 */     if (enumdirection_enumaxis == EnumDirection.EnumAxis.X) {
/*  95 */       Shape blockportal_shape = new Shape(world, blockposition, EnumDirection.EnumAxis.X);
/*  96 */       if ((!blockportal_shape.d()) || (blockportal_shape.e < blockportal_shape.width * blockportal_shape.height)) {
/*  97 */         world.setTypeUpdate(blockposition, Blocks.AIR.getBlockData());
/*     */       }
/*  99 */     } else if (enumdirection_enumaxis == EnumDirection.EnumAxis.Z) {
/* 100 */       Shape blockportal_shape = new Shape(world, blockposition, EnumDirection.EnumAxis.Z);
/* 101 */       if ((!blockportal_shape.d()) || (blockportal_shape.e < blockportal_shape.width * blockportal_shape.height)) {
/* 102 */         world.setTypeUpdate(blockposition, Blocks.AIR.getBlockData());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int a(Random random)
/*     */   {
/* 109 */     return 0;
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
/* 113 */     if ((entity.vehicle == null) && (entity.passenger == null))
/*     */     {
/* 115 */       org.bukkit.event.entity.EntityPortalEnterEvent event = new org.bukkit.event.entity.EntityPortalEnterEvent(entity.getBukkitEntity(), new org.bukkit.Location(world.getWorld(), blockposition.getX(), blockposition.getY(), blockposition.getZ()));
/* 116 */       world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 118 */       entity.d(blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i)
/*     */   {
/* 124 */     return getBlockData().set(AXIS, (i & 0x3) == 2 ? EnumDirection.EnumAxis.Z : EnumDirection.EnumAxis.X);
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 128 */     return a((EnumDirection.EnumAxis)iblockdata.get(AXIS));
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 132 */     return new BlockStateList(this, new IBlockState[] { AXIS });
/*     */   }
/*     */   
/*     */   public ShapeDetector.ShapeDetectorCollection f(World world, BlockPosition blockposition) {
/* 136 */     EnumDirection.EnumAxis enumdirection_enumaxis = EnumDirection.EnumAxis.Z;
/* 137 */     Shape blockportal_shape = new Shape(world, blockposition, EnumDirection.EnumAxis.X);
/* 138 */     com.google.common.cache.LoadingCache loadingcache = ShapeDetector.a(world, true);
/*     */     
/* 140 */     if (!blockportal_shape.d()) {
/* 141 */       enumdirection_enumaxis = EnumDirection.EnumAxis.X;
/* 142 */       blockportal_shape = new Shape(world, blockposition, EnumDirection.EnumAxis.Z);
/*     */     }
/*     */     
/* 145 */     if (!blockportal_shape.d()) {
/* 146 */       return new ShapeDetector.ShapeDetectorCollection(blockposition, EnumDirection.NORTH, EnumDirection.UP, loadingcache, 1, 1, 1);
/*     */     }
/* 148 */     int[] aint = new int[EnumDirection.EnumAxisDirection.values().length];
/* 149 */     EnumDirection enumdirection = blockportal_shape.c.f();
/* 150 */     BlockPosition blockposition1 = blockportal_shape.position.up(blockportal_shape.a() - 1);
/* 151 */     EnumDirection.EnumAxisDirection[] aenumdirection_enumaxisdirection = EnumDirection.EnumAxisDirection.values();
/* 152 */     int i = aenumdirection_enumaxisdirection.length;
/*     */     
/*     */ 
/*     */ 
/* 156 */     for (int j = 0; j < i; j++) {
/* 157 */       EnumDirection.EnumAxisDirection enumdirection_enumaxisdirection = aenumdirection_enumaxisdirection[j];
/* 158 */       ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection = new ShapeDetector.ShapeDetectorCollection(enumdirection.c() == enumdirection_enumaxisdirection ? blockposition1 : blockposition1.shift(blockportal_shape.c, blockportal_shape.b() - 1), EnumDirection.a(enumdirection_enumaxisdirection, enumdirection_enumaxis), EnumDirection.UP, loadingcache, blockportal_shape.b(), blockportal_shape.a(), 1);
/*     */       
/* 160 */       for (int k = 0; k < blockportal_shape.b(); k++) {
/* 161 */         for (int l = 0; l < blockportal_shape.a(); l++) {
/* 162 */           ShapeDetectorBlock shapedetectorblock = shapedetector_shapedetectorcollection.a(k, l, 1);
/*     */           
/* 164 */           if ((shapedetectorblock.a() != null) && (shapedetectorblock.a().getBlock().getMaterial() != Material.AIR)) {
/* 165 */             aint[enumdirection_enumaxisdirection.ordinal()] += 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 171 */     EnumDirection.EnumAxisDirection enumdirection_enumaxisdirection1 = EnumDirection.EnumAxisDirection.POSITIVE;
/* 172 */     EnumDirection.EnumAxisDirection[] aenumdirection_enumaxisdirection1 = EnumDirection.EnumAxisDirection.values();
/*     */     
/* 174 */     j = aenumdirection_enumaxisdirection1.length;
/*     */     
/* 176 */     for (int i1 = 0; i1 < j; i1++) {
/* 177 */       EnumDirection.EnumAxisDirection enumdirection_enumaxisdirection2 = aenumdirection_enumaxisdirection1[i1];
/*     */       
/* 179 */       if (aint[enumdirection_enumaxisdirection2.ordinal()] < aint[enumdirection_enumaxisdirection1.ordinal()]) {
/* 180 */         enumdirection_enumaxisdirection1 = enumdirection_enumaxisdirection2;
/*     */       }
/*     */     }
/*     */     
/* 184 */     return new ShapeDetector.ShapeDetectorCollection(enumdirection.c() == enumdirection_enumaxisdirection1 ? blockposition1 : blockposition1.shift(blockportal_shape.c, blockportal_shape.b() - 1), EnumDirection.a(enumdirection_enumaxisdirection1, enumdirection_enumaxis), EnumDirection.UP, loadingcache, blockportal_shape.b(), blockportal_shape.a(), 1);
/*     */   }
/*     */   
/*     */ 
/*     */   public static class Shape
/*     */   {
/*     */     private final World a;
/*     */     private final EnumDirection.EnumAxis b;
/*     */     private final EnumDirection c;
/*     */     private final EnumDirection d;
/* 194 */     private int e = 0;
/*     */     private BlockPosition position;
/*     */     private int height;
/*     */     private int width;
/* 198 */     Collection<org.bukkit.block.Block> blocks = new java.util.HashSet();
/*     */     
/*     */     public Shape(World world, BlockPosition blockposition, EnumDirection.EnumAxis enumdirection_enumaxis) {
/* 201 */       this.a = world;
/* 202 */       this.b = enumdirection_enumaxis;
/* 203 */       if (enumdirection_enumaxis == EnumDirection.EnumAxis.X) {
/* 204 */         this.d = EnumDirection.EAST;
/* 205 */         this.c = EnumDirection.WEST;
/*     */       } else {
/* 207 */         this.d = EnumDirection.NORTH;
/* 208 */         this.c = EnumDirection.SOUTH;
/*     */       }
/*     */       
/* 211 */       for (BlockPosition blockposition1 = blockposition; (blockposition.getY() > blockposition1.getY() - 21) && (blockposition.getY() > 0) && (a(world.getType(blockposition.down()).getBlock())); blockposition = blockposition.down()) {}
/*     */       
/*     */ 
/*     */ 
/* 215 */       int i = a(blockposition, this.d) - 1;
/*     */       
/* 217 */       if (i >= 0) {
/* 218 */         this.position = blockposition.shift(this.d, i);
/* 219 */         this.width = a(this.position, this.c);
/* 220 */         if ((this.width < 2) || (this.width > 21)) {
/* 221 */           this.position = null;
/* 222 */           this.width = 0;
/*     */         }
/*     */       }
/*     */       
/* 226 */       if (this.position != null) {
/* 227 */         this.height = c();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     protected int a(BlockPosition blockposition, EnumDirection enumdirection)
/*     */     {
/* 235 */       for (int i = 0; i < 22; i++) {
/* 236 */         BlockPosition blockposition1 = blockposition.shift(enumdirection, i);
/*     */         
/* 238 */         if ((!a(this.a.getType(blockposition1).getBlock())) || (this.a.getType(blockposition1.down()).getBlock() != Blocks.OBSIDIAN)) {
/*     */           break;
/*     */         }
/*     */       }
/*     */       
/* 243 */       Block block = this.a.getType(blockposition.shift(enumdirection, i)).getBlock();
/*     */       
/* 245 */       return block == Blocks.OBSIDIAN ? i : 0;
/*     */     }
/*     */     
/*     */     public int a() {
/* 249 */       return this.height;
/*     */     }
/*     */     
/*     */     public int b() {
/* 253 */       return this.width;
/*     */     }
/*     */     
/*     */     protected int c()
/*     */     {
/* 258 */       this.blocks.clear();
/* 259 */       org.bukkit.World bworld = this.a.getWorld();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 264 */       for (this.height = 0; this.height < 21; this.height += 1) {
/* 265 */         for (int i = 0; i < this.width; i++) {
/* 266 */           BlockPosition blockposition = this.position.shift(this.c, i).up(this.height);
/* 267 */           Block block = this.a.getType(blockposition).getBlock();
/*     */           
/* 269 */           if (!a(block)) {
/*     */             break;
/*     */           }
/*     */           
/* 273 */           if (block == Blocks.PORTAL) {
/* 274 */             this.e += 1;
/*     */           }
/*     */           
/* 277 */           if (i == 0) {
/* 278 */             block = this.a.getType(blockposition.shift(this.d)).getBlock();
/* 279 */             if (block != Blocks.OBSIDIAN) {
/*     */               break;
/*     */             }
/*     */             
/* 283 */             BlockPosition pos = blockposition.shift(this.d);
/* 284 */             this.blocks.add(bworld.getBlockAt(pos.getX(), pos.getY(), pos.getZ()));
/*     */ 
/*     */           }
/* 287 */           else if (i == this.width - 1) {
/* 288 */             block = this.a.getType(blockposition.shift(this.c)).getBlock();
/* 289 */             if (block != Blocks.OBSIDIAN) {
/*     */               break;
/*     */             }
/*     */             
/* 293 */             BlockPosition pos = blockposition.shift(this.c);
/* 294 */             this.blocks.add(bworld.getBlockAt(pos.getX(), pos.getY(), pos.getZ()));
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 301 */       for (int i = 0; i < this.width; i++) {
/* 302 */         if (this.a.getType(this.position.shift(this.c, i).up(this.height)).getBlock() != Blocks.OBSIDIAN) {
/* 303 */           this.height = 0;
/* 304 */           break;
/*     */         }
/*     */         
/* 307 */         BlockPosition pos = this.position.shift(this.c, i).up(this.height);
/* 308 */         this.blocks.add(bworld.getBlockAt(pos.getX(), pos.getY(), pos.getZ()));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 313 */       if ((this.height <= 21) && (this.height >= 3)) {
/* 314 */         return this.height;
/*     */       }
/* 316 */       this.position = null;
/* 317 */       this.width = 0;
/* 318 */       this.height = 0;
/* 319 */       return 0;
/*     */     }
/*     */     
/*     */     protected boolean a(Block block)
/*     */     {
/* 324 */       return (block.material == Material.AIR) || (block == Blocks.FIRE) || (block == Blocks.PORTAL);
/*     */     }
/*     */     
/*     */     public boolean d() {
/* 328 */       return (this.position != null) && (this.width >= 2) && (this.width <= 21) && (this.height >= 3) && (this.height <= 21);
/*     */     }
/*     */     
/*     */     public boolean createPortal()
/*     */     {
/* 333 */       org.bukkit.World bworld = this.a.getWorld();
/*     */       
/*     */ 
/* 336 */       for (int i = 0; i < this.width; i++) {
/* 337 */         BlockPosition blockposition = this.position.shift(this.c, i);
/*     */         
/* 339 */         for (int j = 0; j < this.height; j++) {
/* 340 */           BlockPosition pos = blockposition.up(j);
/* 341 */           this.blocks.add(bworld.getBlockAt(pos.getX(), pos.getY(), pos.getZ()));
/*     */         }
/*     */       }
/*     */       
/* 345 */       PortalCreateEvent event = new PortalCreateEvent(this.blocks, bworld, org.bukkit.event.world.PortalCreateEvent.CreateReason.FIRE);
/* 346 */       this.a.getServer().getPluginManager().callEvent(event);
/*     */       
/* 348 */       if (event.isCancelled()) {
/* 349 */         return false;
/*     */       }
/*     */       
/* 352 */       for (int i = 0; i < this.width; i++) {
/* 353 */         BlockPosition blockposition = this.position.shift(this.c, i);
/*     */         
/* 355 */         for (int j = 0; j < this.height; j++) {
/* 356 */           this.a.setTypeAndData(blockposition.up(j), Blocks.PORTAL.getBlockData().set(BlockPortal.AXIS, this.b), 2);
/*     */         }
/*     */       }
/*     */       
/* 360 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPortal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */