/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class CocoaPlant
/*     */   extends MaterialData
/*     */   implements Directional, Attachable
/*     */ {
/*     */   public static enum CocoaPlantSize
/*     */   {
/*  12 */     SMALL, 
/*  13 */     MEDIUM, 
/*  14 */     LARGE;
/*     */   }
/*     */   
/*     */   public CocoaPlant() {
/*  18 */     super(Material.COCOA);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public CocoaPlant(int type)
/*     */   {
/*  27 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public CocoaPlant(int type, byte data)
/*     */   {
/*  37 */     super(type, data);
/*     */   }
/*     */   
/*     */   public CocoaPlant(CocoaPlantSize sz) {
/*  41 */     this();
/*  42 */     setSize(sz);
/*     */   }
/*     */   
/*     */   public CocoaPlant(CocoaPlantSize sz, BlockFace dir) {
/*  46 */     this();
/*  47 */     setSize(sz);
/*  48 */     setFacingDirection(dir);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CocoaPlantSize getSize()
/*     */   {
/*  57 */     switch (getData() & 0xC) {
/*     */     case 0: 
/*  59 */       return CocoaPlantSize.SMALL;
/*     */     case 4: 
/*  61 */       return CocoaPlantSize.MEDIUM;
/*     */     }
/*  63 */     return CocoaPlantSize.LARGE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSize(CocoaPlantSize sz)
/*     */   {
/*  73 */     int dat = getData() & 0x3;
/*  74 */     switch (sz) {
/*     */     case LARGE: 
/*     */       break;
/*     */     case MEDIUM: 
/*  78 */       dat |= 0x4;
/*  79 */       break;
/*     */     case SMALL: 
/*  81 */       dat |= 0x8;
/*     */     }
/*     */     
/*  84 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */   public BlockFace getAttachedFace() {
/*  88 */     return getFacing().getOppositeFace();
/*     */   }
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*  92 */     int dat = getData() & 0xC;
/*  93 */     switch (face) {
/*     */     case EAST_NORTH_EAST: 
/*     */     default: 
/*     */       break;
/*     */     case EAST_SOUTH_EAST: 
/*  98 */       dat |= 0x1;
/*  99 */       break;
/*     */     case DOWN: 
/* 101 */       dat |= 0x2;
/* 102 */       break;
/*     */     case EAST: 
/* 104 */       dat |= 0x3;
/*     */     }
/*     */     
/* 107 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/* 111 */     switch (getData() & 0x3) {
/*     */     case 0: 
/* 113 */       return BlockFace.SOUTH;
/*     */     case 1: 
/* 115 */       return BlockFace.WEST;
/*     */     case 2: 
/* 117 */       return BlockFace.NORTH;
/*     */     case 3: 
/* 119 */       return BlockFace.EAST;
/*     */     }
/* 121 */     return null;
/*     */   }
/*     */   
/*     */   public CocoaPlant clone()
/*     */   {
/* 126 */     return (CocoaPlant)super.clone();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 131 */     return super.toString() + " facing " + getFacing() + " " + getSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\CocoaPlant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */