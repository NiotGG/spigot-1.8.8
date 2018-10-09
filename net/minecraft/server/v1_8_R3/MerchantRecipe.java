/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ public class MerchantRecipe
/*     */ {
/*     */   private ItemStack buyingItem1;
/*     */   
/*     */   private ItemStack buyingItem2;
/*     */   
/*     */   private ItemStack sellingItem;
/*     */   
/*     */   private int uses;
/*     */   
/*     */   private int maxUses;
/*     */   
/*     */   private boolean rewardExp;
/*     */   
/*     */   public MerchantRecipe(NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  20 */     a(paramNBTTagCompound);
/*     */   }
/*     */   
/*     */   public MerchantRecipe(ItemStack paramItemStack1, ItemStack paramItemStack2, ItemStack paramItemStack3) {
/*  24 */     this(paramItemStack1, paramItemStack2, paramItemStack3, 0, 7);
/*     */   }
/*     */   
/*     */   public MerchantRecipe(ItemStack paramItemStack1, ItemStack paramItemStack2, ItemStack paramItemStack3, int paramInt1, int paramInt2) {
/*  28 */     this.buyingItem1 = paramItemStack1;
/*  29 */     this.buyingItem2 = paramItemStack2;
/*  30 */     this.sellingItem = paramItemStack3;
/*  31 */     this.uses = paramInt1;
/*  32 */     this.maxUses = paramInt2;
/*  33 */     this.rewardExp = true;
/*     */   }
/*     */   
/*     */   public MerchantRecipe(ItemStack paramItemStack1, ItemStack paramItemStack2) {
/*  37 */     this(paramItemStack1, null, paramItemStack2);
/*     */   }
/*     */   
/*     */   public MerchantRecipe(ItemStack paramItemStack, Item paramItem) {
/*  41 */     this(paramItemStack, new ItemStack(paramItem));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack getBuyItem1()
/*     */   {
/*  49 */     return this.buyingItem1;
/*     */   }
/*     */   
/*     */   public ItemStack getBuyItem2() {
/*  53 */     return this.buyingItem2;
/*     */   }
/*     */   
/*     */   public boolean hasSecondItem() {
/*  57 */     return this.buyingItem2 != null;
/*     */   }
/*     */   
/*     */   public ItemStack getBuyItem3() {
/*  61 */     return this.sellingItem;
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
/*     */ 
/*     */   public int e()
/*     */   {
/*  77 */     return this.uses;
/*     */   }
/*     */   
/*     */   public int f() {
/*  81 */     return this.maxUses;
/*     */   }
/*     */   
/*     */   public void g() {
/*  85 */     this.uses += 1;
/*     */   }
/*     */   
/*     */   public void a(int paramInt) {
/*  89 */     this.maxUses += paramInt;
/*     */   }
/*     */   
/*     */   public boolean h() {
/*  93 */     return this.uses >= this.maxUses;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean j()
/*     */   {
/* 101 */     return this.rewardExp;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 105 */     NBTTagCompound localNBTTagCompound1 = paramNBTTagCompound.getCompound("buy");
/* 106 */     this.buyingItem1 = ItemStack.createStack(localNBTTagCompound1);
/* 107 */     NBTTagCompound localNBTTagCompound2 = paramNBTTagCompound.getCompound("sell");
/* 108 */     this.sellingItem = ItemStack.createStack(localNBTTagCompound2);
/* 109 */     if (paramNBTTagCompound.hasKeyOfType("buyB", 10)) {
/* 110 */       this.buyingItem2 = ItemStack.createStack(paramNBTTagCompound.getCompound("buyB"));
/*     */     }
/* 112 */     if (paramNBTTagCompound.hasKeyOfType("uses", 99)) {
/* 113 */       this.uses = paramNBTTagCompound.getInt("uses");
/*     */     }
/* 115 */     if (paramNBTTagCompound.hasKeyOfType("maxUses", 99)) {
/* 116 */       this.maxUses = paramNBTTagCompound.getInt("maxUses");
/*     */     } else {
/* 118 */       this.maxUses = 7;
/*     */     }
/* 120 */     if (paramNBTTagCompound.hasKeyOfType("rewardExp", 1)) {
/* 121 */       this.rewardExp = paramNBTTagCompound.getBoolean("rewardExp");
/*     */     } else {
/* 123 */       this.rewardExp = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTTagCompound k() {
/* 128 */     NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/* 129 */     localNBTTagCompound.set("buy", this.buyingItem1.save(new NBTTagCompound()));
/* 130 */     localNBTTagCompound.set("sell", this.sellingItem.save(new NBTTagCompound()));
/* 131 */     if (this.buyingItem2 != null) {
/* 132 */       localNBTTagCompound.set("buyB", this.buyingItem2.save(new NBTTagCompound()));
/*     */     }
/* 134 */     localNBTTagCompound.setInt("uses", this.uses);
/* 135 */     localNBTTagCompound.setInt("maxUses", this.maxUses);
/* 136 */     localNBTTagCompound.setBoolean("rewardExp", this.rewardExp);
/* 137 */     return localNBTTagCompound;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MerchantRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */