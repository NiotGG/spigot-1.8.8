/*    */ package io.netty.handler.codec.http.multipart;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
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
/*    */ final class CaseIgnoringComparator
/*    */   implements Comparator<CharSequence>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4582133183775373862L;
/* 25 */   static final CaseIgnoringComparator INSTANCE = new CaseIgnoringComparator();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int compare(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*    */   {
/* 32 */     int i = paramCharSequence1.length();
/* 33 */     int j = paramCharSequence2.length();
/* 34 */     int k = Math.min(i, j);
/* 35 */     for (int m = 0; m < k; m++) {
/* 36 */       char c1 = paramCharSequence1.charAt(m);
/* 37 */       char c2 = paramCharSequence2.charAt(m);
/* 38 */       if (c1 != c2) {
/* 39 */         c1 = Character.toUpperCase(c1);
/* 40 */         c2 = Character.toUpperCase(c2);
/* 41 */         if (c1 != c2) {
/* 42 */           c1 = Character.toLowerCase(c1);
/* 43 */           c2 = Character.toLowerCase(c2);
/* 44 */           if (c1 != c2) {
/* 45 */             return c1 - c2;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 50 */     return i - j;
/*    */   }
/*    */   
/*    */   private Object readResolve() {
/* 54 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\CaseIgnoringComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */