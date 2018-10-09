/*    */ package org.bukkit.material;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MonsterEggs
/*    */   extends TexturedMaterial
/*    */ {
/* 13 */   private static final List<Material> textures = new ArrayList();
/*    */   
/* 15 */   static { textures.add(Material.STONE);
/* 16 */     textures.add(Material.COBBLESTONE);
/* 17 */     textures.add(Material.SMOOTH_BRICK);
/*    */   }
/*    */   
/*    */   public MonsterEggs() {
/* 21 */     super(Material.MONSTER_EGGS);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public MonsterEggs(int type)
/*    */   {
/* 30 */     super(type);
/*    */   }
/*    */   
/*    */   public MonsterEggs(Material type) {
/* 34 */     super(textures.contains(type) ? Material.MONSTER_EGGS : type);
/* 35 */     if (textures.contains(type)) {
/* 36 */       setMaterial(type);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public MonsterEggs(int type, byte data)
/*    */   {
/* 47 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public MonsterEggs(Material type, byte data)
/*    */   {
/* 57 */     super(type, data);
/*    */   }
/*    */   
/*    */   public List<Material> getTextures()
/*    */   {
/* 62 */     return textures;
/*    */   }
/*    */   
/*    */   public MonsterEggs clone()
/*    */   {
/* 67 */     return (MonsterEggs)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\MonsterEggs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */