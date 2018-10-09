/*    */ package org.yaml.snakeyaml.tokens;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
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
/*    */ public final class DocumentEndToken
/*    */   extends Token
/*    */ {
/*    */   public DocumentEndToken(Mark startMark, Mark endMark)
/*    */   {
/* 23 */     super(startMark, endMark);
/*    */   }
/*    */   
/*    */   public Token.ID getTokenId()
/*    */   {
/* 28 */     return Token.ID.DocumentEnd;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\tokens\DocumentEndToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */