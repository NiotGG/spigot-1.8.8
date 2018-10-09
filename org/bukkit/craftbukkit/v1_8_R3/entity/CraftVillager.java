/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityVillager;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Villager.Profession;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftVillager extends CraftAgeable implements org.bukkit.entity.Villager, org.bukkit.inventory.InventoryHolder
/*    */ {
/*    */   public CraftVillager(CraftServer server, EntityVillager entity)
/*    */   {
/* 14 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityVillager getHandle()
/*    */   {
/* 19 */     return (EntityVillager)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 24 */     return "CraftVillager";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 28 */     return EntityType.VILLAGER;
/*    */   }
/*    */   
/*    */   public Villager.Profession getProfession() {
/* 32 */     return Villager.Profession.getProfession(getHandle().getProfession());
/*    */   }
/*    */   
/*    */   public void setProfession(Villager.Profession profession) {
/* 36 */     Validate.notNull(profession);
/* 37 */     getHandle().setProfession(profession.getId());
/*    */   }
/*    */   
/*    */   public Inventory getInventory()
/*    */   {
/* 42 */     return new org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory(getHandle().inventory);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftVillager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */