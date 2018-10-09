/*    */ package org.spigotmc;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TicksPerSecondCommand
/*    */   extends Command
/*    */ {
/*    */   public TicksPerSecondCommand(String name)
/*    */   {
/* 15 */     super(name);
/* 16 */     this.description = "Gets the current ticks per second for the server";
/* 17 */     this.usageMessage = "/tps";
/* 18 */     setPermission("bukkit.command.tps");
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 24 */     if (!testPermission(sender))
/*    */     {
/* 26 */       return true;
/*    */     }
/*    */     
/* 29 */     StringBuilder sb = new StringBuilder(ChatColor.GOLD + "TPS from last 1m, 5m, 15m: ");
/* 30 */     double[] arrayOfDouble; int i = (arrayOfDouble = MinecraftServer.getServer().recentTps).length; for (int j = 0; j < i; j++) { double tps = arrayOfDouble[j];
/*    */       
/* 32 */       sb.append(format(tps));
/* 33 */       sb.append(", ");
/*    */     }
/* 35 */     sender.sendMessage(sb.substring(0, sb.length() - 2));
/*    */     
/* 37 */     return true;
/*    */   }
/*    */   
/*    */   private String format(double tps)
/*    */   {
/* 42 */     return 
/* 43 */       (tps > 16.0D ? ChatColor.YELLOW : tps > 18.0D ? ChatColor.GREEN : ChatColor.RED).toString() + (tps > 20.0D ? "*" : "") + Math.min(Math.round(tps * 100.0D) / 100.0D, 20.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\TicksPerSecondCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */