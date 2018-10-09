/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Torch
/*     */   extends SimpleAttachableMaterialData
/*     */ {
/*     */   public Torch()
/*     */   {
/*  11 */     super(Material.TORCH);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Torch(int type)
/*     */   {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */   public Torch(Material type) {
/*  24 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Torch(int type, byte data)
/*     */   {
/*  34 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Torch(Material type, byte data)
/*     */   {
/*  44 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockFace getAttachedFace()
/*     */   {
/*  53 */     byte data = getData();
/*     */     
/*  55 */     switch (data) {
/*     */     case 1: 
/*  57 */       return BlockFace.WEST;
/*     */     
/*     */     case 2: 
/*  60 */       return BlockFace.EAST;
/*     */     
/*     */     case 3: 
/*  63 */       return BlockFace.NORTH;
/*     */     
/*     */     case 4: 
/*  66 */       return BlockFace.SOUTH;
/*     */     }
/*     */     
/*     */     
/*  70 */     return BlockFace.DOWN; }
/*     */   
/*     */   public void setFacingDirection(BlockFace face) { byte data;
/*     */     byte data;
/*     */     byte data;
/*     */     byte data;
/*     */     byte data;
/*  77 */     switch (face) {
/*     */     case EAST: 
/*  79 */       data = 1;
/*  80 */       break;
/*     */     
/*     */     case EAST_SOUTH_EAST: 
/*  83 */       data = 2;
/*  84 */       break;
/*     */     
/*     */     case EAST_NORTH_EAST: 
/*  87 */       data = 3;
/*  88 */       break;
/*     */     
/*     */     case DOWN: 
/*  91 */       data = 4;
/*  92 */       break;
/*     */     
/*     */     case NORTH: 
/*     */     default: 
/*  96 */       data = 5;
/*     */     }
/*     */     
/*  99 */     setData(data);
/*     */   }
/*     */   
/*     */   public Torch clone()
/*     */   {
/* 104 */     return (Torch)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Torch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */