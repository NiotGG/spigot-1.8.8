/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftShapedRecipe;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*     */ 
/*     */ public class ShapedRecipes implements IRecipe
/*     */ {
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final ItemStack[] items;
/*     */   public ItemStack result;
/*     */   private boolean e;
/*     */   
/*     */   public ShapedRecipes(int i, int j, ItemStack[] aitemstack, ItemStack itemstack)
/*     */   {
/*  17 */     this.width = i;
/*  18 */     this.height = j;
/*  19 */     this.items = aitemstack;
/*  20 */     this.result = itemstack;
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ShapedRecipe toBukkitRecipe()
/*     */   {
/*  25 */     CraftItemStack result = CraftItemStack.asCraftMirror(this.result);
/*  26 */     CraftShapedRecipe recipe = new CraftShapedRecipe(result, this);
/*  27 */     switch (this.height) {
/*     */     case 1: 
/*  29 */       switch (this.width) {
/*     */       case 1: 
/*  31 */         recipe.shape(new String[] { "a" });
/*  32 */         break;
/*     */       case 2: 
/*  34 */         recipe.shape(new String[] { "ab" });
/*  35 */         break;
/*     */       case 3: 
/*  37 */         recipe.shape(new String[] { "abc" });
/*     */       }
/*     */       
/*  40 */       break;
/*     */     case 2: 
/*  42 */       switch (this.width) {
/*     */       case 1: 
/*  44 */         recipe.shape(new String[] { "a", "b" });
/*  45 */         break;
/*     */       case 2: 
/*  47 */         recipe.shape(new String[] { "ab", "cd" });
/*  48 */         break;
/*     */       case 3: 
/*  50 */         recipe.shape(new String[] { "abc", "def" });
/*     */       }
/*     */       
/*  53 */       break;
/*     */     case 3: 
/*  55 */       switch (this.width) {
/*     */       case 1: 
/*  57 */         recipe.shape(new String[] { "a", "b", "c" });
/*  58 */         break;
/*     */       case 2: 
/*  60 */         recipe.shape(new String[] { "ab", "cd", "ef" });
/*  61 */         break;
/*     */       case 3: 
/*  63 */         recipe.shape(new String[] { "abc", "def", "ghi" });
/*     */       }
/*     */       
/*     */       break;
/*     */     }
/*  68 */     char c = 'a';
/*  69 */     ItemStack[] arrayOfItemStack; int i = (arrayOfItemStack = this.items).length; for (int j = 0; j < i; j++) { ItemStack stack = arrayOfItemStack[j];
/*  70 */       if (stack != null) {
/*  71 */         recipe.setIngredient(c, CraftMagicNumbers.getMaterial(stack.getItem()), stack.getData());
/*     */       }
/*  73 */       c = (char)(c + '\001');
/*     */     }
/*  75 */     return recipe;
/*     */   }
/*     */   
/*     */   public ItemStack b()
/*     */   {
/*  80 */     return this.result;
/*     */   }
/*     */   
/*     */   public ItemStack[] b(InventoryCrafting inventorycrafting) {
/*  84 */     ItemStack[] aitemstack = new ItemStack[inventorycrafting.getSize()];
/*     */     
/*  86 */     for (int i = 0; i < aitemstack.length; i++) {
/*  87 */       ItemStack itemstack = inventorycrafting.getItem(i);
/*     */       
/*  89 */       if ((itemstack != null) && (itemstack.getItem().r())) {
/*  90 */         aitemstack[i] = new ItemStack(itemstack.getItem().q());
/*     */       }
/*     */     }
/*     */     
/*  94 */     return aitemstack;
/*     */   }
/*     */   
/*     */   public boolean a(InventoryCrafting inventorycrafting, World world) {
/*  98 */     for (int i = 0; i <= 3 - this.width; i++) {
/*  99 */       for (int j = 0; j <= 3 - this.height; j++) {
/* 100 */         if (a(inventorycrafting, i, j, true)) {
/* 101 */           return true;
/*     */         }
/*     */         
/* 104 */         if (a(inventorycrafting, i, j, false)) {
/* 105 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   private boolean a(InventoryCrafting inventorycrafting, int i, int j, boolean flag) {
/* 114 */     for (int k = 0; k < 3; k++) {
/* 115 */       for (int l = 0; l < 3; l++) {
/* 116 */         int i1 = k - i;
/* 117 */         int j1 = l - j;
/* 118 */         ItemStack itemstack = null;
/*     */         
/* 120 */         if ((i1 >= 0) && (j1 >= 0) && (i1 < this.width) && (j1 < this.height)) {
/* 121 */           if (flag) {
/* 122 */             itemstack = this.items[(this.width - i1 - 1 + j1 * this.width)];
/*     */           } else {
/* 124 */             itemstack = this.items[(i1 + j1 * this.width)];
/*     */           }
/*     */         }
/*     */         
/* 128 */         ItemStack itemstack1 = inventorycrafting.c(k, l);
/*     */         
/* 130 */         if ((itemstack1 != null) || (itemstack != null)) {
/* 131 */           if (((itemstack1 == null) && (itemstack != null)) || ((itemstack1 != null) && (itemstack == null))) {
/* 132 */             return false;
/*     */           }
/*     */           
/* 135 */           if (itemstack.getItem() != itemstack1.getItem()) {
/* 136 */             return false;
/*     */           }
/*     */           
/* 139 */           if ((itemstack.getData() != 32767) && (itemstack.getData() != itemstack1.getData())) {
/* 140 */             return false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 146 */     return true;
/*     */   }
/*     */   
/*     */   public ItemStack craftItem(InventoryCrafting inventorycrafting) {
/* 150 */     ItemStack itemstack = b().cloneItemStack();
/*     */     
/* 152 */     if (this.e) {
/* 153 */       for (int i = 0; i < inventorycrafting.getSize(); i++) {
/* 154 */         ItemStack itemstack1 = inventorycrafting.getItem(i);
/*     */         
/* 156 */         if ((itemstack1 != null) && (itemstack1.hasTag())) {
/* 157 */           itemstack.setTag((NBTTagCompound)itemstack1.getTag().clone());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 162 */     return itemstack;
/*     */   }
/*     */   
/*     */   public int a() {
/* 166 */     return this.width * this.height;
/*     */   }
/*     */   
/*     */ 
/*     */   public java.util.List<ItemStack> getIngredients()
/*     */   {
/* 172 */     return java.util.Arrays.asList(this.items);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ShapedRecipes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */