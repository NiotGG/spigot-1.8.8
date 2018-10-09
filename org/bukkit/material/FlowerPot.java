/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.GrassSpecies;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.TreeSpecies;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlowerPot
/*     */   extends MaterialData
/*     */ {
/*     */   public FlowerPot()
/*     */   {
/*  16 */     super(Material.FLOWER_POT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public FlowerPot(int type)
/*     */   {
/*  25 */     super(type);
/*     */   }
/*     */   
/*     */   public FlowerPot(Material type) {
/*  29 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public FlowerPot(int type, byte data)
/*     */   {
/*  39 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public FlowerPot(Material type, byte data)
/*     */   {
/*  49 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MaterialData getContents()
/*     */   {
/*  59 */     switch (getData()) {
/*     */     case 1: 
/*  61 */       return new MaterialData(Material.RED_ROSE);
/*     */     case 2: 
/*  63 */       return new MaterialData(Material.YELLOW_FLOWER);
/*     */     case 3: 
/*  65 */       return new Tree(TreeSpecies.GENERIC);
/*     */     case 4: 
/*  67 */       return new Tree(TreeSpecies.REDWOOD);
/*     */     case 5: 
/*  69 */       return new Tree(TreeSpecies.BIRCH);
/*     */     case 6: 
/*  71 */       return new Tree(TreeSpecies.JUNGLE);
/*     */     case 7: 
/*  73 */       return new MaterialData(Material.RED_MUSHROOM);
/*     */     case 8: 
/*  75 */       return new MaterialData(Material.BROWN_MUSHROOM);
/*     */     case 9: 
/*  77 */       return new MaterialData(Material.CACTUS);
/*     */     case 10: 
/*  79 */       return new MaterialData(Material.DEAD_BUSH);
/*     */     case 11: 
/*  81 */       return new LongGrass(GrassSpecies.FERN_LIKE);
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setContents(MaterialData materialData)
/*     */   {
/*  93 */     Material mat = materialData.getItemType();
/*     */     
/*  95 */     if (mat == Material.RED_ROSE) {
/*  96 */       setData((byte)1);
/*  97 */     } else if (mat == Material.YELLOW_FLOWER) {
/*  98 */       setData((byte)2);
/*  99 */     } else if (mat == Material.RED_MUSHROOM) {
/* 100 */       setData((byte)7);
/* 101 */     } else if (mat == Material.BROWN_MUSHROOM) {
/* 102 */       setData((byte)8);
/* 103 */     } else if (mat == Material.CACTUS) {
/* 104 */       setData((byte)9);
/* 105 */     } else if (mat == Material.DEAD_BUSH) {
/* 106 */       setData((byte)10);
/* 107 */     } else if (mat == Material.SAPLING) {
/* 108 */       TreeSpecies species = ((Tree)materialData).getSpecies();
/*     */       
/* 110 */       if (species == TreeSpecies.GENERIC) {
/* 111 */         setData((byte)3);
/* 112 */       } else if (species == TreeSpecies.REDWOOD) {
/* 113 */         setData((byte)4);
/* 114 */       } else if (species == TreeSpecies.BIRCH) {
/* 115 */         setData((byte)5);
/*     */       } else {
/* 117 */         setData((byte)6);
/*     */       }
/* 119 */     } else if (mat == Material.LONG_GRASS) {
/* 120 */       GrassSpecies species = ((LongGrass)materialData).getSpecies();
/*     */       
/* 122 */       if (species == GrassSpecies.FERN_LIKE) {
/* 123 */         setData((byte)11);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 130 */     return super.toString() + " containing " + getContents();
/*     */   }
/*     */   
/*     */   public FlowerPot clone()
/*     */   {
/* 135 */     return (FlowerPot)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\FlowerPot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */