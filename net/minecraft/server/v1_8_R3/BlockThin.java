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
/*     */ public class BlockThin
/*     */   extends Block
/*     */ {
/*  22 */   public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
/*  23 */   public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
/*  24 */   public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
/*  25 */   public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
/*     */   private final boolean a;
/*     */   
/*     */   protected BlockThin(Material paramMaterial, boolean paramBoolean)
/*     */   {
/*  30 */     super(paramMaterial);
/*  31 */     j(this.blockStateList.getBlockData().set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)));
/*  32 */     this.a = paramBoolean;
/*  33 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData paramIBlockData, IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  38 */     return paramIBlockData.set(NORTH, Boolean.valueOf(c(paramIBlockAccess.getType(paramBlockPosition.north()).getBlock()))).set(SOUTH, Boolean.valueOf(c(paramIBlockAccess.getType(paramBlockPosition.south()).getBlock()))).set(WEST, Boolean.valueOf(c(paramIBlockAccess.getType(paramBlockPosition.west()).getBlock()))).set(EAST, Boolean.valueOf(c(paramIBlockAccess.getType(paramBlockPosition.east()).getBlock())));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  48 */     if (!this.a) {
/*  49 */       return null;
/*     */     }
/*  51 */     return super.getDropType(paramIBlockData, paramRandom, paramInt);
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  56 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  61 */     return false;
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
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, AxisAlignedBB paramAxisAlignedBB, List<AxisAlignedBB> paramList, Entity paramEntity)
/*     */   {
/*  74 */     boolean bool1 = c(paramWorld.getType(paramBlockPosition.north()).getBlock());
/*  75 */     boolean bool2 = c(paramWorld.getType(paramBlockPosition.south()).getBlock());
/*  76 */     boolean bool3 = c(paramWorld.getType(paramBlockPosition.west()).getBlock());
/*  77 */     boolean bool4 = c(paramWorld.getType(paramBlockPosition.east()).getBlock());
/*     */     
/*  79 */     if (((bool3) && (bool4)) || ((!bool3) && (!bool4) && (!bool1) && (!bool2))) {
/*  80 */       a(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
/*  81 */       super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*  82 */     } else if (bool3) {
/*  83 */       a(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
/*  84 */       super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*  85 */     } else if (bool4) {
/*  86 */       a(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
/*  87 */       super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */     }
/*  89 */     if (((bool1) && (bool2)) || ((!bool3) && (!bool4) && (!bool1) && (!bool2))) {
/*  90 */       a(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
/*  91 */       super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*  92 */     } else if (bool1) {
/*  93 */       a(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
/*  94 */       super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*  95 */     } else if (bool2) {
/*  96 */       a(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
/*  97 */       super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */     }
/*     */   }
/*     */   
/*     */   public void j()
/*     */   {
/* 103 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/* 108 */     float f1 = 0.4375F;
/* 109 */     float f2 = 0.5625F;
/* 110 */     float f3 = 0.4375F;
/* 111 */     float f4 = 0.5625F;
/*     */     
/* 113 */     boolean bool1 = c(paramIBlockAccess.getType(paramBlockPosition.north()).getBlock());
/* 114 */     boolean bool2 = c(paramIBlockAccess.getType(paramBlockPosition.south()).getBlock());
/* 115 */     boolean bool3 = c(paramIBlockAccess.getType(paramBlockPosition.west()).getBlock());
/* 116 */     boolean bool4 = c(paramIBlockAccess.getType(paramBlockPosition.east()).getBlock());
/*     */     
/* 118 */     if (((bool3) && (bool4)) || ((!bool3) && (!bool4) && (!bool1) && (!bool2))) {
/* 119 */       f1 = 0.0F;
/* 120 */       f2 = 1.0F;
/* 121 */     } else if (bool3) {
/* 122 */       f1 = 0.0F;
/* 123 */     } else if (bool4) {
/* 124 */       f2 = 1.0F;
/*     */     }
/* 126 */     if (((bool1) && (bool2)) || ((!bool3) && (!bool4) && (!bool1) && (!bool2))) {
/* 127 */       f3 = 0.0F;
/* 128 */       f4 = 1.0F;
/* 129 */     } else if (bool1) {
/* 130 */       f3 = 0.0F;
/* 131 */     } else if (bool2) {
/* 132 */       f4 = 1.0F;
/*     */     }
/*     */     
/* 135 */     a(f1, 0.0F, f3, f2, 1.0F, f4);
/*     */   }
/*     */   
/*     */   public final boolean c(Block paramBlock) {
/* 139 */     return (paramBlock.o()) || (paramBlock == this) || (paramBlock == Blocks.GLASS) || (paramBlock == Blocks.STAINED_GLASS) || (paramBlock == Blocks.STAINED_GLASS_PANE) || ((paramBlock instanceof BlockThin));
/*     */   }
/*     */   
/*     */   protected boolean I()
/*     */   {
/* 144 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 154 */     return 0;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 159 */     return new BlockStateList(this, new IBlockState[] { NORTH, EAST, WEST, SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockThin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */