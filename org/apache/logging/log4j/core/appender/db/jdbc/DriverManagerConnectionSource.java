/*     */ package org.apache.logging.log4j.core.appender.db.jdbc;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Driver;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.NameUtil;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ @Plugin(name="DriverManager", category="Core", elementType="connectionSource", printObject=true)
/*     */ public final class DriverManagerConnectionSource
/*     */   implements ConnectionSource
/*     */ {
/*  38 */   private static final Logger LOGGER = ;
/*     */   
/*     */   private final String databasePassword;
/*     */   private final String databaseUrl;
/*     */   private final String databaseUsername;
/*     */   private final String description;
/*     */   
/*     */   private DriverManagerConnectionSource(String paramString1, String paramString2, String paramString3)
/*     */   {
/*  47 */     this.databaseUrl = paramString1;
/*  48 */     this.databaseUsername = paramString2;
/*  49 */     this.databasePassword = paramString3;
/*  50 */     this.description = ("driverManager{ url=" + this.databaseUrl + ", username=" + this.databaseUsername + ", passwordHash=" + NameUtil.md5(new StringBuilder().append(this.databasePassword).append(getClass().getName()).toString()) + " }");
/*     */   }
/*     */   
/*     */   public Connection getConnection()
/*     */     throws SQLException
/*     */   {
/*  56 */     if (this.databaseUsername == null) {
/*  57 */       return DriverManager.getConnection(this.databaseUrl);
/*     */     }
/*  59 */     return DriverManager.getConnection(this.databaseUrl, this.databaseUsername, this.databasePassword);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  64 */     return this.description;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static DriverManagerConnectionSource createConnectionSource(@PluginAttribute("url") String paramString1, @PluginAttribute("username") String paramString2, @PluginAttribute("password") String paramString3)
/*     */   {
/*  81 */     if (Strings.isEmpty(paramString1)) {
/*  82 */       LOGGER.error("No JDBC URL specified for the database.");
/*  83 */       return null;
/*     */     }
/*     */     Driver localDriver;
/*     */     try
/*     */     {
/*  88 */       localDriver = DriverManager.getDriver(paramString1);
/*     */     } catch (SQLException localSQLException) {
/*  90 */       LOGGER.error("No matching driver found for database URL [" + paramString1 + "].", localSQLException);
/*  91 */       return null;
/*     */     }
/*     */     
/*  94 */     if (localDriver == null) {
/*  95 */       LOGGER.error("No matching driver found for database URL [" + paramString1 + "].");
/*  96 */       return null;
/*     */     }
/*     */     
/*  99 */     if ((paramString2 == null) || (paramString2.trim().isEmpty())) {
/* 100 */       paramString2 = null;
/* 101 */       paramString3 = null;
/*     */     }
/*     */     
/* 104 */     return new DriverManagerConnectionSource(paramString1, paramString2, paramString3);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jdbc\DriverManagerConnectionSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */