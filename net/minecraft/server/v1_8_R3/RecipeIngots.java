/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipeIngots
/*    */ {
/* 12 */   private Object[][] a = { { Blocks.GOLD_BLOCK, new ItemStack(Items.GOLD_INGOT, 9) }, { Blocks.IRON_BLOCK, new ItemStack(Items.IRON_INGOT, 9) }, { Blocks.DIAMOND_BLOCK, new ItemStack(Items.DIAMOND, 9) }, { Blocks.EMERALD_BLOCK, new ItemStack(Items.EMERALD, 9) }, { Blocks.LAPIS_BLOCK, new ItemStack(Items.DYE, 9, EnumColor.BLUE.getInvColorIndex()) }, { Blocks.REDSTONE_BLOCK, new ItemStack(Items.REDSTONE, 9) }, { Blocks.COAL_BLOCK, new ItemStack(Items.COAL, 9, 0) }, { Blocks.HAY_BLOCK, new ItemStack(Items.WHEAT, 9) }, { Blocks.SLIME, new ItemStack(Items.SLIME, 9) } };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void a(CraftingManager paramCraftingManager)
/*    */   {
/* 25 */     for (int i = 0; i < this.a.length; i++) {
/* 26 */       Block localBlock = (Block)this.a[i][0];
/* 27 */       ItemStack localItemStack = (ItemStack)this.a[i][1];
/* 28 */       paramCraftingManager.registerShapedRecipe(new ItemStack(localBlock), new Object[] { "###", "###", "###", Character.valueOf('#'), localItemStack });
/*    */       
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 36 */       paramCraftingManager.registerShapedRecipe(localItemStack, new Object[] { "#", Character.valueOf('#'), localBlock });
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 42 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.GOLD_INGOT), new Object[] { "###", "###", "###", Character.valueOf('#'), Items.GOLD_NUGGET });
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 49 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.GOLD_NUGGET, 9), new Object[] { "#", Character.valueOf('#'), Items.GOLD_INGOT });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipeIngots.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */