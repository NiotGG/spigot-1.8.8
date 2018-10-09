/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesArmor
/*    */ {
/*  8 */   private String[][] a = { { "XXX", "X X" }, { "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" }, { "X X", "X X" } };
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
/*    */ 
/* 30 */   private Item[][] b = { { Items.LEATHER, Items.IRON_INGOT, Items.DIAMOND, Items.GOLD_INGOT }, { Items.LEATHER_HELMET, Items.IRON_HELMET, Items.DIAMOND_HELMET, Items.GOLDEN_HELMET }, { Items.LEATHER_CHESTPLATE, Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.GOLDEN_CHESTPLATE }, { Items.LEATHER_LEGGINGS, Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS, Items.GOLDEN_LEGGINGS }, { Items.LEATHER_BOOTS, Items.IRON_BOOTS, Items.DIAMOND_BOOTS, Items.GOLDEN_BOOTS } };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void a(CraftingManager paramCraftingManager)
/*    */   {
/* 39 */     for (int i = 0; i < this.b[0].length; i++) {
/* 40 */       Item localItem1 = this.b[0][i];
/*    */       
/* 42 */       for (int j = 0; j < this.b.length - 1; j++) {
/* 43 */         Item localItem2 = this.b[(j + 1)][i];
/* 44 */         paramCraftingManager.registerShapedRecipe(new ItemStack(localItem2), new Object[] { this.a[j], Character.valueOf('X'), localItem1 });
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipesArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */