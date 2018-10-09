/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import org.bukkit.entity.ArmorStand;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.inventory.EquipmentSlot;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerArmorStandManipulateEvent
/*    */   extends PlayerInteractEntityEvent
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final ItemStack playerItem;
/*    */   private final ItemStack armorStandItem;
/*    */   private final EquipmentSlot slot;
/*    */   
/*    */   public PlayerArmorStandManipulateEvent(Player who, ArmorStand clickedEntity, ItemStack playerItem, ItemStack armorStandItem, EquipmentSlot slot)
/*    */   {
/* 21 */     super(who, clickedEntity);
/* 22 */     this.playerItem = playerItem;
/* 23 */     this.armorStandItem = armorStandItem;
/* 24 */     this.slot = slot;
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
/*    */   public ItemStack getPlayerItem()
/*    */   {
/* 37 */     return this.playerItem;
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
/*    */   public ItemStack getArmorStandItem()
/*    */   {
/* 50 */     return this.armorStandItem;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EquipmentSlot getSlot()
/*    */   {
/* 59 */     return this.slot;
/*    */   }
/*    */   
/*    */   public ArmorStand getRightClicked()
/*    */   {
/* 64 */     return (ArmorStand)this.clickedEntity;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 69 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 73 */     return handlers;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerArmorStandManipulateEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */