/*    */ package org.bukkit.event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Event
/*    */ {
/*    */   private String name;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private final boolean async;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Event()
/*    */   {
/* 22 */     this(false);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Event(boolean isAsync)
/*    */   {
/* 33 */     this.async = isAsync;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getEventName()
/*    */   {
/* 44 */     if (this.name == null) {
/* 45 */       this.name = getClass().getSimpleName();
/*    */     }
/* 47 */     return this.name;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract HandlerList getHandlers();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final boolean isAsynchronous()
/*    */   {
/* 75 */     return this.async;
/*    */   }
/*    */   
/*    */   public static enum Result
/*    */   {
/* 80 */     DENY, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 86 */     DEFAULT, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 91 */     ALLOW;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\Event.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */