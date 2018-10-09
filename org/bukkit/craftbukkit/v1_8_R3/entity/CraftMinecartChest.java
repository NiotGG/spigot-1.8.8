/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityMinecartChest;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.StorageMinecart;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftMinecartChest extends CraftMinecart implements StorageMinecart
/*    */ {
/*    */   private final CraftInventory inventory;
/*    */   
/*    */   public CraftMinecartChest(CraftServer server, EntityMinecartChest entity)
/*    */   {
/* 16 */     super(server, entity);
/* 17 */     this.inventory = new CraftInventory(entity);
/*    */   }
/*    */   
/*    */   public Inventory getInventory() {
/* 21 */     return this.inventory;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 26 */     return "CraftMinecartChest{inventory=" + this.inventory + '}';
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 30 */     return EntityType.MINECART_CHEST;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftMinecartChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */