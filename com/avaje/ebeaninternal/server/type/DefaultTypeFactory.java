/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
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
/*     */ public class DefaultTypeFactory
/*     */ {
/*     */   private final ServerConfig serverConfig;
/*     */   
/*     */   public DefaultTypeFactory(ServerConfig serverConfig)
/*     */   {
/*  38 */     this.serverConfig = serverConfig;
/*     */   }
/*     */   
/*     */   private ScalarType<Boolean> createBoolean(String trueValue, String falseValue)
/*     */   {
/*     */     try
/*     */     {
/*  45 */       Integer intTrue = BasicTypeConverter.toInteger(trueValue);
/*  46 */       Integer intFalse = BasicTypeConverter.toInteger(falseValue);
/*     */       
/*  48 */       return new ScalarTypeBoolean.IntBoolean(intTrue, intFalse);
/*     */     }
/*     */     catch (NumberFormatException e) {}
/*     */     
/*     */ 
/*     */ 
/*  54 */     return new ScalarTypeBoolean.StringBoolean(trueValue, falseValue);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ScalarType<Boolean> createBoolean()
/*     */   {
/*  65 */     if (this.serverConfig == null) {
/*  66 */       return new ScalarTypeBoolean.Native();
/*     */     }
/*  68 */     String trueValue = this.serverConfig.getDatabaseBooleanTrue();
/*  69 */     String falseValue = this.serverConfig.getDatabaseBooleanFalse();
/*     */     
/*  71 */     if ((falseValue != null) && (trueValue != null))
/*     */     {
/*  73 */       return createBoolean(trueValue, falseValue);
/*     */     }
/*     */     
/*     */ 
/*  77 */     int booleanDbType = this.serverConfig.getDatabasePlatform().getBooleanDbType();
/*     */     
/*     */ 
/*  80 */     if (booleanDbType == -7) {
/*  81 */       return new ScalarTypeBoolean.BitBoolean();
/*     */     }
/*     */     
/*  84 */     if (booleanDbType == 4) {
/*  85 */       return new ScalarTypeBoolean.IntBoolean(Integer.valueOf(1), Integer.valueOf(0));
/*     */     }
/*  87 */     if (booleanDbType == 12) {
/*  88 */       return new ScalarTypeBoolean.StringBoolean("T", "F");
/*     */     }
/*     */     
/*  91 */     if (booleanDbType == 16) {
/*  92 */       return new ScalarTypeBoolean.Native();
/*     */     }
/*     */     
/*     */ 
/*  96 */     return new ScalarTypeBoolean.Native();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ScalarType<Date> createUtilDate()
/*     */   {
/* 106 */     int utilDateType = getTemporalMapType("timestamp");
/*     */     
/* 108 */     return createUtilDate(utilDateType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ScalarType<Date> createUtilDate(int utilDateType)
/*     */   {
/* 117 */     switch (utilDateType) {
/*     */     case 91: 
/* 119 */       return new ScalarTypeUtilDate.DateType();
/*     */     
/*     */     case 93: 
/* 122 */       return new ScalarTypeUtilDate.TimestampType();
/*     */     }
/*     */     
/* 125 */     throw new RuntimeException("Invalid type " + utilDateType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ScalarType<Calendar> createCalendar()
/*     */   {
/* 137 */     int jdbcType = getTemporalMapType("timestamp");
/*     */     
/* 139 */     return createCalendar(jdbcType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ScalarType<Calendar> createCalendar(int jdbcType)
/*     */   {
/* 148 */     return new ScalarTypeCalendar(jdbcType);
/*     */   }
/*     */   
/*     */   private int getTemporalMapType(String mapType) {
/* 152 */     if (mapType.equalsIgnoreCase("date")) {
/* 153 */       return 91;
/*     */     }
/* 155 */     return 93;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ScalarType<BigInteger> createMathBigInteger()
/*     */   {
/* 163 */     return new ScalarTypeMathBigInteger();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\DefaultTypeFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */