/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemFood
/*    */   extends Item
/*    */ {
/* 11 */   public final int a = 32;
/*    */   
/*    */   private final int b;
/*    */   private final float c;
/*    */   private final boolean d;
/*    */   private boolean k;
/*    */   private int l;
/*    */   private int m;
/*    */   private int n;
/*    */   private float o;
/*    */   
/*    */   public ItemFood(int paramInt, float paramFloat, boolean paramBoolean)
/*    */   {
/* 24 */     this.b = paramInt;
/* 25 */     this.d = paramBoolean;
/* 26 */     this.c = paramFloat;
/* 27 */     a(CreativeModeTab.h);
/*    */   }
/*    */   
/*    */   public ItemFood(int paramInt, boolean paramBoolean) {
/* 31 */     this(paramInt, 0.6F, paramBoolean);
/*    */   }
/*    */   
/*    */   public ItemStack b(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*    */   {
/* 36 */     paramItemStack.count -= 1;
/* 37 */     paramEntityHuman.getFoodData().a(this, paramItemStack);
/* 38 */     paramWorld.makeSound(paramEntityHuman, "random.burp", 0.5F, paramWorld.random.nextFloat() * 0.1F + 0.9F);
/*    */     
/* 40 */     c(paramItemStack, paramWorld, paramEntityHuman);
/* 41 */     paramEntityHuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*    */     
/* 43 */     return paramItemStack;
/*    */   }
/*    */   
/*    */   protected void c(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 47 */     if ((!paramWorld.isClientSide) && (this.l > 0) && (paramWorld.random.nextFloat() < this.o)) {
/* 48 */       paramEntityHuman.addEffect(new MobEffect(this.l, this.m * 20, this.n));
/*    */     }
/*    */   }
/*    */   
/*    */   public int d(ItemStack paramItemStack)
/*    */   {
/* 54 */     return 32;
/*    */   }
/*    */   
/*    */   public EnumAnimation e(ItemStack paramItemStack)
/*    */   {
/* 59 */     return EnumAnimation.EAT;
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*    */   {
/* 64 */     if (paramEntityHuman.j(this.k)) {
/* 65 */       paramEntityHuman.a(paramItemStack, d(paramItemStack));
/*    */     }
/* 67 */     return paramItemStack;
/*    */   }
/*    */   
/*    */   public int getNutrition(ItemStack paramItemStack) {
/* 71 */     return this.b;
/*    */   }
/*    */   
/*    */   public float getSaturationModifier(ItemStack paramItemStack) {
/* 75 */     return this.c;
/*    */   }
/*    */   
/*    */   public boolean g() {
/* 79 */     return this.d;
/*    */   }
/*    */   
/*    */   public ItemFood a(int paramInt1, int paramInt2, int paramInt3, float paramFloat) {
/* 83 */     this.l = paramInt1;
/* 84 */     this.m = paramInt2;
/* 85 */     this.n = paramInt3;
/* 86 */     this.o = paramFloat;
/* 87 */     return this;
/*    */   }
/*    */   
/*    */   public ItemFood h() {
/* 91 */     this.k = true;
/* 92 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemFood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */