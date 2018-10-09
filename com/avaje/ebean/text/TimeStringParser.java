/*    */ package com.avaje.ebean.text;
/*    */ 
/*    */ import java.sql.Time;
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
/*    */ public final class TimeStringParser
/*    */   implements StringParser
/*    */ {
/* 31 */   private static final TimeStringParser SHARED = new TimeStringParser();
/*    */   
/*    */ 
/*    */ 
/*    */   public static TimeStringParser get()
/*    */   {
/* 37 */     return SHARED;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object parse(String value)
/*    */   {
/* 45 */     if ((value == null) || (value.trim().length() == 0)) {
/* 46 */       return null;
/*    */     }
/*    */     
/* 49 */     String s = value.trim();
/*    */     
/*    */ 
/* 52 */     int firstColon = s.indexOf(':');
/* 53 */     int secondColon = s.indexOf(':', firstColon + 1);
/*    */     
/* 55 */     if (firstColon == -1) {
/* 56 */       throw new IllegalArgumentException("No ':' in value [" + s + "]");
/*    */     }
/*    */     try {
/* 59 */       int hour = Integer.parseInt(s.substring(0, firstColon));
/* 60 */       int second; int minute; int second; if (secondColon == -1) {
/* 61 */         int minute = Integer.parseInt(s.substring(firstColon + 1, s.length()));
/* 62 */         second = 0;
/*    */       } else {
/* 64 */         minute = Integer.parseInt(s.substring(firstColon + 1, secondColon));
/* 65 */         second = Integer.parseInt(s.substring(secondColon + 1));
/*    */       }
/*    */       
/* 68 */       return new Time(hour, minute, second);
/*    */     }
/*    */     catch (NumberFormatException e) {
/* 71 */       throw new IllegalArgumentException("Number format Error parsing time [" + s + "] " + e.getMessage(), e);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\TimeStringParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */