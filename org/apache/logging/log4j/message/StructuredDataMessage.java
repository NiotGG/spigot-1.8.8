/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.util.Map;
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
/*     */ public class StructuredDataMessage
/*     */   extends MapMessage
/*     */ {
/*     */   private static final long serialVersionUID = 1703221292892071920L;
/*     */   private static final int MAX_LENGTH = 32;
/*     */   private static final int HASHVAL = 31;
/*     */   private StructuredDataId id;
/*     */   private String message;
/*     */   private String type;
/*     */   
/*     */   public static enum Format
/*     */   {
/*  45 */     XML, 
/*     */     
/*  47 */     FULL;
/*     */     
/*     */ 
/*     */ 
/*     */     private Format() {}
/*     */   }
/*     */   
/*     */ 
/*     */   public StructuredDataMessage(String paramString1, String paramString2, String paramString3)
/*     */   {
/*  57 */     this.id = new StructuredDataId(paramString1, null, null);
/*  58 */     this.message = paramString2;
/*  59 */     this.type = paramString3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StructuredDataMessage(String paramString1, String paramString2, String paramString3, Map<String, String> paramMap)
/*     */   {
/*  70 */     super(paramMap);
/*  71 */     this.id = new StructuredDataId(paramString1, null, null);
/*  72 */     this.message = paramString2;
/*  73 */     this.type = paramString3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StructuredDataMessage(StructuredDataId paramStructuredDataId, String paramString1, String paramString2)
/*     */   {
/*  83 */     this.id = paramStructuredDataId;
/*  84 */     this.message = paramString1;
/*  85 */     this.type = paramString2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StructuredDataMessage(StructuredDataId paramStructuredDataId, String paramString1, String paramString2, Map<String, String> paramMap)
/*     */   {
/*  97 */     super(paramMap);
/*  98 */     this.id = paramStructuredDataId;
/*  99 */     this.message = paramString1;
/* 100 */     this.type = paramString2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private StructuredDataMessage(StructuredDataMessage paramStructuredDataMessage, Map<String, String> paramMap)
/*     */   {
/* 110 */     super(paramMap);
/* 111 */     this.id = paramStructuredDataMessage.id;
/* 112 */     this.message = paramStructuredDataMessage.message;
/* 113 */     this.type = paramStructuredDataMessage.type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected StructuredDataMessage() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFormats()
/*     */   {
/* 130 */     String[] arrayOfString = new String[Format.values().length];
/* 131 */     int i = 0;
/* 132 */     for (Format localFormat : Format.values()) {
/* 133 */       arrayOfString[(i++)] = localFormat.name();
/*     */     }
/* 135 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public StructuredDataId getId()
/*     */   {
/* 143 */     return this.id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setId(String paramString)
/*     */   {
/* 151 */     this.id = new StructuredDataId(paramString, null, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setId(StructuredDataId paramStructuredDataId)
/*     */   {
/* 159 */     this.id = paramStructuredDataId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getType()
/*     */   {
/* 167 */     return this.type;
/*     */   }
/*     */   
/*     */   protected void setType(String paramString) {
/* 171 */     if (paramString.length() > 32) {
/* 172 */       throw new IllegalArgumentException("structured data type exceeds maximum length of 32 characters: " + paramString);
/*     */     }
/* 174 */     this.type = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormat()
/*     */   {
/* 183 */     return this.message;
/*     */   }
/*     */   
/*     */   protected void setMessageFormat(String paramString) {
/* 187 */     this.message = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void validate(String paramString1, String paramString2)
/*     */   {
/* 193 */     validateKey(paramString1);
/*     */   }
/*     */   
/*     */   private void validateKey(String paramString) {
/* 197 */     if (paramString.length() > 32) {
/* 198 */       throw new IllegalArgumentException("Structured data keys are limited to 32 characters. key: " + paramString);
/*     */     }
/* 200 */     char[] arrayOfChar1 = paramString.toCharArray();
/* 201 */     for (int k : arrayOfChar1) {
/* 202 */       if ((k < 33) || (k > 126) || (k == 61) || (k == 93) || (k == 34)) {
/* 203 */         throw new IllegalArgumentException("Structured data keys must contain printable US ASCII charactersand may not contain a space, =, ], or \"");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String asString()
/*     */   {
/* 216 */     return asString(Format.FULL, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String asString(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 229 */       return asString((Format)EnglishEnums.valueOf(Format.class, paramString), null);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {}
/* 231 */     return asString();
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
/*     */   public final String asString(Format paramFormat, StructuredDataId paramStructuredDataId)
/*     */   {
/* 245 */     StringBuilder localStringBuilder = new StringBuilder();
/* 246 */     boolean bool = Format.FULL.equals(paramFormat);
/* 247 */     if (bool) {
/* 248 */       localObject = getType();
/* 249 */       if (localObject == null) {
/* 250 */         return localStringBuilder.toString();
/*     */       }
/* 252 */       localStringBuilder.append(getType()).append(" ");
/*     */     }
/* 254 */     Object localObject = getId();
/* 255 */     if (localObject != null) {
/* 256 */       localObject = ((StructuredDataId)localObject).makeId(paramStructuredDataId);
/*     */     } else {
/* 258 */       localObject = paramStructuredDataId;
/*     */     }
/* 260 */     if ((localObject == null) || (((StructuredDataId)localObject).getName() == null)) {
/* 261 */       return localStringBuilder.toString();
/*     */     }
/* 263 */     localStringBuilder.append("[");
/* 264 */     localStringBuilder.append(localObject);
/* 265 */     localStringBuilder.append(" ");
/* 266 */     appendMap(localStringBuilder);
/* 267 */     localStringBuilder.append("]");
/* 268 */     if (bool) {
/* 269 */       String str = getFormat();
/* 270 */       if (str != null) {
/* 271 */         localStringBuilder.append(" ").append(str);
/*     */       }
/*     */     }
/* 274 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedMessage()
/*     */   {
/* 283 */     return asString(Format.FULL, null);
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
/*     */   public String getFormattedMessage(String[] paramArrayOfString)
/*     */   {
/* 297 */     if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
/* 298 */       for (String str : paramArrayOfString) {
/* 299 */         if (Format.XML.name().equalsIgnoreCase(str))
/* 300 */           return asXML();
/* 301 */         if (Format.FULL.name().equalsIgnoreCase(str)) {
/* 302 */           return asString(Format.FULL, null);
/*     */         }
/*     */       }
/* 305 */       return asString(null, null);
/*     */     }
/* 307 */     return asString(Format.FULL, null);
/*     */   }
/*     */   
/*     */   private String asXML() {
/* 311 */     StringBuilder localStringBuilder = new StringBuilder();
/* 312 */     StructuredDataId localStructuredDataId = getId();
/* 313 */     if ((localStructuredDataId == null) || (localStructuredDataId.getName() == null) || (this.type == null)) {
/* 314 */       return localStringBuilder.toString();
/*     */     }
/* 316 */     localStringBuilder.append("<StructuredData>\n");
/* 317 */     localStringBuilder.append("<type>").append(this.type).append("</type>\n");
/* 318 */     localStringBuilder.append("<id>").append(localStructuredDataId).append("</id>\n");
/* 319 */     super.asXML(localStringBuilder);
/* 320 */     localStringBuilder.append("</StructuredData>\n");
/* 321 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 326 */     return asString(null, null);
/*     */   }
/*     */   
/*     */ 
/*     */   public MapMessage newInstance(Map<String, String> paramMap)
/*     */   {
/* 332 */     return new StructuredDataMessage(this, paramMap);
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 337 */     if (this == paramObject) {
/* 338 */       return true;
/*     */     }
/* 340 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 341 */       return false;
/*     */     }
/*     */     
/* 344 */     StructuredDataMessage localStructuredDataMessage = (StructuredDataMessage)paramObject;
/*     */     
/* 346 */     if (!super.equals(paramObject)) {
/* 347 */       return false;
/*     */     }
/* 349 */     if (this.type != null ? !this.type.equals(localStructuredDataMessage.type) : localStructuredDataMessage.type != null) {
/* 350 */       return false;
/*     */     }
/* 352 */     if (this.id != null ? !this.id.equals(localStructuredDataMessage.id) : localStructuredDataMessage.id != null) {
/* 353 */       return false;
/*     */     }
/* 355 */     if (this.message != null ? !this.message.equals(localStructuredDataMessage.message) : localStructuredDataMessage.message != null) {
/* 356 */       return false;
/*     */     }
/*     */     
/* 359 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 364 */     int i = super.hashCode();
/* 365 */     i = 31 * i + (this.type != null ? this.type.hashCode() : 0);
/* 366 */     i = 31 * i + (this.id != null ? this.id.hashCode() : 0);
/* 367 */     i = 31 * i + (this.message != null ? this.message.hashCode() : 0);
/* 368 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\StructuredDataMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */