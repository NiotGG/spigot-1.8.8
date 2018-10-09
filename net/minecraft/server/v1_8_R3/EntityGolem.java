/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public abstract class EntityGolem
/*    */   extends EntityCreature
/*    */   implements IAnimal
/*    */ {
/*    */   public EntityGolem(World paramWorld)
/*    */   {
/*  9 */     super(paramWorld);
/*    */   }
/*    */   
/*    */ 
/*    */   public void e(float paramFloat1, float paramFloat2) {}
/*    */   
/*    */ 
/*    */   protected String z()
/*    */   {
/* 18 */     return "none";
/*    */   }
/*    */   
/*    */   protected String bo()
/*    */   {
/* 23 */     return "none";
/*    */   }
/*    */   
/*    */   protected String bp()
/*    */   {
/* 28 */     return "none";
/*    */   }
/*    */   
/*    */   public int w()
/*    */   {
/* 33 */     return 120;
/*    */   }
/*    */   
/*    */   protected boolean isTypeNotPersistent()
/*    */   {
/* 38 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityGolem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */