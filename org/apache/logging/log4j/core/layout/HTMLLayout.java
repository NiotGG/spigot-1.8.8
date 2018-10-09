/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.ThreadContext.ContextStack;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.helpers.Constants;
/*     */ import org.apache.logging.log4j.core.helpers.Transform;
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
/*     */ @Plugin(name="HTMLLayout", category="Core", elementType="layout", printObject=true)
/*     */ public final class HTMLLayout
/*     */   extends AbstractStringLayout
/*     */ {
/*     */   private static final int BUF_SIZE = 256;
/*     */   private static final String TRACE_PREFIX = "<br />&nbsp;&nbsp;&nbsp;&nbsp;";
/*  53 */   private static final String REGEXP = Constants.LINE_SEP + "|\n";
/*     */   
/*     */   private static final String DEFAULT_TITLE = "Log4j Log Messages";
/*     */   
/*     */   private static final String DEFAULT_CONTENT_TYPE = "text/html";
/*     */   
/*  59 */   private final long jvmStartTime = ManagementFactory.getRuntimeMXBean().getStartTime();
/*     */   
/*     */   private final boolean locationInfo;
/*     */   private final String title;
/*     */   private final String contentType;
/*     */   private final String font;
/*     */   private final String fontSize;
/*     */   private final String headerSize;
/*     */   
/*     */   private static enum FontSize
/*     */   {
/*  70 */     SMALLER("smaller"),  XXSMALL("xx-small"),  XSMALL("x-small"),  SMALL("small"),  MEDIUM("medium"),  LARGE("large"), 
/*  71 */     XLARGE("x-large"),  XXLARGE("xx-large"),  LARGER("larger");
/*     */     
/*     */     private final String size;
/*     */     
/*     */     private FontSize(String paramString) {
/*  76 */       this.size = paramString;
/*     */     }
/*     */     
/*     */     public String getFontSize() {
/*  80 */       return this.size;
/*     */     }
/*     */     
/*     */     public static FontSize getFontSize(String paramString) {
/*  84 */       for (FontSize localFontSize : ) {
/*  85 */         if (localFontSize.size.equals(paramString)) {
/*  86 */           return localFontSize;
/*     */         }
/*     */       }
/*  89 */       return SMALL;
/*     */     }
/*     */     
/*     */     public FontSize larger() {
/*  93 */       return ordinal() < XXLARGE.ordinal() ? values()[(ordinal() + 1)] : this;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private HTMLLayout(boolean paramBoolean, String paramString1, String paramString2, Charset paramCharset, String paramString3, String paramString4, String paramString5)
/*     */   {
/* 103 */     super(paramCharset);
/* 104 */     this.locationInfo = paramBoolean;
/* 105 */     this.title = paramString1;
/* 106 */     this.contentType = paramString2;
/* 107 */     this.font = paramString3;
/* 108 */     this.fontSize = paramString4;
/* 109 */     this.headerSize = paramString5;
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
/* 120 */     StringBuilder localStringBuilder = new StringBuilder(256);
/*     */     
/* 122 */     localStringBuilder.append(Constants.LINE_SEP).append("<tr>").append(Constants.LINE_SEP);
/*     */     
/* 124 */     localStringBuilder.append("<td>");
/* 125 */     localStringBuilder.append(paramLogEvent.getMillis() - this.jvmStartTime);
/* 126 */     localStringBuilder.append("</td>").append(Constants.LINE_SEP);
/*     */     
/* 128 */     String str1 = Transform.escapeHtmlTags(paramLogEvent.getThreadName());
/* 129 */     localStringBuilder.append("<td title=\"").append(str1).append(" thread\">");
/* 130 */     localStringBuilder.append(str1);
/* 131 */     localStringBuilder.append("</td>").append(Constants.LINE_SEP);
/*     */     
/* 133 */     localStringBuilder.append("<td title=\"Level\">");
/* 134 */     if (paramLogEvent.getLevel().equals(Level.DEBUG)) {
/* 135 */       localStringBuilder.append("<font color=\"#339933\">");
/* 136 */       localStringBuilder.append(Transform.escapeHtmlTags(String.valueOf(paramLogEvent.getLevel())));
/* 137 */       localStringBuilder.append("</font>");
/* 138 */     } else if (paramLogEvent.getLevel().isAtLeastAsSpecificAs(Level.WARN)) {
/* 139 */       localStringBuilder.append("<font color=\"#993300\"><strong>");
/* 140 */       localStringBuilder.append(Transform.escapeHtmlTags(String.valueOf(paramLogEvent.getLevel())));
/* 141 */       localStringBuilder.append("</strong></font>");
/*     */     } else {
/* 143 */       localStringBuilder.append(Transform.escapeHtmlTags(String.valueOf(paramLogEvent.getLevel())));
/*     */     }
/* 145 */     localStringBuilder.append("</td>").append(Constants.LINE_SEP);
/*     */     
/* 147 */     String str2 = Transform.escapeHtmlTags(paramLogEvent.getLoggerName());
/* 148 */     if (str2.isEmpty()) {
/* 149 */       str2 = "root";
/*     */     }
/* 151 */     localStringBuilder.append("<td title=\"").append(str2).append(" logger\">");
/* 152 */     localStringBuilder.append(str2);
/* 153 */     localStringBuilder.append("</td>").append(Constants.LINE_SEP);
/*     */     
/* 155 */     if (this.locationInfo) {
/* 156 */       localObject = paramLogEvent.getSource();
/* 157 */       localStringBuilder.append("<td>");
/* 158 */       localStringBuilder.append(Transform.escapeHtmlTags(((StackTraceElement)localObject).getFileName()));
/* 159 */       localStringBuilder.append(':');
/* 160 */       localStringBuilder.append(((StackTraceElement)localObject).getLineNumber());
/* 161 */       localStringBuilder.append("</td>").append(Constants.LINE_SEP);
/*     */     }
/*     */     
/* 164 */     localStringBuilder.append("<td title=\"Message\">");
/* 165 */     localStringBuilder.append(Transform.escapeHtmlTags(paramLogEvent.getMessage().getFormattedMessage()).replaceAll(REGEXP, "<br />"));
/* 166 */     localStringBuilder.append("</td>").append(Constants.LINE_SEP);
/* 167 */     localStringBuilder.append("</tr>").append(Constants.LINE_SEP);
/*     */     
/* 169 */     if (paramLogEvent.getContextStack().getDepth() > 0) {
/* 170 */       localStringBuilder.append("<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : ").append(this.fontSize);
/* 171 */       localStringBuilder.append(";\" colspan=\"6\" ");
/* 172 */       localStringBuilder.append("title=\"Nested Diagnostic Context\">");
/* 173 */       localStringBuilder.append("NDC: ").append(Transform.escapeHtmlTags(paramLogEvent.getContextStack().toString()));
/* 174 */       localStringBuilder.append("</td></tr>").append(Constants.LINE_SEP);
/*     */     }
/*     */     
/* 177 */     if (paramLogEvent.getContextMap().size() > 0) {
/* 178 */       localStringBuilder.append("<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : ").append(this.fontSize);
/* 179 */       localStringBuilder.append(";\" colspan=\"6\" ");
/* 180 */       localStringBuilder.append("title=\"Mapped Diagnostic Context\">");
/* 181 */       localStringBuilder.append("MDC: ").append(Transform.escapeHtmlTags(paramLogEvent.getContextMap().toString()));
/* 182 */       localStringBuilder.append("</td></tr>").append(Constants.LINE_SEP);
/*     */     }
/*     */     
/* 185 */     Object localObject = paramLogEvent.getThrown();
/* 186 */     if (localObject != null) {
/* 187 */       localStringBuilder.append("<tr><td bgcolor=\"#993300\" style=\"color:White; font-size : ").append(this.fontSize);
/* 188 */       localStringBuilder.append(";\" colspan=\"6\">");
/* 189 */       appendThrowableAsHTML((Throwable)localObject, localStringBuilder);
/* 190 */       localStringBuilder.append("</td></tr>").append(Constants.LINE_SEP);
/*     */     }
/*     */     
/* 193 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, String> getContentFormat()
/*     */   {
/* 203 */     return new HashMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getContentType()
/*     */   {
/* 211 */     return this.contentType;
/*     */   }
/*     */   
/*     */   private void appendThrowableAsHTML(Throwable paramThrowable, StringBuilder paramStringBuilder) {
/* 215 */     StringWriter localStringWriter = new StringWriter();
/* 216 */     PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
/*     */     try {
/* 218 */       paramThrowable.printStackTrace(localPrintWriter);
/*     */     }
/*     */     catch (RuntimeException localRuntimeException) {}
/*     */     
/* 222 */     localPrintWriter.flush();
/* 223 */     LineNumberReader localLineNumberReader = new LineNumberReader(new StringReader(localStringWriter.toString()));
/* 224 */     ArrayList localArrayList = new ArrayList();
/*     */     try {
/* 226 */       String str1 = localLineNumberReader.readLine();
/* 227 */       while (str1 != null) {
/* 228 */         localArrayList.add(str1);
/* 229 */         str1 = localLineNumberReader.readLine();
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 232 */       if ((localIOException instanceof InterruptedIOException)) {
/* 233 */         Thread.currentThread().interrupt();
/*     */       }
/* 235 */       localArrayList.add(localIOException.toString());
/*     */     }
/* 237 */     int i = 1;
/* 238 */     for (String str2 : localArrayList) {
/* 239 */       if (i == 0) {
/* 240 */         paramStringBuilder.append("<br />&nbsp;&nbsp;&nbsp;&nbsp;");
/*     */       } else {
/* 242 */         i = 0;
/*     */       }
/* 244 */       paramStringBuilder.append(Transform.escapeHtmlTags(str2));
/* 245 */       paramStringBuilder.append(Constants.LINE_SEP);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] getHeader()
/*     */   {
/* 255 */     StringBuilder localStringBuilder = new StringBuilder();
/* 256 */     localStringBuilder.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" ");
/* 257 */     localStringBuilder.append("\"http://www.w3.org/TR/html4/loose.dtd\">");
/* 258 */     localStringBuilder.append(Constants.LINE_SEP);
/* 259 */     localStringBuilder.append("<html>").append(Constants.LINE_SEP);
/* 260 */     localStringBuilder.append("<head>").append(Constants.LINE_SEP);
/* 261 */     localStringBuilder.append("<meta charset=\"").append(getCharset()).append("\"/>").append(Constants.LINE_SEP);
/* 262 */     localStringBuilder.append("<title>").append(this.title).append("</title>").append(Constants.LINE_SEP);
/* 263 */     localStringBuilder.append("<style type=\"text/css\">").append(Constants.LINE_SEP);
/* 264 */     localStringBuilder.append("<!--").append(Constants.LINE_SEP);
/* 265 */     localStringBuilder.append("body, table {font-family:").append(this.font).append("; font-size: ");
/* 266 */     localStringBuilder.append(this.headerSize).append(";}").append(Constants.LINE_SEP);
/* 267 */     localStringBuilder.append("th {background: #336699; color: #FFFFFF; text-align: left;}").append(Constants.LINE_SEP);
/* 268 */     localStringBuilder.append("-->").append(Constants.LINE_SEP);
/* 269 */     localStringBuilder.append("</style>").append(Constants.LINE_SEP);
/* 270 */     localStringBuilder.append("</head>").append(Constants.LINE_SEP);
/* 271 */     localStringBuilder.append("<body bgcolor=\"#FFFFFF\" topmargin=\"6\" leftmargin=\"6\">").append(Constants.LINE_SEP);
/* 272 */     localStringBuilder.append("<hr size=\"1\" noshade>").append(Constants.LINE_SEP);
/* 273 */     localStringBuilder.append("Log session start time " + new Date() + "<br>").append(Constants.LINE_SEP);
/* 274 */     localStringBuilder.append("<br>").append(Constants.LINE_SEP);
/* 275 */     localStringBuilder.append("<table cellspacing=\"0\" cellpadding=\"4\" border=\"1\" bordercolor=\"#224466\" width=\"100%\">");
/*     */     
/* 277 */     localStringBuilder.append(Constants.LINE_SEP);
/* 278 */     localStringBuilder.append("<tr>").append(Constants.LINE_SEP);
/* 279 */     localStringBuilder.append("<th>Time</th>").append(Constants.LINE_SEP);
/* 280 */     localStringBuilder.append("<th>Thread</th>").append(Constants.LINE_SEP);
/* 281 */     localStringBuilder.append("<th>Level</th>").append(Constants.LINE_SEP);
/* 282 */     localStringBuilder.append("<th>Logger</th>").append(Constants.LINE_SEP);
/* 283 */     if (this.locationInfo) {
/* 284 */       localStringBuilder.append("<th>File:Line</th>").append(Constants.LINE_SEP);
/*     */     }
/* 286 */     localStringBuilder.append("<th>Message</th>").append(Constants.LINE_SEP);
/* 287 */     localStringBuilder.append("</tr>").append(Constants.LINE_SEP);
/* 288 */     return localStringBuilder.toString().getBytes(getCharset());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] getFooter()
/*     */   {
/* 297 */     StringBuilder localStringBuilder = new StringBuilder();
/* 298 */     localStringBuilder.append("</table>").append(Constants.LINE_SEP);
/* 299 */     localStringBuilder.append("<br>").append(Constants.LINE_SEP);
/* 300 */     localStringBuilder.append("</body></html>");
/* 301 */     return localStringBuilder.toString().getBytes(getCharset());
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
/*     */   @PluginFactory
/*     */   public static HTMLLayout createLayout(@PluginAttribute("locationInfo") String paramString1, @PluginAttribute("title") String paramString2, @PluginAttribute("contentType") String paramString3, @PluginAttribute("charset") String paramString4, @PluginAttribute("fontSize") String paramString5, @PluginAttribute("fontName") String paramString6)
/*     */   {
/* 322 */     Charset localCharset = Charsets.getSupportedCharset(paramString4, Charsets.UTF_8);
/* 323 */     if (paramString6 == null) {
/* 324 */       paramString6 = "arial,sans-serif";
/*     */     }
/* 326 */     FontSize localFontSize = FontSize.getFontSize(paramString5);
/* 327 */     paramString5 = localFontSize.getFontSize();
/* 328 */     String str = localFontSize.larger().getFontSize();
/* 329 */     boolean bool = Boolean.parseBoolean(paramString1);
/* 330 */     if (paramString2 == null) {
/* 331 */       paramString2 = "Log4j Log Messages";
/*     */     }
/* 333 */     if (paramString3 == null) {
/* 334 */       paramString3 = "text/html; charset=" + localCharset;
/*     */     }
/* 336 */     return new HTMLLayout(bool, paramString2, paramString3, localCharset, paramString6, paramString5, str);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\HTMLLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */