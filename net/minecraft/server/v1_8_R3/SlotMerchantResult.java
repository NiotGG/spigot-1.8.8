/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class SlotMerchantResult
/*    */   extends Slot
/*    */ {
/*    */   private final InventoryMerchant a;
/*    */   
/*    */   private EntityHuman b;
/*    */   
/*    */   private int c;
/*    */   private final IMerchant h;
/*    */   
/*    */   public SlotMerchantResult(EntityHuman paramEntityHuman, IMerchant paramIMerchant, InventoryMerchant paramInventoryMerchant, int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 16 */     super(paramInventoryMerchant, paramInt1, paramInt2, paramInt3);
/* 17 */     this.b = paramEntityHuman;
/* 18 */     this.h = paramIMerchant;
/* 19 */     this.a = paramInventoryMerchant;
/*    */   }
/*    */   
/*    */   public boolean isAllowed(ItemStack paramItemStack)
/*    */   {
/* 24 */     return false;
/*    */   }
/*    */   
/*    */   public ItemStack a(int paramInt)
/*    */   {
/* 29 */     if (hasItem()) {
/* 30 */       this.c += Math.min(paramInt, getItem().count);
/*    */     }
/* 32 */     return super.a(paramInt);
/*    */   }
/*    */   
/*    */   protected void a(ItemStack paramItemStack, int paramInt)
/*    */   {
/* 37 */     this.c += paramInt;
/* 38 */     c(paramItemStack);
/*    */   }
/*    */   
/*    */   protected void c(ItemStack paramItemStack)
/*    */   {
/* 43 */     paramItemStack.a(this.b.world, this.b, this.c);
/* 44 */     this.c = 0;
/*    */   }
/*    */   
/*    */   public void a(EntityHuman paramEntityHuman, ItemStack paramItemStack)
/*    */   {
/* 49 */     c(paramItemStack);
/*    */     
/* 51 */     MerchantRecipe localMerchantRecipe = this.a.getRecipe();
/* 52 */     if (localMerchantRecipe != null) {
/* 53 */       ItemStack localItemStack1 = this.a.getItem(0);
/* 54 */       ItemStack localItemStack2 = this.a.getItem(1);
/*    */       
/*    */ 
/* 57 */       if ((a(localMerchantRecipe, localItemStack1, localItemStack2)) || (a(localMerchantRecipe, localItemStack2, localItemStack1))) {
/* 58 */         this.h.a(localMerchantRecipe);
/* 59 */         paramEntityHuman.b(StatisticList.G);
/*    */         
/* 61 */         if ((localItemStack1 != null) && (localItemStack1.count <= 0)) {
/* 62 */           localItemStack1 = null;
/*    */         }
/* 64 */         if ((localItemStack2 != null) && (localItemStack2.count <= 0)) {
/* 65 */           localItemStack2 = null;
/*    */         }
/* 67 */         this.a.setItem(0, localItemStack1);
/* 68 */         this.a.setItem(1, localItemStack2);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   private boolean a(MerchantRecipe paramMerchantRecipe, ItemStack paramItemStack1, ItemStack paramItemStack2) {
/* 74 */     ItemStack localItemStack1 = paramMerchantRecipe.getBuyItem1();
/* 75 */     ItemStack localItemStack2 = paramMerchantRecipe.getBuyItem2();
/*    */     
/* 77 */     if ((paramItemStack1 != null) && (paramItemStack1.getItem() == localItemStack1.getItem())) {
/* 78 */       if ((localItemStack2 != null) && (paramItemStack2 != null) && (localItemStack2.getItem() == paramItemStack2.getItem())) {
/* 79 */         paramItemStack1.count -= localItemStack1.count;
/* 80 */         paramItemStack2.count -= localItemStack2.count;
/* 81 */         return true; }
/* 82 */       if ((localItemStack2 == null) && (paramItemStack2 == null)) {
/* 83 */         paramItemStack1.count -= localItemStack1.count;
/* 84 */         return true;
/*    */       }
/*    */     }
/* 87 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\SlotMerchantResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */