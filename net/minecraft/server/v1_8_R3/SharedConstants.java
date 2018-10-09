/*    */ package net.minecraft.server.v1_8_R3;
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
/*    */ public class SharedConstants
/*    */ {
/*    */   public static boolean isAllowedChatCharacter(char paramChar)
/*    */   {
/* 36 */     return (paramChar != '§') && (paramChar >= ' ') && (paramChar != '');
/*    */   }
/*    */   
/* 39 */   public static final char[] allowedCharacters = { '/', '\n', '\r', '\t', '\000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String a(String paramString)
/*    */   {
/* 48 */     StringBuilder localStringBuilder = new StringBuilder();
/*    */     
/* 50 */     for (char c : paramString.toCharArray()) {
/* 51 */       if (isAllowedChatCharacter(c)) {
/* 52 */         localStringBuilder.append(c);
/*    */       }
/*    */     }
/*    */     
/* 56 */     return localStringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\SharedConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */