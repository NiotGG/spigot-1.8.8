/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.event.player.PlayerFishEvent;
/*    */ 
/*    */ public class ItemFishingRod extends Item
/*    */ {
/*    */   public ItemFishingRod() {
/*  8 */     setMaxDurability(64);
/*  9 */     c(1);
/* 10 */     a(CreativeModeTab.i);
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 14 */     if (entityhuman.hookedFish != null) {
/* 15 */       int i = entityhuman.hookedFish.l();
/*    */       
/* 17 */       itemstack.damage(i, entityhuman);
/* 18 */       entityhuman.bw();
/*    */     }
/*    */     else {
/* 21 */       EntityFishingHook hook = new EntityFishingHook(world, entityhuman);
/* 22 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((org.bukkit.entity.Player)entityhuman.getBukkitEntity(), null, (org.bukkit.entity.Fish)hook.getBukkitEntity(), org.bukkit.event.player.PlayerFishEvent.State.FISHING);
/* 23 */       world.getServer().getPluginManager().callEvent(playerFishEvent);
/*    */       
/* 25 */       if (playerFishEvent.isCancelled()) {
/* 26 */         entityhuman.hookedFish = null;
/* 27 */         return itemstack;
/*    */       }
/*    */       
/* 30 */       world.makeSound(entityhuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
/* 31 */       if (!world.isClientSide) {
/* 32 */         world.addEntity(hook);
/*    */       }
/*    */       
/* 35 */       entityhuman.bw();
/* 36 */       entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*    */     }
/*    */     
/* 39 */     return itemstack;
/*    */   }
/*    */   
/*    */   public boolean f_(ItemStack itemstack) {
/* 43 */     return super.f_(itemstack);
/*    */   }
/*    */   
/*    */   public int b() {
/* 47 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemFishingRod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */