/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class WorldGenVillage extends StructureGenerator
/*     */ {
/*  12 */   public static final List<BiomeBase> d = java.util.Arrays.asList(new BiomeBase[] { BiomeBase.PLAINS, BiomeBase.DESERT, BiomeBase.SAVANNA });
/*     */   private int f;
/*     */   private int g;
/*     */   private int h;
/*     */   
/*     */   public WorldGenVillage() {
/*  18 */     this.g = 32;
/*  19 */     this.h = 8;
/*     */   }
/*     */   
/*     */   public WorldGenVillage(Map<String, String> map) {
/*  23 */     this();
/*  24 */     Iterator iterator = map.entrySet().iterator();
/*     */     
/*  26 */     while (iterator.hasNext()) {
/*  27 */       Map.Entry entry = (Map.Entry)iterator.next();
/*     */       
/*  29 */       if (((String)entry.getKey()).equals("size")) {
/*  30 */         this.f = MathHelper.a((String)entry.getValue(), this.f, 0);
/*  31 */       } else if (((String)entry.getKey()).equals("distance")) {
/*  32 */         this.g = MathHelper.a((String)entry.getValue(), this.g, this.h + 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String a()
/*     */   {
/*  39 */     return "Village";
/*     */   }
/*     */   
/*     */   protected boolean a(int i, int j) {
/*  43 */     int k = i;
/*  44 */     int l = j;
/*     */     
/*  46 */     if (i < 0) {
/*  47 */       i -= this.g - 1;
/*     */     }
/*     */     
/*  50 */     if (j < 0) {
/*  51 */       j -= this.g - 1;
/*     */     }
/*     */     
/*  54 */     int i1 = i / this.g;
/*  55 */     int j1 = j / this.g;
/*  56 */     Random random = this.c.a(i1, j1, this.c.spigotConfig.villageSeed);
/*     */     
/*  58 */     i1 *= this.g;
/*  59 */     j1 *= this.g;
/*  60 */     i1 += random.nextInt(this.g - this.h);
/*  61 */     j1 += random.nextInt(this.g - this.h);
/*  62 */     if ((k == i1) && (l == j1)) {
/*  63 */       boolean flag = this.c.getWorldChunkManager().a(k * 16 + 8, l * 16 + 8, 0, d);
/*     */       
/*  65 */       if (flag) {
/*  66 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  70 */     return false;
/*     */   }
/*     */   
/*     */   protected StructureStart b(int i, int j) {
/*  74 */     return new WorldGenVillageStart(this.c, this.b, i, j, this.f);
/*     */   }
/*     */   
/*     */   public static class WorldGenVillageStart extends StructureStart
/*     */   {
/*     */     private boolean c;
/*     */     
/*     */     public WorldGenVillageStart() {}
/*     */     
/*     */     public WorldGenVillageStart(World world, Random random, int i, int j, int k) {
/*  84 */       super(j);
/*  85 */       List list = WorldGenVillagePieces.a(random, k);
/*  86 */       WorldGenVillagePieces.WorldGenVillageStartPiece worldgenvillagepieces_worldgenvillagestartpiece = new WorldGenVillagePieces.WorldGenVillageStartPiece(world.getWorldChunkManager(), 0, random, (i << 4) + 2, (j << 4) + 2, list, k);
/*     */       
/*  88 */       this.a.add(worldgenvillagepieces_worldgenvillagestartpiece);
/*  89 */       worldgenvillagepieces_worldgenvillagestartpiece.a(worldgenvillagepieces_worldgenvillagestartpiece, this.a, random);
/*  90 */       List list1 = worldgenvillagepieces_worldgenvillagestartpiece.g;
/*  91 */       List list2 = worldgenvillagepieces_worldgenvillagestartpiece.f;
/*     */       
/*     */ 
/*     */ 
/*  95 */       while ((!list1.isEmpty()) || (!list2.isEmpty()))
/*     */       {
/*     */ 
/*  98 */         if (list1.isEmpty()) {
/*  99 */           int l = random.nextInt(list2.size());
/* 100 */           StructurePiece structurepiece = (StructurePiece)list2.remove(l);
/* 101 */           structurepiece.a(worldgenvillagepieces_worldgenvillagestartpiece, this.a, random);
/*     */         } else {
/* 103 */           int l = random.nextInt(list1.size());
/* 104 */           StructurePiece structurepiece = (StructurePiece)list1.remove(l);
/* 105 */           structurepiece.a(worldgenvillagepieces_worldgenvillagestartpiece, this.a, random);
/*     */         }
/*     */       }
/*     */       
/* 109 */       c();
/* 110 */       int l = 0;
/* 111 */       Iterator iterator = this.a.iterator();
/*     */       
/* 113 */       while (iterator.hasNext()) {
/* 114 */         StructurePiece structurepiece1 = (StructurePiece)iterator.next();
/*     */         
/* 116 */         if (!(structurepiece1 instanceof WorldGenVillagePieces.WorldGenVillageRoadPiece)) {
/* 117 */           l++;
/*     */         }
/*     */       }
/*     */       
/* 121 */       this.c = (l > 2);
/*     */     }
/*     */     
/*     */     public boolean d() {
/* 125 */       return this.c;
/*     */     }
/*     */     
/*     */     public void a(NBTTagCompound nbttagcompound) {
/* 129 */       super.a(nbttagcompound);
/* 130 */       nbttagcompound.setBoolean("Valid", this.c);
/*     */     }
/*     */     
/*     */     public void b(NBTTagCompound nbttagcompound) {
/* 134 */       super.b(nbttagcompound);
/* 135 */       this.c = nbttagcompound.getBoolean("Valid");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenVillage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */