/*    */ package org.apache.commons.lang3.text.translate;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
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
/*    */ public class OctalUnescaper
/*    */   extends CharSequenceTranslator
/*    */ {
/*    */   public int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
/*    */     throws IOException
/*    */   {
/* 40 */     int i = paramCharSequence.length() - paramInt - 1;
/* 41 */     StringBuilder localStringBuilder = new StringBuilder();
/* 42 */     if ((paramCharSequence.charAt(paramInt) == '\\') && (i > 0) && (isOctalDigit(paramCharSequence.charAt(paramInt + 1)))) {
/* 43 */       int j = paramInt + 1;
/* 44 */       int k = paramInt + 2;
/* 45 */       int m = paramInt + 3;
/*    */       
/*    */ 
/* 48 */       localStringBuilder.append(paramCharSequence.charAt(j));
/*    */       
/* 50 */       if ((i > 1) && (isOctalDigit(paramCharSequence.charAt(k)))) {
/* 51 */         localStringBuilder.append(paramCharSequence.charAt(k));
/* 52 */         if ((i > 2) && (isZeroToThree(paramCharSequence.charAt(j))) && (isOctalDigit(paramCharSequence.charAt(m)))) {
/* 53 */           localStringBuilder.append(paramCharSequence.charAt(m));
/*    */         }
/*    */       }
/*    */       
/* 57 */       paramWriter.write(Integer.parseInt(localStringBuilder.toString(), 8));
/* 58 */       return 1 + localStringBuilder.length();
/*    */     }
/* 60 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private boolean isOctalDigit(char paramChar)
/*    */   {
/* 69 */     return (paramChar >= '0') && (paramChar <= '7');
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private boolean isZeroToThree(char paramChar)
/*    */   {
/* 78 */     return (paramChar >= '0') && (paramChar <= '3');
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\translate\OctalUnescaper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */