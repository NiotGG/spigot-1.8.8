/*     */ package org.apache.logging.log4j.core.helpers;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
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
/*     */ public class Closer
/*     */ {
/*     */   public static void closeSilent(Closeable paramCloseable)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       if (paramCloseable != null) {
/*  40 */         paramCloseable.close();
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void close(Closeable paramCloseable)
/*     */     throws IOException
/*     */   {
/*  54 */     if (paramCloseable != null) {
/*  55 */       paramCloseable.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void closeSilent(Statement paramStatement)
/*     */   {
/*     */     try
/*     */     {
/*  67 */       if (paramStatement != null) {
/*  68 */         paramStatement.close();
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void close(Statement paramStatement)
/*     */     throws SQLException
/*     */   {
/*  82 */     if (paramStatement != null) {
/*  83 */       paramStatement.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void closeSilent(Connection paramConnection)
/*     */   {
/*     */     try
/*     */     {
/*  95 */       if (paramConnection != null) {
/*  96 */         paramConnection.close();
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void close(Connection paramConnection)
/*     */     throws SQLException
/*     */   {
/* 110 */     if (paramConnection != null) {
/* 111 */       paramConnection.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\Closer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */