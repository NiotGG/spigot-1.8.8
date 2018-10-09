/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class ItemSeeds
/*    */   extends Item
/*    */ {
/*    */   private Block a;
/*    */   
/*    */   private Block b;
/*    */   
/*    */ 
/*    */   public ItemSeeds(Block paramBlock1, Block paramBlock2)
/*    */   {
/* 14 */     this.a = paramBlock1;
/* 15 */     this.b = paramBlock2;
/* 16 */     a(CreativeModeTab.l);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 21 */     if (paramEnumDirection != EnumDirection.UP) {
/* 22 */       return false;
/*    */     }
/*    */     
/* 25 */     if (!paramEntityHuman.a(paramBlockPosition.shift(paramEnumDirection), paramEnumDirection, paramItemStack)) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     if ((paramWorld.getType(paramBlockPosition).getBlock() == this.b) && (paramWorld.isEmpty(paramBlockPosition.up()))) {
/* 30 */       paramWorld.setTypeUpdate(paramBlockPosition.up(), this.a.getBlockData());
/* 31 */       paramItemStack.count -= 1;
/* 32 */       return true;
/*    */     }
/* 34 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemSeeds.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */