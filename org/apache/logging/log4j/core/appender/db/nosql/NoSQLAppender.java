/*    */ package org.apache.logging.log4j.core.appender.db.nosql;
/*    */ 
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.Filter;
/*    */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*    */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseAppender;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.core.helpers.Booleans;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(name="NoSql", category="Core", elementType="appender", printObject=true)
/*    */ public final class NoSQLAppender
/*    */   extends AbstractDatabaseAppender<NoSQLDatabaseManager<?>>
/*    */ {
/*    */   private final String description;
/*    */   
/*    */   private NoSQLAppender(String paramString, Filter paramFilter, boolean paramBoolean, NoSQLDatabaseManager<?> paramNoSQLDatabaseManager)
/*    */   {
/* 46 */     super(paramString, paramFilter, paramBoolean, paramNoSQLDatabaseManager);
/* 47 */     this.description = (getName() + "{ manager=" + getManager() + " }");
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 52 */     return this.description;
/*    */   }
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
/*    */   @PluginFactory
/*    */   public static NoSQLAppender createAppender(@PluginAttribute("name") String paramString1, @PluginAttribute("ignoreExceptions") String paramString2, @PluginElement("Filter") Filter paramFilter, @PluginAttribute("bufferSize") String paramString3, @PluginElement("NoSqlProvider") NoSQLProvider<?> paramNoSQLProvider)
/*    */   {
/* 74 */     if (paramNoSQLProvider == null) {
/* 75 */       LOGGER.error("NoSQL provider not specified for appender [{}].", new Object[] { paramString1 });
/* 76 */       return null;
/*    */     }
/*    */     
/* 79 */     int i = AbstractAppender.parseInt(paramString3, 0);
/* 80 */     boolean bool = Booleans.parseBoolean(paramString2, true);
/*    */     
/* 82 */     String str = "noSqlManager{ description=" + paramString1 + ", bufferSize=" + i + ", provider=" + paramNoSQLProvider + " }";
/*    */     
/*    */ 
/* 85 */     NoSQLDatabaseManager localNoSQLDatabaseManager = NoSQLDatabaseManager.getNoSQLDatabaseManager(str, i, paramNoSQLProvider);
/*    */     
/*    */ 
/* 88 */     if (localNoSQLDatabaseManager == null) {
/* 89 */       return null;
/*    */     }
/*    */     
/* 92 */     return new NoSQLAppender(paramString1, paramFilter, bool, localNoSQLDatabaseManager);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\NoSQLAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */