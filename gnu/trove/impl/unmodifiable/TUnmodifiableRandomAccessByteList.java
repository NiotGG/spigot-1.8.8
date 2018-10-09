/*    */ package gnu.trove.impl.unmodifiable;
/*    */ 
/*    */ import gnu.trove.list.TByteList;
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
/*    */ public class TUnmodifiableRandomAccessByteList
/*    */   extends TUnmodifiableByteList
/*    */   implements RandomAccess
/*    */ {
/*    */   private static final long serialVersionUID = -2542308836966382001L;
/*    */   
/*    */   public TUnmodifiableRandomAccessByteList(TByteList list)
/*    */   {
/* 58 */     super(list);
/*    */   }
/*    */   
/*    */   public TByteList subList(int fromIndex, int toIndex) {
/* 62 */     return new TUnmodifiableRandomAccessByteList(this.list.subList(fromIndex, toIndex));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private Object writeReplace()
/*    */   {
/* 72 */     return new TUnmodifiableByteList(this.list);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableRandomAccessByteList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */