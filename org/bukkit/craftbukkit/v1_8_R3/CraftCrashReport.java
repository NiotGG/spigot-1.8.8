/*    */ package org.bukkit.craftbukkit.v1_8_R3;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ import java.util.Arrays;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.concurrent.Callable;
/*    */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.PluginDescriptionFile;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class CraftCrashReport implements Callable<Object>
/*    */ {
/*    */   public Object call() throws Exception
/*    */   {
/* 18 */     StringWriter value = new StringWriter();
/*    */     try {
/* 20 */       value.append("\n   Running: ").append(Bukkit.getName()).append(" version ").append(Bukkit.getVersion()).append(" (Implementing API version ").append(Bukkit.getBukkitVersion()).append(") ").append(String.valueOf(MinecraftServer.getServer().getOnlineMode()));
/* 21 */       value.append("\n   Plugins: {");
/* 22 */       Plugin[] arrayOfPlugin; int i = (arrayOfPlugin = Bukkit.getPluginManager().getPlugins()).length; for (int j = 0; j < i; j++) { Plugin plugin = arrayOfPlugin[j];
/* 23 */         PluginDescriptionFile description = plugin.getDescription();
/* 24 */         value.append(' ').append(description.getFullName()).append(' ').append(description.getMain()).append(' ').append(Arrays.toString(description.getAuthors().toArray())).append(',');
/*    */       }
/* 26 */       value.append("}\n   Warnings: ").append(Bukkit.getWarningState().name());
/* 27 */       value.append("\n   Reload Count: ").append(String.valueOf(MinecraftServer.getServer().server.reloadCount));
/* 28 */       value.append("\n   Threads: {");
/* 29 */       for (Map.Entry<Thread, ? extends Object[]> entry : Thread.getAllStackTraces().entrySet()) {
/* 30 */         value.append(' ').append(((Thread)entry.getKey()).getState().name()).append(' ').append(((Thread)entry.getKey()).getName()).append(": ").append(Arrays.toString((Object[])entry.getValue())).append(',');
/*    */       }
/* 32 */       value.append("}\n   ").append(Bukkit.getScheduler().toString());
/*    */     } catch (Throwable t) {
/* 34 */       value.append("\n   Failed to handle CraftCrashReport:\n");
/* 35 */       PrintWriter writer = new PrintWriter(value);
/* 36 */       t.printStackTrace(writer);
/* 37 */       writer.flush();
/*    */     }
/* 39 */     return value.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftCrashReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */