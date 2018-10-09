/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.Event.Result;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerInteractEvent
/*     */   extends PlayerEvent
/*     */   implements Cancellable
/*     */ {
/*  19 */   private static final HandlerList handlers = new HandlerList();
/*     */   protected ItemStack item;
/*     */   protected Action action;
/*     */   protected Block blockClicked;
/*     */   protected BlockFace blockFace;
/*     */   private Event.Result useClickedBlock;
/*     */   private Event.Result useItemInHand;
/*     */   
/*     */   public PlayerInteractEvent(Player who, Action action, ItemStack item, Block clickedBlock, BlockFace clickedFace) {
/*  28 */     super(who);
/*  29 */     this.action = action;
/*  30 */     this.item = item;
/*  31 */     this.blockClicked = clickedBlock;
/*  32 */     this.blockFace = clickedFace;
/*     */     
/*  34 */     this.useItemInHand = Event.Result.DEFAULT;
/*  35 */     this.useClickedBlock = (clickedBlock == null ? Event.Result.DENY : Event.Result.ALLOW);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Action getAction()
/*     */   {
/*  44 */     return this.action;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCancelled()
/*     */   {
/*  54 */     return useInteractedBlock() == Event.Result.DENY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCancelled(boolean cancel)
/*     */   {
/*  68 */     setUseInteractedBlock(useInteractedBlock() == Event.Result.DENY ? Event.Result.DEFAULT : cancel ? Event.Result.DENY : useInteractedBlock());
/*  69 */     setUseItemInHand(useItemInHand() == Event.Result.DENY ? Event.Result.DEFAULT : cancel ? Event.Result.DENY : useItemInHand());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack getItem()
/*     */   {
/*  78 */     return this.item;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Material getMaterial()
/*     */   {
/*  88 */     if (!hasItem()) {
/*  89 */       return Material.AIR;
/*     */     }
/*     */     
/*  92 */     return this.item.getType();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasBlock()
/*     */   {
/* 101 */     return this.blockClicked != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasItem()
/*     */   {
/* 110 */     return this.item != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isBlockInHand()
/*     */   {
/* 120 */     if (!hasItem()) {
/* 121 */       return false;
/*     */     }
/*     */     
/* 124 */     return this.item.getType().isBlock();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Block getClickedBlock()
/*     */   {
/* 133 */     return this.blockClicked;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockFace getBlockFace()
/*     */   {
/* 142 */     return this.blockFace;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Event.Result useInteractedBlock()
/*     */   {
/* 153 */     return this.useClickedBlock;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setUseInteractedBlock(Event.Result useInteractedBlock)
/*     */   {
/* 160 */     this.useClickedBlock = useInteractedBlock;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Event.Result useItemInHand()
/*     */   {
/* 172 */     return this.useItemInHand;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setUseItemInHand(Event.Result useItemInHand)
/*     */   {
/* 179 */     this.useItemInHand = useItemInHand;
/*     */   }
/*     */   
/*     */   public HandlerList getHandlers()
/*     */   {
/* 184 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 188 */     return handlers;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerInteractEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */