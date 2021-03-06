/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ public class EnderChest
/*    */   extends DirectionalContainer
/*    */ {
/*    */   public EnderChest()
/*    */   {
/* 12 */     super(Material.ENDER_CHEST);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EnderChest(BlockFace direction)
/*    */   {
/* 21 */     this();
/* 22 */     setFacingDirection(direction);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public EnderChest(int type)
/*    */   {
/* 31 */     super(type);
/*    */   }
/*    */   
/*    */   public EnderChest(Material type) {
/* 35 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public EnderChest(int type, byte data)
/*    */   {
/* 45 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public EnderChest(Material type, byte data)
/*    */   {
/* 55 */     super(type, data);
/*    */   }
/*    */   
/*    */   public EnderChest clone()
/*    */   {
/* 60 */     return (EnderChest)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\EnderChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */