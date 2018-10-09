/*    */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*    */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*    */ import net.minecraft.server.v1_8_R3.PlayerList;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class LazyPlayerSet extends LazyHashSet<Player>
/*    */ {
/*    */   HashSet<Player> makeReference()
/*    */   {
/* 14 */     if (this.reference != null) {
/* 15 */       throw new IllegalStateException("Reference already created!");
/*    */     }
/* 17 */     List<EntityPlayer> players = MinecraftServer.getServer().getPlayerList().players;
/* 18 */     HashSet<Player> reference = new HashSet(players.size());
/* 19 */     for (EntityPlayer player : players) {
/* 20 */       reference.add(player.getBukkitEntity());
/*    */     }
/* 22 */     return reference;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\LazyPlayerSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */