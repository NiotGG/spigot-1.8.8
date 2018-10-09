/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemShears
/*    */   extends Item
/*    */ {
/*    */   public ItemShears()
/*    */   {
/* 13 */     c(1);
/* 14 */     setMaxDurability(238);
/* 15 */     a(CreativeModeTab.i);
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, World paramWorld, Block paramBlock, BlockPosition paramBlockPosition, EntityLiving paramEntityLiving)
/*    */   {
/* 20 */     if ((paramBlock.getMaterial() == Material.LEAVES) || (paramBlock == Blocks.WEB) || (paramBlock == Blocks.TALLGRASS) || (paramBlock == Blocks.VINE) || (paramBlock == Blocks.TRIPWIRE) || (paramBlock == Blocks.WOOL)) {
/* 21 */       paramItemStack.damage(1, paramEntityLiving);
/* 22 */       return true;
/*    */     }
/* 24 */     return super.a(paramItemStack, paramWorld, paramBlock, paramBlockPosition, paramEntityLiving);
/*    */   }
/*    */   
/*    */   public boolean canDestroySpecialBlock(Block paramBlock)
/*    */   {
/* 29 */     return (paramBlock == Blocks.WEB) || (paramBlock == Blocks.REDSTONE_WIRE) || (paramBlock == Blocks.TRIPWIRE);
/*    */   }
/*    */   
/*    */   public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
/*    */   {
/* 34 */     if ((paramBlock == Blocks.WEB) || (paramBlock.getMaterial() == Material.LEAVES)) {
/* 35 */       return 15.0F;
/*    */     }
/* 37 */     if (paramBlock == Blocks.WOOL) {
/* 38 */       return 5.0F;
/*    */     }
/* 40 */     return super.getDestroySpeed(paramItemStack, paramBlock);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemShears.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */