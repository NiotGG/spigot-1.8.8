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
/*     */ public class BlockBed
/*     */   extends BlockDirectional
/*     */ {
/*  25 */   public static final BlockStateEnum<EnumBedPart> PART = BlockStateEnum.of("part", EnumBedPart.class);
/*  26 */   public static final BlockStateBoolean OCCUPIED = BlockStateBoolean.of("occupied");
/*     */   
/*     */   public BlockBed() {
/*  29 */     super(Material.CLOTH);
/*  30 */     j(this.blockStateList.getBlockData().set(PART, EnumBedPart.FOOT).set(OCCUPIED, Boolean.valueOf(false)));
/*  31 */     l();
/*     */   }
/*     */   
/*     */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/*  36 */     if (paramWorld.isClientSide) {
/*  37 */       return true;
/*     */     }
/*     */     
/*  40 */     if (paramIBlockData.get(PART) != EnumBedPart.HEAD)
/*     */     {
/*  42 */       paramBlockPosition = paramBlockPosition.shift((EnumDirection)paramIBlockData.get(FACING));
/*  43 */       paramIBlockData = paramWorld.getType(paramBlockPosition);
/*  44 */       if (paramIBlockData.getBlock() != this) {
/*  45 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  49 */     if ((!paramWorld.worldProvider.e()) || (paramWorld.getBiome(paramBlockPosition) == BiomeBase.HELL))
/*     */     {
/*  51 */       paramWorld.setAir(paramBlockPosition);
/*     */       
/*     */ 
/*  54 */       localObject = paramBlockPosition.shift(((EnumDirection)paramIBlockData.get(FACING)).opposite());
/*  55 */       if (paramWorld.getType((BlockPosition)localObject).getBlock() == this) {
/*  56 */         paramWorld.setAir((BlockPosition)localObject);
/*     */       }
/*     */       
/*  59 */       paramWorld.createExplosion(null, paramBlockPosition.getX() + 0.5D, paramBlockPosition.getY() + 0.5D, paramBlockPosition.getZ() + 0.5D, 5.0F, true, true);
/*  60 */       return true;
/*     */     }
/*     */     
/*  63 */     if (((Boolean)paramIBlockData.get(OCCUPIED)).booleanValue()) {
/*  64 */       localObject = f(paramWorld, paramBlockPosition);
/*  65 */       if (localObject == null) {
/*  66 */         paramIBlockData = paramIBlockData.set(OCCUPIED, Boolean.valueOf(false));
/*  67 */         paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData, 4);
/*     */       } else {
/*  69 */         paramEntityHuman.b(new ChatMessage("tile.bed.occupied", new Object[0]));
/*  70 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  74 */     Object localObject = paramEntityHuman.a(paramBlockPosition);
/*  75 */     if (localObject == EntityHuman.EnumBedResult.OK) {
/*  76 */       paramIBlockData = paramIBlockData.set(OCCUPIED, Boolean.valueOf(true));
/*  77 */       paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData, 4);
/*  78 */       return true;
/*     */     }
/*     */     
/*  81 */     if (localObject == EntityHuman.EnumBedResult.NOT_POSSIBLE_NOW) {
/*  82 */       paramEntityHuman.b(new ChatMessage("tile.bed.noSleep", new Object[0]));
/*  83 */     } else if (localObject == EntityHuman.EnumBedResult.NOT_SAFE) {
/*  84 */       paramEntityHuman.b(new ChatMessage("tile.bed.notSafe", new Object[0]));
/*     */     }
/*  86 */     return true;
/*     */   }
/*     */   
/*     */   private EntityHuman f(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  91 */     for (EntityHuman localEntityHuman : paramWorld.players) {
/*  92 */       if ((localEntityHuman.isSleeping()) && (localEntityHuman.bx.equals(paramBlockPosition))) {
/*  93 */         return localEntityHuman;
/*     */       }
/*     */     }
/*     */     
/*  97 */     return null;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/* 107 */     return false;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/* 112 */     l();
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*     */   {
/* 117 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/*     */     
/* 119 */     if (paramIBlockData.get(PART) == EnumBedPart.HEAD) {
/* 120 */       if (paramWorld.getType(paramBlockPosition.shift(localEnumDirection.opposite())).getBlock() != this) {
/* 121 */         paramWorld.setAir(paramBlockPosition);
/*     */       }
/*     */     }
/* 124 */     else if (paramWorld.getType(paramBlockPosition.shift(localEnumDirection)).getBlock() != this) {
/* 125 */       paramWorld.setAir(paramBlockPosition);
/* 126 */       if (!paramWorld.isClientSide) {
/* 127 */         b(paramWorld, paramBlockPosition, paramIBlockData, 0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/* 136 */     if (paramIBlockData.get(PART) == EnumBedPart.HEAD) {
/* 137 */       return null;
/*     */     }
/* 139 */     return Items.BED;
/*     */   }
/*     */   
/*     */   private void l() {
/* 143 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
/*     */   }
/*     */   
/*     */   public static BlockPosition a(World paramWorld, BlockPosition paramBlockPosition, int paramInt)
/*     */   {
/* 148 */     EnumDirection localEnumDirection = (EnumDirection)paramWorld.getType(paramBlockPosition).get(FACING);
/*     */     
/*     */ 
/* 151 */     int i = paramBlockPosition.getX();
/* 152 */     int j = paramBlockPosition.getY();
/* 153 */     int k = paramBlockPosition.getZ();
/*     */     
/*     */ 
/* 156 */     for (int m = 0; m <= 1; m++) {
/* 157 */       int n = i - localEnumDirection.getAdjacentX() * m - 1;
/* 158 */       int i1 = k - localEnumDirection.getAdjacentZ() * m - 1;
/* 159 */       int i2 = n + 2;
/* 160 */       int i3 = i1 + 2;
/*     */       
/* 162 */       for (int i4 = n; i4 <= i2; i4++) {
/* 163 */         for (int i5 = i1; i5 <= i3; i5++) {
/* 164 */           BlockPosition localBlockPosition = new BlockPosition(i4, j, i5);
/* 165 */           if (e(paramWorld, localBlockPosition)) {
/* 166 */             if (paramInt > 0) {
/* 167 */               paramInt--;
/*     */             }
/*     */             else {
/* 170 */               return localBlockPosition;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 176 */     return null;
/*     */   }
/*     */   
/*     */   protected static boolean e(World paramWorld, BlockPosition paramBlockPosition) {
/* 180 */     return (World.a(paramWorld, paramBlockPosition.down())) && (!paramWorld.getType(paramBlockPosition).getBlock().getMaterial().isBuildable()) && (!paramWorld.getType(paramBlockPosition.up()).getBlock().getMaterial().isBuildable());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void dropNaturally(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, float paramFloat, int paramInt)
/*     */   {
/* 187 */     if (paramIBlockData.get(PART) == EnumBedPart.FOOT) {
/* 188 */       super.dropNaturally(paramWorld, paramBlockPosition, paramIBlockData, paramFloat, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public int k()
/*     */   {
/* 194 */     return 1;
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
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman)
/*     */   {
/* 211 */     if ((paramEntityHuman.abilities.canInstantlyBuild) && 
/* 212 */       (paramIBlockData.get(PART) == EnumBedPart.HEAD)) {
/* 213 */       BlockPosition localBlockPosition = paramBlockPosition.shift(((EnumDirection)paramIBlockData.get(FACING)).opposite());
/* 214 */       if (paramWorld.getType(localBlockPosition).getBlock() == this) {
/* 215 */         paramWorld.setAir(localBlockPosition);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 223 */     EnumDirection localEnumDirection = EnumDirection.fromType2(paramInt);
/* 224 */     if ((paramInt & 0x8) > 0) {
/* 225 */       return getBlockData().set(PART, EnumBedPart.HEAD).set(FACING, localEnumDirection).set(OCCUPIED, Boolean.valueOf((paramInt & 0x4) > 0));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 230 */     return getBlockData().set(PART, EnumBedPart.FOOT).set(FACING, localEnumDirection);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockData updateState(IBlockData paramIBlockData, IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/* 238 */     if (paramIBlockData.get(PART) == EnumBedPart.FOOT) {
/* 239 */       IBlockData localIBlockData = paramIBlockAccess.getType(paramBlockPosition.shift((EnumDirection)paramIBlockData.get(FACING)));
/* 240 */       if (localIBlockData.getBlock() == this) {
/* 241 */         paramIBlockData = paramIBlockData.set(OCCUPIED, localIBlockData.get(OCCUPIED));
/*     */       }
/*     */     }
/*     */     
/* 245 */     return paramIBlockData;
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 250 */     int i = 0;
/*     */     
/* 252 */     i |= ((EnumDirection)paramIBlockData.get(FACING)).b();
/*     */     
/* 254 */     if (paramIBlockData.get(PART) == EnumBedPart.HEAD) {
/* 255 */       i |= 0x8;
/*     */       
/* 257 */       if (((Boolean)paramIBlockData.get(OCCUPIED)).booleanValue()) {
/* 258 */         i |= 0x4;
/*     */       }
/*     */     }
/*     */     
/* 262 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 267 */     return new BlockStateList(this, new IBlockState[] { FACING, PART, OCCUPIED });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumBedPart
/*     */     implements INamable
/*     */   {
/*     */     private final String c;
/*     */     
/*     */     private EnumBedPart(String paramString)
/*     */     {
/* 278 */       this.c = paramString;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 283 */       return this.c;
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 288 */       return this.c;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockBed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */