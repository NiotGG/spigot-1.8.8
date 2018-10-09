/*     */ package org.apache.logging.log4j.core.appender.db.jdbc;
/*     */ 
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseAppender;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
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
/*     */ @Plugin(name="JDBC", category="Core", elementType="appender", printObject=true)
/*     */ public final class JDBCAppender
/*     */   extends AbstractDatabaseAppender<JDBCDatabaseManager>
/*     */ {
/*     */   private final String description;
/*     */   
/*     */   private JDBCAppender(String paramString, Filter paramFilter, boolean paramBoolean, JDBCDatabaseManager paramJDBCDatabaseManager)
/*     */   {
/*  43 */     super(paramString, paramFilter, paramBoolean, paramJDBCDatabaseManager);
/*  44 */     this.description = (getName() + "{ manager=" + getManager() + " }");
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  49 */     return this.description;
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
/*     */   public static JDBCAppender createAppender(@PluginAttribute("name") String paramString1, @PluginAttribute("ignoreExceptions") String paramString2, @PluginElement("Filter") Filter paramFilter, @PluginElement("ConnectionSource") ConnectionSource paramConnectionSource, @PluginAttribute("bufferSize") String paramString3, @PluginAttribute("tableName") String paramString4, @PluginElement("ColumnConfigs") ColumnConfig[] paramArrayOfColumnConfig)
/*     */   {
/*  77 */     int i = AbstractAppender.parseInt(paramString3, 0);
/*  78 */     boolean bool = Booleans.parseBoolean(paramString2, true);
/*     */     
/*  80 */     StringBuilder localStringBuilder = new StringBuilder("jdbcManager{ description=").append(paramString1).append(", bufferSize=").append(i).append(", connectionSource=").append(paramConnectionSource.toString()).append(", tableName=").append(paramString4).append(", columns=[ ");
/*     */     
/*     */ 
/*     */ 
/*  84 */     int j = 0;
/*  85 */     for (Object localObject2 : paramArrayOfColumnConfig) {
/*  86 */       if (j++ > 0) {
/*  87 */         localStringBuilder.append(", ");
/*     */       }
/*  89 */       localStringBuilder.append(((ColumnConfig)localObject2).toString());
/*     */     }
/*     */     
/*  92 */     localStringBuilder.append(" ] }");
/*     */     
/*  94 */     ??? = JDBCDatabaseManager.getJDBCDatabaseManager(localStringBuilder.toString(), i, paramConnectionSource, paramString4, paramArrayOfColumnConfig);
/*     */     
/*     */ 
/*  97 */     if (??? == null) {
/*  98 */       return null;
/*     */     }
/*     */     
/* 101 */     return new JDBCAppender(paramString1, paramFilter, bool, (JDBCDatabaseManager)???);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jdbc\JDBCAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */