/*    */ package org.apache.logging.log4j.core.appender.db.jdbc;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import javax.naming.InitialContext;
/*    */ import javax.naming.NamingException;
/*    */ import javax.sql.DataSource;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.core.helpers.Strings;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
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
/*    */ @Plugin(name="DataSource", category="Core", elementType="connectionSource", printObject=true)
/*    */ public final class DataSourceConnectionSource
/*    */   implements ConnectionSource
/*    */ {
/* 38 */   private static final Logger LOGGER = ;
/*    */   private final DataSource dataSource;
/*    */   private final String description;
/*    */   
/*    */   private DataSourceConnectionSource(String paramString, DataSource paramDataSource)
/*    */   {
/* 44 */     this.dataSource = paramDataSource;
/* 45 */     this.description = ("dataSource{ name=" + paramString + ", value=" + paramDataSource + " }");
/*    */   }
/*    */   
/*    */   public Connection getConnection() throws SQLException
/*    */   {
/* 50 */     return this.dataSource.getConnection();
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 55 */     return this.description;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @PluginFactory
/*    */   public static DataSourceConnectionSource createConnectionSource(@PluginAttribute("jndiName") String paramString)
/*    */   {
/* 67 */     if (Strings.isEmpty(paramString)) {
/* 68 */       LOGGER.error("No JNDI name provided.");
/* 69 */       return null;
/*    */     }
/*    */     try
/*    */     {
/* 73 */       InitialContext localInitialContext = new InitialContext();
/* 74 */       DataSource localDataSource = (DataSource)localInitialContext.lookup(paramString);
/* 75 */       if (localDataSource == null) {
/* 76 */         LOGGER.error("No data source found with JNDI name [" + paramString + "].");
/* 77 */         return null;
/*    */       }
/*    */       
/* 80 */       return new DataSourceConnectionSource(paramString, localDataSource);
/*    */     } catch (NamingException localNamingException) {
/* 82 */       LOGGER.error(localNamingException.getMessage(), localNamingException); }
/* 83 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jdbc\DataSourceConnectionSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */