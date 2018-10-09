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
/*     */ 
/*     */ 
/*     */ public class BlockPistonMoving
/*     */   extends BlockContainer
/*     */ {
/*  26 */   public static final BlockStateDirection FACING = BlockPistonExtension.FACING;
/*  27 */   public static final BlockStateEnum<BlockPistonExtension.EnumPistonType> TYPE = BlockPistonExtension.TYPE;
/*     */   
/*     */   public BlockPistonMoving() {
/*  30 */     super(Material.PISTON);
/*  31 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(TYPE, BlockPistonExtension.EnumPistonType.DEFAULT));
/*  32 */     c(-1.0F);
/*     */   }
/*     */   
/*     */   public TileEntity a(World paramWorld, int paramInt)
/*     */   {
/*  37 */     return null;
/*     */   }
/*     */   
/*     */   public static TileEntity a(IBlockData paramIBlockData, EnumDirection paramEnumDirection, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  42 */     return new TileEntityPiston(paramIBlockData, paramEnumDirection, paramBoolean1, paramBoolean2);
/*     */   }
/*     */   
/*     */   public void remove(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  47 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/*  48 */     if ((localTileEntity instanceof TileEntityPiston)) {
/*  49 */       ((TileEntityPiston)localTileEntity).h();
/*     */     } else {
/*  51 */       super.remove(paramWorld, paramBlockPosition, paramIBlockData);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  57 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection)
/*     */   {
/*  62 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void postBreak(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  68 */     BlockPosition localBlockPosition = paramBlockPosition.shift(((EnumDirection)paramIBlockData.get(FACING)).opposite());
/*  69 */     IBlockData localIBlockData = paramWorld.getType(localBlockPosition);
/*  70 */     if (((localIBlockData.getBlock() instanceof BlockPiston)) && (((Boolean)localIBlockData.get(BlockPiston.EXTENDED)).booleanValue())) {
/*  71 */       paramWorld.setAir(localBlockPosition);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  77 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  82 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/*  88 */     if ((!paramWorld.isClientSide) && (paramWorld.getTileEntity(paramBlockPosition) == null))
/*     */     {
/*  90 */       paramWorld.setAir(paramBlockPosition);
/*  91 */       return true;
/*     */     }
/*  93 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public void dropNaturally(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, float paramFloat, int paramInt)
/*     */   {
/* 104 */     if (paramWorld.isClientSide) {
/* 105 */       return;
/*     */     }
/*     */     
/* 108 */     TileEntityPiston localTileEntityPiston = e(paramWorld, paramBlockPosition);
/* 109 */     if (localTileEntityPiston == null) {
/* 110 */       return;
/*     */     }
/*     */     
/* 113 */     IBlockData localIBlockData = localTileEntityPiston.b();
/* 114 */     localIBlockData.getBlock().b(paramWorld, paramBlockPosition, localIBlockData, 0);
/*     */   }
/*     */   
/*     */ 
/*     */   public MovingObjectPosition a(World paramWorld, BlockPosition paramBlockPosition, Vec3D paramVec3D1, Vec3D paramVec3D2)
/*     */   {
/* 120 */     return null;
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*     */   {
/* 125 */     if (!paramWorld.isClientSide) {
/* 126 */       paramWorld.getTileEntity(paramBlockPosition);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 133 */     TileEntityPiston localTileEntityPiston = e(paramWorld, paramBlockPosition);
/* 134 */     if (localTileEntityPiston == null) {
/* 135 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 139 */     float f = localTileEntityPiston.a(0.0F);
/* 140 */     if (localTileEntityPiston.d()) {
/* 141 */       f = 1.0F - f;
/*     */     }
/* 143 */     return a(paramWorld, paramBlockPosition, localTileEntityPiston.b(), f, localTileEntityPiston.e());
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/* 148 */     TileEntityPiston localTileEntityPiston = e(paramIBlockAccess, paramBlockPosition);
/* 149 */     if (localTileEntityPiston != null) {
/* 150 */       IBlockData localIBlockData = localTileEntityPiston.b();
/* 151 */       Block localBlock = localIBlockData.getBlock();
/* 152 */       if ((localBlock == this) || (localBlock.getMaterial() == Material.AIR)) {
/* 153 */         return;
/*     */       }
/*     */       
/* 156 */       float f = localTileEntityPiston.a(0.0F);
/* 157 */       if (localTileEntityPiston.d()) {
/* 158 */         f = 1.0F - f;
/*     */       }
/* 160 */       localBlock.updateShape(paramIBlockAccess, paramBlockPosition);
/* 161 */       if ((localBlock == Blocks.PISTON) || (localBlock == Blocks.STICKY_PISTON)) {
/* 162 */         f = 0.0F;
/*     */       }
/* 164 */       EnumDirection localEnumDirection = localTileEntityPiston.e();
/* 165 */       this.minX = (localBlock.B() - localEnumDirection.getAdjacentX() * f);
/* 166 */       this.minY = (localBlock.D() - localEnumDirection.getAdjacentY() * f);
/* 167 */       this.minZ = (localBlock.F() - localEnumDirection.getAdjacentZ() * f);
/* 168 */       this.maxX = (localBlock.C() - localEnumDirection.getAdjacentX() * f);
/* 169 */       this.maxY = (localBlock.E() - localEnumDirection.getAdjacentY() * f);
/* 170 */       this.maxZ = (localBlock.G() - localEnumDirection.getAdjacentZ() * f);
/*     */     }
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, float paramFloat, EnumDirection paramEnumDirection) {
/* 175 */     if ((paramIBlockData.getBlock() == this) || (paramIBlockData.getBlock().getMaterial() == Material.AIR)) {
/* 176 */       return null;
/*     */     }
/* 178 */     AxisAlignedBB localAxisAlignedBB = paramIBlockData.getBlock().a(paramWorld, paramBlockPosition, paramIBlockData);
/*     */     
/* 180 */     if (localAxisAlignedBB == null) {
/* 181 */       return null;
/*     */     }
/*     */     
/* 184 */     double d1 = localAxisAlignedBB.a;
/* 185 */     double d2 = localAxisAlignedBB.b;
/* 186 */     double d3 = localAxisAlignedBB.c;
/* 187 */     double d4 = localAxisAlignedBB.d;
/* 188 */     double d5 = localAxisAlignedBB.e;
/* 189 */     double d6 = localAxisAlignedBB.f;
/*     */     
/*     */ 
/* 192 */     if (paramEnumDirection.getAdjacentX() < 0) {
/* 193 */       d1 -= paramEnumDirection.getAdjacentX() * paramFloat;
/*     */     } else {
/* 195 */       d4 -= paramEnumDirection.getAdjacentX() * paramFloat;
/*     */     }
/* 197 */     if (paramEnumDirection.getAdjacentY() < 0) {
/* 198 */       d2 -= paramEnumDirection.getAdjacentY() * paramFloat;
/*     */     } else {
/* 200 */       d5 -= paramEnumDirection.getAdjacentY() * paramFloat;
/*     */     }
/* 202 */     if (paramEnumDirection.getAdjacentZ() < 0) {
/* 203 */       d3 -= paramEnumDirection.getAdjacentZ() * paramFloat;
/*     */     } else {
/* 205 */       d6 -= paramEnumDirection.getAdjacentZ() * paramFloat;
/*     */     }
/* 207 */     return new AxisAlignedBB(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */   
/*     */   private TileEntityPiston e(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition) {
/* 211 */     TileEntity localTileEntity = paramIBlockAccess.getTileEntity(paramBlockPosition);
/* 212 */     if ((localTileEntity instanceof TileEntityPiston)) {
/* 213 */       return (TileEntityPiston)localTileEntity;
/*     */     }
/* 215 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 225 */     return getBlockData().set(FACING, BlockPistonExtension.b(paramInt)).set(TYPE, (paramInt & 0x8) > 0 ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 232 */     int i = 0;
/*     */     
/* 234 */     i |= ((EnumDirection)paramIBlockData.get(FACING)).a();
/*     */     
/* 236 */     if (paramIBlockData.get(TYPE) == BlockPistonExtension.EnumPistonType.STICKY) {
/* 237 */       i |= 0x8;
/*     */     }
/*     */     
/* 240 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 245 */     return new BlockStateList(this, new IBlockState[] { FACING, TYPE });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPistonMoving.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */