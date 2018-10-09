/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
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
/*     */ public class EntityWitch
/*     */   extends EntityMonster
/*     */   implements IRangedEntity
/*     */ {
/*  29 */   private static final UUID a = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
/*  30 */   private static final AttributeModifier b = new AttributeModifier(a, "Drinking speed penalty", -0.25D, 0).a(false);
/*     */   
/*     */ 
/*  33 */   private static final Item[] c = { Items.GLOWSTONE_DUST, Items.SUGAR, Items.REDSTONE, Items.SPIDER_EYE, Items.GLASS_BOTTLE, Items.GUNPOWDER, Items.STICK, Items.STICK };
/*     */   
/*     */   private int bm;
/*     */   
/*     */ 
/*     */   public EntityWitch(World paramWorld)
/*     */   {
/*  40 */     super(paramWorld);
/*  41 */     setSize(0.6F, 1.95F);
/*     */     
/*  43 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  44 */     this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 60, 10.0F));
/*  45 */     this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 1.0D));
/*  46 */     this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  47 */     this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
/*     */     
/*  49 */     this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
/*  50 */     this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
/*     */   }
/*     */   
/*     */   protected void h()
/*     */   {
/*  55 */     super.h();
/*     */     
/*  57 */     getDataWatcher().a(21, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   protected String z()
/*     */   {
/*  62 */     return null;
/*     */   }
/*     */   
/*     */   protected String bo()
/*     */   {
/*  67 */     return null;
/*     */   }
/*     */   
/*     */   protected String bp()
/*     */   {
/*  72 */     return null;
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/*  76 */     getDataWatcher().watch(21, Byte.valueOf((byte)(paramBoolean ? 1 : 0)));
/*     */   }
/*     */   
/*     */   public boolean n() {
/*  80 */     return getDataWatcher().getByte(21) == 1;
/*     */   }
/*     */   
/*     */   protected void initAttributes()
/*     */   {
/*  85 */     super.initAttributes();
/*     */     
/*  87 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(26.0D);
/*  88 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
/*     */   }
/*     */   
/*     */   public void m()
/*     */   {
/*  93 */     if (!this.world.isClientSide) { Object localObject;
/*  94 */       if (n()) {
/*  95 */         if (this.bm-- <= 0) {
/*  96 */           a(false);
/*  97 */           ItemStack localItemStack = bA();
/*  98 */           setEquipment(0, null);
/*     */           
/* 100 */           if ((localItemStack != null) && (localItemStack.getItem() == Items.POTION)) {
/* 101 */             localObject = Items.POTION.h(localItemStack);
/* 102 */             if (localObject != null) {
/* 103 */               for (MobEffect localMobEffect : (List)localObject) {
/* 104 */                 addEffect(new MobEffect(localMobEffect));
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 109 */           getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).c(b);
/*     */         }
/*     */       } else {
/* 112 */         int i = -1;
/*     */         
/* 114 */         if ((this.random.nextFloat() < 0.15F) && (a(Material.WATER)) && (!hasEffect(MobEffectList.WATER_BREATHING))) {
/* 115 */           i = 8237;
/* 116 */         } else if ((this.random.nextFloat() < 0.15F) && (isBurning()) && (!hasEffect(MobEffectList.FIRE_RESISTANCE))) {
/* 117 */           i = 16307;
/* 118 */         } else if ((this.random.nextFloat() < 0.05F) && (getHealth() < getMaxHealth())) {
/* 119 */           i = 16341;
/* 120 */         } else if ((this.random.nextFloat() < 0.25F) && (getGoalTarget() != null) && (!hasEffect(MobEffectList.FASTER_MOVEMENT)) && (getGoalTarget().h(this) > 121.0D)) {
/* 121 */           i = 16274;
/* 122 */         } else if ((this.random.nextFloat() < 0.25F) && (getGoalTarget() != null) && (!hasEffect(MobEffectList.FASTER_MOVEMENT)) && (getGoalTarget().h(this) > 121.0D)) {
/* 123 */           i = 16274;
/*     */         }
/*     */         
/* 126 */         if (i > -1) {
/* 127 */           setEquipment(0, new ItemStack(Items.POTION, 1, i));
/* 128 */           this.bm = bA().l();
/* 129 */           a(true);
/* 130 */           localObject = getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
/* 131 */           ((AttributeInstance)localObject).c(b);
/* 132 */           ((AttributeInstance)localObject).b(b);
/*     */         }
/*     */       }
/*     */       
/* 136 */       if (this.random.nextFloat() < 7.5E-4F) {
/* 137 */         this.world.broadcastEntityEffect(this, (byte)15);
/*     */       }
/*     */     }
/*     */     
/* 141 */     super.m();
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
/*     */   protected float applyMagicModifier(DamageSource paramDamageSource, float paramFloat)
/*     */   {
/* 157 */     paramFloat = super.applyMagicModifier(paramDamageSource, paramFloat);
/*     */     
/* 159 */     if (paramDamageSource.getEntity() == this) {
/* 160 */       paramFloat = 0.0F;
/*     */     }
/* 162 */     if (paramDamageSource.isMagic()) {
/* 163 */       paramFloat = (float)(paramFloat * 0.15D);
/*     */     }
/*     */     
/* 166 */     return paramFloat;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean paramBoolean, int paramInt)
/*     */   {
/* 171 */     int i = this.random.nextInt(3) + 1;
/* 172 */     for (int j = 0; j < i; j++) {
/* 173 */       int k = this.random.nextInt(3);
/* 174 */       Item localItem = c[this.random.nextInt(c.length)];
/* 175 */       if (paramInt > 0) {
/* 176 */         k += this.random.nextInt(paramInt + 1);
/*     */       }
/*     */       
/* 179 */       for (int m = 0; m < k; m++) {
/* 180 */         a(localItem, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(EntityLiving paramEntityLiving, float paramFloat)
/*     */   {
/* 187 */     if (n()) {
/* 188 */       return;
/*     */     }
/*     */     
/* 191 */     EntityPotion localEntityPotion = new EntityPotion(this.world, this, 32732);
/* 192 */     double d1 = paramEntityLiving.locY + paramEntityLiving.getHeadHeight() - 1.100000023841858D;
/* 193 */     localEntityPotion.pitch -= -20.0F;
/* 194 */     double d2 = paramEntityLiving.locX + paramEntityLiving.motX - this.locX;
/* 195 */     double d3 = d1 - this.locY;
/* 196 */     double d4 = paramEntityLiving.locZ + paramEntityLiving.motZ - this.locZ;
/* 197 */     float f = MathHelper.sqrt(d2 * d2 + d4 * d4);
/*     */     
/* 199 */     if ((f >= 8.0F) && (!paramEntityLiving.hasEffect(MobEffectList.SLOWER_MOVEMENT))) {
/* 200 */       localEntityPotion.setPotionValue(32698);
/* 201 */     } else if ((paramEntityLiving.getHealth() >= 8.0F) && (!paramEntityLiving.hasEffect(MobEffectList.POISON))) {
/* 202 */       localEntityPotion.setPotionValue(32660);
/* 203 */     } else if ((f <= 3.0F) && (!paramEntityLiving.hasEffect(MobEffectList.WEAKNESS)) && (this.random.nextFloat() < 0.25F)) {
/* 204 */       localEntityPotion.setPotionValue(32696);
/*     */     }
/*     */     
/* 207 */     localEntityPotion.shoot(d2, d3 + f * 0.2F, d4, 0.75F, 8.0F);
/*     */     
/* 209 */     this.world.addEntity(localEntityPotion);
/*     */   }
/*     */   
/*     */   public float getHeadHeight()
/*     */   {
/* 214 */     return 1.62F;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityWitch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */