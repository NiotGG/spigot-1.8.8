/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
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
/*     */ public class ItemPotion
/*     */   extends Item
/*     */ {
/*  33 */   private Map<Integer, List<MobEffect>> a = Maps.newHashMap();
/*  34 */   private static final Map<List<MobEffect>, Integer> b = ;
/*     */   
/*     */   public ItemPotion() {
/*  37 */     c(1);
/*  38 */     a(true);
/*  39 */     setMaxDurability(0);
/*  40 */     a(CreativeModeTab.k);
/*     */   }
/*     */   
/*     */   public List<MobEffect> h(ItemStack paramItemStack) {
/*  44 */     if ((!paramItemStack.hasTag()) || (!paramItemStack.getTag().hasKeyOfType("CustomPotionEffects", 9))) {
/*  45 */       localObject = (List)this.a.get(Integer.valueOf(paramItemStack.getData()));
/*     */       
/*  47 */       if (localObject == null) {
/*  48 */         localObject = PotionBrewer.getEffects(paramItemStack.getData(), false);
/*  49 */         this.a.put(Integer.valueOf(paramItemStack.getData()), localObject);
/*     */       }
/*     */       
/*  52 */       return (List<MobEffect>)localObject;
/*     */     }
/*  54 */     Object localObject = Lists.newArrayList();
/*  55 */     NBTTagList localNBTTagList = paramItemStack.getTag().getList("CustomPotionEffects", 10);
/*     */     
/*  57 */     for (int i = 0; i < localNBTTagList.size(); i++) {
/*  58 */       NBTTagCompound localNBTTagCompound = localNBTTagList.get(i);
/*  59 */       MobEffect localMobEffect = MobEffect.b(localNBTTagCompound);
/*  60 */       if (localMobEffect != null) {
/*  61 */         ((List)localObject).add(localMobEffect);
/*     */       }
/*     */     }
/*     */     
/*  65 */     return (List<MobEffect>)localObject;
/*     */   }
/*     */   
/*     */   public List<MobEffect> e(int paramInt)
/*     */   {
/*  70 */     List localList = (List)this.a.get(Integer.valueOf(paramInt));
/*  71 */     if (localList == null) {
/*  72 */       localList = PotionBrewer.getEffects(paramInt, false);
/*  73 */       this.a.put(Integer.valueOf(paramInt), localList);
/*     */     }
/*  75 */     return localList;
/*     */   }
/*     */   
/*     */   public ItemStack b(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*     */   {
/*  80 */     if (!paramEntityHuman.abilities.canInstantlyBuild) {
/*  81 */       paramItemStack.count -= 1;
/*     */     }
/*     */     
/*  84 */     if (!paramWorld.isClientSide) {
/*  85 */       List localList = h(paramItemStack);
/*  86 */       if (localList != null) {
/*  87 */         for (MobEffect localMobEffect : localList) {
/*  88 */           paramEntityHuman.addEffect(new MobEffect(localMobEffect));
/*     */         }
/*     */       }
/*     */     }
/*  92 */     paramEntityHuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*  93 */     if (!paramEntityHuman.abilities.canInstantlyBuild) {
/*  94 */       if (paramItemStack.count <= 0) {
/*  95 */         return new ItemStack(Items.GLASS_BOTTLE);
/*     */       }
/*  97 */       paramEntityHuman.inventory.pickup(new ItemStack(Items.GLASS_BOTTLE));
/*     */     }
/*     */     
/*     */ 
/* 101 */     return paramItemStack;
/*     */   }
/*     */   
/*     */   public int d(ItemStack paramItemStack)
/*     */   {
/* 106 */     return 32;
/*     */   }
/*     */   
/*     */   public EnumAnimation e(ItemStack paramItemStack)
/*     */   {
/* 111 */     return EnumAnimation.DRINK;
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*     */   {
/* 116 */     if (f(paramItemStack.getData())) {
/* 117 */       if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 118 */         paramItemStack.count -= 1;
/*     */       }
/* 120 */       paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
/* 121 */       if (!paramWorld.isClientSide) {
/* 122 */         paramWorld.addEntity(new EntityPotion(paramWorld, paramEntityHuman, paramItemStack));
/*     */       }
/* 124 */       paramEntityHuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/* 125 */       return paramItemStack;
/*     */     }
/* 127 */     paramEntityHuman.a(paramItemStack, d(paramItemStack));
/* 128 */     return paramItemStack;
/*     */   }
/*     */   
/*     */   public static boolean f(int paramInt) {
/* 132 */     return (paramInt & 0x4000) != 0;
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
/*     */ 
/*     */ 
/*     */   public String a(ItemStack paramItemStack)
/*     */   {
/* 162 */     if (paramItemStack.getData() == 0) {
/* 163 */       return LocaleI18n.get("item.emptyPotion.name").trim();
/*     */     }
/*     */     
/* 166 */     String str1 = "";
/* 167 */     if (f(paramItemStack.getData())) {
/* 168 */       str1 = LocaleI18n.get("potion.prefix.grenade").trim() + " ";
/*     */     }
/*     */     
/* 171 */     List localList = Items.POTION.h(paramItemStack);
/* 172 */     if ((localList != null) && (!localList.isEmpty())) {
/* 173 */       str2 = ((MobEffect)localList.get(0)).g();
/* 174 */       str2 = str2 + ".postfix";
/* 175 */       return str1 + LocaleI18n.get(str2).trim();
/*     */     }
/* 177 */     String str2 = PotionBrewer.c(paramItemStack.getData());
/* 178 */     return LocaleI18n.get(str2).trim() + " " + super.a(paramItemStack);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemPotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */