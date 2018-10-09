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
/*     */ public class BlockFurnace
/*     */   extends BlockContainer
/*     */ {
/*  25 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*     */   private final boolean b;
/*     */   private static boolean N;
/*     */   
/*     */   protected BlockFurnace(boolean paramBoolean)
/*     */   {
/*  31 */     super(Material.STONE);
/*  32 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
/*  33 */     this.b = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  39 */     return Item.getItemOf(Blocks.FURNACE);
/*     */   }
/*     */   
/*     */   public void onPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  44 */     e(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   private void e(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData) {
/*  48 */     if (paramWorld.isClientSide) {
/*  49 */       return;
/*     */     }
/*     */     
/*  52 */     Block localBlock1 = paramWorld.getType(paramBlockPosition.north()).getBlock();
/*  53 */     Block localBlock2 = paramWorld.getType(paramBlockPosition.south()).getBlock();
/*  54 */     Block localBlock3 = paramWorld.getType(paramBlockPosition.west()).getBlock();
/*  55 */     Block localBlock4 = paramWorld.getType(paramBlockPosition.east()).getBlock();
/*     */     
/*  57 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/*     */     
/*  59 */     if ((localEnumDirection == EnumDirection.NORTH) && (localBlock1.o()) && (!localBlock2.o())) {
/*  60 */       localEnumDirection = EnumDirection.SOUTH;
/*  61 */     } else if ((localEnumDirection == EnumDirection.SOUTH) && (localBlock2.o()) && (!localBlock1.o())) {
/*  62 */       localEnumDirection = EnumDirection.NORTH;
/*  63 */     } else if ((localEnumDirection == EnumDirection.WEST) && (localBlock3.o()) && (!localBlock4.o())) {
/*  64 */       localEnumDirection = EnumDirection.EAST;
/*  65 */     } else if ((localEnumDirection == EnumDirection.EAST) && (localBlock4.o()) && (!localBlock3.o())) {
/*  66 */       localEnumDirection = EnumDirection.WEST;
/*     */     }
/*     */     
/*  69 */     paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData.set(FACING, localEnumDirection), 2);
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
/*     */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 108 */     if (paramWorld.isClientSide) {
/* 109 */       return true;
/*     */     }
/*     */     
/* 112 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 113 */     if ((localTileEntity instanceof TileEntityFurnace)) {
/* 114 */       paramEntityHuman.openContainer((TileEntityFurnace)localTileEntity);
/* 115 */       paramEntityHuman.b(StatisticList.Y);
/*     */     }
/*     */     
/* 118 */     return true;
/*     */   }
/*     */   
/*     */   public static void a(boolean paramBoolean, World paramWorld, BlockPosition paramBlockPosition) {
/* 122 */     IBlockData localIBlockData = paramWorld.getType(paramBlockPosition);
/* 123 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/*     */     
/* 125 */     N = true;
/*     */     
/* 127 */     if (paramBoolean) {
/* 128 */       paramWorld.setTypeAndData(paramBlockPosition, Blocks.LIT_FURNACE.getBlockData().set(FACING, localIBlockData.get(FACING)), 3);
/* 129 */       paramWorld.setTypeAndData(paramBlockPosition, Blocks.LIT_FURNACE.getBlockData().set(FACING, localIBlockData.get(FACING)), 3);
/*     */     } else {
/* 131 */       paramWorld.setTypeAndData(paramBlockPosition, Blocks.FURNACE.getBlockData().set(FACING, localIBlockData.get(FACING)), 3);
/* 132 */       paramWorld.setTypeAndData(paramBlockPosition, Blocks.FURNACE.getBlockData().set(FACING, localIBlockData.get(FACING)), 3);
/*     */     }
/* 134 */     N = false;
/*     */     
/* 136 */     if (localTileEntity != null) {
/* 137 */       localTileEntity.D();
/* 138 */       paramWorld.setTileEntity(paramBlockPosition, localTileEntity);
/*     */     }
/*     */   }
/*     */   
/*     */   public TileEntity a(World paramWorld, int paramInt)
/*     */   {
/* 144 */     return new TileEntityFurnace();
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*     */   {
/* 149 */     return getBlockData().set(FACING, paramEntityLiving.getDirection().opposite());
/*     */   }
/*     */   
/*     */   public void postPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityLiving paramEntityLiving, ItemStack paramItemStack)
/*     */   {
/* 154 */     paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData.set(FACING, paramEntityLiving.getDirection().opposite()), 2);
/*     */     
/* 156 */     if (paramItemStack.hasName()) {
/* 157 */       TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 158 */       if ((localTileEntity instanceof TileEntityFurnace)) {
/* 159 */         ((TileEntityFurnace)localTileEntity).a(paramItemStack.getName());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 166 */     if (!N) {
/* 167 */       TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 168 */       if ((localTileEntity instanceof TileEntityFurnace)) {
/* 169 */         InventoryUtils.dropInventory(paramWorld, paramBlockPosition, (TileEntityFurnace)localTileEntity);
/*     */         
/* 171 */         paramWorld.updateAdjacentComparators(paramBlockPosition, this);
/*     */       }
/*     */     }
/* 174 */     super.remove(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone()
/*     */   {
/* 179 */     return true;
/*     */   }
/*     */   
/*     */   public int l(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/* 184 */     return Container.a(paramWorld.getTileEntity(paramBlockPosition));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int b()
/*     */   {
/* 194 */     return 3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 204 */     EnumDirection localEnumDirection = EnumDirection.fromType1(paramInt);
/* 205 */     if (localEnumDirection.k() == EnumDirection.EnumAxis.Y) {
/* 206 */       localEnumDirection = EnumDirection.NORTH;
/*     */     }
/* 208 */     return getBlockData().set(FACING, localEnumDirection);
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 214 */     return ((EnumDirection)paramIBlockData.get(FACING)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 219 */     return new BlockStateList(this, new IBlockState[] { FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */