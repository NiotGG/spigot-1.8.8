/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class ItemDoor extends Item
/*    */ {
/*    */   private Block a;
/*    */   
/*    */   public ItemDoor(Block block) {
/*  8 */     this.a = block;
/*  9 */     a(CreativeModeTab.d);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2) {
/* 13 */     if (enumdirection != EnumDirection.UP) {
/* 14 */       return false;
/*    */     }
/* 16 */     IBlockData iblockdata = world.getType(blockposition);
/* 17 */     Block block = iblockdata.getBlock();
/*    */     
/* 19 */     if (!block.a(world, blockposition)) {
/* 20 */       blockposition = blockposition.shift(enumdirection);
/*    */     }
/*    */     
/* 23 */     if (!entityhuman.a(blockposition, enumdirection, itemstack))
/* 24 */       return false;
/* 25 */     if (!this.a.canPlace(world, blockposition)) {
/* 26 */       return false;
/*    */     }
/* 28 */     a(world, blockposition, EnumDirection.fromAngle(entityhuman.yaw), this.a);
/* 29 */     itemstack.count -= 1;
/* 30 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public static void a(World world, BlockPosition blockposition, EnumDirection enumdirection, Block block)
/*    */   {
/* 36 */     BlockPosition blockposition1 = blockposition.shift(enumdirection.e());
/* 37 */     BlockPosition blockposition2 = blockposition.shift(enumdirection.f());
/* 38 */     int i = (world.getType(blockposition2).getBlock().isOccluding() ? 1 : 0) + (world.getType(blockposition2.up()).getBlock().isOccluding() ? 1 : 0);
/* 39 */     int j = (world.getType(blockposition1).getBlock().isOccluding() ? 1 : 0) + (world.getType(blockposition1.up()).getBlock().isOccluding() ? 1 : 0);
/* 40 */     boolean flag = (world.getType(blockposition2).getBlock() == block) || (world.getType(blockposition2.up()).getBlock() == block);
/* 41 */     boolean flag1 = (world.getType(blockposition1).getBlock() == block) || (world.getType(blockposition1.up()).getBlock() == block);
/* 42 */     boolean flag2 = false;
/*    */     
/* 44 */     if (((flag) && (!flag1)) || (j > i)) {
/* 45 */       flag2 = true;
/*    */     }
/*    */     
/* 48 */     BlockPosition blockposition3 = blockposition.up();
/* 49 */     IBlockData iblockdata = block.getBlockData().set(BlockDoor.FACING, enumdirection).set(BlockDoor.HINGE, flag2 ? BlockDoor.EnumDoorHinge.RIGHT : BlockDoor.EnumDoorHinge.LEFT);
/*    */     
/*    */ 
/* 52 */     world.setTypeAndData(blockposition, iblockdata.set(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 3);
/* 53 */     world.setTypeAndData(blockposition3, iblockdata.set(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 3);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */