/*    */ package org.bukkit.event.block;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.HandlerList;
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
/*    */ public class BlockCanBuildEvent
/*    */   extends BlockEvent
/*    */ {
/* 19 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected boolean buildable;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   protected int material;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public BlockCanBuildEvent(Block block, int id, boolean canBuild)
/*    */   {
/* 38 */     super(block);
/* 39 */     this.buildable = canBuild;
/* 40 */     this.material = id;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isBuildable()
/*    */   {
/* 52 */     return this.buildable;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setBuildable(boolean cancel)
/*    */   {
/* 62 */     this.buildable = cancel;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Material getMaterial()
/*    */   {
/* 71 */     return Material.getMaterial(this.material);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public int getMaterialId()
/*    */   {
/* 82 */     return this.material;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 87 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 91 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\block\BlockCanBuildEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */