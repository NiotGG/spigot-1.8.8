/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
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
/*      */ public class WorldGenMonumentPieces
/*      */ {
/*      */   public static void a()
/*      */   {
/*   21 */     WorldGenFactory.a(WorldGenMonumentPiece1.class, "OMB");
/*   22 */     WorldGenFactory.a(WorldGenMonumentPiece2.class, "OMCR");
/*   23 */     WorldGenFactory.a(WorldGenMonumentPiece3.class, "OMDXR");
/*   24 */     WorldGenFactory.a(WorldGenMonumentPiece4.class, "OMDXYR");
/*   25 */     WorldGenFactory.a(WorldGenMonumentPiece5.class, "OMDYR");
/*   26 */     WorldGenFactory.a(WorldGenMonumentPiece6.class, "OMDYZR");
/*   27 */     WorldGenFactory.a(WorldGenMonumentPiece7.class, "OMDZR");
/*   28 */     WorldGenFactory.a(WorldGenMonumentPieceEntry.class, "OMEntry");
/*   29 */     WorldGenFactory.a(WorldGenMonumentPiecePenthouse.class, "OMPenthouse");
/*   30 */     WorldGenFactory.a(WorldGenMonumentPieceSimple.class, "OMSimple");
/*   31 */     WorldGenFactory.a(WorldGenMonumentPieceSimpleT.class, "OMSimpleT");
/*      */   }
/*      */   
/*      */ 
/*      */   public static abstract class WorldGenMonumentPiece
/*      */     extends StructurePiece
/*      */   {
/*   38 */     protected static final IBlockData a = Blocks.PRISMARINE.fromLegacyData(BlockPrismarine.b);
/*   39 */     protected static final IBlockData b = Blocks.PRISMARINE.fromLegacyData(BlockPrismarine.N);
/*   40 */     protected static final IBlockData c = Blocks.PRISMARINE.fromLegacyData(BlockPrismarine.O);
/*      */     
/*   42 */     protected static final IBlockData d = b;
/*      */     
/*   44 */     protected static final IBlockData e = Blocks.SEA_LANTERN.getBlockData();
/*      */     
/*      */ 
/*   47 */     protected static final IBlockData f = Blocks.WATER.getBlockData();
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
/*   58 */     protected static final int g = b(2, 0, 0);
/*   59 */     protected static final int h = b(2, 2, 0);
/*   60 */     protected static final int i = b(0, 1, 0);
/*   61 */     protected static final int j = b(4, 1, 0);
/*      */     
/*      */ 
/*      */     protected WorldGenMonumentPieces.WorldGenMonumentStateTracker k;
/*      */     
/*      */ 
/*      */ 
/*      */     protected static final int b(int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/*   70 */       return paramInt2 * 25 + paramInt3 * 5 + paramInt1;
/*      */     }
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
/*      */     public WorldGenMonumentPiece()
/*      */     {
/*   88 */       super();
/*      */     }
/*      */     
/*      */     public WorldGenMonumentPiece(int paramInt) {
/*   92 */       super();
/*      */     }
/*      */     
/*      */     public WorldGenMonumentPiece(EnumDirection paramEnumDirection, StructureBoundingBox paramStructureBoundingBox) {
/*   96 */       super();
/*   97 */       this.m = paramEnumDirection;
/*   98 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     protected WorldGenMonumentPiece(int paramInt1, EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, int paramInt2, int paramInt3, int paramInt4) {
/*  102 */       super();
/*  103 */       this.m = paramEnumDirection;
/*  104 */       this.k = paramWorldGenMonumentStateTracker;
/*      */       
/*  106 */       int m = paramWorldGenMonumentStateTracker.a;
/*  107 */       int n = m % 5;
/*  108 */       int i1 = m / 5 % 5;
/*  109 */       int i2 = m / 25;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  114 */       if ((paramEnumDirection == EnumDirection.NORTH) || (paramEnumDirection == EnumDirection.SOUTH)) {
/*  115 */         this.l = new StructureBoundingBox(0, 0, 0, paramInt2 * 8 - 1, paramInt3 * 4 - 1, paramInt4 * 8 - 1);
/*      */       }
/*      */       else {
/*  118 */         this.l = new StructureBoundingBox(0, 0, 0, paramInt4 * 8 - 1, paramInt3 * 4 - 1, paramInt2 * 8 - 1);
/*      */       }
/*      */       
/*  121 */       switch (WorldGenMonumentPieces.1.a[paramEnumDirection.ordinal()]) {
/*      */       case 1: 
/*  123 */         this.l.a(n * 8, i2 * 4, -(i1 + paramInt4) * 8 + 1);
/*  124 */         break;
/*      */       
/*      */       case 2: 
/*  127 */         this.l.a(n * 8, i2 * 4, i1 * 8);
/*  128 */         break;
/*      */       case 3: 
/*  130 */         this.l.a(-(i1 + paramInt4) * 8 + 1, i2 * 4, n * 8);
/*  131 */         break;
/*      */       default: 
/*  133 */         this.l.a(i1 * 8, i2 * 4, n * 8);
/*      */       }
/*      */       
/*      */     }
/*      */     
/*      */ 
/*      */     protected void a(NBTTagCompound paramNBTTagCompound) {}
/*      */     
/*      */ 
/*      */     protected void b(NBTTagCompound paramNBTTagCompound) {}
/*      */     
/*      */ 
/*      */     protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean)
/*      */     {
/*  147 */       for (int m = paramInt2; m <= paramInt5; m++) {
/*  148 */         for (int n = paramInt1; n <= paramInt4; n++) {
/*  149 */           for (int i1 = paramInt3; i1 <= paramInt6; i1++) {
/*  150 */             if ((!paramBoolean) || (a(paramWorld, n, m, i1, paramStructureBoundingBox).getBlock().getMaterial() != Material.AIR))
/*      */             {
/*      */ 
/*  153 */               if (d(m) >= paramWorld.F()) {
/*  154 */                 a(paramWorld, Blocks.AIR.getBlockData(), n, m, i1, paramStructureBoundingBox);
/*      */               } else
/*  156 */                 a(paramWorld, f, n, m, i1, paramStructureBoundingBox);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, boolean paramBoolean) {
/*  164 */       if (paramBoolean) {
/*  165 */         a(paramWorld, paramStructureBoundingBox, paramInt1 + 0, 0, paramInt2 + 0, paramInt1 + 2, 0, paramInt2 + 8 - 1, a, a, false);
/*  166 */         a(paramWorld, paramStructureBoundingBox, paramInt1 + 5, 0, paramInt2 + 0, paramInt1 + 8 - 1, 0, paramInt2 + 8 - 1, a, a, false);
/*  167 */         a(paramWorld, paramStructureBoundingBox, paramInt1 + 3, 0, paramInt2 + 0, paramInt1 + 4, 0, paramInt2 + 2, a, a, false);
/*  168 */         a(paramWorld, paramStructureBoundingBox, paramInt1 + 3, 0, paramInt2 + 5, paramInt1 + 4, 0, paramInt2 + 8 - 1, a, a, false);
/*      */         
/*  170 */         a(paramWorld, paramStructureBoundingBox, paramInt1 + 3, 0, paramInt2 + 2, paramInt1 + 4, 0, paramInt2 + 2, b, b, false);
/*  171 */         a(paramWorld, paramStructureBoundingBox, paramInt1 + 3, 0, paramInt2 + 5, paramInt1 + 4, 0, paramInt2 + 5, b, b, false);
/*  172 */         a(paramWorld, paramStructureBoundingBox, paramInt1 + 2, 0, paramInt2 + 3, paramInt1 + 2, 0, paramInt2 + 4, b, b, false);
/*  173 */         a(paramWorld, paramStructureBoundingBox, paramInt1 + 5, 0, paramInt2 + 3, paramInt1 + 5, 0, paramInt2 + 4, b, b, false);
/*      */       } else {
/*  175 */         a(paramWorld, paramStructureBoundingBox, paramInt1 + 0, 0, paramInt2 + 0, paramInt1 + 8 - 1, 0, paramInt2 + 8 - 1, a, a, false);
/*      */       }
/*      */     }
/*      */     
/*      */     protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, IBlockData paramIBlockData) {
/*  180 */       for (int m = paramInt2; m <= paramInt5; m++) {
/*  181 */         for (int n = paramInt1; n <= paramInt4; n++) {
/*  182 */           for (int i1 = paramInt3; i1 <= paramInt6; i1++) {
/*  183 */             if (a(paramWorld, n, m, i1, paramStructureBoundingBox) == f)
/*      */             {
/*      */ 
/*  186 */               a(paramWorld, paramIBlockData, n, m, i1, paramStructureBoundingBox); }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     protected boolean a(StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  193 */       int m = a(paramInt1, paramInt2);
/*  194 */       int n = b(paramInt1, paramInt2);
/*  195 */       int i1 = a(paramInt3, paramInt4);
/*  196 */       int i2 = b(paramInt3, paramInt4);
/*  197 */       return paramStructureBoundingBox.a(Math.min(m, i1), Math.min(n, i2), Math.max(m, i1), Math.max(n, i2));
/*      */     }
/*      */     
/*      */     protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3) {
/*  201 */       int m = a(paramInt1, paramInt3);
/*  202 */       int n = d(paramInt2);
/*  203 */       int i1 = b(paramInt1, paramInt3);
/*      */       
/*  205 */       if (paramStructureBoundingBox.b(new BlockPosition(m, n, i1))) {
/*  206 */         EntityGuardian localEntityGuardian = new EntityGuardian(paramWorld);
/*  207 */         localEntityGuardian.setElder(true);
/*  208 */         localEntityGuardian.heal(localEntityGuardian.getMaxHealth());
/*  209 */         localEntityGuardian.setPositionRotation(m + 0.5D, n, i1 + 0.5D, 0.0F, 0.0F);
/*  210 */         localEntityGuardian.prepare(paramWorld.E(new BlockPosition(localEntityGuardian)), null);
/*  211 */         paramWorld.addEntity(localEntityGuardian);
/*  212 */         return true;
/*      */       }
/*  214 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class WorldGenMonumentPiece1
/*      */     extends WorldGenMonumentPieces.WorldGenMonumentPiece
/*      */   {
/*      */     private WorldGenMonumentPieces.WorldGenMonumentStateTracker o;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private WorldGenMonumentPieces.WorldGenMonumentStateTracker p;
/*      */     
/*      */ 
/*      */ 
/*  234 */     private List<WorldGenMonumentPieces.WorldGenMonumentPiece> q = Lists.newArrayList();
/*      */     
/*      */ 
/*      */     public WorldGenMonumentPiece1() {}
/*      */     
/*      */ 
/*      */     public WorldGenMonumentPiece1(Random paramRandom, int paramInt1, int paramInt2, EnumDirection paramEnumDirection)
/*      */     {
/*  242 */       super();
/*      */       
/*  244 */       this.m = paramEnumDirection;
/*      */       
/*  246 */       switch (WorldGenMonumentPieces.1.a[this.m.ordinal()]) {
/*      */       case 1: 
/*      */       case 2: 
/*  249 */         this.l = new StructureBoundingBox(paramInt1, 39, paramInt2, paramInt1 + 58 - 1, 61, paramInt2 + 58 - 1);
/*  250 */         break;
/*      */       default: 
/*  252 */         this.l = new StructureBoundingBox(paramInt1, 39, paramInt2, paramInt1 + 58 - 1, 61, paramInt2 + 58 - 1);
/*      */       }
/*      */       
/*      */       
/*  256 */       List localList = a(paramRandom);
/*      */       
/*  258 */       this.o.d = true;
/*  259 */       this.q.add(new WorldGenMonumentPieces.WorldGenMonumentPieceEntry(this.m, this.o));
/*  260 */       this.q.add(new WorldGenMonumentPieces.WorldGenMonumentPiece2(this.m, this.p, paramRandom));
/*      */       
/*  262 */       ArrayList localArrayList = Lists.newArrayList();
/*  263 */       localArrayList.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector6(null));
/*  264 */       localArrayList.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector4(null));
/*  265 */       localArrayList.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector3(null));
/*  266 */       localArrayList.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector7(null));
/*  267 */       localArrayList.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector5(null));
/*  268 */       localArrayList.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector1(null));
/*  269 */       localArrayList.add(new WorldGenMonumentPieces.WorldGenMonumentPieceSelector2(null));
/*      */       
/*  271 */       for (Iterator localIterator1 = localList.iterator(); localIterator1.hasNext();) { localWorldGenMonumentStateTracker = (WorldGenMonumentPieces.WorldGenMonumentStateTracker)localIterator1.next();
/*  272 */         if ((!localWorldGenMonumentStateTracker.d) && (!localWorldGenMonumentStateTracker.b()))
/*      */         {
/*  274 */           for (localIterator2 = localArrayList.iterator(); localIterator2.hasNext();) { localObject1 = (WorldGenMonumentPieces.IWorldGenMonumentPieceSelector)localIterator2.next();
/*  275 */             if (((WorldGenMonumentPieces.IWorldGenMonumentPieceSelector)localObject1).a(localWorldGenMonumentStateTracker)) {
/*  276 */               this.q.add(((WorldGenMonumentPieces.IWorldGenMonumentPieceSelector)localObject1).a(this.m, localWorldGenMonumentStateTracker, paramRandom));
/*  277 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker;
/*      */       Iterator localIterator2;
/*  284 */       int i = this.l.b;
/*  285 */       int j = a(9, 22);
/*  286 */       int k = b(9, 22);
/*  287 */       for (Object localObject1 = this.q.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (WorldGenMonumentPieces.WorldGenMonumentPiece)((Iterator)localObject1).next();
/*  288 */         ((WorldGenMonumentPieces.WorldGenMonumentPiece)localObject2).c().a(j, i, k);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  293 */       localObject1 = StructureBoundingBox.a(a(1, 1), d(1), b(1, 1), a(23, 21), d(8), b(23, 21));
/*  294 */       Object localObject2 = StructureBoundingBox.a(a(34, 1), d(1), b(34, 1), a(56, 21), d(8), b(56, 21));
/*  295 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(a(22, 22), d(13), b(22, 22), a(35, 35), d(17), b(35, 35));
/*      */       
/*      */ 
/*  298 */       int m = paramRandom.nextInt();
/*  299 */       this.q.add(new WorldGenMonumentPieces.WorldGenMonumentPiece8(this.m, (StructureBoundingBox)localObject1, m++));
/*  300 */       this.q.add(new WorldGenMonumentPieces.WorldGenMonumentPiece8(this.m, (StructureBoundingBox)localObject2, m++));
/*      */       
/*  302 */       this.q.add(new WorldGenMonumentPieces.WorldGenMonumentPiecePenthouse(this.m, localStructureBoundingBox));
/*      */     }
/*      */     
/*      */     private List<WorldGenMonumentPieces.WorldGenMonumentStateTracker> a(Random paramRandom)
/*      */     {
/*  307 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker[] arrayOfWorldGenMonumentStateTracker = new WorldGenMonumentPieces.WorldGenMonumentStateTracker[75];
/*      */       int j;
/*  309 */       int k; int m; for (int i = 0; i < 5; i++) {
/*  310 */         for (j = 0; j < 4; j++) {
/*  311 */           k = 0;
/*  312 */           m = b(i, k, j);
/*  313 */           arrayOfWorldGenMonumentStateTracker[m] = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(m);
/*      */         }
/*      */       }
/*  316 */       for (i = 0; i < 5; i++) {
/*  317 */         for (j = 0; j < 4; j++) {
/*  318 */           k = 1;
/*  319 */           m = b(i, k, j);
/*  320 */           arrayOfWorldGenMonumentStateTracker[m] = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(m);
/*      */         }
/*      */       }
/*  323 */       for (i = 1; i < 4; i++) {
/*  324 */         for (j = 0; j < 2; j++) {
/*  325 */           k = 2;
/*  326 */           m = b(i, k, j);
/*  327 */           arrayOfWorldGenMonumentStateTracker[m] = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(m);
/*      */         }
/*      */       }
/*      */       
/*  331 */       this.o = arrayOfWorldGenMonumentStateTracker[g];
/*      */       EnumDirection localEnumDirection;
/*  333 */       int i4; int i5; int i6; for (i = 0; i < 5; i++) {
/*  334 */         for (j = 0; j < 5; j++) {
/*  335 */           for (k = 0; k < 3; k++) {
/*  336 */             m = b(i, k, j);
/*  337 */             if (arrayOfWorldGenMonumentStateTracker[m] != null)
/*      */             {
/*      */ 
/*  340 */               for (localEnumDirection : EnumDirection.values()) {
/*  341 */                 i4 = i + localEnumDirection.getAdjacentX();
/*  342 */                 i5 = k + localEnumDirection.getAdjacentY();
/*  343 */                 i6 = j + localEnumDirection.getAdjacentZ();
/*  344 */                 if ((i4 >= 0) && (i4 < 5) && (i6 >= 0) && (i6 < 5) && (i5 >= 0) && (i5 < 3)) {
/*  345 */                   int i7 = b(i4, i5, i6);
/*  346 */                   if (arrayOfWorldGenMonumentStateTracker[i7] != null)
/*      */                   {
/*      */ 
/*  349 */                     if (i6 != j) {
/*  350 */                       arrayOfWorldGenMonumentStateTracker[m].a(localEnumDirection.opposite(), arrayOfWorldGenMonumentStateTracker[i7]);
/*      */                     } else
/*  352 */                       arrayOfWorldGenMonumentStateTracker[m].a(localEnumDirection, arrayOfWorldGenMonumentStateTracker[i7]);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker1;
/*  361 */       arrayOfWorldGenMonumentStateTracker[h].a(EnumDirection.UP, localWorldGenMonumentStateTracker1 = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(1003));
/*  362 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker2; arrayOfWorldGenMonumentStateTracker[i].a(EnumDirection.SOUTH, localWorldGenMonumentStateTracker2 = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(1001));
/*  363 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker3; arrayOfWorldGenMonumentStateTracker[j].a(EnumDirection.SOUTH, localWorldGenMonumentStateTracker3 = new WorldGenMonumentPieces.WorldGenMonumentStateTracker(1002));
/*  364 */       localWorldGenMonumentStateTracker1.d = true;
/*  365 */       localWorldGenMonumentStateTracker2.d = true;
/*  366 */       localWorldGenMonumentStateTracker3.d = true;
/*  367 */       this.o.e = true;
/*      */       
/*      */ 
/*  370 */       this.p = arrayOfWorldGenMonumentStateTracker[b(paramRandom.nextInt(4), 0, 2)];
/*  371 */       this.p.d = true;
/*  372 */       this.p.b[EnumDirection.EAST.a()].d = true;
/*  373 */       this.p.b[EnumDirection.NORTH.a()].d = true;
/*  374 */       this.p.b[EnumDirection.EAST.a()].b[EnumDirection.NORTH.a()].d = true;
/*  375 */       this.p.b[EnumDirection.UP.a()].d = true;
/*  376 */       this.p.b[EnumDirection.EAST.a()].b[EnumDirection.UP.a()].d = true;
/*  377 */       this.p.b[EnumDirection.NORTH.a()].b[EnumDirection.UP.a()].d = true;
/*  378 */       this.p.b[EnumDirection.EAST.a()].b[EnumDirection.NORTH.a()].b[EnumDirection.UP.a()].d = true;
/*      */       
/*  380 */       ArrayList localArrayList = Lists.newArrayList();
/*  381 */       for (localEnumDirection : arrayOfWorldGenMonumentStateTracker) {
/*  382 */         if (localEnumDirection != null) {
/*  383 */           localEnumDirection.a();
/*  384 */           localArrayList.add(localEnumDirection);
/*      */         }
/*      */       }
/*  387 */       localWorldGenMonumentStateTracker1.a();
/*      */       
/*  389 */       Collections.shuffle(localArrayList, paramRandom);
/*  390 */       int n = 1;
/*  391 */       for (WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker4 : localArrayList)
/*      */       {
/*  393 */         int i3 = 0;
/*  394 */         i4 = 0;
/*  395 */         while ((i3 < 2) && (i4 < 5)) {
/*  396 */           i4++;
/*      */           
/*  398 */           i5 = paramRandom.nextInt(6);
/*  399 */           if (localWorldGenMonumentStateTracker4.c[i5] != 0) {
/*  400 */             i6 = EnumDirection.fromType1(i5).opposite().a();
/*      */             
/*      */ 
/*  403 */             localWorldGenMonumentStateTracker4.c[i5] = false;
/*  404 */             localWorldGenMonumentStateTracker4.b[i5].c[i6] = false;
/*      */             
/*  406 */             if ((localWorldGenMonumentStateTracker4.a(n++)) && (localWorldGenMonumentStateTracker4.b[i5].a(n++))) {
/*  407 */               i3++;
/*      */             }
/*      */             else {
/*  410 */               localWorldGenMonumentStateTracker4.c[i5] = true;
/*  411 */               localWorldGenMonumentStateTracker4.b[i5].c[i6] = true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  416 */       localArrayList.add(localWorldGenMonumentStateTracker1);
/*  417 */       localArrayList.add(localWorldGenMonumentStateTracker2);
/*  418 */       localArrayList.add(localWorldGenMonumentStateTracker3);
/*      */       
/*  420 */       return localArrayList;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  426 */       int i = Math.max(paramWorld.F(), 64) - this.l.b;
/*      */       
/*  428 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 58, i, 58, false);
/*      */       
/*      */ 
/*  431 */       a(false, 0, paramWorld, paramRandom, paramStructureBoundingBox);
/*      */       
/*      */ 
/*  434 */       a(true, 33, paramWorld, paramRandom, paramStructureBoundingBox);
/*      */       
/*      */ 
/*  437 */       b(paramWorld, paramRandom, paramStructureBoundingBox);
/*      */       
/*  439 */       c(paramWorld, paramRandom, paramStructureBoundingBox);
/*  440 */       d(paramWorld, paramRandom, paramStructureBoundingBox);
/*      */       
/*  442 */       e(paramWorld, paramRandom, paramStructureBoundingBox);
/*  443 */       f(paramWorld, paramRandom, paramStructureBoundingBox);
/*  444 */       g(paramWorld, paramRandom, paramStructureBoundingBox);
/*      */       
/*      */       int k;
/*  447 */       for (int j = 0; j < 7; j++) {
/*  448 */         for (k = 0; k < 7;) {
/*  449 */           if ((k == 0) && (j == 3))
/*      */           {
/*  451 */             k = 6;
/*      */           }
/*      */           
/*  454 */           int m = j * 9;
/*  455 */           int n = k * 9;
/*  456 */           for (int i1 = 0; i1 < 4; i1++) {
/*  457 */             for (int i2 = 0; i2 < 4; i2++) {
/*  458 */               a(paramWorld, b, m + i1, 0, n + i2, paramStructureBoundingBox);
/*  459 */               b(paramWorld, b, m + i1, -1, n + i2, paramStructureBoundingBox);
/*      */             }
/*      */           }
/*      */           
/*  463 */           if ((j == 0) || (j == 6)) {
/*  464 */             k++;
/*      */           } else {
/*  466 */             k += 6;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  472 */       for (j = 0; j < 5; j++) {
/*  473 */         a(paramWorld, paramStructureBoundingBox, -1 - j, 0 + j * 2, -1 - j, -1 - j, 23, 58 + j, false);
/*  474 */         a(paramWorld, paramStructureBoundingBox, 58 + j, 0 + j * 2, -1 - j, 58 + j, 23, 58 + j, false);
/*  475 */         a(paramWorld, paramStructureBoundingBox, 0 - j, 0 + j * 2, -1 - j, 57 + j, 23, -1 - j, false);
/*  476 */         a(paramWorld, paramStructureBoundingBox, 0 - j, 0 + j * 2, 58 + j, 57 + j, 23, 58 + j, false);
/*      */       }
/*      */       
/*  479 */       for (WorldGenMonumentPieces.WorldGenMonumentPiece localWorldGenMonumentPiece : this.q) {
/*  480 */         if (localWorldGenMonumentPiece.c().a(paramStructureBoundingBox)) {
/*  481 */           localWorldGenMonumentPiece.a(paramWorld, paramRandom, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*  485 */       return true;
/*      */     }
/*      */     
/*      */     private void a(boolean paramBoolean, int paramInt, World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  490 */       int i = 24;
/*  491 */       if (a(paramStructureBoundingBox, paramInt, 0, paramInt + 23, 20)) {
/*  492 */         a(paramWorld, paramStructureBoundingBox, paramInt + 0, 0, 0, paramInt + 24, 0, 20, a, a, false);
/*      */         
/*  494 */         a(paramWorld, paramStructureBoundingBox, paramInt + 0, 1, 0, paramInt + 24, 10, 20, false);
/*      */         
/*  496 */         for (int j = 0; j < 4; j++) {
/*  497 */           a(paramWorld, paramStructureBoundingBox, paramInt + j, j + 1, j, paramInt + j, j + 1, 20, b, b, false);
/*  498 */           a(paramWorld, paramStructureBoundingBox, paramInt + j + 7, j + 5, j + 7, paramInt + j + 7, j + 5, 20, b, b, false);
/*  499 */           a(paramWorld, paramStructureBoundingBox, paramInt + 17 - j, j + 5, j + 7, paramInt + 17 - j, j + 5, 20, b, b, false);
/*  500 */           a(paramWorld, paramStructureBoundingBox, paramInt + 24 - j, j + 1, j, paramInt + 24 - j, j + 1, 20, b, b, false);
/*      */           
/*  502 */           a(paramWorld, paramStructureBoundingBox, paramInt + j + 1, j + 1, j, paramInt + 23 - j, j + 1, j, b, b, false);
/*  503 */           a(paramWorld, paramStructureBoundingBox, paramInt + j + 8, j + 5, j + 7, paramInt + 16 - j, j + 5, j + 7, b, b, false);
/*      */         }
/*  505 */         a(paramWorld, paramStructureBoundingBox, paramInt + 4, 4, 4, paramInt + 6, 4, 20, a, a, false);
/*  506 */         a(paramWorld, paramStructureBoundingBox, paramInt + 7, 4, 4, paramInt + 17, 4, 6, a, a, false);
/*  507 */         a(paramWorld, paramStructureBoundingBox, paramInt + 18, 4, 4, paramInt + 20, 4, 20, a, a, false);
/*  508 */         a(paramWorld, paramStructureBoundingBox, paramInt + 11, 8, 11, paramInt + 13, 8, 20, a, a, false);
/*  509 */         a(paramWorld, d, paramInt + 12, 9, 12, paramStructureBoundingBox);
/*  510 */         a(paramWorld, d, paramInt + 12, 9, 15, paramStructureBoundingBox);
/*  511 */         a(paramWorld, d, paramInt + 12, 9, 18, paramStructureBoundingBox);
/*      */         
/*  513 */         j = paramBoolean ? paramInt + 19 : paramInt + 5;
/*  514 */         int k = paramBoolean ? paramInt + 5 : paramInt + 19;
/*  515 */         for (int m = 20; m >= 5; m -= 3) {
/*  516 */           a(paramWorld, d, j, 5, m, paramStructureBoundingBox);
/*      */         }
/*  518 */         for (m = 19; m >= 7; m -= 3) {
/*  519 */           a(paramWorld, d, k, 5, m, paramStructureBoundingBox);
/*      */         }
/*  521 */         for (m = 0; m < 4; m++) {
/*  522 */           int n = paramBoolean ? paramInt + (24 - (17 - m * 3)) : paramInt + 17 - m * 3;
/*  523 */           a(paramWorld, d, n, 5, 5, paramStructureBoundingBox);
/*      */         }
/*  525 */         a(paramWorld, d, k, 5, 5, paramStructureBoundingBox);
/*      */         
/*      */ 
/*  528 */         a(paramWorld, paramStructureBoundingBox, paramInt + 11, 1, 12, paramInt + 13, 7, 12, a, a, false);
/*  529 */         a(paramWorld, paramStructureBoundingBox, paramInt + 12, 1, 11, paramInt + 12, 7, 13, a, a, false);
/*      */       }
/*      */     }
/*      */     
/*      */     private void b(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  535 */       if (a(paramStructureBoundingBox, 22, 5, 35, 17))
/*      */       {
/*  537 */         a(paramWorld, paramStructureBoundingBox, 25, 0, 0, 32, 8, 20, false);
/*      */         
/*      */ 
/*  540 */         for (int i = 0; i < 4; i++) {
/*  541 */           a(paramWorld, paramStructureBoundingBox, 24, 2, 5 + i * 4, 24, 4, 5 + i * 4, b, b, false);
/*  542 */           a(paramWorld, paramStructureBoundingBox, 22, 4, 5 + i * 4, 23, 4, 5 + i * 4, b, b, false);
/*  543 */           a(paramWorld, b, 25, 5, 5 + i * 4, paramStructureBoundingBox);
/*  544 */           a(paramWorld, b, 26, 6, 5 + i * 4, paramStructureBoundingBox);
/*  545 */           a(paramWorld, e, 26, 5, 5 + i * 4, paramStructureBoundingBox);
/*      */           
/*  547 */           a(paramWorld, paramStructureBoundingBox, 33, 2, 5 + i * 4, 33, 4, 5 + i * 4, b, b, false);
/*  548 */           a(paramWorld, paramStructureBoundingBox, 34, 4, 5 + i * 4, 35, 4, 5 + i * 4, b, b, false);
/*  549 */           a(paramWorld, b, 32, 5, 5 + i * 4, paramStructureBoundingBox);
/*  550 */           a(paramWorld, b, 31, 6, 5 + i * 4, paramStructureBoundingBox);
/*  551 */           a(paramWorld, e, 31, 5, 5 + i * 4, paramStructureBoundingBox);
/*      */           
/*  553 */           a(paramWorld, paramStructureBoundingBox, 27, 6, 5 + i * 4, 30, 6, 5 + i * 4, a, a, false);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     private void c(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  561 */       if (a(paramStructureBoundingBox, 15, 20, 42, 21)) {
/*  562 */         a(paramWorld, paramStructureBoundingBox, 15, 0, 21, 42, 0, 21, a, a, false);
/*      */         
/*  564 */         a(paramWorld, paramStructureBoundingBox, 26, 1, 21, 31, 3, 21, false);
/*      */         
/*      */ 
/*      */ 
/*  568 */         a(paramWorld, paramStructureBoundingBox, 21, 12, 21, 36, 12, 21, a, a, false);
/*  569 */         a(paramWorld, paramStructureBoundingBox, 17, 11, 21, 40, 11, 21, a, a, false);
/*  570 */         a(paramWorld, paramStructureBoundingBox, 16, 10, 21, 41, 10, 21, a, a, false);
/*  571 */         a(paramWorld, paramStructureBoundingBox, 15, 7, 21, 42, 9, 21, a, a, false);
/*  572 */         a(paramWorld, paramStructureBoundingBox, 16, 6, 21, 41, 6, 21, a, a, false);
/*  573 */         a(paramWorld, paramStructureBoundingBox, 17, 5, 21, 40, 5, 21, a, a, false);
/*  574 */         a(paramWorld, paramStructureBoundingBox, 21, 4, 21, 36, 4, 21, a, a, false);
/*  575 */         a(paramWorld, paramStructureBoundingBox, 22, 3, 21, 26, 3, 21, a, a, false);
/*  576 */         a(paramWorld, paramStructureBoundingBox, 31, 3, 21, 35, 3, 21, a, a, false);
/*  577 */         a(paramWorld, paramStructureBoundingBox, 23, 2, 21, 25, 2, 21, a, a, false);
/*  578 */         a(paramWorld, paramStructureBoundingBox, 32, 2, 21, 34, 2, 21, a, a, false);
/*      */         
/*      */ 
/*  581 */         a(paramWorld, paramStructureBoundingBox, 28, 4, 20, 29, 4, 21, b, b, false);
/*  582 */         a(paramWorld, b, 27, 3, 21, paramStructureBoundingBox);
/*  583 */         a(paramWorld, b, 30, 3, 21, paramStructureBoundingBox);
/*  584 */         a(paramWorld, b, 26, 2, 21, paramStructureBoundingBox);
/*  585 */         a(paramWorld, b, 31, 2, 21, paramStructureBoundingBox);
/*  586 */         a(paramWorld, b, 25, 1, 21, paramStructureBoundingBox);
/*  587 */         a(paramWorld, b, 32, 1, 21, paramStructureBoundingBox);
/*  588 */         for (int i = 0; i < 7; i++) {
/*  589 */           a(paramWorld, c, 28 - i, 6 + i, 21, paramStructureBoundingBox);
/*  590 */           a(paramWorld, c, 29 + i, 6 + i, 21, paramStructureBoundingBox);
/*      */         }
/*  592 */         for (i = 0; i < 4; i++) {
/*  593 */           a(paramWorld, c, 28 - i, 9 + i, 21, paramStructureBoundingBox);
/*  594 */           a(paramWorld, c, 29 + i, 9 + i, 21, paramStructureBoundingBox);
/*      */         }
/*  596 */         a(paramWorld, c, 28, 12, 21, paramStructureBoundingBox);
/*  597 */         a(paramWorld, c, 29, 12, 21, paramStructureBoundingBox);
/*  598 */         for (i = 0; i < 3; i++) {
/*  599 */           a(paramWorld, c, 22 - i * 2, 8, 21, paramStructureBoundingBox);
/*  600 */           a(paramWorld, c, 22 - i * 2, 9, 21, paramStructureBoundingBox);
/*      */           
/*  602 */           a(paramWorld, c, 35 + i * 2, 8, 21, paramStructureBoundingBox);
/*  603 */           a(paramWorld, c, 35 + i * 2, 9, 21, paramStructureBoundingBox);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  608 */         a(paramWorld, paramStructureBoundingBox, 15, 13, 21, 42, 15, 21, false);
/*  609 */         a(paramWorld, paramStructureBoundingBox, 15, 1, 21, 15, 6, 21, false);
/*  610 */         a(paramWorld, paramStructureBoundingBox, 16, 1, 21, 16, 5, 21, false);
/*  611 */         a(paramWorld, paramStructureBoundingBox, 17, 1, 21, 20, 4, 21, false);
/*  612 */         a(paramWorld, paramStructureBoundingBox, 21, 1, 21, 21, 3, 21, false);
/*  613 */         a(paramWorld, paramStructureBoundingBox, 22, 1, 21, 22, 2, 21, false);
/*  614 */         a(paramWorld, paramStructureBoundingBox, 23, 1, 21, 24, 1, 21, false);
/*  615 */         a(paramWorld, paramStructureBoundingBox, 42, 1, 21, 42, 6, 21, false);
/*  616 */         a(paramWorld, paramStructureBoundingBox, 41, 1, 21, 41, 5, 21, false);
/*  617 */         a(paramWorld, paramStructureBoundingBox, 37, 1, 21, 40, 4, 21, false);
/*  618 */         a(paramWorld, paramStructureBoundingBox, 36, 1, 21, 36, 3, 21, false);
/*  619 */         a(paramWorld, paramStructureBoundingBox, 33, 1, 21, 34, 1, 21, false);
/*  620 */         a(paramWorld, paramStructureBoundingBox, 35, 1, 21, 35, 2, 21, false);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void d(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  628 */       if (a(paramStructureBoundingBox, 21, 21, 36, 36)) {
/*  629 */         a(paramWorld, paramStructureBoundingBox, 21, 0, 22, 36, 0, 36, a, a, false);
/*      */         
/*      */ 
/*      */ 
/*  633 */         a(paramWorld, paramStructureBoundingBox, 21, 1, 22, 36, 23, 36, false);
/*      */         
/*      */ 
/*  636 */         for (int i = 0; i < 4; i++) {
/*  637 */           a(paramWorld, paramStructureBoundingBox, 21 + i, 13 + i, 21 + i, 36 - i, 13 + i, 21 + i, b, b, false);
/*  638 */           a(paramWorld, paramStructureBoundingBox, 21 + i, 13 + i, 36 - i, 36 - i, 13 + i, 36 - i, b, b, false);
/*  639 */           a(paramWorld, paramStructureBoundingBox, 21 + i, 13 + i, 22 + i, 21 + i, 13 + i, 35 - i, b, b, false);
/*  640 */           a(paramWorld, paramStructureBoundingBox, 36 - i, 13 + i, 22 + i, 36 - i, 13 + i, 35 - i, b, b, false);
/*      */         }
/*  642 */         a(paramWorld, paramStructureBoundingBox, 25, 16, 25, 32, 16, 32, a, a, false);
/*  643 */         a(paramWorld, paramStructureBoundingBox, 25, 17, 25, 25, 19, 25, b, b, false);
/*  644 */         a(paramWorld, paramStructureBoundingBox, 32, 17, 25, 32, 19, 25, b, b, false);
/*  645 */         a(paramWorld, paramStructureBoundingBox, 25, 17, 32, 25, 19, 32, b, b, false);
/*  646 */         a(paramWorld, paramStructureBoundingBox, 32, 17, 32, 32, 19, 32, b, b, false);
/*      */         
/*  648 */         a(paramWorld, b, 26, 20, 26, paramStructureBoundingBox);
/*  649 */         a(paramWorld, b, 27, 21, 27, paramStructureBoundingBox);
/*  650 */         a(paramWorld, e, 27, 20, 27, paramStructureBoundingBox);
/*  651 */         a(paramWorld, b, 26, 20, 31, paramStructureBoundingBox);
/*  652 */         a(paramWorld, b, 27, 21, 30, paramStructureBoundingBox);
/*  653 */         a(paramWorld, e, 27, 20, 30, paramStructureBoundingBox);
/*  654 */         a(paramWorld, b, 31, 20, 31, paramStructureBoundingBox);
/*  655 */         a(paramWorld, b, 30, 21, 30, paramStructureBoundingBox);
/*  656 */         a(paramWorld, e, 30, 20, 30, paramStructureBoundingBox);
/*  657 */         a(paramWorld, b, 31, 20, 26, paramStructureBoundingBox);
/*  658 */         a(paramWorld, b, 30, 21, 27, paramStructureBoundingBox);
/*  659 */         a(paramWorld, e, 30, 20, 27, paramStructureBoundingBox);
/*      */         
/*  661 */         a(paramWorld, paramStructureBoundingBox, 28, 21, 27, 29, 21, 27, a, a, false);
/*  662 */         a(paramWorld, paramStructureBoundingBox, 27, 21, 28, 27, 21, 29, a, a, false);
/*  663 */         a(paramWorld, paramStructureBoundingBox, 28, 21, 30, 29, 21, 30, a, a, false);
/*  664 */         a(paramWorld, paramStructureBoundingBox, 30, 21, 28, 30, 21, 29, a, a, false);
/*      */       }
/*      */     }
/*      */     
/*      */     private void e(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*      */       int i;
/*  671 */       if (a(paramStructureBoundingBox, 0, 21, 6, 58)) {
/*  672 */         a(paramWorld, paramStructureBoundingBox, 0, 0, 21, 6, 0, 57, a, a, false);
/*      */         
/*  674 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 21, 6, 7, 57, false);
/*      */         
/*      */ 
/*  677 */         a(paramWorld, paramStructureBoundingBox, 4, 4, 21, 6, 4, 53, a, a, false);
/*  678 */         for (i = 0; i < 4; i++) {
/*  679 */           a(paramWorld, paramStructureBoundingBox, i, i + 1, 21, i, i + 1, 57 - i, b, b, false);
/*      */         }
/*  681 */         for (i = 23; i < 53; i += 3) {
/*  682 */           a(paramWorld, d, 5, 5, i, paramStructureBoundingBox);
/*      */         }
/*  684 */         a(paramWorld, d, 5, 5, 52, paramStructureBoundingBox);
/*      */         
/*  686 */         for (i = 0; i < 4; i++) {
/*  687 */           a(paramWorld, paramStructureBoundingBox, i, i + 1, 21, i, i + 1, 57 - i, b, b, false);
/*      */         }
/*      */         
/*  690 */         a(paramWorld, paramStructureBoundingBox, 4, 1, 52, 6, 3, 52, a, a, false);
/*  691 */         a(paramWorld, paramStructureBoundingBox, 5, 1, 51, 5, 3, 53, a, a, false);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  696 */       if (a(paramStructureBoundingBox, 51, 21, 58, 58)) {
/*  697 */         a(paramWorld, paramStructureBoundingBox, 51, 0, 21, 57, 0, 57, a, a, false);
/*      */         
/*  699 */         a(paramWorld, paramStructureBoundingBox, 51, 1, 21, 57, 7, 57, false);
/*      */         
/*      */ 
/*  702 */         a(paramWorld, paramStructureBoundingBox, 51, 4, 21, 53, 4, 53, a, a, false);
/*  703 */         for (i = 0; i < 4; i++) {
/*  704 */           a(paramWorld, paramStructureBoundingBox, 57 - i, i + 1, 21, 57 - i, i + 1, 57 - i, b, b, false);
/*      */         }
/*  706 */         for (i = 23; i < 53; i += 3) {
/*  707 */           a(paramWorld, d, 52, 5, i, paramStructureBoundingBox);
/*      */         }
/*  709 */         a(paramWorld, d, 52, 5, 52, paramStructureBoundingBox);
/*      */         
/*      */ 
/*  712 */         a(paramWorld, paramStructureBoundingBox, 51, 1, 52, 53, 3, 52, a, a, false);
/*  713 */         a(paramWorld, paramStructureBoundingBox, 52, 1, 51, 52, 3, 53, a, a, false);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  718 */       if (a(paramStructureBoundingBox, 0, 51, 57, 57)) {
/*  719 */         a(paramWorld, paramStructureBoundingBox, 7, 0, 51, 50, 0, 57, a, a, false);
/*      */         
/*  721 */         a(paramWorld, paramStructureBoundingBox, 7, 1, 51, 50, 10, 57, false);
/*      */         
/*      */ 
/*  724 */         for (i = 0; i < 4; i++) {
/*  725 */           a(paramWorld, paramStructureBoundingBox, i + 1, i + 1, 57 - i, 56 - i, i + 1, 57 - i, b, b, false);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     private void f(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*      */       int i;
/*  733 */       if (a(paramStructureBoundingBox, 7, 21, 13, 50)) {
/*  734 */         a(paramWorld, paramStructureBoundingBox, 7, 0, 21, 13, 0, 50, a, a, false);
/*      */         
/*  736 */         a(paramWorld, paramStructureBoundingBox, 7, 1, 21, 13, 10, 50, false);
/*      */         
/*      */ 
/*  739 */         a(paramWorld, paramStructureBoundingBox, 11, 8, 21, 13, 8, 53, a, a, false);
/*  740 */         for (i = 0; i < 4; i++) {
/*  741 */           a(paramWorld, paramStructureBoundingBox, i + 7, i + 5, 21, i + 7, i + 5, 54, b, b, false);
/*      */         }
/*  743 */         for (i = 21; i <= 45; i += 3) {
/*  744 */           a(paramWorld, d, 12, 9, i, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  750 */       if (a(paramStructureBoundingBox, 44, 21, 50, 54)) {
/*  751 */         a(paramWorld, paramStructureBoundingBox, 44, 0, 21, 50, 0, 50, a, a, false);
/*      */         
/*  753 */         a(paramWorld, paramStructureBoundingBox, 44, 1, 21, 50, 10, 50, false);
/*      */         
/*      */ 
/*  756 */         a(paramWorld, paramStructureBoundingBox, 44, 8, 21, 46, 8, 53, a, a, false);
/*  757 */         for (i = 0; i < 4; i++) {
/*  758 */           a(paramWorld, paramStructureBoundingBox, 50 - i, i + 5, 21, 50 - i, i + 5, 54, b, b, false);
/*      */         }
/*  760 */         for (i = 21; i <= 45; i += 3) {
/*  761 */           a(paramWorld, d, 45, 9, i, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  767 */       if (a(paramStructureBoundingBox, 8, 44, 49, 54)) {
/*  768 */         a(paramWorld, paramStructureBoundingBox, 14, 0, 44, 43, 0, 50, a, a, false);
/*      */         
/*  770 */         a(paramWorld, paramStructureBoundingBox, 14, 1, 44, 43, 10, 50, false);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  775 */         for (i = 12; i <= 45; i += 3) {
/*  776 */           a(paramWorld, d, i, 9, 45, paramStructureBoundingBox);
/*  777 */           a(paramWorld, d, i, 9, 52, paramStructureBoundingBox);
/*  778 */           if ((i == 12) || (i == 18) || (i == 24) || (i == 33) || (i == 39) || (i == 45)) {
/*  779 */             a(paramWorld, d, i, 9, 47, paramStructureBoundingBox);
/*  780 */             a(paramWorld, d, i, 9, 50, paramStructureBoundingBox);
/*  781 */             a(paramWorld, d, i, 10, 45, paramStructureBoundingBox);
/*  782 */             a(paramWorld, d, i, 10, 46, paramStructureBoundingBox);
/*  783 */             a(paramWorld, d, i, 10, 51, paramStructureBoundingBox);
/*  784 */             a(paramWorld, d, i, 10, 52, paramStructureBoundingBox);
/*  785 */             a(paramWorld, d, i, 11, 47, paramStructureBoundingBox);
/*  786 */             a(paramWorld, d, i, 11, 50, paramStructureBoundingBox);
/*  787 */             a(paramWorld, d, i, 12, 48, paramStructureBoundingBox);
/*  788 */             a(paramWorld, d, i, 12, 49, paramStructureBoundingBox);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  793 */         for (i = 0; i < 3; i++) {
/*  794 */           a(paramWorld, paramStructureBoundingBox, 8 + i, 5 + i, 54, 49 - i, 5 + i, 54, a, a, false);
/*      */         }
/*  796 */         a(paramWorld, paramStructureBoundingBox, 11, 8, 54, 46, 8, 54, b, b, false);
/*  797 */         a(paramWorld, paramStructureBoundingBox, 14, 8, 44, 43, 8, 53, a, a, false);
/*      */       }
/*      */     }
/*      */     
/*      */     private void g(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*      */       int i;
/*  804 */       if (a(paramStructureBoundingBox, 14, 21, 20, 43)) {
/*  805 */         a(paramWorld, paramStructureBoundingBox, 14, 0, 21, 20, 0, 43, a, a, false);
/*      */         
/*  807 */         a(paramWorld, paramStructureBoundingBox, 14, 1, 22, 20, 14, 43, false);
/*      */         
/*      */ 
/*  810 */         a(paramWorld, paramStructureBoundingBox, 18, 12, 22, 20, 12, 39, a, a, false);
/*  811 */         a(paramWorld, paramStructureBoundingBox, 18, 12, 21, 20, 12, 21, b, b, false);
/*  812 */         for (i = 0; i < 4; i++) {
/*  813 */           a(paramWorld, paramStructureBoundingBox, i + 14, i + 9, 21, i + 14, i + 9, 43 - i, b, b, false);
/*      */         }
/*  815 */         for (i = 23; i <= 39; i += 3) {
/*  816 */           a(paramWorld, d, 19, 13, i, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  822 */       if (a(paramStructureBoundingBox, 37, 21, 43, 43)) {
/*  823 */         a(paramWorld, paramStructureBoundingBox, 37, 0, 21, 43, 0, 43, a, a, false);
/*      */         
/*  825 */         a(paramWorld, paramStructureBoundingBox, 37, 1, 22, 43, 14, 43, false);
/*      */         
/*      */ 
/*  828 */         a(paramWorld, paramStructureBoundingBox, 37, 12, 22, 39, 12, 39, a, a, false);
/*  829 */         a(paramWorld, paramStructureBoundingBox, 37, 12, 21, 39, 12, 21, b, b, false);
/*  830 */         for (i = 0; i < 4; i++) {
/*  831 */           a(paramWorld, paramStructureBoundingBox, 43 - i, i + 9, 21, 43 - i, i + 9, 43 - i, b, b, false);
/*      */         }
/*  833 */         for (i = 23; i <= 39; i += 3) {
/*  834 */           a(paramWorld, d, 38, 13, i, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  840 */       if (a(paramStructureBoundingBox, 15, 37, 42, 43)) {
/*  841 */         a(paramWorld, paramStructureBoundingBox, 21, 0, 37, 36, 0, 43, a, a, false);
/*      */         
/*  843 */         a(paramWorld, paramStructureBoundingBox, 21, 1, 37, 36, 14, 43, false);
/*      */         
/*      */ 
/*  846 */         a(paramWorld, paramStructureBoundingBox, 21, 12, 37, 36, 12, 39, a, a, false);
/*  847 */         for (i = 0; i < 4; i++) {
/*  848 */           a(paramWorld, paramStructureBoundingBox, 15 + i, i + 9, 43 - i, 42 - i, i + 9, 43 - i, b, b, false);
/*      */         }
/*  850 */         for (i = 21; i <= 36; i += 3) {
/*  851 */           a(paramWorld, d, i, 13, 38, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenMonumentPieceEntry extends WorldGenMonumentPieces.WorldGenMonumentPiece
/*      */   {
/*      */     public WorldGenMonumentPieceEntry() {}
/*      */     
/*      */     public WorldGenMonumentPieceEntry(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker)
/*      */     {
/*  863 */       super(paramEnumDirection, paramWorldGenMonumentStateTracker, 1, 1, 1);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  869 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 2, 3, 7, b, b, false);
/*  870 */       a(paramWorld, paramStructureBoundingBox, 5, 3, 0, 7, 3, 7, b, b, false);
/*  871 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 1, 2, 7, b, b, false);
/*  872 */       a(paramWorld, paramStructureBoundingBox, 6, 2, 0, 7, 2, 7, b, b, false);
/*  873 */       a(paramWorld, paramStructureBoundingBox, 0, 1, 0, 0, 1, 7, b, b, false);
/*  874 */       a(paramWorld, paramStructureBoundingBox, 7, 1, 0, 7, 1, 7, b, b, false);
/*      */       
/*      */ 
/*  877 */       a(paramWorld, paramStructureBoundingBox, 0, 1, 7, 7, 3, 7, b, b, false);
/*      */       
/*      */ 
/*  880 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 0, 2, 3, 0, b, b, false);
/*  881 */       a(paramWorld, paramStructureBoundingBox, 5, 1, 0, 6, 3, 0, b, b, false);
/*      */       
/*  883 */       if (this.k.c[EnumDirection.NORTH.a()] != 0) {
/*  884 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 7, 4, 2, 7, false);
/*      */       }
/*  886 */       if (this.k.c[EnumDirection.WEST.a()] != 0) {
/*  887 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 3, 1, 2, 4, false);
/*      */       }
/*  889 */       if (this.k.c[EnumDirection.EAST.a()] != 0) {
/*  890 */         a(paramWorld, paramStructureBoundingBox, 6, 1, 3, 7, 2, 4, false);
/*      */       }
/*      */       
/*  893 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenMonumentPieceSimple extends WorldGenMonumentPieces.WorldGenMonumentPiece
/*      */   {
/*      */     private int o;
/*      */     
/*      */     public WorldGenMonumentPieceSimple() {}
/*      */     
/*      */     public WorldGenMonumentPieceSimple(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/*  905 */       super(paramEnumDirection, paramWorldGenMonumentStateTracker, 1, 1, 1);
/*  906 */       this.o = paramRandom.nextInt(3);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  911 */       if (this.k.a / 25 > 0) {
/*  912 */         a(paramWorld, paramStructureBoundingBox, 0, 0, this.k.c[EnumDirection.DOWN.a()]);
/*      */       }
/*  914 */       if (this.k.b[EnumDirection.UP.a()] == null) {
/*  915 */         a(paramWorld, paramStructureBoundingBox, 1, 4, 1, 6, 4, 6, a);
/*      */       }
/*      */       
/*  918 */       int i = (this.o != 0) && (paramRandom.nextBoolean()) && (this.k.c[EnumDirection.DOWN.a()] == 0) && (this.k.c[EnumDirection.UP.a()] == 0) && (this.k.c() > 1) ? 1 : 0;
/*      */       
/*  920 */       if (this.o == 0)
/*      */       {
/*  922 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 0, 2, 1, 2, b, b, false);
/*  923 */         a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 2, 3, 2, b, b, false);
/*  924 */         a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 2, 2, a, a, false);
/*  925 */         a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 2, 2, 0, a, a, false);
/*  926 */         a(paramWorld, e, 1, 2, 1, paramStructureBoundingBox);
/*      */         
/*      */ 
/*  929 */         a(paramWorld, paramStructureBoundingBox, 5, 1, 0, 7, 1, 2, b, b, false);
/*  930 */         a(paramWorld, paramStructureBoundingBox, 5, 3, 0, 7, 3, 2, b, b, false);
/*  931 */         a(paramWorld, paramStructureBoundingBox, 7, 2, 0, 7, 2, 2, a, a, false);
/*  932 */         a(paramWorld, paramStructureBoundingBox, 5, 2, 0, 6, 2, 0, a, a, false);
/*  933 */         a(paramWorld, e, 6, 2, 1, paramStructureBoundingBox);
/*      */         
/*      */ 
/*  936 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 5, 2, 1, 7, b, b, false);
/*  937 */         a(paramWorld, paramStructureBoundingBox, 0, 3, 5, 2, 3, 7, b, b, false);
/*  938 */         a(paramWorld, paramStructureBoundingBox, 0, 2, 5, 0, 2, 7, a, a, false);
/*  939 */         a(paramWorld, paramStructureBoundingBox, 1, 2, 7, 2, 2, 7, a, a, false);
/*  940 */         a(paramWorld, e, 1, 2, 6, paramStructureBoundingBox);
/*      */         
/*      */ 
/*  943 */         a(paramWorld, paramStructureBoundingBox, 5, 1, 5, 7, 1, 7, b, b, false);
/*  944 */         a(paramWorld, paramStructureBoundingBox, 5, 3, 5, 7, 3, 7, b, b, false);
/*  945 */         a(paramWorld, paramStructureBoundingBox, 7, 2, 5, 7, 2, 7, a, a, false);
/*  946 */         a(paramWorld, paramStructureBoundingBox, 5, 2, 7, 6, 2, 7, a, a, false);
/*  947 */         a(paramWorld, e, 6, 2, 6, paramStructureBoundingBox);
/*      */         
/*  949 */         if (this.k.c[EnumDirection.SOUTH.a()] != 0) {
/*  950 */           a(paramWorld, paramStructureBoundingBox, 3, 3, 0, 4, 3, 0, b, b, false);
/*      */         } else {
/*  952 */           a(paramWorld, paramStructureBoundingBox, 3, 3, 0, 4, 3, 1, b, b, false);
/*  953 */           a(paramWorld, paramStructureBoundingBox, 3, 2, 0, 4, 2, 0, a, a, false);
/*  954 */           a(paramWorld, paramStructureBoundingBox, 3, 1, 0, 4, 1, 1, b, b, false);
/*      */         }
/*  956 */         if (this.k.c[EnumDirection.NORTH.a()] != 0) {
/*  957 */           a(paramWorld, paramStructureBoundingBox, 3, 3, 7, 4, 3, 7, b, b, false);
/*      */         } else {
/*  959 */           a(paramWorld, paramStructureBoundingBox, 3, 3, 6, 4, 3, 7, b, b, false);
/*  960 */           a(paramWorld, paramStructureBoundingBox, 3, 2, 7, 4, 2, 7, a, a, false);
/*  961 */           a(paramWorld, paramStructureBoundingBox, 3, 1, 6, 4, 1, 7, b, b, false);
/*      */         }
/*  963 */         if (this.k.c[EnumDirection.WEST.a()] != 0) {
/*  964 */           a(paramWorld, paramStructureBoundingBox, 0, 3, 3, 0, 3, 4, b, b, false);
/*      */         } else {
/*  966 */           a(paramWorld, paramStructureBoundingBox, 0, 3, 3, 1, 3, 4, b, b, false);
/*  967 */           a(paramWorld, paramStructureBoundingBox, 0, 2, 3, 0, 2, 4, a, a, false);
/*  968 */           a(paramWorld, paramStructureBoundingBox, 0, 1, 3, 1, 1, 4, b, b, false);
/*      */         }
/*  970 */         if (this.k.c[EnumDirection.EAST.a()] != 0) {
/*  971 */           a(paramWorld, paramStructureBoundingBox, 7, 3, 3, 7, 3, 4, b, b, false);
/*      */         } else {
/*  973 */           a(paramWorld, paramStructureBoundingBox, 6, 3, 3, 7, 3, 4, b, b, false);
/*  974 */           a(paramWorld, paramStructureBoundingBox, 7, 2, 3, 7, 2, 4, a, a, false);
/*  975 */           a(paramWorld, paramStructureBoundingBox, 6, 1, 3, 7, 1, 4, b, b, false);
/*      */         }
/*  977 */       } else if (this.o == 1)
/*      */       {
/*  979 */         a(paramWorld, paramStructureBoundingBox, 2, 1, 2, 2, 3, 2, b, b, false);
/*  980 */         a(paramWorld, paramStructureBoundingBox, 2, 1, 5, 2, 3, 5, b, b, false);
/*  981 */         a(paramWorld, paramStructureBoundingBox, 5, 1, 5, 5, 3, 5, b, b, false);
/*  982 */         a(paramWorld, paramStructureBoundingBox, 5, 1, 2, 5, 3, 2, b, b, false);
/*  983 */         a(paramWorld, e, 2, 2, 2, paramStructureBoundingBox);
/*  984 */         a(paramWorld, e, 2, 2, 5, paramStructureBoundingBox);
/*  985 */         a(paramWorld, e, 5, 2, 5, paramStructureBoundingBox);
/*  986 */         a(paramWorld, e, 5, 2, 2, paramStructureBoundingBox);
/*      */         
/*      */ 
/*  989 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 0, 1, 3, 0, b, b, false);
/*  990 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 3, 1, b, b, false);
/*  991 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 7, 1, 3, 7, b, b, false);
/*  992 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 6, 0, 3, 6, b, b, false);
/*  993 */         a(paramWorld, paramStructureBoundingBox, 6, 1, 7, 7, 3, 7, b, b, false);
/*  994 */         a(paramWorld, paramStructureBoundingBox, 7, 1, 6, 7, 3, 6, b, b, false);
/*  995 */         a(paramWorld, paramStructureBoundingBox, 6, 1, 0, 7, 3, 0, b, b, false);
/*  996 */         a(paramWorld, paramStructureBoundingBox, 7, 1, 1, 7, 3, 1, b, b, false);
/*  997 */         a(paramWorld, a, 1, 2, 0, paramStructureBoundingBox);
/*  998 */         a(paramWorld, a, 0, 2, 1, paramStructureBoundingBox);
/*  999 */         a(paramWorld, a, 1, 2, 7, paramStructureBoundingBox);
/* 1000 */         a(paramWorld, a, 0, 2, 6, paramStructureBoundingBox);
/* 1001 */         a(paramWorld, a, 6, 2, 7, paramStructureBoundingBox);
/* 1002 */         a(paramWorld, a, 7, 2, 6, paramStructureBoundingBox);
/* 1003 */         a(paramWorld, a, 6, 2, 0, paramStructureBoundingBox);
/* 1004 */         a(paramWorld, a, 7, 2, 1, paramStructureBoundingBox);
/* 1005 */         if (this.k.c[EnumDirection.SOUTH.a()] == 0) {
/* 1006 */           a(paramWorld, paramStructureBoundingBox, 1, 3, 0, 6, 3, 0, b, b, false);
/* 1007 */           a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 6, 2, 0, a, a, false);
/* 1008 */           a(paramWorld, paramStructureBoundingBox, 1, 1, 0, 6, 1, 0, b, b, false);
/*      */         }
/* 1010 */         if (this.k.c[EnumDirection.NORTH.a()] == 0) {
/* 1011 */           a(paramWorld, paramStructureBoundingBox, 1, 3, 7, 6, 3, 7, b, b, false);
/* 1012 */           a(paramWorld, paramStructureBoundingBox, 1, 2, 7, 6, 2, 7, a, a, false);
/* 1013 */           a(paramWorld, paramStructureBoundingBox, 1, 1, 7, 6, 1, 7, b, b, false);
/*      */         }
/* 1015 */         if (this.k.c[EnumDirection.WEST.a()] == 0) {
/* 1016 */           a(paramWorld, paramStructureBoundingBox, 0, 3, 1, 0, 3, 6, b, b, false);
/* 1017 */           a(paramWorld, paramStructureBoundingBox, 0, 2, 1, 0, 2, 6, a, a, false);
/* 1018 */           a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 1, 6, b, b, false);
/*      */         }
/* 1020 */         if (this.k.c[EnumDirection.EAST.a()] == 0) {
/* 1021 */           a(paramWorld, paramStructureBoundingBox, 7, 3, 1, 7, 3, 6, b, b, false);
/* 1022 */           a(paramWorld, paramStructureBoundingBox, 7, 2, 1, 7, 2, 6, a, a, false);
/* 1023 */           a(paramWorld, paramStructureBoundingBox, 7, 1, 1, 7, 1, 6, b, b, false);
/*      */         }
/* 1025 */       } else if (this.o == 2) {
/* 1026 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 0, 0, 1, 7, b, b, false);
/* 1027 */         a(paramWorld, paramStructureBoundingBox, 7, 1, 0, 7, 1, 7, b, b, false);
/* 1028 */         a(paramWorld, paramStructureBoundingBox, 1, 1, 0, 6, 1, 0, b, b, false);
/* 1029 */         a(paramWorld, paramStructureBoundingBox, 1, 1, 7, 6, 1, 7, b, b, false);
/*      */         
/* 1031 */         a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 2, 7, c, c, false);
/* 1032 */         a(paramWorld, paramStructureBoundingBox, 7, 2, 0, 7, 2, 7, c, c, false);
/* 1033 */         a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 6, 2, 0, c, c, false);
/* 1034 */         a(paramWorld, paramStructureBoundingBox, 1, 2, 7, 6, 2, 7, c, c, false);
/*      */         
/* 1036 */         a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 0, 3, 7, b, b, false);
/* 1037 */         a(paramWorld, paramStructureBoundingBox, 7, 3, 0, 7, 3, 7, b, b, false);
/* 1038 */         a(paramWorld, paramStructureBoundingBox, 1, 3, 0, 6, 3, 0, b, b, false);
/* 1039 */         a(paramWorld, paramStructureBoundingBox, 1, 3, 7, 6, 3, 7, b, b, false);
/*      */         
/* 1041 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 3, 0, 2, 4, c, c, false);
/* 1042 */         a(paramWorld, paramStructureBoundingBox, 7, 1, 3, 7, 2, 4, c, c, false);
/* 1043 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 0, 4, 2, 0, c, c, false);
/* 1044 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 7, 4, 2, 7, c, c, false);
/*      */         
/* 1046 */         if (this.k.c[EnumDirection.SOUTH.a()] != 0) {
/* 1047 */           a(paramWorld, paramStructureBoundingBox, 3, 1, 0, 4, 2, 0, false);
/*      */         }
/* 1049 */         if (this.k.c[EnumDirection.NORTH.a()] != 0) {
/* 1050 */           a(paramWorld, paramStructureBoundingBox, 3, 1, 7, 4, 2, 7, false);
/*      */         }
/* 1052 */         if (this.k.c[EnumDirection.WEST.a()] != 0) {
/* 1053 */           a(paramWorld, paramStructureBoundingBox, 0, 1, 3, 0, 2, 4, false);
/*      */         }
/* 1055 */         if (this.k.c[EnumDirection.EAST.a()] != 0) {
/* 1056 */           a(paramWorld, paramStructureBoundingBox, 7, 1, 3, 7, 2, 4, false);
/*      */         }
/*      */       }
/* 1059 */       if (i != 0) {
/* 1060 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 3, 4, 1, 4, b, b, false);
/* 1061 */         a(paramWorld, paramStructureBoundingBox, 3, 2, 3, 4, 2, 4, a, a, false);
/* 1062 */         a(paramWorld, paramStructureBoundingBox, 3, 3, 3, 4, 3, 4, b, b, false);
/*      */       }
/*      */       
/* 1065 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenMonumentPieceSimpleT extends WorldGenMonumentPieces.WorldGenMonumentPiece
/*      */   {
/*      */     public WorldGenMonumentPieceSimpleT() {}
/*      */     
/*      */     public WorldGenMonumentPieceSimpleT(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 1075 */       super(paramEnumDirection, paramWorldGenMonumentStateTracker, 1, 1, 1);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1080 */       if (this.k.a / 25 > 0) {
/* 1081 */         a(paramWorld, paramStructureBoundingBox, 0, 0, this.k.c[EnumDirection.DOWN.a()]);
/*      */       }
/* 1083 */       if (this.k.b[EnumDirection.UP.a()] == null) {
/* 1084 */         a(paramWorld, paramStructureBoundingBox, 1, 4, 1, 6, 4, 6, a);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1089 */       for (int i = 1; i <= 6; i++) {
/* 1090 */         for (int j = 1; j <= 6; j++) {
/* 1091 */           if (paramRandom.nextInt(3) != 0) {
/* 1092 */             int k = 2 + (paramRandom.nextInt(4) == 0 ? 0 : 1);
/* 1093 */             a(paramWorld, paramStructureBoundingBox, i, k, j, i, 3, j, Blocks.SPONGE.fromLegacyData(1), Blocks.SPONGE.fromLegacyData(1), false);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1098 */       a(paramWorld, paramStructureBoundingBox, 0, 1, 0, 0, 1, 7, b, b, false);
/* 1099 */       a(paramWorld, paramStructureBoundingBox, 7, 1, 0, 7, 1, 7, b, b, false);
/* 1100 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 0, 6, 1, 0, b, b, false);
/* 1101 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 7, 6, 1, 7, b, b, false);
/*      */       
/* 1103 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 2, 7, c, c, false);
/* 1104 */       a(paramWorld, paramStructureBoundingBox, 7, 2, 0, 7, 2, 7, c, c, false);
/* 1105 */       a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 6, 2, 0, c, c, false);
/* 1106 */       a(paramWorld, paramStructureBoundingBox, 1, 2, 7, 6, 2, 7, c, c, false);
/*      */       
/* 1108 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 0, 3, 7, b, b, false);
/* 1109 */       a(paramWorld, paramStructureBoundingBox, 7, 3, 0, 7, 3, 7, b, b, false);
/* 1110 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 0, 6, 3, 0, b, b, false);
/* 1111 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 7, 6, 3, 7, b, b, false);
/*      */       
/* 1113 */       a(paramWorld, paramStructureBoundingBox, 0, 1, 3, 0, 2, 4, c, c, false);
/* 1114 */       a(paramWorld, paramStructureBoundingBox, 7, 1, 3, 7, 2, 4, c, c, false);
/* 1115 */       a(paramWorld, paramStructureBoundingBox, 3, 1, 0, 4, 2, 0, c, c, false);
/* 1116 */       a(paramWorld, paramStructureBoundingBox, 3, 1, 7, 4, 2, 7, c, c, false);
/*      */       
/* 1118 */       if (this.k.c[EnumDirection.SOUTH.a()] != 0) {
/* 1119 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/*      */       
/*      */ 
/* 1123 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenMonumentPiece5 extends WorldGenMonumentPieces.WorldGenMonumentPiece
/*      */   {
/*      */     public WorldGenMonumentPiece5() {}
/*      */     
/*      */     public WorldGenMonumentPiece5(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 1133 */       super(paramEnumDirection, paramWorldGenMonumentStateTracker, 1, 2, 1);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1138 */       if (this.k.a / 25 > 0) {
/* 1139 */         a(paramWorld, paramStructureBoundingBox, 0, 0, this.k.c[EnumDirection.DOWN.a()]);
/*      */       }
/* 1141 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker1 = this.k.b[EnumDirection.UP.a()];
/* 1142 */       if (localWorldGenMonumentStateTracker1.b[EnumDirection.UP.a()] == null) {
/* 1143 */         a(paramWorld, paramStructureBoundingBox, 1, 8, 1, 6, 8, 6, a);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1148 */       a(paramWorld, paramStructureBoundingBox, 0, 4, 0, 0, 4, 7, b, b, false);
/* 1149 */       a(paramWorld, paramStructureBoundingBox, 7, 4, 0, 7, 4, 7, b, b, false);
/* 1150 */       a(paramWorld, paramStructureBoundingBox, 1, 4, 0, 6, 4, 0, b, b, false);
/* 1151 */       a(paramWorld, paramStructureBoundingBox, 1, 4, 7, 6, 4, 7, b, b, false);
/*      */       
/* 1153 */       a(paramWorld, paramStructureBoundingBox, 2, 4, 1, 2, 4, 2, b, b, false);
/* 1154 */       a(paramWorld, paramStructureBoundingBox, 1, 4, 2, 1, 4, 2, b, b, false);
/* 1155 */       a(paramWorld, paramStructureBoundingBox, 5, 4, 1, 5, 4, 2, b, b, false);
/* 1156 */       a(paramWorld, paramStructureBoundingBox, 6, 4, 2, 6, 4, 2, b, b, false);
/* 1157 */       a(paramWorld, paramStructureBoundingBox, 2, 4, 5, 2, 4, 6, b, b, false);
/* 1158 */       a(paramWorld, paramStructureBoundingBox, 1, 4, 5, 1, 4, 5, b, b, false);
/* 1159 */       a(paramWorld, paramStructureBoundingBox, 5, 4, 5, 5, 4, 6, b, b, false);
/* 1160 */       a(paramWorld, paramStructureBoundingBox, 6, 4, 5, 6, 4, 5, b, b, false);
/*      */       
/* 1162 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker2 = this.k;
/* 1163 */       for (int i = 1; i <= 5; i += 4) {
/* 1164 */         int j = 0;
/* 1165 */         if (localWorldGenMonumentStateTracker2.c[EnumDirection.SOUTH.a()] != 0) {
/* 1166 */           a(paramWorld, paramStructureBoundingBox, 2, i, j, 2, i + 2, j, b, b, false);
/* 1167 */           a(paramWorld, paramStructureBoundingBox, 5, i, j, 5, i + 2, j, b, b, false);
/* 1168 */           a(paramWorld, paramStructureBoundingBox, 3, i + 2, j, 4, i + 2, j, b, b, false);
/*      */         } else {
/* 1170 */           a(paramWorld, paramStructureBoundingBox, 0, i, j, 7, i + 2, j, b, b, false);
/* 1171 */           a(paramWorld, paramStructureBoundingBox, 0, i + 1, j, 7, i + 1, j, a, a, false);
/*      */         }
/* 1173 */         j = 7;
/* 1174 */         if (localWorldGenMonumentStateTracker2.c[EnumDirection.NORTH.a()] != 0) {
/* 1175 */           a(paramWorld, paramStructureBoundingBox, 2, i, j, 2, i + 2, j, b, b, false);
/* 1176 */           a(paramWorld, paramStructureBoundingBox, 5, i, j, 5, i + 2, j, b, b, false);
/* 1177 */           a(paramWorld, paramStructureBoundingBox, 3, i + 2, j, 4, i + 2, j, b, b, false);
/*      */         } else {
/* 1179 */           a(paramWorld, paramStructureBoundingBox, 0, i, j, 7, i + 2, j, b, b, false);
/* 1180 */           a(paramWorld, paramStructureBoundingBox, 0, i + 1, j, 7, i + 1, j, a, a, false);
/*      */         }
/* 1182 */         int k = 0;
/* 1183 */         if (localWorldGenMonumentStateTracker2.c[EnumDirection.WEST.a()] != 0) {
/* 1184 */           a(paramWorld, paramStructureBoundingBox, k, i, 2, k, i + 2, 2, b, b, false);
/* 1185 */           a(paramWorld, paramStructureBoundingBox, k, i, 5, k, i + 2, 5, b, b, false);
/* 1186 */           a(paramWorld, paramStructureBoundingBox, k, i + 2, 3, k, i + 2, 4, b, b, false);
/*      */         } else {
/* 1188 */           a(paramWorld, paramStructureBoundingBox, k, i, 0, k, i + 2, 7, b, b, false);
/* 1189 */           a(paramWorld, paramStructureBoundingBox, k, i + 1, 0, k, i + 1, 7, a, a, false);
/*      */         }
/* 1191 */         k = 7;
/* 1192 */         if (localWorldGenMonumentStateTracker2.c[EnumDirection.EAST.a()] != 0) {
/* 1193 */           a(paramWorld, paramStructureBoundingBox, k, i, 2, k, i + 2, 2, b, b, false);
/* 1194 */           a(paramWorld, paramStructureBoundingBox, k, i, 5, k, i + 2, 5, b, b, false);
/* 1195 */           a(paramWorld, paramStructureBoundingBox, k, i + 2, 3, k, i + 2, 4, b, b, false);
/*      */         } else {
/* 1197 */           a(paramWorld, paramStructureBoundingBox, k, i, 0, k, i + 2, 7, b, b, false);
/* 1198 */           a(paramWorld, paramStructureBoundingBox, k, i + 1, 0, k, i + 1, 7, a, a, false);
/*      */         }
/* 1200 */         localWorldGenMonumentStateTracker2 = localWorldGenMonumentStateTracker1;
/*      */       }
/*      */       
/*      */ 
/* 1204 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenMonumentPiece3 extends WorldGenMonumentPieces.WorldGenMonumentPiece
/*      */   {
/*      */     public WorldGenMonumentPiece3() {}
/*      */     
/*      */     public WorldGenMonumentPiece3(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 1214 */       super(paramEnumDirection, paramWorldGenMonumentStateTracker, 2, 1, 1);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1219 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker1 = this.k.b[EnumDirection.EAST.a()];
/* 1220 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker2 = this.k;
/* 1221 */       if (this.k.a / 25 > 0) {
/* 1222 */         a(paramWorld, paramStructureBoundingBox, 8, 0, localWorldGenMonumentStateTracker1.c[EnumDirection.DOWN.a()]);
/* 1223 */         a(paramWorld, paramStructureBoundingBox, 0, 0, localWorldGenMonumentStateTracker2.c[EnumDirection.DOWN.a()]);
/*      */       }
/* 1225 */       if (localWorldGenMonumentStateTracker2.b[EnumDirection.UP.a()] == null) {
/* 1226 */         a(paramWorld, paramStructureBoundingBox, 1, 4, 1, 7, 4, 6, a);
/*      */       }
/* 1228 */       if (localWorldGenMonumentStateTracker1.b[EnumDirection.UP.a()] == null) {
/* 1229 */         a(paramWorld, paramStructureBoundingBox, 8, 4, 1, 14, 4, 6, a);
/*      */       }
/*      */       
/*      */ 
/* 1233 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 0, 3, 7, b, b, false);
/* 1234 */       a(paramWorld, paramStructureBoundingBox, 15, 3, 0, 15, 3, 7, b, b, false);
/* 1235 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 0, 15, 3, 0, b, b, false);
/* 1236 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 7, 14, 3, 7, b, b, false);
/* 1237 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 2, 7, a, a, false);
/* 1238 */       a(paramWorld, paramStructureBoundingBox, 15, 2, 0, 15, 2, 7, a, a, false);
/* 1239 */       a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 15, 2, 0, a, a, false);
/* 1240 */       a(paramWorld, paramStructureBoundingBox, 1, 2, 7, 14, 2, 7, a, a, false);
/* 1241 */       a(paramWorld, paramStructureBoundingBox, 0, 1, 0, 0, 1, 7, b, b, false);
/* 1242 */       a(paramWorld, paramStructureBoundingBox, 15, 1, 0, 15, 1, 7, b, b, false);
/* 1243 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 0, 15, 1, 0, b, b, false);
/* 1244 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 7, 14, 1, 7, b, b, false);
/*      */       
/*      */ 
/* 1247 */       a(paramWorld, paramStructureBoundingBox, 5, 1, 0, 10, 1, 4, b, b, false);
/* 1248 */       a(paramWorld, paramStructureBoundingBox, 6, 2, 0, 9, 2, 3, a, a, false);
/* 1249 */       a(paramWorld, paramStructureBoundingBox, 5, 3, 0, 10, 3, 4, b, b, false);
/*      */       
/* 1251 */       a(paramWorld, e, 6, 2, 3, paramStructureBoundingBox);
/* 1252 */       a(paramWorld, e, 9, 2, 3, paramStructureBoundingBox);
/*      */       
/*      */ 
/* 1255 */       if (localWorldGenMonumentStateTracker2.c[EnumDirection.SOUTH.a()] != 0) {
/* 1256 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/* 1258 */       if (localWorldGenMonumentStateTracker2.c[EnumDirection.NORTH.a()] != 0) {
/* 1259 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 7, 4, 2, 7, false);
/*      */       }
/* 1261 */       if (localWorldGenMonumentStateTracker2.c[EnumDirection.WEST.a()] != 0) {
/* 1262 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 3, 0, 2, 4, false);
/*      */       }
/* 1264 */       if (localWorldGenMonumentStateTracker1.c[EnumDirection.SOUTH.a()] != 0) {
/* 1265 */         a(paramWorld, paramStructureBoundingBox, 11, 1, 0, 12, 2, 0, false);
/*      */       }
/* 1267 */       if (localWorldGenMonumentStateTracker1.c[EnumDirection.NORTH.a()] != 0) {
/* 1268 */         a(paramWorld, paramStructureBoundingBox, 11, 1, 7, 12, 2, 7, false);
/*      */       }
/* 1270 */       if (localWorldGenMonumentStateTracker1.c[EnumDirection.EAST.a()] != 0) {
/* 1271 */         a(paramWorld, paramStructureBoundingBox, 15, 1, 3, 15, 2, 4, false);
/*      */       }
/*      */       
/* 1274 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenMonumentPiece7 extends WorldGenMonumentPieces.WorldGenMonumentPiece
/*      */   {
/*      */     public WorldGenMonumentPiece7() {}
/*      */     
/*      */     public WorldGenMonumentPiece7(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 1284 */       super(paramEnumDirection, paramWorldGenMonumentStateTracker, 1, 1, 2);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1289 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker1 = this.k.b[EnumDirection.NORTH.a()];
/* 1290 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker2 = this.k;
/* 1291 */       if (this.k.a / 25 > 0) {
/* 1292 */         a(paramWorld, paramStructureBoundingBox, 0, 8, localWorldGenMonumentStateTracker1.c[EnumDirection.DOWN.a()]);
/* 1293 */         a(paramWorld, paramStructureBoundingBox, 0, 0, localWorldGenMonumentStateTracker2.c[EnumDirection.DOWN.a()]);
/*      */       }
/* 1295 */       if (localWorldGenMonumentStateTracker2.b[EnumDirection.UP.a()] == null) {
/* 1296 */         a(paramWorld, paramStructureBoundingBox, 1, 4, 1, 6, 4, 7, a);
/*      */       }
/* 1298 */       if (localWorldGenMonumentStateTracker1.b[EnumDirection.UP.a()] == null) {
/* 1299 */         a(paramWorld, paramStructureBoundingBox, 1, 4, 8, 6, 4, 14, a);
/*      */       }
/*      */       
/*      */ 
/* 1303 */       a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 0, 3, 15, b, b, false);
/* 1304 */       a(paramWorld, paramStructureBoundingBox, 7, 3, 0, 7, 3, 15, b, b, false);
/* 1305 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 0, 7, 3, 0, b, b, false);
/* 1306 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 15, 6, 3, 15, b, b, false);
/* 1307 */       a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 2, 15, a, a, false);
/* 1308 */       a(paramWorld, paramStructureBoundingBox, 7, 2, 0, 7, 2, 15, a, a, false);
/* 1309 */       a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 7, 2, 0, a, a, false);
/* 1310 */       a(paramWorld, paramStructureBoundingBox, 1, 2, 15, 6, 2, 15, a, a, false);
/* 1311 */       a(paramWorld, paramStructureBoundingBox, 0, 1, 0, 0, 1, 15, b, b, false);
/* 1312 */       a(paramWorld, paramStructureBoundingBox, 7, 1, 0, 7, 1, 15, b, b, false);
/* 1313 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 0, 7, 1, 0, b, b, false);
/* 1314 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 15, 6, 1, 15, b, b, false);
/*      */       
/*      */ 
/* 1317 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 1, 1, 2, b, b, false);
/* 1318 */       a(paramWorld, paramStructureBoundingBox, 6, 1, 1, 6, 1, 2, b, b, false);
/* 1319 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 1, 1, 3, 2, b, b, false);
/* 1320 */       a(paramWorld, paramStructureBoundingBox, 6, 3, 1, 6, 3, 2, b, b, false);
/* 1321 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 13, 1, 1, 14, b, b, false);
/* 1322 */       a(paramWorld, paramStructureBoundingBox, 6, 1, 13, 6, 1, 14, b, b, false);
/* 1323 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 13, 1, 3, 14, b, b, false);
/* 1324 */       a(paramWorld, paramStructureBoundingBox, 6, 3, 13, 6, 3, 14, b, b, false);
/*      */       
/*      */ 
/* 1327 */       a(paramWorld, paramStructureBoundingBox, 2, 1, 6, 2, 3, 6, b, b, false);
/* 1328 */       a(paramWorld, paramStructureBoundingBox, 5, 1, 6, 5, 3, 6, b, b, false);
/* 1329 */       a(paramWorld, paramStructureBoundingBox, 2, 1, 9, 2, 3, 9, b, b, false);
/* 1330 */       a(paramWorld, paramStructureBoundingBox, 5, 1, 9, 5, 3, 9, b, b, false);
/*      */       
/* 1332 */       a(paramWorld, paramStructureBoundingBox, 3, 2, 6, 4, 2, 6, b, b, false);
/* 1333 */       a(paramWorld, paramStructureBoundingBox, 3, 2, 9, 4, 2, 9, b, b, false);
/* 1334 */       a(paramWorld, paramStructureBoundingBox, 2, 2, 7, 2, 2, 8, b, b, false);
/* 1335 */       a(paramWorld, paramStructureBoundingBox, 5, 2, 7, 5, 2, 8, b, b, false);
/*      */       
/* 1337 */       a(paramWorld, e, 2, 2, 5, paramStructureBoundingBox);
/* 1338 */       a(paramWorld, e, 5, 2, 5, paramStructureBoundingBox);
/* 1339 */       a(paramWorld, e, 2, 2, 10, paramStructureBoundingBox);
/* 1340 */       a(paramWorld, e, 5, 2, 10, paramStructureBoundingBox);
/* 1341 */       a(paramWorld, b, 2, 3, 5, paramStructureBoundingBox);
/* 1342 */       a(paramWorld, b, 5, 3, 5, paramStructureBoundingBox);
/* 1343 */       a(paramWorld, b, 2, 3, 10, paramStructureBoundingBox);
/* 1344 */       a(paramWorld, b, 5, 3, 10, paramStructureBoundingBox);
/*      */       
/*      */ 
/* 1347 */       if (localWorldGenMonumentStateTracker2.c[EnumDirection.SOUTH.a()] != 0) {
/* 1348 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/* 1350 */       if (localWorldGenMonumentStateTracker2.c[EnumDirection.EAST.a()] != 0) {
/* 1351 */         a(paramWorld, paramStructureBoundingBox, 7, 1, 3, 7, 2, 4, false);
/*      */       }
/* 1353 */       if (localWorldGenMonumentStateTracker2.c[EnumDirection.WEST.a()] != 0) {
/* 1354 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 3, 0, 2, 4, false);
/*      */       }
/* 1356 */       if (localWorldGenMonumentStateTracker1.c[EnumDirection.NORTH.a()] != 0) {
/* 1357 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 15, 4, 2, 15, false);
/*      */       }
/* 1359 */       if (localWorldGenMonumentStateTracker1.c[EnumDirection.WEST.a()] != 0) {
/* 1360 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 11, 0, 2, 12, false);
/*      */       }
/* 1362 */       if (localWorldGenMonumentStateTracker1.c[EnumDirection.EAST.a()] != 0) {
/* 1363 */         a(paramWorld, paramStructureBoundingBox, 7, 1, 11, 7, 2, 12, false);
/*      */       }
/*      */       
/* 1366 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenMonumentPiece4 extends WorldGenMonumentPieces.WorldGenMonumentPiece
/*      */   {
/*      */     public WorldGenMonumentPiece4() {}
/*      */     
/*      */     public WorldGenMonumentPiece4(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 1376 */       super(paramEnumDirection, paramWorldGenMonumentStateTracker, 2, 2, 1);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1381 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker1 = this.k.b[EnumDirection.EAST.a()];
/* 1382 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker2 = this.k;
/* 1383 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker3 = localWorldGenMonumentStateTracker2.b[EnumDirection.UP.a()];
/* 1384 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker4 = localWorldGenMonumentStateTracker1.b[EnumDirection.UP.a()];
/*      */       
/*      */ 
/*      */ 
/* 1388 */       if (this.k.a / 25 > 0) {
/* 1389 */         a(paramWorld, paramStructureBoundingBox, 8, 0, localWorldGenMonumentStateTracker1.c[EnumDirection.DOWN.a()]);
/* 1390 */         a(paramWorld, paramStructureBoundingBox, 0, 0, localWorldGenMonumentStateTracker2.c[EnumDirection.DOWN.a()]);
/*      */       }
/* 1392 */       if (localWorldGenMonumentStateTracker3.b[EnumDirection.UP.a()] == null) {
/* 1393 */         a(paramWorld, paramStructureBoundingBox, 1, 8, 1, 7, 8, 6, a);
/*      */       }
/* 1395 */       if (localWorldGenMonumentStateTracker4.b[EnumDirection.UP.a()] == null) {
/* 1396 */         a(paramWorld, paramStructureBoundingBox, 8, 8, 1, 14, 8, 6, a);
/*      */       }
/*      */       
/*      */ 
/* 1400 */       for (int i = 1; i <= 7; i++) {
/* 1401 */         IBlockData localIBlockData = b;
/* 1402 */         if ((i == 2) || (i == 6)) {
/* 1403 */           localIBlockData = a;
/*      */         }
/* 1405 */         a(paramWorld, paramStructureBoundingBox, 0, i, 0, 0, i, 7, localIBlockData, localIBlockData, false);
/* 1406 */         a(paramWorld, paramStructureBoundingBox, 15, i, 0, 15, i, 7, localIBlockData, localIBlockData, false);
/* 1407 */         a(paramWorld, paramStructureBoundingBox, 1, i, 0, 15, i, 0, localIBlockData, localIBlockData, false);
/* 1408 */         a(paramWorld, paramStructureBoundingBox, 1, i, 7, 14, i, 7, localIBlockData, localIBlockData, false);
/*      */       }
/*      */       
/*      */ 
/* 1412 */       a(paramWorld, paramStructureBoundingBox, 2, 1, 3, 2, 7, 4, b, b, false);
/* 1413 */       a(paramWorld, paramStructureBoundingBox, 3, 1, 2, 4, 7, 2, b, b, false);
/* 1414 */       a(paramWorld, paramStructureBoundingBox, 3, 1, 5, 4, 7, 5, b, b, false);
/* 1415 */       a(paramWorld, paramStructureBoundingBox, 13, 1, 3, 13, 7, 4, b, b, false);
/* 1416 */       a(paramWorld, paramStructureBoundingBox, 11, 1, 2, 12, 7, 2, b, b, false);
/* 1417 */       a(paramWorld, paramStructureBoundingBox, 11, 1, 5, 12, 7, 5, b, b, false);
/*      */       
/* 1419 */       a(paramWorld, paramStructureBoundingBox, 5, 1, 3, 5, 3, 4, b, b, false);
/* 1420 */       a(paramWorld, paramStructureBoundingBox, 10, 1, 3, 10, 3, 4, b, b, false);
/*      */       
/* 1422 */       a(paramWorld, paramStructureBoundingBox, 5, 7, 2, 10, 7, 5, b, b, false);
/* 1423 */       a(paramWorld, paramStructureBoundingBox, 5, 5, 2, 5, 7, 2, b, b, false);
/* 1424 */       a(paramWorld, paramStructureBoundingBox, 10, 5, 2, 10, 7, 2, b, b, false);
/* 1425 */       a(paramWorld, paramStructureBoundingBox, 5, 5, 5, 5, 7, 5, b, b, false);
/* 1426 */       a(paramWorld, paramStructureBoundingBox, 10, 5, 5, 10, 7, 5, b, b, false);
/* 1427 */       a(paramWorld, b, 6, 6, 2, paramStructureBoundingBox);
/* 1428 */       a(paramWorld, b, 9, 6, 2, paramStructureBoundingBox);
/* 1429 */       a(paramWorld, b, 6, 6, 5, paramStructureBoundingBox);
/* 1430 */       a(paramWorld, b, 9, 6, 5, paramStructureBoundingBox);
/*      */       
/* 1432 */       a(paramWorld, paramStructureBoundingBox, 5, 4, 3, 6, 4, 4, b, b, false);
/* 1433 */       a(paramWorld, paramStructureBoundingBox, 9, 4, 3, 10, 4, 4, b, b, false);
/* 1434 */       a(paramWorld, e, 5, 4, 2, paramStructureBoundingBox);
/* 1435 */       a(paramWorld, e, 5, 4, 5, paramStructureBoundingBox);
/* 1436 */       a(paramWorld, e, 10, 4, 2, paramStructureBoundingBox);
/* 1437 */       a(paramWorld, e, 10, 4, 5, paramStructureBoundingBox);
/*      */       
/*      */ 
/* 1440 */       if (localWorldGenMonumentStateTracker2.c[EnumDirection.SOUTH.a()] != 0) {
/* 1441 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/* 1443 */       if (localWorldGenMonumentStateTracker2.c[EnumDirection.NORTH.a()] != 0) {
/* 1444 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 7, 4, 2, 7, false);
/*      */       }
/* 1446 */       if (localWorldGenMonumentStateTracker2.c[EnumDirection.WEST.a()] != 0) {
/* 1447 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 3, 0, 2, 4, false);
/*      */       }
/* 1449 */       if (localWorldGenMonumentStateTracker1.c[EnumDirection.SOUTH.a()] != 0) {
/* 1450 */         a(paramWorld, paramStructureBoundingBox, 11, 1, 0, 12, 2, 0, false);
/*      */       }
/* 1452 */       if (localWorldGenMonumentStateTracker1.c[EnumDirection.NORTH.a()] != 0) {
/* 1453 */         a(paramWorld, paramStructureBoundingBox, 11, 1, 7, 12, 2, 7, false);
/*      */       }
/* 1455 */       if (localWorldGenMonumentStateTracker1.c[EnumDirection.EAST.a()] != 0) {
/* 1456 */         a(paramWorld, paramStructureBoundingBox, 15, 1, 3, 15, 2, 4, false);
/*      */       }
/* 1458 */       if (localWorldGenMonumentStateTracker3.c[EnumDirection.SOUTH.a()] != 0) {
/* 1459 */         a(paramWorld, paramStructureBoundingBox, 3, 5, 0, 4, 6, 0, false);
/*      */       }
/* 1461 */       if (localWorldGenMonumentStateTracker3.c[EnumDirection.NORTH.a()] != 0) {
/* 1462 */         a(paramWorld, paramStructureBoundingBox, 3, 5, 7, 4, 6, 7, false);
/*      */       }
/* 1464 */       if (localWorldGenMonumentStateTracker3.c[EnumDirection.WEST.a()] != 0) {
/* 1465 */         a(paramWorld, paramStructureBoundingBox, 0, 5, 3, 0, 6, 4, false);
/*      */       }
/* 1467 */       if (localWorldGenMonumentStateTracker4.c[EnumDirection.SOUTH.a()] != 0) {
/* 1468 */         a(paramWorld, paramStructureBoundingBox, 11, 5, 0, 12, 6, 0, false);
/*      */       }
/* 1470 */       if (localWorldGenMonumentStateTracker4.c[EnumDirection.NORTH.a()] != 0) {
/* 1471 */         a(paramWorld, paramStructureBoundingBox, 11, 5, 7, 12, 6, 7, false);
/*      */       }
/* 1473 */       if (localWorldGenMonumentStateTracker4.c[EnumDirection.EAST.a()] != 0) {
/* 1474 */         a(paramWorld, paramStructureBoundingBox, 15, 5, 3, 15, 6, 4, false);
/*      */       }
/*      */       
/* 1477 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenMonumentPiece6 extends WorldGenMonumentPieces.WorldGenMonumentPiece
/*      */   {
/*      */     public WorldGenMonumentPiece6() {}
/*      */     
/*      */     public WorldGenMonumentPiece6(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 1487 */       super(paramEnumDirection, paramWorldGenMonumentStateTracker, 1, 2, 2);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1492 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker1 = this.k.b[EnumDirection.NORTH.a()];
/* 1493 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker2 = this.k;
/* 1494 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker3 = localWorldGenMonumentStateTracker1.b[EnumDirection.UP.a()];
/* 1495 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker4 = localWorldGenMonumentStateTracker2.b[EnumDirection.UP.a()];
/* 1496 */       if (this.k.a / 25 > 0) {
/* 1497 */         a(paramWorld, paramStructureBoundingBox, 0, 8, localWorldGenMonumentStateTracker1.c[EnumDirection.DOWN.a()]);
/* 1498 */         a(paramWorld, paramStructureBoundingBox, 0, 0, localWorldGenMonumentStateTracker2.c[EnumDirection.DOWN.a()]);
/*      */       }
/* 1500 */       if (localWorldGenMonumentStateTracker4.b[EnumDirection.UP.a()] == null) {
/* 1501 */         a(paramWorld, paramStructureBoundingBox, 1, 8, 1, 6, 8, 7, a);
/*      */       }
/* 1503 */       if (localWorldGenMonumentStateTracker3.b[EnumDirection.UP.a()] == null) {
/* 1504 */         a(paramWorld, paramStructureBoundingBox, 1, 8, 8, 6, 8, 14, a);
/*      */       }
/*      */       
/*      */       IBlockData localIBlockData;
/* 1508 */       for (int i = 1; i <= 7; i++) {
/* 1509 */         localIBlockData = b;
/* 1510 */         if ((i == 2) || (i == 6)) {
/* 1511 */           localIBlockData = a;
/*      */         }
/* 1513 */         a(paramWorld, paramStructureBoundingBox, 0, i, 0, 0, i, 15, localIBlockData, localIBlockData, false);
/* 1514 */         a(paramWorld, paramStructureBoundingBox, 7, i, 0, 7, i, 15, localIBlockData, localIBlockData, false);
/* 1515 */         a(paramWorld, paramStructureBoundingBox, 1, i, 0, 6, i, 0, localIBlockData, localIBlockData, false);
/* 1516 */         a(paramWorld, paramStructureBoundingBox, 1, i, 15, 6, i, 15, localIBlockData, localIBlockData, false);
/*      */       }
/*      */       
/*      */ 
/* 1520 */       for (i = 1; i <= 7; i++) {
/* 1521 */         localIBlockData = c;
/* 1522 */         if ((i == 2) || (i == 6)) {
/* 1523 */           localIBlockData = e;
/*      */         }
/* 1525 */         a(paramWorld, paramStructureBoundingBox, 3, i, 7, 4, i, 8, localIBlockData, localIBlockData, false);
/*      */       }
/*      */       
/*      */ 
/* 1529 */       if (localWorldGenMonumentStateTracker2.c[EnumDirection.SOUTH.a()] != 0) {
/* 1530 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/* 1532 */       if (localWorldGenMonumentStateTracker2.c[EnumDirection.EAST.a()] != 0) {
/* 1533 */         a(paramWorld, paramStructureBoundingBox, 7, 1, 3, 7, 2, 4, false);
/*      */       }
/* 1535 */       if (localWorldGenMonumentStateTracker2.c[EnumDirection.WEST.a()] != 0) {
/* 1536 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 3, 0, 2, 4, false);
/*      */       }
/* 1538 */       if (localWorldGenMonumentStateTracker1.c[EnumDirection.NORTH.a()] != 0) {
/* 1539 */         a(paramWorld, paramStructureBoundingBox, 3, 1, 15, 4, 2, 15, false);
/*      */       }
/* 1541 */       if (localWorldGenMonumentStateTracker1.c[EnumDirection.WEST.a()] != 0) {
/* 1542 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 11, 0, 2, 12, false);
/*      */       }
/* 1544 */       if (localWorldGenMonumentStateTracker1.c[EnumDirection.EAST.a()] != 0) {
/* 1545 */         a(paramWorld, paramStructureBoundingBox, 7, 1, 11, 7, 2, 12, false);
/*      */       }
/*      */       
/* 1548 */       if (localWorldGenMonumentStateTracker4.c[EnumDirection.SOUTH.a()] != 0) {
/* 1549 */         a(paramWorld, paramStructureBoundingBox, 3, 5, 0, 4, 6, 0, false);
/*      */       }
/* 1551 */       if (localWorldGenMonumentStateTracker4.c[EnumDirection.EAST.a()] != 0) {
/* 1552 */         a(paramWorld, paramStructureBoundingBox, 7, 5, 3, 7, 6, 4, false);
/* 1553 */         a(paramWorld, paramStructureBoundingBox, 5, 4, 2, 6, 4, 5, b, b, false);
/* 1554 */         a(paramWorld, paramStructureBoundingBox, 6, 1, 2, 6, 3, 2, b, b, false);
/* 1555 */         a(paramWorld, paramStructureBoundingBox, 6, 1, 5, 6, 3, 5, b, b, false);
/*      */       }
/* 1557 */       if (localWorldGenMonumentStateTracker4.c[EnumDirection.WEST.a()] != 0) {
/* 1558 */         a(paramWorld, paramStructureBoundingBox, 0, 5, 3, 0, 6, 4, false);
/* 1559 */         a(paramWorld, paramStructureBoundingBox, 1, 4, 2, 2, 4, 5, b, b, false);
/* 1560 */         a(paramWorld, paramStructureBoundingBox, 1, 1, 2, 1, 3, 2, b, b, false);
/* 1561 */         a(paramWorld, paramStructureBoundingBox, 1, 1, 5, 1, 3, 5, b, b, false);
/*      */       }
/* 1563 */       if (localWorldGenMonumentStateTracker3.c[EnumDirection.NORTH.a()] != 0) {
/* 1564 */         a(paramWorld, paramStructureBoundingBox, 3, 5, 15, 4, 6, 15, false);
/*      */       }
/* 1566 */       if (localWorldGenMonumentStateTracker3.c[EnumDirection.WEST.a()] != 0) {
/* 1567 */         a(paramWorld, paramStructureBoundingBox, 0, 5, 11, 0, 6, 12, false);
/* 1568 */         a(paramWorld, paramStructureBoundingBox, 1, 4, 10, 2, 4, 13, b, b, false);
/* 1569 */         a(paramWorld, paramStructureBoundingBox, 1, 1, 10, 1, 3, 10, b, b, false);
/* 1570 */         a(paramWorld, paramStructureBoundingBox, 1, 1, 13, 1, 3, 13, b, b, false);
/*      */       }
/* 1572 */       if (localWorldGenMonumentStateTracker3.c[EnumDirection.EAST.a()] != 0) {
/* 1573 */         a(paramWorld, paramStructureBoundingBox, 7, 5, 11, 7, 6, 12, false);
/* 1574 */         a(paramWorld, paramStructureBoundingBox, 5, 4, 10, 6, 4, 13, b, b, false);
/* 1575 */         a(paramWorld, paramStructureBoundingBox, 6, 1, 10, 6, 3, 10, b, b, false);
/* 1576 */         a(paramWorld, paramStructureBoundingBox, 6, 1, 13, 6, 3, 13, b, b, false);
/*      */       }
/*      */       
/* 1579 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenMonumentPiece2 extends WorldGenMonumentPieces.WorldGenMonumentPiece
/*      */   {
/*      */     public WorldGenMonumentPiece2() {}
/*      */     
/*      */     public WorldGenMonumentPiece2(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 1589 */       super(paramEnumDirection, paramWorldGenMonumentStateTracker, 2, 2, 2);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1595 */       a(paramWorld, paramStructureBoundingBox, 1, 8, 0, 14, 8, 14, a);
/*      */       
/*      */ 
/*      */ 
/* 1599 */       int i = 7;
/* 1600 */       IBlockData localIBlockData = b;
/* 1601 */       a(paramWorld, paramStructureBoundingBox, 0, i, 0, 0, i, 15, localIBlockData, localIBlockData, false);
/* 1602 */       a(paramWorld, paramStructureBoundingBox, 15, i, 0, 15, i, 15, localIBlockData, localIBlockData, false);
/* 1603 */       a(paramWorld, paramStructureBoundingBox, 1, i, 0, 15, i, 0, localIBlockData, localIBlockData, false);
/* 1604 */       a(paramWorld, paramStructureBoundingBox, 1, i, 15, 14, i, 15, localIBlockData, localIBlockData, false);
/*      */       
/* 1606 */       for (i = 1; i <= 6; i++) {
/* 1607 */         localIBlockData = b;
/* 1608 */         if ((i == 2) || (i == 6)) {
/* 1609 */           localIBlockData = a;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1614 */         for (int k = 0; k <= 15; k += 15) {
/* 1615 */           a(paramWorld, paramStructureBoundingBox, k, i, 0, k, i, 1, localIBlockData, localIBlockData, false);
/* 1616 */           a(paramWorld, paramStructureBoundingBox, k, i, 6, k, i, 9, localIBlockData, localIBlockData, false);
/* 1617 */           a(paramWorld, paramStructureBoundingBox, k, i, 14, k, i, 15, localIBlockData, localIBlockData, false);
/*      */         }
/* 1619 */         a(paramWorld, paramStructureBoundingBox, 1, i, 0, 1, i, 0, localIBlockData, localIBlockData, false);
/* 1620 */         a(paramWorld, paramStructureBoundingBox, 6, i, 0, 9, i, 0, localIBlockData, localIBlockData, false);
/* 1621 */         a(paramWorld, paramStructureBoundingBox, 14, i, 0, 14, i, 0, localIBlockData, localIBlockData, false);
/*      */         
/* 1623 */         a(paramWorld, paramStructureBoundingBox, 1, i, 15, 14, i, 15, localIBlockData, localIBlockData, false);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1628 */       a(paramWorld, paramStructureBoundingBox, 6, 3, 6, 9, 6, 9, c, c, false);
/* 1629 */       a(paramWorld, paramStructureBoundingBox, 7, 4, 7, 8, 5, 8, Blocks.GOLD_BLOCK.getBlockData(), Blocks.GOLD_BLOCK.getBlockData(), false);
/* 1630 */       for (i = 3; i <= 6; i += 3) {
/* 1631 */         for (int j = 6; j <= 9; j += 3) {
/* 1632 */           a(paramWorld, e, j, i, 6, paramStructureBoundingBox);
/* 1633 */           a(paramWorld, e, j, i, 9, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/* 1637 */       a(paramWorld, paramStructureBoundingBox, 5, 1, 6, 5, 2, 6, b, b, false);
/* 1638 */       a(paramWorld, paramStructureBoundingBox, 5, 1, 9, 5, 2, 9, b, b, false);
/* 1639 */       a(paramWorld, paramStructureBoundingBox, 10, 1, 6, 10, 2, 6, b, b, false);
/* 1640 */       a(paramWorld, paramStructureBoundingBox, 10, 1, 9, 10, 2, 9, b, b, false);
/* 1641 */       a(paramWorld, paramStructureBoundingBox, 6, 1, 5, 6, 2, 5, b, b, false);
/* 1642 */       a(paramWorld, paramStructureBoundingBox, 9, 1, 5, 9, 2, 5, b, b, false);
/* 1643 */       a(paramWorld, paramStructureBoundingBox, 6, 1, 10, 6, 2, 10, b, b, false);
/* 1644 */       a(paramWorld, paramStructureBoundingBox, 9, 1, 10, 9, 2, 10, b, b, false);
/*      */       
/* 1646 */       a(paramWorld, paramStructureBoundingBox, 5, 2, 5, 5, 6, 5, b, b, false);
/* 1647 */       a(paramWorld, paramStructureBoundingBox, 5, 2, 10, 5, 6, 10, b, b, false);
/* 1648 */       a(paramWorld, paramStructureBoundingBox, 10, 2, 5, 10, 6, 5, b, b, false);
/* 1649 */       a(paramWorld, paramStructureBoundingBox, 10, 2, 10, 10, 6, 10, b, b, false);
/*      */       
/* 1651 */       a(paramWorld, paramStructureBoundingBox, 5, 7, 1, 5, 7, 6, b, b, false);
/* 1652 */       a(paramWorld, paramStructureBoundingBox, 10, 7, 1, 10, 7, 6, b, b, false);
/* 1653 */       a(paramWorld, paramStructureBoundingBox, 5, 7, 9, 5, 7, 14, b, b, false);
/* 1654 */       a(paramWorld, paramStructureBoundingBox, 10, 7, 9, 10, 7, 14, b, b, false);
/*      */       
/* 1656 */       a(paramWorld, paramStructureBoundingBox, 1, 7, 5, 6, 7, 5, b, b, false);
/* 1657 */       a(paramWorld, paramStructureBoundingBox, 1, 7, 10, 6, 7, 10, b, b, false);
/* 1658 */       a(paramWorld, paramStructureBoundingBox, 9, 7, 5, 14, 7, 5, b, b, false);
/* 1659 */       a(paramWorld, paramStructureBoundingBox, 9, 7, 10, 14, 7, 10, b, b, false);
/*      */       
/*      */ 
/* 1662 */       a(paramWorld, paramStructureBoundingBox, 2, 1, 2, 2, 1, 3, b, b, false);
/* 1663 */       a(paramWorld, paramStructureBoundingBox, 3, 1, 2, 3, 1, 2, b, b, false);
/* 1664 */       a(paramWorld, paramStructureBoundingBox, 13, 1, 2, 13, 1, 3, b, b, false);
/* 1665 */       a(paramWorld, paramStructureBoundingBox, 12, 1, 2, 12, 1, 2, b, b, false);
/* 1666 */       a(paramWorld, paramStructureBoundingBox, 2, 1, 12, 2, 1, 13, b, b, false);
/* 1667 */       a(paramWorld, paramStructureBoundingBox, 3, 1, 13, 3, 1, 13, b, b, false);
/* 1668 */       a(paramWorld, paramStructureBoundingBox, 13, 1, 12, 13, 1, 13, b, b, false);
/* 1669 */       a(paramWorld, paramStructureBoundingBox, 12, 1, 13, 12, 1, 13, b, b, false);
/*      */       
/* 1671 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenMonumentPiece8 extends WorldGenMonumentPieces.WorldGenMonumentPiece
/*      */   {
/*      */     private int o;
/*      */     
/*      */     public WorldGenMonumentPiece8() {}
/*      */     
/*      */     public WorldGenMonumentPiece8(EnumDirection paramEnumDirection, StructureBoundingBox paramStructureBoundingBox, int paramInt)
/*      */     {
/* 1683 */       super(paramStructureBoundingBox);
/* 1684 */       this.o = (paramInt & 0x1);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/*      */       int i;
/* 1689 */       if (this.o == 0) {
/* 1690 */         for (i = 0; i < 4; i++) {
/* 1691 */           a(paramWorld, paramStructureBoundingBox, 10 - i, 3 - i, 20 - i, 12 + i, 3 - i, 20, b, b, false);
/*      */         }
/* 1693 */         a(paramWorld, paramStructureBoundingBox, 7, 0, 6, 15, 0, 16, b, b, false);
/* 1694 */         a(paramWorld, paramStructureBoundingBox, 6, 0, 6, 6, 3, 20, b, b, false);
/* 1695 */         a(paramWorld, paramStructureBoundingBox, 16, 0, 6, 16, 3, 20, b, b, false);
/* 1696 */         a(paramWorld, paramStructureBoundingBox, 7, 1, 7, 7, 1, 20, b, b, false);
/* 1697 */         a(paramWorld, paramStructureBoundingBox, 15, 1, 7, 15, 1, 20, b, b, false);
/*      */         
/* 1699 */         a(paramWorld, paramStructureBoundingBox, 7, 1, 6, 9, 3, 6, b, b, false);
/* 1700 */         a(paramWorld, paramStructureBoundingBox, 13, 1, 6, 15, 3, 6, b, b, false);
/* 1701 */         a(paramWorld, paramStructureBoundingBox, 8, 1, 7, 9, 1, 7, b, b, false);
/* 1702 */         a(paramWorld, paramStructureBoundingBox, 13, 1, 7, 14, 1, 7, b, b, false);
/* 1703 */         a(paramWorld, paramStructureBoundingBox, 9, 0, 5, 13, 0, 5, b, b, false);
/*      */         
/* 1705 */         a(paramWorld, paramStructureBoundingBox, 10, 0, 7, 12, 0, 7, c, c, false);
/* 1706 */         a(paramWorld, paramStructureBoundingBox, 8, 0, 10, 8, 0, 12, c, c, false);
/* 1707 */         a(paramWorld, paramStructureBoundingBox, 14, 0, 10, 14, 0, 12, c, c, false);
/*      */         
/* 1709 */         for (i = 18; i >= 7; i -= 3) {
/* 1710 */           a(paramWorld, e, 6, 3, i, paramStructureBoundingBox);
/* 1711 */           a(paramWorld, e, 16, 3, i, paramStructureBoundingBox);
/*      */         }
/* 1713 */         a(paramWorld, e, 10, 0, 10, paramStructureBoundingBox);
/* 1714 */         a(paramWorld, e, 12, 0, 10, paramStructureBoundingBox);
/* 1715 */         a(paramWorld, e, 10, 0, 12, paramStructureBoundingBox);
/* 1716 */         a(paramWorld, e, 12, 0, 12, paramStructureBoundingBox);
/*      */         
/* 1718 */         a(paramWorld, e, 8, 3, 6, paramStructureBoundingBox);
/* 1719 */         a(paramWorld, e, 14, 3, 6, paramStructureBoundingBox);
/*      */         
/*      */ 
/* 1722 */         a(paramWorld, b, 4, 2, 4, paramStructureBoundingBox);
/* 1723 */         a(paramWorld, e, 4, 1, 4, paramStructureBoundingBox);
/* 1724 */         a(paramWorld, b, 4, 0, 4, paramStructureBoundingBox);
/*      */         
/* 1726 */         a(paramWorld, b, 18, 2, 4, paramStructureBoundingBox);
/* 1727 */         a(paramWorld, e, 18, 1, 4, paramStructureBoundingBox);
/* 1728 */         a(paramWorld, b, 18, 0, 4, paramStructureBoundingBox);
/*      */         
/* 1730 */         a(paramWorld, b, 4, 2, 18, paramStructureBoundingBox);
/* 1731 */         a(paramWorld, e, 4, 1, 18, paramStructureBoundingBox);
/* 1732 */         a(paramWorld, b, 4, 0, 18, paramStructureBoundingBox);
/*      */         
/* 1734 */         a(paramWorld, b, 18, 2, 18, paramStructureBoundingBox);
/* 1735 */         a(paramWorld, e, 18, 1, 18, paramStructureBoundingBox);
/* 1736 */         a(paramWorld, b, 18, 0, 18, paramStructureBoundingBox);
/*      */         
/*      */ 
/* 1739 */         a(paramWorld, b, 9, 7, 20, paramStructureBoundingBox);
/* 1740 */         a(paramWorld, b, 13, 7, 20, paramStructureBoundingBox);
/* 1741 */         a(paramWorld, paramStructureBoundingBox, 6, 0, 21, 7, 4, 21, b, b, false);
/* 1742 */         a(paramWorld, paramStructureBoundingBox, 15, 0, 21, 16, 4, 21, b, b, false);
/*      */         
/* 1744 */         a(paramWorld, paramStructureBoundingBox, 11, 2, 16);
/* 1745 */       } else if (this.o == 1) {
/* 1746 */         a(paramWorld, paramStructureBoundingBox, 9, 3, 18, 13, 3, 20, b, b, false);
/* 1747 */         a(paramWorld, paramStructureBoundingBox, 9, 0, 18, 9, 2, 18, b, b, false);
/* 1748 */         a(paramWorld, paramStructureBoundingBox, 13, 0, 18, 13, 2, 18, b, b, false);
/* 1749 */         i = 9;
/* 1750 */         int j = 20;
/* 1751 */         int k = 5;
/* 1752 */         for (int m = 0; m < 2; m++) {
/* 1753 */           a(paramWorld, b, i, k + 1, j, paramStructureBoundingBox);
/* 1754 */           a(paramWorld, e, i, k, j, paramStructureBoundingBox);
/* 1755 */           a(paramWorld, b, i, k - 1, j, paramStructureBoundingBox);
/* 1756 */           i = 13;
/*      */         }
/*      */         
/* 1759 */         a(paramWorld, paramStructureBoundingBox, 7, 3, 7, 15, 3, 14, b, b, false);
/* 1760 */         i = 10;
/* 1761 */         for (m = 0; m < 2; m++) {
/* 1762 */           a(paramWorld, paramStructureBoundingBox, i, 0, 10, i, 6, 10, b, b, false);
/* 1763 */           a(paramWorld, paramStructureBoundingBox, i, 0, 12, i, 6, 12, b, b, false);
/* 1764 */           a(paramWorld, e, i, 0, 10, paramStructureBoundingBox);
/* 1765 */           a(paramWorld, e, i, 0, 12, paramStructureBoundingBox);
/* 1766 */           a(paramWorld, e, i, 4, 10, paramStructureBoundingBox);
/* 1767 */           a(paramWorld, e, i, 4, 12, paramStructureBoundingBox);
/* 1768 */           i = 12;
/*      */         }
/* 1770 */         i = 8;
/* 1771 */         for (m = 0; m < 2; m++) {
/* 1772 */           a(paramWorld, paramStructureBoundingBox, i, 0, 7, i, 2, 7, b, b, false);
/* 1773 */           a(paramWorld, paramStructureBoundingBox, i, 0, 14, i, 2, 14, b, b, false);
/* 1774 */           i = 14;
/*      */         }
/* 1776 */         a(paramWorld, paramStructureBoundingBox, 8, 3, 8, 8, 3, 13, c, c, false);
/* 1777 */         a(paramWorld, paramStructureBoundingBox, 14, 3, 8, 14, 3, 13, c, c, false);
/*      */         
/* 1779 */         a(paramWorld, paramStructureBoundingBox, 11, 5, 13);
/*      */       }
/*      */       
/* 1782 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenMonumentPiecePenthouse extends WorldGenMonumentPieces.WorldGenMonumentPiece
/*      */   {
/*      */     public WorldGenMonumentPiecePenthouse() {}
/*      */     
/*      */     public WorldGenMonumentPiecePenthouse(EnumDirection paramEnumDirection, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1792 */       super(paramStructureBoundingBox);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1798 */       a(paramWorld, paramStructureBoundingBox, 2, -1, 2, 11, -1, 11, b, b, false);
/* 1799 */       a(paramWorld, paramStructureBoundingBox, 0, -1, 0, 1, -1, 11, a, a, false);
/* 1800 */       a(paramWorld, paramStructureBoundingBox, 12, -1, 0, 13, -1, 11, a, a, false);
/* 1801 */       a(paramWorld, paramStructureBoundingBox, 2, -1, 0, 11, -1, 1, a, a, false);
/* 1802 */       a(paramWorld, paramStructureBoundingBox, 2, -1, 12, 11, -1, 13, a, a, false);
/*      */       
/* 1804 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 0, 0, 13, b, b, false);
/* 1805 */       a(paramWorld, paramStructureBoundingBox, 13, 0, 0, 13, 0, 13, b, b, false);
/* 1806 */       a(paramWorld, paramStructureBoundingBox, 1, 0, 0, 12, 0, 0, b, b, false);
/* 1807 */       a(paramWorld, paramStructureBoundingBox, 1, 0, 13, 12, 0, 13, b, b, false);
/*      */       
/* 1809 */       for (int i = 2; i <= 11; i += 3) {
/* 1810 */         a(paramWorld, e, 0, 0, i, paramStructureBoundingBox);
/* 1811 */         a(paramWorld, e, 13, 0, i, paramStructureBoundingBox);
/* 1812 */         a(paramWorld, e, i, 0, 0, paramStructureBoundingBox);
/*      */       }
/*      */       
/* 1815 */       a(paramWorld, paramStructureBoundingBox, 2, 0, 3, 4, 0, 9, b, b, false);
/* 1816 */       a(paramWorld, paramStructureBoundingBox, 9, 0, 3, 11, 0, 9, b, b, false);
/* 1817 */       a(paramWorld, paramStructureBoundingBox, 4, 0, 9, 9, 0, 11, b, b, false);
/* 1818 */       a(paramWorld, b, 5, 0, 8, paramStructureBoundingBox);
/* 1819 */       a(paramWorld, b, 8, 0, 8, paramStructureBoundingBox);
/* 1820 */       a(paramWorld, b, 10, 0, 10, paramStructureBoundingBox);
/* 1821 */       a(paramWorld, b, 3, 0, 10, paramStructureBoundingBox);
/* 1822 */       a(paramWorld, paramStructureBoundingBox, 3, 0, 3, 3, 0, 7, c, c, false);
/* 1823 */       a(paramWorld, paramStructureBoundingBox, 10, 0, 3, 10, 0, 7, c, c, false);
/* 1824 */       a(paramWorld, paramStructureBoundingBox, 6, 0, 10, 7, 0, 10, c, c, false);
/*      */       
/* 1826 */       i = 3;
/* 1827 */       for (int j = 0; j < 2; j++) {
/* 1828 */         for (int k = 2; k <= 8; k += 3) {
/* 1829 */           a(paramWorld, paramStructureBoundingBox, i, 0, k, i, 2, k, b, b, false);
/*      */         }
/* 1831 */         i = 10;
/*      */       }
/* 1833 */       a(paramWorld, paramStructureBoundingBox, 5, 0, 10, 5, 2, 10, b, b, false);
/* 1834 */       a(paramWorld, paramStructureBoundingBox, 8, 0, 10, 8, 2, 10, b, b, false);
/*      */       
/* 1836 */       a(paramWorld, paramStructureBoundingBox, 6, -1, 7, 7, -1, 8, c, c, false);
/*      */       
/*      */ 
/* 1839 */       a(paramWorld, paramStructureBoundingBox, 6, -1, 3, 7, -1, 4, false);
/*      */       
/* 1841 */       a(paramWorld, paramStructureBoundingBox, 6, 1, 6);
/*      */       
/* 1843 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static class WorldGenMonumentStateTracker {
/*      */     int a;
/* 1849 */     WorldGenMonumentStateTracker[] b = new WorldGenMonumentStateTracker[6];
/* 1850 */     boolean[] c = new boolean[6];
/*      */     boolean d;
/*      */     boolean e;
/*      */     int f;
/*      */     
/*      */     public WorldGenMonumentStateTracker(int paramInt) {
/* 1856 */       this.a = paramInt;
/*      */     }
/*      */     
/*      */     public void a(EnumDirection paramEnumDirection, WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker) {
/* 1860 */       this.b[paramEnumDirection.a()] = paramWorldGenMonumentStateTracker;
/* 1861 */       paramWorldGenMonumentStateTracker.b[paramEnumDirection.opposite().a()] = this;
/*      */     }
/*      */     
/*      */     public void a() {
/* 1865 */       for (int i = 0; i < 6; i++) {
/* 1866 */         this.c[i] = (this.b[i] != null ? 1 : false);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean a(int paramInt) {
/* 1871 */       if (this.e) {
/* 1872 */         return true;
/*      */       }
/* 1874 */       this.f = paramInt;
/* 1875 */       for (int i = 0; i < 6; i++) {
/* 1876 */         if ((this.b[i] != null) && (this.c[i] != 0) && 
/* 1877 */           (this.b[i].f != paramInt) && (this.b[i].a(paramInt))) {
/* 1878 */           return true;
/*      */         }
/*      */       }
/*      */       
/* 1882 */       return false;
/*      */     }
/*      */     
/*      */     public boolean b() {
/* 1886 */       return this.a >= 75;
/*      */     }
/*      */     
/*      */     public int c() {
/* 1890 */       int i = 0;
/* 1891 */       for (int j = 0; j < 6; j++) {
/* 1892 */         if (this.c[j] != 0) {
/* 1893 */           i++;
/*      */         }
/*      */       }
/* 1896 */       return i;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract interface IWorldGenMonumentPieceSelector
/*      */   {
/*      */     public abstract boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker);
/*      */     
/*      */     public abstract WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom);
/*      */   }
/*      */   
/*      */   static class WorldGenMonumentPieceSelector2 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector {
/*      */     public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker) {
/* 1909 */       return true;
/*      */     }
/*      */     
/*      */     public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 1914 */       paramWorldGenMonumentStateTracker.d = true;
/* 1915 */       return new WorldGenMonumentPieces.WorldGenMonumentPieceSimple(paramEnumDirection, paramWorldGenMonumentStateTracker, paramRandom);
/*      */     }
/*      */   }
/*      */   
/*      */   static class WorldGenMonumentPieceSelector1 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
/*      */   {
/*      */     public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker) {
/* 1922 */       return (paramWorldGenMonumentStateTracker.c[EnumDirection.WEST.a()] == 0) && (paramWorldGenMonumentStateTracker.c[EnumDirection.EAST.a()] == 0) && (paramWorldGenMonumentStateTracker.c[EnumDirection.NORTH.a()] == 0) && (paramWorldGenMonumentStateTracker.c[EnumDirection.SOUTH.a()] == 0) && (paramWorldGenMonumentStateTracker.c[EnumDirection.UP.a()] == 0);
/*      */     }
/*      */     
/*      */     public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 1927 */       paramWorldGenMonumentStateTracker.d = true;
/* 1928 */       return new WorldGenMonumentPieces.WorldGenMonumentPieceSimpleT(paramEnumDirection, paramWorldGenMonumentStateTracker, paramRandom);
/*      */     }
/*      */   }
/*      */   
/*      */   static class WorldGenMonumentPieceSelector5 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
/*      */   {
/*      */     public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker) {
/* 1935 */       if ((paramWorldGenMonumentStateTracker.c[EnumDirection.UP.a()] != 0) && (!paramWorldGenMonumentStateTracker.b[EnumDirection.UP.a()].d)) {
/* 1936 */         return true;
/*      */       }
/* 1938 */       return false;
/*      */     }
/*      */     
/*      */     public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 1943 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker = paramWorldGenMonumentStateTracker;
/* 1944 */       localWorldGenMonumentStateTracker.d = true;
/* 1945 */       localWorldGenMonumentStateTracker.b[EnumDirection.UP.a()].d = true;
/* 1946 */       return new WorldGenMonumentPieces.WorldGenMonumentPiece5(paramEnumDirection, localWorldGenMonumentStateTracker, paramRandom);
/*      */     }
/*      */   }
/*      */   
/*      */   static class WorldGenMonumentPieceSelector7 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
/*      */   {
/*      */     public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker) {
/* 1953 */       if ((paramWorldGenMonumentStateTracker.c[EnumDirection.EAST.a()] != 0) && (!paramWorldGenMonumentStateTracker.b[EnumDirection.EAST.a()].d)) {
/* 1954 */         return true;
/*      */       }
/* 1956 */       return false;
/*      */     }
/*      */     
/*      */     public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 1961 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker = paramWorldGenMonumentStateTracker;
/* 1962 */       localWorldGenMonumentStateTracker.d = true;
/* 1963 */       localWorldGenMonumentStateTracker.b[EnumDirection.EAST.a()].d = true;
/* 1964 */       return new WorldGenMonumentPieces.WorldGenMonumentPiece3(paramEnumDirection, localWorldGenMonumentStateTracker, paramRandom);
/*      */     }
/*      */   }
/*      */   
/*      */   static class WorldGenMonumentPieceSelector3 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
/*      */   {
/*      */     public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker) {
/* 1971 */       if ((paramWorldGenMonumentStateTracker.c[EnumDirection.NORTH.a()] != 0) && (!paramWorldGenMonumentStateTracker.b[EnumDirection.NORTH.a()].d)) {
/* 1972 */         return true;
/*      */       }
/* 1974 */       return false;
/*      */     }
/*      */     
/*      */     public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 1979 */       WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker = paramWorldGenMonumentStateTracker;
/* 1980 */       if ((paramWorldGenMonumentStateTracker.c[EnumDirection.NORTH.a()] == 0) || (paramWorldGenMonumentStateTracker.b[EnumDirection.NORTH.a()].d)) {
/* 1981 */         localWorldGenMonumentStateTracker = paramWorldGenMonumentStateTracker.b[EnumDirection.SOUTH.a()];
/*      */       }
/* 1983 */       localWorldGenMonumentStateTracker.d = true;
/* 1984 */       localWorldGenMonumentStateTracker.b[EnumDirection.NORTH.a()].d = true;
/* 1985 */       return new WorldGenMonumentPieces.WorldGenMonumentPiece7(paramEnumDirection, localWorldGenMonumentStateTracker, paramRandom);
/*      */     }
/*      */   }
/*      */   
/*      */   static class WorldGenMonumentPieceSelector6 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
/*      */   {
/*      */     public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker) {
/* 1992 */       if ((paramWorldGenMonumentStateTracker.c[EnumDirection.EAST.a()] != 0) && (!paramWorldGenMonumentStateTracker.b[EnumDirection.EAST.a()].d) && 
/* 1993 */         (paramWorldGenMonumentStateTracker.c[EnumDirection.UP.a()] != 0) && (!paramWorldGenMonumentStateTracker.b[EnumDirection.UP.a()].d)) {
/* 1994 */         WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker = paramWorldGenMonumentStateTracker.b[EnumDirection.EAST.a()];
/*      */         
/* 1996 */         return (localWorldGenMonumentStateTracker.c[EnumDirection.UP.a()] != 0) && (!localWorldGenMonumentStateTracker.b[EnumDirection.UP.a()].d);
/*      */       }
/*      */       
/* 1999 */       return false;
/*      */     }
/*      */     
/*      */     public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 2004 */       paramWorldGenMonumentStateTracker.d = true;
/* 2005 */       paramWorldGenMonumentStateTracker.b[EnumDirection.EAST.a()].d = true;
/* 2006 */       paramWorldGenMonumentStateTracker.b[EnumDirection.UP.a()].d = true;
/* 2007 */       paramWorldGenMonumentStateTracker.b[EnumDirection.EAST.a()].b[EnumDirection.UP.a()].d = true;
/* 2008 */       return new WorldGenMonumentPieces.WorldGenMonumentPiece4(paramEnumDirection, paramWorldGenMonumentStateTracker, paramRandom);
/*      */     }
/*      */   }
/*      */   
/*      */   static class WorldGenMonumentPieceSelector4 implements WorldGenMonumentPieces.IWorldGenMonumentPieceSelector
/*      */   {
/*      */     public boolean a(WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker) {
/* 2015 */       if ((paramWorldGenMonumentStateTracker.c[EnumDirection.NORTH.a()] != 0) && (!paramWorldGenMonumentStateTracker.b[EnumDirection.NORTH.a()].d) && 
/* 2016 */         (paramWorldGenMonumentStateTracker.c[EnumDirection.UP.a()] != 0) && (!paramWorldGenMonumentStateTracker.b[EnumDirection.UP.a()].d)) {
/* 2017 */         WorldGenMonumentPieces.WorldGenMonumentStateTracker localWorldGenMonumentStateTracker = paramWorldGenMonumentStateTracker.b[EnumDirection.NORTH.a()];
/*      */         
/* 2019 */         return (localWorldGenMonumentStateTracker.c[EnumDirection.UP.a()] != 0) && (!localWorldGenMonumentStateTracker.b[EnumDirection.UP.a()].d);
/*      */       }
/*      */       
/* 2022 */       return false;
/*      */     }
/*      */     
/*      */     public WorldGenMonumentPieces.WorldGenMonumentPiece a(EnumDirection paramEnumDirection, WorldGenMonumentPieces.WorldGenMonumentStateTracker paramWorldGenMonumentStateTracker, Random paramRandom)
/*      */     {
/* 2027 */       paramWorldGenMonumentStateTracker.d = true;
/* 2028 */       paramWorldGenMonumentStateTracker.b[EnumDirection.NORTH.a()].d = true;
/* 2029 */       paramWorldGenMonumentStateTracker.b[EnumDirection.UP.a()].d = true;
/* 2030 */       paramWorldGenMonumentStateTracker.b[EnumDirection.NORTH.a()].b[EnumDirection.UP.a()].d = true;
/* 2031 */       return new WorldGenMonumentPieces.WorldGenMonumentPiece6(paramEnumDirection, paramWorldGenMonumentStateTracker, paramRandom);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenMonumentPieces.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */