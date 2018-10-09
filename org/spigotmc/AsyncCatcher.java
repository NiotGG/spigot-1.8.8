/*    */ package org.spigotmc;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*    */ 
/*    */ 
/*    */ public class AsyncCatcher
/*    */ {
/*  8 */   public static boolean enabled = true;
/*    */   
/*    */   public static void catchOp(String reason)
/*    */   {
/* 12 */     if ((enabled) && (Thread.currentThread() != MinecraftServer.getServer().primaryThread))
/*    */     {
/* 14 */       throw new IllegalStateException("Asynchronous " + reason + "!");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\AsyncCatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */