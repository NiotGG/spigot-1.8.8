/*    */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.server.v1_8_R3.CraftingManager;
/*    */ import net.minecraft.server.v1_8_R3.ShapelessRecipes;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*    */ import org.bukkit.inventory.ShapelessRecipe;
/*    */ 
/*    */ public class CraftShapelessRecipe
/*    */   extends ShapelessRecipe
/*    */   implements CraftRecipe
/*    */ {
/*    */   private ShapelessRecipes recipe;
/*    */   
/*    */   public CraftShapelessRecipe(org.bukkit.inventory.ItemStack result)
/*    */   {
/* 17 */     super(result);
/*    */   }
/*    */   
/*    */   public CraftShapelessRecipe(org.bukkit.inventory.ItemStack result, ShapelessRecipes recipe) {
/* 21 */     this(result);
/* 22 */     this.recipe = recipe;
/*    */   }
/*    */   
/*    */   public static CraftShapelessRecipe fromBukkitRecipe(ShapelessRecipe recipe) {
/* 26 */     if ((recipe instanceof CraftShapelessRecipe)) {
/* 27 */       return (CraftShapelessRecipe)recipe;
/*    */     }
/* 29 */     CraftShapelessRecipe ret = new CraftShapelessRecipe(recipe.getResult());
/* 30 */     for (org.bukkit.inventory.ItemStack ingred : recipe.getIngredientList()) {
/* 31 */       ret.addIngredient(ingred.getType(), ingred.getDurability());
/*    */     }
/* 33 */     return ret;
/*    */   }
/*    */   
/*    */   public void addToCraftingManager() {
/* 37 */     List<org.bukkit.inventory.ItemStack> ingred = getIngredientList();
/* 38 */     Object[] data = new Object[ingred.size()];
/* 39 */     int i = 0;
/* 40 */     for (org.bukkit.inventory.ItemStack mdata : ingred) {
/* 41 */       int id = mdata.getTypeId();
/* 42 */       short dmg = mdata.getDurability();
/* 43 */       data[i] = new net.minecraft.server.v1_8_R3.ItemStack(CraftMagicNumbers.getItem(id), 1, dmg);
/* 44 */       i++;
/*    */     }
/* 46 */     CraftingManager.getInstance().registerShapelessRecipe(CraftItemStack.asNMSCopy(getResult()), data);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftShapelessRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */