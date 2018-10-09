/*     */ package org.bukkit.material;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.Material;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TexturedMaterial
/*     */   extends MaterialData
/*     */ {
/*     */   public TexturedMaterial(Material m)
/*     */   {
/*  13 */     super(m);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public TexturedMaterial(int type)
/*     */   {
/*  22 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public TexturedMaterial(int type, byte data)
/*     */   {
/*  32 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public TexturedMaterial(Material type, byte data)
/*     */   {
/*  42 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract List<Material> getTextures();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Material getMaterial()
/*     */   {
/*  59 */     int n = getTextureIndex();
/*  60 */     if (n > getTextures().size() - 1) {
/*  61 */       n = 0;
/*     */     }
/*     */     
/*  64 */     return (Material)getTextures().get(n);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMaterial(Material material)
/*     */   {
/*  74 */     if (getTextures().contains(material)) {
/*  75 */       setTextureIndex(getTextures().indexOf(material));
/*     */     } else {
/*  77 */       setTextureIndex(0);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected int getTextureIndex()
/*     */   {
/*  89 */     return getData();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected void setTextureIndex(int idx)
/*     */   {
/* 100 */     setData((byte)idx);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 105 */     return getMaterial() + " " + super.toString();
/*     */   }
/*     */   
/*     */   public TexturedMaterial clone()
/*     */   {
/* 110 */     return (TexturedMaterial)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\TexturedMaterial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */