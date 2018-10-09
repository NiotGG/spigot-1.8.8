/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
/*     */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.spigotmc.SpigotWorldConfig;
/*     */ 
/*     */ public class EntityWither extends EntityMonster implements IRangedEntity
/*     */ {
/*  16 */   private float[] a = new float[2];
/*  17 */   private float[] b = new float[2];
/*  18 */   private float[] c = new float[2];
/*  19 */   private float[] bm = new float[2];
/*  20 */   private int[] bn = new int[2];
/*  21 */   private int[] bo = new int[2];
/*     */   private int bp;
/*  23 */   private static final Predicate<Entity> bq = new Predicate() {
/*     */     public boolean a(Entity entity) {
/*  25 */       return ((entity instanceof EntityLiving)) && (((EntityLiving)entity).getMonsterType() != EnumMonsterType.UNDEAD);
/*     */     }
/*     */     
/*     */     public boolean apply(Object object) {
/*  29 */       return a((Entity)object);
/*     */     }
/*     */   };
/*     */   
/*     */   public EntityWither(World world) {
/*  34 */     super(world);
/*  35 */     setHealth(getMaxHealth());
/*  36 */     setSize(0.9F, 3.5F);
/*  37 */     this.fireProof = true;
/*  38 */     ((Navigation)getNavigation()).d(true);
/*  39 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  40 */     this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 40, 20.0F));
/*  41 */     this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
/*  42 */     this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  43 */     this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
/*  44 */     this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
/*  45 */     this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityInsentient.class, 0, false, false, bq));
/*  46 */     this.b_ = 50;
/*     */   }
/*     */   
/*     */   protected void h() {
/*  50 */     super.h();
/*  51 */     this.datawatcher.a(17, new Integer(0));
/*  52 */     this.datawatcher.a(18, new Integer(0));
/*  53 */     this.datawatcher.a(19, new Integer(0));
/*  54 */     this.datawatcher.a(20, new Integer(0));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  58 */     super.b(nbttagcompound);
/*  59 */     nbttagcompound.setInt("Invul", cl());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  63 */     super.a(nbttagcompound);
/*  64 */     r(nbttagcompound.getInt("Invul"));
/*     */   }
/*     */   
/*     */   protected String z() {
/*  68 */     return "mob.wither.idle";
/*     */   }
/*     */   
/*     */   protected String bo() {
/*  72 */     return "mob.wither.hurt";
/*     */   }
/*     */   
/*     */   protected String bp() {
/*  76 */     return "mob.wither.death";
/*     */   }
/*     */   
/*     */   public void m() {
/*  80 */     this.motY *= 0.6000000238418579D;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  85 */     if ((!this.world.isClientSide) && (s(0) > 0)) {
/*  86 */       Entity entity = this.world.a(s(0));
/*     */       
/*  88 */       if (entity != null) {
/*  89 */         if ((this.locY < entity.locY) || ((!cm()) && (this.locY < entity.locY + 5.0D))) {
/*  90 */           if (this.motY < 0.0D) {
/*  91 */             this.motY = 0.0D;
/*     */           }
/*     */           
/*  94 */           this.motY += (0.5D - this.motY) * 0.6000000238418579D;
/*     */         }
/*     */         
/*  97 */         double d3 = entity.locX - this.locX;
/*     */         
/*  99 */         double d0 = entity.locZ - this.locZ;
/* 100 */         double d1 = d3 * d3 + d0 * d0;
/* 101 */         if (d1 > 9.0D) {
/* 102 */           double d2 = MathHelper.sqrt(d1);
/* 103 */           this.motX += (d3 / d2 * 0.5D - this.motX) * 0.6000000238418579D;
/* 104 */           this.motZ += (d0 / d2 * 0.5D - this.motZ) * 0.6000000238418579D;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 109 */     if (this.motX * this.motX + this.motZ * this.motZ > 0.05000000074505806D) {
/* 110 */       this.yaw = ((float)MathHelper.b(this.motZ, this.motX) * 57.295776F - 90.0F);
/*     */     }
/*     */     
/* 113 */     super.m();
/*     */     
/*     */ 
/*     */ 
/* 117 */     for (int i = 0; i < 2; i++) {
/* 118 */       this.bm[i] = this.b[i];
/* 119 */       this.c[i] = this.a[i];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 124 */     for (i = 0; i < 2; i++) {
/* 125 */       int j = s(i + 1);
/* 126 */       Entity entity1 = null;
/*     */       
/* 128 */       if (j > 0) {
/* 129 */         entity1 = this.world.a(j);
/*     */       }
/*     */       
/* 132 */       if (entity1 != null) {
/* 133 */         double d0 = t(i + 1);
/* 134 */         double d1 = u(i + 1);
/* 135 */         double d2 = v(i + 1);
/* 136 */         double d4 = entity1.locX - d0;
/* 137 */         double d5 = entity1.locY + entity1.getHeadHeight() - d1;
/* 138 */         double d6 = entity1.locZ - d2;
/* 139 */         double d7 = MathHelper.sqrt(d4 * d4 + d6 * d6);
/* 140 */         float f = (float)(MathHelper.b(d6, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
/* 141 */         float f1 = (float)-(MathHelper.b(d5, d7) * 180.0D / 3.1415927410125732D);
/*     */         
/* 143 */         this.a[i] = b(this.a[i], f1, 40.0F);
/* 144 */         this.b[i] = b(this.b[i], f, 10.0F);
/*     */       } else {
/* 146 */         this.b[i] = b(this.b[i], this.aI, 10.0F);
/*     */       }
/*     */     }
/*     */     
/* 150 */     boolean flag = cm();
/*     */     
/* 152 */     for (int j = 0; j < 3; j++) {
/* 153 */       double d8 = t(j);
/* 154 */       double d9 = u(j);
/* 155 */       double d10 = v(j);
/*     */       
/* 157 */       this.world.addParticle(EnumParticle.SMOKE_NORMAL, d8 + this.random.nextGaussian() * 0.30000001192092896D, d9 + this.random.nextGaussian() * 0.30000001192092896D, d10 + this.random.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
/* 158 */       if ((flag) && (this.world.random.nextInt(4) == 0)) {
/* 159 */         this.world.addParticle(EnumParticle.SPELL_MOB, d8 + this.random.nextGaussian() * 0.30000001192092896D, d9 + this.random.nextGaussian() * 0.30000001192092896D, d10 + this.random.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]);
/*     */       }
/*     */     }
/*     */     
/* 163 */     if (cl() > 0) {
/* 164 */       for (j = 0; j < 3; j++) {
/* 165 */         this.world.addParticle(EnumParticle.SPELL_MOB, this.locX + this.random.nextGaussian() * 1.0D, this.locY + this.random.nextFloat() * 3.3F, this.locZ + this.random.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void E()
/*     */   {
/* 174 */     if (cl() > 0) {
/* 175 */       int i = cl() - 1;
/* 176 */       if (i <= 0)
/*     */       {
/*     */ 
/* 179 */         ExplosionPrimeEvent event = new ExplosionPrimeEvent(getBukkitEntity(), 7.0F, false);
/* 180 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 182 */         if (!event.isCancelled()) {
/* 183 */           this.world.createExplosion(this, this.locX, this.locY + getHeadHeight(), this.locZ, event.getRadius(), event.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 189 */         int viewDistance = ((WorldServer)this.world).getServer().getViewDistance() * 16;
/* 190 */         for (EntityPlayer player : MinecraftServer.getServer().getPlayerList().players) {
/* 191 */           double deltaX = this.locX - player.locX;
/* 192 */           double deltaZ = this.locZ - player.locZ;
/* 193 */           double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
/* 194 */           if ((this.world.spigotConfig.witherSpawnSoundRadius <= 0) || (distanceSquared <= this.world.spigotConfig.witherSpawnSoundRadius * this.world.spigotConfig.witherSpawnSoundRadius)) {
/* 195 */             if (distanceSquared > viewDistance * viewDistance) {
/* 196 */               double deltaLength = Math.sqrt(distanceSquared);
/* 197 */               double relativeX = player.locX + deltaX / deltaLength * viewDistance;
/* 198 */               double relativeZ = player.locZ + deltaZ / deltaLength * viewDistance;
/* 199 */               player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1013, new BlockPosition((int)relativeX, (int)this.locY, (int)relativeZ), 0, true));
/*     */             } else {
/* 201 */               player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1013, new BlockPosition((int)this.locX, (int)this.locY, (int)this.locZ), 0, true));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 207 */       r(i);
/* 208 */       if (this.ticksLived % 10 == 0) {
/* 209 */         heal(10.0F, EntityRegainHealthEvent.RegainReason.WITHER_SPAWN);
/*     */       }
/*     */     }
/*     */     else {
/* 213 */       super.E();
/*     */       
/*     */ 
/*     */ 
/* 217 */       for (int i = 1; i < 3; i++) {
/* 218 */         if (this.ticksLived >= this.bn[(i - 1)]) {
/* 219 */           this.bn[(i - 1)] = (this.ticksLived + 10 + this.random.nextInt(10));
/* 220 */           if ((this.world.getDifficulty() == EnumDifficulty.NORMAL) || (this.world.getDifficulty() == EnumDifficulty.HARD)) {
/* 221 */             int k = i - 1;
/* 222 */             int l = this.bo[(i - 1)];
/*     */             
/* 224 */             this.bo[k] = (this.bo[(i - 1)] + 1);
/* 225 */             if (l > 15) {
/* 226 */               float f = 10.0F;
/* 227 */               float f1 = 5.0F;
/* 228 */               double d0 = MathHelper.a(this.random, this.locX - f, this.locX + f);
/* 229 */               double d1 = MathHelper.a(this.random, this.locY - f1, this.locY + f1);
/* 230 */               double d2 = MathHelper.a(this.random, this.locZ - f, this.locZ + f);
/*     */               
/* 232 */               a(i + 1, d0, d1, d2, true);
/* 233 */               this.bo[(i - 1)] = 0;
/*     */             }
/*     */           }
/*     */           
/* 237 */           int j = s(i);
/* 238 */           if (j > 0) {
/* 239 */             Entity entity = this.world.a(j);
/*     */             
/* 241 */             if ((entity != null) && (entity.isAlive()) && (h(entity) <= 900.0D) && (hasLineOfSight(entity))) {
/* 242 */               if (((entity instanceof EntityHuman)) && (((EntityHuman)entity).abilities.isInvulnerable)) {
/* 243 */                 b(i, 0);
/*     */               } else {
/* 245 */                 a(i + 1, (EntityLiving)entity);
/* 246 */                 this.bn[(i - 1)] = (this.ticksLived + 40 + this.random.nextInt(20));
/* 247 */                 this.bo[(i - 1)] = 0;
/*     */               }
/*     */             } else {
/* 250 */               b(i, 0);
/*     */             }
/*     */           } else {
/* 253 */             List list = this.world.a(EntityLiving.class, getBoundingBox().grow(20.0D, 8.0D, 20.0D), Predicates.and(bq, IEntitySelector.d));
/*     */             
/* 255 */             for (int i1 = 0; (i1 < 10) && (!list.isEmpty()); i1++) {
/* 256 */               EntityLiving entityliving = (EntityLiving)list.get(this.random.nextInt(list.size()));
/*     */               
/* 258 */               if ((entityliving != this) && (entityliving.isAlive()) && (hasLineOfSight(entityliving))) {
/* 259 */                 if ((entityliving instanceof EntityHuman)) {
/* 260 */                   if (((EntityHuman)entityliving).abilities.isInvulnerable) break;
/* 261 */                   b(i, entityliving.getId());
/*     */                   
/* 263 */                   break; }
/* 264 */                 b(i, entityliving.getId());
/*     */                 
/* 266 */                 break;
/*     */               }
/*     */               
/* 269 */               list.remove(entityliving);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 275 */       if (getGoalTarget() != null) {
/* 276 */         b(0, getGoalTarget().getId());
/*     */       } else {
/* 278 */         b(0, 0);
/*     */       }
/*     */       
/* 281 */       if (this.bp > 0) {
/* 282 */         this.bp -= 1;
/* 283 */         if ((this.bp == 0) && (this.world.getGameRules().getBoolean("mobGriefing"))) {
/* 284 */           i = MathHelper.floor(this.locY);
/* 285 */           int j = MathHelper.floor(this.locX);
/* 286 */           int j1 = MathHelper.floor(this.locZ);
/* 287 */           boolean flag = false;
/*     */           
/* 289 */           for (int k1 = -1; k1 <= 1; k1++) {
/* 290 */             for (int l1 = -1; l1 <= 1; l1++) {
/* 291 */               for (int i2 = 0; i2 <= 3; i2++) {
/* 292 */                 int j2 = j + k1;
/* 293 */                 int k2 = i + i2;
/* 294 */                 int l2 = j1 + l1;
/* 295 */                 BlockPosition blockposition = new BlockPosition(j2, k2, l2);
/* 296 */                 Block block = this.world.getType(blockposition).getBlock();
/*     */                 
/* 298 */                 if ((block.getMaterial() != Material.AIR) && (a(block)))
/*     */                 {
/* 300 */                   if (!org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callEntityChangeBlockEvent(this, j2, k2, l2, Blocks.AIR, 0).isCancelled())
/*     */                   {
/*     */ 
/*     */ 
/* 304 */                     flag = (this.world.setAir(blockposition, true)) || (flag);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 310 */           if (flag) {
/* 311 */             this.world.a(null, 1012, new BlockPosition(this), 0);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 316 */       if (this.ticksLived % 20 == 0) {
/* 317 */         heal(1.0F, EntityRegainHealthEvent.RegainReason.REGEN);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean a(Block block)
/*     */   {
/* 324 */     return (block != Blocks.BEDROCK) && (block != Blocks.END_PORTAL) && (block != Blocks.END_PORTAL_FRAME) && (block != Blocks.COMMAND_BLOCK) && (block != Blocks.BARRIER);
/*     */   }
/*     */   
/*     */   public void n() {
/* 328 */     r(220);
/* 329 */     setHealth(getMaxHealth() / 3.0F);
/*     */   }
/*     */   
/*     */   public void aA() {}
/*     */   
/*     */   public int br() {
/* 335 */     return 4;
/*     */   }
/*     */   
/*     */   private double t(int i) {
/* 339 */     if (i <= 0) {
/* 340 */       return this.locX;
/*     */     }
/* 342 */     float f = (this.aI + 180 * (i - 1)) / 180.0F * 3.1415927F;
/* 343 */     float f1 = MathHelper.cos(f);
/*     */     
/* 345 */     return this.locX + f1 * 1.3D;
/*     */   }
/*     */   
/*     */   private double u(int i)
/*     */   {
/* 350 */     return i <= 0 ? this.locY + 3.0D : this.locY + 2.2D;
/*     */   }
/*     */   
/*     */   private double v(int i) {
/* 354 */     if (i <= 0) {
/* 355 */       return this.locZ;
/*     */     }
/* 357 */     float f = (this.aI + 180 * (i - 1)) / 180.0F * 3.1415927F;
/* 358 */     float f1 = MathHelper.sin(f);
/*     */     
/* 360 */     return this.locZ + f1 * 1.3D;
/*     */   }
/*     */   
/*     */   private float b(float f, float f1, float f2)
/*     */   {
/* 365 */     float f3 = MathHelper.g(f1 - f);
/*     */     
/* 367 */     if (f3 > f2) {
/* 368 */       f3 = f2;
/*     */     }
/*     */     
/* 371 */     if (f3 < -f2) {
/* 372 */       f3 = -f2;
/*     */     }
/*     */     
/* 375 */     return f + f3;
/*     */   }
/*     */   
/*     */   private void a(int i, EntityLiving entityliving) {
/* 379 */     a(i, entityliving.locX, entityliving.locY + entityliving.getHeadHeight() * 0.5D, entityliving.locZ, (i == 0) && (this.random.nextFloat() < 0.001F));
/*     */   }
/*     */   
/*     */   private void a(int i, double d0, double d1, double d2, boolean flag) {
/* 383 */     this.world.a(null, 1014, new BlockPosition(this), 0);
/* 384 */     double d3 = t(i);
/* 385 */     double d4 = u(i);
/* 386 */     double d5 = v(i);
/* 387 */     double d6 = d0 - d3;
/* 388 */     double d7 = d1 - d4;
/* 389 */     double d8 = d2 - d5;
/* 390 */     EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.world, this, d6, d7, d8);
/*     */     
/* 392 */     if (flag) {
/* 393 */       entitywitherskull.setCharged(true);
/*     */     }
/*     */     
/* 396 */     entitywitherskull.locY = d4;
/* 397 */     entitywitherskull.locX = d3;
/* 398 */     entitywitherskull.locZ = d5;
/* 399 */     this.world.addEntity(entitywitherskull);
/*     */   }
/*     */   
/*     */   public void a(EntityLiving entityliving, float f) {
/* 403 */     a(0, entityliving);
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 407 */     if (isInvulnerable(damagesource))
/* 408 */       return false;
/* 409 */     if ((damagesource != DamageSource.DROWN) && (!(damagesource.getEntity() instanceof EntityWither))) {
/* 410 */       if ((cl() > 0) && (damagesource != DamageSource.OUT_OF_WORLD)) {
/* 411 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 415 */       if (cm()) {
/* 416 */         Entity entity = damagesource.i();
/* 417 */         if ((entity instanceof EntityArrow)) {
/* 418 */           return false;
/*     */         }
/*     */       }
/*     */       
/* 422 */       Entity entity = damagesource.getEntity();
/* 423 */       if ((entity != null) && (!(entity instanceof EntityHuman)) && ((entity instanceof EntityLiving)) && (((EntityLiving)entity).getMonsterType() == getMonsterType())) {
/* 424 */         return false;
/*     */       }
/* 426 */       if (this.bp <= 0) {
/* 427 */         this.bp = 20;
/*     */       }
/*     */       
/* 430 */       for (int i = 0; i < this.bo.length; i++) {
/* 431 */         this.bo[i] += 3;
/*     */       }
/*     */       
/* 434 */       return super.damageEntity(damagesource, f);
/*     */     }
/*     */     
/*     */ 
/* 438 */     return false;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i)
/*     */   {
/* 443 */     EntityItem entityitem = a(Items.NETHER_STAR, 1);
/*     */     
/* 445 */     if (entityitem != null) {
/* 446 */       entityitem.u();
/*     */     }
/*     */     
/* 449 */     if (!this.world.isClientSide) {
/* 450 */       Iterator iterator = this.world.a(EntityHuman.class, getBoundingBox().grow(50.0D, 100.0D, 50.0D)).iterator();
/*     */       
/* 452 */       while (iterator.hasNext()) {
/* 453 */         EntityHuman entityhuman = (EntityHuman)iterator.next();
/*     */         
/* 455 */         entityhuman.b(AchievementList.J);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void D()
/*     */   {
/* 462 */     this.ticksFarFromPlayer = 0;
/*     */   }
/*     */   
/*     */   public void e(float f, float f1) {}
/*     */   
/*     */   public void addEffect(MobEffect mobeffect) {}
/*     */   
/*     */   protected void initAttributes() {
/* 470 */     super.initAttributes();
/* 471 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(300.0D);
/* 472 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.6000000238418579D);
/* 473 */     getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(40.0D);
/*     */   }
/*     */   
/*     */   public int cl() {
/* 477 */     return this.datawatcher.getInt(20);
/*     */   }
/*     */   
/*     */   public void r(int i) {
/* 481 */     this.datawatcher.watch(20, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int s(int i) {
/* 485 */     return this.datawatcher.getInt(17 + i);
/*     */   }
/*     */   
/*     */   public void b(int i, int j) {
/* 489 */     this.datawatcher.watch(17 + i, Integer.valueOf(j));
/*     */   }
/*     */   
/*     */   public boolean cm() {
/* 493 */     return getHealth() <= getMaxHealth() / 2.0F;
/*     */   }
/*     */   
/*     */   public EnumMonsterType getMonsterType() {
/* 497 */     return EnumMonsterType.UNDEAD;
/*     */   }
/*     */   
/*     */   public void mount(Entity entity) {
/* 501 */     this.vehicle = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityWither.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */