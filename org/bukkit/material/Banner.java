/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Banner extends MaterialData implements Attachable
/*     */ {
/*     */   public Banner()
/*     */   {
/*   9 */     super(org.bukkit.Material.BANNER);
/*     */   }
/*     */   
/*     */   public Banner(org.bukkit.Material type) {
/*  13 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Banner(int type)
/*     */   {
/*  22 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Banner(org.bukkit.Material type, byte data)
/*     */   {
/*  33 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Banner(int type, byte data)
/*     */   {
/*  43 */     super(type, data);
/*     */   }
/*     */   
/*     */   public boolean isWallBanner() {
/*  47 */     return getItemType() == org.bukkit.Material.WALL_BANNER;
/*     */   }
/*     */   
/*     */   public BlockFace getAttachedFace() {
/*  51 */     if (isWallBanner()) {
/*  52 */       byte data = getData();
/*     */       
/*  54 */       switch (data) {
/*     */       case 2: 
/*  56 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 3: 
/*  59 */         return BlockFace.NORTH;
/*     */       
/*     */       case 4: 
/*  62 */         return BlockFace.EAST;
/*     */       
/*     */       case 5: 
/*  65 */         return BlockFace.WEST;
/*     */       }
/*     */       
/*  68 */       return null;
/*     */     }
/*  70 */     return BlockFace.DOWN;
/*     */   }
/*     */   
/*     */   public BlockFace getFacing()
/*     */   {
/*  75 */     byte data = getData();
/*     */     
/*  77 */     if (!isWallBanner()) {
/*  78 */       switch (data) {
/*     */       case 0: 
/*  80 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 1: 
/*  83 */         return BlockFace.SOUTH_SOUTH_WEST;
/*     */       
/*     */       case 2: 
/*  86 */         return BlockFace.SOUTH_WEST;
/*     */       
/*     */       case 3: 
/*  89 */         return BlockFace.WEST_SOUTH_WEST;
/*     */       
/*     */       case 4: 
/*  92 */         return BlockFace.WEST;
/*     */       
/*     */       case 5: 
/*  95 */         return BlockFace.WEST_NORTH_WEST;
/*     */       
/*     */       case 6: 
/*  98 */         return BlockFace.NORTH_WEST;
/*     */       
/*     */       case 7: 
/* 101 */         return BlockFace.NORTH_NORTH_WEST;
/*     */       
/*     */       case 8: 
/* 104 */         return BlockFace.NORTH;
/*     */       
/*     */       case 9: 
/* 107 */         return BlockFace.NORTH_NORTH_EAST;
/*     */       
/*     */       case 10: 
/* 110 */         return BlockFace.NORTH_EAST;
/*     */       
/*     */       case 11: 
/* 113 */         return BlockFace.EAST_NORTH_EAST;
/*     */       
/*     */       case 12: 
/* 116 */         return BlockFace.EAST;
/*     */       
/*     */       case 13: 
/* 119 */         return BlockFace.EAST_SOUTH_EAST;
/*     */       
/*     */       case 14: 
/* 122 */         return BlockFace.SOUTH_EAST;
/*     */       
/*     */       case 15: 
/* 125 */         return BlockFace.SOUTH_SOUTH_EAST;
/*     */       }
/*     */       
/* 128 */       return null;
/*     */     }
/* 130 */     return getAttachedFace().getOppositeFace();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setFacingDirection(BlockFace face)
/*     */   {
/*     */     byte data;
/* 137 */     if (isWallBanner()) { byte data;
/* 138 */       byte data; byte data; byte data; switch (face) {
/*     */       case DOWN: 
/* 140 */         data = 2;
/* 141 */         break;
/*     */       
/*     */       case EAST_NORTH_EAST: 
/* 144 */         data = 3;
/* 145 */         break;
/*     */       
/*     */       case EAST_SOUTH_EAST: 
/* 148 */         data = 4;
/* 149 */         break;
/*     */       
/*     */       case EAST: 
/*     */       default: 
/* 153 */         data = 5;
/*     */         
/* 155 */         break; } } else { byte data;
/* 156 */       byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; switch (face) {
/*     */       case EAST_NORTH_EAST: 
/* 158 */         data = 0;
/* 159 */         break;
/*     */       
/*     */       case WEST: 
/* 162 */         data = 1;
/* 163 */         break;
/*     */       
/*     */       case SELF: 
/* 166 */         data = 2;
/* 167 */         break;
/*     */       
/*     */       case WEST_NORTH_WEST: 
/* 170 */         data = 3;
/* 171 */         break;
/*     */       
/*     */       case EAST_SOUTH_EAST: 
/* 174 */         data = 4;
/* 175 */         break;
/*     */       
/*     */       case SOUTH: 
/* 178 */         data = 5;
/* 179 */         break;
/*     */       
/*     */       case NORTH_NORTH_WEST: 
/* 182 */         data = 6;
/* 183 */         break;
/*     */       
/*     */       case SOUTH_EAST: 
/* 186 */         data = 7;
/* 187 */         break;
/*     */       
/*     */       case DOWN: 
/* 190 */         data = 8;
/* 191 */         break;
/*     */       
/*     */       case SOUTH_SOUTH_EAST: 
/* 194 */         data = 9;
/* 195 */         break;
/*     */       
/*     */       case NORTH_NORTH_EAST: 
/* 198 */         data = 10;
/* 199 */         break;
/*     */       
/*     */       case SOUTH_SOUTH_WEST: 
/* 202 */         data = 11;
/* 203 */         break;
/*     */       
/*     */       case EAST: 
/* 206 */         data = 12;
/* 207 */         break;
/*     */       
/*     */       case SOUTH_WEST: 
/* 210 */         data = 13;
/* 211 */         break;
/*     */       
/*     */       case UP: 
/* 214 */         data = 15;
/* 215 */         break;
/*     */       case NORTH: case NORTH_EAST: 
/*     */       case NORTH_WEST: 
/*     */       default: 
/* 219 */         data = 14;
/*     */       }
/*     */       
/*     */     }
/* 223 */     setData(data);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 228 */     return super.toString() + " facing " + getFacing();
/*     */   }
/*     */   
/*     */   public Banner clone()
/*     */   {
/* 233 */     return (Banner)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Banner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */