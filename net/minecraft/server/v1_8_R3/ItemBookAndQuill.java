/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBookAndQuill
/*    */   extends Item
/*    */ {
/*    */   public ItemBookAndQuill()
/*    */   {
/* 12 */     c(1);
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*    */   {
/* 17 */     paramEntityHuman.openBook(paramItemStack);
/* 18 */     paramEntityHuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/* 19 */     return paramItemStack;
/*    */   }
/*    */   
/*    */   public static boolean b(NBTTagCompound paramNBTTagCompound) {
/* 23 */     if (paramNBTTagCompound == null) {
/* 24 */       return false;
/*    */     }
/* 26 */     if (!paramNBTTagCompound.hasKeyOfType("pages", 9)) {
/* 27 */       return false;
/*    */     }
/*    */     
/* 30 */     NBTTagList localNBTTagList = paramNBTTagCompound.getList("pages", 8);
/* 31 */     for (int i = 0; i < localNBTTagList.size(); i++) {
/* 32 */       String str = localNBTTagList.getString(i);
/*    */       
/* 34 */       if (str == null) {
/* 35 */         return false;
/*    */       }
/* 37 */       if (str.length() > 32767) {
/* 38 */         return false;
/*    */       }
/*    */     }
/*    */     
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemBookAndQuill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */