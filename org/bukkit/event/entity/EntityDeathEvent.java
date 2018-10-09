/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ public class EntityDeathEvent
/*    */   extends EntityEvent
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final List<ItemStack> drops;
/* 14 */   private int dropExp = 0;
/*    */   
/*    */   public EntityDeathEvent(LivingEntity entity, List<ItemStack> drops) {
/* 17 */     this(entity, drops, 0);
/*    */   }
/*    */   
/*    */   public EntityDeathEvent(LivingEntity what, List<ItemStack> drops, int droppedExp) {
/* 21 */     super(what);
/* 22 */     this.drops = drops;
/* 23 */     this.dropExp = droppedExp;
/*    */   }
/*    */   
/*    */   public LivingEntity getEntity()
/*    */   {
/* 28 */     return (LivingEntity)this.entity;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getDroppedExp()
/*    */   {
/* 40 */     return this.dropExp;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setDroppedExp(int exp)
/*    */   {
/* 52 */     this.dropExp = exp;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<ItemStack> getDrops()
/*    */   {
/* 61 */     return this.drops;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 66 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 70 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityDeathEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */