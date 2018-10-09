/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import java.util.List;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
/*    */ 
/*    */ public class PathfinderGoalNearestAttackableTargetInsentient extends PathfinderGoal
/*    */ {
/* 11 */   private static final Logger a = ;
/*    */   private EntityInsentient b;
/*    */   private final Predicate<EntityLiving> c;
/*    */   private final PathfinderGoalNearestAttackableTarget.DistanceComparator d;
/*    */   private EntityLiving e;
/*    */   private Class<? extends EntityLiving> f;
/*    */   
/*    */   public PathfinderGoalNearestAttackableTargetInsentient(EntityInsentient entityinsentient, Class<? extends EntityLiving> oclass) {
/* 19 */     this.b = entityinsentient;
/* 20 */     this.f = oclass;
/* 21 */     if ((entityinsentient instanceof EntityCreature)) {
/* 22 */       a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
/*    */     }
/*    */     
/* 25 */     this.c = new Predicate() {
/*    */       public boolean a(EntityLiving entityliving) {
/* 27 */         double d0 = PathfinderGoalNearestAttackableTargetInsentient.this.f();
/*    */         
/* 29 */         if (entityliving.isSneaking()) {
/* 30 */           d0 *= 0.800000011920929D;
/*    */         }
/*    */         
/* 33 */         return entityliving.g(PathfinderGoalNearestAttackableTargetInsentient.this.b) > d0 ? false : entityliving.isInvisible() ? false : PathfinderGoalTarget.a(PathfinderGoalNearestAttackableTargetInsentient.this.b, entityliving, false, true);
/*    */       }
/*    */       
/*    */       public boolean apply(Object object) {
/* 37 */         return a((EntityLiving)object);
/*    */       }
/* 39 */     };
/* 40 */     this.d = new PathfinderGoalNearestAttackableTarget.DistanceComparator(entityinsentient);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 44 */     double d0 = f();
/* 45 */     List list = this.b.world.a(this.f, this.b.getBoundingBox().grow(d0, 4.0D, d0), this.c);
/*    */     
/* 47 */     java.util.Collections.sort(list, this.d);
/* 48 */     if (list.isEmpty()) {
/* 49 */       return false;
/*    */     }
/* 51 */     this.e = ((EntityLiving)list.get(0));
/* 52 */     return true;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 57 */     EntityLiving entityliving = this.b.getGoalTarget();
/*    */     
/* 59 */     if (entityliving == null)
/* 60 */       return false;
/* 61 */     if (!entityliving.isAlive()) {
/* 62 */       return false;
/*    */     }
/* 64 */     double d0 = f();
/*    */     
/* 66 */     return this.b.h(entityliving) <= d0 * d0;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 71 */     this.b.setGoalTarget(this.e, EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);
/* 72 */     super.c();
/*    */   }
/*    */   
/*    */   public void d() {
/* 76 */     this.b.setGoalTarget(null);
/* 77 */     super.c();
/*    */   }
/*    */   
/*    */   protected double f() {
/* 81 */     AttributeInstance attributeinstance = this.b.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
/*    */     
/* 83 */     return attributeinstance == null ? 16.0D : attributeinstance.getValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalNearestAttackableTargetInsentient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */