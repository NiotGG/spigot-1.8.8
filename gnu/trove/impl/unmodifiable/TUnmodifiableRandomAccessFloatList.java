/*    */ package gnu.trove.impl.unmodifiable;
/*    */ 
/*    */ import gnu.trove.list.TFloatList;
/*    */ import java.util.RandomAccess;
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
/*    */ public class TUnmodifiableRandomAccessFloatList
/*    */   extends TUnmodifiableFloatList
/*    */   implements RandomAccess
/*    */ {
/*    */   private static final long serialVersionUID = -2542308836966382001L;
/*    */   
/*    */   public TUnmodifiableRandomAccessFloatList(TFloatList list)
/*    */   {
/* 58 */     super(list);
/*    */   }
/*    */   
/*    */   public TFloatList subList(int fromIndex, int toIndex) {
/* 62 */     return new TUnmodifiableRandomAccessFloatList(this.list.subList(fromIndex, toIndex));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private Object writeReplace()
/*    */   {
/* 72 */     return new TUnmodifiableFloatList(this.list);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableRandomAccessFloatList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */