/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesWeapons
/*    */ {
/*  9 */   private String[][] a = { { "X", "X", "#" } };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 17 */   private Object[][] b = { { Blocks.PLANKS, Blocks.COBBLESTONE, Items.IRON_INGOT, Items.DIAMOND, Items.GOLD_INGOT }, { Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.DIAMOND_SWORD, Items.GOLDEN_SWORD } };
/*    */   
/*    */ 
/*    */ 
/*    */   public void a(CraftingManager paramCraftingManager)
/*    */   {
/* 23 */     for (int i = 0; i < this.b[0].length; i++) {
/* 24 */       Object localObject = this.b[0][i];
/*    */       
/* 26 */       for (int j = 0; j < this.b.length - 1; j++) {
/* 27 */         Item localItem = (Item)this.b[(j + 1)][i];
/* 28 */         paramCraftingManager.registerShapedRecipe(new ItemStack(localItem), new Object[] { this.a[j], Character.valueOf('#'), Items.STICK, Character.valueOf('X'), localObject });
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 37 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.BOW, 1), new Object[] { " #X", "# X", " #X", Character.valueOf('X'), Items.STRING, Character.valueOf('#'), Items.STICK });
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 46 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.ARROW, 4), new Object[] { "X", "#", "Y", Character.valueOf('Y'), Items.FEATHER, Character.valueOf('X'), Items.FLINT, Character.valueOf('#'), Items.STICK });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipesWeapons.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */