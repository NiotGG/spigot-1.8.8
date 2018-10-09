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
/*     */ public class BlockBanner
/*     */   extends BlockContainer
/*     */ {
/*  27 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*  28 */   public static final BlockStateInteger ROTATION = BlockStateInteger.of("rotation", 0, 15);
/*     */   
/*     */   protected BlockBanner() {
/*  31 */     super(Material.WOOD);
/*  32 */     float f1 = 0.25F;
/*  33 */     float f2 = 1.0F;
/*  34 */     a(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f2, 0.5F + f1);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  39 */     return LocaleI18n.get("item.banner.white.name");
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  45 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean d()
/*     */   {
/*  56 */     return false;
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  61 */     return true;
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  66 */     return false;
/*     */   }
/*     */   
/*     */   public boolean g()
/*     */   {
/*  71 */     return true;
/*     */   }
/*     */   
/*     */   public TileEntity a(World paramWorld, int paramInt)
/*     */   {
/*  76 */     return new TileEntityBanner();
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  82 */     return Items.BANNER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void dropNaturally(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, float paramFloat, int paramInt)
/*     */   {
/*  92 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/*  93 */     if ((localTileEntity instanceof TileEntityBanner)) {
/*  94 */       ItemStack localItemStack = new ItemStack(Items.BANNER, 1, ((TileEntityBanner)localTileEntity).b());
/*     */       
/*  96 */       NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/*  97 */       localTileEntity.b(localNBTTagCompound);
/*  98 */       localNBTTagCompound.remove("x");
/*  99 */       localNBTTagCompound.remove("y");
/* 100 */       localNBTTagCompound.remove("z");
/* 101 */       localNBTTagCompound.remove("id");
/* 102 */       localItemStack.a("BlockEntityTag", localNBTTagCompound);
/*     */       
/* 104 */       a(paramWorld, paramBlockPosition, localItemStack);
/*     */     } else {
/* 106 */       super.dropNaturally(paramWorld, paramBlockPosition, paramIBlockData, paramFloat, paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/* 112 */     return (!e(paramWorld, paramBlockPosition)) && (super.canPlace(paramWorld, paramBlockPosition));
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, EntityHuman paramEntityHuman, BlockPosition paramBlockPosition, IBlockData paramIBlockData, TileEntity paramTileEntity)
/*     */   {
/* 117 */     if ((paramTileEntity instanceof TileEntityBanner)) {
/* 118 */       TileEntityBanner localTileEntityBanner = (TileEntityBanner)paramTileEntity;
/* 119 */       ItemStack localItemStack = new ItemStack(Items.BANNER, 1, ((TileEntityBanner)paramTileEntity).b());
/*     */       
/* 121 */       NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/* 122 */       TileEntityBanner.a(localNBTTagCompound, localTileEntityBanner.b(), localTileEntityBanner.d());
/* 123 */       localItemStack.a("BlockEntityTag", localNBTTagCompound);
/*     */       
/* 125 */       a(paramWorld, paramBlockPosition, localItemStack);
/*     */     } else {
/* 127 */       super.a(paramWorld, paramEntityHuman, paramBlockPosition, paramIBlockData, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BlockWallBanner extends BlockBanner {
/*     */     public BlockWallBanner() {
/* 133 */       j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
/*     */     }
/*     */     
/*     */     public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */     {
/* 138 */       EnumDirection localEnumDirection = (EnumDirection)paramIBlockAccess.getType(paramBlockPosition).get(FACING);
/*     */       
/* 140 */       float f1 = 0.0F;
/* 141 */       float f2 = 0.78125F;
/* 142 */       float f3 = 0.0F;
/* 143 */       float f4 = 1.0F;
/*     */       
/* 145 */       float f5 = 0.125F;
/*     */       
/* 147 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 149 */       switch (BlockBanner.1.a[localEnumDirection.ordinal()]) {
/*     */       case 1: 
/*     */       default: 
/* 152 */         a(f3, f1, 1.0F - f5, f4, f2, 1.0F);
/* 153 */         break;
/*     */       case 2: 
/* 155 */         a(f3, f1, 0.0F, f4, f2, f5);
/* 156 */         break;
/*     */       case 3: 
/* 158 */         a(1.0F - f5, f1, f3, 1.0F, f2, f4);
/* 159 */         break;
/*     */       case 4: 
/* 161 */         a(0.0F, f1, f3, f5, f2, f4);
/*     */       }
/*     */       
/*     */     }
/*     */     
/*     */     public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*     */     {
/* 168 */       EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/*     */       
/* 170 */       if (!paramWorld.getType(paramBlockPosition.shift(localEnumDirection.opposite())).getBlock().getMaterial().isBuildable()) {
/* 171 */         b(paramWorld, paramBlockPosition, paramIBlockData, 0);
/* 172 */         paramWorld.setAir(paramBlockPosition);
/*     */       }
/*     */       
/* 175 */       super.doPhysics(paramWorld, paramBlockPosition, paramIBlockData, paramBlock);
/*     */     }
/*     */     
/*     */     public IBlockData fromLegacyData(int paramInt)
/*     */     {
/* 180 */       EnumDirection localEnumDirection = EnumDirection.fromType1(paramInt);
/* 181 */       if (localEnumDirection.k() == EnumDirection.EnumAxis.Y) {
/* 182 */         localEnumDirection = EnumDirection.NORTH;
/*     */       }
/* 184 */       return getBlockData().set(FACING, localEnumDirection);
/*     */     }
/*     */     
/*     */     public int toLegacyData(IBlockData paramIBlockData)
/*     */     {
/* 189 */       return ((EnumDirection)paramIBlockData.get(FACING)).a();
/*     */     }
/*     */     
/*     */     protected BlockStateList getStateList()
/*     */     {
/* 194 */       return new BlockStateList(this, new IBlockState[] { FACING });
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BlockStandingBanner extends BlockBanner {
/*     */     public BlockStandingBanner() {
/* 200 */       j(this.blockStateList.getBlockData().set(ROTATION, Integer.valueOf(0)));
/*     */     }
/*     */     
/*     */     public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*     */     {
/* 205 */       if (!paramWorld.getType(paramBlockPosition.down()).getBlock().getMaterial().isBuildable()) {
/* 206 */         b(paramWorld, paramBlockPosition, paramIBlockData, 0);
/* 207 */         paramWorld.setAir(paramBlockPosition);
/*     */       }
/*     */       
/* 210 */       super.doPhysics(paramWorld, paramBlockPosition, paramIBlockData, paramBlock);
/*     */     }
/*     */     
/*     */     public IBlockData fromLegacyData(int paramInt)
/*     */     {
/* 215 */       return getBlockData().set(ROTATION, Integer.valueOf(paramInt));
/*     */     }
/*     */     
/*     */     public int toLegacyData(IBlockData paramIBlockData)
/*     */     {
/* 220 */       return ((Integer)paramIBlockData.get(ROTATION)).intValue();
/*     */     }
/*     */     
/*     */     protected BlockStateList getStateList()
/*     */     {
/* 225 */       return new BlockStateList(this, new IBlockState[] { ROTATION });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockBanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */