/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*      */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.Date;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class UpdatableResultSet
/*      */   extends ResultSetImpl
/*      */ {
/*   47 */   protected static final byte[] STREAM_DATA_MARKER = "** STREAM DATA **".getBytes();
/*      */   
/*      */ 
/*      */   protected SingleByteCharsetConverter charConverter;
/*      */   
/*      */ 
/*      */   private String charEncoding;
/*      */   
/*      */ 
/*      */   private byte[][] defaultColumnValue;
/*      */   
/*   58 */   private PreparedStatement deleter = null;
/*      */   
/*   60 */   private String deleteSQL = null;
/*      */   
/*   62 */   private boolean initializedCharConverter = false;
/*      */   
/*      */ 
/*   65 */   protected PreparedStatement inserter = null;
/*      */   
/*   67 */   private String insertSQL = null;
/*      */   
/*      */ 
/*   70 */   private boolean isUpdatable = false;
/*      */   
/*      */ 
/*   73 */   private String notUpdatableReason = null;
/*      */   
/*      */ 
/*   76 */   private List primaryKeyIndicies = null;
/*      */   
/*      */   private String qualifiedAndQuotedTableName;
/*      */   
/*   80 */   private String quotedIdChar = null;
/*      */   
/*      */ 
/*      */   private PreparedStatement refresher;
/*      */   
/*   85 */   private String refreshSQL = null;
/*      */   
/*      */ 
/*      */   private ResultSetRow savedCurrentRow;
/*      */   
/*      */ 
/*   91 */   protected PreparedStatement updater = null;
/*      */   
/*      */ 
/*   94 */   private String updateSQL = null;
/*      */   
/*   96 */   private boolean populateInserterWithDefaultValues = false;
/*      */   
/*   98 */   private Map databasesUsedToTablesUsed = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected UpdatableResultSet(String catalog, Field[] fields, RowData tuples, MySQLConnection conn, StatementImpl creatorStmt)
/*      */     throws SQLException
/*      */   {
/*  120 */     super(catalog, fields, tuples, conn, creatorStmt);
/*  121 */     checkUpdatability();
/*  122 */     this.populateInserterWithDefaultValues = this.connection.getPopulateInsertRowWithDefaultValues();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean absolute(int row)
/*      */     throws SQLException
/*      */   {
/*  165 */     return super.absolute(row);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void afterLast()
/*      */     throws SQLException
/*      */   {
/*  181 */     super.afterLast();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void beforeFirst()
/*      */     throws SQLException
/*      */   {
/*  197 */     super.beforeFirst();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void cancelRowUpdates()
/*      */     throws SQLException
/*      */   {
/*  211 */     checkClosed();
/*      */     
/*  213 */     if (this.doingUpdates) {
/*  214 */       this.doingUpdates = false;
/*  215 */       this.updater.clearParameters();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void checkRowPos()
/*      */     throws SQLException
/*      */   {
/*  225 */     checkClosed();
/*      */     
/*  227 */     if (!this.onInsertRow) {
/*  228 */       super.checkRowPos();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void checkUpdatability()
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/*  240 */       if (this.fields == null)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  247 */         return;
/*      */       }
/*      */       
/*  250 */       String singleTableName = null;
/*  251 */       String catalogName = null;
/*      */       
/*  253 */       int primaryKeyCount = 0;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  260 */       if ((this.catalog == null) || (this.catalog.length() == 0)) {
/*  261 */         this.catalog = this.fields[0].getDatabaseName();
/*      */         
/*  263 */         if ((this.catalog == null) || (this.catalog.length() == 0)) {
/*  264 */           throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.43"), "S1009", getExceptionInterceptor());
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  270 */       if (this.fields.length > 0) {
/*  271 */         singleTableName = this.fields[0].getOriginalTableName();
/*  272 */         catalogName = this.fields[0].getDatabaseName();
/*      */         
/*  274 */         if (singleTableName == null) {
/*  275 */           singleTableName = this.fields[0].getTableName();
/*  276 */           catalogName = this.catalog;
/*      */         }
/*      */         
/*  279 */         if ((singleTableName != null) && (singleTableName.length() == 0)) {
/*  280 */           this.isUpdatable = false;
/*  281 */           this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
/*      */           
/*  283 */           return;
/*      */         }
/*      */         
/*  286 */         if (this.fields[0].isPrimaryKey()) {
/*  287 */           primaryKeyCount++;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  293 */         for (int i = 1; i < this.fields.length; i++) {
/*  294 */           String otherTableName = this.fields[i].getOriginalTableName();
/*  295 */           String otherCatalogName = this.fields[i].getDatabaseName();
/*      */           
/*  297 */           if (otherTableName == null) {
/*  298 */             otherTableName = this.fields[i].getTableName();
/*  299 */             otherCatalogName = this.catalog;
/*      */           }
/*      */           
/*  302 */           if ((otherTableName != null) && (otherTableName.length() == 0)) {
/*  303 */             this.isUpdatable = false;
/*  304 */             this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
/*      */             
/*  306 */             return;
/*      */           }
/*      */           
/*  309 */           if ((singleTableName == null) || (!otherTableName.equals(singleTableName)))
/*      */           {
/*  311 */             this.isUpdatable = false;
/*  312 */             this.notUpdatableReason = Messages.getString("NotUpdatableReason.0");
/*      */             
/*  314 */             return;
/*      */           }
/*      */           
/*      */ 
/*  318 */           if ((catalogName == null) || (!otherCatalogName.equals(catalogName)))
/*      */           {
/*  320 */             this.isUpdatable = false;
/*  321 */             this.notUpdatableReason = Messages.getString("NotUpdatableReason.1");
/*      */             
/*  323 */             return;
/*      */           }
/*      */           
/*  326 */           if (this.fields[i].isPrimaryKey()) {
/*  327 */             primaryKeyCount++;
/*      */           }
/*      */         }
/*      */         
/*  331 */         if ((singleTableName == null) || (singleTableName.length() == 0)) {
/*  332 */           this.isUpdatable = false;
/*  333 */           this.notUpdatableReason = Messages.getString("NotUpdatableReason.2");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  338 */         this.isUpdatable = false;
/*  339 */         this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
/*      */         
/*  341 */         return;
/*      */       }
/*      */       
/*  344 */       if (this.connection.getStrictUpdates()) {
/*  345 */         DatabaseMetaData dbmd = this.connection.getMetaData();
/*      */         
/*  347 */         ResultSet rs = null;
/*  348 */         HashMap primaryKeyNames = new HashMap();
/*      */         try
/*      */         {
/*  351 */           rs = dbmd.getPrimaryKeys(catalogName, null, singleTableName);
/*      */           
/*  353 */           while (rs.next()) {
/*  354 */             String keyName = rs.getString(4);
/*  355 */             keyName = keyName.toUpperCase();
/*  356 */             primaryKeyNames.put(keyName, keyName);
/*      */           }
/*      */         } finally {
/*  359 */           if (rs != null) {
/*      */             try {
/*  361 */               rs.close();
/*      */             } catch (Exception ex) {
/*  363 */               AssertionFailedException.shouldNotHappen(ex);
/*      */             }
/*      */             
/*  366 */             rs = null;
/*      */           }
/*      */         }
/*      */         
/*  370 */         int existingPrimaryKeysCount = primaryKeyNames.size();
/*      */         
/*  372 */         if (existingPrimaryKeysCount == 0) {
/*  373 */           this.isUpdatable = false;
/*  374 */           this.notUpdatableReason = Messages.getString("NotUpdatableReason.5");
/*      */           
/*  376 */           return;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  382 */         for (int i = 0; i < this.fields.length; i++) {
/*  383 */           if (this.fields[i].isPrimaryKey()) {
/*  384 */             String columnNameUC = this.fields[i].getName().toUpperCase();
/*      */             
/*      */ 
/*  387 */             if (primaryKeyNames.remove(columnNameUC) == null)
/*      */             {
/*  389 */               String originalName = this.fields[i].getOriginalName();
/*      */               
/*  391 */               if ((originalName != null) && 
/*  392 */                 (primaryKeyNames.remove(originalName.toUpperCase()) == null))
/*      */               {
/*      */ 
/*  395 */                 this.isUpdatable = false;
/*  396 */                 this.notUpdatableReason = Messages.getString("NotUpdatableReason.6", new Object[] { originalName });
/*      */                 
/*      */ 
/*  399 */                 return;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  406 */         this.isUpdatable = primaryKeyNames.isEmpty();
/*      */         
/*  408 */         if (!this.isUpdatable) {
/*  409 */           if (existingPrimaryKeysCount > 1) {
/*  410 */             this.notUpdatableReason = Messages.getString("NotUpdatableReason.7");
/*      */           } else {
/*  412 */             this.notUpdatableReason = Messages.getString("NotUpdatableReason.4");
/*      */           }
/*      */           
/*  415 */           return;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  422 */       if (primaryKeyCount == 0) {
/*  423 */         this.isUpdatable = false;
/*  424 */         this.notUpdatableReason = Messages.getString("NotUpdatableReason.4");
/*      */         
/*  426 */         return;
/*      */       }
/*      */       
/*  429 */       this.isUpdatable = true;
/*  430 */       this.notUpdatableReason = null;
/*      */       
/*  432 */       return;
/*      */     } catch (SQLException sqlEx) {
/*  434 */       this.isUpdatable = false;
/*  435 */       this.notUpdatableReason = sqlEx.getMessage();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void deleteRow()
/*      */     throws SQLException
/*      */   {
/*  450 */     checkClosed();
/*      */     
/*  452 */     if (!this.isUpdatable) {
/*  453 */       throw new NotUpdatable(this.notUpdatableReason);
/*      */     }
/*      */     
/*  456 */     if (this.onInsertRow)
/*  457 */       throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.1"), getExceptionInterceptor());
/*  458 */     if (this.rowData.size() == 0)
/*  459 */       throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.2"), getExceptionInterceptor());
/*  460 */     if (isBeforeFirst())
/*  461 */       throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.3"), getExceptionInterceptor());
/*  462 */     if (isAfterLast()) {
/*  463 */       throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.4"), getExceptionInterceptor());
/*      */     }
/*      */     
/*  466 */     if (this.deleter == null) {
/*  467 */       if (this.deleteSQL == null) {
/*  468 */         generateStatements();
/*      */       }
/*      */       
/*  471 */       this.deleter = ((PreparedStatement)this.connection.clientPrepareStatement(this.deleteSQL));
/*      */     }
/*      */     
/*      */ 
/*  475 */     this.deleter.clearParameters();
/*      */     
/*  477 */     String characterEncoding = null;
/*      */     
/*  479 */     if (this.connection.getUseUnicode()) {
/*  480 */       characterEncoding = this.connection.getEncoding();
/*      */     }
/*      */     
/*  483 */     int numKeys = this.primaryKeyIndicies.size();
/*      */     
/*  485 */     if (numKeys == 1) {
/*  486 */       int index = ((Integer)this.primaryKeyIndicies.get(0)).intValue();
/*      */       
/*  488 */       setParamValue(this.deleter, 1, this.thisRow, index, this.fields[index].getSQLType());
/*      */     }
/*      */     else {
/*  491 */       for (int i = 0; i < numKeys; i++) {
/*  492 */         int index = ((Integer)this.primaryKeyIndicies.get(i)).intValue();
/*      */         
/*  494 */         setParamValue(this.deleter, i + 1, this.thisRow, index, this.fields[index].getSQLType());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  500 */     this.deleter.executeUpdate();
/*  501 */     this.rowData.removeRow(this.rowData.getCurrentRowNumber());
/*      */     
/*      */ 
/*  504 */     previous();
/*      */   }
/*      */   
/*      */ 
/*      */   private synchronized void setParamValue(PreparedStatement ps, int psIdx, ResultSetRow row, int rsIdx, int sqlType)
/*      */     throws SQLException
/*      */   {
/*  511 */     byte[] val = row.getColumnValue(rsIdx);
/*  512 */     if (val == null) {
/*  513 */       ps.setNull(psIdx, 0);
/*  514 */       return;
/*      */     }
/*  516 */     switch (sqlType) {
/*      */     case 0: 
/*  518 */       ps.setNull(psIdx, 0);
/*  519 */       break;
/*      */     case -6: 
/*      */     case 4: 
/*      */     case 5: 
/*  523 */       ps.setInt(psIdx, row.getInt(rsIdx));
/*  524 */       break;
/*      */     case -5: 
/*  526 */       ps.setLong(psIdx, row.getLong(rsIdx));
/*  527 */       break;
/*      */     case -1: 
/*      */     case 1: 
/*      */     case 2: 
/*      */     case 3: 
/*      */     case 12: 
/*  533 */       ps.setString(psIdx, row.getString(rsIdx, this.charEncoding, this.connection));
/*  534 */       break;
/*      */     case 91: 
/*  536 */       ps.setDate(psIdx, row.getDateFast(rsIdx, this.connection, this, this.fastDateCal), this.fastDateCal);
/*  537 */       break;
/*      */     case 93: 
/*  539 */       ps.setTimestamp(psIdx, row.getTimestampFast(rsIdx, this.fastDateCal, this.defaultTimeZone, false, this.connection, this));
/*  540 */       break;
/*      */     case 92: 
/*  542 */       ps.setTime(psIdx, row.getTimeFast(rsIdx, this.fastDateCal, this.defaultTimeZone, false, this.connection, this));
/*  543 */       break;
/*      */     case 6: 
/*      */     case 7: 
/*      */     case 8: 
/*      */     case 16: 
/*  548 */       ps.setBytesNoEscapeNoQuotes(psIdx, val);
/*  549 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     default: 
/*  555 */       ps.setBytes(psIdx, val);
/*      */     }
/*      */   }
/*      */   
/*      */   private synchronized void extractDefaultValues()
/*      */     throws SQLException
/*      */   {
/*  562 */     DatabaseMetaData dbmd = this.connection.getMetaData();
/*  563 */     this.defaultColumnValue = new byte[this.fields.length][];
/*      */     
/*  565 */     ResultSet columnsResultSet = null;
/*  566 */     Iterator referencedDbs = this.databasesUsedToTablesUsed.entrySet().iterator();
/*      */     
/*  568 */     while (referencedDbs.hasNext()) {
/*  569 */       Map.Entry dbEntry = (Map.Entry)referencedDbs.next();
/*  570 */       String databaseName = dbEntry.getKey().toString();
/*      */       
/*  572 */       Iterator referencedTables = ((Map)dbEntry.getValue()).entrySet().iterator();
/*      */       
/*  574 */       while (referencedTables.hasNext()) {
/*  575 */         Map.Entry tableEntry = (Map.Entry)referencedTables.next();
/*  576 */         String tableName = tableEntry.getKey().toString();
/*  577 */         Map columnNamesToIndices = (Map)tableEntry.getValue();
/*      */         try
/*      */         {
/*  580 */           columnsResultSet = dbmd.getColumns(this.catalog, null, tableName, "%");
/*      */           
/*      */ 
/*  583 */           while (columnsResultSet.next()) {
/*  584 */             String columnName = columnsResultSet.getString("COLUMN_NAME");
/*  585 */             byte[] defaultValue = columnsResultSet.getBytes("COLUMN_DEF");
/*      */             
/*  587 */             if (columnNamesToIndices.containsKey(columnName)) {
/*  588 */               int localColumnIndex = ((Integer)columnNamesToIndices.get(columnName)).intValue();
/*      */               
/*  590 */               this.defaultColumnValue[localColumnIndex] = defaultValue;
/*      */             }
/*      */           }
/*      */         } finally {
/*  594 */           if (columnsResultSet != null) {
/*  595 */             columnsResultSet.close();
/*      */             
/*  597 */             columnsResultSet = null;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean first()
/*      */     throws SQLException
/*      */   {
/*  618 */     return super.first();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected synchronized void generateStatements()
/*      */     throws SQLException
/*      */   {
/*  631 */     if (!this.isUpdatable) {
/*  632 */       this.doingUpdates = false;
/*  633 */       this.onInsertRow = false;
/*      */       
/*  635 */       throw new NotUpdatable(this.notUpdatableReason);
/*      */     }
/*      */     
/*  638 */     String quotedId = getQuotedIdChar();
/*      */     
/*  640 */     Map tableNamesSoFar = null;
/*      */     
/*  642 */     if (this.connection.lowerCaseTableNames()) {
/*  643 */       tableNamesSoFar = new TreeMap(String.CASE_INSENSITIVE_ORDER);
/*  644 */       this.databasesUsedToTablesUsed = new TreeMap(String.CASE_INSENSITIVE_ORDER);
/*      */     } else {
/*  646 */       tableNamesSoFar = new TreeMap();
/*  647 */       this.databasesUsedToTablesUsed = new TreeMap();
/*      */     }
/*      */     
/*  650 */     this.primaryKeyIndicies = new ArrayList();
/*      */     
/*  652 */     StringBuffer fieldValues = new StringBuffer();
/*  653 */     StringBuffer keyValues = new StringBuffer();
/*  654 */     StringBuffer columnNames = new StringBuffer();
/*  655 */     StringBuffer insertPlaceHolders = new StringBuffer();
/*  656 */     StringBuffer allTablesBuf = new StringBuffer();
/*  657 */     Map columnIndicesToTable = new HashMap();
/*      */     
/*  659 */     boolean firstTime = true;
/*  660 */     boolean keysFirstTime = true;
/*      */     
/*  662 */     String equalsStr = this.connection.versionMeetsMinimum(3, 23, 0) ? "<=>" : "=";
/*      */     
/*      */ 
/*  665 */     for (int i = 0; i < this.fields.length; i++) {
/*  666 */       StringBuffer tableNameBuffer = new StringBuffer();
/*  667 */       Map updColumnNameToIndex = null;
/*      */       
/*      */ 
/*  670 */       if (this.fields[i].getOriginalTableName() != null)
/*      */       {
/*  672 */         String databaseName = this.fields[i].getDatabaseName();
/*      */         
/*  674 */         if ((databaseName != null) && (databaseName.length() > 0)) {
/*  675 */           tableNameBuffer.append(quotedId);
/*  676 */           tableNameBuffer.append(databaseName);
/*  677 */           tableNameBuffer.append(quotedId);
/*  678 */           tableNameBuffer.append('.');
/*      */         }
/*      */         
/*  681 */         String tableOnlyName = this.fields[i].getOriginalTableName();
/*      */         
/*  683 */         tableNameBuffer.append(quotedId);
/*  684 */         tableNameBuffer.append(tableOnlyName);
/*  685 */         tableNameBuffer.append(quotedId);
/*      */         
/*  687 */         String fqTableName = tableNameBuffer.toString();
/*      */         
/*  689 */         if (!tableNamesSoFar.containsKey(fqTableName)) {
/*  690 */           if (!tableNamesSoFar.isEmpty()) {
/*  691 */             allTablesBuf.append(',');
/*      */           }
/*      */           
/*  694 */           allTablesBuf.append(fqTableName);
/*  695 */           tableNamesSoFar.put(fqTableName, fqTableName);
/*      */         }
/*      */         
/*  698 */         columnIndicesToTable.put(new Integer(i), fqTableName);
/*      */         
/*  700 */         updColumnNameToIndex = getColumnsToIndexMapForTableAndDB(databaseName, tableOnlyName);
/*      */       } else {
/*  702 */         String tableOnlyName = this.fields[i].getTableName();
/*      */         
/*  704 */         if (tableOnlyName != null) {
/*  705 */           tableNameBuffer.append(quotedId);
/*  706 */           tableNameBuffer.append(tableOnlyName);
/*  707 */           tableNameBuffer.append(quotedId);
/*      */           
/*  709 */           String fqTableName = tableNameBuffer.toString();
/*      */           
/*  711 */           if (!tableNamesSoFar.containsKey(fqTableName)) {
/*  712 */             if (!tableNamesSoFar.isEmpty()) {
/*  713 */               allTablesBuf.append(',');
/*      */             }
/*      */             
/*  716 */             allTablesBuf.append(fqTableName);
/*  717 */             tableNamesSoFar.put(fqTableName, fqTableName);
/*      */           }
/*      */           
/*  720 */           columnIndicesToTable.put(new Integer(i), fqTableName);
/*      */           
/*  722 */           updColumnNameToIndex = getColumnsToIndexMapForTableAndDB(this.catalog, tableOnlyName);
/*      */         }
/*      */       }
/*      */       
/*  726 */       String originalColumnName = this.fields[i].getOriginalName();
/*  727 */       String columnName = null;
/*      */       
/*  729 */       if ((this.connection.getIO().hasLongColumnInfo()) && (originalColumnName != null) && (originalColumnName.length() > 0))
/*      */       {
/*      */ 
/*  732 */         columnName = originalColumnName;
/*      */       } else {
/*  734 */         columnName = this.fields[i].getName();
/*      */       }
/*      */       
/*  737 */       if ((updColumnNameToIndex != null) && (columnName != null)) {
/*  738 */         updColumnNameToIndex.put(columnName, new Integer(i));
/*      */       }
/*      */       
/*  741 */       String originalTableName = this.fields[i].getOriginalTableName();
/*  742 */       String tableName = null;
/*      */       
/*  744 */       if ((this.connection.getIO().hasLongColumnInfo()) && (originalTableName != null) && (originalTableName.length() > 0))
/*      */       {
/*      */ 
/*  747 */         tableName = originalTableName;
/*      */       } else {
/*  749 */         tableName = this.fields[i].getTableName();
/*      */       }
/*      */       
/*  752 */       StringBuffer fqcnBuf = new StringBuffer();
/*  753 */       String databaseName = this.fields[i].getDatabaseName();
/*      */       
/*  755 */       if ((databaseName != null) && (databaseName.length() > 0)) {
/*  756 */         fqcnBuf.append(quotedId);
/*  757 */         fqcnBuf.append(databaseName);
/*  758 */         fqcnBuf.append(quotedId);
/*  759 */         fqcnBuf.append('.');
/*      */       }
/*      */       
/*  762 */       fqcnBuf.append(quotedId);
/*  763 */       fqcnBuf.append(tableName);
/*  764 */       fqcnBuf.append(quotedId);
/*  765 */       fqcnBuf.append('.');
/*  766 */       fqcnBuf.append(quotedId);
/*  767 */       fqcnBuf.append(columnName);
/*  768 */       fqcnBuf.append(quotedId);
/*      */       
/*  770 */       String qualifiedColumnName = fqcnBuf.toString();
/*      */       
/*  772 */       if (this.fields[i].isPrimaryKey()) {
/*  773 */         this.primaryKeyIndicies.add(Constants.integerValueOf(i));
/*      */         
/*  775 */         if (!keysFirstTime) {
/*  776 */           keyValues.append(" AND ");
/*      */         } else {
/*  778 */           keysFirstTime = false;
/*      */         }
/*      */         
/*  781 */         keyValues.append(qualifiedColumnName);
/*  782 */         keyValues.append(equalsStr);
/*  783 */         keyValues.append("?");
/*      */       }
/*      */       
/*  786 */       if (firstTime) {
/*  787 */         firstTime = false;
/*  788 */         fieldValues.append("SET ");
/*      */       } else {
/*  790 */         fieldValues.append(",");
/*  791 */         columnNames.append(",");
/*  792 */         insertPlaceHolders.append(",");
/*      */       }
/*      */       
/*  795 */       insertPlaceHolders.append("?");
/*      */       
/*  797 */       columnNames.append(qualifiedColumnName);
/*      */       
/*  799 */       fieldValues.append(qualifiedColumnName);
/*  800 */       fieldValues.append("=?");
/*      */     }
/*      */     
/*  803 */     this.qualifiedAndQuotedTableName = allTablesBuf.toString();
/*      */     
/*  805 */     this.updateSQL = ("UPDATE " + this.qualifiedAndQuotedTableName + " " + fieldValues.toString() + " WHERE " + keyValues.toString());
/*      */     
/*      */ 
/*  808 */     this.insertSQL = ("INSERT INTO " + this.qualifiedAndQuotedTableName + " (" + columnNames.toString() + ") VALUES (" + insertPlaceHolders.toString() + ")");
/*      */     
/*      */ 
/*  811 */     this.refreshSQL = ("SELECT " + columnNames.toString() + " FROM " + this.qualifiedAndQuotedTableName + " WHERE " + keyValues.toString());
/*      */     
/*      */ 
/*  814 */     this.deleteSQL = ("DELETE FROM " + this.qualifiedAndQuotedTableName + " WHERE " + keyValues.toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private Map getColumnsToIndexMapForTableAndDB(String databaseName, String tableName)
/*      */   {
/*  821 */     Map tablesUsedToColumnsMap = (Map)this.databasesUsedToTablesUsed.get(databaseName);
/*      */     
/*  823 */     if (tablesUsedToColumnsMap == null) {
/*  824 */       if (this.connection.lowerCaseTableNames()) {
/*  825 */         tablesUsedToColumnsMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
/*      */       } else {
/*  827 */         tablesUsedToColumnsMap = new TreeMap();
/*      */       }
/*      */       
/*  830 */       this.databasesUsedToTablesUsed.put(databaseName, tablesUsedToColumnsMap);
/*      */     }
/*      */     
/*  833 */     Map nameToIndex = (Map)tablesUsedToColumnsMap.get(tableName);
/*      */     
/*  835 */     if (nameToIndex == null) {
/*  836 */       nameToIndex = new HashMap();
/*  837 */       tablesUsedToColumnsMap.put(tableName, nameToIndex);
/*      */     }
/*      */     
/*  840 */     return nameToIndex;
/*      */   }
/*      */   
/*      */   private synchronized SingleByteCharsetConverter getCharConverter() throws SQLException
/*      */   {
/*  845 */     if (!this.initializedCharConverter) {
/*  846 */       this.initializedCharConverter = true;
/*      */       
/*  848 */       if (this.connection.getUseUnicode()) {
/*  849 */         this.charEncoding = this.connection.getEncoding();
/*  850 */         this.charConverter = this.connection.getCharsetConverter(this.charEncoding);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  855 */     return this.charConverter;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getConcurrency()
/*      */     throws SQLException
/*      */   {
/*  868 */     return this.isUpdatable ? 1008 : 1007;
/*      */   }
/*      */   
/*      */   private synchronized String getQuotedIdChar() throws SQLException {
/*  872 */     if (this.quotedIdChar == null) {
/*  873 */       boolean useQuotedIdentifiers = this.connection.supportsQuotedIdentifiers();
/*      */       
/*      */ 
/*  876 */       if (useQuotedIdentifiers) {
/*  877 */         DatabaseMetaData dbmd = this.connection.getMetaData();
/*  878 */         this.quotedIdChar = dbmd.getIdentifierQuoteString();
/*      */       } else {
/*  880 */         this.quotedIdChar = "";
/*      */       }
/*      */     }
/*      */     
/*  884 */     return this.quotedIdChar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void insertRow()
/*      */     throws SQLException
/*      */   {
/*  897 */     checkClosed();
/*      */     
/*  899 */     if (!this.onInsertRow) {
/*  900 */       throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.7"), getExceptionInterceptor());
/*      */     }
/*      */     
/*  903 */     this.inserter.executeUpdate();
/*      */     
/*  905 */     long autoIncrementId = this.inserter.getLastInsertID();
/*  906 */     int numFields = this.fields.length;
/*  907 */     byte[][] newRow = new byte[numFields][];
/*      */     
/*  909 */     for (int i = 0; i < numFields; i++) {
/*  910 */       if (this.inserter.isNull(i)) {
/*  911 */         newRow[i] = null;
/*      */       } else {
/*  913 */         newRow[i] = this.inserter.getBytesRepresentation(i);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  920 */       if ((this.fields[i].isAutoIncrement()) && (autoIncrementId > 0L)) {
/*  921 */         newRow[i] = String.valueOf(autoIncrementId).getBytes();
/*  922 */         this.inserter.setBytesNoEscapeNoQuotes(i + 1, newRow[i]);
/*      */       }
/*      */     }
/*      */     
/*  926 */     ResultSetRow resultSetRow = new ByteArrayRow(newRow, getExceptionInterceptor());
/*      */     
/*  928 */     refreshRow(this.inserter, resultSetRow);
/*      */     
/*  930 */     this.rowData.addRow(resultSetRow);
/*  931 */     resetInserter();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isAfterLast()
/*      */     throws SQLException
/*      */   {
/*  948 */     return super.isAfterLast();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isBeforeFirst()
/*      */     throws SQLException
/*      */   {
/*  965 */     return super.isBeforeFirst();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isFirst()
/*      */     throws SQLException
/*      */   {
/*  981 */     return super.isFirst();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean isLast()
/*      */     throws SQLException
/*      */   {
/* 1000 */     return super.isLast();
/*      */   }
/*      */   
/*      */   boolean isUpdatable() {
/* 1004 */     return this.isUpdatable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean last()
/*      */     throws SQLException
/*      */   {
/* 1021 */     return super.last();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void moveToCurrentRow()
/*      */     throws SQLException
/*      */   {
/* 1035 */     checkClosed();
/*      */     
/* 1037 */     if (!this.isUpdatable) {
/* 1038 */       throw new NotUpdatable(this.notUpdatableReason);
/*      */     }
/*      */     
/* 1041 */     if (this.onInsertRow) {
/* 1042 */       this.onInsertRow = false;
/* 1043 */       this.thisRow = this.savedCurrentRow;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void moveToInsertRow()
/*      */     throws SQLException
/*      */   {
/* 1065 */     checkClosed();
/*      */     
/* 1067 */     if (!this.isUpdatable) {
/* 1068 */       throw new NotUpdatable(this.notUpdatableReason);
/*      */     }
/*      */     
/* 1071 */     if (this.inserter == null) {
/* 1072 */       if (this.insertSQL == null) {
/* 1073 */         generateStatements();
/*      */       }
/*      */       
/* 1076 */       this.inserter = ((PreparedStatement)this.connection.clientPrepareStatement(this.insertSQL));
/*      */       
/* 1078 */       if (this.populateInserterWithDefaultValues) {
/* 1079 */         extractDefaultValues();
/*      */       }
/*      */       
/* 1082 */       resetInserter();
/*      */     } else {
/* 1084 */       resetInserter();
/*      */     }
/*      */     
/* 1087 */     int numFields = this.fields.length;
/*      */     
/* 1089 */     this.onInsertRow = true;
/* 1090 */     this.doingUpdates = false;
/* 1091 */     this.savedCurrentRow = this.thisRow;
/* 1092 */     byte[][] newRowData = new byte[numFields][];
/* 1093 */     this.thisRow = new ByteArrayRow(newRowData, getExceptionInterceptor());
/*      */     
/* 1095 */     for (int i = 0; i < numFields; i++) {
/* 1096 */       if (!this.populateInserterWithDefaultValues) {
/* 1097 */         this.inserter.setBytesNoEscapeNoQuotes(i + 1, "DEFAULT".getBytes());
/*      */         
/* 1099 */         newRowData = (byte[][])null;
/*      */       }
/* 1101 */       else if (this.defaultColumnValue[i] != null) {
/* 1102 */         Field f = this.fields[i];
/*      */         
/* 1104 */         switch (f.getMysqlType())
/*      */         {
/*      */         case 7: 
/*      */         case 10: 
/*      */         case 11: 
/*      */         case 12: 
/*      */         case 14: 
/* 1111 */           if ((this.defaultColumnValue[i].length > 7) && (this.defaultColumnValue[i][0] == 67) && (this.defaultColumnValue[i][1] == 85) && (this.defaultColumnValue[i][2] == 82) && (this.defaultColumnValue[i][3] == 82) && (this.defaultColumnValue[i][4] == 69) && (this.defaultColumnValue[i][5] == 78) && (this.defaultColumnValue[i][6] == 84) && (this.defaultColumnValue[i][7] == 95))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1120 */             this.inserter.setBytesNoEscapeNoQuotes(i + 1, this.defaultColumnValue[i]);
/*      */           }
/*      */           
/* 1123 */           break;
/*      */         }
/*      */         
/* 1126 */         this.inserter.setBytes(i + 1, this.defaultColumnValue[i], false, false);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1132 */         byte[] defaultValueCopy = new byte[this.defaultColumnValue[i].length];
/* 1133 */         System.arraycopy(this.defaultColumnValue[i], 0, defaultValueCopy, 0, defaultValueCopy.length);
/*      */         
/* 1135 */         newRowData[i] = defaultValueCopy;
/*      */       } else {
/* 1137 */         this.inserter.setNull(i + 1, 0);
/* 1138 */         newRowData[i] = null;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean next()
/*      */     throws SQLException
/*      */   {
/* 1164 */     return super.next();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean prev()
/*      */     throws SQLException
/*      */   {
/* 1183 */     return super.prev();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean previous()
/*      */     throws SQLException
/*      */   {
/* 1205 */     return super.previous();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void realClose(boolean calledExplicitly)
/*      */     throws SQLException
/*      */   {
/* 1218 */     if (this.isClosed) {
/* 1219 */       return;
/*      */     }
/*      */     
/* 1222 */     SQLException sqlEx = null;
/*      */     
/* 1224 */     if ((this.useUsageAdvisor) && 
/* 1225 */       (this.deleter == null) && (this.inserter == null) && (this.refresher == null) && (this.updater == null))
/*      */     {
/* 1227 */       this.eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
/*      */       
/* 1229 */       String message = Messages.getString("UpdatableResultSet.34");
/*      */       
/* 1231 */       this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", this.owningStatement == null ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, this.owningStatement == null ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1245 */       if (this.deleter != null) {
/* 1246 */         this.deleter.close();
/*      */       }
/*      */     } catch (SQLException ex) {
/* 1249 */       sqlEx = ex;
/*      */     }
/*      */     try
/*      */     {
/* 1253 */       if (this.inserter != null) {
/* 1254 */         this.inserter.close();
/*      */       }
/*      */     } catch (SQLException ex) {
/* 1257 */       sqlEx = ex;
/*      */     }
/*      */     try
/*      */     {
/* 1261 */       if (this.refresher != null) {
/* 1262 */         this.refresher.close();
/*      */       }
/*      */     } catch (SQLException ex) {
/* 1265 */       sqlEx = ex;
/*      */     }
/*      */     try
/*      */     {
/* 1269 */       if (this.updater != null) {
/* 1270 */         this.updater.close();
/*      */       }
/*      */     } catch (SQLException ex) {
/* 1273 */       sqlEx = ex;
/*      */     }
/*      */     
/* 1276 */     super.realClose(calledExplicitly);
/*      */     
/* 1278 */     if (sqlEx != null) {
/* 1279 */       throw sqlEx;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void refreshRow()
/*      */     throws SQLException
/*      */   {
/* 1304 */     checkClosed();
/*      */     
/* 1306 */     if (!this.isUpdatable) {
/* 1307 */       throw new NotUpdatable();
/*      */     }
/*      */     
/* 1310 */     if (this.onInsertRow)
/* 1311 */       throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.8"), getExceptionInterceptor());
/* 1312 */     if (this.rowData.size() == 0)
/* 1313 */       throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.9"), getExceptionInterceptor());
/* 1314 */     if (isBeforeFirst())
/* 1315 */       throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.10"), getExceptionInterceptor());
/* 1316 */     if (isAfterLast()) {
/* 1317 */       throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.11"), getExceptionInterceptor());
/*      */     }
/*      */     
/* 1320 */     refreshRow(this.updater, this.thisRow);
/*      */   }
/*      */   
/*      */   private synchronized void refreshRow(PreparedStatement updateInsertStmt, ResultSetRow rowToRefresh) throws SQLException
/*      */   {
/* 1325 */     if (this.refresher == null) {
/* 1326 */       if (this.refreshSQL == null) {
/* 1327 */         generateStatements();
/*      */       }
/*      */       
/* 1330 */       this.refresher = ((PreparedStatement)this.connection.clientPrepareStatement(this.refreshSQL));
/*      */     }
/*      */     
/*      */ 
/* 1334 */     this.refresher.clearParameters();
/*      */     
/* 1336 */     int numKeys = this.primaryKeyIndicies.size();
/*      */     
/* 1338 */     if (numKeys == 1) {
/* 1339 */       byte[] dataFrom = null;
/* 1340 */       int index = ((Integer)this.primaryKeyIndicies.get(0)).intValue();
/*      */       
/* 1342 */       if ((!this.doingUpdates) && (!this.onInsertRow)) {
/* 1343 */         dataFrom = (byte[])rowToRefresh.getColumnValue(index);
/*      */       } else {
/* 1345 */         dataFrom = updateInsertStmt.getBytesRepresentation(index);
/*      */         
/*      */ 
/* 1348 */         if ((updateInsertStmt.isNull(index)) || (dataFrom.length == 0)) {
/* 1349 */           dataFrom = (byte[])rowToRefresh.getColumnValue(index);
/*      */         } else {
/* 1351 */           dataFrom = stripBinaryPrefix(dataFrom);
/*      */         }
/*      */       }
/*      */       
/* 1355 */       if (this.fields[index].getvalueNeedsQuoting()) {
/* 1356 */         this.refresher.setBytesNoEscape(1, dataFrom);
/*      */       } else {
/* 1358 */         this.refresher.setBytesNoEscapeNoQuotes(1, dataFrom);
/*      */       }
/*      */     }
/*      */     else {
/* 1362 */       for (int i = 0; i < numKeys; i++) {
/* 1363 */         byte[] dataFrom = null;
/* 1364 */         int index = ((Integer)this.primaryKeyIndicies.get(i)).intValue();
/*      */         
/*      */ 
/* 1367 */         if ((!this.doingUpdates) && (!this.onInsertRow)) {
/* 1368 */           dataFrom = (byte[])rowToRefresh.getColumnValue(index);
/*      */         } else {
/* 1370 */           dataFrom = updateInsertStmt.getBytesRepresentation(index);
/*      */           
/*      */ 
/* 1373 */           if ((updateInsertStmt.isNull(index)) || (dataFrom.length == 0)) {
/* 1374 */             dataFrom = (byte[])rowToRefresh.getColumnValue(index);
/*      */           } else {
/* 1376 */             dataFrom = stripBinaryPrefix(dataFrom);
/*      */           }
/*      */         }
/*      */         
/* 1380 */         this.refresher.setBytesNoEscape(i + 1, dataFrom);
/*      */       }
/*      */     }
/*      */     
/* 1384 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1387 */       rs = this.refresher.executeQuery();
/*      */       
/* 1389 */       int numCols = rs.getMetaData().getColumnCount();
/*      */       
/* 1391 */       if (rs.next()) {
/* 1392 */         for (int i = 0; i < numCols; i++) {
/* 1393 */           byte[] val = rs.getBytes(i + 1);
/*      */           
/* 1395 */           if ((val == null) || (rs.wasNull())) {
/* 1396 */             rowToRefresh.setColumnValue(i, null);
/*      */           } else {
/* 1398 */             rowToRefresh.setColumnValue(i, rs.getBytes(i + 1));
/*      */           }
/*      */         }
/*      */       } else {
/* 1402 */         throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.12"), "S1000", getExceptionInterceptor());
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/* 1407 */       if (rs != null) {
/*      */         try {
/* 1409 */           rs.close();
/*      */         }
/*      */         catch (SQLException ex) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean relative(int rows)
/*      */     throws SQLException
/*      */   {
/* 1444 */     return super.relative(rows);
/*      */   }
/*      */   
/*      */   private void resetInserter() throws SQLException {
/* 1448 */     this.inserter.clearParameters();
/*      */     
/* 1450 */     for (int i = 0; i < this.fields.length; i++) {
/* 1451 */       this.inserter.setNull(i + 1, 0);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean rowDeleted()
/*      */     throws SQLException
/*      */   {
/* 1471 */     throw SQLError.notImplemented();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean rowInserted()
/*      */     throws SQLException
/*      */   {
/* 1489 */     throw SQLError.notImplemented();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized boolean rowUpdated()
/*      */     throws SQLException
/*      */   {
/* 1507 */     throw SQLError.notImplemented();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setResultSetConcurrency(int concurrencyFlag)
/*      */   {
/* 1517 */     super.setResultSetConcurrency(concurrencyFlag);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private byte[] stripBinaryPrefix(byte[] dataFrom)
/*      */   {
/* 1531 */     return StringUtils.stripEnclosure(dataFrom, "_binary'", "'");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected synchronized void syncUpdate()
/*      */     throws SQLException
/*      */   {
/* 1542 */     if (this.updater == null) {
/* 1543 */       if (this.updateSQL == null) {
/* 1544 */         generateStatements();
/*      */       }
/*      */       
/* 1547 */       this.updater = ((PreparedStatement)this.connection.clientPrepareStatement(this.updateSQL));
/*      */     }
/*      */     
/*      */ 
/* 1551 */     int numFields = this.fields.length;
/* 1552 */     this.updater.clearParameters();
/*      */     
/* 1554 */     for (int i = 0; i < numFields; i++) {
/* 1555 */       if (this.thisRow.getColumnValue(i) != null)
/*      */       {
/* 1557 */         if (this.fields[i].getvalueNeedsQuoting()) {
/* 1558 */           this.updater.setBytes(i + 1, (byte[])this.thisRow.getColumnValue(i), this.fields[i].isBinary(), false);
/*      */         }
/*      */         else {
/* 1561 */           this.updater.setBytesNoEscapeNoQuotes(i + 1, (byte[])this.thisRow.getColumnValue(i));
/*      */         }
/*      */       } else {
/* 1564 */         this.updater.setNull(i + 1, 0);
/*      */       }
/*      */     }
/*      */     
/* 1568 */     int numKeys = this.primaryKeyIndicies.size();
/*      */     
/* 1570 */     if (numKeys == 1) {
/* 1571 */       int index = ((Integer)this.primaryKeyIndicies.get(0)).intValue();
/* 1572 */       setParamValue(this.updater, numFields + 1, this.thisRow, index, this.fields[index].getSQLType());
/*      */     }
/*      */     else {
/* 1575 */       for (int i = 0; i < numKeys; i++) {
/* 1576 */         int idx = ((Integer)this.primaryKeyIndicies.get(i)).intValue();
/* 1577 */         setParamValue(this.updater, numFields + i + 1, this.thisRow, idx, this.fields[idx].getSQLType());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateAsciiStream(int columnIndex, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 1602 */     if (!this.onInsertRow) {
/* 1603 */       if (!this.doingUpdates) {
/* 1604 */         this.doingUpdates = true;
/* 1605 */         syncUpdate();
/*      */       }
/*      */       
/* 1608 */       this.updater.setAsciiStream(columnIndex, x, length);
/*      */     } else {
/* 1610 */       this.inserter.setAsciiStream(columnIndex, x, length);
/* 1611 */       this.thisRow.setColumnValue(columnIndex - 1, STREAM_DATA_MARKER);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateAsciiStream(String columnName, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 1634 */     updateAsciiStream(findColumn(columnName), x, length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateBigDecimal(int columnIndex, BigDecimal x)
/*      */     throws SQLException
/*      */   {
/* 1653 */     if (!this.onInsertRow) {
/* 1654 */       if (!this.doingUpdates) {
/* 1655 */         this.doingUpdates = true;
/* 1656 */         syncUpdate();
/*      */       }
/*      */       
/* 1659 */       this.updater.setBigDecimal(columnIndex, x);
/*      */     } else {
/* 1661 */       this.inserter.setBigDecimal(columnIndex, x);
/*      */       
/* 1663 */       if (x == null) {
/* 1664 */         this.thisRow.setColumnValue(columnIndex - 1, null);
/*      */       } else {
/* 1666 */         this.thisRow.setColumnValue(columnIndex - 1, x.toString().getBytes());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateBigDecimal(String columnName, BigDecimal x)
/*      */     throws SQLException
/*      */   {
/* 1687 */     updateBigDecimal(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateBinaryStream(int columnIndex, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 1709 */     if (!this.onInsertRow) {
/* 1710 */       if (!this.doingUpdates) {
/* 1711 */         this.doingUpdates = true;
/* 1712 */         syncUpdate();
/*      */       }
/*      */       
/* 1715 */       this.updater.setBinaryStream(columnIndex, x, length);
/*      */     } else {
/* 1717 */       this.inserter.setBinaryStream(columnIndex, x, length);
/*      */       
/* 1719 */       if (x == null) {
/* 1720 */         this.thisRow.setColumnValue(columnIndex - 1, null);
/*      */       } else {
/* 1722 */         this.thisRow.setColumnValue(columnIndex - 1, STREAM_DATA_MARKER);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateBinaryStream(String columnName, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 1746 */     updateBinaryStream(findColumn(columnName), x, length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public synchronized void updateBlob(int columnIndex, Blob blob)
/*      */     throws SQLException
/*      */   {
/* 1754 */     if (!this.onInsertRow) {
/* 1755 */       if (!this.doingUpdates) {
/* 1756 */         this.doingUpdates = true;
/* 1757 */         syncUpdate();
/*      */       }
/*      */       
/* 1760 */       this.updater.setBlob(columnIndex, blob);
/*      */     } else {
/* 1762 */       this.inserter.setBlob(columnIndex, blob);
/*      */       
/* 1764 */       if (blob == null) {
/* 1765 */         this.thisRow.setColumnValue(columnIndex - 1, null);
/*      */       } else {
/* 1767 */         this.thisRow.setColumnValue(columnIndex - 1, STREAM_DATA_MARKER);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public synchronized void updateBlob(String columnName, Blob blob)
/*      */     throws SQLException
/*      */   {
/* 1777 */     updateBlob(findColumn(columnName), blob);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateBoolean(int columnIndex, boolean x)
/*      */     throws SQLException
/*      */   {
/* 1796 */     if (!this.onInsertRow) {
/* 1797 */       if (!this.doingUpdates) {
/* 1798 */         this.doingUpdates = true;
/* 1799 */         syncUpdate();
/*      */       }
/*      */       
/* 1802 */       this.updater.setBoolean(columnIndex, x);
/*      */     } else {
/* 1804 */       this.inserter.setBoolean(columnIndex, x);
/*      */       
/* 1806 */       this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateBoolean(String columnName, boolean x)
/*      */     throws SQLException
/*      */   {
/* 1827 */     updateBoolean(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateByte(int columnIndex, byte x)
/*      */     throws SQLException
/*      */   {
/* 1846 */     if (!this.onInsertRow) {
/* 1847 */       if (!this.doingUpdates) {
/* 1848 */         this.doingUpdates = true;
/* 1849 */         syncUpdate();
/*      */       }
/*      */       
/* 1852 */       this.updater.setByte(columnIndex, x);
/*      */     } else {
/* 1854 */       this.inserter.setByte(columnIndex, x);
/*      */       
/* 1856 */       this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateByte(String columnName, byte x)
/*      */     throws SQLException
/*      */   {
/* 1877 */     updateByte(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateBytes(int columnIndex, byte[] x)
/*      */     throws SQLException
/*      */   {
/* 1896 */     if (!this.onInsertRow) {
/* 1897 */       if (!this.doingUpdates) {
/* 1898 */         this.doingUpdates = true;
/* 1899 */         syncUpdate();
/*      */       }
/*      */       
/* 1902 */       this.updater.setBytes(columnIndex, x);
/*      */     } else {
/* 1904 */       this.inserter.setBytes(columnIndex, x);
/*      */       
/* 1906 */       this.thisRow.setColumnValue(columnIndex - 1, x);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateBytes(String columnName, byte[] x)
/*      */     throws SQLException
/*      */   {
/* 1926 */     updateBytes(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateCharacterStream(int columnIndex, Reader x, int length)
/*      */     throws SQLException
/*      */   {
/* 1948 */     if (!this.onInsertRow) {
/* 1949 */       if (!this.doingUpdates) {
/* 1950 */         this.doingUpdates = true;
/* 1951 */         syncUpdate();
/*      */       }
/*      */       
/* 1954 */       this.updater.setCharacterStream(columnIndex, x, length);
/*      */     } else {
/* 1956 */       this.inserter.setCharacterStream(columnIndex, x, length);
/*      */       
/* 1958 */       if (x == null) {
/* 1959 */         this.thisRow.setColumnValue(columnIndex - 1, null);
/*      */       } else {
/* 1961 */         this.thisRow.setColumnValue(columnIndex - 1, STREAM_DATA_MARKER);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateCharacterStream(String columnName, Reader reader, int length)
/*      */     throws SQLException
/*      */   {
/* 1985 */     updateCharacterStream(findColumn(columnName), reader, length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void updateClob(int columnIndex, Clob clob)
/*      */     throws SQLException
/*      */   {
/* 1993 */     if (clob == null) {
/* 1994 */       updateNull(columnIndex);
/*      */     } else {
/* 1996 */       updateCharacterStream(columnIndex, clob.getCharacterStream(), (int)clob.length());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateDate(int columnIndex, Date x)
/*      */     throws SQLException
/*      */   {
/* 2017 */     if (!this.onInsertRow) {
/* 2018 */       if (!this.doingUpdates) {
/* 2019 */         this.doingUpdates = true;
/* 2020 */         syncUpdate();
/*      */       }
/*      */       
/* 2023 */       this.updater.setDate(columnIndex, x);
/*      */     } else {
/* 2025 */       this.inserter.setDate(columnIndex, x);
/*      */       
/* 2027 */       this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateDate(String columnName, Date x)
/*      */     throws SQLException
/*      */   {
/* 2048 */     updateDate(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateDouble(int columnIndex, double x)
/*      */     throws SQLException
/*      */   {
/* 2067 */     if (!this.onInsertRow) {
/* 2068 */       if (!this.doingUpdates) {
/* 2069 */         this.doingUpdates = true;
/* 2070 */         syncUpdate();
/*      */       }
/*      */       
/* 2073 */       this.updater.setDouble(columnIndex, x);
/*      */     } else {
/* 2075 */       this.inserter.setDouble(columnIndex, x);
/*      */       
/* 2077 */       this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateDouble(String columnName, double x)
/*      */     throws SQLException
/*      */   {
/* 2098 */     updateDouble(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateFloat(int columnIndex, float x)
/*      */     throws SQLException
/*      */   {
/* 2117 */     if (!this.onInsertRow) {
/* 2118 */       if (!this.doingUpdates) {
/* 2119 */         this.doingUpdates = true;
/* 2120 */         syncUpdate();
/*      */       }
/*      */       
/* 2123 */       this.updater.setFloat(columnIndex, x);
/*      */     } else {
/* 2125 */       this.inserter.setFloat(columnIndex, x);
/*      */       
/* 2127 */       this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateFloat(String columnName, float x)
/*      */     throws SQLException
/*      */   {
/* 2148 */     updateFloat(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateInt(int columnIndex, int x)
/*      */     throws SQLException
/*      */   {
/* 2167 */     if (!this.onInsertRow) {
/* 2168 */       if (!this.doingUpdates) {
/* 2169 */         this.doingUpdates = true;
/* 2170 */         syncUpdate();
/*      */       }
/*      */       
/* 2173 */       this.updater.setInt(columnIndex, x);
/*      */     } else {
/* 2175 */       this.inserter.setInt(columnIndex, x);
/*      */       
/* 2177 */       this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateInt(String columnName, int x)
/*      */     throws SQLException
/*      */   {
/* 2198 */     updateInt(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateLong(int columnIndex, long x)
/*      */     throws SQLException
/*      */   {
/* 2217 */     if (!this.onInsertRow) {
/* 2218 */       if (!this.doingUpdates) {
/* 2219 */         this.doingUpdates = true;
/* 2220 */         syncUpdate();
/*      */       }
/*      */       
/* 2223 */       this.updater.setLong(columnIndex, x);
/*      */     } else {
/* 2225 */       this.inserter.setLong(columnIndex, x);
/*      */       
/* 2227 */       this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateLong(String columnName, long x)
/*      */     throws SQLException
/*      */   {
/* 2248 */     updateLong(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateNull(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 2264 */     if (!this.onInsertRow) {
/* 2265 */       if (!this.doingUpdates) {
/* 2266 */         this.doingUpdates = true;
/* 2267 */         syncUpdate();
/*      */       }
/*      */       
/* 2270 */       this.updater.setNull(columnIndex, 0);
/*      */     } else {
/* 2272 */       this.inserter.setNull(columnIndex, 0);
/*      */       
/* 2274 */       this.thisRow.setColumnValue(columnIndex - 1, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateNull(String columnName)
/*      */     throws SQLException
/*      */   {
/* 2291 */     updateNull(findColumn(columnName));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateObject(int columnIndex, Object x)
/*      */     throws SQLException
/*      */   {
/* 2310 */     if (!this.onInsertRow) {
/* 2311 */       if (!this.doingUpdates) {
/* 2312 */         this.doingUpdates = true;
/* 2313 */         syncUpdate();
/*      */       }
/*      */       
/* 2316 */       this.updater.setObject(columnIndex, x);
/*      */     } else {
/* 2318 */       this.inserter.setObject(columnIndex, x);
/*      */       
/* 2320 */       this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateObject(int columnIndex, Object x, int scale)
/*      */     throws SQLException
/*      */   {
/* 2345 */     if (!this.onInsertRow) {
/* 2346 */       if (!this.doingUpdates) {
/* 2347 */         this.doingUpdates = true;
/* 2348 */         syncUpdate();
/*      */       }
/*      */       
/* 2351 */       this.updater.setObject(columnIndex, x);
/*      */     } else {
/* 2353 */       this.inserter.setObject(columnIndex, x);
/*      */       
/* 2355 */       this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateObject(String columnName, Object x)
/*      */     throws SQLException
/*      */   {
/* 2376 */     updateObject(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateObject(String columnName, Object x, int scale)
/*      */     throws SQLException
/*      */   {
/* 2399 */     updateObject(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateRow()
/*      */     throws SQLException
/*      */   {
/* 2413 */     if (!this.isUpdatable) {
/* 2414 */       throw new NotUpdatable(this.notUpdatableReason);
/*      */     }
/*      */     
/* 2417 */     if (this.doingUpdates) {
/* 2418 */       this.updater.executeUpdate();
/* 2419 */       refreshRow();
/* 2420 */       this.doingUpdates = false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2426 */     syncUpdate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateShort(int columnIndex, short x)
/*      */     throws SQLException
/*      */   {
/* 2445 */     if (!this.onInsertRow) {
/* 2446 */       if (!this.doingUpdates) {
/* 2447 */         this.doingUpdates = true;
/* 2448 */         syncUpdate();
/*      */       }
/*      */       
/* 2451 */       this.updater.setShort(columnIndex, x);
/*      */     } else {
/* 2453 */       this.inserter.setShort(columnIndex, x);
/*      */       
/* 2455 */       this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateShort(String columnName, short x)
/*      */     throws SQLException
/*      */   {
/* 2476 */     updateShort(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateString(int columnIndex, String x)
/*      */     throws SQLException
/*      */   {
/* 2495 */     checkClosed();
/*      */     
/* 2497 */     if (!this.onInsertRow) {
/* 2498 */       if (!this.doingUpdates) {
/* 2499 */         this.doingUpdates = true;
/* 2500 */         syncUpdate();
/*      */       }
/*      */       
/* 2503 */       this.updater.setString(columnIndex, x);
/*      */     } else {
/* 2505 */       this.inserter.setString(columnIndex, x);
/*      */       
/* 2507 */       if (x == null) {
/* 2508 */         this.thisRow.setColumnValue(columnIndex - 1, null);
/*      */       }
/* 2510 */       else if (getCharConverter() != null) {
/* 2511 */         this.thisRow.setColumnValue(columnIndex - 1, StringUtils.getBytes(x, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor()));
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2516 */         this.thisRow.setColumnValue(columnIndex - 1, x.getBytes());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateString(String columnName, String x)
/*      */     throws SQLException
/*      */   {
/* 2538 */     updateString(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateTime(int columnIndex, Time x)
/*      */     throws SQLException
/*      */   {
/* 2557 */     if (!this.onInsertRow) {
/* 2558 */       if (!this.doingUpdates) {
/* 2559 */         this.doingUpdates = true;
/* 2560 */         syncUpdate();
/*      */       }
/*      */       
/* 2563 */       this.updater.setTime(columnIndex, x);
/*      */     } else {
/* 2565 */       this.inserter.setTime(columnIndex, x);
/*      */       
/* 2567 */       this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateTime(String columnName, Time x)
/*      */     throws SQLException
/*      */   {
/* 2588 */     updateTime(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateTimestamp(int columnIndex, Timestamp x)
/*      */     throws SQLException
/*      */   {
/* 2607 */     if (!this.onInsertRow) {
/* 2608 */       if (!this.doingUpdates) {
/* 2609 */         this.doingUpdates = true;
/* 2610 */         syncUpdate();
/*      */       }
/*      */       
/* 2613 */       this.updater.setTimestamp(columnIndex, x);
/*      */     } else {
/* 2615 */       this.inserter.setTimestamp(columnIndex, x);
/*      */       
/* 2617 */       this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void updateTimestamp(String columnName, Timestamp x)
/*      */     throws SQLException
/*      */   {
/* 2638 */     updateTimestamp(findColumn(columnName), x);
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\UpdatableResultSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */