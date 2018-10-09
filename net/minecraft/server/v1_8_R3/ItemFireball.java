/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class ItemFireball extends Item {
/*  6 */   public ItemFireball() { a(CreativeModeTab.f); }
/*    */   
/*    */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2)
/*    */   {
/* 10 */     if (world.isClientSide) {
/* 11 */       return true;
/*    */     }
/* 13 */     blockposition = blockposition.shift(enumdirection);
/* 14 */     if (!entityhuman.a(blockposition, enumdirection, itemstack)) {
/* 15 */       return false;
/*    */     }
/* 17 */     if (world.getType(blockposition).getBlock().getMaterial() == Material.AIR)
/*    */     {
/* 19 */       if (org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callBlockIgniteEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), org.bukkit.event.block.BlockIgniteEvent.IgniteCause.FIREBALL, entityhuman).isCancelled()) {
/* 20 */         if (!entityhuman.abilities.canInstantlyBuild) {
/* 21 */           itemstack.count -= 1;
/*    */         }
/* 23 */         return false;
/*    */       }
/*    */       
/* 26 */       world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D, "item.fireCharge.use", 1.0F, (g.nextFloat() - g.nextFloat()) * 0.2F + 1.0F);
/* 27 */       world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
/*    */     }
/*    */     
/* 30 */     if (!entityhuman.abilities.canInstantlyBuild) {
/* 31 */       itemstack.count -= 1;
/*    */     }
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemFireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */