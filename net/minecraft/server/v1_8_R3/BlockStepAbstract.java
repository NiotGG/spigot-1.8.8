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
/*     */ public abstract class BlockStepAbstract
/*     */   extends Block
/*     */ {
/*  21 */   public static final BlockStateEnum<EnumSlabHalf> HALF = BlockStateEnum.of("half", EnumSlabHalf.class);
/*     */   
/*     */ 
/*     */ 
/*     */   public BlockStepAbstract(Material paramMaterial)
/*     */   {
/*  27 */     super(paramMaterial);
/*     */     
/*  29 */     if (l()) {
/*  30 */       this.r = true;
/*     */     } else {
/*  32 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     }
/*  34 */     e(255);
/*     */   }
/*     */   
/*     */   protected boolean I()
/*     */   {
/*  39 */     return false;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  44 */     if (l()) {
/*  45 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  46 */       return;
/*     */     }
/*     */     
/*  49 */     IBlockData localIBlockData = paramIBlockAccess.getType(paramBlockPosition);
/*  50 */     if (localIBlockData.getBlock() == this) {
/*  51 */       if (localIBlockData.get(HALF) == EnumSlabHalf.TOP) {
/*  52 */         a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       } else {
/*  54 */         a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void j()
/*     */   {
/*  61 */     if (l()) {
/*  62 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } else {
/*  64 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, AxisAlignedBB paramAxisAlignedBB, List<AxisAlignedBB> paramList, Entity paramEntity)
/*     */   {
/*  70 */     updateShape(paramWorld, paramBlockPosition);
/*  71 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  76 */     return l();
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*     */   {
/*  81 */     IBlockData localIBlockData = super.getPlacedState(paramWorld, paramBlockPosition, paramEnumDirection, paramFloat1, paramFloat2, paramFloat3, paramInt, paramEntityLiving).set(HALF, EnumSlabHalf.BOTTOM);
/*     */     
/*  83 */     if (l()) {
/*  84 */       return localIBlockData;
/*     */     }
/*     */     
/*  87 */     if ((paramEnumDirection == EnumDirection.DOWN) || ((paramEnumDirection != EnumDirection.UP) && (paramFloat2 > 0.5D))) {
/*  88 */       return localIBlockData.set(HALF, EnumSlabHalf.TOP);
/*     */     }
/*  90 */     return localIBlockData;
/*     */   }
/*     */   
/*     */   public int a(Random paramRandom)
/*     */   {
/*  95 */     if (l()) {
/*  96 */       return 2;
/*     */     }
/*  98 */     return 1;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/* 103 */     return l();
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
/*     */   public abstract String b(int paramInt);
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
/*     */   public int getDropData(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/* 150 */     return super.getDropData(paramWorld, paramBlockPosition) & 0x7;
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract boolean l();
/*     */   
/*     */   public abstract IBlockState<?> n();
/*     */   
/*     */   public abstract Object a(ItemStack paramItemStack);
/*     */   
/*     */   public static enum EnumSlabHalf
/*     */     implements INamable
/*     */   {
/*     */     private final String c;
/*     */     
/*     */     private EnumSlabHalf(String paramString)
/*     */     {
/* 167 */       this.c = paramString;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 172 */       return this.c;
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 177 */       return this.c;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStepAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */