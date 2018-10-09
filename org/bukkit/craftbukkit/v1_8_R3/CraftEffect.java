/*    */ package org.bukkit.craftbukkit.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class CraftEffect {
/*    */   public static <T> int getDataValue(org.bukkit.Effect effect, T data) {
/*    */     int datavalue;
/*    */     int datavalue;
/*    */     int datavalue;
/*    */     int datavalue;
/*    */     int datavalue;
/* 12 */     switch (effect) {
/*    */     case FLAME: 
/* 14 */       datavalue = ((org.bukkit.potion.Potion)data).toDamageValue() & 0x3F;
/* 15 */       break;
/*    */     case COLOURED_DUST: 
/* 17 */       org.apache.commons.lang.Validate.isTrue(((Material)data).isRecord(), "Invalid record type!");
/* 18 */       datavalue = ((Material)data).getId();
/* 19 */       break; case EXTINGUISH:  int datavalue;
/*    */       int datavalue;
/* 21 */       int datavalue; int datavalue; int datavalue; int datavalue; int datavalue; int datavalue; int datavalue; switch ((org.bukkit.block.BlockFace)data) {
/*    */       case NORTH_WEST: 
/* 23 */         datavalue = 0;
/* 24 */         break;
/*    */       case EAST_NORTH_EAST: 
/* 26 */         datavalue = 1;
/* 27 */         break;
/*    */       case SELF: 
/* 29 */         datavalue = 2;
/* 30 */         break;
/*    */       case EAST: 
/* 32 */         datavalue = 3;
/* 33 */         break;
/*    */       case NORTH: 
/*    */       case WEST_SOUTH_WEST: 
/* 36 */         datavalue = 4;
/* 37 */         break;
/*    */       case EAST_SOUTH_EAST: 
/* 39 */         datavalue = 5;
/* 40 */         break;
/*    */       case NORTH_NORTH_EAST: 
/* 42 */         datavalue = 6;
/* 43 */         break;
/*    */       case DOWN: 
/* 45 */         datavalue = 7;
/* 46 */         break;
/*    */       case NORTH_NORTH_WEST: 
/* 48 */         datavalue = 8;
/* 49 */         break;
/*    */       case NORTH_EAST: case SOUTH: case SOUTH_EAST: case SOUTH_SOUTH_EAST: case SOUTH_SOUTH_WEST: case SOUTH_WEST: case UP: case WEST: case WEST_NORTH_WEST: default: 
/* 51 */         throw new IllegalArgumentException("Bad smoke direction!");
/*    */       }
/*    */       break;
/*    */     case FIREWORKS_SPARK: 
/* 55 */       org.apache.commons.lang.Validate.isTrue(((Material)data).isBlock(), "Material is not a block!");
/* 56 */       datavalue = ((Material)data).getId();
/* 57 */       break;
/*    */     case ZOMBIE_CHEW_IRON_DOOR: 
/* 59 */       datavalue = ((Material)data).getId();
/* 60 */       break;
/*    */     default: 
/* 62 */       datavalue = 0;
/*    */     }
/* 64 */     return datavalue;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */