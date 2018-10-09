/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class EntityIronGolem extends EntityGolem
/*     */ {
/*     */   private int b;
/*     */   Village a;
/*     */   private int c;
/*     */   private int bm;
/*     */   
/*     */   public EntityIronGolem(World world) {
/*  13 */     super(world);
/*  14 */     setSize(1.4F, 2.9F);
/*  15 */     ((Navigation)getNavigation()).a(true);
/*  16 */     this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 1.0D, true));
/*  17 */     this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9D, 32.0F));
/*  18 */     this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.6D, true));
/*  19 */     this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
/*  20 */     this.goalSelector.a(5, new PathfinderGoalOfferFlower(this));
/*  21 */     this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.6D));
/*  22 */     this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/*  23 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*  24 */     this.targetSelector.a(1, new PathfinderGoalDefendVillage(this));
/*  25 */     this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
/*  26 */     this.targetSelector.a(3, new PathfinderGoalNearestGolemTarget(this, EntityInsentient.class, 10, false, true, IMonster.e));
/*     */   }
/*     */   
/*     */   protected void h() {
/*  30 */     super.h();
/*  31 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   protected void E() {
/*  35 */     if (--this.b <= 0) {
/*  36 */       this.b = (70 + this.random.nextInt(50));
/*  37 */       this.a = this.world.ae().getClosestVillage(new BlockPosition(this), 32);
/*  38 */       if (this.a == null) {
/*  39 */         cj();
/*     */       } else {
/*  41 */         BlockPosition blockposition = this.a.a();
/*     */         
/*  43 */         a(blockposition, (int)(this.a.b() * 0.6F));
/*     */       }
/*     */     }
/*     */     
/*  47 */     super.E();
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  51 */     super.initAttributes();
/*  52 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(100.0D);
/*  53 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
/*     */   }
/*     */   
/*     */   protected int j(int i) {
/*  57 */     return i;
/*     */   }
/*     */   
/*     */   protected void s(Entity entity) {
/*  61 */     if (((entity instanceof IMonster)) && (!(entity instanceof EntityCreeper)) && (bc().nextInt(20) == 0)) {
/*  62 */       setGoalTarget((EntityLiving)entity, org.bukkit.event.entity.EntityTargetEvent.TargetReason.COLLISION, true);
/*     */     }
/*     */     
/*  65 */     super.s(entity);
/*     */   }
/*     */   
/*     */   public void m() {
/*  69 */     super.m();
/*  70 */     if (this.c > 0) {
/*  71 */       this.c -= 1;
/*     */     }
/*     */     
/*  74 */     if (this.bm > 0) {
/*  75 */       this.bm -= 1;
/*     */     }
/*     */     
/*  78 */     if ((this.motX * this.motX + this.motZ * this.motZ > 2.500000277905201E-7D) && (this.random.nextInt(5) == 0)) {
/*  79 */       int i = MathHelper.floor(this.locX);
/*  80 */       int j = MathHelper.floor(this.locY - 0.20000000298023224D);
/*  81 */       int k = MathHelper.floor(this.locZ);
/*  82 */       IBlockData iblockdata = this.world.getType(new BlockPosition(i, j, k));
/*  83 */       Block block = iblockdata.getBlock();
/*     */       
/*  85 */       if (block.getMaterial() != Material.AIR) {
/*  86 */         this.world.addParticle(EnumParticle.BLOCK_CRACK, this.locX + (this.random.nextFloat() - 0.5D) * this.width, getBoundingBox().b + 0.1D, this.locZ + (this.random.nextFloat() - 0.5D) * this.width, 4.0D * (this.random.nextFloat() - 0.5D), 0.5D, (this.random.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getCombinedId(iblockdata) });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(Class<? extends EntityLiving> oclass)
/*     */   {
/*  93 */     return oclass == EntityCreeper.class ? false : (isPlayerCreated()) && (EntityHuman.class.isAssignableFrom(oclass)) ? false : super.a(oclass);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  97 */     super.b(nbttagcompound);
/*  98 */     nbttagcompound.setBoolean("PlayerCreated", isPlayerCreated());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 102 */     super.a(nbttagcompound);
/* 103 */     setPlayerCreated(nbttagcompound.getBoolean("PlayerCreated"));
/*     */   }
/*     */   
/*     */   public boolean r(Entity entity) {
/* 107 */     this.c = 10;
/* 108 */     this.world.broadcastEntityEffect(this, (byte)4);
/* 109 */     boolean flag = entity.damageEntity(DamageSource.mobAttack(this), 7 + this.random.nextInt(15));
/*     */     
/* 111 */     if (flag) {
/* 112 */       entity.motY += 0.4000000059604645D;
/* 113 */       a(this, entity);
/*     */     }
/*     */     
/* 116 */     makeSound("mob.irongolem.throw", 1.0F, 1.0F);
/* 117 */     return flag;
/*     */   }
/*     */   
/*     */   public Village n() {
/* 121 */     return this.a;
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/* 125 */     this.bm = (flag ? 400 : 0);
/* 126 */     this.world.broadcastEntityEffect(this, (byte)11);
/*     */   }
/*     */   
/*     */   protected String bo() {
/* 130 */     return "mob.irongolem.hit";
/*     */   }
/*     */   
/*     */   protected String bp() {
/* 134 */     return "mob.irongolem.death";
/*     */   }
/*     */   
/*     */   protected void a(BlockPosition blockposition, Block block) {
/* 138 */     makeSound("mob.irongolem.walk", 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/* 142 */     int j = this.random.nextInt(3);
/*     */     
/*     */ 
/*     */ 
/* 146 */     for (int k = 0; k < j; k++) {
/* 147 */       a(Item.getItemOf(Blocks.RED_FLOWER), 1, BlockFlowers.EnumFlowerVarient.POPPY.b());
/*     */     }
/*     */     
/* 150 */     k = 3 + this.random.nextInt(3);
/*     */     
/* 152 */     for (int l = 0; l < k; l++) {
/* 153 */       a(Items.IRON_INGOT, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public int cm()
/*     */   {
/* 159 */     return this.bm;
/*     */   }
/*     */   
/*     */   public boolean isPlayerCreated() {
/* 163 */     return (this.datawatcher.getByte(16) & 0x1) != 0;
/*     */   }
/*     */   
/*     */   public void setPlayerCreated(boolean flag) {
/* 167 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 169 */     if (flag) {
/* 170 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     } else {
/* 172 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     }
/*     */   }
/*     */   
/*     */   public void die(DamageSource damagesource)
/*     */   {
/* 178 */     if ((!isPlayerCreated()) && (this.killer != null) && (this.a != null)) {
/* 179 */       this.a.a(this.killer.getName(), -5);
/*     */     }
/*     */     
/* 182 */     super.die(damagesource);
/*     */   }
/*     */   
/*     */   static class PathfinderGoalNearestGolemTarget<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget<T>
/*     */   {
/*     */     public PathfinderGoalNearestGolemTarget(final EntityCreature entitycreature, Class<T> oclass, int i, boolean flag, boolean flag1, final com.google.common.base.Predicate<? super T> predicate) {
/* 188 */       super(oclass, i, flag, flag1, predicate);
/* 189 */       this.c = new com.google.common.base.Predicate() {
/*     */         public boolean a(T t0) {
/* 191 */           if ((predicate != null) && (!predicate.apply(t0)))
/* 192 */             return false;
/* 193 */           if ((t0 instanceof EntityCreeper)) {
/* 194 */             return false;
/*     */           }
/* 196 */           if ((t0 instanceof EntityHuman)) {
/* 197 */             double d0 = EntityIronGolem.PathfinderGoalNearestGolemTarget.this.f();
/*     */             
/* 199 */             if (t0.isSneaking()) {
/* 200 */               d0 *= 0.800000011920929D;
/*     */             }
/*     */             
/* 203 */             if (t0.isInvisible()) {
/* 204 */               float f = ((EntityHuman)t0).bY();
/*     */               
/* 206 */               if (f < 0.1F) {
/* 207 */                 f = 0.1F;
/*     */               }
/*     */               
/* 210 */               d0 *= 0.7F * f;
/*     */             }
/*     */             
/* 213 */             if (t0.g(entitycreature) > d0) {
/* 214 */               return false;
/*     */             }
/*     */           }
/*     */           
/* 218 */           return EntityIronGolem.PathfinderGoalNearestGolemTarget.this.a(t0, false);
/*     */         }
/*     */         
/*     */         public boolean apply(Object object)
/*     */         {
/* 223 */           return a((EntityLiving)object);
/*     */         }
/*     */       };
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityIronGolem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */