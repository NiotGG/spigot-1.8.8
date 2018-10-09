/*    */ package org.bukkit.craftbukkit.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.inventory.EquipmentSlot;
/*    */ 
/*    */ public class CraftEquipmentSlot
/*    */ {
/*  7 */   private static final int[] slots = new int[EquipmentSlot.values().length];
/*  8 */   private static final EquipmentSlot[] enums = new EquipmentSlot[EquipmentSlot.values().length];
/*    */   
/*    */   static {
/* 11 */     set(EquipmentSlot.HAND, 0);
/* 12 */     set(EquipmentSlot.FEET, 1);
/* 13 */     set(EquipmentSlot.LEGS, 2);
/* 14 */     set(EquipmentSlot.CHEST, 3);
/* 15 */     set(EquipmentSlot.HEAD, 4);
/*    */   }
/*    */   
/*    */   private static void set(EquipmentSlot type, int value) {
/* 19 */     slots[type.ordinal()] = value;
/* 20 */     if (value < enums.length) {
/* 21 */       enums[value] = type;
/*    */     }
/*    */   }
/*    */   
/*    */   public static EquipmentSlot getSlot(int magic) {
/* 26 */     if ((magic > 0) && (magic < enums.length)) {
/* 27 */       return enums[magic];
/*    */     }
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   public static int getSlotIndex(EquipmentSlot slot) {
/* 33 */     return slots[slot.ordinal()];
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftEquipmentSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */