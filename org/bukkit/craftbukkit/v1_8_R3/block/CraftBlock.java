/*     */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_8_R3.BiomeBase;
/*     */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*     */ import net.minecraft.server.v1_8_R3.BlockRedstoneWire;
/*     */ import net.minecraft.server.v1_8_R3.Blocks;
/*     */ import net.minecraft.server.v1_8_R3.EnumDirection;
/*     */ import net.minecraft.server.v1_8_R3.EnumSkyBlock;
/*     */ import net.minecraft.server.v1_8_R3.IBlockData;
/*     */ import net.minecraft.server.v1_8_R3.Item;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import net.minecraft.server.v1_8_R3.TileEntitySkull;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.block.Biome;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.block.PistonMoveReaction;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.metadata.BlockMetadataStore;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.util.BlockVector;
/*     */ 
/*     */ public class CraftBlock implements org.bukkit.block.Block
/*     */ {
/*     */   private final CraftChunk chunk;
/*     */   private final int x;
/*     */   private final int y;
/*     */   private final int z;
/*     */   
/*     */   public CraftBlock(CraftChunk chunk, int x, int y, int z)
/*     */   {
/*  36 */     this.x = x;
/*  37 */     this.y = y;
/*  38 */     this.z = z;
/*  39 */     this.chunk = chunk;
/*     */   }
/*     */   
/*     */   private net.minecraft.server.v1_8_R3.Block getNMSBlock() {
/*  43 */     return CraftMagicNumbers.getBlock(this);
/*     */   }
/*     */   
/*     */   private static net.minecraft.server.v1_8_R3.Block getNMSBlock(int type) {
/*  47 */     return CraftMagicNumbers.getBlock(type);
/*     */   }
/*     */   
/*     */   public org.bukkit.World getWorld() {
/*  51 */     return this.chunk.getWorld();
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/*  55 */     return new Location(getWorld(), this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public Location getLocation(Location loc) {
/*  59 */     if (loc != null) {
/*  60 */       loc.setWorld(getWorld());
/*  61 */       loc.setX(this.x);
/*  62 */       loc.setY(this.y);
/*  63 */       loc.setZ(this.z);
/*  64 */       loc.setYaw(0.0F);
/*  65 */       loc.setPitch(0.0F);
/*     */     }
/*     */     
/*  68 */     return loc;
/*     */   }
/*     */   
/*     */   public BlockVector getVector() {
/*  72 */     return new BlockVector(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public int getX() {
/*  76 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  80 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getZ() {
/*  84 */     return this.z;
/*     */   }
/*     */   
/*     */   public org.bukkit.Chunk getChunk() {
/*  88 */     return this.chunk;
/*     */   }
/*     */   
/*     */   public void setData(byte data) {
/*  92 */     setData(data, 3);
/*     */   }
/*     */   
/*     */   public void setData(byte data, boolean applyPhysics) {
/*  96 */     if (applyPhysics) {
/*  97 */       setData(data, 3);
/*     */     } else {
/*  99 */       setData(data, 2);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setData(byte data, int flag) {
/* 104 */     net.minecraft.server.v1_8_R3.World world = this.chunk.getHandle().getWorld();
/* 105 */     BlockPosition position = new BlockPosition(this.x, this.y, this.z);
/* 106 */     IBlockData blockData = world.getType(position);
/* 107 */     world.setTypeAndData(position, blockData.getBlock().fromLegacyData(data), flag);
/*     */   }
/*     */   
/*     */   public byte getData() {
/* 111 */     IBlockData blockData = this.chunk.getHandle().getBlockData(new BlockPosition(this.x, this.y, this.z));
/* 112 */     return (byte)blockData.getBlock().toLegacyData(blockData);
/*     */   }
/*     */   
/*     */   public void setType(org.bukkit.Material type) {
/* 116 */     setType(type, true);
/*     */   }
/*     */   
/*     */   public void setType(org.bukkit.Material type, boolean applyPhysics)
/*     */   {
/* 121 */     setTypeId(type.getId(), applyPhysics);
/*     */   }
/*     */   
/*     */   public boolean setTypeId(int type) {
/* 125 */     return setTypeId(type, true);
/*     */   }
/*     */   
/*     */   public boolean setTypeId(int type, boolean applyPhysics) {
/* 129 */     net.minecraft.server.v1_8_R3.Block block = getNMSBlock(type);
/* 130 */     return setTypeIdAndData(type, (byte)block.toLegacyData(block.getBlockData()), applyPhysics);
/*     */   }
/*     */   
/*     */   public boolean setTypeIdAndData(int type, byte data, boolean applyPhysics) {
/* 134 */     IBlockData blockData = getNMSBlock(type).fromLegacyData(data);
/* 135 */     BlockPosition position = new BlockPosition(this.x, this.y, this.z);
/* 136 */     if (applyPhysics) {
/* 137 */       return this.chunk.getHandle().getWorld().setTypeAndData(position, blockData, 3);
/*     */     }
/* 139 */     boolean success = this.chunk.getHandle().getWorld().setTypeAndData(position, blockData, 2);
/* 140 */     if (success) {
/* 141 */       this.chunk.getHandle().getWorld().notify(position);
/*     */     }
/* 143 */     return success;
/*     */   }
/*     */   
/*     */   public org.bukkit.Material getType()
/*     */   {
/* 148 */     return org.bukkit.Material.getMaterial(getTypeId());
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int getTypeId()
/*     */   {
/* 154 */     return CraftMagicNumbers.getId(this.chunk.getHandle().getType(new BlockPosition(this.x, this.y, this.z)));
/*     */   }
/*     */   
/*     */   public byte getLightLevel() {
/* 158 */     return (byte)this.chunk.getHandle().getWorld().getLightLevel(new BlockPosition(this.x, this.y, this.z));
/*     */   }
/*     */   
/*     */   public byte getLightFromSky() {
/* 162 */     return (byte)this.chunk.getHandle().getBrightness(EnumSkyBlock.SKY, new BlockPosition(this.x, this.y, this.z));
/*     */   }
/*     */   
/*     */   public byte getLightFromBlocks() {
/* 166 */     return (byte)this.chunk.getHandle().getBrightness(EnumSkyBlock.BLOCK, new BlockPosition(this.x, this.y, this.z));
/*     */   }
/*     */   
/*     */   public org.bukkit.block.Block getFace(BlockFace face)
/*     */   {
/* 171 */     return getRelative(face, 1);
/*     */   }
/*     */   
/*     */   public org.bukkit.block.Block getFace(BlockFace face, int distance) {
/* 175 */     return getRelative(face, distance);
/*     */   }
/*     */   
/*     */   public org.bukkit.block.Block getRelative(int modX, int modY, int modZ) {
/* 179 */     return getWorld().getBlockAt(getX() + modX, getY() + modY, getZ() + modZ);
/*     */   }
/*     */   
/*     */   public org.bukkit.block.Block getRelative(BlockFace face) {
/* 183 */     return getRelative(face, 1);
/*     */   }
/*     */   
/*     */   public org.bukkit.block.Block getRelative(BlockFace face, int distance) {
/* 187 */     return getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);
/*     */   }
/*     */   
/*     */   public BlockFace getFace(org.bukkit.block.Block block) {
/* 191 */     BlockFace[] values = BlockFace.values();
/*     */     BlockFace[] arrayOfBlockFace1;
/* 193 */     int i = (arrayOfBlockFace1 = values).length; for (int j = 0; j < i; j++) { BlockFace face = arrayOfBlockFace1[j];
/* 194 */       if ((getX() + face.getModX() == block.getX()) && 
/* 195 */         (getY() + face.getModY() == block.getY()) && 
/* 196 */         (getZ() + face.getModZ() == block.getZ()))
/*     */       {
/* 198 */         return face;
/*     */       }
/*     */     }
/*     */     
/* 202 */     return null;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 207 */     return "CraftBlock{chunk=" + this.chunk + ",x=" + this.x + ",y=" + this.y + ",z=" + this.z + ",type=" + getType() + ",data=" + getData() + '}';
/*     */   }
/*     */   
/*     */   public static BlockFace notchToBlockFace(EnumDirection notch) {
/* 211 */     if (notch == null) return BlockFace.SELF;
/* 212 */     switch (notch) {
/*     */     case DOWN: 
/* 214 */       return BlockFace.DOWN;
/*     */     case EAST: 
/* 216 */       return BlockFace.UP;
/*     */     case NORTH: 
/* 218 */       return BlockFace.NORTH;
/*     */     case SOUTH: 
/* 220 */       return BlockFace.SOUTH;
/*     */     case UP: 
/* 222 */       return BlockFace.WEST;
/*     */     case WEST: 
/* 224 */       return BlockFace.EAST;
/*     */     }
/* 226 */     return BlockFace.SELF;
/*     */   }
/*     */   
/*     */   public static EnumDirection blockFaceToNotch(BlockFace face)
/*     */   {
/* 231 */     switch (face) {
/*     */     case NORTH_EAST: 
/* 233 */       return EnumDirection.DOWN;
/*     */     case NORTH: 
/* 235 */       return EnumDirection.UP;
/*     */     case DOWN: 
/* 237 */       return EnumDirection.NORTH;
/*     */     case EAST_NORTH_EAST: 
/* 239 */       return EnumDirection.SOUTH;
/*     */     case EAST_SOUTH_EAST: 
/* 241 */       return EnumDirection.WEST;
/*     */     case EAST: 
/* 243 */       return EnumDirection.EAST;
/*     */     }
/* 245 */     return null;
/*     */   }
/*     */   
/*     */   public org.bukkit.block.BlockState getState()
/*     */   {
/* 250 */     org.bukkit.Material material = getType();
/*     */     
/* 252 */     switch (material) {
/*     */     case COCOA: 
/*     */     case COOKED_CHICKEN: 
/*     */     case QUARTZ_STAIRS: 
/* 256 */       return new CraftSign(this);
/*     */     case CLAY: 
/*     */     case GOLD_BOOTS: 
/* 259 */       return new CraftChest(this);
/*     */     case COBBLESTONE_STAIRS: 
/*     */     case COBBLE_WALL: 
/* 262 */       return new CraftFurnace(this);
/*     */     case BLAZE_POWDER: 
/* 264 */       return new CraftDispenser(this);
/*     */     case GOLD_SWORD: 
/* 266 */       return new CraftDropper(this);
/*     */     case GOLD_PICKAXE: 
/* 268 */       return new CraftHopper(this);
/*     */     case CHAINMAIL_LEGGINGS: 
/* 270 */       return new CraftCreatureSpawner(this);
/*     */     case BOAT: 
/* 272 */       return new CraftNoteBlock(this);
/*     */     case DIAMOND_AXE: 
/* 274 */       return new CraftJukebox(this);
/*     */     case ENDER_STONE: 
/* 276 */       return new CraftBrewingStand(this);
/*     */     case GOLD_BARDING: 
/* 278 */       return new CraftSkull(this);
/*     */     case GLASS_BOTTLE: 
/* 280 */       return new CraftCommandBlock(this);
/*     */     case GLOWING_REDSTONE_ORE: 
/* 282 */       return new CraftBeacon(this);
/*     */     case IRON_DOOR: 
/*     */     case IRON_DOOR_BLOCK: 
/*     */     case WHEAT: 
/* 286 */       return new CraftBanner(this);
/*     */     }
/* 288 */     return new CraftBlockState(this);
/*     */   }
/*     */   
/*     */   public Biome getBiome()
/*     */   {
/* 293 */     return getWorld().getBiome(this.x, this.z);
/*     */   }
/*     */   
/*     */   public void setBiome(Biome bio) {
/* 297 */     getWorld().setBiome(this.x, this.z, bio);
/*     */   }
/*     */   
/*     */   public static Biome biomeBaseToBiome(BiomeBase base) {
/* 301 */     if (base == null) {
/* 302 */       return null;
/*     */     }
/*     */     
/* 305 */     return BIOME_MAPPING[base.id];
/*     */   }
/*     */   
/*     */   public static BiomeBase biomeToBiomeBase(Biome bio) {
/* 309 */     if (bio == null) {
/* 310 */       return null;
/*     */     }
/* 312 */     return BIOMEBASE_MAPPING[bio.ordinal()];
/*     */   }
/*     */   
/*     */   public double getTemperature() {
/* 316 */     return getWorld().getTemperature(this.x, this.z);
/*     */   }
/*     */   
/*     */   public double getHumidity() {
/* 320 */     return getWorld().getHumidity(this.x, this.z);
/*     */   }
/*     */   
/*     */   public boolean isBlockPowered() {
/* 324 */     return this.chunk.getHandle().getWorld().getBlockPower(new BlockPosition(this.x, this.y, this.z)) > 0;
/*     */   }
/*     */   
/*     */   public boolean isBlockIndirectlyPowered() {
/* 328 */     return this.chunk.getHandle().getWorld().isBlockIndirectlyPowered(new BlockPosition(this.x, this.y, this.z));
/*     */   }
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/* 333 */     if (o == this) return true;
/* 334 */     if (!(o instanceof CraftBlock)) return false;
/* 335 */     CraftBlock other = (CraftBlock)o;
/*     */     
/* 337 */     return (this.x == other.x) && (this.y == other.y) && (this.z == other.z) && (getWorld().equals(other.getWorld()));
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 342 */     return this.y << 24 ^ this.x ^ this.z ^ getWorld().hashCode();
/*     */   }
/*     */   
/*     */   public boolean isBlockFacePowered(BlockFace face) {
/* 346 */     return this.chunk.getHandle().getWorld().isBlockFacePowered(new BlockPosition(this.x, this.y, this.z), blockFaceToNotch(face));
/*     */   }
/*     */   
/*     */   public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
/* 350 */     int power = this.chunk.getHandle().getWorld().getBlockFacePower(new BlockPosition(this.x, this.y, this.z), blockFaceToNotch(face));
/*     */     
/* 352 */     org.bukkit.block.Block relative = getRelative(face);
/* 353 */     if (relative.getType() == org.bukkit.Material.REDSTONE_WIRE) {
/* 354 */       return Math.max(power, relative.getData()) > 0;
/*     */     }
/*     */     
/* 357 */     return power > 0;
/*     */   }
/*     */   
/*     */   public int getBlockPower(BlockFace face) {
/* 361 */     int power = 0;
/* 362 */     BlockRedstoneWire wire = Blocks.REDSTONE_WIRE;
/* 363 */     net.minecraft.server.v1_8_R3.World world = this.chunk.getHandle().getWorld();
/* 364 */     if (((face == BlockFace.DOWN) || (face == BlockFace.SELF)) && (world.isBlockFacePowered(new BlockPosition(this.x, this.y - 1, this.z), EnumDirection.DOWN))) power = wire.getPower(world, new BlockPosition(this.x, this.y - 1, this.z), power);
/* 365 */     if (((face == BlockFace.UP) || (face == BlockFace.SELF)) && (world.isBlockFacePowered(new BlockPosition(this.x, this.y + 1, this.z), EnumDirection.UP))) power = wire.getPower(world, new BlockPosition(this.x, this.y + 1, this.z), power);
/* 366 */     if (((face == BlockFace.EAST) || (face == BlockFace.SELF)) && (world.isBlockFacePowered(new BlockPosition(this.x + 1, this.y, this.z), EnumDirection.EAST))) power = wire.getPower(world, new BlockPosition(this.x + 1, this.y, this.z), power);
/* 367 */     if (((face == BlockFace.WEST) || (face == BlockFace.SELF)) && (world.isBlockFacePowered(new BlockPosition(this.x - 1, this.y, this.z), EnumDirection.WEST))) power = wire.getPower(world, new BlockPosition(this.x - 1, this.y, this.z), power);
/* 368 */     if (((face == BlockFace.NORTH) || (face == BlockFace.SELF)) && (world.isBlockFacePowered(new BlockPosition(this.x, this.y, this.z - 1), EnumDirection.NORTH))) power = wire.getPower(world, new BlockPosition(this.x, this.y, this.z - 1), power);
/* 369 */     if (((face == BlockFace.SOUTH) || (face == BlockFace.SELF)) && (world.isBlockFacePowered(new BlockPosition(this.x, this.y, this.z + 1), EnumDirection.SOUTH))) power = wire.getPower(world, new BlockPosition(this.x, this.y, this.z - 1), power);
/* 370 */     return face == BlockFace.SELF ? isBlockIndirectlyPowered() : isBlockFaceIndirectlyPowered(face) ? 15 : power > 0 ? power : 0;
/*     */   }
/*     */   
/*     */   public int getBlockPower() {
/* 374 */     return getBlockPower(BlockFace.SELF);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 378 */     return getType() == org.bukkit.Material.AIR;
/*     */   }
/*     */   
/*     */   public boolean isLiquid() {
/* 382 */     return (getType() == org.bukkit.Material.WATER) || (getType() == org.bukkit.Material.STATIONARY_WATER) || (getType() == org.bukkit.Material.LAVA) || (getType() == org.bukkit.Material.STATIONARY_LAVA);
/*     */   }
/*     */   
/*     */   public PistonMoveReaction getPistonMoveReaction() {
/* 386 */     return PistonMoveReaction.getById(getNMSBlock().getMaterial().getPushReaction());
/*     */   }
/*     */   
/*     */   private boolean itemCausesDrops(org.bukkit.inventory.ItemStack item) {
/* 390 */     net.minecraft.server.v1_8_R3.Block block = getNMSBlock();
/* 391 */     Item itemType = item != null ? Item.getById(item.getTypeId()) : null;
/* 392 */     return (block != null) && ((block.getMaterial().isAlwaysDestroyable()) || ((itemType != null) && (itemType.canDestroySpecialBlock(block))));
/*     */   }
/*     */   
/*     */   public boolean breakNaturally()
/*     */   {
/* 397 */     net.minecraft.server.v1_8_R3.Block block = getNMSBlock();
/* 398 */     byte data = getData();
/* 399 */     boolean result = false;
/*     */     
/* 401 */     if ((block != null) && (block != Blocks.AIR)) {
/* 402 */       block.dropNaturally(this.chunk.getHandle().getWorld(), new BlockPosition(this.x, this.y, this.z), block.fromLegacyData(data), 1.0F, 0);
/* 403 */       result = true;
/*     */     }
/*     */     
/* 406 */     setTypeId(org.bukkit.Material.AIR.getId());
/* 407 */     return result;
/*     */   }
/*     */   
/*     */   public boolean breakNaturally(org.bukkit.inventory.ItemStack item) {
/* 411 */     if (itemCausesDrops(item)) {
/* 412 */       return breakNaturally();
/*     */     }
/* 414 */     return setTypeId(org.bukkit.Material.AIR.getId());
/*     */   }
/*     */   
/*     */   public Collection<org.bukkit.inventory.ItemStack> getDrops()
/*     */   {
/* 419 */     List<org.bukkit.inventory.ItemStack> drops = new java.util.ArrayList();
/*     */     
/* 421 */     net.minecraft.server.v1_8_R3.Block block = getNMSBlock();
/* 422 */     if (block != Blocks.AIR) {
/* 423 */       byte data = getData();
/*     */       
/* 425 */       int count = block.getDropCount(0, this.chunk.getHandle().getWorld().random);
/* 426 */       for (int i = 0; i < count; i++) {
/* 427 */         Item item = block.getDropType(block.fromLegacyData(data), this.chunk.getHandle().getWorld().random, 0);
/* 428 */         if (item != null)
/*     */         {
/* 430 */           if (Blocks.SKULL == block) {
/* 431 */             net.minecraft.server.v1_8_R3.ItemStack nmsStack = new net.minecraft.server.v1_8_R3.ItemStack(item, 1, block.getDropData(this.chunk.getHandle().getWorld(), new BlockPosition(this.x, this.y, this.z)));
/* 432 */             TileEntitySkull tileentityskull = (TileEntitySkull)this.chunk.getHandle().getWorld().getTileEntity(new BlockPosition(this.x, this.y, this.z));
/*     */             
/* 434 */             if ((tileentityskull.getSkullType() == 3) && (tileentityskull.getGameProfile() != null)) {
/* 435 */               nmsStack.setTag(new NBTTagCompound());
/* 436 */               NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */               
/* 438 */               net.minecraft.server.v1_8_R3.GameProfileSerializer.serialize(nbttagcompound, tileentityskull.getGameProfile());
/* 439 */               nmsStack.getTag().set("SkullOwner", nbttagcompound);
/*     */             }
/*     */             
/* 442 */             drops.add(org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asBukkitCopy(nmsStack));
/*     */           }
/* 444 */           else if (Blocks.COCOA == block) {
/* 445 */             int age = ((Integer)block.fromLegacyData(data).get(net.minecraft.server.v1_8_R3.BlockCocoa.AGE)).intValue();
/* 446 */             int dropAmount = age >= 2 ? 3 : 1;
/* 447 */             for (int j = 0; j < dropAmount; j++) {
/* 448 */               drops.add(new org.bukkit.inventory.ItemStack(org.bukkit.Material.INK_SACK, 1, (short)3));
/*     */             }
/*     */           } else {
/* 451 */             drops.add(new org.bukkit.inventory.ItemStack(CraftMagicNumbers.getMaterial(item), 1, (short)block.getDropData(block.fromLegacyData(data))));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 456 */     return drops;
/*     */   }
/*     */   
/*     */   public Collection<org.bukkit.inventory.ItemStack> getDrops(org.bukkit.inventory.ItemStack item) {
/* 460 */     if (itemCausesDrops(item)) {
/* 461 */       return getDrops();
/*     */     }
/* 463 */     return java.util.Collections.emptyList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 469 */   private static final Biome[] BIOME_MAPPING = new Biome[BiomeBase.getBiomes().length];
/* 470 */   private static final BiomeBase[] BIOMEBASE_MAPPING = new BiomeBase[Biome.values().length];
/* 471 */   static { BIOME_MAPPING[BiomeBase.OCEAN.id] = Biome.OCEAN;
/* 472 */     BIOME_MAPPING[BiomeBase.PLAINS.id] = Biome.PLAINS;
/* 473 */     BIOME_MAPPING[BiomeBase.DESERT.id] = Biome.DESERT;
/* 474 */     BIOME_MAPPING[BiomeBase.EXTREME_HILLS.id] = Biome.EXTREME_HILLS;
/* 475 */     BIOME_MAPPING[BiomeBase.FOREST.id] = Biome.FOREST;
/* 476 */     BIOME_MAPPING[BiomeBase.TAIGA.id] = Biome.TAIGA;
/* 477 */     BIOME_MAPPING[BiomeBase.SWAMPLAND.id] = Biome.SWAMPLAND;
/* 478 */     BIOME_MAPPING[BiomeBase.RIVER.id] = Biome.RIVER;
/* 479 */     BIOME_MAPPING[BiomeBase.HELL.id] = Biome.HELL;
/* 480 */     BIOME_MAPPING[BiomeBase.SKY.id] = Biome.SKY;
/* 481 */     BIOME_MAPPING[BiomeBase.FROZEN_OCEAN.id] = Biome.FROZEN_OCEAN;
/* 482 */     BIOME_MAPPING[BiomeBase.FROZEN_RIVER.id] = Biome.FROZEN_RIVER;
/* 483 */     BIOME_MAPPING[BiomeBase.ICE_PLAINS.id] = Biome.ICE_PLAINS;
/* 484 */     BIOME_MAPPING[BiomeBase.ICE_MOUNTAINS.id] = Biome.ICE_MOUNTAINS;
/* 485 */     BIOME_MAPPING[BiomeBase.MUSHROOM_ISLAND.id] = Biome.MUSHROOM_ISLAND;
/* 486 */     BIOME_MAPPING[BiomeBase.MUSHROOM_SHORE.id] = Biome.MUSHROOM_SHORE;
/* 487 */     BIOME_MAPPING[BiomeBase.BEACH.id] = Biome.BEACH;
/* 488 */     BIOME_MAPPING[BiomeBase.DESERT_HILLS.id] = Biome.DESERT_HILLS;
/* 489 */     BIOME_MAPPING[BiomeBase.FOREST_HILLS.id] = Biome.FOREST_HILLS;
/* 490 */     BIOME_MAPPING[BiomeBase.TAIGA_HILLS.id] = Biome.TAIGA_HILLS;
/* 491 */     BIOME_MAPPING[BiomeBase.SMALL_MOUNTAINS.id] = Biome.SMALL_MOUNTAINS;
/* 492 */     BIOME_MAPPING[BiomeBase.JUNGLE.id] = Biome.JUNGLE;
/* 493 */     BIOME_MAPPING[BiomeBase.JUNGLE_HILLS.id] = Biome.JUNGLE_HILLS;
/* 494 */     BIOME_MAPPING[BiomeBase.JUNGLE_EDGE.id] = Biome.JUNGLE_EDGE;
/* 495 */     BIOME_MAPPING[BiomeBase.DEEP_OCEAN.id] = Biome.DEEP_OCEAN;
/* 496 */     BIOME_MAPPING[BiomeBase.STONE_BEACH.id] = Biome.STONE_BEACH;
/* 497 */     BIOME_MAPPING[BiomeBase.COLD_BEACH.id] = Biome.COLD_BEACH;
/* 498 */     BIOME_MAPPING[BiomeBase.BIRCH_FOREST.id] = Biome.BIRCH_FOREST;
/* 499 */     BIOME_MAPPING[BiomeBase.BIRCH_FOREST_HILLS.id] = Biome.BIRCH_FOREST_HILLS;
/* 500 */     BIOME_MAPPING[BiomeBase.ROOFED_FOREST.id] = Biome.ROOFED_FOREST;
/* 501 */     BIOME_MAPPING[BiomeBase.COLD_TAIGA.id] = Biome.COLD_TAIGA;
/* 502 */     BIOME_MAPPING[BiomeBase.COLD_TAIGA_HILLS.id] = Biome.COLD_TAIGA_HILLS;
/* 503 */     BIOME_MAPPING[BiomeBase.MEGA_TAIGA.id] = Biome.MEGA_TAIGA;
/* 504 */     BIOME_MAPPING[BiomeBase.MEGA_TAIGA_HILLS.id] = Biome.MEGA_TAIGA_HILLS;
/* 505 */     BIOME_MAPPING[BiomeBase.EXTREME_HILLS_PLUS.id] = Biome.EXTREME_HILLS_PLUS;
/* 506 */     BIOME_MAPPING[BiomeBase.SAVANNA.id] = Biome.SAVANNA;
/* 507 */     BIOME_MAPPING[BiomeBase.SAVANNA_PLATEAU.id] = Biome.SAVANNA_PLATEAU;
/* 508 */     BIOME_MAPPING[BiomeBase.MESA.id] = Biome.MESA;
/* 509 */     BIOME_MAPPING[BiomeBase.MESA_PLATEAU_F.id] = Biome.MESA_PLATEAU_FOREST;
/* 510 */     BIOME_MAPPING[BiomeBase.MESA_PLATEAU.id] = Biome.MESA_PLATEAU;
/*     */     
/*     */ 
/* 513 */     BIOME_MAPPING[(BiomeBase.PLAINS.id + 128)] = Biome.SUNFLOWER_PLAINS;
/* 514 */     BIOME_MAPPING[(BiomeBase.DESERT.id + 128)] = Biome.DESERT_MOUNTAINS;
/* 515 */     BIOME_MAPPING[(BiomeBase.FOREST.id + 128)] = Biome.FLOWER_FOREST;
/* 516 */     BIOME_MAPPING[(BiomeBase.TAIGA.id + 128)] = Biome.TAIGA_MOUNTAINS;
/* 517 */     BIOME_MAPPING[(BiomeBase.SWAMPLAND.id + 128)] = Biome.SWAMPLAND_MOUNTAINS;
/* 518 */     BIOME_MAPPING[(BiomeBase.ICE_PLAINS.id + 128)] = Biome.ICE_PLAINS_SPIKES;
/* 519 */     BIOME_MAPPING[(BiomeBase.JUNGLE.id + 128)] = Biome.JUNGLE_MOUNTAINS;
/* 520 */     BIOME_MAPPING[(BiomeBase.JUNGLE_EDGE.id + 128)] = Biome.JUNGLE_EDGE_MOUNTAINS;
/* 521 */     BIOME_MAPPING[(BiomeBase.COLD_TAIGA.id + 128)] = Biome.COLD_TAIGA_MOUNTAINS;
/* 522 */     BIOME_MAPPING[(BiomeBase.SAVANNA.id + 128)] = Biome.SAVANNA_MOUNTAINS;
/* 523 */     BIOME_MAPPING[(BiomeBase.SAVANNA_PLATEAU.id + 128)] = Biome.SAVANNA_PLATEAU_MOUNTAINS;
/* 524 */     BIOME_MAPPING[(BiomeBase.MESA.id + 128)] = Biome.MESA_BRYCE;
/* 525 */     BIOME_MAPPING[(BiomeBase.MESA_PLATEAU_F.id + 128)] = Biome.MESA_PLATEAU_FOREST_MOUNTAINS;
/* 526 */     BIOME_MAPPING[(BiomeBase.MESA_PLATEAU.id + 128)] = Biome.MESA_PLATEAU_MOUNTAINS;
/* 527 */     BIOME_MAPPING[(BiomeBase.BIRCH_FOREST.id + 128)] = Biome.BIRCH_FOREST_MOUNTAINS;
/* 528 */     BIOME_MAPPING[(BiomeBase.BIRCH_FOREST_HILLS.id + 128)] = Biome.BIRCH_FOREST_HILLS_MOUNTAINS;
/* 529 */     BIOME_MAPPING[(BiomeBase.ROOFED_FOREST.id + 128)] = Biome.ROOFED_FOREST_MOUNTAINS;
/* 530 */     BIOME_MAPPING[(BiomeBase.MEGA_TAIGA.id + 128)] = Biome.MEGA_SPRUCE_TAIGA;
/* 531 */     BIOME_MAPPING[(BiomeBase.EXTREME_HILLS.id + 128)] = Biome.EXTREME_HILLS_MOUNTAINS;
/* 532 */     BIOME_MAPPING[(BiomeBase.EXTREME_HILLS_PLUS.id + 128)] = Biome.EXTREME_HILLS_PLUS_MOUNTAINS;
/* 533 */     BIOME_MAPPING[(BiomeBase.MEGA_TAIGA_HILLS.id + 128)] = Biome.MEGA_SPRUCE_TAIGA_HILLS;
/*     */     
/*     */ 
/*     */ 
/* 537 */     for (int i = 0; i < BIOME_MAPPING.length; i++) {
/* 538 */       if ((BiomeBase.getBiome(i) != null) && (BIOME_MAPPING[i] == null)) {
/* 539 */         throw new IllegalArgumentException("Missing Biome mapping for BiomeBase[" + i + ", " + BiomeBase.getBiome(i) + "]");
/*     */       }
/* 541 */       if (BIOME_MAPPING[i] != null) {
/* 542 */         BIOMEBASE_MAPPING[BIOME_MAPPING[i].ordinal()] = BiomeBase.getBiome(i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
/* 548 */     this.chunk.getCraftWorld().getBlockMetadata().setMetadata(this, metadataKey, newMetadataValue);
/*     */   }
/*     */   
/*     */   public List<MetadataValue> getMetadata(String metadataKey) {
/* 552 */     return this.chunk.getCraftWorld().getBlockMetadata().getMetadata(this, metadataKey);
/*     */   }
/*     */   
/*     */   public boolean hasMetadata(String metadataKey) {
/* 556 */     return this.chunk.getCraftWorld().getBlockMetadata().hasMetadata(this, metadataKey);
/*     */   }
/*     */   
/*     */   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
/* 560 */     this.chunk.getCraftWorld().getBlockMetadata().removeMetadata(this, metadataKey, owningPlugin);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */