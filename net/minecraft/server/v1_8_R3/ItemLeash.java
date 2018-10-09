/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.bukkit.event.hanging.HangingPlaceEvent;
/*    */ 
/*    */ public class ItemLeash extends Item
/*    */ {
/*    */   public ItemLeash()
/*    */   {
/* 11 */     a(CreativeModeTab.i);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2) {
/* 15 */     Block block = world.getType(blockposition).getBlock();
/*    */     
/* 17 */     if ((block instanceof BlockFence)) {
/* 18 */       if (world.isClientSide) {
/* 19 */         return true;
/*    */       }
/* 21 */       a(entityhuman, world, blockposition);
/* 22 */       return true;
/*    */     }
/*    */     
/* 25 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean a(EntityHuman entityhuman, World world, BlockPosition blockposition)
/*    */   {
/* 30 */     EntityLeash entityleash = EntityLeash.b(world, blockposition);
/* 31 */     boolean flag = false;
/* 32 */     double d0 = 7.0D;
/* 33 */     int i = blockposition.getX();
/* 34 */     int j = blockposition.getY();
/* 35 */     int k = blockposition.getZ();
/* 36 */     List list = world.a(EntityInsentient.class, new AxisAlignedBB(i - d0, j - d0, k - d0, i + d0, j + d0, k + d0));
/* 37 */     Iterator iterator = list.iterator();
/*    */     
/* 39 */     while (iterator.hasNext()) {
/* 40 */       EntityInsentient entityinsentient = (EntityInsentient)iterator.next();
/*    */       
/* 42 */       if ((entityinsentient.cc()) && (entityinsentient.getLeashHolder() == entityhuman)) {
/* 43 */         if (entityleash == null) {
/* 44 */           entityleash = EntityLeash.a(world, blockposition);
/*    */           
/*    */ 
/* 47 */           HangingPlaceEvent event = new HangingPlaceEvent((org.bukkit.entity.Hanging)entityleash.getBukkitEntity(), entityhuman != null ? (org.bukkit.entity.Player)entityhuman.getBukkitEntity() : null, world.getWorld().getBlockAt(i, j, k), org.bukkit.block.BlockFace.SELF);
/* 48 */           world.getServer().getPluginManager().callEvent(event);
/*    */           
/* 50 */           if (event.isCancelled()) {
/* 51 */             entityleash.die();
/* 52 */             return false;
/*    */           }
/*    */         }
/*    */         
/*    */ 
/*    */ 
/* 58 */         if (!org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callPlayerLeashEntityEvent(entityinsentient, entityleash, entityhuman).isCancelled())
/*    */         {
/*    */ 
/*    */ 
/*    */ 
/* 63 */           entityinsentient.setLeashHolder(entityleash, true);
/* 64 */           flag = true;
/*    */         }
/*    */       }
/*    */     }
/* 68 */     return flag;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemLeash.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */