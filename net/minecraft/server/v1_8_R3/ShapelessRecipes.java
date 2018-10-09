/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftShapelessRecipe;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*    */ import org.bukkit.inventory.ShapelessRecipe;
/*    */ 
/*    */ public class ShapelessRecipes implements IRecipe
/*    */ {
/*    */   public final ItemStack result;
/*    */   private final List<ItemStack> ingredients;
/*    */   
/*    */   public ShapelessRecipes(ItemStack itemstack, List<ItemStack> list)
/*    */   {
/* 19 */     this.result = itemstack;
/* 20 */     this.ingredients = list;
/*    */   }
/*    */   
/*    */ 
/*    */   public ShapelessRecipe toBukkitRecipe()
/*    */   {
/* 26 */     CraftItemStack result = CraftItemStack.asCraftMirror(this.result);
/* 27 */     CraftShapelessRecipe recipe = new CraftShapelessRecipe(result, this);
/* 28 */     for (ItemStack stack : this.ingredients) {
/* 29 */       if (stack != null) {
/* 30 */         recipe.addIngredient(CraftMagicNumbers.getMaterial(stack.getItem()), stack.getData());
/*    */       }
/*    */     }
/* 33 */     return recipe;
/*    */   }
/*    */   
/*    */   public ItemStack b()
/*    */   {
/* 38 */     return this.result;
/*    */   }
/*    */   
/*    */   public ItemStack[] b(InventoryCrafting inventorycrafting) {
/* 42 */     ItemStack[] aitemstack = new ItemStack[inventorycrafting.getSize()];
/*    */     
/* 44 */     for (int i = 0; i < aitemstack.length; i++) {
/* 45 */       ItemStack itemstack = inventorycrafting.getItem(i);
/*    */       
/* 47 */       if ((itemstack != null) && (itemstack.getItem().r())) {
/* 48 */         aitemstack[i] = new ItemStack(itemstack.getItem().q());
/*    */       }
/*    */     }
/*    */     
/* 52 */     return aitemstack;
/*    */   }
/*    */   
/*    */   public boolean a(InventoryCrafting inventorycrafting, World world) {
/* 56 */     ArrayList arraylist = com.google.common.collect.Lists.newArrayList(this.ingredients);
/*    */     
/* 58 */     for (int i = 0; i < inventorycrafting.h(); i++) {
/* 59 */       for (int j = 0; j < inventorycrafting.i(); j++) {
/* 60 */         ItemStack itemstack = inventorycrafting.c(j, i);
/*    */         
/* 62 */         if (itemstack != null) {
/* 63 */           boolean flag = false;
/* 64 */           Iterator iterator = arraylist.iterator();
/*    */           
/* 66 */           while (iterator.hasNext()) {
/* 67 */             ItemStack itemstack1 = (ItemStack)iterator.next();
/*    */             
/* 69 */             if ((itemstack.getItem() == itemstack1.getItem()) && ((itemstack1.getData() == 32767) || (itemstack.getData() == itemstack1.getData()))) {
/* 70 */               flag = true;
/* 71 */               arraylist.remove(itemstack1);
/* 72 */               break;
/*    */             }
/*    */           }
/*    */           
/* 76 */           if (!flag) {
/* 77 */             return false;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 83 */     return arraylist.isEmpty();
/*    */   }
/*    */   
/*    */   public ItemStack craftItem(InventoryCrafting inventorycrafting) {
/* 87 */     return this.result.cloneItemStack();
/*    */   }
/*    */   
/*    */   public int a() {
/* 91 */     return this.ingredients.size();
/*    */   }
/*    */   
/*    */ 
/*    */   public List<ItemStack> getIngredients()
/*    */   {
/* 97 */     return Collections.unmodifiableList(this.ingredients);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ShapelessRecipes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */