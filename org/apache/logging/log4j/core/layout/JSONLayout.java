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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="JSONLayout", category="Core", elementType="layout", printObject=true)
/*     */ public class JSONLayout
/*     */   extends AbstractStringLayout
/*     */ {
/*     */   private static final int DEFAULT_SIZE = 256;
/*     */   private static final String DEFAULT_EOL = "\r\n";
/*     */   private static final String COMPACT_EOL = "";
/*     */   private static final String DEFAULT_INDENT = "  ";
/*     */   private static final String COMPACT_INDENT = "";
/*  96 */   private static final String[] FORMATS = { "json" };
/*     */   
/*     */   private final boolean locationInfo;
/*     */   private final boolean properties;
/*     */   private final boolean complete;
/*     */   private final String eol;
/*     */   private final String indent1;
/*     */   private final String indent2;
/*     */   private final String indent3;
/*     */   private final String indent4;
/*     */   private volatile boolean firstLayoutDone;
/*     */   
/*     */   protected JSONLayout(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, Charset paramCharset)
/*     */   {
/* 110 */     super(paramCharset);
/* 111 */     this.locationInfo = paramBoolean1;
/* 112 */     this.properties = paramBoolean2;
/* 113 */     this.complete = paramBoolean3;
/* 114 */     this.eol = (paramBoolean4 ? "" : "\r\n");
/* 115 */     this.indent1 = (paramBoolean4 ? "" : "  ");
/* 116 */     this.indent2 = (this.indent1 + this.indent1);
/* 117 */     this.indent3 = (this.indent2 + this.indent1);
/* 118 */     this.indent4 = (this.indent3 + this.indent1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toSerializable(LogEvent paramLogEvent)
/*     */   {
/* 130 */     StringBuilder localStringBuilder = new StringBuilder(256);
/*     */     
/* 132 */     boolean bool = this.firstLayoutDone;
/* 133 */     if (!this.firstLayoutDone) {
/* 134 */       synchronized (this) {
/* 135 */         bool = this.firstLayoutDone;
/* 136 */         if (!bool) {
/* 137 */           this.firstLayoutDone = true;
/*     */         } else {
/* 139 */           localStringBuilder.append(',');
/* 140 */           localStringBuilder.append(this.eol);
/*     */         }
/*     */       }
/*     */     } else {
/* 144 */       localStringBuilder.append(',');
/* 145 */       localStringBuilder.append(this.eol);
/*     */     }
/* 147 */     localStringBuilder.append(this.indent1);
/* 148 */     localStringBuilder.append('{');
/* 149 */     localStringBuilder.append(this.eol);
/* 150 */     localStringBuilder.append(this.indent2);
/* 151 */     localStringBuilder.append("\"logger\":\"");
/* 152 */     ??? = paramLogEvent.getLoggerName();
/* 153 */     if (((String)???).isEmpty()) {
/* 154 */       ??? = "root";
/*     */     }
/* 156 */     localStringBuilder.append(Transform.escapeJsonControlCharacters((String)???));
/* 157 */     localStringBuilder.append("\",");
/* 158 */     localStringBuilder.append(this.eol);
/* 159 */     localStringBuilder.append(this.indent2);
/* 160 */     localStringBuilder.append("\"timestamp\":\"");
/* 161 */     localStringBuilder.append(paramLogEvent.getMillis());
/* 162 */     localStringBuilder.append("\",");
/* 163 */     localStringBuilder.append(this.eol);
/* 164 */     localStringBuilder.append(this.indent2);
/* 165 */     localStringBuilder.append("\"level\":\"");
/* 166 */     localStringBuilder.append(Transform.escapeJsonControlCharacters(String.valueOf(paramLogEvent.getLevel())));
/* 167 */     localStringBuilder.append("\",");
/* 168 */     localStringBuilder.append(this.eol);
/* 169 */     localStringBuilder.append(this.indent2);
/* 170 */     localStringBuilder.append("\"thread\":\"");
/* 171 */     localStringBuilder.append(Transform.escapeJsonControlCharacters(paramLogEvent.getThreadName()));
/* 172 */     localStringBuilder.append("\",");
/* 173 */     localStringBuilder.append(this.eol);
/*     */     
/* 175 */     Message localMessage = paramLogEvent.getMessage();
/* 176 */     Object localObject2; if (localMessage != null) {
/* 177 */       int i = 0;
/* 178 */       if ((localMessage instanceof MultiformatMessage)) {
/* 179 */         localObject2 = ((MultiformatMessage)localMessage).getFormats();
/* 180 */         for (Object localObject5 : localObject2) {
/* 181 */           if (((String)localObject5).equalsIgnoreCase("JSON")) {
/* 182 */             i = 1;
/* 183 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 187 */       localStringBuilder.append(this.indent2);
/* 188 */       localStringBuilder.append("\"message\":\"");
/* 189 */       if (i != 0) {
/* 190 */         localStringBuilder.append(((MultiformatMessage)localMessage).getFormattedMessage(FORMATS));
/*     */       } else {
/* 192 */         Transform.appendEscapingCDATA(localStringBuilder, paramLogEvent.getMessage().getFormattedMessage());
/*     */       }
/* 194 */       localStringBuilder.append('"');
/*     */     }
/*     */     
/* 197 */     if (paramLogEvent.getContextStack().getDepth() > 0) {
/* 198 */       localStringBuilder.append(",");
/* 199 */       localStringBuilder.append(this.eol);
/* 200 */       localStringBuilder.append("\"ndc\":");
/* 201 */       Transform.appendEscapingCDATA(localStringBuilder, paramLogEvent.getContextStack().toString());
/* 202 */       localStringBuilder.append("\"");
/*     */     }
/*     */     
/* 205 */     Throwable localThrowable = paramLogEvent.getThrown();
/* 206 */     Object localObject4; if (localThrowable != null) {
/* 207 */       localStringBuilder.append(",");
/* 208 */       localStringBuilder.append(this.eol);
/* 209 */       localStringBuilder.append(this.indent2);
/* 210 */       localStringBuilder.append("\"throwable\":\"");
/* 211 */       localObject2 = Throwables.toStringList(localThrowable);
/* 212 */       for (??? = ((List)localObject2).iterator(); ((Iterator)???).hasNext();) { localObject4 = (String)((Iterator)???).next();
/* 213 */         localStringBuilder.append(Transform.escapeJsonControlCharacters((String)localObject4));
/* 214 */         localStringBuilder.append("\\\\n");
/*     */       }
/* 216 */       localStringBuilder.append("\"");
/*     */     }
/*     */     
/* 219 */     if (this.locationInfo) {
/* 220 */       localObject2 = paramLogEvent.getSource();
/* 221 */       localStringBuilder.append(",");
/* 222 */       localStringBuilder.append(this.eol);
/* 223 */       localStringBuilder.append(this.indent2);
/* 224 */       localStringBuilder.append("\"LocationInfo\":{");
/* 225 */       localStringBuilder.append(this.eol);
/* 226 */       localStringBuilder.append(this.indent3);
/* 227 */       localStringBuilder.append("\"class\":\"");
/* 228 */       localStringBuilder.append(Transform.escapeJsonControlCharacters(((StackTraceElement)localObject2).getClassName()));
/* 229 */       localStringBuilder.append("\",");
/* 230 */       localStringBuilder.append(this.eol);
/* 231 */       localStringBuilder.append(this.indent3);
/* 232 */       localStringBuilder.append("\"method\":\"");
/* 233 */       localStringBuilder.append(Transform.escapeJsonControlCharacters(((StackTraceElement)localObject2).getMethodName()));
/* 234 */       localStringBuilder.append("\",");
/* 235 */       localStringBuilder.append(this.eol);
/* 236 */       localStringBuilder.append(this.indent3);
/* 237 */       localStringBuilder.append("\"file\":\"");
/* 238 */       localStringBuilder.append(Transform.escapeJsonControlCharacters(((StackTraceElement)localObject2).getFileName()));
/* 239 */       localStringBuilder.append("\",");
/* 240 */       localStringBuilder.append(this.eol);
/* 241 */       localStringBuilder.append(this.indent3);
/* 242 */       localStringBuilder.append("\"line\":\"");
/* 243 */       localStringBuilder.append(((StackTraceElement)localObject2).getLineNumber());
/* 244 */       localStringBuilder.append("\"");
/* 245 */       localStringBuilder.append(this.eol);
/* 246 */       localStringBuilder.append(this.indent2);
/* 247 */       localStringBuilder.append("}");
/*     */     }
/*     */     
/* 250 */     if ((this.properties) && (paramLogEvent.getContextMap().size() > 0)) {
/* 251 */       localStringBuilder.append(",");
/* 252 */       localStringBuilder.append(this.eol);
/* 253 */       localStringBuilder.append(this.indent2);
/* 254 */       localStringBuilder.append("\"Properties\":[");
/* 255 */       localStringBuilder.append(this.eol);
/* 256 */       localObject2 = paramLogEvent.getContextMap().entrySet();
/* 257 */       int j = 1;
/* 258 */       for (localObject4 = ((Set)localObject2).iterator(); ((Iterator)localObject4).hasNext();) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject4).next();
/* 259 */         localStringBuilder.append(this.indent3);
/* 260 */         localStringBuilder.append('{');
/* 261 */         localStringBuilder.append(this.eol);
/* 262 */         localStringBuilder.append(this.indent4);
/* 263 */         localStringBuilder.append("\"name\":\"");
/* 264 */         localStringBuilder.append(Transform.escapeJsonControlCharacters((String)localEntry.getKey()));
/* 265 */         localStringBuilder.append("\",");
/* 266 */         localStringBuilder.append(this.eol);
/* 267 */         localStringBuilder.append(this.indent4);
/* 268 */         localStringBuilder.append("\"value\":\"");
/* 269 */         localStringBuilder.append(Transform.escapeJsonControlCharacters(String.valueOf(localEntry.getValue())));
/* 270 */         localStringBuilder.append("\"");
/* 271 */         localStringBuilder.append(this.eol);
/* 272 */         localStringBuilder.append(this.indent3);
/* 273 */         localStringBuilder.append("}");
/* 274 */         if (j < ((Set)localObject2).size()) {
/* 275 */           localStringBuilder.append(",");
/*     */         }
/* 277 */         localStringBuilder.append(this.eol);
/* 278 */         j++;
/*     */       }
/* 280 */       localStringBuilder.append(this.indent2);
/* 281 */       localStringBuilder.append("]");
/*     */     }
/*     */     
/* 284 */     localStringBuilder.append(this.eol);
/* 285 */     localStringBuilder.append(this.indent1);
/* 286 */     localStringBuilder.append("}");
/*     */     
/* 288 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] getHeader()
/*     */   {
/* 298 */     if (!this.complete) {
/* 299 */       return null;
/*     */     }
/* 301 */     StringBuilder localStringBuilder = new StringBuilder();
/* 302 */     localStringBuilder.append('[');
/* 303 */     localStringBuilder.append(this.eol);
/* 304 */     return localStringBuilder.toString().getBytes(getCharset());
/*     */   }
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
/* 317 */     return (this.eol + "]" + this.eol).getBytes(getCharset());
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
/* 331 */     HashMap localHashMap = new HashMap();
/* 332 */     localHashMap.put("version", "2.0");
/* 333 */     return localHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getContentType()
/*     */   {
/* 341 */     return "application/json; charset=" + getCharset();
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
/*     */   @PluginFactory
/*     */   public static JSONLayout createLayout(@PluginAttribute("locationInfo") String paramString1, @PluginAttribute("properties") String paramString2, @PluginAttribute("complete") String paramString3, @PluginAttribute("compact") String paramString4, @PluginAttribute("charset") String paramString5)
/*     */   {
/* 366 */     Charset localCharset = Charsets.getSupportedCharset(paramString5, Charsets.UTF_8);
/* 367 */     boolean bool1 = Boolean.parseBoolean(paramString1);
/* 368 */     boolean bool2 = Boolean.parseBoolean(paramString2);
/* 369 */     boolean bool3 = Boolean.parseBoolean(paramString3);
/* 370 */     boolean bool4 = Boolean.parseBoolean(paramString4);
/* 371 */     return new JSONLayout(bool1, bool2, bool3, bool4, localCharset);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\JSONLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */