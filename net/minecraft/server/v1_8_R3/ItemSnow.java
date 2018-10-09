/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSnow
/*    */   extends ItemBlock
/*    */ {
/*    */   public ItemSnow(Block paramBlock)
/*    */   {
/* 15 */     super(paramBlock);
/*    */     
/* 17 */     setMaxDurability(0);
/* 18 */     a(true);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 23 */     if (paramItemStack.count == 0) {
/* 24 */       return false;
/*    */     }
/* 26 */     if (!paramEntityHuman.a(paramBlockPosition, paramEnumDirection, paramItemStack)) {
/* 27 */       return false;
/*    */     }
/*    */     
/* 30 */     IBlockData localIBlockData1 = paramWorld.getType(paramBlockPosition);
/* 31 */     Block localBlock = localIBlockData1.getBlock();
/* 32 */     BlockPosition localBlockPosition = paramBlockPosition;
/*    */     
/* 34 */     if (((paramEnumDirection != EnumDirection.UP) || (localBlock != this.a)) && (!localBlock.a(paramWorld, paramBlockPosition))) {
/* 35 */       localBlockPosition = paramBlockPosition.shift(paramEnumDirection);
/* 36 */       localIBlockData1 = paramWorld.getType(localBlockPosition);
/* 37 */       localBlock = localIBlockData1.getBlock();
/*    */     }
/*    */     
/* 40 */     if (localBlock == this.a) {
/* 41 */       int i = ((Integer)localIBlockData1.get(BlockSnow.LAYERS)).intValue();
/*    */       
/* 43 */       if (i <= 7) {
/* 44 */         IBlockData localIBlockData2 = localIBlockData1.set(BlockSnow.LAYERS, Integer.valueOf(i + 1));
/* 45 */         AxisAlignedBB localAxisAlignedBB = this.a.a(paramWorld, localBlockPosition, localIBlockData2);
/* 46 */         if ((localAxisAlignedBB != null) && (paramWorld.b(localAxisAlignedBB)) && (paramWorld.setTypeAndData(localBlockPosition, localIBlockData2, 2))) {
/* 47 */           paramWorld.makeSound(localBlockPosition.getX() + 0.5F, localBlockPosition.getY() + 0.5F, localBlockPosition.getZ() + 0.5F, this.a.stepSound.getPlaceSound(), (this.a.stepSound.getVolume1() + 1.0F) / 2.0F, this.a.stepSound.getVolume2() * 0.8F);
/* 48 */           paramItemStack.count -= 1;
/* 49 */           return true;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 54 */     return super.interactWith(paramItemStack, paramEntityHuman, paramWorld, localBlockPosition, paramEnumDirection, paramFloat1, paramFloat2, paramFloat3);
/*    */   }
/*    */   
/*    */   public int filterData(int paramInt)
/*    */   {
/* 59 */     return paramInt;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemSnow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */