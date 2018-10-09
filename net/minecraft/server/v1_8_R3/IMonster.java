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
/*    */ 
/*    */ public abstract interface IMonster
/*    */   extends IAnimal
/*    */ {
/* 15 */   public static final Predicate<Entity> d = new Predicate()
/*    */   {
/*    */     public boolean a(Entity paramAnonymousEntity) {
/* 18 */       return paramAnonymousEntity instanceof IMonster;
/*    */     }
/*    */   };
/*    */   
/* 22 */   public static final Predicate<Entity> e = new Predicate()
/*    */   {
/*    */     public boolean a(Entity paramAnonymousEntity) {
/* 25 */       return ((paramAnonymousEntity instanceof IMonster)) && (!paramAnonymousEntity.isInvisible());
/*    */     }
/*    */   };
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IMonster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */