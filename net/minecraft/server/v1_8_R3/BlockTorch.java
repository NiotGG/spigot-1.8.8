/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
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
/*     */ public class BlockTorch
/*     */   extends Block
/*     */ {
/*  23 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", new Predicate()
/*     */   {
/*     */     public boolean a(EnumDirection paramAnonymousEnumDirection) {
/*  26 */       return paramAnonymousEnumDirection != EnumDirection.DOWN;
/*     */     }
/*  23 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BlockTorch()
/*     */   {
/*  31 */     super(Material.ORIENTABLE);
/*  32 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.UP));
/*  33 */     a(true);
/*  34 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  40 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  45 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  50 */     return false;
/*     */   }
/*     */   
/*     */   private boolean e(World paramWorld, BlockPosition paramBlockPosition) {
/*  54 */     if (World.a(paramWorld, paramBlockPosition)) {
/*  55 */       return true;
/*     */     }
/*  57 */     Block localBlock = paramWorld.getType(paramBlockPosition).getBlock();
/*  58 */     return ((localBlock instanceof BlockFence)) || (localBlock == Blocks.GLASS) || (localBlock == Blocks.COBBLESTONE_WALL) || (localBlock == Blocks.STAINED_GLASS);
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  63 */     for (EnumDirection localEnumDirection : FACING.c()) {
/*  64 */       if (a(paramWorld, paramBlockPosition, localEnumDirection)) {
/*  65 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  69 */     return false;
/*     */   }
/*     */   
/*     */   private boolean a(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection) {
/*  73 */     BlockPosition localBlockPosition = paramBlockPosition.shift(paramEnumDirection.opposite());
/*     */     
/*  75 */     boolean bool = paramEnumDirection.k().c();
/*  76 */     return ((bool) && (paramWorld.d(localBlockPosition, true))) || ((paramEnumDirection.equals(EnumDirection.UP)) && (e(paramWorld, localBlockPosition)));
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*     */   {
/*  81 */     if (a(paramWorld, paramBlockPosition, paramEnumDirection)) {
/*  82 */       return getBlockData().set(FACING, paramEnumDirection);
/*     */     }
/*     */     
/*  85 */     for (EnumDirection localEnumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
/*  86 */       if (paramWorld.d(paramBlockPosition.shift(localEnumDirection.opposite()), true)) {
/*  87 */         return getBlockData().set(FACING, localEnumDirection);
/*     */       }
/*     */     }
/*     */     
/*  91 */     return getBlockData();
/*     */   }
/*     */   
/*     */   public void onPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  96 */     f(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*     */   {
/* 101 */     e(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   protected boolean e(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData) {
/* 105 */     if (!f(paramWorld, paramBlockPosition, paramIBlockData)) {
/* 106 */       return true;
/*     */     }
/*     */     
/* 109 */     EnumDirection localEnumDirection1 = (EnumDirection)paramIBlockData.get(FACING);
/* 110 */     EnumDirection.EnumAxis localEnumAxis = localEnumDirection1.k();
/* 111 */     EnumDirection localEnumDirection2 = localEnumDirection1.opposite();
/*     */     
/* 113 */     int i = 0;
/* 114 */     if ((localEnumAxis.c()) && (!paramWorld.d(paramBlockPosition.shift(localEnumDirection2), true))) {
/* 115 */       i = 1;
/* 116 */     } else if ((localEnumAxis.b()) && (!e(paramWorld, paramBlockPosition.shift(localEnumDirection2)))) {
/* 117 */       i = 1;
/*     */     }
/*     */     
/* 120 */     if (i != 0) {
/* 121 */       b(paramWorld, paramBlockPosition, paramIBlockData, 0);
/* 122 */       paramWorld.setAir(paramBlockPosition);
/* 123 */       return true;
/*     */     }
/*     */     
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean f(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData) {
/* 130 */     if ((paramIBlockData.getBlock() == this) && 
/* 131 */       (a(paramWorld, paramBlockPosition, (EnumDirection)paramIBlockData.get(FACING)))) {
/* 132 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 136 */     if (paramWorld.getType(paramBlockPosition).getBlock() == this) {
/* 137 */       b(paramWorld, paramBlockPosition, paramIBlockData, 0);
/* 138 */       paramWorld.setAir(paramBlockPosition);
/*     */     }
/*     */     
/* 141 */     return false;
/*     */   }
/*     */   
/*     */   public MovingObjectPosition a(World paramWorld, BlockPosition paramBlockPosition, Vec3D paramVec3D1, Vec3D paramVec3D2)
/*     */   {
/* 146 */     EnumDirection localEnumDirection = (EnumDirection)paramWorld.getType(paramBlockPosition).get(FACING);
/*     */     
/* 148 */     float f = 0.15F;
/* 149 */     if (localEnumDirection == EnumDirection.EAST) {
/* 150 */       a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/* 151 */     } else if (localEnumDirection == EnumDirection.WEST) {
/* 152 */       a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/* 153 */     } else if (localEnumDirection == EnumDirection.SOUTH) {
/* 154 */       a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/* 155 */     } else if (localEnumDirection == EnumDirection.NORTH) {
/* 156 */       a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */     } else {
/* 158 */       f = 0.1F;
/* 159 */       a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
/*     */     }
/*     */     
/* 162 */     return super.a(paramWorld, paramBlockPosition, paramVec3D1, paramVec3D2);
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
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 191 */     IBlockData localIBlockData = getBlockData();
/* 192 */     switch (paramInt) {
/*     */     case 1: 
/* 194 */       localIBlockData = localIBlockData.set(FACING, EnumDirection.EAST);
/* 195 */       break;
/*     */     case 2: 
/* 197 */       localIBlockData = localIBlockData.set(FACING, EnumDirection.WEST);
/* 198 */       break;
/*     */     case 3: 
/* 200 */       localIBlockData = localIBlockData.set(FACING, EnumDirection.SOUTH);
/* 201 */       break;
/*     */     case 4: 
/* 203 */       localIBlockData = localIBlockData.set(FACING, EnumDirection.NORTH);
/* 204 */       break;
/*     */     case 5: 
/*     */     default: 
/* 207 */       localIBlockData = localIBlockData.set(FACING, EnumDirection.UP);
/*     */     }
/*     */     
/*     */     
/* 211 */     return localIBlockData;
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 216 */     int i = 0;
/*     */     
/* 218 */     switch (2.a[((EnumDirection)paramIBlockData.get(FACING)).ordinal()]) {
/*     */     case 1: 
/* 220 */       i |= 0x1;
/* 221 */       break;
/*     */     case 2: 
/* 223 */       i |= 0x2;
/* 224 */       break;
/*     */     case 3: 
/* 226 */       i |= 0x3;
/* 227 */       break;
/*     */     case 4: 
/* 229 */       i |= 0x4;
/* 230 */       break;
/*     */     case 5: 
/*     */     case 6: 
/*     */     default: 
/* 234 */       i |= 0x5;
/*     */     }
/*     */     
/*     */     
/* 238 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 243 */     return new BlockStateList(this, new IBlockState[] { FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockTorch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */