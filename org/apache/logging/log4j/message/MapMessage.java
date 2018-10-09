/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.logging.log4j.util.EnglishEnums;
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
/*     */ public class MapMessage
/*     */   implements MultiformatMessage
/*     */ {
/*     */   private static final long serialVersionUID = -5031471831131487120L;
/*     */   private final SortedMap<String, String> data;
/*     */   
/*     */   public static enum MapFormat
/*     */   {
/*  36 */     XML, 
/*     */     
/*  38 */     JSON, 
/*     */     
/*  40 */     JAVA;
/*     */     
/*     */ 
/*     */ 
/*     */     private MapFormat() {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public MapMessage()
/*     */   {
/*  51 */     this.data = new TreeMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public MapMessage(Map<String, String> paramMap)
/*     */   {
/*  59 */     this.data = ((paramMap instanceof SortedMap) ? (SortedMap)paramMap : new TreeMap(paramMap));
/*     */   }
/*     */   
/*     */   public String[] getFormats()
/*     */   {
/*  64 */     String[] arrayOfString = new String[MapFormat.values().length];
/*  65 */     int i = 0;
/*  66 */     for (MapFormat localMapFormat : MapFormat.values()) {
/*  67 */       arrayOfString[(i++)] = localMapFormat.name();
/*     */     }
/*  69 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] getParameters()
/*     */   {
/*  78 */     return this.data.values().toArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormat()
/*     */   {
/*  87 */     return "";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, String> getData()
/*     */   {
/*  95 */     return Collections.unmodifiableMap(this.data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 102 */     this.data.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void put(String paramString1, String paramString2)
/*     */   {
/* 111 */     if (paramString2 == null) {
/* 112 */       throw new IllegalArgumentException("No value provided for key " + paramString1);
/*     */     }
/* 114 */     validate(paramString1, paramString2);
/* 115 */     this.data.put(paramString1, paramString2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void validate(String paramString1, String paramString2) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void putAll(Map<String, String> paramMap)
/*     */   {
/* 127 */     this.data.putAll(paramMap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String get(String paramString)
/*     */   {
/* 136 */     return (String)this.data.get(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String remove(String paramString)
/*     */   {
/* 145 */     return (String)this.data.remove(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String asString()
/*     */   {
/* 154 */     return asString((MapFormat)null);
/*     */   }
/*     */   
/*     */   public String asString(String paramString) {
/*     */     try {
/* 159 */       return asString((MapFormat)EnglishEnums.valueOf(MapFormat.class, paramString));
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {}
/* 161 */     return asString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String asString(MapFormat paramMapFormat)
/*     */   {
/* 171 */     StringBuilder localStringBuilder = new StringBuilder();
/* 172 */     if (paramMapFormat == null) {
/* 173 */       appendMap(localStringBuilder);
/*     */     } else {
/* 175 */       switch (paramMapFormat) {
/*     */       case XML: 
/* 177 */         asXML(localStringBuilder);
/* 178 */         break;
/*     */       
/*     */       case JSON: 
/* 181 */         asJSON(localStringBuilder);
/* 182 */         break;
/*     */       
/*     */       case JAVA: 
/* 185 */         asJava(localStringBuilder);
/* 186 */         break;
/*     */       
/*     */       default: 
/* 189 */         appendMap(localStringBuilder);
/*     */       }
/*     */       
/*     */     }
/* 193 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   public void asXML(StringBuilder paramStringBuilder) {
/* 197 */     paramStringBuilder.append("<Map>\n");
/* 198 */     for (Map.Entry localEntry : this.data.entrySet()) {
/* 199 */       paramStringBuilder.append("  <Entry key=\"").append((String)localEntry.getKey()).append("\">").append((String)localEntry.getValue()).append("</Entry>\n");
/*     */     }
/*     */     
/* 202 */     paramStringBuilder.append("</Map>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedMessage()
/*     */   {
/* 211 */     return asString();
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
/*     */   public String getFormattedMessage(String[] paramArrayOfString)
/*     */   {
/* 224 */     if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
/* 225 */       return asString();
/*     */     }
/* 227 */     for (String str : paramArrayOfString) {
/* 228 */       for (MapFormat localMapFormat : MapFormat.values()) {
/* 229 */         if (localMapFormat.name().equalsIgnoreCase(str)) {
/* 230 */           return asString(localMapFormat);
/*     */         }
/*     */       }
/*     */     }
/* 234 */     return asString();
/*     */   }
/*     */   
/*     */   protected void appendMap(StringBuilder paramStringBuilder)
/*     */   {
/* 239 */     int i = 1;
/* 240 */     for (Map.Entry localEntry : this.data.entrySet()) {
/* 241 */       if (i == 0) {
/* 242 */         paramStringBuilder.append(" ");
/*     */       }
/* 244 */       i = 0;
/* 245 */       paramStringBuilder.append((String)localEntry.getKey()).append("=\"").append((String)localEntry.getValue()).append("\"");
/*     */     }
/*     */   }
/*     */   
/*     */   protected void asJSON(StringBuilder paramStringBuilder) {
/* 250 */     int i = 1;
/* 251 */     paramStringBuilder.append("{");
/* 252 */     for (Map.Entry localEntry : this.data.entrySet()) {
/* 253 */       if (i == 0) {
/* 254 */         paramStringBuilder.append(", ");
/*     */       }
/* 256 */       i = 0;
/* 257 */       paramStringBuilder.append("\"").append((String)localEntry.getKey()).append("\":");
/* 258 */       paramStringBuilder.append("\"").append((String)localEntry.getValue()).append("\"");
/*     */     }
/* 260 */     paramStringBuilder.append("}");
/*     */   }
/*     */   
/*     */   protected void asJava(StringBuilder paramStringBuilder)
/*     */   {
/* 265 */     int i = 1;
/* 266 */     paramStringBuilder.append("{");
/* 267 */     for (Map.Entry localEntry : this.data.entrySet()) {
/* 268 */       if (i == 0) {
/* 269 */         paramStringBuilder.append(", ");
/*     */       }
/* 271 */       i = 0;
/* 272 */       paramStringBuilder.append((String)localEntry.getKey()).append("=\"").append((String)localEntry.getValue()).append("\"");
/*     */     }
/* 274 */     paramStringBuilder.append("}");
/*     */   }
/*     */   
/*     */   public MapMessage newInstance(Map<String, String> paramMap) {
/* 278 */     return new MapMessage(paramMap);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 283 */     return asString();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 288 */     if (this == paramObject) {
/* 289 */       return true;
/*     */     }
/* 291 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 292 */       return false;
/*     */     }
/*     */     
/* 295 */     MapMessage localMapMessage = (MapMessage)paramObject;
/*     */     
/* 297 */     return this.data.equals(localMapMessage.data);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 302 */     return this.data.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Throwable getThrowable()
/*     */   {
/* 312 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\MapMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */