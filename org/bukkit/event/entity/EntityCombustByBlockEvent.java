/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ public class EntityCombustByBlockEvent
/*    */   extends EntityCombustEvent
/*    */ {
/*    */   private final Block combuster;
/*    */   
/*    */   public EntityCombustByBlockEvent(Block combuster, Entity combustee, int duration)
/*    */   {
/* 13 */     super(combustee, duration);
/* 14 */     this.combuster = combuster;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Block getCombuster()
/*    */   {
/* 25 */     return this.combuster;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityCombustByBlockEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */