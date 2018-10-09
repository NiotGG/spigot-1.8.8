/*    */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.RecipesFurnace;
/*    */ import org.bukkit.inventory.FurnaceRecipe;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftFurnaceRecipe extends FurnaceRecipe implements CraftRecipe
/*    */ {
/*    */   public CraftFurnaceRecipe(ItemStack result, ItemStack source)
/*    */   {
/* 11 */     super(result, source.getType(), source.getDurability());
/*    */   }
/*    */   
/*    */   public static CraftFurnaceRecipe fromBukkitRecipe(FurnaceRecipe recipe) {
/* 15 */     if ((recipe instanceof CraftFurnaceRecipe)) {
/* 16 */       return (CraftFurnaceRecipe)recipe;
/*    */     }
/* 18 */     return new CraftFurnaceRecipe(recipe.getResult(), recipe.getInput());
/*    */   }
/*    */   
/*    */   public void addToCraftingManager()
/*    */   {
/* 23 */     ItemStack result = getResult();
/* 24 */     ItemStack input = getInput();
/* 25 */     RecipesFurnace.getInstance().registerRecipe(CraftItemStack.asNMSCopy(input), CraftItemStack.asNMSCopy(result));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftFurnaceRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */