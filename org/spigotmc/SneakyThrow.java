/*    */ package org.spigotmc;
/*    */ 
/*    */ 
/*    */ public class SneakyThrow
/*    */ {
/*    */   public static void sneaky(Throwable t)
/*    */   {
/*  8 */     throw ((RuntimeException)superSneaky(t));
/*    */   }
/*    */   
/*    */   private static <T extends Throwable> T superSneaky(Throwable t) throws Throwable
/*    */   {
/* 13 */     throw t;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\SneakyThrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */