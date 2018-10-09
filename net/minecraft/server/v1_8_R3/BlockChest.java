/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
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
/*     */ public class BlockChest
/*     */   extends BlockContainer
/*     */ {
/*  33 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*     */   
/*     */ 
/*     */   public final int b;
/*     */   
/*     */ 
/*     */ 
/*     */   protected BlockChest(int paramInt)
/*     */   {
/*  42 */     super(Material.WOOD);
/*  43 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
/*  44 */     this.b = paramInt;
/*  45 */     a(CreativeModeTab.c);
/*  46 */     a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  56 */     return false;
/*     */   }
/*     */   
/*     */   public int b()
/*     */   {
/*  61 */     return 2;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  66 */     if (paramIBlockAccess.getType(paramBlockPosition.north()).getBlock() == this) {
/*  67 */       a(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
/*  68 */     } else if (paramIBlockAccess.getType(paramBlockPosition.south()).getBlock() == this) {
/*  69 */       a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
/*  70 */     } else if (paramIBlockAccess.getType(paramBlockPosition.west()).getBlock() == this) {
/*  71 */       a(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*  72 */     } else if (paramIBlockAccess.getType(paramBlockPosition.east()).getBlock() == this) {
/*  73 */       a(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
/*     */     } else {
/*  75 */       a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  81 */     e(paramWorld, paramBlockPosition, paramIBlockData);
/*     */     
/*  83 */     for (EnumDirection localEnumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
/*  84 */       BlockPosition localBlockPosition = paramBlockPosition.shift(localEnumDirection);
/*  85 */       IBlockData localIBlockData = paramWorld.getType(localBlockPosition);
/*  86 */       if (localIBlockData.getBlock() == this) {
/*  87 */         e(paramWorld, localBlockPosition, localIBlockData);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*     */   {
/*  94 */     return getBlockData().set(FACING, paramEntityLiving.getDirection());
/*     */   }
/*     */   
/*     */   public void postPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityLiving paramEntityLiving, ItemStack paramItemStack)
/*     */   {
/*  99 */     EnumDirection localEnumDirection = EnumDirection.fromType2(MathHelper.floor(paramEntityLiving.yaw * 4.0F / 360.0F + 0.5D) & 0x3).opposite();
/* 100 */     paramIBlockData = paramIBlockData.set(FACING, localEnumDirection);
/*     */     
/* 102 */     BlockPosition localBlockPosition1 = paramBlockPosition.north();
/* 103 */     BlockPosition localBlockPosition2 = paramBlockPosition.south();
/* 104 */     BlockPosition localBlockPosition3 = paramBlockPosition.west();
/* 105 */     BlockPosition localBlockPosition4 = paramBlockPosition.east();
/*     */     
/* 107 */     int i = this == paramWorld.getType(localBlockPosition1).getBlock() ? 1 : 0;
/* 108 */     int j = this == paramWorld.getType(localBlockPosition2).getBlock() ? 1 : 0;
/* 109 */     int k = this == paramWorld.getType(localBlockPosition3).getBlock() ? 1 : 0;
/* 110 */     int m = this == paramWorld.getType(localBlockPosition4).getBlock() ? 1 : 0;
/*     */     
/* 112 */     if ((i != 0) || (j != 0) || (k != 0) || (m != 0)) {
/* 113 */       if ((localEnumDirection.k() == EnumDirection.EnumAxis.X) && ((i != 0) || (j != 0))) {
/* 114 */         if (i != 0) {
/* 115 */           paramWorld.setTypeAndData(localBlockPosition1, paramIBlockData, 3);
/*     */         } else {
/* 117 */           paramWorld.setTypeAndData(localBlockPosition2, paramIBlockData, 3);
/*     */         }
/* 119 */         paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData, 3);
/* 120 */       } else if ((localEnumDirection.k() == EnumDirection.EnumAxis.Z) && ((k != 0) || (m != 0))) {
/* 121 */         if (k != 0) {
/* 122 */           paramWorld.setTypeAndData(localBlockPosition3, paramIBlockData, 3);
/*     */         } else {
/* 124 */           paramWorld.setTypeAndData(localBlockPosition4, paramIBlockData, 3);
/*     */         }
/* 126 */         paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData, 3);
/*     */       }
/*     */     } else {
/* 129 */       paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData, 3);
/*     */     }
/*     */     
/* 132 */     if (paramItemStack.hasName()) {
/* 133 */       TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 134 */       if ((localTileEntity instanceof TileEntityChest)) {
/* 135 */         ((TileEntityChest)localTileEntity).a(paramItemStack.getName());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockData e(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 142 */     if (paramWorld.isClientSide) {
/* 143 */       return paramIBlockData;
/*     */     }
/*     */     
/* 146 */     IBlockData localIBlockData1 = paramWorld.getType(paramBlockPosition.north());
/* 147 */     IBlockData localIBlockData2 = paramWorld.getType(paramBlockPosition.south());
/* 148 */     IBlockData localIBlockData3 = paramWorld.getType(paramBlockPosition.west());
/* 149 */     IBlockData localIBlockData4 = paramWorld.getType(paramBlockPosition.east());
/*     */     
/*     */ 
/* 152 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/*     */     
/* 154 */     Block localBlock1 = localIBlockData1.getBlock();
/* 155 */     Block localBlock2 = localIBlockData2.getBlock();
/* 156 */     Block localBlock3 = localIBlockData3.getBlock();
/* 157 */     Block localBlock4 = localIBlockData4.getBlock();
/* 158 */     Object localObject1; Object localObject2; Object localObject3; Object localObject4; if ((localBlock1 == this) || (localBlock2 == this)) {
/* 159 */       BlockPosition localBlockPosition = localBlock1 == this ? paramBlockPosition.north() : paramBlockPosition.south();
/* 160 */       IBlockData localIBlockData5 = paramWorld.getType(localBlockPosition.west());
/* 161 */       localObject1 = paramWorld.getType(localBlockPosition.east());
/*     */       
/* 163 */       localEnumDirection = EnumDirection.EAST;
/*     */       
/*     */ 
/* 166 */       if (localBlock1 == this) {
/* 167 */         localObject2 = (EnumDirection)localIBlockData1.get(FACING);
/*     */       } else {
/* 169 */         localObject2 = (EnumDirection)localIBlockData2.get(FACING);
/*     */       }
/* 171 */       if (localObject2 == EnumDirection.WEST) {
/* 172 */         localEnumDirection = EnumDirection.WEST;
/*     */       }
/*     */       
/* 175 */       localObject3 = localIBlockData5.getBlock();
/* 176 */       localObject4 = ((IBlockData)localObject1).getBlock();
/* 177 */       if (((localBlock3.o()) || (((Block)localObject3).o())) && (!localBlock4.o()) && (!((Block)localObject4).o())) {
/* 178 */         localEnumDirection = EnumDirection.EAST;
/*     */       }
/* 180 */       if (((localBlock4.o()) || (((Block)localObject4).o())) && (!localBlock3.o()) && (!((Block)localObject3).o())) {
/* 181 */         localEnumDirection = EnumDirection.WEST;
/*     */       }
/*     */     } else {
/* 184 */       boolean bool1 = localBlock1.o();
/* 185 */       boolean bool2 = localBlock2.o();
/* 186 */       if ((localBlock3 == this) || (localBlock4 == this)) {
/* 187 */         localObject1 = localBlock3 == this ? paramBlockPosition.west() : paramBlockPosition.east();
/* 188 */         localObject2 = paramWorld.getType(((BlockPosition)localObject1).north());
/* 189 */         localObject3 = paramWorld.getType(((BlockPosition)localObject1).south());
/*     */         
/* 191 */         localEnumDirection = EnumDirection.SOUTH;
/*     */         
/* 193 */         if (localBlock3 == this) {
/* 194 */           localObject4 = (EnumDirection)localIBlockData3.get(FACING);
/*     */         } else {
/* 196 */           localObject4 = (EnumDirection)localIBlockData4.get(FACING);
/*     */         }
/* 198 */         if (localObject4 == EnumDirection.NORTH) {
/* 199 */           localEnumDirection = EnumDirection.NORTH;
/*     */         }
/*     */         
/* 202 */         Block localBlock5 = ((IBlockData)localObject2).getBlock();
/* 203 */         Block localBlock6 = ((IBlockData)localObject3).getBlock();
/* 204 */         if (((bool1) || (localBlock5.o())) && (!bool2) && (!localBlock6.o())) {
/* 205 */           localEnumDirection = EnumDirection.SOUTH;
/*     */         }
/* 207 */         if (((bool2) || (localBlock6.o())) && (!bool1) && (!localBlock5.o())) {
/* 208 */           localEnumDirection = EnumDirection.NORTH;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 213 */     paramIBlockData = paramIBlockData.set(FACING, localEnumDirection);
/* 214 */     paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData, 3);
/* 215 */     return paramIBlockData;
/*     */   }
/*     */   
/*     */   public IBlockData f(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 220 */     Object localObject1 = null;
/* 221 */     for (Object localObject2 = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator(); ((Iterator)localObject2).hasNext();) { EnumDirection localEnumDirection = (EnumDirection)((Iterator)localObject2).next();
/* 222 */       IBlockData localIBlockData = paramWorld.getType(paramBlockPosition.shift(localEnumDirection));
/* 223 */       if (localIBlockData.getBlock() == this) {
/* 224 */         return paramIBlockData;
/*     */       }
/* 226 */       if (localIBlockData.getBlock().o()) {
/* 227 */         if (localObject1 == null) {
/* 228 */           localObject1 = localEnumDirection;
/*     */         } else {
/* 230 */           localObject1 = null;
/* 231 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 235 */     if (localObject1 != null) {
/* 236 */       return paramIBlockData.set(FACING, ((EnumDirection)localObject1).opposite());
/*     */     }
/*     */     
/*     */ 
/* 240 */     localObject2 = (EnumDirection)paramIBlockData.get(FACING);
/* 241 */     if (paramWorld.getType(paramBlockPosition.shift((EnumDirection)localObject2)).getBlock().o()) {
/* 242 */       localObject2 = ((EnumDirection)localObject2).opposite();
/*     */     }
/* 244 */     if (paramWorld.getType(paramBlockPosition.shift((EnumDirection)localObject2)).getBlock().o()) {
/* 245 */       localObject2 = ((EnumDirection)localObject2).e();
/*     */     }
/* 247 */     if (paramWorld.getType(paramBlockPosition.shift((EnumDirection)localObject2)).getBlock().o()) {
/* 248 */       localObject2 = ((EnumDirection)localObject2).opposite();
/*     */     }
/*     */     
/* 251 */     return paramIBlockData.set(FACING, (Comparable)localObject2);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/* 257 */     int i = 0;
/*     */     
/* 259 */     BlockPosition localBlockPosition1 = paramBlockPosition.west();
/* 260 */     BlockPosition localBlockPosition2 = paramBlockPosition.east();
/* 261 */     BlockPosition localBlockPosition3 = paramBlockPosition.north();
/* 262 */     BlockPosition localBlockPosition4 = paramBlockPosition.south();
/*     */     
/* 264 */     if (paramWorld.getType(localBlockPosition1).getBlock() == this) {
/* 265 */       if (m(paramWorld, localBlockPosition1)) {
/* 266 */         return false;
/*     */       }
/* 268 */       i++;
/*     */     }
/* 270 */     if (paramWorld.getType(localBlockPosition2).getBlock() == this) {
/* 271 */       if (m(paramWorld, localBlockPosition2)) {
/* 272 */         return false;
/*     */       }
/* 274 */       i++;
/*     */     }
/* 276 */     if (paramWorld.getType(localBlockPosition3).getBlock() == this) {
/* 277 */       if (m(paramWorld, localBlockPosition3)) {
/* 278 */         return false;
/*     */       }
/* 280 */       i++;
/*     */     }
/* 282 */     if (paramWorld.getType(localBlockPosition4).getBlock() == this) {
/* 283 */       if (m(paramWorld, localBlockPosition4)) {
/* 284 */         return false;
/*     */       }
/* 286 */       i++;
/*     */     }
/*     */     
/* 289 */     if (i > 1) {
/* 290 */       return false;
/*     */     }
/*     */     
/* 293 */     return true;
/*     */   }
/*     */   
/*     */   private boolean m(World paramWorld, BlockPosition paramBlockPosition) {
/* 297 */     if (paramWorld.getType(paramBlockPosition).getBlock() != this) {
/* 298 */       return false;
/*     */     }
/*     */     
/* 301 */     for (EnumDirection localEnumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
/* 302 */       if (paramWorld.getType(paramBlockPosition.shift(localEnumDirection)).getBlock() == this) {
/* 303 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 307 */     return false;
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*     */   {
/* 312 */     super.doPhysics(paramWorld, paramBlockPosition, paramIBlockData, paramBlock);
/*     */     
/* 314 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 315 */     if ((localTileEntity instanceof TileEntityChest)) {
/* 316 */       localTileEntity.E();
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 322 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 323 */     if ((localTileEntity instanceof IInventory)) {
/* 324 */       InventoryUtils.dropInventory(paramWorld, paramBlockPosition, (IInventory)localTileEntity);
/*     */       
/* 326 */       paramWorld.updateAdjacentComparators(paramBlockPosition, this);
/*     */     }
/* 328 */     super.remove(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 333 */     if (paramWorld.isClientSide) {
/* 334 */       return true;
/*     */     }
/* 336 */     ITileInventory localITileInventory = f(paramWorld, paramBlockPosition);
/*     */     
/* 338 */     if (localITileInventory != null) {
/* 339 */       paramEntityHuman.openContainer(localITileInventory);
/*     */       
/* 341 */       if (this.b == 0) {
/* 342 */         paramEntityHuman.b(StatisticList.aa);
/* 343 */       } else if (this.b == 1) {
/* 344 */         paramEntityHuman.b(StatisticList.U);
/*     */       }
/*     */     }
/*     */     
/* 348 */     return true;
/*     */   }
/*     */   
/*     */   public ITileInventory f(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/* 353 */     TileEntity localTileEntity1 = paramWorld.getTileEntity(paramBlockPosition);
/* 354 */     if (!(localTileEntity1 instanceof TileEntityChest)) {
/* 355 */       return null;
/*     */     }
/*     */     
/* 358 */     Object localObject = (TileEntityChest)localTileEntity1;
/*     */     
/* 360 */     if (n(paramWorld, paramBlockPosition)) {
/* 361 */       return null;
/*     */     }
/*     */     
/* 364 */     for (EnumDirection localEnumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
/* 365 */       BlockPosition localBlockPosition = paramBlockPosition.shift(localEnumDirection);
/* 366 */       Block localBlock = paramWorld.getType(localBlockPosition).getBlock();
/*     */       
/* 368 */       if (localBlock == this) {
/* 369 */         if (n(paramWorld, localBlockPosition)) {
/* 370 */           return null;
/*     */         }
/*     */         
/* 373 */         TileEntity localTileEntity2 = paramWorld.getTileEntity(localBlockPosition);
/* 374 */         if ((localTileEntity2 instanceof TileEntityChest))
/*     */         {
/* 376 */           if ((localEnumDirection == EnumDirection.WEST) || (localEnumDirection == EnumDirection.NORTH)) {
/* 377 */             localObject = new InventoryLargeChest("container.chestDouble", (TileEntityChest)localTileEntity2, (ITileInventory)localObject);
/*     */           } else {
/* 379 */             localObject = new InventoryLargeChest("container.chestDouble", (ITileInventory)localObject, (TileEntityChest)localTileEntity2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 384 */     return (ITileInventory)localObject;
/*     */   }
/*     */   
/*     */   public TileEntity a(World paramWorld, int paramInt)
/*     */   {
/* 389 */     return new TileEntityChest();
/*     */   }
/*     */   
/*     */   public boolean isPowerSource()
/*     */   {
/* 394 */     return this.b == 1;
/*     */   }
/*     */   
/*     */   public int a(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EnumDirection paramEnumDirection)
/*     */   {
/* 399 */     if (!isPowerSource()) {
/* 400 */       return 0;
/*     */     }
/*     */     
/* 403 */     int i = 0;
/* 404 */     TileEntity localTileEntity = paramIBlockAccess.getTileEntity(paramBlockPosition);
/* 405 */     if ((localTileEntity instanceof TileEntityChest)) {
/* 406 */       i = ((TileEntityChest)localTileEntity).l;
/*     */     }
/* 408 */     return MathHelper.clamp(i, 0, 15);
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EnumDirection paramEnumDirection)
/*     */   {
/* 413 */     if (paramEnumDirection == EnumDirection.UP) {
/* 414 */       return a(paramIBlockAccess, paramBlockPosition, paramIBlockData, paramEnumDirection);
/*     */     }
/* 416 */     return 0;
/*     */   }
/*     */   
/*     */   private boolean n(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/* 421 */     return (o(paramWorld, paramBlockPosition)) || (p(paramWorld, paramBlockPosition));
/*     */   }
/*     */   
/*     */   private boolean o(World paramWorld, BlockPosition paramBlockPosition) {
/* 425 */     return paramWorld.getType(paramBlockPosition.up()).getBlock().isOccluding();
/*     */   }
/*     */   
/*     */   private boolean p(World paramWorld, BlockPosition paramBlockPosition) {
/* 429 */     for (Entity localEntity : paramWorld.a(EntityOcelot.class, new AxisAlignedBB(paramBlockPosition.getX(), paramBlockPosition.getY() + 1, paramBlockPosition.getZ(), paramBlockPosition.getX() + 1, paramBlockPosition.getY() + 2, paramBlockPosition.getZ() + 1))) {
/* 430 */       EntityOcelot localEntityOcelot = (EntityOcelot)localEntity;
/* 431 */       if (localEntityOcelot.isSitting()) {
/* 432 */         return true;
/*     */       }
/*     */     }
/* 435 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone()
/*     */   {
/* 440 */     return true;
/*     */   }
/*     */   
/*     */   public int l(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/* 445 */     return Container.b(f(paramWorld, paramBlockPosition));
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 450 */     EnumDirection localEnumDirection = EnumDirection.fromType1(paramInt);
/* 451 */     if (localEnumDirection.k() == EnumDirection.EnumAxis.Y) {
/* 452 */       localEnumDirection = EnumDirection.NORTH;
/*     */     }
/* 454 */     return getBlockData().set(FACING, localEnumDirection);
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 460 */     return ((EnumDirection)paramIBlockData.get(FACING)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 465 */     return new BlockStateList(this, new IBlockState[] { FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */