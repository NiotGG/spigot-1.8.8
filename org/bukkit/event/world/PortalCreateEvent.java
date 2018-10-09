/*    */ package org.bukkit.event.world;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class PortalCreateEvent
/*    */   extends WorldEvent
/*    */   implements Cancellable
/*    */ {
/* 15 */   private static final HandlerList handlers = new HandlerList();
/* 16 */   private boolean cancel = false;
/* 17 */   private final ArrayList<Block> blocks = new ArrayList();
/* 18 */   private CreateReason reason = CreateReason.FIRE;
/*    */   
/*    */   public PortalCreateEvent(Collection<Block> blocks, World world, CreateReason reason) {
/* 21 */     super(world);
/*    */     
/* 23 */     this.blocks.addAll(blocks);
/* 24 */     this.reason = reason;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ArrayList<Block> getBlocks()
/*    */   {
/* 33 */     return this.blocks;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 37 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel) {
/* 41 */     this.cancel = cancel;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public CreateReason getReason()
/*    */   {
/* 50 */     return this.reason;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 55 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 59 */     return handlers;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static enum CreateReason
/*    */   {
/* 66 */     FIRE, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 71 */     OBC_DESTINATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\world\PortalCreateEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */