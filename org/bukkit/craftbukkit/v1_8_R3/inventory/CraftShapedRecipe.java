/*    */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.v1_8_R3.CraftingManager;
/*    */ import net.minecraft.server.v1_8_R3.ShapedRecipes;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*    */ import org.bukkit.inventory.ShapedRecipe;
/*    */ 
/*    */ public class CraftShapedRecipe extends ShapedRecipe implements CraftRecipe
/*    */ {
/*    */   private ShapedRecipes recipe;
/*    */   
/*    */   public CraftShapedRecipe(org.bukkit.inventory.ItemStack result)
/*    */   {
/* 17 */     super(result);
/*    */   }
/*    */   
/*    */   public CraftShapedRecipe(org.bukkit.inventory.ItemStack result, ShapedRecipes recipe) {
/* 21 */     this(result);
/* 22 */     this.recipe = recipe;
/*    */   }
/*    */   
/*    */   public static CraftShapedRecipe fromBukkitRecipe(ShapedRecipe recipe) {
/* 26 */     if ((recipe instanceof CraftShapedRecipe)) {
/* 27 */       return (CraftShapedRecipe)recipe;
/*    */     }
/* 29 */     CraftShapedRecipe ret = new CraftShapedRecipe(recipe.getResult());
/* 30 */     String[] shape = recipe.getShape();
/* 31 */     ret.shape(shape);
/* 32 */     Map<Character, org.bukkit.inventory.ItemStack> ingredientMap = recipe.getIngredientMap();
/* 33 */     for (Iterator localIterator = ingredientMap.keySet().iterator(); localIterator.hasNext();) { char c = ((Character)localIterator.next()).charValue();
/* 34 */       org.bukkit.inventory.ItemStack stack = (org.bukkit.inventory.ItemStack)ingredientMap.get(Character.valueOf(c));
/* 35 */       if (stack != null) {
/* 36 */         ret.setIngredient(c, stack.getType(), stack.getDurability());
/*    */       }
/*    */     }
/* 39 */     return ret;
/*    */   }
/*    */   
/*    */   public void addToCraftingManager()
/*    */   {
/* 44 */     String[] shape = getShape();
/* 45 */     Map<Character, org.bukkit.inventory.ItemStack> ingred = getIngredientMap();
/* 46 */     int datalen = shape.length;
/* 47 */     datalen += ingred.size() * 2;
/* 48 */     int i = 0;
/* 49 */     Object[] data = new Object[datalen];
/* 50 */     for (; i < shape.length; i++) {
/* 51 */       data[i] = shape[i];
/*    */     }
/* 53 */     for (Iterator localIterator = ingred.keySet().iterator(); localIterator.hasNext();) { char c = ((Character)localIterator.next()).charValue();
/* 54 */       org.bukkit.inventory.ItemStack mdata = (org.bukkit.inventory.ItemStack)ingred.get(Character.valueOf(c));
/* 55 */       if (mdata != null) {
/* 56 */         data[i] = Character.valueOf(c);
/* 57 */         i++;
/* 58 */         int id = mdata.getTypeId();
/* 59 */         short dmg = mdata.getDurability();
/* 60 */         data[i] = new net.minecraft.server.v1_8_R3.ItemStack(CraftMagicNumbers.getItem(id), 1, dmg);
/* 61 */         i++;
/*    */       } }
/* 63 */     CraftingManager.getInstance().registerShapedRecipe(CraftItemStack.asNMSCopy(getResult()), data);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftShapedRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */