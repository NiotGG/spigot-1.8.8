/*     */ package org.apache.logging.log4j.core.appender.db.jdbc;
/*     */ 
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.core.layout.PatternLayout;
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
/*     */ @Plugin(name="Column", category="Core", printObject=true)
/*     */ public final class ColumnConfig
/*     */ {
/*  35 */   private static final Logger LOGGER = ;
/*     */   
/*     */   private final String columnName;
/*     */   private final PatternLayout layout;
/*     */   private final String literalValue;
/*     */   private final boolean eventTimestamp;
/*     */   private final boolean unicode;
/*     */   private final boolean clob;
/*     */   
/*     */   private ColumnConfig(String paramString1, PatternLayout paramPatternLayout, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*     */   {
/*  46 */     this.columnName = paramString1;
/*  47 */     this.layout = paramPatternLayout;
/*  48 */     this.literalValue = paramString2;
/*  49 */     this.eventTimestamp = paramBoolean1;
/*  50 */     this.unicode = paramBoolean2;
/*  51 */     this.clob = paramBoolean3;
/*     */   }
/*     */   
/*     */   public String getColumnName() {
/*  55 */     return this.columnName;
/*     */   }
/*     */   
/*     */   public PatternLayout getLayout() {
/*  59 */     return this.layout;
/*     */   }
/*     */   
/*     */   public String getLiteralValue() {
/*  63 */     return this.literalValue;
/*     */   }
/*     */   
/*     */   public boolean isEventTimestamp() {
/*  67 */     return this.eventTimestamp;
/*     */   }
/*     */   
/*     */   public boolean isUnicode() {
/*  71 */     return this.unicode;
/*     */   }
/*     */   
/*     */   public boolean isClob() {
/*  75 */     return this.clob;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  80 */     return "{ name=" + this.columnName + ", layout=" + this.layout + ", literal=" + this.literalValue + ", timestamp=" + this.eventTimestamp + " }";
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
/*     */ 
/*     */   @PluginFactory
/*     */   public static ColumnConfig createColumnConfig(@PluginConfiguration Configuration paramConfiguration, @PluginAttribute("name") String paramString1, @PluginAttribute("pattern") String paramString2, @PluginAttribute("literal") String paramString3, @PluginAttribute("isEventTimestamp") String paramString4, @PluginAttribute("isUnicode") String paramString5, @PluginAttribute("isClob") String paramString6)
/*     */   {
/* 109 */     if (Strings.isEmpty(paramString1)) {
/* 110 */       LOGGER.error("The column config is not valid because it does not contain a column name.");
/* 111 */       return null;
/*     */     }
/*     */     
/* 114 */     boolean bool1 = Strings.isNotEmpty(paramString2);
/* 115 */     boolean bool2 = Strings.isNotEmpty(paramString3);
/* 116 */     boolean bool3 = Boolean.parseBoolean(paramString4);
/* 117 */     boolean bool4 = Booleans.parseBoolean(paramString5, true);
/* 118 */     boolean bool5 = Boolean.parseBoolean(paramString6);
/*     */     
/* 120 */     if (((bool1) && (bool2)) || ((bool1) && (bool3)) || ((bool2) && (bool3))) {
/* 121 */       LOGGER.error("The pattern, literal, and isEventTimestamp attributes are mutually exclusive.");
/* 122 */       return null;
/*     */     }
/*     */     
/* 125 */     if (bool3) {
/* 126 */       return new ColumnConfig(paramString1, null, null, true, false, false);
/*     */     }
/* 128 */     if (bool2) {
/* 129 */       return new ColumnConfig(paramString1, null, paramString3, false, false, false);
/*     */     }
/* 131 */     if (bool1) {
/* 132 */       return new ColumnConfig(paramString1, PatternLayout.createLayout(paramString2, paramConfiguration, null, null, "false"), null, false, bool4, bool5);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 138 */     LOGGER.error("To configure a column you must specify a pattern or literal or set isEventDate to true.");
/* 139 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jdbc\ColumnConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */