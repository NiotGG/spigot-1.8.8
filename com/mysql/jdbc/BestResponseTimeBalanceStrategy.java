/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BestResponseTimeBalanceStrategy
/*     */   implements BalanceStrategy
/*     */ {
/*     */   public void destroy() {}
/*     */   
/*     */   public void init(Connection conn, Properties props)
/*     */     throws SQLException
/*     */   {}
/*     */   
/*     */   public ConnectionImpl pickConnection(LoadBalancingConnectionProxy proxy, List<String> configuredHosts, Map<String, ConnectionImpl> liveConnections, long[] responseTimes, int numRetries)
/*     */     throws SQLException
/*     */   {
/*  53 */     Map<String, Long> blackList = proxy.getGlobalBlacklist();
/*     */     
/*  55 */     SQLException ex = null;
/*     */     
/*  57 */     int attempts = 0; ConnectionImpl conn; for (;;) { if (attempts >= numRetries) break label240;
/*  58 */       long minResponseTime = Long.MAX_VALUE;
/*     */       
/*  60 */       int bestHostIndex = 0;
/*     */       
/*     */ 
/*  63 */       if (blackList.size() == configuredHosts.size()) {
/*  64 */         blackList = proxy.getGlobalBlacklist();
/*     */       }
/*     */       
/*  67 */       for (int i = 0; i < responseTimes.length; i++) {
/*  68 */         long candidateResponseTime = responseTimes[i];
/*     */         
/*  70 */         if ((candidateResponseTime < minResponseTime) && (!blackList.containsKey(configuredHosts.get(i))))
/*     */         {
/*  72 */           if (candidateResponseTime == 0L) {
/*  73 */             bestHostIndex = i;
/*     */             
/*  75 */             break;
/*     */           }
/*     */           
/*  78 */           bestHostIndex = i;
/*  79 */           minResponseTime = candidateResponseTime;
/*     */         }
/*     */       }
/*     */       
/*  83 */       String bestHost = (String)configuredHosts.get(bestHostIndex);
/*     */       
/*  85 */       conn = (ConnectionImpl)liveConnections.get(bestHost);
/*     */       
/*  87 */       if (conn == null) {
/*     */         try {
/*  89 */           conn = proxy.createConnectionForHost(bestHost);
/*     */         } catch (SQLException sqlEx) {
/*  91 */           ex = sqlEx;
/*     */           
/*  93 */           if (proxy.shouldExceptionTriggerFailover(sqlEx)) {
/*  94 */             proxy.addToGlobalBlacklist(bestHost);
/*  95 */             blackList.put(bestHost, null);
/*     */             
/*     */ 
/*  98 */             if (blackList.size() == configuredHosts.size()) {
/*  99 */               attempts++;
/*     */               try {
/* 101 */                 Thread.sleep(250L);
/*     */               }
/*     */               catch (InterruptedException e) {}
/* 104 */               blackList = proxy.getGlobalBlacklist();
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 109 */             throw sqlEx;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 114 */     return conn;
/*     */     
/*     */     label240:
/* 117 */     if (ex != null) {
/* 118 */       throw ex;
/*     */     }
/*     */     
/* 121 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\BestResponseTimeBalanceStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */