/*     */ package org.apache.logging.log4j.simple;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.MessageFactory;
/*     */ import org.apache.logging.log4j.spi.AbstractLogger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
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
/*     */ public class SimpleLogger
/*     */   extends AbstractLogger
/*     */ {
/*     */   private static final char SPACE = ' ';
/*     */   private DateFormat dateFormatter;
/*     */   private Level level;
/*     */   private final boolean showDateTime;
/*     */   private final boolean showContextMap;
/*     */   private PrintStream stream;
/*     */   private final String logName;
/*     */   
/*     */   public SimpleLogger(String paramString1, Level paramLevel, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, String paramString2, MessageFactory paramMessageFactory, PropertiesUtil paramPropertiesUtil, PrintStream paramPrintStream)
/*     */   {
/*  64 */     super(paramString1, paramMessageFactory);
/*  65 */     String str = paramPropertiesUtil.getStringProperty("org.apache.logging.log4j.simplelog." + paramString1 + ".level");
/*  66 */     this.level = Level.toLevel(str, paramLevel);
/*  67 */     if (paramBoolean2) {
/*  68 */       int i = paramString1.lastIndexOf(".");
/*  69 */       if ((i > 0) && (i < paramString1.length())) {
/*  70 */         this.logName = paramString1.substring(i + 1);
/*     */       } else {
/*  72 */         this.logName = paramString1;
/*     */       }
/*  74 */     } else if (paramBoolean1) {
/*  75 */       this.logName = paramString1;
/*     */     } else {
/*  77 */       this.logName = null;
/*     */     }
/*  79 */     this.showDateTime = paramBoolean3;
/*  80 */     this.showContextMap = paramBoolean4;
/*  81 */     this.stream = paramPrintStream;
/*     */     
/*  83 */     if (paramBoolean3) {
/*     */       try {
/*  85 */         this.dateFormatter = new SimpleDateFormat(paramString2);
/*     */       }
/*     */       catch (IllegalArgumentException localIllegalArgumentException) {
/*  88 */         this.dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS zzz");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setStream(PrintStream paramPrintStream) {
/*  94 */     this.stream = paramPrintStream;
/*     */   }
/*     */   
/*     */   public Level getLevel() {
/*  98 */     return this.level;
/*     */   }
/*     */   
/*     */   public void setLevel(Level paramLevel) {
/* 102 */     if (paramLevel != null) {
/* 103 */       this.level = paramLevel;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void log(Marker paramMarker, String paramString, Level paramLevel, Message paramMessage, Throwable paramThrowable)
/*     */   {
/* 110 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     Object localObject2;
/* 112 */     if (this.showDateTime) {
/* 113 */       localObject1 = new Date();
/*     */       
/* 115 */       synchronized (this.dateFormatter) {
/* 116 */         localObject2 = this.dateFormatter.format((Date)localObject1);
/*     */       }
/* 118 */       localStringBuilder.append((String)localObject2);
/* 119 */       localStringBuilder.append(' ');
/*     */     }
/*     */     
/* 122 */     localStringBuilder.append(paramLevel.toString());
/* 123 */     localStringBuilder.append(' ');
/* 124 */     if ((this.logName != null) && (this.logName.length() > 0)) {
/* 125 */       localStringBuilder.append(this.logName);
/* 126 */       localStringBuilder.append(' ');
/*     */     }
/* 128 */     localStringBuilder.append(paramMessage.getFormattedMessage());
/* 129 */     if (this.showContextMap) {
/* 130 */       localObject1 = ThreadContext.getContext();
/* 131 */       if (((Map)localObject1).size() > 0) {
/* 132 */         localStringBuilder.append(' ');
/* 133 */         localStringBuilder.append(localObject1.toString());
/* 134 */         localStringBuilder.append(' ');
/*     */       }
/*     */     }
/* 137 */     Object localObject1 = paramMessage.getParameters();
/*     */     
/* 139 */     if ((paramThrowable == null) && (localObject1 != null) && ((localObject1[(localObject1.length - 1)] instanceof Throwable))) {
/* 140 */       localObject2 = (Throwable)localObject1[(localObject1.length - 1)];
/*     */     } else {
/* 142 */       localObject2 = paramThrowable;
/*     */     }
/* 144 */     if (localObject2 != null) {
/* 145 */       localStringBuilder.append(' ');
/* 146 */       ??? = new ByteArrayOutputStream();
/* 147 */       ((Throwable)localObject2).printStackTrace(new PrintStream((OutputStream)???));
/* 148 */       localStringBuilder.append(((ByteArrayOutputStream)???).toString());
/*     */     }
/* 150 */     this.stream.println(localStringBuilder.toString());
/*     */   }
/*     */   
/*     */   protected boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString)
/*     */   {
/* 155 */     return this.level.intLevel() >= paramLevel.intLevel();
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString, Throwable paramThrowable)
/*     */   {
/* 161 */     return this.level.intLevel() >= paramLevel.intLevel();
/*     */   }
/*     */   
/*     */   protected boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*     */   {
/* 166 */     return this.level.intLevel() >= paramLevel.intLevel();
/*     */   }
/*     */   
/*     */   protected boolean isEnabled(Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*     */   {
/* 171 */     return this.level.intLevel() >= paramLevel.intLevel();
/*     */   }
/*     */   
/*     */   protected boolean isEnabled(Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*     */   {
/* 176 */     return this.level.intLevel() >= paramLevel.intLevel();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\simple\SimpleLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */