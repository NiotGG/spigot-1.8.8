/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommunicationsException
/*     */   extends SQLException
/*     */   implements StreamingNotifiable
/*     */ {
/*  45 */   private String exceptionMessage = null;
/*     */   
/*  47 */   private boolean streamingResultSetInPlay = false;
/*     */   
/*     */   private MySQLConnection conn;
/*     */   
/*     */   private long lastPacketSentTimeMs;
/*     */   
/*     */   private long lastPacketReceivedTimeMs;
/*     */   private Exception underlyingException;
/*     */   
/*     */   public CommunicationsException(MySQLConnection conn, long lastPacketSentTimeMs, long lastPacketReceivedTimeMs, Exception underlyingException)
/*     */   {
/*  58 */     this.conn = conn;
/*  59 */     this.lastPacketReceivedTimeMs = lastPacketReceivedTimeMs;
/*  60 */     this.lastPacketSentTimeMs = lastPacketSentTimeMs;
/*  61 */     this.underlyingException = underlyingException;
/*     */     
/*  63 */     if (underlyingException != null) {
/*  64 */       initCause(underlyingException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getMessage()
/*     */   {
/*  78 */     if (this.exceptionMessage == null) {
/*  79 */       this.exceptionMessage = SQLError.createLinkFailureMessageBasedOnHeuristics(this.conn, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, this.underlyingException, this.streamingResultSetInPlay);
/*     */       
/*     */ 
/*  82 */       this.conn = null;
/*  83 */       this.underlyingException = null;
/*     */     }
/*  85 */     return this.exceptionMessage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSQLState()
/*     */   {
/*  94 */     return "08S01";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setWasStreamingResults()
/*     */   {
/* 101 */     this.streamingResultSetInPlay = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\CommunicationsException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */