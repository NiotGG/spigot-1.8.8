/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StructureStart
/*     */ {
/*  14 */   protected LinkedList<StructurePiece> a = new LinkedList();
/*     */   
/*     */   protected StructureBoundingBox b;
/*     */   private int c;
/*     */   private int d;
/*     */   
/*     */   public StructureStart() {}
/*     */   
/*     */   public StructureStart(int paramInt1, int paramInt2)
/*     */   {
/*  24 */     this.c = paramInt1;
/*  25 */     this.d = paramInt2;
/*     */   }
/*     */   
/*     */   public StructureBoundingBox a() {
/*  29 */     return this.b;
/*     */   }
/*     */   
/*     */   public LinkedList<StructurePiece> b() {
/*  33 */     return this.a;
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/*  37 */     Iterator localIterator = this.a.iterator();
/*  38 */     while (localIterator.hasNext()) {
/*  39 */       StructurePiece localStructurePiece = (StructurePiece)localIterator.next();
/*  40 */       if ((localStructurePiece.c().a(paramStructureBoundingBox)) && 
/*  41 */         (!localStructurePiece.a(paramWorld, paramRandom, paramStructureBoundingBox))) {
/*  42 */         localIterator.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void c()
/*     */   {
/*  49 */     this.b = StructureBoundingBox.a();
/*     */     
/*  51 */     for (StructurePiece localStructurePiece : this.a) {
/*  52 */       this.b.b(localStructurePiece.c());
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTTagCompound a(int paramInt1, int paramInt2) {
/*  57 */     NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/*     */     
/*  59 */     localNBTTagCompound.setString("id", WorldGenFactory.a(this));
/*  60 */     localNBTTagCompound.setInt("ChunkX", paramInt1);
/*  61 */     localNBTTagCompound.setInt("ChunkZ", paramInt2);
/*  62 */     localNBTTagCompound.set("BB", this.b.g());
/*     */     
/*  64 */     NBTTagList localNBTTagList = new NBTTagList();
/*  65 */     for (StructurePiece localStructurePiece : this.a) {
/*  66 */       localNBTTagList.add(localStructurePiece.b());
/*     */     }
/*  68 */     localNBTTagCompound.set("Children", localNBTTagList);
/*     */     
/*  70 */     a(localNBTTagCompound);
/*     */     
/*  72 */     return localNBTTagCompound;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {}
/*     */   
/*     */   public void a(World paramWorld, NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  79 */     this.c = paramNBTTagCompound.getInt("ChunkX");
/*  80 */     this.d = paramNBTTagCompound.getInt("ChunkZ");
/*  81 */     if (paramNBTTagCompound.hasKey("BB")) {
/*  82 */       this.b = new StructureBoundingBox(paramNBTTagCompound.getIntArray("BB"));
/*     */     }
/*     */     
/*  85 */     NBTTagList localNBTTagList = paramNBTTagCompound.getList("Children", 10);
/*  86 */     for (int i = 0; i < localNBTTagList.size(); i++) {
/*  87 */       this.a.add(WorldGenFactory.b(localNBTTagList.get(i), paramWorld));
/*     */     }
/*     */     
/*  90 */     b(paramNBTTagCompound);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {}
/*     */   
/*     */   protected void a(World paramWorld, Random paramRandom, int paramInt)
/*     */   {
/*  97 */     int i = paramWorld.F() - paramInt;
/*     */     
/*     */ 
/* 100 */     int j = this.b.d() + 1;
/*     */     
/* 102 */     if (j < i) {
/* 103 */       j += paramRandom.nextInt(i - j);
/*     */     }
/*     */     
/*     */ 
/* 107 */     int k = j - this.b.e;
/* 108 */     this.b.a(0, k, 0);
/* 109 */     for (StructurePiece localStructurePiece : this.a) {
/* 110 */       localStructurePiece.a(0, k, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 115 */     int i = paramInt2 - paramInt1 + 1 - this.b.d();
/* 116 */     int j = 1;
/*     */     
/* 118 */     if (i > 1) {
/* 119 */       j = paramInt1 + paramRandom.nextInt(i);
/*     */     } else {
/* 121 */       j = paramInt1;
/*     */     }
/*     */     
/*     */ 
/* 125 */     int k = j - this.b.b;
/* 126 */     this.b.a(0, k, 0);
/* 127 */     for (StructurePiece localStructurePiece : this.a) {
/* 128 */       localStructurePiece.a(0, k, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 133 */     return true;
/*     */   }
/*     */   
/*     */   public boolean a(ChunkCoordIntPair paramChunkCoordIntPair) {
/* 137 */     return true;
/*     */   }
/*     */   
/*     */   public void b(ChunkCoordIntPair paramChunkCoordIntPair) {}
/*     */   
/*     */   public int e()
/*     */   {
/* 144 */     return this.c;
/*     */   }
/*     */   
/*     */   public int f() {
/* 148 */     return this.d;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\StructureStart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */