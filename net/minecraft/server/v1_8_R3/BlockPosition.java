/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPosition
/*     */   extends BaseBlockPosition
/*     */ {
/*  13 */   public static final BlockPosition ZERO = new BlockPosition(0, 0, 0);
/*     */   
/*  15 */   private static final int c = 1 + MathHelper.c(MathHelper.b(30000000));
/*  16 */   private static final int d = c;
/*  17 */   private static final int e = 64 - c - d;
/*     */   
/*  19 */   private static final int f = 0 + d;
/*  20 */   private static final int g = f + e;
/*  21 */   private static final long h = (1L << c) - 1L;
/*  22 */   private static final long i = (1L << e) - 1L;
/*  23 */   private static final long j = (1L << d) - 1L;
/*     */   
/*     */   public BlockPosition(int paramInt1, int paramInt2, int paramInt3) {
/*  26 */     super(paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public BlockPosition(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  30 */     super(paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */   
/*     */   public BlockPosition(Entity paramEntity) {
/*  34 */     this(paramEntity.locX, paramEntity.locY, paramEntity.locZ);
/*     */   }
/*     */   
/*     */   public BlockPosition(Vec3D paramVec3D) {
/*  38 */     this(paramVec3D.a, paramVec3D.b, paramVec3D.c);
/*     */   }
/*     */   
/*     */   public BlockPosition(BaseBlockPosition paramBaseBlockPosition) {
/*  42 */     this(paramBaseBlockPosition.getX(), paramBaseBlockPosition.getY(), paramBaseBlockPosition.getZ());
/*     */   }
/*     */   
/*     */   public BlockPosition a(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  47 */     if ((paramDouble1 == 0.0D) && (paramDouble2 == 0.0D) && (paramDouble3 == 0.0D)) {
/*  48 */       return this;
/*     */     }
/*  50 */     return new BlockPosition(getX() + paramDouble1, getY() + paramDouble2, getZ() + paramDouble3);
/*     */   }
/*     */   
/*     */   public BlockPosition a(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  55 */     if ((paramInt1 == 0) && (paramInt2 == 0) && (paramInt3 == 0)) {
/*  56 */       return this;
/*     */     }
/*  58 */     return new BlockPosition(getX() + paramInt1, getY() + paramInt2, getZ() + paramInt3);
/*     */   }
/*     */   
/*     */   public BlockPosition a(BaseBlockPosition paramBaseBlockPosition)
/*     */   {
/*  63 */     if ((paramBaseBlockPosition.getX() == 0) && (paramBaseBlockPosition.getY() == 0) && (paramBaseBlockPosition.getZ() == 0)) {
/*  64 */       return this;
/*     */     }
/*  66 */     return new BlockPosition(getX() + paramBaseBlockPosition.getX(), getY() + paramBaseBlockPosition.getY(), getZ() + paramBaseBlockPosition.getZ());
/*     */   }
/*     */   
/*     */   public BlockPosition b(BaseBlockPosition paramBaseBlockPosition)
/*     */   {
/*  71 */     if ((paramBaseBlockPosition.getX() == 0) && (paramBaseBlockPosition.getY() == 0) && (paramBaseBlockPosition.getZ() == 0)) {
/*  72 */       return this;
/*     */     }
/*  74 */     return new BlockPosition(getX() - paramBaseBlockPosition.getX(), getY() - paramBaseBlockPosition.getY(), getZ() - paramBaseBlockPosition.getZ());
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
/*     */   public BlockPosition up()
/*     */   {
/*  89 */     return up(1);
/*     */   }
/*     */   
/*     */   public BlockPosition up(int paramInt)
/*     */   {
/*  94 */     return shift(EnumDirection.UP, paramInt);
/*     */   }
/*     */   
/*     */   public BlockPosition down()
/*     */   {
/*  99 */     return down(1);
/*     */   }
/*     */   
/*     */   public BlockPosition down(int paramInt)
/*     */   {
/* 104 */     return shift(EnumDirection.DOWN, paramInt);
/*     */   }
/*     */   
/*     */   public BlockPosition north()
/*     */   {
/* 109 */     return north(1);
/*     */   }
/*     */   
/*     */   public BlockPosition north(int paramInt)
/*     */   {
/* 114 */     return shift(EnumDirection.NORTH, paramInt);
/*     */   }
/*     */   
/*     */   public BlockPosition south()
/*     */   {
/* 119 */     return south(1);
/*     */   }
/*     */   
/*     */   public BlockPosition south(int paramInt)
/*     */   {
/* 124 */     return shift(EnumDirection.SOUTH, paramInt);
/*     */   }
/*     */   
/*     */   public BlockPosition west()
/*     */   {
/* 129 */     return west(1);
/*     */   }
/*     */   
/*     */   public BlockPosition west(int paramInt)
/*     */   {
/* 134 */     return shift(EnumDirection.WEST, paramInt);
/*     */   }
/*     */   
/*     */   public BlockPosition east()
/*     */   {
/* 139 */     return east(1);
/*     */   }
/*     */   
/*     */   public BlockPosition east(int paramInt)
/*     */   {
/* 144 */     return shift(EnumDirection.EAST, paramInt);
/*     */   }
/*     */   
/*     */   public BlockPosition shift(EnumDirection paramEnumDirection)
/*     */   {
/* 149 */     return shift(paramEnumDirection, 1);
/*     */   }
/*     */   
/*     */   public BlockPosition shift(EnumDirection paramEnumDirection, int paramInt)
/*     */   {
/* 154 */     if (paramInt == 0) {
/* 155 */       return this;
/*     */     }
/* 157 */     return new BlockPosition(getX() + paramEnumDirection.getAdjacentX() * paramInt, getY() + paramEnumDirection.getAdjacentY() * paramInt, getZ() + paramEnumDirection.getAdjacentZ() * paramInt);
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
/*     */ 
/*     */   public BlockPosition c(BaseBlockPosition paramBaseBlockPosition)
/*     */   {
/* 173 */     return new BlockPosition(getY() * paramBaseBlockPosition.getZ() - getZ() * paramBaseBlockPosition.getY(), getZ() * paramBaseBlockPosition.getX() - getX() * paramBaseBlockPosition.getZ(), getX() * paramBaseBlockPosition.getY() - getY() * paramBaseBlockPosition.getX());
/*     */   }
/*     */   
/*     */   public long asLong()
/*     */   {
/* 178 */     return (getX() & h) << g | (getY() & i) << f | (getZ() & j) << 0;
/*     */   }
/*     */   
/*     */   public static BlockPosition fromLong(long paramLong) {
/* 182 */     int k = (int)(paramLong << 64 - g - c >> 64 - c);
/* 183 */     int m = (int)(paramLong << 64 - f - e >> 64 - e);
/* 184 */     int n = (int)(paramLong << 64 - d >> 64 - d);
/*     */     
/* 186 */     return new BlockPosition(k, m, n);
/*     */   }
/*     */   
/*     */   public static Iterable<BlockPosition> a(BlockPosition paramBlockPosition1, BlockPosition paramBlockPosition2) {
/* 190 */     BlockPosition localBlockPosition1 = new BlockPosition(Math.min(paramBlockPosition1.getX(), paramBlockPosition2.getX()), Math.min(paramBlockPosition1.getY(), paramBlockPosition2.getY()), Math.min(paramBlockPosition1.getZ(), paramBlockPosition2.getZ()));
/* 191 */     final BlockPosition localBlockPosition2 = new BlockPosition(Math.max(paramBlockPosition1.getX(), paramBlockPosition2.getX()), Math.max(paramBlockPosition1.getY(), paramBlockPosition2.getY()), Math.max(paramBlockPosition1.getZ(), paramBlockPosition2.getZ()));
/*     */     
/* 193 */     new Iterable()
/*     */     {
/*     */       public Iterator<BlockPosition> iterator() {
/* 196 */         new AbstractIterator() {
/* 197 */           private BlockPosition b = null;
/*     */           
/*     */           protected BlockPosition a()
/*     */           {
/* 201 */             if (this.b == null)
/*     */             {
/* 203 */               this.b = BlockPosition.1.this.a;
/* 204 */               return this.b; }
/* 205 */             if (this.b.equals(BlockPosition.1.this.b))
/*     */             {
/* 207 */               return (BlockPosition)endOfData();
/*     */             }
/*     */             
/* 210 */             int i = this.b.getX();
/* 211 */             int j = this.b.getY();
/* 212 */             int k = this.b.getZ();
/* 213 */             if (i < BlockPosition.1.this.b.getX()) {
/* 214 */               i++;
/* 215 */             } else if (j < BlockPosition.1.this.b.getY()) {
/* 216 */               i = BlockPosition.1.this.a.getX();
/* 217 */               j++;
/* 218 */             } else if (k < BlockPosition.1.this.b.getZ()) {
/* 219 */               i = BlockPosition.1.this.a.getX();
/* 220 */               j = BlockPosition.1.this.a.getY();
/* 221 */               k++;
/*     */             }
/* 223 */             this.b = new BlockPosition(i, j, k);
/* 224 */             return this.b;
/*     */           }
/*     */         };
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public static final class MutableBlockPosition extends BlockPosition
/*     */   {
/*     */     private int c;
/*     */     private int d;
/*     */     private int e;
/*     */     
/*     */     public MutableBlockPosition() {
/* 238 */       this(0, 0, 0);
/*     */     }
/*     */     
/*     */     public MutableBlockPosition(int paramInt1, int paramInt2, int paramInt3) {
/* 242 */       super(0, 0);
/* 243 */       this.c = paramInt1;
/* 244 */       this.d = paramInt2;
/* 245 */       this.e = paramInt3;
/*     */     }
/*     */     
/*     */     public int getX()
/*     */     {
/* 250 */       return this.c;
/*     */     }
/*     */     
/*     */     public int getY()
/*     */     {
/* 255 */       return this.d;
/*     */     }
/*     */     
/*     */     public int getZ()
/*     */     {
/* 260 */       return this.e;
/*     */     }
/*     */     
/*     */     public MutableBlockPosition c(int paramInt1, int paramInt2, int paramInt3) {
/* 264 */       this.c = paramInt1;
/* 265 */       this.d = paramInt2;
/* 266 */       this.e = paramInt3;
/* 267 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   public static Iterable<MutableBlockPosition> b(BlockPosition paramBlockPosition1, BlockPosition paramBlockPosition2) {
/* 272 */     BlockPosition localBlockPosition1 = new BlockPosition(Math.min(paramBlockPosition1.getX(), paramBlockPosition2.getX()), Math.min(paramBlockPosition1.getY(), paramBlockPosition2.getY()), Math.min(paramBlockPosition1.getZ(), paramBlockPosition2.getZ()));
/* 273 */     final BlockPosition localBlockPosition2 = new BlockPosition(Math.max(paramBlockPosition1.getX(), paramBlockPosition2.getX()), Math.max(paramBlockPosition1.getY(), paramBlockPosition2.getY()), Math.max(paramBlockPosition1.getZ(), paramBlockPosition2.getZ()));
/*     */     
/* 275 */     new Iterable()
/*     */     {
/*     */       public Iterator<BlockPosition.MutableBlockPosition> iterator() {
/* 278 */         new AbstractIterator() {
/* 279 */           private BlockPosition.MutableBlockPosition b = null;
/*     */           
/*     */           protected BlockPosition.MutableBlockPosition a()
/*     */           {
/* 283 */             if (this.b == null)
/*     */             {
/* 285 */               this.b = new BlockPosition.MutableBlockPosition(BlockPosition.2.this.a.getX(), BlockPosition.2.this.a.getY(), BlockPosition.2.this.a.getZ());
/* 286 */               return this.b; }
/* 287 */             if (this.b.equals(BlockPosition.2.this.b))
/*     */             {
/* 289 */               return (BlockPosition.MutableBlockPosition)endOfData();
/*     */             }
/*     */             
/* 292 */             int i = this.b.getX();
/* 293 */             int j = this.b.getY();
/* 294 */             int k = this.b.getZ();
/* 295 */             if (i < BlockPosition.2.this.b.getX()) {
/* 296 */               i++;
/* 297 */             } else if (j < BlockPosition.2.this.b.getY()) {
/* 298 */               i = BlockPosition.2.this.a.getX();
/* 299 */               j++;
/* 300 */             } else if (k < BlockPosition.2.this.b.getZ()) {
/* 301 */               i = BlockPosition.2.this.a.getX();
/* 302 */               j = BlockPosition.2.this.a.getY();
/* 303 */               k++;
/*     */             }
/* 305 */             BlockPosition.MutableBlockPosition.a(this.b, i);
/* 306 */             BlockPosition.MutableBlockPosition.b(this.b, j);
/* 307 */             BlockPosition.MutableBlockPosition.c(this.b, k);
/* 308 */             return this.b;
/*     */           }
/*     */         };
/*     */       }
/*     */     };
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */