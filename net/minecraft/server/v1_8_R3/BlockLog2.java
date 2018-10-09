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
/*     */ public class BlockLog2
/*     */   extends BlockLogAbstract
/*     */ {
/*  15 */   public static final BlockStateEnum<BlockWood.EnumLogVariant> VARIANT = BlockStateEnum.a("variant", BlockWood.EnumLogVariant.class, new Predicate()
/*     */   {
/*     */     public boolean a(BlockWood.EnumLogVariant paramAnonymousEnumLogVariant) {
/*  18 */       return paramAnonymousEnumLogVariant.a() >= 4;
/*     */     }
/*  15 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockLog2()
/*     */   {
/*  23 */     j(this.blockStateList.getBlockData().set(VARIANT, BlockWood.EnumLogVariant.ACACIA).set(AXIS, BlockLogAbstract.EnumLogRotation.Y));
/*     */   }
/*     */   
/*     */   public MaterialMapColor g(IBlockData paramIBlockData)
/*     */   {
/*  28 */     BlockWood.EnumLogVariant localEnumLogVariant = (BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT);
/*  29 */     switch (2.b[((BlockLogAbstract.EnumLogRotation)paramIBlockData.get(AXIS)).ordinal()]) {
/*     */     case 1: 
/*     */     case 2: 
/*     */     case 3: 
/*     */     default: 
/*  34 */       switch (2.a[localEnumLogVariant.ordinal()]) {
/*     */       case 1: 
/*     */       default: 
/*  37 */         return MaterialMapColor.m;
/*     */       }
/*  39 */       return BlockWood.EnumLogVariant.DARK_OAK.c();
/*     */     }
/*     */     
/*  42 */     return localEnumLogVariant.c();
/*     */   }
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
/*  54 */     IBlockData localIBlockData = getBlockData().set(VARIANT, BlockWood.EnumLogVariant.a((paramInt & 0x3) + 4));
/*     */     
/*     */ 
/*  57 */     switch (paramInt & 0xC) {
/*     */     case 0: 
/*  59 */       localIBlockData = localIBlockData.set(AXIS, BlockLogAbstract.EnumLogRotation.Y);
/*  60 */       break;
/*     */     case 4: 
/*  62 */       localIBlockData = localIBlockData.set(AXIS, BlockLogAbstract.EnumLogRotation.X);
/*  63 */       break;
/*     */     case 8: 
/*  65 */       localIBlockData = localIBlockData.set(AXIS, BlockLogAbstract.EnumLogRotation.Z);
/*  66 */       break;
/*     */     default: 
/*  68 */       localIBlockData = localIBlockData.set(AXIS, BlockLogAbstract.EnumLogRotation.NONE);
/*     */     }
/*     */     
/*     */     
/*  72 */     return localIBlockData;
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  77 */     int i = 0;
/*     */     
/*  79 */     i |= ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a() - 4;
/*     */     
/*  81 */     switch (2.b[((BlockLogAbstract.EnumLogRotation)paramIBlockData.get(AXIS)).ordinal()]) {
/*     */     case 1: 
/*  83 */       i |= 0x4;
/*  84 */       break;
/*     */     case 2: 
/*  86 */       i |= 0x8;
/*  87 */       break;
/*     */     case 3: 
/*  89 */       i |= 0xC;
/*     */     }
/*     */     
/*     */     
/*  93 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  98 */     return new BlockStateList(this, new IBlockState[] { VARIANT, AXIS });
/*     */   }
/*     */   
/*     */   protected ItemStack i(IBlockData paramIBlockData)
/*     */   {
/* 103 */     return new ItemStack(Item.getItemOf(this), 1, ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a() - 4);
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/* 108 */     return ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a() - 4;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockLog2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */