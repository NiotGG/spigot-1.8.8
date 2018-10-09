/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class ItemSeedFood
/*    */   extends ItemFood
/*    */ {
/*    */   private Block b;
/*    */   
/*    */   private Block c;
/*    */   
/*    */ 
/*    */   public ItemSeedFood(int paramInt, float paramFloat, Block paramBlock1, Block paramBlock2)
/*    */   {
/* 14 */     super(paramInt, paramFloat, false);
/*    */     
/* 16 */     this.b = paramBlock1;
/* 17 */     this.c = paramBlock2;
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 22 */     if (paramEnumDirection != EnumDirection.UP) {
/* 23 */       return false;
/*    */     }
/*    */     
/* 26 */     if (!paramEntityHuman.a(paramBlockPosition.shift(paramEnumDirection), paramEnumDirection, paramItemStack)) {
/* 27 */       return false;
/*    */     }
/*    */     
/* 30 */     if ((paramWorld.getType(paramBlockPosition).getBlock() == this.c) && (paramWorld.isEmpty(paramBlockPosition.up()))) {
/* 31 */       paramWorld.setTypeUpdate(paramBlockPosition.up(), this.b.getBlockData());
/* 32 */       paramItemStack.count -= 1;
/* 33 */       return true;
/*    */     }
/* 35 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemSeedFood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */