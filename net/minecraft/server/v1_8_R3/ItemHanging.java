/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.event.hanging.HangingPlaceEvent;
/*    */ import org.bukkit.event.painting.PaintingPlaceEvent;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class ItemHanging extends Item
/*    */ {
/*    */   private final Class<? extends EntityHanging> a;
/*    */   
/*    */   public ItemHanging(Class<? extends EntityHanging> oclass)
/*    */   {
/* 14 */     this.a = oclass;
/* 15 */     a(CreativeModeTab.c);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2) {
/* 19 */     if (enumdirection == EnumDirection.DOWN)
/* 20 */       return false;
/* 21 */     if (enumdirection == EnumDirection.UP) {
/* 22 */       return false;
/*    */     }
/* 24 */     BlockPosition blockposition1 = blockposition.shift(enumdirection);
/*    */     
/* 26 */     if (!entityhuman.a(blockposition1, enumdirection, itemstack)) {
/* 27 */       return false;
/*    */     }
/* 29 */     EntityHanging entityhanging = a(world, blockposition1, enumdirection);
/*    */     
/* 31 */     if ((entityhanging != null) && (entityhanging.survives())) {
/* 32 */       if (!world.isClientSide)
/*    */       {
/* 34 */         org.bukkit.entity.Player who = entityhuman == null ? null : (org.bukkit.entity.Player)entityhuman.getBukkitEntity();
/* 35 */         org.bukkit.block.Block blockClicked = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 36 */         org.bukkit.block.BlockFace blockFace = org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock.notchToBlockFace(enumdirection);
/*    */         
/* 38 */         HangingPlaceEvent event = new HangingPlaceEvent((org.bukkit.entity.Hanging)entityhanging.getBukkitEntity(), who, blockClicked, blockFace);
/* 39 */         world.getServer().getPluginManager().callEvent(event);
/*    */         
/* 41 */         PaintingPlaceEvent paintingEvent = null;
/* 42 */         if ((entityhanging instanceof EntityPainting))
/*    */         {
/* 44 */           paintingEvent = new PaintingPlaceEvent((org.bukkit.entity.Painting)entityhanging.getBukkitEntity(), who, blockClicked, blockFace);
/* 45 */           paintingEvent.setCancelled(event.isCancelled());
/* 46 */           world.getServer().getPluginManager().callEvent(paintingEvent);
/*    */         }
/*    */         
/* 49 */         if ((event.isCancelled()) || ((paintingEvent != null) && (paintingEvent.isCancelled()))) {
/* 50 */           return false;
/*    */         }
/*    */         
/* 53 */         world.addEntity(entityhanging);
/*    */       }
/*    */       
/* 56 */       itemstack.count -= 1;
/*    */     }
/*    */     
/* 59 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   private EntityHanging a(World world, BlockPosition blockposition, EnumDirection enumdirection)
/*    */   {
/* 65 */     return this.a == EntityItemFrame.class ? new EntityItemFrame(world, blockposition, enumdirection) : this.a == EntityPainting.class ? new EntityPainting(world, blockposition, enumdirection) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemHanging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */