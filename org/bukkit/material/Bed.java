/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Bed
/*     */   extends MaterialData
/*     */   implements Directional
/*     */ {
/*     */   public Bed()
/*     */   {
/*  15 */     super(Material.BED_BLOCK);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Bed(BlockFace direction)
/*     */   {
/*  24 */     this();
/*  25 */     setFacingDirection(direction);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Bed(int type)
/*     */   {
/*  35 */     super(type);
/*     */   }
/*     */   
/*     */   public Bed(Material type) {
/*  39 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Bed(int type, byte data)
/*     */   {
/*  49 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Bed(Material type, byte data)
/*     */   {
/*  59 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isHeadOfBed()
/*     */   {
/*  68 */     return (getData() & 0x8) == 8;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHeadOfBed(boolean isHeadOfBed)
/*     */   {
/*  77 */     setData((byte)(isHeadOfBed ? getData() | 0x8 : getData() & 0xFFFFFFF7));
/*     */   }
/*     */   
/*     */ 
/*     */   public void setFacingDirection(BlockFace face)
/*     */   {
/*     */     byte data;
/*     */     byte data;
/*     */     byte data;
/*     */     byte data;
/*  87 */     switch (face) {
/*     */     case EAST_NORTH_EAST: 
/*  89 */       data = 0;
/*  90 */       break;
/*     */     
/*     */     case EAST_SOUTH_EAST: 
/*  93 */       data = 1;
/*  94 */       break;
/*     */     
/*     */     case DOWN: 
/*  97 */       data = 2;
/*  98 */       break;
/*     */     
/*     */     case EAST: 
/*     */     default: 
/* 102 */       data = 3;
/*     */     }
/*     */     
/* 105 */     if (isHeadOfBed()) {
/* 106 */       data = (byte)(data | 0x8);
/*     */     }
/*     */     
/* 109 */     setData(data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockFace getFacing()
/*     */   {
/* 118 */     byte data = (byte)(getData() & 0x7);
/*     */     
/* 120 */     switch (data) {
/*     */     case 0: 
/* 122 */       return BlockFace.SOUTH;
/*     */     
/*     */     case 1: 
/* 125 */       return BlockFace.WEST;
/*     */     
/*     */     case 2: 
/* 128 */       return BlockFace.NORTH;
/*     */     }
/*     */     
/*     */     
/* 132 */     return BlockFace.EAST;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 138 */     return (isHeadOfBed() ? "HEAD" : "FOOT") + " of " + super.toString() + " facing " + getFacing();
/*     */   }
/*     */   
/*     */   public Bed clone()
/*     */   {
/* 143 */     return (Bed)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Bed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */