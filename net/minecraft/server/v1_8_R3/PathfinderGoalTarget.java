/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.apache.commons.lang3.StringUtils;
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
/*     */ public abstract class PathfinderGoalTarget
/*     */   extends PathfinderGoal
/*     */ {
/*     */   protected final EntityCreature e;
/*     */   protected boolean f;
/*     */   private boolean a;
/*     */   private int b;
/*     */   private int c;
/*     */   private int d;
/*     */   
/*     */   public PathfinderGoalTarget(EntityCreature paramEntityCreature, boolean paramBoolean)
/*     */   {
/*  34 */     this(paramEntityCreature, paramBoolean, false);
/*     */   }
/*     */   
/*     */   public PathfinderGoalTarget(EntityCreature paramEntityCreature, boolean paramBoolean1, boolean paramBoolean2) {
/*  38 */     this.e = paramEntityCreature;
/*  39 */     this.f = paramBoolean1;
/*  40 */     this.a = paramBoolean2;
/*     */   }
/*     */   
/*     */   public boolean b()
/*     */   {
/*  45 */     EntityLiving localEntityLiving = this.e.getGoalTarget();
/*  46 */     if (localEntityLiving == null) {
/*  47 */       return false;
/*     */     }
/*  49 */     if (!localEntityLiving.isAlive()) {
/*  50 */       return false;
/*     */     }
/*     */     
/*  53 */     ScoreboardTeamBase localScoreboardTeamBase1 = this.e.getScoreboardTeam();
/*  54 */     ScoreboardTeamBase localScoreboardTeamBase2 = localEntityLiving.getScoreboardTeam();
/*  55 */     if ((localScoreboardTeamBase1 != null) && (localScoreboardTeamBase2 == localScoreboardTeamBase1)) {
/*  56 */       return false;
/*     */     }
/*     */     
/*  59 */     double d1 = f();
/*  60 */     if (this.e.h(localEntityLiving) > d1 * d1) {
/*  61 */       return false;
/*     */     }
/*  63 */     if (this.f) {
/*  64 */       if (this.e.getEntitySenses().a(localEntityLiving)) {
/*  65 */         this.d = 0;
/*     */       }
/*  67 */       else if (++this.d > 60) {
/*  68 */         return false;
/*     */       }
/*     */     }
/*     */     
/*  72 */     if (((localEntityLiving instanceof EntityHuman)) && 
/*  73 */       (((EntityHuman)localEntityLiving).abilities.isInvulnerable)) {
/*  74 */       return false;
/*     */     }
/*     */     
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   protected double f() {
/*  81 */     AttributeInstance localAttributeInstance = this.e.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
/*  82 */     return localAttributeInstance == null ? 16.0D : localAttributeInstance.getValue();
/*     */   }
/*     */   
/*     */   public void c()
/*     */   {
/*  87 */     this.b = 0;
/*  88 */     this.c = 0;
/*  89 */     this.d = 0;
/*     */   }
/*     */   
/*     */   public void d()
/*     */   {
/*  94 */     this.e.setGoalTarget(null);
/*     */   }
/*     */   
/*     */   public static boolean a(EntityInsentient paramEntityInsentient, EntityLiving paramEntityLiving, boolean paramBoolean1, boolean paramBoolean2) {
/*  98 */     if (paramEntityLiving == null) {
/*  99 */       return false;
/*     */     }
/* 101 */     if (paramEntityLiving == paramEntityInsentient) {
/* 102 */       return false;
/*     */     }
/* 104 */     if (!paramEntityLiving.isAlive()) {
/* 105 */       return false;
/*     */     }
/* 107 */     if (!paramEntityInsentient.a(paramEntityLiving.getClass())) {
/* 108 */       return false;
/*     */     }
/*     */     
/* 111 */     ScoreboardTeamBase localScoreboardTeamBase1 = paramEntityInsentient.getScoreboardTeam();
/* 112 */     ScoreboardTeamBase localScoreboardTeamBase2 = paramEntityLiving.getScoreboardTeam();
/* 113 */     if ((localScoreboardTeamBase1 != null) && (localScoreboardTeamBase2 == localScoreboardTeamBase1)) {
/* 114 */       return false;
/*     */     }
/*     */     
/* 117 */     if (((paramEntityInsentient instanceof EntityOwnable)) && (StringUtils.isNotEmpty(((EntityOwnable)paramEntityInsentient).getOwnerUUID()))) {
/* 118 */       if (((paramEntityLiving instanceof EntityOwnable)) && (((EntityOwnable)paramEntityInsentient).getOwnerUUID().equals(((EntityOwnable)paramEntityLiving).getOwnerUUID())))
/*     */       {
/* 120 */         return false;
/*     */       }
/*     */       
/* 123 */       if (paramEntityLiving == ((EntityOwnable)paramEntityInsentient).getOwner())
/*     */       {
/* 125 */         return false;
/*     */       }
/* 127 */     } else if (((paramEntityLiving instanceof EntityHuman)) && 
/* 128 */       (!paramBoolean1) && (((EntityHuman)paramEntityLiving).abilities.isInvulnerable)) {
/* 129 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 133 */     if ((paramBoolean2) && (!paramEntityInsentient.getEntitySenses().a(paramEntityLiving))) {
/* 134 */       return false;
/*     */     }
/*     */     
/* 137 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean a(EntityLiving paramEntityLiving, boolean paramBoolean) {
/* 141 */     if (!a(this.e, paramEntityLiving, paramBoolean, this.f)) {
/* 142 */       return false;
/*     */     }
/*     */     
/* 145 */     if (!this.e.e(new BlockPosition(paramEntityLiving))) {
/* 146 */       return false;
/*     */     }
/*     */     
/* 149 */     if (this.a) {
/* 150 */       if (--this.c <= 0) {
/* 151 */         this.b = 0;
/*     */       }
/* 153 */       if (this.b == 0) {
/* 154 */         this.b = (a(paramEntityLiving) ? 1 : 2);
/*     */       }
/* 156 */       if (this.b == 2) {
/* 157 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 161 */     return true;
/*     */   }
/*     */   
/*     */   private boolean a(EntityLiving paramEntityLiving) {
/* 165 */     this.c = (10 + this.e.bc().nextInt(5));
/* 166 */     PathEntity localPathEntity = this.e.getNavigation().a(paramEntityLiving);
/* 167 */     if (localPathEntity == null) {
/* 168 */       return false;
/*     */     }
/* 170 */     PathPoint localPathPoint = localPathEntity.c();
/* 171 */     if (localPathPoint == null) {
/* 172 */       return false;
/*     */     }
/* 174 */     int i = localPathPoint.a - MathHelper.floor(paramEntityLiving.locX);
/* 175 */     int j = localPathPoint.c - MathHelper.floor(paramEntityLiving.locZ);
/* 176 */     return i * i + j * j <= 2.25D;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */