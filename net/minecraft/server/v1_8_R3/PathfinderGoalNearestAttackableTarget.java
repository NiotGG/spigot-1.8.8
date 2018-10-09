/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
/*     */ 
/*     */ public class PathfinderGoalNearestAttackableTarget<T extends EntityLiving> extends PathfinderGoalTarget
/*     */ {
/*     */   protected final Class<T> a;
/*     */   private final int g;
/*     */   protected final DistanceComparator b;
/*     */   protected Predicate<? super T> c;
/*     */   protected EntityLiving d;
/*     */   
/*     */   public PathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class<T> oclass, boolean flag)
/*     */   {
/*  18 */     this(entitycreature, oclass, flag, false);
/*     */   }
/*     */   
/*     */   public PathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class<T> oclass, boolean flag, boolean flag1) {
/*  22 */     this(entitycreature, oclass, 10, flag, flag1, null);
/*     */   }
/*     */   
/*     */   public PathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class<T> oclass, int i, boolean flag, boolean flag1, final Predicate<? super T> predicate) {
/*  26 */     super(entitycreature, flag, flag1);
/*  27 */     this.a = oclass;
/*  28 */     this.g = i;
/*  29 */     this.b = new DistanceComparator(entitycreature);
/*  30 */     a(1);
/*  31 */     this.c = new Predicate() {
/*     */       public boolean a(T t0) {
/*  33 */         if ((predicate != null) && (!predicate.apply(t0))) {
/*  34 */           return false;
/*     */         }
/*  36 */         if ((t0 instanceof EntityHuman)) {
/*  37 */           double d0 = PathfinderGoalNearestAttackableTarget.this.f();
/*     */           
/*  39 */           if (t0.isSneaking()) {
/*  40 */             d0 *= 0.800000011920929D;
/*     */           }
/*     */           
/*  43 */           if (t0.isInvisible()) {
/*  44 */             float f = ((EntityHuman)t0).bY();
/*     */             
/*  46 */             if (f < 0.1F) {
/*  47 */               f = 0.1F;
/*     */             }
/*     */             
/*  50 */             d0 *= 0.7F * f;
/*     */           }
/*     */           
/*  53 */           if (t0.g(PathfinderGoalNearestAttackableTarget.this.e) > d0) {
/*  54 */             return false;
/*     */           }
/*     */         }
/*     */         
/*  58 */         return PathfinderGoalNearestAttackableTarget.this.a(t0, false);
/*     */       }
/*     */       
/*     */       public boolean apply(Object object)
/*     */       {
/*  63 */         return a((EntityLiving)object);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public boolean a() {
/*  69 */     if ((this.g > 0) && (this.e.bc().nextInt(this.g) != 0)) {
/*  70 */       return false;
/*     */     }
/*  72 */     double d0 = f();
/*  73 */     List list = this.e.world.a(this.a, this.e.getBoundingBox().grow(d0, 4.0D, d0), Predicates.and(this.c, IEntitySelector.d));
/*     */     
/*  75 */     java.util.Collections.sort(list, this.b);
/*  76 */     if (list.isEmpty()) {
/*  77 */       return false;
/*     */     }
/*  79 */     this.d = ((EntityLiving)list.get(0));
/*  80 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void c()
/*     */   {
/*  86 */     this.e.setGoalTarget(this.d, (this.d instanceof EntityPlayer) ? EntityTargetEvent.TargetReason.CLOSEST_PLAYER : EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);
/*  87 */     super.c();
/*     */   }
/*     */   
/*     */   public static class DistanceComparator implements java.util.Comparator<Entity>
/*     */   {
/*     */     private final Entity a;
/*     */     
/*     */     public DistanceComparator(Entity entity) {
/*  95 */       this.a = entity;
/*     */     }
/*     */     
/*     */     public int a(Entity entity, Entity entity1) {
/*  99 */       double d0 = this.a.h(entity);
/* 100 */       double d1 = this.a.h(entity1);
/*     */       
/* 102 */       return d0 > d1 ? 1 : d0 < d1 ? -1 : 0;
/*     */     }
/*     */     
/*     */     public int compare(Entity object, Entity object1) {
/* 106 */       return a(object, object1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalNearestAttackableTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */