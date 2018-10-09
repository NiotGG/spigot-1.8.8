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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockEnderChest
/*     */   extends BlockContainer
/*     */ {
/*  30 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*     */   
/*     */   protected BlockEnderChest() {
/*  33 */     super(Material.STONE);
/*  34 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
/*  35 */     a(CreativeModeTab.c);
/*  36 */     a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  41 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  46 */     return false;
/*     */   }
/*     */   
/*     */   public int b()
/*     */   {
/*  51 */     return 2;
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  57 */     return Item.getItemOf(Blocks.OBSIDIAN);
/*     */   }
/*     */   
/*     */   public int a(Random paramRandom)
/*     */   {
/*  62 */     return 8;
/*     */   }
/*     */   
/*     */   protected boolean I()
/*     */   {
/*  67 */     return true;
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*     */   {
/*  72 */     return getBlockData().set(FACING, paramEntityLiving.getDirection().opposite());
/*     */   }
/*     */   
/*     */   public void postPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityLiving paramEntityLiving, ItemStack paramItemStack)
/*     */   {
/*  77 */     paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData.set(FACING, paramEntityLiving.getDirection().opposite()), 2);
/*     */   }
/*     */   
/*     */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/*  82 */     InventoryEnderChest localInventoryEnderChest = paramEntityHuman.getEnderChest();
/*  83 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/*  84 */     if ((localInventoryEnderChest == null) || (!(localTileEntity instanceof TileEntityEnderChest))) {
/*  85 */       return true;
/*     */     }
/*     */     
/*  88 */     if (paramWorld.getType(paramBlockPosition.up()).getBlock().isOccluding()) {
/*  89 */       return true;
/*     */     }
/*     */     
/*  92 */     if (paramWorld.isClientSide) {
/*  93 */       return true;
/*     */     }
/*     */     
/*  96 */     localInventoryEnderChest.a((TileEntityEnderChest)localTileEntity);
/*  97 */     paramEntityHuman.openContainer(localInventoryEnderChest);
/*  98 */     paramEntityHuman.b(StatisticList.V);
/*     */     
/* 100 */     return true;
/*     */   }
/*     */   
/*     */   public TileEntity a(World paramWorld, int paramInt)
/*     */   {
/* 105 */     return new TileEntityEnderChest();
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
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 127 */     EnumDirection localEnumDirection = EnumDirection.fromType1(paramInt);
/* 128 */     if (localEnumDirection.k() == EnumDirection.EnumAxis.Y) {
/* 129 */       localEnumDirection = EnumDirection.NORTH;
/*     */     }
/* 131 */     return getBlockData().set(FACING, localEnumDirection);
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 137 */     return ((EnumDirection)paramIBlockData.get(FACING)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 142 */     return new BlockStateList(this, new IBlockState[] { FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockEnderChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */