/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class RecipeRepair extends ShapelessRecipes implements IRecipe
/*     */ {
/*     */   public RecipeRepair()
/*     */   {
/*  10 */     super(new ItemStack(Items.LEATHER_HELMET), java.util.Arrays.asList(new ItemStack[] { new ItemStack(Items.LEATHER_HELMET) }));
/*     */   }
/*     */   
/*     */   public boolean a(InventoryCrafting inventorycrafting, World world)
/*     */   {
/*  15 */     ArrayList arraylist = com.google.common.collect.Lists.newArrayList();
/*     */     
/*  17 */     for (int i = 0; i < inventorycrafting.getSize(); i++) {
/*  18 */       ItemStack itemstack = inventorycrafting.getItem(i);
/*     */       
/*  20 */       if (itemstack != null) {
/*  21 */         arraylist.add(itemstack);
/*  22 */         if (arraylist.size() > 1) {
/*  23 */           ItemStack itemstack1 = (ItemStack)arraylist.get(0);
/*     */           
/*  25 */           if ((itemstack.getItem() != itemstack1.getItem()) || (itemstack1.count != 1) || (itemstack.count != 1) || (!itemstack1.getItem().usesDurability())) {
/*  26 */             return false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  32 */     return arraylist.size() == 2;
/*     */   }
/*     */   
/*     */   public ItemStack craftItem(InventoryCrafting inventorycrafting) {
/*  36 */     ArrayList arraylist = com.google.common.collect.Lists.newArrayList();
/*     */     
/*     */ 
/*     */ 
/*  40 */     for (int i = 0; i < inventorycrafting.getSize(); i++) {
/*  41 */       ItemStack itemstack = inventorycrafting.getItem(i);
/*  42 */       if (itemstack != null) {
/*  43 */         arraylist.add(itemstack);
/*  44 */         if (arraylist.size() > 1) {
/*  45 */           ItemStack itemstack1 = (ItemStack)arraylist.get(0);
/*     */           
/*  47 */           if ((itemstack.getItem() != itemstack1.getItem()) || (itemstack1.count != 1) || (itemstack.count != 1) || (!itemstack1.getItem().usesDurability())) {
/*  48 */             return null;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  54 */     if (arraylist.size() == 2) {
/*  55 */       ItemStack itemstack2 = (ItemStack)arraylist.get(0);
/*     */       
/*  57 */       ItemStack itemstack = (ItemStack)arraylist.get(1);
/*  58 */       if ((itemstack2.getItem() == itemstack.getItem()) && (itemstack2.count == 1) && (itemstack.count == 1) && (itemstack2.getItem().usesDurability())) {
/*  59 */         Item item = itemstack2.getItem();
/*  60 */         int j = item.getMaxDurability() - itemstack2.h();
/*  61 */         int k = item.getMaxDurability() - itemstack.h();
/*  62 */         int l = j + k + item.getMaxDurability() * 5 / 100;
/*  63 */         int i1 = item.getMaxDurability() - l;
/*     */         
/*  65 */         if (i1 < 0) {
/*  66 */           i1 = 0;
/*     */         }
/*     */         
/*     */ 
/*  70 */         ItemStack result = new ItemStack(itemstack.getItem(), 1, i1);
/*  71 */         List<ItemStack> ingredients = new ArrayList();
/*  72 */         ingredients.add(itemstack2.cloneItemStack());
/*  73 */         ingredients.add(itemstack.cloneItemStack());
/*  74 */         ShapelessRecipes recipe = new ShapelessRecipes(result.cloneItemStack(), ingredients);
/*  75 */         inventorycrafting.currentRecipe = recipe;
/*  76 */         result = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callPreCraftEvent(inventorycrafting, result, CraftingManager.getInstance().lastCraftView, true);
/*  77 */         return result;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  83 */     return null;
/*     */   }
/*     */   
/*     */   public int a() {
/*  87 */     return 4;
/*     */   }
/*     */   
/*     */   public ItemStack b() {
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack[] b(InventoryCrafting inventorycrafting) {
/*  95 */     ItemStack[] aitemstack = new ItemStack[inventorycrafting.getSize()];
/*     */     
/*  97 */     for (int i = 0; i < aitemstack.length; i++) {
/*  98 */       ItemStack itemstack = inventorycrafting.getItem(i);
/*     */       
/* 100 */       if ((itemstack != null) && (itemstack.getItem().r())) {
/* 101 */         aitemstack[i] = new ItemStack(itemstack.getItem().q());
/*     */       }
/*     */     }
/*     */     
/* 105 */     return aitemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipeRepair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */