/*    */ package com.mysql.jdbc.profiler;
/*    */ 
/*    */ import com.mysql.jdbc.Connection;
/*    */ import com.mysql.jdbc.log.Log;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Properties;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoggingProfilerEventHandler
/*    */   implements ProfilerEventHandler
/*    */ {
/*    */   private Log log;
/*    */   
/*    */   public void consumeEvent(ProfilerEvent evt)
/*    */   {
/* 44 */     if (evt.eventType == 0) {
/* 45 */       this.log.logWarn(evt);
/*    */     } else {
/* 47 */       this.log.logInfo(evt);
/*    */     }
/*    */   }
/*    */   
/*    */   public void destroy() {
/* 52 */     this.log = null;
/*    */   }
/*    */   
/*    */   public void init(Connection conn, Properties props) throws SQLException {
/* 56 */     this.log = conn.getLog();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\profiler\LoggingProfilerEventHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */