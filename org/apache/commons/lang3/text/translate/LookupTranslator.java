/*    */ package org.apache.commons.lang3.text.translate;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.HashMap;
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
/*    */ public class LookupTranslator
/*    */   extends CharSequenceTranslator
/*    */ {
/*    */   private final HashMap<String, CharSequence> lookupMap;
/*    */   private final int shortest;
/*    */   private final int longest;
/*    */   
/*    */   public LookupTranslator(CharSequence[]... paramVarArgs)
/*    */   {
/* 46 */     this.lookupMap = new HashMap();
/* 47 */     int i = Integer.MAX_VALUE;
/* 48 */     int j = 0;
/* 49 */     if (paramVarArgs != null) {
/* 50 */       for (CharSequence[] arrayOfCharSequence1 : paramVarArgs) {
/* 51 */         this.lookupMap.put(arrayOfCharSequence1[0].toString(), arrayOfCharSequence1[1]);
/* 52 */         int n = arrayOfCharSequence1[0].length();
/* 53 */         if (n < i) {
/* 54 */           i = n;
/*    */         }
/* 56 */         if (n > j) {
/* 57 */           j = n;
/*    */         }
/*    */       }
/*    */     }
/* 61 */     this.shortest = i;
/* 62 */     this.longest = j;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
/*    */     throws IOException
/*    */   {
/* 70 */     int i = this.longest;
/* 71 */     if (paramInt + this.longest > paramCharSequence.length()) {
/* 72 */       i = paramCharSequence.length() - paramInt;
/*    */     }
/*    */     
/* 75 */     for (int j = i; j >= this.shortest; j--) {
/* 76 */       CharSequence localCharSequence1 = paramCharSequence.subSequence(paramInt, paramInt + j);
/* 77 */       CharSequence localCharSequence2 = (CharSequence)this.lookupMap.get(localCharSequence1.toString());
/* 78 */       if (localCharSequence2 != null) {
/* 79 */         paramWriter.write(localCharSequence2.toString());
/* 80 */         return j;
/*    */       }
/*    */     }
/* 83 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\translate\LookupTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */