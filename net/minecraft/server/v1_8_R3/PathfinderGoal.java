/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public abstract class PathfinderGoal {
/*    */   private int a;
/*    */   
/*    */   public abstract boolean a();
/*    */   
/*    */   public boolean b() {
/*  9 */     return a();
/*    */   }
/*    */   
/*    */   public boolean i() {
/* 13 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public void c() {}
/*    */   
/*    */ 
/*    */   public void d() {}
/*    */   
/*    */   public void e() {}
/*    */   
/*    */   public void a(int paramInt)
/*    */   {
/* 26 */     this.a = paramInt;
/*    */   }
/*    */   
/*    */   public int j() {
/* 30 */     return this.a;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */