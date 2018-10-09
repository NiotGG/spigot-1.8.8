/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WorldGenNetherPieces
/*      */ {
/*      */   public static void a()
/*      */   {
/*   26 */     WorldGenFactory.a(WorldGenNetherPiece1.class, "NeBCr");
/*   27 */     WorldGenFactory.a(WorldGenNetherPiece2.class, "NeBEF");
/*   28 */     WorldGenFactory.a(WorldGenNetherPiece3.class, "NeBS");
/*   29 */     WorldGenFactory.a(WorldGenNetherPiece4.class, "NeCCS");
/*   30 */     WorldGenFactory.a(WorldGenNetherPiece5.class, "NeCTB");
/*   31 */     WorldGenFactory.a(WorldGenNetherPiece6.class, "NeCE");
/*   32 */     WorldGenFactory.a(WorldGenNetherPiece7.class, "NeSCSC");
/*   33 */     WorldGenFactory.a(WorldGenNetherPiece8.class, "NeSCLT");
/*   34 */     WorldGenFactory.a(WorldGenNetherPiece9.class, "NeSC");
/*   35 */     WorldGenFactory.a(WorldGenNetherPiece10.class, "NeSCRT");
/*   36 */     WorldGenFactory.a(WorldGenNetherPiece11.class, "NeCSR");
/*   37 */     WorldGenFactory.a(WorldGenNetherPiece12.class, "NeMT");
/*   38 */     WorldGenFactory.a(WorldGenNetherPiece13.class, "NeRC");
/*   39 */     WorldGenFactory.a(WorldGenNetherPiece14.class, "NeSR");
/*   40 */     WorldGenFactory.a(WorldGenNetherPiece15.class, "NeStart");
/*      */   }
/*      */   
/*      */   static class WorldGenNetherPieceWeight {
/*      */     public Class<? extends WorldGenNetherPieces.WorldGenNetherPiece> a;
/*      */     public final int b;
/*      */     public int c;
/*      */     public int d;
/*      */     public boolean e;
/*      */     
/*      */     public WorldGenNetherPieceWeight(Class<? extends WorldGenNetherPieces.WorldGenNetherPiece> paramClass, int paramInt1, int paramInt2, boolean paramBoolean) {
/*   51 */       this.a = paramClass;
/*   52 */       this.b = paramInt1;
/*   53 */       this.d = paramInt2;
/*   54 */       this.e = paramBoolean;
/*      */     }
/*      */     
/*      */     public WorldGenNetherPieceWeight(Class<? extends WorldGenNetherPieces.WorldGenNetherPiece> paramClass, int paramInt1, int paramInt2) {
/*   58 */       this(paramClass, paramInt1, paramInt2, false);
/*      */     }
/*      */     
/*      */     public boolean a(int paramInt) {
/*   62 */       return (this.d == 0) || (this.c < this.d);
/*      */     }
/*      */     
/*      */     public boolean a() {
/*   66 */       return (this.d == 0) || (this.c < this.d);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*   71 */   private static final WorldGenNetherPieceWeight[] a = { new WorldGenNetherPieceWeight(WorldGenNetherPiece3.class, 30, 0, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece1.class, 10, 4), new WorldGenNetherPieceWeight(WorldGenNetherPiece13.class, 10, 4), new WorldGenNetherPieceWeight(WorldGenNetherPiece14.class, 10, 3), new WorldGenNetherPieceWeight(WorldGenNetherPiece12.class, 5, 2), new WorldGenNetherPieceWeight(WorldGenNetherPiece6.class, 5, 1) };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   79 */   private static final WorldGenNetherPieceWeight[] b = { new WorldGenNetherPieceWeight(WorldGenNetherPiece9.class, 25, 0, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece7.class, 15, 5), new WorldGenNetherPieceWeight(WorldGenNetherPiece10.class, 5, 10), new WorldGenNetherPieceWeight(WorldGenNetherPiece8.class, 5, 10), new WorldGenNetherPieceWeight(WorldGenNetherPiece4.class, 10, 3, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece5.class, 7, 2), new WorldGenNetherPieceWeight(WorldGenNetherPiece11.class, 5, 2) };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static WorldGenNetherPiece b(WorldGenNetherPieceWeight paramWorldGenNetherPieceWeight, List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4)
/*      */   {
/*   91 */     Class localClass = paramWorldGenNetherPieceWeight.a;
/*   92 */     Object localObject = null;
/*      */     
/*   94 */     if (localClass == WorldGenNetherPiece3.class) {
/*   95 */       localObject = WorldGenNetherPiece3.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*   96 */     } else if (localClass == WorldGenNetherPiece1.class) {
/*   97 */       localObject = WorldGenNetherPiece1.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*   98 */     } else if (localClass == WorldGenNetherPiece13.class) {
/*   99 */       localObject = WorldGenNetherPiece13.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  100 */     } else if (localClass == WorldGenNetherPiece14.class) {
/*  101 */       localObject = WorldGenNetherPiece14.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramEnumDirection);
/*  102 */     } else if (localClass == WorldGenNetherPiece12.class) {
/*  103 */       localObject = WorldGenNetherPiece12.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramEnumDirection);
/*  104 */     } else if (localClass == WorldGenNetherPiece6.class) {
/*  105 */       localObject = WorldGenNetherPiece6.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  106 */     } else if (localClass == WorldGenNetherPiece9.class) {
/*  107 */       localObject = WorldGenNetherPiece9.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  108 */     } else if (localClass == WorldGenNetherPiece10.class) {
/*  109 */       localObject = WorldGenNetherPiece10.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  110 */     } else if (localClass == WorldGenNetherPiece8.class) {
/*  111 */       localObject = WorldGenNetherPiece8.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  112 */     } else if (localClass == WorldGenNetherPiece4.class) {
/*  113 */       localObject = WorldGenNetherPiece4.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  114 */     } else if (localClass == WorldGenNetherPiece5.class) {
/*  115 */       localObject = WorldGenNetherPiece5.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  116 */     } else if (localClass == WorldGenNetherPiece7.class) {
/*  117 */       localObject = WorldGenNetherPiece7.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  118 */     } else if (localClass == WorldGenNetherPiece11.class) {
/*  119 */       localObject = WorldGenNetherPiece11.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*      */     }
/*  121 */     return (WorldGenNetherPiece)localObject;
/*      */   }
/*      */   
/*      */   static abstract class WorldGenNetherPiece extends StructurePiece
/*      */   {
/*  126 */     protected static final List<StructurePieceTreasure> a = Lists.newArrayList(new StructurePieceTreasure[] { new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 5), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 5), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 15), new StructurePieceTreasure(Items.GOLDEN_SWORD, 0, 1, 1, 5), new StructurePieceTreasure(Items.GOLDEN_CHESTPLATE, 0, 1, 1, 5), new StructurePieceTreasure(Items.FLINT_AND_STEEL, 0, 1, 1, 5), new StructurePieceTreasure(Items.NETHER_WART, 0, 3, 7, 5), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 10), new StructurePieceTreasure(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 8), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 5), new StructurePieceTreasure(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 3), new StructurePieceTreasure(Item.getItemOf(Blocks.OBSIDIAN), 0, 2, 4, 2) });
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public WorldGenNetherPiece() {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected WorldGenNetherPiece(int paramInt)
/*      */     {
/*  147 */       super();
/*      */     }
/*      */     
/*      */ 
/*      */     protected void b(NBTTagCompound paramNBTTagCompound) {}
/*      */     
/*      */ 
/*      */     protected void a(NBTTagCompound paramNBTTagCompound) {}
/*      */     
/*      */ 
/*      */     private int a(List<WorldGenNetherPieces.WorldGenNetherPieceWeight> paramList)
/*      */     {
/*  159 */       int i = 0;
/*  160 */       int j = 0;
/*  161 */       for (WorldGenNetherPieces.WorldGenNetherPieceWeight localWorldGenNetherPieceWeight : paramList) {
/*  162 */         if ((localWorldGenNetherPieceWeight.d > 0) && (localWorldGenNetherPieceWeight.c < localWorldGenNetherPieceWeight.d)) {
/*  163 */           i = 1;
/*      */         }
/*  165 */         j += localWorldGenNetherPieceWeight.b;
/*      */       }
/*  167 */       return i != 0 ? j : -1;
/*      */     }
/*      */     
/*      */     private WorldGenNetherPiece a(WorldGenNetherPieces.WorldGenNetherPiece15 paramWorldGenNetherPiece15, List<WorldGenNetherPieces.WorldGenNetherPieceWeight> paramList, List<StructurePiece> paramList1, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  171 */       int i = a(paramList);
/*  172 */       int j = (i > 0) && (paramInt4 <= 30) ? 1 : 0;
/*      */       
/*  174 */       int k = 0;
/*  175 */       int m; while ((k < 5) && (j != 0)) {
/*  176 */         k++;
/*      */         
/*  178 */         m = paramRandom.nextInt(i);
/*  179 */         for (WorldGenNetherPieces.WorldGenNetherPieceWeight localWorldGenNetherPieceWeight : paramList) {
/*  180 */           m -= localWorldGenNetherPieceWeight.b;
/*  181 */           if (m < 0) {
/*  182 */             if ((!localWorldGenNetherPieceWeight.a(paramInt4)) || ((localWorldGenNetherPieceWeight == paramWorldGenNetherPiece15.b) && (!localWorldGenNetherPieceWeight.e))) {
/*      */               break;
/*      */             }
/*      */             
/*  186 */             WorldGenNetherPiece localWorldGenNetherPiece = WorldGenNetherPieces.a(localWorldGenNetherPieceWeight, paramList1, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  187 */             if (localWorldGenNetherPiece != null) {
/*  188 */               localWorldGenNetherPieceWeight.c += 1;
/*  189 */               paramWorldGenNetherPiece15.b = localWorldGenNetherPieceWeight;
/*      */               
/*  191 */               if (!localWorldGenNetherPieceWeight.a()) {
/*  192 */                 paramList.remove(localWorldGenNetherPieceWeight);
/*      */               }
/*  194 */               return localWorldGenNetherPiece;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  199 */       return WorldGenNetherPieces.WorldGenNetherPiece2.a(paramList1, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*      */     }
/*      */     
/*      */     private StructurePiece a(WorldGenNetherPieces.WorldGenNetherPiece15 paramWorldGenNetherPiece15, List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4, boolean paramBoolean) {
/*  203 */       if ((Math.abs(paramInt1 - paramWorldGenNetherPiece15.c().a) > 112) || (Math.abs(paramInt3 - paramWorldGenNetherPiece15.c().c) > 112)) {
/*  204 */         return WorldGenNetherPieces.WorldGenNetherPiece2.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*      */       }
/*  206 */       List localList = paramWorldGenNetherPiece15.c;
/*  207 */       if (paramBoolean) {
/*  208 */         localList = paramWorldGenNetherPiece15.d;
/*      */       }
/*  210 */       WorldGenNetherPiece localWorldGenNetherPiece = a(paramWorldGenNetherPiece15, localList, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4 + 1);
/*  211 */       if (localWorldGenNetherPiece != null) {
/*  212 */         paramList.add(localWorldGenNetherPiece);
/*  213 */         paramWorldGenNetherPiece15.e.add(localWorldGenNetherPiece);
/*      */       }
/*  215 */       return localWorldGenNetherPiece;
/*      */     }
/*      */     
/*      */     protected StructurePiece a(WorldGenNetherPieces.WorldGenNetherPiece15 paramWorldGenNetherPiece15, List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, boolean paramBoolean) {
/*  219 */       if (this.m != null) {
/*  220 */         switch (WorldGenNetherPieces.1.a[this.m.ordinal()]) {
/*      */         case 1: 
/*  222 */           return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.l.a + paramInt1, this.l.b + paramInt2, this.l.c - 1, this.m, d(), paramBoolean);
/*      */         case 2: 
/*  224 */           return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.l.a + paramInt1, this.l.b + paramInt2, this.l.f + 1, this.m, d(), paramBoolean);
/*      */         case 3: 
/*  226 */           return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.l.a - 1, this.l.b + paramInt2, this.l.c + paramInt1, this.m, d(), paramBoolean);
/*      */         case 4: 
/*  228 */           return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.l.d + 1, this.l.b + paramInt2, this.l.c + paramInt1, this.m, d(), paramBoolean);
/*      */         }
/*      */       }
/*  231 */       return null;
/*      */     }
/*      */     
/*      */     protected StructurePiece b(WorldGenNetherPieces.WorldGenNetherPiece15 paramWorldGenNetherPiece15, List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, boolean paramBoolean) {
/*  235 */       if (this.m != null) {
/*  236 */         switch (WorldGenNetherPieces.1.a[this.m.ordinal()]) {
/*      */         case 1: 
/*  238 */           return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.l.a - 1, this.l.b + paramInt1, this.l.c + paramInt2, EnumDirection.WEST, d(), paramBoolean);
/*      */         case 2: 
/*  240 */           return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.l.a - 1, this.l.b + paramInt1, this.l.c + paramInt2, EnumDirection.WEST, d(), paramBoolean);
/*      */         case 3: 
/*  242 */           return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.l.a + paramInt2, this.l.b + paramInt1, this.l.c - 1, EnumDirection.NORTH, d(), paramBoolean);
/*      */         case 4: 
/*  244 */           return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.l.a + paramInt2, this.l.b + paramInt1, this.l.c - 1, EnumDirection.NORTH, d(), paramBoolean);
/*      */         }
/*      */       }
/*  247 */       return null;
/*      */     }
/*      */     
/*      */     protected StructurePiece c(WorldGenNetherPieces.WorldGenNetherPiece15 paramWorldGenNetherPiece15, List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, boolean paramBoolean) {
/*  251 */       if (this.m != null) {
/*  252 */         switch (WorldGenNetherPieces.1.a[this.m.ordinal()]) {
/*      */         case 1: 
/*  254 */           return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.l.d + 1, this.l.b + paramInt1, this.l.c + paramInt2, EnumDirection.EAST, d(), paramBoolean);
/*      */         case 2: 
/*  256 */           return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.l.d + 1, this.l.b + paramInt1, this.l.c + paramInt2, EnumDirection.EAST, d(), paramBoolean);
/*      */         case 3: 
/*  258 */           return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.l.a + paramInt2, this.l.b + paramInt1, this.l.f + 1, EnumDirection.SOUTH, d(), paramBoolean);
/*      */         case 4: 
/*  260 */           return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.l.a + paramInt2, this.l.b + paramInt1, this.l.f + 1, EnumDirection.SOUTH, d(), paramBoolean);
/*      */         }
/*      */       }
/*  263 */       return null;
/*      */     }
/*      */     
/*      */     protected static boolean a(StructureBoundingBox paramStructureBoundingBox) {
/*  267 */       return (paramStructureBoundingBox != null) && (paramStructureBoundingBox.b > 10);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class WorldGenNetherPiece15
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece1
/*      */   {
/*      */     public WorldGenNetherPieces.WorldGenNetherPieceWeight b;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public List<WorldGenNetherPieces.WorldGenNetherPieceWeight> c;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public List<WorldGenNetherPieces.WorldGenNetherPieceWeight> d;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  314 */     public List<StructurePiece> e = Lists.newArrayList();
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece15() {}
/*      */     
/*      */     public WorldGenNetherPiece15(Random paramRandom, int paramInt1, int paramInt2)
/*      */     {
/*  321 */       super(paramInt1, paramInt2);
/*      */       
/*  323 */       this.c = Lists.newArrayList();
/*  324 */       WorldGenNetherPieces.WorldGenNetherPieceWeight localWorldGenNetherPieceWeight; for (localWorldGenNetherPieceWeight : WorldGenNetherPieces.b()) {
/*  325 */         localWorldGenNetherPieceWeight.c = 0;
/*  326 */         this.c.add(localWorldGenNetherPieceWeight);
/*      */       }
/*      */       
/*  329 */       this.d = Lists.newArrayList();
/*  330 */       for (localWorldGenNetherPieceWeight : WorldGenNetherPieces.c()) {
/*  331 */         localWorldGenNetherPieceWeight.c = 0;
/*  332 */         this.d.add(localWorldGenNetherPieceWeight);
/*      */       }
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  338 */       super.b(paramNBTTagCompound);
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  343 */       super.a(paramNBTTagCompound);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenNetherPiece3
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     public WorldGenNetherPiece3() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece3(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  357 */       super();
/*      */       
/*  359 */       this.m = paramEnumDirection;
/*  360 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  365 */       a((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 1, 3, false);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece3 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  369 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -3, 0, 5, 10, 19, paramEnumDirection);
/*      */       
/*  371 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  372 */         return null;
/*      */       }
/*      */       
/*  375 */       return new WorldGenNetherPiece3(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  381 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 4, 4, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*  383 */       a(paramWorld, paramStructureBoundingBox, 1, 5, 0, 3, 7, 18, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/*  386 */       a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 0, 5, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  387 */       a(paramWorld, paramStructureBoundingBox, 4, 5, 0, 4, 5, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/*  390 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 4, 2, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  391 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 13, 4, 2, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  392 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 1, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  393 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 15, 4, 1, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*  395 */       for (int i = 0; i <= 4; i++) {
/*  396 */         for (int j = 0; j <= 2; j++) {
/*  397 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, paramStructureBoundingBox);
/*  398 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, 18 - j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*  402 */       a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 4, 1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  403 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 4, 0, 4, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  404 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 14, 0, 4, 14, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  405 */       a(paramWorld, paramStructureBoundingBox, 0, 1, 17, 0, 4, 17, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  406 */       a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 4, 4, 1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  407 */       a(paramWorld, paramStructureBoundingBox, 4, 3, 4, 4, 4, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  408 */       a(paramWorld, paramStructureBoundingBox, 4, 3, 14, 4, 4, 14, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  409 */       a(paramWorld, paramStructureBoundingBox, 4, 1, 17, 4, 4, 17, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       
/*  411 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static class WorldGenNetherPiece2
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     private int b;
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece2() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece2(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  427 */       super();
/*      */       
/*  429 */       this.m = paramEnumDirection;
/*  430 */       this.l = paramStructureBoundingBox;
/*  431 */       this.b = paramRandom.nextInt();
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece2 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  435 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -3, 0, 5, 10, 8, paramEnumDirection);
/*      */       
/*  437 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  438 */         return null;
/*      */       }
/*      */       
/*  441 */       return new WorldGenNetherPiece2(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  446 */       super.b(paramNBTTagCompound);
/*      */       
/*  448 */       this.b = paramNBTTagCompound.getInt("Seed");
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  453 */       super.a(paramNBTTagCompound);
/*      */       
/*  455 */       paramNBTTagCompound.setInt("Seed", this.b);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  460 */       Random localRandom = new Random(this.b);
/*      */       int j;
/*      */       int k;
/*  463 */       for (int i = 0; i <= 4; i++) {
/*  464 */         for (j = 3; j <= 4; j++) {
/*  465 */           k = localRandom.nextInt(8);
/*  466 */           a(paramWorld, paramStructureBoundingBox, i, j, 0, i, j, k, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  472 */       i = localRandom.nextInt(8);
/*  473 */       a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 0, 5, i, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/*  476 */       i = localRandom.nextInt(8);
/*  477 */       a(paramWorld, paramStructureBoundingBox, 4, 5, 0, 4, 5, i, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/*      */ 
/*  481 */       for (i = 0; i <= 4; i++) {
/*  482 */         j = localRandom.nextInt(5);
/*  483 */         a(paramWorld, paramStructureBoundingBox, i, 2, 0, i, 2, j, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       }
/*  485 */       for (i = 0; i <= 4; i++) {
/*  486 */         for (j = 0; j <= 1; j++) {
/*  487 */           k = localRandom.nextInt(3);
/*  488 */           a(paramWorld, paramStructureBoundingBox, i, j, 0, i, j, k, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */         }
/*      */       }
/*      */       
/*  492 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenNetherPiece1
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     public WorldGenNetherPiece1() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece1(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  506 */       super();
/*      */       
/*  508 */       this.m = paramEnumDirection;
/*  509 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     protected WorldGenNetherPiece1(Random paramRandom, int paramInt1, int paramInt2) {
/*  513 */       super();
/*      */       
/*  515 */       this.m = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(paramRandom);
/*      */       
/*  517 */       switch (WorldGenNetherPieces.1.a[this.m.ordinal()]) {
/*      */       case 1: 
/*      */       case 2: 
/*  520 */         this.l = new StructureBoundingBox(paramInt1, 64, paramInt2, paramInt1 + 19 - 1, 73, paramInt2 + 19 - 1);
/*  521 */         break;
/*      */       default: 
/*  523 */         this.l = new StructureBoundingBox(paramInt1, 64, paramInt2, paramInt1 + 19 - 1, 73, paramInt2 + 19 - 1);
/*      */       }
/*      */       
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  530 */       a((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 8, 3, false);
/*  531 */       b((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 3, 8, false);
/*  532 */       c((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 3, 8, false);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece1 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  536 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -8, -3, 0, 19, 10, 19, paramEnumDirection);
/*      */       
/*  538 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  539 */         return null;
/*      */       }
/*      */       
/*  542 */       return new WorldGenNetherPiece1(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  548 */       a(paramWorld, paramStructureBoundingBox, 7, 3, 0, 11, 4, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  549 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 7, 18, 4, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*  551 */       a(paramWorld, paramStructureBoundingBox, 8, 5, 0, 10, 7, 18, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  552 */       a(paramWorld, paramStructureBoundingBox, 0, 5, 8, 18, 7, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*  554 */       a(paramWorld, paramStructureBoundingBox, 7, 5, 0, 7, 5, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  555 */       a(paramWorld, paramStructureBoundingBox, 7, 5, 11, 7, 5, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  556 */       a(paramWorld, paramStructureBoundingBox, 11, 5, 0, 11, 5, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  557 */       a(paramWorld, paramStructureBoundingBox, 11, 5, 11, 11, 5, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  558 */       a(paramWorld, paramStructureBoundingBox, 0, 5, 7, 7, 5, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  559 */       a(paramWorld, paramStructureBoundingBox, 11, 5, 7, 18, 5, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  560 */       a(paramWorld, paramStructureBoundingBox, 0, 5, 11, 7, 5, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  561 */       a(paramWorld, paramStructureBoundingBox, 11, 5, 11, 18, 5, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/*  564 */       a(paramWorld, paramStructureBoundingBox, 7, 2, 0, 11, 2, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  565 */       a(paramWorld, paramStructureBoundingBox, 7, 2, 13, 11, 2, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  566 */       a(paramWorld, paramStructureBoundingBox, 7, 0, 0, 11, 1, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  567 */       a(paramWorld, paramStructureBoundingBox, 7, 0, 15, 11, 1, 18, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  568 */       int j; for (int i = 7; i <= 11; i++) {
/*  569 */         for (j = 0; j <= 2; j++) {
/*  570 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, paramStructureBoundingBox);
/*  571 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, 18 - j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*  575 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 7, 5, 2, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  576 */       a(paramWorld, paramStructureBoundingBox, 13, 2, 7, 18, 2, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  577 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 7, 3, 1, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  578 */       a(paramWorld, paramStructureBoundingBox, 15, 0, 7, 18, 1, 11, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  579 */       for (i = 0; i <= 2; i++) {
/*  580 */         for (j = 7; j <= 11; j++) {
/*  581 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, paramStructureBoundingBox);
/*  582 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), 18 - i, -1, j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*  586 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenNetherPiece13
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     public WorldGenNetherPiece13() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece13(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  600 */       super();
/*      */       
/*  602 */       this.m = paramEnumDirection;
/*  603 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  608 */       a((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 2, 0, false);
/*  609 */       b((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 0, 2, false);
/*  610 */       c((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 0, 2, false);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece13 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  614 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -2, 0, 0, 7, 9, 7, paramEnumDirection);
/*      */       
/*  616 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  617 */         return null;
/*      */       }
/*      */       
/*  620 */       return new WorldGenNetherPiece13(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  626 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*  628 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 6, 7, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/*  631 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 1, 6, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  632 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 6, 1, 6, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  633 */       a(paramWorld, paramStructureBoundingBox, 5, 2, 0, 6, 6, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  634 */       a(paramWorld, paramStructureBoundingBox, 5, 2, 6, 6, 6, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  635 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 6, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  636 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 5, 0, 6, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  637 */       a(paramWorld, paramStructureBoundingBox, 6, 2, 0, 6, 6, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  638 */       a(paramWorld, paramStructureBoundingBox, 6, 2, 5, 6, 6, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/*  641 */       a(paramWorld, paramStructureBoundingBox, 2, 6, 0, 4, 6, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  642 */       a(paramWorld, paramStructureBoundingBox, 2, 5, 0, 4, 5, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  643 */       a(paramWorld, paramStructureBoundingBox, 2, 6, 6, 4, 6, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  644 */       a(paramWorld, paramStructureBoundingBox, 2, 5, 6, 4, 5, 6, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  645 */       a(paramWorld, paramStructureBoundingBox, 0, 6, 2, 0, 6, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  646 */       a(paramWorld, paramStructureBoundingBox, 0, 5, 2, 0, 5, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  647 */       a(paramWorld, paramStructureBoundingBox, 6, 6, 2, 6, 6, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  648 */       a(paramWorld, paramStructureBoundingBox, 6, 5, 2, 6, 5, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       
/*      */ 
/*  651 */       for (int i = 0; i <= 6; i++) {
/*  652 */         for (int j = 0; j <= 6; j++) {
/*  653 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*  657 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenNetherPiece14
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     public WorldGenNetherPiece14() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece14(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  671 */       super();
/*      */       
/*  673 */       this.m = paramEnumDirection;
/*  674 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  679 */       c((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 6, 2, false);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece14 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, EnumDirection paramEnumDirection) {
/*  683 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -2, 0, 0, 7, 11, 7, paramEnumDirection);
/*      */       
/*  685 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  686 */         return null;
/*      */       }
/*      */       
/*  689 */       return new WorldGenNetherPiece14(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  695 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*  697 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 6, 10, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/*  700 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 1, 8, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  701 */       a(paramWorld, paramStructureBoundingBox, 5, 2, 0, 6, 8, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  702 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 1, 0, 8, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  703 */       a(paramWorld, paramStructureBoundingBox, 6, 2, 1, 6, 8, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  704 */       a(paramWorld, paramStructureBoundingBox, 1, 2, 6, 5, 8, 6, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/*  707 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 2, 0, 5, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  708 */       a(paramWorld, paramStructureBoundingBox, 6, 3, 2, 6, 5, 2, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  709 */       a(paramWorld, paramStructureBoundingBox, 6, 3, 4, 6, 5, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       
/*      */ 
/*  712 */       a(paramWorld, Blocks.NETHER_BRICK.getBlockData(), 5, 2, 5, paramStructureBoundingBox);
/*  713 */       a(paramWorld, paramStructureBoundingBox, 4, 2, 5, 4, 3, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  714 */       a(paramWorld, paramStructureBoundingBox, 3, 2, 5, 3, 4, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  715 */       a(paramWorld, paramStructureBoundingBox, 2, 2, 5, 2, 5, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  716 */       a(paramWorld, paramStructureBoundingBox, 1, 2, 5, 1, 6, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/*  719 */       a(paramWorld, paramStructureBoundingBox, 1, 7, 1, 5, 7, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  720 */       a(paramWorld, paramStructureBoundingBox, 6, 8, 2, 6, 8, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/*  723 */       a(paramWorld, paramStructureBoundingBox, 2, 6, 0, 4, 8, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  724 */       a(paramWorld, paramStructureBoundingBox, 2, 5, 0, 4, 5, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       
/*  726 */       for (int i = 0; i <= 6; i++) {
/*  727 */         for (int j = 0; j <= 6; j++) {
/*  728 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*  732 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static class WorldGenNetherPiece12
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     private boolean b;
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece12() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece12(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  748 */       super();
/*      */       
/*  750 */       this.m = paramEnumDirection;
/*  751 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  756 */       super.b(paramNBTTagCompound);
/*      */       
/*  758 */       this.b = paramNBTTagCompound.getBoolean("Mob");
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  763 */       super.a(paramNBTTagCompound);
/*      */       
/*  765 */       paramNBTTagCompound.setBoolean("Mob", this.b);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece12 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, EnumDirection paramEnumDirection) {
/*  769 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -2, 0, 0, 7, 8, 9, paramEnumDirection);
/*      */       
/*  771 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  772 */         return null;
/*      */       }
/*      */       
/*  775 */       return new WorldGenNetherPiece12(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  781 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 6, 7, 7, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/*  784 */       a(paramWorld, paramStructureBoundingBox, 1, 0, 0, 5, 1, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  785 */       a(paramWorld, paramStructureBoundingBox, 1, 2, 1, 5, 2, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  786 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 2, 5, 3, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  787 */       a(paramWorld, paramStructureBoundingBox, 1, 4, 3, 5, 4, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/*  790 */       a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 1, 4, 2, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  791 */       a(paramWorld, paramStructureBoundingBox, 5, 2, 0, 5, 4, 2, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  792 */       a(paramWorld, paramStructureBoundingBox, 1, 5, 2, 1, 5, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  793 */       a(paramWorld, paramStructureBoundingBox, 5, 5, 2, 5, 5, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  794 */       a(paramWorld, paramStructureBoundingBox, 0, 5, 3, 0, 5, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  795 */       a(paramWorld, paramStructureBoundingBox, 6, 5, 3, 6, 5, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  796 */       a(paramWorld, paramStructureBoundingBox, 1, 5, 8, 5, 5, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*  798 */       a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 1, 6, 3, paramStructureBoundingBox);
/*  799 */       a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 5, 6, 3, paramStructureBoundingBox);
/*  800 */       a(paramWorld, paramStructureBoundingBox, 0, 6, 3, 0, 6, 8, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  801 */       a(paramWorld, paramStructureBoundingBox, 6, 6, 3, 6, 6, 8, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  802 */       a(paramWorld, paramStructureBoundingBox, 1, 6, 8, 5, 7, 8, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  803 */       a(paramWorld, paramStructureBoundingBox, 2, 8, 8, 4, 8, 8, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       
/*  805 */       if (!this.b) {
/*  806 */         BlockPosition localBlockPosition = new BlockPosition(a(3, 5), d(5), b(3, 5));
/*  807 */         if (paramStructureBoundingBox.b(localBlockPosition)) {
/*  808 */           this.b = true;
/*  809 */           paramWorld.setTypeAndData(localBlockPosition, Blocks.MOB_SPAWNER.getBlockData(), 2);
/*      */           
/*  811 */           TileEntity localTileEntity = paramWorld.getTileEntity(localBlockPosition);
/*  812 */           if ((localTileEntity instanceof TileEntityMobSpawner)) {
/*  813 */             ((TileEntityMobSpawner)localTileEntity).getSpawner().setMobName("Blaze");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  819 */       for (int i = 0; i <= 6; i++) {
/*  820 */         for (int j = 0; j <= 6; j++) {
/*  821 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*  825 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenNetherPiece6
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     public WorldGenNetherPiece6() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece6(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  839 */       super();
/*      */       
/*  841 */       this.m = paramEnumDirection;
/*  842 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  847 */       a((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 5, 3, true);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece6 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  851 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -5, -3, 0, 13, 14, 13, paramEnumDirection);
/*      */       
/*  853 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  854 */         return null;
/*      */       }
/*      */       
/*  857 */       return new WorldGenNetherPiece6(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  863 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*  865 */       a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/*  868 */       a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  869 */       a(paramWorld, paramStructureBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  870 */       a(paramWorld, paramStructureBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  871 */       a(paramWorld, paramStructureBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  872 */       a(paramWorld, paramStructureBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  873 */       a(paramWorld, paramStructureBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  874 */       a(paramWorld, paramStructureBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  875 */       a(paramWorld, paramStructureBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/*  878 */       a(paramWorld, paramStructureBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/*  881 */       a(paramWorld, paramStructureBoundingBox, 5, 8, 0, 7, 8, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       
/*      */ 
/*  884 */       for (int i = 1; i <= 11; i += 2) {
/*  885 */         a(paramWorld, paramStructureBoundingBox, i, 10, 0, i, 11, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  886 */         a(paramWorld, paramStructureBoundingBox, i, 10, 12, i, 11, 12, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  887 */         a(paramWorld, paramStructureBoundingBox, 0, 10, i, 0, 11, i, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  888 */         a(paramWorld, paramStructureBoundingBox, 12, 10, i, 12, 11, i, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  889 */         a(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, 13, 0, paramStructureBoundingBox);
/*  890 */         a(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, 13, 12, paramStructureBoundingBox);
/*  891 */         a(paramWorld, Blocks.NETHER_BRICK.getBlockData(), 0, 13, i, paramStructureBoundingBox);
/*  892 */         a(paramWorld, Blocks.NETHER_BRICK.getBlockData(), 12, 13, i, paramStructureBoundingBox);
/*  893 */         a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), i + 1, 13, 0, paramStructureBoundingBox);
/*  894 */         a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), i + 1, 13, 12, paramStructureBoundingBox);
/*  895 */         a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, i + 1, paramStructureBoundingBox);
/*  896 */         a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 12, 13, i + 1, paramStructureBoundingBox);
/*      */       }
/*  898 */       a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, 0, paramStructureBoundingBox);
/*  899 */       a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, 12, paramStructureBoundingBox);
/*  900 */       a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, 0, paramStructureBoundingBox);
/*  901 */       a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 12, 13, 0, paramStructureBoundingBox);
/*      */       
/*      */ 
/*  904 */       for (i = 3; i <= 9; i += 2) {
/*  905 */         a(paramWorld, paramStructureBoundingBox, 1, 7, i, 1, 8, i, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*  906 */         a(paramWorld, paramStructureBoundingBox, 11, 7, i, 11, 8, i, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       }
/*      */       
/*      */ 
/*  910 */       a(paramWorld, paramStructureBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  911 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*  913 */       a(paramWorld, paramStructureBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  914 */       a(paramWorld, paramStructureBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  915 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  916 */       a(paramWorld, paramStructureBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       int j;
/*  918 */       for (i = 4; i <= 8; i++) {
/*  919 */         for (j = 0; j <= 2; j++) {
/*  920 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, paramStructureBoundingBox);
/*  921 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, 12 - j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*  924 */       for (i = 0; i <= 2; i++) {
/*  925 */         for (j = 4; j <= 8; j++) {
/*  926 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, paramStructureBoundingBox);
/*  927 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), 12 - i, -1, j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  932 */       a(paramWorld, paramStructureBoundingBox, 5, 5, 5, 7, 5, 7, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  933 */       a(paramWorld, paramStructureBoundingBox, 6, 1, 6, 6, 4, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  934 */       a(paramWorld, Blocks.NETHER_BRICK.getBlockData(), 6, 0, 6, paramStructureBoundingBox);
/*  935 */       a(paramWorld, Blocks.FLOWING_LAVA.getBlockData(), 6, 5, 6, paramStructureBoundingBox);
/*      */       
/*  937 */       BlockPosition localBlockPosition = new BlockPosition(a(6, 6), d(5), b(6, 6));
/*  938 */       if (paramStructureBoundingBox.b(localBlockPosition)) {
/*  939 */         paramWorld.a(Blocks.FLOWING_LAVA, localBlockPosition, paramRandom);
/*      */       }
/*      */       
/*  942 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenNetherPiece11
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     public WorldGenNetherPiece11() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece11(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  956 */       super();
/*      */       
/*  958 */       this.m = paramEnumDirection;
/*  959 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  964 */       a((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 5, 3, true);
/*  965 */       a((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 5, 11, true);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece11 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  969 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -5, -3, 0, 13, 14, 13, paramEnumDirection);
/*      */       
/*  971 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  972 */         return null;
/*      */       }
/*      */       
/*  975 */       return new WorldGenNetherPiece11(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  981 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*  983 */       a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/*  986 */       a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  987 */       a(paramWorld, paramStructureBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  988 */       a(paramWorld, paramStructureBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  989 */       a(paramWorld, paramStructureBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  990 */       a(paramWorld, paramStructureBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  991 */       a(paramWorld, paramStructureBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  992 */       a(paramWorld, paramStructureBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*  993 */       a(paramWorld, paramStructureBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/*  996 */       a(paramWorld, paramStructureBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/*  999 */       for (int i = 1; i <= 11; i += 2) {
/* 1000 */         a(paramWorld, paramStructureBoundingBox, i, 10, 0, i, 11, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1001 */         a(paramWorld, paramStructureBoundingBox, i, 10, 12, i, 11, 12, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1002 */         a(paramWorld, paramStructureBoundingBox, 0, 10, i, 0, 11, i, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1003 */         a(paramWorld, paramStructureBoundingBox, 12, 10, i, 12, 11, i, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1004 */         a(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, 13, 0, paramStructureBoundingBox);
/* 1005 */         a(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, 13, 12, paramStructureBoundingBox);
/* 1006 */         a(paramWorld, Blocks.NETHER_BRICK.getBlockData(), 0, 13, i, paramStructureBoundingBox);
/* 1007 */         a(paramWorld, Blocks.NETHER_BRICK.getBlockData(), 12, 13, i, paramStructureBoundingBox);
/* 1008 */         a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), i + 1, 13, 0, paramStructureBoundingBox);
/* 1009 */         a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), i + 1, 13, 12, paramStructureBoundingBox);
/* 1010 */         a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, i + 1, paramStructureBoundingBox);
/* 1011 */         a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 12, 13, i + 1, paramStructureBoundingBox);
/*      */       }
/* 1013 */       a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, 0, paramStructureBoundingBox);
/* 1014 */       a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, 12, paramStructureBoundingBox);
/* 1015 */       a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 0, 13, 0, paramStructureBoundingBox);
/* 1016 */       a(paramWorld, Blocks.NETHER_BRICK_FENCE.getBlockData(), 12, 13, 0, paramStructureBoundingBox);
/*      */       
/*      */ 
/* 1019 */       for (i = 3; i <= 9; i += 2) {
/* 1020 */         a(paramWorld, paramStructureBoundingBox, 1, 7, i, 1, 8, i, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1021 */         a(paramWorld, paramStructureBoundingBox, 11, 7, i, 11, 8, i, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       }
/*      */       
/*      */ 
/* 1025 */       i = a(Blocks.NETHER_BRICK_STAIRS, 3);
/* 1026 */       for (int j = 0; j <= 6; j++) {
/* 1027 */         k = j + 4;
/* 1028 */         for (m = 5; m <= 7; m++) {
/* 1029 */           a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(i), m, 5 + j, k, paramStructureBoundingBox);
/*      */         }
/* 1031 */         if ((k >= 5) && (k <= 8)) {
/* 1032 */           a(paramWorld, paramStructureBoundingBox, 5, 5, k, 7, j + 4, k, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1033 */         } else if ((k >= 9) && (k <= 10)) {
/* 1034 */           a(paramWorld, paramStructureBoundingBox, 5, 8, k, 7, j + 4, k, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */         }
/* 1036 */         if (j >= 1) {
/* 1037 */           a(paramWorld, paramStructureBoundingBox, 5, 6 + j, k, 7, 9 + j, k, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */         }
/*      */       }
/* 1040 */       for (j = 5; j <= 7; j++) {
/* 1041 */         a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(i), j, 12, 11, paramStructureBoundingBox);
/*      */       }
/* 1043 */       a(paramWorld, paramStructureBoundingBox, 5, 6, 7, 5, 7, 7, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1044 */       a(paramWorld, paramStructureBoundingBox, 7, 6, 7, 7, 7, 7, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1045 */       a(paramWorld, paramStructureBoundingBox, 5, 13, 12, 7, 13, 12, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/* 1048 */       a(paramWorld, paramStructureBoundingBox, 2, 5, 2, 3, 5, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1049 */       a(paramWorld, paramStructureBoundingBox, 2, 5, 9, 3, 5, 10, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1050 */       a(paramWorld, paramStructureBoundingBox, 2, 5, 4, 2, 5, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1051 */       a(paramWorld, paramStructureBoundingBox, 9, 5, 2, 10, 5, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1052 */       a(paramWorld, paramStructureBoundingBox, 9, 5, 9, 10, 5, 10, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1053 */       a(paramWorld, paramStructureBoundingBox, 10, 5, 4, 10, 5, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1054 */       j = a(Blocks.NETHER_BRICK_STAIRS, 0);
/* 1055 */       int k = a(Blocks.NETHER_BRICK_STAIRS, 1);
/* 1056 */       a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(k), 4, 5, 2, paramStructureBoundingBox);
/* 1057 */       a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(k), 4, 5, 3, paramStructureBoundingBox);
/* 1058 */       a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(k), 4, 5, 9, paramStructureBoundingBox);
/* 1059 */       a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(k), 4, 5, 10, paramStructureBoundingBox);
/* 1060 */       a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(j), 8, 5, 2, paramStructureBoundingBox);
/* 1061 */       a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(j), 8, 5, 3, paramStructureBoundingBox);
/* 1062 */       a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(j), 8, 5, 9, paramStructureBoundingBox);
/* 1063 */       a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(j), 8, 5, 10, paramStructureBoundingBox);
/*      */       
/*      */ 
/* 1066 */       a(paramWorld, paramStructureBoundingBox, 3, 4, 4, 4, 4, 8, Blocks.SOUL_SAND.getBlockData(), Blocks.SOUL_SAND.getBlockData(), false);
/* 1067 */       a(paramWorld, paramStructureBoundingBox, 8, 4, 4, 9, 4, 8, Blocks.SOUL_SAND.getBlockData(), Blocks.SOUL_SAND.getBlockData(), false);
/* 1068 */       a(paramWorld, paramStructureBoundingBox, 3, 5, 4, 4, 5, 8, Blocks.NETHER_WART.getBlockData(), Blocks.NETHER_WART.getBlockData(), false);
/* 1069 */       a(paramWorld, paramStructureBoundingBox, 8, 5, 4, 9, 5, 8, Blocks.NETHER_WART.getBlockData(), Blocks.NETHER_WART.getBlockData(), false);
/*      */       
/*      */ 
/* 1072 */       a(paramWorld, paramStructureBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1073 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/* 1075 */       a(paramWorld, paramStructureBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1076 */       a(paramWorld, paramStructureBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1077 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1078 */       a(paramWorld, paramStructureBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       int n;
/* 1080 */       for (int m = 4; m <= 8; m++) {
/* 1081 */         for (n = 0; n <= 2; n++) {
/* 1082 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), m, -1, n, paramStructureBoundingBox);
/* 1083 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), m, -1, 12 - n, paramStructureBoundingBox);
/*      */         }
/*      */       }
/* 1086 */       for (m = 0; m <= 2; m++) {
/* 1087 */         for (n = 4; n <= 8; n++) {
/* 1088 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), m, -1, n, paramStructureBoundingBox);
/* 1089 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), 12 - m, -1, n, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/* 1093 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenNetherPiece9
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     public WorldGenNetherPiece9() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece9(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/* 1107 */       super();
/*      */       
/* 1109 */       this.m = paramEnumDirection;
/* 1110 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/* 1115 */       a((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 1, 0, true);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece9 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/* 1119 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, 0, 0, 5, 7, 5, paramEnumDirection);
/*      */       
/* 1121 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/* 1122 */         return null;
/*      */       }
/*      */       
/* 1125 */       return new WorldGenNetherPiece9(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1131 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/* 1133 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/* 1136 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1137 */       a(paramWorld, paramStructureBoundingBox, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1138 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 1, 0, 4, 1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1139 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 3, 0, 4, 3, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1140 */       a(paramWorld, paramStructureBoundingBox, 4, 3, 1, 4, 4, 1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1141 */       a(paramWorld, paramStructureBoundingBox, 4, 3, 3, 4, 4, 3, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       
/*      */ 
/* 1144 */       a(paramWorld, paramStructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/* 1147 */       for (int i = 0; i <= 4; i++) {
/* 1148 */         for (int j = 0; j <= 4; j++) {
/* 1149 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/* 1153 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenNetherPiece7
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     public WorldGenNetherPiece7() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece7(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/* 1167 */       super();
/*      */       
/* 1169 */       this.m = paramEnumDirection;
/* 1170 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/* 1175 */       a((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 1, 0, true);
/* 1176 */       b((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 0, 1, true);
/* 1177 */       c((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 0, 1, true);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece7 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/* 1181 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, 0, 0, 5, 7, 5, paramEnumDirection);
/*      */       
/* 1183 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/* 1184 */         return null;
/*      */       }
/*      */       
/* 1187 */       return new WorldGenNetherPiece7(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1193 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/* 1195 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/* 1198 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1199 */       a(paramWorld, paramStructureBoundingBox, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1200 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 4, 0, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1201 */       a(paramWorld, paramStructureBoundingBox, 4, 2, 4, 4, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/* 1204 */       a(paramWorld, paramStructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/* 1207 */       for (int i = 0; i <= 4; i++) {
/* 1208 */         for (int j = 0; j <= 4; j++) {
/* 1209 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/* 1213 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static class WorldGenNetherPiece10
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     private boolean b;
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece10() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece10(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/* 1229 */       super();
/*      */       
/* 1231 */       this.m = paramEnumDirection;
/* 1232 */       this.l = paramStructureBoundingBox;
/*      */       
/* 1234 */       this.b = (paramRandom.nextInt(3) == 0);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/* 1239 */       super.b(paramNBTTagCompound);
/*      */       
/* 1241 */       this.b = paramNBTTagCompound.getBoolean("Chest");
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/* 1246 */       super.a(paramNBTTagCompound);
/*      */       
/* 1248 */       paramNBTTagCompound.setBoolean("Chest", this.b);
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/* 1253 */       c((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 0, 1, true);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece10 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/* 1257 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, 0, 0, 5, 7, 5, paramEnumDirection);
/*      */       
/* 1259 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/* 1260 */         return null;
/*      */       }
/*      */       
/* 1263 */       return new WorldGenNetherPiece10(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1269 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/* 1271 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/* 1274 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1275 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 1, 0, 4, 1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1276 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 3, 0, 4, 3, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       
/* 1278 */       a(paramWorld, paramStructureBoundingBox, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/* 1280 */       a(paramWorld, paramStructureBoundingBox, 1, 2, 4, 4, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1281 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 4, 1, 4, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1282 */       a(paramWorld, paramStructureBoundingBox, 3, 3, 4, 3, 4, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/* 1284 */       if ((this.b) && 
/* 1285 */         (paramStructureBoundingBox.b(new BlockPosition(a(1, 3), d(2), b(1, 3))))) {
/* 1286 */         this.b = false;
/* 1287 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 1, 2, 3, a, 2 + paramRandom.nextInt(4));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1292 */       a(paramWorld, paramStructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/* 1295 */       for (int i = 0; i <= 4; i++) {
/* 1296 */         for (int j = 0; j <= 4; j++) {
/* 1297 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/* 1301 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static class WorldGenNetherPiece8
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     private boolean b;
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece8() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece8(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/* 1317 */       super();
/*      */       
/* 1319 */       this.m = paramEnumDirection;
/* 1320 */       this.l = paramStructureBoundingBox;
/*      */       
/* 1322 */       this.b = (paramRandom.nextInt(3) == 0);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/* 1327 */       super.b(paramNBTTagCompound);
/*      */       
/* 1329 */       this.b = paramNBTTagCompound.getBoolean("Chest");
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/* 1334 */       super.a(paramNBTTagCompound);
/*      */       
/* 1336 */       paramNBTTagCompound.setBoolean("Chest", this.b);
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/* 1341 */       b((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 0, 1, true);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece8 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/* 1345 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, 0, 0, 5, 7, 5, paramEnumDirection);
/*      */       
/* 1347 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/* 1348 */         return null;
/*      */       }
/*      */       
/* 1351 */       return new WorldGenNetherPiece8(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1357 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/* 1359 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/* 1362 */       a(paramWorld, paramStructureBoundingBox, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1363 */       a(paramWorld, paramStructureBoundingBox, 4, 3, 1, 4, 4, 1, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1364 */       a(paramWorld, paramStructureBoundingBox, 4, 3, 3, 4, 4, 3, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       
/* 1366 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/* 1368 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 4, 3, 5, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1369 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 4, 1, 4, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1370 */       a(paramWorld, paramStructureBoundingBox, 3, 3, 4, 3, 4, 4, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/* 1372 */       if ((this.b) && 
/* 1373 */         (paramStructureBoundingBox.b(new BlockPosition(a(3, 3), d(2), b(3, 3))))) {
/* 1374 */         this.b = false;
/* 1375 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 3, 2, 3, a, 2 + paramRandom.nextInt(4));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1380 */       a(paramWorld, paramStructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/* 1383 */       for (int i = 0; i <= 4; i++) {
/* 1384 */         for (int j = 0; j <= 4; j++) {
/* 1385 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i, -1, j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/* 1389 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenNetherPiece4
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     public WorldGenNetherPiece4() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece4(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/* 1403 */       super();
/*      */       
/* 1405 */       this.m = paramEnumDirection;
/* 1406 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/* 1411 */       a((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 1, 0, true);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece4 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/* 1415 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -7, 0, 5, 14, 10, paramEnumDirection);
/*      */       
/* 1417 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/* 1418 */         return null;
/*      */       }
/*      */       
/* 1421 */       return new WorldGenNetherPiece4(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1427 */       int i = a(Blocks.NETHER_BRICK_STAIRS, 2);
/* 1428 */       for (int j = 0; j <= 9; j++) {
/* 1429 */         int k = Math.max(1, 7 - j);
/* 1430 */         int m = Math.min(Math.max(k + 5, 14 - j), 13);
/* 1431 */         int n = j;
/*      */         
/*      */ 
/* 1434 */         a(paramWorld, paramStructureBoundingBox, 0, 0, n, 4, k, n, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */         
/* 1436 */         a(paramWorld, paramStructureBoundingBox, 1, k + 1, n, 3, m - 1, n, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 1437 */         if (j <= 6) {
/* 1438 */           a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(i), 1, k + 1, n, paramStructureBoundingBox);
/* 1439 */           a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(i), 2, k + 1, n, paramStructureBoundingBox);
/* 1440 */           a(paramWorld, Blocks.NETHER_BRICK_STAIRS.fromLegacyData(i), 3, k + 1, n, paramStructureBoundingBox);
/*      */         }
/*      */         
/* 1443 */         a(paramWorld, paramStructureBoundingBox, 0, m, n, 4, m, n, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */         
/* 1445 */         a(paramWorld, paramStructureBoundingBox, 0, k + 1, n, 0, m - 1, n, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1446 */         a(paramWorld, paramStructureBoundingBox, 4, k + 1, n, 4, m - 1, n, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1447 */         if ((j & 0x1) == 0) {
/* 1448 */           a(paramWorld, paramStructureBoundingBox, 0, k + 2, n, 0, k + 3, n, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1449 */           a(paramWorld, paramStructureBoundingBox, 4, k + 2, n, 4, k + 3, n, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */         }
/*      */         
/*      */ 
/* 1453 */         for (int i1 = 0; i1 <= 4; i1++) {
/* 1454 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), i1, -1, n, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/* 1458 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenNetherPiece5
/*      */     extends WorldGenNetherPieces.WorldGenNetherPiece
/*      */   {
/*      */     public WorldGenNetherPiece5() {}
/*      */     
/*      */ 
/*      */     public WorldGenNetherPiece5(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/* 1472 */       super();
/*      */       
/* 1474 */       this.m = paramEnumDirection;
/* 1475 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/* 1480 */       int i = 1;
/*      */       
/* 1482 */       if ((this.m == EnumDirection.WEST) || (this.m == EnumDirection.NORTH)) {
/* 1483 */         i = 5;
/*      */       }
/*      */       
/* 1486 */       b((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 0, i, paramRandom.nextInt(8) > 0);
/* 1487 */       c((WorldGenNetherPieces.WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 0, i, paramRandom.nextInt(8) > 0);
/*      */     }
/*      */     
/*      */     public static WorldGenNetherPiece5 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/* 1491 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -3, 0, 0, 9, 7, 9, paramEnumDirection);
/*      */       
/* 1493 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/* 1494 */         return null;
/*      */       }
/*      */       
/* 1497 */       return new WorldGenNetherPiece5(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1503 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 8, 1, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/* 1505 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 8, 5, 8, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/* 1507 */       a(paramWorld, paramStructureBoundingBox, 0, 6, 0, 8, 6, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/*      */       
/*      */ 
/* 1510 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 2, 5, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1511 */       a(paramWorld, paramStructureBoundingBox, 6, 2, 0, 8, 5, 0, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1512 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 0, 1, 4, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1513 */       a(paramWorld, paramStructureBoundingBox, 7, 3, 0, 7, 4, 0, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       
/*      */ 
/* 1516 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 4, 8, 2, 8, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1517 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 4, 2, 2, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 1518 */       a(paramWorld, paramStructureBoundingBox, 6, 1, 4, 7, 2, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/* 1521 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 8, 8, 3, 8, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1522 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 6, 0, 3, 7, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1523 */       a(paramWorld, paramStructureBoundingBox, 8, 3, 6, 8, 3, 7, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       
/*      */ 
/* 1526 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 4, 0, 5, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1527 */       a(paramWorld, paramStructureBoundingBox, 8, 3, 4, 8, 5, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1528 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 5, 2, 5, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1529 */       a(paramWorld, paramStructureBoundingBox, 6, 3, 5, 7, 5, 5, Blocks.NETHER_BRICK.getBlockData(), Blocks.NETHER_BRICK.getBlockData(), false);
/* 1530 */       a(paramWorld, paramStructureBoundingBox, 1, 4, 5, 1, 5, 5, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/* 1531 */       a(paramWorld, paramStructureBoundingBox, 7, 4, 5, 7, 5, 5, Blocks.NETHER_BRICK_FENCE.getBlockData(), Blocks.NETHER_BRICK_FENCE.getBlockData(), false);
/*      */       
/*      */ 
/* 1534 */       for (int i = 0; i <= 5; i++) {
/* 1535 */         for (int j = 0; j <= 8; j++) {
/* 1536 */           b(paramWorld, Blocks.NETHER_BRICK.getBlockData(), j, -1, i, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/* 1540 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenNetherPieces.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */