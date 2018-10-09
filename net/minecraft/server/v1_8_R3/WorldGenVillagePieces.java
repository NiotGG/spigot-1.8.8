/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ 
/*      */ public class WorldGenVillagePieces
/*      */ {
/*      */   public static void a()
/*      */   {
/*   12 */     WorldGenFactory.a(WorldGenVillageLibrary.class, "ViBH");
/*   13 */     WorldGenFactory.a(WorldGenVillageFarm2.class, "ViDF");
/*   14 */     WorldGenFactory.a(WorldGenVillageFarm.class, "ViF");
/*   15 */     WorldGenFactory.a(WorldGenVillageLight.class, "ViL");
/*   16 */     WorldGenFactory.a(WorldGenVillageButcher.class, "ViPH");
/*   17 */     WorldGenFactory.a(WorldGenVillageHouse.class, "ViSH");
/*   18 */     WorldGenFactory.a(WorldGenVillageHut.class, "ViSmH");
/*   19 */     WorldGenFactory.a(WorldGenVillageTemple.class, "ViST");
/*   20 */     WorldGenFactory.a(WorldGenVillageBlacksmith.class, "ViS");
/*   21 */     WorldGenFactory.a(WorldGenVillageStartPiece.class, "ViStart");
/*   22 */     WorldGenFactory.a(WorldGenVillageRoad.class, "ViSR");
/*   23 */     WorldGenFactory.a(WorldGenVillageHouse2.class, "ViTRH");
/*   24 */     WorldGenFactory.a(WorldGenVillageWell.class, "ViW");
/*      */   }
/*      */   
/*      */   public static List<WorldGenVillagePieceWeight> a(Random random, int i) {
/*   28 */     ArrayList arraylist = com.google.common.collect.Lists.newArrayList();
/*      */     
/*   30 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageHouse.class, 4, MathHelper.nextInt(random, 2 + i, 4 + i * 2)));
/*   31 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageTemple.class, 20, MathHelper.nextInt(random, 0 + i, 1 + i)));
/*   32 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageLibrary.class, 20, MathHelper.nextInt(random, 0 + i, 2 + i)));
/*   33 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageHut.class, 3, MathHelper.nextInt(random, 2 + i, 5 + i * 3)));
/*   34 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageButcher.class, 15, MathHelper.nextInt(random, 0 + i, 2 + i)));
/*   35 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageFarm2.class, 3, MathHelper.nextInt(random, 1 + i, 4 + i)));
/*   36 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageFarm.class, 3, MathHelper.nextInt(random, 2 + i, 4 + i * 2)));
/*   37 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageBlacksmith.class, 15, MathHelper.nextInt(random, 0, 1 + i)));
/*   38 */     arraylist.add(new WorldGenVillagePieceWeight(WorldGenVillageHouse2.class, 8, MathHelper.nextInt(random, 0 + i, 3 + i * 2)));
/*   39 */     Iterator iterator = arraylist.iterator();
/*      */     
/*   41 */     while (iterator.hasNext()) {
/*   42 */       if (((WorldGenVillagePieceWeight)iterator.next()).d == 0) {
/*   43 */         iterator.remove();
/*      */       }
/*      */     }
/*      */     
/*   47 */     return arraylist;
/*      */   }
/*      */   
/*      */   private static int a(List<WorldGenVillagePieceWeight> list) {
/*   51 */     boolean flag = false;
/*   52 */     int i = 0;
/*      */     
/*      */     WorldGenVillagePieceWeight worldgenvillagepieces_worldgenvillagepieceweight;
/*      */     
/*   56 */     for (Iterator iterator = list.iterator(); iterator.hasNext(); i += worldgenvillagepieces_worldgenvillagepieceweight.b) {
/*   57 */       worldgenvillagepieces_worldgenvillagepieceweight = (WorldGenVillagePieceWeight)iterator.next();
/*   58 */       if ((worldgenvillagepieces_worldgenvillagepieceweight.d > 0) && (worldgenvillagepieces_worldgenvillagepieceweight.c < worldgenvillagepieces_worldgenvillagepieceweight.d)) {
/*   59 */         flag = true;
/*      */       }
/*      */     }
/*      */     
/*   63 */     return flag ? i : -1;
/*      */   }
/*      */   
/*      */   private static WorldGenVillagePiece a(WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, WorldGenVillagePieceWeight worldgenvillagepieces_worldgenvillagepieceweight, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
/*   67 */     Class oclass = worldgenvillagepieces_worldgenvillagepieceweight.a;
/*   68 */     Object object = null;
/*      */     
/*   70 */     if (oclass == WorldGenVillageHouse.class) {
/*   71 */       object = WorldGenVillageHouse.a(worldgenvillagepieces_worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
/*   72 */     } else if (oclass == WorldGenVillageTemple.class) {
/*   73 */       object = WorldGenVillageTemple.a(worldgenvillagepieces_worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
/*   74 */     } else if (oclass == WorldGenVillageLibrary.class) {
/*   75 */       object = WorldGenVillageLibrary.a(worldgenvillagepieces_worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
/*   76 */     } else if (oclass == WorldGenVillageHut.class) {
/*   77 */       object = WorldGenVillageHut.a(worldgenvillagepieces_worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
/*   78 */     } else if (oclass == WorldGenVillageButcher.class) {
/*   79 */       object = WorldGenVillageButcher.a(worldgenvillagepieces_worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
/*   80 */     } else if (oclass == WorldGenVillageFarm2.class) {
/*   81 */       object = WorldGenVillageFarm2.a(worldgenvillagepieces_worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
/*   82 */     } else if (oclass == WorldGenVillageFarm.class) {
/*   83 */       object = WorldGenVillageFarm.a(worldgenvillagepieces_worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
/*   84 */     } else if (oclass == WorldGenVillageBlacksmith.class) {
/*   85 */       object = WorldGenVillageBlacksmith.a(worldgenvillagepieces_worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
/*   86 */     } else if (oclass == WorldGenVillageHouse2.class) {
/*   87 */       object = WorldGenVillageHouse2.a(worldgenvillagepieces_worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l);
/*      */     }
/*      */     
/*   90 */     return (WorldGenVillagePiece)object;
/*      */   }
/*      */   
/*      */   private static WorldGenVillagePiece c(WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
/*   94 */     int i1 = a(worldgenvillagepieces_worldgenvillagestartpiece.e);
/*      */     
/*   96 */     if (i1 <= 0) {
/*   97 */       return null;
/*      */     }
/*   99 */     int j1 = 0;
/*      */     Iterator iterator;
/*  101 */     label183: for (; j1 < 5; 
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  106 */         iterator.hasNext())
/*      */     {
/*  102 */       j1++;
/*  103 */       int k1 = random.nextInt(i1);
/*  104 */       iterator = worldgenvillagepieces_worldgenvillagestartpiece.e.iterator();
/*      */       
/*  106 */       continue;
/*  107 */       WorldGenVillagePieceWeight worldgenvillagepieces_worldgenvillagepieceweight = (WorldGenVillagePieceWeight)iterator.next();
/*      */       
/*  109 */       k1 -= worldgenvillagepieces_worldgenvillagepieceweight.b;
/*  110 */       if (k1 < 0) {
/*  111 */         if ((!worldgenvillagepieces_worldgenvillagepieceweight.a(l)) || ((worldgenvillagepieces_worldgenvillagepieceweight == worldgenvillagepieces_worldgenvillagestartpiece.d) && (worldgenvillagepieces_worldgenvillagestartpiece.e.size() > 1))) {
/*      */           break label183;
/*      */         }
/*      */         
/*  115 */         WorldGenVillagePiece worldgenvillagepieces_worldgenvillagepiece = a(worldgenvillagepieces_worldgenvillagestartpiece, worldgenvillagepieces_worldgenvillagepieceweight, list, random, i, j, k, enumdirection, l);
/*      */         
/*  117 */         if (worldgenvillagepieces_worldgenvillagepiece != null) {
/*  118 */           worldgenvillagepieces_worldgenvillagepieceweight.c += 1;
/*  119 */           worldgenvillagepieces_worldgenvillagestartpiece.d = worldgenvillagepieces_worldgenvillagepieceweight;
/*  120 */           if (!worldgenvillagepieces_worldgenvillagepieceweight.a()) {
/*  121 */             worldgenvillagepieces_worldgenvillagestartpiece.e.remove(worldgenvillagepieces_worldgenvillagepieceweight);
/*      */           }
/*      */           
/*  124 */           return worldgenvillagepieces_worldgenvillagepiece;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  130 */     StructureBoundingBox structureboundingbox = WorldGenVillageLight.a(worldgenvillagepieces_worldgenvillagestartpiece, list, random, i, j, k, enumdirection);
/*      */     
/*  132 */     if (structureboundingbox != null) {
/*  133 */       return new WorldGenVillageLight(worldgenvillagepieces_worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection);
/*      */     }
/*  135 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   private static StructurePiece d(WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l)
/*      */   {
/*  141 */     if (l > 50)
/*  142 */       return null;
/*  143 */     if ((Math.abs(i - worldgenvillagepieces_worldgenvillagestartpiece.c().a) <= 112) && (Math.abs(k - worldgenvillagepieces_worldgenvillagestartpiece.c().c) <= 112)) {
/*  144 */       WorldGenVillagePiece worldgenvillagepieces_worldgenvillagepiece = c(worldgenvillagepieces_worldgenvillagestartpiece, list, random, i, j, k, enumdirection, l + 1);
/*      */       
/*  146 */       if (worldgenvillagepieces_worldgenvillagepiece != null) {
/*  147 */         int i1 = (worldgenvillagepieces_worldgenvillagepiece.l.a + worldgenvillagepieces_worldgenvillagepiece.l.d) / 2;
/*  148 */         int j1 = (worldgenvillagepieces_worldgenvillagepiece.l.c + worldgenvillagepieces_worldgenvillagepiece.l.f) / 2;
/*  149 */         int k1 = worldgenvillagepieces_worldgenvillagepiece.l.d - worldgenvillagepieces_worldgenvillagepiece.l.a;
/*  150 */         int l1 = worldgenvillagepieces_worldgenvillagepiece.l.f - worldgenvillagepieces_worldgenvillagepiece.l.c;
/*  151 */         int i2 = k1 > l1 ? k1 : l1;
/*      */         
/*  153 */         if (worldgenvillagepieces_worldgenvillagestartpiece.e().a(i1, j1, i2 / 2 + 4, WorldGenVillage.d)) {
/*  154 */           list.add(worldgenvillagepieces_worldgenvillagepiece);
/*  155 */           worldgenvillagepieces_worldgenvillagestartpiece.f.add(worldgenvillagepieces_worldgenvillagepiece);
/*  156 */           return worldgenvillagepieces_worldgenvillagepiece;
/*      */         }
/*      */       }
/*      */       
/*  160 */       return null;
/*      */     }
/*  162 */     return null;
/*      */   }
/*      */   
/*      */   private static StructurePiece e(WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l)
/*      */   {
/*  167 */     if (l > 3 + worldgenvillagepieces_worldgenvillagestartpiece.c)
/*  168 */       return null;
/*  169 */     if ((Math.abs(i - worldgenvillagepieces_worldgenvillagestartpiece.c().a) <= 112) && (Math.abs(k - worldgenvillagepieces_worldgenvillagestartpiece.c().c) <= 112)) {
/*  170 */       StructureBoundingBox structureboundingbox = WorldGenVillageRoad.a(worldgenvillagepieces_worldgenvillagestartpiece, list, random, i, j, k, enumdirection);
/*      */       
/*  172 */       if ((structureboundingbox != null) && (structureboundingbox.b > 10)) {
/*  173 */         WorldGenVillageRoad worldgenvillagepieces_worldgenvillageroad = new WorldGenVillageRoad(worldgenvillagepieces_worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection);
/*  174 */         int i1 = (worldgenvillagepieces_worldgenvillageroad.l.a + worldgenvillagepieces_worldgenvillageroad.l.d) / 2;
/*  175 */         int j1 = (worldgenvillagepieces_worldgenvillageroad.l.c + worldgenvillagepieces_worldgenvillageroad.l.f) / 2;
/*  176 */         int k1 = worldgenvillagepieces_worldgenvillageroad.l.d - worldgenvillagepieces_worldgenvillageroad.l.a;
/*  177 */         int l1 = worldgenvillagepieces_worldgenvillageroad.l.f - worldgenvillagepieces_worldgenvillageroad.l.c;
/*  178 */         int i2 = k1 > l1 ? k1 : l1;
/*      */         
/*  180 */         if (worldgenvillagepieces_worldgenvillagestartpiece.e().a(i1, j1, i2 / 2 + 4, WorldGenVillage.d)) {
/*  181 */           list.add(worldgenvillagepieces_worldgenvillageroad);
/*  182 */           worldgenvillagepieces_worldgenvillagestartpiece.g.add(worldgenvillagepieces_worldgenvillageroad);
/*  183 */           return worldgenvillagepieces_worldgenvillageroad;
/*      */         }
/*      */       }
/*      */       
/*  187 */       return null;
/*      */     }
/*  189 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   static class SyntheticClass_1
/*      */   {
/*  195 */     static final int[] a = new int[EnumDirection.values().length];
/*      */     
/*      */     static {
/*      */       try {
/*  199 */         a[EnumDirection.NORTH.ordinal()] = 1;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*      */       
/*      */       try
/*      */       {
/*  205 */         a[EnumDirection.SOUTH.ordinal()] = 2;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*      */       
/*      */       try
/*      */       {
/*  211 */         a[EnumDirection.WEST.ordinal()] = 3;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*      */       
/*      */       try
/*      */       {
/*  217 */         a[EnumDirection.EAST.ordinal()] = 4;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageLight
/*      */     extends WorldGenVillagePieces.WorldGenVillagePiece
/*      */   {
/*      */     public WorldGenVillageLight() {}
/*      */     
/*      */     public WorldGenVillageLight(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection)
/*      */     {
/*  230 */       super(i);
/*  231 */       this.m = enumdirection;
/*  232 */       this.l = structureboundingbox;
/*      */     }
/*      */     
/*      */     public static StructureBoundingBox a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection) {
/*  236 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 3, 4, 2, enumdirection);
/*      */       
/*  238 */       return StructurePiece.a(list, structureboundingbox) != null ? null : structureboundingbox;
/*      */     }
/*      */     
/*      */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/*  242 */       if (this.h < 0) {
/*  243 */         this.h = b(world, structureboundingbox);
/*  244 */         if (this.h < 0) {
/*  245 */           return true;
/*      */         }
/*      */         
/*  248 */         this.l.a(0, this.h - this.l.e + 4 - 1, 0);
/*      */       }
/*      */       
/*  251 */       a(world, structureboundingbox, 0, 0, 0, 2, 3, 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  252 */       a(world, Blocks.FENCE.getBlockData(), 1, 0, 0, structureboundingbox);
/*  253 */       a(world, Blocks.FENCE.getBlockData(), 1, 1, 0, structureboundingbox);
/*  254 */       a(world, Blocks.FENCE.getBlockData(), 1, 2, 0, structureboundingbox);
/*  255 */       a(world, Blocks.WOOL.fromLegacyData(EnumColor.WHITE.getInvColorIndex()), 1, 3, 0, structureboundingbox);
/*  256 */       boolean flag = (this.m == EnumDirection.EAST) || (this.m == EnumDirection.NORTH);
/*      */       
/*  258 */       a(world, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.e()), flag ? 2 : 0, 3, 0, structureboundingbox);
/*  259 */       a(world, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m), 1, 3, 1, structureboundingbox);
/*  260 */       a(world, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.f()), flag ? 0 : 2, 3, 0, structureboundingbox);
/*  261 */       a(world, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.opposite()), 1, 3, -1, structureboundingbox);
/*  262 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageFarm2 extends WorldGenVillagePieces.WorldGenVillagePiece
/*      */   {
/*      */     private Block a;
/*      */     private Block b;
/*      */     private Block c;
/*      */     private Block d;
/*      */     
/*      */     public WorldGenVillageFarm2() {}
/*      */     
/*      */     public WorldGenVillageFarm2(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
/*  276 */       super(i);
/*  277 */       this.m = enumdirection;
/*  278 */       this.l = structureboundingbox;
/*  279 */       this.a = a(random);
/*  280 */       this.b = a(random);
/*  281 */       this.c = a(random);
/*  282 */       this.d = a(random);
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound nbttagcompound) {
/*  286 */       super.a(nbttagcompound);
/*  287 */       nbttagcompound.setInt("CA", Block.REGISTRY.b(this.a));
/*  288 */       nbttagcompound.setInt("CB", Block.REGISTRY.b(this.b));
/*  289 */       nbttagcompound.setInt("CC", Block.REGISTRY.b(this.c));
/*  290 */       nbttagcompound.setInt("CD", Block.REGISTRY.b(this.d));
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound nbttagcompound) {
/*  294 */       super.b(nbttagcompound);
/*  295 */       this.a = Block.getById(nbttagcompound.getInt("CA"));
/*  296 */       this.b = Block.getById(nbttagcompound.getInt("CB"));
/*  297 */       this.c = Block.getById(nbttagcompound.getInt("CC"));
/*  298 */       this.d = Block.getById(nbttagcompound.getInt("CD"));
/*      */     }
/*      */     
/*      */     private Block a(Random random) {
/*  302 */       switch (random.nextInt(5)) {
/*      */       case 0: 
/*  304 */         return Blocks.CARROTS;
/*      */       
/*      */       case 1: 
/*  307 */         return Blocks.POTATOES;
/*      */       }
/*      */       
/*  310 */       return Blocks.WHEAT;
/*      */     }
/*      */     
/*      */     public static WorldGenVillageFarm2 a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l)
/*      */     {
/*  315 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 13, 4, 9, enumdirection);
/*      */       
/*  317 */       return (a(structureboundingbox)) && (StructurePiece.a(list, structureboundingbox) == null) ? new WorldGenVillageFarm2(worldgenvillagepieces_worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
/*      */     }
/*      */     
/*      */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/*  321 */       if (this.h < 0) {
/*  322 */         this.h = b(world, structureboundingbox);
/*  323 */         if (this.h < 0) {
/*  324 */           return true;
/*      */         }
/*      */         
/*  327 */         this.l.a(0, this.h - this.l.e + 4 - 1, 0);
/*      */       }
/*      */       
/*  330 */       a(world, structureboundingbox, 0, 1, 0, 12, 4, 8, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  331 */       a(world, structureboundingbox, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
/*  332 */       a(world, structureboundingbox, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
/*  333 */       a(world, structureboundingbox, 7, 0, 1, 8, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
/*  334 */       a(world, structureboundingbox, 10, 0, 1, 11, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
/*  335 */       a(world, structureboundingbox, 0, 0, 0, 0, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  336 */       a(world, structureboundingbox, 6, 0, 0, 6, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  337 */       a(world, structureboundingbox, 12, 0, 0, 12, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  338 */       a(world, structureboundingbox, 1, 0, 0, 11, 0, 0, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  339 */       a(world, structureboundingbox, 1, 0, 8, 11, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  340 */       a(world, structureboundingbox, 3, 0, 1, 3, 0, 7, Blocks.WATER.getBlockData(), Blocks.WATER.getBlockData(), false);
/*  341 */       a(world, structureboundingbox, 9, 0, 1, 9, 0, 7, Blocks.WATER.getBlockData(), Blocks.WATER.getBlockData(), false);
/*      */       
/*      */ 
/*      */ 
/*  345 */       for (int i = 1; i <= 7; i++) {
/*  346 */         a(world, this.a.fromLegacyData(MathHelper.nextInt(random, 2, 7)), 1, 1, i, structureboundingbox);
/*  347 */         a(world, this.a.fromLegacyData(MathHelper.nextInt(random, 2, 7)), 2, 1, i, structureboundingbox);
/*  348 */         a(world, this.b.fromLegacyData(MathHelper.nextInt(random, 2, 7)), 4, 1, i, structureboundingbox);
/*  349 */         a(world, this.b.fromLegacyData(MathHelper.nextInt(random, 2, 7)), 5, 1, i, structureboundingbox);
/*  350 */         a(world, this.c.fromLegacyData(MathHelper.nextInt(random, 2, 7)), 7, 1, i, structureboundingbox);
/*  351 */         a(world, this.c.fromLegacyData(MathHelper.nextInt(random, 2, 7)), 8, 1, i, structureboundingbox);
/*  352 */         a(world, this.d.fromLegacyData(MathHelper.nextInt(random, 2, 7)), 10, 1, i, structureboundingbox);
/*  353 */         a(world, this.d.fromLegacyData(MathHelper.nextInt(random, 2, 7)), 11, 1, i, structureboundingbox);
/*      */       }
/*      */       
/*  356 */       for (i = 0; i < 9; i++) {
/*  357 */         for (int j = 0; j < 13; j++) {
/*  358 */           b(world, j, 4, i, structureboundingbox);
/*  359 */           b(world, Blocks.DIRT.getBlockData(), j, -1, i, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/*  363 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageFarm extends WorldGenVillagePieces.WorldGenVillagePiece
/*      */   {
/*      */     private Block a;
/*      */     private Block b;
/*      */     
/*      */     public WorldGenVillageFarm() {}
/*      */     
/*      */     public WorldGenVillageFarm(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
/*  375 */       super(i);
/*  376 */       this.m = enumdirection;
/*  377 */       this.l = structureboundingbox;
/*  378 */       this.a = a(random);
/*  379 */       this.b = a(random);
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound nbttagcompound) {
/*  383 */       super.a(nbttagcompound);
/*  384 */       nbttagcompound.setInt("CA", Block.REGISTRY.b(this.a));
/*  385 */       nbttagcompound.setInt("CB", Block.REGISTRY.b(this.b));
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound nbttagcompound) {
/*  389 */       super.b(nbttagcompound);
/*  390 */       this.a = Block.getById(nbttagcompound.getInt("CA"));
/*  391 */       this.b = Block.getById(nbttagcompound.getInt("CB"));
/*      */     }
/*      */     
/*      */     private Block a(Random random) {
/*  395 */       switch (random.nextInt(5)) {
/*      */       case 0: 
/*  397 */         return Blocks.CARROTS;
/*      */       
/*      */       case 1: 
/*  400 */         return Blocks.POTATOES;
/*      */       }
/*      */       
/*  403 */       return Blocks.WHEAT;
/*      */     }
/*      */     
/*      */     public static WorldGenVillageFarm a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l)
/*      */     {
/*  408 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 7, 4, 9, enumdirection);
/*      */       
/*  410 */       return (a(structureboundingbox)) && (StructurePiece.a(list, structureboundingbox) == null) ? new WorldGenVillageFarm(worldgenvillagepieces_worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
/*      */     }
/*      */     
/*      */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/*  414 */       if (this.h < 0) {
/*  415 */         this.h = b(world, structureboundingbox);
/*  416 */         if (this.h < 0) {
/*  417 */           return true;
/*      */         }
/*      */         
/*  420 */         this.l.a(0, this.h - this.l.e + 4 - 1, 0);
/*      */       }
/*      */       
/*  423 */       a(world, structureboundingbox, 0, 1, 0, 6, 4, 8, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  424 */       a(world, structureboundingbox, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
/*  425 */       a(world, structureboundingbox, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getBlockData(), Blocks.FARMLAND.getBlockData(), false);
/*  426 */       a(world, structureboundingbox, 0, 0, 0, 0, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  427 */       a(world, structureboundingbox, 6, 0, 0, 6, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  428 */       a(world, structureboundingbox, 1, 0, 0, 5, 0, 0, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  429 */       a(world, structureboundingbox, 1, 0, 8, 5, 0, 8, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  430 */       a(world, structureboundingbox, 3, 0, 1, 3, 0, 7, Blocks.WATER.getBlockData(), Blocks.WATER.getBlockData(), false);
/*      */       
/*      */ 
/*      */ 
/*  434 */       for (int i = 1; i <= 7; i++) {
/*  435 */         a(world, this.a.fromLegacyData(MathHelper.nextInt(random, 2, 7)), 1, 1, i, structureboundingbox);
/*  436 */         a(world, this.a.fromLegacyData(MathHelper.nextInt(random, 2, 7)), 2, 1, i, structureboundingbox);
/*  437 */         a(world, this.b.fromLegacyData(MathHelper.nextInt(random, 2, 7)), 4, 1, i, structureboundingbox);
/*  438 */         a(world, this.b.fromLegacyData(MathHelper.nextInt(random, 2, 7)), 5, 1, i, structureboundingbox);
/*      */       }
/*      */       
/*  441 */       for (i = 0; i < 9; i++) {
/*  442 */         for (int j = 0; j < 7; j++) {
/*  443 */           b(world, j, 4, i, structureboundingbox);
/*  444 */           b(world, Blocks.DIRT.getBlockData(), j, -1, i, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/*  448 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageBlacksmith extends WorldGenVillagePieces.WorldGenVillagePiece
/*      */   {
/*  454 */     private static final List<StructurePieceTreasure> a = com.google.common.collect.Lists.newArrayList(new StructurePieceTreasure[] { new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 5), new StructurePieceTreasure(Items.BREAD, 0, 1, 3, 15), new StructurePieceTreasure(Items.APPLE, 0, 1, 3, 15), new StructurePieceTreasure(Items.IRON_PICKAXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_SWORD, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_CHESTPLATE, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_HELMET, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_LEGGINGS, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_BOOTS, 0, 1, 1, 5), new StructurePieceTreasure(Item.getItemOf(Blocks.OBSIDIAN), 0, 3, 7, 5), new StructurePieceTreasure(Item.getItemOf(Blocks.SAPLING), 0, 3, 7, 5), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 3), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 1) });
/*      */     private boolean b;
/*      */     
/*      */     public WorldGenVillageBlacksmith() {}
/*      */     
/*      */     public WorldGenVillageBlacksmith(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
/*  460 */       super(i);
/*  461 */       this.m = enumdirection;
/*  462 */       this.l = structureboundingbox;
/*      */     }
/*      */     
/*      */     public static WorldGenVillageBlacksmith a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
/*  466 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 10, 6, 7, enumdirection);
/*      */       
/*  468 */       return (a(structureboundingbox)) && (StructurePiece.a(list, structureboundingbox) == null) ? new WorldGenVillageBlacksmith(worldgenvillagepieces_worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound nbttagcompound) {
/*  472 */       super.a(nbttagcompound);
/*  473 */       nbttagcompound.setBoolean("Chest", this.b);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound nbttagcompound) {
/*  477 */       super.b(nbttagcompound);
/*  478 */       this.b = nbttagcompound.getBoolean("Chest");
/*      */     }
/*      */     
/*      */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/*  482 */       if (this.h < 0) {
/*  483 */         this.h = b(world, structureboundingbox);
/*  484 */         if (this.h < 0) {
/*  485 */           return true;
/*      */         }
/*      */         
/*  488 */         this.l.a(0, this.h - this.l.e + 6 - 1, 0);
/*      */       }
/*      */       
/*  491 */       a(world, structureboundingbox, 0, 1, 0, 9, 4, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  492 */       a(world, structureboundingbox, 0, 0, 0, 9, 0, 6, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  493 */       a(world, structureboundingbox, 0, 4, 0, 9, 4, 6, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  494 */       a(world, structureboundingbox, 0, 5, 0, 9, 5, 6, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
/*  495 */       a(world, structureboundingbox, 1, 5, 1, 8, 5, 5, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  496 */       a(world, structureboundingbox, 1, 1, 0, 2, 3, 0, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  497 */       a(world, structureboundingbox, 0, 1, 0, 0, 4, 0, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  498 */       a(world, structureboundingbox, 3, 1, 0, 3, 4, 0, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  499 */       a(world, structureboundingbox, 0, 1, 6, 0, 4, 6, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  500 */       a(world, Blocks.PLANKS.getBlockData(), 3, 3, 1, structureboundingbox);
/*  501 */       a(world, structureboundingbox, 3, 1, 2, 3, 3, 2, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  502 */       a(world, structureboundingbox, 4, 1, 3, 5, 3, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  503 */       a(world, structureboundingbox, 0, 1, 1, 0, 3, 5, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  504 */       a(world, structureboundingbox, 1, 1, 6, 5, 3, 6, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  505 */       a(world, structureboundingbox, 5, 1, 0, 5, 3, 0, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
/*  506 */       a(world, structureboundingbox, 9, 1, 0, 9, 3, 0, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
/*  507 */       a(world, structureboundingbox, 6, 1, 4, 9, 4, 6, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  508 */       a(world, Blocks.FLOWING_LAVA.getBlockData(), 7, 1, 5, structureboundingbox);
/*  509 */       a(world, Blocks.FLOWING_LAVA.getBlockData(), 8, 1, 5, structureboundingbox);
/*  510 */       a(world, Blocks.IRON_BARS.getBlockData(), 9, 2, 5, structureboundingbox);
/*  511 */       a(world, Blocks.IRON_BARS.getBlockData(), 9, 2, 4, structureboundingbox);
/*  512 */       a(world, structureboundingbox, 7, 2, 4, 8, 2, 5, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  513 */       a(world, Blocks.COBBLESTONE.getBlockData(), 6, 1, 3, structureboundingbox);
/*  514 */       a(world, Blocks.FURNACE.getBlockData(), 6, 2, 3, structureboundingbox);
/*  515 */       a(world, Blocks.FURNACE.getBlockData(), 6, 3, 3, structureboundingbox);
/*  516 */       a(world, Blocks.DOUBLE_STONE_SLAB.getBlockData(), 8, 1, 1, structureboundingbox);
/*  517 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, structureboundingbox);
/*  518 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 2, 4, structureboundingbox);
/*  519 */       a(world, Blocks.GLASS_PANE.getBlockData(), 2, 2, 6, structureboundingbox);
/*  520 */       a(world, Blocks.GLASS_PANE.getBlockData(), 4, 2, 6, structureboundingbox);
/*  521 */       a(world, Blocks.FENCE.getBlockData(), 2, 1, 4, structureboundingbox);
/*  522 */       a(world, Blocks.WOODEN_PRESSURE_PLATE.getBlockData(), 2, 2, 4, structureboundingbox);
/*  523 */       a(world, Blocks.PLANKS.getBlockData(), 1, 1, 5, structureboundingbox);
/*  524 */       a(world, Blocks.OAK_STAIRS.fromLegacyData(a(Blocks.OAK_STAIRS, 3)), 2, 1, 5, structureboundingbox);
/*  525 */       a(world, Blocks.OAK_STAIRS.fromLegacyData(a(Blocks.OAK_STAIRS, 1)), 1, 1, 4, structureboundingbox);
/*  526 */       if ((!this.b) && (structureboundingbox.b(new BlockPosition(a(5, 5), d(1), b(5, 5))))) {
/*  527 */         this.b = true;
/*  528 */         a(world, structureboundingbox, random, 5, 1, 5, a, 3 + random.nextInt(6));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  533 */       for (int i = 6; i <= 8; i++) {
/*  534 */         if ((a(world, i, 0, -1, structureboundingbox).getBlock().getMaterial() == Material.AIR) && (a(world, i, -1, -1, structureboundingbox).getBlock().getMaterial() != Material.AIR)) {
/*  535 */           a(world, Blocks.STONE_STAIRS.fromLegacyData(a(Blocks.STONE_STAIRS, 3)), i, 0, -1, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/*  539 */       for (i = 0; i < 7; i++) {
/*  540 */         for (int j = 0; j < 10; j++) {
/*  541 */           b(world, j, 6, i, structureboundingbox);
/*  542 */           b(world, Blocks.COBBLESTONE.getBlockData(), j, -1, i, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/*  546 */       a(world, structureboundingbox, 7, 1, 1, 1);
/*  547 */       return true;
/*      */     }
/*      */     
/*      */     protected int c(int i, int j) {
/*  551 */       return 3;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageHouse2 extends WorldGenVillagePieces.WorldGenVillagePiece
/*      */   {
/*      */     public WorldGenVillageHouse2() {}
/*      */     
/*      */     public WorldGenVillageHouse2(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
/*  560 */       super(i);
/*  561 */       this.m = enumdirection;
/*  562 */       this.l = structureboundingbox;
/*      */     }
/*      */     
/*      */     public static WorldGenVillageHouse2 a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
/*  566 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 9, 7, 12, enumdirection);
/*      */       
/*  568 */       return (a(structureboundingbox)) && (StructurePiece.a(list, structureboundingbox) == null) ? new WorldGenVillageHouse2(worldgenvillagepieces_worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
/*      */     }
/*      */     
/*      */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/*  572 */       if (this.h < 0) {
/*  573 */         this.h = b(world, structureboundingbox);
/*  574 */         if (this.h < 0) {
/*  575 */           return true;
/*      */         }
/*      */         
/*  578 */         this.l.a(0, this.h - this.l.e + 7 - 1, 0);
/*      */       }
/*      */       
/*  581 */       a(world, structureboundingbox, 1, 1, 1, 7, 4, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  582 */       a(world, structureboundingbox, 2, 1, 6, 8, 4, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  583 */       a(world, structureboundingbox, 2, 0, 5, 8, 0, 10, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  584 */       a(world, structureboundingbox, 1, 0, 1, 7, 0, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  585 */       a(world, structureboundingbox, 0, 0, 0, 0, 3, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  586 */       a(world, structureboundingbox, 8, 0, 0, 8, 3, 10, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  587 */       a(world, structureboundingbox, 1, 0, 0, 7, 2, 0, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  588 */       a(world, structureboundingbox, 1, 0, 5, 2, 1, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  589 */       a(world, structureboundingbox, 2, 0, 6, 2, 3, 10, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  590 */       a(world, structureboundingbox, 3, 0, 10, 7, 3, 10, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  591 */       a(world, structureboundingbox, 1, 2, 0, 7, 3, 0, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  592 */       a(world, structureboundingbox, 1, 2, 5, 2, 3, 5, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  593 */       a(world, structureboundingbox, 0, 4, 1, 8, 4, 1, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  594 */       a(world, structureboundingbox, 0, 4, 4, 3, 4, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  595 */       a(world, structureboundingbox, 0, 5, 2, 8, 5, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  596 */       a(world, Blocks.PLANKS.getBlockData(), 0, 4, 2, structureboundingbox);
/*  597 */       a(world, Blocks.PLANKS.getBlockData(), 0, 4, 3, structureboundingbox);
/*  598 */       a(world, Blocks.PLANKS.getBlockData(), 8, 4, 2, structureboundingbox);
/*  599 */       a(world, Blocks.PLANKS.getBlockData(), 8, 4, 3, structureboundingbox);
/*  600 */       a(world, Blocks.PLANKS.getBlockData(), 8, 4, 4, structureboundingbox);
/*  601 */       int i = a(Blocks.OAK_STAIRS, 3);
/*  602 */       int j = a(Blocks.OAK_STAIRS, 2);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  607 */       for (int k = -1; k <= 2; k++) {
/*  608 */         for (int l = 0; l <= 8; l++) {
/*  609 */           a(world, Blocks.OAK_STAIRS.fromLegacyData(i), l, 4 + k, k, structureboundingbox);
/*  610 */           if (((k > -1) || (l <= 1)) && ((k > 0) || (l <= 3)) && ((k > 1) || (l <= 4) || (l >= 6))) {
/*  611 */             a(world, Blocks.OAK_STAIRS.fromLegacyData(j), l, 4 + k, 5 - k, structureboundingbox);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  616 */       a(world, structureboundingbox, 3, 4, 5, 3, 4, 10, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  617 */       a(world, structureboundingbox, 7, 4, 2, 7, 4, 10, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  618 */       a(world, structureboundingbox, 4, 5, 4, 4, 5, 10, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  619 */       a(world, structureboundingbox, 6, 5, 4, 6, 5, 10, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  620 */       a(world, structureboundingbox, 5, 6, 3, 5, 6, 10, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  621 */       k = a(Blocks.OAK_STAIRS, 0);
/*      */       
/*      */ 
/*      */ 
/*  625 */       for (int l = 4; l >= 1; l--) {
/*  626 */         a(world, Blocks.PLANKS.getBlockData(), l, 2 + l, 7 - l, structureboundingbox);
/*      */         
/*  628 */         for (int i1 = 8 - l; i1 <= 10; i1++) {
/*  629 */           a(world, Blocks.OAK_STAIRS.fromLegacyData(k), l, 2 + l, i1, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/*  633 */       l = a(Blocks.OAK_STAIRS, 1);
/*  634 */       a(world, Blocks.PLANKS.getBlockData(), 6, 6, 3, structureboundingbox);
/*  635 */       a(world, Blocks.PLANKS.getBlockData(), 7, 5, 4, structureboundingbox);
/*  636 */       a(world, Blocks.OAK_STAIRS.fromLegacyData(l), 6, 6, 4, structureboundingbox);
/*      */       
/*      */ 
/*      */ 
/*  640 */       for (int i1 = 6; i1 <= 8; i1++) {
/*  641 */         for (int j1 = 5; j1 <= 10; j1++) {
/*  642 */           a(world, Blocks.OAK_STAIRS.fromLegacyData(l), i1, 12 - i1, j1, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/*  646 */       a(world, Blocks.LOG.getBlockData(), 0, 2, 1, structureboundingbox);
/*  647 */       a(world, Blocks.LOG.getBlockData(), 0, 2, 4, structureboundingbox);
/*  648 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, structureboundingbox);
/*  649 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 2, 3, structureboundingbox);
/*  650 */       a(world, Blocks.LOG.getBlockData(), 4, 2, 0, structureboundingbox);
/*  651 */       a(world, Blocks.GLASS_PANE.getBlockData(), 5, 2, 0, structureboundingbox);
/*  652 */       a(world, Blocks.LOG.getBlockData(), 6, 2, 0, structureboundingbox);
/*  653 */       a(world, Blocks.LOG.getBlockData(), 8, 2, 1, structureboundingbox);
/*  654 */       a(world, Blocks.GLASS_PANE.getBlockData(), 8, 2, 2, structureboundingbox);
/*  655 */       a(world, Blocks.GLASS_PANE.getBlockData(), 8, 2, 3, structureboundingbox);
/*  656 */       a(world, Blocks.LOG.getBlockData(), 8, 2, 4, structureboundingbox);
/*  657 */       a(world, Blocks.PLANKS.getBlockData(), 8, 2, 5, structureboundingbox);
/*  658 */       a(world, Blocks.LOG.getBlockData(), 8, 2, 6, structureboundingbox);
/*  659 */       a(world, Blocks.GLASS_PANE.getBlockData(), 8, 2, 7, structureboundingbox);
/*  660 */       a(world, Blocks.GLASS_PANE.getBlockData(), 8, 2, 8, structureboundingbox);
/*  661 */       a(world, Blocks.LOG.getBlockData(), 8, 2, 9, structureboundingbox);
/*  662 */       a(world, Blocks.LOG.getBlockData(), 2, 2, 6, structureboundingbox);
/*  663 */       a(world, Blocks.GLASS_PANE.getBlockData(), 2, 2, 7, structureboundingbox);
/*  664 */       a(world, Blocks.GLASS_PANE.getBlockData(), 2, 2, 8, structureboundingbox);
/*  665 */       a(world, Blocks.LOG.getBlockData(), 2, 2, 9, structureboundingbox);
/*  666 */       a(world, Blocks.LOG.getBlockData(), 4, 4, 10, structureboundingbox);
/*  667 */       a(world, Blocks.GLASS_PANE.getBlockData(), 5, 4, 10, structureboundingbox);
/*  668 */       a(world, Blocks.LOG.getBlockData(), 6, 4, 10, structureboundingbox);
/*  669 */       a(world, Blocks.PLANKS.getBlockData(), 5, 5, 10, structureboundingbox);
/*  670 */       a(world, Blocks.AIR.getBlockData(), 2, 1, 0, structureboundingbox);
/*  671 */       a(world, Blocks.AIR.getBlockData(), 2, 2, 0, structureboundingbox);
/*  672 */       a(world, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m), 2, 3, 1, structureboundingbox);
/*  673 */       a(world, structureboundingbox, random, 2, 1, 0, EnumDirection.fromType2(a(Blocks.WOODEN_DOOR, 1)));
/*  674 */       a(world, structureboundingbox, 1, 0, -1, 3, 2, -1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  675 */       if ((a(world, 2, 0, -1, structureboundingbox).getBlock().getMaterial() == Material.AIR) && (a(world, 2, -1, -1, structureboundingbox).getBlock().getMaterial() != Material.AIR)) {
/*  676 */         a(world, Blocks.STONE_STAIRS.fromLegacyData(a(Blocks.STONE_STAIRS, 3)), 2, 0, -1, structureboundingbox);
/*      */       }
/*      */       
/*  679 */       for (i1 = 0; i1 < 5; i1++) {
/*  680 */         for (int j1 = 0; j1 < 9; j1++) {
/*  681 */           b(world, j1, 7, i1, structureboundingbox);
/*  682 */           b(world, Blocks.COBBLESTONE.getBlockData(), j1, -1, i1, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/*  686 */       for (i1 = 5; i1 < 11; i1++) {
/*  687 */         for (int j1 = 2; j1 < 9; j1++) {
/*  688 */           b(world, j1, 7, i1, structureboundingbox);
/*  689 */           b(world, Blocks.COBBLESTONE.getBlockData(), j1, -1, i1, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/*  693 */       a(world, structureboundingbox, 4, 1, 2, 2);
/*  694 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageButcher extends WorldGenVillagePieces.WorldGenVillagePiece
/*      */   {
/*      */     public WorldGenVillageButcher() {}
/*      */     
/*      */     public WorldGenVillageButcher(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
/*  703 */       super(i);
/*  704 */       this.m = enumdirection;
/*  705 */       this.l = structureboundingbox;
/*      */     }
/*      */     
/*      */     public static WorldGenVillageButcher a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
/*  709 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 9, 7, 11, enumdirection);
/*      */       
/*  711 */       return (a(structureboundingbox)) && (StructurePiece.a(list, structureboundingbox) == null) ? new WorldGenVillageButcher(worldgenvillagepieces_worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
/*      */     }
/*      */     
/*      */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/*  715 */       if (this.h < 0) {
/*  716 */         this.h = b(world, structureboundingbox);
/*  717 */         if (this.h < 0) {
/*  718 */           return true;
/*      */         }
/*      */         
/*  721 */         this.l.a(0, this.h - this.l.e + 7 - 1, 0);
/*      */       }
/*      */       
/*  724 */       a(world, structureboundingbox, 1, 1, 1, 7, 4, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  725 */       a(world, structureboundingbox, 2, 1, 6, 8, 4, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  726 */       a(world, structureboundingbox, 2, 0, 6, 8, 0, 10, Blocks.DIRT.getBlockData(), Blocks.DIRT.getBlockData(), false);
/*  727 */       a(world, Blocks.COBBLESTONE.getBlockData(), 6, 0, 6, structureboundingbox);
/*  728 */       a(world, structureboundingbox, 2, 1, 6, 2, 1, 10, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
/*  729 */       a(world, structureboundingbox, 8, 1, 6, 8, 1, 10, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
/*  730 */       a(world, structureboundingbox, 3, 1, 10, 7, 1, 10, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
/*  731 */       a(world, structureboundingbox, 1, 0, 1, 7, 0, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  732 */       a(world, structureboundingbox, 0, 0, 0, 0, 3, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  733 */       a(world, structureboundingbox, 8, 0, 0, 8, 3, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  734 */       a(world, structureboundingbox, 1, 0, 0, 7, 1, 0, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  735 */       a(world, structureboundingbox, 1, 0, 5, 7, 1, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  736 */       a(world, structureboundingbox, 1, 2, 0, 7, 3, 0, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  737 */       a(world, structureboundingbox, 1, 2, 5, 7, 3, 5, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  738 */       a(world, structureboundingbox, 0, 4, 1, 8, 4, 1, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  739 */       a(world, structureboundingbox, 0, 4, 4, 8, 4, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  740 */       a(world, structureboundingbox, 0, 5, 2, 8, 5, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  741 */       a(world, Blocks.PLANKS.getBlockData(), 0, 4, 2, structureboundingbox);
/*  742 */       a(world, Blocks.PLANKS.getBlockData(), 0, 4, 3, structureboundingbox);
/*  743 */       a(world, Blocks.PLANKS.getBlockData(), 8, 4, 2, structureboundingbox);
/*  744 */       a(world, Blocks.PLANKS.getBlockData(), 8, 4, 3, structureboundingbox);
/*  745 */       int i = a(Blocks.OAK_STAIRS, 3);
/*  746 */       int j = a(Blocks.OAK_STAIRS, 2);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  751 */       for (int k = -1; k <= 2; k++) {
/*  752 */         for (int l = 0; l <= 8; l++) {
/*  753 */           a(world, Blocks.OAK_STAIRS.fromLegacyData(i), l, 4 + k, k, structureboundingbox);
/*  754 */           a(world, Blocks.OAK_STAIRS.fromLegacyData(j), l, 4 + k, 5 - k, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/*  758 */       a(world, Blocks.LOG.getBlockData(), 0, 2, 1, structureboundingbox);
/*  759 */       a(world, Blocks.LOG.getBlockData(), 0, 2, 4, structureboundingbox);
/*  760 */       a(world, Blocks.LOG.getBlockData(), 8, 2, 1, structureboundingbox);
/*  761 */       a(world, Blocks.LOG.getBlockData(), 8, 2, 4, structureboundingbox);
/*  762 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, structureboundingbox);
/*  763 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 2, 3, structureboundingbox);
/*  764 */       a(world, Blocks.GLASS_PANE.getBlockData(), 8, 2, 2, structureboundingbox);
/*  765 */       a(world, Blocks.GLASS_PANE.getBlockData(), 8, 2, 3, structureboundingbox);
/*  766 */       a(world, Blocks.GLASS_PANE.getBlockData(), 2, 2, 5, structureboundingbox);
/*  767 */       a(world, Blocks.GLASS_PANE.getBlockData(), 3, 2, 5, structureboundingbox);
/*  768 */       a(world, Blocks.GLASS_PANE.getBlockData(), 5, 2, 0, structureboundingbox);
/*  769 */       a(world, Blocks.GLASS_PANE.getBlockData(), 6, 2, 5, structureboundingbox);
/*  770 */       a(world, Blocks.FENCE.getBlockData(), 2, 1, 3, structureboundingbox);
/*  771 */       a(world, Blocks.WOODEN_PRESSURE_PLATE.getBlockData(), 2, 2, 3, structureboundingbox);
/*  772 */       a(world, Blocks.PLANKS.getBlockData(), 1, 1, 4, structureboundingbox);
/*  773 */       a(world, Blocks.OAK_STAIRS.fromLegacyData(a(Blocks.OAK_STAIRS, 3)), 2, 1, 4, structureboundingbox);
/*  774 */       a(world, Blocks.OAK_STAIRS.fromLegacyData(a(Blocks.OAK_STAIRS, 1)), 1, 1, 3, structureboundingbox);
/*  775 */       a(world, structureboundingbox, 5, 0, 1, 7, 0, 3, Blocks.DOUBLE_STONE_SLAB.getBlockData(), Blocks.DOUBLE_STONE_SLAB.getBlockData(), false);
/*  776 */       a(world, Blocks.DOUBLE_STONE_SLAB.getBlockData(), 6, 1, 1, structureboundingbox);
/*  777 */       a(world, Blocks.DOUBLE_STONE_SLAB.getBlockData(), 6, 1, 2, structureboundingbox);
/*  778 */       a(world, Blocks.AIR.getBlockData(), 2, 1, 0, structureboundingbox);
/*  779 */       a(world, Blocks.AIR.getBlockData(), 2, 2, 0, structureboundingbox);
/*  780 */       a(world, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m), 2, 3, 1, structureboundingbox);
/*  781 */       a(world, structureboundingbox, random, 2, 1, 0, EnumDirection.fromType2(a(Blocks.WOODEN_DOOR, 1)));
/*  782 */       if ((a(world, 2, 0, -1, structureboundingbox).getBlock().getMaterial() == Material.AIR) && (a(world, 2, -1, -1, structureboundingbox).getBlock().getMaterial() != Material.AIR)) {
/*  783 */         a(world, Blocks.STONE_STAIRS.fromLegacyData(a(Blocks.STONE_STAIRS, 3)), 2, 0, -1, structureboundingbox);
/*      */       }
/*      */       
/*  786 */       a(world, Blocks.AIR.getBlockData(), 6, 1, 5, structureboundingbox);
/*  787 */       a(world, Blocks.AIR.getBlockData(), 6, 2, 5, structureboundingbox);
/*  788 */       a(world, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.opposite()), 6, 3, 4, structureboundingbox);
/*  789 */       a(world, structureboundingbox, random, 6, 1, 5, EnumDirection.fromType2(a(Blocks.WOODEN_DOOR, 1)));
/*      */       
/*  791 */       for (k = 0; k < 5; k++) {
/*  792 */         for (int l = 0; l < 9; l++) {
/*  793 */           b(world, l, 7, k, structureboundingbox);
/*  794 */           b(world, Blocks.COBBLESTONE.getBlockData(), l, -1, k, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/*  798 */       a(world, structureboundingbox, 4, 1, 2, 2);
/*  799 */       return true;
/*      */     }
/*      */     
/*      */     protected int c(int i, int j) {
/*  803 */       return i == 0 ? 4 : super.c(i, j);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageHut extends WorldGenVillagePieces.WorldGenVillagePiece
/*      */   {
/*      */     private boolean a;
/*      */     private int b;
/*      */     
/*      */     public WorldGenVillageHut() {}
/*      */     
/*      */     public WorldGenVillageHut(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
/*  815 */       super(i);
/*  816 */       this.m = enumdirection;
/*  817 */       this.l = structureboundingbox;
/*  818 */       this.a = random.nextBoolean();
/*  819 */       this.b = random.nextInt(3);
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound nbttagcompound) {
/*  823 */       super.a(nbttagcompound);
/*  824 */       nbttagcompound.setInt("T", this.b);
/*  825 */       nbttagcompound.setBoolean("C", this.a);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound nbttagcompound) {
/*  829 */       super.b(nbttagcompound);
/*  830 */       this.b = nbttagcompound.getInt("T");
/*  831 */       this.a = nbttagcompound.getBoolean("C");
/*      */     }
/*      */     
/*      */     public static WorldGenVillageHut a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
/*  835 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 4, 6, 5, enumdirection);
/*      */       
/*  837 */       return (a(structureboundingbox)) && (StructurePiece.a(list, structureboundingbox) == null) ? new WorldGenVillageHut(worldgenvillagepieces_worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
/*      */     }
/*      */     
/*      */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/*  841 */       if (this.h < 0) {
/*  842 */         this.h = b(world, structureboundingbox);
/*  843 */         if (this.h < 0) {
/*  844 */           return true;
/*      */         }
/*      */         
/*  847 */         this.l.a(0, this.h - this.l.e + 6 - 1, 0);
/*      */       }
/*      */       
/*  850 */       a(world, structureboundingbox, 1, 1, 1, 3, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  851 */       a(world, structureboundingbox, 0, 0, 0, 3, 0, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  852 */       a(world, structureboundingbox, 1, 0, 1, 2, 0, 3, Blocks.DIRT.getBlockData(), Blocks.DIRT.getBlockData(), false);
/*  853 */       if (this.a) {
/*  854 */         a(world, structureboundingbox, 1, 4, 1, 2, 4, 3, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*      */       } else {
/*  856 */         a(world, structureboundingbox, 1, 5, 1, 2, 5, 3, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*      */       }
/*      */       
/*  859 */       a(world, Blocks.LOG.getBlockData(), 1, 4, 0, structureboundingbox);
/*  860 */       a(world, Blocks.LOG.getBlockData(), 2, 4, 0, structureboundingbox);
/*  861 */       a(world, Blocks.LOG.getBlockData(), 1, 4, 4, structureboundingbox);
/*  862 */       a(world, Blocks.LOG.getBlockData(), 2, 4, 4, structureboundingbox);
/*  863 */       a(world, Blocks.LOG.getBlockData(), 0, 4, 1, structureboundingbox);
/*  864 */       a(world, Blocks.LOG.getBlockData(), 0, 4, 2, structureboundingbox);
/*  865 */       a(world, Blocks.LOG.getBlockData(), 0, 4, 3, structureboundingbox);
/*  866 */       a(world, Blocks.LOG.getBlockData(), 3, 4, 1, structureboundingbox);
/*  867 */       a(world, Blocks.LOG.getBlockData(), 3, 4, 2, structureboundingbox);
/*  868 */       a(world, Blocks.LOG.getBlockData(), 3, 4, 3, structureboundingbox);
/*  869 */       a(world, structureboundingbox, 0, 1, 0, 0, 3, 0, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  870 */       a(world, structureboundingbox, 3, 1, 0, 3, 3, 0, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  871 */       a(world, structureboundingbox, 0, 1, 4, 0, 3, 4, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  872 */       a(world, structureboundingbox, 3, 1, 4, 3, 3, 4, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  873 */       a(world, structureboundingbox, 0, 1, 1, 0, 3, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  874 */       a(world, structureboundingbox, 3, 1, 1, 3, 3, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  875 */       a(world, structureboundingbox, 1, 1, 0, 2, 3, 0, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  876 */       a(world, structureboundingbox, 1, 1, 4, 2, 3, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  877 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, structureboundingbox);
/*  878 */       a(world, Blocks.GLASS_PANE.getBlockData(), 3, 2, 2, structureboundingbox);
/*  879 */       if (this.b > 0) {
/*  880 */         a(world, Blocks.FENCE.getBlockData(), this.b, 1, 3, structureboundingbox);
/*  881 */         a(world, Blocks.WOODEN_PRESSURE_PLATE.getBlockData(), this.b, 2, 3, structureboundingbox);
/*      */       }
/*      */       
/*  884 */       a(world, Blocks.AIR.getBlockData(), 1, 1, 0, structureboundingbox);
/*  885 */       a(world, Blocks.AIR.getBlockData(), 1, 2, 0, structureboundingbox);
/*  886 */       a(world, structureboundingbox, random, 1, 1, 0, EnumDirection.fromType2(a(Blocks.WOODEN_DOOR, 1)));
/*  887 */       if ((a(world, 1, 0, -1, structureboundingbox).getBlock().getMaterial() == Material.AIR) && (a(world, 1, -1, -1, structureboundingbox).getBlock().getMaterial() != Material.AIR)) {
/*  888 */         a(world, Blocks.STONE_STAIRS.fromLegacyData(a(Blocks.STONE_STAIRS, 3)), 1, 0, -1, structureboundingbox);
/*      */       }
/*      */       
/*  891 */       for (int i = 0; i < 5; i++) {
/*  892 */         for (int j = 0; j < 4; j++) {
/*  893 */           b(world, j, 6, i, structureboundingbox);
/*  894 */           b(world, Blocks.COBBLESTONE.getBlockData(), j, -1, i, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/*  898 */       a(world, structureboundingbox, 1, 1, 2, 1);
/*  899 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageLibrary extends WorldGenVillagePieces.WorldGenVillagePiece
/*      */   {
/*      */     public WorldGenVillageLibrary() {}
/*      */     
/*      */     public WorldGenVillageLibrary(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
/*  908 */       super(i);
/*  909 */       this.m = enumdirection;
/*  910 */       this.l = structureboundingbox;
/*      */     }
/*      */     
/*      */     public static WorldGenVillageLibrary a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
/*  914 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 9, 9, 6, enumdirection);
/*      */       
/*  916 */       return (a(structureboundingbox)) && (StructurePiece.a(list, structureboundingbox) == null) ? new WorldGenVillageLibrary(worldgenvillagepieces_worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
/*      */     }
/*      */     
/*      */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/*  920 */       if (this.h < 0) {
/*  921 */         this.h = b(world, structureboundingbox);
/*  922 */         if (this.h < 0) {
/*  923 */           return true;
/*      */         }
/*      */         
/*  926 */         this.l.a(0, this.h - this.l.e + 9 - 1, 0);
/*      */       }
/*      */       
/*  929 */       a(world, structureboundingbox, 1, 1, 1, 7, 5, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  930 */       a(world, structureboundingbox, 0, 0, 0, 8, 0, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  931 */       a(world, structureboundingbox, 0, 5, 0, 8, 5, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  932 */       a(world, structureboundingbox, 0, 6, 1, 8, 6, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  933 */       a(world, structureboundingbox, 0, 7, 2, 8, 7, 3, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  934 */       int i = a(Blocks.OAK_STAIRS, 3);
/*  935 */       int j = a(Blocks.OAK_STAIRS, 2);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  940 */       for (int k = -1; k <= 2; k++) {
/*  941 */         for (int l = 0; l <= 8; l++) {
/*  942 */           a(world, Blocks.OAK_STAIRS.fromLegacyData(i), l, 6 + k, k, structureboundingbox);
/*  943 */           a(world, Blocks.OAK_STAIRS.fromLegacyData(j), l, 6 + k, 5 - k, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/*  947 */       a(world, structureboundingbox, 0, 1, 0, 0, 1, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  948 */       a(world, structureboundingbox, 1, 1, 5, 8, 1, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  949 */       a(world, structureboundingbox, 8, 1, 0, 8, 1, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  950 */       a(world, structureboundingbox, 2, 1, 0, 7, 1, 0, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  951 */       a(world, structureboundingbox, 0, 2, 0, 0, 4, 0, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  952 */       a(world, structureboundingbox, 0, 2, 5, 0, 4, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  953 */       a(world, structureboundingbox, 8, 2, 5, 8, 4, 5, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  954 */       a(world, structureboundingbox, 8, 2, 0, 8, 4, 0, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*  955 */       a(world, structureboundingbox, 0, 2, 1, 0, 4, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  956 */       a(world, structureboundingbox, 1, 2, 5, 7, 4, 5, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  957 */       a(world, structureboundingbox, 8, 2, 1, 8, 4, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  958 */       a(world, structureboundingbox, 1, 2, 0, 7, 4, 0, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  959 */       a(world, Blocks.GLASS_PANE.getBlockData(), 4, 2, 0, structureboundingbox);
/*  960 */       a(world, Blocks.GLASS_PANE.getBlockData(), 5, 2, 0, structureboundingbox);
/*  961 */       a(world, Blocks.GLASS_PANE.getBlockData(), 6, 2, 0, structureboundingbox);
/*  962 */       a(world, Blocks.GLASS_PANE.getBlockData(), 4, 3, 0, structureboundingbox);
/*  963 */       a(world, Blocks.GLASS_PANE.getBlockData(), 5, 3, 0, structureboundingbox);
/*  964 */       a(world, Blocks.GLASS_PANE.getBlockData(), 6, 3, 0, structureboundingbox);
/*  965 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, structureboundingbox);
/*  966 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 2, 3, structureboundingbox);
/*  967 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 3, 2, structureboundingbox);
/*  968 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 3, 3, structureboundingbox);
/*  969 */       a(world, Blocks.GLASS_PANE.getBlockData(), 8, 2, 2, structureboundingbox);
/*  970 */       a(world, Blocks.GLASS_PANE.getBlockData(), 8, 2, 3, structureboundingbox);
/*  971 */       a(world, Blocks.GLASS_PANE.getBlockData(), 8, 3, 2, structureboundingbox);
/*  972 */       a(world, Blocks.GLASS_PANE.getBlockData(), 8, 3, 3, structureboundingbox);
/*  973 */       a(world, Blocks.GLASS_PANE.getBlockData(), 2, 2, 5, structureboundingbox);
/*  974 */       a(world, Blocks.GLASS_PANE.getBlockData(), 3, 2, 5, structureboundingbox);
/*  975 */       a(world, Blocks.GLASS_PANE.getBlockData(), 5, 2, 5, structureboundingbox);
/*  976 */       a(world, Blocks.GLASS_PANE.getBlockData(), 6, 2, 5, structureboundingbox);
/*  977 */       a(world, structureboundingbox, 1, 4, 1, 7, 4, 1, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  978 */       a(world, structureboundingbox, 1, 4, 4, 7, 4, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*  979 */       a(world, structureboundingbox, 1, 3, 4, 7, 3, 4, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
/*  980 */       a(world, Blocks.PLANKS.getBlockData(), 7, 1, 4, structureboundingbox);
/*  981 */       a(world, Blocks.OAK_STAIRS.fromLegacyData(a(Blocks.OAK_STAIRS, 0)), 7, 1, 3, structureboundingbox);
/*  982 */       k = a(Blocks.OAK_STAIRS, 3);
/*  983 */       a(world, Blocks.OAK_STAIRS.fromLegacyData(k), 6, 1, 4, structureboundingbox);
/*  984 */       a(world, Blocks.OAK_STAIRS.fromLegacyData(k), 5, 1, 4, structureboundingbox);
/*  985 */       a(world, Blocks.OAK_STAIRS.fromLegacyData(k), 4, 1, 4, structureboundingbox);
/*  986 */       a(world, Blocks.OAK_STAIRS.fromLegacyData(k), 3, 1, 4, structureboundingbox);
/*  987 */       a(world, Blocks.FENCE.getBlockData(), 6, 1, 3, structureboundingbox);
/*  988 */       a(world, Blocks.WOODEN_PRESSURE_PLATE.getBlockData(), 6, 2, 3, structureboundingbox);
/*  989 */       a(world, Blocks.FENCE.getBlockData(), 4, 1, 3, structureboundingbox);
/*  990 */       a(world, Blocks.WOODEN_PRESSURE_PLATE.getBlockData(), 4, 2, 3, structureboundingbox);
/*  991 */       a(world, Blocks.CRAFTING_TABLE.getBlockData(), 7, 1, 1, structureboundingbox);
/*  992 */       a(world, Blocks.AIR.getBlockData(), 1, 1, 0, structureboundingbox);
/*  993 */       a(world, Blocks.AIR.getBlockData(), 1, 2, 0, structureboundingbox);
/*  994 */       a(world, structureboundingbox, random, 1, 1, 0, EnumDirection.fromType2(a(Blocks.WOODEN_DOOR, 1)));
/*  995 */       if ((a(world, 1, 0, -1, structureboundingbox).getBlock().getMaterial() == Material.AIR) && (a(world, 1, -1, -1, structureboundingbox).getBlock().getMaterial() != Material.AIR)) {
/*  996 */         a(world, Blocks.STONE_STAIRS.fromLegacyData(a(Blocks.STONE_STAIRS, 3)), 1, 0, -1, structureboundingbox);
/*      */       }
/*      */       
/*  999 */       for (int l = 0; l < 6; l++) {
/* 1000 */         for (int i1 = 0; i1 < 9; i1++) {
/* 1001 */           b(world, i1, 9, l, structureboundingbox);
/* 1002 */           b(world, Blocks.COBBLESTONE.getBlockData(), i1, -1, l, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/* 1006 */       a(world, structureboundingbox, 2, 1, 2, 1);
/* 1007 */       return true;
/*      */     }
/*      */     
/*      */     protected int c(int i, int j) {
/* 1011 */       return 1;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageTemple extends WorldGenVillagePieces.WorldGenVillagePiece
/*      */   {
/*      */     public WorldGenVillageTemple() {}
/*      */     
/*      */     public WorldGenVillageTemple(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
/* 1020 */       super(i);
/* 1021 */       this.m = enumdirection;
/* 1022 */       this.l = structureboundingbox;
/*      */     }
/*      */     
/*      */     public static WorldGenVillageTemple a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
/* 1026 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 5, 12, 9, enumdirection);
/*      */       
/* 1028 */       return (a(structureboundingbox)) && (StructurePiece.a(list, structureboundingbox) == null) ? new WorldGenVillageTemple(worldgenvillagepieces_worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection) : null;
/*      */     }
/*      */     
/*      */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/* 1032 */       if (this.h < 0) {
/* 1033 */         this.h = b(world, structureboundingbox);
/* 1034 */         if (this.h < 0) {
/* 1035 */           return true;
/*      */         }
/*      */         
/* 1038 */         this.l.a(0, this.h - this.l.e + 12 - 1, 0);
/*      */       }
/*      */       
/* 1041 */       a(world, structureboundingbox, 1, 1, 1, 3, 3, 7, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 1042 */       a(world, structureboundingbox, 1, 5, 1, 3, 9, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 1043 */       a(world, structureboundingbox, 1, 0, 0, 3, 0, 8, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/* 1044 */       a(world, structureboundingbox, 1, 1, 0, 3, 10, 0, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/* 1045 */       a(world, structureboundingbox, 0, 1, 1, 0, 10, 3, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/* 1046 */       a(world, structureboundingbox, 4, 1, 1, 4, 10, 3, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/* 1047 */       a(world, structureboundingbox, 0, 0, 4, 0, 4, 7, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/* 1048 */       a(world, structureboundingbox, 4, 0, 4, 4, 4, 7, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/* 1049 */       a(world, structureboundingbox, 1, 1, 8, 3, 4, 8, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/* 1050 */       a(world, structureboundingbox, 1, 5, 4, 3, 10, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/* 1051 */       a(world, structureboundingbox, 1, 5, 5, 3, 5, 7, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/* 1052 */       a(world, structureboundingbox, 0, 9, 0, 4, 9, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/* 1053 */       a(world, structureboundingbox, 0, 4, 0, 4, 4, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/* 1054 */       a(world, Blocks.COBBLESTONE.getBlockData(), 0, 11, 2, structureboundingbox);
/* 1055 */       a(world, Blocks.COBBLESTONE.getBlockData(), 4, 11, 2, structureboundingbox);
/* 1056 */       a(world, Blocks.COBBLESTONE.getBlockData(), 2, 11, 0, structureboundingbox);
/* 1057 */       a(world, Blocks.COBBLESTONE.getBlockData(), 2, 11, 4, structureboundingbox);
/* 1058 */       a(world, Blocks.COBBLESTONE.getBlockData(), 1, 1, 6, structureboundingbox);
/* 1059 */       a(world, Blocks.COBBLESTONE.getBlockData(), 1, 1, 7, structureboundingbox);
/* 1060 */       a(world, Blocks.COBBLESTONE.getBlockData(), 2, 1, 7, structureboundingbox);
/* 1061 */       a(world, Blocks.COBBLESTONE.getBlockData(), 3, 1, 6, structureboundingbox);
/* 1062 */       a(world, Blocks.COBBLESTONE.getBlockData(), 3, 1, 7, structureboundingbox);
/* 1063 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(a(Blocks.STONE_STAIRS, 3)), 1, 1, 5, structureboundingbox);
/* 1064 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(a(Blocks.STONE_STAIRS, 3)), 2, 1, 6, structureboundingbox);
/* 1065 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(a(Blocks.STONE_STAIRS, 3)), 3, 1, 5, structureboundingbox);
/* 1066 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(a(Blocks.STONE_STAIRS, 1)), 1, 2, 7, structureboundingbox);
/* 1067 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(a(Blocks.STONE_STAIRS, 0)), 3, 2, 7, structureboundingbox);
/* 1068 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, structureboundingbox);
/* 1069 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 3, 2, structureboundingbox);
/* 1070 */       a(world, Blocks.GLASS_PANE.getBlockData(), 4, 2, 2, structureboundingbox);
/* 1071 */       a(world, Blocks.GLASS_PANE.getBlockData(), 4, 3, 2, structureboundingbox);
/* 1072 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 6, 2, structureboundingbox);
/* 1073 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 7, 2, structureboundingbox);
/* 1074 */       a(world, Blocks.GLASS_PANE.getBlockData(), 4, 6, 2, structureboundingbox);
/* 1075 */       a(world, Blocks.GLASS_PANE.getBlockData(), 4, 7, 2, structureboundingbox);
/* 1076 */       a(world, Blocks.GLASS_PANE.getBlockData(), 2, 6, 0, structureboundingbox);
/* 1077 */       a(world, Blocks.GLASS_PANE.getBlockData(), 2, 7, 0, structureboundingbox);
/* 1078 */       a(world, Blocks.GLASS_PANE.getBlockData(), 2, 6, 4, structureboundingbox);
/* 1079 */       a(world, Blocks.GLASS_PANE.getBlockData(), 2, 7, 4, structureboundingbox);
/* 1080 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 3, 6, structureboundingbox);
/* 1081 */       a(world, Blocks.GLASS_PANE.getBlockData(), 4, 3, 6, structureboundingbox);
/* 1082 */       a(world, Blocks.GLASS_PANE.getBlockData(), 2, 3, 8, structureboundingbox);
/* 1083 */       a(world, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.opposite()), 2, 4, 7, structureboundingbox);
/* 1084 */       a(world, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.e()), 1, 4, 6, structureboundingbox);
/* 1085 */       a(world, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m.f()), 3, 4, 6, structureboundingbox);
/* 1086 */       a(world, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m), 2, 4, 5, structureboundingbox);
/* 1087 */       int i = a(Blocks.LADDER, 4);
/*      */       
/*      */ 
/*      */ 
/* 1091 */       for (int j = 1; j <= 9; j++) {
/* 1092 */         a(world, Blocks.LADDER.fromLegacyData(i), 3, j, 3, structureboundingbox);
/*      */       }
/*      */       
/* 1095 */       a(world, Blocks.AIR.getBlockData(), 2, 1, 0, structureboundingbox);
/* 1096 */       a(world, Blocks.AIR.getBlockData(), 2, 2, 0, structureboundingbox);
/* 1097 */       a(world, structureboundingbox, random, 2, 1, 0, EnumDirection.fromType2(a(Blocks.WOODEN_DOOR, 1)));
/* 1098 */       if ((a(world, 2, 0, -1, structureboundingbox).getBlock().getMaterial() == Material.AIR) && (a(world, 2, -1, -1, structureboundingbox).getBlock().getMaterial() != Material.AIR)) {
/* 1099 */         a(world, Blocks.STONE_STAIRS.fromLegacyData(a(Blocks.STONE_STAIRS, 3)), 2, 0, -1, structureboundingbox);
/*      */       }
/*      */       
/* 1102 */       for (j = 0; j < 9; j++) {
/* 1103 */         for (int k = 0; k < 5; k++) {
/* 1104 */           b(world, k, 12, j, structureboundingbox);
/* 1105 */           b(world, Blocks.COBBLESTONE.getBlockData(), k, -1, j, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/* 1109 */       a(world, structureboundingbox, 2, 1, 2, 1);
/* 1110 */       return true;
/*      */     }
/*      */     
/*      */     protected int c(int i, int j) {
/* 1114 */       return 2;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageHouse extends WorldGenVillagePieces.WorldGenVillagePiece
/*      */   {
/*      */     private boolean a;
/*      */     
/*      */     public WorldGenVillageHouse() {}
/*      */     
/*      */     public WorldGenVillageHouse(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
/* 1125 */       super(i);
/* 1126 */       this.m = enumdirection;
/* 1127 */       this.l = structureboundingbox;
/* 1128 */       this.a = random.nextBoolean();
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound nbttagcompound) {
/* 1132 */       super.a(nbttagcompound);
/* 1133 */       nbttagcompound.setBoolean("Terrace", this.a);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound nbttagcompound) {
/* 1137 */       super.b(nbttagcompound);
/* 1138 */       this.a = nbttagcompound.getBoolean("Terrace");
/*      */     }
/*      */     
/*      */     public static WorldGenVillageHouse a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection, int l) {
/* 1142 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 5, 6, 5, enumdirection);
/*      */       
/* 1144 */       return StructurePiece.a(list, structureboundingbox) != null ? null : new WorldGenVillageHouse(worldgenvillagepieces_worldgenvillagestartpiece, l, random, structureboundingbox, enumdirection);
/*      */     }
/*      */     
/*      */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/* 1148 */       if (this.h < 0) {
/* 1149 */         this.h = b(world, structureboundingbox);
/* 1150 */         if (this.h < 0) {
/* 1151 */           return true;
/*      */         }
/*      */         
/* 1154 */         this.l.a(0, this.h - this.l.e + 6 - 1, 0);
/*      */       }
/*      */       
/* 1157 */       a(world, structureboundingbox, 0, 0, 0, 4, 0, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/* 1158 */       a(world, structureboundingbox, 0, 4, 0, 4, 4, 4, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/* 1159 */       a(world, structureboundingbox, 1, 4, 1, 3, 4, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/* 1160 */       a(world, Blocks.COBBLESTONE.getBlockData(), 0, 1, 0, structureboundingbox);
/* 1161 */       a(world, Blocks.COBBLESTONE.getBlockData(), 0, 2, 0, structureboundingbox);
/* 1162 */       a(world, Blocks.COBBLESTONE.getBlockData(), 0, 3, 0, structureboundingbox);
/* 1163 */       a(world, Blocks.COBBLESTONE.getBlockData(), 4, 1, 0, structureboundingbox);
/* 1164 */       a(world, Blocks.COBBLESTONE.getBlockData(), 4, 2, 0, structureboundingbox);
/* 1165 */       a(world, Blocks.COBBLESTONE.getBlockData(), 4, 3, 0, structureboundingbox);
/* 1166 */       a(world, Blocks.COBBLESTONE.getBlockData(), 0, 1, 4, structureboundingbox);
/* 1167 */       a(world, Blocks.COBBLESTONE.getBlockData(), 0, 2, 4, structureboundingbox);
/* 1168 */       a(world, Blocks.COBBLESTONE.getBlockData(), 0, 3, 4, structureboundingbox);
/* 1169 */       a(world, Blocks.COBBLESTONE.getBlockData(), 4, 1, 4, structureboundingbox);
/* 1170 */       a(world, Blocks.COBBLESTONE.getBlockData(), 4, 2, 4, structureboundingbox);
/* 1171 */       a(world, Blocks.COBBLESTONE.getBlockData(), 4, 3, 4, structureboundingbox);
/* 1172 */       a(world, structureboundingbox, 0, 1, 1, 0, 3, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/* 1173 */       a(world, structureboundingbox, 4, 1, 1, 4, 3, 3, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/* 1174 */       a(world, structureboundingbox, 1, 1, 4, 3, 3, 4, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/* 1175 */       a(world, Blocks.GLASS_PANE.getBlockData(), 0, 2, 2, structureboundingbox);
/* 1176 */       a(world, Blocks.GLASS_PANE.getBlockData(), 2, 2, 4, structureboundingbox);
/* 1177 */       a(world, Blocks.GLASS_PANE.getBlockData(), 4, 2, 2, structureboundingbox);
/* 1178 */       a(world, Blocks.PLANKS.getBlockData(), 1, 1, 0, structureboundingbox);
/* 1179 */       a(world, Blocks.PLANKS.getBlockData(), 1, 2, 0, structureboundingbox);
/* 1180 */       a(world, Blocks.PLANKS.getBlockData(), 1, 3, 0, structureboundingbox);
/* 1181 */       a(world, Blocks.PLANKS.getBlockData(), 2, 3, 0, structureboundingbox);
/* 1182 */       a(world, Blocks.PLANKS.getBlockData(), 3, 3, 0, structureboundingbox);
/* 1183 */       a(world, Blocks.PLANKS.getBlockData(), 3, 2, 0, structureboundingbox);
/* 1184 */       a(world, Blocks.PLANKS.getBlockData(), 3, 1, 0, structureboundingbox);
/* 1185 */       if ((a(world, 2, 0, -1, structureboundingbox).getBlock().getMaterial() == Material.AIR) && (a(world, 2, -1, -1, structureboundingbox).getBlock().getMaterial() != Material.AIR)) {
/* 1186 */         a(world, Blocks.STONE_STAIRS.fromLegacyData(a(Blocks.STONE_STAIRS, 3)), 2, 0, -1, structureboundingbox);
/*      */       }
/*      */       
/* 1189 */       a(world, structureboundingbox, 1, 1, 1, 3, 3, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 1190 */       if (this.a) {
/* 1191 */         a(world, Blocks.FENCE.getBlockData(), 0, 5, 0, structureboundingbox);
/* 1192 */         a(world, Blocks.FENCE.getBlockData(), 1, 5, 0, structureboundingbox);
/* 1193 */         a(world, Blocks.FENCE.getBlockData(), 2, 5, 0, structureboundingbox);
/* 1194 */         a(world, Blocks.FENCE.getBlockData(), 3, 5, 0, structureboundingbox);
/* 1195 */         a(world, Blocks.FENCE.getBlockData(), 4, 5, 0, structureboundingbox);
/* 1196 */         a(world, Blocks.FENCE.getBlockData(), 0, 5, 4, structureboundingbox);
/* 1197 */         a(world, Blocks.FENCE.getBlockData(), 1, 5, 4, structureboundingbox);
/* 1198 */         a(world, Blocks.FENCE.getBlockData(), 2, 5, 4, structureboundingbox);
/* 1199 */         a(world, Blocks.FENCE.getBlockData(), 3, 5, 4, structureboundingbox);
/* 1200 */         a(world, Blocks.FENCE.getBlockData(), 4, 5, 4, structureboundingbox);
/* 1201 */         a(world, Blocks.FENCE.getBlockData(), 4, 5, 1, structureboundingbox);
/* 1202 */         a(world, Blocks.FENCE.getBlockData(), 4, 5, 2, structureboundingbox);
/* 1203 */         a(world, Blocks.FENCE.getBlockData(), 4, 5, 3, structureboundingbox);
/* 1204 */         a(world, Blocks.FENCE.getBlockData(), 0, 5, 1, structureboundingbox);
/* 1205 */         a(world, Blocks.FENCE.getBlockData(), 0, 5, 2, structureboundingbox);
/* 1206 */         a(world, Blocks.FENCE.getBlockData(), 0, 5, 3, structureboundingbox);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1211 */       if (this.a) {
/* 1212 */         int i = a(Blocks.LADDER, 3);
/* 1213 */         a(world, Blocks.LADDER.fromLegacyData(i), 3, 1, 3, structureboundingbox);
/* 1214 */         a(world, Blocks.LADDER.fromLegacyData(i), 3, 2, 3, structureboundingbox);
/* 1215 */         a(world, Blocks.LADDER.fromLegacyData(i), 3, 3, 3, structureboundingbox);
/* 1216 */         a(world, Blocks.LADDER.fromLegacyData(i), 3, 4, 3, structureboundingbox);
/*      */       }
/*      */       
/* 1219 */       a(world, Blocks.TORCH.getBlockData().set(BlockTorch.FACING, this.m), 2, 3, 1, structureboundingbox);
/*      */       
/* 1221 */       for (int i = 0; i < 5; i++) {
/* 1222 */         for (int j = 0; j < 5; j++) {
/* 1223 */           b(world, j, 6, i, structureboundingbox);
/* 1224 */           b(world, Blocks.COBBLESTONE.getBlockData(), j, -1, i, structureboundingbox);
/*      */         }
/*      */       }
/*      */       
/* 1228 */       a(world, structureboundingbox, 1, 1, 2, 1);
/* 1229 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageRoad extends WorldGenVillagePieces.WorldGenVillageRoadPiece
/*      */   {
/*      */     private int a;
/*      */     
/*      */     public WorldGenVillageRoad() {}
/*      */     
/*      */     public WorldGenVillageRoad(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i, Random random, StructureBoundingBox structureboundingbox, EnumDirection enumdirection) {
/* 1240 */       super(i);
/* 1241 */       this.m = enumdirection;
/* 1242 */       this.l = structureboundingbox;
/* 1243 */       this.a = Math.max(structureboundingbox.c(), structureboundingbox.e());
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound nbttagcompound) {
/* 1247 */       super.a(nbttagcompound);
/* 1248 */       nbttagcompound.setInt("Length", this.a);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound nbttagcompound) {
/* 1252 */       super.b(nbttagcompound);
/* 1253 */       this.a = nbttagcompound.getInt("Length");
/*      */     }
/*      */     
/*      */     public void a(StructurePiece structurepiece, List<StructurePiece> list, Random random) {
/* 1257 */       boolean flag = false;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1262 */       for (int i = random.nextInt(5); i < this.a - 8; i += 2 + random.nextInt(5)) {
/* 1263 */         StructurePiece structurepiece1 = a((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, 0, i);
/* 1264 */         if (structurepiece1 != null) {
/* 1265 */           i += Math.max(structurepiece1.l.c(), structurepiece1.l.e());
/* 1266 */           flag = true;
/*      */         }
/*      */       }
/*      */       
/* 1270 */       for (i = random.nextInt(5); i < this.a - 8; i += 2 + random.nextInt(5)) {
/* 1271 */         StructurePiece structurepiece1 = b((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, 0, i);
/* 1272 */         if (structurepiece1 != null) {
/* 1273 */           i += Math.max(structurepiece1.l.c(), structurepiece1.l.e());
/* 1274 */           flag = true;
/*      */         }
/*      */       }
/*      */       
/* 1278 */       if ((flag) && (random.nextInt(3) > 0) && (this.m != null)) {
/* 1279 */         switch (WorldGenVillagePieces.SyntheticClass_1.a[this.m.ordinal()]) {
/*      */         case 1: 
/* 1281 */           WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.l.a - 1, this.l.b, this.l.c, EnumDirection.WEST, d());
/* 1282 */           break;
/*      */         
/*      */         case 2: 
/* 1285 */           WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.l.a - 1, this.l.b, this.l.f - 2, EnumDirection.WEST, d());
/* 1286 */           break;
/*      */         
/*      */         case 3: 
/* 1289 */           WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.l.a, this.l.b, this.l.c - 1, EnumDirection.NORTH, d());
/* 1290 */           break;
/*      */         
/*      */         case 4: 
/* 1293 */           WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.l.d - 2, this.l.b, this.l.c - 1, EnumDirection.NORTH, d());
/*      */         }
/*      */         
/*      */       }
/* 1297 */       if ((flag) && (random.nextInt(3) > 0) && (this.m != null)) {
/* 1298 */         switch (WorldGenVillagePieces.SyntheticClass_1.a[this.m.ordinal()]) {
/*      */         case 1: 
/* 1300 */           WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.l.d + 1, this.l.b, this.l.c, EnumDirection.EAST, d());
/* 1301 */           break;
/*      */         
/*      */         case 2: 
/* 1304 */           WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.l.d + 1, this.l.b, this.l.f - 2, EnumDirection.EAST, d());
/* 1305 */           break;
/*      */         
/*      */         case 3: 
/* 1308 */           WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.l.a, this.l.b, this.l.f + 1, EnumDirection.SOUTH, d());
/* 1309 */           break;
/*      */         
/*      */         case 4: 
/* 1312 */           WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.l.d - 2, this.l.b, this.l.f + 1, EnumDirection.SOUTH, d());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public static StructureBoundingBox a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j, int k, EnumDirection enumdirection)
/*      */     {
/* 1319 */       for (int l = 7 * MathHelper.nextInt(random, 3, 5); l >= 7; l -= 7) {
/* 1320 */         StructureBoundingBox structureboundingbox = StructureBoundingBox.a(i, j, k, 0, 0, 0, 3, 3, l, enumdirection);
/*      */         
/* 1322 */         if (StructurePiece.a(list, structureboundingbox) == null) {
/* 1323 */           return structureboundingbox;
/*      */         }
/*      */       }
/*      */       
/* 1327 */       return null;
/*      */     }
/*      */     
/*      */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/* 1331 */       IBlockData iblockdata = a(Blocks.GRAVEL.getBlockData());
/* 1332 */       IBlockData iblockdata1 = a(Blocks.COBBLESTONE.getBlockData());
/*      */       
/* 1334 */       for (int i = this.l.a; i <= this.l.d; i++) {
/* 1335 */         for (int j = this.l.c; j <= this.l.f; j++) {
/* 1336 */           BlockPosition blockposition = new BlockPosition(i, 64, j);
/*      */           
/* 1338 */           if (structureboundingbox.b(blockposition)) {
/* 1339 */             blockposition = world.r(blockposition).down();
/* 1340 */             world.setTypeAndData(blockposition, iblockdata, 2);
/* 1341 */             world.setTypeAndData(blockposition.down(), iblockdata1, 2);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1346 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static abstract class WorldGenVillageRoadPiece extends WorldGenVillagePieces.WorldGenVillagePiece
/*      */   {
/*      */     public WorldGenVillageRoadPiece() {}
/*      */     
/*      */     protected WorldGenVillageRoadPiece(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i) {
/* 1355 */       super(i);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageStartPiece extends WorldGenVillagePieces.WorldGenVillageWell
/*      */   {
/*      */     public WorldChunkManager a;
/*      */     public boolean b;
/*      */     public int c;
/*      */     public WorldGenVillagePieces.WorldGenVillagePieceWeight d;
/*      */     public List<WorldGenVillagePieces.WorldGenVillagePieceWeight> e;
/* 1366 */     public List<StructurePiece> f = com.google.common.collect.Lists.newArrayList();
/* 1367 */     public List<StructurePiece> g = com.google.common.collect.Lists.newArrayList();
/*      */     
/*      */     public WorldGenVillageStartPiece() {}
/*      */     
/*      */     public WorldGenVillageStartPiece(WorldChunkManager worldchunkmanager, int i, Random random, int j, int k, List<WorldGenVillagePieces.WorldGenVillagePieceWeight> list, int l) {
/* 1372 */       super(0, random, j, k);
/* 1373 */       this.a = worldchunkmanager;
/* 1374 */       this.e = list;
/* 1375 */       this.c = l;
/* 1376 */       BiomeBase biomebase = worldchunkmanager.getBiome(new BlockPosition(j, 0, k), BiomeBase.ad);
/*      */       
/* 1378 */       this.b = ((biomebase == BiomeBase.DESERT) || (biomebase == BiomeBase.DESERT_HILLS));
/* 1379 */       a(this.b);
/*      */     }
/*      */     
/*      */     public WorldChunkManager e() {
/* 1383 */       return this.a;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillageWell extends WorldGenVillagePieces.WorldGenVillagePiece
/*      */   {
/*      */     public WorldGenVillageWell() {}
/*      */     
/*      */     public WorldGenVillageWell(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i, Random random, int j, int k) {
/* 1392 */       super(i);
/* 1393 */       this.m = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(random);
/* 1394 */       switch (WorldGenVillagePieces.SyntheticClass_1.a[this.m.ordinal()]) {
/*      */       case 1: 
/*      */       case 2: 
/* 1397 */         this.l = new StructureBoundingBox(j, 64, k, j + 6 - 1, 78, k + 6 - 1);
/* 1398 */         break;
/*      */       
/*      */       default: 
/* 1401 */         this.l = new StructureBoundingBox(j, 64, k, j + 6 - 1, 78, k + 6 - 1);
/*      */       }
/*      */     }
/*      */     
/*      */     public void a(StructurePiece structurepiece, List<StructurePiece> list, Random random)
/*      */     {
/* 1407 */       WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.l.a - 1, this.l.e - 4, this.l.c + 1, EnumDirection.WEST, d());
/* 1408 */       WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.l.d + 1, this.l.e - 4, this.l.c + 1, EnumDirection.EAST, d());
/* 1409 */       WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.l.a + 1, this.l.e - 4, this.l.c - 1, EnumDirection.NORTH, d());
/* 1410 */       WorldGenVillagePieces.e((WorldGenVillagePieces.WorldGenVillageStartPiece)structurepiece, list, random, this.l.a + 1, this.l.e - 4, this.l.f + 1, EnumDirection.SOUTH, d());
/*      */     }
/*      */     
/*      */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/* 1414 */       if (this.h < 0) {
/* 1415 */         this.h = b(world, structureboundingbox);
/* 1416 */         if (this.h < 0) {
/* 1417 */           return true;
/*      */         }
/*      */         
/* 1420 */         this.l.a(0, this.h - this.l.e + 3, 0);
/*      */       }
/*      */       
/* 1423 */       a(world, structureboundingbox, 1, 0, 1, 4, 12, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.FLOWING_WATER.getBlockData(), false);
/* 1424 */       a(world, Blocks.AIR.getBlockData(), 2, 12, 2, structureboundingbox);
/* 1425 */       a(world, Blocks.AIR.getBlockData(), 3, 12, 2, structureboundingbox);
/* 1426 */       a(world, Blocks.AIR.getBlockData(), 2, 12, 3, structureboundingbox);
/* 1427 */       a(world, Blocks.AIR.getBlockData(), 3, 12, 3, structureboundingbox);
/* 1428 */       a(world, Blocks.FENCE.getBlockData(), 1, 13, 1, structureboundingbox);
/* 1429 */       a(world, Blocks.FENCE.getBlockData(), 1, 14, 1, structureboundingbox);
/* 1430 */       a(world, Blocks.FENCE.getBlockData(), 4, 13, 1, structureboundingbox);
/* 1431 */       a(world, Blocks.FENCE.getBlockData(), 4, 14, 1, structureboundingbox);
/* 1432 */       a(world, Blocks.FENCE.getBlockData(), 1, 13, 4, structureboundingbox);
/* 1433 */       a(world, Blocks.FENCE.getBlockData(), 1, 14, 4, structureboundingbox);
/* 1434 */       a(world, Blocks.FENCE.getBlockData(), 4, 13, 4, structureboundingbox);
/* 1435 */       a(world, Blocks.FENCE.getBlockData(), 4, 14, 4, structureboundingbox);
/* 1436 */       a(world, structureboundingbox, 1, 15, 1, 4, 15, 4, Blocks.COBBLESTONE.getBlockData(), Blocks.COBBLESTONE.getBlockData(), false);
/*      */       
/* 1438 */       for (int i = 0; i <= 5; i++) {
/* 1439 */         for (int j = 0; j <= 5; j++) {
/* 1440 */           if ((j == 0) || (j == 5) || (i == 0) || (i == 5)) {
/* 1441 */             a(world, Blocks.GRAVEL.getBlockData(), j, 11, i, structureboundingbox);
/* 1442 */             b(world, j, 12, i, structureboundingbox);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1447 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class WorldGenVillagePiece extends StructurePiece
/*      */   {
/* 1453 */     protected int h = -1;
/*      */     private int a;
/*      */     private boolean b;
/*      */     
/*      */     public WorldGenVillagePiece() {}
/*      */     
/*      */     protected WorldGenVillagePiece(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, int i) {
/* 1460 */       super();
/* 1461 */       if (worldgenvillagepieces_worldgenvillagestartpiece != null) {
/* 1462 */         this.b = worldgenvillagepieces_worldgenvillagestartpiece.b;
/*      */       }
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound nbttagcompound)
/*      */     {
/* 1468 */       nbttagcompound.setInt("HPos", this.h);
/* 1469 */       nbttagcompound.setInt("VCount", this.a);
/* 1470 */       nbttagcompound.setBoolean("Desert", this.b);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound nbttagcompound) {
/* 1474 */       this.h = nbttagcompound.getInt("HPos");
/* 1475 */       this.a = nbttagcompound.getInt("VCount");
/* 1476 */       this.b = nbttagcompound.getBoolean("Desert");
/*      */     }
/*      */     
/*      */     protected StructurePiece a(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j) {
/* 1480 */       if (this.m != null) {
/* 1481 */         switch (WorldGenVillagePieces.SyntheticClass_1.a[this.m.ordinal()]) {
/*      */         case 1: 
/* 1483 */           return WorldGenVillagePieces.d(worldgenvillagepieces_worldgenvillagestartpiece, list, random, this.l.a - 1, this.l.b + i, this.l.c + j, EnumDirection.WEST, d());
/*      */         
/*      */         case 2: 
/* 1486 */           return WorldGenVillagePieces.d(worldgenvillagepieces_worldgenvillagestartpiece, list, random, this.l.a - 1, this.l.b + i, this.l.c + j, EnumDirection.WEST, d());
/*      */         
/*      */         case 3: 
/* 1489 */           return WorldGenVillagePieces.d(worldgenvillagepieces_worldgenvillagestartpiece, list, random, this.l.a + j, this.l.b + i, this.l.c - 1, EnumDirection.NORTH, d());
/*      */         
/*      */         case 4: 
/* 1492 */           return WorldGenVillagePieces.d(worldgenvillagepieces_worldgenvillagestartpiece, list, random, this.l.a + j, this.l.b + i, this.l.c - 1, EnumDirection.NORTH, d());
/*      */         }
/*      */         
/*      */       }
/* 1496 */       return null;
/*      */     }
/*      */     
/*      */     protected StructurePiece b(WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece, List<StructurePiece> list, Random random, int i, int j) {
/* 1500 */       if (this.m != null) {
/* 1501 */         switch (WorldGenVillagePieces.SyntheticClass_1.a[this.m.ordinal()]) {
/*      */         case 1: 
/* 1503 */           return WorldGenVillagePieces.d(worldgenvillagepieces_worldgenvillagestartpiece, list, random, this.l.d + 1, this.l.b + i, this.l.c + j, EnumDirection.EAST, d());
/*      */         
/*      */         case 2: 
/* 1506 */           return WorldGenVillagePieces.d(worldgenvillagepieces_worldgenvillagestartpiece, list, random, this.l.d + 1, this.l.b + i, this.l.c + j, EnumDirection.EAST, d());
/*      */         
/*      */         case 3: 
/* 1509 */           return WorldGenVillagePieces.d(worldgenvillagepieces_worldgenvillagestartpiece, list, random, this.l.a + j, this.l.b + i, this.l.f + 1, EnumDirection.SOUTH, d());
/*      */         
/*      */         case 4: 
/* 1512 */           return WorldGenVillagePieces.d(worldgenvillagepieces_worldgenvillagestartpiece, list, random, this.l.a + j, this.l.b + i, this.l.f + 1, EnumDirection.SOUTH, d());
/*      */         }
/*      */         
/*      */       }
/* 1516 */       return null;
/*      */     }
/*      */     
/*      */     protected int b(World world, StructureBoundingBox structureboundingbox) {
/* 1520 */       int i = 0;
/* 1521 */       int j = 0;
/* 1522 */       BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*      */       
/* 1524 */       for (int k = this.l.c; k <= this.l.f; k++) {
/* 1525 */         for (int l = this.l.a; l <= this.l.d; l++) {
/* 1526 */           blockposition_mutableblockposition.c(l, 64, k);
/* 1527 */           if (structureboundingbox.b(blockposition_mutableblockposition)) {
/* 1528 */             i += Math.max(world.r(blockposition_mutableblockposition).getY(), world.worldProvider.getSeaLevel());
/* 1529 */             j++;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1534 */       if (j == 0) {
/* 1535 */         return -1;
/*      */       }
/* 1537 */       return i / j;
/*      */     }
/*      */     
/*      */     protected static boolean a(StructureBoundingBox structureboundingbox)
/*      */     {
/* 1542 */       return (structureboundingbox != null) && (structureboundingbox.b > 10);
/*      */     }
/*      */     
/*      */     protected void a(World world, StructureBoundingBox structureboundingbox, int i, int j, int k, int l) {
/* 1546 */       if (this.a < l) {
/* 1547 */         for (int i1 = this.a; i1 < l; i1++) {
/* 1548 */           int j1 = a(i + i1, k);
/* 1549 */           int k1 = d(j);
/* 1550 */           int l1 = b(i + i1, k);
/*      */           
/* 1552 */           if (!structureboundingbox.b(new BlockPosition(j1, k1, l1))) {
/*      */             break;
/*      */           }
/*      */           
/* 1556 */           this.a += 1;
/* 1557 */           EntityVillager entityvillager = new EntityVillager(world);
/*      */           
/* 1559 */           entityvillager.setPositionRotation(j1 + 0.5D, k1, l1 + 0.5D, 0.0F, 0.0F);
/* 1560 */           entityvillager.prepare(world.E(new BlockPosition(entityvillager)), null);
/* 1561 */           entityvillager.setProfession(c(i1, entityvillager.getProfession()));
/* 1562 */           world.addEntity(entityvillager, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.CHUNK_GEN);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     protected int c(int i, int j)
/*      */     {
/* 1569 */       return j;
/*      */     }
/*      */     
/*      */     protected IBlockData a(IBlockData iblockdata) {
/* 1573 */       if (this.b) {
/* 1574 */         if ((iblockdata.getBlock() == Blocks.LOG) || (iblockdata.getBlock() == Blocks.LOG2)) {
/* 1575 */           return Blocks.SANDSTONE.getBlockData();
/*      */         }
/*      */         
/* 1578 */         if (iblockdata.getBlock() == Blocks.COBBLESTONE) {
/* 1579 */           return Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.DEFAULT.a());
/*      */         }
/*      */         
/* 1582 */         if (iblockdata.getBlock() == Blocks.PLANKS) {
/* 1583 */           return Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a());
/*      */         }
/*      */         
/* 1586 */         if (iblockdata.getBlock() == Blocks.OAK_STAIRS) {
/* 1587 */           return Blocks.SANDSTONE_STAIRS.getBlockData().set(BlockStairs.FACING, (EnumDirection)iblockdata.get(BlockStairs.FACING));
/*      */         }
/*      */         
/* 1590 */         if (iblockdata.getBlock() == Blocks.STONE_STAIRS) {
/* 1591 */           return Blocks.SANDSTONE_STAIRS.getBlockData().set(BlockStairs.FACING, (EnumDirection)iblockdata.get(BlockStairs.FACING));
/*      */         }
/*      */         
/* 1594 */         if (iblockdata.getBlock() == Blocks.GRAVEL) {
/* 1595 */           return Blocks.SANDSTONE.getBlockData();
/*      */         }
/*      */       }
/*      */       
/* 1599 */       return iblockdata;
/*      */     }
/*      */     
/*      */     protected void a(World world, IBlockData iblockdata, int i, int j, int k, StructureBoundingBox structureboundingbox) {
/* 1603 */       IBlockData iblockdata1 = a(iblockdata);
/*      */       
/* 1605 */       super.a(world, iblockdata1, i, j, k, structureboundingbox);
/*      */     }
/*      */     
/*      */     protected void a(World world, StructureBoundingBox structureboundingbox, int i, int j, int k, int l, int i1, int j1, IBlockData iblockdata, IBlockData iblockdata1, boolean flag) {
/* 1609 */       IBlockData iblockdata2 = a(iblockdata);
/* 1610 */       IBlockData iblockdata3 = a(iblockdata1);
/*      */       
/* 1612 */       super.a(world, structureboundingbox, i, j, k, l, i1, j1, iblockdata2, iblockdata3, flag);
/*      */     }
/*      */     
/*      */     protected void b(World world, IBlockData iblockdata, int i, int j, int k, StructureBoundingBox structureboundingbox) {
/* 1616 */       IBlockData iblockdata1 = a(iblockdata);
/*      */       
/* 1618 */       super.b(world, iblockdata1, i, j, k, structureboundingbox);
/*      */     }
/*      */     
/*      */     protected void a(boolean flag) {
/* 1622 */       this.b = flag;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenVillagePieceWeight
/*      */   {
/*      */     public Class<? extends WorldGenVillagePieces.WorldGenVillagePiece> a;
/*      */     public final int b;
/*      */     public int c;
/*      */     public int d;
/*      */     
/*      */     public WorldGenVillagePieceWeight(Class<? extends WorldGenVillagePieces.WorldGenVillagePiece> oclass, int i, int j) {
/* 1634 */       this.a = oclass;
/* 1635 */       this.b = i;
/* 1636 */       this.d = j;
/*      */     }
/*      */     
/*      */     public boolean a(int i) {
/* 1640 */       return (this.d == 0) || (this.c < this.d);
/*      */     }
/*      */     
/*      */     public boolean a() {
/* 1644 */       return (this.d == 0) || (this.c < this.d);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenVillagePieces.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */