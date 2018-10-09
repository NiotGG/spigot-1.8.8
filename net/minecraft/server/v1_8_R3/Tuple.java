/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class Tuple<A, B> {
/*    */   private A a;
/*    */   private B b;
/*    */   
/*    */   public Tuple(A paramA, B paramB) {
/*  8 */     this.a = paramA;
/*  9 */     this.b = paramB;
/*    */   }
/*    */   
/*    */   public A a() {
/* 13 */     return (A)this.a;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public B b()
/*    */   {
/* 21 */     return (B)this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Tuple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */