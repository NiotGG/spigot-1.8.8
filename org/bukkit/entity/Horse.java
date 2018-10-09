/*    */ package org.bukkit.entity;
/*    */ 
/*    */ import org.bukkit.inventory.HorseInventory;
/*    */ 
/*    */ public abstract interface Horse extends Animals, Vehicle, org.bukkit.inventory.InventoryHolder, Tameable
/*    */ {
/*    */   public abstract Variant getVariant();
/*    */   
/*    */   public abstract void setVariant(Variant paramVariant);
/*    */   
/*    */   public abstract Color getColor();
/*    */   
/*    */   public static enum Variant
/*    */   {
/* 15 */     HORSE, 
/*    */     
/*    */ 
/*    */ 
/* 19 */     DONKEY, 
/*    */     
/*    */ 
/*    */ 
/* 23 */     MULE, 
/*    */     
/*    */ 
/*    */ 
/* 27 */     UNDEAD_HORSE, 
/*    */     
/*    */ 
/*    */ 
/* 31 */     SKELETON_HORSE;
/*    */   }
/*    */   
/*    */   public abstract void setColor(Color paramColor);
/*    */   
/*    */   public abstract Style getStyle();
/*    */   
/*    */   public abstract void setStyle(Style paramStyle);
/*    */   
/*    */   public abstract boolean isCarryingChest();
/*    */   
/* 42 */   public static enum Color { WHITE, 
/*    */     
/*    */ 
/*    */ 
/* 46 */     CREAMY, 
/*    */     
/*    */ 
/*    */ 
/* 50 */     CHESTNUT, 
/*    */     
/*    */ 
/*    */ 
/* 54 */     BROWN, 
/*    */     
/*    */ 
/*    */ 
/* 58 */     BLACK, 
/*    */     
/*    */ 
/*    */ 
/* 62 */     GRAY, 
/*    */     
/*    */ 
/*    */ 
/* 66 */     DARK_BROWN;
/*    */   }
/*    */   
/*    */   public abstract void setCarryingChest(boolean paramBoolean);
/*    */   
/*    */   public abstract int getDomestication();
/*    */   
/*    */   public abstract void setDomestication(int paramInt);
/*    */   
/*    */   public abstract int getMaxDomestication();
/*    */   
/* 77 */   public static enum Style { NONE, 
/*    */     
/*    */ 
/*    */ 
/* 81 */     WHITE, 
/*    */     
/*    */ 
/*    */ 
/* 85 */     WHITEFIELD, 
/*    */     
/*    */ 
/*    */ 
/* 89 */     WHITE_DOTS, 
/*    */     
/*    */ 
/*    */ 
/* 93 */     BLACK_DOTS;
/*    */   }
/*    */   
/*    */   public abstract void setMaxDomestication(int paramInt);
/*    */   
/*    */   public abstract double getJumpStrength();
/*    */   
/*    */   public abstract void setJumpStrength(double paramDouble);
/*    */   
/*    */   public abstract HorseInventory getInventory();
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Horse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */