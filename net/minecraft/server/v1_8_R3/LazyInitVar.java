/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public abstract class LazyInitVar<T> {
/*    */   private T a;
/*  5 */   private boolean b = false;
/*    */   
/*    */   public T c() {
/*  8 */     if (!this.b) {
/*  9 */       this.b = true;
/* 10 */       this.a = init();
/*    */     }
/*    */     
/* 13 */     return (T)this.a;
/*    */   }
/*    */   
/*    */   protected abstract T init();
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\LazyInitVar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */