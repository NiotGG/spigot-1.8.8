/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Ladder
/*     */   extends SimpleAttachableMaterialData
/*     */ {
/*     */   public Ladder()
/*     */   {
/*  11 */     super(Material.LADDER);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Ladder(int type)
/*     */   {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */   public Ladder(Material type) {
/*  24 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Ladder(int type, byte data)
/*     */   {
/*  34 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Ladder(Material type, byte data)
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
/*     */     case 2: 
/*  57 */       return BlockFace.SOUTH;
/*     */     
/*     */     case 3: 
/*  60 */       return BlockFace.NORTH;
/*     */     
/*     */     case 4: 
/*  63 */       return BlockFace.EAST;
/*     */     
/*     */     case 5: 
/*  66 */       return BlockFace.WEST;
/*     */     }
/*     */     
/*  69 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setFacingDirection(BlockFace face)
/*     */   {
/*  76 */     byte data = 0;
/*     */     
/*  78 */     switch (face) {
/*     */     case EAST_NORTH_EAST: 
/*  80 */       data = 2;
/*  81 */       break;
/*     */     
/*     */     case DOWN: 
/*  84 */       data = 3;
/*  85 */       break;
/*     */     
/*     */     case EAST: 
/*  88 */       data = 4;
/*  89 */       break;
/*     */     
/*     */     case EAST_SOUTH_EAST: 
/*  92 */       data = 5;
/*     */     }
/*     */     
/*     */     
/*  96 */     setData(data);
/*     */   }
/*     */   
/*     */ 
/*     */   public Ladder clone()
/*     */   {
/* 102 */     return (Ladder)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Ladder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */