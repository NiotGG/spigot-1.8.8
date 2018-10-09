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
/*     */ public class BlockHugeMushroom
/*     */   extends Block
/*     */ {
/*  19 */   public static final BlockStateEnum<EnumHugeMushroomVariant> VARIANT = BlockStateEnum.of("variant", EnumHugeMushroomVariant.class);
/*     */   private final Block b;
/*     */   
/*     */   public BlockHugeMushroom(Material paramMaterial, MaterialMapColor paramMaterialMapColor, Block paramBlock)
/*     */   {
/*  24 */     super(paramMaterial, paramMaterialMapColor);
/*  25 */     j(this.blockStateList.getBlockData().set(VARIANT, EnumHugeMushroomVariant.ALL_OUTSIDE));
/*  26 */     this.b = paramBlock;
/*     */   }
/*     */   
/*     */   public int a(Random paramRandom)
/*     */   {
/*  31 */     return Math.max(0, paramRandom.nextInt(10) - 7);
/*     */   }
/*     */   
/*     */   public MaterialMapColor g(IBlockData paramIBlockData)
/*     */   {
/*  36 */     switch (1.a[((EnumHugeMushroomVariant)paramIBlockData.get(VARIANT)).ordinal()]) {
/*     */     case 1: 
/*  38 */       return MaterialMapColor.e;
/*     */     case 2: 
/*  40 */       return MaterialMapColor.d;
/*     */     case 3: 
/*  42 */       return MaterialMapColor.d;
/*     */     }
/*  44 */     return super.g(paramIBlockData);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  51 */     return Item.getItemOf(this.b);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*     */   {
/*  61 */     return getBlockData();
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  66 */     return getBlockData().set(VARIANT, EnumHugeMushroomVariant.a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  72 */     return ((EnumHugeMushroomVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  77 */     return new BlockStateList(this, new IBlockState[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum EnumHugeMushroomVariant
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumHugeMushroomVariant[] n;
/*     */     
/*     */ 
/*     */ 
/*     */     private final int o;
/*     */     
/*     */ 
/*     */ 
/*     */     private final String p;
/*     */     
/*     */ 
/*     */ 
/*     */     static
/*     */     {
/* 100 */       n = new EnumHugeMushroomVariant[16];
/*     */       
/* 102 */       for (EnumHugeMushroomVariant localEnumHugeMushroomVariant : values()) {
/* 103 */         n[localEnumHugeMushroomVariant.a()] = localEnumHugeMushroomVariant;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumHugeMushroomVariant(int paramInt, String paramString) {
/* 108 */       this.o = paramInt;
/* 109 */       this.p = paramString;
/*     */     }
/*     */     
/*     */     public int a() {
/* 113 */       return this.o;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 122 */       return this.p;
/*     */     }
/*     */     
/*     */     public static EnumHugeMushroomVariant a(int paramInt) {
/* 126 */       if ((paramInt < 0) || (paramInt >= n.length)) {
/* 127 */         paramInt = 0;
/*     */       }
/* 129 */       EnumHugeMushroomVariant localEnumHugeMushroomVariant = n[paramInt];
/* 130 */       return localEnumHugeMushroomVariant == null ? n[0] : localEnumHugeMushroomVariant;
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 135 */       return this.p;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockHugeMushroom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */