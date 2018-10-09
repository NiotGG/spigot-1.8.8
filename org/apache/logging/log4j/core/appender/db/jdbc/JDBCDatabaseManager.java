/*     */ package org.apache.logging.log4j.core.appender.db.jdbc;
/*     */ 
/*     */ import java.io.StringReader;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager.AbstractFactoryData;
/*     */ import org.apache.logging.log4j.core.helpers.Closer;
/*     */ import org.apache.logging.log4j.core.layout.PatternLayout;
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
/*     */ public final class JDBCDatabaseManager
/*     */   extends AbstractDatabaseManager
/*     */ {
/*  38 */   private static final JDBCDatabaseManagerFactory FACTORY = new JDBCDatabaseManagerFactory(null);
/*     */   
/*     */   private final List<Column> columns;
/*     */   
/*     */   private final ConnectionSource connectionSource;
/*     */   private final String sqlStatement;
/*     */   private Connection connection;
/*     */   private PreparedStatement statement;
/*     */   
/*     */   private JDBCDatabaseManager(String paramString1, int paramInt, ConnectionSource paramConnectionSource, String paramString2, List<Column> paramList)
/*     */   {
/*  49 */     super(paramString1, paramInt);
/*  50 */     this.connectionSource = paramConnectionSource;
/*  51 */     this.sqlStatement = paramString2;
/*  52 */     this.columns = paramList;
/*     */   }
/*     */   
/*     */   protected void connectInternal() throws SQLException
/*     */   {
/*  57 */     this.connection = this.connectionSource.getConnection();
/*  58 */     this.statement = this.connection.prepareStatement(this.sqlStatement);
/*     */   }
/*     */   
/*     */   protected void disconnectInternal() throws SQLException
/*     */   {
/*     */     try {
/*  64 */       Closer.close(this.statement);
/*     */     } finally {
/*  66 */       Closer.close(this.connection);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void writeInternal(LogEvent paramLogEvent)
/*     */   {
/*  72 */     StringReader localStringReader = null;
/*     */     try {
/*  74 */       if ((!isConnected()) || (this.connection == null) || (this.connection.isClosed())) {
/*  75 */         throw new AppenderLoggingException("Cannot write logging event; JDBC manager not connected to the database.");
/*     */       }
/*     */       
/*     */ 
/*  79 */       int i = 1;
/*  80 */       for (Column localColumn : this.columns) {
/*  81 */         if (localColumn.isEventTimestamp) {
/*  82 */           this.statement.setTimestamp(i++, new Timestamp(paramLogEvent.getMillis()));
/*     */         }
/*  84 */         else if (localColumn.isClob) {
/*  85 */           localStringReader = new StringReader(localColumn.layout.toSerializable(paramLogEvent));
/*  86 */           if (localColumn.isUnicode) {
/*  87 */             this.statement.setNClob(i++, localStringReader);
/*     */           } else {
/*  89 */             this.statement.setClob(i++, localStringReader);
/*     */           }
/*     */         }
/*  92 */         else if (localColumn.isUnicode) {
/*  93 */           this.statement.setNString(i++, localColumn.layout.toSerializable(paramLogEvent));
/*     */         } else {
/*  95 */           this.statement.setString(i++, localColumn.layout.toSerializable(paramLogEvent));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 101 */       if (this.statement.executeUpdate() == 0) {
/* 102 */         throw new AppenderLoggingException("No records inserted in database table for log event in JDBC manager.");
/*     */       }
/*     */     }
/*     */     catch (SQLException localSQLException) {
/* 106 */       throw new AppenderLoggingException("Failed to insert record for log event in JDBC manager: " + localSQLException.getMessage(), localSQLException);
/*     */     }
/*     */     finally {
/* 109 */       Closer.closeSilent(localStringReader);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JDBCDatabaseManager getJDBCDatabaseManager(String paramString1, int paramInt, ConnectionSource paramConnectionSource, String paramString2, ColumnConfig[] paramArrayOfColumnConfig)
/*     */   {
/* 128 */     return (JDBCDatabaseManager)AbstractDatabaseManager.getManager(paramString1, new FactoryData(paramInt, paramConnectionSource, paramString2, paramArrayOfColumnConfig), FACTORY);
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class FactoryData
/*     */     extends AbstractDatabaseManager.AbstractFactoryData
/*     */   {
/*     */     private final ColumnConfig[] columnConfigs;
/*     */     
/*     */     private final ConnectionSource connectionSource;
/*     */     
/*     */     private final String tableName;
/*     */     
/*     */     protected FactoryData(int paramInt, ConnectionSource paramConnectionSource, String paramString, ColumnConfig[] paramArrayOfColumnConfig)
/*     */     {
/* 143 */       super();
/* 144 */       this.connectionSource = paramConnectionSource;
/* 145 */       this.tableName = paramString;
/* 146 */       this.columnConfigs = paramArrayOfColumnConfig;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class JDBCDatabaseManagerFactory
/*     */     implements ManagerFactory<JDBCDatabaseManager, JDBCDatabaseManager.FactoryData>
/*     */   {
/*     */     public JDBCDatabaseManager createManager(String paramString, JDBCDatabaseManager.FactoryData paramFactoryData)
/*     */     {
/* 156 */       StringBuilder localStringBuilder1 = new StringBuilder();
/* 157 */       StringBuilder localStringBuilder2 = new StringBuilder();
/* 158 */       ArrayList localArrayList = new ArrayList();
/* 159 */       int i = 0;
/* 160 */       for (Object localObject2 : JDBCDatabaseManager.FactoryData.access$500(paramFactoryData)) {
/* 161 */         if (i++ > 0) {
/* 162 */           localStringBuilder1.append(',');
/* 163 */           localStringBuilder2.append(',');
/*     */         }
/*     */         
/* 166 */         localStringBuilder1.append(((ColumnConfig)localObject2).getColumnName());
/*     */         
/* 168 */         if (((ColumnConfig)localObject2).getLiteralValue() != null) {
/* 169 */           localStringBuilder2.append(((ColumnConfig)localObject2).getLiteralValue());
/*     */         } else {
/* 171 */           localArrayList.add(new JDBCDatabaseManager.Column(((ColumnConfig)localObject2).getLayout(), ((ColumnConfig)localObject2).isEventTimestamp(), ((ColumnConfig)localObject2).isUnicode(), ((ColumnConfig)localObject2).isClob(), null));
/*     */           
/*     */ 
/* 174 */           localStringBuilder2.append('?');
/*     */         }
/*     */       }
/*     */       
/* 178 */       ??? = "INSERT INTO " + JDBCDatabaseManager.FactoryData.access$700(paramFactoryData) + " (" + localStringBuilder1 + ") VALUES (" + localStringBuilder2 + ")";
/*     */       
/*     */ 
/* 181 */       return new JDBCDatabaseManager(paramString, paramFactoryData.getBufferSize(), JDBCDatabaseManager.FactoryData.access$800(paramFactoryData), (String)???, localArrayList, null);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class Column
/*     */   {
/*     */     private final PatternLayout layout;
/*     */     
/*     */     private final boolean isEventTimestamp;
/*     */     private final boolean isUnicode;
/*     */     private final boolean isClob;
/*     */     
/*     */     private Column(PatternLayout paramPatternLayout, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*     */     {
/* 196 */       this.layout = paramPatternLayout;
/* 197 */       this.isEventTimestamp = paramBoolean1;
/* 198 */       this.isUnicode = paramBoolean2;
/* 199 */       this.isClob = paramBoolean3;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jdbc\JDBCDatabaseManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */