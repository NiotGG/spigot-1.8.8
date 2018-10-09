/*     */ package org.bukkit.inventory;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShapedRecipe
/*     */   implements Recipe
/*     */ {
/*     */   private ItemStack output;
/*     */   private String[] rows;
/*  17 */   private Map<Character, ItemStack> ingredients = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ShapedRecipe(ItemStack result)
/*     */   {
/*  31 */     this.output = new ItemStack(result);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ShapedRecipe shape(String... shape)
/*     */   {
/*  46 */     Validate.notNull(shape, "Must provide a shape");
/*  47 */     Validate.isTrue((shape.length > 0) && (shape.length < 4), "Crafting recipes should be 1, 2, 3 rows, not ", shape.length);
/*     */     String[] arrayOfString1;
/*  49 */     int j = (arrayOfString1 = shape).length; for (int k = 0; k < j; k++) { String row = arrayOfString1[k];
/*  50 */       Validate.notNull(row, "Shape cannot have null rows");
/*  51 */       Validate.isTrue((row.length() > 0) && (row.length() < 4), "Crafting rows should be 1, 2, or 3 characters, not ", row.length());
/*     */     }
/*  53 */     this.rows = new String[shape.length];
/*  54 */     for (int i = 0; i < shape.length; i++) {
/*  55 */       this.rows[i] = shape[i];
/*     */     }
/*     */     
/*     */ 
/*  59 */     HashMap<Character, ItemStack> newIngredients = new HashMap();
/*  60 */     String[] arrayOfString2; int i = (arrayOfString2 = shape).length; for (j = 0; j < i; j++) { String row = arrayOfString2[j];
/*  61 */       char[] arrayOfChar; int m = (arrayOfChar = row.toCharArray()).length; for (int n = 0; n < m; n++) { Character c = Character.valueOf(arrayOfChar[n]);
/*  62 */         newIngredients.put(c, (ItemStack)this.ingredients.get(c));
/*     */       }
/*     */     }
/*  65 */     this.ingredients = newIngredients;
/*     */     
/*  67 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ShapedRecipe setIngredient(char key, MaterialData ingredient)
/*     */   {
/*  78 */     return setIngredient(key, ingredient.getItemType(), ingredient.getData());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ShapedRecipe setIngredient(char key, Material ingredient)
/*     */   {
/*  89 */     return setIngredient(key, ingredient, 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public ShapedRecipe setIngredient(char key, Material ingredient, int raw)
/*     */   {
/* 103 */     Validate.isTrue(this.ingredients.containsKey(Character.valueOf(key)), "Symbol does not appear in the shape:", key);
/*     */     
/*     */ 
/* 106 */     if (raw == -1) {
/* 107 */       raw = 32767;
/*     */     }
/*     */     
/* 110 */     this.ingredients.put(Character.valueOf(key), new ItemStack(ingredient, 1, (short)raw));
/* 111 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<Character, ItemStack> getIngredientMap()
/*     */   {
/* 120 */     HashMap<Character, ItemStack> result = new HashMap();
/* 121 */     for (Map.Entry<Character, ItemStack> ingredient : this.ingredients.entrySet()) {
/* 122 */       if (ingredient.getValue() == null) {
/* 123 */         result.put((Character)ingredient.getKey(), null);
/*     */       } else {
/* 125 */         result.put((Character)ingredient.getKey(), ((ItemStack)ingredient.getValue()).clone());
/*     */       }
/*     */     }
/* 128 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getShape()
/*     */   {
/* 137 */     return (String[])this.rows.clone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack getResult()
/*     */   {
/* 146 */     return this.output.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\ShapedRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */