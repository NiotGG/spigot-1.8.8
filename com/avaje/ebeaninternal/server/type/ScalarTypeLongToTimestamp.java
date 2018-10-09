/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.sql.Timestamp;
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
/*    */ public class ScalarTypeLongToTimestamp
/*    */   extends ScalarTypeWrapper<Long, Timestamp>
/*    */ {
/*    */   public ScalarTypeLongToTimestamp()
/*    */   {
/* 27 */     super(Long.class, new ScalarTypeTimestamp(), new LongToTimestampConverter());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeLongToTimestamp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */