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
/*    */ public class UnicodeUnpairedSurrogateRemover
/*    */   extends CodePointTranslator
/*    */ {
/*    */   public boolean translate(int paramInt, Writer paramWriter)
/*    */     throws IOException
/*    */   {
/* 34 */     if ((paramInt >= 55296) && (paramInt <= 57343))
/*    */     {
/* 36 */       return true;
/*    */     }
/*    */     
/* 39 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\translate\UnicodeUnpairedSurrogateRemover.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */