/*    */ package gnu.trove.impl.unmodifiable;
/*    */ 
/*    */ import gnu.trove.list.TShortList;
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
/*    */ public class TUnmodifiableRandomAccessShortList
/*    */   extends TUnmodifiableShortList
/*    */   implements RandomAccess
/*    */ {
/*    */   private static final long serialVersionUID = -2542308836966382001L;
/*    */   
/*    */   public TUnmodifiableRandomAccessShortList(TShortList list)
/*    */   {
/* 58 */     super(list);
/*    */   }
/*    */   
/*    */   public TShortList subList(int fromIndex, int toIndex) {
/* 62 */     return new TUnmodifiableRandomAccessShortList(this.list.subList(fromIndex, toIndex));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private Object writeReplace()
/*    */   {
/* 72 */     return new TUnmodifiableShortList(this.list);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableRandomAccessShortList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */