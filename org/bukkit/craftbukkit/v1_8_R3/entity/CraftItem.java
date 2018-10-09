/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.Entity;
/*    */ import net.minecraft.server.v1_8_R3.EntityItem;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftItem extends CraftEntity implements org.bukkit.entity.Item
/*    */ {
/*    */   private final EntityItem item;
/*    */   
/*    */   public CraftItem(CraftServer server, Entity entity, EntityItem item)
/*    */   {
/* 16 */     super(server, entity);
/* 17 */     this.item = item;
/*    */   }
/*    */   
/*    */   public CraftItem(CraftServer server, EntityItem entity) {
/* 21 */     this(server, entity, entity);
/*    */   }
/*    */   
/*    */   public ItemStack getItemStack() {
/* 25 */     return CraftItemStack.asCraftMirror(this.item.getItemStack());
/*    */   }
/*    */   
/*    */   public void setItemStack(ItemStack stack) {
/* 29 */     this.item.setItemStack(CraftItemStack.asNMSCopy(stack));
/*    */   }
/*    */   
/*    */   public int getPickupDelay() {
/* 33 */     return this.item.pickupDelay;
/*    */   }
/*    */   
/*    */   public void setPickupDelay(int delay) {
/* 37 */     this.item.pickupDelay = Math.min(delay, 32767);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 42 */     return "CraftItem";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 46 */     return EntityType.DROPPED_ITEM;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */