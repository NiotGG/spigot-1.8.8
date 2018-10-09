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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WorldGenStrongholdPieces
/*      */ {
/*      */   public static void a()
/*      */   {
/*   31 */     WorldGenFactory.a(WorldGenStrongholdChestCorridor.class, "SHCC");
/*   32 */     WorldGenFactory.a(WorldGenStrongholdCorridor.class, "SHFC");
/*   33 */     WorldGenFactory.a(WorldGenStrongholdCrossing.class, "SH5C");
/*   34 */     WorldGenFactory.a(WorldGenStrongholdLeftTurn.class, "SHLT");
/*   35 */     WorldGenFactory.a(WorldGenStrongholdLibrary.class, "SHLi");
/*   36 */     WorldGenFactory.a(WorldGenStrongholdPortalRoom.class, "SHPR");
/*   37 */     WorldGenFactory.a(WorldGenStrongholdPrison.class, "SHPH");
/*   38 */     WorldGenFactory.a(WorldGenStrongholdRightTurn.class, "SHRT");
/*   39 */     WorldGenFactory.a(WorldGenStrongholdRoomCrossing.class, "SHRC");
/*   40 */     WorldGenFactory.a(WorldGenStrongholdStairs2.class, "SHSD");
/*   41 */     WorldGenFactory.a(WorldGenStrongholdStart.class, "SHStart");
/*   42 */     WorldGenFactory.a(WorldGenStrongholdStairs.class, "SHS");
/*   43 */     WorldGenFactory.a(WorldGenStrongholdStairsStraight.class, "SHSSD");
/*      */   }
/*      */   
/*      */   static class WorldGenStrongholdPieceWeight {
/*      */     public Class<? extends WorldGenStrongholdPieces.WorldGenStrongholdPiece> a;
/*      */     public final int b;
/*      */     public int c;
/*      */     public int d;
/*      */     
/*      */     public WorldGenStrongholdPieceWeight(Class<? extends WorldGenStrongholdPieces.WorldGenStrongholdPiece> paramClass, int paramInt1, int paramInt2) {
/*   53 */       this.a = paramClass;
/*   54 */       this.b = paramInt1;
/*   55 */       this.d = paramInt2;
/*      */     }
/*      */     
/*      */     public boolean a(int paramInt) {
/*   59 */       return (this.d == 0) || (this.c < this.d);
/*      */     }
/*      */     
/*      */     public boolean a() {
/*   63 */       return (this.d == 0) || (this.c < this.d);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*   68 */   private static final WorldGenStrongholdPieceWeight[] b = { new WorldGenStrongholdPieceWeight(WorldGenStrongholdStairs.class, 40, 0), new WorldGenStrongholdPieceWeight(WorldGenStrongholdPrison.class, 5, 5), new WorldGenStrongholdPieceWeight(WorldGenStrongholdLeftTurn.class, 20, 0), new WorldGenStrongholdPieceWeight(WorldGenStrongholdRightTurn.class, 20, 0), new WorldGenStrongholdPieceWeight(WorldGenStrongholdRoomCrossing.class, 10, 6), new WorldGenStrongholdPieceWeight(WorldGenStrongholdStairsStraight.class, 5, 5), new WorldGenStrongholdPieceWeight(WorldGenStrongholdStairs2.class, 5, 5), new WorldGenStrongholdPieceWeight(WorldGenStrongholdCrossing.class, 5, 4), new WorldGenStrongholdPieceWeight(WorldGenStrongholdChestCorridor.class, 5, 4), new WorldGenStrongholdPieceWeight(WorldGenStrongholdLibrary.class, 10, 2)new WorldGenStrongholdPieceWeight
/*      */   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean a(int paramAnonymousInt)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   81 */       return (super.a(paramAnonymousInt)) && (paramAnonymousInt > 4);
/*      */     }
/*   68 */   }, new WorldGenStrongholdPieceWeight(WorldGenStrongholdPortalRoom.class, 20, 1)
/*      */   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean a(int paramAnonymousInt)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   87 */       return (super.a(paramAnonymousInt)) && (paramAnonymousInt > 5);
/*      */     }
/*   68 */   } };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static List<WorldGenStrongholdPieceWeight> c;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Class<? extends WorldGenStrongholdPiece> d;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static int a;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void b()
/*      */   {
/*   97 */     c = Lists.newArrayList();
/*   98 */     for (WorldGenStrongholdPieceWeight localWorldGenStrongholdPieceWeight : b) {
/*   99 */       localWorldGenStrongholdPieceWeight.c = 0;
/*  100 */       c.add(localWorldGenStrongholdPieceWeight);
/*      */     }
/*  102 */     d = null;
/*      */   }
/*      */   
/*      */   private static boolean d() {
/*  106 */     boolean bool = false;
/*  107 */     a = 0;
/*  108 */     for (WorldGenStrongholdPieceWeight localWorldGenStrongholdPieceWeight : c) {
/*  109 */       if ((localWorldGenStrongholdPieceWeight.d > 0) && (localWorldGenStrongholdPieceWeight.c < localWorldGenStrongholdPieceWeight.d)) {
/*  110 */         bool = true;
/*      */       }
/*  112 */       a += localWorldGenStrongholdPieceWeight.b;
/*      */     }
/*  114 */     return bool;
/*      */   }
/*      */   
/*      */   private static WorldGenStrongholdPiece a(Class<? extends WorldGenStrongholdPiece> paramClass, List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  118 */     Object localObject = null;
/*      */     
/*  120 */     if (paramClass == WorldGenStrongholdStairs.class) {
/*  121 */       localObject = WorldGenStrongholdStairs.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  122 */     } else if (paramClass == WorldGenStrongholdPrison.class) {
/*  123 */       localObject = WorldGenStrongholdPrison.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  124 */     } else if (paramClass == WorldGenStrongholdLeftTurn.class) {
/*  125 */       localObject = WorldGenStrongholdLeftTurn.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  126 */     } else if (paramClass == WorldGenStrongholdRightTurn.class) {
/*  127 */       localObject = WorldGenStrongholdRightTurn.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  128 */     } else if (paramClass == WorldGenStrongholdRoomCrossing.class) {
/*  129 */       localObject = WorldGenStrongholdRoomCrossing.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  130 */     } else if (paramClass == WorldGenStrongholdStairsStraight.class) {
/*  131 */       localObject = WorldGenStrongholdStairsStraight.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  132 */     } else if (paramClass == WorldGenStrongholdStairs2.class) {
/*  133 */       localObject = WorldGenStrongholdStairs2.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  134 */     } else if (paramClass == WorldGenStrongholdCrossing.class) {
/*  135 */       localObject = WorldGenStrongholdCrossing.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  136 */     } else if (paramClass == WorldGenStrongholdChestCorridor.class) {
/*  137 */       localObject = WorldGenStrongholdChestCorridor.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  138 */     } else if (paramClass == WorldGenStrongholdLibrary.class) {
/*  139 */       localObject = WorldGenStrongholdLibrary.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  140 */     } else if (paramClass == WorldGenStrongholdPortalRoom.class) {
/*  141 */       localObject = WorldGenStrongholdPortalRoom.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*      */     }
/*      */     
/*  144 */     return (WorldGenStrongholdPiece)localObject;
/*      */   }
/*      */   
/*      */   private static WorldGenStrongholdPiece b(WorldGenStrongholdStart paramWorldGenStrongholdStart, List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  148 */     if (!d()) {
/*  149 */       return null;
/*      */     }
/*      */     
/*  152 */     if (d != null) {
/*  153 */       WorldGenStrongholdPiece localWorldGenStrongholdPiece1 = a(d, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  154 */       d = null;
/*      */       
/*  156 */       if (localWorldGenStrongholdPiece1 != null) {
/*  157 */         return localWorldGenStrongholdPiece1;
/*      */       }
/*      */     }
/*      */     
/*  161 */     int i = 0;
/*  162 */     int j; while (i < 5) {
/*  163 */       i++;
/*      */       
/*  165 */       j = paramRandom.nextInt(a);
/*  166 */       for (WorldGenStrongholdPieceWeight localWorldGenStrongholdPieceWeight : c) {
/*  167 */         j -= localWorldGenStrongholdPieceWeight.b;
/*  168 */         if (j < 0) {
/*  169 */           if ((!localWorldGenStrongholdPieceWeight.a(paramInt4)) || (localWorldGenStrongholdPieceWeight == paramWorldGenStrongholdStart.a)) {
/*      */             break;
/*      */           }
/*      */           
/*  173 */           WorldGenStrongholdPiece localWorldGenStrongholdPiece2 = a(localWorldGenStrongholdPieceWeight.a, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4);
/*  174 */           if (localWorldGenStrongholdPiece2 != null) {
/*  175 */             localWorldGenStrongholdPieceWeight.c += 1;
/*  176 */             paramWorldGenStrongholdStart.a = localWorldGenStrongholdPieceWeight;
/*      */             
/*  178 */             if (!localWorldGenStrongholdPieceWeight.a()) {
/*  179 */               c.remove(localWorldGenStrongholdPieceWeight);
/*      */             }
/*  181 */             return localWorldGenStrongholdPiece2;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  186 */     StructureBoundingBox localStructureBoundingBox = WorldGenStrongholdCorridor.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection);
/*  187 */     if ((localStructureBoundingBox != null) && (localStructureBoundingBox.b > 1)) {
/*  188 */       return new WorldGenStrongholdCorridor(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*  191 */     return null;
/*      */   }
/*      */   
/*      */   private static StructurePiece c(WorldGenStrongholdStart paramWorldGenStrongholdStart, List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  195 */     if (paramInt4 > 50) {
/*  196 */       return null;
/*      */     }
/*  198 */     if ((Math.abs(paramInt1 - paramWorldGenStrongholdStart.c().a) > 112) || (Math.abs(paramInt3 - paramWorldGenStrongholdStart.c().c) > 112)) {
/*  199 */       return null;
/*      */     }
/*      */     
/*  202 */     WorldGenStrongholdPiece localWorldGenStrongholdPiece = b(paramWorldGenStrongholdStart, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4 + 1);
/*  203 */     if (localWorldGenStrongholdPiece != null) {
/*  204 */       paramList.add(localWorldGenStrongholdPiece);
/*  205 */       paramWorldGenStrongholdStart.c.add(localWorldGenStrongholdPiece);
/*      */     }
/*  207 */     return localWorldGenStrongholdPiece;
/*      */   }
/*      */   
/*      */   static abstract class WorldGenStrongholdPiece extends StructurePiece {
/*  211 */     protected WorldGenStrongholdDoorType d = WorldGenStrongholdDoorType.OPENING;
/*      */     
/*      */ 
/*      */     public WorldGenStrongholdPiece() {}
/*      */     
/*      */     protected WorldGenStrongholdPiece(int paramInt)
/*      */     {
/*  218 */       super();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  227 */       paramNBTTagCompound.setString("EntryDoor", this.d.name());
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  232 */       this.d = WorldGenStrongholdDoorType.valueOf(paramNBTTagCompound.getString("EntryDoor"));
/*      */     }
/*      */     
/*      */     protected void a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, WorldGenStrongholdDoorType paramWorldGenStrongholdDoorType, int paramInt1, int paramInt2, int paramInt3) {
/*  236 */       switch (WorldGenStrongholdPieces.3.a[paramWorldGenStrongholdDoorType.ordinal()]) {
/*      */       case 1: 
/*      */       default: 
/*  239 */         a(paramWorld, paramStructureBoundingBox, paramInt1, paramInt2, paramInt3, paramInt1 + 3 - 1, paramInt2 + 3 - 1, paramInt3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  240 */         break;
/*      */       case 2: 
/*  242 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
/*  243 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/*  244 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/*  245 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1 + 1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/*  246 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1 + 2, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/*  247 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1 + 2, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/*  248 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1 + 2, paramInt2, paramInt3, paramStructureBoundingBox);
/*  249 */         a(paramWorld, Blocks.WOODEN_DOOR.getBlockData(), paramInt1 + 1, paramInt2, paramInt3, paramStructureBoundingBox);
/*  250 */         a(paramWorld, Blocks.WOODEN_DOOR.fromLegacyData(8), paramInt1 + 1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/*  251 */         break;
/*      */       case 3: 
/*  253 */         a(paramWorld, Blocks.AIR.getBlockData(), paramInt1 + 1, paramInt2, paramInt3, paramStructureBoundingBox);
/*  254 */         a(paramWorld, Blocks.AIR.getBlockData(), paramInt1 + 1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/*  255 */         a(paramWorld, Blocks.IRON_BARS.getBlockData(), paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
/*  256 */         a(paramWorld, Blocks.IRON_BARS.getBlockData(), paramInt1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/*  257 */         a(paramWorld, Blocks.IRON_BARS.getBlockData(), paramInt1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/*  258 */         a(paramWorld, Blocks.IRON_BARS.getBlockData(), paramInt1 + 1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/*  259 */         a(paramWorld, Blocks.IRON_BARS.getBlockData(), paramInt1 + 2, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/*  260 */         a(paramWorld, Blocks.IRON_BARS.getBlockData(), paramInt1 + 2, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/*  261 */         a(paramWorld, Blocks.IRON_BARS.getBlockData(), paramInt1 + 2, paramInt2, paramInt3, paramStructureBoundingBox);
/*  262 */         break;
/*      */       case 4: 
/*  264 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
/*  265 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/*  266 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/*  267 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1 + 1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/*  268 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1 + 2, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/*  269 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1 + 2, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/*  270 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), paramInt1 + 2, paramInt2, paramInt3, paramStructureBoundingBox);
/*  271 */         a(paramWorld, Blocks.IRON_DOOR.getBlockData(), paramInt1 + 1, paramInt2, paramInt3, paramStructureBoundingBox);
/*  272 */         a(paramWorld, Blocks.IRON_DOOR.fromLegacyData(8), paramInt1 + 1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/*  273 */         a(paramWorld, Blocks.STONE_BUTTON.fromLegacyData(a(Blocks.STONE_BUTTON, 4)), paramInt1 + 2, paramInt2 + 1, paramInt3 + 1, paramStructureBoundingBox);
/*  274 */         a(paramWorld, Blocks.STONE_BUTTON.fromLegacyData(a(Blocks.STONE_BUTTON, 3)), paramInt1 + 2, paramInt2 + 1, paramInt3 - 1, paramStructureBoundingBox);
/*      */       }
/*      */     }
/*      */     
/*      */     protected WorldGenStrongholdDoorType a(Random paramRandom)
/*      */     {
/*  280 */       int i = paramRandom.nextInt(5);
/*  281 */       switch (i) {
/*      */       case 0: 
/*      */       case 1: 
/*      */       default: 
/*  285 */         return WorldGenStrongholdDoorType.OPENING;
/*      */       case 2: 
/*  287 */         return WorldGenStrongholdDoorType.WOOD_DOOR;
/*      */       case 3: 
/*  289 */         return WorldGenStrongholdDoorType.GRATES;
/*      */       }
/*  291 */       return WorldGenStrongholdDoorType.IRON_DOOR;
/*      */     }
/*      */     
/*      */     protected StructurePiece a(WorldGenStrongholdPieces.WorldGenStrongholdStart paramWorldGenStrongholdStart, List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2)
/*      */     {
/*  296 */       if (this.m != null) {
/*  297 */         switch (WorldGenStrongholdPieces.3.b[this.m.ordinal()]) {
/*      */         case 1: 
/*  299 */           return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.l.a + paramInt1, this.l.b + paramInt2, this.l.c - 1, this.m, d());
/*      */         case 2: 
/*  301 */           return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.l.a + paramInt1, this.l.b + paramInt2, this.l.f + 1, this.m, d());
/*      */         case 3: 
/*  303 */           return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.l.a - 1, this.l.b + paramInt2, this.l.c + paramInt1, this.m, d());
/*      */         case 4: 
/*  305 */           return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.l.d + 1, this.l.b + paramInt2, this.l.c + paramInt1, this.m, d());
/*      */         }
/*      */       }
/*  308 */       return null;
/*      */     }
/*      */     
/*      */     protected StructurePiece b(WorldGenStrongholdPieces.WorldGenStrongholdStart paramWorldGenStrongholdStart, List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2) {
/*  312 */       if (this.m != null) {
/*  313 */         switch (WorldGenStrongholdPieces.3.b[this.m.ordinal()]) {
/*      */         case 1: 
/*  315 */           return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.l.a - 1, this.l.b + paramInt1, this.l.c + paramInt2, EnumDirection.WEST, d());
/*      */         case 2: 
/*  317 */           return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.l.a - 1, this.l.b + paramInt1, this.l.c + paramInt2, EnumDirection.WEST, d());
/*      */         case 3: 
/*  319 */           return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.l.a + paramInt2, this.l.b + paramInt1, this.l.c - 1, EnumDirection.NORTH, d());
/*      */         case 4: 
/*  321 */           return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.l.a + paramInt2, this.l.b + paramInt1, this.l.c - 1, EnumDirection.NORTH, d());
/*      */         }
/*      */       }
/*  324 */       return null;
/*      */     }
/*      */     
/*      */     protected StructurePiece c(WorldGenStrongholdPieces.WorldGenStrongholdStart paramWorldGenStrongholdStart, List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2) {
/*  328 */       if (this.m != null) {
/*  329 */         switch (WorldGenStrongholdPieces.3.b[this.m.ordinal()]) {
/*      */         case 1: 
/*  331 */           return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.l.d + 1, this.l.b + paramInt1, this.l.c + paramInt2, EnumDirection.EAST, d());
/*      */         case 2: 
/*  333 */           return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.l.d + 1, this.l.b + paramInt1, this.l.c + paramInt2, EnumDirection.EAST, d());
/*      */         case 3: 
/*  335 */           return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.l.a + paramInt2, this.l.b + paramInt1, this.l.f + 1, EnumDirection.SOUTH, d());
/*      */         case 4: 
/*  337 */           return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.l.a + paramInt2, this.l.b + paramInt1, this.l.f + 1, EnumDirection.SOUTH, d());
/*      */         }
/*      */       }
/*  340 */       return null;
/*      */     }
/*      */     
/*      */     protected static boolean a(StructureBoundingBox paramStructureBoundingBox) {
/*  344 */       return (paramStructureBoundingBox != null) && (paramStructureBoundingBox.b > 10);
/*      */     }
/*      */     
/*      */     public static enum WorldGenStrongholdDoorType
/*      */     {
/*      */       private WorldGenStrongholdDoorType() {}
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenStrongholdCorridor extends WorldGenStrongholdPieces.WorldGenStrongholdPiece {
/*      */     private int a;
/*      */     
/*      */     public WorldGenStrongholdCorridor() {}
/*      */     
/*      */     public WorldGenStrongholdCorridor(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection) {
/*  359 */       super();
/*      */       
/*  361 */       this.m = paramEnumDirection;
/*  362 */       this.l = paramStructureBoundingBox;
/*  363 */       this.a = ((paramEnumDirection == EnumDirection.NORTH) || (paramEnumDirection == EnumDirection.SOUTH) ? paramStructureBoundingBox.e() : paramStructureBoundingBox.c());
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  368 */       super.a(paramNBTTagCompound);
/*  369 */       paramNBTTagCompound.setInt("Steps", this.a);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  374 */       super.b(paramNBTTagCompound);
/*  375 */       this.a = paramNBTTagCompound.getInt("Steps");
/*      */     }
/*      */     
/*      */     public static StructureBoundingBox a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection) {
/*  379 */       int i = 3;
/*      */       
/*  381 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, 4, paramEnumDirection);
/*      */       
/*  383 */       StructurePiece localStructurePiece = StructurePiece.a(paramList, localStructureBoundingBox);
/*  384 */       if (localStructurePiece == null)
/*      */       {
/*  386 */         return null;
/*      */       }
/*      */       
/*  389 */       if (localStructurePiece.c().b == localStructureBoundingBox.b)
/*      */       {
/*  391 */         for (int j = 3; j >= 1; j--) {
/*  392 */           localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, j - 1, paramEnumDirection);
/*  393 */           if (!localStructurePiece.c().a(localStructureBoundingBox))
/*      */           {
/*      */ 
/*  396 */             return StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, j, paramEnumDirection);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  401 */       return null;
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  406 */       if (a(paramWorld, paramStructureBoundingBox)) {
/*  407 */         return false;
/*      */       }
/*      */       
/*      */ 
/*  411 */       for (int i = 0; i < this.a; i++)
/*      */       {
/*  413 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 0, 0, i, paramStructureBoundingBox);
/*  414 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 1, 0, i, paramStructureBoundingBox);
/*  415 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 2, 0, i, paramStructureBoundingBox);
/*  416 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 3, 0, i, paramStructureBoundingBox);
/*  417 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 4, 0, i, paramStructureBoundingBox);
/*      */         
/*  419 */         for (int j = 1; j <= 3; j++) {
/*  420 */           a(paramWorld, Blocks.STONEBRICK.getBlockData(), 0, j, i, paramStructureBoundingBox);
/*  421 */           a(paramWorld, Blocks.AIR.getBlockData(), 1, j, i, paramStructureBoundingBox);
/*  422 */           a(paramWorld, Blocks.AIR.getBlockData(), 2, j, i, paramStructureBoundingBox);
/*  423 */           a(paramWorld, Blocks.AIR.getBlockData(), 3, j, i, paramStructureBoundingBox);
/*  424 */           a(paramWorld, Blocks.STONEBRICK.getBlockData(), 4, j, i, paramStructureBoundingBox);
/*      */         }
/*      */         
/*  427 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 0, 4, i, paramStructureBoundingBox);
/*  428 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 1, 4, i, paramStructureBoundingBox);
/*  429 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 2, 4, i, paramStructureBoundingBox);
/*  430 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 3, 4, i, paramStructureBoundingBox);
/*  431 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 4, 4, i, paramStructureBoundingBox);
/*      */       }
/*      */       
/*  434 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static class WorldGenStrongholdStairs2
/*      */     extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
/*      */   {
/*      */     private boolean a;
/*      */     
/*      */ 
/*      */     public WorldGenStrongholdStairs2() {}
/*      */     
/*      */ 
/*      */     public WorldGenStrongholdStairs2(int paramInt1, Random paramRandom, int paramInt2, int paramInt3)
/*      */     {
/*  450 */       super();
/*      */       
/*  452 */       this.a = true;
/*  453 */       this.m = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(paramRandom);
/*  454 */       this.d = WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.OPENING;
/*      */       
/*  456 */       switch (WorldGenStrongholdPieces.3.b[this.m.ordinal()]) {
/*      */       case 1: 
/*      */       case 2: 
/*  459 */         this.l = new StructureBoundingBox(paramInt2, 64, paramInt3, paramInt2 + 5 - 1, 74, paramInt3 + 5 - 1);
/*  460 */         break;
/*      */       default: 
/*  462 */         this.l = new StructureBoundingBox(paramInt2, 64, paramInt3, paramInt2 + 5 - 1, 74, paramInt3 + 5 - 1);
/*      */       }
/*      */     }
/*      */     
/*      */     public WorldGenStrongholdStairs2(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  468 */       super();
/*      */       
/*  470 */       this.a = false;
/*  471 */       this.m = paramEnumDirection;
/*  472 */       this.d = a(paramRandom);
/*  473 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  478 */       super.a(paramNBTTagCompound);
/*  479 */       paramNBTTagCompound.setBoolean("Source", this.a);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  484 */       super.b(paramNBTTagCompound);
/*  485 */       this.a = paramNBTTagCompound.getBoolean("Source");
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  490 */       if (this.a)
/*      */       {
/*  492 */         WorldGenStrongholdPieces.a(WorldGenStrongholdPieces.WorldGenStrongholdCrossing.class);
/*      */       }
/*  494 */       a((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
/*      */     }
/*      */     
/*      */     public static WorldGenStrongholdStairs2 a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  498 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -7, 0, 5, 11, 5, paramEnumDirection);
/*      */       
/*  500 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  501 */         return null;
/*      */       }
/*      */       
/*  504 */       return new WorldGenStrongholdStairs2(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  509 */       if (a(paramWorld, paramStructureBoundingBox)) {
/*  510 */         return false;
/*      */       }
/*      */       
/*      */ 
/*  514 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 10, 4, true, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/*  516 */       a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 1, 7, 0);
/*      */       
/*  518 */       a(paramWorld, paramRandom, paramStructureBoundingBox, WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.OPENING, 1, 1, 4);
/*      */       
/*      */ 
/*  521 */       a(paramWorld, Blocks.STONEBRICK.getBlockData(), 2, 6, 1, paramStructureBoundingBox);
/*  522 */       a(paramWorld, Blocks.STONEBRICK.getBlockData(), 1, 5, 1, paramStructureBoundingBox);
/*  523 */       a(paramWorld, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), 1, 6, 1, paramStructureBoundingBox);
/*  524 */       a(paramWorld, Blocks.STONEBRICK.getBlockData(), 1, 5, 2, paramStructureBoundingBox);
/*  525 */       a(paramWorld, Blocks.STONEBRICK.getBlockData(), 1, 4, 3, paramStructureBoundingBox);
/*  526 */       a(paramWorld, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), 1, 5, 3, paramStructureBoundingBox);
/*  527 */       a(paramWorld, Blocks.STONEBRICK.getBlockData(), 2, 4, 3, paramStructureBoundingBox);
/*  528 */       a(paramWorld, Blocks.STONEBRICK.getBlockData(), 3, 3, 3, paramStructureBoundingBox);
/*  529 */       a(paramWorld, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), 3, 4, 3, paramStructureBoundingBox);
/*  530 */       a(paramWorld, Blocks.STONEBRICK.getBlockData(), 3, 3, 2, paramStructureBoundingBox);
/*  531 */       a(paramWorld, Blocks.STONEBRICK.getBlockData(), 3, 2, 1, paramStructureBoundingBox);
/*  532 */       a(paramWorld, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), 3, 3, 1, paramStructureBoundingBox);
/*  533 */       a(paramWorld, Blocks.STONEBRICK.getBlockData(), 2, 2, 1, paramStructureBoundingBox);
/*  534 */       a(paramWorld, Blocks.STONEBRICK.getBlockData(), 1, 1, 1, paramStructureBoundingBox);
/*  535 */       a(paramWorld, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), 1, 2, 1, paramStructureBoundingBox);
/*  536 */       a(paramWorld, Blocks.STONEBRICK.getBlockData(), 1, 1, 2, paramStructureBoundingBox);
/*  537 */       a(paramWorld, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), 1, 1, 3, paramStructureBoundingBox);
/*      */       
/*  539 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static class WorldGenStrongholdStart
/*      */     extends WorldGenStrongholdPieces.WorldGenStrongholdStairs2
/*      */   {
/*      */     public WorldGenStrongholdPieces.WorldGenStrongholdPieceWeight a;
/*      */     public WorldGenStrongholdPieces.WorldGenStrongholdPortalRoom b;
/*  549 */     public List<StructurePiece> c = Lists.newArrayList();
/*      */     
/*      */ 
/*      */     public WorldGenStrongholdStart() {}
/*      */     
/*      */     public WorldGenStrongholdStart(int paramInt1, Random paramRandom, int paramInt2, int paramInt3)
/*      */     {
/*  556 */       super(paramRandom, paramInt2, paramInt3);
/*      */     }
/*      */     
/*      */     public BlockPosition a()
/*      */     {
/*  561 */       if (this.b != null) {
/*  562 */         return this.b.a();
/*      */       }
/*  564 */       return super.a();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static class WorldGenStrongholdStairs
/*      */     extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
/*      */   {
/*      */     private boolean a;
/*      */     
/*      */     private boolean b;
/*      */     
/*      */ 
/*      */     public WorldGenStrongholdStairs() {}
/*      */     
/*      */     public WorldGenStrongholdStairs(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  581 */       super();
/*      */       
/*  583 */       this.m = paramEnumDirection;
/*  584 */       this.d = a(paramRandom);
/*  585 */       this.l = paramStructureBoundingBox;
/*      */       
/*  587 */       this.a = (paramRandom.nextInt(2) == 0);
/*  588 */       this.b = (paramRandom.nextInt(2) == 0);
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  593 */       super.a(paramNBTTagCompound);
/*  594 */       paramNBTTagCompound.setBoolean("Left", this.a);
/*  595 */       paramNBTTagCompound.setBoolean("Right", this.b);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  600 */       super.b(paramNBTTagCompound);
/*  601 */       this.a = paramNBTTagCompound.getBoolean("Left");
/*  602 */       this.b = paramNBTTagCompound.getBoolean("Right");
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  607 */       a((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
/*  608 */       if (this.a) {
/*  609 */         b((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 2);
/*      */       }
/*  611 */       if (this.b) {
/*  612 */         c((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 2);
/*      */       }
/*      */     }
/*      */     
/*      */     public static WorldGenStrongholdStairs a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  617 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, 7, paramEnumDirection);
/*      */       
/*  619 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  620 */         return null;
/*      */       }
/*      */       
/*  623 */       return new WorldGenStrongholdStairs(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  628 */       if (a(paramWorld, paramStructureBoundingBox)) {
/*  629 */         return false;
/*      */       }
/*      */       
/*      */ 
/*  633 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 4, 6, true, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/*  635 */       a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 1, 1, 0);
/*      */       
/*  637 */       a(paramWorld, paramRandom, paramStructureBoundingBox, WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.OPENING, 1, 1, 6);
/*      */       
/*  639 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 1, 2, 1, Blocks.TORCH.getBlockData());
/*  640 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 3, 2, 1, Blocks.TORCH.getBlockData());
/*  641 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 1, 2, 5, Blocks.TORCH.getBlockData());
/*  642 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 3, 2, 5, Blocks.TORCH.getBlockData());
/*      */       
/*  644 */       if (this.a) {
/*  645 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 2, 0, 3, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       }
/*  647 */       if (this.b) {
/*  648 */         a(paramWorld, paramStructureBoundingBox, 4, 1, 2, 4, 3, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       }
/*      */       
/*  651 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenStrongholdChestCorridor extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
/*      */   {
/*  657 */     private static final List<StructurePieceTreasure> a = Lists.newArrayList(new StructurePieceTreasure[] { new StructurePieceTreasure(Items.ENDER_PEARL, 0, 1, 1, 10), new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 5), new StructurePieceTreasure(Items.REDSTONE, 0, 4, 9, 5), new StructurePieceTreasure(Items.BREAD, 0, 1, 3, 15), new StructurePieceTreasure(Items.APPLE, 0, 1, 3, 15), new StructurePieceTreasure(Items.IRON_PICKAXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_SWORD, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_CHESTPLATE, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_HELMET, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_LEGGINGS, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_BOOTS, 0, 1, 1, 5), new StructurePieceTreasure(Items.GOLDEN_APPLE, 0, 1, 1, 1), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 1), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 1) });
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private boolean b;
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
/*      */     public WorldGenStrongholdChestCorridor() {}
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
/*      */     public WorldGenStrongholdChestCorridor(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  691 */       super();
/*      */       
/*  693 */       this.m = paramEnumDirection;
/*  694 */       this.d = a(paramRandom);
/*  695 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  700 */       super.a(paramNBTTagCompound);
/*  701 */       paramNBTTagCompound.setBoolean("Chest", this.b);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  706 */       super.b(paramNBTTagCompound);
/*  707 */       this.b = paramNBTTagCompound.getBoolean("Chest");
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  712 */       a((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
/*      */     }
/*      */     
/*      */     public static WorldGenStrongholdChestCorridor a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  716 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, 7, paramEnumDirection);
/*      */       
/*  718 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  719 */         return null;
/*      */       }
/*      */       
/*  722 */       return new WorldGenStrongholdChestCorridor(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  727 */       if (a(paramWorld, paramStructureBoundingBox)) {
/*  728 */         return false;
/*      */       }
/*      */       
/*      */ 
/*  732 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 4, 6, true, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/*  734 */       a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 1, 1, 0);
/*      */       
/*  736 */       a(paramWorld, paramRandom, paramStructureBoundingBox, WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.OPENING, 1, 1, 6);
/*      */       
/*      */ 
/*  739 */       a(paramWorld, paramStructureBoundingBox, 3, 1, 2, 3, 1, 4, Blocks.STONEBRICK.getBlockData(), Blocks.STONEBRICK.getBlockData(), false);
/*  740 */       a(paramWorld, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a()), 3, 1, 1, paramStructureBoundingBox);
/*  741 */       a(paramWorld, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a()), 3, 1, 5, paramStructureBoundingBox);
/*  742 */       a(paramWorld, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a()), 3, 2, 2, paramStructureBoundingBox);
/*  743 */       a(paramWorld, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a()), 3, 2, 4, paramStructureBoundingBox);
/*  744 */       for (int i = 2; i <= 4; i++) {
/*  745 */         a(paramWorld, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a()), 2, 1, i, paramStructureBoundingBox);
/*      */       }
/*      */       
/*  748 */       if ((!this.b) && 
/*  749 */         (paramStructureBoundingBox.b(new BlockPosition(a(3, 3), d(2), b(3, 3))))) {
/*  750 */         this.b = true;
/*  751 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 3, 2, 3, StructurePieceTreasure.a(a, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 2 + paramRandom.nextInt(2));
/*      */       }
/*      */       
/*      */ 
/*  755 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenStrongholdStairsStraight
/*      */     extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
/*      */   {
/*      */     public WorldGenStrongholdStairsStraight() {}
/*      */     
/*      */ 
/*      */     public WorldGenStrongholdStairsStraight(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  769 */       super();
/*      */       
/*  771 */       this.m = paramEnumDirection;
/*  772 */       this.d = a(paramRandom);
/*  773 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  778 */       a((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
/*      */     }
/*      */     
/*      */     public static WorldGenStrongholdStairsStraight a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  782 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -7, 0, 5, 11, 8, paramEnumDirection);
/*      */       
/*  784 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  785 */         return null;
/*      */       }
/*      */       
/*  788 */       return new WorldGenStrongholdStairsStraight(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  793 */       if (a(paramWorld, paramStructureBoundingBox)) {
/*  794 */         return false;
/*      */       }
/*      */       
/*      */ 
/*  798 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 10, 7, true, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/*  800 */       a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 1, 7, 0);
/*      */       
/*  802 */       a(paramWorld, paramRandom, paramStructureBoundingBox, WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.OPENING, 1, 1, 7);
/*      */       
/*      */ 
/*  805 */       int i = a(Blocks.STONE_STAIRS, 2);
/*  806 */       for (int j = 0; j < 6; j++) {
/*  807 */         a(paramWorld, Blocks.STONE_STAIRS.fromLegacyData(i), 1, 6 - j, 1 + j, paramStructureBoundingBox);
/*  808 */         a(paramWorld, Blocks.STONE_STAIRS.fromLegacyData(i), 2, 6 - j, 1 + j, paramStructureBoundingBox);
/*  809 */         a(paramWorld, Blocks.STONE_STAIRS.fromLegacyData(i), 3, 6 - j, 1 + j, paramStructureBoundingBox);
/*  810 */         if (j < 5) {
/*  811 */           a(paramWorld, Blocks.STONEBRICK.getBlockData(), 1, 5 - j, 1 + j, paramStructureBoundingBox);
/*  812 */           a(paramWorld, Blocks.STONEBRICK.getBlockData(), 2, 5 - j, 1 + j, paramStructureBoundingBox);
/*  813 */           a(paramWorld, Blocks.STONEBRICK.getBlockData(), 3, 5 - j, 1 + j, paramStructureBoundingBox);
/*      */         }
/*      */       }
/*      */       
/*  817 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenStrongholdLeftTurn
/*      */     extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
/*      */   {
/*      */     public WorldGenStrongholdLeftTurn() {}
/*      */     
/*      */ 
/*      */     public WorldGenStrongholdLeftTurn(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  831 */       super();
/*      */       
/*  833 */       this.m = paramEnumDirection;
/*  834 */       this.d = a(paramRandom);
/*  835 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  840 */       if ((this.m == EnumDirection.NORTH) || (this.m == EnumDirection.EAST)) {
/*  841 */         b((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
/*      */       } else {
/*  843 */         c((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
/*      */       }
/*      */     }
/*      */     
/*      */     public static WorldGenStrongholdLeftTurn a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  848 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, 5, paramEnumDirection);
/*      */       
/*  850 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  851 */         return null;
/*      */       }
/*      */       
/*  854 */       return new WorldGenStrongholdLeftTurn(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  859 */       if (a(paramWorld, paramStructureBoundingBox)) {
/*  860 */         return false;
/*      */       }
/*      */       
/*      */ 
/*  864 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 4, 4, true, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/*  866 */       a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 1, 1, 0);
/*      */       
/*  868 */       if ((this.m == EnumDirection.NORTH) || (this.m == EnumDirection.EAST)) {
/*  869 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       } else {
/*  871 */         a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       }
/*      */       
/*  874 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class WorldGenStrongholdRightTurn
/*      */     extends WorldGenStrongholdPieces.WorldGenStrongholdLeftTurn
/*      */   {
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  889 */       if ((this.m == EnumDirection.NORTH) || (this.m == EnumDirection.EAST)) {
/*  890 */         c((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
/*      */       } else {
/*  892 */         b((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  898 */       if (a(paramWorld, paramStructureBoundingBox)) {
/*  899 */         return false;
/*      */       }
/*      */       
/*      */ 
/*  903 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 4, 4, true, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/*  905 */       a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 1, 1, 0);
/*      */       
/*  907 */       if ((this.m == EnumDirection.NORTH) || (this.m == EnumDirection.EAST)) {
/*  908 */         a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       } else {
/*  910 */         a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       }
/*      */       
/*  913 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenStrongholdRoomCrossing extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
/*      */   {
/*  919 */     private static final List<StructurePieceTreasure> b = Lists.newArrayList(new StructurePieceTreasure[] { new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 5), new StructurePieceTreasure(Items.REDSTONE, 0, 4, 9, 5), new StructurePieceTreasure(Items.COAL, 0, 3, 8, 10), new StructurePieceTreasure(Items.BREAD, 0, 1, 3, 15), new StructurePieceTreasure(Items.APPLE, 0, 1, 3, 15), new StructurePieceTreasure(Items.IRON_PICKAXE, 0, 1, 1, 1) });
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected int a;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public WorldGenStrongholdRoomCrossing() {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public WorldGenStrongholdRoomCrossing(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/*  941 */       super();
/*      */       
/*  943 */       this.m = paramEnumDirection;
/*  944 */       this.d = a(paramRandom);
/*  945 */       this.l = paramStructureBoundingBox;
/*  946 */       this.a = paramRandom.nextInt(5);
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  951 */       super.a(paramNBTTagCompound);
/*  952 */       paramNBTTagCompound.setInt("Type", this.a);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/*  957 */       super.b(paramNBTTagCompound);
/*  958 */       this.a = paramNBTTagCompound.getInt("Type");
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/*  963 */       a((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 4, 1);
/*  964 */       b((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 4);
/*  965 */       c((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 4);
/*      */     }
/*      */     
/*      */     public static WorldGenStrongholdRoomCrossing a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  969 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -4, -1, 0, 11, 7, 11, paramEnumDirection);
/*      */       
/*  971 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/*  972 */         return null;
/*      */       }
/*      */       
/*  975 */       return new WorldGenStrongholdRoomCrossing(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/*  980 */       if (a(paramWorld, paramStructureBoundingBox)) {
/*  981 */         return false;
/*      */       }
/*      */       
/*      */ 
/*  985 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 10, 6, 10, true, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/*  987 */       a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 4, 1, 0);
/*      */       
/*  989 */       a(paramWorld, paramStructureBoundingBox, 4, 1, 10, 6, 3, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  990 */       a(paramWorld, paramStructureBoundingBox, 0, 1, 4, 0, 3, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*  991 */       a(paramWorld, paramStructureBoundingBox, 10, 1, 4, 10, 3, 6, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       int i;
/*  993 */       switch (this.a)
/*      */       {
/*      */       default: 
/*      */         break;
/*      */       case 0: 
/*  998 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 5, 1, 5, paramStructureBoundingBox);
/*  999 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 5, 2, 5, paramStructureBoundingBox);
/* 1000 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 5, 3, 5, paramStructureBoundingBox);
/* 1001 */         a(paramWorld, Blocks.TORCH.getBlockData(), 4, 3, 5, paramStructureBoundingBox);
/* 1002 */         a(paramWorld, Blocks.TORCH.getBlockData(), 6, 3, 5, paramStructureBoundingBox);
/* 1003 */         a(paramWorld, Blocks.TORCH.getBlockData(), 5, 3, 4, paramStructureBoundingBox);
/* 1004 */         a(paramWorld, Blocks.TORCH.getBlockData(), 5, 3, 6, paramStructureBoundingBox);
/* 1005 */         a(paramWorld, Blocks.STONE_SLAB.getBlockData(), 4, 1, 4, paramStructureBoundingBox);
/* 1006 */         a(paramWorld, Blocks.STONE_SLAB.getBlockData(), 4, 1, 5, paramStructureBoundingBox);
/* 1007 */         a(paramWorld, Blocks.STONE_SLAB.getBlockData(), 4, 1, 6, paramStructureBoundingBox);
/* 1008 */         a(paramWorld, Blocks.STONE_SLAB.getBlockData(), 6, 1, 4, paramStructureBoundingBox);
/* 1009 */         a(paramWorld, Blocks.STONE_SLAB.getBlockData(), 6, 1, 5, paramStructureBoundingBox);
/* 1010 */         a(paramWorld, Blocks.STONE_SLAB.getBlockData(), 6, 1, 6, paramStructureBoundingBox);
/* 1011 */         a(paramWorld, Blocks.STONE_SLAB.getBlockData(), 5, 1, 4, paramStructureBoundingBox);
/* 1012 */         a(paramWorld, Blocks.STONE_SLAB.getBlockData(), 5, 1, 6, paramStructureBoundingBox);
/* 1013 */         break;
/*      */       case 1: 
/* 1015 */         for (i = 0; i < 5; i++) {
/* 1016 */           a(paramWorld, Blocks.STONEBRICK.getBlockData(), 3, 1, 3 + i, paramStructureBoundingBox);
/* 1017 */           a(paramWorld, Blocks.STONEBRICK.getBlockData(), 7, 1, 3 + i, paramStructureBoundingBox);
/* 1018 */           a(paramWorld, Blocks.STONEBRICK.getBlockData(), 3 + i, 1, 3, paramStructureBoundingBox);
/* 1019 */           a(paramWorld, Blocks.STONEBRICK.getBlockData(), 3 + i, 1, 7, paramStructureBoundingBox);
/*      */         }
/* 1021 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 5, 1, 5, paramStructureBoundingBox);
/* 1022 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 5, 2, 5, paramStructureBoundingBox);
/* 1023 */         a(paramWorld, Blocks.STONEBRICK.getBlockData(), 5, 3, 5, paramStructureBoundingBox);
/* 1024 */         a(paramWorld, Blocks.FLOWING_WATER.getBlockData(), 5, 4, 5, paramStructureBoundingBox);
/* 1025 */         break;
/*      */       case 2: 
/* 1027 */         for (i = 1; i <= 9; i++) {
/* 1028 */           a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 1, 3, i, paramStructureBoundingBox);
/* 1029 */           a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 9, 3, i, paramStructureBoundingBox);
/*      */         }
/* 1031 */         for (i = 1; i <= 9; i++) {
/* 1032 */           a(paramWorld, Blocks.COBBLESTONE.getBlockData(), i, 3, 1, paramStructureBoundingBox);
/* 1033 */           a(paramWorld, Blocks.COBBLESTONE.getBlockData(), i, 3, 9, paramStructureBoundingBox);
/*      */         }
/* 1035 */         a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 5, 1, 4, paramStructureBoundingBox);
/* 1036 */         a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 5, 1, 6, paramStructureBoundingBox);
/* 1037 */         a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 5, 3, 4, paramStructureBoundingBox);
/* 1038 */         a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 5, 3, 6, paramStructureBoundingBox);
/* 1039 */         a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 4, 1, 5, paramStructureBoundingBox);
/* 1040 */         a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 6, 1, 5, paramStructureBoundingBox);
/* 1041 */         a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 4, 3, 5, paramStructureBoundingBox);
/* 1042 */         a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 6, 3, 5, paramStructureBoundingBox);
/* 1043 */         for (i = 1; i <= 3; i++) {
/* 1044 */           a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 4, i, 4, paramStructureBoundingBox);
/* 1045 */           a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 6, i, 4, paramStructureBoundingBox);
/* 1046 */           a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 4, i, 6, paramStructureBoundingBox);
/* 1047 */           a(paramWorld, Blocks.COBBLESTONE.getBlockData(), 6, i, 6, paramStructureBoundingBox);
/*      */         }
/* 1049 */         a(paramWorld, Blocks.TORCH.getBlockData(), 5, 3, 5, paramStructureBoundingBox);
/* 1050 */         for (i = 2; i <= 8; i++) {
/* 1051 */           a(paramWorld, Blocks.PLANKS.getBlockData(), 2, 3, i, paramStructureBoundingBox);
/* 1052 */           a(paramWorld, Blocks.PLANKS.getBlockData(), 3, 3, i, paramStructureBoundingBox);
/* 1053 */           if ((i <= 3) || (i >= 7)) {
/* 1054 */             a(paramWorld, Blocks.PLANKS.getBlockData(), 4, 3, i, paramStructureBoundingBox);
/* 1055 */             a(paramWorld, Blocks.PLANKS.getBlockData(), 5, 3, i, paramStructureBoundingBox);
/* 1056 */             a(paramWorld, Blocks.PLANKS.getBlockData(), 6, 3, i, paramStructureBoundingBox);
/*      */           }
/* 1058 */           a(paramWorld, Blocks.PLANKS.getBlockData(), 7, 3, i, paramStructureBoundingBox);
/* 1059 */           a(paramWorld, Blocks.PLANKS.getBlockData(), 8, 3, i, paramStructureBoundingBox);
/*      */         }
/* 1061 */         a(paramWorld, Blocks.LADDER.fromLegacyData(a(Blocks.LADDER, EnumDirection.WEST.a())), 9, 1, 3, paramStructureBoundingBox);
/* 1062 */         a(paramWorld, Blocks.LADDER.fromLegacyData(a(Blocks.LADDER, EnumDirection.WEST.a())), 9, 2, 3, paramStructureBoundingBox);
/* 1063 */         a(paramWorld, Blocks.LADDER.fromLegacyData(a(Blocks.LADDER, EnumDirection.WEST.a())), 9, 3, 3, paramStructureBoundingBox);
/*      */         
/* 1065 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 3, 4, 8, StructurePieceTreasure.a(b, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 1 + paramRandom.nextInt(4));
/*      */       }
/*      */       
/*      */       
/* 1069 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static class WorldGenStrongholdPrison
/*      */     extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
/*      */   {
/*      */     public WorldGenStrongholdPrison() {}
/*      */     
/*      */ 
/*      */     public WorldGenStrongholdPrison(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/* 1083 */       super();
/*      */       
/* 1085 */       this.m = paramEnumDirection;
/* 1086 */       this.d = a(paramRandom);
/* 1087 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/* 1092 */       a((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
/*      */     }
/*      */     
/*      */     public static WorldGenStrongholdPrison a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/* 1096 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 9, 5, 11, paramEnumDirection);
/*      */       
/* 1098 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/* 1099 */         return null;
/*      */       }
/*      */       
/* 1102 */       return new WorldGenStrongholdPrison(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1107 */       if (a(paramWorld, paramStructureBoundingBox)) {
/* 1108 */         return false;
/*      */       }
/*      */       
/*      */ 
/* 1112 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 8, 4, 10, true, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/* 1114 */       a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 1, 1, 0);
/*      */       
/* 1116 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 10, 3, 3, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/* 1119 */       a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 4, 3, 1, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1120 */       a(paramWorld, paramStructureBoundingBox, 4, 1, 3, 4, 3, 3, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1121 */       a(paramWorld, paramStructureBoundingBox, 4, 1, 7, 4, 3, 7, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1122 */       a(paramWorld, paramStructureBoundingBox, 4, 1, 9, 4, 3, 9, false, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/*      */ 
/* 1125 */       a(paramWorld, paramStructureBoundingBox, 4, 1, 4, 4, 3, 6, Blocks.IRON_BARS.getBlockData(), Blocks.IRON_BARS.getBlockData(), false);
/* 1126 */       a(paramWorld, paramStructureBoundingBox, 5, 1, 5, 7, 3, 5, Blocks.IRON_BARS.getBlockData(), Blocks.IRON_BARS.getBlockData(), false);
/*      */       
/*      */ 
/* 1129 */       a(paramWorld, Blocks.IRON_BARS.getBlockData(), 4, 3, 2, paramStructureBoundingBox);
/* 1130 */       a(paramWorld, Blocks.IRON_BARS.getBlockData(), 4, 3, 8, paramStructureBoundingBox);
/* 1131 */       a(paramWorld, Blocks.IRON_DOOR.fromLegacyData(a(Blocks.IRON_DOOR, 3)), 4, 1, 2, paramStructureBoundingBox);
/* 1132 */       a(paramWorld, Blocks.IRON_DOOR.fromLegacyData(a(Blocks.IRON_DOOR, 3) + 8), 4, 2, 2, paramStructureBoundingBox);
/* 1133 */       a(paramWorld, Blocks.IRON_DOOR.fromLegacyData(a(Blocks.IRON_DOOR, 3)), 4, 1, 8, paramStructureBoundingBox);
/* 1134 */       a(paramWorld, Blocks.IRON_DOOR.fromLegacyData(a(Blocks.IRON_DOOR, 3) + 8), 4, 2, 8, paramStructureBoundingBox);
/*      */       
/* 1136 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenStrongholdLibrary extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
/*      */   {
/* 1142 */     private static final List<StructurePieceTreasure> a = Lists.newArrayList(new StructurePieceTreasure[] { new StructurePieceTreasure(Items.BOOK, 0, 1, 3, 20), new StructurePieceTreasure(Items.PAPER, 0, 2, 7, 20), new StructurePieceTreasure(Items.MAP, 0, 1, 1, 1), new StructurePieceTreasure(Items.COMPASS, 0, 1, 1, 1) });
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private boolean b;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public WorldGenStrongholdLibrary() {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public WorldGenStrongholdLibrary(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/* 1162 */       super();
/*      */       
/* 1164 */       this.m = paramEnumDirection;
/* 1165 */       this.d = a(paramRandom);
/* 1166 */       this.l = paramStructureBoundingBox;
/* 1167 */       this.b = (paramStructureBoundingBox.d() > 6);
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/* 1172 */       super.a(paramNBTTagCompound);
/* 1173 */       paramNBTTagCompound.setBoolean("Tall", this.b);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/* 1178 */       super.b(paramNBTTagCompound);
/* 1179 */       this.b = paramNBTTagCompound.getBoolean("Tall");
/*      */     }
/*      */     
/*      */     public static WorldGenStrongholdLibrary a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4)
/*      */     {
/* 1184 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -4, -1, 0, 14, 11, 15, paramEnumDirection);
/*      */       
/* 1186 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null))
/*      */       {
/* 1188 */         localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -4, -1, 0, 14, 6, 15, paramEnumDirection);
/*      */         
/* 1190 */         if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/* 1191 */           return null;
/*      */         }
/*      */       }
/*      */       
/* 1195 */       return new WorldGenStrongholdLibrary(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1200 */       if (a(paramWorld, paramStructureBoundingBox)) {
/* 1201 */         return false;
/*      */       }
/*      */       
/* 1204 */       int i = 11;
/* 1205 */       if (!this.b) {
/* 1206 */         i = 6;
/*      */       }
/*      */       
/*      */ 
/* 1210 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 13, i - 1, 14, true, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/* 1212 */       a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 4, 1, 0);
/*      */       
/*      */ 
/* 1215 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.WEB.getBlockData(), Blocks.WEB.getBlockData(), false);
/*      */       
/* 1217 */       int j = 1;
/* 1218 */       int k = 12;
/*      */       
/*      */ 
/* 1221 */       for (int m = 1; m <= 13; m++) {
/* 1222 */         if ((m - 1) % 4 == 0) {
/* 1223 */           a(paramWorld, paramStructureBoundingBox, 1, 1, m, 1, 4, m, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/* 1224 */           a(paramWorld, paramStructureBoundingBox, 12, 1, m, 12, 4, m, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*      */           
/* 1226 */           a(paramWorld, Blocks.TORCH.getBlockData(), 2, 3, m, paramStructureBoundingBox);
/* 1227 */           a(paramWorld, Blocks.TORCH.getBlockData(), 11, 3, m, paramStructureBoundingBox);
/*      */           
/* 1229 */           if (this.b) {
/* 1230 */             a(paramWorld, paramStructureBoundingBox, 1, 6, m, 1, 9, m, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/* 1231 */             a(paramWorld, paramStructureBoundingBox, 12, 6, m, 12, 9, m, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*      */           }
/*      */         } else {
/* 1234 */           a(paramWorld, paramStructureBoundingBox, 1, 1, m, 1, 4, m, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
/* 1235 */           a(paramWorld, paramStructureBoundingBox, 12, 1, m, 12, 4, m, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
/*      */           
/* 1237 */           if (this.b) {
/* 1238 */             a(paramWorld, paramStructureBoundingBox, 1, 6, m, 1, 9, m, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
/* 1239 */             a(paramWorld, paramStructureBoundingBox, 12, 6, m, 12, 9, m, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1245 */       for (m = 3; m < 12; m += 2) {
/* 1246 */         a(paramWorld, paramStructureBoundingBox, 3, 1, m, 4, 3, m, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
/* 1247 */         a(paramWorld, paramStructureBoundingBox, 6, 1, m, 7, 3, m, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
/* 1248 */         a(paramWorld, paramStructureBoundingBox, 9, 1, m, 10, 3, m, Blocks.BOOKSHELF.getBlockData(), Blocks.BOOKSHELF.getBlockData(), false);
/*      */       }
/*      */       
/* 1251 */       if (this.b)
/*      */       {
/* 1253 */         a(paramWorld, paramStructureBoundingBox, 1, 5, 1, 3, 5, 13, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/* 1254 */         a(paramWorld, paramStructureBoundingBox, 10, 5, 1, 12, 5, 13, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/* 1255 */         a(paramWorld, paramStructureBoundingBox, 4, 5, 1, 9, 5, 2, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/* 1256 */         a(paramWorld, paramStructureBoundingBox, 4, 5, 12, 9, 5, 13, Blocks.PLANKS.getBlockData(), Blocks.PLANKS.getBlockData(), false);
/*      */         
/* 1258 */         a(paramWorld, Blocks.PLANKS.getBlockData(), 9, 5, 11, paramStructureBoundingBox);
/* 1259 */         a(paramWorld, Blocks.PLANKS.getBlockData(), 8, 5, 11, paramStructureBoundingBox);
/* 1260 */         a(paramWorld, Blocks.PLANKS.getBlockData(), 9, 5, 10, paramStructureBoundingBox);
/*      */         
/*      */ 
/* 1263 */         a(paramWorld, paramStructureBoundingBox, 3, 6, 2, 3, 6, 12, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
/* 1264 */         a(paramWorld, paramStructureBoundingBox, 10, 6, 2, 10, 6, 10, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
/* 1265 */         a(paramWorld, paramStructureBoundingBox, 4, 6, 2, 9, 6, 2, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
/* 1266 */         a(paramWorld, paramStructureBoundingBox, 4, 6, 12, 8, 6, 12, Blocks.FENCE.getBlockData(), Blocks.FENCE.getBlockData(), false);
/* 1267 */         a(paramWorld, Blocks.FENCE.getBlockData(), 9, 6, 11, paramStructureBoundingBox);
/* 1268 */         a(paramWorld, Blocks.FENCE.getBlockData(), 8, 6, 11, paramStructureBoundingBox);
/* 1269 */         a(paramWorld, Blocks.FENCE.getBlockData(), 9, 6, 10, paramStructureBoundingBox);
/*      */         
/*      */ 
/* 1272 */         m = a(Blocks.LADDER, 3);
/* 1273 */         a(paramWorld, Blocks.LADDER.fromLegacyData(m), 10, 1, 13, paramStructureBoundingBox);
/* 1274 */         a(paramWorld, Blocks.LADDER.fromLegacyData(m), 10, 2, 13, paramStructureBoundingBox);
/* 1275 */         a(paramWorld, Blocks.LADDER.fromLegacyData(m), 10, 3, 13, paramStructureBoundingBox);
/* 1276 */         a(paramWorld, Blocks.LADDER.fromLegacyData(m), 10, 4, 13, paramStructureBoundingBox);
/* 1277 */         a(paramWorld, Blocks.LADDER.fromLegacyData(m), 10, 5, 13, paramStructureBoundingBox);
/* 1278 */         a(paramWorld, Blocks.LADDER.fromLegacyData(m), 10, 6, 13, paramStructureBoundingBox);
/* 1279 */         a(paramWorld, Blocks.LADDER.fromLegacyData(m), 10, 7, 13, paramStructureBoundingBox);
/*      */         
/*      */ 
/* 1282 */         int n = 7;
/* 1283 */         int i1 = 7;
/* 1284 */         a(paramWorld, Blocks.FENCE.getBlockData(), n - 1, 9, i1, paramStructureBoundingBox);
/* 1285 */         a(paramWorld, Blocks.FENCE.getBlockData(), n, 9, i1, paramStructureBoundingBox);
/* 1286 */         a(paramWorld, Blocks.FENCE.getBlockData(), n - 1, 8, i1, paramStructureBoundingBox);
/* 1287 */         a(paramWorld, Blocks.FENCE.getBlockData(), n, 8, i1, paramStructureBoundingBox);
/* 1288 */         a(paramWorld, Blocks.FENCE.getBlockData(), n - 1, 7, i1, paramStructureBoundingBox);
/* 1289 */         a(paramWorld, Blocks.FENCE.getBlockData(), n, 7, i1, paramStructureBoundingBox);
/*      */         
/* 1291 */         a(paramWorld, Blocks.FENCE.getBlockData(), n - 2, 7, i1, paramStructureBoundingBox);
/* 1292 */         a(paramWorld, Blocks.FENCE.getBlockData(), n + 1, 7, i1, paramStructureBoundingBox);
/* 1293 */         a(paramWorld, Blocks.FENCE.getBlockData(), n - 1, 7, i1 - 1, paramStructureBoundingBox);
/* 1294 */         a(paramWorld, Blocks.FENCE.getBlockData(), n - 1, 7, i1 + 1, paramStructureBoundingBox);
/* 1295 */         a(paramWorld, Blocks.FENCE.getBlockData(), n, 7, i1 - 1, paramStructureBoundingBox);
/* 1296 */         a(paramWorld, Blocks.FENCE.getBlockData(), n, 7, i1 + 1, paramStructureBoundingBox);
/*      */         
/* 1298 */         a(paramWorld, Blocks.TORCH.getBlockData(), n - 2, 8, i1, paramStructureBoundingBox);
/* 1299 */         a(paramWorld, Blocks.TORCH.getBlockData(), n + 1, 8, i1, paramStructureBoundingBox);
/* 1300 */         a(paramWorld, Blocks.TORCH.getBlockData(), n - 1, 8, i1 - 1, paramStructureBoundingBox);
/* 1301 */         a(paramWorld, Blocks.TORCH.getBlockData(), n - 1, 8, i1 + 1, paramStructureBoundingBox);
/* 1302 */         a(paramWorld, Blocks.TORCH.getBlockData(), n, 8, i1 - 1, paramStructureBoundingBox);
/* 1303 */         a(paramWorld, Blocks.TORCH.getBlockData(), n, 8, i1 + 1, paramStructureBoundingBox);
/*      */       }
/*      */       
/*      */ 
/* 1307 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 3, 3, 5, StructurePieceTreasure.a(a, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.a(paramRandom, 1, 5, 2) }), 1 + paramRandom.nextInt(4));
/* 1308 */       if (this.b) {
/* 1309 */         a(paramWorld, Blocks.AIR.getBlockData(), 12, 9, 1, paramStructureBoundingBox);
/* 1310 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 12, 8, 1, StructurePieceTreasure.a(a, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.a(paramRandom, 1, 5, 2) }), 1 + paramRandom.nextInt(4));
/*      */       }
/*      */       
/* 1313 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WorldGenStrongholdCrossing
/*      */     extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
/*      */   {
/*      */     private boolean a;
/*      */     private boolean b;
/*      */     private boolean c;
/*      */     private boolean e;
/*      */     
/*      */     public WorldGenStrongholdCrossing() {}
/*      */     
/*      */     public WorldGenStrongholdCrossing(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/* 1329 */       super();
/*      */       
/* 1331 */       this.m = paramEnumDirection;
/* 1332 */       this.d = a(paramRandom);
/* 1333 */       this.l = paramStructureBoundingBox;
/*      */       
/* 1335 */       this.a = paramRandom.nextBoolean();
/* 1336 */       this.b = paramRandom.nextBoolean();
/* 1337 */       this.c = paramRandom.nextBoolean();
/* 1338 */       this.e = (paramRandom.nextInt(3) > 0);
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/* 1343 */       super.a(paramNBTTagCompound);
/* 1344 */       paramNBTTagCompound.setBoolean("leftLow", this.a);
/* 1345 */       paramNBTTagCompound.setBoolean("leftHigh", this.b);
/* 1346 */       paramNBTTagCompound.setBoolean("rightLow", this.c);
/* 1347 */       paramNBTTagCompound.setBoolean("rightHigh", this.e);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/* 1352 */       super.b(paramNBTTagCompound);
/* 1353 */       this.a = paramNBTTagCompound.getBoolean("leftLow");
/* 1354 */       this.b = paramNBTTagCompound.getBoolean("leftHigh");
/* 1355 */       this.c = paramNBTTagCompound.getBoolean("rightLow");
/* 1356 */       this.e = paramNBTTagCompound.getBoolean("rightHigh");
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/* 1361 */       int i = 3;
/* 1362 */       int j = 5;
/*      */       
/* 1364 */       if ((this.m == EnumDirection.WEST) || (this.m == EnumDirection.NORTH)) {
/* 1365 */         i = 8 - i;
/* 1366 */         j = 8 - j;
/*      */       }
/*      */       
/* 1369 */       a((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 5, 1);
/* 1370 */       if (this.a) {
/* 1371 */         b((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, i, 1);
/*      */       }
/* 1373 */       if (this.b) {
/* 1374 */         b((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, j, 7);
/*      */       }
/* 1376 */       if (this.c) {
/* 1377 */         c((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, i, 1);
/*      */       }
/* 1379 */       if (this.e) {
/* 1380 */         c((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, j, 7);
/*      */       }
/*      */     }
/*      */     
/*      */     public static WorldGenStrongholdCrossing a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/* 1385 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -4, -3, 0, 10, 9, 11, paramEnumDirection);
/*      */       
/* 1387 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/* 1388 */         return null;
/*      */       }
/*      */       
/* 1391 */       return new WorldGenStrongholdCrossing(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1396 */       if (a(paramWorld, paramStructureBoundingBox)) {
/* 1397 */         return false;
/*      */       }
/*      */       
/*      */ 
/* 1401 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 9, 8, 10, true, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/* 1403 */       a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 4, 3, 0);
/*      */       
/*      */ 
/* 1406 */       if (this.a) {
/* 1407 */         a(paramWorld, paramStructureBoundingBox, 0, 3, 1, 0, 5, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       }
/* 1409 */       if (this.c) {
/* 1410 */         a(paramWorld, paramStructureBoundingBox, 9, 3, 1, 9, 5, 3, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       }
/* 1412 */       if (this.b) {
/* 1413 */         a(paramWorld, paramStructureBoundingBox, 0, 5, 7, 0, 7, 9, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       }
/* 1415 */       if (this.e) {
/* 1416 */         a(paramWorld, paramStructureBoundingBox, 9, 5, 7, 9, 7, 9, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       }
/* 1418 */       a(paramWorld, paramStructureBoundingBox, 5, 1, 10, 7, 3, 10, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*      */       
/*      */ 
/* 1421 */       a(paramWorld, paramStructureBoundingBox, 1, 2, 1, 8, 2, 6, false, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/* 1423 */       a(paramWorld, paramStructureBoundingBox, 4, 1, 5, 4, 4, 9, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1424 */       a(paramWorld, paramStructureBoundingBox, 8, 1, 5, 8, 4, 9, false, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/* 1426 */       a(paramWorld, paramStructureBoundingBox, 1, 4, 7, 3, 4, 9, false, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/*      */ 
/* 1429 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 5, 3, 3, 6, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1430 */       a(paramWorld, paramStructureBoundingBox, 1, 3, 4, 3, 3, 4, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
/* 1431 */       a(paramWorld, paramStructureBoundingBox, 1, 4, 6, 3, 4, 6, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
/*      */       
/*      */ 
/* 1434 */       a(paramWorld, paramStructureBoundingBox, 5, 1, 7, 7, 1, 8, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1435 */       a(paramWorld, paramStructureBoundingBox, 5, 1, 9, 7, 1, 9, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
/* 1436 */       a(paramWorld, paramStructureBoundingBox, 5, 2, 7, 7, 2, 7, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
/*      */       
/*      */ 
/* 1439 */       a(paramWorld, paramStructureBoundingBox, 4, 5, 7, 4, 5, 9, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
/* 1440 */       a(paramWorld, paramStructureBoundingBox, 8, 5, 7, 8, 5, 9, Blocks.STONE_SLAB.getBlockData(), Blocks.STONE_SLAB.getBlockData(), false);
/* 1441 */       a(paramWorld, paramStructureBoundingBox, 5, 5, 7, 7, 5, 9, Blocks.DOUBLE_STONE_SLAB.getBlockData(), Blocks.DOUBLE_STONE_SLAB.getBlockData(), false);
/* 1442 */       a(paramWorld, Blocks.TORCH.getBlockData(), 6, 5, 6, paramStructureBoundingBox);
/*      */       
/* 1444 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static class WorldGenStrongholdPortalRoom
/*      */     extends WorldGenStrongholdPieces.WorldGenStrongholdPiece
/*      */   {
/*      */     private boolean a;
/*      */     
/*      */ 
/*      */     public WorldGenStrongholdPortalRoom() {}
/*      */     
/*      */ 
/*      */     public WorldGenStrongholdPortalRoom(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*      */     {
/* 1460 */       super();
/*      */       
/* 1462 */       this.m = paramEnumDirection;
/* 1463 */       this.l = paramStructureBoundingBox;
/*      */     }
/*      */     
/*      */     protected void a(NBTTagCompound paramNBTTagCompound)
/*      */     {
/* 1468 */       super.a(paramNBTTagCompound);
/* 1469 */       paramNBTTagCompound.setBoolean("Mob", this.a);
/*      */     }
/*      */     
/*      */     protected void b(NBTTagCompound paramNBTTagCompound)
/*      */     {
/* 1474 */       super.b(paramNBTTagCompound);
/* 1475 */       this.a = paramNBTTagCompound.getBoolean("Mob");
/*      */     }
/*      */     
/*      */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*      */     {
/* 1480 */       if (paramStructurePiece != null) {
/* 1481 */         ((WorldGenStrongholdPieces.WorldGenStrongholdStart)paramStructurePiece).b = this;
/*      */       }
/*      */     }
/*      */     
/*      */     public static WorldGenStrongholdPortalRoom a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/* 1486 */       StructureBoundingBox localStructureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -4, -1, 0, 11, 8, 16, paramEnumDirection);
/*      */       
/* 1488 */       if ((!a(localStructureBoundingBox)) || (StructurePiece.a(paramList, localStructureBoundingBox) != null)) {
/* 1489 */         return null;
/*      */       }
/*      */       
/* 1492 */       return new WorldGenStrongholdPortalRoom(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*      */     {
/* 1498 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 10, 7, 15, false, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/* 1500 */       a(paramWorld, paramRandom, paramStructureBoundingBox, WorldGenStrongholdPieces.WorldGenStrongholdPiece.WorldGenStrongholdDoorType.GRATES, 4, 1, 0);
/*      */       
/*      */ 
/* 1503 */       int i = 6;
/* 1504 */       a(paramWorld, paramStructureBoundingBox, 1, i, 1, 1, i, 14, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1505 */       a(paramWorld, paramStructureBoundingBox, 9, i, 1, 9, i, 14, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1506 */       a(paramWorld, paramStructureBoundingBox, 2, i, 1, 8, i, 2, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1507 */       a(paramWorld, paramStructureBoundingBox, 2, i, 14, 8, i, 14, false, paramRandom, WorldGenStrongholdPieces.c());
/*      */       
/*      */ 
/* 1510 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 2, 1, 4, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1511 */       a(paramWorld, paramStructureBoundingBox, 8, 1, 1, 9, 1, 4, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1512 */       a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 1, 1, 3, Blocks.FLOWING_LAVA.getBlockData(), Blocks.FLOWING_LAVA.getBlockData(), false);
/* 1513 */       a(paramWorld, paramStructureBoundingBox, 9, 1, 1, 9, 1, 3, Blocks.FLOWING_LAVA.getBlockData(), Blocks.FLOWING_LAVA.getBlockData(), false);
/*      */       
/*      */ 
/* 1516 */       a(paramWorld, paramStructureBoundingBox, 3, 1, 8, 7, 1, 12, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1517 */       a(paramWorld, paramStructureBoundingBox, 4, 1, 9, 6, 1, 11, Blocks.FLOWING_LAVA.getBlockData(), Blocks.FLOWING_LAVA.getBlockData(), false);
/*      */       
/*      */ 
/* 1520 */       for (int j = 3; j < 14; j += 2) {
/* 1521 */         a(paramWorld, paramStructureBoundingBox, 0, 3, j, 0, 4, j, Blocks.IRON_BARS.getBlockData(), Blocks.IRON_BARS.getBlockData(), false);
/* 1522 */         a(paramWorld, paramStructureBoundingBox, 10, 3, j, 10, 4, j, Blocks.IRON_BARS.getBlockData(), Blocks.IRON_BARS.getBlockData(), false);
/*      */       }
/* 1524 */       for (j = 2; j < 9; j += 2) {
/* 1525 */         a(paramWorld, paramStructureBoundingBox, j, 3, 15, j, 4, 15, Blocks.IRON_BARS.getBlockData(), Blocks.IRON_BARS.getBlockData(), false);
/*      */       }
/*      */       
/*      */ 
/* 1529 */       j = a(Blocks.STONE_BRICK_STAIRS, 3);
/* 1530 */       a(paramWorld, paramStructureBoundingBox, 4, 1, 5, 6, 1, 7, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1531 */       a(paramWorld, paramStructureBoundingBox, 4, 2, 6, 6, 2, 7, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1532 */       a(paramWorld, paramStructureBoundingBox, 4, 3, 7, 6, 3, 7, false, paramRandom, WorldGenStrongholdPieces.c());
/* 1533 */       for (int k = 4; k <= 6; k++) {
/* 1534 */         a(paramWorld, Blocks.STONE_BRICK_STAIRS.fromLegacyData(j), k, 1, 4, paramStructureBoundingBox);
/* 1535 */         a(paramWorld, Blocks.STONE_BRICK_STAIRS.fromLegacyData(j), k, 2, 5, paramStructureBoundingBox);
/* 1536 */         a(paramWorld, Blocks.STONE_BRICK_STAIRS.fromLegacyData(j), k, 3, 6, paramStructureBoundingBox);
/*      */       }
/*      */       
/* 1539 */       k = EnumDirection.NORTH.b();
/* 1540 */       int m = EnumDirection.SOUTH.b();
/* 1541 */       int n = EnumDirection.EAST.b();
/* 1542 */       int i1 = EnumDirection.WEST.b();
/*      */       
/* 1544 */       if (this.m != null) {
/* 1545 */         switch (WorldGenStrongholdPieces.3.b[this.m.ordinal()]) {
/*      */         case 2: 
/* 1547 */           k = EnumDirection.SOUTH.b();
/* 1548 */           m = EnumDirection.NORTH.b();
/* 1549 */           break;
/*      */         case 4: 
/* 1551 */           k = EnumDirection.EAST.b();
/* 1552 */           m = EnumDirection.WEST.b();
/* 1553 */           n = EnumDirection.SOUTH.b();
/* 1554 */           i1 = EnumDirection.NORTH.b();
/* 1555 */           break;
/*      */         case 3: 
/* 1557 */           k = EnumDirection.WEST.b();
/* 1558 */           m = EnumDirection.EAST.b();
/* 1559 */           n = EnumDirection.SOUTH.b();
/* 1560 */           i1 = EnumDirection.NORTH.b();
/*      */         }
/*      */         
/*      */       }
/*      */       
/* 1565 */       a(paramWorld, Blocks.END_PORTAL_FRAME.fromLegacyData(k).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(paramRandom.nextFloat() > 0.9F)), 4, 3, 8, paramStructureBoundingBox);
/* 1566 */       a(paramWorld, Blocks.END_PORTAL_FRAME.fromLegacyData(k).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(paramRandom.nextFloat() > 0.9F)), 5, 3, 8, paramStructureBoundingBox);
/* 1567 */       a(paramWorld, Blocks.END_PORTAL_FRAME.fromLegacyData(k).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(paramRandom.nextFloat() > 0.9F)), 6, 3, 8, paramStructureBoundingBox);
/* 1568 */       a(paramWorld, Blocks.END_PORTAL_FRAME.fromLegacyData(m).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(paramRandom.nextFloat() > 0.9F)), 4, 3, 12, paramStructureBoundingBox);
/* 1569 */       a(paramWorld, Blocks.END_PORTAL_FRAME.fromLegacyData(m).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(paramRandom.nextFloat() > 0.9F)), 5, 3, 12, paramStructureBoundingBox);
/* 1570 */       a(paramWorld, Blocks.END_PORTAL_FRAME.fromLegacyData(m).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(paramRandom.nextFloat() > 0.9F)), 6, 3, 12, paramStructureBoundingBox);
/* 1571 */       a(paramWorld, Blocks.END_PORTAL_FRAME.fromLegacyData(n).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(paramRandom.nextFloat() > 0.9F)), 3, 3, 9, paramStructureBoundingBox);
/* 1572 */       a(paramWorld, Blocks.END_PORTAL_FRAME.fromLegacyData(n).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(paramRandom.nextFloat() > 0.9F)), 3, 3, 10, paramStructureBoundingBox);
/* 1573 */       a(paramWorld, Blocks.END_PORTAL_FRAME.fromLegacyData(n).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(paramRandom.nextFloat() > 0.9F)), 3, 3, 11, paramStructureBoundingBox);
/* 1574 */       a(paramWorld, Blocks.END_PORTAL_FRAME.fromLegacyData(i1).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(paramRandom.nextFloat() > 0.9F)), 7, 3, 9, paramStructureBoundingBox);
/* 1575 */       a(paramWorld, Blocks.END_PORTAL_FRAME.fromLegacyData(i1).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(paramRandom.nextFloat() > 0.9F)), 7, 3, 10, paramStructureBoundingBox);
/* 1576 */       a(paramWorld, Blocks.END_PORTAL_FRAME.fromLegacyData(i1).set(BlockEnderPortalFrame.EYE, Boolean.valueOf(paramRandom.nextFloat() > 0.9F)), 7, 3, 11, paramStructureBoundingBox);
/*      */       
/* 1578 */       if (!this.a) {
/* 1579 */         i = d(3);
/* 1580 */         BlockPosition localBlockPosition = new BlockPosition(a(5, 6), i, b(5, 6));
/* 1581 */         if (paramStructureBoundingBox.b(localBlockPosition)) {
/* 1582 */           this.a = true;
/* 1583 */           paramWorld.setTypeAndData(localBlockPosition, Blocks.MOB_SPAWNER.getBlockData(), 2);
/*      */           
/* 1585 */           TileEntity localTileEntity = paramWorld.getTileEntity(localBlockPosition);
/* 1586 */           if ((localTileEntity instanceof TileEntityMobSpawner)) {
/* 1587 */             ((TileEntityMobSpawner)localTileEntity).getSpawner().setMobName("Silverfish");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1592 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static class WorldGenStrongholdStones extends StructurePiece.StructurePieceBlockSelector
/*      */   {
/*      */     public void a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 1599 */       if (paramBoolean) {
/* 1600 */         float f = paramRandom.nextFloat();
/* 1601 */         if (f < 0.2F) {
/* 1602 */           this.a = Blocks.STONEBRICK.fromLegacyData(BlockSmoothBrick.O);
/* 1603 */         } else if (f < 0.5F) {
/* 1604 */           this.a = Blocks.STONEBRICK.fromLegacyData(BlockSmoothBrick.N);
/* 1605 */         } else if (f < 0.55F) {
/* 1606 */           this.a = Blocks.MONSTER_EGG.fromLegacyData(BlockMonsterEggs.EnumMonsterEggVarient.STONEBRICK.a());
/*      */         } else {
/* 1608 */           this.a = Blocks.STONEBRICK.getBlockData();
/*      */         }
/*      */       } else {
/* 1611 */         this.a = Blocks.AIR.getBlockData();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 1616 */   private static final WorldGenStrongholdStones e = new WorldGenStrongholdStones(null);
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenStrongholdPieces.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */