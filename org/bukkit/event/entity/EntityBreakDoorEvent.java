/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityBreakDoorEvent
/*    */   extends EntityChangeBlockEvent
/*    */ {
/*    */   public EntityBreakDoorEvent(LivingEntity entity, Block targetBlock)
/*    */   {
/* 15 */     super(entity, targetBlock, Material.AIR, (byte)0);
/*    */   }
/*    */   
/*    */   public LivingEntity getEntity()
/*    */   {
/* 20 */     return (LivingEntity)this.entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityBreakDoorEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */