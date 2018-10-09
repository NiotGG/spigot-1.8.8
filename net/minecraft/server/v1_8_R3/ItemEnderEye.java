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
/*     */ public class ItemEnderEye
/*     */   extends Item
/*     */ {
/*     */   public ItemEnderEye()
/*     */   {
/*  20 */     a(CreativeModeTab.f);
/*     */   }
/*     */   
/*     */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/*  25 */     IBlockData localIBlockData1 = paramWorld.getType(paramBlockPosition);
/*     */     
/*  27 */     if ((paramEntityHuman.a(paramBlockPosition.shift(paramEnumDirection), paramEnumDirection, paramItemStack)) && (localIBlockData1.getBlock() == Blocks.END_PORTAL_FRAME) && (!((Boolean)localIBlockData1.get(BlockEnderPortalFrame.EYE)).booleanValue())) {
/*  28 */       if (paramWorld.isClientSide) {
/*  29 */         return true;
/*     */       }
/*  31 */       paramWorld.setTypeAndData(paramBlockPosition, localIBlockData1.set(BlockEnderPortalFrame.EYE, Boolean.valueOf(true)), 2);
/*  32 */       paramWorld.updateAdjacentComparators(paramBlockPosition, Blocks.END_PORTAL_FRAME);
/*  33 */       paramItemStack.count -= 1;
/*     */       
/*  35 */       for (int i = 0; i < 16; i++) {
/*  36 */         double d1 = paramBlockPosition.getX() + (5.0F + g.nextFloat() * 6.0F) / 16.0F;
/*  37 */         double d2 = paramBlockPosition.getY() + 0.8125F;
/*  38 */         double d3 = paramBlockPosition.getZ() + (5.0F + g.nextFloat() * 6.0F) / 16.0F;
/*  39 */         double d4 = 0.0D;
/*  40 */         double d5 = 0.0D;
/*  41 */         double d6 = 0.0D;
/*     */         
/*  43 */         paramWorld.addParticle(EnumParticle.SMOKE_NORMAL, d1, d2, d3, d4, d5, d6, new int[0]);
/*     */       }
/*     */       
/*     */ 
/*  47 */       EnumDirection localEnumDirection1 = (EnumDirection)localIBlockData1.get(BlockEnderPortalFrame.FACING);
/*     */       
/*     */ 
/*  50 */       BlockPosition localBlockPosition1 = 0;
/*  51 */       BlockPosition localBlockPosition2 = 0;
/*  52 */       int j = 0;
/*  53 */       int k = 1;
/*  54 */       EnumDirection localEnumDirection2 = localEnumDirection1.e();
/*  55 */       BlockPosition localBlockPosition5; Object localObject1; for (BlockPosition localBlockPosition3 = -2; localBlockPosition3 <= 2; localBlockPosition3++) {
/*  56 */         localBlockPosition5 = paramBlockPosition.shift(localEnumDirection2, localBlockPosition3);
/*  57 */         localObject1 = paramWorld.getType(localBlockPosition5);
/*     */         
/*  59 */         if (((IBlockData)localObject1).getBlock() == Blocks.END_PORTAL_FRAME) {
/*  60 */           if (!((Boolean)((IBlockData)localObject1).get(BlockEnderPortalFrame.EYE)).booleanValue()) {
/*  61 */             k = 0;
/*  62 */             break;
/*     */           }
/*  64 */           localBlockPosition2 = localBlockPosition3;
/*  65 */           if (j == 0) {
/*  66 */             localBlockPosition1 = localBlockPosition3;
/*  67 */             j = 1;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  73 */       if ((k != 0) && (localBlockPosition2 == localBlockPosition1 + 2))
/*     */       {
/*  75 */         BlockPosition localBlockPosition4 = paramBlockPosition.shift(localEnumDirection1, 4);
/*  76 */         Object localObject2; for (localBlockPosition5 = localBlockPosition1; localBlockPosition5 <= localBlockPosition2; localBlockPosition5++) {
/*  77 */           localObject1 = localBlockPosition4.shift(localEnumDirection2, localBlockPosition5);
/*  78 */           localObject2 = paramWorld.getType((BlockPosition)localObject1);
/*     */           
/*  80 */           if ((((IBlockData)localObject2).getBlock() != Blocks.END_PORTAL_FRAME) || (!((Boolean)((IBlockData)localObject2).get(BlockEnderPortalFrame.EYE)).booleanValue())) {
/*  81 */             k = 0;
/*  82 */             break;
/*     */           }
/*     */         }
/*     */         
/*     */         int m;
/*  87 */         for (BlockPosition localBlockPosition6 = localBlockPosition1 - 1; localBlockPosition6 <= localBlockPosition2 + 1; localBlockPosition6 += 4) {
/*  88 */           localBlockPosition4 = paramBlockPosition.shift(localEnumDirection2, localBlockPosition6);
/*  89 */           for (m = 1; m <= 3; m++) {
/*  90 */             localObject2 = localBlockPosition4.shift(localEnumDirection1, m);
/*  91 */             IBlockData localIBlockData2 = paramWorld.getType((BlockPosition)localObject2);
/*     */             
/*  93 */             if ((localIBlockData2.getBlock() != Blocks.END_PORTAL_FRAME) || (!((Boolean)localIBlockData2.get(BlockEnderPortalFrame.EYE)).booleanValue())) {
/*  94 */               k = 0;
/*  95 */               break;
/*     */             }
/*     */           }
/*     */         }
/*  99 */         if (k != 0)
/*     */         {
/* 101 */           for (localBlockPosition6 = localBlockPosition1; localBlockPosition6 <= localBlockPosition2; localBlockPosition6++) {
/* 102 */             localBlockPosition4 = paramBlockPosition.shift(localEnumDirection2, localBlockPosition6);
/* 103 */             for (m = 1; m <= 3; m++) {
/* 104 */               localObject2 = localBlockPosition4.shift(localEnumDirection1, m);
/*     */               
/* 106 */               paramWorld.setTypeAndData((BlockPosition)localObject2, Blocks.END_PORTAL.getBlockData(), 2);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 112 */       return true;
/*     */     }
/* 114 */     return false;
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*     */   {
/* 119 */     MovingObjectPosition localMovingObjectPosition = a(paramWorld, paramEntityHuman, false);
/* 120 */     if ((localMovingObjectPosition != null) && (localMovingObjectPosition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) && 
/* 121 */       (paramWorld.getType(localMovingObjectPosition.a()).getBlock() == Blocks.END_PORTAL_FRAME)) {
/* 122 */       return paramItemStack;
/*     */     }
/*     */     
/*     */ 
/* 126 */     if (!paramWorld.isClientSide) {
/* 127 */       BlockPosition localBlockPosition = paramWorld.a("Stronghold", new BlockPosition(paramEntityHuman));
/* 128 */       if (localBlockPosition != null) {
/* 129 */         EntityEnderSignal localEntityEnderSignal = new EntityEnderSignal(paramWorld, paramEntityHuman.locX, paramEntityHuman.locY, paramEntityHuman.locZ);
/* 130 */         localEntityEnderSignal.a(localBlockPosition);
/* 131 */         paramWorld.addEntity(localEntityEnderSignal);
/*     */         
/* 133 */         paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
/* 134 */         paramWorld.a(null, 1002, new BlockPosition(paramEntityHuman), 0);
/* 135 */         if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 136 */           paramItemStack.count -= 1;
/*     */         }
/* 138 */         paramEntityHuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*     */       }
/*     */     }
/* 141 */     return paramItemStack;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemEnderEye.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */