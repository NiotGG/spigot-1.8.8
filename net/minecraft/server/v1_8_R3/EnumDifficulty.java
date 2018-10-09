/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public enum EnumDifficulty
/*    */ {
/*    */   private static final EnumDifficulty[] e;
/*    */   
/*    */   private final int f;
/*    */   
/*    */   private final String g;
/*    */   
/*    */   private EnumDifficulty(int paramInt, String paramString)
/*    */   {
/* 14 */     this.f = paramInt;
/* 15 */     this.g = paramString;
/*    */   }
/*    */   
/*    */   public int a() {
/* 19 */     return this.f;
/*    */   }
/*    */   
/*    */   public static EnumDifficulty getById(int paramInt) {
/* 23 */     return e[(paramInt % e.length)];
/*    */   }
/*    */   
/*    */   static
/*    */   {
/*  9 */     e = new EnumDifficulty[values().length];
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
/* 27 */     for (EnumDifficulty localEnumDifficulty : values()) {
/* 28 */       e[localEnumDifficulty.f] = localEnumDifficulty;
/*    */     }
/*    */   }
/*    */   
/*    */   public String b() {
/* 33 */     return this.g;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnumDifficulty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */