/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalAvoidTarget<T extends Entity>
/*    */   extends PathfinderGoal
/*    */ {
/* 17 */   private final Predicate<Entity> c = new Predicate()
/*    */   {
/*    */     public boolean a(Entity paramAnonymousEntity) {
/* 20 */       return (paramAnonymousEntity.isAlive()) && (PathfinderGoalAvoidTarget.this.a.getEntitySenses().a(paramAnonymousEntity));
/*    */     }
/*    */   };
/*    */   protected EntityCreature a;
/*    */   private double d;
/*    */   private double e;
/*    */   protected T b;
/*    */   private float f;
/*    */   private PathEntity g;
/*    */   private NavigationAbstract h;
/*    */   private Class<T> i;
/*    */   private Predicate<? super T> j;
/*    */   
/*    */   public PathfinderGoalAvoidTarget(EntityCreature paramEntityCreature, Class<T> paramClass, float paramFloat, double paramDouble1, double paramDouble2) {
/* 34 */     this(paramEntityCreature, paramClass, Predicates.alwaysTrue(), paramFloat, paramDouble1, paramDouble2);
/*    */   }
/*    */   
/*    */   public PathfinderGoalAvoidTarget(EntityCreature paramEntityCreature, Class<T> paramClass, Predicate<? super T> paramPredicate, float paramFloat, double paramDouble1, double paramDouble2) {
/* 38 */     this.a = paramEntityCreature;
/* 39 */     this.i = paramClass;
/* 40 */     this.j = paramPredicate;
/* 41 */     this.f = paramFloat;
/* 42 */     this.d = paramDouble1;
/* 43 */     this.e = paramDouble2;
/* 44 */     this.h = paramEntityCreature.getNavigation();
/* 45 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 50 */     List localList = this.a.world.a(this.i, this.a.getBoundingBox().grow(this.f, 3.0D, this.f), Predicates.and(new Predicate[] { IEntitySelector.d, this.c, this.j }));
/* 51 */     if (localList.isEmpty()) {
/* 52 */       return false;
/*    */     }
/* 54 */     this.b = ((Entity)localList.get(0));
/*    */     
/* 56 */     Vec3D localVec3D = RandomPositionGenerator.b(this.a, 16, 7, new Vec3D(this.b.locX, this.b.locY, this.b.locZ));
/* 57 */     if (localVec3D == null) {
/* 58 */       return false;
/*    */     }
/* 60 */     if (this.b.e(localVec3D.a, localVec3D.b, localVec3D.c) < this.b.h(this.a)) {
/* 61 */       return false;
/*    */     }
/* 63 */     this.g = this.h.a(localVec3D.a, localVec3D.b, localVec3D.c);
/* 64 */     if (this.g == null) {
/* 65 */       return false;
/*    */     }
/* 67 */     if (!this.g.b(localVec3D)) {
/* 68 */       return false;
/*    */     }
/* 70 */     return true;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 75 */     return !this.h.m();
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 80 */     this.h.a(this.g, this.d);
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 85 */     this.b = null;
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 90 */     if (this.a.h(this.b) < 49.0D) {
/* 91 */       this.a.getNavigation().a(this.e);
/*    */     } else {
/* 93 */       this.a.getNavigation().a(this.d);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalAvoidTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */