/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.server.v1_8_R3.DataWatcher;
/*    */ import net.minecraft.server.v1_8_R3.EntityFireworks;
/*    */ import net.minecraft.server.v1_8_R3.ItemStack;
/*    */ import net.minecraft.server.v1_8_R3.Items;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Firework;
/*    */ import org.bukkit.inventory.meta.FireworkMeta;
/*    */ 
/*    */ public class CraftFirework
/*    */   extends CraftEntity implements Firework
/*    */ {
/*    */   private static final int FIREWORK_ITEM_INDEX = 8;
/* 19 */   private final Random random = new Random();
/*    */   private final CraftItemStack item;
/*    */   
/*    */   public CraftFirework(CraftServer server, EntityFireworks entity) {
/* 23 */     super(server, entity);
/*    */     
/* 25 */     ItemStack item = getHandle().getDataWatcher().getItemStack(8);
/*    */     
/* 27 */     if (item == null) {
/* 28 */       item = new ItemStack(Items.FIREWORKS);
/* 29 */       getHandle().getDataWatcher().watch(8, item);
/*    */     }
/*    */     
/* 32 */     this.item = CraftItemStack.asCraftMirror(item);
/*    */     
/*    */ 
/* 35 */     if (this.item.getType() != Material.FIREWORK) {
/* 36 */       this.item.setType(Material.FIREWORK);
/*    */     }
/*    */   }
/*    */   
/*    */   public EntityFireworks getHandle()
/*    */   {
/* 42 */     return (EntityFireworks)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 47 */     return "CraftFirework";
/*    */   }
/*    */   
/*    */   public EntityType getType()
/*    */   {
/* 52 */     return EntityType.FIREWORK;
/*    */   }
/*    */   
/*    */   public FireworkMeta getFireworkMeta()
/*    */   {
/* 57 */     return (FireworkMeta)this.item.getItemMeta();
/*    */   }
/*    */   
/*    */   public void setFireworkMeta(FireworkMeta meta)
/*    */   {
/* 62 */     this.item.setItemMeta(meta);
/*    */     
/*    */ 
/* 65 */     getHandle().expectedLifespan = (10 * (1 + meta.getPower()) + this.random.nextInt(6) + this.random.nextInt(7));
/*    */     
/* 67 */     getHandle().getDataWatcher().update(8);
/*    */   }
/*    */   
/*    */   public void detonate()
/*    */   {
/* 72 */     getHandle().expectedLifespan = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftFirework.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */