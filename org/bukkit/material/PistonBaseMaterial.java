/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PistonBaseMaterial
/*     */   extends MaterialData
/*     */   implements Directional, Redstone
/*     */ {
/*     */   @Deprecated
/*     */   public PistonBaseMaterial(int type)
/*     */   {
/*  19 */     super(type);
/*     */   }
/*     */   
/*     */   public PistonBaseMaterial(Material type) {
/*  23 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public PistonBaseMaterial(int type, byte data)
/*     */   {
/*  35 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public PistonBaseMaterial(Material type, byte data)
/*     */   {
/*  47 */     super(type, data);
/*     */   }
/*     */   
/*     */   public void setFacingDirection(BlockFace face)
/*     */   {
/*  52 */     byte data = (byte)(getData() & 0x8);
/*     */     
/*  54 */     switch (face) {
/*     */     case NORTH: 
/*  56 */       data = (byte)(data | 0x1);
/*  57 */       break;
/*     */     case DOWN: 
/*  59 */       data = (byte)(data | 0x2);
/*  60 */       break;
/*     */     case EAST_NORTH_EAST: 
/*  62 */       data = (byte)(data | 0x3);
/*  63 */       break;
/*     */     case EAST_SOUTH_EAST: 
/*  65 */       data = (byte)(data | 0x4);
/*  66 */       break;
/*     */     case EAST: 
/*  68 */       data = (byte)(data | 0x5);
/*     */     }
/*     */     
/*  71 */     setData(data);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing()
/*     */   {
/*  76 */     byte dir = (byte)(getData() & 0x7);
/*     */     
/*  78 */     switch (dir) {
/*     */     case 0: 
/*  80 */       return BlockFace.DOWN;
/*     */     case 1: 
/*  82 */       return BlockFace.UP;
/*     */     case 2: 
/*  84 */       return BlockFace.NORTH;
/*     */     case 3: 
/*  86 */       return BlockFace.SOUTH;
/*     */     case 4: 
/*  88 */       return BlockFace.WEST;
/*     */     case 5: 
/*  90 */       return BlockFace.EAST;
/*     */     }
/*  92 */     return BlockFace.SELF;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isPowered()
/*     */   {
/*  98 */     return (getData() & 0x8) == 8;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPowered(boolean powered)
/*     */   {
/* 107 */     setData((byte)(powered ? getData() | 0x8 : getData() & 0xFFFFFFF7));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSticky()
/*     */   {
/* 116 */     return getItemType() == Material.PISTON_STICKY_BASE;
/*     */   }
/*     */   
/*     */   public PistonBaseMaterial clone()
/*     */   {
/* 121 */     return (PistonBaseMaterial)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\PistonBaseMaterial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */