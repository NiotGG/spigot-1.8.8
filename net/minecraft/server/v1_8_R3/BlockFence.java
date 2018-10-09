/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public class BlockFence
/*     */   extends Block
/*     */ {
/*  21 */   public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
/*  22 */   public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
/*  23 */   public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
/*  24 */   public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
/*     */   
/*     */   public BlockFence(Material paramMaterial) {
/*  27 */     this(paramMaterial, paramMaterial.r());
/*     */   }
/*     */   
/*     */   public BlockFence(Material paramMaterial, MaterialMapColor paramMaterialMapColor) {
/*  31 */     super(paramMaterial, paramMaterialMapColor);
/*  32 */     j(this.blockStateList.getBlockData().set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)));
/*  33 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, AxisAlignedBB paramAxisAlignedBB, List<AxisAlignedBB> paramList, Entity paramEntity)
/*     */   {
/*  38 */     boolean bool1 = e(paramWorld, paramBlockPosition.north());
/*  39 */     boolean bool2 = e(paramWorld, paramBlockPosition.south());
/*  40 */     boolean bool3 = e(paramWorld, paramBlockPosition.west());
/*  41 */     boolean bool4 = e(paramWorld, paramBlockPosition.east());
/*     */     
/*  43 */     float f1 = 0.375F;
/*  44 */     float f2 = 0.625F;
/*  45 */     float f3 = 0.375F;
/*  46 */     float f4 = 0.625F;
/*     */     
/*  48 */     if (bool1) {
/*  49 */       f3 = 0.0F;
/*     */     }
/*  51 */     if (bool2) {
/*  52 */       f4 = 1.0F;
/*     */     }
/*  54 */     if ((bool1) || (bool2)) {
/*  55 */       a(f1, 0.0F, f3, f2, 1.5F, f4);
/*  56 */       super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */     }
/*  58 */     f3 = 0.375F;
/*  59 */     f4 = 0.625F;
/*  60 */     if (bool3) {
/*  61 */       f1 = 0.0F;
/*     */     }
/*  63 */     if (bool4) {
/*  64 */       f2 = 1.0F;
/*     */     }
/*  66 */     if ((bool3) || (bool4) || ((!bool1) && (!bool2))) {
/*  67 */       a(f1, 0.0F, f3, f2, 1.5F, f4);
/*  68 */       super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */     }
/*     */     
/*  71 */     if (bool1) {
/*  72 */       f3 = 0.0F;
/*     */     }
/*  74 */     if (bool2) {
/*  75 */       f4 = 1.0F;
/*     */     }
/*     */     
/*  78 */     a(f1, 0.0F, f3, f2, 1.0F, f4);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  83 */     boolean bool1 = e(paramIBlockAccess, paramBlockPosition.north());
/*  84 */     boolean bool2 = e(paramIBlockAccess, paramBlockPosition.south());
/*  85 */     boolean bool3 = e(paramIBlockAccess, paramBlockPosition.west());
/*  86 */     boolean bool4 = e(paramIBlockAccess, paramBlockPosition.east());
/*     */     
/*  88 */     float f1 = 0.375F;
/*  89 */     float f2 = 0.625F;
/*  90 */     float f3 = 0.375F;
/*  91 */     float f4 = 0.625F;
/*     */     
/*  93 */     if (bool1) {
/*  94 */       f3 = 0.0F;
/*     */     }
/*  96 */     if (bool2) {
/*  97 */       f4 = 1.0F;
/*     */     }
/*  99 */     if (bool3) {
/* 100 */       f1 = 0.0F;
/*     */     }
/* 102 */     if (bool4) {
/* 103 */       f2 = 1.0F;
/*     */     }
/*     */     
/* 106 */     a(f1, 0.0F, f3, f2, 1.0F, f4);
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/* 116 */     return false;
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/* 121 */     return false;
/*     */   }
/*     */   
/*     */   public boolean e(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition) {
/* 125 */     Block localBlock = paramIBlockAccess.getType(paramBlockPosition).getBlock();
/* 126 */     if (localBlock == Blocks.BARRIER) {
/* 127 */       return false;
/*     */     }
/* 129 */     if ((((localBlock instanceof BlockFence)) && (localBlock.material == this.material)) || ((localBlock instanceof BlockFenceGate))) {
/* 130 */       return true;
/*     */     }
/* 132 */     if ((localBlock.material.k()) && (localBlock.d())) {
/* 133 */       return localBlock.material != Material.PUMPKIN;
/*     */     }
/* 135 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 145 */     if (paramWorld.isClientSide) {
/* 146 */       return true;
/*     */     }
/*     */     
/* 149 */     return ItemLeash.a(paramEntityHuman, paramWorld, paramBlockPosition);
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 154 */     return 0;
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData paramIBlockData, IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/* 159 */     return paramIBlockData.set(NORTH, Boolean.valueOf(e(paramIBlockAccess, paramBlockPosition.north()))).set(EAST, Boolean.valueOf(e(paramIBlockAccess, paramBlockPosition.east()))).set(SOUTH, Boolean.valueOf(e(paramIBlockAccess, paramBlockPosition.south()))).set(WEST, Boolean.valueOf(e(paramIBlockAccess, paramBlockPosition.west())));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 168 */     return new BlockStateList(this, new IBlockState[] { NORTH, EAST, WEST, SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockFence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */