/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ public final class WorldSettings
/*     */ {
/*     */   private final long a;
/*     */   private final EnumGamemode b;
/*     */   private final boolean c;
/*     */   private final boolean d;
/*     */   private final WorldType e;
/*     */   private boolean f;
/*     */   private boolean g;
/*     */   
/*     */   public static enum EnumGamemode
/*     */   {
/*     */     int f;
/*     */     String g;
/*     */     
/*     */     private EnumGamemode(int paramInt, String paramString) {
/*  19 */       this.f = paramInt;
/*  20 */       this.g = paramString;
/*     */     }
/*     */     
/*     */     public int getId() {
/*  24 */       return this.f;
/*     */     }
/*     */     
/*     */     public String b() {
/*  28 */       return this.g;
/*     */     }
/*     */     
/*     */     public void a(PlayerAbilities paramPlayerAbilities) {
/*  32 */       if (this == CREATIVE) {
/*  33 */         paramPlayerAbilities.canFly = true;
/*  34 */         paramPlayerAbilities.canInstantlyBuild = true;
/*  35 */         paramPlayerAbilities.isInvulnerable = true;
/*  36 */       } else if (this == SPECTATOR) {
/*  37 */         paramPlayerAbilities.canFly = true;
/*  38 */         paramPlayerAbilities.canInstantlyBuild = false;
/*  39 */         paramPlayerAbilities.isInvulnerable = true;
/*  40 */         paramPlayerAbilities.isFlying = true;
/*     */       } else {
/*  42 */         paramPlayerAbilities.canFly = false;
/*  43 */         paramPlayerAbilities.canInstantlyBuild = false;
/*  44 */         paramPlayerAbilities.isInvulnerable = false;
/*  45 */         paramPlayerAbilities.isFlying = false;
/*     */       }
/*  47 */       paramPlayerAbilities.mayBuild = (!c());
/*     */     }
/*     */     
/*     */     public boolean c() {
/*  51 */       return (this == ADVENTURE) || (this == SPECTATOR);
/*     */     }
/*     */     
/*     */     public boolean d() {
/*  55 */       return this == CREATIVE;
/*     */     }
/*     */     
/*     */     public boolean e() {
/*  59 */       return (this == SURVIVAL) || (this == ADVENTURE);
/*     */     }
/*     */     
/*     */     public static EnumGamemode getById(int paramInt) {
/*  63 */       for (EnumGamemode localEnumGamemode : ) {
/*  64 */         if (localEnumGamemode.f == paramInt) {
/*  65 */           return localEnumGamemode;
/*     */         }
/*     */       }
/*  68 */       return SURVIVAL;
/*     */     }
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
/*  88 */   private String h = "";
/*     */   
/*     */   public WorldSettings(long paramLong, EnumGamemode paramEnumGamemode, boolean paramBoolean1, boolean paramBoolean2, WorldType paramWorldType) {
/*  91 */     this.a = paramLong;
/*  92 */     this.b = paramEnumGamemode;
/*  93 */     this.c = paramBoolean1;
/*  94 */     this.d = paramBoolean2;
/*  95 */     this.e = paramWorldType;
/*     */   }
/*     */   
/*     */   public WorldSettings(WorldData paramWorldData) {
/*  99 */     this(paramWorldData.getSeed(), paramWorldData.getGameType(), paramWorldData.shouldGenerateMapFeatures(), paramWorldData.isHardcore(), paramWorldData.getType());
/*     */   }
/*     */   
/*     */   public WorldSettings a() {
/* 103 */     this.g = true;
/* 104 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldSettings setGeneratorSettings(String paramString)
/*     */   {
/* 113 */     this.h = paramString;
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 118 */     return this.g;
/*     */   }
/*     */   
/*     */   public long d() {
/* 122 */     return this.a;
/*     */   }
/*     */   
/*     */   public EnumGamemode e() {
/* 126 */     return this.b;
/*     */   }
/*     */   
/*     */   public boolean f() {
/* 130 */     return this.d;
/*     */   }
/*     */   
/*     */   public boolean g() {
/* 134 */     return this.c;
/*     */   }
/*     */   
/*     */   public WorldType h() {
/* 138 */     return this.e;
/*     */   }
/*     */   
/*     */   public boolean i() {
/* 142 */     return this.f;
/*     */   }
/*     */   
/*     */   public static EnumGamemode a(int paramInt) {
/* 146 */     return EnumGamemode.getById(paramInt);
/*     */   }
/*     */   
/*     */   public String j() {
/* 150 */     return this.h;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */