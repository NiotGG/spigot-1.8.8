/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.net.Facility;
/*     */ import org.apache.logging.log4j.core.net.Priority;
/*     */ import org.apache.logging.log4j.message.Message;
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
/*     */ @Plugin(name="SyslogLayout", category="Core", elementType="layout", printObject=true)
/*     */ public class SyslogLayout
/*     */   extends AbstractStringLayout
/*     */ {
/*  47 */   public static final Pattern NEWLINE_PATTERN = Pattern.compile("\\r?\\n");
/*     */   
/*     */ 
/*     */   private final Facility facility;
/*     */   
/*     */   private final boolean includeNewLine;
/*     */   
/*     */   private final String escapeNewLine;
/*     */   
/*  56 */   private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd HH:mm:ss ", Locale.ENGLISH);
/*     */   
/*     */ 
/*     */ 
/*  60 */   private final String localHostname = getLocalHostname();
/*     */   
/*     */ 
/*     */   protected SyslogLayout(Facility paramFacility, boolean paramBoolean, String paramString, Charset paramCharset)
/*     */   {
/*  65 */     super(paramCharset);
/*  66 */     this.facility = paramFacility;
/*  67 */     this.includeNewLine = paramBoolean;
/*  68 */     this.escapeNewLine = (paramString == null ? null : Matcher.quoteReplacement(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toSerializable(LogEvent paramLogEvent)
/*     */   {
/*  79 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/*  81 */     localStringBuilder.append("<");
/*  82 */     localStringBuilder.append(Priority.getPriority(this.facility, paramLogEvent.getLevel()));
/*  83 */     localStringBuilder.append(">");
/*  84 */     addDate(paramLogEvent.getMillis(), localStringBuilder);
/*  85 */     localStringBuilder.append(" ");
/*  86 */     localStringBuilder.append(this.localHostname);
/*  87 */     localStringBuilder.append(" ");
/*     */     
/*  89 */     String str = paramLogEvent.getMessage().getFormattedMessage();
/*  90 */     if (null != this.escapeNewLine) {
/*  91 */       str = NEWLINE_PATTERN.matcher(str).replaceAll(this.escapeNewLine);
/*     */     }
/*  93 */     localStringBuilder.append(str);
/*     */     
/*  95 */     if (this.includeNewLine) {
/*  96 */       localStringBuilder.append("\n");
/*     */     }
/*  98 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String getLocalHostname()
/*     */   {
/*     */     try
/*     */     {
/* 110 */       InetAddress localInetAddress = InetAddress.getLocalHost();
/* 111 */       return localInetAddress.getHostName();
/*     */     } catch (UnknownHostException localUnknownHostException) {
/* 113 */       LOGGER.error("Could not determine local host name", localUnknownHostException); }
/* 114 */     return "UNKNOWN_LOCALHOST";
/*     */   }
/*     */   
/*     */   private synchronized void addDate(long paramLong, StringBuilder paramStringBuilder)
/*     */   {
/* 119 */     int i = paramStringBuilder.length() + 4;
/* 120 */     paramStringBuilder.append(this.dateFormat.format(new Date(paramLong)));
/*     */     
/* 122 */     if (paramStringBuilder.charAt(i) == '0') {
/* 123 */       paramStringBuilder.setCharAt(i, ' ');
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
/*     */   public Map<String, String> getContentFormat()
/*     */   {
/* 138 */     HashMap localHashMap = new HashMap();
/* 139 */     localHashMap.put("structured", "false");
/* 140 */     localHashMap.put("formatType", "logfilepatternreceiver");
/* 141 */     localHashMap.put("dateFormat", this.dateFormat.toPattern());
/* 142 */     localHashMap.put("format", "<LEVEL>TIMESTAMP PROP(HOSTNAME) MESSAGE");
/* 143 */     return localHashMap;
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
/*     */   public static SyslogLayout createLayout(@PluginAttribute("facility") String paramString1, @PluginAttribute("newLine") String paramString2, @PluginAttribute("newLineEscape") String paramString3, @PluginAttribute("charset") String paramString4)
/*     */   {
/* 160 */     Charset localCharset = Charsets.getSupportedCharset(paramString4);
/* 161 */     boolean bool = Boolean.parseBoolean(paramString2);
/* 162 */     Facility localFacility = Facility.toFacility(paramString1, Facility.LOCAL0);
/* 163 */     return new SyslogLayout(localFacility, bool, paramString3, localCharset);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\SyslogLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */