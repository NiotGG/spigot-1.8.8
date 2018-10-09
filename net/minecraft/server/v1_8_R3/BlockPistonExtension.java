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
/*     */ public class BlockPistonExtension
/*     */   extends Block
/*     */ {
/*  26 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing");
/*  27 */   public static final BlockStateEnum<EnumPistonType> TYPE = BlockStateEnum.of("type", EnumPistonType.class);
/*  28 */   public static final BlockStateBoolean SHORT = BlockStateBoolean.of("short");
/*     */   
/*     */ 
/*     */ 
/*     */   public BlockPistonExtension()
/*     */   {
/*  34 */     super(Material.PISTON);
/*  35 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(TYPE, EnumPistonType.DEFAULT).set(SHORT, Boolean.valueOf(false)));
/*  36 */     a(i);
/*  37 */     c(0.5F);
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman)
/*     */   {
/*  42 */     if (paramEntityHuman.abilities.canInstantlyBuild) {
/*  43 */       EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/*  44 */       if (localEnumDirection != null) {
/*  45 */         BlockPosition localBlockPosition = paramBlockPosition.shift(localEnumDirection.opposite());
/*  46 */         Block localBlock = paramWorld.getType(localBlockPosition).getBlock();
/*  47 */         if ((localBlock == Blocks.PISTON) || (localBlock == Blocks.STICKY_PISTON)) {
/*  48 */           paramWorld.setAir(localBlockPosition);
/*     */         }
/*     */       }
/*     */     }
/*  52 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramEntityHuman);
/*     */   }
/*     */   
/*     */   public void remove(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  57 */     super.remove(paramWorld, paramBlockPosition, paramIBlockData);
/*  58 */     EnumDirection localEnumDirection = ((EnumDirection)paramIBlockData.get(FACING)).opposite();
/*  59 */     paramBlockPosition = paramBlockPosition.shift(localEnumDirection);
/*     */     
/*  61 */     IBlockData localIBlockData = paramWorld.getType(paramBlockPosition);
/*  62 */     if (((localIBlockData.getBlock() == Blocks.PISTON) || (localIBlockData.getBlock() == Blocks.STICKY_PISTON)) && 
/*  63 */       (((Boolean)localIBlockData.get(BlockPiston.EXTENDED)).booleanValue())) {
/*  64 */       localIBlockData.getBlock().b(paramWorld, paramBlockPosition, localIBlockData, 0);
/*  65 */       paramWorld.setAir(paramBlockPosition);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean c()
/*     */   {
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  77 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection)
/*     */   {
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public int a(Random paramRandom)
/*     */   {
/*  92 */     return 0;
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, AxisAlignedBB paramAxisAlignedBB, List<AxisAlignedBB> paramList, Entity paramEntity)
/*     */   {
/*  97 */     d(paramIBlockData);
/*  98 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */     
/* 100 */     e(paramIBlockData);
/* 101 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */     
/* 103 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   private void e(IBlockData paramIBlockData) {
/* 107 */     float f1 = 0.25F;
/* 108 */     float f2 = 0.375F;
/* 109 */     float f3 = 0.625F;
/* 110 */     float f4 = 0.25F;
/* 111 */     float f5 = 0.75F;
/*     */     
/* 113 */     switch (1.a[((EnumDirection)paramIBlockData.get(FACING)).ordinal()]) {
/*     */     case 1: 
/* 115 */       a(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
/* 116 */       break;
/*     */     case 2: 
/* 118 */       a(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
/* 119 */       break;
/*     */     case 3: 
/* 121 */       a(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
/* 122 */       break;
/*     */     case 4: 
/* 124 */       a(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
/* 125 */       break;
/*     */     case 5: 
/* 127 */       a(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
/* 128 */       break;
/*     */     case 6: 
/* 130 */       a(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/* 137 */     d(paramIBlockAccess.getType(paramBlockPosition));
/*     */   }
/*     */   
/*     */   public void d(IBlockData paramIBlockData) {
/* 141 */     float f = 0.25F;
/* 142 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/* 143 */     if (localEnumDirection == null) {
/* 144 */       return;
/*     */     }
/*     */     
/* 147 */     switch (1.a[localEnumDirection.ordinal()]) {
/*     */     case 1: 
/* 149 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
/* 150 */       break;
/*     */     case 2: 
/* 152 */       a(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 153 */       break;
/*     */     case 3: 
/* 155 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
/* 156 */       break;
/*     */     case 4: 
/* 158 */       a(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
/* 159 */       break;
/*     */     case 5: 
/* 161 */       a(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
/* 162 */       break;
/*     */     case 6: 
/* 164 */       a(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*     */   {
/* 171 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/* 172 */     BlockPosition localBlockPosition = paramBlockPosition.shift(localEnumDirection.opposite());
/* 173 */     IBlockData localIBlockData = paramWorld.getType(localBlockPosition);
/* 174 */     if ((localIBlockData.getBlock() != Blocks.PISTON) && (localIBlockData.getBlock() != Blocks.STICKY_PISTON)) {
/* 175 */       paramWorld.setAir(paramBlockPosition);
/*     */     } else {
/* 177 */       localIBlockData.getBlock().doPhysics(paramWorld, localBlockPosition, localIBlockData, paramBlock);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static EnumDirection b(int paramInt)
/*     */   {
/* 188 */     int i = paramInt & 0x7;
/* 189 */     if (i > 5) {
/* 190 */       return null;
/*     */     }
/* 192 */     return EnumDirection.fromType1(i);
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
/* 205 */     return getBlockData().set(FACING, b(paramInt)).set(TYPE, (paramInt & 0x8) > 0 ? EnumPistonType.STICKY : EnumPistonType.DEFAULT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 212 */     int i = 0;
/*     */     
/* 214 */     i |= ((EnumDirection)paramIBlockData.get(FACING)).a();
/*     */     
/* 216 */     if (paramIBlockData.get(TYPE) == EnumPistonType.STICKY) {
/* 217 */       i |= 0x8;
/*     */     }
/*     */     
/* 220 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 225 */     return new BlockStateList(this, new IBlockState[] { FACING, TYPE, SHORT });
/*     */   }
/*     */   
/*     */   public static enum EnumPistonType implements INamable
/*     */   {
/*     */     private final String c;
/*     */     
/*     */     private EnumPistonType(String paramString)
/*     */     {
/* 234 */       this.c = paramString;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 239 */       return this.c;
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 244 */       return this.c;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPistonExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */