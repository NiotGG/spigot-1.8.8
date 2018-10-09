/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class ControllerJump
/*    */ {
/*    */   private EntityInsentient b;
/*    */   protected boolean a;
/*    */   
/*    */   public ControllerJump(EntityInsentient paramEntityInsentient)
/*    */   {
/* 10 */     this.b = paramEntityInsentient;
/*    */   }
/*    */   
/*    */   public void a() {
/* 14 */     this.a = true;
/*    */   }
/*    */   
/*    */   public void b() {
/* 18 */     this.b.i(this.a);
/* 19 */     this.a = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ControllerJump.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */