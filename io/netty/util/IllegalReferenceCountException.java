/*    */ package io.netty.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IllegalReferenceCountException
/*    */   extends IllegalStateException
/*    */ {
/*    */   private static final long serialVersionUID = -2507492394288153468L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IllegalReferenceCountException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IllegalReferenceCountException(int paramInt)
/*    */   {
/* 30 */     this("refCnt: " + paramInt);
/*    */   }
/*    */   
/*    */   public IllegalReferenceCountException(int paramInt1, int paramInt2) {
/* 34 */     this("refCnt: " + paramInt1 + ", " + (paramInt2 > 0 ? "increment: " + paramInt2 : new StringBuilder().append("decrement: ").append(-paramInt2).toString()));
/*    */   }
/*    */   
/*    */   public IllegalReferenceCountException(String paramString) {
/* 38 */     super(paramString);
/*    */   }
/*    */   
/*    */   public IllegalReferenceCountException(String paramString, Throwable paramThrowable) {
/* 42 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */   public IllegalReferenceCountException(Throwable paramThrowable) {
/* 46 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\IllegalReferenceCountException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */