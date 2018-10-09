/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraft.server.v1_8_R3.EntityPotion;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.potion.Potion;
/*    */ import org.bukkit.potion.PotionBrewer;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ 
/*    */ public class CraftThrownPotion extends CraftProjectile implements org.bukkit.entity.ThrownPotion
/*    */ {
/*    */   public CraftThrownPotion(CraftServer server, EntityPotion entity)
/*    */   {
/* 19 */     super(server, entity);
/*    */   }
/*    */   
/*    */ 
/*    */   public Collection<PotionEffect> getEffects()
/*    */   {
/* 25 */     return Potion.getBrewer().getEffectsFromDamage(getHandle().getPotionValue());
/*    */   }
/*    */   
/*    */   public ItemStack getItem()
/*    */   {
/* 30 */     getHandle().getPotionValue();
/*    */     
/* 32 */     return CraftItemStack.asBukkitCopy(getHandle().item);
/*    */   }
/*    */   
/*    */   public void setItem(ItemStack item)
/*    */   {
/* 37 */     Validate.notNull(item, "ItemStack cannot be null.");
/*    */     
/*    */ 
/* 40 */     Validate.isTrue(item.getType() == Material.POTION, "ItemStack must be a potion. This item stack was " + item.getType() + ".");
/*    */     
/* 42 */     getHandle().item = CraftItemStack.asNMSCopy(item);
/*    */   }
/*    */   
/*    */   public EntityPotion getHandle()
/*    */   {
/* 47 */     return (EntityPotion)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 52 */     return "CraftThrownPotion";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 56 */     return EntityType.SPLASH_POTION;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftThrownPotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */