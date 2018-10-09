/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ 
/*    */ public class ItemWaterLily extends ItemWithAuxData {
/*  6 */   public ItemWaterLily(Block block) { super(block, false); }
/*    */   
/*    */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman)
/*    */   {
/* 10 */     MovingObjectPosition movingobjectposition = a(world, entityhuman, true);
/*    */     
/* 12 */     if (movingobjectposition == null) {
/* 13 */       return itemstack;
/*    */     }
/* 15 */     if (movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
/* 16 */       BlockPosition blockposition = movingobjectposition.a();
/*    */       
/* 18 */       if (!world.a(entityhuman, blockposition)) {
/* 19 */         return itemstack;
/*    */       }
/*    */       
/* 22 */       if (!entityhuman.a(blockposition.shift(movingobjectposition.direction), movingobjectposition.direction, itemstack)) {
/* 23 */         return itemstack;
/*    */       }
/*    */       
/* 26 */       BlockPosition blockposition1 = blockposition.up();
/* 27 */       IBlockData iblockdata = world.getType(blockposition);
/*    */       
/* 29 */       if ((iblockdata.getBlock().getMaterial() == Material.WATER) && (((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() == 0) && (world.isEmpty(blockposition1)))
/*    */       {
/* 31 */         org.bukkit.block.BlockState blockstate = org.bukkit.craftbukkit.v1_8_R3.block.CraftBlockState.getBlockState(world, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
/* 32 */         world.setTypeUpdate(blockposition1, Blocks.WATERLILY.getBlockData());
/* 33 */         BlockPlaceEvent placeEvent = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockstate, blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 34 */         if ((placeEvent != null) && ((placeEvent.isCancelled()) || (!placeEvent.canBuild()))) {
/* 35 */           blockstate.update(true, false);
/* 36 */           return itemstack;
/*    */         }
/*    */         
/* 39 */         if (!entityhuman.abilities.canInstantlyBuild) {
/* 40 */           itemstack.count -= 1;
/*    */         }
/*    */         
/* 43 */         entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*    */       }
/*    */     }
/*    */     
/* 47 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemWaterLily.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */