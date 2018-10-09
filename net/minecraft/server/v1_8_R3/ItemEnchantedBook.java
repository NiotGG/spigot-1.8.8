/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
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
/*     */ public class ItemEnchantedBook
/*     */   extends Item
/*     */ {
/*     */   public boolean f_(ItemStack paramItemStack)
/*     */   {
/*  26 */     return false;
/*     */   }
/*     */   
/*     */   public EnumItemRarity g(ItemStack paramItemStack)
/*     */   {
/*  31 */     if (h(paramItemStack).size() > 0) {
/*  32 */       return EnumItemRarity.UNCOMMON;
/*     */     }
/*  34 */     return super.g(paramItemStack);
/*     */   }
/*     */   
/*     */   public NBTTagList h(ItemStack paramItemStack)
/*     */   {
/*  39 */     NBTTagCompound localNBTTagCompound = paramItemStack.getTag();
/*  40 */     if ((localNBTTagCompound == null) || (!localNBTTagCompound.hasKeyOfType("StoredEnchantments", 9))) {
/*  41 */       return new NBTTagList();
/*     */     }
/*     */     
/*  44 */     return (NBTTagList)localNBTTagCompound.get("StoredEnchantments");
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
/*     */   public void a(ItemStack paramItemStack, WeightedRandomEnchant paramWeightedRandomEnchant)
/*     */   {
/*  66 */     NBTTagList localNBTTagList = h(paramItemStack);
/*  67 */     int i = 1;
/*     */     
/*  69 */     for (int j = 0; j < localNBTTagList.size(); j++) {
/*  70 */       NBTTagCompound localNBTTagCompound2 = localNBTTagList.get(j);
/*     */       
/*  72 */       if (localNBTTagCompound2.getShort("id") == paramWeightedRandomEnchant.enchantment.id) {
/*  73 */         if (localNBTTagCompound2.getShort("lvl") < paramWeightedRandomEnchant.level) {
/*  74 */           localNBTTagCompound2.setShort("lvl", (short)paramWeightedRandomEnchant.level);
/*     */         }
/*     */         
/*  77 */         i = 0;
/*  78 */         break;
/*     */       }
/*     */     }
/*     */     
/*  82 */     if (i != 0) {
/*  83 */       NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
/*     */       
/*  85 */       localNBTTagCompound1.setShort("id", (short)paramWeightedRandomEnchant.enchantment.id);
/*  86 */       localNBTTagCompound1.setShort("lvl", (short)paramWeightedRandomEnchant.level);
/*     */       
/*  88 */       localNBTTagList.add(localNBTTagCompound1);
/*     */     }
/*     */     
/*  91 */     if (!paramItemStack.hasTag()) {
/*  92 */       paramItemStack.setTag(new NBTTagCompound());
/*     */     }
/*  94 */     paramItemStack.getTag().set("StoredEnchantments", localNBTTagList);
/*     */   }
/*     */   
/*     */   public ItemStack a(WeightedRandomEnchant paramWeightedRandomEnchant) {
/*  98 */     ItemStack localItemStack = new ItemStack(this);
/*  99 */     a(localItemStack, paramWeightedRandomEnchant);
/* 100 */     return localItemStack;
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
/*     */   public StructurePieceTreasure b(Random paramRandom)
/*     */   {
/* 120 */     return a(paramRandom, 1, 1, 1);
/*     */   }
/*     */   
/*     */   public StructurePieceTreasure a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 124 */     ItemStack localItemStack = new ItemStack(Items.BOOK, 1, 0);
/* 125 */     EnchantmentManager.a(paramRandom, localItemStack, 30);
/*     */     
/* 127 */     return new StructurePieceTreasure(localItemStack, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemEnchantedBook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */