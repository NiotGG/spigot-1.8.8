/*    */ package org.bukkit.material;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SmoothBrick
/*    */   extends TexturedMaterial
/*    */ {
/* 13 */   private static final List<Material> textures = new ArrayList();
/*    */   
/* 15 */   static { textures.add(Material.STONE);
/* 16 */     textures.add(Material.MOSSY_COBBLESTONE);
/* 17 */     textures.add(Material.COBBLESTONE);
/* 18 */     textures.add(Material.SMOOTH_BRICK);
/*    */   }
/*    */   
/*    */   public SmoothBrick() {
/* 22 */     super(Material.SMOOTH_BRICK);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public SmoothBrick(int type)
/*    */   {
/* 31 */     super(type);
/*    */   }
/*    */   
/*    */   public SmoothBrick(Material type) {
/* 35 */     super(textures.contains(type) ? Material.SMOOTH_BRICK : type);
/* 36 */     if (textures.contains(type)) {
/* 37 */       setMaterial(type);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public SmoothBrick(int type, byte data)
/*    */   {
/* 48 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public SmoothBrick(Material type, byte data)
/*    */   {
/* 58 */     super(type, data);
/*    */   }
/*    */   
/*    */   public List<Material> getTextures()
/*    */   {
/* 63 */     return textures;
/*    */   }
/*    */   
/*    */   public SmoothBrick clone()
/*    */   {
/* 68 */     return (SmoothBrick)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\SmoothBrick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */