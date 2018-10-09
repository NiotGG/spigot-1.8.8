/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Callable;
/*     */ 
/*     */ public abstract class StructureGenerator extends WorldGenBase
/*     */ {
/*     */   private PersistentStructure d;
/*  13 */   protected Map<Long, StructureStart> e = com.google.common.collect.Maps.newHashMap();
/*     */   
/*     */ 
/*     */   public abstract String a();
/*     */   
/*     */   protected final void a(World world, final int i, final int j, int k, int l, ChunkSnapshot chunksnapshot)
/*     */   {
/*  20 */     a(world);
/*  21 */     if (!this.e.containsKey(Long.valueOf(ChunkCoordIntPair.a(i, j)))) {
/*  22 */       this.b.nextInt();
/*     */       try
/*     */       {
/*  25 */         if (a(i, j)) {
/*  26 */           StructureStart structurestart = b(i, j);
/*     */           
/*  28 */           this.e.put(Long.valueOf(ChunkCoordIntPair.a(i, j)), structurestart);
/*  29 */           a(i, j, structurestart);
/*     */         }
/*     */       }
/*     */       catch (Throwable throwable) {
/*  33 */         CrashReport crashreport = CrashReport.a(throwable, "Exception preparing structure feature");
/*  34 */         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Feature being prepared");
/*     */         
/*  36 */         crashreportsystemdetails.a("Is feature chunk", new Callable() {
/*     */           public String a() throws Exception {
/*  38 */             return StructureGenerator.this.a(i, j) ? "True" : "False";
/*     */           }
/*     */           
/*     */           public Object call() throws Exception {
/*  42 */             return a();
/*     */           }
/*  44 */         });
/*  45 */         crashreportsystemdetails.a("Chunk location", String.format("%d,%d", new Object[] { Integer.valueOf(i), Integer.valueOf(j) }));
/*  46 */         crashreportsystemdetails.a("Chunk pos hash", new Callable() {
/*     */           public String a() throws Exception {
/*  48 */             return String.valueOf(ChunkCoordIntPair.a(i, j));
/*     */           }
/*     */           
/*     */           public Object call() throws Exception {
/*  52 */             return a();
/*     */           }
/*  54 */         });
/*  55 */         crashreportsystemdetails.a("Structure type", new Callable() {
/*     */           public String a() throws Exception {
/*  57 */             return StructureGenerator.this.getClass().getCanonicalName();
/*     */           }
/*     */           
/*     */           public Object call() throws Exception {
/*  61 */             return a();
/*     */           }
/*  63 */         });
/*  64 */         throw new ReportedException(crashreport);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, ChunkCoordIntPair chunkcoordintpair) {
/*  70 */     a(world);
/*  71 */     int i = (chunkcoordintpair.x << 4) + 8;
/*  72 */     int j = (chunkcoordintpair.z << 4) + 8;
/*  73 */     boolean flag = false;
/*  74 */     Iterator iterator = this.e.values().iterator();
/*     */     
/*  76 */     while (iterator.hasNext()) {
/*  77 */       StructureStart structurestart = (StructureStart)iterator.next();
/*     */       
/*  79 */       if ((structurestart.d()) && (structurestart.a(chunkcoordintpair)) && (structurestart.a().a(i, j, i + 15, j + 15))) {
/*  80 */         structurestart.a(world, random, new StructureBoundingBox(i, j, i + 15, j + 15));
/*  81 */         structurestart.b(chunkcoordintpair);
/*  82 */         flag = true;
/*  83 */         a(structurestart.e(), structurestart.f(), structurestart);
/*     */       }
/*     */     }
/*     */     
/*  87 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean b(BlockPosition blockposition) {
/*  91 */     a(this.c);
/*  92 */     return c(blockposition) != null;
/*     */   }
/*     */   
/*     */   protected StructureStart c(BlockPosition blockposition) {
/*  96 */     Iterator iterator = this.e.values().iterator();
/*     */     Iterator iterator1;
/*  98 */     label94: for (; iterator.hasNext(); 
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 104 */         iterator1.hasNext())
/*     */     {
/*  99 */       StructureStart structurestart = (StructureStart)iterator.next();
/*     */       
/* 101 */       if ((!structurestart.d()) || (!structurestart.a().b(blockposition))) break label94;
/* 102 */       iterator1 = structurestart.b().iterator();
/*     */       
/* 104 */       continue;
/* 105 */       StructurePiece structurepiece = (StructurePiece)iterator1.next();
/*     */       
/* 107 */       if (structurepiece.c().b(blockposition)) {
/* 108 */         return structurestart;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition) {
/* 118 */     a(world);
/* 119 */     Iterator iterator = this.e.values().iterator();
/*     */     
/*     */     StructureStart structurestart;
/*     */     do
/*     */     {
/* 124 */       if (!iterator.hasNext()) {
/* 125 */         return false;
/*     */       }
/*     */       
/* 128 */       structurestart = (StructureStart)iterator.next();
/* 129 */     } while ((!structurestart.d()) || (!structurestart.a().b(blockposition)));
/*     */     
/* 131 */     return true;
/*     */   }
/*     */   
/*     */   public BlockPosition getNearestGeneratedFeature(World world, BlockPosition blockposition) {
/* 135 */     this.c = world;
/* 136 */     a(world);
/* 137 */     this.b.setSeed(world.getSeed());
/* 138 */     long i = this.b.nextLong();
/* 139 */     long j = this.b.nextLong();
/* 140 */     long k = (blockposition.getX() >> 4) * i;
/* 141 */     long l = (blockposition.getZ() >> 4) * j;
/*     */     
/* 143 */     this.b.setSeed(k ^ l ^ world.getSeed());
/* 144 */     a(world, blockposition.getX() >> 4, blockposition.getZ() >> 4, 0, 0, null);
/* 145 */     double d0 = Double.MAX_VALUE;
/* 146 */     BlockPosition blockposition1 = null;
/* 147 */     Iterator iterator = this.e.values().iterator();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 152 */     while (iterator.hasNext()) {
/* 153 */       StructureStart structurestart = (StructureStart)iterator.next();
/*     */       
/* 155 */       if (structurestart.d()) {
/* 156 */         StructurePiece structurepiece = (StructurePiece)structurestart.b().get(0);
/*     */         
/* 158 */         BlockPosition blockposition2 = structurepiece.a();
/* 159 */         double d1 = blockposition2.i(blockposition);
/* 160 */         if (d1 < d0) {
/* 161 */           d0 = d1;
/* 162 */           blockposition1 = blockposition2;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 167 */     if (blockposition1 != null) {
/* 168 */       return blockposition1;
/*     */     }
/* 170 */     List list = z_();
/*     */     
/* 172 */     if (list != null) {
/* 173 */       BlockPosition blockposition3 = null;
/* 174 */       Iterator iterator1 = list.iterator();
/*     */       
/* 176 */       while (iterator1.hasNext()) {
/* 177 */         BlockPosition blockposition2 = (BlockPosition)iterator1.next();
/* 178 */         double d1 = blockposition2.i(blockposition);
/* 179 */         if (d1 < d0) {
/* 180 */           d0 = d1;
/* 181 */           blockposition3 = blockposition2;
/*     */         }
/*     */       }
/*     */       
/* 185 */       return blockposition3;
/*     */     }
/* 187 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   protected List<BlockPosition> z_()
/*     */   {
/* 193 */     return null;
/*     */   }
/*     */   
/*     */   private void a(World world) {
/* 197 */     if (this.d == null)
/*     */     {
/* 199 */       if ((world.spigotConfig.saveStructureInfo) && (!a().equals("Mineshaft")))
/*     */       {
/* 201 */         this.d = ((PersistentStructure)world.a(PersistentStructure.class, a()));
/*     */       }
/*     */       else {
/* 204 */         this.d = new PersistentStructure(a());
/*     */       }
/*     */       
/* 207 */       if (this.d == null) {
/* 208 */         this.d = new PersistentStructure(a());
/* 209 */         world.a(a(), this.d);
/*     */       } else {
/* 211 */         NBTTagCompound nbttagcompound = this.d.a();
/* 212 */         Iterator iterator = nbttagcompound.c().iterator();
/*     */         
/* 214 */         while (iterator.hasNext()) {
/* 215 */           String s = (String)iterator.next();
/* 216 */           NBTBase nbtbase = nbttagcompound.get(s);
/*     */           
/* 218 */           if (nbtbase.getTypeId() == 10) {
/* 219 */             NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtbase;
/*     */             
/* 221 */             if ((nbttagcompound1.hasKey("ChunkX")) && (nbttagcompound1.hasKey("ChunkZ"))) {
/* 222 */               int i = nbttagcompound1.getInt("ChunkX");
/* 223 */               int j = nbttagcompound1.getInt("ChunkZ");
/* 224 */               StructureStart structurestart = WorldGenFactory.a(nbttagcompound1, world);
/*     */               
/* 226 */               if (structurestart != null) {
/* 227 */                 this.e.put(Long.valueOf(ChunkCoordIntPair.a(i, j)), structurestart);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(int i, int j, StructureStart structurestart)
/*     */   {
/* 238 */     this.d.a(structurestart.a(i, j), i, j);
/* 239 */     this.d.c();
/*     */   }
/*     */   
/*     */   protected abstract boolean a(int paramInt1, int paramInt2);
/*     */   
/*     */   protected abstract StructureStart b(int paramInt1, int paramInt2);
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\StructureGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */