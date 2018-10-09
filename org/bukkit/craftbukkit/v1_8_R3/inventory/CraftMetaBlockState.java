/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.BlockJukeBox.TileEntityRecordPlayer;
/*     */ import net.minecraft.server.v1_8_R3.NBTBase;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import net.minecraft.server.v1_8_R3.TileEntity;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityBanner;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityBeacon;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityBrewingStand;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityChest;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityCommand;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityDispenser;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityDropper;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityFurnace;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityHopper;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityMobSpawner;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityNote;
/*     */ import net.minecraft.server.v1_8_R3.TileEntitySign;
/*     */ import net.minecraft.server.v1_8_R3.TileEntitySkull;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftBanner;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftBeacon;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlockState;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftBrewingStand;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftChest;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftCommandBlock;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftCreatureSpawner;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftDispenser;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftDropper;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftFurnace;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftHopper;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftJukebox;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftNoteBlock;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftSign;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftSkull;
/*     */ import org.bukkit.inventory.meta.BlockStateMeta;
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ public class CraftMetaBlockState
/*     */   extends CraftMetaItem implements BlockStateMeta
/*     */ {
/*  48 */   static final CraftMetaItem.ItemMetaKey BLOCK_ENTITY_TAG = new CraftMetaItem.ItemMetaKey("BlockEntityTag");
/*     */   final Material material;
/*     */   NBTTagCompound blockEntityTag;
/*     */   
/*     */   CraftMetaBlockState(CraftMetaItem meta, Material material)
/*     */   {
/*  54 */     super(meta);
/*  55 */     this.material = material;
/*     */     
/*  57 */     if ((!(meta instanceof CraftMetaBlockState)) || 
/*  58 */       (((CraftMetaBlockState)meta).material != material) || 
/*  59 */       (material == Material.SIGN) || 
/*  60 */       (material == Material.COMMAND)) {
/*  61 */       this.blockEntityTag = null;
/*  62 */       return;
/*     */     }
/*     */     
/*  65 */     CraftMetaBlockState te = (CraftMetaBlockState)meta;
/*  66 */     this.blockEntityTag = te.blockEntityTag;
/*     */   }
/*     */   
/*     */   CraftMetaBlockState(NBTTagCompound tag, Material material) {
/*  70 */     super(tag);
/*  71 */     this.material = material;
/*     */     
/*  73 */     if (tag.hasKeyOfType(BLOCK_ENTITY_TAG.NBT, 10)) {
/*  74 */       this.blockEntityTag = tag.getCompound(BLOCK_ENTITY_TAG.NBT);
/*     */     } else {
/*  76 */       this.blockEntityTag = null;
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaBlockState(Map<String, Object> map) {
/*  81 */     super(map);
/*  82 */     String matName = CraftMetaItem.SerializableMeta.getString(map, "blockMaterial", true);
/*  83 */     Material m = Material.getMaterial(matName);
/*  84 */     if (m != null) {
/*  85 */       this.material = m;
/*     */     } else {
/*  87 */       this.material = Material.AIR;
/*     */     }
/*     */   }
/*     */   
/*     */   void applyToItem(NBTTagCompound tag)
/*     */   {
/*  93 */     super.applyToItem(tag);
/*     */     
/*  95 */     if (this.blockEntityTag != null) {
/*  96 */       tag.set(BLOCK_ENTITY_TAG.NBT, this.blockEntityTag);
/*     */     }
/*     */   }
/*     */   
/*     */   void deserializeInternal(NBTTagCompound tag)
/*     */   {
/* 102 */     if (tag.hasKeyOfType(BLOCK_ENTITY_TAG.NBT, 10)) {
/* 103 */       this.blockEntityTag = tag.getCompound(BLOCK_ENTITY_TAG.NBT);
/*     */     }
/*     */   }
/*     */   
/*     */   void serializeInternal(Map<String, NBTBase> internalTags)
/*     */   {
/* 109 */     if (this.blockEntityTag != null) {
/* 110 */       internalTags.put(BLOCK_ENTITY_TAG.NBT, this.blockEntityTag);
/*     */     }
/*     */   }
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder)
/*     */   {
/* 116 */     super.serialize(builder);
/* 117 */     builder.put("blockMaterial", this.material.name());
/* 118 */     return builder;
/*     */   }
/*     */   
/*     */   int applyHash()
/*     */   {
/*     */     int original;
/* 124 */     int hash = original = super.applyHash();
/* 125 */     if (this.blockEntityTag != null) {
/* 126 */       hash = 61 * hash + this.blockEntityTag.hashCode();
/*     */     }
/* 128 */     return original != hash ? CraftMetaBlockState.class.hashCode() ^ hash : hash;
/*     */   }
/*     */   
/*     */   public boolean equalsCommon(CraftMetaItem meta)
/*     */   {
/* 133 */     if (!super.equalsCommon(meta)) {
/* 134 */       return false;
/*     */     }
/* 136 */     if ((meta instanceof CraftMetaBlockState)) {
/* 137 */       CraftMetaBlockState that = (CraftMetaBlockState)meta;
/*     */       
/* 139 */       return Objects.equal(this.blockEntityTag, that.blockEntityTag);
/*     */     }
/* 141 */     return true;
/*     */   }
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta)
/*     */   {
/* 146 */     return (super.notUncommon(meta)) && (((meta instanceof CraftMetaBlockState)) || (this.blockEntityTag == null));
/*     */   }
/*     */   
/*     */   boolean isEmpty()
/*     */   {
/* 151 */     return (super.isEmpty()) && (this.blockEntityTag == null);
/*     */   }
/*     */   
/*     */   boolean applicableTo(Material type)
/*     */   {
/* 156 */     switch (type) {
/*     */     case BLAZE_POWDER: 
/*     */     case BOAT: 
/*     */     case BREWING_STAND: 
/*     */     case CHAINMAIL_LEGGINGS: 
/*     */     case CLAY: 
/*     */     case COBBLESTONE_STAIRS: 
/*     */     case DIAMOND_AXE: 
/*     */     case ENDER_PORTAL_FRAME: 
/*     */     case GLASS_BOTTLE: 
/*     */     case GLOWING_REDSTONE_ORE: 
/*     */     case GOLD_BOOTS: 
/*     */     case GOLD_LEGGINGS: 
/*     */     case GOLD_PICKAXE: 
/*     */     case GOLD_SWORD: 
/*     */     case IRON_FENCE: 
/*     */     case QUARTZ_STAIRS: 
/*     */     case SPECKLED_MELON: 
/*     */     case STANDING_BANNER: 
/*     */     case STORAGE_MINECART: 
/* 176 */       return true;
/*     */     }
/* 178 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasBlockState()
/*     */   {
/* 183 */     return this.blockEntityTag != null;
/*     */   }
/*     */   
/*     */   public BlockState getBlockState()
/*     */   {
/* 188 */     TileEntity te = this.blockEntityTag == null ? null : TileEntity.c(this.blockEntityTag);
/*     */     
/* 190 */     switch (this.material) {
/*     */     case COCOA: 
/*     */     case COOKED_CHICKEN: 
/*     */     case QUARTZ_STAIRS: 
/* 194 */       if (te == null) {
/* 195 */         te = new TileEntitySign();
/*     */       }
/* 197 */       return new CraftSign(this.material, (TileEntitySign)te);
/*     */     case CLAY: 
/*     */     case GOLD_BOOTS: 
/* 200 */       if (te == null) {
/* 201 */         te = new TileEntityChest();
/*     */       }
/* 203 */       return new CraftChest(this.material, (TileEntityChest)te);
/*     */     case COBBLESTONE_STAIRS: 
/*     */     case COBBLE_WALL: 
/* 206 */       if (te == null) {
/* 207 */         te = new TileEntityFurnace();
/*     */       }
/* 209 */       return new CraftFurnace(this.material, (TileEntityFurnace)te);
/*     */     case BLAZE_POWDER: 
/* 211 */       if (te == null) {
/* 212 */         te = new TileEntityDispenser();
/*     */       }
/* 214 */       return new CraftDispenser(this.material, (TileEntityDispenser)te);
/*     */     case GOLD_SWORD: 
/* 216 */       if (te == null) {
/* 217 */         te = new TileEntityDispenser();
/*     */       }
/* 219 */       return new CraftDropper(this.material, (TileEntityDropper)te);
/*     */     case GOLD_PICKAXE: 
/* 221 */       if (te == null) {
/* 222 */         te = new TileEntityHopper();
/*     */       }
/* 224 */       return new CraftHopper(this.material, (TileEntityHopper)te);
/*     */     case CHAINMAIL_LEGGINGS: 
/* 226 */       if (te == null) {
/* 227 */         te = new TileEntityMobSpawner();
/*     */       }
/* 229 */       return new CraftCreatureSpawner(this.material, (TileEntityMobSpawner)te);
/*     */     case BOAT: 
/* 231 */       if (te == null) {
/* 232 */         te = new TileEntityNote();
/*     */       }
/* 234 */       return new CraftNoteBlock(this.material, (TileEntityNote)te);
/*     */     case DIAMOND_AXE: 
/* 236 */       if (te == null) {
/* 237 */         te = new BlockJukeBox.TileEntityRecordPlayer();
/*     */       }
/* 239 */       return new CraftJukebox(this.material, (BlockJukeBox.TileEntityRecordPlayer)te);
/*     */     case ENDER_STONE: 
/* 241 */       if (te == null) {
/* 242 */         te = new TileEntityBrewingStand();
/*     */       }
/* 244 */       return new CraftBrewingStand(this.material, (TileEntityBrewingStand)te);
/*     */     case GOLD_BARDING: 
/* 246 */       if (te == null) {
/* 247 */         te = new TileEntitySkull();
/*     */       }
/* 249 */       return new CraftSkull(this.material, (TileEntitySkull)te);
/*     */     case GLASS_BOTTLE: 
/* 251 */       if (te == null) {
/* 252 */         te = new TileEntityCommand();
/*     */       }
/* 254 */       return new CraftCommandBlock(this.material, (TileEntityCommand)te);
/*     */     case GLOWING_REDSTONE_ORE: 
/* 256 */       if (te == null) {
/* 257 */         te = new TileEntityBeacon();
/*     */       }
/* 259 */       return new CraftBeacon(this.material, (TileEntityBeacon)te);
/*     */     case IRON_DOOR: 
/*     */     case IRON_DOOR_BLOCK: 
/*     */     case WHEAT: 
/* 263 */       if (te == null) {
/* 264 */         te = new TileEntityBanner();
/*     */       }
/* 266 */       return new CraftBanner(this.material, (TileEntityBanner)te);
/*     */     }
/* 268 */     throw new IllegalStateException("Missing blockState for " + this.material);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setBlockState(BlockState blockState)
/*     */   {
/* 274 */     Validate.notNull(blockState, "blockState must not be null");
/* 275 */     TileEntity te = ((CraftBlockState)blockState).getTileEntity();
/* 276 */     Validate.notNull(te, "Invalid blockState");
/*     */     boolean valid;
/*     */     boolean valid;
/* 279 */     boolean valid; boolean valid; boolean valid; boolean valid; boolean valid; boolean valid; boolean valid; boolean valid; boolean valid; boolean valid; boolean valid; boolean valid; boolean valid; switch (this.material) {
/*     */     case COCOA: 
/*     */     case COOKED_CHICKEN: 
/*     */     case QUARTZ_STAIRS: 
/* 283 */       valid = te instanceof TileEntitySign;
/* 284 */       break;
/*     */     case CLAY: 
/*     */     case GOLD_BOOTS: 
/* 287 */       valid = te instanceof TileEntityChest;
/* 288 */       break;
/*     */     case COBBLESTONE_STAIRS: 
/*     */     case COBBLE_WALL: 
/* 291 */       valid = te instanceof TileEntityFurnace;
/* 292 */       break;
/*     */     case BLAZE_POWDER: 
/* 294 */       valid = te instanceof TileEntityDispenser;
/* 295 */       break;
/*     */     case GOLD_SWORD: 
/* 297 */       valid = te instanceof TileEntityDropper;
/* 298 */       break;
/*     */     case GOLD_PICKAXE: 
/* 300 */       valid = te instanceof TileEntityHopper;
/* 301 */       break;
/*     */     case CHAINMAIL_LEGGINGS: 
/* 303 */       valid = te instanceof TileEntityMobSpawner;
/* 304 */       break;
/*     */     case BOAT: 
/* 306 */       valid = te instanceof TileEntityNote;
/* 307 */       break;
/*     */     case DIAMOND_AXE: 
/* 309 */       valid = te instanceof BlockJukeBox.TileEntityRecordPlayer;
/* 310 */       break;
/*     */     case ENDER_STONE: 
/* 312 */       valid = te instanceof TileEntityBrewingStand;
/* 313 */       break;
/*     */     case GOLD_BARDING: 
/* 315 */       valid = te instanceof TileEntitySkull;
/* 316 */       break;
/*     */     case GLASS_BOTTLE: 
/* 318 */       valid = te instanceof TileEntityCommand;
/* 319 */       break;
/*     */     case GLOWING_REDSTONE_ORE: 
/* 321 */       valid = te instanceof TileEntityBeacon;
/* 322 */       break;
/*     */     case IRON_DOOR: 
/*     */     case IRON_DOOR_BLOCK: 
/*     */     case WHEAT: 
/* 326 */       valid = te instanceof TileEntityBanner;
/* 327 */       break;
/*     */     default: 
/* 329 */       valid = false;
/*     */     }
/*     */     
/*     */     
/* 333 */     Validate.isTrue(valid, "Invalid blockState for " + this.material);
/*     */     
/* 335 */     this.blockEntityTag = new NBTTagCompound();
/* 336 */     te.b(this.blockEntityTag);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftMetaBlockState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */