/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.exceptions.MySQLStatementCancelledException;
/*      */ import com.mysql.jdbc.exceptions.MySQLTimeoutException;
/*      */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*      */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URL;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.CharsetEncoder;
/*      */ import java.sql.Array;
/*      */ import java.sql.BatchUpdateException;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.ParameterMetaData;
/*      */ import java.sql.Ref;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.DateFormat;
/*      */ import java.text.ParsePosition;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import java.util.Timer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PreparedStatement
/*      */   extends StatementImpl
/*      */   implements java.sql.PreparedStatement
/*      */ {
/*      */   private static final Constructor JDBC_4_PSTMT_2_ARG_CTOR;
/*      */   private static final Constructor JDBC_4_PSTMT_3_ARG_CTOR;
/*      */   private static final Constructor JDBC_4_PSTMT_4_ARG_CTOR;
/*      */   
/*      */   static
/*      */   {
/*   99 */     if (Util.isJdbc4()) {
/*      */       try {
/*  101 */         JDBC_4_PSTMT_2_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4PreparedStatement").getConstructor(new Class[] { MySQLConnection.class, String.class });
/*      */         
/*      */ 
/*      */ 
/*  105 */         JDBC_4_PSTMT_3_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4PreparedStatement").getConstructor(new Class[] { MySQLConnection.class, String.class, String.class });
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  110 */         JDBC_4_PSTMT_4_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4PreparedStatement").getConstructor(new Class[] { MySQLConnection.class, String.class, String.class, ParseInfo.class });
/*      */ 
/*      */       }
/*      */       catch (SecurityException e)
/*      */       {
/*      */ 
/*  116 */         throw new RuntimeException(e);
/*      */       } catch (NoSuchMethodException e) {
/*  118 */         throw new RuntimeException(e);
/*      */       } catch (ClassNotFoundException e) {
/*  120 */         throw new RuntimeException(e);
/*      */       }
/*      */     } else {
/*  123 */       JDBC_4_PSTMT_2_ARG_CTOR = null;
/*  124 */       JDBC_4_PSTMT_3_ARG_CTOR = null;
/*  125 */       JDBC_4_PSTMT_4_ARG_CTOR = null;
/*      */     }
/*      */   }
/*      */   
/*      */   class BatchParams {
/*  130 */     boolean[] isNull = null;
/*      */     
/*  132 */     boolean[] isStream = null;
/*      */     
/*  134 */     InputStream[] parameterStreams = null;
/*      */     
/*  136 */     byte[][] parameterStrings = (byte[][])null;
/*      */     
/*  138 */     int[] streamLengths = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     BatchParams(byte[][] strings, InputStream[] streams, boolean[] isStreamFlags, int[] lengths, boolean[] isNullFlags)
/*      */     {
/*  145 */       this.parameterStrings = new byte[strings.length][];
/*  146 */       this.parameterStreams = new InputStream[streams.length];
/*  147 */       this.isStream = new boolean[isStreamFlags.length];
/*  148 */       this.streamLengths = new int[lengths.length];
/*  149 */       this.isNull = new boolean[isNullFlags.length];
/*  150 */       System.arraycopy(strings, 0, this.parameterStrings, 0, strings.length);
/*      */       
/*  152 */       System.arraycopy(streams, 0, this.parameterStreams, 0, streams.length);
/*      */       
/*  154 */       System.arraycopy(isStreamFlags, 0, this.isStream, 0, isStreamFlags.length);
/*      */       
/*  156 */       System.arraycopy(lengths, 0, this.streamLengths, 0, lengths.length);
/*  157 */       System.arraycopy(isNullFlags, 0, this.isNull, 0, isNullFlags.length);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   class EndPoint
/*      */   {
/*      */     int begin;
/*      */     int end;
/*      */     
/*      */     EndPoint(int b, int e)
/*      */     {
/*  169 */       this.begin = b;
/*  170 */       this.end = e;
/*      */     }
/*      */   }
/*      */   
/*      */   class ParseInfo {
/*  175 */     char firstStmtChar = '\000';
/*      */     
/*  177 */     boolean foundLimitClause = false;
/*      */     
/*  179 */     boolean foundLoadData = false;
/*      */     
/*  181 */     long lastUsed = 0L;
/*      */     
/*  183 */     int statementLength = 0;
/*      */     
/*  185 */     int statementStartPos = 0;
/*      */     
/*  187 */     boolean canRewriteAsMultiValueInsert = false;
/*      */     
/*  189 */     byte[][] staticSql = (byte[][])null;
/*      */     
/*  191 */     boolean isOnDuplicateKeyUpdate = false;
/*      */     
/*  193 */     int locationOfOnDuplicateKeyUpdate = -1;
/*      */     
/*      */     String valuesClause;
/*      */     
/*  197 */     boolean parametersInDuplicateKeyClause = false;
/*      */     
/*      */     private ParseInfo batchHead;
/*      */     
/*      */     private ParseInfo batchValues;
/*      */     
/*      */     private ParseInfo batchODKUClause;
/*      */     
/*      */     ParseInfo(String sql, MySQLConnection conn, DatabaseMetaData dbmd, String encoding, SingleByteCharsetConverter converter)
/*      */       throws SQLException
/*      */     {
/*  208 */       this(sql, conn, dbmd, encoding, converter, true);
/*      */     }
/*      */     
/*      */     public ParseInfo(String sql, MySQLConnection conn, DatabaseMetaData dbmd, String encoding, SingleByteCharsetConverter converter, boolean buildRewriteInfo) throws SQLException
/*      */     {
/*      */       try
/*      */       {
/*  215 */         if (sql == null) {
/*  216 */           throw SQLError.createSQLException(Messages.getString("PreparedStatement.61"), "S1009", PreparedStatement.this.getExceptionInterceptor());
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  221 */         this.locationOfOnDuplicateKeyUpdate = PreparedStatement.this.getOnDuplicateKeyLocation(sql);
/*  222 */         this.isOnDuplicateKeyUpdate = (this.locationOfOnDuplicateKeyUpdate != -1);
/*      */         
/*  224 */         this.lastUsed = System.currentTimeMillis();
/*      */         
/*  226 */         quotedIdentifierString = dbmd.getIdentifierQuoteString();
/*      */         
/*  228 */         char quotedIdentifierChar = '\000';
/*      */         
/*  230 */         if ((quotedIdentifierString != null) && (!quotedIdentifierString.equals(" ")) && (quotedIdentifierString.length() > 0))
/*      */         {
/*      */ 
/*  233 */           quotedIdentifierChar = quotedIdentifierString.charAt(0);
/*      */         }
/*      */         
/*  236 */         this.statementLength = sql.length();
/*      */         
/*  238 */         ArrayList endpointList = new ArrayList();
/*  239 */         boolean inQuotes = false;
/*  240 */         char quoteChar = '\000';
/*  241 */         boolean inQuotedId = false;
/*  242 */         int lastParmEnd = 0;
/*      */         
/*      */ 
/*  245 */         int stopLookingForLimitClause = this.statementLength - 5;
/*      */         
/*  247 */         this.foundLimitClause = false;
/*      */         
/*  249 */         boolean noBackslashEscapes = PreparedStatement.this.connection.isNoBackslashEscapesSet();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  255 */         this.statementStartPos = PreparedStatement.this.findStartOfStatement(sql);
/*      */         
/*  257 */         for (int i = this.statementStartPos; i < this.statementLength; i++) {
/*  258 */           char c = sql.charAt(i);
/*      */           
/*  260 */           if ((this.firstStmtChar == 0) && (Character.isLetter(c)))
/*      */           {
/*      */ 
/*  263 */             this.firstStmtChar = Character.toUpperCase(c);
/*      */           }
/*      */           
/*  266 */           if ((!noBackslashEscapes) && (c == '\\') && (i < this.statementLength - 1))
/*      */           {
/*  268 */             i++;
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*  274 */             if ((!inQuotes) && (quotedIdentifierChar != 0) && (c == quotedIdentifierChar))
/*      */             {
/*  276 */               inQuotedId = !inQuotedId;
/*  277 */             } else if (!inQuotedId)
/*      */             {
/*      */ 
/*  280 */               if (inQuotes) {
/*  281 */                 if (((c == '\'') || (c == '"')) && (c == quoteChar)) {
/*  282 */                   if ((i < this.statementLength - 1) && (sql.charAt(i + 1) == quoteChar)) {
/*  283 */                     i++;
/*  284 */                     continue;
/*      */                   }
/*      */                   
/*  287 */                   inQuotes = !inQuotes;
/*  288 */                   quoteChar = '\000';
/*  289 */                 } else if (((c == '\'') || (c == '"')) && (c == quoteChar)) {
/*  290 */                   inQuotes = !inQuotes;
/*  291 */                   quoteChar = '\000';
/*      */                 }
/*      */               } else {
/*  294 */                 if ((c == '#') || ((c == '-') && (i + 1 < this.statementLength) && (sql.charAt(i + 1) == '-')))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*  299 */                   int endOfStmt = this.statementLength - 1;
/*  301 */                   for (; 
/*  301 */                       i < endOfStmt; i++) {
/*  302 */                     c = sql.charAt(i);
/*      */                     
/*  304 */                     if ((c == '\r') || (c == '\n')) {
/*      */                       break;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*  310 */                 if ((c == '/') && (i + 1 < this.statementLength))
/*      */                 {
/*  312 */                   char cNext = sql.charAt(i + 1);
/*      */                   
/*  314 */                   if (cNext == '*') {
/*  315 */                     i += 2;
/*      */                     
/*  317 */                     for (int j = i; j < this.statementLength; j++) {
/*  318 */                       i++;
/*  319 */                       cNext = sql.charAt(j);
/*      */                       
/*  321 */                       if ((cNext == '*') && (j + 1 < this.statementLength) && 
/*  322 */                         (sql.charAt(j + 1) == '/')) {
/*  323 */                         i++;
/*      */                         
/*  325 */                         if (i >= this.statementLength) break;
/*  326 */                         c = sql.charAt(i); break;
/*      */                       }
/*      */                       
/*      */                     }
/*      */                     
/*      */                   }
/*      */                   
/*      */                 }
/*  334 */                 else if ((c == '\'') || (c == '"')) {
/*  335 */                   inQuotes = true;
/*  336 */                   quoteChar = c;
/*      */                 }
/*      */               }
/*      */             }
/*      */             
/*  341 */             if ((c == '?') && (!inQuotes) && (!inQuotedId)) {
/*  342 */               endpointList.add(new int[] { lastParmEnd, i });
/*  343 */               lastParmEnd = i + 1;
/*      */               
/*  345 */               if ((this.isOnDuplicateKeyUpdate) && (i > this.locationOfOnDuplicateKeyUpdate)) {
/*  346 */                 this.parametersInDuplicateKeyClause = true;
/*      */               }
/*      */             }
/*      */             
/*  350 */             if ((!inQuotes) && (i < stopLookingForLimitClause) && (
/*  351 */               (c == 'L') || (c == 'l'))) {
/*  352 */               char posI1 = sql.charAt(i + 1);
/*      */               
/*  354 */               if ((posI1 == 'I') || (posI1 == 'i')) {
/*  355 */                 char posM = sql.charAt(i + 2);
/*      */                 
/*  357 */                 if ((posM == 'M') || (posM == 'm')) {
/*  358 */                   char posI2 = sql.charAt(i + 3);
/*      */                   
/*  360 */                   if ((posI2 == 'I') || (posI2 == 'i')) {
/*  361 */                     char posT = sql.charAt(i + 4);
/*      */                     
/*  363 */                     if ((posT == 'T') || (posT == 't')) {
/*  364 */                       this.foundLimitClause = true;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  373 */         if (this.firstStmtChar == 'L') {
/*  374 */           if (StringUtils.startsWithIgnoreCaseAndWs(sql, "LOAD DATA")) {
/*  375 */             this.foundLoadData = true;
/*      */           } else {
/*  377 */             this.foundLoadData = false;
/*      */           }
/*      */         } else {
/*  380 */           this.foundLoadData = false;
/*      */         }
/*      */         
/*  383 */         endpointList.add(new int[] { lastParmEnd, this.statementLength });
/*  384 */         this.staticSql = new byte[endpointList.size()][];
/*  385 */         char[] asCharArray = sql.toCharArray();
/*      */         
/*  387 */         for (i = 0; i < this.staticSql.length; i++) {
/*  388 */           int[] ep = (int[])endpointList.get(i);
/*  389 */           int end = ep[1];
/*  390 */           int begin = ep[0];
/*  391 */           int len = end - begin;
/*      */           
/*  393 */           if (this.foundLoadData) {
/*  394 */             String temp = new String(asCharArray, begin, len);
/*  395 */             this.staticSql[i] = temp.getBytes();
/*  396 */           } else if (encoding == null) {
/*  397 */             byte[] buf = new byte[len];
/*      */             
/*  399 */             for (int j = 0; j < len; j++) {
/*  400 */               buf[j] = ((byte)sql.charAt(begin + j));
/*      */             }
/*      */             
/*  403 */             this.staticSql[i] = buf;
/*      */           }
/*  405 */           else if (converter != null) {
/*  406 */             this.staticSql[i] = StringUtils.getBytes(sql, converter, encoding, PreparedStatement.this.connection.getServerCharacterEncoding(), begin, len, PreparedStatement.this.connection.parserKnowsUnicode(), PreparedStatement.this.getExceptionInterceptor());
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  411 */             String temp = new String(asCharArray, begin, len);
/*      */             
/*  413 */             this.staticSql[i] = StringUtils.getBytes(temp, encoding, PreparedStatement.this.connection.getServerCharacterEncoding(), PreparedStatement.this.connection.parserKnowsUnicode(), conn, PreparedStatement.this.getExceptionInterceptor());
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (StringIndexOutOfBoundsException oobEx)
/*      */       {
/*      */         String quotedIdentifierString;
/*      */         
/*  421 */         SQLException sqlEx = new SQLException("Parse error for " + sql);
/*  422 */         sqlEx.initCause(oobEx);
/*      */         
/*  424 */         throw sqlEx;
/*      */       }
/*      */       
/*      */ 
/*  428 */       if (buildRewriteInfo) {
/*  429 */         this.canRewriteAsMultiValueInsert = ((PreparedStatement.canRewrite(sql, this.isOnDuplicateKeyUpdate, this.locationOfOnDuplicateKeyUpdate, this.statementStartPos)) && (!this.parametersInDuplicateKeyClause));
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  434 */         if ((this.canRewriteAsMultiValueInsert) && (conn.getRewriteBatchedStatements()))
/*      */         {
/*  436 */           buildRewriteBatchedParams(sql, conn, dbmd, encoding, converter);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void buildRewriteBatchedParams(String sql, MySQLConnection conn, DatabaseMetaData metadata, String encoding, SingleByteCharsetConverter converter)
/*      */       throws SQLException
/*      */     {
/*  451 */       this.valuesClause = extractValuesClause(sql);
/*  452 */       String odkuClause = this.isOnDuplicateKeyUpdate ? sql.substring(this.locationOfOnDuplicateKeyUpdate) : null;
/*      */       
/*      */ 
/*  455 */       String headSql = null;
/*      */       
/*  457 */       if (this.isOnDuplicateKeyUpdate) {
/*  458 */         headSql = sql.substring(0, this.locationOfOnDuplicateKeyUpdate);
/*      */       } else {
/*  460 */         headSql = sql;
/*      */       }
/*      */       
/*  463 */       this.batchHead = new ParseInfo(PreparedStatement.this, headSql, conn, metadata, encoding, converter, false);
/*      */       
/*  465 */       this.batchValues = new ParseInfo(PreparedStatement.this, "," + this.valuesClause, conn, metadata, encoding, converter, false);
/*      */       
/*  467 */       this.batchODKUClause = null;
/*      */       
/*  469 */       if ((odkuClause != null) && (odkuClause.length() > 0)) {
/*  470 */         this.batchODKUClause = new ParseInfo(PreparedStatement.this, "," + this.valuesClause + " " + odkuClause, conn, metadata, encoding, converter, false);
/*      */       }
/*      */     }
/*      */     
/*      */     private String extractValuesClause(String sql)
/*      */       throws SQLException
/*      */     {
/*  477 */       String quoteCharStr = PreparedStatement.this.connection.getMetaData().getIdentifierQuoteString();
/*      */       
/*      */ 
/*  480 */       int indexOfValues = -1;
/*  481 */       int valuesSearchStart = this.statementStartPos;
/*      */       
/*  483 */       while (indexOfValues == -1) {
/*  484 */         if (quoteCharStr.length() > 0) {
/*  485 */           indexOfValues = StringUtils.indexOfIgnoreCaseRespectQuotes(valuesSearchStart, PreparedStatement.this.originalSql, "VALUES", quoteCharStr.charAt(0), false);
/*      */         }
/*      */         else
/*      */         {
/*  489 */           indexOfValues = StringUtils.indexOfIgnoreCase(valuesSearchStart, PreparedStatement.this.originalSql, "VALUES");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  494 */         if (indexOfValues <= 0)
/*      */           break;
/*  496 */         char c = PreparedStatement.this.originalSql.charAt(indexOfValues - 1);
/*  497 */         if ((!Character.isWhitespace(c)) && (c != ')') && (c != '`')) {
/*  498 */           valuesSearchStart = indexOfValues + 6;
/*  499 */           indexOfValues = -1;
/*      */         }
/*      */         else {
/*  502 */           c = PreparedStatement.this.originalSql.charAt(indexOfValues + 6);
/*  503 */           if ((!Character.isWhitespace(c)) && (c != '(')) {
/*  504 */             valuesSearchStart = indexOfValues + 6;
/*  505 */             indexOfValues = -1;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  513 */       if (indexOfValues == -1) {
/*  514 */         return null;
/*      */       }
/*      */       
/*  517 */       int indexOfFirstParen = sql.indexOf('(', indexOfValues + 6);
/*      */       
/*  519 */       if (indexOfFirstParen == -1) {
/*  520 */         return null;
/*      */       }
/*      */       
/*  523 */       int endOfValuesClause = sql.lastIndexOf(')');
/*      */       
/*  525 */       if (endOfValuesClause == -1) {
/*  526 */         return null;
/*      */       }
/*      */       
/*  529 */       if (this.isOnDuplicateKeyUpdate) {
/*  530 */         endOfValuesClause = this.locationOfOnDuplicateKeyUpdate - 1;
/*      */       }
/*      */       
/*  533 */       return sql.substring(indexOfFirstParen, endOfValuesClause + 1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     synchronized ParseInfo getParseInfoForBatch(int numBatch)
/*      */     {
/*  540 */       PreparedStatement.AppendingBatchVisitor apv = new PreparedStatement.AppendingBatchVisitor(PreparedStatement.this);
/*  541 */       buildInfoForBatch(numBatch, apv);
/*      */       
/*  543 */       ParseInfo batchParseInfo = new ParseInfo(PreparedStatement.this, apv.getStaticSqlStrings(), this.firstStmtChar, this.foundLimitClause, this.foundLoadData, this.isOnDuplicateKeyUpdate, this.locationOfOnDuplicateKeyUpdate, this.statementLength, this.statementStartPos);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  549 */       return batchParseInfo;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     String getSqlForBatch(int numBatch)
/*      */       throws UnsupportedEncodingException
/*      */     {
/*  558 */       ParseInfo batchInfo = getParseInfoForBatch(numBatch);
/*      */       
/*  560 */       return getSqlForBatch(batchInfo);
/*      */     }
/*      */     
/*      */ 
/*      */     String getSqlForBatch(ParseInfo batchInfo)
/*      */       throws UnsupportedEncodingException
/*      */     {
/*  567 */       int size = 0;
/*  568 */       byte[][] sqlStrings = batchInfo.staticSql;
/*  569 */       int sqlStringsLength = sqlStrings.length;
/*      */       
/*  571 */       for (int i = 0; i < sqlStringsLength; i++) {
/*  572 */         size += sqlStrings[i].length;
/*  573 */         size++;
/*      */       }
/*      */       
/*  576 */       StringBuffer buf = new StringBuffer(size);
/*      */       
/*  578 */       for (int i = 0; i < sqlStringsLength - 1; i++) {
/*  579 */         buf.append(new String(sqlStrings[i], PreparedStatement.this.charEncoding));
/*  580 */         buf.append("?");
/*      */       }
/*      */       
/*  583 */       buf.append(new String(sqlStrings[(sqlStringsLength - 1)]));
/*      */       
/*  585 */       return buf.toString();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private void buildInfoForBatch(int numBatch, PreparedStatement.BatchVisitor visitor)
/*      */     {
/*  597 */       byte[][] headStaticSql = this.batchHead.staticSql;
/*  598 */       int headStaticSqlLength = headStaticSql.length;
/*      */       
/*  600 */       if (headStaticSqlLength > 1) {
/*  601 */         for (int i = 0; i < headStaticSqlLength - 1; i++) {
/*  602 */           visitor.append(headStaticSql[i]).increment();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  607 */       byte[] endOfHead = headStaticSql[(headStaticSqlLength - 1)];
/*  608 */       byte[][] valuesStaticSql = this.batchValues.staticSql;
/*  609 */       byte[] beginOfValues = valuesStaticSql[0];
/*      */       
/*  611 */       visitor.merge(endOfHead, beginOfValues).increment();
/*      */       
/*  613 */       int numValueRepeats = numBatch - 1;
/*      */       
/*  615 */       if (this.batchODKUClause != null) {
/*  616 */         numValueRepeats--;
/*      */       }
/*      */       
/*  619 */       int valuesStaticSqlLength = valuesStaticSql.length;
/*  620 */       byte[] endOfValues = valuesStaticSql[(valuesStaticSqlLength - 1)];
/*      */       
/*  622 */       for (int i = 0; i < numValueRepeats; i++) {
/*  623 */         for (int j = 1; j < valuesStaticSqlLength - 1; j++) {
/*  624 */           visitor.append(valuesStaticSql[j]).increment();
/*      */         }
/*  626 */         visitor.merge(endOfValues, beginOfValues).increment();
/*      */       }
/*      */       
/*  629 */       if (this.batchODKUClause != null) {
/*  630 */         byte[][] batchOdkuStaticSql = this.batchODKUClause.staticSql;
/*  631 */         byte[] beginOfOdku = batchOdkuStaticSql[0];
/*  632 */         visitor.decrement().merge(endOfValues, beginOfOdku).increment();
/*      */         
/*  634 */         int batchOdkuStaticSqlLength = batchOdkuStaticSql.length;
/*      */         
/*  636 */         if (numBatch > 1) {
/*  637 */           for (int i = 1; i < batchOdkuStaticSqlLength; i++) {
/*  638 */             visitor.append(batchOdkuStaticSql[i]).increment();
/*      */           }
/*      */           
/*      */         } else {
/*  642 */           visitor.decrement().append(batchOdkuStaticSql[(batchOdkuStaticSqlLength - 1)]);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  647 */         visitor.decrement().append(this.staticSql[(this.staticSql.length - 1)]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private ParseInfo(byte[][] staticSql, char firstStmtChar, boolean foundLimitClause, boolean foundLoadData, boolean isOnDuplicateKeyUpdate, int locationOfOnDuplicateKeyUpdate, int statementLength, int statementStartPos)
/*      */     {
/*  656 */       this.firstStmtChar = firstStmtChar;
/*  657 */       this.foundLimitClause = foundLimitClause;
/*  658 */       this.foundLoadData = foundLoadData;
/*  659 */       this.isOnDuplicateKeyUpdate = isOnDuplicateKeyUpdate;
/*  660 */       this.locationOfOnDuplicateKeyUpdate = locationOfOnDuplicateKeyUpdate;
/*  661 */       this.statementLength = statementLength;
/*  662 */       this.statementStartPos = statementStartPos;
/*  663 */       this.staticSql = staticSql;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract interface BatchVisitor { public abstract BatchVisitor increment();
/*      */     
/*      */     public abstract BatchVisitor decrement();
/*      */     
/*      */     public abstract BatchVisitor append(byte[] paramArrayOfByte);
/*      */     
/*      */     public abstract BatchVisitor merge(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
/*      */   }
/*      */   
/*      */   class AppendingBatchVisitor implements PreparedStatement.BatchVisitor { AppendingBatchVisitor() {}
/*      */     
/*  678 */     LinkedList statementComponents = new LinkedList();
/*      */     
/*      */     public PreparedStatement.BatchVisitor append(byte[] values) {
/*  681 */       this.statementComponents.addLast(values);
/*      */       
/*  683 */       return this;
/*      */     }
/*      */     
/*      */     public PreparedStatement.BatchVisitor increment()
/*      */     {
/*  688 */       return this;
/*      */     }
/*      */     
/*      */     public PreparedStatement.BatchVisitor decrement() {
/*  692 */       this.statementComponents.removeLast();
/*      */       
/*  694 */       return this;
/*      */     }
/*      */     
/*      */     public PreparedStatement.BatchVisitor merge(byte[] front, byte[] back) {
/*  698 */       int mergedLength = front.length + back.length;
/*  699 */       byte[] merged = new byte[mergedLength];
/*  700 */       System.arraycopy(front, 0, merged, 0, front.length);
/*  701 */       System.arraycopy(back, 0, merged, front.length, back.length);
/*  702 */       this.statementComponents.addLast(merged);
/*  703 */       return this;
/*      */     }
/*      */     
/*      */     public byte[][] getStaticSqlStrings() {
/*  707 */       byte[][] asBytes = new byte[this.statementComponents.size()][];
/*  708 */       this.statementComponents.toArray(asBytes);
/*      */       
/*  710 */       return asBytes;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  714 */       StringBuffer buf = new StringBuffer();
/*  715 */       Iterator iter = this.statementComponents.iterator();
/*  716 */       while (iter.hasNext()) {
/*  717 */         buf.append(new String((byte[])iter.next()));
/*      */       }
/*      */       
/*  720 */       return buf.toString();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*  725 */   private static final byte[] HEX_DIGITS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static int readFully(Reader reader, char[] buf, int length)
/*      */     throws IOException
/*      */   {
/*  748 */     int numCharsRead = 0;
/*      */     
/*  750 */     while (numCharsRead < length) {
/*  751 */       int count = reader.read(buf, numCharsRead, length - numCharsRead);
/*      */       
/*  753 */       if (count < 0) {
/*      */         break;
/*      */       }
/*      */       
/*  757 */       numCharsRead += count;
/*      */     }
/*      */     
/*  760 */     return numCharsRead;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  769 */   protected boolean batchHasPlainStatements = false;
/*      */   
/*  771 */   private DatabaseMetaData dbmd = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  777 */   protected char firstCharOfStmt = '\000';
/*      */   
/*      */ 
/*  780 */   protected boolean hasLimitClause = false;
/*      */   
/*      */ 
/*  783 */   protected boolean isLoadDataQuery = false;
/*      */   
/*  785 */   private boolean[] isNull = null;
/*      */   
/*  787 */   private boolean[] isStream = null;
/*      */   
/*  789 */   protected int numberOfExecutions = 0;
/*      */   
/*      */ 
/*  792 */   protected String originalSql = null;
/*      */   
/*      */ 
/*      */   protected int parameterCount;
/*      */   
/*      */   protected MysqlParameterMetadata parameterMetaData;
/*      */   
/*  799 */   private InputStream[] parameterStreams = null;
/*      */   
/*  801 */   private byte[][] parameterValues = (byte[][])null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  807 */   protected int[] parameterTypes = null;
/*      */   
/*      */   protected ParseInfo parseInfo;
/*      */   
/*      */   private java.sql.ResultSetMetaData pstmtResultMetaData;
/*      */   
/*  813 */   private byte[][] staticSqlStrings = (byte[][])null;
/*      */   
/*  815 */   private byte[] streamConvertBuf = new byte['က'];
/*      */   
/*  817 */   private int[] streamLengths = null;
/*      */   
/*  819 */   private SimpleDateFormat tsdf = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  824 */   protected boolean useTrueBoolean = false;
/*      */   
/*      */   protected boolean usingAnsiMode;
/*      */   
/*      */   protected String batchedValuesClause;
/*      */   
/*      */   private boolean doPingInstead;
/*      */   
/*      */   private SimpleDateFormat ddf;
/*      */   private SimpleDateFormat tdf;
/*  834 */   private boolean compensateForOnDuplicateKeyUpdate = false;
/*      */   
/*      */ 
/*      */   private CharsetEncoder charsetEncoder;
/*      */   
/*      */ 
/*  840 */   private int batchCommandIndex = -1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static PreparedStatement getInstance(MySQLConnection conn, String catalog)
/*      */     throws SQLException
/*      */   {
/*  851 */     if (!Util.isJdbc4()) {
/*  852 */       return new PreparedStatement(conn, catalog);
/*      */     }
/*      */     
/*  855 */     return (PreparedStatement)Util.handleNewInstance(JDBC_4_PSTMT_2_ARG_CTOR, new Object[] { conn, catalog }, conn.getExceptionInterceptor());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static PreparedStatement getInstance(MySQLConnection conn, String sql, String catalog)
/*      */     throws SQLException
/*      */   {
/*  868 */     if (!Util.isJdbc4()) {
/*  869 */       return new PreparedStatement(conn, sql, catalog);
/*      */     }
/*      */     
/*  872 */     return (PreparedStatement)Util.handleNewInstance(JDBC_4_PSTMT_3_ARG_CTOR, new Object[] { conn, sql, catalog }, conn.getExceptionInterceptor());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static PreparedStatement getInstance(MySQLConnection conn, String sql, String catalog, ParseInfo cachedParseInfo)
/*      */     throws SQLException
/*      */   {
/*  885 */     if (!Util.isJdbc4()) {
/*  886 */       return new PreparedStatement(conn, sql, catalog, cachedParseInfo);
/*      */     }
/*      */     
/*  889 */     return (PreparedStatement)Util.handleNewInstance(JDBC_4_PSTMT_4_ARG_CTOR, new Object[] { conn, sql, catalog, cachedParseInfo }, conn.getExceptionInterceptor());
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
/*      */   public PreparedStatement(MySQLConnection conn, String catalog)
/*      */     throws SQLException
/*      */   {
/*  907 */     super(conn, catalog);
/*      */     
/*  909 */     this.compensateForOnDuplicateKeyUpdate = this.connection.getCompensateOnDuplicateKeyUpdateCounts();
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
/*      */   public PreparedStatement(MySQLConnection conn, String sql, String catalog)
/*      */     throws SQLException
/*      */   {
/*  927 */     super(conn, catalog);
/*      */     
/*  929 */     if (sql == null) {
/*  930 */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.0"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*  934 */     this.originalSql = sql;
/*      */     
/*  936 */     if (this.originalSql.startsWith("/* ping */")) {
/*  937 */       this.doPingInstead = true;
/*      */     } else {
/*  939 */       this.doPingInstead = false;
/*      */     }
/*      */     
/*  942 */     this.dbmd = this.connection.getMetaData();
/*      */     
/*  944 */     this.useTrueBoolean = this.connection.versionMeetsMinimum(3, 21, 23);
/*      */     
/*  946 */     this.parseInfo = new ParseInfo(sql, this.connection, this.dbmd, this.charEncoding, this.charConverter);
/*      */     
/*      */ 
/*  949 */     initializeFromParseInfo();
/*      */     
/*  951 */     this.compensateForOnDuplicateKeyUpdate = this.connection.getCompensateOnDuplicateKeyUpdateCounts();
/*      */     
/*  953 */     if (conn.getRequiresEscapingEncoder()) {
/*  954 */       this.charsetEncoder = Charset.forName(conn.getEncoding()).newEncoder();
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
/*      */   public PreparedStatement(MySQLConnection conn, String sql, String catalog, ParseInfo cachedParseInfo)
/*      */     throws SQLException
/*      */   {
/*  974 */     super(conn, catalog);
/*      */     
/*  976 */     if (sql == null) {
/*  977 */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.1"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*  981 */     this.originalSql = sql;
/*      */     
/*  983 */     this.dbmd = this.connection.getMetaData();
/*      */     
/*  985 */     this.useTrueBoolean = this.connection.versionMeetsMinimum(3, 21, 23);
/*      */     
/*  987 */     this.parseInfo = cachedParseInfo;
/*      */     
/*  989 */     this.usingAnsiMode = (!this.connection.useAnsiQuotedIdentifiers());
/*      */     
/*  991 */     initializeFromParseInfo();
/*      */     
/*  993 */     this.compensateForOnDuplicateKeyUpdate = this.connection.getCompensateOnDuplicateKeyUpdateCounts();
/*      */     
/*  995 */     if (conn.getRequiresEscapingEncoder()) {
/*  996 */       this.charsetEncoder = Charset.forName(conn.getEncoding()).newEncoder();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addBatch()
/*      */     throws SQLException
/*      */   {
/* 1008 */     if (this.batchedArgs == null) {
/* 1009 */       this.batchedArgs = new ArrayList();
/*      */     }
/*      */     
/* 1012 */     for (int i = 0; i < this.parameterValues.length; i++) {
/* 1013 */       checkAllParametersSet(this.parameterValues[i], this.parameterStreams[i], i);
/*      */     }
/*      */     
/*      */ 
/* 1017 */     this.batchedArgs.add(new BatchParams(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull));
/*      */   }
/*      */   
/*      */   public synchronized void addBatch(String sql)
/*      */     throws SQLException
/*      */   {
/* 1023 */     this.batchHasPlainStatements = true;
/*      */     
/* 1025 */     super.addBatch(sql);
/*      */   }
/*      */   
/*      */   protected String asSql() throws SQLException {
/* 1029 */     return asSql(false);
/*      */   }
/*      */   
/*      */   protected String asSql(boolean quoteStreamsAndUnknowns) throws SQLException {
/* 1033 */     if (this.isClosed) {
/* 1034 */       return "statement has been closed, no further internal information available";
/*      */     }
/*      */     
/* 1037 */     StringBuffer buf = new StringBuffer();
/*      */     try
/*      */     {
/* 1040 */       int realParameterCount = this.parameterCount + getParameterIndexOffset();
/* 1041 */       Object batchArg = null;
/* 1042 */       if (this.batchCommandIndex != -1) {
/* 1043 */         batchArg = this.batchedArgs.get(this.batchCommandIndex);
/*      */       }
/* 1045 */       for (int i = 0; i < realParameterCount; i++) {
/* 1046 */         if (this.charEncoding != null) {
/* 1047 */           buf.append(new String(this.staticSqlStrings[i], this.charEncoding));
/*      */         }
/*      */         else {
/* 1050 */           buf.append(new String(this.staticSqlStrings[i]));
/*      */         }
/*      */         
/* 1053 */         byte[] val = null;
/* 1054 */         if ((batchArg != null) && ((batchArg instanceof String))) {
/* 1055 */           buf.append((String)batchArg);
/*      */         }
/*      */         else {
/* 1058 */           if (this.batchCommandIndex == -1) {
/* 1059 */             val = this.parameterValues[i];
/*      */           } else {
/* 1061 */             val = ((BatchParams)batchArg).parameterStrings[i];
/*      */           }
/* 1063 */           boolean isStreamParam = false;
/* 1064 */           if (this.batchCommandIndex == -1) {
/* 1065 */             isStreamParam = this.isStream[i];
/*      */           } else {
/* 1067 */             isStreamParam = ((BatchParams)batchArg).isStream[i];
/*      */           }
/* 1069 */           if ((val == null) && (!isStreamParam)) {
/* 1070 */             if (quoteStreamsAndUnknowns) {
/* 1071 */               buf.append("'");
/*      */             }
/*      */             
/* 1074 */             buf.append("** NOT SPECIFIED **");
/*      */             
/* 1076 */             if (quoteStreamsAndUnknowns) {
/* 1077 */               buf.append("'");
/*      */             }
/* 1079 */           } else if (isStreamParam) {
/* 1080 */             if (quoteStreamsAndUnknowns) {
/* 1081 */               buf.append("'");
/*      */             }
/*      */             
/* 1084 */             buf.append("** STREAM DATA **");
/*      */             
/* 1086 */             if (quoteStreamsAndUnknowns) {
/* 1087 */               buf.append("'");
/*      */             }
/*      */           }
/* 1090 */           else if (this.charConverter != null) {
/* 1091 */             buf.append(this.charConverter.toString(val));
/*      */           }
/* 1093 */           else if (this.charEncoding != null) {
/* 1094 */             buf.append(new String(val, this.charEncoding));
/*      */           } else {
/* 1096 */             buf.append(StringUtils.toAsciiString(val));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1102 */       if (this.charEncoding != null) {
/* 1103 */         buf.append(new String(this.staticSqlStrings[(this.parameterCount + getParameterIndexOffset())], this.charEncoding));
/*      */       }
/*      */       else
/*      */       {
/* 1107 */         buf.append(StringUtils.toAsciiString(this.staticSqlStrings[(this.parameterCount + getParameterIndexOffset())]));
/*      */       }
/*      */     }
/*      */     catch (UnsupportedEncodingException uue)
/*      */     {
/* 1112 */       throw new RuntimeException(Messages.getString("PreparedStatement.32") + this.charEncoding + Messages.getString("PreparedStatement.33"));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1118 */     return buf.toString();
/*      */   }
/*      */   
/*      */   public synchronized void clearBatch() throws SQLException {
/* 1122 */     this.batchHasPlainStatements = false;
/*      */     
/* 1124 */     super.clearBatch();
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
/*      */   public synchronized void clearParameters()
/*      */     throws SQLException
/*      */   {
/* 1138 */     checkClosed();
/*      */     
/* 1140 */     for (int i = 0; i < this.parameterValues.length; i++) {
/* 1141 */       this.parameterValues[i] = null;
/* 1142 */       this.parameterStreams[i] = null;
/* 1143 */       this.isStream[i] = false;
/* 1144 */       this.isNull[i] = false;
/* 1145 */       this.parameterTypes[i] = 0;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void close()
/*      */     throws SQLException
/*      */   {
/* 1156 */     realClose(true, true);
/*      */   }
/*      */   
/*      */   private final void escapeblockFast(byte[] buf, Buffer packet, int size) throws SQLException
/*      */   {
/* 1161 */     int lastwritten = 0;
/*      */     
/* 1163 */     for (int i = 0; i < size; i++) {
/* 1164 */       byte b = buf[i];
/*      */       
/* 1166 */       if (b == 0)
/*      */       {
/* 1168 */         if (i > lastwritten) {
/* 1169 */           packet.writeBytesNoNull(buf, lastwritten, i - lastwritten);
/*      */         }
/*      */         
/*      */ 
/* 1173 */         packet.writeByte((byte)92);
/* 1174 */         packet.writeByte((byte)48);
/* 1175 */         lastwritten = i + 1;
/*      */       }
/* 1177 */       else if ((b == 92) || (b == 39) || ((!this.usingAnsiMode) && (b == 34)))
/*      */       {
/*      */ 
/* 1180 */         if (i > lastwritten) {
/* 1181 */           packet.writeBytesNoNull(buf, lastwritten, i - lastwritten);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1186 */         packet.writeByte((byte)92);
/* 1187 */         lastwritten = i;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1193 */     if (lastwritten < size) {
/* 1194 */       packet.writeBytesNoNull(buf, lastwritten, size - lastwritten);
/*      */     }
/*      */   }
/*      */   
/*      */   private final void escapeblockFast(byte[] buf, ByteArrayOutputStream bytesOut, int size)
/*      */   {
/* 1200 */     int lastwritten = 0;
/*      */     
/* 1202 */     for (int i = 0; i < size; i++) {
/* 1203 */       byte b = buf[i];
/*      */       
/* 1205 */       if (b == 0)
/*      */       {
/* 1207 */         if (i > lastwritten) {
/* 1208 */           bytesOut.write(buf, lastwritten, i - lastwritten);
/*      */         }
/*      */         
/*      */ 
/* 1212 */         bytesOut.write(92);
/* 1213 */         bytesOut.write(48);
/* 1214 */         lastwritten = i + 1;
/*      */       }
/* 1216 */       else if ((b == 92) || (b == 39) || ((!this.usingAnsiMode) && (b == 34)))
/*      */       {
/*      */ 
/* 1219 */         if (i > lastwritten) {
/* 1220 */           bytesOut.write(buf, lastwritten, i - lastwritten);
/*      */         }
/*      */         
/*      */ 
/* 1224 */         bytesOut.write(92);
/* 1225 */         lastwritten = i;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1231 */     if (lastwritten < size) {
/* 1232 */       bytesOut.write(buf, lastwritten, size - lastwritten);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean checkReadOnlySafeStatement()
/*      */     throws SQLException
/*      */   {
/* 1243 */     return (!this.connection.isReadOnly()) || (this.firstCharOfStmt == 'S');
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
/*      */   public boolean execute()
/*      */     throws SQLException
/*      */   {
/* 1258 */     checkClosed();
/*      */     
/* 1260 */     MySQLConnection locallyScopedConn = this.connection;
/*      */     
/* 1262 */     if (!checkReadOnlySafeStatement()) {
/* 1263 */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.20") + Messages.getString("PreparedStatement.21"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1268 */     ResultSetInternalMethods rs = null;
/*      */     
/* 1270 */     CachedResultSetMetaData cachedMetadata = null;
/*      */     
/* 1272 */     synchronized (locallyScopedConn.getMutex()) {
/* 1273 */       this.lastQueryIsOnDupKeyUpdate = false;
/* 1274 */       if (this.retrieveGeneratedKeys)
/* 1275 */         this.lastQueryIsOnDupKeyUpdate = containsOnDuplicateKeyUpdateInSQL();
/* 1276 */       boolean doStreaming = createStreamingResultSet();
/*      */       
/* 1278 */       clearWarnings();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1288 */       if ((doStreaming) && (this.connection.getNetTimeoutForStreamingResults() > 0))
/*      */       {
/* 1290 */         executeSimpleNonQuery(locallyScopedConn, "SET net_write_timeout=" + this.connection.getNetTimeoutForStreamingResults());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1296 */       this.batchedGeneratedKeys = null;
/*      */       
/* 1298 */       Buffer sendPacket = fillSendPacket();
/*      */       
/* 1300 */       String oldCatalog = null;
/*      */       
/* 1302 */       if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
/* 1303 */         oldCatalog = locallyScopedConn.getCatalog();
/* 1304 */         locallyScopedConn.setCatalog(this.currentCatalog);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1310 */       if (locallyScopedConn.getCacheResultSetMetadata()) {
/* 1311 */         cachedMetadata = locallyScopedConn.getCachedMetaData(this.originalSql);
/*      */       }
/*      */       
/* 1314 */       Field[] metadataFromCache = null;
/*      */       
/* 1316 */       if (cachedMetadata != null) {
/* 1317 */         metadataFromCache = cachedMetadata.fields;
/*      */       }
/*      */       
/* 1320 */       boolean oldInfoMsgState = false;
/*      */       
/* 1322 */       if (this.retrieveGeneratedKeys) {
/* 1323 */         oldInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
/* 1324 */         locallyScopedConn.setReadInfoMsgEnabled(true);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1336 */       if (locallyScopedConn.useMaxRows()) {
/* 1337 */         int rowLimit = -1;
/*      */         
/* 1339 */         if (this.firstCharOfStmt == 'S') {
/* 1340 */           if (this.hasLimitClause) {
/* 1341 */             rowLimit = this.maxRows;
/*      */           }
/* 1343 */           else if (this.maxRows <= 0) {
/* 1344 */             executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");
/*      */           }
/*      */           else {
/* 1347 */             executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows);
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         else {
/* 1353 */           executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1358 */         rs = executeInternal(rowLimit, sendPacket, doStreaming, this.firstCharOfStmt == 'S', metadataFromCache, false);
/*      */       }
/*      */       else
/*      */       {
/* 1362 */         rs = executeInternal(-1, sendPacket, doStreaming, this.firstCharOfStmt == 'S', metadataFromCache, false);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1367 */       if (cachedMetadata != null) {
/* 1368 */         locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, cachedMetadata, this.results);
/*      */ 
/*      */       }
/* 1371 */       else if ((rs.reallyResult()) && (locallyScopedConn.getCacheResultSetMetadata())) {
/* 1372 */         locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, null, rs);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1377 */       if (this.retrieveGeneratedKeys) {
/* 1378 */         locallyScopedConn.setReadInfoMsgEnabled(oldInfoMsgState);
/* 1379 */         rs.setFirstCharOfQuery(this.firstCharOfStmt);
/*      */       }
/*      */       
/* 1382 */       if (oldCatalog != null) {
/* 1383 */         locallyScopedConn.setCatalog(oldCatalog);
/*      */       }
/*      */       
/* 1386 */       if (rs != null) {
/* 1387 */         this.lastInsertId = rs.getUpdateID();
/*      */         
/* 1389 */         this.results = rs;
/*      */       }
/*      */     }
/*      */     
/* 1393 */     return (rs != null) && (rs.reallyResult());
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
/*      */   public int[] executeBatch()
/*      */     throws SQLException
/*      */   {
/* 1411 */     checkClosed();
/*      */     
/* 1413 */     if (this.connection.isReadOnly()) {
/* 1414 */       throw new SQLException(Messages.getString("PreparedStatement.25") + Messages.getString("PreparedStatement.26"), "S1009");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1419 */     synchronized (this.connection.getMutex()) {
/* 1420 */       if ((this.batchedArgs == null) || (this.batchedArgs.size() == 0)) {
/* 1421 */         return new int[0];
/*      */       }
/*      */       
/*      */ 
/* 1425 */       int batchTimeout = this.timeoutInMillis;
/* 1426 */       this.timeoutInMillis = 0;
/*      */       
/* 1428 */       resetCancelledState();
/*      */       try
/*      */       {
/* 1431 */         clearWarnings();
/*      */         
/* 1433 */         if ((!this.batchHasPlainStatements) && (this.connection.getRewriteBatchedStatements()))
/*      */         {
/*      */ 
/*      */ 
/* 1437 */           if (canRewriteAsMultiValueInsertAtSqlLevel()) {
/* 1438 */             arrayOfInt = executeBatchedInserts(batchTimeout);jsr 83;return arrayOfInt;
/*      */           }
/*      */           
/* 1441 */           if ((this.connection.versionMeetsMinimum(4, 1, 0)) && (!this.batchHasPlainStatements) && (this.batchedArgs != null) && (this.batchedArgs.size() > 3))
/*      */           {
/*      */ 
/*      */ 
/* 1445 */             arrayOfInt = executePreparedBatchAsMultiStatement(batchTimeout);jsr 28;return arrayOfInt;
/*      */           }
/*      */         }
/*      */         
/* 1449 */         int[] arrayOfInt = executeBatchSerially(batchTimeout);jsr 15;return arrayOfInt;
/*      */       } finally {
/* 1451 */         jsr 6; } localObject2 = returnAddress;clearBatch();ret;
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean canRewriteAsMultiValueInsertAtSqlLevel() throws SQLException
/*      */   {
/* 1457 */     return this.parseInfo.canRewriteAsMultiValueInsert;
/*      */   }
/*      */   
/*      */   protected int getLocationOfOnDuplicateKeyUpdate() {
/* 1461 */     return this.parseInfo.locationOfOnDuplicateKeyUpdate;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String generateMultiStatementForBatch(int numBatches)
/*      */   {
/* 1662 */     StringBuffer newStatementSql = new StringBuffer((this.originalSql.length() + 1) * numBatches);
/*      */     
/*      */ 
/* 1665 */     newStatementSql.append(this.originalSql);
/*      */     
/* 1667 */     for (int i = 0; i < numBatches - 1; i++) {
/* 1668 */       newStatementSql.append(';');
/* 1669 */       newStatementSql.append(this.originalSql);
/*      */     }
/*      */     
/* 1672 */     return newStatementSql.toString();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getValuesClause()
/*      */     throws SQLException
/*      */   {
/* 1832 */     return this.parseInfo.valuesClause;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int computeBatchSize(int numBatchedArgs)
/*      */     throws SQLException
/*      */   {
/* 1844 */     long[] combinedValues = computeMaxParameterSetSizeAndBatchSize(numBatchedArgs);
/*      */     
/* 1846 */     long maxSizeOfParameterSet = combinedValues[0];
/* 1847 */     long sizeOfEntireBatch = combinedValues[1];
/*      */     
/* 1849 */     int maxAllowedPacket = this.connection.getMaxAllowedPacket();
/*      */     
/* 1851 */     if (sizeOfEntireBatch < maxAllowedPacket - this.originalSql.length()) {
/* 1852 */       return numBatchedArgs;
/*      */     }
/*      */     
/* 1855 */     return (int)Math.max(1L, (maxAllowedPacket - this.originalSql.length()) / maxSizeOfParameterSet);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected long[] computeMaxParameterSetSizeAndBatchSize(int numBatchedArgs)
/*      */     throws SQLException
/*      */   {
/* 1864 */     long sizeOfEntireBatch = 0L;
/* 1865 */     long maxSizeOfParameterSet = 0L;
/*      */     
/* 1867 */     for (int i = 0; i < numBatchedArgs; i++) {
/* 1868 */       BatchParams paramArg = (BatchParams)this.batchedArgs.get(i);
/*      */       
/*      */ 
/* 1871 */       boolean[] isNullBatch = paramArg.isNull;
/* 1872 */       boolean[] isStreamBatch = paramArg.isStream;
/*      */       
/* 1874 */       long sizeOfParameterSet = 0L;
/*      */       
/* 1876 */       for (int j = 0; j < isNullBatch.length; j++) {
/* 1877 */         if (isNullBatch[j] == 0)
/*      */         {
/* 1879 */           if (isStreamBatch[j] != 0) {
/* 1880 */             int streamLength = paramArg.streamLengths[j];
/*      */             
/* 1882 */             if (streamLength != -1) {
/* 1883 */               sizeOfParameterSet += streamLength * 2;
/*      */             } else {
/* 1885 */               int paramLength = paramArg.parameterStrings[j].length;
/* 1886 */               sizeOfParameterSet += paramLength;
/*      */             }
/*      */           } else {
/* 1889 */             sizeOfParameterSet += paramArg.parameterStrings[j].length;
/*      */           }
/*      */         } else {
/* 1892 */           sizeOfParameterSet += 4L;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1904 */       if (getValuesClause() != null) {
/* 1905 */         sizeOfParameterSet += getValuesClause().length() + 1;
/*      */       } else {
/* 1907 */         sizeOfParameterSet += this.originalSql.length() + 1;
/*      */       }
/*      */       
/* 1910 */       sizeOfEntireBatch += sizeOfParameterSet;
/*      */       
/* 1912 */       if (sizeOfParameterSet > maxSizeOfParameterSet) {
/* 1913 */         maxSizeOfParameterSet = sizeOfParameterSet;
/*      */       }
/*      */     }
/*      */     
/* 1917 */     return new long[] { maxSizeOfParameterSet, sizeOfEntireBatch };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int[] executeBatchSerially(int batchTimeout)
/*      */     throws SQLException
/*      */   {
/* 1930 */     MySQLConnection locallyScopedConn = this.connection;
/*      */     
/* 1932 */     if (locallyScopedConn == null) {
/* 1933 */       checkClosed();
/*      */     }
/*      */     
/* 1936 */     int[] updateCounts = null;
/*      */     
/* 1938 */     if (this.batchedArgs != null) {
/* 1939 */       int nbrCommands = this.batchedArgs.size();
/* 1940 */       updateCounts = new int[nbrCommands];
/*      */       
/* 1942 */       for (int i = 0; i < nbrCommands; i++) {
/* 1943 */         updateCounts[i] = -3;
/*      */       }
/*      */       
/* 1946 */       SQLException sqlEx = null;
/*      */       
/* 1948 */       StatementImpl.CancelTask timeoutTask = null;
/*      */       try
/*      */       {
/* 1951 */         if ((locallyScopedConn.getEnableQueryTimeouts()) && (batchTimeout != 0) && (locallyScopedConn.versionMeetsMinimum(5, 0, 0)))
/*      */         {
/*      */ 
/* 1954 */           timeoutTask = new StatementImpl.CancelTask(this, this);
/* 1955 */           locallyScopedConn.getCancelTimer().schedule(timeoutTask, batchTimeout);
/*      */         }
/*      */         
/*      */ 
/* 1959 */         if (this.retrieveGeneratedKeys) {
/* 1960 */           this.batchedGeneratedKeys = new ArrayList(nbrCommands);
/*      */         }
/*      */         
/* 1963 */         for (this.batchCommandIndex = 0; this.batchCommandIndex < nbrCommands; this.batchCommandIndex += 1) {
/* 1964 */           Object arg = this.batchedArgs.get(this.batchCommandIndex);
/*      */           
/* 1966 */           if ((arg instanceof String)) {
/* 1967 */             updateCounts[this.batchCommandIndex] = executeUpdate((String)arg);
/*      */           } else {
/* 1969 */             BatchParams paramArg = (BatchParams)arg;
/*      */             try
/*      */             {
/* 1972 */               updateCounts[this.batchCommandIndex] = executeUpdate(paramArg.parameterStrings, paramArg.parameterStreams, paramArg.isStream, paramArg.streamLengths, paramArg.isNull, true);
/*      */               
/*      */ 
/*      */ 
/*      */ 
/* 1977 */               if (this.retrieveGeneratedKeys) {
/* 1978 */                 ResultSet rs = null;
/*      */                 try
/*      */                 {
/* 1981 */                   if (containsOnDuplicateKeyUpdateInSQL()) {
/* 1982 */                     rs = getGeneratedKeysInternal(1);
/*      */                   } else {
/* 1984 */                     rs = getGeneratedKeysInternal();
/*      */                   }
/* 1986 */                   while (rs.next()) {
/* 1987 */                     this.batchedGeneratedKeys.add(new ByteArrayRow(new byte[][] { rs.getBytes(1) }, getExceptionInterceptor()));
/*      */                   }
/*      */                 }
/*      */                 finally {
/* 1991 */                   if (rs != null) {
/* 1992 */                     rs.close();
/*      */                   }
/*      */                 }
/*      */               }
/*      */             } catch (SQLException ex) {
/* 1997 */               updateCounts[this.batchCommandIndex] = -3;
/*      */               
/* 1999 */               if ((this.continueBatchOnError) && (!(ex instanceof MySQLTimeoutException)) && (!(ex instanceof MySQLStatementCancelledException)) && (!hasDeadlockOrTimeoutRolledBackTx(ex)))
/*      */               {
/*      */ 
/*      */ 
/* 2003 */                 sqlEx = ex;
/*      */               } else {
/* 2005 */                 int[] newUpdateCounts = new int[this.batchCommandIndex];
/* 2006 */                 System.arraycopy(updateCounts, 0, newUpdateCounts, 0, this.batchCommandIndex);
/*      */                 
/*      */ 
/* 2009 */                 throw new BatchUpdateException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), newUpdateCounts);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2017 */         if (sqlEx != null) {
/* 2018 */           throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);
/*      */         }
/*      */       }
/*      */       catch (NullPointerException npe) {
/*      */         try {
/* 2023 */           checkClosed();
/*      */         } catch (SQLException connectionClosedEx) {
/* 2025 */           updateCounts[this.batchCommandIndex] = -3;
/*      */           
/* 2027 */           int[] newUpdateCounts = new int[this.batchCommandIndex];
/*      */           
/* 2029 */           System.arraycopy(updateCounts, 0, newUpdateCounts, 0, this.batchCommandIndex);
/*      */           
/*      */ 
/* 2032 */           throw new BatchUpdateException(connectionClosedEx.getMessage(), connectionClosedEx.getSQLState(), connectionClosedEx.getErrorCode(), newUpdateCounts);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2037 */         throw npe;
/*      */       } finally {
/* 2039 */         this.batchCommandIndex = -1;
/*      */         
/* 2041 */         if (timeoutTask != null) {
/* 2042 */           timeoutTask.cancel();
/* 2043 */           locallyScopedConn.getCancelTimer().purge();
/*      */         }
/*      */         
/* 2046 */         resetCancelledState();
/*      */       }
/*      */     }
/*      */     
/* 2050 */     return updateCounts != null ? updateCounts : new int[0];
/*      */   }
/*      */   
/*      */   public String getDateTime(String pattern)
/*      */   {
/* 2055 */     SimpleDateFormat sdf = new SimpleDateFormat(pattern);
/* 2056 */     return sdf.format(new java.util.Date());
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
/*      */   protected ResultSetInternalMethods executeInternal(int maxRowsToRetrieve, Buffer sendPacket, boolean createStreamingResultSet, boolean queryIsSelectOnly, Field[] metadataFromCache, boolean isBatch)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 2086 */       resetCancelledState();
/*      */       
/* 2088 */       MySQLConnection locallyScopedConnection = this.connection;
/*      */       
/* 2090 */       this.numberOfExecutions += 1;
/*      */       
/* 2092 */       if (this.doPingInstead) {
/* 2093 */         doPingInstead();
/*      */         
/* 2095 */         return this.results;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2100 */       StatementImpl.CancelTask timeoutTask = null;
/*      */       ResultSetInternalMethods rs;
/*      */       try {
/* 2103 */         if ((locallyScopedConnection.getEnableQueryTimeouts()) && (this.timeoutInMillis != 0) && (locallyScopedConnection.versionMeetsMinimum(5, 0, 0)))
/*      */         {
/*      */ 
/* 2106 */           timeoutTask = new StatementImpl.CancelTask(this, this);
/* 2107 */           locallyScopedConnection.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis);
/*      */         }
/*      */         
/*      */ 
/* 2111 */         rs = locallyScopedConnection.execSQL(this, null, maxRowsToRetrieve, sendPacket, this.resultSetType, this.resultSetConcurrency, createStreamingResultSet, this.currentCatalog, metadataFromCache, isBatch);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2116 */         if (timeoutTask != null) {
/* 2117 */           timeoutTask.cancel();
/*      */           
/* 2119 */           locallyScopedConnection.getCancelTimer().purge();
/*      */           
/* 2121 */           if (timeoutTask.caughtWhileCancelling != null) {
/* 2122 */             throw timeoutTask.caughtWhileCancelling;
/*      */           }
/*      */           
/* 2125 */           timeoutTask = null;
/*      */         }
/*      */         
/* 2128 */         synchronized (this.cancelTimeoutMutex) {
/* 2129 */           if (this.wasCancelled) {
/* 2130 */             SQLException cause = null;
/*      */             
/* 2132 */             if (this.wasCancelledByTimeout) {
/* 2133 */               cause = new MySQLTimeoutException();
/*      */             } else {
/* 2135 */               cause = new MySQLStatementCancelledException();
/*      */             }
/*      */             
/* 2138 */             resetCancelledState();
/*      */             
/* 2140 */             throw cause;
/*      */           }
/*      */         }
/*      */       } finally {
/* 2144 */         if (timeoutTask != null) {
/* 2145 */           timeoutTask.cancel();
/* 2146 */           locallyScopedConnection.getCancelTimer().purge();
/*      */         }
/*      */       }
/*      */       
/* 2150 */       return rs;
/*      */     } catch (NullPointerException npe) {
/* 2152 */       checkClosed();
/*      */       
/*      */ 
/*      */ 
/* 2156 */       throw npe;
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
/*      */   public ResultSet executeQuery()
/*      */     throws SQLException
/*      */   {
/* 2170 */     checkClosed();
/*      */     
/* 2172 */     MySQLConnection locallyScopedConn = this.connection;
/*      */     
/* 2174 */     checkForDml(this.originalSql, this.firstCharOfStmt);
/*      */     
/* 2176 */     CachedResultSetMetaData cachedMetadata = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2182 */     synchronized (locallyScopedConn.getMutex()) {
/* 2183 */       clearWarnings();
/*      */       
/* 2185 */       boolean doStreaming = createStreamingResultSet();
/*      */       
/* 2187 */       this.batchedGeneratedKeys = null;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2197 */       if ((doStreaming) && (this.connection.getNetTimeoutForStreamingResults() > 0))
/*      */       {
/*      */ 
/* 2200 */         Statement stmt = null;
/*      */         try
/*      */         {
/* 2203 */           stmt = this.connection.createStatement();
/*      */           
/* 2205 */           ((StatementImpl)stmt).executeSimpleNonQuery(this.connection, "SET net_write_timeout=" + this.connection.getNetTimeoutForStreamingResults());
/*      */         }
/*      */         finally {
/* 2208 */           if (stmt != null) {
/* 2209 */             stmt.close();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2214 */       Buffer sendPacket = fillSendPacket();
/*      */       
/* 2216 */       if ((this.results != null) && 
/* 2217 */         (!this.connection.getHoldResultsOpenOverStatementClose()) && 
/* 2218 */         (!this.holdResultsOpenOverClose)) {
/* 2219 */         this.results.realClose(false);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2224 */       String oldCatalog = null;
/*      */       
/* 2226 */       if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
/* 2227 */         oldCatalog = locallyScopedConn.getCatalog();
/* 2228 */         locallyScopedConn.setCatalog(this.currentCatalog);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2234 */       if (locallyScopedConn.getCacheResultSetMetadata()) {
/* 2235 */         cachedMetadata = locallyScopedConn.getCachedMetaData(this.originalSql);
/*      */       }
/*      */       
/* 2238 */       Field[] metadataFromCache = null;
/*      */       
/* 2240 */       if (cachedMetadata != null) {
/* 2241 */         metadataFromCache = cachedMetadata.fields;
/*      */       }
/*      */       
/* 2244 */       if (locallyScopedConn.useMaxRows())
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2251 */         if (this.hasLimitClause) {
/* 2252 */           this.results = executeInternal(this.maxRows, sendPacket, createStreamingResultSet(), true, metadataFromCache, false);
/*      */         }
/*      */         else
/*      */         {
/* 2256 */           if (this.maxRows <= 0) {
/* 2257 */             executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");
/*      */           }
/*      */           else {
/* 2260 */             executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows);
/*      */           }
/*      */           
/*      */ 
/* 2264 */           this.results = executeInternal(-1, sendPacket, doStreaming, true, metadataFromCache, false);
/*      */           
/*      */ 
/*      */ 
/* 2268 */           if (oldCatalog != null) {
/* 2269 */             this.connection.setCatalog(oldCatalog);
/*      */           }
/*      */         }
/*      */       } else {
/* 2273 */         this.results = executeInternal(-1, sendPacket, doStreaming, true, metadataFromCache, false);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2278 */       if (oldCatalog != null) {
/* 2279 */         locallyScopedConn.setCatalog(oldCatalog);
/*      */       }
/*      */       
/* 2282 */       if (cachedMetadata != null) {
/* 2283 */         locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, cachedMetadata, this.results);
/*      */ 
/*      */       }
/* 2286 */       else if (locallyScopedConn.getCacheResultSetMetadata()) {
/* 2287 */         locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, null, this.results);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2293 */     this.lastInsertId = this.results.getUpdateID();
/*      */     
/* 2295 */     return this.results;
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
/*      */   public int executeUpdate()
/*      */     throws SQLException
/*      */   {
/* 2310 */     return executeUpdate(true, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int executeUpdate(boolean clearBatchedGeneratedKeysAndWarnings, boolean isBatch)
/*      */     throws SQLException
/*      */   {
/* 2320 */     if (clearBatchedGeneratedKeysAndWarnings) {
/* 2321 */       clearWarnings();
/* 2322 */       this.batchedGeneratedKeys = null;
/*      */     }
/*      */     
/* 2325 */     return executeUpdate(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull, isBatch);
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
/*      */   protected int executeUpdate(byte[][] batchedParameterStrings, InputStream[] batchedParameterStreams, boolean[] batchedIsStream, int[] batchedStreamLengths, boolean[] batchedIsNull, boolean isReallyBatch)
/*      */     throws SQLException
/*      */   {
/* 2353 */     checkClosed();
/*      */     
/* 2355 */     MySQLConnection locallyScopedConn = this.connection;
/*      */     
/* 2357 */     if (locallyScopedConn.isReadOnly()) {
/* 2358 */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.34") + Messages.getString("PreparedStatement.35"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2363 */     if ((this.firstCharOfStmt == 'S') && (isSelectQuery()))
/*      */     {
/* 2365 */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.37"), "01S03", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/* 2369 */     if ((this.results != null) && 
/* 2370 */       (!locallyScopedConn.getHoldResultsOpenOverStatementClose())) {
/* 2371 */       this.results.realClose(false);
/*      */     }
/*      */     
/*      */ 
/* 2375 */     ResultSetInternalMethods rs = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2380 */     synchronized (locallyScopedConn.getMutex()) {
/* 2381 */       Buffer sendPacket = fillSendPacket(batchedParameterStrings, batchedParameterStreams, batchedIsStream, batchedStreamLengths);
/*      */       
/*      */ 
/*      */ 
/* 2385 */       String oldCatalog = null;
/*      */       
/* 2387 */       if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
/* 2388 */         oldCatalog = locallyScopedConn.getCatalog();
/* 2389 */         locallyScopedConn.setCatalog(this.currentCatalog);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2395 */       if (locallyScopedConn.useMaxRows()) {
/* 2396 */         executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");
/*      */       }
/*      */       
/*      */ 
/* 2400 */       boolean oldInfoMsgState = false;
/*      */       
/* 2402 */       if (this.retrieveGeneratedKeys) {
/* 2403 */         oldInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
/* 2404 */         locallyScopedConn.setReadInfoMsgEnabled(true);
/*      */       }
/*      */       
/* 2407 */       rs = executeInternal(-1, sendPacket, false, false, null, isReallyBatch);
/*      */       
/*      */ 
/* 2410 */       if (this.retrieveGeneratedKeys) {
/* 2411 */         locallyScopedConn.setReadInfoMsgEnabled(oldInfoMsgState);
/* 2412 */         rs.setFirstCharOfQuery(this.firstCharOfStmt);
/*      */       }
/*      */       
/* 2415 */       if (oldCatalog != null) {
/* 2416 */         locallyScopedConn.setCatalog(oldCatalog);
/*      */       }
/*      */     }
/*      */     
/* 2420 */     this.results = rs;
/*      */     
/* 2422 */     this.updateCount = rs.getUpdateCount();
/*      */     
/* 2424 */     if ((containsOnDuplicateKeyUpdateInSQL()) && (this.compensateForOnDuplicateKeyUpdate))
/*      */     {
/* 2426 */       if ((this.updateCount == 2L) || (this.updateCount == 0L)) {
/* 2427 */         this.updateCount = 1L;
/*      */       }
/*      */     }
/*      */     
/* 2431 */     int truncatedUpdateCount = 0;
/*      */     
/* 2433 */     if (this.updateCount > 2147483647L) {
/* 2434 */       truncatedUpdateCount = Integer.MAX_VALUE;
/*      */     } else {
/* 2436 */       truncatedUpdateCount = (int)this.updateCount;
/*      */     }
/*      */     
/* 2439 */     this.lastInsertId = rs.getUpdateID();
/*      */     
/* 2441 */     return truncatedUpdateCount;
/*      */   }
/*      */   
/*      */   protected boolean containsOnDuplicateKeyUpdateInSQL() {
/* 2445 */     return this.parseInfo.isOnDuplicateKeyUpdate;
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
/*      */   protected Buffer fillSendPacket()
/*      */     throws SQLException
/*      */   {
/* 2460 */     return fillSendPacket(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths);
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
/*      */   protected Buffer fillSendPacket(byte[][] batchedParameterStrings, InputStream[] batchedParameterStreams, boolean[] batchedIsStream, int[] batchedStreamLengths)
/*      */     throws SQLException
/*      */   {
/* 2484 */     Buffer sendPacket = this.connection.getIO().getSharedSendPacket();
/*      */     
/* 2486 */     sendPacket.clear();
/*      */     
/* 2488 */     sendPacket.writeByte((byte)3);
/*      */     
/* 2490 */     boolean useStreamLengths = this.connection.getUseStreamLengthsInPrepStmts();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2497 */     int ensurePacketSize = 0;
/*      */     
/* 2499 */     String statementComment = this.connection.getStatementComment();
/*      */     
/* 2501 */     byte[] commentAsBytes = null;
/*      */     
/* 2503 */     if (statementComment != null) {
/* 2504 */       if (this.charConverter != null) {
/* 2505 */         commentAsBytes = this.charConverter.toBytes(statementComment);
/*      */       } else {
/* 2507 */         commentAsBytes = StringUtils.getBytes(statementComment, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2513 */       ensurePacketSize += commentAsBytes.length;
/* 2514 */       ensurePacketSize += 6;
/*      */     }
/*      */     
/* 2517 */     for (int i = 0; i < batchedParameterStrings.length; i++) {
/* 2518 */       if ((batchedIsStream[i] != 0) && (useStreamLengths)) {
/* 2519 */         ensurePacketSize += batchedStreamLengths[i];
/*      */       }
/*      */     }
/*      */     
/* 2523 */     if (ensurePacketSize != 0) {
/* 2524 */       sendPacket.ensureCapacity(ensurePacketSize);
/*      */     }
/*      */     
/* 2527 */     if (commentAsBytes != null) {
/* 2528 */       sendPacket.writeBytesNoNull(Constants.SLASH_STAR_SPACE_AS_BYTES);
/* 2529 */       sendPacket.writeBytesNoNull(commentAsBytes);
/* 2530 */       sendPacket.writeBytesNoNull(Constants.SPACE_STAR_SLASH_SPACE_AS_BYTES);
/*      */     }
/*      */     
/* 2533 */     for (int i = 0; i < batchedParameterStrings.length; i++) {
/* 2534 */       checkAllParametersSet(batchedParameterStrings[i], batchedParameterStreams[i], i);
/*      */       
/*      */ 
/* 2537 */       sendPacket.writeBytesNoNull(this.staticSqlStrings[i]);
/*      */       
/* 2539 */       if (batchedIsStream[i] != 0) {
/* 2540 */         streamToBytes(sendPacket, batchedParameterStreams[i], true, batchedStreamLengths[i], useStreamLengths);
/*      */       }
/*      */       else {
/* 2543 */         sendPacket.writeBytesNoNull(batchedParameterStrings[i]);
/*      */       }
/*      */     }
/*      */     
/* 2547 */     sendPacket.writeBytesNoNull(this.staticSqlStrings[batchedParameterStrings.length]);
/*      */     
/*      */ 
/* 2550 */     return sendPacket;
/*      */   }
/*      */   
/*      */   private void checkAllParametersSet(byte[] parameterString, InputStream parameterStream, int columnIndex) throws SQLException
/*      */   {
/* 2555 */     if ((parameterString == null) && (parameterStream == null))
/*      */     {
/*      */ 
/* 2558 */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.40") + (columnIndex + 1), "07001", getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PreparedStatement prepareBatchedInsertSQL(MySQLConnection localConn, int numBatches)
/*      */     throws SQLException
/*      */   {
/* 2568 */     PreparedStatement pstmt = new PreparedStatement(localConn, "Rewritten batch of: " + this.originalSql, this.currentCatalog, this.parseInfo.getParseInfoForBatch(numBatches));
/* 2569 */     pstmt.setRetrieveGeneratedKeys(this.retrieveGeneratedKeys);
/* 2570 */     pstmt.rewrittenBatchSize = numBatches;
/*      */     
/* 2572 */     return pstmt;
/*      */   }
/*      */   
/* 2575 */   protected int rewrittenBatchSize = 0;
/*      */   
/*      */   public int getRewrittenBatchSize() {
/* 2578 */     return this.rewrittenBatchSize;
/*      */   }
/*      */   
/*      */   public String getNonRewrittenSql() {
/* 2582 */     int indexOfBatch = this.originalSql.indexOf(" of: ");
/*      */     
/* 2584 */     if (indexOfBatch != -1) {
/* 2585 */       return this.originalSql.substring(indexOfBatch + 5);
/*      */     }
/*      */     
/* 2588 */     return this.originalSql;
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
/*      */   public byte[] getBytesRepresentation(int parameterIndex)
/*      */     throws SQLException
/*      */   {
/* 2605 */     if (this.isStream[parameterIndex] != 0) {
/* 2606 */       return streamToBytes(this.parameterStreams[parameterIndex], false, this.streamLengths[parameterIndex], this.connection.getUseStreamLengthsInPrepStmts());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2611 */     byte[] parameterVal = this.parameterValues[parameterIndex];
/*      */     
/* 2613 */     if (parameterVal == null) {
/* 2614 */       return null;
/*      */     }
/*      */     
/* 2617 */     if ((parameterVal[0] == 39) && (parameterVal[(parameterVal.length - 1)] == 39))
/*      */     {
/* 2619 */       byte[] valNoQuotes = new byte[parameterVal.length - 2];
/* 2620 */       System.arraycopy(parameterVal, 1, valNoQuotes, 0, parameterVal.length - 2);
/*      */       
/*      */ 
/* 2623 */       return valNoQuotes;
/*      */     }
/*      */     
/* 2626 */     return parameterVal;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected byte[] getBytesRepresentationForBatch(int parameterIndex, int commandIndex)
/*      */     throws SQLException
/*      */   {
/* 2638 */     Object batchedArg = this.batchedArgs.get(commandIndex);
/* 2639 */     if ((batchedArg instanceof String)) {
/*      */       try {
/* 2641 */         return ((String)batchedArg).getBytes(this.charEncoding);
/*      */       }
/*      */       catch (UnsupportedEncodingException uue) {
/* 2644 */         throw new RuntimeException(Messages.getString("PreparedStatement.32") + this.charEncoding + Messages.getString("PreparedStatement.33"));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2651 */     BatchParams params = (BatchParams)batchedArg;
/* 2652 */     if (params.isStream[parameterIndex] != 0) {
/* 2653 */       return streamToBytes(params.parameterStreams[parameterIndex], false, params.streamLengths[parameterIndex], this.connection.getUseStreamLengthsInPrepStmts());
/*      */     }
/*      */     
/* 2656 */     byte[] parameterVal = params.parameterStrings[parameterIndex];
/* 2657 */     if (parameterVal == null) {
/* 2658 */       return null;
/*      */     }
/* 2660 */     if ((parameterVal[0] == 39) && (parameterVal[(parameterVal.length - 1)] == 39))
/*      */     {
/* 2662 */       byte[] valNoQuotes = new byte[parameterVal.length - 2];
/* 2663 */       System.arraycopy(parameterVal, 1, valNoQuotes, 0, parameterVal.length - 2);
/*      */       
/*      */ 
/* 2666 */       return valNoQuotes;
/*      */     }
/*      */     
/* 2669 */     return parameterVal;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final String getDateTimePattern(String dt, boolean toTime)
/*      */     throws Exception
/*      */   {
/* 2679 */     int dtLength = dt != null ? dt.length() : 0;
/*      */     
/* 2681 */     if ((dtLength >= 8) && (dtLength <= 10)) {
/* 2682 */       int dashCount = 0;
/* 2683 */       boolean isDateOnly = true;
/*      */       
/* 2685 */       for (int i = 0; i < dtLength; i++) {
/* 2686 */         char c = dt.charAt(i);
/*      */         
/* 2688 */         if ((!Character.isDigit(c)) && (c != '-')) {
/* 2689 */           isDateOnly = false;
/*      */           
/* 2691 */           break;
/*      */         }
/*      */         
/* 2694 */         if (c == '-') {
/* 2695 */           dashCount++;
/*      */         }
/*      */       }
/*      */       
/* 2699 */       if ((isDateOnly) && (dashCount == 2)) {
/* 2700 */         return "yyyy-MM-dd";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2707 */     boolean colonsOnly = true;
/*      */     
/* 2709 */     for (int i = 0; i < dtLength; i++) {
/* 2710 */       char c = dt.charAt(i);
/*      */       
/* 2712 */       if ((!Character.isDigit(c)) && (c != ':')) {
/* 2713 */         colonsOnly = false;
/*      */         
/* 2715 */         break;
/*      */       }
/*      */     }
/*      */     
/* 2719 */     if (colonsOnly) {
/* 2720 */       return "HH:mm:ss";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2729 */     StringReader reader = new StringReader(dt + " ");
/* 2730 */     ArrayList vec = new ArrayList();
/* 2731 */     ArrayList vecRemovelist = new ArrayList();
/* 2732 */     Object[] nv = new Object[3];
/*      */     
/* 2734 */     nv[0] = Constants.characterValueOf('y');
/* 2735 */     nv[1] = new StringBuffer();
/* 2736 */     nv[2] = Constants.integerValueOf(0);
/* 2737 */     vec.add(nv);
/*      */     
/* 2739 */     if (toTime) {
/* 2740 */       nv = new Object[3];
/* 2741 */       nv[0] = Constants.characterValueOf('h');
/* 2742 */       nv[1] = new StringBuffer();
/* 2743 */       nv[2] = Constants.integerValueOf(0);
/* 2744 */       vec.add(nv);
/*      */     }
/*      */     int z;
/* 2747 */     while ((z = reader.read()) != -1) {
/* 2748 */       char separator = (char)z;
/* 2749 */       int maxvecs = vec.size();
/*      */       
/* 2751 */       for (int count = 0; count < maxvecs; count++) {
/* 2752 */         Object[] v = (Object[])vec.get(count);
/* 2753 */         int n = ((Integer)v[2]).intValue();
/* 2754 */         char c = getSuccessor(((Character)v[0]).charValue(), n);
/*      */         
/* 2756 */         if (!Character.isLetterOrDigit(separator)) {
/* 2757 */           if ((c == ((Character)v[0]).charValue()) && (c != 'S')) {
/* 2758 */             vecRemovelist.add(v);
/*      */           } else {
/* 2760 */             ((StringBuffer)v[1]).append(separator);
/*      */             
/* 2762 */             if ((c == 'X') || (c == 'Y')) {
/* 2763 */               v[2] = Constants.integerValueOf(4);
/*      */             }
/*      */           }
/*      */         } else {
/* 2767 */           if (c == 'X') {
/* 2768 */             c = 'y';
/* 2769 */             nv = new Object[3];
/* 2770 */             nv[1] = new StringBuffer(((StringBuffer)v[1]).toString()).append('M');
/*      */             
/* 2772 */             nv[0] = Constants.characterValueOf('M');
/* 2773 */             nv[2] = Constants.integerValueOf(1);
/* 2774 */             vec.add(nv);
/* 2775 */           } else if (c == 'Y') {
/* 2776 */             c = 'M';
/* 2777 */             nv = new Object[3];
/* 2778 */             nv[1] = new StringBuffer(((StringBuffer)v[1]).toString()).append('d');
/*      */             
/* 2780 */             nv[0] = Constants.characterValueOf('d');
/* 2781 */             nv[2] = Constants.integerValueOf(1);
/* 2782 */             vec.add(nv);
/*      */           }
/*      */           
/* 2785 */           ((StringBuffer)v[1]).append(c);
/*      */           
/* 2787 */           if (c == ((Character)v[0]).charValue()) {
/* 2788 */             v[2] = Constants.integerValueOf(n + 1);
/*      */           } else {
/* 2790 */             v[0] = Constants.characterValueOf(c);
/* 2791 */             v[2] = Constants.integerValueOf(1);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2796 */       int size = vecRemovelist.size();
/*      */       
/* 2798 */       for (int i = 0; i < size; i++) {
/* 2799 */         Object[] v = (Object[])vecRemovelist.get(i);
/* 2800 */         vec.remove(v);
/*      */       }
/*      */       
/* 2803 */       vecRemovelist.clear();
/*      */     }
/*      */     
/* 2806 */     int size = vec.size();
/*      */     
/* 2808 */     for (int i = 0; i < size; i++) {
/* 2809 */       Object[] v = (Object[])vec.get(i);
/* 2810 */       char c = ((Character)v[0]).charValue();
/* 2811 */       int n = ((Integer)v[2]).intValue();
/*      */       
/* 2813 */       boolean bk = getSuccessor(c, n) != c;
/* 2814 */       boolean atEnd = ((c == 's') || (c == 'm') || ((c == 'h') && (toTime))) && (bk);
/* 2815 */       boolean finishesAtDate = (bk) && (c == 'd') && (!toTime);
/* 2816 */       boolean containsEnd = ((StringBuffer)v[1]).toString().indexOf('W') != -1;
/*      */       
/*      */ 
/* 2819 */       if (((!atEnd) && (!finishesAtDate)) || (containsEnd)) {
/* 2820 */         vecRemovelist.add(v);
/*      */       }
/*      */     }
/*      */     
/* 2824 */     size = vecRemovelist.size();
/*      */     
/* 2826 */     for (int i = 0; i < size; i++) {
/* 2827 */       vec.remove(vecRemovelist.get(i));
/*      */     }
/*      */     
/* 2830 */     vecRemovelist.clear();
/* 2831 */     Object[] v = (Object[])vec.get(0);
/*      */     
/* 2833 */     StringBuffer format = (StringBuffer)v[1];
/* 2834 */     format.setLength(format.length() - 1);
/*      */     
/* 2836 */     return format.toString();
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
/*      */   public java.sql.ResultSetMetaData getMetaData()
/*      */     throws SQLException
/*      */   {
/* 2862 */     if (!isSelectQuery()) {
/* 2863 */       return null;
/*      */     }
/*      */     
/* 2866 */     PreparedStatement mdStmt = null;
/* 2867 */     ResultSet mdRs = null;
/*      */     
/* 2869 */     if (this.pstmtResultMetaData == null) {
/*      */       try {
/* 2871 */         mdStmt = new PreparedStatement(this.connection, this.originalSql, this.currentCatalog, this.parseInfo);
/*      */         
/*      */ 
/* 2874 */         mdStmt.setMaxRows(0);
/*      */         
/* 2876 */         int paramCount = this.parameterValues.length;
/*      */         
/* 2878 */         for (int i = 1; i <= paramCount; i++) {
/* 2879 */           mdStmt.setString(i, "");
/*      */         }
/*      */         
/* 2882 */         boolean hadResults = mdStmt.execute();
/*      */         
/* 2884 */         if (hadResults) {
/* 2885 */           mdRs = mdStmt.getResultSet();
/*      */           
/* 2887 */           this.pstmtResultMetaData = mdRs.getMetaData();
/*      */         } else {
/* 2889 */           this.pstmtResultMetaData = new ResultSetMetaData(new Field[0], this.connection.getUseOldAliasMetadataBehavior(), getExceptionInterceptor());
/*      */         }
/*      */       }
/*      */       finally
/*      */       {
/* 2894 */         SQLException sqlExRethrow = null;
/*      */         
/* 2896 */         if (mdRs != null) {
/*      */           try {
/* 2898 */             mdRs.close();
/*      */           } catch (SQLException sqlEx) {
/* 2900 */             sqlExRethrow = sqlEx;
/*      */           }
/*      */           
/* 2903 */           mdRs = null;
/*      */         }
/*      */         
/* 2906 */         if (mdStmt != null) {
/*      */           try {
/* 2908 */             mdStmt.close();
/*      */           } catch (SQLException sqlEx) {
/* 2910 */             sqlExRethrow = sqlEx;
/*      */           }
/*      */           
/* 2913 */           mdStmt = null;
/*      */         }
/*      */         
/* 2916 */         if (sqlExRethrow != null) {
/* 2917 */           throw sqlExRethrow;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2922 */     return this.pstmtResultMetaData;
/*      */   }
/*      */   
/*      */   protected boolean isSelectQuery() {
/* 2926 */     return StringUtils.startsWithIgnoreCaseAndWs(StringUtils.stripComments(this.originalSql, "'\"", "'\"", true, false, true, true), "SELECT");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ParameterMetaData getParameterMetaData()
/*      */     throws SQLException
/*      */   {
/* 2937 */     if (this.parameterMetaData == null) {
/* 2938 */       if (this.connection.getGenerateSimpleParameterMetadata()) {
/* 2939 */         this.parameterMetaData = new MysqlParameterMetadata(this.parameterCount);
/*      */       } else {
/* 2941 */         this.parameterMetaData = new MysqlParameterMetadata(null, this.parameterCount, getExceptionInterceptor());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2946 */     return this.parameterMetaData;
/*      */   }
/*      */   
/*      */   ParseInfo getParseInfo() {
/* 2950 */     return this.parseInfo;
/*      */   }
/*      */   
/*      */   private final char getSuccessor(char c, int n) {
/* 2954 */     return (c == 's') && (n < 2) ? 's' : c == 'm' ? 's' : (c == 'm') && (n < 2) ? 'm' : c == 'H' ? 'm' : (c == 'H') && (n < 2) ? 'H' : c == 'd' ? 'H' : (c == 'd') && (n < 2) ? 'd' : c == 'M' ? 'd' : (c == 'M') && (n < 3) ? 'M' : (c == 'M') && (n == 2) ? 'Y' : c == 'y' ? 'M' : (c == 'y') && (n < 4) ? 'y' : (c == 'y') && (n == 2) ? 'X' : 'W';
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
/*      */   private final void hexEscapeBlock(byte[] buf, Buffer packet, int size)
/*      */     throws SQLException
/*      */   {
/* 2980 */     for (int i = 0; i < size; i++) {
/* 2981 */       byte b = buf[i];
/* 2982 */       int lowBits = (b & 0xFF) / 16;
/* 2983 */       int highBits = (b & 0xFF) % 16;
/*      */       
/* 2985 */       packet.writeByte(HEX_DIGITS[lowBits]);
/* 2986 */       packet.writeByte(HEX_DIGITS[highBits]);
/*      */     }
/*      */   }
/*      */   
/*      */   private void initializeFromParseInfo() throws SQLException {
/* 2991 */     this.staticSqlStrings = this.parseInfo.staticSql;
/* 2992 */     this.hasLimitClause = this.parseInfo.foundLimitClause;
/* 2993 */     this.isLoadDataQuery = this.parseInfo.foundLoadData;
/* 2994 */     this.firstCharOfStmt = this.parseInfo.firstStmtChar;
/*      */     
/* 2996 */     this.parameterCount = (this.staticSqlStrings.length - 1);
/*      */     
/* 2998 */     this.parameterValues = new byte[this.parameterCount][];
/* 2999 */     this.parameterStreams = new InputStream[this.parameterCount];
/* 3000 */     this.isStream = new boolean[this.parameterCount];
/* 3001 */     this.streamLengths = new int[this.parameterCount];
/* 3002 */     this.isNull = new boolean[this.parameterCount];
/* 3003 */     this.parameterTypes = new int[this.parameterCount];
/*      */     
/* 3005 */     clearParameters();
/*      */     
/* 3007 */     for (int j = 0; j < this.parameterCount; j++) {
/* 3008 */       this.isStream[j] = false;
/*      */     }
/*      */   }
/*      */   
/*      */   boolean isNull(int paramIndex) {
/* 3013 */     return this.isNull[paramIndex];
/*      */   }
/*      */   
/*      */   private final int readblock(InputStream i, byte[] b) throws SQLException {
/*      */     try {
/* 3018 */       return i.read(b);
/*      */     } catch (Throwable ex) {
/* 3020 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("PreparedStatement.56") + ex.getClass().getName(), "S1000", getExceptionInterceptor());
/*      */       
/* 3022 */       sqlEx.initCause(ex);
/*      */       
/* 3024 */       throw sqlEx;
/*      */     }
/*      */   }
/*      */   
/*      */   private final int readblock(InputStream i, byte[] b, int length) throws SQLException
/*      */   {
/*      */     try {
/* 3031 */       int lengthToRead = length;
/*      */       
/* 3033 */       if (lengthToRead > b.length) {
/* 3034 */         lengthToRead = b.length;
/*      */       }
/*      */       
/* 3037 */       return i.read(b, 0, lengthToRead);
/*      */     } catch (Throwable ex) {
/* 3039 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("PreparedStatement.56") + ex.getClass().getName(), "S1000", getExceptionInterceptor());
/*      */       
/* 3041 */       sqlEx.initCause(ex);
/*      */       
/* 3043 */       throw sqlEx;
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
/*      */   protected void realClose(boolean calledExplicitly, boolean closeOpenResults)
/*      */     throws SQLException
/*      */   {
/* 3058 */     if ((this.useUsageAdvisor) && 
/* 3059 */       (this.numberOfExecutions <= 1)) {
/* 3060 */       String message = Messages.getString("PreparedStatement.43");
/*      */       
/* 3062 */       this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", this.currentCatalog, this.connectionId, getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3071 */     super.realClose(calledExplicitly, closeOpenResults);
/*      */     
/* 3073 */     this.dbmd = null;
/* 3074 */     this.originalSql = null;
/* 3075 */     this.staticSqlStrings = ((byte[][])null);
/* 3076 */     this.parameterValues = ((byte[][])null);
/* 3077 */     this.parameterStreams = null;
/* 3078 */     this.isStream = null;
/* 3079 */     this.streamLengths = null;
/* 3080 */     this.isNull = null;
/* 3081 */     this.streamConvertBuf = null;
/* 3082 */     this.parameterTypes = null;
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
/*      */   public void setArray(int i, Array x)
/*      */     throws SQLException
/*      */   {
/* 3099 */     throw SQLError.notImplemented();
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
/*      */   public void setAsciiStream(int parameterIndex, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 3126 */     if (x == null) {
/* 3127 */       setNull(parameterIndex, 12);
/*      */     } else {
/* 3129 */       setBinaryStream(parameterIndex, x, length);
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
/*      */   public void setBigDecimal(int parameterIndex, BigDecimal x)
/*      */     throws SQLException
/*      */   {
/* 3147 */     if (x == null) {
/* 3148 */       setNull(parameterIndex, 3);
/*      */     } else {
/* 3150 */       setInternal(parameterIndex, StringUtils.fixDecimalExponent(StringUtils.consistentToString(x)));
/*      */       
/*      */ 
/* 3153 */       this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 3;
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
/*      */   public void setBinaryStream(int parameterIndex, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 3179 */     if (x == null) {
/* 3180 */       setNull(parameterIndex, -2);
/*      */     } else {
/* 3182 */       int parameterIndexOffset = getParameterIndexOffset();
/*      */       
/* 3184 */       if ((parameterIndex < 1) || (parameterIndex > this.staticSqlStrings.length))
/*      */       {
/* 3186 */         throw SQLError.createSQLException(Messages.getString("PreparedStatement.2") + parameterIndex + Messages.getString("PreparedStatement.3") + this.staticSqlStrings.length + Messages.getString("PreparedStatement.4"), "S1009", getExceptionInterceptor());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3191 */       if ((parameterIndexOffset == -1) && (parameterIndex == 1)) {
/* 3192 */         throw SQLError.createSQLException("Can't set IN parameter for return value of stored function call.", "S1009", getExceptionInterceptor());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3197 */       this.parameterStreams[(parameterIndex - 1 + parameterIndexOffset)] = x;
/* 3198 */       this.isStream[(parameterIndex - 1 + parameterIndexOffset)] = true;
/* 3199 */       this.streamLengths[(parameterIndex - 1 + parameterIndexOffset)] = length;
/* 3200 */       this.isNull[(parameterIndex - 1 + parameterIndexOffset)] = false;
/* 3201 */       this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 2004;
/*      */     }
/*      */   }
/*      */   
/*      */   public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException
/*      */   {
/* 3207 */     setBinaryStream(parameterIndex, inputStream, (int)length);
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
/*      */   public void setBlob(int i, Blob x)
/*      */     throws SQLException
/*      */   {
/* 3222 */     if (x == null) {
/* 3223 */       setNull(i, 2004);
/*      */     } else {
/* 3225 */       ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
/*      */       
/* 3227 */       bytesOut.write(39);
/* 3228 */       escapeblockFast(x.getBytes(1L, (int)x.length()), bytesOut, (int)x.length());
/*      */       
/* 3230 */       bytesOut.write(39);
/*      */       
/* 3232 */       setInternal(i, bytesOut.toByteArray());
/*      */       
/* 3234 */       this.parameterTypes[(i - 1 + getParameterIndexOffset())] = 2004;
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
/*      */   public void setBoolean(int parameterIndex, boolean x)
/*      */     throws SQLException
/*      */   {
/* 3251 */     if (this.useTrueBoolean) {
/* 3252 */       setInternal(parameterIndex, x ? "1" : "0");
/*      */     } else {
/* 3254 */       setInternal(parameterIndex, x ? "'t'" : "'f'");
/*      */       
/* 3256 */       this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 16;
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
/*      */   public void setByte(int parameterIndex, byte x)
/*      */     throws SQLException
/*      */   {
/* 3273 */     setInternal(parameterIndex, String.valueOf(x));
/*      */     
/* 3275 */     this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = -6;
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
/*      */   public void setBytes(int parameterIndex, byte[] x)
/*      */     throws SQLException
/*      */   {
/* 3292 */     setBytes(parameterIndex, x, true, true);
/*      */     
/* 3294 */     if (x != null) {
/* 3295 */       this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = -2;
/*      */     }
/*      */   }
/*      */   
/*      */   protected void setBytes(int parameterIndex, byte[] x, boolean checkForIntroducer, boolean escapeForMBChars)
/*      */     throws SQLException
/*      */   {
/* 3302 */     if (x == null) {
/* 3303 */       setNull(parameterIndex, -2);
/*      */     } else {
/* 3305 */       String connectionEncoding = this.connection.getEncoding();
/*      */       
/* 3307 */       if ((this.connection.isNoBackslashEscapesSet()) || ((escapeForMBChars) && (this.connection.getUseUnicode()) && (connectionEncoding != null) && (CharsetMapping.isMultibyteCharset(connectionEncoding))))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3315 */         ByteArrayOutputStream bOut = new ByteArrayOutputStream(x.length * 2 + 3);
/*      */         
/* 3317 */         bOut.write(120);
/* 3318 */         bOut.write(39);
/*      */         
/* 3320 */         for (int i = 0; i < x.length; i++) {
/* 3321 */           int lowBits = (x[i] & 0xFF) / 16;
/* 3322 */           int highBits = (x[i] & 0xFF) % 16;
/*      */           
/* 3324 */           bOut.write(HEX_DIGITS[lowBits]);
/* 3325 */           bOut.write(HEX_DIGITS[highBits]);
/*      */         }
/*      */         
/* 3328 */         bOut.write(39);
/*      */         
/* 3330 */         setInternal(parameterIndex, bOut.toByteArray());
/*      */         
/* 3332 */         return;
/*      */       }
/*      */       
/*      */ 
/* 3336 */       int numBytes = x.length;
/*      */       
/* 3338 */       int pad = 2;
/*      */       
/* 3340 */       boolean needsIntroducer = (checkForIntroducer) && (this.connection.versionMeetsMinimum(4, 1, 0));
/*      */       
/*      */ 
/* 3343 */       if (needsIntroducer) {
/* 3344 */         pad += 7;
/*      */       }
/*      */       
/* 3347 */       ByteArrayOutputStream bOut = new ByteArrayOutputStream(numBytes + pad);
/*      */       
/*      */ 
/* 3350 */       if (needsIntroducer) {
/* 3351 */         bOut.write(95);
/* 3352 */         bOut.write(98);
/* 3353 */         bOut.write(105);
/* 3354 */         bOut.write(110);
/* 3355 */         bOut.write(97);
/* 3356 */         bOut.write(114);
/* 3357 */         bOut.write(121);
/*      */       }
/* 3359 */       bOut.write(39);
/*      */       
/* 3361 */       for (int i = 0; i < numBytes; i++) {
/* 3362 */         byte b = x[i];
/*      */         
/* 3364 */         switch (b) {
/*      */         case 0: 
/* 3366 */           bOut.write(92);
/* 3367 */           bOut.write(48);
/*      */           
/* 3369 */           break;
/*      */         
/*      */         case 10: 
/* 3372 */           bOut.write(92);
/* 3373 */           bOut.write(110);
/*      */           
/* 3375 */           break;
/*      */         
/*      */         case 13: 
/* 3378 */           bOut.write(92);
/* 3379 */           bOut.write(114);
/*      */           
/* 3381 */           break;
/*      */         
/*      */         case 92: 
/* 3384 */           bOut.write(92);
/* 3385 */           bOut.write(92);
/*      */           
/* 3387 */           break;
/*      */         
/*      */         case 39: 
/* 3390 */           bOut.write(92);
/* 3391 */           bOut.write(39);
/*      */           
/* 3393 */           break;
/*      */         
/*      */         case 34: 
/* 3396 */           bOut.write(92);
/* 3397 */           bOut.write(34);
/*      */           
/* 3399 */           break;
/*      */         
/*      */         case 26: 
/* 3402 */           bOut.write(92);
/* 3403 */           bOut.write(90);
/*      */           
/* 3405 */           break;
/*      */         
/*      */         default: 
/* 3408 */           bOut.write(b);
/*      */         }
/*      */         
/*      */       }
/* 3412 */       bOut.write(39);
/*      */       
/* 3414 */       setInternal(parameterIndex, bOut.toByteArray());
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
/*      */   protected void setBytesNoEscape(int parameterIndex, byte[] parameterAsBytes)
/*      */     throws SQLException
/*      */   {
/* 3432 */     byte[] parameterWithQuotes = new byte[parameterAsBytes.length + 2];
/* 3433 */     parameterWithQuotes[0] = 39;
/* 3434 */     System.arraycopy(parameterAsBytes, 0, parameterWithQuotes, 1, parameterAsBytes.length);
/*      */     
/* 3436 */     parameterWithQuotes[(parameterAsBytes.length + 1)] = 39;
/*      */     
/* 3438 */     setInternal(parameterIndex, parameterWithQuotes);
/*      */   }
/*      */   
/*      */   protected void setBytesNoEscapeNoQuotes(int parameterIndex, byte[] parameterAsBytes) throws SQLException
/*      */   {
/* 3443 */     setInternal(parameterIndex, parameterAsBytes);
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
/*      */   public void setCharacterStream(int parameterIndex, Reader reader, int length)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 3471 */       if (reader == null) {
/* 3472 */         setNull(parameterIndex, -1);
/*      */       } else {
/* 3474 */         char[] c = null;
/* 3475 */         int len = 0;
/*      */         
/* 3477 */         boolean useLength = this.connection.getUseStreamLengthsInPrepStmts();
/*      */         
/*      */ 
/* 3480 */         String forcedEncoding = this.connection.getClobCharacterEncoding();
/*      */         
/* 3482 */         if ((useLength) && (length != -1)) {
/* 3483 */           c = new char[length];
/*      */           
/* 3485 */           int numCharsRead = readFully(reader, c, length);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 3490 */           if (forcedEncoding == null) {
/* 3491 */             setString(parameterIndex, new String(c, 0, numCharsRead));
/*      */           } else {
/*      */             try {
/* 3494 */               setBytes(parameterIndex, new String(c, 0, numCharsRead).getBytes(forcedEncoding));
/*      */             }
/*      */             catch (UnsupportedEncodingException uee)
/*      */             {
/* 3498 */               throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", getExceptionInterceptor());
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 3503 */           c = new char['က'];
/*      */           
/* 3505 */           StringBuffer buf = new StringBuffer();
/*      */           
/* 3507 */           while ((len = reader.read(c)) != -1) {
/* 3508 */             buf.append(c, 0, len);
/*      */           }
/*      */           
/* 3511 */           if (forcedEncoding == null) {
/* 3512 */             setString(parameterIndex, buf.toString());
/*      */           } else {
/*      */             try {
/* 3515 */               setBytes(parameterIndex, buf.toString().getBytes(forcedEncoding));
/*      */             }
/*      */             catch (UnsupportedEncodingException uee) {
/* 3518 */               throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", getExceptionInterceptor());
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 3524 */         this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 2005;
/*      */       }
/*      */     } catch (IOException ioEx) {
/* 3527 */       throw SQLError.createSQLException(ioEx.toString(), "S1000", getExceptionInterceptor());
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
/*      */   public void setClob(int i, Clob x)
/*      */     throws SQLException
/*      */   {
/* 3544 */     if (x == null) {
/* 3545 */       setNull(i, 2005);
/*      */     }
/*      */     else {
/* 3548 */       String forcedEncoding = this.connection.getClobCharacterEncoding();
/*      */       
/* 3550 */       if (forcedEncoding == null) {
/* 3551 */         setString(i, x.getSubString(1L, (int)x.length()));
/*      */       } else {
/*      */         try {
/* 3554 */           setBytes(i, x.getSubString(1L, (int)x.length()).getBytes(forcedEncoding));
/*      */         }
/*      */         catch (UnsupportedEncodingException uee) {
/* 3557 */           throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", getExceptionInterceptor());
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 3562 */       this.parameterTypes[(i - 1 + getParameterIndexOffset())] = 2005;
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
/*      */   public void setDate(int parameterIndex, java.sql.Date x)
/*      */     throws SQLException
/*      */   {
/* 3580 */     setDate(parameterIndex, x, null);
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
/*      */   public void setDate(int parameterIndex, java.sql.Date x, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 3599 */     if (x == null) {
/* 3600 */       setNull(parameterIndex, 91);
/*      */     } else {
/* 3602 */       checkClosed();
/*      */       
/* 3604 */       if (!this.useLegacyDatetimeCode) {
/* 3605 */         newSetDateInternal(parameterIndex, x, cal);
/*      */       }
/*      */       else
/*      */       {
/* 3609 */         SimpleDateFormat dateFormatter = new SimpleDateFormat("''yyyy-MM-dd''", Locale.US);
/*      */         
/* 3611 */         setInternal(parameterIndex, dateFormatter.format(x));
/*      */         
/* 3613 */         this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 91;
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
/*      */   public void setDouble(int parameterIndex, double x)
/*      */     throws SQLException
/*      */   {
/* 3632 */     if ((!this.connection.getAllowNanAndInf()) && ((x == Double.POSITIVE_INFINITY) || (x == Double.NEGATIVE_INFINITY) || (Double.isNaN(x))))
/*      */     {
/*      */ 
/* 3635 */       throw SQLError.createSQLException("'" + x + "' is not a valid numeric or approximate numeric value", "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3641 */     setInternal(parameterIndex, StringUtils.fixDecimalExponent(String.valueOf(x)));
/*      */     
/*      */ 
/* 3644 */     this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 8;
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
/*      */   public void setFloat(int parameterIndex, float x)
/*      */     throws SQLException
/*      */   {
/* 3660 */     setInternal(parameterIndex, StringUtils.fixDecimalExponent(String.valueOf(x)));
/*      */     
/*      */ 
/* 3663 */     this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 6;
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
/*      */   public void setInt(int parameterIndex, int x)
/*      */     throws SQLException
/*      */   {
/* 3679 */     setInternal(parameterIndex, String.valueOf(x));
/*      */     
/* 3681 */     this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 4;
/*      */   }
/*      */   
/*      */   protected final void setInternal(int paramIndex, byte[] val) throws SQLException
/*      */   {
/* 3686 */     if (this.isClosed) {
/* 3687 */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.48"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/* 3691 */     int parameterIndexOffset = getParameterIndexOffset();
/*      */     
/* 3693 */     checkBounds(paramIndex, parameterIndexOffset);
/*      */     
/* 3695 */     this.isStream[(paramIndex - 1 + parameterIndexOffset)] = false;
/* 3696 */     this.isNull[(paramIndex - 1 + parameterIndexOffset)] = false;
/* 3697 */     this.parameterStreams[(paramIndex - 1 + parameterIndexOffset)] = null;
/* 3698 */     this.parameterValues[(paramIndex - 1 + parameterIndexOffset)] = val;
/*      */   }
/*      */   
/*      */   private void checkBounds(int paramIndex, int parameterIndexOffset) throws SQLException
/*      */   {
/* 3703 */     if (paramIndex < 1) {
/* 3704 */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.49") + paramIndex + Messages.getString("PreparedStatement.50"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/* 3708 */     if (paramIndex > this.parameterCount) {
/* 3709 */       throw SQLError.createSQLException(Messages.getString("PreparedStatement.51") + paramIndex + Messages.getString("PreparedStatement.52") + this.parameterValues.length + Messages.getString("PreparedStatement.53"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3714 */     if ((parameterIndexOffset == -1) && (paramIndex == 1)) {
/* 3715 */       throw SQLError.createSQLException("Can't set IN parameter for return value of stored function call.", "S1009", getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void setInternal(int paramIndex, String val)
/*      */     throws SQLException
/*      */   {
/* 3722 */     checkClosed();
/*      */     
/* 3724 */     byte[] parameterAsBytes = null;
/*      */     
/* 3726 */     if (this.charConverter != null) {
/* 3727 */       parameterAsBytes = this.charConverter.toBytes(val);
/*      */     } else {
/* 3729 */       parameterAsBytes = StringUtils.getBytes(val, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3735 */     setInternal(paramIndex, parameterAsBytes);
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
/*      */   public void setLong(int parameterIndex, long x)
/*      */     throws SQLException
/*      */   {
/* 3751 */     setInternal(parameterIndex, String.valueOf(x));
/*      */     
/* 3753 */     this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = -5;
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
/*      */   public void setNull(int parameterIndex, int sqlType)
/*      */     throws SQLException
/*      */   {
/* 3773 */     setInternal(parameterIndex, "null");
/* 3774 */     this.isNull[(parameterIndex - 1 + getParameterIndexOffset())] = true;
/*      */     
/* 3776 */     this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 0;
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
/*      */   public void setNull(int parameterIndex, int sqlType, String arg)
/*      */     throws SQLException
/*      */   {
/* 3798 */     setNull(parameterIndex, sqlType);
/*      */     
/* 3800 */     this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 0;
/*      */   }
/*      */   
/*      */   private void setNumericObject(int parameterIndex, Object parameterObj, int targetSqlType, int scale) throws SQLException {
/*      */     Number parameterAsNum;
/*      */     Number parameterAsNum;
/* 3806 */     if ((parameterObj instanceof Boolean)) {
/* 3807 */       parameterAsNum = ((Boolean)parameterObj).booleanValue() ? Constants.integerValueOf(1) : Constants.integerValueOf(0);
/*      */ 
/*      */     }
/* 3810 */     else if ((parameterObj instanceof String)) { Number parameterAsNum;
/* 3811 */       switch (targetSqlType) {
/*      */       case -7:  Number parameterAsNum;
/* 3813 */         if (("1".equals((String)parameterObj)) || ("0".equals((String)parameterObj)))
/*      */         {
/* 3815 */           parameterAsNum = Integer.valueOf((String)parameterObj);
/*      */         } else {
/* 3817 */           boolean parameterAsBoolean = "true".equalsIgnoreCase((String)parameterObj);
/*      */           
/*      */ 
/* 3820 */           parameterAsNum = parameterAsBoolean ? Constants.integerValueOf(1) : Constants.integerValueOf(0);
/*      */         }
/*      */         
/*      */ 
/* 3824 */         break;
/*      */       
/*      */       case -6: 
/*      */       case 4: 
/*      */       case 5: 
/* 3829 */         parameterAsNum = Integer.valueOf((String)parameterObj);
/*      */         
/*      */ 
/* 3832 */         break;
/*      */       
/*      */       case -5: 
/* 3835 */         parameterAsNum = Long.valueOf((String)parameterObj);
/*      */         
/*      */ 
/* 3838 */         break;
/*      */       
/*      */       case 7: 
/* 3841 */         parameterAsNum = Float.valueOf((String)parameterObj);
/*      */         
/*      */ 
/* 3844 */         break;
/*      */       
/*      */       case 6: 
/*      */       case 8: 
/* 3848 */         parameterAsNum = Double.valueOf((String)parameterObj);
/*      */         
/*      */ 
/* 3851 */         break;
/*      */       case -4: case -3: case -2: 
/*      */       case -1: case 0: 
/*      */       case 1: case 2: 
/*      */       case 3: default: 
/* 3856 */         parameterAsNum = new BigDecimal((String)parameterObj);break;
/*      */       }
/*      */     }
/*      */     else {
/* 3860 */       parameterAsNum = (Number)parameterObj;
/*      */     }
/*      */     
/* 3863 */     switch (targetSqlType) {
/*      */     case -7: 
/*      */     case -6: 
/*      */     case 4: 
/*      */     case 5: 
/* 3868 */       setInt(parameterIndex, parameterAsNum.intValue());
/*      */       
/* 3870 */       break;
/*      */     
/*      */     case -5: 
/* 3873 */       setLong(parameterIndex, parameterAsNum.longValue());
/*      */       
/* 3875 */       break;
/*      */     
/*      */     case 7: 
/* 3878 */       setFloat(parameterIndex, parameterAsNum.floatValue());
/*      */       
/* 3880 */       break;
/*      */     
/*      */     case 6: 
/*      */     case 8: 
/* 3884 */       setDouble(parameterIndex, parameterAsNum.doubleValue());
/*      */       
/* 3886 */       break;
/*      */     
/*      */ 
/*      */     case 2: 
/*      */     case 3: 
/* 3891 */       if ((parameterAsNum instanceof BigDecimal)) {
/* 3892 */         BigDecimal scaledBigDecimal = null;
/*      */         try
/*      */         {
/* 3895 */           scaledBigDecimal = ((BigDecimal)parameterAsNum).setScale(scale);
/*      */         }
/*      */         catch (ArithmeticException ex) {
/*      */           try {
/* 3899 */             scaledBigDecimal = ((BigDecimal)parameterAsNum).setScale(scale, 4);
/*      */           }
/*      */           catch (ArithmeticException arEx)
/*      */           {
/* 3903 */             throw SQLError.createSQLException("Can't set scale of '" + scale + "' for DECIMAL argument '" + parameterAsNum + "'", "S1009", getExceptionInterceptor());
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3912 */         setBigDecimal(parameterIndex, scaledBigDecimal);
/* 3913 */       } else if ((parameterAsNum instanceof BigInteger)) {
/* 3914 */         setBigDecimal(parameterIndex, new BigDecimal((BigInteger)parameterAsNum, scale));
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 3920 */         setBigDecimal(parameterIndex, new BigDecimal(parameterAsNum.doubleValue()));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setObject(int parameterIndex, Object parameterObj)
/*      */     throws SQLException
/*      */   {
/* 3942 */     if (parameterObj == null) {
/* 3943 */       setNull(parameterIndex, 1111);
/*      */     }
/* 3945 */     else if ((parameterObj instanceof Byte)) {
/* 3946 */       setInt(parameterIndex, ((Byte)parameterObj).intValue());
/* 3947 */     } else if ((parameterObj instanceof String)) {
/* 3948 */       setString(parameterIndex, (String)parameterObj);
/* 3949 */     } else if ((parameterObj instanceof BigDecimal)) {
/* 3950 */       setBigDecimal(parameterIndex, (BigDecimal)parameterObj);
/* 3951 */     } else if ((parameterObj instanceof Short)) {
/* 3952 */       setShort(parameterIndex, ((Short)parameterObj).shortValue());
/* 3953 */     } else if ((parameterObj instanceof Integer)) {
/* 3954 */       setInt(parameterIndex, ((Integer)parameterObj).intValue());
/* 3955 */     } else if ((parameterObj instanceof Long)) {
/* 3956 */       setLong(parameterIndex, ((Long)parameterObj).longValue());
/* 3957 */     } else if ((parameterObj instanceof Float)) {
/* 3958 */       setFloat(parameterIndex, ((Float)parameterObj).floatValue());
/* 3959 */     } else if ((parameterObj instanceof Double)) {
/* 3960 */       setDouble(parameterIndex, ((Double)parameterObj).doubleValue());
/* 3961 */     } else if ((parameterObj instanceof byte[])) {
/* 3962 */       setBytes(parameterIndex, (byte[])parameterObj);
/* 3963 */     } else if ((parameterObj instanceof java.sql.Date)) {
/* 3964 */       setDate(parameterIndex, (java.sql.Date)parameterObj);
/* 3965 */     } else if ((parameterObj instanceof Time)) {
/* 3966 */       setTime(parameterIndex, (Time)parameterObj);
/* 3967 */     } else if ((parameterObj instanceof Timestamp)) {
/* 3968 */       setTimestamp(parameterIndex, (Timestamp)parameterObj);
/* 3969 */     } else if ((parameterObj instanceof Boolean)) {
/* 3970 */       setBoolean(parameterIndex, ((Boolean)parameterObj).booleanValue());
/*      */     }
/* 3972 */     else if ((parameterObj instanceof InputStream)) {
/* 3973 */       setBinaryStream(parameterIndex, (InputStream)parameterObj, -1);
/* 3974 */     } else if ((parameterObj instanceof Blob)) {
/* 3975 */       setBlob(parameterIndex, (Blob)parameterObj);
/* 3976 */     } else if ((parameterObj instanceof Clob)) {
/* 3977 */       setClob(parameterIndex, (Clob)parameterObj);
/* 3978 */     } else if ((this.connection.getTreatUtilDateAsTimestamp()) && ((parameterObj instanceof java.util.Date)))
/*      */     {
/* 3980 */       setTimestamp(parameterIndex, new Timestamp(((java.util.Date)parameterObj).getTime()));
/*      */     }
/* 3982 */     else if ((parameterObj instanceof BigInteger)) {
/* 3983 */       setString(parameterIndex, parameterObj.toString());
/*      */     } else {
/* 3985 */       setSerializableObject(parameterIndex, parameterObj);
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
/*      */   public void setObject(int parameterIndex, Object parameterObj, int targetSqlType)
/*      */     throws SQLException
/*      */   {
/* 4006 */     if (!(parameterObj instanceof BigDecimal)) {
/* 4007 */       setObject(parameterIndex, parameterObj, targetSqlType, 0);
/*      */     } else {
/* 4009 */       setObject(parameterIndex, parameterObj, targetSqlType, ((BigDecimal)parameterObj).scale());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setObject(int parameterIndex, Object parameterObj, int targetSqlType, int scale)
/*      */     throws SQLException
/*      */   {
/* 4045 */     if (parameterObj == null) {
/* 4046 */       setNull(parameterIndex, 1111);
/*      */     } else {
/*      */       try {
/* 4049 */         switch (targetSqlType)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         case 16: 
/* 4069 */           if ((parameterObj instanceof Boolean)) {
/* 4070 */             setBoolean(parameterIndex, ((Boolean)parameterObj).booleanValue());
/*      */ 
/*      */           }
/* 4073 */           else if ((parameterObj instanceof String)) {
/* 4074 */             setBoolean(parameterIndex, ("true".equalsIgnoreCase((String)parameterObj)) || (!"0".equalsIgnoreCase((String)parameterObj)));
/*      */ 
/*      */ 
/*      */           }
/* 4078 */           else if ((parameterObj instanceof Number)) {
/* 4079 */             int intValue = ((Number)parameterObj).intValue();
/*      */             
/* 4081 */             setBoolean(parameterIndex, intValue != 0);
/*      */           }
/*      */           else
/*      */           {
/* 4085 */             throw SQLError.createSQLException("No conversion from " + parameterObj.getClass().getName() + " to Types.BOOLEAN possible.", "S1009", getExceptionInterceptor());
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */           break;
/*      */         case -7: 
/*      */         case -6: 
/*      */         case -5: 
/*      */         case 2: 
/*      */         case 3: 
/*      */         case 4: 
/*      */         case 5: 
/*      */         case 6: 
/*      */         case 7: 
/*      */         case 8: 
/* 4101 */           setNumericObject(parameterIndex, parameterObj, targetSqlType, scale);
/*      */           
/* 4103 */           break;
/*      */         
/*      */         case -1: 
/*      */         case 1: 
/*      */         case 12: 
/* 4108 */           if ((parameterObj instanceof BigDecimal)) {
/* 4109 */             setString(parameterIndex, StringUtils.fixDecimalExponent(StringUtils.consistentToString((BigDecimal)parameterObj)));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 4115 */             setString(parameterIndex, parameterObj.toString());
/*      */           }
/*      */           
/* 4118 */           break;
/*      */         
/*      */ 
/*      */         case 2005: 
/* 4122 */           if ((parameterObj instanceof Clob)) {
/* 4123 */             setClob(parameterIndex, (Clob)parameterObj);
/*      */           } else {
/* 4125 */             setString(parameterIndex, parameterObj.toString());
/*      */           }
/*      */           
/* 4128 */           break;
/*      */         
/*      */ 
/*      */         case -4: 
/*      */         case -3: 
/*      */         case -2: 
/*      */         case 2004: 
/* 4135 */           if ((parameterObj instanceof byte[])) {
/* 4136 */             setBytes(parameterIndex, (byte[])parameterObj);
/* 4137 */           } else if ((parameterObj instanceof Blob)) {
/* 4138 */             setBlob(parameterIndex, (Blob)parameterObj);
/*      */           } else {
/* 4140 */             setBytes(parameterIndex, StringUtils.getBytes(parameterObj.toString(), this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor()));
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4147 */           break;
/*      */         case 91: 
/*      */         case 93: 
/*      */           java.util.Date parameterAsDate;
/*      */           
/*      */           java.util.Date parameterAsDate;
/*      */           
/* 4154 */           if ((parameterObj instanceof String)) {
/* 4155 */             ParsePosition pp = new ParsePosition(0);
/* 4156 */             DateFormat sdf = new SimpleDateFormat(getDateTimePattern((String)parameterObj, false), Locale.US);
/*      */             
/* 4158 */             parameterAsDate = sdf.parse((String)parameterObj, pp);
/*      */           } else {
/* 4160 */             parameterAsDate = (java.util.Date)parameterObj;
/*      */           }
/*      */           
/* 4163 */           switch (targetSqlType)
/*      */           {
/*      */           case 91: 
/* 4166 */             if ((parameterAsDate instanceof java.sql.Date)) {
/* 4167 */               setDate(parameterIndex, (java.sql.Date)parameterAsDate);
/*      */             }
/*      */             else {
/* 4170 */               setDate(parameterIndex, new java.sql.Date(parameterAsDate.getTime()));
/*      */             }
/*      */             
/*      */ 
/* 4174 */             break;
/*      */           
/*      */ 
/*      */           case 93: 
/* 4178 */             if ((parameterAsDate instanceof Timestamp)) {
/* 4179 */               setTimestamp(parameterIndex, (Timestamp)parameterAsDate);
/*      */             }
/*      */             else {
/* 4182 */               setTimestamp(parameterIndex, new Timestamp(parameterAsDate.getTime()));
/*      */             }
/*      */             
/*      */ 
/*      */             break;
/*      */           }
/*      */           
/*      */           
/* 4190 */           break;
/*      */         
/*      */ 
/*      */         case 92: 
/* 4194 */           if ((parameterObj instanceof String)) {
/* 4195 */             DateFormat sdf = new SimpleDateFormat(getDateTimePattern((String)parameterObj, true), Locale.US);
/*      */             
/* 4197 */             setTime(parameterIndex, new Time(sdf.parse((String)parameterObj).getTime()));
/*      */           }
/* 4199 */           else if ((parameterObj instanceof Timestamp)) {
/* 4200 */             Timestamp xT = (Timestamp)parameterObj;
/* 4201 */             setTime(parameterIndex, new Time(xT.getTime()));
/*      */           } else {
/* 4203 */             setTime(parameterIndex, (Time)parameterObj);
/*      */           }
/*      */           
/* 4206 */           break;
/*      */         
/*      */         case 1111: 
/* 4209 */           setSerializableObject(parameterIndex, parameterObj);
/*      */           
/* 4211 */           break;
/*      */         
/*      */         default: 
/* 4214 */           throw SQLError.createSQLException(Messages.getString("PreparedStatement.16"), "S1000", getExceptionInterceptor());
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception ex) {
/* 4219 */         if ((ex instanceof SQLException)) {
/* 4220 */           throw ((SQLException)ex);
/*      */         }
/*      */         
/* 4223 */         SQLException sqlEx = SQLError.createSQLException(Messages.getString("PreparedStatement.17") + parameterObj.getClass().toString() + Messages.getString("PreparedStatement.18") + ex.getClass().getName() + Messages.getString("PreparedStatement.19") + ex.getMessage(), "S1000", getExceptionInterceptor());
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4231 */         sqlEx.initCause(ex);
/*      */         
/* 4233 */         throw sqlEx;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected int setOneBatchedParameterSet(java.sql.PreparedStatement batchedStatement, int batchedParamIndex, Object paramSet)
/*      */     throws SQLException
/*      */   {
/* 4241 */     BatchParams paramArg = (BatchParams)paramSet;
/*      */     
/* 4243 */     boolean[] isNullBatch = paramArg.isNull;
/* 4244 */     boolean[] isStreamBatch = paramArg.isStream;
/*      */     
/* 4246 */     for (int j = 0; j < isNullBatch.length; j++) {
/* 4247 */       if (isNullBatch[j] != 0) {
/* 4248 */         batchedStatement.setNull(batchedParamIndex++, 0);
/*      */       }
/* 4250 */       else if (isStreamBatch[j] != 0) {
/* 4251 */         batchedStatement.setBinaryStream(batchedParamIndex++, paramArg.parameterStreams[j], paramArg.streamLengths[j]);
/*      */       }
/*      */       else
/*      */       {
/* 4255 */         ((PreparedStatement)batchedStatement).setBytesNoEscapeNoQuotes(batchedParamIndex++, paramArg.parameterStrings[j]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4262 */     return batchedParamIndex;
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
/*      */   public void setRef(int i, Ref x)
/*      */     throws SQLException
/*      */   {
/* 4279 */     throw SQLError.notImplemented();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setResultSetConcurrency(int concurrencyFlag)
/*      */   {
/* 4289 */     this.resultSetConcurrency = concurrencyFlag;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setResultSetType(int typeFlag)
/*      */   {
/* 4299 */     this.resultSetType = typeFlag;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setRetrieveGeneratedKeys(boolean retrieveGeneratedKeys)
/*      */   {
/* 4308 */     this.retrieveGeneratedKeys = retrieveGeneratedKeys;
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
/*      */   private final void setSerializableObject(int parameterIndex, Object parameterObj)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 4328 */       ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
/* 4329 */       ObjectOutputStream objectOut = new ObjectOutputStream(bytesOut);
/* 4330 */       objectOut.writeObject(parameterObj);
/* 4331 */       objectOut.flush();
/* 4332 */       objectOut.close();
/* 4333 */       bytesOut.flush();
/* 4334 */       bytesOut.close();
/*      */       
/* 4336 */       byte[] buf = bytesOut.toByteArray();
/* 4337 */       ByteArrayInputStream bytesIn = new ByteArrayInputStream(buf);
/* 4338 */       setBinaryStream(parameterIndex, bytesIn, buf.length);
/* 4339 */       this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = -2;
/*      */     } catch (Exception ex) {
/* 4341 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("PreparedStatement.54") + ex.getClass().getName(), "S1009", getExceptionInterceptor());
/*      */       
/*      */ 
/* 4344 */       sqlEx.initCause(ex);
/*      */       
/* 4346 */       throw sqlEx;
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
/*      */   public void setShort(int parameterIndex, short x)
/*      */     throws SQLException
/*      */   {
/* 4363 */     setInternal(parameterIndex, String.valueOf(x));
/*      */     
/* 4365 */     this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 5;
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
/*      */   public void setString(int parameterIndex, String x)
/*      */     throws SQLException
/*      */   {
/* 4383 */     if (x == null) {
/* 4384 */       setNull(parameterIndex, 1);
/*      */     } else {
/* 4386 */       checkClosed();
/*      */       
/* 4388 */       int stringLength = x.length();
/*      */       
/* 4390 */       if (this.connection.isNoBackslashEscapesSet())
/*      */       {
/*      */ 
/* 4393 */         boolean needsHexEscape = isEscapeNeededForString(x, stringLength);
/*      */         
/*      */ 
/* 4396 */         if (!needsHexEscape) {
/* 4397 */           byte[] parameterAsBytes = null;
/*      */           
/* 4399 */           StringBuffer quotedString = new StringBuffer(x.length() + 2);
/* 4400 */           quotedString.append('\'');
/* 4401 */           quotedString.append(x);
/* 4402 */           quotedString.append('\'');
/*      */           
/* 4404 */           if (!this.isLoadDataQuery) {
/* 4405 */             parameterAsBytes = StringUtils.getBytes(quotedString.toString(), this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 4411 */             parameterAsBytes = quotedString.toString().getBytes();
/*      */           }
/*      */           
/* 4414 */           setInternal(parameterIndex, parameterAsBytes);
/*      */         } else {
/* 4416 */           byte[] parameterAsBytes = null;
/*      */           
/* 4418 */           if (!this.isLoadDataQuery) {
/* 4419 */             parameterAsBytes = StringUtils.getBytes(x, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 4425 */             parameterAsBytes = x.getBytes();
/*      */           }
/*      */           
/* 4428 */           setBytes(parameterIndex, parameterAsBytes);
/*      */         }
/*      */         
/* 4431 */         return;
/*      */       }
/*      */       
/* 4434 */       String parameterAsString = x;
/* 4435 */       boolean needsQuoted = true;
/*      */       
/* 4437 */       if ((this.isLoadDataQuery) || (isEscapeNeededForString(x, stringLength))) {
/* 4438 */         needsQuoted = false;
/*      */         
/* 4440 */         StringBuffer buf = new StringBuffer((int)(x.length() * 1.1D));
/*      */         
/* 4442 */         buf.append('\'');
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4451 */         for (int i = 0; i < stringLength; i++) {
/* 4452 */           char c = x.charAt(i);
/*      */           
/* 4454 */           switch (c) {
/*      */           case '\000': 
/* 4456 */             buf.append('\\');
/* 4457 */             buf.append('0');
/*      */             
/* 4459 */             break;
/*      */           
/*      */           case '\n': 
/* 4462 */             buf.append('\\');
/* 4463 */             buf.append('n');
/*      */             
/* 4465 */             break;
/*      */           
/*      */           case '\r': 
/* 4468 */             buf.append('\\');
/* 4469 */             buf.append('r');
/*      */             
/* 4471 */             break;
/*      */           
/*      */           case '\\': 
/* 4474 */             buf.append('\\');
/* 4475 */             buf.append('\\');
/*      */             
/* 4477 */             break;
/*      */           
/*      */           case '\'': 
/* 4480 */             buf.append('\\');
/* 4481 */             buf.append('\'');
/*      */             
/* 4483 */             break;
/*      */           
/*      */           case '"': 
/* 4486 */             if (this.usingAnsiMode) {
/* 4487 */               buf.append('\\');
/*      */             }
/*      */             
/* 4490 */             buf.append('"');
/*      */             
/* 4492 */             break;
/*      */           
/*      */           case '\032': 
/* 4495 */             buf.append('\\');
/* 4496 */             buf.append('Z');
/*      */             
/* 4498 */             break;
/*      */           
/*      */ 
/*      */           case '¥': 
/*      */           case '₩': 
/* 4503 */             if (this.charsetEncoder != null) {
/* 4504 */               CharBuffer cbuf = CharBuffer.allocate(1);
/* 4505 */               ByteBuffer bbuf = ByteBuffer.allocate(1);
/* 4506 */               cbuf.put(c);
/* 4507 */               cbuf.position(0);
/* 4508 */               this.charsetEncoder.encode(cbuf, bbuf, true);
/* 4509 */               if (bbuf.get(0) == 92) {
/* 4510 */                 buf.append('\\');
/*      */               }
/*      */             }
/*      */             break;
/*      */           }
/*      */           
/* 4516 */           buf.append(c);
/*      */         }
/*      */         
/*      */ 
/* 4520 */         buf.append('\'');
/*      */         
/* 4522 */         parameterAsString = buf.toString();
/*      */       }
/*      */       
/* 4525 */       byte[] parameterAsBytes = null;
/*      */       
/* 4527 */       if (!this.isLoadDataQuery) {
/* 4528 */         if (needsQuoted) {
/* 4529 */           parameterAsBytes = StringUtils.getBytesWrapped(parameterAsString, '\'', '\'', this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 4534 */           parameterAsBytes = StringUtils.getBytes(parameterAsString, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 4541 */         parameterAsBytes = parameterAsString.getBytes();
/*      */       }
/*      */       
/* 4544 */       setInternal(parameterIndex, parameterAsBytes);
/*      */       
/* 4546 */       this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 12;
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isEscapeNeededForString(String x, int stringLength) {
/* 4551 */     boolean needsHexEscape = false;
/*      */     
/* 4553 */     for (int i = 0; i < stringLength; i++) {
/* 4554 */       char c = x.charAt(i);
/*      */       
/* 4556 */       switch (c)
/*      */       {
/*      */       case '\000': 
/* 4559 */         needsHexEscape = true;
/* 4560 */         break;
/*      */       
/*      */       case '\n': 
/* 4563 */         needsHexEscape = true;
/*      */         
/* 4565 */         break;
/*      */       
/*      */       case '\r': 
/* 4568 */         needsHexEscape = true;
/* 4569 */         break;
/*      */       
/*      */       case '\\': 
/* 4572 */         needsHexEscape = true;
/*      */         
/* 4574 */         break;
/*      */       
/*      */       case '\'': 
/* 4577 */         needsHexEscape = true;
/*      */         
/* 4579 */         break;
/*      */       
/*      */       case '"': 
/* 4582 */         needsHexEscape = true;
/*      */         
/* 4584 */         break;
/*      */       
/*      */       case '\032': 
/* 4587 */         needsHexEscape = true;
/*      */       }
/*      */       
/*      */       
/* 4591 */       if (needsHexEscape) {
/*      */         break;
/*      */       }
/*      */     }
/* 4595 */     return needsHexEscape;
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
/*      */   public void setTime(int parameterIndex, Time x, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 4614 */     setTimeInternal(parameterIndex, x, cal, cal.getTimeZone(), true);
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
/*      */   public void setTime(int parameterIndex, Time x)
/*      */     throws SQLException
/*      */   {
/* 4631 */     setTimeInternal(parameterIndex, x, null, Util.getDefaultTimeZone(), false);
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
/*      */   private void setTimeInternal(int parameterIndex, Time x, Calendar targetCalendar, TimeZone tz, boolean rollForward)
/*      */     throws SQLException
/*      */   {
/* 4652 */     if (x == null) {
/* 4653 */       setNull(parameterIndex, 92);
/*      */     } else {
/* 4655 */       checkClosed();
/*      */       
/* 4657 */       if (!this.useLegacyDatetimeCode) {
/* 4658 */         newSetTimeInternal(parameterIndex, x, targetCalendar);
/*      */       } else {
/* 4660 */         Calendar sessionCalendar = getCalendarInstanceForSessionOrNew();
/*      */         
/* 4662 */         synchronized (sessionCalendar) {
/* 4663 */           x = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4670 */         setInternal(parameterIndex, "'" + x.toString() + "'");
/*      */       }
/*      */       
/* 4673 */       this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 92;
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
/*      */   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 4693 */     setTimestampInternal(parameterIndex, x, cal, cal.getTimeZone(), true);
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
/*      */   public void setTimestamp(int parameterIndex, Timestamp x)
/*      */     throws SQLException
/*      */   {
/* 4710 */     setTimestampInternal(parameterIndex, x, null, Util.getDefaultTimeZone(), false);
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
/*      */   private void setTimestampInternal(int parameterIndex, Timestamp x, Calendar targetCalendar, TimeZone tz, boolean rollForward)
/*      */     throws SQLException
/*      */   {
/* 4730 */     if (x == null) {
/* 4731 */       setNull(parameterIndex, 93);
/*      */     } else {
/* 4733 */       checkClosed();
/*      */       
/* 4735 */       if (!this.useLegacyDatetimeCode) {
/* 4736 */         newSetTimestampInternal(parameterIndex, x, targetCalendar);
/*      */       } else {
/* 4738 */         String timestampString = null;
/*      */         
/* 4740 */         Calendar sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : getCalendarInstanceForSessionOrNew();
/*      */         
/*      */ 
/*      */ 
/* 4744 */         synchronized (sessionCalendar) {
/* 4745 */           x = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4752 */         if (this.connection.getUseSSPSCompatibleTimezoneShift()) {
/* 4753 */           doSSPSCompatibleTimezoneShift(parameterIndex, x, sessionCalendar);
/*      */         } else {
/* 4755 */           synchronized (this) {
/* 4756 */             if (this.tsdf == null) {
/* 4757 */               this.tsdf = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss''", Locale.US);
/*      */             }
/*      */             
/* 4760 */             timestampString = this.tsdf.format(x);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4775 */             setInternal(parameterIndex, timestampString);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4781 */       this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 93;
/*      */     }
/*      */   }
/*      */   
/*      */   private synchronized void newSetTimestampInternal(int parameterIndex, Timestamp x, Calendar targetCalendar) throws SQLException
/*      */   {
/* 4787 */     if (this.tsdf == null) {
/* 4788 */       this.tsdf = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss", Locale.US);
/*      */     }
/*      */     
/* 4791 */     String timestampString = null;
/*      */     
/* 4793 */     if (targetCalendar != null) {
/* 4794 */       targetCalendar.setTime(x);
/* 4795 */       this.tsdf.setTimeZone(targetCalendar.getTimeZone());
/*      */       
/* 4797 */       timestampString = this.tsdf.format(x);
/*      */     } else {
/* 4799 */       this.tsdf.setTimeZone(this.connection.getServerTimezoneTZ());
/* 4800 */       timestampString = this.tsdf.format(x);
/*      */     }
/*      */     
/* 4803 */     StringBuffer buf = new StringBuffer();
/* 4804 */     buf.append(timestampString);
/* 4805 */     buf.append('.');
/* 4806 */     buf.append(formatNanos(x.getNanos()));
/* 4807 */     buf.append('\'');
/*      */     
/* 4809 */     setInternal(parameterIndex, buf.toString());
/*      */   }
/*      */   
/*      */   private String formatNanos(int nanos)
/*      */   {
/* 4814 */     return "0";
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
/*      */   private synchronized void newSetTimeInternal(int parameterIndex, Time x, Calendar targetCalendar)
/*      */     throws SQLException
/*      */   {
/* 4844 */     if (this.tdf == null) {
/* 4845 */       this.tdf = new SimpleDateFormat("''HH:mm:ss''", Locale.US);
/*      */     }
/*      */     
/*      */ 
/* 4849 */     String timeString = null;
/*      */     
/* 4851 */     if (targetCalendar != null) {
/* 4852 */       targetCalendar.setTime(x);
/* 4853 */       this.tdf.setTimeZone(targetCalendar.getTimeZone());
/*      */       
/* 4855 */       timeString = this.tdf.format(x);
/*      */     } else {
/* 4857 */       this.tdf.setTimeZone(this.connection.getServerTimezoneTZ());
/* 4858 */       timeString = this.tdf.format(x);
/*      */     }
/*      */     
/* 4861 */     setInternal(parameterIndex, timeString);
/*      */   }
/*      */   
/*      */   private synchronized void newSetDateInternal(int parameterIndex, java.sql.Date x, Calendar targetCalendar) throws SQLException
/*      */   {
/* 4866 */     if (this.ddf == null) {
/* 4867 */       this.ddf = new SimpleDateFormat("''yyyy-MM-dd''", Locale.US);
/*      */     }
/*      */     
/*      */ 
/* 4871 */     String timeString = null;
/*      */     
/* 4873 */     if (targetCalendar != null) {
/* 4874 */       targetCalendar.setTime(x);
/* 4875 */       this.ddf.setTimeZone(targetCalendar.getTimeZone());
/*      */       
/* 4877 */       timeString = this.ddf.format(x);
/*      */     } else {
/* 4879 */       this.ddf.setTimeZone(this.connection.getServerTimezoneTZ());
/* 4880 */       timeString = this.ddf.format(x);
/*      */     }
/*      */     
/* 4883 */     setInternal(parameterIndex, timeString);
/*      */   }
/*      */   
/*      */   private void doSSPSCompatibleTimezoneShift(int parameterIndex, Timestamp x, Calendar sessionCalendar) throws SQLException {
/* 4887 */     Calendar sessionCalendar2 = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : getCalendarInstanceForSessionOrNew();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4892 */     synchronized (sessionCalendar2) {
/* 4893 */       java.util.Date oldTime = sessionCalendar2.getTime();
/*      */       try
/*      */       {
/* 4896 */         sessionCalendar2.setTime(x);
/*      */         
/* 4898 */         int year = sessionCalendar2.get(1);
/* 4899 */         int month = sessionCalendar2.get(2) + 1;
/* 4900 */         int date = sessionCalendar2.get(5);
/*      */         
/* 4902 */         int hour = sessionCalendar2.get(11);
/* 4903 */         int minute = sessionCalendar2.get(12);
/* 4904 */         int seconds = sessionCalendar2.get(13);
/*      */         
/* 4906 */         StringBuffer tsBuf = new StringBuffer();
/*      */         
/* 4908 */         tsBuf.append('\'');
/* 4909 */         tsBuf.append(year);
/*      */         
/* 4911 */         tsBuf.append("-");
/*      */         
/* 4913 */         if (month < 10) {
/* 4914 */           tsBuf.append('0');
/*      */         }
/*      */         
/* 4917 */         tsBuf.append(month);
/*      */         
/* 4919 */         tsBuf.append('-');
/*      */         
/* 4921 */         if (date < 10) {
/* 4922 */           tsBuf.append('0');
/*      */         }
/*      */         
/* 4925 */         tsBuf.append(date);
/*      */         
/* 4927 */         tsBuf.append(' ');
/*      */         
/* 4929 */         if (hour < 10) {
/* 4930 */           tsBuf.append('0');
/*      */         }
/*      */         
/* 4933 */         tsBuf.append(hour);
/*      */         
/* 4935 */         tsBuf.append(':');
/*      */         
/* 4937 */         if (minute < 10) {
/* 4938 */           tsBuf.append('0');
/*      */         }
/*      */         
/* 4941 */         tsBuf.append(minute);
/*      */         
/* 4943 */         tsBuf.append(':');
/*      */         
/* 4945 */         if (seconds < 10) {
/* 4946 */           tsBuf.append('0');
/*      */         }
/*      */         
/* 4949 */         tsBuf.append(seconds);
/*      */         
/* 4951 */         tsBuf.append('.');
/* 4952 */         tsBuf.append(formatNanos(x.getNanos()));
/* 4953 */         tsBuf.append('\'');
/*      */         
/* 4955 */         setInternal(parameterIndex, tsBuf.toString());
/*      */       }
/*      */       finally {
/* 4958 */         sessionCalendar.setTime(oldTime);
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
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public void setUnicodeStream(int parameterIndex, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 4989 */     if (x == null) {
/* 4990 */       setNull(parameterIndex, 12);
/*      */     } else {
/* 4992 */       setBinaryStream(parameterIndex, x, length);
/*      */       
/* 4994 */       this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 2005;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void setURL(int parameterIndex, URL arg)
/*      */     throws SQLException
/*      */   {
/* 5002 */     if (arg != null) {
/* 5003 */       setString(parameterIndex, arg.toString());
/*      */       
/* 5005 */       this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 70;
/*      */     } else {
/* 5007 */       setNull(parameterIndex, 1);
/*      */     }
/*      */   }
/*      */   
/*      */   private final void streamToBytes(Buffer packet, InputStream in, boolean escape, int streamLength, boolean useLength) throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 5015 */       String connectionEncoding = this.connection.getEncoding();
/*      */       
/* 5017 */       boolean hexEscape = false;
/*      */       
/* 5019 */       if ((this.connection.isNoBackslashEscapesSet()) || ((this.connection.getUseUnicode()) && (connectionEncoding != null) && (CharsetMapping.isMultibyteCharset(connectionEncoding)) && (!this.connection.parserKnowsUnicode())))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 5024 */         hexEscape = true;
/*      */       }
/*      */       
/* 5027 */       if (streamLength == -1) {
/* 5028 */         useLength = false;
/*      */       }
/*      */       
/* 5031 */       int bc = -1;
/*      */       
/* 5033 */       if (useLength) {
/* 5034 */         bc = readblock(in, this.streamConvertBuf, streamLength);
/*      */       } else {
/* 5036 */         bc = readblock(in, this.streamConvertBuf);
/*      */       }
/*      */       
/* 5039 */       int lengthLeftToRead = streamLength - bc;
/*      */       
/* 5041 */       if (hexEscape) {
/* 5042 */         packet.writeStringNoNull("x");
/* 5043 */       } else if (this.connection.getIO().versionMeetsMinimum(4, 1, 0)) {
/* 5044 */         packet.writeStringNoNull("_binary");
/*      */       }
/*      */       
/* 5047 */       if (escape) {
/* 5048 */         packet.writeByte((byte)39);
/*      */       }
/*      */       
/* 5051 */       while (bc > 0) {
/* 5052 */         if (hexEscape) {
/* 5053 */           hexEscapeBlock(this.streamConvertBuf, packet, bc);
/* 5054 */         } else if (escape) {
/* 5055 */           escapeblockFast(this.streamConvertBuf, packet, bc);
/*      */         } else {
/* 5057 */           packet.writeBytesNoNull(this.streamConvertBuf, 0, bc);
/*      */         }
/*      */         
/* 5060 */         if (useLength) {
/* 5061 */           bc = readblock(in, this.streamConvertBuf, lengthLeftToRead);
/*      */           
/* 5063 */           if (bc > 0) {
/* 5064 */             lengthLeftToRead -= bc;
/*      */           }
/*      */         } else {
/* 5067 */           bc = readblock(in, this.streamConvertBuf);
/*      */         }
/*      */       }
/*      */       
/* 5071 */       if (escape) {
/* 5072 */         packet.writeByte((byte)39);
/*      */       }
/*      */     } finally {
/* 5075 */       if (this.connection.getAutoClosePStmtStreams()) {
/*      */         try {
/* 5077 */           in.close();
/*      */         }
/*      */         catch (IOException ioEx) {}
/*      */         
/*      */ 
/* 5082 */         in = null;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private final byte[] streamToBytes(InputStream in, boolean escape, int streamLength, boolean useLength) throws SQLException
/*      */   {
/*      */     try {
/* 5090 */       if (streamLength == -1) {
/* 5091 */         useLength = false;
/*      */       }
/*      */       
/* 5094 */       ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
/*      */       
/* 5096 */       int bc = -1;
/*      */       
/* 5098 */       if (useLength) {
/* 5099 */         bc = readblock(in, this.streamConvertBuf, streamLength);
/*      */       } else {
/* 5101 */         bc = readblock(in, this.streamConvertBuf);
/*      */       }
/*      */       
/* 5104 */       int lengthLeftToRead = streamLength - bc;
/*      */       
/* 5106 */       if (escape) {
/* 5107 */         if (this.connection.versionMeetsMinimum(4, 1, 0)) {
/* 5108 */           bytesOut.write(95);
/* 5109 */           bytesOut.write(98);
/* 5110 */           bytesOut.write(105);
/* 5111 */           bytesOut.write(110);
/* 5112 */           bytesOut.write(97);
/* 5113 */           bytesOut.write(114);
/* 5114 */           bytesOut.write(121);
/*      */         }
/*      */         
/* 5117 */         bytesOut.write(39);
/*      */       }
/*      */       
/* 5120 */       while (bc > 0) {
/* 5121 */         if (escape) {
/* 5122 */           escapeblockFast(this.streamConvertBuf, bytesOut, bc);
/*      */         } else {
/* 5124 */           bytesOut.write(this.streamConvertBuf, 0, bc);
/*      */         }
/*      */         
/* 5127 */         if (useLength) {
/* 5128 */           bc = readblock(in, this.streamConvertBuf, lengthLeftToRead);
/*      */           
/* 5130 */           if (bc > 0) {
/* 5131 */             lengthLeftToRead -= bc;
/*      */           }
/*      */         } else {
/* 5134 */           bc = readblock(in, this.streamConvertBuf);
/*      */         }
/*      */       }
/*      */       
/* 5138 */       if (escape) {
/* 5139 */         bytesOut.write(39);
/*      */       }
/*      */       
/* 5142 */       return bytesOut.toByteArray();
/*      */     } finally {
/* 5144 */       if (this.connection.getAutoClosePStmtStreams()) {
/*      */         try {
/* 5146 */           in.close();
/*      */         }
/*      */         catch (IOException ioEx) {}
/*      */         
/*      */ 
/* 5151 */         in = null;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 5162 */     StringBuffer buf = new StringBuffer();
/* 5163 */     buf.append(super.toString());
/* 5164 */     buf.append(": ");
/*      */     try
/*      */     {
/* 5167 */       buf.append(asSql());
/*      */     } catch (SQLException sqlEx) {
/* 5169 */       buf.append("EXCEPTION: " + sqlEx.toString());
/*      */     }
/*      */     
/* 5172 */     return buf.toString();
/*      */   }
/*      */   
/*      */   public synchronized boolean isClosed()
/*      */     throws SQLException
/*      */   {
/* 5178 */     return this.isClosed;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int getParameterIndexOffset()
/*      */   {
/* 5189 */     return 0;
/*      */   }
/*      */   
/*      */   public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
/* 5193 */     setAsciiStream(parameterIndex, x, -1);
/*      */   }
/*      */   
/*      */   public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
/* 5197 */     setAsciiStream(parameterIndex, x, (int)length);
/* 5198 */     this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 2005;
/*      */   }
/*      */   
/*      */   public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
/* 5202 */     setBinaryStream(parameterIndex, x, -1);
/*      */   }
/*      */   
/*      */   public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
/* 5206 */     setBinaryStream(parameterIndex, x, (int)length);
/*      */   }
/*      */   
/*      */   public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
/* 5210 */     setBinaryStream(parameterIndex, inputStream);
/*      */   }
/*      */   
/*      */   public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
/* 5214 */     setCharacterStream(parameterIndex, reader, -1);
/*      */   }
/*      */   
/*      */   public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
/* 5218 */     setCharacterStream(parameterIndex, reader, (int)length);
/*      */   }
/*      */   
/*      */   public void setClob(int parameterIndex, Reader reader) throws SQLException
/*      */   {
/* 5223 */     setCharacterStream(parameterIndex, reader);
/*      */   }
/*      */   
/*      */   public void setClob(int parameterIndex, Reader reader, long length)
/*      */     throws SQLException
/*      */   {
/* 5229 */     setCharacterStream(parameterIndex, reader, length);
/*      */   }
/*      */   
/*      */   public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
/* 5233 */     setNCharacterStream(parameterIndex, value, -1L);
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
/*      */   public void setNString(int parameterIndex, String x)
/*      */     throws SQLException
/*      */   {
/* 5251 */     if ((this.charEncoding.equalsIgnoreCase("UTF-8")) || (this.charEncoding.equalsIgnoreCase("utf8")))
/*      */     {
/* 5253 */       setString(parameterIndex, x);
/* 5254 */       return;
/*      */     }
/*      */     
/*      */ 
/* 5258 */     if (x == null) {
/* 5259 */       setNull(parameterIndex, 1);
/*      */     } else {
/* 5261 */       int stringLength = x.length();
/*      */       
/*      */ 
/*      */ 
/* 5265 */       StringBuffer buf = new StringBuffer((int)(x.length() * 1.1D + 4.0D));
/* 5266 */       buf.append("_utf8");
/* 5267 */       buf.append('\'');
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5276 */       for (int i = 0; i < stringLength; i++) {
/* 5277 */         char c = x.charAt(i);
/*      */         
/* 5279 */         switch (c) {
/*      */         case '\000': 
/* 5281 */           buf.append('\\');
/* 5282 */           buf.append('0');
/*      */           
/* 5284 */           break;
/*      */         
/*      */         case '\n': 
/* 5287 */           buf.append('\\');
/* 5288 */           buf.append('n');
/*      */           
/* 5290 */           break;
/*      */         
/*      */         case '\r': 
/* 5293 */           buf.append('\\');
/* 5294 */           buf.append('r');
/*      */           
/* 5296 */           break;
/*      */         
/*      */         case '\\': 
/* 5299 */           buf.append('\\');
/* 5300 */           buf.append('\\');
/*      */           
/* 5302 */           break;
/*      */         
/*      */         case '\'': 
/* 5305 */           buf.append('\\');
/* 5306 */           buf.append('\'');
/*      */           
/* 5308 */           break;
/*      */         
/*      */         case '"': 
/* 5311 */           if (this.usingAnsiMode) {
/* 5312 */             buf.append('\\');
/*      */           }
/*      */           
/* 5315 */           buf.append('"');
/*      */           
/* 5317 */           break;
/*      */         
/*      */         case '\032': 
/* 5320 */           buf.append('\\');
/* 5321 */           buf.append('Z');
/*      */           
/* 5323 */           break;
/*      */         
/*      */         default: 
/* 5326 */           buf.append(c);
/*      */         }
/*      */         
/*      */       }
/* 5330 */       buf.append('\'');
/*      */       
/* 5332 */       String parameterAsString = buf.toString();
/*      */       
/* 5334 */       byte[] parameterAsBytes = null;
/*      */       
/* 5336 */       if (!this.isLoadDataQuery) {
/* 5337 */         parameterAsBytes = StringUtils.getBytes(parameterAsString, this.connection.getCharsetConverter("UTF-8"), "UTF-8", this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 5343 */         parameterAsBytes = parameterAsString.getBytes();
/*      */       }
/*      */       
/* 5346 */       setInternal(parameterIndex, parameterAsBytes);
/*      */       
/* 5348 */       this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = -9;
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
/*      */   public void setNCharacterStream(int parameterIndex, Reader reader, long length)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 5377 */       if (reader == null) {
/* 5378 */         setNull(parameterIndex, -1);
/*      */       }
/*      */       else {
/* 5381 */         char[] c = null;
/* 5382 */         int len = 0;
/*      */         
/* 5384 */         boolean useLength = this.connection.getUseStreamLengthsInPrepStmts();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 5389 */         if ((useLength) && (length != -1L)) {
/* 5390 */           c = new char[(int)length];
/*      */           
/* 5392 */           int numCharsRead = readFully(reader, c, (int)length);
/*      */           
/*      */ 
/*      */ 
/* 5396 */           setNString(parameterIndex, new String(c, 0, numCharsRead));
/*      */         }
/*      */         else {
/* 5399 */           c = new char['က'];
/*      */           
/* 5401 */           StringBuffer buf = new StringBuffer();
/*      */           
/* 5403 */           while ((len = reader.read(c)) != -1) {
/* 5404 */             buf.append(c, 0, len);
/*      */           }
/*      */           
/* 5407 */           setNString(parameterIndex, buf.toString());
/*      */         }
/*      */         
/* 5410 */         this.parameterTypes[(parameterIndex - 1 + getParameterIndexOffset())] = 2011;
/*      */       }
/*      */     } catch (IOException ioEx) {
/* 5413 */       throw SQLError.createSQLException(ioEx.toString(), "S1000", getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */   
/*      */   public void setNClob(int parameterIndex, Reader reader) throws SQLException
/*      */   {
/* 5419 */     setNCharacterStream(parameterIndex, reader);
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
/*      */   public void setNClob(int parameterIndex, Reader reader, long length)
/*      */     throws SQLException
/*      */   {
/* 5437 */     if (reader == null) {
/* 5438 */       setNull(parameterIndex, -1);
/*      */     } else {
/* 5440 */       setNCharacterStream(parameterIndex, reader, length);
/*      */     }
/*      */   }
/*      */   
/*      */   public ParameterBindings getParameterBindings() throws SQLException {
/* 5445 */     return new EmulatedPreparedStatementBindings();
/*      */   }
/*      */   
/*      */   class EmulatedPreparedStatementBindings implements ParameterBindings
/*      */   {
/*      */     private ResultSetImpl bindingsAsRs;
/*      */     private boolean[] parameterIsNull;
/*      */     
/*      */     public EmulatedPreparedStatementBindings() throws SQLException {
/* 5454 */       List rows = new ArrayList();
/* 5455 */       this.parameterIsNull = new boolean[PreparedStatement.this.parameterCount];
/* 5456 */       System.arraycopy(PreparedStatement.this.isNull, 0, this.parameterIsNull, 0, PreparedStatement.this.parameterCount);
/*      */       
/*      */ 
/* 5459 */       byte[][] rowData = new byte[PreparedStatement.this.parameterCount][];
/* 5460 */       Field[] typeMetadata = new Field[PreparedStatement.this.parameterCount];
/*      */       
/* 5462 */       for (int i = 0; i < PreparedStatement.this.parameterCount; i++) {
/* 5463 */         if (PreparedStatement.this.batchCommandIndex == -1) {
/* 5464 */           rowData[i] = PreparedStatement.this.getBytesRepresentation(i);
/*      */         } else {
/* 5466 */           rowData[i] = PreparedStatement.this.getBytesRepresentationForBatch(i, PreparedStatement.this.batchCommandIndex);
/*      */         }
/* 5468 */         int charsetIndex = 0;
/*      */         
/* 5470 */         if ((PreparedStatement.this.parameterTypes[i] == -2) || (PreparedStatement.this.parameterTypes[i] == 2004))
/*      */         {
/* 5472 */           charsetIndex = 63;
/*      */         } else {
/* 5474 */           String mysqlEncodingName = CharsetMapping.getMysqlEncodingForJavaEncoding(PreparedStatement.this.connection.getEncoding(), PreparedStatement.this.connection);
/*      */           
/*      */ 
/* 5477 */           charsetIndex = CharsetMapping.getCharsetIndexForMysqlEncodingName(mysqlEncodingName);
/*      */         }
/*      */         
/*      */ 
/* 5481 */         Field parameterMetadata = new Field(null, "parameter_" + (i + 1), charsetIndex, PreparedStatement.this.parameterTypes[i], rowData[i].length);
/*      */         
/*      */ 
/* 5484 */         parameterMetadata.setConnection(PreparedStatement.this.connection);
/* 5485 */         typeMetadata[i] = parameterMetadata;
/*      */       }
/*      */       
/* 5488 */       rows.add(new ByteArrayRow(rowData, PreparedStatement.this.getExceptionInterceptor()));
/*      */       
/* 5490 */       this.bindingsAsRs = new ResultSetImpl(PreparedStatement.this.connection.getCatalog(), typeMetadata, new RowDataStatic(rows), PreparedStatement.this.connection, null);
/*      */       
/* 5492 */       this.bindingsAsRs.next();
/*      */     }
/*      */     
/*      */     public Array getArray(int parameterIndex) throws SQLException {
/* 5496 */       return this.bindingsAsRs.getArray(parameterIndex);
/*      */     }
/*      */     
/*      */     public InputStream getAsciiStream(int parameterIndex) throws SQLException
/*      */     {
/* 5501 */       return this.bindingsAsRs.getAsciiStream(parameterIndex);
/*      */     }
/*      */     
/*      */     public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
/* 5505 */       return this.bindingsAsRs.getBigDecimal(parameterIndex);
/*      */     }
/*      */     
/*      */     public InputStream getBinaryStream(int parameterIndex) throws SQLException
/*      */     {
/* 5510 */       return this.bindingsAsRs.getBinaryStream(parameterIndex);
/*      */     }
/*      */     
/*      */     public Blob getBlob(int parameterIndex) throws SQLException {
/* 5514 */       return this.bindingsAsRs.getBlob(parameterIndex);
/*      */     }
/*      */     
/*      */     public boolean getBoolean(int parameterIndex) throws SQLException {
/* 5518 */       return this.bindingsAsRs.getBoolean(parameterIndex);
/*      */     }
/*      */     
/*      */     public byte getByte(int parameterIndex) throws SQLException {
/* 5522 */       return this.bindingsAsRs.getByte(parameterIndex);
/*      */     }
/*      */     
/*      */     public byte[] getBytes(int parameterIndex) throws SQLException {
/* 5526 */       return this.bindingsAsRs.getBytes(parameterIndex);
/*      */     }
/*      */     
/*      */     public Reader getCharacterStream(int parameterIndex) throws SQLException
/*      */     {
/* 5531 */       return this.bindingsAsRs.getCharacterStream(parameterIndex);
/*      */     }
/*      */     
/*      */     public Clob getClob(int parameterIndex) throws SQLException {
/* 5535 */       return this.bindingsAsRs.getClob(parameterIndex);
/*      */     }
/*      */     
/*      */     public java.sql.Date getDate(int parameterIndex) throws SQLException {
/* 5539 */       return this.bindingsAsRs.getDate(parameterIndex);
/*      */     }
/*      */     
/*      */     public double getDouble(int parameterIndex) throws SQLException {
/* 5543 */       return this.bindingsAsRs.getDouble(parameterIndex);
/*      */     }
/*      */     
/*      */     public float getFloat(int parameterIndex) throws SQLException {
/* 5547 */       return this.bindingsAsRs.getFloat(parameterIndex);
/*      */     }
/*      */     
/*      */     public int getInt(int parameterIndex) throws SQLException {
/* 5551 */       return this.bindingsAsRs.getInt(parameterIndex);
/*      */     }
/*      */     
/*      */     public long getLong(int parameterIndex) throws SQLException {
/* 5555 */       return this.bindingsAsRs.getLong(parameterIndex);
/*      */     }
/*      */     
/*      */     public Reader getNCharacterStream(int parameterIndex) throws SQLException
/*      */     {
/* 5560 */       return this.bindingsAsRs.getCharacterStream(parameterIndex);
/*      */     }
/*      */     
/*      */     public Reader getNClob(int parameterIndex) throws SQLException {
/* 5564 */       return this.bindingsAsRs.getCharacterStream(parameterIndex);
/*      */     }
/*      */     
/*      */     public Object getObject(int parameterIndex) throws SQLException {
/* 5568 */       PreparedStatement.this.checkBounds(parameterIndex, 0);
/*      */       
/* 5570 */       if (this.parameterIsNull[(parameterIndex - 1)] != 0) {
/* 5571 */         return null;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5578 */       switch (PreparedStatement.this.parameterTypes[(parameterIndex - 1)]) {
/*      */       case -6: 
/* 5580 */         return new Byte(getByte(parameterIndex));
/*      */       case 5: 
/* 5582 */         return new Short(getShort(parameterIndex));
/*      */       case 4: 
/* 5584 */         return new Integer(getInt(parameterIndex));
/*      */       case -5: 
/* 5586 */         return new Long(getLong(parameterIndex));
/*      */       case 6: 
/* 5588 */         return new Float(getFloat(parameterIndex));
/*      */       case 8: 
/* 5590 */         return new Double(getDouble(parameterIndex));
/*      */       }
/* 5592 */       return this.bindingsAsRs.getObject(parameterIndex);
/*      */     }
/*      */     
/*      */     public Ref getRef(int parameterIndex) throws SQLException
/*      */     {
/* 5597 */       return this.bindingsAsRs.getRef(parameterIndex);
/*      */     }
/*      */     
/*      */     public short getShort(int parameterIndex) throws SQLException {
/* 5601 */       return this.bindingsAsRs.getShort(parameterIndex);
/*      */     }
/*      */     
/*      */     public String getString(int parameterIndex) throws SQLException {
/* 5605 */       return this.bindingsAsRs.getString(parameterIndex);
/*      */     }
/*      */     
/*      */     public Time getTime(int parameterIndex) throws SQLException {
/* 5609 */       return this.bindingsAsRs.getTime(parameterIndex);
/*      */     }
/*      */     
/*      */     public Timestamp getTimestamp(int parameterIndex) throws SQLException {
/* 5613 */       return this.bindingsAsRs.getTimestamp(parameterIndex);
/*      */     }
/*      */     
/*      */     public URL getURL(int parameterIndex) throws SQLException {
/* 5617 */       return this.bindingsAsRs.getURL(parameterIndex);
/*      */     }
/*      */     
/*      */     public boolean isNull(int parameterIndex) throws SQLException {
/* 5621 */       PreparedStatement.this.checkBounds(parameterIndex, 0);
/*      */       
/* 5623 */       return this.parameterIsNull[(parameterIndex - 1)];
/*      */     }
/*      */   }
/*      */   
/*      */   public String getPreparedSql() {
/* 5628 */     if (this.rewrittenBatchSize == 0) {
/* 5629 */       return this.originalSql;
/*      */     }
/*      */     try
/*      */     {
/* 5633 */       return this.parseInfo.getSqlForBatch(this.parseInfo);
/*      */     } catch (UnsupportedEncodingException e) {
/* 5635 */       throw new RuntimeException(e);
/*      */     }
/*      */   }
/*      */   
/*      */   public int getUpdateCount() throws SQLException {
/* 5640 */     int count = super.getUpdateCount();
/*      */     
/* 5642 */     if ((containsOnDuplicateKeyUpdateInSQL()) && (this.compensateForOnDuplicateKeyUpdate))
/*      */     {
/* 5644 */       if ((count == 2) || (count == 0)) {
/* 5645 */         count = 1;
/*      */       }
/*      */     }
/*      */     
/* 5649 */     return count;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected static boolean canRewrite(String sql, boolean isOnDuplicateKeyUpdate, int locationOfOnDuplicateKeyUpdate, int statementStartPos)
/*      */   {
/* 5656 */     boolean rewritableOdku = true;
/*      */     
/* 5658 */     if (isOnDuplicateKeyUpdate) {
/* 5659 */       int updateClausePos = StringUtils.indexOfIgnoreCase(locationOfOnDuplicateKeyUpdate, sql, " UPDATE ");
/*      */       
/*      */ 
/* 5662 */       if (updateClausePos != -1) {
/* 5663 */         rewritableOdku = StringUtils.indexOfIgnoreCaseRespectMarker(updateClausePos, sql, "LAST_INSERT_ID", "\"'`", "\"'`", false) == -1;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 5670 */     return (StringUtils.startsWithIgnoreCaseAndWs(sql, "INSERT", statementStartPos)) && (StringUtils.indexOfIgnoreCaseRespectMarker(statementStartPos, sql, "SELECT", "\"'`", "\"'`", false) == -1) && (rewritableOdku);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   protected int[] executePreparedBatchAsMultiStatement(int batchTimeout)
/*      */     throws SQLException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 185	com/mysql/jdbc/PreparedStatement:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   4: invokeinterface 430 1 0
/*      */     //   9: dup
/*      */     //   10: astore_2
/*      */     //   11: monitorenter
/*      */     //   12: aload_0
/*      */     //   13: getfield 589	com/mysql/jdbc/PreparedStatement:batchedValuesClause	Ljava/lang/String;
/*      */     //   16: ifnonnull +30 -> 46
/*      */     //   19: aload_0
/*      */     //   20: new 352	java/lang/StringBuilder
/*      */     //   23: dup
/*      */     //   24: invokespecial 353	java/lang/StringBuilder:<init>	()V
/*      */     //   27: aload_0
/*      */     //   28: getfield 159	com/mysql/jdbc/PreparedStatement:originalSql	Ljava/lang/String;
/*      */     //   31: invokevirtual 358	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   34: ldc_w 591
/*      */     //   37: invokevirtual 358	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   40: invokevirtual 362	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   43: putfield 589	com/mysql/jdbc/PreparedStatement:batchedValuesClause	Ljava/lang/String;
/*      */     //   46: aload_0
/*      */     //   47: getfield 185	com/mysql/jdbc/PreparedStatement:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   50: astore_3
/*      */     //   51: aload_3
/*      */     //   52: invokeinterface 594 1 0
/*      */     //   57: istore 4
/*      */     //   59: aconst_null
/*      */     //   60: astore 5
/*      */     //   62: aload_0
/*      */     //   63: invokevirtual 445	com/mysql/jdbc/PreparedStatement:clearWarnings	()V
/*      */     //   66: aload_0
/*      */     //   67: getfield 272	com/mysql/jdbc/PreparedStatement:batchedArgs	Ljava/util/List;
/*      */     //   70: invokeinterface 556 1 0
/*      */     //   75: istore 6
/*      */     //   77: aload_0
/*      */     //   78: getfield 436	com/mysql/jdbc/PreparedStatement:retrieveGeneratedKeys	Z
/*      */     //   81: ifeq +16 -> 97
/*      */     //   84: aload_0
/*      */     //   85: new 274	java/util/ArrayList
/*      */     //   88: dup
/*      */     //   89: iload 6
/*      */     //   91: invokespecial 596	java/util/ArrayList:<init>	(I)V
/*      */     //   94: putfield 460	com/mysql/jdbc/PreparedStatement:batchedGeneratedKeys	Ljava/util/ArrayList;
/*      */     //   97: aload_0
/*      */     //   98: iload 6
/*      */     //   100: invokevirtual 600	com/mysql/jdbc/PreparedStatement:computeBatchSize	(I)I
/*      */     //   103: istore 7
/*      */     //   105: iload 6
/*      */     //   107: iload 7
/*      */     //   109: if_icmpge +7 -> 116
/*      */     //   112: iload 6
/*      */     //   114: istore 7
/*      */     //   116: aconst_null
/*      */     //   117: astore 8
/*      */     //   119: iconst_1
/*      */     //   120: istore 9
/*      */     //   122: iconst_0
/*      */     //   123: istore 10
/*      */     //   125: iconst_0
/*      */     //   126: istore 11
/*      */     //   128: iconst_0
/*      */     //   129: istore 12
/*      */     //   131: iload 6
/*      */     //   133: newarray <illegal type>
/*      */     //   135: astore 13
/*      */     //   137: aconst_null
/*      */     //   138: astore 14
/*      */     //   140: iload 4
/*      */     //   142: ifne +12 -> 154
/*      */     //   145: aload_3
/*      */     //   146: invokeinterface 604 1 0
/*      */     //   151: invokevirtual 609	com/mysql/jdbc/MysqlIO:enableMultiQueries	()V
/*      */     //   154: aload_0
/*      */     //   155: getfield 436	com/mysql/jdbc/PreparedStatement:retrieveGeneratedKeys	Z
/*      */     //   158: ifeq +21 -> 179
/*      */     //   161: aload_3
/*      */     //   162: aload_0
/*      */     //   163: iload 7
/*      */     //   165: invokespecial 613	com/mysql/jdbc/PreparedStatement:generateMultiStatementForBatch	(I)Ljava/lang/String;
/*      */     //   168: iconst_1
/*      */     //   169: invokeinterface 617 3 0
/*      */     //   174: astore 8
/*      */     //   176: goto +17 -> 193
/*      */     //   179: aload_3
/*      */     //   180: aload_0
/*      */     //   181: iload 7
/*      */     //   183: invokespecial 613	com/mysql/jdbc/PreparedStatement:generateMultiStatementForBatch	(I)Ljava/lang/String;
/*      */     //   186: invokeinterface 620 2 0
/*      */     //   191: astore 8
/*      */     //   193: aload_3
/*      */     //   194: invokeinterface 623 1 0
/*      */     //   199: ifeq +47 -> 246
/*      */     //   202: iload_1
/*      */     //   203: ifeq +43 -> 246
/*      */     //   206: aload_3
/*      */     //   207: iconst_5
/*      */     //   208: iconst_0
/*      */     //   209: iconst_0
/*      */     //   210: invokeinterface 225 4 0
/*      */     //   215: ifeq +31 -> 246
/*      */     //   218: new 27	com/mysql/jdbc/StatementImpl$CancelTask
/*      */     //   221: dup
/*      */     //   222: aload_0
/*      */     //   223: aload 8
/*      */     //   225: checkcast 4	com/mysql/jdbc/StatementImpl
/*      */     //   228: invokespecial 626	com/mysql/jdbc/StatementImpl$CancelTask:<init>	(Lcom/mysql/jdbc/StatementImpl;Lcom/mysql/jdbc/StatementImpl;)V
/*      */     //   231: astore 5
/*      */     //   233: aload_3
/*      */     //   234: invokeinterface 630 1 0
/*      */     //   239: aload 5
/*      */     //   241: iload_1
/*      */     //   242: i2l
/*      */     //   243: invokevirtual 636	java/util/Timer:schedule	(Ljava/util/TimerTask;J)V
/*      */     //   246: iload 6
/*      */     //   248: iload 7
/*      */     //   250: if_icmpge +10 -> 260
/*      */     //   253: iload 6
/*      */     //   255: istore 10
/*      */     //   257: goto +10 -> 267
/*      */     //   260: iload 6
/*      */     //   262: iload 7
/*      */     //   264: idiv
/*      */     //   265: istore 10
/*      */     //   267: iload 10
/*      */     //   269: iload 7
/*      */     //   271: imul
/*      */     //   272: istore 15
/*      */     //   274: iconst_0
/*      */     //   275: istore 16
/*      */     //   277: iload 16
/*      */     //   279: iload 15
/*      */     //   281: if_icmpge +98 -> 379
/*      */     //   284: iload 16
/*      */     //   286: ifeq +63 -> 349
/*      */     //   289: iload 16
/*      */     //   291: iload 7
/*      */     //   293: irem
/*      */     //   294: ifne +55 -> 349
/*      */     //   297: aload 8
/*      */     //   299: invokeinterface 638 1 0
/*      */     //   304: pop
/*      */     //   305: goto +19 -> 324
/*      */     //   308: astore 17
/*      */     //   310: aload_0
/*      */     //   311: iload 11
/*      */     //   313: iload 7
/*      */     //   315: aload 13
/*      */     //   317: aload 17
/*      */     //   319: invokevirtual 642	com/mysql/jdbc/PreparedStatement:handleExceptionForBatch	(II[ILjava/sql/SQLException;)Ljava/sql/SQLException;
/*      */     //   322: astore 14
/*      */     //   324: aload_0
/*      */     //   325: aload 8
/*      */     //   327: checkcast 4	com/mysql/jdbc/StatementImpl
/*      */     //   330: iload 12
/*      */     //   332: aload 13
/*      */     //   334: invokevirtual 646	com/mysql/jdbc/PreparedStatement:processMultiCountsAndKeys	(Lcom/mysql/jdbc/StatementImpl;I[I)I
/*      */     //   337: istore 12
/*      */     //   339: aload 8
/*      */     //   341: invokeinterface 648 1 0
/*      */     //   346: iconst_1
/*      */     //   347: istore 9
/*      */     //   349: aload_0
/*      */     //   350: aload 8
/*      */     //   352: iload 9
/*      */     //   354: aload_0
/*      */     //   355: getfield 272	com/mysql/jdbc/PreparedStatement:batchedArgs	Ljava/util/List;
/*      */     //   358: iload 11
/*      */     //   360: iinc 11 1
/*      */     //   363: invokeinterface 317 2 0
/*      */     //   368: invokevirtual 652	com/mysql/jdbc/PreparedStatement:setOneBatchedParameterSet	(Ljava/sql/PreparedStatement;ILjava/lang/Object;)I
/*      */     //   371: istore 9
/*      */     //   373: iinc 16 1
/*      */     //   376: goto -99 -> 277
/*      */     //   379: aload 8
/*      */     //   381: invokeinterface 638 1 0
/*      */     //   386: pop
/*      */     //   387: goto +21 -> 408
/*      */     //   390: astore 16
/*      */     //   392: aload_0
/*      */     //   393: iload 11
/*      */     //   395: iconst_1
/*      */     //   396: isub
/*      */     //   397: iload 7
/*      */     //   399: aload 13
/*      */     //   401: aload 16
/*      */     //   403: invokevirtual 642	com/mysql/jdbc/PreparedStatement:handleExceptionForBatch	(II[ILjava/sql/SQLException;)Ljava/sql/SQLException;
/*      */     //   406: astore 14
/*      */     //   408: aload_0
/*      */     //   409: aload 8
/*      */     //   411: checkcast 4	com/mysql/jdbc/StatementImpl
/*      */     //   414: iload 12
/*      */     //   416: aload 13
/*      */     //   418: invokevirtual 646	com/mysql/jdbc/PreparedStatement:processMultiCountsAndKeys	(Lcom/mysql/jdbc/StatementImpl;I[I)I
/*      */     //   421: istore 12
/*      */     //   423: aload 8
/*      */     //   425: invokeinterface 648 1 0
/*      */     //   430: iload 6
/*      */     //   432: iload 11
/*      */     //   434: isub
/*      */     //   435: istore 7
/*      */     //   437: jsr +14 -> 451
/*      */     //   440: goto +27 -> 467
/*      */     //   443: astore 18
/*      */     //   445: jsr +6 -> 451
/*      */     //   448: aload 18
/*      */     //   450: athrow
/*      */     //   451: astore 19
/*      */     //   453: aload 8
/*      */     //   455: ifnull +10 -> 465
/*      */     //   458: aload 8
/*      */     //   460: invokeinterface 654 1 0
/*      */     //   465: ret 19
/*      */     //   467: iload 7
/*      */     //   469: ifle +145 -> 614
/*      */     //   472: aload_0
/*      */     //   473: getfield 436	com/mysql/jdbc/PreparedStatement:retrieveGeneratedKeys	Z
/*      */     //   476: ifeq +21 -> 497
/*      */     //   479: aload_3
/*      */     //   480: aload_0
/*      */     //   481: iload 7
/*      */     //   483: invokespecial 613	com/mysql/jdbc/PreparedStatement:generateMultiStatementForBatch	(I)Ljava/lang/String;
/*      */     //   486: iconst_1
/*      */     //   487: invokeinterface 617 3 0
/*      */     //   492: astore 8
/*      */     //   494: goto +17 -> 511
/*      */     //   497: aload_3
/*      */     //   498: aload_0
/*      */     //   499: iload 7
/*      */     //   501: invokespecial 613	com/mysql/jdbc/PreparedStatement:generateMultiStatementForBatch	(I)Ljava/lang/String;
/*      */     //   504: invokeinterface 620 2 0
/*      */     //   509: astore 8
/*      */     //   511: aload 5
/*      */     //   513: ifnull +13 -> 526
/*      */     //   516: aload 5
/*      */     //   518: aload 8
/*      */     //   520: checkcast 4	com/mysql/jdbc/StatementImpl
/*      */     //   523: putfield 658	com/mysql/jdbc/StatementImpl$CancelTask:toCancel	Lcom/mysql/jdbc/StatementImpl;
/*      */     //   526: iconst_1
/*      */     //   527: istore 9
/*      */     //   529: iload 11
/*      */     //   531: iload 6
/*      */     //   533: if_icmpge +30 -> 563
/*      */     //   536: aload_0
/*      */     //   537: aload 8
/*      */     //   539: iload 9
/*      */     //   541: aload_0
/*      */     //   542: getfield 272	com/mysql/jdbc/PreparedStatement:batchedArgs	Ljava/util/List;
/*      */     //   545: iload 11
/*      */     //   547: iinc 11 1
/*      */     //   550: invokeinterface 317 2 0
/*      */     //   555: invokevirtual 652	com/mysql/jdbc/PreparedStatement:setOneBatchedParameterSet	(Ljava/sql/PreparedStatement;ILjava/lang/Object;)I
/*      */     //   558: istore 9
/*      */     //   560: goto -31 -> 529
/*      */     //   563: aload 8
/*      */     //   565: invokeinterface 638 1 0
/*      */     //   570: pop
/*      */     //   571: goto +21 -> 592
/*      */     //   574: astore 15
/*      */     //   576: aload_0
/*      */     //   577: iload 11
/*      */     //   579: iconst_1
/*      */     //   580: isub
/*      */     //   581: iload 7
/*      */     //   583: aload 13
/*      */     //   585: aload 15
/*      */     //   587: invokevirtual 642	com/mysql/jdbc/PreparedStatement:handleExceptionForBatch	(II[ILjava/sql/SQLException;)Ljava/sql/SQLException;
/*      */     //   590: astore 14
/*      */     //   592: aload_0
/*      */     //   593: aload 8
/*      */     //   595: checkcast 4	com/mysql/jdbc/StatementImpl
/*      */     //   598: iload 12
/*      */     //   600: aload 13
/*      */     //   602: invokevirtual 646	com/mysql/jdbc/PreparedStatement:processMultiCountsAndKeys	(Lcom/mysql/jdbc/StatementImpl;I[I)I
/*      */     //   605: istore 12
/*      */     //   607: aload 8
/*      */     //   609: invokeinterface 648 1 0
/*      */     //   614: aload 5
/*      */     //   616: ifnull +36 -> 652
/*      */     //   619: aload 5
/*      */     //   621: getfield 662	com/mysql/jdbc/StatementImpl$CancelTask:caughtWhileCancelling	Ljava/sql/SQLException;
/*      */     //   624: ifnull +9 -> 633
/*      */     //   627: aload 5
/*      */     //   629: getfield 662	com/mysql/jdbc/StatementImpl$CancelTask:caughtWhileCancelling	Ljava/sql/SQLException;
/*      */     //   632: athrow
/*      */     //   633: aload 5
/*      */     //   635: invokevirtual 665	com/mysql/jdbc/StatementImpl$CancelTask:cancel	()Z
/*      */     //   638: pop
/*      */     //   639: aload_3
/*      */     //   640: invokeinterface 630 1 0
/*      */     //   645: invokevirtual 668	java/util/Timer:purge	()I
/*      */     //   648: pop
/*      */     //   649: aconst_null
/*      */     //   650: astore 5
/*      */     //   652: aload 14
/*      */     //   654: ifnull +28 -> 682
/*      */     //   657: new 670	java/sql/BatchUpdateException
/*      */     //   660: dup
/*      */     //   661: aload 14
/*      */     //   663: invokevirtual 673	java/sql/SQLException:getMessage	()Ljava/lang/String;
/*      */     //   666: aload 14
/*      */     //   668: invokevirtual 676	java/sql/SQLException:getSQLState	()Ljava/lang/String;
/*      */     //   671: aload 14
/*      */     //   673: invokevirtual 679	java/sql/SQLException:getErrorCode	()I
/*      */     //   676: aload 13
/*      */     //   678: invokespecial 682	java/sql/BatchUpdateException:<init>	(Ljava/lang/String;Ljava/lang/String;I[I)V
/*      */     //   681: athrow
/*      */     //   682: aload 13
/*      */     //   684: astore 15
/*      */     //   686: jsr +19 -> 705
/*      */     //   689: jsr +40 -> 729
/*      */     //   692: aload_2
/*      */     //   693: monitorexit
/*      */     //   694: aload 15
/*      */     //   696: areturn
/*      */     //   697: astore 20
/*      */     //   699: jsr +6 -> 705
/*      */     //   702: aload 20
/*      */     //   704: athrow
/*      */     //   705: astore 21
/*      */     //   707: aload 8
/*      */     //   709: ifnull +10 -> 719
/*      */     //   712: aload 8
/*      */     //   714: invokeinterface 654 1 0
/*      */     //   719: ret 21
/*      */     //   721: astore 22
/*      */     //   723: jsr +6 -> 729
/*      */     //   726: aload 22
/*      */     //   728: athrow
/*      */     //   729: astore 23
/*      */     //   731: aload 5
/*      */     //   733: ifnull +19 -> 752
/*      */     //   736: aload 5
/*      */     //   738: invokevirtual 665	com/mysql/jdbc/StatementImpl$CancelTask:cancel	()Z
/*      */     //   741: pop
/*      */     //   742: aload_3
/*      */     //   743: invokeinterface 630 1 0
/*      */     //   748: invokevirtual 668	java/util/Timer:purge	()I
/*      */     //   751: pop
/*      */     //   752: aload_0
/*      */     //   753: invokevirtual 562	com/mysql/jdbc/PreparedStatement:resetCancelledState	()V
/*      */     //   756: iload 4
/*      */     //   758: ifne +12 -> 770
/*      */     //   761: aload_3
/*      */     //   762: invokeinterface 604 1 0
/*      */     //   767: invokevirtual 685	com/mysql/jdbc/MysqlIO:disableMultiQueries	()V
/*      */     //   770: aload_0
/*      */     //   771: invokevirtual 579	com/mysql/jdbc/PreparedStatement:clearBatch	()V
/*      */     //   774: ret 23
/*      */     //   776: astore 24
/*      */     //   778: aload_2
/*      */     //   779: monitorexit
/*      */     //   780: aload 24
/*      */     //   782: athrow
/*      */     // Line number table:
/*      */     //   Java source line #1475	-> byte code offset #0
/*      */     //   Java source line #1477	-> byte code offset #12
/*      */     //   Java source line #1478	-> byte code offset #19
/*      */     //   Java source line #1481	-> byte code offset #46
/*      */     //   Java source line #1483	-> byte code offset #51
/*      */     //   Java source line #1484	-> byte code offset #59
/*      */     //   Java source line #1487	-> byte code offset #62
/*      */     //   Java source line #1489	-> byte code offset #66
/*      */     //   Java source line #1491	-> byte code offset #77
/*      */     //   Java source line #1492	-> byte code offset #84
/*      */     //   Java source line #1495	-> byte code offset #97
/*      */     //   Java source line #1497	-> byte code offset #105
/*      */     //   Java source line #1498	-> byte code offset #112
/*      */     //   Java source line #1501	-> byte code offset #116
/*      */     //   Java source line #1503	-> byte code offset #119
/*      */     //   Java source line #1504	-> byte code offset #122
/*      */     //   Java source line #1505	-> byte code offset #125
/*      */     //   Java source line #1506	-> byte code offset #128
/*      */     //   Java source line #1507	-> byte code offset #131
/*      */     //   Java source line #1508	-> byte code offset #137
/*      */     //   Java source line #1511	-> byte code offset #140
/*      */     //   Java source line #1512	-> byte code offset #145
/*      */     //   Java source line #1515	-> byte code offset #154
/*      */     //   Java source line #1516	-> byte code offset #161
/*      */     //   Java source line #1520	-> byte code offset #179
/*      */     //   Java source line #1524	-> byte code offset #193
/*      */     //   Java source line #1527	-> byte code offset #218
/*      */     //   Java source line #1528	-> byte code offset #233
/*      */     //   Java source line #1532	-> byte code offset #246
/*      */     //   Java source line #1533	-> byte code offset #253
/*      */     //   Java source line #1535	-> byte code offset #260
/*      */     //   Java source line #1538	-> byte code offset #267
/*      */     //   Java source line #1540	-> byte code offset #274
/*      */     //   Java source line #1541	-> byte code offset #284
/*      */     //   Java source line #1543	-> byte code offset #297
/*      */     //   Java source line #1547	-> byte code offset #305
/*      */     //   Java source line #1544	-> byte code offset #308
/*      */     //   Java source line #1545	-> byte code offset #310
/*      */     //   Java source line #1549	-> byte code offset #324
/*      */     //   Java source line #1553	-> byte code offset #339
/*      */     //   Java source line #1554	-> byte code offset #346
/*      */     //   Java source line #1557	-> byte code offset #349
/*      */     //   Java source line #1540	-> byte code offset #373
/*      */     //   Java source line #1563	-> byte code offset #379
/*      */     //   Java source line #1567	-> byte code offset #387
/*      */     //   Java source line #1564	-> byte code offset #390
/*      */     //   Java source line #1565	-> byte code offset #392
/*      */     //   Java source line #1569	-> byte code offset #408
/*      */     //   Java source line #1573	-> byte code offset #423
/*      */     //   Java source line #1575	-> byte code offset #430
/*      */     //   Java source line #1576	-> byte code offset #437
/*      */     //   Java source line #1580	-> byte code offset #440
/*      */     //   Java source line #1577	-> byte code offset #443
/*      */     //   Java source line #1578	-> byte code offset #458
/*      */     //   Java source line #1583	-> byte code offset #467
/*      */     //   Java source line #1585	-> byte code offset #472
/*      */     //   Java source line #1586	-> byte code offset #479
/*      */     //   Java source line #1590	-> byte code offset #497
/*      */     //   Java source line #1594	-> byte code offset #511
/*      */     //   Java source line #1595	-> byte code offset #516
/*      */     //   Java source line #1598	-> byte code offset #526
/*      */     //   Java source line #1600	-> byte code offset #529
/*      */     //   Java source line #1601	-> byte code offset #536
/*      */     //   Java source line #1607	-> byte code offset #563
/*      */     //   Java source line #1611	-> byte code offset #571
/*      */     //   Java source line #1608	-> byte code offset #574
/*      */     //   Java source line #1609	-> byte code offset #576
/*      */     //   Java source line #1613	-> byte code offset #592
/*      */     //   Java source line #1617	-> byte code offset #607
/*      */     //   Java source line #1620	-> byte code offset #614
/*      */     //   Java source line #1621	-> byte code offset #619
/*      */     //   Java source line #1622	-> byte code offset #627
/*      */     //   Java source line #1625	-> byte code offset #633
/*      */     //   Java source line #1627	-> byte code offset #639
/*      */     //   Java source line #1629	-> byte code offset #649
/*      */     //   Java source line #1632	-> byte code offset #652
/*      */     //   Java source line #1633	-> byte code offset #657
/*      */     //   Java source line #1638	-> byte code offset #682
/*      */     //   Java source line #1640	-> byte code offset #697
/*      */     //   Java source line #1641	-> byte code offset #712
/*      */     //   Java source line #1645	-> byte code offset #721
/*      */     //   Java source line #1646	-> byte code offset #736
/*      */     //   Java source line #1647	-> byte code offset #742
/*      */     //   Java source line #1650	-> byte code offset #752
/*      */     //   Java source line #1652	-> byte code offset #756
/*      */     //   Java source line #1653	-> byte code offset #761
/*      */     //   Java source line #1656	-> byte code offset #770
/*      */     //   Java source line #1658	-> byte code offset #776
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	783	0	this	PreparedStatement
/*      */     //   0	783	1	batchTimeout	int
/*      */     //   10	769	2	Ljava/lang/Object;	Object
/*      */     //   50	712	3	locallyScopedConn	MySQLConnection
/*      */     //   57	700	4	multiQueriesEnabled	boolean
/*      */     //   60	677	5	timeoutTask	StatementImpl.CancelTask
/*      */     //   75	457	6	numBatchedArgs	int
/*      */     //   103	479	7	numValuesPerBatch	int
/*      */     //   117	596	8	batchedStatement	java.sql.PreparedStatement
/*      */     //   120	439	9	batchedParamIndex	int
/*      */     //   123	145	10	numberToExecuteAsMultiValue	int
/*      */     //   126	452	11	batchCounter	int
/*      */     //   129	477	12	updateCountCounter	int
/*      */     //   135	548	13	updateCounts	int[]
/*      */     //   138	534	14	sqlEx	SQLException
/*      */     //   272	8	15	numberArgsToExecute	int
/*      */     //   574	121	15	ex	SQLException
/*      */     //   275	99	16	i	int
/*      */     //   390	12	16	ex	SQLException
/*      */     //   308	10	17	ex	SQLException
/*      */     //   443	6	18	localObject1	Object
/*      */     //   451	1	19	localObject2	Object
/*      */     //   697	6	20	localObject3	Object
/*      */     //   705	1	21	localObject4	Object
/*      */     //   721	6	22	localObject5	Object
/*      */     //   729	1	23	localObject6	Object
/*      */     //   776	5	24	localObject7	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   297	305	308	java/sql/SQLException
/*      */     //   379	387	390	java/sql/SQLException
/*      */     //   140	440	443	finally
/*      */     //   443	448	443	finally
/*      */     //   563	571	574	java/sql/SQLException
/*      */     //   467	689	697	finally
/*      */     //   697	702	697	finally
/*      */     //   62	692	721	finally
/*      */     //   697	726	721	finally
/*      */     //   12	694	776	finally
/*      */     //   697	780	776	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   protected int[] executeBatchedInserts(int batchTimeout)
/*      */     throws SQLException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual 711	com/mysql/jdbc/PreparedStatement:getValuesClause	()Ljava/lang/String;
/*      */     //   4: astore_2
/*      */     //   5: aload_0
/*      */     //   6: getfield 185	com/mysql/jdbc/PreparedStatement:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   9: astore_3
/*      */     //   10: aload_2
/*      */     //   11: ifnonnull +9 -> 20
/*      */     //   14: aload_0
/*      */     //   15: iload_1
/*      */     //   16: invokevirtual 578	com/mysql/jdbc/PreparedStatement:executeBatchSerially	(I)[I
/*      */     //   19: areturn
/*      */     //   20: aload_0
/*      */     //   21: getfield 272	com/mysql/jdbc/PreparedStatement:batchedArgs	Ljava/util/List;
/*      */     //   24: invokeinterface 556 1 0
/*      */     //   29: istore 4
/*      */     //   31: aload_0
/*      */     //   32: getfield 436	com/mysql/jdbc/PreparedStatement:retrieveGeneratedKeys	Z
/*      */     //   35: ifeq +16 -> 51
/*      */     //   38: aload_0
/*      */     //   39: new 274	java/util/ArrayList
/*      */     //   42: dup
/*      */     //   43: iload 4
/*      */     //   45: invokespecial 596	java/util/ArrayList:<init>	(I)V
/*      */     //   48: putfield 460	com/mysql/jdbc/PreparedStatement:batchedGeneratedKeys	Ljava/util/ArrayList;
/*      */     //   51: aload_0
/*      */     //   52: iload 4
/*      */     //   54: invokevirtual 600	com/mysql/jdbc/PreparedStatement:computeBatchSize	(I)I
/*      */     //   57: istore 5
/*      */     //   59: iload 4
/*      */     //   61: iload 5
/*      */     //   63: if_icmpge +7 -> 70
/*      */     //   66: iload 4
/*      */     //   68: istore 5
/*      */     //   70: aconst_null
/*      */     //   71: astore 6
/*      */     //   73: iconst_1
/*      */     //   74: istore 7
/*      */     //   76: iconst_0
/*      */     //   77: istore 8
/*      */     //   79: iconst_0
/*      */     //   80: istore 9
/*      */     //   82: iconst_0
/*      */     //   83: istore 10
/*      */     //   85: aconst_null
/*      */     //   86: astore 11
/*      */     //   88: aconst_null
/*      */     //   89: astore 12
/*      */     //   91: iload 4
/*      */     //   93: newarray <illegal type>
/*      */     //   95: astore 13
/*      */     //   97: iconst_0
/*      */     //   98: istore 14
/*      */     //   100: iload 14
/*      */     //   102: aload_0
/*      */     //   103: getfield 272	com/mysql/jdbc/PreparedStatement:batchedArgs	Ljava/util/List;
/*      */     //   106: invokeinterface 556 1 0
/*      */     //   111: if_icmpge +15 -> 126
/*      */     //   114: aload 13
/*      */     //   116: iload 14
/*      */     //   118: iconst_1
/*      */     //   119: iastore
/*      */     //   120: iinc 14 1
/*      */     //   123: goto -23 -> 100
/*      */     //   126: aload_0
/*      */     //   127: aload_3
/*      */     //   128: iload 5
/*      */     //   130: invokevirtual 715	com/mysql/jdbc/PreparedStatement:prepareBatchedInsertSQL	(Lcom/mysql/jdbc/MySQLConnection;I)Lcom/mysql/jdbc/PreparedStatement;
/*      */     //   133: astore 6
/*      */     //   135: aload_3
/*      */     //   136: invokeinterface 623 1 0
/*      */     //   141: ifeq +47 -> 188
/*      */     //   144: iload_1
/*      */     //   145: ifeq +43 -> 188
/*      */     //   148: aload_3
/*      */     //   149: iconst_5
/*      */     //   150: iconst_0
/*      */     //   151: iconst_0
/*      */     //   152: invokeinterface 225 4 0
/*      */     //   157: ifeq +31 -> 188
/*      */     //   160: new 27	com/mysql/jdbc/StatementImpl$CancelTask
/*      */     //   163: dup
/*      */     //   164: aload_0
/*      */     //   165: aload 6
/*      */     //   167: checkcast 4	com/mysql/jdbc/StatementImpl
/*      */     //   170: invokespecial 626	com/mysql/jdbc/StatementImpl$CancelTask:<init>	(Lcom/mysql/jdbc/StatementImpl;Lcom/mysql/jdbc/StatementImpl;)V
/*      */     //   173: astore 11
/*      */     //   175: aload_3
/*      */     //   176: invokeinterface 630 1 0
/*      */     //   181: aload 11
/*      */     //   183: iload_1
/*      */     //   184: i2l
/*      */     //   185: invokevirtual 636	java/util/Timer:schedule	(Ljava/util/TimerTask;J)V
/*      */     //   188: iload 4
/*      */     //   190: iload 5
/*      */     //   192: if_icmpge +10 -> 202
/*      */     //   195: iload 4
/*      */     //   197: istore 9
/*      */     //   199: goto +10 -> 209
/*      */     //   202: iload 4
/*      */     //   204: iload 5
/*      */     //   206: idiv
/*      */     //   207: istore 9
/*      */     //   209: iload 9
/*      */     //   211: iload 5
/*      */     //   213: imul
/*      */     //   214: istore 14
/*      */     //   216: iconst_0
/*      */     //   217: istore 15
/*      */     //   219: iload 15
/*      */     //   221: iload 14
/*      */     //   223: if_icmpge +95 -> 318
/*      */     //   226: iload 15
/*      */     //   228: ifeq +60 -> 288
/*      */     //   231: iload 15
/*      */     //   233: iload 5
/*      */     //   235: irem
/*      */     //   236: ifne +52 -> 288
/*      */     //   239: iload 8
/*      */     //   241: aload 6
/*      */     //   243: invokeinterface 718 1 0
/*      */     //   248: iadd
/*      */     //   249: istore 8
/*      */     //   251: goto +21 -> 272
/*      */     //   254: astore 16
/*      */     //   256: aload_0
/*      */     //   257: iload 10
/*      */     //   259: iconst_1
/*      */     //   260: isub
/*      */     //   261: iload 5
/*      */     //   263: aload 13
/*      */     //   265: aload 16
/*      */     //   267: invokevirtual 642	com/mysql/jdbc/PreparedStatement:handleExceptionForBatch	(II[ILjava/sql/SQLException;)Ljava/sql/SQLException;
/*      */     //   270: astore 12
/*      */     //   272: aload_0
/*      */     //   273: aload 6
/*      */     //   275: invokevirtual 722	com/mysql/jdbc/PreparedStatement:getBatchedGeneratedKeys	(Ljava/sql/Statement;)V
/*      */     //   278: aload 6
/*      */     //   280: invokeinterface 648 1 0
/*      */     //   285: iconst_1
/*      */     //   286: istore 7
/*      */     //   288: aload_0
/*      */     //   289: aload 6
/*      */     //   291: iload 7
/*      */     //   293: aload_0
/*      */     //   294: getfield 272	com/mysql/jdbc/PreparedStatement:batchedArgs	Ljava/util/List;
/*      */     //   297: iload 10
/*      */     //   299: iinc 10 1
/*      */     //   302: invokeinterface 317 2 0
/*      */     //   307: invokevirtual 652	com/mysql/jdbc/PreparedStatement:setOneBatchedParameterSet	(Ljava/sql/PreparedStatement;ILjava/lang/Object;)I
/*      */     //   310: istore 7
/*      */     //   312: iinc 15 1
/*      */     //   315: goto -96 -> 219
/*      */     //   318: iload 8
/*      */     //   320: aload 6
/*      */     //   322: invokeinterface 718 1 0
/*      */     //   327: iadd
/*      */     //   328: istore 8
/*      */     //   330: goto +21 -> 351
/*      */     //   333: astore 15
/*      */     //   335: aload_0
/*      */     //   336: iload 10
/*      */     //   338: iconst_1
/*      */     //   339: isub
/*      */     //   340: iload 5
/*      */     //   342: aload 13
/*      */     //   344: aload 15
/*      */     //   346: invokevirtual 642	com/mysql/jdbc/PreparedStatement:handleExceptionForBatch	(II[ILjava/sql/SQLException;)Ljava/sql/SQLException;
/*      */     //   349: astore 12
/*      */     //   351: aload_0
/*      */     //   352: aload 6
/*      */     //   354: invokevirtual 722	com/mysql/jdbc/PreparedStatement:getBatchedGeneratedKeys	(Ljava/sql/Statement;)V
/*      */     //   357: iload 4
/*      */     //   359: iload 10
/*      */     //   361: isub
/*      */     //   362: istore 5
/*      */     //   364: jsr +14 -> 378
/*      */     //   367: goto +27 -> 394
/*      */     //   370: astore 17
/*      */     //   372: jsr +6 -> 378
/*      */     //   375: aload 17
/*      */     //   377: athrow
/*      */     //   378: astore 18
/*      */     //   380: aload 6
/*      */     //   382: ifnull +10 -> 392
/*      */     //   385: aload 6
/*      */     //   387: invokeinterface 654 1 0
/*      */     //   392: ret 18
/*      */     //   394: iload 5
/*      */     //   396: ifle +103 -> 499
/*      */     //   399: aload_0
/*      */     //   400: aload_3
/*      */     //   401: iload 5
/*      */     //   403: invokevirtual 715	com/mysql/jdbc/PreparedStatement:prepareBatchedInsertSQL	(Lcom/mysql/jdbc/MySQLConnection;I)Lcom/mysql/jdbc/PreparedStatement;
/*      */     //   406: astore 6
/*      */     //   408: aload 11
/*      */     //   410: ifnull +13 -> 423
/*      */     //   413: aload 11
/*      */     //   415: aload 6
/*      */     //   417: checkcast 4	com/mysql/jdbc/StatementImpl
/*      */     //   420: putfield 658	com/mysql/jdbc/StatementImpl$CancelTask:toCancel	Lcom/mysql/jdbc/StatementImpl;
/*      */     //   423: iconst_1
/*      */     //   424: istore 7
/*      */     //   426: iload 10
/*      */     //   428: iload 4
/*      */     //   430: if_icmpge +30 -> 460
/*      */     //   433: aload_0
/*      */     //   434: aload 6
/*      */     //   436: iload 7
/*      */     //   438: aload_0
/*      */     //   439: getfield 272	com/mysql/jdbc/PreparedStatement:batchedArgs	Ljava/util/List;
/*      */     //   442: iload 10
/*      */     //   444: iinc 10 1
/*      */     //   447: invokeinterface 317 2 0
/*      */     //   452: invokevirtual 652	com/mysql/jdbc/PreparedStatement:setOneBatchedParameterSet	(Ljava/sql/PreparedStatement;ILjava/lang/Object;)I
/*      */     //   455: istore 7
/*      */     //   457: goto -31 -> 426
/*      */     //   460: iload 8
/*      */     //   462: aload 6
/*      */     //   464: invokeinterface 718 1 0
/*      */     //   469: iadd
/*      */     //   470: istore 8
/*      */     //   472: goto +21 -> 493
/*      */     //   475: astore 14
/*      */     //   477: aload_0
/*      */     //   478: iload 10
/*      */     //   480: iconst_1
/*      */     //   481: isub
/*      */     //   482: iload 5
/*      */     //   484: aload 13
/*      */     //   486: aload 14
/*      */     //   488: invokevirtual 642	com/mysql/jdbc/PreparedStatement:handleExceptionForBatch	(II[ILjava/sql/SQLException;)Ljava/sql/SQLException;
/*      */     //   491: astore 12
/*      */     //   493: aload_0
/*      */     //   494: aload 6
/*      */     //   496: invokevirtual 722	com/mysql/jdbc/PreparedStatement:getBatchedGeneratedKeys	(Ljava/sql/Statement;)V
/*      */     //   499: aload 12
/*      */     //   501: ifnull +28 -> 529
/*      */     //   504: new 670	java/sql/BatchUpdateException
/*      */     //   507: dup
/*      */     //   508: aload 12
/*      */     //   510: invokevirtual 673	java/sql/SQLException:getMessage	()Ljava/lang/String;
/*      */     //   513: aload 12
/*      */     //   515: invokevirtual 676	java/sql/SQLException:getSQLState	()Ljava/lang/String;
/*      */     //   518: aload 12
/*      */     //   520: invokevirtual 679	java/sql/SQLException:getErrorCode	()I
/*      */     //   523: aload 13
/*      */     //   525: invokespecial 682	java/sql/BatchUpdateException:<init>	(Ljava/lang/String;Ljava/lang/String;I[I)V
/*      */     //   528: athrow
/*      */     //   529: aload 13
/*      */     //   531: astore 14
/*      */     //   533: jsr +17 -> 550
/*      */     //   536: jsr +38 -> 574
/*      */     //   539: aload 14
/*      */     //   541: areturn
/*      */     //   542: astore 19
/*      */     //   544: jsr +6 -> 550
/*      */     //   547: aload 19
/*      */     //   549: athrow
/*      */     //   550: astore 20
/*      */     //   552: aload 6
/*      */     //   554: ifnull +10 -> 564
/*      */     //   557: aload 6
/*      */     //   559: invokeinterface 654 1 0
/*      */     //   564: ret 20
/*      */     //   566: astore 21
/*      */     //   568: jsr +6 -> 574
/*      */     //   571: aload 21
/*      */     //   573: athrow
/*      */     //   574: astore 22
/*      */     //   576: aload 11
/*      */     //   578: ifnull +19 -> 597
/*      */     //   581: aload 11
/*      */     //   583: invokevirtual 665	com/mysql/jdbc/StatementImpl$CancelTask:cancel	()Z
/*      */     //   586: pop
/*      */     //   587: aload_3
/*      */     //   588: invokeinterface 630 1 0
/*      */     //   593: invokevirtual 668	java/util/Timer:purge	()I
/*      */     //   596: pop
/*      */     //   597: aload_0
/*      */     //   598: invokevirtual 562	com/mysql/jdbc/PreparedStatement:resetCancelledState	()V
/*      */     //   601: ret 22
/*      */     // Line number table:
/*      */     //   Java source line #1685	-> byte code offset #0
/*      */     //   Java source line #1687	-> byte code offset #5
/*      */     //   Java source line #1689	-> byte code offset #10
/*      */     //   Java source line #1690	-> byte code offset #14
/*      */     //   Java source line #1693	-> byte code offset #20
/*      */     //   Java source line #1695	-> byte code offset #31
/*      */     //   Java source line #1696	-> byte code offset #38
/*      */     //   Java source line #1699	-> byte code offset #51
/*      */     //   Java source line #1701	-> byte code offset #59
/*      */     //   Java source line #1702	-> byte code offset #66
/*      */     //   Java source line #1705	-> byte code offset #70
/*      */     //   Java source line #1707	-> byte code offset #73
/*      */     //   Java source line #1708	-> byte code offset #76
/*      */     //   Java source line #1709	-> byte code offset #79
/*      */     //   Java source line #1710	-> byte code offset #82
/*      */     //   Java source line #1711	-> byte code offset #85
/*      */     //   Java source line #1712	-> byte code offset #88
/*      */     //   Java source line #1714	-> byte code offset #91
/*      */     //   Java source line #1716	-> byte code offset #97
/*      */     //   Java source line #1717	-> byte code offset #114
/*      */     //   Java source line #1716	-> byte code offset #120
/*      */     //   Java source line #1722	-> byte code offset #126
/*      */     //   Java source line #1725	-> byte code offset #135
/*      */     //   Java source line #1728	-> byte code offset #160
/*      */     //   Java source line #1730	-> byte code offset #175
/*      */     //   Java source line #1734	-> byte code offset #188
/*      */     //   Java source line #1735	-> byte code offset #195
/*      */     //   Java source line #1737	-> byte code offset #202
/*      */     //   Java source line #1741	-> byte code offset #209
/*      */     //   Java source line #1744	-> byte code offset #216
/*      */     //   Java source line #1745	-> byte code offset #226
/*      */     //   Java source line #1747	-> byte code offset #239
/*      */     //   Java source line #1752	-> byte code offset #251
/*      */     //   Java source line #1749	-> byte code offset #254
/*      */     //   Java source line #1750	-> byte code offset #256
/*      */     //   Java source line #1754	-> byte code offset #272
/*      */     //   Java source line #1755	-> byte code offset #278
/*      */     //   Java source line #1756	-> byte code offset #285
/*      */     //   Java source line #1760	-> byte code offset #288
/*      */     //   Java source line #1744	-> byte code offset #312
/*      */     //   Java source line #1766	-> byte code offset #318
/*      */     //   Java source line #1770	-> byte code offset #330
/*      */     //   Java source line #1767	-> byte code offset #333
/*      */     //   Java source line #1768	-> byte code offset #335
/*      */     //   Java source line #1772	-> byte code offset #351
/*      */     //   Java source line #1774	-> byte code offset #357
/*      */     //   Java source line #1775	-> byte code offset #364
/*      */     //   Java source line #1779	-> byte code offset #367
/*      */     //   Java source line #1776	-> byte code offset #370
/*      */     //   Java source line #1777	-> byte code offset #385
/*      */     //   Java source line #1782	-> byte code offset #394
/*      */     //   Java source line #1783	-> byte code offset #399
/*      */     //   Java source line #1787	-> byte code offset #408
/*      */     //   Java source line #1788	-> byte code offset #413
/*      */     //   Java source line #1791	-> byte code offset #423
/*      */     //   Java source line #1793	-> byte code offset #426
/*      */     //   Java source line #1794	-> byte code offset #433
/*      */     //   Java source line #1800	-> byte code offset #460
/*      */     //   Java source line #1804	-> byte code offset #472
/*      */     //   Java source line #1801	-> byte code offset #475
/*      */     //   Java source line #1802	-> byte code offset #477
/*      */     //   Java source line #1806	-> byte code offset #493
/*      */     //   Java source line #1809	-> byte code offset #499
/*      */     //   Java source line #1810	-> byte code offset #504
/*      */     //   Java source line #1815	-> byte code offset #529
/*      */     //   Java source line #1817	-> byte code offset #542
/*      */     //   Java source line #1818	-> byte code offset #557
/*      */     //   Java source line #1822	-> byte code offset #566
/*      */     //   Java source line #1823	-> byte code offset #581
/*      */     //   Java source line #1824	-> byte code offset #587
/*      */     //   Java source line #1827	-> byte code offset #597
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	603	0	this	PreparedStatement
/*      */     //   0	603	1	batchTimeout	int
/*      */     //   4	7	2	valuesClause	String
/*      */     //   9	579	3	locallyScopedConn	MySQLConnection
/*      */     //   29	400	4	numBatchedArgs	int
/*      */     //   57	426	5	numValuesPerBatch	int
/*      */     //   71	487	6	batchedStatement	java.sql.PreparedStatement
/*      */     //   74	382	7	batchedParamIndex	int
/*      */     //   77	394	8	updateCountRunningTotal	int
/*      */     //   80	130	9	numberToExecuteAsMultiValue	int
/*      */     //   83	396	10	batchCounter	int
/*      */     //   86	496	11	timeoutTask	StatementImpl.CancelTask
/*      */     //   89	430	12	sqlEx	SQLException
/*      */     //   95	435	13	updateCounts	int[]
/*      */     //   98	23	14	i	int
/*      */     //   214	8	14	numberArgsToExecute	int
/*      */     //   475	65	14	ex	SQLException
/*      */     //   217	96	15	i	int
/*      */     //   333	12	15	ex	SQLException
/*      */     //   254	12	16	ex	SQLException
/*      */     //   370	6	17	localObject1	Object
/*      */     //   378	1	18	localObject2	Object
/*      */     //   542	6	19	localObject3	Object
/*      */     //   550	1	20	localObject4	Object
/*      */     //   566	6	21	localObject5	Object
/*      */     //   574	1	22	localObject6	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   239	251	254	java/sql/SQLException
/*      */     //   318	330	333	java/sql/SQLException
/*      */     //   126	367	370	finally
/*      */     //   370	375	370	finally
/*      */     //   460	472	475	java/sql/SQLException
/*      */     //   394	536	542	finally
/*      */     //   542	547	542	finally
/*      */     //   126	539	566	finally
/*      */     //   542	571	566	finally
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\PreparedStatement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */