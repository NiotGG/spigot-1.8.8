/*     */ package net.minecraft.server.v1_8_R3;
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
/*     */ public class DamageSource
/*     */ {
/*  15 */   public static DamageSource FIRE = new DamageSource("inFire").setExplosion();
/*  16 */   public static DamageSource LIGHTNING = new DamageSource("lightningBolt");
/*  17 */   public static DamageSource BURN = new DamageSource("onFire").setIgnoreArmor().setExplosion();
/*  18 */   public static DamageSource LAVA = new DamageSource("lava").setExplosion();
/*  19 */   public static DamageSource STUCK = new DamageSource("inWall").setIgnoreArmor();
/*  20 */   public static DamageSource DROWN = new DamageSource("drown").setIgnoreArmor();
/*  21 */   public static DamageSource STARVE = new DamageSource("starve").setIgnoreArmor().m();
/*  22 */   public static DamageSource CACTUS = new DamageSource("cactus");
/*  23 */   public static DamageSource FALL = new DamageSource("fall").setIgnoreArmor();
/*  24 */   public static DamageSource OUT_OF_WORLD = new DamageSource("outOfWorld").setIgnoreArmor().l();
/*  25 */   public static DamageSource GENERIC = new DamageSource("generic").setIgnoreArmor();
/*  26 */   public static DamageSource MAGIC = new DamageSource("magic").setIgnoreArmor().setMagic();
/*  27 */   public static DamageSource WITHER = new DamageSource("wither").setIgnoreArmor();
/*  28 */   public static DamageSource ANVIL = new DamageSource("anvil");
/*  29 */   public static DamageSource FALLING_BLOCK = new DamageSource("fallingBlock");
/*     */   private boolean q;
/*     */   
/*  32 */   public static DamageSource mobAttack(EntityLiving paramEntityLiving) { return new EntityDamageSource("mob", paramEntityLiving); }
/*     */   
/*     */   public static DamageSource playerAttack(EntityHuman paramEntityHuman)
/*     */   {
/*  36 */     return new EntityDamageSource("player", paramEntityHuman);
/*     */   }
/*     */   
/*     */   public static DamageSource arrow(EntityArrow paramEntityArrow, Entity paramEntity) {
/*  40 */     return new EntityDamageSourceIndirect("arrow", paramEntityArrow, paramEntity).b();
/*     */   }
/*     */   
/*     */   public static DamageSource fireball(EntityFireball paramEntityFireball, Entity paramEntity) {
/*  44 */     if (paramEntity == null) {
/*  45 */       return new EntityDamageSourceIndirect("onFire", paramEntityFireball, paramEntityFireball).setExplosion().b();
/*     */     }
/*  47 */     return new EntityDamageSourceIndirect("fireball", paramEntityFireball, paramEntity).setExplosion().b();
/*     */   }
/*     */   
/*     */   public static DamageSource projectile(Entity paramEntity1, Entity paramEntity2) {
/*  51 */     return new EntityDamageSourceIndirect("thrown", paramEntity1, paramEntity2).b();
/*     */   }
/*     */   
/*     */   public static DamageSource b(Entity paramEntity1, Entity paramEntity2) {
/*  55 */     return new EntityDamageSourceIndirect("indirectMagic", paramEntity1, paramEntity2).setIgnoreArmor().setMagic();
/*     */   }
/*     */   
/*     */   public static DamageSource a(Entity paramEntity) {
/*  59 */     return new EntityDamageSource("thorns", paramEntity).v().setMagic();
/*     */   }
/*     */   
/*     */   public static DamageSource explosion(Explosion paramExplosion) {
/*  63 */     if ((paramExplosion != null) && (paramExplosion.getSource() != null)) {
/*  64 */       return new EntityDamageSource("explosion.player", paramExplosion.getSource()).q().d();
/*     */     }
/*  66 */     return new DamageSource("explosion").q().d();
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean r;
/*     */   
/*     */   private boolean s;
/*     */   
/*  74 */   private float t = 0.3F;
/*     */   private boolean u;
/*     */   private boolean v;
/*     */   private boolean w;
/*     */   private boolean x;
/*     */   private boolean y;
/*     */   public String translationIndex;
/*     */   
/*  82 */   public boolean a() { return this.v; }
/*     */   
/*     */   public DamageSource b()
/*     */   {
/*  86 */     this.v = true;
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isExplosion() {
/*  91 */     return this.y;
/*     */   }
/*     */   
/*     */   public DamageSource d() {
/*  95 */     this.y = true;
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   public boolean ignoresArmor() {
/* 100 */     return this.q;
/*     */   }
/*     */   
/*     */   public float getExhaustionCost() {
/* 104 */     return this.t;
/*     */   }
/*     */   
/*     */   public boolean ignoresInvulnerability() {
/* 108 */     return this.r;
/*     */   }
/*     */   
/*     */   public boolean isStarvation() {
/* 112 */     return this.s;
/*     */   }
/*     */   
/*     */ 
/*     */   protected DamageSource(String paramString)
/*     */   {
/* 118 */     this.translationIndex = paramString;
/*     */   }
/*     */   
/*     */   public Entity i() {
/* 122 */     return getEntity();
/*     */   }
/*     */   
/*     */   public Entity getEntity() {
/* 126 */     return null;
/*     */   }
/*     */   
/*     */   protected DamageSource setIgnoreArmor() {
/* 130 */     this.q = true;
/*     */     
/* 132 */     this.t = 0.0F;
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   protected DamageSource l() {
/* 137 */     this.r = true;
/* 138 */     return this;
/*     */   }
/*     */   
/*     */   protected DamageSource m() {
/* 142 */     this.s = true;
/*     */     
/* 144 */     this.t = 0.0F;
/* 145 */     return this;
/*     */   }
/*     */   
/*     */   protected DamageSource setExplosion() {
/* 149 */     this.u = true;
/* 150 */     return this;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getLocalizedDeathMessage(EntityLiving paramEntityLiving) {
/* 154 */     EntityLiving localEntityLiving = paramEntityLiving.bt();
/* 155 */     String str1 = "death.attack." + this.translationIndex;
/* 156 */     String str2 = str1 + ".player";
/*     */     
/* 158 */     if ((localEntityLiving != null) && (LocaleI18n.c(str2))) {
/* 159 */       return new ChatMessage(str2, new Object[] { paramEntityLiving.getScoreboardDisplayName(), localEntityLiving.getScoreboardDisplayName() });
/*     */     }
/* 161 */     return new ChatMessage(str1, new Object[] { paramEntityLiving.getScoreboardDisplayName() });
/*     */   }
/*     */   
/*     */   public boolean o()
/*     */   {
/* 166 */     return this.u;
/*     */   }
/*     */   
/*     */   public String p() {
/* 170 */     return this.translationIndex;
/*     */   }
/*     */   
/*     */   public DamageSource q() {
/* 174 */     this.w = true;
/* 175 */     return this;
/*     */   }
/*     */   
/*     */   public boolean r() {
/* 179 */     return this.w;
/*     */   }
/*     */   
/*     */   public boolean isMagic() {
/* 183 */     return this.x;
/*     */   }
/*     */   
/*     */   public DamageSource setMagic() {
/* 187 */     this.x = true;
/* 188 */     return this;
/*     */   }
/*     */   
/*     */   public boolean u() {
/* 192 */     Entity localEntity = getEntity();
/* 193 */     if (((localEntity instanceof EntityHuman)) && (((EntityHuman)localEntity).abilities.canInstantlyBuild)) {
/* 194 */       return true;
/*     */     }
/* 196 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\DamageSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */