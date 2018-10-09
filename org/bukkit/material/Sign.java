/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Sign
/*     */   extends MaterialData implements Attachable
/*     */ {
/*     */   public Sign()
/*     */   {
/*  11 */     super(Material.SIGN_POST);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Sign(int type)
/*     */   {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */   public Sign(Material type) {
/*  24 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Sign(int type, byte data)
/*     */   {
/*  34 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Sign(Material type, byte data)
/*     */   {
/*  44 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isWallSign()
/*     */   {
/*  54 */     return getItemType() == Material.WALL_SIGN;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockFace getAttachedFace()
/*     */   {
/*  63 */     if (isWallSign()) {
/*  64 */       byte data = getData();
/*     */       
/*  66 */       switch (data) {
/*     */       case 2: 
/*  68 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 3: 
/*  71 */         return BlockFace.NORTH;
/*     */       
/*     */       case 4: 
/*  74 */         return BlockFace.EAST;
/*     */       
/*     */       case 5: 
/*  77 */         return BlockFace.WEST;
/*     */       }
/*     */       
/*  80 */       return null;
/*     */     }
/*  82 */     return BlockFace.DOWN;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockFace getFacing()
/*     */   {
/*  92 */     byte data = getData();
/*     */     
/*  94 */     if (!isWallSign()) {
/*  95 */       switch (data) {
/*     */       case 0: 
/*  97 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 1: 
/* 100 */         return BlockFace.SOUTH_SOUTH_WEST;
/*     */       
/*     */       case 2: 
/* 103 */         return BlockFace.SOUTH_WEST;
/*     */       
/*     */       case 3: 
/* 106 */         return BlockFace.WEST_SOUTH_WEST;
/*     */       
/*     */       case 4: 
/* 109 */         return BlockFace.WEST;
/*     */       
/*     */       case 5: 
/* 112 */         return BlockFace.WEST_NORTH_WEST;
/*     */       
/*     */       case 6: 
/* 115 */         return BlockFace.NORTH_WEST;
/*     */       
/*     */       case 7: 
/* 118 */         return BlockFace.NORTH_NORTH_WEST;
/*     */       
/*     */       case 8: 
/* 121 */         return BlockFace.NORTH;
/*     */       
/*     */       case 9: 
/* 124 */         return BlockFace.NORTH_NORTH_EAST;
/*     */       
/*     */       case 10: 
/* 127 */         return BlockFace.NORTH_EAST;
/*     */       
/*     */       case 11: 
/* 130 */         return BlockFace.EAST_NORTH_EAST;
/*     */       
/*     */       case 12: 
/* 133 */         return BlockFace.EAST;
/*     */       
/*     */       case 13: 
/* 136 */         return BlockFace.EAST_SOUTH_EAST;
/*     */       
/*     */       case 14: 
/* 139 */         return BlockFace.SOUTH_EAST;
/*     */       
/*     */       case 15: 
/* 142 */         return BlockFace.SOUTH_SOUTH_EAST;
/*     */       }
/*     */       
/* 145 */       return null;
/*     */     }
/* 147 */     return getAttachedFace().getOppositeFace();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setFacingDirection(BlockFace face)
/*     */   {
/*     */     byte data;
/* 154 */     if (isWallSign()) { byte data;
/* 155 */       byte data; byte data; byte data; switch (face) {
/*     */       case DOWN: 
/* 157 */         data = 2;
/* 158 */         break;
/*     */       
/*     */       case EAST_NORTH_EAST: 
/* 161 */         data = 3;
/* 162 */         break;
/*     */       
/*     */       case EAST_SOUTH_EAST: 
/* 165 */         data = 4;
/* 166 */         break;
/*     */       
/*     */       case EAST: 
/*     */       default: 
/* 170 */         data = 5;
/*     */         
/* 172 */         break; } } else { byte data;
/* 173 */       byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; byte data; switch (face) {
/*     */       case EAST_NORTH_EAST: 
/* 175 */         data = 0;
/* 176 */         break;
/*     */       
/*     */       case WEST: 
/* 179 */         data = 1;
/* 180 */         break;
/*     */       
/*     */       case SELF: 
/* 183 */         data = 2;
/* 184 */         break;
/*     */       
/*     */       case WEST_NORTH_WEST: 
/* 187 */         data = 3;
/* 188 */         break;
/*     */       
/*     */       case EAST_SOUTH_EAST: 
/* 191 */         data = 4;
/* 192 */         break;
/*     */       
/*     */       case SOUTH: 
/* 195 */         data = 5;
/* 196 */         break;
/*     */       
/*     */       case NORTH_NORTH_WEST: 
/* 199 */         data = 6;
/* 200 */         break;
/*     */       
/*     */       case SOUTH_EAST: 
/* 203 */         data = 7;
/* 204 */         break;
/*     */       
/*     */       case DOWN: 
/* 207 */         data = 8;
/* 208 */         break;
/*     */       
/*     */       case SOUTH_SOUTH_EAST: 
/* 211 */         data = 9;
/* 212 */         break;
/*     */       
/*     */       case NORTH_NORTH_EAST: 
/* 215 */         data = 10;
/* 216 */         break;
/*     */       
/*     */       case SOUTH_SOUTH_WEST: 
/* 219 */         data = 11;
/* 220 */         break;
/*     */       
/*     */       case EAST: 
/* 223 */         data = 12;
/* 224 */         break;
/*     */       
/*     */       case SOUTH_WEST: 
/* 227 */         data = 13;
/* 228 */         break;
/*     */       
/*     */       case UP: 
/* 231 */         data = 15;
/* 232 */         break;
/*     */       case NORTH: case NORTH_EAST: 
/*     */       case NORTH_WEST: 
/*     */       default: 
/* 236 */         data = 14;
/*     */       }
/*     */       
/*     */     }
/* 240 */     setData(data);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 245 */     return super.toString() + " facing " + getFacing();
/*     */   }
/*     */   
/*     */   public Sign clone()
/*     */   {
/* 250 */     return (Sign)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Sign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */