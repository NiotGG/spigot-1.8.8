/*    */ package org.bukkit.util;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ 
/*    */ public class Java15Compat
/*    */ {
/*    */   public static <T> T[] Arrays_copyOfRange(T[] original, int start, int end) {
/*  8 */     if ((original.length >= start) && (start >= 0)) {
/*  9 */       if (start <= end) {
/* 10 */         int length = end - start;
/* 11 */         int copyLength = Math.min(length, original.length - start);
/* 12 */         Object[] copy = (Object[])Array.newInstance(original.getClass().getComponentType(), length);
/*    */         
/* 14 */         System.arraycopy(original, start, copy, 0, copyLength);
/* 15 */         return copy;
/*    */       }
/* 17 */       throw new IllegalArgumentException();
/*    */     }
/* 19 */     throw new ArrayIndexOutOfBoundsException();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\util\Java15Compat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */