/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class ItemCloth extends ItemBlock
/*    */ {
/*    */   public ItemCloth(Block paramBlock)
/*    */   {
/*  7 */     super(paramBlock);
/*    */     
/*  9 */     setMaxDurability(0);
/* 10 */     a(true);
/*    */   }
/*    */   
/*    */   public int filterData(int paramInt)
/*    */   {
/* 15 */     return paramInt;
/*    */   }
/*    */   
/*    */   public String e_(ItemStack paramItemStack)
/*    */   {
/* 20 */     return super.getName() + "." + EnumColor.fromColorIndex(paramItemStack.getData()).d();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemCloth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */