/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.GrassSpecies;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class LongGrass
/*    */   extends MaterialData
/*    */ {
/*    */   public LongGrass()
/*    */   {
/* 11 */     super(Material.LONG_GRASS);
/*    */   }
/*    */   
/*    */   public LongGrass(GrassSpecies species) {
/* 15 */     this();
/* 16 */     setSpecies(species);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public LongGrass(int type)
/*    */   {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public LongGrass(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public LongGrass(int type, byte data)
/*    */   {
/* 39 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public LongGrass(Material type, byte data)
/*    */   {
/* 49 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public GrassSpecies getSpecies()
/*    */   {
/* 58 */     return GrassSpecies.getByData(getData());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setSpecies(GrassSpecies species)
/*    */   {
/* 67 */     setData(species.getData());
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 72 */     return getSpecies() + " " + super.toString();
/*    */   }
/*    */   
/*    */   public LongGrass clone()
/*    */   {
/* 77 */     return (LongGrass)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\LongGrass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */