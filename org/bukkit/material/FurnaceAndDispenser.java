/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FurnaceAndDispenser
/*    */   extends DirectionalContainer
/*    */ {
/*    */   @Deprecated
/*    */   public FurnaceAndDispenser(int type)
/*    */   {
/* 16 */     super(type);
/*    */   }
/*    */   
/*    */   public FurnaceAndDispenser(Material type) {
/* 20 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public FurnaceAndDispenser(int type, byte data)
/*    */   {
/* 30 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public FurnaceAndDispenser(Material type, byte data)
/*    */   {
/* 40 */     super(type, data);
/*    */   }
/*    */   
/*    */   public FurnaceAndDispenser clone()
/*    */   {
/* 45 */     return (FurnaceAndDispenser)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\FurnaceAndDispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */