/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ public class Furnace
/*    */   extends FurnaceAndDispenser
/*    */ {
/*    */   public Furnace()
/*    */   {
/* 12 */     super(Material.FURNACE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Furnace(BlockFace direction)
/*    */   {
/* 21 */     this();
/* 22 */     setFacingDirection(direction);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Furnace(int type)
/*    */   {
/* 31 */     super(type);
/*    */   }
/*    */   
/*    */   public Furnace(Material type) {
/* 35 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Furnace(int type, byte data)
/*    */   {
/* 45 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Furnace(Material type, byte data)
/*    */   {
/* 55 */     super(type, data);
/*    */   }
/*    */   
/*    */   public Furnace clone()
/*    */   {
/* 60 */     return (Furnace)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Furnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */