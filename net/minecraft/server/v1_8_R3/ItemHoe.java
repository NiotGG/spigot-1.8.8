/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemHoe
/*    */   extends Item
/*    */ {
/*    */   protected Item.EnumToolMaterial a;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public ItemHoe(Item.EnumToolMaterial paramEnumToolMaterial)
/*    */   {
/* 17 */     this.a = paramEnumToolMaterial;
/* 18 */     this.maxStackSize = 1;
/* 19 */     setMaxDurability(paramEnumToolMaterial.a());
/* 20 */     a(CreativeModeTab.i);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 25 */     if (!paramEntityHuman.a(paramBlockPosition.shift(paramEnumDirection), paramEnumDirection, paramItemStack)) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     IBlockData localIBlockData = paramWorld.getType(paramBlockPosition);
/* 30 */     Block localBlock = localIBlockData.getBlock();
/*    */     
/* 32 */     if ((paramEnumDirection != EnumDirection.DOWN) && (paramWorld.getType(paramBlockPosition.up()).getBlock().getMaterial() == Material.AIR)) {
/* 33 */       if (localBlock == Blocks.GRASS)
/* 34 */         return a(paramItemStack, paramEntityHuman, paramWorld, paramBlockPosition, Blocks.FARMLAND.getBlockData());
/* 35 */       if (localBlock == Blocks.DIRT) {
/* 36 */         switch (1.a[((BlockDirt.EnumDirtVariant)localIBlockData.get(BlockDirt.VARIANT)).ordinal()]) {
/*    */         case 1: 
/* 38 */           return a(paramItemStack, paramEntityHuman, paramWorld, paramBlockPosition, Blocks.FARMLAND.getBlockData());
/*    */         case 2: 
/* 40 */           return a(paramItemStack, paramEntityHuman, paramWorld, paramBlockPosition, Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.DIRT));
/*    */         }
/*    */         
/*    */       }
/*    */     }
/* 45 */     return false;
/*    */   }
/*    */   
/*    */   protected boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData) {
/* 49 */     paramWorld.makeSound(paramBlockPosition.getX() + 0.5F, paramBlockPosition.getY() + 0.5F, paramBlockPosition.getZ() + 0.5F, paramIBlockData.getBlock().stepSound.getStepSound(), (paramIBlockData.getBlock().stepSound.getVolume1() + 1.0F) / 2.0F, paramIBlockData.getBlock().stepSound.getVolume2() * 0.8F);
/*    */     
/* 51 */     if (paramWorld.isClientSide) {
/* 52 */       return true;
/*    */     }
/* 54 */     paramWorld.setTypeUpdate(paramBlockPosition, paramIBlockData);
/* 55 */     paramItemStack.damage(1, paramEntityHuman);
/*    */     
/* 57 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String g()
/*    */   {
/* 66 */     return this.a.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemHoe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */