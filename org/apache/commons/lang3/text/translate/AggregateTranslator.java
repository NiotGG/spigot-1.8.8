/*    */ package org.apache.commons.lang3.text.translate;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import org.apache.commons.lang3.ArrayUtils;
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
/*    */ public class AggregateTranslator
/*    */   extends CharSequenceTranslator
/*    */ {
/*    */   private final CharSequenceTranslator[] translators;
/*    */   
/*    */   public AggregateTranslator(CharSequenceTranslator... paramVarArgs)
/*    */   {
/* 41 */     this.translators = ((CharSequenceTranslator[])ArrayUtils.clone(paramVarArgs));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
/*    */     throws IOException
/*    */   {
/* 51 */     for (CharSequenceTranslator localCharSequenceTranslator : this.translators) {
/* 52 */       int k = localCharSequenceTranslator.translate(paramCharSequence, paramInt, paramWriter);
/* 53 */       if (k != 0) {
/* 54 */         return k;
/*    */       }
/*    */     }
/* 57 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\translate\AggregateTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */