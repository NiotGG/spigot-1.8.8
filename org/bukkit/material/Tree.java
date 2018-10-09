/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.TreeSpecies;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Tree
/*     */   extends MaterialData
/*     */ {
/*     */   public Tree()
/*     */   {
/*  12 */     super(Material.LOG);
/*     */   }
/*     */   
/*     */   public Tree(TreeSpecies species) {
/*  16 */     this();
/*  17 */     setSpecies(species);
/*     */   }
/*     */   
/*     */   public Tree(TreeSpecies species, BlockFace dir) {
/*  21 */     this();
/*  22 */     setSpecies(species);
/*  23 */     setDirection(dir);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Tree(int type)
/*     */   {
/*  32 */     super(type);
/*     */   }
/*     */   
/*     */   public Tree(Material type) {
/*  36 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Tree(int type, byte data)
/*     */   {
/*  46 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Tree(Material type, byte data)
/*     */   {
/*  56 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TreeSpecies getSpecies()
/*     */   {
/*  65 */     return TreeSpecies.getByData((byte)(getData() & 0x3));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSpecies(TreeSpecies species)
/*     */   {
/*  74 */     setData((byte)(getData() & 0xC | species.getData()));
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
/*     */ 
/*     */   public BlockFace getDirection()
/*     */   {
/*  89 */     switch (getData() >> 2 & 0x3) {
/*     */     case 0: 
/*     */     default: 
/*  92 */       return BlockFace.UP;
/*     */     case 1: 
/*  94 */       return BlockFace.WEST;
/*     */     case 2: 
/*  96 */       return BlockFace.NORTH;
/*     */     }
/*  98 */     return BlockFace.SELF;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDirection(BlockFace dir)
/*     */   {
/*     */     int dat;
/*     */     int dat;
/*     */     int dat;
/*     */     int dat;
/* 108 */     switch (dir) {
/*     */     case NORTH: 
/*     */     case NORTH_EAST: 
/*     */     default: 
/* 112 */       dat = 0;
/* 113 */       break;
/*     */     case EAST: 
/*     */     case EAST_SOUTH_EAST: 
/* 116 */       dat = 1;
/* 117 */       break;
/*     */     case DOWN: 
/*     */     case EAST_NORTH_EAST: 
/* 120 */       dat = 2;
/* 121 */       break;
/*     */     case WEST_SOUTH_WEST: 
/* 123 */       dat = 3;
/*     */     }
/*     */     
/* 126 */     setData((byte)(getData() & 0x3 | dat << 2));
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 131 */     return getSpecies() + " " + getDirection() + " " + super.toString();
/*     */   }
/*     */   
/*     */   public Tree clone()
/*     */   {
/* 136 */     return (Tree)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Tree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */