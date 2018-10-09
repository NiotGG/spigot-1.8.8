/*    */ package org.bukkit.event.hanging;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Hanging;
/*    */ 
/*    */ public class HangingBreakByEntityEvent
/*    */   extends HangingBreakEvent
/*    */ {
/*    */   private final Entity remover;
/*    */   
/*    */   public HangingBreakByEntityEvent(Hanging hanging, Entity remover)
/*    */   {
/* 13 */     super(hanging, HangingBreakEvent.RemoveCause.ENTITY);
/* 14 */     this.remover = remover;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Entity getRemover()
/*    */   {
/* 23 */     return this.remover;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\hanging\HangingBreakByEntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */