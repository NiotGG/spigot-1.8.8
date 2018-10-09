/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class ItemAnvil extends ItemMultiTexture
/*    */ {
/*    */   public ItemAnvil(Block paramBlock)
/*    */   {
/*  7 */     super(paramBlock, paramBlock, new String[] { "intact", "slightlyDamaged", "veryDamaged" });
/*    */   }
/*    */   
/*    */   public int filterData(int paramInt)
/*    */   {
/* 12 */     return paramInt << 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemAnvil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */