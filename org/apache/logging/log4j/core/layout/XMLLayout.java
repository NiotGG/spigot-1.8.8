/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.ThreadContext.ContextStack;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.core.helpers.Throwables;
/*     */ import org.apache.logging.log4j.core.helpers.Transform;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.MultiformatMessage;
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
/*     */ @Plugin(name="XMLLayout", category="Core", elementType="layout", printObject=true)
/*     */ public class XMLLayout
/*     */   extends AbstractStringLayout
/*     */ {
/*     */   private static final String XML_NAMESPACE = "http://logging.apache.org/log4j/2.0/events";
/*     */   private static final String ROOT_TAG = "Events";
/*     */   private static final int DEFAULT_SIZE = 256;
/*     */   private static final String DEFAULT_EOL = "\r\n";
/*     */   private static final String COMPACT_EOL = "";
/*     */   private static final String DEFAULT_INDENT = "  ";
/*     */   private static final String COMPACT_INDENT = "";
/*     */   private static final String DEFAULT_NS_PREFIX = "log4j";
/*  93 */   private static final String[] FORMATS = { "xml" };
/*     */   
/*     */   private final boolean locationInfo;
/*     */   private final boolean properties;
/*     */   private final boolean complete;
/*     */   private final String namespacePrefix;
/*     */   private final String eol;
/*     */   private final String indent1;
/*     */   private final String indent2;
/*     */   private final String indent3;
/*     */   
/*     */   protected XMLLayout(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, String paramString, Charset paramCharset)
/*     */   {
/* 106 */     super(paramCharset);
/* 107 */     this.locationInfo = paramBoolean1;
/* 108 */     this.properties = paramBoolean2;
/* 109 */     this.complete = paramBoolean3;
/* 110 */     this.eol = (paramBoolean4 ? "" : "\r\n");
/* 111 */     this.indent1 = (paramBoolean4 ? "" : "  ");
/* 112 */     this.indent2 = (this.indent1 + this.indent1);
/* 113 */     this.indent3 = (this.indent2 + this.indent1);
/* 114 */     this.namespacePrefix = ((Strings.isEmpty(paramString) ? "log4j" : paramString) + ":");
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
/* 125 */     StringBuilder localStringBuilder = new StringBuilder(256);
/*     */     
/* 127 */     localStringBuilder.append(this.indent1);
/* 128 */     localStringBuilder.append('<');
/* 129 */     if (!this.complete) {
/* 130 */       localStringBuilder.append(this.namespacePrefix);
/*     */     }
/* 132 */     localStringBuilder.append("Event logger=\"");
/* 133 */     String str1 = paramLogEvent.getLoggerName();
/* 134 */     if (str1.isEmpty()) {
/* 135 */       str1 = "root";
/*     */     }
/* 137 */     localStringBuilder.append(Transform.escapeHtmlTags(str1));
/* 138 */     localStringBuilder.append("\" timestamp=\"");
/* 139 */     localStringBuilder.append(paramLogEvent.getMillis());
/* 140 */     localStringBuilder.append("\" level=\"");
/* 141 */     localStringBuilder.append(Transform.escapeHtmlTags(String.valueOf(paramLogEvent.getLevel())));
/* 142 */     localStringBuilder.append("\" thread=\"");
/* 143 */     localStringBuilder.append(Transform.escapeHtmlTags(paramLogEvent.getThreadName()));
/* 144 */     localStringBuilder.append("\">");
/* 145 */     localStringBuilder.append(this.eol);
/*     */     
/* 147 */     Message localMessage = paramLogEvent.getMessage();
/* 148 */     Object localObject1; if (localMessage != null) {
/* 149 */       int i = 0;
/* 150 */       if ((localMessage instanceof MultiformatMessage)) {
/* 151 */         localObject1 = ((MultiformatMessage)localMessage).getFormats();
/* 152 */         for (Object localObject3 : localObject1) {
/* 153 */           if (((String)localObject3).equalsIgnoreCase("XML")) {
/* 154 */             i = 1;
/* 155 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 159 */       localStringBuilder.append(this.indent2);
/* 160 */       localStringBuilder.append('<');
/* 161 */       if (!this.complete) {
/* 162 */         localStringBuilder.append(this.namespacePrefix);
/*     */       }
/* 164 */       localStringBuilder.append("Message>");
/* 165 */       if (i != 0) {
/* 166 */         localStringBuilder.append(((MultiformatMessage)localMessage).getFormattedMessage(FORMATS));
/*     */       } else {
/* 168 */         localStringBuilder.append("<![CDATA[");
/*     */         
/*     */ 
/* 171 */         Transform.appendEscapingCDATA(localStringBuilder, paramLogEvent.getMessage().getFormattedMessage());
/* 172 */         localStringBuilder.append("]]>");
/*     */       }
/* 174 */       localStringBuilder.append("</");
/* 175 */       if (!this.complete) {
/* 176 */         localStringBuilder.append(this.namespacePrefix);
/*     */       }
/* 178 */       localStringBuilder.append("Message>");
/* 179 */       localStringBuilder.append(this.eol);
/*     */     }
/*     */     
/* 182 */     if (paramLogEvent.getContextStack().getDepth() > 0) {
/* 183 */       localStringBuilder.append(this.indent2);
/* 184 */       localStringBuilder.append('<');
/* 185 */       if (!this.complete) {
/* 186 */         localStringBuilder.append(this.namespacePrefix);
/*     */       }
/* 188 */       localStringBuilder.append("NDC><![CDATA[");
/* 189 */       Transform.appendEscapingCDATA(localStringBuilder, paramLogEvent.getContextStack().toString());
/* 190 */       localStringBuilder.append("]]></");
/* 191 */       if (!this.complete) {
/* 192 */         localStringBuilder.append(this.namespacePrefix);
/*     */       }
/* 194 */       localStringBuilder.append("NDC>");
/* 195 */       localStringBuilder.append(this.eol);
/*     */     }
/*     */     
/* 198 */     Throwable localThrowable = paramLogEvent.getThrown();
/* 199 */     if (localThrowable != null) {
/* 200 */       localObject1 = Throwables.toStringList(localThrowable);
/* 201 */       localStringBuilder.append(this.indent2);
/* 202 */       localStringBuilder.append('<');
/* 203 */       if (!this.complete) {
/* 204 */         localStringBuilder.append(this.namespacePrefix);
/*     */       }
/* 206 */       localStringBuilder.append("Throwable><![CDATA[");
/* 207 */       for (??? = ((List)localObject1).iterator(); ((Iterator)???).hasNext();) { String str2 = (String)((Iterator)???).next();
/* 208 */         Transform.appendEscapingCDATA(localStringBuilder, str2);
/* 209 */         localStringBuilder.append(this.eol);
/*     */       }
/* 211 */       localStringBuilder.append("]]></");
/* 212 */       if (!this.complete) {
/* 213 */         localStringBuilder.append(this.namespacePrefix);
/*     */       }
/* 215 */       localStringBuilder.append("Throwable>");
/* 216 */       localStringBuilder.append(this.eol);
/*     */     }
/*     */     
/* 219 */     if (this.locationInfo) {
/* 220 */       localObject1 = paramLogEvent.getSource();
/* 221 */       localStringBuilder.append(this.indent2);
/* 222 */       localStringBuilder.append('<');
/* 223 */       if (!this.complete) {
/* 224 */         localStringBuilder.append(this.namespacePrefix);
/*     */       }
/* 226 */       localStringBuilder.append("LocationInfo class=\"");
/* 227 */       localStringBuilder.append(Transform.escapeHtmlTags(((StackTraceElement)localObject1).getClassName()));
/* 228 */       localStringBuilder.append("\" method=\"");
/* 229 */       localStringBuilder.append(Transform.escapeHtmlTags(((StackTraceElement)localObject1).getMethodName()));
/* 230 */       localStringBuilder.append("\" file=\"");
/* 231 */       localStringBuilder.append(Transform.escapeHtmlTags(((StackTraceElement)localObject1).getFileName()));
/* 232 */       localStringBuilder.append("\" line=\"");
/* 233 */       localStringBuilder.append(((StackTraceElement)localObject1).getLineNumber());
/* 234 */       localStringBuilder.append("\"/>");
/* 235 */       localStringBuilder.append(this.eol);
/*     */     }
/*     */     
/* 238 */     if ((this.properties) && (paramLogEvent.getContextMap().size() > 0)) {
/* 239 */       localStringBuilder.append(this.indent2);
/* 240 */       localStringBuilder.append('<');
/* 241 */       if (!this.complete) {
/* 242 */         localStringBuilder.append(this.namespacePrefix);
/*     */       }
/* 244 */       localStringBuilder.append("Properties>");
/* 245 */       localStringBuilder.append(this.eol);
/* 246 */       for (localObject1 = paramLogEvent.getContextMap().entrySet().iterator(); ((Iterator)localObject1).hasNext();) { ??? = (Map.Entry)((Iterator)localObject1).next();
/* 247 */         localStringBuilder.append(this.indent3);
/* 248 */         localStringBuilder.append('<');
/* 249 */         if (!this.complete) {
/* 250 */           localStringBuilder.append(this.namespacePrefix);
/*     */         }
/* 252 */         localStringBuilder.append("Data name=\"");
/* 253 */         localStringBuilder.append(Transform.escapeHtmlTags((String)((Map.Entry)???).getKey()));
/* 254 */         localStringBuilder.append("\" value=\"");
/* 255 */         localStringBuilder.append(Transform.escapeHtmlTags(String.valueOf(((Map.Entry)???).getValue())));
/* 256 */         localStringBuilder.append("\"/>");
/* 257 */         localStringBuilder.append(this.eol);
/*     */       }
/* 259 */       localStringBuilder.append(this.indent2);
/* 260 */       localStringBuilder.append("</");
/* 261 */       if (!this.complete) {
/* 262 */         localStringBuilder.append(this.namespacePrefix);
/*     */       }
/* 264 */       localStringBuilder.append("Properties>");
/* 265 */       localStringBuilder.append(this.eol);
/*     */     }
/*     */     
/* 268 */     localStringBuilder.append(this.indent1);
/* 269 */     localStringBuilder.append("</");
/* 270 */     if (!this.complete) {
/* 271 */       localStringBuilder.append(this.namespacePrefix);
/*     */     }
/* 273 */     localStringBuilder.append("Event>");
/* 274 */     localStringBuilder.append(this.eol);
/*     */     
/* 276 */     return localStringBuilder.toString();
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
/*     */   public byte[] getHeader()
/*     */   {
/* 290 */     if (!this.complete) {
/* 291 */       return null;
/*     */     }
/* 293 */     StringBuilder localStringBuilder = new StringBuilder();
/* 294 */     localStringBuilder.append("<?xml version=\"1.0\" encoding=\"");
/* 295 */     localStringBuilder.append(getCharset().name());
/* 296 */     localStringBuilder.append("\"?>");
/* 297 */     localStringBuilder.append(this.eol);
/*     */     
/* 299 */     localStringBuilder.append('<');
/* 300 */     localStringBuilder.append("Events");
/* 301 */     localStringBuilder.append(" xmlns=\"http://logging.apache.org/log4j/2.0/events\">");
/* 302 */     localStringBuilder.append(this.eol);
/* 303 */     return localStringBuilder.toString().getBytes(getCharset());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] getFooter()
/*     */   {
/* 314 */     if (!this.complete) {
/* 315 */       return null;
/*     */     }
/* 317 */     return ("</Events>" + this.eol).getBytes(getCharset());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, String> getContentFormat()
/*     */   {
/* 328 */     HashMap localHashMap = new HashMap();
/*     */     
/* 330 */     localHashMap.put("xsd", "log4j-events.xsd");
/* 331 */     localHashMap.put("version", "2.0");
/* 332 */     return localHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getContentType()
/*     */   {
/* 340 */     return "text/xml; charset=" + getCharset();
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
/*     */   @PluginFactory
/*     */   public static XMLLayout createLayout(@PluginAttribute("locationInfo") String paramString1, @PluginAttribute("properties") String paramString2, @PluginAttribute("complete") String paramString3, @PluginAttribute("compact") String paramString4, @PluginAttribute("namespacePrefix") String paramString5, @PluginAttribute("charset") String paramString6)
/*     */   {
/* 362 */     Charset localCharset = Charsets.getSupportedCharset(paramString6, Charsets.UTF_8);
/* 363 */     boolean bool1 = Boolean.parseBoolean(paramString1);
/* 364 */     boolean bool2 = Boolean.parseBoolean(paramString2);
/* 365 */     boolean bool3 = Boolean.parseBoolean(paramString3);
/* 366 */     boolean bool4 = Boolean.parseBoolean(paramString4);
/* 367 */     return new XMLLayout(bool1, bool2, bool3, bool4, paramString5, localCharset);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\XMLLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */