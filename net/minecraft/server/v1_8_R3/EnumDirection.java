/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum EnumDirection
/*     */   implements INamable
/*     */ {
/*     */   private final int g;
/*     */   private final int h;
/*     */   private final int i;
/*     */   private final String j;
/*     */   private final EnumAxis k;
/*     */   private final EnumAxisDirection l;
/*     */   private final BaseBlockPosition m;
/*     */   private static final EnumDirection[] n;
/*     */   private static final EnumDirection[] o;
/*     */   private static final Map<String, EnumDirection> p;
/*     */   
/*     */   static
/*     */   {
/*  31 */     n = new EnumDirection[6];
/*  32 */     o = new EnumDirection[4];
/*  33 */     p = Maps.newHashMap();
/*     */     
/*     */ 
/*  36 */     for (EnumDirection localEnumDirection : values()) {
/*  37 */       n[localEnumDirection.g] = localEnumDirection;
/*     */       
/*  39 */       if (localEnumDirection.k().c()) {
/*  40 */         o[localEnumDirection.i] = localEnumDirection;
/*     */       }
/*     */       
/*  43 */       p.put(localEnumDirection.j().toLowerCase(), localEnumDirection);
/*     */     }
/*     */   }
/*     */   
/*     */   private EnumDirection(int paramInt1, int paramInt2, int paramInt3, String paramString, EnumAxisDirection paramEnumAxisDirection, EnumAxis paramEnumAxis, BaseBlockPosition paramBaseBlockPosition) {
/*  48 */     this.g = paramInt1;
/*  49 */     this.i = paramInt3;
/*  50 */     this.h = paramInt2;
/*  51 */     this.j = paramString;
/*  52 */     this.k = paramEnumAxis;
/*  53 */     this.l = paramEnumAxisDirection;
/*  54 */     this.m = paramBaseBlockPosition;
/*     */   }
/*     */   
/*     */   public int a() {
/*  58 */     return this.g;
/*     */   }
/*     */   
/*     */   public int b() {
/*  62 */     return this.i;
/*     */   }
/*     */   
/*     */   public EnumAxisDirection c() {
/*  66 */     return this.l;
/*     */   }
/*     */   
/*     */   public EnumDirection opposite() {
/*  70 */     return fromType1(this.h);
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
/*     */ 
/*     */ 
/*     */   public EnumDirection e()
/*     */   {
/* 124 */     switch (1.b[ordinal()]) {
/*     */     case 1: 
/* 126 */       return EAST;
/*     */     case 2: 
/* 128 */       return SOUTH;
/*     */     case 3: 
/* 130 */       return WEST;
/*     */     case 4: 
/* 132 */       return NORTH;
/*     */     }
/* 134 */     throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
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
/*     */   public EnumDirection f()
/*     */   {
/* 199 */     switch (1.b[ordinal()]) {
/*     */     case 1: 
/* 201 */       return WEST;
/*     */     case 2: 
/* 203 */       return NORTH;
/*     */     case 3: 
/* 205 */       return EAST;
/*     */     case 4: 
/* 207 */       return SOUTH;
/*     */     }
/* 209 */     throw new IllegalStateException("Unable to get CCW facing of " + this);
/*     */   }
/*     */   
/*     */   public int getAdjacentX()
/*     */   {
/* 214 */     if (this.k == EnumAxis.X) {
/* 215 */       return this.l.a();
/*     */     }
/* 217 */     return 0;
/*     */   }
/*     */   
/*     */   public int getAdjacentY()
/*     */   {
/* 222 */     if (this.k == EnumAxis.Y) {
/* 223 */       return this.l.a();
/*     */     }
/* 225 */     return 0;
/*     */   }
/*     */   
/*     */   public int getAdjacentZ()
/*     */   {
/* 230 */     if (this.k == EnumAxis.Z) {
/* 231 */       return this.l.a();
/*     */     }
/* 233 */     return 0;
/*     */   }
/*     */   
/*     */   public String j()
/*     */   {
/* 238 */     return this.j;
/*     */   }
/*     */   
/*     */   public EnumAxis k() {
/* 242 */     return this.k;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static EnumDirection fromType1(int paramInt)
/*     */   {
/* 254 */     return n[MathHelper.a(paramInt % n.length)];
/*     */   }
/*     */   
/*     */   public static EnumDirection fromType2(int paramInt) {
/* 258 */     return o[MathHelper.a(paramInt % o.length)];
/*     */   }
/*     */   
/*     */   public static EnumDirection fromAngle(double paramDouble) {
/* 262 */     return fromType2(MathHelper.floor(paramDouble / 90.0D + 0.5D) & 0x3);
/*     */   }
/*     */   
/*     */   public static EnumDirection a(Random paramRandom) {
/* 266 */     return values()[paramRandom.nextInt(values().length)];
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
/*     */   public String toString()
/*     */   {
/* 298 */     return this.j;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 303 */     return this.j;
/*     */   }
/*     */   
/*     */   public static EnumDirection a(EnumAxisDirection paramEnumAxisDirection, EnumAxis paramEnumAxis) {
/* 307 */     for (EnumDirection localEnumDirection : ) {
/* 308 */       if ((localEnumDirection.c() == paramEnumAxisDirection) && (localEnumDirection.k() == paramEnumAxis)) {
/* 309 */         return localEnumDirection;
/*     */       }
/*     */     }
/* 312 */     throw new IllegalArgumentException("No such direction: " + paramEnumAxisDirection + " " + paramEnumAxis);
/*     */   }
/*     */   
/*     */   public static enum EnumAxis implements Predicate<EnumDirection>, INamable {
/*     */     private static final Map<String, EnumAxis> d;
/*     */     private final String e;
/*     */     private final EnumDirection.EnumDirectionLimit f;
/*     */     
/*     */     static {
/* 321 */       d = Maps.newHashMap();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 327 */       for (EnumAxis localEnumAxis : values()) {
/* 328 */         d.put(localEnumAxis.a().toLowerCase(), localEnumAxis);
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumAxis(String paramString, EnumDirection.EnumDirectionLimit paramEnumDirectionLimit) {
/* 333 */       this.e = paramString;
/* 334 */       this.f = paramEnumDirectionLimit;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String a()
/*     */     {
/* 346 */       return this.e;
/*     */     }
/*     */     
/*     */     public boolean b() {
/* 350 */       return this.f == EnumDirection.EnumDirectionLimit.VERTICAL;
/*     */     }
/*     */     
/*     */     public boolean c() {
/* 354 */       return this.f == EnumDirection.EnumDirectionLimit.HORIZONTAL;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 359 */       return this.e;
/*     */     }
/*     */     
/*     */     public boolean a(EnumDirection paramEnumDirection)
/*     */     {
/* 364 */       return (paramEnumDirection != null) && (paramEnumDirection.k() == this);
/*     */     }
/*     */     
/*     */     public EnumDirection.EnumDirectionLimit d() {
/* 368 */       return this.f;
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 373 */       return this.e;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumAxisDirection
/*     */   {
/*     */     private final int c;
/*     */     
/*     */     private final String d;
/*     */     
/*     */     private EnumAxisDirection(int paramInt, String paramString)
/*     */     {
/* 386 */       this.c = paramInt;
/* 387 */       this.d = paramString;
/*     */     }
/*     */     
/*     */     public int a() {
/* 391 */       return this.c;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 400 */       return this.d;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum EnumDirectionLimit
/*     */     implements Predicate<EnumDirection>, Iterable<EnumDirection>
/*     */   {
/*     */     private EnumDirectionLimit() {}
/*     */     
/*     */ 
/*     */     public EnumDirection[] a()
/*     */     {
/* 414 */       switch (EnumDirection.1.c[ordinal()]) {
/*     */       case 1: 
/* 416 */         return new EnumDirection[] { EnumDirection.NORTH, EnumDirection.EAST, EnumDirection.SOUTH, EnumDirection.WEST };
/*     */       case 2: 
/* 418 */         return new EnumDirection[] { EnumDirection.UP, EnumDirection.DOWN };
/*     */       }
/* 420 */       throw new Error("Someone's been tampering with the universe!");
/*     */     }
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
/*     */     public EnumDirection a(Random paramRandom)
/*     */     {
/* 434 */       EnumDirection[] arrayOfEnumDirection = a();
/* 435 */       return arrayOfEnumDirection[paramRandom.nextInt(arrayOfEnumDirection.length)];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean a(EnumDirection paramEnumDirection)
/*     */     {
/* 445 */       return (paramEnumDirection != null) && (paramEnumDirection.k().d() == this);
/*     */     }
/*     */     
/*     */     public Iterator<EnumDirection> iterator()
/*     */     {
/* 450 */       return Iterators.forArray(a());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnumDirection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */