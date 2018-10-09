/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotFurnaceFuel
/*    */   extends Slot
/*    */ {
/*    */   public SlotFurnaceFuel(IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 10 */     super(paramIInventory, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   
/*    */   public boolean isAllowed(ItemStack paramItemStack)
/*    */   {
/* 15 */     return (TileEntityFurnace.isFuel(paramItemStack)) || (c_(paramItemStack));
/*    */   }
/*    */   
/*    */   public int getMaxStackSize(ItemStack paramItemStack)
/*    */   {
/* 20 */     return c_(paramItemStack) ? 1 : super.getMaxStackSize(paramItemStack);
/*    */   }
/*    */   
/*    */   public static boolean c_(ItemStack paramItemStack) {
/* 24 */     return (paramItemStack != null) && (paramItemStack.getItem() != null) && (paramItemStack.getItem() == Items.BUCKET);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\SlotFurnaceFuel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */