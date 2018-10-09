/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public abstract class EntityAmbient
/*    */   extends EntityInsentient
/*    */   implements IAnimal
/*    */ {
/*    */   public EntityAmbient(World paramWorld)
/*    */   {
/* 10 */     super(paramWorld);
/*    */   }
/*    */   
/*    */   public boolean cb()
/*    */   {
/* 15 */     return false;
/*    */   }
/*    */   
/*    */   protected boolean a(EntityHuman paramEntityHuman)
/*    */   {
/* 20 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityAmbient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */