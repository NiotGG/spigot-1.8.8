/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class ItemSoup
/*    */   extends ItemFood
/*    */ {
/*    */   public ItemSoup(int paramInt)
/*    */   {
/*  8 */     super(paramInt, false);
/*    */     
/* 10 */     c(1);
/*    */   }
/*    */   
/*    */   public ItemStack b(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*    */   {
/* 15 */     super.b(paramItemStack, paramWorld, paramEntityHuman);
/*    */     
/* 17 */     return new ItemStack(Items.BOWL);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemSoup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */