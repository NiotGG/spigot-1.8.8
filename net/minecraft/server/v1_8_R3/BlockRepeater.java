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
/*     */ public class BlockRepeater
/*     */   extends BlockDiodeAbstract
/*     */ {
/*  22 */   public static final BlockStateBoolean LOCKED = BlockStateBoolean.of("locked");
/*  23 */   public static final BlockStateInteger DELAY = BlockStateInteger.of("delay", 1, 4);
/*     */   
/*     */   protected BlockRepeater(boolean paramBoolean) {
/*  26 */     super(paramBoolean);
/*  27 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(DELAY, Integer.valueOf(1)).set(LOCKED, Boolean.valueOf(false)));
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  32 */     return LocaleI18n.get("item.diode.name");
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData paramIBlockData, IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  37 */     return paramIBlockData.set(LOCKED, Boolean.valueOf(b(paramIBlockAccess, paramBlockPosition, paramIBlockData)));
/*     */   }
/*     */   
/*     */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/*  42 */     if (!paramEntityHuman.abilities.mayBuild) {
/*  43 */       return false;
/*     */     }
/*     */     
/*  46 */     paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData.a(DELAY), 3);
/*  47 */     return true;
/*     */   }
/*     */   
/*     */   protected int d(IBlockData paramIBlockData)
/*     */   {
/*  52 */     return ((Integer)paramIBlockData.get(DELAY)).intValue() * 2;
/*     */   }
/*     */   
/*     */   protected IBlockData e(IBlockData paramIBlockData)
/*     */   {
/*  57 */     Integer localInteger = (Integer)paramIBlockData.get(DELAY);
/*  58 */     Boolean localBoolean = (Boolean)paramIBlockData.get(LOCKED);
/*  59 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/*  60 */     return Blocks.POWERED_REPEATER.getBlockData().set(FACING, localEnumDirection).set(DELAY, localInteger).set(LOCKED, localBoolean);
/*     */   }
/*     */   
/*     */   protected IBlockData k(IBlockData paramIBlockData)
/*     */   {
/*  65 */     Integer localInteger = (Integer)paramIBlockData.get(DELAY);
/*  66 */     Boolean localBoolean = (Boolean)paramIBlockData.get(LOCKED);
/*  67 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/*  68 */     return Blocks.UNPOWERED_REPEATER.getBlockData().set(FACING, localEnumDirection).set(DELAY, localInteger).set(LOCKED, localBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  74 */     return Items.REPEATER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean b(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  84 */     return c(paramIBlockAccess, paramBlockPosition, paramIBlockData) > 0;
/*     */   }
/*     */   
/*     */   protected boolean c(Block paramBlock)
/*     */   {
/*  89 */     return d(paramBlock);
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
/*     */   public void remove(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 117 */     super.remove(paramWorld, paramBlockPosition, paramIBlockData);
/* 118 */     h(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 123 */     return getBlockData().set(FACING, EnumDirection.fromType2(paramInt)).set(LOCKED, Boolean.valueOf(false)).set(DELAY, Integer.valueOf(1 + (paramInt >> 2)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 131 */     int i = 0;
/*     */     
/* 133 */     i |= ((EnumDirection)paramIBlockData.get(FACING)).b();
/* 134 */     i |= ((Integer)paramIBlockData.get(DELAY)).intValue() - 1 << 2;
/*     */     
/* 136 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 141 */     return new BlockStateList(this, new IBlockState[] { FACING, DELAY, LOCKED });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockRepeater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */