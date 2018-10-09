/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Button
/*     */   extends SimpleAttachableMaterialData implements Redstone
/*     */ {
/*     */   public Button()
/*     */   {
/*  11 */     super(Material.STONE_BUTTON);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Button(int type)
/*     */   {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */   public Button(Material type) {
/*  24 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Button(int type, byte data)
/*     */   {
/*  34 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Button(Material type, byte data)
/*     */   {
/*  44 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isPowered()
/*     */   {
/*  54 */     return (getData() & 0x8) == 8;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPowered(boolean bool)
/*     */   {
/*  64 */     setData((byte)(bool ? getData() | 0x8 : getData() & 0xFFFFFFF7));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockFace getAttachedFace()
/*     */   {
/*  73 */     byte data = (byte)(getData() & 0x7);
/*     */     
/*  75 */     switch (data) {
/*     */     case 0: 
/*  77 */       return BlockFace.UP;
/*     */     
/*     */     case 1: 
/*  80 */       return BlockFace.WEST;
/*     */     
/*     */     case 2: 
/*  83 */       return BlockFace.EAST;
/*     */     
/*     */     case 3: 
/*  86 */       return BlockFace.NORTH;
/*     */     
/*     */     case 4: 
/*  89 */       return BlockFace.SOUTH;
/*     */     
/*     */     case 5: 
/*  92 */       return BlockFace.DOWN;
/*     */     }
/*     */     
/*  95 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setFacingDirection(BlockFace face)
/*     */   {
/* 102 */     byte data = (byte)(getData() & 0x8);
/*     */     
/* 104 */     switch (face) {
/*     */     case NORTH_EAST: 
/* 106 */       data = (byte)(data | 0x0);
/* 107 */       break;
/*     */     
/*     */     case EAST: 
/* 110 */       data = (byte)(data | 0x1);
/* 111 */       break;
/*     */     
/*     */     case EAST_SOUTH_EAST: 
/* 114 */       data = (byte)(data | 0x2);
/* 115 */       break;
/*     */     
/*     */     case EAST_NORTH_EAST: 
/* 118 */       data = (byte)(data | 0x3);
/* 119 */       break;
/*     */     
/*     */     case DOWN: 
/* 122 */       data = (byte)(data | 0x4);
/* 123 */       break;
/*     */     
/*     */     case NORTH: 
/* 126 */       data = (byte)(data | 0x5);
/*     */     }
/*     */     
/*     */     
/* 130 */     setData(data);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 135 */     return super.toString() + " " + (isPowered() ? "" : "NOT ") + "POWERED";
/*     */   }
/*     */   
/*     */   public Button clone()
/*     */   {
/* 140 */     return (Button)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Button.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */