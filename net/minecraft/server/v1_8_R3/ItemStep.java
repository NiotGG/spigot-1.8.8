/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemStep
/*     */   extends ItemBlock
/*     */ {
/*     */   private final BlockStepAbstract b;
/*     */   
/*     */ 
/*     */   private final BlockStepAbstract c;
/*     */   
/*     */ 
/*     */ 
/*     */   public ItemStep(Block paramBlock, BlockStepAbstract paramBlockStepAbstract1, BlockStepAbstract paramBlockStepAbstract2)
/*     */   {
/*  17 */     super(paramBlock);
/*  18 */     this.b = paramBlockStepAbstract1;
/*  19 */     this.c = paramBlockStepAbstract2;
/*     */     
/*  21 */     setMaxDurability(0);
/*  22 */     a(true);
/*     */   }
/*     */   
/*     */   public int filterData(int paramInt)
/*     */   {
/*  27 */     return paramInt;
/*     */   }
/*     */   
/*     */   public String e_(ItemStack paramItemStack)
/*     */   {
/*  32 */     return this.b.b(paramItemStack.getData());
/*     */   }
/*     */   
/*     */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/*  37 */     if (paramItemStack.count == 0) {
/*  38 */       return false;
/*     */     }
/*  40 */     if (!paramEntityHuman.a(paramBlockPosition.shift(paramEnumDirection), paramEnumDirection, paramItemStack)) {
/*  41 */       return false;
/*     */     }
/*     */     
/*  44 */     Object localObject = this.b.a(paramItemStack);
/*  45 */     IBlockData localIBlockData1 = paramWorld.getType(paramBlockPosition);
/*     */     
/*  47 */     if (localIBlockData1.getBlock() == this.b) {
/*  48 */       IBlockState localIBlockState = this.b.n();
/*  49 */       Comparable localComparable = localIBlockData1.get(localIBlockState);
/*  50 */       BlockStepAbstract.EnumSlabHalf localEnumSlabHalf = (BlockStepAbstract.EnumSlabHalf)localIBlockData1.get(BlockStepAbstract.HALF);
/*     */       
/*  52 */       if (((paramEnumDirection == EnumDirection.UP) && (localEnumSlabHalf == BlockStepAbstract.EnumSlabHalf.BOTTOM)) || ((paramEnumDirection == EnumDirection.DOWN) && (localEnumSlabHalf == BlockStepAbstract.EnumSlabHalf.TOP) && (localComparable == localObject))) {
/*  53 */         IBlockData localIBlockData2 = this.c.getBlockData().set(localIBlockState, localComparable);
/*     */         
/*  55 */         if ((paramWorld.b(this.c.a(paramWorld, paramBlockPosition, localIBlockData2))) && (paramWorld.setTypeAndData(paramBlockPosition, localIBlockData2, 3))) {
/*  56 */           paramWorld.makeSound(paramBlockPosition.getX() + 0.5F, paramBlockPosition.getY() + 0.5F, paramBlockPosition.getZ() + 0.5F, this.c.stepSound.getPlaceSound(), (this.c.stepSound.getVolume1() + 1.0F) / 2.0F, this.c.stepSound.getVolume2() * 0.8F);
/*  57 */           paramItemStack.count -= 1;
/*     */         }
/*  59 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  63 */     if (a(paramItemStack, paramWorld, paramBlockPosition.shift(paramEnumDirection), localObject)) {
/*  64 */       return true;
/*     */     }
/*     */     
/*  67 */     return super.interactWith(paramItemStack, paramEntityHuman, paramWorld, paramBlockPosition, paramEnumDirection, paramFloat1, paramFloat2, paramFloat3);
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
/*     */   private boolean a(ItemStack paramItemStack, World paramWorld, BlockPosition paramBlockPosition, Object paramObject)
/*     */   {
/*  97 */     IBlockData localIBlockData1 = paramWorld.getType(paramBlockPosition);
/*  98 */     if (localIBlockData1.getBlock() == this.b) {
/*  99 */       Comparable localComparable = localIBlockData1.get(this.b.n());
/*     */       
/* 101 */       if (localComparable == paramObject) {
/* 102 */         IBlockData localIBlockData2 = this.c.getBlockData().set(this.b.n(), localComparable);
/*     */         
/* 104 */         if ((paramWorld.b(this.c.a(paramWorld, paramBlockPosition, localIBlockData2))) && (paramWorld.setTypeAndData(paramBlockPosition, localIBlockData2, 3))) {
/* 105 */           paramWorld.makeSound(paramBlockPosition.getX() + 0.5F, paramBlockPosition.getY() + 0.5F, paramBlockPosition.getZ() + 0.5F, this.c.stepSound.getPlaceSound(), (this.c.stepSound.getVolume1() + 1.0F) / 2.0F, this.c.stepSound.getVolume2() * 0.8F);
/* 106 */           paramItemStack.count -= 1;
/*     */         }
/*     */         
/* 109 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 113 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemStep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */