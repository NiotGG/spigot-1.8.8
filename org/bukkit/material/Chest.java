/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ public class Chest
/*    */   extends DirectionalContainer
/*    */ {
/*    */   public Chest()
/*    */   {
/* 12 */     super(Material.CHEST);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Chest(BlockFace direction)
/*    */   {
/* 21 */     this();
/* 22 */     setFacingDirection(direction);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Chest(int type)
/*    */   {
/* 31 */     super(type);
/*    */   }
/*    */   
/*    */   public Chest(Material type) {
/* 35 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Chest(int type, byte data)
/*    */   {
/* 45 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Chest(Material type, byte data)
/*    */   {
/* 55 */     super(type, data);
/*    */   }
/*    */   
/*    */   public Chest clone()
/*    */   {
/* 60 */     return (Chest)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Chest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */