/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBed
/*    */   extends Item
/*    */ {
/*    */   public ItemBed()
/*    */   {
/* 15 */     a(CreativeModeTab.c);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 20 */     if (paramWorld.isClientSide) {
/* 21 */       return true;
/*    */     }
/*    */     
/* 24 */     if (paramEnumDirection != EnumDirection.UP) {
/* 25 */       return false;
/*    */     }
/*    */     
/* 28 */     IBlockData localIBlockData1 = paramWorld.getType(paramBlockPosition);
/* 29 */     Block localBlock = localIBlockData1.getBlock();
/* 30 */     boolean bool1 = localBlock.a(paramWorld, paramBlockPosition);
/*    */     
/*    */ 
/* 33 */     if (!bool1) {
/* 34 */       paramBlockPosition = paramBlockPosition.up();
/*    */     }
/*    */     
/* 37 */     int i = MathHelper.floor(paramEntityHuman.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
/* 38 */     EnumDirection localEnumDirection = EnumDirection.fromType2(i);
/* 39 */     BlockPosition localBlockPosition = paramBlockPosition.shift(localEnumDirection);
/*    */     
/* 41 */     if ((!paramEntityHuman.a(paramBlockPosition, paramEnumDirection, paramItemStack)) || (!paramEntityHuman.a(localBlockPosition, paramEnumDirection, paramItemStack))) {
/* 42 */       return false;
/*    */     }
/*    */     
/* 45 */     boolean bool2 = paramWorld.getType(localBlockPosition).getBlock().a(paramWorld, localBlockPosition);
/* 46 */     int j = (bool1) || (paramWorld.isEmpty(paramBlockPosition)) ? 1 : 0;
/* 47 */     int k = (bool2) || (paramWorld.isEmpty(localBlockPosition)) ? 1 : 0;
/*    */     
/* 49 */     if ((j != 0) && (k != 0) && (World.a(paramWorld, paramBlockPosition.down())) && (World.a(paramWorld, localBlockPosition.down())))
/*    */     {
/* 51 */       IBlockData localIBlockData2 = Blocks.BED.getBlockData().set(BlockBed.OCCUPIED, Boolean.valueOf(false)).set(BlockBed.FACING, localEnumDirection).set(BlockBed.PART, BlockBed.EnumBedPart.FOOT);
/* 52 */       if (paramWorld.setTypeAndData(paramBlockPosition, localIBlockData2, 3)) {
/* 53 */         IBlockData localIBlockData3 = localIBlockData2.set(BlockBed.PART, BlockBed.EnumBedPart.HEAD);
/* 54 */         paramWorld.setTypeAndData(localBlockPosition, localIBlockData3, 3);
/*    */       }
/*    */       
/* 57 */       paramItemStack.count -= 1;
/* 58 */       return true;
/*    */     }
/*    */     
/* 61 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemBed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */