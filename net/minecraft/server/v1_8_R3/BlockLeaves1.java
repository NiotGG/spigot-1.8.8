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
/*     */ 
/*     */ 
/*     */ public class BlockLeaves1
/*     */   extends BlockLeaves
/*     */ {
/*  22 */   public static final BlockStateEnum<BlockWood.EnumLogVariant> VARIANT = BlockStateEnum.a("variant", BlockWood.EnumLogVariant.class, new Predicate()
/*     */   {
/*     */     public boolean a(BlockWood.EnumLogVariant paramAnonymousEnumLogVariant) {
/*  25 */       return paramAnonymousEnumLogVariant.a() < 4;
/*     */     }
/*  22 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockLeaves1()
/*     */   {
/*  30 */     j(this.blockStateList.getBlockData().set(VARIANT, BlockWood.EnumLogVariant.OAK).set(CHECK_DECAY, Boolean.valueOf(true)).set(DECAYABLE, Boolean.valueOf(true)));
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
/*     */   protected void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, int paramInt)
/*     */   {
/*  69 */     if ((paramIBlockData.get(VARIANT) == BlockWood.EnumLogVariant.OAK) && (paramWorld.random.nextInt(paramInt) == 0)) {
/*  70 */       a(paramWorld, paramBlockPosition, new ItemStack(Items.APPLE, 1, 0));
/*     */     }
/*     */   }
/*     */   
/*     */   protected int d(IBlockData paramIBlockData)
/*     */   {
/*  76 */     if (paramIBlockData.get(VARIANT) == BlockWood.EnumLogVariant.JUNGLE) {
/*  77 */       return 40;
/*     */     }
/*     */     
/*  80 */     return super.d(paramIBlockData);
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
/*     */   protected ItemStack i(IBlockData paramIBlockData)
/*     */   {
/*  93 */     return new ItemStack(Item.getItemOf(this), 1, ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a());
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  98 */     return getBlockData().set(VARIANT, b(paramInt)).set(DECAYABLE, Boolean.valueOf((paramInt & 0x4) == 0)).set(CHECK_DECAY, Boolean.valueOf((paramInt & 0x8) > 0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 106 */     int i = 0;
/*     */     
/* 108 */     i |= ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a();
/*     */     
/* 110 */     if (!((Boolean)paramIBlockData.get(DECAYABLE)).booleanValue()) {
/* 111 */       i |= 0x4;
/*     */     }
/*     */     
/* 114 */     if (((Boolean)paramIBlockData.get(CHECK_DECAY)).booleanValue()) {
/* 115 */       i |= 0x8;
/*     */     }
/*     */     
/* 118 */     return i;
/*     */   }
/*     */   
/*     */   public BlockWood.EnumLogVariant b(int paramInt)
/*     */   {
/* 123 */     return BlockWood.EnumLogVariant.a((paramInt & 0x3) % 4);
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 128 */     return new BlockStateList(this, new IBlockState[] { VARIANT, CHECK_DECAY, DECAYABLE });
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/* 133 */     return ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, EntityHuman paramEntityHuman, BlockPosition paramBlockPosition, IBlockData paramIBlockData, TileEntity paramTileEntity)
/*     */   {
/* 138 */     if ((!paramWorld.isClientSide) && (paramEntityHuman.bZ() != null) && (paramEntityHuman.bZ().getItem() == Items.SHEARS)) {
/* 139 */       paramEntityHuman.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
/*     */       
/*     */ 
/* 142 */       a(paramWorld, paramBlockPosition, new ItemStack(Item.getItemOf(this), 1, ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a()));
/* 143 */       return;
/*     */     }
/*     */     
/* 146 */     super.a(paramWorld, paramEntityHuman, paramBlockPosition, paramIBlockData, paramTileEntity);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockLeaves1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */