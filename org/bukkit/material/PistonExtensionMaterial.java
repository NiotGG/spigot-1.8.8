/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PistonExtensionMaterial
/*     */   extends MaterialData
/*     */   implements Attachable
/*     */ {
/*     */   @Deprecated
/*     */   public PistonExtensionMaterial(int type)
/*     */   {
/*  16 */     super(type);
/*     */   }
/*     */   
/*     */   public PistonExtensionMaterial(Material type) {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public PistonExtensionMaterial(int type, byte data)
/*     */   {
/*  30 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public PistonExtensionMaterial(Material type, byte data)
/*     */   {
/*  40 */     super(type, data);
/*     */   }
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*  44 */     byte data = (byte)(getData() & 0x8);
/*     */     
/*  46 */     switch (face) {
/*     */     case NORTH: 
/*  48 */       data = (byte)(data | 0x1);
/*  49 */       break;
/*     */     case DOWN: 
/*  51 */       data = (byte)(data | 0x2);
/*  52 */       break;
/*     */     case EAST_NORTH_EAST: 
/*  54 */       data = (byte)(data | 0x3);
/*  55 */       break;
/*     */     case EAST_SOUTH_EAST: 
/*  57 */       data = (byte)(data | 0x4);
/*  58 */       break;
/*     */     case EAST: 
/*  60 */       data = (byte)(data | 0x5);
/*     */     }
/*     */     
/*  63 */     setData(data);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/*  67 */     byte dir = (byte)(getData() & 0x7);
/*     */     
/*  69 */     switch (dir) {
/*     */     case 0: 
/*  71 */       return BlockFace.DOWN;
/*     */     case 1: 
/*  73 */       return BlockFace.UP;
/*     */     case 2: 
/*  75 */       return BlockFace.NORTH;
/*     */     case 3: 
/*  77 */       return BlockFace.SOUTH;
/*     */     case 4: 
/*  79 */       return BlockFace.WEST;
/*     */     case 5: 
/*  81 */       return BlockFace.EAST;
/*     */     }
/*  83 */     return BlockFace.SELF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSticky()
/*     */   {
/*  93 */     return (getData() & 0x8) == 8;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSticky(boolean sticky)
/*     */   {
/* 102 */     setData((byte)(sticky ? getData() | 0x8 : getData() & 0xFFFFFFF7));
/*     */   }
/*     */   
/*     */   public BlockFace getAttachedFace() {
/* 106 */     return getFacing().getOppositeFace();
/*     */   }
/*     */   
/*     */   public PistonExtensionMaterial clone()
/*     */   {
/* 111 */     return (PistonExtensionMaterial)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\PistonExtensionMaterial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */