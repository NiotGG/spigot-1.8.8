/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesFood
/*    */ {
/*    */   public void a(CraftingManager paramCraftingManager)
/*    */   {
/* 10 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.MUSHROOM_STEW), new Object[] { Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Items.BOWL });
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 16 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.COOKIE, 8), new Object[] { "#X#", Character.valueOf('X'), new ItemStack(Items.DYE, 1, EnumColor.BROWN.getInvColorIndex()), Character.valueOf('#'), Items.WHEAT });
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 23 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.RABBIT_STEW), new Object[] { " R ", "CPM", " B ", Character.valueOf('R'), new ItemStack(Items.COOKED_RABBIT), Character.valueOf('C'), Items.CARROT, Character.valueOf('P'), Items.BAKED_POTATO, Character.valueOf('M'), Blocks.BROWN_MUSHROOM, Character.valueOf('B'), Items.BOWL });
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 30 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.RABBIT_STEW), new Object[] { " R ", "CPD", " B ", Character.valueOf('R'), new ItemStack(Items.COOKED_RABBIT), Character.valueOf('C'), Items.CARROT, Character.valueOf('P'), Items.BAKED_POTATO, Character.valueOf('D'), Blocks.RED_MUSHROOM, Character.valueOf('B'), Items.BOWL });
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 37 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.MELON_BLOCK), new Object[] { "MMM", "MMM", "MMM", Character.valueOf('M'), Items.MELON });
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 45 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.MELON_SEEDS), new Object[] { "M", Character.valueOf('M'), Items.MELON });
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 51 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.PUMPKIN_SEEDS, 4), new Object[] { "M", Character.valueOf('M'), Blocks.PUMPKIN });
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 57 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.PUMPKIN_PIE), new Object[] { Blocks.PUMPKIN, Items.SUGAR, Items.EGG });
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 62 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.FERMENTED_SPIDER_EYE), new Object[] { Items.SPIDER_EYE, Blocks.BROWN_MUSHROOM, Items.SUGAR });
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 68 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.BLAZE_POWDER, 2), new Object[] { Items.BLAZE_ROD });
/*    */     
/*    */ 
/*    */ 
/* 72 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.MAGMA_CREAM), new Object[] { Items.BLAZE_POWDER, Items.SLIME });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipesFood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */