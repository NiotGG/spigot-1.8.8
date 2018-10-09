/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ public class Material {
/*   4 */   public static final Material AIR = new MaterialGas(MaterialMapColor.b);
/*   5 */   public static final Material GRASS = new Material(MaterialMapColor.c);
/*   6 */   public static final Material EARTH = new Material(MaterialMapColor.l);
/*   7 */   public static final Material WOOD = new Material(MaterialMapColor.o).g();
/*   8 */   public static final Material STONE = new Material(MaterialMapColor.m).f();
/*   9 */   public static final Material ORE = new Material(MaterialMapColor.h).f();
/*  10 */   public static final Material HEAVY = new Material(MaterialMapColor.h).f().o();
/*  11 */   public static final Material WATER = new MaterialLiquid(MaterialMapColor.n).n();
/*  12 */   public static final Material LAVA = new MaterialLiquid(MaterialMapColor.f).n();
/*  13 */   public static final Material LEAVES = new Material(MaterialMapColor.i).g().s().n();
/*  14 */   public static final Material PLANT = new MaterialDecoration(MaterialMapColor.i).n();
/*  15 */   public static final Material REPLACEABLE_PLANT = new MaterialDecoration(MaterialMapColor.i).g().n().i();
/*  16 */   public static final Material SPONGE = new Material(MaterialMapColor.t);
/*  17 */   public static final Material CLOTH = new Material(MaterialMapColor.e).g();
/*  18 */   public static final Material FIRE = new MaterialGas(MaterialMapColor.b).n();
/*  19 */   public static final Material SAND = new Material(MaterialMapColor.d);
/*  20 */   public static final Material ORIENTABLE = new MaterialDecoration(MaterialMapColor.b).n();
/*  21 */   public static final Material WOOL = new MaterialDecoration(MaterialMapColor.e).g();
/*  22 */   public static final Material SHATTERABLE = new Material(MaterialMapColor.b).s().p();
/*  23 */   public static final Material BUILDABLE_GLASS = new Material(MaterialMapColor.b).p();
/*  24 */   public static final Material TNT = new Material(MaterialMapColor.f).g().s();
/*  25 */   public static final Material CORAL = new Material(MaterialMapColor.i).n();
/*  26 */   public static final Material ICE = new Material(MaterialMapColor.g).s().p();
/*  27 */   public static final Material SNOW_LAYER = new Material(MaterialMapColor.g).p();
/*  28 */   public static final Material PACKED_ICE = new MaterialDecoration(MaterialMapColor.j).i().s().f().n();
/*  29 */   public static final Material SNOW_BLOCK = new Material(MaterialMapColor.j).f();
/*  30 */   public static final Material CACTUS = new Material(MaterialMapColor.i).s().n();
/*  31 */   public static final Material CLAY = new Material(MaterialMapColor.k);
/*  32 */   public static final Material PUMPKIN = new Material(MaterialMapColor.i).n();
/*  33 */   public static final Material DRAGON_EGG = new Material(MaterialMapColor.i).n();
/*  34 */   public static final Material PORTAL = new MaterialPortal(MaterialMapColor.b).o();
/*  35 */   public static final Material CAKE = new Material(MaterialMapColor.b).n();
/*  36 */   public static final Material WEB = new Material(MaterialMapColor.e)
/*     */   {
/*     */     public boolean isSolid() {
/*  39 */       return false;
/*     */     }
/*  36 */   }.f().n();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  42 */   public static final Material PISTON = new Material(MaterialMapColor.m).o();
/*  43 */   public static final Material BANNER = new Material(MaterialMapColor.b).f().o();
/*     */   
/*     */   private boolean canBurn;
/*     */   
/*     */   private boolean K;
/*     */   
/*     */   private boolean L;
/*     */   
/*     */   private final MaterialMapColor M;
/*  52 */   private boolean N = true;
/*     */   private int O;
/*     */   private boolean P;
/*     */   
/*     */   public Material(MaterialMapColor paramMaterialMapColor) {
/*  57 */     this.M = paramMaterialMapColor;
/*     */   }
/*     */   
/*     */   public boolean isLiquid() {
/*  61 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isBuildable()
/*     */   {
/*  69 */     return true;
/*     */   }
/*     */   
/*     */   public boolean blocksLight() {
/*  73 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isSolid() {
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   private Material s() {
/*  81 */     this.L = true;
/*  82 */     return this;
/*     */   }
/*     */   
/*     */   protected Material f() {
/*  86 */     this.N = false;
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   protected Material g() {
/*  91 */     this.canBurn = true;
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isBurnable() {
/*  96 */     return this.canBurn;
/*     */   }
/*     */   
/*     */   public Material i() {
/* 100 */     this.K = true;
/* 101 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isReplaceable() {
/* 105 */     return this.K;
/*     */   }
/*     */   
/*     */   public boolean k() {
/* 109 */     if (this.L) {
/* 110 */       return false;
/*     */     }
/* 112 */     return isSolid();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isAlwaysDestroyable()
/*     */   {
/* 118 */     return this.N;
/*     */   }
/*     */   
/*     */   public int getPushReaction() {
/* 122 */     return this.O;
/*     */   }
/*     */   
/*     */   protected Material n() {
/* 126 */     this.O = 1;
/* 127 */     return this;
/*     */   }
/*     */   
/*     */   protected Material o() {
/* 131 */     this.O = 2;
/* 132 */     return this;
/*     */   }
/*     */   
/*     */   protected Material p() {
/* 136 */     this.P = true;
/* 137 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public MaterialMapColor r()
/*     */   {
/* 145 */     return this.M;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Material.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */