/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Objects.ToStringHelper;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShapeDetector
/*     */ {
/*     */   private final Predicate<ShapeDetectorBlock>[][][] a;
/*     */   private final int b;
/*     */   private final int c;
/*     */   private final int d;
/*     */   
/*     */   public ShapeDetector(Predicate<ShapeDetectorBlock>[][][] paramArrayOfPredicate)
/*     */   {
/*  22 */     this.a = paramArrayOfPredicate;
/*     */     
/*  24 */     this.b = paramArrayOfPredicate.length;
/*     */     
/*  26 */     if (this.b > 0) {
/*  27 */       this.c = paramArrayOfPredicate[0].length;
/*     */       
/*  29 */       if (this.c > 0) {
/*  30 */         this.d = paramArrayOfPredicate[0][0].length;
/*     */       } else {
/*  32 */         this.d = 0;
/*     */       }
/*     */     } else {
/*  35 */       this.c = 0;
/*  36 */       this.d = 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int b()
/*     */   {
/*  45 */     return this.c;
/*     */   }
/*     */   
/*     */   public int c() {
/*  49 */     return this.d;
/*     */   }
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
/*     */   private ShapeDetectorCollection a(BlockPosition paramBlockPosition, EnumDirection paramEnumDirection1, EnumDirection paramEnumDirection2, LoadingCache<BlockPosition, ShapeDetectorBlock> paramLoadingCache)
/*     */   {
/*  64 */     for (int i = 0; i < this.d; i++) {
/*  65 */       for (int j = 0; j < this.c; j++) {
/*  66 */         for (int k = 0; k < this.b; k++) {
/*  67 */           if (!this.a[k][j][i].apply(paramLoadingCache.getUnchecked(a(paramBlockPosition, paramEnumDirection1, paramEnumDirection2, i, j, k)))) {
/*  68 */             return null;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  74 */     return new ShapeDetectorCollection(paramBlockPosition, paramEnumDirection1, paramEnumDirection2, paramLoadingCache, this.d, this.c, this.b);
/*     */   }
/*     */   
/*     */   public ShapeDetectorCollection a(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  79 */     LoadingCache localLoadingCache = a(paramWorld, false);
/*     */     
/*  81 */     int i = Math.max(Math.max(this.d, this.c), this.b);
/*     */     
/*  83 */     for (BlockPosition localBlockPosition : BlockPosition.a(paramBlockPosition, paramBlockPosition.a(i - 1, i - 1, i - 1))) {
/*  84 */       for (EnumDirection localEnumDirection1 : EnumDirection.values()) {
/*  85 */         for (EnumDirection localEnumDirection2 : EnumDirection.values()) {
/*  86 */           if ((localEnumDirection2 != localEnumDirection1) && (localEnumDirection2 != localEnumDirection1.opposite()))
/*     */           {
/*     */ 
/*     */ 
/*  90 */             ShapeDetectorCollection localShapeDetectorCollection = a(localBlockPosition, localEnumDirection1, localEnumDirection2, localLoadingCache);
/*  91 */             if (localShapeDetectorCollection != null) {
/*  92 */               return localShapeDetectorCollection;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  98 */     return null;
/*     */   }
/*     */   
/*     */   public static LoadingCache<BlockPosition, ShapeDetectorBlock> a(World paramWorld, boolean paramBoolean) {
/* 102 */     return CacheBuilder.newBuilder().build(new BlockLoader(paramWorld, paramBoolean));
/*     */   }
/*     */   
/*     */   protected static BlockPosition a(BlockPosition paramBlockPosition, EnumDirection paramEnumDirection1, EnumDirection paramEnumDirection2, int paramInt1, int paramInt2, int paramInt3) {
/* 106 */     if ((paramEnumDirection1 == paramEnumDirection2) || (paramEnumDirection1 == paramEnumDirection2.opposite())) {
/* 107 */       throw new IllegalArgumentException("Invalid forwards & up combination");
/*     */     }
/*     */     
/* 110 */     BaseBlockPosition localBaseBlockPosition1 = new BaseBlockPosition(paramEnumDirection1.getAdjacentX(), paramEnumDirection1.getAdjacentY(), paramEnumDirection1.getAdjacentZ());
/* 111 */     BaseBlockPosition localBaseBlockPosition2 = new BaseBlockPosition(paramEnumDirection2.getAdjacentX(), paramEnumDirection2.getAdjacentY(), paramEnumDirection2.getAdjacentZ());
/* 112 */     BaseBlockPosition localBaseBlockPosition3 = localBaseBlockPosition1.d(localBaseBlockPosition2);
/*     */     
/* 114 */     return paramBlockPosition.a(localBaseBlockPosition2.getX() * -paramInt2 + localBaseBlockPosition3.getX() * paramInt1 + localBaseBlockPosition1.getX() * paramInt3, localBaseBlockPosition2.getY() * -paramInt2 + localBaseBlockPosition3.getY() * paramInt1 + localBaseBlockPosition1.getY() * paramInt3, localBaseBlockPosition2.getZ() * -paramInt2 + localBaseBlockPosition3.getZ() * paramInt1 + localBaseBlockPosition1.getZ() * paramInt3);
/*     */   }
/*     */   
/*     */ 
/*     */   static class BlockLoader
/*     */     extends CacheLoader<BlockPosition, ShapeDetectorBlock>
/*     */   {
/*     */     private final World a;
/*     */     private final boolean b;
/*     */     
/*     */     public BlockLoader(World paramWorld, boolean paramBoolean)
/*     */     {
/* 126 */       this.a = paramWorld;
/* 127 */       this.b = paramBoolean;
/*     */     }
/*     */     
/*     */     public ShapeDetectorBlock a(BlockPosition paramBlockPosition) throws Exception
/*     */     {
/* 132 */       return new ShapeDetectorBlock(this.a, paramBlockPosition, this.b);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ShapeDetectorCollection {
/*     */     private final BlockPosition a;
/*     */     private final EnumDirection b;
/*     */     private final EnumDirection c;
/*     */     private final LoadingCache<BlockPosition, ShapeDetectorBlock> d;
/*     */     private final int e;
/*     */     private final int f;
/*     */     private final int g;
/*     */     
/*     */     public ShapeDetectorCollection(BlockPosition paramBlockPosition, EnumDirection paramEnumDirection1, EnumDirection paramEnumDirection2, LoadingCache<BlockPosition, ShapeDetectorBlock> paramLoadingCache, int paramInt1, int paramInt2, int paramInt3) {
/* 146 */       this.a = paramBlockPosition;
/* 147 */       this.b = paramEnumDirection1;
/* 148 */       this.c = paramEnumDirection2;
/* 149 */       this.d = paramLoadingCache;
/* 150 */       this.e = paramInt1;
/* 151 */       this.f = paramInt2;
/* 152 */       this.g = paramInt3;
/*     */     }
/*     */     
/*     */     public BlockPosition a() {
/* 156 */       return this.a;
/*     */     }
/*     */     
/*     */     public EnumDirection b() {
/* 160 */       return this.b;
/*     */     }
/*     */     
/*     */     public EnumDirection c() {
/* 164 */       return this.c;
/*     */     }
/*     */     
/*     */     public int d() {
/* 168 */       return this.e;
/*     */     }
/*     */     
/*     */     public int e() {
/* 172 */       return this.f;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public ShapeDetectorBlock a(int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 180 */       return (ShapeDetectorBlock)this.d.getUnchecked(ShapeDetector.a(this.a, b(), c(), paramInt1, paramInt2, paramInt3));
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 185 */       return Objects.toStringHelper(this).add("up", this.c).add("forwards", this.b).add("frontTopLeft", this.a).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ShapeDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */