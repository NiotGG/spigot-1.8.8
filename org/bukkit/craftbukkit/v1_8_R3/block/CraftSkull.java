/*     */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*     */ import net.minecraft.server.v1_8_R3.TileEntitySkull;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.SkullType;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ 
/*     */ public class CraftSkull extends CraftBlockState implements org.bukkit.block.Skull
/*     */ {
/*     */   private static final int MAX_OWNER_LENGTH = 16;
/*     */   private final TileEntitySkull skull;
/*     */   private GameProfile profile;
/*     */   private SkullType skullType;
/*     */   private byte rotation;
/*     */   
/*     */   public CraftSkull(Block block)
/*     */   {
/*  22 */     super(block);
/*     */     
/*  24 */     CraftWorld world = (CraftWorld)block.getWorld();
/*  25 */     this.skull = ((TileEntitySkull)world.getTileEntityAt(getX(), getY(), getZ()));
/*  26 */     this.profile = this.skull.getGameProfile();
/*  27 */     this.skullType = getSkullType(this.skull.getSkullType());
/*  28 */     this.rotation = ((byte)this.skull.getRotation());
/*     */   }
/*     */   
/*     */   public CraftSkull(Material material, TileEntitySkull te) {
/*  32 */     super(material);
/*  33 */     this.skull = te;
/*  34 */     this.profile = this.skull.getGameProfile();
/*  35 */     this.skullType = getSkullType(this.skull.getSkullType());
/*  36 */     this.rotation = ((byte)this.skull.getRotation());
/*     */   }
/*     */   
/*     */   static SkullType getSkullType(int id) {
/*  40 */     switch (id) {
/*     */     case 0: 
/*     */     default: 
/*  43 */       return SkullType.SKELETON;
/*     */     case 1: 
/*  45 */       return SkullType.WITHER;
/*     */     case 2: 
/*  47 */       return SkullType.ZOMBIE;
/*     */     case 3: 
/*  49 */       return SkullType.PLAYER;
/*     */     }
/*  51 */     return SkullType.CREEPER;
/*     */   }
/*     */   
/*     */   static int getSkullType(SkullType type)
/*     */   {
/*  56 */     switch (type) {
/*     */     case CREEPER: 
/*     */     default: 
/*  59 */       return 0;
/*     */     case PLAYER: 
/*  61 */       return 1;
/*     */     case SKELETON: 
/*  63 */       return 2;
/*     */     case WITHER: 
/*  65 */       return 3;
/*     */     }
/*  67 */     return 4;
/*     */   }
/*     */   
/*     */   static byte getBlockFace(BlockFace rotation)
/*     */   {
/*  72 */     switch (rotation) {
/*     */     case DOWN: 
/*  74 */       return 0;
/*     */     case SOUTH_SOUTH_EAST: 
/*  76 */       return 1;
/*     */     case NORTH_NORTH_EAST: 
/*  78 */       return 2;
/*     */     case SOUTH_SOUTH_WEST: 
/*  80 */       return 3;
/*     */     case EAST: 
/*  82 */       return 4;
/*     */     case SOUTH_WEST: 
/*  84 */       return 5;
/*     */     case NORTH_WEST: 
/*  86 */       return 6;
/*     */     case UP: 
/*  88 */       return 7;
/*     */     case EAST_NORTH_EAST: 
/*  90 */       return 8;
/*     */     case WEST: 
/*  92 */       return 9;
/*     */     case SELF: 
/*  94 */       return 10;
/*     */     case WEST_NORTH_WEST: 
/*  96 */       return 11;
/*     */     case EAST_SOUTH_EAST: 
/*  98 */       return 12;
/*     */     case SOUTH: 
/* 100 */       return 13;
/*     */     case NORTH_NORTH_WEST: 
/* 102 */       return 14;
/*     */     case SOUTH_EAST: 
/* 104 */       return 15;
/*     */     }
/* 106 */     throw new IllegalArgumentException("Invalid BlockFace rotation: " + rotation);
/*     */   }
/*     */   
/*     */   static BlockFace getBlockFace(byte rotation)
/*     */   {
/* 111 */     switch (rotation) {
/*     */     case 0: 
/* 113 */       return BlockFace.NORTH;
/*     */     case 1: 
/* 115 */       return BlockFace.NORTH_NORTH_EAST;
/*     */     case 2: 
/* 117 */       return BlockFace.NORTH_EAST;
/*     */     case 3: 
/* 119 */       return BlockFace.EAST_NORTH_EAST;
/*     */     case 4: 
/* 121 */       return BlockFace.EAST;
/*     */     case 5: 
/* 123 */       return BlockFace.EAST_SOUTH_EAST;
/*     */     case 6: 
/* 125 */       return BlockFace.SOUTH_EAST;
/*     */     case 7: 
/* 127 */       return BlockFace.SOUTH_SOUTH_EAST;
/*     */     case 8: 
/* 129 */       return BlockFace.SOUTH;
/*     */     case 9: 
/* 131 */       return BlockFace.SOUTH_SOUTH_WEST;
/*     */     case 10: 
/* 133 */       return BlockFace.SOUTH_WEST;
/*     */     case 11: 
/* 135 */       return BlockFace.WEST_SOUTH_WEST;
/*     */     case 12: 
/* 137 */       return BlockFace.WEST;
/*     */     case 13: 
/* 139 */       return BlockFace.WEST_NORTH_WEST;
/*     */     case 14: 
/* 141 */       return BlockFace.NORTH_WEST;
/*     */     case 15: 
/* 143 */       return BlockFace.NORTH_NORTH_WEST;
/*     */     }
/* 145 */     throw new AssertionError(rotation);
/*     */   }
/*     */   
/*     */   public boolean hasOwner()
/*     */   {
/* 150 */     return this.profile != null;
/*     */   }
/*     */   
/*     */   public String getOwner() {
/* 154 */     return hasOwner() ? this.profile.getName() : null;
/*     */   }
/*     */   
/*     */   public boolean setOwner(String name) {
/* 158 */     if ((name == null) || (name.length() > 16)) {
/* 159 */       return false;
/*     */     }
/*     */     
/* 162 */     GameProfile profile = MinecraftServer.getServer().getUserCache().getProfile(name);
/* 163 */     if (profile == null) {
/* 164 */       return false;
/*     */     }
/*     */     
/* 167 */     if (this.skullType != SkullType.PLAYER) {
/* 168 */       this.skullType = SkullType.PLAYER;
/*     */     }
/*     */     
/* 171 */     this.profile = profile;
/* 172 */     return true;
/*     */   }
/*     */   
/*     */   public BlockFace getRotation() {
/* 176 */     return getBlockFace(this.rotation);
/*     */   }
/*     */   
/*     */   public void setRotation(BlockFace rotation) {
/* 180 */     this.rotation = getBlockFace(rotation);
/*     */   }
/*     */   
/*     */   public SkullType getSkullType() {
/* 184 */     return this.skullType;
/*     */   }
/*     */   
/*     */   public void setSkullType(SkullType skullType) {
/* 188 */     this.skullType = skullType;
/*     */     
/* 190 */     if (skullType != SkullType.PLAYER) {
/* 191 */       this.profile = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean update(boolean force, boolean applyPhysics)
/*     */   {
/* 197 */     boolean result = super.update(force, applyPhysics);
/*     */     
/* 199 */     if (result) {
/* 200 */       if (this.skullType == SkullType.PLAYER) {
/* 201 */         this.skull.setGameProfile(this.profile);
/*     */       } else {
/* 203 */         this.skull.setSkullType(getSkullType(this.skullType));
/*     */       }
/*     */       
/* 206 */       this.skull.setRotation(this.rotation);
/* 207 */       this.skull.update();
/*     */     }
/*     */     
/* 210 */     return result;
/*     */   }
/*     */   
/*     */   public TileEntitySkull getTileEntity()
/*     */   {
/* 215 */     return this.skull;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */