/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class EntitySilverfish extends EntityMonster
/*     */ {
/*     */   private PathfinderGoalSilverfishWakeOthers a;
/*     */   
/*     */   public EntitySilverfish(World world) {
/*  10 */     super(world);
/*  11 */     setSize(0.4F, 0.3F);
/*  12 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  13 */     this.goalSelector.a(3, this.a = new PathfinderGoalSilverfishWakeOthers(this));
/*  14 */     this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
/*  15 */     this.goalSelector.a(5, new PathfinderGoalSilverfishHideInBlock(this));
/*  16 */     this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
/*  17 */     this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
/*     */   }
/*     */   
/*     */   public double am() {
/*  21 */     return 0.2D;
/*     */   }
/*     */   
/*     */   public float getHeadHeight() {
/*  25 */     return 0.1F;
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  29 */     super.initAttributes();
/*  30 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
/*  31 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
/*  32 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(1.0D);
/*     */   }
/*     */   
/*     */   protected boolean s_() {
/*  36 */     return false;
/*     */   }
/*     */   
/*     */   protected String z() {
/*  40 */     return "mob.silverfish.say";
/*     */   }
/*     */   
/*     */   protected String bo() {
/*  44 */     return "mob.silverfish.hit";
/*     */   }
/*     */   
/*     */   protected String bp() {
/*  48 */     return "mob.silverfish.kill";
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  52 */     if (isInvulnerable(damagesource)) {
/*  53 */       return false;
/*     */     }
/*  55 */     if (((damagesource instanceof EntityDamageSource)) || (damagesource == DamageSource.MAGIC)) {
/*  56 */       this.a.f();
/*     */     }
/*     */     
/*  59 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */   
/*     */   protected void a(BlockPosition blockposition, Block block)
/*     */   {
/*  64 */     makeSound("mob.silverfish.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/*  68 */     return null;
/*     */   }
/*     */   
/*     */   public void t_() {
/*  72 */     this.aI = this.yaw;
/*  73 */     super.t_();
/*     */   }
/*     */   
/*     */   public float a(BlockPosition blockposition) {
/*  77 */     return this.world.getType(blockposition.down()).getBlock() == Blocks.STONE ? 10.0F : super.a(blockposition);
/*     */   }
/*     */   
/*     */   protected boolean n_() {
/*  81 */     return true;
/*     */   }
/*     */   
/*     */   public boolean bR() {
/*  85 */     if (super.bR()) {
/*  86 */       EntityHuman entityhuman = this.world.findNearbyPlayer(this, 5.0D);
/*     */       
/*  88 */       return entityhuman == null;
/*     */     }
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   public EnumMonsterType getMonsterType()
/*     */   {
/*  95 */     return EnumMonsterType.ARTHROPOD;
/*     */   }
/*     */   
/*     */   static class PathfinderGoalSilverfishHideInBlock extends PathfinderGoalRandomStroll
/*     */   {
/*     */     private final EntitySilverfish silverfish;
/*     */     private EnumDirection b;
/*     */     private boolean c;
/*     */     
/*     */     public PathfinderGoalSilverfishHideInBlock(EntitySilverfish entitysilverfish) {
/* 105 */       super(1.0D, 10);
/* 106 */       this.silverfish = entitysilverfish;
/* 107 */       a(1);
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 111 */       if (this.silverfish.getGoalTarget() != null)
/* 112 */         return false;
/* 113 */       if (!this.silverfish.getNavigation().m()) {
/* 114 */         return false;
/*     */       }
/* 116 */       Random random = this.silverfish.bc();
/*     */       
/* 118 */       if (random.nextInt(10) == 0) {
/* 119 */         this.b = EnumDirection.a(random);
/* 120 */         BlockPosition blockposition = new BlockPosition(this.silverfish.locX, this.silverfish.locY + 0.5D, this.silverfish.locZ).shift(this.b);
/* 121 */         IBlockData iblockdata = this.silverfish.world.getType(blockposition);
/*     */         
/* 123 */         if (BlockMonsterEggs.d(iblockdata)) {
/* 124 */           this.c = true;
/* 125 */           return true;
/*     */         }
/*     */       }
/*     */       
/* 129 */       this.c = false;
/* 130 */       return super.a();
/*     */     }
/*     */     
/*     */     public boolean b()
/*     */     {
/* 135 */       return this.c ? false : super.b();
/*     */     }
/*     */     
/*     */     public void c() {
/* 139 */       if (!this.c) {
/* 140 */         super.c();
/*     */       } else {
/* 142 */         World world = this.silverfish.world;
/* 143 */         BlockPosition blockposition = new BlockPosition(this.silverfish.locX, this.silverfish.locY + 0.5D, this.silverfish.locZ).shift(this.b);
/* 144 */         IBlockData iblockdata = world.getType(blockposition);
/*     */         
/* 146 */         if (BlockMonsterEggs.d(iblockdata))
/*     */         {
/* 148 */           if (org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callEntityChangeBlockEvent(this.silverfish, blockposition.getX(), blockposition.getY(), blockposition.getZ(), Blocks.MONSTER_EGG, Block.getId(BlockMonsterEggs.getById(iblockdata.getBlock().toLegacyData(iblockdata)))).isCancelled()) {
/* 149 */             return;
/*     */           }
/*     */           
/* 152 */           world.setTypeAndData(blockposition, Blocks.MONSTER_EGG.getBlockData().set(BlockMonsterEggs.VARIANT, BlockMonsterEggs.EnumMonsterEggVarient.a(iblockdata)), 3);
/* 153 */           this.silverfish.y();
/* 154 */           this.silverfish.die();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalSilverfishWakeOthers extends PathfinderGoal
/*     */   {
/*     */     private EntitySilverfish silverfish;
/*     */     private int b;
/*     */     
/*     */     public PathfinderGoalSilverfishWakeOthers(EntitySilverfish entitysilverfish)
/*     */     {
/* 167 */       this.silverfish = entitysilverfish;
/*     */     }
/*     */     
/*     */     public void f() {
/* 171 */       if (this.b == 0) {
/* 172 */         this.b = 20;
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean a()
/*     */     {
/* 178 */       return this.b > 0;
/*     */     }
/*     */     
/*     */     public void e() {
/* 182 */       this.b -= 1;
/* 183 */       if (this.b <= 0) {
/* 184 */         World world = this.silverfish.world;
/* 185 */         Random random = this.silverfish.bc();
/* 186 */         BlockPosition blockposition = new BlockPosition(this.silverfish);
/*     */         
/* 188 */         for (int i = 0; (i <= 5) && (i >= -5); i = i <= 0 ? 1 - i : 0 - i) {
/* 189 */           for (int j = 0; (j <= 10) && (j >= -10); j = j <= 0 ? 1 - j : 0 - j) {
/* 190 */             for (int k = 0; (k <= 10) && (k >= -10); k = k <= 0 ? 1 - k : 0 - k) {
/* 191 */               BlockPosition blockposition1 = blockposition.a(j, i, k);
/* 192 */               IBlockData iblockdata = world.getType(blockposition1);
/*     */               
/* 194 */               if (iblockdata.getBlock() == Blocks.MONSTER_EGG)
/*     */               {
/* 196 */                 if (!org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callEntityChangeBlockEvent(this.silverfish, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), Blocks.AIR, 0).isCancelled())
/*     */                 {
/*     */ 
/*     */ 
/* 200 */                   if (world.getGameRules().getBoolean("mobGriefing")) {
/* 201 */                     world.setAir(blockposition1, true);
/*     */                   } else {
/* 203 */                     world.setTypeAndData(blockposition1, ((BlockMonsterEggs.EnumMonsterEggVarient)iblockdata.get(BlockMonsterEggs.VARIANT)).d(), 3);
/*     */                   }
/*     */                   
/* 206 */                   if (random.nextBoolean()) {
/* 207 */                     return;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntitySilverfish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */