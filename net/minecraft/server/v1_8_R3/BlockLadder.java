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
/*     */ public class BlockLadder
/*     */   extends Block
/*     */ {
/*  19 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*     */   
/*     */   protected BlockLadder() {
/*  22 */     super(Material.ORIENTABLE);
/*  23 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
/*  24 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  30 */     updateShape(paramWorld, paramBlockPosition);
/*  31 */     return super.a(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  42 */     IBlockData localIBlockData = paramIBlockAccess.getType(paramBlockPosition);
/*  43 */     if (localIBlockData.getBlock() != this) {
/*  44 */       return;
/*     */     }
/*     */     
/*  47 */     float f = 0.125F;
/*  48 */     switch (1.a[((EnumDirection)localIBlockData.get(FACING)).ordinal()]) {
/*     */     case 1: 
/*  50 */       a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*  51 */       break;
/*     */     case 2: 
/*  53 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  54 */       break;
/*     */     case 3: 
/*  56 */       a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  57 */       break;
/*     */     case 4: 
/*     */     default: 
/*  60 */       a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  67 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  77 */     if (paramWorld.getType(paramBlockPosition.west()).getBlock().isOccluding())
/*  78 */       return true;
/*  79 */     if (paramWorld.getType(paramBlockPosition.east()).getBlock().isOccluding())
/*  80 */       return true;
/*  81 */     if (paramWorld.getType(paramBlockPosition.north()).getBlock().isOccluding())
/*  82 */       return true;
/*  83 */     if (paramWorld.getType(paramBlockPosition.south()).getBlock().isOccluding()) {
/*  84 */       return true;
/*     */     }
/*  86 */     return false;
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*     */   {
/*  91 */     if ((paramEnumDirection.k().c()) && (a(paramWorld, paramBlockPosition, paramEnumDirection))) {
/*  92 */       return getBlockData().set(FACING, paramEnumDirection);
/*     */     }
/*     */     
/*  95 */     for (EnumDirection localEnumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
/*  96 */       if (a(paramWorld, paramBlockPosition, localEnumDirection)) {
/*  97 */         return getBlockData().set(FACING, localEnumDirection);
/*     */       }
/*     */     }
/*     */     
/* 101 */     return getBlockData();
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*     */   {
/* 106 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/* 107 */     if (!a(paramWorld, paramBlockPosition, localEnumDirection)) {
/* 108 */       b(paramWorld, paramBlockPosition, paramIBlockData, 0);
/* 109 */       paramWorld.setAir(paramBlockPosition);
/*     */     }
/*     */     
/* 112 */     super.doPhysics(paramWorld, paramBlockPosition, paramIBlockData, paramBlock);
/*     */   }
/*     */   
/*     */   protected boolean a(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection) {
/* 116 */     return paramWorld.getType(paramBlockPosition.shift(paramEnumDirection.opposite())).getBlock().isOccluding();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 126 */     EnumDirection localEnumDirection = EnumDirection.fromType1(paramInt);
/* 127 */     if (localEnumDirection.k() == EnumDirection.EnumAxis.Y) {
/* 128 */       localEnumDirection = EnumDirection.NORTH;
/*     */     }
/* 130 */     return getBlockData().set(FACING, localEnumDirection);
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 136 */     return ((EnumDirection)paramIBlockData.get(FACING)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 141 */     return new BlockStateList(this, new IBlockState[] { FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockLadder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */