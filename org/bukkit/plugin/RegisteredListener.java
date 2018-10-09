/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ 
/*    */ public class RegisteredListener
/*    */ {
/*    */   private final Listener listener;
/*    */   private final EventPriority priority;
/*    */   private final Plugin plugin;
/*    */   private final EventExecutor executor;
/*    */   private final boolean ignoreCancelled;
/*    */   
/*    */   public RegisteredListener(Listener listener, EventExecutor executor, EventPriority priority, Plugin plugin, boolean ignoreCancelled)
/*    */   {
/* 16 */     this.listener = listener;
/* 17 */     this.priority = priority;
/* 18 */     this.plugin = plugin;
/* 19 */     this.executor = executor;
/* 20 */     this.ignoreCancelled = ignoreCancelled;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Listener getListener()
/*    */   {
/* 29 */     return this.listener;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Plugin getPlugin()
/*    */   {
/* 38 */     return this.plugin;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EventPriority getPriority()
/*    */   {
/* 47 */     return this.priority;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void callEvent(org.bukkit.event.Event event)
/*    */     throws org.bukkit.event.EventException
/*    */   {
/* 57 */     if (((event instanceof org.bukkit.event.Cancellable)) && 
/* 58 */       (((org.bukkit.event.Cancellable)event).isCancelled()) && (isIgnoringCancelled())) {
/* 59 */       return;
/*    */     }
/*    */     
/* 62 */     this.executor.execute(this.listener, event);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isIgnoringCancelled()
/*    */   {
/* 71 */     return this.ignoreCancelled;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\RegisteredListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */