/*     */ package net.minecraft.server.v1_8_R3;
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
/*     */ public class BlockFenceGate
/*     */   extends BlockDirectional
/*     */ {
/*  19 */   public static final BlockStateBoolean OPEN = BlockStateBoolean.of("open");
/*  20 */   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
/*  21 */   public static final BlockStateBoolean IN_WALL = BlockStateBoolean.of("in_wall");
/*     */   
/*     */   public BlockFenceGate(BlockWood.EnumLogVariant paramEnumLogVariant) {
/*  24 */     super(Material.WOOD, paramEnumLogVariant.c());
/*     */     
/*  26 */     j(this.blockStateList.getBlockData().set(OPEN, Boolean.valueOf(false)).set(POWERED, Boolean.valueOf(false)).set(IN_WALL, Boolean.valueOf(false)));
/*  27 */     a(CreativeModeTab.d);
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData paramIBlockData, IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  32 */     EnumDirection.EnumAxis localEnumAxis = ((EnumDirection)paramIBlockData.get(FACING)).k();
/*  33 */     if (((localEnumAxis == EnumDirection.EnumAxis.Z) && ((paramIBlockAccess.getType(paramBlockPosition.west()).getBlock() == Blocks.COBBLESTONE_WALL) || (paramIBlockAccess.getType(paramBlockPosition.east()).getBlock() == Blocks.COBBLESTONE_WALL))) || ((localEnumAxis == EnumDirection.EnumAxis.X) && ((paramIBlockAccess.getType(paramBlockPosition.north()).getBlock() == Blocks.COBBLESTONE_WALL) || (paramIBlockAccess.getType(paramBlockPosition.south()).getBlock() == Blocks.COBBLESTONE_WALL))))
/*     */     {
/*     */ 
/*     */ 
/*  37 */       paramIBlockData = paramIBlockData.set(IN_WALL, Boolean.valueOf(true));
/*     */     }
/*     */     
/*  40 */     return paramIBlockData;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  45 */     if (paramWorld.getType(paramBlockPosition.down()).getBlock().getMaterial().isBuildable()) {
/*  46 */       return super.canPlace(paramWorld, paramBlockPosition);
/*     */     }
/*     */     
/*  49 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  55 */     if (((Boolean)paramIBlockData.get(OPEN)).booleanValue()) {
/*  56 */       return null;
/*     */     }
/*     */     
/*  59 */     EnumDirection.EnumAxis localEnumAxis = ((EnumDirection)paramIBlockData.get(FACING)).k();
/*  60 */     if (localEnumAxis == EnumDirection.EnumAxis.Z) {
/*  61 */       return new AxisAlignedBB(paramBlockPosition.getX(), paramBlockPosition.getY(), paramBlockPosition.getZ() + 0.375F, paramBlockPosition.getX() + 1, paramBlockPosition.getY() + 1.5F, paramBlockPosition.getZ() + 0.625F);
/*     */     }
/*     */     
/*  64 */     return new AxisAlignedBB(paramBlockPosition.getX() + 0.375F, paramBlockPosition.getY(), paramBlockPosition.getZ(), paramBlockPosition.getX() + 0.625F, paramBlockPosition.getY() + 1.5F, paramBlockPosition.getZ() + 1);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  69 */     EnumDirection.EnumAxis localEnumAxis = ((EnumDirection)paramIBlockAccess.getType(paramBlockPosition).get(FACING)).k();
/*  70 */     if (localEnumAxis == EnumDirection.EnumAxis.Z) {
/*  71 */       a(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
/*     */     } else {
/*  73 */       a(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  79 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  89 */     return ((Boolean)paramIBlockAccess.getType(paramBlockPosition).get(OPEN)).booleanValue();
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*     */   {
/*  94 */     return getBlockData().set(FACING, paramEntityLiving.getDirection()).set(OPEN, Boolean.valueOf(false)).set(POWERED, Boolean.valueOf(false)).set(IN_WALL, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/*  99 */     if (((Boolean)paramIBlockData.get(OPEN)).booleanValue()) {
/* 100 */       paramIBlockData = paramIBlockData.set(OPEN, Boolean.valueOf(false));
/* 101 */       paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData, 2);
/*     */     }
/*     */     else {
/* 104 */       EnumDirection localEnumDirection = EnumDirection.fromAngle(paramEntityHuman.yaw);
/* 105 */       if (paramIBlockData.get(FACING) == localEnumDirection.opposite()) {
/* 106 */         paramIBlockData = paramIBlockData.set(FACING, localEnumDirection);
/*     */       }
/* 108 */       paramIBlockData = paramIBlockData.set(OPEN, Boolean.valueOf(true));
/* 109 */       paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData, 2);
/*     */     }
/*     */     
/* 112 */     paramWorld.a(paramEntityHuman, ((Boolean)paramIBlockData.get(OPEN)).booleanValue() ? 1003 : 1006, paramBlockPosition, 0);
/* 113 */     return true;
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*     */   {
/* 118 */     if (paramWorld.isClientSide) {
/* 119 */       return;
/*     */     }
/*     */     
/* 122 */     boolean bool = paramWorld.isBlockIndirectlyPowered(paramBlockPosition);
/* 123 */     if ((bool) || (paramBlock.isPowerSource())) {
/* 124 */       if ((bool) && (!((Boolean)paramIBlockData.get(OPEN)).booleanValue()) && (!((Boolean)paramIBlockData.get(POWERED)).booleanValue())) {
/* 125 */         paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData.set(OPEN, Boolean.valueOf(true)).set(POWERED, Boolean.valueOf(true)), 2);
/* 126 */         paramWorld.a(null, 1003, paramBlockPosition, 0);
/* 127 */       } else if ((!bool) && (((Boolean)paramIBlockData.get(OPEN)).booleanValue()) && (((Boolean)paramIBlockData.get(POWERED)).booleanValue())) {
/* 128 */         paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData.set(OPEN, Boolean.valueOf(false)).set(POWERED, Boolean.valueOf(false)), 2);
/* 129 */         paramWorld.a(null, 1006, paramBlockPosition, 0);
/* 130 */       } else if (bool != ((Boolean)paramIBlockData.get(POWERED)).booleanValue()) {
/* 131 */         paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData.set(POWERED, Boolean.valueOf(bool)), 2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 143 */     return getBlockData().set(FACING, EnumDirection.fromType2(paramInt)).set(OPEN, Boolean.valueOf((paramInt & 0x4) != 0)).set(POWERED, Boolean.valueOf((paramInt & 0x8) != 0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 151 */     int i = 0;
/*     */     
/* 153 */     i |= ((EnumDirection)paramIBlockData.get(FACING)).b();
/*     */     
/* 155 */     if (((Boolean)paramIBlockData.get(POWERED)).booleanValue()) {
/* 156 */       i |= 0x8;
/*     */     }
/*     */     
/* 159 */     if (((Boolean)paramIBlockData.get(OPEN)).booleanValue()) {
/* 160 */       i |= 0x4;
/*     */     }
/*     */     
/* 163 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 168 */     return new BlockStateList(this, new IBlockState[] { FACING, OPEN, POWERED, IN_WALL });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockFenceGate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */