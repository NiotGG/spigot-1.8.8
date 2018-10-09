/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class BlockPhysicsEvent
/*    */   extends BlockEvent
/*    */   implements Cancellable
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final int changed;
/* 14 */   private boolean cancel = false;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public BlockPhysicsEvent(Block block, int changed)
/*    */   {
/* 24 */     super(block);
/* 25 */     this.changed = changed;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public int getChangedTypeId()
/*    */   {
/* 36 */     return this.changed;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Material getChangedType()
/*    */   {
/* 45 */     return Material.getMaterial(this.changed);
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 49 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 53 */     this.cancel = cancel;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 58 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 62 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockPhysicsEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */