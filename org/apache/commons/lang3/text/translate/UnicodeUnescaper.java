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
/*    */ public class UnicodeUnescaper
/*    */   extends CharSequenceTranslator
/*    */ {
/*    */   public int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
/*    */     throws IOException
/*    */   {
/* 37 */     if ((paramCharSequence.charAt(paramInt) == '\\') && (paramInt + 1 < paramCharSequence.length()) && (paramCharSequence.charAt(paramInt + 1) == 'u'))
/*    */     {
/* 39 */       int i = 2;
/* 40 */       while ((paramInt + i < paramCharSequence.length()) && (paramCharSequence.charAt(paramInt + i) == 'u')) {
/* 41 */         i++;
/*    */       }
/*    */       
/* 44 */       if ((paramInt + i < paramCharSequence.length()) && (paramCharSequence.charAt(paramInt + i) == '+')) {
/* 45 */         i++;
/*    */       }
/*    */       
/* 48 */       if (paramInt + i + 4 <= paramCharSequence.length())
/*    */       {
/* 50 */         CharSequence localCharSequence = paramCharSequence.subSequence(paramInt + i, paramInt + i + 4);
/*    */         try
/*    */         {
/* 53 */           int j = Integer.parseInt(localCharSequence.toString(), 16);
/* 54 */           paramWriter.write((char)j);
/*    */         } catch (NumberFormatException localNumberFormatException) {
/* 56 */           throw new IllegalArgumentException("Unable to parse unicode value: " + localCharSequence, localNumberFormatException);
/*    */         }
/* 58 */         return i + 4;
/*    */       }
/* 60 */       throw new IllegalArgumentException("Less than 4 hex digits in unicode value: '" + paramCharSequence.subSequence(paramInt, paramCharSequence.length()) + "' due to end of CharSequence");
/*    */     }
/*    */     
/*    */ 
/* 64 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\translate\UnicodeUnescaper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */