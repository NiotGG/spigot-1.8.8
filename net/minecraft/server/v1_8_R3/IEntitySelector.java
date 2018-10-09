/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class IEntitySelector
/*    */ {
/* 13 */   public static final Predicate<Entity> a = new Predicate()
/*    */   {
/*    */     public boolean a(Entity paramAnonymousEntity) {
/* 16 */       return paramAnonymousEntity.isAlive();
/*    */     }
/*    */   };
/* 19 */   public static final Predicate<Entity> b = new Predicate()
/*    */   {
/*    */     public boolean a(Entity paramAnonymousEntity) {
/* 22 */       return (paramAnonymousEntity.isAlive()) && (paramAnonymousEntity.passenger == null) && (paramAnonymousEntity.vehicle == null);
/*    */     }
/*    */   };
/* 25 */   public static final Predicate<Entity> c = new Predicate()
/*    */   {
/*    */     public boolean a(Entity paramAnonymousEntity) {
/* 28 */       return ((paramAnonymousEntity instanceof IInventory)) && (paramAnonymousEntity.isAlive());
/*    */     }
/*    */   };
/* 31 */   public static final Predicate<Entity> d = new Predicate()
/*    */   {
/*    */     public boolean a(Entity paramAnonymousEntity) {
/* 34 */       return (!(paramAnonymousEntity instanceof EntityHuman)) || (!((EntityHuman)paramAnonymousEntity).isSpectator());
/*    */     }
/*    */   };
/*    */   
/*    */   public static class EntitySelectorEquipable implements Predicate<Entity> {
/*    */     private final ItemStack a;
/*    */     
/*    */     public EntitySelectorEquipable(ItemStack paramItemStack) {
/* 42 */       this.a = paramItemStack;
/*    */     }
/*    */     
/*    */     public boolean a(Entity paramEntity)
/*    */     {
/* 47 */       if (!paramEntity.isAlive()) {
/* 48 */         return false;
/*    */       }
/* 50 */       if (!(paramEntity instanceof EntityLiving)) {
/* 51 */         return false;
/*    */       }
/* 53 */       EntityLiving localEntityLiving = (EntityLiving)paramEntity;
/* 54 */       if (localEntityLiving.getEquipment(EntityInsentient.c(this.a)) != null) {
/* 55 */         return false;
/*    */       }
/*    */       
/* 58 */       if ((localEntityLiving instanceof EntityInsentient))
/* 59 */         return ((EntityInsentient)localEntityLiving).bY();
/* 60 */       if ((localEntityLiving instanceof EntityArmorStand))
/* 61 */         return true;
/* 62 */       if ((localEntityLiving instanceof EntityHuman)) {
/* 63 */         return true;
/*    */       }
/*    */       
/* 66 */       return false;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IEntitySelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */