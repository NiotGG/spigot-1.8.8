/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ public class SlotResult
/*     */   extends Slot
/*     */ {
/*     */   private final InventoryCrafting a;
/*     */   
/*     */   private final EntityHuman b;
/*     */   
/*     */   private int c;
/*     */   
/*     */ 
/*     */   public SlotResult(EntityHuman paramEntityHuman, InventoryCrafting paramInventoryCrafting, IInventory paramIInventory, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  16 */     super(paramIInventory, paramInt1, paramInt2, paramInt3);
/*  17 */     this.b = paramEntityHuman;
/*  18 */     this.a = paramInventoryCrafting;
/*     */   }
/*     */   
/*     */   public boolean isAllowed(ItemStack paramItemStack)
/*     */   {
/*  23 */     return false;
/*     */   }
/*     */   
/*     */   public ItemStack a(int paramInt)
/*     */   {
/*  28 */     if (hasItem()) {
/*  29 */       this.c += Math.min(paramInt, getItem().count);
/*     */     }
/*  31 */     return super.a(paramInt);
/*     */   }
/*     */   
/*     */   protected void a(ItemStack paramItemStack, int paramInt)
/*     */   {
/*  36 */     this.c += paramInt;
/*  37 */     c(paramItemStack);
/*     */   }
/*     */   
/*     */   protected void c(ItemStack paramItemStack)
/*     */   {
/*  42 */     if (this.c > 0) {
/*  43 */       paramItemStack.a(this.b.world, this.b, this.c);
/*     */     }
/*  45 */     this.c = 0;
/*     */     
/*  47 */     if (paramItemStack.getItem() == Item.getItemOf(Blocks.CRAFTING_TABLE)) {
/*  48 */       this.b.b(AchievementList.h);
/*     */     }
/*  50 */     if ((paramItemStack.getItem() instanceof ItemPickaxe)) {
/*  51 */       this.b.b(AchievementList.i);
/*     */     }
/*  53 */     if (paramItemStack.getItem() == Item.getItemOf(Blocks.FURNACE)) {
/*  54 */       this.b.b(AchievementList.j);
/*     */     }
/*  56 */     if ((paramItemStack.getItem() instanceof ItemHoe)) {
/*  57 */       this.b.b(AchievementList.l);
/*     */     }
/*  59 */     if (paramItemStack.getItem() == Items.BREAD) {
/*  60 */       this.b.b(AchievementList.m);
/*     */     }
/*  62 */     if (paramItemStack.getItem() == Items.CAKE) {
/*  63 */       this.b.b(AchievementList.n);
/*     */     }
/*  65 */     if (((paramItemStack.getItem() instanceof ItemPickaxe)) && (((ItemPickaxe)paramItemStack.getItem()).g() != Item.EnumToolMaterial.WOOD)) {
/*  66 */       this.b.b(AchievementList.o);
/*     */     }
/*  68 */     if ((paramItemStack.getItem() instanceof ItemSword)) {
/*  69 */       this.b.b(AchievementList.r);
/*     */     }
/*  71 */     if (paramItemStack.getItem() == Item.getItemOf(Blocks.ENCHANTING_TABLE)) {
/*  72 */       this.b.b(AchievementList.E);
/*     */     }
/*  74 */     if (paramItemStack.getItem() == Item.getItemOf(Blocks.BOOKSHELF)) {
/*  75 */       this.b.b(AchievementList.G);
/*     */     }
/*  77 */     if ((paramItemStack.getItem() == Items.GOLDEN_APPLE) && (paramItemStack.getData() == 1)) {
/*  78 */       this.b.b(AchievementList.M);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(EntityHuman paramEntityHuman, ItemStack paramItemStack)
/*     */   {
/*  84 */     c(paramItemStack);
/*     */     
/*  86 */     ItemStack[] arrayOfItemStack = CraftingManager.getInstance().b(this.a, paramEntityHuman.world);
/*     */     
/*  88 */     for (int i = 0; i < arrayOfItemStack.length; i++) {
/*  89 */       ItemStack localItemStack1 = this.a.getItem(i);
/*  90 */       ItemStack localItemStack2 = arrayOfItemStack[i];
/*     */       
/*  92 */       if (localItemStack1 != null) {
/*  93 */         this.a.splitStack(i, 1);
/*     */       }
/*     */       
/*  96 */       if (localItemStack2 != null) {
/*  97 */         if (this.a.getItem(i) == null)
/*     */         {
/*  99 */           this.a.setItem(i, localItemStack2);
/* 100 */         } else if (!this.b.inventory.pickup(localItemStack2))
/*     */         {
/* 102 */           this.b.drop(localItemStack2, false);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\SlotResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */