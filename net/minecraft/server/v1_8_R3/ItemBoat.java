/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class ItemBoat extends Item
/*    */ {
/*    */   public ItemBoat() {
/*  8 */     this.maxStackSize = 1;
/*  9 */     a(CreativeModeTab.e);
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 13 */     float f = 1.0F;
/* 14 */     float f1 = entityhuman.lastPitch + (entityhuman.pitch - entityhuman.lastPitch) * f;
/* 15 */     float f2 = entityhuman.lastYaw + (entityhuman.yaw - entityhuman.lastYaw) * f;
/* 16 */     double d0 = entityhuman.lastX + (entityhuman.locX - entityhuman.lastX) * f;
/* 17 */     double d1 = entityhuman.lastY + (entityhuman.locY - entityhuman.lastY) * f + entityhuman.getHeadHeight();
/* 18 */     double d2 = entityhuman.lastZ + (entityhuman.locZ - entityhuman.lastZ) * f;
/* 19 */     Vec3D vec3d = new Vec3D(d0, d1, d2);
/* 20 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 21 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 22 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 23 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/* 24 */     float f7 = f4 * f5;
/* 25 */     float f8 = f3 * f5;
/* 26 */     double d3 = 5.0D;
/* 27 */     Vec3D vec3d1 = vec3d.add(f7 * d3, f6 * d3, f8 * d3);
/* 28 */     MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d1, true);
/*    */     
/* 30 */     if (movingobjectposition == null) {
/* 31 */       return itemstack;
/*    */     }
/* 33 */     Vec3D vec3d2 = entityhuman.d(f);
/* 34 */     boolean flag = false;
/* 35 */     float f9 = 1.0F;
/* 36 */     List list = world.getEntities(entityhuman, entityhuman.getBoundingBox().a(vec3d2.a * d3, vec3d2.b * d3, vec3d2.c * d3).grow(f9, f9, f9));
/*    */     
/* 38 */     for (int i = 0; i < list.size(); i++) {
/* 39 */       Entity entity = (Entity)list.get(i);
/*    */       
/* 41 */       if (entity.ad()) {
/* 42 */         float f10 = entity.ao();
/* 43 */         AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow(f10, f10, f10);
/*    */         
/* 45 */         if (axisalignedbb.a(vec3d)) {
/* 46 */           flag = true;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 51 */     if (flag) {
/* 52 */       return itemstack;
/*    */     }
/* 54 */     if (movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
/* 55 */       BlockPosition blockposition = movingobjectposition.a();
/*    */       
/*    */ 
/* 58 */       org.bukkit.event.player.PlayerInteractEvent event = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callPlayerInteractEvent(entityhuman, org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK, blockposition, movingobjectposition.direction, itemstack);
/*    */       
/* 60 */       if (event.isCancelled()) {
/* 61 */         return itemstack;
/*    */       }
/*    */       
/*    */ 
/* 65 */       if (world.getType(blockposition).getBlock() == Blocks.SNOW_LAYER) {
/* 66 */         blockposition = blockposition.down();
/*    */       }
/*    */       
/* 69 */       EntityBoat entityboat = new EntityBoat(world, blockposition.getX() + 0.5F, blockposition.getY() + 1.0F, blockposition.getZ() + 0.5F);
/*    */       
/* 71 */       entityboat.yaw = (((MathHelper.floor(entityhuman.yaw * 4.0F / 360.0F + 0.5D) & 0x3) - 1) * 90);
/* 72 */       if (!world.getCubes(entityboat, entityboat.getBoundingBox().grow(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/* 73 */         return itemstack;
/*    */       }
/*    */       
/* 76 */       if (!world.isClientSide) {
/* 77 */         world.addEntity(entityboat);
/*    */       }
/*    */       
/* 80 */       if (!entityhuman.abilities.canInstantlyBuild) {
/* 81 */         itemstack.count -= 1;
/*    */       }
/*    */       
/* 84 */       entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*    */     }
/*    */     
/* 87 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemBoat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */