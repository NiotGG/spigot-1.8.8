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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockCobbleWall
/*     */   extends Block
/*     */ {
/*  23 */   public static final BlockStateBoolean UP = BlockStateBoolean.of("up");
/*  24 */   public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
/*  25 */   public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
/*  26 */   public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
/*  27 */   public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
/*  28 */   public static final BlockStateEnum<EnumCobbleVariant> VARIANT = BlockStateEnum.of("variant", EnumCobbleVariant.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockCobbleWall(Block paramBlock)
/*     */   {
/*  36 */     super(paramBlock.material);
/*  37 */     j(this.blockStateList.getBlockData().set(UP, Boolean.valueOf(false)).set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)).set(VARIANT, EnumCobbleVariant.NORMAL));
/*  38 */     c(paramBlock.strength);
/*  39 */     b(paramBlock.durability / 3.0F);
/*  40 */     a(paramBlock.stepSound);
/*  41 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  46 */     return LocaleI18n.get(a() + "." + EnumCobbleVariant.NORMAL.c() + ".name");
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  56 */     return false;
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  61 */     return false;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  66 */     boolean bool1 = e(paramIBlockAccess, paramBlockPosition.north());
/*  67 */     boolean bool2 = e(paramIBlockAccess, paramBlockPosition.south());
/*  68 */     boolean bool3 = e(paramIBlockAccess, paramBlockPosition.west());
/*  69 */     boolean bool4 = e(paramIBlockAccess, paramBlockPosition.east());
/*     */     
/*  71 */     float f1 = 0.25F;
/*  72 */     float f2 = 0.75F;
/*  73 */     float f3 = 0.25F;
/*  74 */     float f4 = 0.75F;
/*  75 */     float f5 = 1.0F;
/*     */     
/*  77 */     if (bool1) {
/*  78 */       f3 = 0.0F;
/*     */     }
/*  80 */     if (bool2) {
/*  81 */       f4 = 1.0F;
/*     */     }
/*  83 */     if (bool3) {
/*  84 */       f1 = 0.0F;
/*     */     }
/*  86 */     if (bool4) {
/*  87 */       f2 = 1.0F;
/*     */     }
/*     */     
/*  90 */     if ((bool1) && (bool2) && (!bool3) && (!bool4)) {
/*  91 */       f5 = 0.8125F;
/*  92 */       f1 = 0.3125F;
/*  93 */       f2 = 0.6875F;
/*  94 */     } else if ((!bool1) && (!bool2) && (bool3) && (bool4)) {
/*  95 */       f5 = 0.8125F;
/*  96 */       f3 = 0.3125F;
/*  97 */       f4 = 0.6875F;
/*     */     }
/*     */     
/* 100 */     a(f1, 0.0F, f3, f2, f5, f4);
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 106 */     updateShape(paramWorld, paramBlockPosition);
/* 107 */     this.maxY = 1.5D;
/* 108 */     return super.a(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   public boolean e(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition) {
/* 112 */     Block localBlock = paramIBlockAccess.getType(paramBlockPosition).getBlock();
/* 113 */     if (localBlock == Blocks.BARRIER) {
/* 114 */       return false;
/*     */     }
/* 116 */     if ((localBlock == this) || ((localBlock instanceof BlockFenceGate))) {
/* 117 */       return true;
/*     */     }
/* 119 */     if ((localBlock.material.k()) && (localBlock.d())) {
/* 120 */       return localBlock.material != Material.PUMPKIN;
/*     */     }
/* 122 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/* 134 */     return ((EnumCobbleVariant)paramIBlockData.get(VARIANT)).a();
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
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 147 */     return getBlockData().set(VARIANT, EnumCobbleVariant.a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 153 */     return ((EnumCobbleVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData paramIBlockData, IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/* 158 */     return paramIBlockData.set(UP, Boolean.valueOf(!paramIBlockAccess.isEmpty(paramBlockPosition.up()))).set(NORTH, Boolean.valueOf(e(paramIBlockAccess, paramBlockPosition.north()))).set(EAST, Boolean.valueOf(e(paramIBlockAccess, paramBlockPosition.east()))).set(SOUTH, Boolean.valueOf(e(paramIBlockAccess, paramBlockPosition.south()))).set(WEST, Boolean.valueOf(e(paramIBlockAccess, paramBlockPosition.west())));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 168 */     return new BlockStateList(this, new IBlockState[] { UP, NORTH, EAST, WEST, SOUTH, VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumCobbleVariant
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumCobbleVariant[] c;
/*     */     private final int d;
/*     */     private final String e;
/*     */     private String f;
/*     */     
/*     */     static
/*     */     {
/* 182 */       c = new EnumCobbleVariant[values().length];
/*     */       
/* 184 */       for (EnumCobbleVariant localEnumCobbleVariant : values()) {
/* 185 */         c[localEnumCobbleVariant.a()] = localEnumCobbleVariant;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumCobbleVariant(int paramInt, String paramString1, String paramString2) {
/* 190 */       this.d = paramInt;
/* 191 */       this.e = paramString1;
/* 192 */       this.f = paramString2;
/*     */     }
/*     */     
/*     */     public int a() {
/* 196 */       return this.d;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 205 */       return this.e;
/*     */     }
/*     */     
/*     */     public static EnumCobbleVariant a(int paramInt) {
/* 209 */       if ((paramInt < 0) || (paramInt >= c.length)) {
/* 210 */         paramInt = 0;
/*     */       }
/* 212 */       return c[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 217 */       return this.e;
/*     */     }
/*     */     
/*     */     public String c() {
/* 221 */       return this.f;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockCobbleWall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */