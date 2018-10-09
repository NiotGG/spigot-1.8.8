/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Stairs
/*     */   extends MaterialData
/*     */   implements Directional
/*     */ {
/*     */   @Deprecated
/*     */   public Stairs(int type)
/*     */   {
/*  17 */     super(type);
/*     */   }
/*     */   
/*     */   public Stairs(Material type) {
/*  21 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Stairs(int type, byte data)
/*     */   {
/*  31 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Stairs(Material type, byte data)
/*     */   {
/*  41 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public BlockFace getAscendingDirection()
/*     */   {
/*  48 */     byte data = getData();
/*     */     
/*  50 */     switch (data & 0x3) {
/*     */     case 0: 
/*     */     default: 
/*  53 */       return BlockFace.EAST;
/*     */     
/*     */     case 1: 
/*  56 */       return BlockFace.WEST;
/*     */     
/*     */     case 2: 
/*  59 */       return BlockFace.SOUTH;
/*     */     }
/*     */     
/*  62 */     return BlockFace.NORTH;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockFace getDescendingDirection()
/*     */   {
/*  70 */     return getAscendingDirection().getOppositeFace();
/*     */   }
/*     */   
/*     */   public void setFacingDirection(BlockFace face)
/*     */   {
/*     */     byte data;
/*     */     byte data;
/*     */     byte data;
/*     */     byte data;
/*  79 */     switch (face) {
/*     */     case DOWN: 
/*  81 */       data = 3;
/*  82 */       break;
/*     */     
/*     */     case EAST_NORTH_EAST: 
/*  85 */       data = 2;
/*  86 */       break;
/*     */     
/*     */     case EAST: 
/*     */     default: 
/*  90 */       data = 0;
/*  91 */       break;
/*     */     
/*     */     case EAST_SOUTH_EAST: 
/*  94 */       data = 1;
/*     */     }
/*     */     
/*     */     
/*  98 */     setData((byte)(getData() & 0xC | data));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public BlockFace getFacing()
/*     */   {
/* 105 */     return getDescendingDirection();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInverted()
/*     */   {
/* 114 */     return (getData() & 0x4) != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInverted(boolean inv)
/*     */   {
/* 124 */     int dat = getData() & 0x3;
/* 125 */     if (inv) {
/* 126 */       dat |= 0x4;
/*     */     }
/* 128 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 133 */     return super.toString() + " facing " + getFacing() + (isInverted() ? " inverted" : "");
/*     */   }
/*     */   
/*     */   public Stairs clone()
/*     */   {
/* 138 */     return (Stairs)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Stairs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */