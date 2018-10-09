/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import gnu.trove.map.hash.TObjectIntHashMap;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.LongHash;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.LongHashSet;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
/*     */ import org.spigotmc.SpigotWorldConfig;
/*     */ 
/*     */ 
/*     */ public final class SpawnerCreature
/*     */ {
/*  17 */   private static final int a = (int)Math.pow(17.0D, 2.0D);
/*  18 */   private final LongHashSet b = new LongHashSet();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private int getEntityCount(WorldServer server, Class oClass)
/*     */   {
/*  25 */     int i = 0;
/*  26 */     Iterator<Long> it = this.b.iterator();
/*  27 */     while (it.hasNext())
/*     */     {
/*  29 */       Long coord = (Long)it.next();
/*  30 */       int x = LongHash.msw(coord.longValue());
/*  31 */       int z = LongHash.lsw(coord.longValue());
/*  32 */       if ((!server.chunkProviderServer.unloadQueue.contains(coord.longValue())) && (server.isChunkLoaded(x, z, true)))
/*     */       {
/*  34 */         i += server.getChunkAt(x, z).entityCount.get(oClass);
/*     */       }
/*     */     }
/*  37 */     return i;
/*     */   }
/*     */   
/*     */   public int a(WorldServer worldserver, boolean flag, boolean flag1, boolean flag2)
/*     */   {
/*  42 */     if ((!flag) && (!flag1)) {
/*  43 */       return 0;
/*     */     }
/*  45 */     this.b.clear();
/*  46 */     int i = 0;
/*  47 */     Iterator iterator = worldserver.players.iterator();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  52 */     while (iterator.hasNext()) {
/*  53 */       EntityHuman entityhuman = (EntityHuman)iterator.next();
/*     */       
/*  55 */       if (!entityhuman.isSpectator()) {
/*  56 */         int l = MathHelper.floor(entityhuman.locX / 16.0D);
/*     */         
/*  58 */         int j = MathHelper.floor(entityhuman.locZ / 16.0D);
/*  59 */         byte b0 = 8;
/*     */         
/*  61 */         b0 = worldserver.spigotConfig.mobSpawnRange;
/*  62 */         b0 = b0 > worldserver.spigotConfig.viewDistance ? (byte)worldserver.spigotConfig.viewDistance : b0;
/*  63 */         b0 = b0 > 8 ? 8 : b0;
/*     */         
/*     */ 
/*  66 */         for (int i1 = -b0; i1 <= b0; i1++) {
/*  67 */           for (int k = -b0; k <= b0; k++) {
/*  68 */             boolean flag3 = (i1 == -b0) || (i1 == b0) || (k == -b0) || (k == b0);
/*     */             
/*     */ 
/*     */ 
/*  72 */             long chunkCoords = LongHash.toLong(i1 + l, k + j);
/*  73 */             if (!this.b.contains(chunkCoords)) {
/*  74 */               i++;
/*  75 */               if ((!flag3) && (worldserver.getWorldBorder().isInBounds(i1 + l, k + j))) {
/*  76 */                 this.b.add(chunkCoords);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  85 */     int j1 = 0;
/*  86 */     BlockPosition blockposition = worldserver.getSpawn();
/*  87 */     EnumCreatureType[] aenumcreaturetype = EnumCreatureType.values();
/*     */     
/*  89 */     int j = aenumcreaturetype.length;
/*     */     
/*  91 */     for (int k1 = 0; k1 < j; k1++) {
/*  92 */       EnumCreatureType enumcreaturetype = aenumcreaturetype[k1];
/*     */       
/*     */ 
/*  95 */       int limit = enumcreaturetype.b();
/*  96 */       switch (enumcreaturetype) {
/*     */       case AMBIENT: 
/*  98 */         limit = worldserver.getWorld().getMonsterSpawnLimit();
/*  99 */         break;
/*     */       case CREATURE: 
/* 101 */         limit = worldserver.getWorld().getAnimalSpawnLimit();
/* 102 */         break;
/*     */       case WATER_CREATURE: 
/* 104 */         limit = worldserver.getWorld().getWaterAnimalSpawnLimit();
/* 105 */         break;
/*     */       case MONSTER: 
/* 107 */         limit = worldserver.getWorld().getAmbientSpawnLimit();
/*     */       }
/*     */       
/*     */       
/* 111 */       if (limit != 0)
/*     */       {
/*     */ 
/* 114 */         int mobcnt = 0;
/*     */         
/*     */ 
/* 117 */         if (((!enumcreaturetype.d()) || (flag1)) && ((enumcreaturetype.d()) || (flag)) && ((!enumcreaturetype.e()) || (flag2))) {
/* 118 */           int k = worldserver.a(enumcreaturetype.a());
/* 119 */           (limit * i / a);
/*     */           
/* 121 */           if ((mobcnt = getEntityCount(worldserver, enumcreaturetype.a())) <= limit * i / 256) {
/* 122 */             Iterator iterator1 = this.b.iterator();
/*     */             
/* 124 */             int moblimit = limit * i / 256 - mobcnt + 1;
/*     */             label1018:
/* 126 */             while ((iterator1.hasNext()) && (moblimit > 0))
/*     */             {
/* 128 */               long key = ((Long)iterator1.next()).longValue();
/* 129 */               BlockPosition blockposition1 = getRandomPosition(worldserver, LongHash.msw(key), LongHash.lsw(key));
/*     */               
/* 131 */               int i2 = blockposition1.getX();
/* 132 */               int j2 = blockposition1.getY();
/* 133 */               int k2 = blockposition1.getZ();
/* 134 */               Block block = worldserver.getType(blockposition1).getBlock();
/*     */               
/* 136 */               if (!block.isOccluding()) {
/* 137 */                 int l2 = 0;
/* 138 */                 int i3 = 0;
/*     */                 
/* 140 */                 while (i3 < 3) {
/* 141 */                   int j3 = i2;
/* 142 */                   int k3 = j2;
/* 143 */                   int l3 = k2;
/* 144 */                   byte b1 = 6;
/* 145 */                   BiomeBase.BiomeMeta biomebase_biomemeta = null;
/* 146 */                   GroupDataEntity groupdataentity = null;
/* 147 */                   int i4 = 0;
/*     */                   
/*     */ 
/* 150 */                   while (i4 < 4)
/*     */                   {
/* 152 */                     j3 += worldserver.random.nextInt(b1) - worldserver.random.nextInt(b1);
/* 153 */                     k3 += worldserver.random.nextInt(1) - worldserver.random.nextInt(1);
/* 154 */                     l3 += worldserver.random.nextInt(b1) - worldserver.random.nextInt(b1);
/* 155 */                     BlockPosition blockposition2 = new BlockPosition(j3, k3, l3);
/* 156 */                     float f = j3 + 0.5F;
/* 157 */                     float f1 = l3 + 0.5F;
/*     */                     
/* 159 */                     if ((!worldserver.isPlayerNearby(f, k3, f1, 24.0D)) && (blockposition.c(f, k3, f1) >= 576.0D)) {
/* 160 */                       if (biomebase_biomemeta == null) {
/* 161 */                         biomebase_biomemeta = worldserver.a(enumcreaturetype, blockposition2);
/* 162 */                         if (biomebase_biomemeta == null) {
/*     */                           break;
/*     */                         }
/*     */                       }
/*     */                       
/* 167 */                       if ((worldserver.a(enumcreaturetype, biomebase_biomemeta, blockposition2)) && (a(EntityPositionTypes.a(biomebase_biomemeta.b), worldserver, blockposition2)))
/*     */                       {
/*     */                         try
/*     */                         {
/* 171 */                           entityinsentient = (EntityInsentient)biomebase_biomemeta.b.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldserver });
/*     */                         } catch (Exception exception) { EntityInsentient entityinsentient;
/* 173 */                           exception.printStackTrace();
/* 174 */                           return j1;
/*     */                         }
/*     */                         EntityInsentient entityinsentient;
/* 177 */                         entityinsentient.setPositionRotation(f, k3, f1, worldserver.random.nextFloat() * 360.0F, 0.0F);
/* 178 */                         if ((entityinsentient.bR()) && (entityinsentient.canSpawn())) {
/* 179 */                           groupdataentity = entityinsentient.prepare(worldserver.E(new BlockPosition(entityinsentient)), groupdataentity);
/* 180 */                           if (entityinsentient.canSpawn()) {
/* 181 */                             l2++;
/* 182 */                             worldserver.addEntity(entityinsentient, CreatureSpawnEvent.SpawnReason.NATURAL);
/*     */                           }
/*     */                           
/*     */ 
/* 186 */                           moblimit--; if (moblimit <= 0) {
/*     */                             break label1018;
/*     */                           }
/*     */                           
/*     */ 
/*     */ 
/* 192 */                           if (l2 >= entityinsentient.bV()) {
/*     */                             break label1018;
/*     */                           }
/*     */                         }
/*     */                         
/* 197 */                         j1 += l2;
/*     */                       }
/*     */                     }
/*     */                     
/* 201 */                     i4++;
/*     */                   }
/*     */                   
/*     */ 
/*     */ 
/* 206 */                   i3++;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 216 */     return j1;
/*     */   }
/*     */   
/*     */   protected static BlockPosition getRandomPosition(World world, int i, int j)
/*     */   {
/* 221 */     Chunk chunk = world.getChunkAt(i, j);
/* 222 */     int k = i * 16 + world.random.nextInt(16);
/* 223 */     int l = j * 16 + world.random.nextInt(16);
/* 224 */     int i1 = MathHelper.c(chunk.f(new BlockPosition(k, 0, l)) + 1, 16);
/* 225 */     int j1 = world.random.nextInt(i1 > 0 ? i1 : chunk.g() + 16 - 1);
/*     */     
/* 227 */     return new BlockPosition(k, j1, l);
/*     */   }
/*     */   
/*     */   public static boolean a(EntityInsentient.EnumEntityPositionType entityinsentient_enumentitypositiontype, World world, BlockPosition blockposition) {
/* 231 */     if (!world.getWorldBorder().a(blockposition)) {
/* 232 */       return false;
/*     */     }
/* 234 */     Block block = world.getType(blockposition).getBlock();
/*     */     
/* 236 */     if (entityinsentient_enumentitypositiontype == EntityInsentient.EnumEntityPositionType.IN_WATER) {
/* 237 */       return (block.getMaterial().isLiquid()) && (world.getType(blockposition.down()).getBlock().getMaterial().isLiquid()) && (!world.getType(blockposition.up()).getBlock().isOccluding());
/*     */     }
/* 239 */     BlockPosition blockposition1 = blockposition.down();
/*     */     
/* 241 */     if (!World.a(world, blockposition1)) {
/* 242 */       return false;
/*     */     }
/* 244 */     Block block1 = world.getType(blockposition1).getBlock();
/* 245 */     boolean flag = (block1 != Blocks.BEDROCK) && (block1 != Blocks.BARRIER);
/*     */     
/* 247 */     return (flag) && (!block.isOccluding()) && (!block.getMaterial().isLiquid()) && (!world.getType(blockposition.up()).getBlock().isOccluding());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void a(World world, BiomeBase biomebase, int i, int j, int k, int l, Random random)
/*     */   {
/* 254 */     List list = biomebase.getMobs(EnumCreatureType.CREATURE);
/*     */     
/* 256 */     if (!list.isEmpty()) { int i1;
/* 257 */       int j2; for (; random.nextFloat() < biomebase.g(); 
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 266 */           j2 < i1)
/*     */       {
/* 258 */         BiomeBase.BiomeMeta biomebase_biomemeta = (BiomeBase.BiomeMeta)WeightedRandom.a(world.random, list);
/* 259 */         i1 = biomebase_biomemeta.c + random.nextInt(1 + biomebase_biomemeta.d - biomebase_biomemeta.c);
/* 260 */         GroupDataEntity groupdataentity = null;
/* 261 */         int j1 = i + random.nextInt(k);
/* 262 */         int k1 = j + random.nextInt(l);
/* 263 */         int l1 = j1;
/* 264 */         int i2 = k1;
/*     */         
/* 266 */         j2 = 0; continue;
/* 267 */         boolean flag = false;
/*     */         
/* 269 */         for (int k2 = 0; (!flag) && (k2 < 4); k2++) {
/* 270 */           BlockPosition blockposition = world.r(new BlockPosition(j1, 0, k1));
/*     */           
/* 272 */           if (a(EntityInsentient.EnumEntityPositionType.ON_GROUND, world, blockposition))
/*     */           {
/*     */             try
/*     */             {
/* 276 */               entityinsentient = (EntityInsentient)biomebase_biomemeta.b.getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
/*     */             } catch (Exception exception) { EntityInsentient entityinsentient;
/* 278 */               exception.printStackTrace();
/* 279 */               continue;
/*     */             }
/*     */             EntityInsentient entityinsentient;
/* 282 */             entityinsentient.setPositionRotation(j1 + 0.5F, blockposition.getY(), k1 + 0.5F, random.nextFloat() * 360.0F, 0.0F);
/*     */             
/* 284 */             groupdataentity = entityinsentient.prepare(world.E(new BlockPosition(entityinsentient)), groupdataentity);
/* 285 */             world.addEntity(entityinsentient, CreatureSpawnEvent.SpawnReason.CHUNK_GEN);
/*     */             
/* 287 */             flag = true;
/*     */           }
/*     */           
/* 290 */           j1 += random.nextInt(5) - random.nextInt(5);
/*     */           
/* 292 */           for (k1 += random.nextInt(5) - random.nextInt(5); (j1 < i) || (j1 >= i + k) || (k1 < j) || (k1 >= j + k); k1 = i2 + random.nextInt(5) - random.nextInt(5)) {
/* 293 */             j1 = l1 + random.nextInt(5) - random.nextInt(5);
/*     */           }
/*     */         }
/* 266 */         j2++;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\SpawnerCreature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */