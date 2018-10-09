/*    */ package org.bukkit.plugin.messaging;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReservedChannelException
/*    */   extends RuntimeException
/*    */ {
/*    */   public ReservedChannelException()
/*    */   {
/* 10 */     this("Attempted to register for a reserved channel name.");
/*    */   }
/*    */   
/*    */   public ReservedChannelException(String name) {
/* 14 */     super("Attempted to register for a reserved channel name ('" + name + "')");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\messaging\ReservedChannelException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */