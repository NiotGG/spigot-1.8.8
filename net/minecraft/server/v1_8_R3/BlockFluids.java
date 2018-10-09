/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
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
/*     */ public abstract class BlockFluids
/*     */   extends Block
/*     */ {
/*  24 */   public static final BlockStateInteger LEVEL = BlockStateInteger.of("level", 0, 15);
/*     */   
/*     */   protected BlockFluids(Material paramMaterial) {
/*  27 */     super(paramMaterial);
/*  28 */     j(this.blockStateList.getBlockData().set(LEVEL, Integer.valueOf(0)));
/*  29 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  30 */     a(true);
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  35 */     return this.material != Material.LAVA;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static float b(int paramInt)
/*     */   {
/*  47 */     if (paramInt >= 8) {
/*  48 */       paramInt = 0;
/*     */     }
/*  50 */     return (paramInt + 1) / 9.0F;
/*     */   }
/*     */   
/*     */   protected int e(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition) {
/*  54 */     if (paramIBlockAccess.getType(paramBlockPosition).getBlock().getMaterial() == this.material) {
/*  55 */       return ((Integer)paramIBlockAccess.getType(paramBlockPosition).get(LEVEL)).intValue();
/*     */     }
/*     */     
/*  58 */     return -1;
/*     */   }
/*     */   
/*     */   protected int f(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition) {
/*  62 */     int i = e(paramIBlockAccess, paramBlockPosition);
/*     */     
/*  64 */     return i >= 8 ? 0 : i;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  69 */     return false;
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  74 */     return false;
/*     */   }
/*     */   
/*     */   public boolean a(IBlockData paramIBlockData, boolean paramBoolean)
/*     */   {
/*  79 */     return (paramBoolean) && (((Integer)paramIBlockData.get(LEVEL)).intValue() == 0);
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection)
/*     */   {
/*  84 */     Material localMaterial = paramIBlockAccess.getType(paramBlockPosition).getBlock().getMaterial();
/*  85 */     if (localMaterial == this.material) {
/*  86 */       return false;
/*     */     }
/*  88 */     if (paramEnumDirection == EnumDirection.UP) {
/*  89 */       return true;
/*     */     }
/*  91 */     if (localMaterial == Material.ICE) {
/*  92 */       return false;
/*     */     }
/*  94 */     return super.b(paramIBlockAccess, paramBlockPosition, paramEnumDirection);
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
/*     */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 126 */     return null;
/*     */   }
/*     */   
/*     */   public int b()
/*     */   {
/* 131 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/* 137 */     return null;
/*     */   }
/*     */   
/*     */   public int a(Random paramRandom)
/*     */   {
/* 142 */     return 0;
/*     */   }
/*     */   
/*     */   protected Vec3D h(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition) {
/* 146 */     Vec3D localVec3D = new Vec3D(0.0D, 0.0D, 0.0D);
/* 147 */     int i = f(paramIBlockAccess, paramBlockPosition);
/* 148 */     for (Iterator localIterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator(); localIterator.hasNext();) { localEnumDirection = (EnumDirection)localIterator.next();
/* 149 */       localBlockPosition = paramBlockPosition.shift(localEnumDirection);
/*     */       
/* 151 */       int j = f(paramIBlockAccess, localBlockPosition);
/* 152 */       int k; if (j < 0) {
/* 153 */         if (!paramIBlockAccess.getType(localBlockPosition).getBlock().getMaterial().isSolid()) {
/* 154 */           j = f(paramIBlockAccess, localBlockPosition.down());
/* 155 */           if (j >= 0) {
/* 156 */             k = j - (i - 8);
/* 157 */             localVec3D = localVec3D.add((localBlockPosition.getX() - paramBlockPosition.getX()) * k, (localBlockPosition.getY() - paramBlockPosition.getY()) * k, (localBlockPosition.getZ() - paramBlockPosition.getZ()) * k);
/*     */           }
/*     */         }
/* 160 */       } else if (j >= 0) {
/* 161 */         k = j - i;
/* 162 */         localVec3D = localVec3D.add((localBlockPosition.getX() - paramBlockPosition.getX()) * k, (localBlockPosition.getY() - paramBlockPosition.getY()) * k, (localBlockPosition.getZ() - paramBlockPosition.getZ()) * k); } }
/*     */     EnumDirection localEnumDirection;
/*     */     BlockPosition localBlockPosition;
/* 165 */     if (((Integer)paramIBlockAccess.getType(paramBlockPosition).get(LEVEL)).intValue() >= 8) {
/* 166 */       for (localIterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator(); localIterator.hasNext();) { localEnumDirection = (EnumDirection)localIterator.next();
/* 167 */         localBlockPosition = paramBlockPosition.shift(localEnumDirection);
/* 168 */         if ((b(paramIBlockAccess, localBlockPosition, localEnumDirection)) || (b(paramIBlockAccess, localBlockPosition.up(), localEnumDirection))) {
/* 169 */           localVec3D = localVec3D.a().add(0.0D, -6.0D, 0.0D);
/* 170 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 174 */     return localVec3D.a();
/*     */   }
/*     */   
/*     */   public Vec3D a(World paramWorld, BlockPosition paramBlockPosition, Entity paramEntity, Vec3D paramVec3D)
/*     */   {
/* 179 */     return paramVec3D.e(h(paramWorld, paramBlockPosition));
/*     */   }
/*     */   
/*     */   public int a(World paramWorld)
/*     */   {
/* 184 */     if (this.material == Material.WATER) {
/* 185 */       return 5;
/*     */     }
/* 187 */     if (this.material == Material.LAVA) {
/* 188 */       return paramWorld.worldProvider.o() ? 10 : 30;
/*     */     }
/* 190 */     return 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 270 */     e(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*     */   {
/* 275 */     e(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   public boolean e(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData) {
/* 279 */     if (this.material == Material.LAVA) {
/* 280 */       int i = 0;
/* 281 */       for (EnumDirection localEnumDirection : EnumDirection.values()) {
/* 282 */         if ((localEnumDirection != EnumDirection.DOWN) && (paramWorld.getType(paramBlockPosition.shift(localEnumDirection)).getBlock().getMaterial() == Material.WATER)) {
/* 283 */           i = 1;
/* 284 */           break;
/*     */         }
/*     */       }
/* 287 */       if (i != 0) {
/* 288 */         ??? = (Integer)paramIBlockData.get(LEVEL);
/* 289 */         if (((Integer)???).intValue() == 0) {
/* 290 */           paramWorld.setTypeUpdate(paramBlockPosition, Blocks.OBSIDIAN.getBlockData());
/* 291 */           fizz(paramWorld, paramBlockPosition);
/* 292 */           return true; }
/* 293 */         if (((Integer)???).intValue() <= 4) {
/* 294 */           paramWorld.setTypeUpdate(paramBlockPosition, Blocks.COBBLESTONE.getBlockData());
/* 295 */           fizz(paramWorld, paramBlockPosition);
/* 296 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 301 */     return false;
/*     */   }
/*     */   
/*     */   protected void fizz(World paramWorld, BlockPosition paramBlockPosition) {
/* 305 */     double d1 = paramBlockPosition.getX();
/* 306 */     double d2 = paramBlockPosition.getY();
/* 307 */     double d3 = paramBlockPosition.getZ();
/*     */     
/* 309 */     paramWorld.makeSound(d1 + 0.5D, d2 + 0.5D, d3 + 0.5D, "random.fizz", 0.5F, 2.6F + (paramWorld.random.nextFloat() - paramWorld.random.nextFloat()) * 0.8F);
/* 310 */     for (int i = 0; i < 8; i++) {
/* 311 */       paramWorld.addParticle(EnumParticle.SMOKE_LARGE, d1 + Math.random(), d2 + 1.2D, d3 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 317 */     return getBlockData().set(LEVEL, Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 322 */     return ((Integer)paramIBlockData.get(LEVEL)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 327 */     return new BlockStateList(this, new IBlockState[] { LEVEL });
/*     */   }
/*     */   
/*     */   public static BlockFlowing a(Material paramMaterial) {
/* 331 */     if (paramMaterial == Material.WATER)
/* 332 */       return Blocks.FLOWING_WATER;
/* 333 */     if (paramMaterial == Material.LAVA) {
/* 334 */       return Blocks.FLOWING_LAVA;
/*     */     }
/*     */     
/* 337 */     throw new IllegalArgumentException("Invalid material");
/*     */   }
/*     */   
/*     */   public static BlockStationary b(Material paramMaterial) {
/* 341 */     if (paramMaterial == Material.WATER)
/* 342 */       return Blocks.WATER;
/* 343 */     if (paramMaterial == Material.LAVA) {
/* 344 */       return Blocks.LAVA;
/*     */     }
/*     */     
/* 347 */     throw new IllegalArgumentException("Invalid material");
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockFluids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */