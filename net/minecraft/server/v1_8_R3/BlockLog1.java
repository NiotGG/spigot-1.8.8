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
/*     */ public class BlockLog1
/*     */   extends BlockLogAbstract
/*     */ {
/*  15 */   public static final BlockStateEnum<BlockWood.EnumLogVariant> VARIANT = BlockStateEnum.a("variant", BlockWood.EnumLogVariant.class, new Predicate()
/*     */   {
/*     */     public boolean a(BlockWood.EnumLogVariant paramAnonymousEnumLogVariant) {
/*  18 */       return paramAnonymousEnumLogVariant.a() < 4;
/*     */     }
/*  15 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockLog1()
/*     */   {
/*  23 */     j(this.blockStateList.getBlockData().set(VARIANT, BlockWood.EnumLogVariant.OAK).set(AXIS, BlockLogAbstract.EnumLogRotation.Y));
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
/*  37 */         return BlockWood.EnumLogVariant.SPRUCE.c();
/*     */       case 2: 
/*  39 */         return BlockWood.EnumLogVariant.DARK_OAK.c();
/*     */       case 3: 
/*  41 */         return MaterialMapColor.p;
/*     */       }
/*  43 */       return BlockWood.EnumLogVariant.SPRUCE.c();
/*     */     }
/*     */     
/*  46 */     return localEnumLogVariant.c();
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
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  60 */     IBlockData localIBlockData = getBlockData().set(VARIANT, BlockWood.EnumLogVariant.a((paramInt & 0x3) % 4));
/*     */     
/*     */ 
/*  63 */     switch (paramInt & 0xC) {
/*     */     case 0: 
/*  65 */       localIBlockData = localIBlockData.set(AXIS, BlockLogAbstract.EnumLogRotation.Y);
/*  66 */       break;
/*     */     case 4: 
/*  68 */       localIBlockData = localIBlockData.set(AXIS, BlockLogAbstract.EnumLogRotation.X);
/*  69 */       break;
/*     */     case 8: 
/*  71 */       localIBlockData = localIBlockData.set(AXIS, BlockLogAbstract.EnumLogRotation.Z);
/*  72 */       break;
/*     */     default: 
/*  74 */       localIBlockData = localIBlockData.set(AXIS, BlockLogAbstract.EnumLogRotation.NONE);
/*     */     }
/*     */     
/*     */     
/*  78 */     return localIBlockData;
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  83 */     int i = 0;
/*     */     
/*  85 */     i |= ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a();
/*     */     
/*  87 */     switch (2.b[((BlockLogAbstract.EnumLogRotation)paramIBlockData.get(AXIS)).ordinal()]) {
/*     */     case 1: 
/*  89 */       i |= 0x4;
/*  90 */       break;
/*     */     case 2: 
/*  92 */       i |= 0x8;
/*  93 */       break;
/*     */     case 3: 
/*  95 */       i |= 0xC;
/*     */     }
/*     */     
/*     */     
/*  99 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 104 */     return new BlockStateList(this, new IBlockState[] { VARIANT, AXIS });
/*     */   }
/*     */   
/*     */   protected ItemStack i(IBlockData paramIBlockData)
/*     */   {
/* 109 */     return new ItemStack(Item.getItemOf(this), 1, ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a());
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/* 114 */     return ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockLog1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */