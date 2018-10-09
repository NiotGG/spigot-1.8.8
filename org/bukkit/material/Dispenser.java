/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ public class Dispenser
/*     */   extends FurnaceAndDispenser
/*     */ {
/*     */   public Dispenser()
/*     */   {
/*  12 */     super(Material.DISPENSER);
/*     */   }
/*     */   
/*     */   public Dispenser(BlockFace direction) {
/*  16 */     this();
/*  17 */     setFacingDirection(direction);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Dispenser(int type)
/*     */   {
/*  26 */     super(type);
/*     */   }
/*     */   
/*     */   public Dispenser(Material type) {
/*  30 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Dispenser(int type, byte data)
/*     */   {
/*  40 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*  50 */   public Dispenser(Material type, byte data) { super(type, data); }
/*     */   
/*     */   public void setFacingDirection(BlockFace face) { byte data;
/*     */     byte data;
/*     */     byte data;
/*     */     byte data;
/*  56 */     byte data; byte data; switch (face) {
/*     */     case NORTH_EAST: 
/*  58 */       data = 0;
/*  59 */       break;
/*     */     
/*     */     case NORTH: 
/*  62 */       data = 1;
/*  63 */       break;
/*     */     
/*     */     case DOWN: 
/*  66 */       data = 2;
/*  67 */       break;
/*     */     
/*     */     case EAST_NORTH_EAST: 
/*  70 */       data = 3;
/*  71 */       break;
/*     */     
/*     */     case EAST_SOUTH_EAST: 
/*  74 */       data = 4;
/*  75 */       break;
/*     */     
/*     */     case EAST: 
/*     */     default: 
/*  79 */       data = 5;
/*     */     }
/*     */     
/*  82 */     setData(data);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/*  86 */     int data = getData() & 0x7;
/*     */     
/*  88 */     switch (data) {
/*     */     case 0: 
/*  90 */       return BlockFace.DOWN;
/*     */     
/*     */     case 1: 
/*  93 */       return BlockFace.UP;
/*     */     
/*     */     case 2: 
/*  96 */       return BlockFace.NORTH;
/*     */     
/*     */     case 3: 
/*  99 */       return BlockFace.SOUTH;
/*     */     
/*     */     case 4: 
/* 102 */       return BlockFace.WEST;
/*     */     }
/*     */     
/*     */     
/* 106 */     return BlockFace.EAST;
/*     */   }
/*     */   
/*     */ 
/*     */   public Dispenser clone()
/*     */   {
/* 112 */     return (Dispenser)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Dispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */