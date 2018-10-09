/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class RedstoneTorch
/*    */   extends Torch implements Redstone
/*    */ {
/*    */   public RedstoneTorch()
/*    */   {
/* 10 */     super(Material.REDSTONE_TORCH_ON);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public RedstoneTorch(int type)
/*    */   {
/* 19 */     super(type);
/*    */   }
/*    */   
/*    */   public RedstoneTorch(Material type) {
/* 23 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public RedstoneTorch(int type, byte data)
/*    */   {
/* 33 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public RedstoneTorch(Material type, byte data)
/*    */   {
/* 43 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isPowered()
/*    */   {
/* 53 */     return getItemType() == Material.REDSTONE_TORCH_ON;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 58 */     return super.toString() + " " + (isPowered() ? "" : "NOT ") + "POWERED";
/*    */   }
/*    */   
/*    */   public RedstoneTorch clone()
/*    */   {
/* 63 */     return (RedstoneTorch)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\RedstoneTorch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */