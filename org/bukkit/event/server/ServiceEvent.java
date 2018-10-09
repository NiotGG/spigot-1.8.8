/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.plugin.RegisteredServiceProvider;
/*    */ 
/*    */ 
/*    */ public abstract class ServiceEvent
/*    */   extends ServerEvent
/*    */ {
/*    */   private final RegisteredServiceProvider<?> provider;
/*    */   
/*    */   public ServiceEvent(RegisteredServiceProvider<?> provider)
/*    */   {
/* 13 */     this.provider = provider;
/*    */   }
/*    */   
/*    */   public RegisteredServiceProvider<?> getProvider() {
/* 17 */     return this.provider;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\server\ServiceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */