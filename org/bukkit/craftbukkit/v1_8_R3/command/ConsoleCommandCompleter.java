/*    */ package org.bukkit.craftbukkit.v1_8_R3.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.logging.Level;
/*    */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.util.Waitable;
/*    */ 
/*    */ public class ConsoleCommandCompleter implements org.bukkit.craftbukkit.libs.jline.console.completer.Completer
/*    */ {
/*    */   private final CraftServer server;
/*    */   
/*    */   public ConsoleCommandCompleter(CraftServer server)
/*    */   {
/* 16 */     this.server = server;
/*    */   }
/*    */   
/*    */   public int complete(final String buffer, int cursor, List<CharSequence> candidates) {
/* 20 */     Waitable<List<String>> waitable = new Waitable()
/*    */     {
/*    */       protected List<String> evaluate() {
/* 23 */         return ConsoleCommandCompleter.this.server.getCommandMap().tabComplete(ConsoleCommandCompleter.this.server.getConsoleSender(), buffer);
/*    */       }
/* 25 */     };
/* 26 */     this.server.getServer().processQueue.add(waitable);
/*    */     try {
/* 28 */       List<String> offers = (List)waitable.get();
/* 29 */       if (offers == null) {
/* 30 */         return cursor;
/*    */       }
/* 32 */       candidates.addAll(offers);
/*    */       
/* 34 */       int lastSpace = buffer.lastIndexOf(' ');
/* 35 */       if (lastSpace == -1) {
/* 36 */         return cursor - buffer.length();
/*    */       }
/* 38 */       return cursor - (buffer.length() - lastSpace - 1);
/*    */     }
/*    */     catch (ExecutionException e) {
/* 41 */       this.server.getLogger().log(Level.WARNING, "Unhandled exception when tab completing", e);
/*    */     } catch (InterruptedException localInterruptedException) {
/* 43 */       Thread.currentThread().interrupt();
/*    */     }
/* 45 */     return cursor;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\command\ConsoleCommandCompleter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */