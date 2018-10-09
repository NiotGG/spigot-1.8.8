/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipeMapExtend
/*    */   extends ShapedRecipes
/*    */ {
/*    */   public RecipeMapExtend()
/*    */   {
/* 14 */     super(3, 3, new ItemStack[] { new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.FILLED_MAP, 0, 32767), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER) }, new ItemStack(Items.MAP, 0, 0));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean a(InventoryCrafting paramInventoryCrafting, World paramWorld)
/*    */   {
/* 26 */     if (!super.a(paramInventoryCrafting, paramWorld)) {
/* 27 */       return false;
/*    */     }
/* 29 */     Object localObject = null;
/*    */     
/* 31 */     for (int i = 0; (i < paramInventoryCrafting.getSize()) && (localObject == null); i++) {
/* 32 */       ItemStack localItemStack = paramInventoryCrafting.getItem(i);
/* 33 */       if ((localItemStack != null) && (localItemStack.getItem() == Items.FILLED_MAP)) {
/* 34 */         localObject = localItemStack;
/*    */       }
/*    */     }
/*    */     
/* 38 */     if (localObject == null) {
/* 39 */       return false;
/*    */     }
/* 41 */     WorldMap localWorldMap = Items.FILLED_MAP.getSavedMap((ItemStack)localObject, paramWorld);
/* 42 */     if (localWorldMap == null) {
/* 43 */       return false;
/*    */     }
/* 45 */     return localWorldMap.scale < 4;
/*    */   }
/*    */   
/*    */ 
/*    */   public ItemStack craftItem(InventoryCrafting paramInventoryCrafting)
/*    */   {
/* 51 */     Object localObject = null;
/*    */     
/* 53 */     for (int i = 0; (i < paramInventoryCrafting.getSize()) && (localObject == null); i++) {
/* 54 */       ItemStack localItemStack = paramInventoryCrafting.getItem(i);
/* 55 */       if ((localItemStack != null) && (localItemStack.getItem() == Items.FILLED_MAP)) {
/* 56 */         localObject = localItemStack;
/*    */       }
/*    */     }
/*    */     
/* 60 */     localObject = ((ItemStack)localObject).cloneItemStack();
/* 61 */     ((ItemStack)localObject).count = 1;
/*    */     
/* 63 */     if (((ItemStack)localObject).getTag() == null) {
/* 64 */       ((ItemStack)localObject).setTag(new NBTTagCompound());
/*    */     }
/* 66 */     ((ItemStack)localObject).getTag().setBoolean("map_is_scaling", true);
/*    */     
/* 68 */     return (ItemStack)localObject;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipeMapExtend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */