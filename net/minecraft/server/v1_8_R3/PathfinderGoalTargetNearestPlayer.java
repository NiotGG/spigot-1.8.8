/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
/*     */ 
/*     */ public class PathfinderGoalTargetNearestPlayer extends PathfinderGoal
/*     */ {
/*  11 */   private static final Logger a = ;
/*     */   private EntityInsentient b;
/*     */   private final Predicate<Entity> c;
/*     */   private final PathfinderGoalNearestAttackableTarget.DistanceComparator d;
/*     */   private EntityLiving e;
/*     */   
/*     */   public PathfinderGoalTargetNearestPlayer(EntityInsentient entityinsentient) {
/*  18 */     this.b = entityinsentient;
/*  19 */     if ((entityinsentient instanceof EntityCreature)) {
/*  20 */       a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
/*     */     }
/*     */     
/*  23 */     this.c = new Predicate() {
/*     */       public boolean a(Entity entity) {
/*  25 */         if (!(entity instanceof EntityHuman))
/*  26 */           return false;
/*  27 */         if (((EntityHuman)entity).abilities.isInvulnerable) {
/*  28 */           return false;
/*     */         }
/*  30 */         double d0 = PathfinderGoalTargetNearestPlayer.this.f();
/*     */         
/*  32 */         if (entity.isSneaking()) {
/*  33 */           d0 *= 0.800000011920929D;
/*     */         }
/*     */         
/*  36 */         if (entity.isInvisible()) {
/*  37 */           float f = ((EntityHuman)entity).bY();
/*     */           
/*  39 */           if (f < 0.1F) {
/*  40 */             f = 0.1F;
/*     */           }
/*     */           
/*  43 */           d0 *= 0.7F * f;
/*     */         }
/*     */         
/*  46 */         return entity.g(PathfinderGoalTargetNearestPlayer.this.b) > d0 ? false : PathfinderGoalTarget.a(PathfinderGoalTargetNearestPlayer.this.b, (EntityLiving)entity, false, true);
/*     */       }
/*     */       
/*     */       public boolean apply(Object object)
/*     */       {
/*  51 */         return a((Entity)object);
/*     */       }
/*  53 */     };
/*  54 */     this.d = new PathfinderGoalNearestAttackableTarget.DistanceComparator(entityinsentient);
/*     */   }
/*     */   
/*     */   public boolean a() {
/*  58 */     double d0 = f();
/*  59 */     List list = this.b.world.a(EntityHuman.class, this.b.getBoundingBox().grow(d0, 4.0D, d0), this.c);
/*     */     
/*  61 */     java.util.Collections.sort(list, this.d);
/*  62 */     if (list.isEmpty()) {
/*  63 */       return false;
/*     */     }
/*  65 */     this.e = ((EntityLiving)list.get(0));
/*  66 */     return true;
/*     */   }
/*     */   
/*     */   public boolean b()
/*     */   {
/*  71 */     EntityLiving entityliving = this.b.getGoalTarget();
/*     */     
/*  73 */     if (entityliving == null)
/*  74 */       return false;
/*  75 */     if (!entityliving.isAlive())
/*  76 */       return false;
/*  77 */     if (((entityliving instanceof EntityHuman)) && (((EntityHuman)entityliving).abilities.isInvulnerable)) {
/*  78 */       return false;
/*     */     }
/*  80 */     ScoreboardTeamBase scoreboardteambase = this.b.getScoreboardTeam();
/*  81 */     ScoreboardTeamBase scoreboardteambase1 = entityliving.getScoreboardTeam();
/*     */     
/*  83 */     if ((scoreboardteambase != null) && (scoreboardteambase1 == scoreboardteambase)) {
/*  84 */       return false;
/*     */     }
/*  86 */     double d0 = f();
/*     */     
/*  88 */     return this.b.h(entityliving) <= d0 * d0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void c()
/*     */   {
/*  94 */     this.b.setGoalTarget(this.e, EntityTargetEvent.TargetReason.CLOSEST_PLAYER, true);
/*  95 */     super.c();
/*     */   }
/*     */   
/*     */   public void d() {
/*  99 */     this.b.setGoalTarget(null);
/* 100 */     super.c();
/*     */   }
/*     */   
/*     */   protected double f() {
/* 104 */     AttributeInstance attributeinstance = this.b.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
/*     */     
/* 106 */     return attributeinstance == null ? 16.0D : attributeinstance.getValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalTargetNearestPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */