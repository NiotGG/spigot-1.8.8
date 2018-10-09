/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenNether
/*    */   extends StructureGenerator
/*    */ {
/* 15 */   private List<BiomeBase.BiomeMeta> d = Lists.newArrayList();
/*    */   
/*    */ 
/*    */   public WorldGenNether()
/*    */   {
/* 20 */     this.d.add(new BiomeBase.BiomeMeta(EntityBlaze.class, 10, 2, 3));
/* 21 */     this.d.add(new BiomeBase.BiomeMeta(EntityPigZombie.class, 5, 4, 4));
/* 22 */     this.d.add(new BiomeBase.BiomeMeta(EntitySkeleton.class, 10, 4, 4));
/* 23 */     this.d.add(new BiomeBase.BiomeMeta(EntityMagmaCube.class, 3, 4, 4));
/*    */   }
/*    */   
/*    */   public String a()
/*    */   {
/* 28 */     return "Fortress";
/*    */   }
/*    */   
/*    */   public List<BiomeBase.BiomeMeta> b() {
/* 32 */     return this.d;
/*    */   }
/*    */   
/*    */   protected boolean a(int paramInt1, int paramInt2)
/*    */   {
/* 37 */     int i = paramInt1 >> 4;
/* 38 */     int j = paramInt2 >> 4;
/*    */     
/* 40 */     this.b.setSeed(i ^ j << 4 ^ this.c.getSeed());
/* 41 */     this.b.nextInt();
/*    */     
/* 43 */     if (this.b.nextInt(3) != 0) {
/* 44 */       return false;
/*    */     }
/* 46 */     if (paramInt1 != (i << 4) + 4 + this.b.nextInt(8)) {
/* 47 */       return false;
/*    */     }
/* 49 */     if (paramInt2 != (j << 4) + 4 + this.b.nextInt(8)) {
/* 50 */       return false;
/*    */     }
/* 52 */     return true;
/*    */   }
/*    */   
/*    */   protected StructureStart b(int paramInt1, int paramInt2)
/*    */   {
/* 57 */     return new WorldGenNetherStart(this.c, this.b, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static class WorldGenNetherStart
/*    */     extends StructureStart
/*    */   {
/*    */     public WorldGenNetherStart() {}
/*    */     
/*    */ 
/*    */     public WorldGenNetherStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
/*    */     {
/* 70 */       super(paramInt2);
/*    */       
/* 72 */       WorldGenNetherPieces.WorldGenNetherPiece15 localWorldGenNetherPiece15 = new WorldGenNetherPieces.WorldGenNetherPiece15(paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2);
/* 73 */       this.a.add(localWorldGenNetherPiece15);
/* 74 */       localWorldGenNetherPiece15.a(localWorldGenNetherPiece15, this.a, paramRandom);
/*    */       
/* 76 */       List localList = localWorldGenNetherPiece15.e;
/* 77 */       while (!localList.isEmpty()) {
/* 78 */         int i = paramRandom.nextInt(localList.size());
/* 79 */         StructurePiece localStructurePiece = (StructurePiece)localList.remove(i);
/* 80 */         localStructurePiece.a(localWorldGenNetherPiece15, this.a, paramRandom);
/*    */       }
/*    */       
/* 83 */       c();
/* 84 */       a(paramWorld, paramRandom, 48, 70);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenNether.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */