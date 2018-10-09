/*    */ package org.bukkit.craftbukkit.v1_8_R3;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityPainting.EnumArt;
/*    */ import org.bukkit.Art;
/*    */ 
/*    */ public class CraftArt
/*    */ {
/*    */   public static Art NotchToBukkit(EntityPainting.EnumArt art)
/*    */   {
/* 10 */     switch (art) {
/* 11 */     case ALBAN:  return Art.KEBAB;
/* 12 */     case AZTEC:  return Art.AZTEC;
/* 13 */     case AZTEC_2:  return Art.ALBAN;
/* 14 */     case BOMB:  return Art.AZTEC2;
/* 15 */     case BURNING_SKULL:  return Art.BOMB;
/* 16 */     case BUST:  return Art.PLANT;
/* 17 */     case COURBET:  return Art.WASTELAND;
/* 18 */     case CREEBET:  return Art.POOL;
/* 19 */     case DONKEY_KONG:  return Art.COURBET;
/* 20 */     case FIGHTERS:  return Art.SEA;
/* 21 */     case GRAHAM:  return Art.SUNSET;
/* 22 */     case KEBAB:  return Art.CREEBET;
/* 23 */     case MATCH:  return Art.WANDERER;
/* 24 */     case PIGSCENE:  return Art.GRAHAM;
/* 25 */     case PLANT:  return Art.MATCH;
/* 26 */     case POINTER:  return Art.BUST;
/* 27 */     case POOL:  return Art.STAGE;
/* 28 */     case SEA:  return Art.VOID;
/* 29 */     case SKELETON:  return Art.SKULL_AND_ROSES;
/* 30 */     case STAGE:  return Art.FIGHTERS;
/* 31 */     case SUNSET:  return Art.POINTER;
/* 32 */     case VOID:  return Art.PIGSCENE;
/* 33 */     case WANDERER:  return Art.BURNINGSKULL;
/* 34 */     case WASTELAND:  return Art.SKELETON;
/* 35 */     case WITHER:  return Art.DONKEYKONG;
/* 36 */     case SKULL_AND_ROSES:  return Art.WITHER;
/*    */     }
/* 38 */     throw new AssertionError(art);
/*    */   }
/*    */   
/*    */   public static EntityPainting.EnumArt BukkitToNotch(Art art)
/*    */   {
/* 43 */     switch (art) {
/* 44 */     case ALBAN:  return EntityPainting.EnumArt.KEBAB;
/* 45 */     case AZTEC:  return EntityPainting.EnumArt.AZTEC;
/* 46 */     case AZTEC2:  return EntityPainting.EnumArt.ALBAN;
/* 47 */     case BOMB:  return EntityPainting.EnumArt.AZTEC_2;
/* 48 */     case BURNINGSKULL:  return EntityPainting.EnumArt.BOMB;
/* 49 */     case BUST:  return EntityPainting.EnumArt.PLANT;
/* 50 */     case COURBET:  return EntityPainting.EnumArt.WASTELAND;
/* 51 */     case CREEBET:  return EntityPainting.EnumArt.POOL;
/* 52 */     case DONKEYKONG:  return EntityPainting.EnumArt.COURBET;
/* 53 */     case FIGHTERS:  return EntityPainting.EnumArt.SEA;
/* 54 */     case GRAHAM:  return EntityPainting.EnumArt.SUNSET;
/* 55 */     case KEBAB:  return EntityPainting.EnumArt.CREEBET;
/* 56 */     case MATCH:  return EntityPainting.EnumArt.WANDERER;
/* 57 */     case PIGSCENE:  return EntityPainting.EnumArt.GRAHAM;
/* 58 */     case PLANT:  return EntityPainting.EnumArt.MATCH;
/* 59 */     case POINTER:  return EntityPainting.EnumArt.BUST;
/* 60 */     case POOL:  return EntityPainting.EnumArt.STAGE;
/* 61 */     case SEA:  return EntityPainting.EnumArt.VOID;
/* 62 */     case SKELETON:  return EntityPainting.EnumArt.SKULL_AND_ROSES;
/* 63 */     case STAGE:  return EntityPainting.EnumArt.FIGHTERS;
/* 64 */     case SUNSET:  return EntityPainting.EnumArt.POINTER;
/* 65 */     case VOID:  return EntityPainting.EnumArt.PIGSCENE;
/* 66 */     case WANDERER:  return EntityPainting.EnumArt.BURNING_SKULL;
/* 67 */     case WASTELAND:  return EntityPainting.EnumArt.SKELETON;
/* 68 */     case WITHER:  return EntityPainting.EnumArt.DONKEY_KONG;
/* 69 */     case SKULL_AND_ROSES:  return EntityPainting.EnumArt.WITHER;
/*    */     }
/* 71 */     throw new AssertionError(art);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftArt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */