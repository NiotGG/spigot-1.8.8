/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathfinderNormal
/*     */   extends PathfinderAbstract
/*     */ {
/*     */   private boolean f;
/*     */   
/*     */ 
/*     */   private boolean g;
/*     */   
/*     */ 
/*     */   private boolean h;
/*     */   
/*     */ 
/*     */   private boolean i;
/*     */   
/*     */ 
/*     */   private boolean j;
/*     */   
/*     */ 
/*     */ 
/*     */   public void a(IBlockAccess paramIBlockAccess, Entity paramEntity)
/*     */   {
/*  27 */     super.a(paramIBlockAccess, paramEntity);
/*  28 */     this.j = this.h;
/*     */   }
/*     */   
/*     */   public void a()
/*     */   {
/*  33 */     super.a();
/*  34 */     this.h = this.j;
/*     */   }
/*     */   
/*     */ 
/*     */   public PathPoint a(Entity paramEntity)
/*     */   {
/*     */     int k;
/*  41 */     if ((this.i) && (paramEntity.V())) {
/*  42 */       k = (int)paramEntity.getBoundingBox().b;
/*  43 */       BlockPosition.MutableBlockPosition localMutableBlockPosition = new BlockPosition.MutableBlockPosition(MathHelper.floor(paramEntity.locX), k, MathHelper.floor(paramEntity.locZ));
/*  44 */       Block localBlock = this.a.getType(localMutableBlockPosition).getBlock();
/*  45 */       while ((localBlock == Blocks.FLOWING_WATER) || (localBlock == Blocks.WATER)) {
/*  46 */         k++;
/*  47 */         localMutableBlockPosition.c(MathHelper.floor(paramEntity.locX), k, MathHelper.floor(paramEntity.locZ));
/*  48 */         localBlock = this.a.getType(localMutableBlockPosition).getBlock();
/*     */       }
/*  50 */       this.h = false;
/*     */     } else {
/*  52 */       k = MathHelper.floor(paramEntity.getBoundingBox().b + 0.5D);
/*     */     }
/*     */     
/*  55 */     return a(MathHelper.floor(paramEntity.getBoundingBox().a), k, MathHelper.floor(paramEntity.getBoundingBox().c));
/*     */   }
/*     */   
/*     */   public PathPoint a(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  60 */     return a(MathHelper.floor(paramDouble1 - paramEntity.width / 2.0F), MathHelper.floor(paramDouble2), MathHelper.floor(paramDouble3 - paramEntity.width / 2.0F));
/*     */   }
/*     */   
/*     */   public int a(PathPoint[] paramArrayOfPathPoint, Entity paramEntity, PathPoint paramPathPoint1, PathPoint paramPathPoint2, float paramFloat)
/*     */   {
/*  65 */     int k = 0;
/*     */     
/*  67 */     int m = 0;
/*  68 */     if (a(paramEntity, paramPathPoint1.a, paramPathPoint1.b + 1, paramPathPoint1.c) == 1) {
/*  69 */       m = 1;
/*     */     }
/*     */     
/*  72 */     PathPoint localPathPoint1 = a(paramEntity, paramPathPoint1.a, paramPathPoint1.b, paramPathPoint1.c + 1, m);
/*  73 */     PathPoint localPathPoint2 = a(paramEntity, paramPathPoint1.a - 1, paramPathPoint1.b, paramPathPoint1.c, m);
/*  74 */     PathPoint localPathPoint3 = a(paramEntity, paramPathPoint1.a + 1, paramPathPoint1.b, paramPathPoint1.c, m);
/*  75 */     PathPoint localPathPoint4 = a(paramEntity, paramPathPoint1.a, paramPathPoint1.b, paramPathPoint1.c - 1, m);
/*     */     
/*  77 */     if ((localPathPoint1 != null) && (!localPathPoint1.i) && (localPathPoint1.a(paramPathPoint2) < paramFloat)) {
/*  78 */       paramArrayOfPathPoint[(k++)] = localPathPoint1;
/*     */     }
/*  80 */     if ((localPathPoint2 != null) && (!localPathPoint2.i) && (localPathPoint2.a(paramPathPoint2) < paramFloat)) {
/*  81 */       paramArrayOfPathPoint[(k++)] = localPathPoint2;
/*     */     }
/*  83 */     if ((localPathPoint3 != null) && (!localPathPoint3.i) && (localPathPoint3.a(paramPathPoint2) < paramFloat)) {
/*  84 */       paramArrayOfPathPoint[(k++)] = localPathPoint3;
/*     */     }
/*  86 */     if ((localPathPoint4 != null) && (!localPathPoint4.i) && (localPathPoint4.a(paramPathPoint2) < paramFloat)) {
/*  87 */       paramArrayOfPathPoint[(k++)] = localPathPoint4;
/*     */     }
/*     */     
/*  90 */     return k;
/*     */   }
/*     */   
/*     */   private PathPoint a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  95 */     PathPoint localPathPoint = null;
/*  96 */     int k = a(paramEntity, paramInt1, paramInt2, paramInt3);
/*  97 */     if (k == 2) {
/*  98 */       return a(paramInt1, paramInt2, paramInt3);
/*     */     }
/* 100 */     if (k == 1) {
/* 101 */       localPathPoint = a(paramInt1, paramInt2, paramInt3);
/*     */     }
/* 103 */     if ((localPathPoint == null) && (paramInt4 > 0) && (k != -3) && (k != -4) && (a(paramEntity, paramInt1, paramInt2 + paramInt4, paramInt3) == 1)) {
/* 104 */       localPathPoint = a(paramInt1, paramInt2 + paramInt4, paramInt3);
/* 105 */       paramInt2 += paramInt4;
/*     */     }
/*     */     
/* 108 */     if (localPathPoint != null) {
/* 109 */       int m = 0;
/* 110 */       int n = 0;
/*     */       
/* 112 */       while (paramInt2 > 0) {
/* 113 */         n = a(paramEntity, paramInt1, paramInt2 - 1, paramInt3);
/* 114 */         if ((this.h) && (n == -1)) {
/* 115 */           return null;
/*     */         }
/* 117 */         if (n == 1)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 122 */           if (m++ >= paramEntity.aE()) {
/* 123 */             return null;
/*     */           }
/* 125 */           paramInt2--;
/* 126 */           if (paramInt2 > 0) {
/* 127 */             localPathPoint = a(paramInt1, paramInt2, paramInt3);
/*     */           } else {
/* 129 */             return null;
/*     */           }
/*     */         }
/*     */       }
/* 133 */       if (n == -2) {
/* 134 */         return null;
/*     */       }
/*     */     }
/*     */     
/* 138 */     return localPathPoint;
/*     */   }
/*     */   
/*     */   private int a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3) {
/* 142 */     return a(this.a, paramEntity, paramInt1, paramInt2, paramInt3, this.c, this.d, this.e, this.h, this.g, this.f);
/*     */   }
/*     */   
/*     */   public static int a(IBlockAccess paramIBlockAccess, Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 146 */     int k = 0;
/* 147 */     BlockPosition localBlockPosition = new BlockPosition(paramEntity);
/*     */     
/* 149 */     BlockPosition.MutableBlockPosition localMutableBlockPosition = new BlockPosition.MutableBlockPosition();
/* 150 */     for (int m = paramInt1; m < paramInt1 + paramInt4; m++)
/* 151 */       for (int n = paramInt2; n < paramInt2 + paramInt5; n++)
/* 152 */         for (int i1 = paramInt3; i1 < paramInt3 + paramInt6; i1++) {
/* 153 */           localMutableBlockPosition.c(m, n, i1);
/* 154 */           Block localBlock = paramIBlockAccess.getType(localMutableBlockPosition).getBlock();
/* 155 */           if (localBlock.getMaterial() != Material.AIR)
/*     */           {
/*     */ 
/* 158 */             if ((localBlock == Blocks.TRAPDOOR) || (localBlock == Blocks.IRON_TRAPDOOR)) {
/* 159 */               k = 1;
/* 160 */             } else if ((localBlock == Blocks.FLOWING_WATER) || (localBlock == Blocks.WATER)) {
/* 161 */               if (paramBoolean1) {
/* 162 */                 return -1;
/*     */               }
/* 164 */               k = 1;
/*     */             }
/* 166 */             else if ((!paramBoolean3) && ((localBlock instanceof BlockDoor)) && (localBlock.getMaterial() == Material.WOOD)) {
/* 167 */               return 0;
/*     */             }
/*     */             
/* 170 */             if ((paramEntity.world.getType(localMutableBlockPosition).getBlock() instanceof BlockMinecartTrackAbstract)) {
/* 171 */               if ((!(paramEntity.world.getType(localBlockPosition).getBlock() instanceof BlockMinecartTrackAbstract)) && (!(paramEntity.world.getType(localBlockPosition.down()).getBlock() instanceof BlockMinecartTrackAbstract)))
/*     */               {
/*     */ 
/* 174 */                 return -3;
/*     */               }
/*     */               
/*     */             }
/* 178 */             else if (!localBlock.b(paramIBlockAccess, localMutableBlockPosition))
/*     */             {
/*     */ 
/* 181 */               if ((!paramBoolean2) || (!(localBlock instanceof BlockDoor)) || (localBlock.getMaterial() != Material.WOOD))
/*     */               {
/*     */ 
/*     */ 
/* 185 */                 if (((localBlock instanceof BlockFence)) || ((localBlock instanceof BlockFenceGate)) || ((localBlock instanceof BlockCobbleWall))) {
/* 186 */                   return -3;
/*     */                 }
/*     */                 
/* 189 */                 if ((localBlock == Blocks.TRAPDOOR) || (localBlock == Blocks.IRON_TRAPDOOR)) {
/* 190 */                   return -4;
/*     */                 }
/* 192 */                 Material localMaterial = localBlock.getMaterial();
/* 193 */                 if (localMaterial == Material.LAVA) {
/* 194 */                   if (!paramEntity.ab())
/*     */                   {
/*     */ 
/* 197 */                     return -2; }
/*     */                 } else
/* 199 */                   return 0;
/*     */               } }
/*     */           }
/*     */         }
/* 203 */     return k != 0 ? 2 : 1;
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/* 207 */     this.f = paramBoolean;
/*     */   }
/*     */   
/*     */   public void b(boolean paramBoolean) {
/* 211 */     this.g = paramBoolean;
/*     */   }
/*     */   
/*     */   public void c(boolean paramBoolean) {
/* 215 */     this.h = paramBoolean;
/*     */   }
/*     */   
/*     */   public void d(boolean paramBoolean) {
/* 219 */     this.i = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean b() {
/* 223 */     return this.f;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean d()
/*     */   {
/* 231 */     return this.i;
/*     */   }
/*     */   
/*     */   public boolean e() {
/* 235 */     return this.h;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderNormal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */