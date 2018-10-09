/*    */ package com.mojang.authlib.minecraft;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.Calendar;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class InsecureTextureException extends RuntimeException
/*    */ {
/*    */   public InsecureTextureException(String paramString)
/*    */   {
/* 11 */     super(paramString);
/*    */   }
/*    */   
/*    */   public static class OutdatedTextureException extends InsecureTextureException {
/*    */     private final java.util.Date validFrom;
/*    */     private final Calendar limit;
/*    */     
/*    */     public OutdatedTextureException(java.util.Date paramDate, Calendar paramCalendar) {
/* 19 */       super();
/* 20 */       this.validFrom = paramDate;
/* 21 */       this.limit = paramCalendar;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class WrongTextureOwnerException extends InsecureTextureException {
/*    */     private final GameProfile expected;
/*    */     private final UUID resultId;
/*    */     private final String resultName;
/*    */     
/*    */     public WrongTextureOwnerException(GameProfile paramGameProfile, UUID paramUUID, String paramString) {
/* 31 */       super();
/* 32 */       this.expected = paramGameProfile;
/* 33 */       this.resultId = paramUUID;
/* 34 */       this.resultName = paramString;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class MissingTextureException extends InsecureTextureException {
/*    */     public MissingTextureException() {
/* 40 */       super();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\minecraft\InsecureTextureException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */