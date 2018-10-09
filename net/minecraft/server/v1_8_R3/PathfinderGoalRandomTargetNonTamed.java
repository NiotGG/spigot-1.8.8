/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ 
/*    */ public class PathfinderGoalRandomTargetNonTamed<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget
/*    */ {
/*    */   private EntityTameableAnimal g;
/*    */   
/*    */   public PathfinderGoalRandomTargetNonTamed(EntityTameableAnimal paramEntityTameableAnimal, Class<T> paramClass, boolean paramBoolean, Predicate<? super T> paramPredicate)
/*    */   {
/* 11 */     super(paramEntityTameableAnimal, paramClass, 10, paramBoolean, false, paramPredicate);
/* 12 */     this.g = paramEntityTameableAnimal;
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 17 */     return (!this.g.isTamed()) && (super.a());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalRandomTargetNonTamed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */