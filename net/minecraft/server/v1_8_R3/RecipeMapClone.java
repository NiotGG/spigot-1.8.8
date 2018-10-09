/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class RecipeMapClone extends ShapelessRecipes implements IRecipe {
/*    */   public RecipeMapClone() {
/*  7 */     super(new ItemStack(Items.MAP, 0, -1), Arrays.asList(new ItemStack[] { new ItemStack(Items.MAP, 0, 0) }));
/*    */   }
/*    */   
/*    */   public boolean a(InventoryCrafting inventorycrafting, World world)
/*    */   {
/* 12 */     int i = 0;
/* 13 */     ItemStack itemstack = null;
/*    */     
/* 15 */     for (int j = 0; j < inventorycrafting.getSize(); j++) {
/* 16 */       ItemStack itemstack1 = inventorycrafting.getItem(j);
/*    */       
/* 18 */       if (itemstack1 != null) {
/* 19 */         if (itemstack1.getItem() == Items.FILLED_MAP) {
/* 20 */           if (itemstack != null) {
/* 21 */             return false;
/*    */           }
/*    */           
/* 24 */           itemstack = itemstack1;
/*    */         } else {
/* 26 */           if (itemstack1.getItem() != Items.MAP) {
/* 27 */             return false;
/*    */           }
/*    */           
/* 30 */           i++;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 35 */     return (itemstack != null) && (i > 0);
/*    */   }
/*    */   
/*    */   public ItemStack craftItem(InventoryCrafting inventorycrafting) {
/* 39 */     int i = 0;
/* 40 */     ItemStack itemstack = null;
/*    */     
/* 42 */     for (int j = 0; j < inventorycrafting.getSize(); j++) {
/* 43 */       ItemStack itemstack1 = inventorycrafting.getItem(j);
/*    */       
/* 45 */       if (itemstack1 != null) {
/* 46 */         if (itemstack1.getItem() == Items.FILLED_MAP) {
/* 47 */           if (itemstack != null) {
/* 48 */             return null;
/*    */           }
/*    */           
/* 51 */           itemstack = itemstack1;
/*    */         } else {
/* 53 */           if (itemstack1.getItem() != Items.MAP) {
/* 54 */             return null;
/*    */           }
/*    */           
/* 57 */           i++;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 62 */     if ((itemstack != null) && (i >= 1)) {
/* 63 */       ItemStack itemstack2 = new ItemStack(Items.FILLED_MAP, i + 1, itemstack.getData());
/*    */       
/* 65 */       if (itemstack.hasName()) {
/* 66 */         itemstack2.c(itemstack.getName());
/*    */       }
/*    */       
/* 69 */       return itemstack2;
/*    */     }
/* 71 */     return null;
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 76 */     return 9;
/*    */   }
/*    */   
/*    */   public ItemStack b() {
/* 80 */     return null;
/*    */   }
/*    */   
/*    */   public ItemStack[] b(InventoryCrafting inventorycrafting) {
/* 84 */     ItemStack[] aitemstack = new ItemStack[inventorycrafting.getSize()];
/*    */     
/* 86 */     for (int i = 0; i < aitemstack.length; i++) {
/* 87 */       ItemStack itemstack = inventorycrafting.getItem(i);
/*    */       
/* 89 */       if ((itemstack != null) && (itemstack.getItem().r())) {
/* 90 */         aitemstack[i] = new ItemStack(itemstack.getItem().q());
/*    */       }
/*    */     }
/*    */     
/* 94 */     return aitemstack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipeMapClone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */