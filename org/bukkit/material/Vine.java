/*     */ package org.bukkit.material;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vine
/*     */   extends MaterialData
/*     */ {
/*     */   private static final int VINE_NORTH = 4;
/*     */   private static final int VINE_EAST = 8;
/*     */   private static final int VINE_WEST = 2;
/*     */   private static final int VINE_SOUTH = 1;
/*  17 */   EnumSet<BlockFace> possibleFaces = EnumSet.of(BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST);
/*     */   
/*     */   public Vine() {
/*  20 */     super(Material.VINE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Vine(int type, byte data)
/*     */   {
/*  30 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Vine(byte data)
/*     */   {
/*  39 */     super(Material.VINE, data);
/*     */   }
/*     */   
/*     */   public Vine(BlockFace... faces) {
/*  43 */     this(EnumSet.copyOf(Arrays.asList(faces)));
/*     */   }
/*     */   
/*     */   public Vine(EnumSet<BlockFace> faces) {
/*  47 */     this((byte)0);
/*  48 */     faces.retainAll(this.possibleFaces);
/*     */     
/*  50 */     byte data = 0;
/*     */     
/*  52 */     if (faces.contains(BlockFace.WEST)) {
/*  53 */       data = (byte)(data | 0x2);
/*     */     }
/*     */     
/*  56 */     if (faces.contains(BlockFace.NORTH)) {
/*  57 */       data = (byte)(data | 0x4);
/*     */     }
/*     */     
/*  60 */     if (faces.contains(BlockFace.SOUTH)) {
/*  61 */       data = (byte)(data | 0x1);
/*     */     }
/*     */     
/*  64 */     if (faces.contains(BlockFace.EAST)) {
/*  65 */       data = (byte)(data | 0x8);
/*     */     }
/*     */     
/*  68 */     setData(data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isOnFace(BlockFace face)
/*     */   {
/*  80 */     switch (face) {
/*     */     case EAST_SOUTH_EAST: 
/*  82 */       return (getData() & 0x2) == 2;
/*     */     case DOWN: 
/*  84 */       return (getData() & 0x4) == 4;
/*     */     case EAST_NORTH_EAST: 
/*  86 */       return (getData() & 0x1) == 1;
/*     */     case EAST: 
/*  88 */       return (getData() & 0x8) == 8;
/*     */     case NORTH_NORTH_EAST: 
/*  90 */       return (isOnFace(BlockFace.EAST)) && (isOnFace(BlockFace.NORTH));
/*     */     case NORTH_NORTH_WEST: 
/*  92 */       return (isOnFace(BlockFace.WEST)) && (isOnFace(BlockFace.NORTH));
/*     */     case NORTH_WEST: 
/*  94 */       return (isOnFace(BlockFace.EAST)) && (isOnFace(BlockFace.SOUTH));
/*     */     case SELF: 
/*  96 */       return (isOnFace(BlockFace.WEST)) && (isOnFace(BlockFace.SOUTH));
/*     */     case NORTH: 
/*  98 */       return true;
/*     */     }
/* 100 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void putOnFace(BlockFace face)
/*     */   {
/* 110 */     switch (face) {
/*     */     case EAST_SOUTH_EAST: 
/* 112 */       setData((byte)(getData() | 0x2));
/* 113 */       break;
/*     */     case DOWN: 
/* 115 */       setData((byte)(getData() | 0x4));
/* 116 */       break;
/*     */     case EAST_NORTH_EAST: 
/* 118 */       setData((byte)(getData() | 0x1));
/* 119 */       break;
/*     */     case EAST: 
/* 121 */       setData((byte)(getData() | 0x8));
/* 122 */       break;
/*     */     case NORTH_NORTH_WEST: 
/* 124 */       putOnFace(BlockFace.WEST);
/* 125 */       putOnFace(BlockFace.NORTH);
/* 126 */       break;
/*     */     case SELF: 
/* 128 */       putOnFace(BlockFace.WEST);
/* 129 */       putOnFace(BlockFace.SOUTH);
/* 130 */       break;
/*     */     case NORTH_NORTH_EAST: 
/* 132 */       putOnFace(BlockFace.EAST);
/* 133 */       putOnFace(BlockFace.NORTH);
/* 134 */       break;
/*     */     case NORTH_WEST: 
/* 136 */       putOnFace(BlockFace.EAST);
/* 137 */       putOnFace(BlockFace.SOUTH);
/* 138 */       break;
/*     */     case NORTH: 
/*     */       break;
/*     */     case NORTH_EAST: default: 
/* 142 */       throw new IllegalArgumentException("Vines can't go on face " + face.toString());
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeFromFace(BlockFace face)
/*     */   {
/* 152 */     switch (face) {
/*     */     case EAST_SOUTH_EAST: 
/* 154 */       setData((byte)(getData() & 0xFFFFFFFD));
/* 155 */       break;
/*     */     case DOWN: 
/* 157 */       setData((byte)(getData() & 0xFFFFFFFB));
/* 158 */       break;
/*     */     case EAST_NORTH_EAST: 
/* 160 */       setData((byte)(getData() & 0xFFFFFFFE));
/* 161 */       break;
/*     */     case EAST: 
/* 163 */       setData((byte)(getData() & 0xFFFFFFF7));
/* 164 */       break;
/*     */     case NORTH_NORTH_WEST: 
/* 166 */       removeFromFace(BlockFace.WEST);
/* 167 */       removeFromFace(BlockFace.NORTH);
/* 168 */       break;
/*     */     case SELF: 
/* 170 */       removeFromFace(BlockFace.WEST);
/* 171 */       removeFromFace(BlockFace.SOUTH);
/* 172 */       break;
/*     */     case NORTH_NORTH_EAST: 
/* 174 */       removeFromFace(BlockFace.EAST);
/* 175 */       removeFromFace(BlockFace.NORTH);
/* 176 */       break;
/*     */     case NORTH_WEST: 
/* 178 */       removeFromFace(BlockFace.EAST);
/* 179 */       removeFromFace(BlockFace.SOUTH);
/* 180 */       break;
/*     */     case NORTH: 
/*     */       break;
/*     */     case NORTH_EAST: default: 
/* 184 */       throw new IllegalArgumentException("Vines can't go on face " + face.toString());
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 190 */     return "VINE";
/*     */   }
/*     */   
/*     */   public Vine clone()
/*     */   {
/* 195 */     return (Vine)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Vine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */