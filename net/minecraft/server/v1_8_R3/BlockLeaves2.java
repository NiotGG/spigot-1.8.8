/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
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
/*     */ public class BlockLeaves2
/*     */   extends BlockLeaves
/*     */ {
/*  20 */   public static final BlockStateEnum<BlockWood.EnumLogVariant> VARIANT = BlockStateEnum.a("variant", BlockWood.EnumLogVariant.class, new Predicate()
/*     */   {
/*     */     public boolean a(BlockWood.EnumLogVariant paramAnonymousEnumLogVariant) {
/*  23 */       return paramAnonymousEnumLogVariant.a() >= 4;
/*     */     }
/*  20 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockLeaves2()
/*     */   {
/*  31 */     j(this.blockStateList.getBlockData().set(VARIANT, BlockWood.EnumLogVariant.ACACIA).set(CHECK_DECAY, Boolean.valueOf(true)).set(DECAYABLE, Boolean.valueOf(true)));
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, int paramInt)
/*     */   {
/*  36 */     if ((paramIBlockData.get(VARIANT) == BlockWood.EnumLogVariant.DARK_OAK) && (paramWorld.random.nextInt(paramInt) == 0)) {
/*  37 */       a(paramWorld, paramBlockPosition, new ItemStack(Items.APPLE, 1, 0));
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  43 */     return ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   public int getDropData(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  48 */     IBlockData localIBlockData = paramWorld.getType(paramBlockPosition);
/*  49 */     return localIBlockData.getBlock().toLegacyData(localIBlockData) & 0x3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ItemStack i(IBlockData paramIBlockData)
/*     */   {
/*  60 */     return new ItemStack(Item.getItemOf(this), 1, ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a() - 4);
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  65 */     return getBlockData().set(VARIANT, b(paramInt)).set(DECAYABLE, Boolean.valueOf((paramInt & 0x4) == 0)).set(CHECK_DECAY, Boolean.valueOf((paramInt & 0x8) > 0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  73 */     int i = 0;
/*     */     
/*  75 */     i |= ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a() - 4;
/*     */     
/*  77 */     if (!((Boolean)paramIBlockData.get(DECAYABLE)).booleanValue()) {
/*  78 */       i |= 0x4;
/*     */     }
/*     */     
/*  81 */     if (((Boolean)paramIBlockData.get(CHECK_DECAY)).booleanValue()) {
/*  82 */       i |= 0x8;
/*     */     }
/*     */     
/*  85 */     return i;
/*     */   }
/*     */   
/*     */   public BlockWood.EnumLogVariant b(int paramInt)
/*     */   {
/*  90 */     return BlockWood.EnumLogVariant.a((paramInt & 0x3) + 4);
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  95 */     return new BlockStateList(this, new IBlockState[] { VARIANT, CHECK_DECAY, DECAYABLE });
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, EntityHuman paramEntityHuman, BlockPosition paramBlockPosition, IBlockData paramIBlockData, TileEntity paramTileEntity)
/*     */   {
/* 100 */     if ((!paramWorld.isClientSide) && (paramEntityHuman.bZ() != null) && (paramEntityHuman.bZ().getItem() == Items.SHEARS)) {
/* 101 */       paramEntityHuman.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
/*     */       
/*     */ 
/* 104 */       a(paramWorld, paramBlockPosition, new ItemStack(Item.getItemOf(this), 1, ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a() - 4));
/* 105 */       return;
/*     */     }
/*     */     
/* 108 */     super.a(paramWorld, paramEntityHuman, paramBlockPosition, paramIBlockData, paramTileEntity);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockLeaves2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */