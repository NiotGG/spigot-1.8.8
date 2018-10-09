/*    */ package gnu.trove.procedure.array;
/*    */ 
/*    */ import gnu.trove.procedure.TObjectProcedure;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ToObjectArrayProceedure<T>
/*    */   implements TObjectProcedure<T>
/*    */ {
/*    */   private final T[] target;
/* 37 */   private int pos = 0;
/*    */   
/*    */   public ToObjectArrayProceedure(T[] target)
/*    */   {
/* 41 */     this.target = target;
/*    */   }
/*    */   
/*    */   public final boolean execute(T value)
/*    */   {
/* 46 */     this.target[(this.pos++)] = value;
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\procedure\array\ToObjectArrayProceedure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */