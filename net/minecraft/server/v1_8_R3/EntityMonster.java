/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.bukkit.event.entity.EntityCombustByEntityEvent;
/*     */ 
/*     */ public abstract class EntityMonster extends EntityCreature implements IMonster
/*     */ {
/*     */   public EntityMonster(World world) {
/*   8 */     super(world);
/*   9 */     this.b_ = 5;
/*     */   }
/*     */   
/*     */   public void m() {
/*  13 */     bx();
/*  14 */     float f = c(1.0F);
/*     */     
/*  16 */     if (f > 0.5F) {
/*  17 */       this.ticksFarFromPlayer += 2;
/*     */     }
/*     */     
/*  20 */     super.m();
/*     */   }
/*     */   
/*     */   public void t_() {
/*  24 */     super.t_();
/*  25 */     if ((!this.world.isClientSide) && (this.world.getDifficulty() == EnumDifficulty.PEACEFUL)) {
/*  26 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   protected String P()
/*     */   {
/*  32 */     return "game.hostile.swim";
/*     */   }
/*     */   
/*     */   protected String aa() {
/*  36 */     return "game.hostile.swim.splash";
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  40 */     if (isInvulnerable(damagesource))
/*  41 */       return false;
/*  42 */     if (super.damageEntity(damagesource, f)) {
/*  43 */       Entity entity = damagesource.getEntity();
/*     */       
/*  45 */       return (this.passenger != entity) && (this.vehicle != entity);
/*     */     }
/*  47 */     return false;
/*     */   }
/*     */   
/*     */   protected String bo()
/*     */   {
/*  52 */     return "game.hostile.hurt";
/*     */   }
/*     */   
/*     */   protected String bp() {
/*  56 */     return "game.hostile.die";
/*     */   }
/*     */   
/*     */   protected String n(int i) {
/*  60 */     return i > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
/*     */   }
/*     */   
/*     */   public boolean r(Entity entity) {
/*  64 */     float f = (float)getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
/*  65 */     int i = 0;
/*     */     
/*  67 */     if ((entity instanceof EntityLiving)) {
/*  68 */       f += EnchantmentManager.a(bA(), ((EntityLiving)entity).getMonsterType());
/*  69 */       i += EnchantmentManager.a(this);
/*     */     }
/*     */     
/*  72 */     boolean flag = entity.damageEntity(DamageSource.mobAttack(this), f);
/*     */     
/*  74 */     if (flag) {
/*  75 */       if (i > 0) {
/*  76 */         entity.g(-MathHelper.sin(this.yaw * 3.1415927F / 180.0F) * i * 0.5F, 0.1D, MathHelper.cos(this.yaw * 3.1415927F / 180.0F) * i * 0.5F);
/*  77 */         this.motX *= 0.6D;
/*  78 */         this.motZ *= 0.6D;
/*     */       }
/*     */       
/*  81 */       int j = EnchantmentManager.getFireAspectEnchantmentLevel(this);
/*     */       
/*  83 */       if (j > 0)
/*     */       {
/*  85 */         EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(getBukkitEntity(), entity.getBukkitEntity(), j * 4);
/*  86 */         org.bukkit.Bukkit.getPluginManager().callEvent(combustEvent);
/*     */         
/*  88 */         if (!combustEvent.isCancelled()) {
/*  89 */           entity.setOnFire(combustEvent.getDuration());
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  94 */       a(this, entity);
/*     */     }
/*     */     
/*  97 */     return flag;
/*     */   }
/*     */   
/*     */   public float a(BlockPosition blockposition) {
/* 101 */     return 0.5F - this.world.o(blockposition);
/*     */   }
/*     */   
/*     */   protected boolean n_() {
/* 105 */     BlockPosition blockposition = new BlockPosition(this.locX, getBoundingBox().b, this.locZ);
/*     */     
/* 107 */     if (this.world.b(EnumSkyBlock.SKY, blockposition) > this.random.nextInt(32)) {
/* 108 */       return false;
/*     */     }
/* 110 */     int i = this.world.getLightLevel(blockposition);
/*     */     
/* 112 */     if (this.world.R()) {
/* 113 */       int j = this.world.ab();
/*     */       
/* 115 */       this.world.c(10);
/* 116 */       i = this.world.getLightLevel(blockposition);
/* 117 */       this.world.c(j);
/*     */     }
/*     */     
/* 120 */     return i <= this.random.nextInt(8);
/*     */   }
/*     */   
/*     */   public boolean bR()
/*     */   {
/* 125 */     return (this.world.getDifficulty() != EnumDifficulty.PEACEFUL) && (n_()) && (super.bR());
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/* 129 */     super.initAttributes();
/* 130 */     getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE);
/*     */   }
/*     */   
/*     */   protected boolean ba() {
/* 134 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityMonster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */