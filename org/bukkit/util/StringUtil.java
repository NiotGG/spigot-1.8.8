/*    */ package org.bukkit.util;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.apache.commons.lang.Validate;
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
/*    */ public class StringUtil
/*    */ {
/*    */   public static <T extends Collection<? super String>> T copyPartialMatches(String token, Iterable<String> originals, T collection)
/*    */     throws UnsupportedOperationException, IllegalArgumentException
/*    */   {
/* 26 */     Validate.notNull(token, "Search token cannot be null");
/* 27 */     Validate.notNull(collection, "Collection cannot be null");
/* 28 */     Validate.notNull(originals, "Originals cannot be null");
/*    */     
/* 30 */     for (String string : originals) {
/* 31 */       if (startsWithIgnoreCase(string, token)) {
/* 32 */         collection.add(string);
/*    */       }
/*    */     }
/*    */     
/* 36 */     return collection;
/*    */   }
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
/*    */   public static boolean startsWithIgnoreCase(String string, String prefix)
/*    */     throws IllegalArgumentException, NullPointerException
/*    */   {
/* 52 */     Validate.notNull(string, "Cannot check a null string for a match");
/* 53 */     if (string.length() < prefix.length()) {
/* 54 */       return false;
/*    */     }
/* 56 */     return string.regionMatches(true, 0, prefix, 0, prefix.length());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\util\StringUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */