/*    */ package com.google.thirdparty.publicsuffix;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
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
/*    */ @GwtCompatible
/*    */  enum PublicSuffixType
/*    */ {
/* 28 */   PRIVATE(':', ','), 
/*    */   
/* 30 */   ICANN('!', '?');
/*    */   
/*    */ 
/*    */   private final char innerNodeCode;
/*    */   
/*    */   private final char leafNodeCode;
/*    */   
/*    */   private PublicSuffixType(char innerNodeCode, char leafNodeCode)
/*    */   {
/* 39 */     this.innerNodeCode = innerNodeCode;
/* 40 */     this.leafNodeCode = leafNodeCode;
/*    */   }
/*    */   
/*    */   char getLeafNodeCode() {
/* 44 */     return this.leafNodeCode;
/*    */   }
/*    */   
/*    */   char getInnerNodeCode() {
/* 48 */     return this.innerNodeCode;
/*    */   }
/*    */   
/*    */   static PublicSuffixType fromCode(char code)
/*    */   {
/* 53 */     for (PublicSuffixType value : ) {
/* 54 */       if ((value.getInnerNodeCode() == code) || (value.getLeafNodeCode() == code)) {
/* 55 */         return value;
/*    */       }
/*    */     }
/* 58 */     throw new IllegalArgumentException("No enum corresponding to given code: " + code);
/*    */   }
/*    */   
/*    */   static PublicSuffixType fromIsPrivate(boolean isPrivate) {
/* 62 */     return isPrivate ? PRIVATE : ICANN;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\thirdparty\publicsuffix\PublicSuffixType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */