/*     */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*     */ 
/*     */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*     */ import net.minecraft.server.v1_8_R3.TileEntity;
/*     */ import net.minecraft.server.v1_8_R3.WorldServer;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.metadata.BlockMetadataStore;
/*     */ import org.bukkit.material.MaterialData;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class CraftBlockState implements org.bukkit.block.BlockState
/*     */ {
/*     */   private final CraftWorld world;
/*     */   private final CraftChunk chunk;
/*     */   private final int x;
/*     */   private final int y;
/*     */   private final int z;
/*     */   protected int type;
/*     */   protected MaterialData data;
/*     */   protected int flag;
/*     */   protected final byte light;
/*     */   
/*     */   public CraftBlockState(Block block)
/*     */   {
/*  31 */     this.world = ((CraftWorld)block.getWorld());
/*  32 */     this.x = block.getX();
/*  33 */     this.y = block.getY();
/*  34 */     this.z = block.getZ();
/*  35 */     this.type = block.getTypeId();
/*  36 */     this.light = block.getLightLevel();
/*  37 */     this.chunk = ((CraftChunk)block.getChunk());
/*  38 */     this.flag = 3;
/*     */     
/*  40 */     createData(block.getData());
/*     */   }
/*     */   
/*     */   public CraftBlockState(Block block, int flag) {
/*  44 */     this(block);
/*  45 */     this.flag = flag;
/*     */   }
/*     */   
/*     */   public CraftBlockState(Material material) {
/*  49 */     this.world = null;
/*  50 */     this.type = material.getId();
/*  51 */     this.light = 0;
/*  52 */     this.chunk = null;
/*  53 */     this.x = (this.y = this.z = 0);
/*     */   }
/*     */   
/*     */   public static CraftBlockState getBlockState(net.minecraft.server.v1_8_R3.World world, int x, int y, int z) {
/*  57 */     return new CraftBlockState(world.getWorld().getBlockAt(x, y, z));
/*     */   }
/*     */   
/*     */   public static CraftBlockState getBlockState(net.minecraft.server.v1_8_R3.World world, int x, int y, int z, int flag) {
/*  61 */     return new CraftBlockState(world.getWorld().getBlockAt(x, y, z), flag);
/*     */   }
/*     */   
/*     */   public org.bukkit.World getWorld() {
/*  65 */     requirePlaced();
/*  66 */     return this.world;
/*     */   }
/*     */   
/*     */   public int getX() {
/*  70 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  74 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getZ() {
/*  78 */     return this.z;
/*     */   }
/*     */   
/*     */   public Chunk getChunk() {
/*  82 */     requirePlaced();
/*  83 */     return this.chunk;
/*     */   }
/*     */   
/*     */   public void setData(MaterialData data) {
/*  87 */     Material mat = getType();
/*     */     
/*  89 */     if ((mat == null) || (mat.getData() == null)) {
/*  90 */       this.data = data;
/*     */     }
/*  92 */     else if ((data.getClass() == mat.getData()) || (data.getClass() == MaterialData.class)) {
/*  93 */       this.data = data;
/*     */     } else {
/*  95 */       throw new IllegalArgumentException("Provided data is not of type " + 
/*  96 */         mat.getData().getName() + ", found " + data.getClass().getName());
/*     */     }
/*     */   }
/*     */   
/*     */   public MaterialData getData()
/*     */   {
/* 102 */     return this.data;
/*     */   }
/*     */   
/*     */   public void setType(Material type) {
/* 106 */     setTypeId(type.getId());
/*     */   }
/*     */   
/*     */   public boolean setTypeId(int type) {
/* 110 */     if (this.type != type) {
/* 111 */       this.type = type;
/*     */       
/* 113 */       createData((byte)0);
/*     */     }
/* 115 */     return true;
/*     */   }
/*     */   
/*     */   public Material getType() {
/* 119 */     return Material.getMaterial(getTypeId());
/*     */   }
/*     */   
/*     */   public void setFlag(int flag) {
/* 123 */     this.flag = flag;
/*     */   }
/*     */   
/*     */   public int getFlag() {
/* 127 */     return this.flag;
/*     */   }
/*     */   
/*     */   public int getTypeId() {
/* 131 */     return this.type;
/*     */   }
/*     */   
/*     */   public byte getLightLevel() {
/* 135 */     return this.light;
/*     */   }
/*     */   
/*     */   public Block getBlock() {
/* 139 */     requirePlaced();
/* 140 */     return this.world.getBlockAt(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public boolean update() {
/* 144 */     return update(false);
/*     */   }
/*     */   
/*     */   public boolean update(boolean force) {
/* 148 */     return update(force, true);
/*     */   }
/*     */   
/*     */   public boolean update(boolean force, boolean applyPhysics) {
/* 152 */     requirePlaced();
/* 153 */     Block block = getBlock();
/*     */     
/* 155 */     if ((block.getType() != getType()) && 
/* 156 */       (!force)) {
/* 157 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 161 */     block.setTypeIdAndData(getTypeId(), getRawData(), applyPhysics);
/* 162 */     this.world.getHandle().notify(new BlockPosition(this.x, this.y, this.z));
/*     */     
/* 164 */     return true;
/*     */   }
/*     */   
/*     */   private void createData(byte data) {
/* 168 */     Material mat = getType();
/* 169 */     if ((mat == null) || (mat.getData() == null)) {
/* 170 */       this.data = new MaterialData(this.type, data);
/*     */     } else {
/* 172 */       this.data = mat.getNewData(data);
/*     */     }
/*     */   }
/*     */   
/*     */   public byte getRawData() {
/* 177 */     return this.data.getData();
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 181 */     return new Location(this.world, this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public Location getLocation(Location loc) {
/* 185 */     if (loc != null) {
/* 186 */       loc.setWorld(this.world);
/* 187 */       loc.setX(this.x);
/* 188 */       loc.setY(this.y);
/* 189 */       loc.setZ(this.z);
/* 190 */       loc.setYaw(0.0F);
/* 191 */       loc.setPitch(0.0F);
/*     */     }
/*     */     
/* 194 */     return loc;
/*     */   }
/*     */   
/*     */   public void setRawData(byte data) {
/* 198 */     this.data.setData(data);
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 203 */     if (obj == null) {
/* 204 */       return false;
/*     */     }
/* 206 */     if (getClass() != obj.getClass()) {
/* 207 */       return false;
/*     */     }
/* 209 */     CraftBlockState other = (CraftBlockState)obj;
/* 210 */     if ((this.world != other.world) && ((this.world == null) || (!this.world.equals(other.world)))) {
/* 211 */       return false;
/*     */     }
/* 213 */     if (this.x != other.x) {
/* 214 */       return false;
/*     */     }
/* 216 */     if (this.y != other.y) {
/* 217 */       return false;
/*     */     }
/* 219 */     if (this.z != other.z) {
/* 220 */       return false;
/*     */     }
/* 222 */     if (this.type != other.type) {
/* 223 */       return false;
/*     */     }
/* 225 */     if ((this.data != other.data) && ((this.data == null) || (!this.data.equals(other.data)))) {
/* 226 */       return false;
/*     */     }
/* 228 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 233 */     int hash = 7;
/* 234 */     hash = 73 * hash + (this.world != null ? this.world.hashCode() : 0);
/* 235 */     hash = 73 * hash + this.x;
/* 236 */     hash = 73 * hash + this.y;
/* 237 */     hash = 73 * hash + this.z;
/* 238 */     hash = 73 * hash + this.type;
/* 239 */     hash = 73 * hash + (this.data != null ? this.data.hashCode() : 0);
/* 240 */     return hash;
/*     */   }
/*     */   
/*     */   public TileEntity getTileEntity() {
/* 244 */     return null;
/*     */   }
/*     */   
/*     */   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
/* 248 */     requirePlaced();
/* 249 */     this.chunk.getCraftWorld().getBlockMetadata().setMetadata(getBlock(), metadataKey, newMetadataValue);
/*     */   }
/*     */   
/*     */   public java.util.List<MetadataValue> getMetadata(String metadataKey) {
/* 253 */     requirePlaced();
/* 254 */     return this.chunk.getCraftWorld().getBlockMetadata().getMetadata(getBlock(), metadataKey);
/*     */   }
/*     */   
/*     */   public boolean hasMetadata(String metadataKey) {
/* 258 */     requirePlaced();
/* 259 */     return this.chunk.getCraftWorld().getBlockMetadata().hasMetadata(getBlock(), metadataKey);
/*     */   }
/*     */   
/*     */   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
/* 263 */     requirePlaced();
/* 264 */     this.chunk.getCraftWorld().getBlockMetadata().removeMetadata(getBlock(), metadataKey, owningPlugin);
/*     */   }
/*     */   
/*     */   public boolean isPlaced()
/*     */   {
/* 269 */     return this.world != null;
/*     */   }
/*     */   
/*     */   protected void requirePlaced() {
/* 273 */     if (!isPlaced()) {
/* 274 */       throw new IllegalStateException("The blockState must be placed to call this method");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftBlockState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */