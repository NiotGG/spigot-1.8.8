/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemFish
/*     */   extends ItemFood
/*     */ {
/*     */   private final boolean b;
/*     */   
/*     */   public ItemFish(boolean paramBoolean)
/*     */   {
/*  19 */     super(0, 0.0F, false);
/*     */     
/*  21 */     this.b = paramBoolean;
/*     */   }
/*     */   
/*     */   public int getNutrition(ItemStack paramItemStack)
/*     */   {
/*  26 */     EnumFish localEnumFish = EnumFish.a(paramItemStack);
/*     */     
/*  28 */     if ((this.b) && (localEnumFish.g())) {
/*  29 */       return localEnumFish.e();
/*     */     }
/*  31 */     return localEnumFish.c();
/*     */   }
/*     */   
/*     */ 
/*     */   public float getSaturationModifier(ItemStack paramItemStack)
/*     */   {
/*  37 */     EnumFish localEnumFish = EnumFish.a(paramItemStack);
/*     */     
/*  39 */     if ((this.b) && (localEnumFish.g())) {
/*  40 */       return localEnumFish.f();
/*     */     }
/*  42 */     return localEnumFish.d();
/*     */   }
/*     */   
/*     */ 
/*     */   public String j(ItemStack paramItemStack)
/*     */   {
/*  48 */     if (EnumFish.a(paramItemStack) == EnumFish.PUFFERFISH) {
/*  49 */       return PotionBrewer.m;
/*     */     }
/*  51 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void c(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*     */   {
/*  57 */     EnumFish localEnumFish = EnumFish.a(paramItemStack);
/*     */     
/*  59 */     if (localEnumFish == EnumFish.PUFFERFISH) {
/*  60 */       paramEntityHuman.addEffect(new MobEffect(MobEffectList.POISON.id, 1200, 3));
/*  61 */       paramEntityHuman.addEffect(new MobEffect(MobEffectList.HUNGER.id, 300, 2));
/*  62 */       paramEntityHuman.addEffect(new MobEffect(MobEffectList.CONFUSION.id, 300, 1));
/*     */     }
/*     */     
/*  65 */     super.c(paramItemStack, paramWorld, paramEntityHuman);
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
/*     */   public String e_(ItemStack paramItemStack)
/*     */   {
/*  79 */     EnumFish localEnumFish = EnumFish.a(paramItemStack);
/*  80 */     return getName() + "." + localEnumFish.b() + "." + ((this.b) && (localEnumFish.g()) ? "cooked" : "raw");
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumFish
/*     */   {
/*     */     private static final Map<Integer, EnumFish> e;
/*     */     
/*     */     private final int f;
/*     */     
/*     */     private final String g;
/*     */     
/*     */     private final int h;
/*     */     private final float i;
/*     */     private final int j;
/*     */     private final float k;
/*  96 */     private boolean l = false;
/*     */     
/*     */     static
/*     */     {
/*  89 */       e = Maps.newHashMap();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  99 */       for (EnumFish localEnumFish : values()) {
/* 100 */         e.put(Integer.valueOf(localEnumFish.a()), localEnumFish);
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumFish(int paramInt1, String paramString, int paramInt2, float paramFloat1, int paramInt3, float paramFloat2) {
/* 105 */       this.f = paramInt1;
/* 106 */       this.g = paramString;
/* 107 */       this.h = paramInt2;
/* 108 */       this.i = paramFloat1;
/* 109 */       this.j = paramInt3;
/* 110 */       this.k = paramFloat2;
/* 111 */       this.l = true;
/*     */     }
/*     */     
/*     */     private EnumFish(int paramInt1, String paramString, int paramInt2, float paramFloat) {
/* 115 */       this.f = paramInt1;
/* 116 */       this.g = paramString;
/* 117 */       this.h = paramInt2;
/* 118 */       this.i = paramFloat;
/* 119 */       this.j = 0;
/* 120 */       this.k = 0.0F;
/* 121 */       this.l = false;
/*     */     }
/*     */     
/*     */     public int a() {
/* 125 */       return this.f;
/*     */     }
/*     */     
/*     */     public String b() {
/* 129 */       return this.g;
/*     */     }
/*     */     
/*     */     public int c() {
/* 133 */       return this.h;
/*     */     }
/*     */     
/*     */     public float d() {
/* 137 */       return this.i;
/*     */     }
/*     */     
/*     */     public int e() {
/* 141 */       return this.j;
/*     */     }
/*     */     
/*     */     public float f() {
/* 145 */       return this.k;
/*     */     }
/*     */     
/*     */     public boolean g() {
/* 149 */       return this.l;
/*     */     }
/*     */     
/*     */     public static EnumFish a(int paramInt) {
/* 153 */       EnumFish localEnumFish = (EnumFish)e.get(Integer.valueOf(paramInt));
/*     */       
/* 155 */       if (localEnumFish == null) {
/* 156 */         return COD;
/*     */       }
/* 158 */       return localEnumFish;
/*     */     }
/*     */     
/*     */     public static EnumFish a(ItemStack paramItemStack)
/*     */     {
/* 163 */       if ((paramItemStack.getItem() instanceof ItemFish)) {
/* 164 */         return a(paramItemStack.getData());
/*     */       }
/* 166 */       return COD;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemFish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */