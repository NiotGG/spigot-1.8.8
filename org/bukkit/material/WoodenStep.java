/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.TreeSpecies;
/*     */ 
/*     */ 
/*     */ public class WoodenStep
/*     */   extends MaterialData
/*     */ {
/*     */   public WoodenStep()
/*     */   {
/*  12 */     super(Material.WOOD_STEP);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public WoodenStep(int type)
/*     */   {
/*  21 */     super(type);
/*     */   }
/*     */   
/*     */   public WoodenStep(TreeSpecies species) {
/*  25 */     this();
/*  26 */     setSpecies(species);
/*     */   }
/*     */   
/*     */   public WoodenStep(TreeSpecies species, boolean inv) {
/*  30 */     this();
/*  31 */     setSpecies(species);
/*  32 */     setInverted(inv);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public WoodenStep(int type, byte data)
/*     */   {
/*  42 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public WoodenStep(Material type, byte data)
/*     */   {
/*  52 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TreeSpecies getSpecies()
/*     */   {
/*  61 */     return TreeSpecies.getByData((byte)(getData() & 0x3));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSpecies(TreeSpecies species)
/*     */   {
/*  70 */     setData((byte)(getData() & 0xC | species.getData()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInverted()
/*     */   {
/*  79 */     return (getData() & 0x8) != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInverted(boolean inv)
/*     */   {
/*  89 */     int dat = getData() & 0x7;
/*  90 */     if (inv) {
/*  91 */       dat |= 0x8;
/*     */     }
/*  93 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */   public WoodenStep clone()
/*     */   {
/*  98 */     return (WoodenStep)super.clone();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 103 */     return super.toString() + " " + getSpecies() + (isInverted() ? " inverted" : "");
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\WoodenStep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */