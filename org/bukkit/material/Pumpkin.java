/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Pumpkin
/*     */   extends MaterialData
/*     */   implements Directional
/*     */ {
/*     */   public Pumpkin()
/*     */   {
/*  12 */     super(Material.PUMPKIN);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Pumpkin(BlockFace direction)
/*     */   {
/*  21 */     this();
/*  22 */     setFacingDirection(direction);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Pumpkin(int type)
/*     */   {
/*  31 */     super(type);
/*     */   }
/*     */   
/*     */   public Pumpkin(Material type) {
/*  35 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Pumpkin(int type, byte data)
/*     */   {
/*  45 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Pumpkin(Material type, byte data)
/*     */   {
/*  55 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*  59 */   public boolean isLit() { return getItemType() == Material.JACK_O_LANTERN; }
/*     */   
/*     */   public void setFacingDirection(BlockFace face) { byte data;
/*     */     byte data;
/*     */     byte data;
/*     */     byte data;
/*  65 */     switch (face) {
/*     */     case DOWN: 
/*  67 */       data = 0;
/*  68 */       break;
/*     */     
/*     */     case EAST: 
/*  71 */       data = 1;
/*  72 */       break;
/*     */     
/*     */     case EAST_NORTH_EAST: 
/*  75 */       data = 2;
/*  76 */       break;
/*     */     
/*     */     case EAST_SOUTH_EAST: 
/*     */     default: 
/*  80 */       data = 3;
/*     */     }
/*     */     
/*  83 */     setData(data);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/*  87 */     byte data = getData();
/*     */     
/*  89 */     switch (data) {
/*     */     case 0: 
/*  91 */       return BlockFace.NORTH;
/*     */     
/*     */     case 1: 
/*  94 */       return BlockFace.EAST;
/*     */     
/*     */     case 2: 
/*  97 */       return BlockFace.SOUTH;
/*     */     }
/*     */     
/*     */     
/* 101 */     return BlockFace.EAST;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 107 */     return super.toString() + " facing " + getFacing() + " " + (isLit() ? "" : "NOT ") + "LIT";
/*     */   }
/*     */   
/*     */   public Pumpkin clone()
/*     */   {
/* 112 */     return (Pumpkin)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Pumpkin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */