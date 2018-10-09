/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class PressurePlate
/*    */   extends MaterialData implements PressureSensor
/*    */ {
/*    */   public PressurePlate()
/*    */   {
/* 10 */     super(Material.WOOD_PLATE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public PressurePlate(int type)
/*    */   {
/* 19 */     super(type);
/*    */   }
/*    */   
/*    */   public PressurePlate(Material type) {
/* 23 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public PressurePlate(int type, byte data)
/*    */   {
/* 33 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public PressurePlate(Material type, byte data)
/*    */   {
/* 43 */     super(type, data);
/*    */   }
/*    */   
/*    */   public boolean isPressed() {
/* 47 */     return getData() == 1;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 52 */     return super.toString() + (isPressed() ? " PRESSED" : "");
/*    */   }
/*    */   
/*    */   public PressurePlate clone()
/*    */   {
/* 57 */     return (PressurePlate)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\PressurePlate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */