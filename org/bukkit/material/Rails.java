/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ public class Rails
/*     */   extends MaterialData
/*     */ {
/*     */   public Rails()
/*     */   {
/*  12 */     super(Material.RAILS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Rails(int type)
/*     */   {
/*  21 */     super(type);
/*     */   }
/*     */   
/*     */   public Rails(Material type) {
/*  25 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Rails(int type, byte data)
/*     */   {
/*  35 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Rails(Material type, byte data)
/*     */   {
/*  45 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isOnSlope()
/*     */   {
/*  52 */     byte d = getConvertedData();
/*     */     
/*  54 */     return (d == 2) || (d == 3) || (d == 4) || (d == 5);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isCurve()
/*     */   {
/*  61 */     byte d = getConvertedData();
/*     */     
/*  63 */     return (d == 6) || (d == 7) || (d == 8) || (d == 9);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockFace getDirection()
/*     */   {
/*  74 */     byte d = getConvertedData();
/*     */     
/*  76 */     switch (d) {
/*     */     case 0: 
/*     */     default: 
/*  79 */       return BlockFace.SOUTH;
/*     */     
/*     */     case 1: 
/*  82 */       return BlockFace.EAST;
/*     */     
/*     */     case 2: 
/*  85 */       return BlockFace.EAST;
/*     */     
/*     */     case 3: 
/*  88 */       return BlockFace.WEST;
/*     */     
/*     */     case 4: 
/*  91 */       return BlockFace.NORTH;
/*     */     
/*     */     case 5: 
/*  94 */       return BlockFace.SOUTH;
/*     */     
/*     */     case 6: 
/*  97 */       return BlockFace.NORTH_WEST;
/*     */     
/*     */     case 7: 
/* 100 */       return BlockFace.NORTH_EAST;
/*     */     
/*     */     case 8: 
/* 103 */       return BlockFace.SOUTH_EAST;
/*     */     }
/*     */     
/* 106 */     return BlockFace.SOUTH_WEST;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 112 */     return super.toString() + " facing " + getDirection() + (isOnSlope() ? " on a slope" : isCurve() ? " on a curve" : "");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected byte getConvertedData()
/*     */   {
/* 125 */     return getData();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDirection(BlockFace face, boolean isOnSlope)
/*     */   {
/* 139 */     switch (face) {
/*     */     case EAST: 
/* 141 */       setData((byte)(isOnSlope ? 2 : 1));
/* 142 */       break;
/*     */     
/*     */     case EAST_SOUTH_EAST: 
/* 145 */       setData((byte)(isOnSlope ? 3 : 1));
/* 146 */       break;
/*     */     
/*     */     case DOWN: 
/* 149 */       setData((byte)(isOnSlope ? 4 : 0));
/* 150 */       break;
/*     */     
/*     */     case EAST_NORTH_EAST: 
/* 153 */       setData((byte)(isOnSlope ? 5 : 0));
/* 154 */       break;
/*     */     
/*     */     case NORTH_NORTH_WEST: 
/* 157 */       setData((byte)6);
/* 158 */       break;
/*     */     
/*     */     case NORTH_NORTH_EAST: 
/* 161 */       setData((byte)7);
/* 162 */       break;
/*     */     
/*     */     case NORTH_WEST: 
/* 165 */       setData((byte)8);
/* 166 */       break;
/*     */     
/*     */     case SELF: 
/* 169 */       setData((byte)9);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public Rails clone()
/*     */   {
/* 176 */     return (Rails)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Rails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */