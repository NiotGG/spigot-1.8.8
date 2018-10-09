/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockRedstoneComparator
/*     */   extends BlockDiodeAbstract
/*     */   implements IContainer
/*     */ {
/*  31 */   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
/*  32 */   public static final BlockStateEnum<EnumComparatorMode> MODE = BlockStateEnum.of("mode", EnumComparatorMode.class);
/*     */   
/*     */   public BlockRedstoneComparator(boolean paramBoolean) {
/*  35 */     super(paramBoolean);
/*  36 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(POWERED, Boolean.valueOf(false)).set(MODE, EnumComparatorMode.COMPARE));
/*  37 */     this.isTileEntity = true;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  42 */     return LocaleI18n.get("item.comparator.name");
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  48 */     return Items.COMPARATOR;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int d(IBlockData paramIBlockData)
/*     */   {
/*  58 */     return 2;
/*     */   }
/*     */   
/*     */   protected IBlockData e(IBlockData paramIBlockData)
/*     */   {
/*  63 */     Boolean localBoolean = (Boolean)paramIBlockData.get(POWERED);
/*  64 */     EnumComparatorMode localEnumComparatorMode = (EnumComparatorMode)paramIBlockData.get(MODE);
/*  65 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/*     */     
/*  67 */     return Blocks.POWERED_COMPARATOR.getBlockData().set(FACING, localEnumDirection).set(POWERED, localBoolean).set(MODE, localEnumComparatorMode);
/*     */   }
/*     */   
/*     */   protected IBlockData k(IBlockData paramIBlockData)
/*     */   {
/*  72 */     Boolean localBoolean = (Boolean)paramIBlockData.get(POWERED);
/*  73 */     EnumComparatorMode localEnumComparatorMode = (EnumComparatorMode)paramIBlockData.get(MODE);
/*  74 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/*     */     
/*  76 */     return Blocks.UNPOWERED_COMPARATOR.getBlockData().set(FACING, localEnumDirection).set(POWERED, localBoolean).set(MODE, localEnumComparatorMode);
/*     */   }
/*     */   
/*     */   protected boolean l(IBlockData paramIBlockData)
/*     */   {
/*  81 */     return (this.N) || (((Boolean)paramIBlockData.get(POWERED)).booleanValue());
/*     */   }
/*     */   
/*     */   protected int a(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  86 */     TileEntity localTileEntity = paramIBlockAccess.getTileEntity(paramBlockPosition);
/*  87 */     if ((localTileEntity instanceof TileEntityComparator)) {
/*  88 */       return ((TileEntityComparator)localTileEntity).b();
/*     */     }
/*     */     
/*  91 */     return 0;
/*     */   }
/*     */   
/*     */   private int j(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData) {
/*  95 */     if (paramIBlockData.get(MODE) == EnumComparatorMode.SUBTRACT) {
/*  96 */       return Math.max(f(paramWorld, paramBlockPosition, paramIBlockData) - c(paramWorld, paramBlockPosition, paramIBlockData), 0);
/*     */     }
/*     */     
/*  99 */     return f(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   protected boolean e(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 104 */     int i = f(paramWorld, paramBlockPosition, paramIBlockData);
/* 105 */     if (i >= 15) {
/* 106 */       return true;
/*     */     }
/* 108 */     if (i == 0) {
/* 109 */       return false;
/*     */     }
/*     */     
/* 112 */     int j = c(paramWorld, paramBlockPosition, paramIBlockData);
/* 113 */     if (j == 0) {
/* 114 */       return true;
/*     */     }
/*     */     
/* 117 */     return i >= j;
/*     */   }
/*     */   
/*     */   protected int f(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 122 */     int i = super.f(paramWorld, paramBlockPosition, paramIBlockData);
/*     */     
/* 124 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/* 125 */     BlockPosition localBlockPosition = paramBlockPosition.shift(localEnumDirection);
/* 126 */     Block localBlock = paramWorld.getType(localBlockPosition).getBlock();
/*     */     
/* 128 */     if (localBlock.isComplexRedstone()) {
/* 129 */       i = localBlock.l(paramWorld, localBlockPosition);
/* 130 */     } else if ((i < 15) && (localBlock.isOccluding())) {
/* 131 */       localBlockPosition = localBlockPosition.shift(localEnumDirection);
/* 132 */       localBlock = paramWorld.getType(localBlockPosition).getBlock();
/*     */       
/* 134 */       if (localBlock.isComplexRedstone()) {
/* 135 */         i = localBlock.l(paramWorld, localBlockPosition);
/* 136 */       } else if (localBlock.getMaterial() == Material.AIR) {
/* 137 */         EntityItemFrame localEntityItemFrame = a(paramWorld, localEnumDirection, localBlockPosition);
/* 138 */         if (localEntityItemFrame != null) {
/* 139 */           i = localEntityItemFrame.q();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 144 */     return i;
/*     */   }
/*     */   
/*     */   private EntityItemFrame a(World paramWorld, final EnumDirection paramEnumDirection, BlockPosition paramBlockPosition)
/*     */   {
/* 149 */     List localList = paramWorld.a(EntityItemFrame.class, new AxisAlignedBB(paramBlockPosition.getX(), paramBlockPosition.getY(), paramBlockPosition.getZ(), paramBlockPosition.getX() + 1, paramBlockPosition.getY() + 1, paramBlockPosition.getZ() + 1), new Predicate()
/*     */     {
/*     */       public boolean a(Entity paramAnonymousEntity) {
/* 152 */         return (paramAnonymousEntity != null) && (paramAnonymousEntity.getDirection() == paramEnumDirection);
/*     */       }
/*     */     });
/*     */     
/* 156 */     if (localList.size() == 1) {
/* 157 */       return (EntityItemFrame)localList.get(0);
/*     */     }
/*     */     
/* 160 */     return null;
/*     */   }
/*     */   
/*     */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 165 */     if (!paramEntityHuman.abilities.mayBuild) {
/* 166 */       return false;
/*     */     }
/*     */     
/* 169 */     paramIBlockData = paramIBlockData.a(MODE);
/* 170 */     paramWorld.makeSound(paramBlockPosition.getX() + 0.5D, paramBlockPosition.getY() + 0.5D, paramBlockPosition.getZ() + 0.5D, "random.click", 0.3F, paramIBlockData.get(MODE) == EnumComparatorMode.SUBTRACT ? 0.55F : 0.5F);
/*     */     
/* 172 */     paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData, 2);
/* 173 */     k(paramWorld, paramBlockPosition, paramIBlockData);
/* 174 */     return true;
/*     */   }
/*     */   
/*     */   protected void g(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 179 */     if (paramWorld.a(paramBlockPosition, this)) {
/* 180 */       return;
/*     */     }
/*     */     
/* 183 */     int i = j(paramWorld, paramBlockPosition, paramIBlockData);
/* 184 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 185 */     int j = (localTileEntity instanceof TileEntityComparator) ? ((TileEntityComparator)localTileEntity).b() : 0;
/*     */     
/* 187 */     if ((i != j) || (l(paramIBlockData) != e(paramWorld, paramBlockPosition, paramIBlockData)))
/*     */     {
/* 189 */       if (i(paramWorld, paramBlockPosition, paramIBlockData)) {
/* 190 */         paramWorld.a(paramBlockPosition, this, 2, -1);
/*     */       } else {
/* 192 */         paramWorld.a(paramBlockPosition, this, 2, 0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void k(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData) {
/* 198 */     int i = j(paramWorld, paramBlockPosition, paramIBlockData);
/*     */     
/* 200 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 201 */     int j = 0;
/* 202 */     if ((localTileEntity instanceof TileEntityComparator)) {
/* 203 */       TileEntityComparator localTileEntityComparator = (TileEntityComparator)localTileEntity;
/*     */       
/* 205 */       j = localTileEntityComparator.b();
/* 206 */       localTileEntityComparator.a(i);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 211 */     if ((j != i) || (paramIBlockData.get(MODE) == EnumComparatorMode.COMPARE)) {
/* 212 */       boolean bool1 = e(paramWorld, paramBlockPosition, paramIBlockData);
/* 213 */       boolean bool2 = l(paramIBlockData);
/*     */       
/* 215 */       if ((bool2) && (!bool1)) {
/* 216 */         paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData.set(POWERED, Boolean.valueOf(false)), 2);
/* 217 */       } else if ((!bool2) && (bool1)) {
/* 218 */         paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData.set(POWERED, Boolean.valueOf(true)), 2);
/*     */       }
/*     */       
/* 221 */       h(paramWorld, paramBlockPosition, paramIBlockData);
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Random paramRandom)
/*     */   {
/* 227 */     if (this.N)
/*     */     {
/* 229 */       paramWorld.setTypeAndData(paramBlockPosition, k(paramIBlockData).set(POWERED, Boolean.valueOf(true)), 4);
/*     */     }
/* 231 */     k(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   public void onPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 236 */     super.onPlace(paramWorld, paramBlockPosition, paramIBlockData);
/* 237 */     paramWorld.setTileEntity(paramBlockPosition, a(paramWorld, 0));
/*     */   }
/*     */   
/*     */   public void remove(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 242 */     super.remove(paramWorld, paramBlockPosition, paramIBlockData);
/* 243 */     paramWorld.t(paramBlockPosition);
/*     */     
/* 245 */     h(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   public boolean a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, int paramInt1, int paramInt2)
/*     */   {
/* 250 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramInt1, paramInt2);
/*     */     
/* 252 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 253 */     if (localTileEntity == null) {
/* 254 */       return false;
/*     */     }
/*     */     
/* 257 */     return localTileEntity.c(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public TileEntity a(World paramWorld, int paramInt)
/*     */   {
/* 262 */     return new TileEntityComparator();
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 267 */     return getBlockData().set(FACING, EnumDirection.fromType2(paramInt)).set(POWERED, Boolean.valueOf((paramInt & 0x8) > 0)).set(MODE, (paramInt & 0x4) > 0 ? EnumComparatorMode.SUBTRACT : EnumComparatorMode.COMPARE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 275 */     int i = 0;
/*     */     
/* 277 */     i |= ((EnumDirection)paramIBlockData.get(FACING)).b();
/*     */     
/* 279 */     if (((Boolean)paramIBlockData.get(POWERED)).booleanValue()) {
/* 280 */       i |= 0x8;
/*     */     }
/*     */     
/* 283 */     if (paramIBlockData.get(MODE) == EnumComparatorMode.SUBTRACT) {
/* 284 */       i |= 0x4;
/*     */     }
/*     */     
/* 287 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 292 */     return new BlockStateList(this, new IBlockState[] { FACING, MODE, POWERED });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumComparatorMode
/*     */     implements INamable
/*     */   {
/*     */     private final String c;
/*     */     
/*     */     private EnumComparatorMode(String paramString)
/*     */     {
/* 303 */       this.c = paramString;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 308 */       return this.c;
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 313 */       return this.c;
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*     */   {
/* 319 */     return getBlockData().set(FACING, paramEntityLiving.getDirection().opposite()).set(POWERED, Boolean.valueOf(false)).set(MODE, EnumComparatorMode.COMPARE);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockRedstoneComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */