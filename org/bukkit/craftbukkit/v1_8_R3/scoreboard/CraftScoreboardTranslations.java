/*    */ package org.bukkit.craftbukkit.v1_8_R3.scoreboard;
/*    */ 
/*    */ import com.google.common.collect.ImmutableBiMap;
/*    */ import net.minecraft.server.v1_8_R3.Scoreboard;
/*    */ import org.bukkit.scoreboard.DisplaySlot;
/*    */ 
/*    */ 
/*    */ class CraftScoreboardTranslations
/*    */ {
/*    */   static final int MAX_DISPLAY_SLOT = 3;
/* 11 */   static ImmutableBiMap<DisplaySlot, String> SLOTS = ImmutableBiMap.of(
/* 12 */     DisplaySlot.BELOW_NAME, "belowName", 
/* 13 */     DisplaySlot.PLAYER_LIST, "list", 
/* 14 */     DisplaySlot.SIDEBAR, "sidebar");
/*    */   
/*    */ 
/*    */   static DisplaySlot toBukkitSlot(int i)
/*    */   {
/* 19 */     return (DisplaySlot)SLOTS.inverse().get(Scoreboard.getSlotName(i));
/*    */   }
/*    */   
/*    */   static int fromBukkitSlot(DisplaySlot slot) {
/* 23 */     return Scoreboard.getSlotForName((String)SLOTS.get(slot));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\scoreboard\CraftScoreboardTranslations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */