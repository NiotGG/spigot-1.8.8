/*    */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*    */ 
/*    */ public class LongHash {
/*    */   public static long toLong(int msw, int lsw) {
/*  5 */     return (msw << 32) + lsw - -2147483648L;
/*    */   }
/*    */   
/*    */   public static int msw(long l) {
/*  9 */     return (int)(l >> 32);
/*    */   }
/*    */   
/*    */   public static int lsw(long l) {
/* 13 */     return (int)(l & 0xFFFFFFFFFFFFFFFF) + Integer.MIN_VALUE;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\LongHash.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */