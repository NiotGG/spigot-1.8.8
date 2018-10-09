/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ public class VillageDoor
/*     */ {
/*     */   private final BlockPosition a;
/*     */   
/*     */   private final BlockPosition b;
/*     */   
/*     */   private final EnumDirection c;
/*     */   
/*     */   private int d;
/*     */   
/*     */   private boolean e;
/*     */   
/*     */   private int f;
/*     */   
/*     */ 
/*     */   public VillageDoor(BlockPosition paramBlockPosition, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  21 */     this(paramBlockPosition, a(paramInt1, paramInt2), paramInt3);
/*     */   }
/*     */   
/*     */   private static EnumDirection a(int paramInt1, int paramInt2) {
/*  25 */     if (paramInt1 < 0)
/*  26 */       return EnumDirection.WEST;
/*  27 */     if (paramInt1 > 0)
/*  28 */       return EnumDirection.EAST;
/*  29 */     if (paramInt2 < 0) {
/*  30 */       return EnumDirection.NORTH;
/*     */     }
/*  32 */     return EnumDirection.SOUTH;
/*     */   }
/*     */   
/*     */   public VillageDoor(BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, int paramInt)
/*     */   {
/*  37 */     this.a = paramBlockPosition;
/*  38 */     this.c = paramEnumDirection;
/*  39 */     this.b = paramBlockPosition.shift(paramEnumDirection, 2);
/*  40 */     this.d = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int b(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  48 */     return (int)this.a.c(paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public int a(BlockPosition paramBlockPosition) {
/*  52 */     return (int)paramBlockPosition.i(d());
/*     */   }
/*     */   
/*     */   public int b(BlockPosition paramBlockPosition) {
/*  56 */     return (int)this.b.i(paramBlockPosition);
/*     */   }
/*     */   
/*     */   public boolean c(BlockPosition paramBlockPosition) {
/*  60 */     int i = paramBlockPosition.getX() - this.a.getX();
/*  61 */     int j = paramBlockPosition.getZ() - this.a.getY();
/*  62 */     return i * this.c.getAdjacentX() + j * this.c.getAdjacentZ() >= 0;
/*     */   }
/*     */   
/*     */   public void a() {
/*  66 */     this.f = 0;
/*     */   }
/*     */   
/*     */   public void b() {
/*  70 */     this.f += 1;
/*     */   }
/*     */   
/*     */   public int c() {
/*  74 */     return this.f;
/*     */   }
/*     */   
/*     */   public BlockPosition d() {
/*  78 */     return this.a;
/*     */   }
/*     */   
/*     */   public BlockPosition e() {
/*  82 */     return this.b;
/*     */   }
/*     */   
/*     */   public int f() {
/*  86 */     return this.c.getAdjacentX() * 2;
/*     */   }
/*     */   
/*     */   public int g() {
/*  90 */     return this.c.getAdjacentZ() * 2;
/*     */   }
/*     */   
/*     */   public int h() {
/*  94 */     return this.d;
/*     */   }
/*     */   
/*     */   public void a(int paramInt) {
/*  98 */     this.d = paramInt;
/*     */   }
/*     */   
/*     */   public boolean i() {
/* 102 */     return this.e;
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/* 106 */     this.e = paramBoolean;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\VillageDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */