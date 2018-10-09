/*     */ package org.apache.logging.log4j.status;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.message.Message;
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
/*     */ public class StatusData
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4341916115118014017L;
/*     */   private final long timestamp;
/*     */   private final StackTraceElement caller;
/*     */   private final Level level;
/*     */   private final Message msg;
/*     */   private final Throwable throwable;
/*     */   
/*     */   public StatusData(StackTraceElement paramStackTraceElement, Level paramLevel, Message paramMessage, Throwable paramThrowable)
/*     */   {
/*  48 */     this.timestamp = System.currentTimeMillis();
/*  49 */     this.caller = paramStackTraceElement;
/*  50 */     this.level = paramLevel;
/*  51 */     this.msg = paramMessage;
/*  52 */     this.throwable = paramThrowable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getTimestamp()
/*     */   {
/*  60 */     return this.timestamp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public StackTraceElement getStackTraceElement()
/*     */   {
/*  68 */     return this.caller;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Level getLevel()
/*     */   {
/*  76 */     return this.level;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Message getMessage()
/*     */   {
/*  84 */     return this.msg;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Throwable getThrowable()
/*     */   {
/*  92 */     return this.throwable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedStatus()
/*     */   {
/* 100 */     StringBuilder localStringBuilder = new StringBuilder();
/* 101 */     SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
/* 102 */     localStringBuilder.append(localSimpleDateFormat.format(new Date(this.timestamp)));
/* 103 */     localStringBuilder.append(" ");
/* 104 */     localStringBuilder.append(this.level.toString());
/* 105 */     localStringBuilder.append(" ");
/* 106 */     localStringBuilder.append(this.msg.getFormattedMessage());
/* 107 */     Object[] arrayOfObject = this.msg.getParameters();
/*     */     Throwable localThrowable;
/* 109 */     if ((this.throwable == null) && (arrayOfObject != null) && ((arrayOfObject[(arrayOfObject.length - 1)] instanceof Throwable))) {
/* 110 */       localThrowable = (Throwable)arrayOfObject[(arrayOfObject.length - 1)];
/*     */     } else {
/* 112 */       localThrowable = this.throwable;
/*     */     }
/* 114 */     if (localThrowable != null) {
/* 115 */       localStringBuilder.append(" ");
/* 116 */       ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/* 117 */       localThrowable.printStackTrace(new PrintStream(localByteArrayOutputStream));
/* 118 */       localStringBuilder.append(localByteArrayOutputStream.toString());
/*     */     }
/* 120 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\status\StatusData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */