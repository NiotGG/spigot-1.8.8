/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public class BlockCauldron
/*     */   extends Block
/*     */ {
/*  29 */   public static final BlockStateInteger LEVEL = BlockStateInteger.of("level", 0, 3);
/*     */   
/*     */ 
/*     */ 
/*     */   public BlockCauldron()
/*     */   {
/*  35 */     super(Material.ORE, MaterialMapColor.m);
/*  36 */     j(this.blockStateList.getBlockData().set(LEVEL, Integer.valueOf(0)));
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, AxisAlignedBB paramAxisAlignedBB, List<AxisAlignedBB> paramList, Entity paramEntity)
/*     */   {
/*  41 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
/*  42 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*  43 */     float f = 0.125F;
/*  44 */     a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*  45 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*  46 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  47 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*  48 */     a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  49 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*  50 */     a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*  51 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */     
/*  53 */     j();
/*     */   }
/*     */   
/*     */   public void j()
/*     */   {
/*  58 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  63 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  68 */     return false;
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Entity paramEntity)
/*     */   {
/*  73 */     int i = ((Integer)paramIBlockData.get(LEVEL)).intValue();
/*  74 */     float f = paramBlockPosition.getY() + (6.0F + 3 * i) / 16.0F;
/*     */     
/*  76 */     if ((!paramWorld.isClientSide) && (paramEntity.isBurning()) && (i > 0) && (paramEntity.getBoundingBox().b <= f)) {
/*  77 */       paramEntity.extinguish();
/*     */       
/*  79 */       a(paramWorld, paramBlockPosition, paramIBlockData, i - 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/*  85 */     if (paramWorld.isClientSide) {
/*  86 */       return true;
/*     */     }
/*     */     
/*  89 */     ItemStack localItemStack = paramEntityHuman.inventory.getItemInHand();
/*  90 */     if (localItemStack == null) {
/*  91 */       return true;
/*     */     }
/*     */     
/*  94 */     int i = ((Integer)paramIBlockData.get(LEVEL)).intValue();
/*  95 */     Item localItem = localItemStack.getItem();
/*  96 */     if (localItem == Items.WATER_BUCKET) {
/*  97 */       if (i < 3) {
/*  98 */         if (!paramEntityHuman.abilities.canInstantlyBuild) {
/*  99 */           paramEntityHuman.inventory.setItem(paramEntityHuman.inventory.itemInHandIndex, new ItemStack(Items.BUCKET));
/*     */         }
/* 101 */         paramEntityHuman.b(StatisticList.I);
/*     */         
/* 103 */         a(paramWorld, paramBlockPosition, paramIBlockData, 3);
/*     */       }
/* 105 */       return true;
/*     */     }
/*     */     Object localObject;
/* 108 */     if (localItem == Items.GLASS_BOTTLE) {
/* 109 */       if (i > 0) {
/* 110 */         if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 111 */           localObject = new ItemStack(Items.POTION, 1, 0);
/* 112 */           if (!paramEntityHuman.inventory.pickup((ItemStack)localObject)) {
/* 113 */             paramWorld.addEntity(new EntityItem(paramWorld, paramBlockPosition.getX() + 0.5D, paramBlockPosition.getY() + 1.5D, paramBlockPosition.getZ() + 0.5D, (ItemStack)localObject));
/* 114 */           } else if ((paramEntityHuman instanceof EntityPlayer)) {
/* 115 */             ((EntityPlayer)paramEntityHuman).updateInventory(paramEntityHuman.defaultContainer);
/*     */           }
/* 117 */           paramEntityHuman.b(StatisticList.J);
/*     */           
/* 119 */           localItemStack.count -= 1;
/* 120 */           if (localItemStack.count <= 0) {
/* 121 */             paramEntityHuman.inventory.setItem(paramEntityHuman.inventory.itemInHandIndex, null);
/*     */           }
/*     */         }
/*     */         
/* 125 */         a(paramWorld, paramBlockPosition, paramIBlockData, i - 1);
/*     */       }
/* 127 */       return true;
/*     */     }
/*     */     
/* 130 */     if ((i > 0) && ((localItem instanceof ItemArmor))) {
/* 131 */       localObject = (ItemArmor)localItem;
/* 132 */       if ((((ItemArmor)localObject).x_() == ItemArmor.EnumArmorMaterial.LEATHER) && (((ItemArmor)localObject).d_(localItemStack))) {
/* 133 */         ((ItemArmor)localObject).c(localItemStack);
/* 134 */         a(paramWorld, paramBlockPosition, paramIBlockData, i - 1);
/* 135 */         paramEntityHuman.b(StatisticList.K);
/* 136 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 140 */     if ((i > 0) && ((localItem instanceof ItemBanner)) && 
/* 141 */       (TileEntityBanner.c(localItemStack) > 0)) {
/* 142 */       localObject = localItemStack.cloneItemStack();
/* 143 */       ((ItemStack)localObject).count = 1;
/* 144 */       TileEntityBanner.e((ItemStack)localObject);
/*     */       
/* 146 */       if ((localItemStack.count > 1) || (paramEntityHuman.abilities.canInstantlyBuild))
/*     */       {
/* 148 */         if (!paramEntityHuman.inventory.pickup((ItemStack)localObject)) {
/* 149 */           paramWorld.addEntity(new EntityItem(paramWorld, paramBlockPosition.getX() + 0.5D, paramBlockPosition.getY() + 1.5D, paramBlockPosition.getZ() + 0.5D, (ItemStack)localObject));
/* 150 */         } else if ((paramEntityHuman instanceof EntityPlayer)) {
/* 151 */           ((EntityPlayer)paramEntityHuman).updateInventory(paramEntityHuman.defaultContainer);
/*     */         }
/* 153 */         paramEntityHuman.b(StatisticList.L);
/* 154 */         if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 155 */           localItemStack.count -= 1;
/*     */         }
/*     */       }
/*     */       else {
/* 159 */         paramEntityHuman.inventory.setItem(paramEntityHuman.inventory.itemInHandIndex, (ItemStack)localObject);
/*     */       }
/*     */       
/* 162 */       if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 163 */         a(paramWorld, paramBlockPosition, paramIBlockData, i - 1);
/*     */       }
/* 165 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 169 */     return false;
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, int paramInt) {
/* 173 */     paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData.set(LEVEL, Integer.valueOf(MathHelper.clamp(paramInt, 0, 3))), 2);
/* 174 */     paramWorld.updateAdjacentComparators(paramBlockPosition, this);
/*     */   }
/*     */   
/*     */   public void k(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/* 179 */     if (paramWorld.random.nextInt(20) != 1) {
/* 180 */       return;
/*     */     }
/*     */     
/* 183 */     IBlockData localIBlockData = paramWorld.getType(paramBlockPosition);
/* 184 */     if (((Integer)localIBlockData.get(LEVEL)).intValue() < 3) {
/* 185 */       paramWorld.setTypeAndData(paramBlockPosition, localIBlockData.a(LEVEL), 2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/* 192 */     return Items.CAULDRON;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isComplexRedstone()
/*     */   {
/* 202 */     return true;
/*     */   }
/*     */   
/*     */   public int l(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/* 207 */     return ((Integer)paramWorld.getType(paramBlockPosition).get(LEVEL)).intValue();
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 212 */     return getBlockData().set(LEVEL, Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 218 */     return ((Integer)paramIBlockData.get(LEVEL)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 223 */     return new BlockStateList(this, new IBlockState[] { LEVEL });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockCauldron.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */