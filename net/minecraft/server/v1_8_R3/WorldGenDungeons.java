/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public class WorldGenDungeons
/*     */   extends WorldGenerator
/*     */ {
/*  22 */   private static final Logger a = ;
/*     */   
/*     */ 
/*  25 */   private static final String[] b = { "Skeleton", "Zombie", "Zombie", "Spider" };
/*  26 */   private static final List<StructurePieceTreasure> c = Lists.newArrayList(new StructurePieceTreasure[] { new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 10), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 4, 10), new StructurePieceTreasure(Items.BREAD, 0, 1, 1, 10), new StructurePieceTreasure(Items.WHEAT, 0, 1, 4, 10), new StructurePieceTreasure(Items.GUNPOWDER, 0, 1, 4, 10), new StructurePieceTreasure(Items.STRING, 0, 1, 4, 10), new StructurePieceTreasure(Items.BUCKET, 0, 1, 1, 10), new StructurePieceTreasure(Items.GOLDEN_APPLE, 0, 1, 1, 1), new StructurePieceTreasure(Items.REDSTONE, 0, 1, 4, 10), new StructurePieceTreasure(Items.RECORD_13, 0, 1, 1, 4), new StructurePieceTreasure(Items.RECORD_CAT, 0, 1, 1, 4), new StructurePieceTreasure(Items.NAME_TAG, 0, 1, 1, 10), new StructurePieceTreasure(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 2), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 5), new StructurePieceTreasure(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 1) });
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
/*     */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  47 */     int i = 3;
/*  48 */     int j = paramRandom.nextInt(2) + 2;
/*  49 */     int k = -j - 1;
/*  50 */     int m = j + 1;
/*     */     
/*  52 */     int n = -1;
/*  53 */     int i1 = 4;
/*     */     
/*  55 */     int i2 = paramRandom.nextInt(2) + 2;
/*  56 */     int i3 = -i2 - 1;
/*  57 */     int i4 = i2 + 1;
/*     */     
/*  59 */     int i5 = 0;
/*  60 */     int i7; int i8; BlockPosition localBlockPosition1; for (int i6 = k; i6 <= m; i6++) {
/*  61 */       for (i7 = -1; i7 <= 4; i7++) {
/*  62 */         for (i8 = i3; i8 <= i4; i8++) {
/*  63 */           localBlockPosition1 = paramBlockPosition.a(i6, i7, i8);
/*  64 */           Material localMaterial = paramWorld.getType(localBlockPosition1).getBlock().getMaterial();
/*  65 */           boolean bool = localMaterial.isBuildable();
/*     */           
/*  67 */           if ((i7 == -1) && (!bool)) {
/*  68 */             return false;
/*     */           }
/*  70 */           if ((i7 == 4) && (!bool)) {
/*  71 */             return false;
/*     */           }
/*     */           
/*  74 */           if (((i6 == k) || (i6 == m) || (i8 == i3) || (i8 == i4)) && 
/*  75 */             (i7 == 0) && (paramWorld.isEmpty(localBlockPosition1)) && (paramWorld.isEmpty(localBlockPosition1.up()))) {
/*  76 */             i5++;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  83 */     if ((i5 < 1) || (i5 > 5)) {
/*  84 */       return false;
/*     */     }
/*     */     
/*  87 */     for (i6 = k; i6 <= m; i6++) {
/*  88 */       for (i7 = 3; i7 >= -1; i7--) {
/*  89 */         for (i8 = i3; i8 <= i4; i8++) {
/*  90 */           localBlockPosition1 = paramBlockPosition.a(i6, i7, i8);
/*     */           
/*  92 */           if ((i6 == k) || (i7 == -1) || (i8 == i3) || (i6 == m) || (i7 == 4) || (i8 == i4)) {
/*  93 */             if ((localBlockPosition1.getY() >= 0) && (!paramWorld.getType(localBlockPosition1.down()).getBlock().getMaterial().isBuildable())) {
/*  94 */               paramWorld.setAir(localBlockPosition1);
/*  95 */             } else if ((paramWorld.getType(localBlockPosition1).getBlock().getMaterial().isBuildable()) && 
/*  96 */               (paramWorld.getType(localBlockPosition1).getBlock() != Blocks.CHEST)) {
/*  97 */               if ((i7 == -1) && (paramRandom.nextInt(4) != 0)) {
/*  98 */                 paramWorld.setTypeAndData(localBlockPosition1, Blocks.MOSSY_COBBLESTONE.getBlockData(), 2);
/*     */               } else {
/* 100 */                 paramWorld.setTypeAndData(localBlockPosition1, Blocks.COBBLESTONE.getBlockData(), 2);
/*     */               }
/*     */               
/*     */             }
/*     */           }
/* 105 */           else if (paramWorld.getType(localBlockPosition1).getBlock() != Blocks.CHEST) {
/* 106 */             paramWorld.setAir(localBlockPosition1);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 113 */     for (i6 = 0; i6 < 2; i6++) {
/* 114 */       for (i7 = 0; i7 < 3; i7++) {
/* 115 */         i8 = paramBlockPosition.getX() + paramRandom.nextInt(j * 2 + 1) - j;
/* 116 */         int i9 = paramBlockPosition.getY();
/* 117 */         int i10 = paramBlockPosition.getZ() + paramRandom.nextInt(i2 * 2 + 1) - i2;
/* 118 */         BlockPosition localBlockPosition2 = new BlockPosition(i8, i9, i10);
/*     */         
/* 120 */         if (paramWorld.isEmpty(localBlockPosition2))
/*     */         {
/*     */ 
/*     */ 
/* 124 */           int i11 = 0;
/* 125 */           for (Object localObject1 = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (EnumDirection)((Iterator)localObject1).next();
/* 126 */             if (paramWorld.getType(localBlockPosition2.shift((EnumDirection)localObject2)).getBlock().getMaterial().isBuildable()) {
/* 127 */               i11++;
/*     */             }
/*     */           }
/*     */           Object localObject2;
/* 131 */           if (i11 == 1)
/*     */           {
/*     */ 
/*     */ 
/* 135 */             paramWorld.setTypeAndData(localBlockPosition2, Blocks.CHEST.f(paramWorld, localBlockPosition2, Blocks.CHEST.getBlockData()), 2);
/*     */             
/* 137 */             localObject1 = StructurePieceTreasure.a(c, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) });
/*     */             
/* 139 */             localObject2 = paramWorld.getTileEntity(localBlockPosition2);
/* 140 */             if (!(localObject2 instanceof TileEntityChest)) break;
/* 141 */             StructurePieceTreasure.a(paramRandom, (List)localObject1, (TileEntityChest)localObject2, 8); break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 148 */     paramWorld.setTypeAndData(paramBlockPosition, Blocks.MOB_SPAWNER.getBlockData(), 2);
/* 149 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/*     */     
/* 151 */     if ((localTileEntity instanceof TileEntityMobSpawner)) {
/* 152 */       ((TileEntityMobSpawner)localTileEntity).getSpawner().setMobName(a(paramRandom));
/*     */     } else {
/* 154 */       a.error("Failed to fetch mob spawner entity at (" + paramBlockPosition.getX() + ", " + paramBlockPosition.getY() + ", " + paramBlockPosition.getZ() + ")");
/*     */     }
/*     */     
/* 157 */     return true;
/*     */   }
/*     */   
/*     */   private String a(Random paramRandom) {
/* 161 */     return b[paramRandom.nextInt(b.length)];
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenDungeons.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */