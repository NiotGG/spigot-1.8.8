/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocaleI18n
/*    */ {
/*  7 */   private static LocaleLanguage a = ;
/*  8 */   private static LocaleLanguage b = new LocaleLanguage();
/*    */   
/*    */   public static String get(String paramString)
/*    */   {
/* 12 */     return a.a(paramString);
/*    */   }
/*    */   
/*    */   public static String a(String paramString, Object... paramVarArgs)
/*    */   {
/* 17 */     return a.a(paramString, paramVarArgs);
/*    */   }
/*    */   
/*    */   public static String b(String paramString)
/*    */   {
/* 22 */     return b.a(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static boolean c(String paramString)
/*    */   {
/* 32 */     return a.b(paramString);
/*    */   }
/*    */   
/*    */   public static long a() {
/* 36 */     return a.c();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\LocaleI18n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */