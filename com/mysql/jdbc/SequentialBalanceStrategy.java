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
/*     */ public class SequentialBalanceStrategy
/*     */   implements BalanceStrategy
/*     */ {
/*  40 */   private int currentHostIndex = -1;
/*     */   
/*     */ 
/*     */ 
/*     */   public void destroy() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void init(Connection conn, Properties props)
/*     */     throws SQLException
/*     */   {}
/*     */   
/*     */ 
/*     */   public ConnectionImpl pickConnection(LoadBalancingConnectionProxy proxy, List<String> configuredHosts, Map<String, ConnectionImpl> liveConnections, long[] responseTimes, int numRetries)
/*     */     throws SQLException
/*     */   {
/*  56 */     int numHosts = configuredHosts.size();
/*     */     
/*  58 */     SQLException ex = null;
/*     */     
/*  60 */     Map<String, Long> blackList = proxy.getGlobalBlacklist();
/*     */     
/*  62 */     int attempts = 0; ConnectionImpl conn; for (;;) { if (attempts >= numRetries) break label417;
/*  63 */       if (numHosts == 1) {
/*  64 */         this.currentHostIndex = 0;
/*  65 */       } else if (this.currentHostIndex == -1) {
/*  66 */         int random = (int)Math.floor(Math.random() * numHosts);
/*     */         
/*  68 */         for (int i = random; i < numHosts; i++) {
/*  69 */           if (!blackList.containsKey(configuredHosts.get(i))) {
/*  70 */             this.currentHostIndex = i;
/*  71 */             break;
/*     */           }
/*     */         }
/*     */         
/*  75 */         if (this.currentHostIndex == -1) {
/*  76 */           for (int i = 0; i < random; i++) {
/*  77 */             if (!blackList.containsKey(configuredHosts.get(i))) {
/*  78 */               this.currentHostIndex = i;
/*  79 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*  84 */         if (this.currentHostIndex == -1) {
/*  85 */           blackList = proxy.getGlobalBlacklist();
/*     */           
/*     */           try
/*     */           {
/*  89 */             Thread.sleep(250L);
/*     */           }
/*     */           catch (InterruptedException e) {}
/*     */           
/*  93 */           continue;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  98 */         int i = this.currentHostIndex + 1;
/*  99 */         boolean foundGoodHost = false;
/* 101 */         for (; 
/* 101 */             i < numHosts; i++) {
/* 102 */           if (!blackList.containsKey(configuredHosts.get(i))) {
/* 103 */             this.currentHostIndex = i;
/* 104 */             foundGoodHost = true;
/* 105 */             break;
/*     */           }
/*     */         }
/*     */         
/* 109 */         if (!foundGoodHost) {
/* 110 */           for (i = 0; i < this.currentHostIndex; i++) {
/* 111 */             if (!blackList.containsKey(configuredHosts.get(i))) {
/* 112 */               this.currentHostIndex = i;
/* 113 */               foundGoodHost = true;
/* 114 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 119 */         if (!foundGoodHost) {
/* 120 */           blackList = proxy.getGlobalBlacklist();
/*     */           
/*     */           try
/*     */           {
/* 124 */             Thread.sleep(250L);
/*     */           }
/*     */           catch (InterruptedException e) {}
/*     */           
/* 128 */           continue;
/*     */         }
/*     */       }
/*     */       
/* 132 */       String hostPortSpec = (String)configuredHosts.get(this.currentHostIndex);
/*     */       
/* 134 */       conn = (ConnectionImpl)liveConnections.get(hostPortSpec);
/*     */       
/* 136 */       if (conn == null) {
/*     */         try {
/* 138 */           conn = proxy.createConnectionForHost(hostPortSpec);
/*     */         } catch (SQLException sqlEx) {
/* 140 */           ex = sqlEx;
/*     */           
/* 142 */           if (((sqlEx instanceof CommunicationsException)) || ("08S01".equals(sqlEx.getSQLState())))
/*     */           {
/*     */ 
/* 145 */             proxy.addToGlobalBlacklist(hostPortSpec);
/*     */             try
/*     */             {
/* 148 */               Thread.sleep(250L);
/*     */             }
/*     */             catch (InterruptedException e) {}
/*     */           }
/*     */           else
/*     */           {
/* 154 */             throw sqlEx;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 159 */     return conn;
/*     */     
/*     */     label417:
/* 162 */     if (ex != null) {
/* 163 */       throw ex;
/*     */     }
/*     */     
/* 166 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\SequentialBalanceStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */