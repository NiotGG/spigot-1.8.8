/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Skull
/*     */   extends MaterialData implements Directional
/*     */ {
/*     */   public Skull()
/*     */   {
/*  11 */     super(Material.SKULL);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Skull(BlockFace direction)
/*     */   {
/*  20 */     this();
/*  21 */     setFacingDirection(direction);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Skull(int type)
/*     */   {
/*  30 */     super(type);
/*     */   }
/*     */   
/*     */   public Skull(Material type) {
/*  34 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Skull(int type, byte data)
/*     */   {
/*  44 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*  54 */   public Skull(Material type, byte data) { super(type, data); }
/*     */   
/*     */   public void setFacingDirection(BlockFace face) { int data;
/*     */     int data;
/*     */     int data;
/*     */     int data;
/*  60 */     int data; switch (face) {
/*     */     case WEST_SOUTH_WEST: 
/*     */     default: 
/*  63 */       data = 1;
/*  64 */       break;
/*     */     
/*     */     case DOWN: 
/*  67 */       data = 2;
/*  68 */       break;
/*     */     
/*     */     case EAST: 
/*  71 */       data = 4;
/*  72 */       break;
/*     */     
/*     */     case EAST_NORTH_EAST: 
/*  75 */       data = 3;
/*  76 */       break;
/*     */     
/*     */     case EAST_SOUTH_EAST: 
/*  79 */       data = 5;
/*     */     }
/*     */     
/*  82 */     setData((byte)data);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/*  86 */     int data = getData();
/*     */     
/*  88 */     switch (data) {
/*     */     case 1: 
/*     */     default: 
/*  91 */       return BlockFace.SELF;
/*     */     
/*     */     case 2: 
/*  94 */       return BlockFace.NORTH;
/*     */     
/*     */     case 3: 
/*  97 */       return BlockFace.SOUTH;
/*     */     
/*     */     case 4: 
/* 100 */       return BlockFace.EAST;
/*     */     }
/*     */     
/* 103 */     return BlockFace.WEST;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 109 */     return super.toString() + " facing " + getFacing();
/*     */   }
/*     */   
/*     */   public Skull clone()
/*     */   {
/* 114 */     return (Skull)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Skull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */