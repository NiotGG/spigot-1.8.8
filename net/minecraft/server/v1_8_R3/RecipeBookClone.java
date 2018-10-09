/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class RecipeBookClone extends ShapelessRecipes implements IRecipe {
/*    */   public RecipeBookClone() {
/*  7 */     super(new ItemStack(Items.WRITTEN_BOOK, 0, -1), Arrays.asList(new ItemStack[] { new ItemStack(Items.WRITABLE_BOOK, 0, 0) }));
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
/* 19 */         if (itemstack1.getItem() == Items.WRITTEN_BOOK) {
/* 20 */           if (itemstack != null) {
/* 21 */             return false;
/*    */           }
/*    */           
/* 24 */           itemstack = itemstack1;
/*    */         } else {
/* 26 */           if (itemstack1.getItem() != Items.WRITABLE_BOOK) {
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
/* 46 */         if (itemstack1.getItem() == Items.WRITTEN_BOOK) {
/* 47 */           if (itemstack != null) {
/* 48 */             return null;
/*    */           }
/*    */           
/* 51 */           itemstack = itemstack1;
/*    */         } else {
/* 53 */           if (itemstack1.getItem() != Items.WRITABLE_BOOK) {
/* 54 */             return null;
/*    */           }
/*    */           
/* 57 */           i++;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 62 */     if ((itemstack != null) && (i >= 1) && (ItemWrittenBook.h(itemstack) < 2)) {
/* 63 */       ItemStack itemstack2 = new ItemStack(Items.WRITTEN_BOOK, i);
/*    */       
/* 65 */       itemstack2.setTag((NBTTagCompound)itemstack.getTag().clone());
/* 66 */       itemstack2.getTag().setInt("generation", ItemWrittenBook.h(itemstack) + 1);
/* 67 */       if (itemstack.hasName()) {
/* 68 */         itemstack2.c(itemstack.getName());
/*    */       }
/*    */       
/* 71 */       return itemstack2;
/*    */     }
/* 73 */     return null;
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 78 */     return 9;
/*    */   }
/*    */   
/*    */   public ItemStack b() {
/* 82 */     return null;
/*    */   }
/*    */   
/*    */   public ItemStack[] b(InventoryCrafting inventorycrafting) {
/* 86 */     ItemStack[] aitemstack = new ItemStack[inventorycrafting.getSize()];
/*    */     
/* 88 */     for (int i = 0; i < aitemstack.length; i++) {
/* 89 */       ItemStack itemstack = inventorycrafting.getItem(i);
/*    */       
/* 91 */       if ((itemstack != null) && ((itemstack.getItem() instanceof ItemWrittenBook))) {
/* 92 */         aitemstack[i] = itemstack;
/* 93 */         break;
/*    */       }
/*    */     }
/*    */     
/* 97 */     return aitemstack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipeBookClone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */