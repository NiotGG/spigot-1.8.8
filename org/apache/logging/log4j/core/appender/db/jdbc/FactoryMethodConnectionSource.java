/*     */ package org.apache.logging.log4j.core.appender.db.jdbc;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
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
/*     */ 
/*     */ 
/*     */ @Plugin(name="ConnectionFactory", category="Core", elementType="connectionSource", printObject=true)
/*     */ public final class FactoryMethodConnectionSource
/*     */   implements ConnectionSource
/*     */ {
/*  39 */   private static final org.apache.logging.log4j.Logger LOGGER = ;
/*     */   
/*     */   private final DataSource dataSource;
/*     */   private final String description;
/*     */   
/*     */   private FactoryMethodConnectionSource(DataSource paramDataSource, String paramString1, String paramString2, String paramString3)
/*     */   {
/*  46 */     this.dataSource = paramDataSource;
/*  47 */     this.description = ("factory{ public static " + paramString3 + " " + paramString1 + "." + paramString2 + "() }");
/*     */   }
/*     */   
/*     */   public Connection getConnection() throws SQLException
/*     */   {
/*  52 */     return this.dataSource.getConnection();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  57 */     return this.description;
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
/*     */   public static FactoryMethodConnectionSource createConnectionSource(@PluginAttribute("class") String paramString1, @PluginAttribute("method") String paramString2)
/*     */   {
/*  74 */     if ((Strings.isEmpty(paramString1)) || (Strings.isEmpty(paramString2))) {
/*  75 */       LOGGER.error("No class name or method name specified for the connection factory method.");
/*  76 */       return null;
/*     */     }
/*     */     Method localMethod;
/*     */     try
/*     */     {
/*  81 */       Class localClass1 = Class.forName(paramString1);
/*  82 */       localMethod = localClass1.getMethod(paramString2, new Class[0]);
/*     */     } catch (Exception localException1) {
/*  84 */       LOGGER.error(localException1.toString(), localException1);
/*  85 */       return null;
/*     */     }
/*     */     
/*  88 */     Class localClass2 = localMethod.getReturnType();
/*  89 */     String str = localClass2.getName();
/*     */     Object localObject;
/*  91 */     if (localClass2 == DataSource.class) {
/*     */       try {
/*  93 */         localObject = (DataSource)localMethod.invoke(null, new Object[0]);
/*  94 */         str = str + "[" + localObject + "]";
/*     */       } catch (Exception localException2) {
/*  96 */         LOGGER.error(localException2.toString(), localException2);
/*  97 */         return null;
/*     */       }
/*  99 */     } else if (localClass2 == Connection.class) {
/* 100 */       localObject = new DataSource()
/*     */       {
/*     */         public Connection getConnection() throws SQLException {
/*     */           try {
/* 104 */             return (Connection)this.val$method.invoke(null, new Object[0]);
/*     */           } catch (Exception localException) {
/* 106 */             throw new SQLException("Failed to obtain connection from factory method.", localException);
/*     */           }
/*     */         }
/*     */         
/*     */         public Connection getConnection(String paramAnonymousString1, String paramAnonymousString2) throws SQLException
/*     */         {
/* 112 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         public int getLoginTimeout() throws SQLException
/*     */         {
/* 117 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         public PrintWriter getLogWriter() throws SQLException
/*     */         {
/* 122 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */ 
/*     */         public java.util.logging.Logger getParentLogger()
/*     */         {
/* 128 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         public boolean isWrapperFor(Class<?> paramAnonymousClass) throws SQLException
/*     */         {
/* 133 */           return false;
/*     */         }
/*     */         
/*     */         public void setLoginTimeout(int paramAnonymousInt) throws SQLException
/*     */         {
/* 138 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         public void setLogWriter(PrintWriter paramAnonymousPrintWriter) throws SQLException
/*     */         {
/* 143 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         public <T> T unwrap(Class<T> paramAnonymousClass) throws SQLException
/*     */         {
/* 148 */           return null;
/*     */         }
/*     */       };
/*     */     } else {
/* 152 */       LOGGER.error("Method [{}.{}()] returns unsupported type [{}].", new Object[] { paramString1, paramString2, localClass2.getName() });
/*     */       
/* 154 */       return null;
/*     */     }
/*     */     
/* 157 */     return new FactoryMethodConnectionSource((DataSource)localObject, paramString1, paramString2, str);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jdbc\FactoryMethodConnectionSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */