/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemCoal
/*    */   extends Item
/*    */ {
/*    */   public ItemCoal()
/*    */   {
/* 12 */     a(true);
/* 13 */     setMaxDurability(0);
/* 14 */     a(CreativeModeTab.l);
/*    */   }
/*    */   
/*    */   public String e_(ItemStack paramItemStack)
/*    */   {
/* 19 */     if (paramItemStack.getData() == 1) {
/* 20 */       return "item.charcoal";
/*    */     }
/* 22 */     return "item.coal";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemCoal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */