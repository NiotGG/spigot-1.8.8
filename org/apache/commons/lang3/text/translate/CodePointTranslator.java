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
/*    */ public abstract class CodePointTranslator
/*    */   extends CharSequenceTranslator
/*    */ {
/*    */   public final int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
/*    */     throws IOException
/*    */   {
/* 37 */     int i = Character.codePointAt(paramCharSequence, paramInt);
/* 38 */     boolean bool = translate(i, paramWriter);
/* 39 */     return bool ? 1 : 0;
/*    */   }
/*    */   
/*    */   public abstract boolean translate(int paramInt, Writer paramWriter)
/*    */     throws IOException;
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\translate\CodePointTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */