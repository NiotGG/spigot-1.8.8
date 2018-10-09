/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ public class MerchantRecipeList
/*     */   extends ArrayList<MerchantRecipe>
/*     */ {
/*     */   public MerchantRecipeList() {}
/*     */   
/*     */   public MerchantRecipeList(NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  23 */     a(paramNBTTagCompound);
/*     */   }
/*     */   
/*     */   public MerchantRecipe a(ItemStack paramItemStack1, ItemStack paramItemStack2, int paramInt)
/*     */   {
/*  28 */     if ((paramInt > 0) && (paramInt < size()))
/*     */     {
/*  30 */       MerchantRecipe localMerchantRecipe1 = (MerchantRecipe)get(paramInt);
/*  31 */       if ((a(paramItemStack1, localMerchantRecipe1.getBuyItem1())) && (((paramItemStack2 == null) && (!localMerchantRecipe1.hasSecondItem())) || ((localMerchantRecipe1.hasSecondItem()) && (a(paramItemStack2, localMerchantRecipe1.getBuyItem2())) && 
/*  32 */         (paramItemStack1.count >= localMerchantRecipe1.getBuyItem1().count) && ((!localMerchantRecipe1.hasSecondItem()) || (paramItemStack2.count >= localMerchantRecipe1.getBuyItem2().count))))) {
/*  33 */         return localMerchantRecipe1;
/*     */       }
/*     */       
/*  36 */       return null;
/*     */     }
/*  38 */     for (int i = 0; i < size(); i++) {
/*  39 */       MerchantRecipe localMerchantRecipe2 = (MerchantRecipe)get(i);
/*  40 */       if ((a(paramItemStack1, localMerchantRecipe2.getBuyItem1())) && (paramItemStack1.count >= localMerchantRecipe2.getBuyItem1().count) && (((!localMerchantRecipe2.hasSecondItem()) && (paramItemStack2 == null)) || ((localMerchantRecipe2.hasSecondItem()) && (a(paramItemStack2, localMerchantRecipe2.getBuyItem2())) && (paramItemStack2.count >= localMerchantRecipe2.getBuyItem2().count)))) {
/*  41 */         return localMerchantRecipe2;
/*     */       }
/*     */     }
/*  44 */     return null;
/*     */   }
/*     */   
/*     */   private boolean a(ItemStack paramItemStack1, ItemStack paramItemStack2) {
/*  48 */     return (ItemStack.c(paramItemStack1, paramItemStack2)) && ((!paramItemStack2.hasTag()) || ((paramItemStack1.hasTag()) && (GameProfileSerializer.a(paramItemStack2.getTag(), paramItemStack1.getTag(), false))));
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
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*     */   {
/*  77 */     paramPacketDataSerializer.writeByte((byte)(size() & 0xFF));
/*  78 */     for (int i = 0; i < size(); i++) {
/*  79 */       MerchantRecipe localMerchantRecipe = (MerchantRecipe)get(i);
/*  80 */       paramPacketDataSerializer.a(localMerchantRecipe.getBuyItem1());
/*  81 */       paramPacketDataSerializer.a(localMerchantRecipe.getBuyItem3());
/*     */       
/*  83 */       ItemStack localItemStack = localMerchantRecipe.getBuyItem2();
/*  84 */       paramPacketDataSerializer.writeBoolean(localItemStack != null);
/*  85 */       if (localItemStack != null) {
/*  86 */         paramPacketDataSerializer.a(localItemStack);
/*     */       }
/*  88 */       paramPacketDataSerializer.writeBoolean(localMerchantRecipe.h());
/*  89 */       paramPacketDataSerializer.writeInt(localMerchantRecipe.e());
/*  90 */       paramPacketDataSerializer.writeInt(localMerchantRecipe.f());
/*     */     }
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
/*     */   public void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 120 */     NBTTagList localNBTTagList = paramNBTTagCompound.getList("Recipes", 10);
/*     */     
/* 122 */     for (int i = 0; i < localNBTTagList.size(); i++) {
/* 123 */       NBTTagCompound localNBTTagCompound = localNBTTagList.get(i);
/* 124 */       add(new MerchantRecipe(localNBTTagCompound));
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTTagCompound a() {
/* 129 */     NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/*     */     
/* 131 */     NBTTagList localNBTTagList = new NBTTagList();
/* 132 */     for (int i = 0; i < size(); i++) {
/* 133 */       MerchantRecipe localMerchantRecipe = (MerchantRecipe)get(i);
/* 134 */       localNBTTagList.add(localMerchantRecipe.k());
/*     */     }
/* 136 */     localNBTTagCompound.set("Recipes", localNBTTagList);
/* 137 */     return localNBTTagCompound;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MerchantRecipeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */