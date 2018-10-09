/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemReed
/*    */   extends Item
/*    */ {
/*    */   private Block a;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public ItemReed(Block paramBlock)
/*    */   {
/* 16 */     this.a = paramBlock;
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 21 */     IBlockData localIBlockData1 = paramWorld.getType(paramBlockPosition);
/* 22 */     Block localBlock = localIBlockData1.getBlock();
/* 23 */     if ((localBlock == Blocks.SNOW_LAYER) && (((Integer)localIBlockData1.get(BlockSnow.LAYERS)).intValue() < 1)) {
/* 24 */       paramEnumDirection = EnumDirection.UP;
/* 25 */     } else if (!localBlock.a(paramWorld, paramBlockPosition)) {
/* 26 */       paramBlockPosition = paramBlockPosition.shift(paramEnumDirection);
/*    */     }
/*    */     
/* 29 */     if (!paramEntityHuman.a(paramBlockPosition, paramEnumDirection, paramItemStack)) {
/* 30 */       return false;
/*    */     }
/* 32 */     if (paramItemStack.count == 0) {
/* 33 */       return false;
/*    */     }
/*    */     
/* 36 */     if (paramWorld.a(this.a, paramBlockPosition, false, paramEnumDirection, null, paramItemStack)) {
/* 37 */       IBlockData localIBlockData2 = this.a.getPlacedState(paramWorld, paramBlockPosition, paramEnumDirection, paramFloat1, paramFloat2, paramFloat3, 0, paramEntityHuman);
/* 38 */       if (paramWorld.setTypeAndData(paramBlockPosition, localIBlockData2, 3)) {
/* 39 */         localIBlockData2 = paramWorld.getType(paramBlockPosition);
/*    */         
/*    */ 
/*    */ 
/* 43 */         if (localIBlockData2.getBlock() == this.a) {
/* 44 */           ItemBlock.a(paramWorld, paramEntityHuman, paramBlockPosition, paramItemStack);
/* 45 */           localIBlockData2.getBlock().postPlace(paramWorld, paramBlockPosition, localIBlockData2, paramEntityHuman, paramItemStack);
/*    */         }
/* 47 */         paramWorld.makeSound(paramBlockPosition.getX() + 0.5F, paramBlockPosition.getY() + 0.5F, paramBlockPosition.getZ() + 0.5F, this.a.stepSound.getPlaceSound(), (this.a.stepSound.getVolume1() + 1.0F) / 2.0F, this.a.stepSound.getVolume2() * 0.8F);
/* 48 */         paramItemStack.count -= 1;
/* 49 */         return true;
/*    */       }
/*    */     }
/* 52 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemReed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */