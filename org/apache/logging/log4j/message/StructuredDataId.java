/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class StructuredDataId
/*     */   implements Serializable
/*     */ {
/*  29 */   public static final StructuredDataId TIME_QUALITY = new StructuredDataId("timeQuality", null, new String[] { "tzKnown", "isSynced", "syncAccuracy" });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  34 */   public static final StructuredDataId ORIGIN = new StructuredDataId("origin", null, new String[] { "ip", "enterpriseId", "software", "swVersion" });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  39 */   public static final StructuredDataId META = new StructuredDataId("meta", null, new String[] { "sequenceId", "sysUpTime", "language" });
/*     */   
/*     */   public static final int RESERVED = -1;
/*     */   
/*     */   private static final long serialVersionUID = 9031746276396249990L;
/*     */   
/*     */   private static final int MAX_LENGTH = 32;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final int enterpriseNumber;
/*     */   
/*     */   private final String[] required;
/*     */   
/*     */   private final String[] optional;
/*     */   
/*     */   protected StructuredDataId(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2)
/*     */   {
/*  57 */     int i = -1;
/*  58 */     if (paramString != null) {
/*  59 */       if (paramString.length() > 32) {
/*  60 */         throw new IllegalArgumentException(String.format("Length of id %s exceeds maximum of %d characters", new Object[] { paramString, Integer.valueOf(32) }));
/*     */       }
/*     */       
/*  63 */       i = paramString.indexOf("@");
/*     */     }
/*     */     
/*  66 */     if (i > 0) {
/*  67 */       this.name = paramString.substring(0, i);
/*  68 */       this.enterpriseNumber = Integer.parseInt(paramString.substring(i + 1));
/*     */     } else {
/*  70 */       this.name = paramString;
/*  71 */       this.enterpriseNumber = -1;
/*     */     }
/*  73 */     this.required = paramArrayOfString1;
/*  74 */     this.optional = paramArrayOfString2;
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
/*     */   public StructuredDataId(String paramString, int paramInt, String[] paramArrayOfString1, String[] paramArrayOfString2)
/*     */   {
/*  87 */     if (paramString == null) {
/*  88 */       throw new IllegalArgumentException("No structured id name was supplied");
/*     */     }
/*  90 */     if (paramString.contains("@")) {
/*  91 */       throw new IllegalArgumentException("Structured id name cannot contain an '@");
/*     */     }
/*  93 */     if (paramInt <= 0) {
/*  94 */       throw new IllegalArgumentException("No enterprise number was supplied");
/*     */     }
/*  96 */     this.name = paramString;
/*  97 */     this.enterpriseNumber = paramInt;
/*  98 */     String str = paramString + "@" + paramInt;
/*  99 */     if (str.length() > 32) {
/* 100 */       throw new IllegalArgumentException("Length of id exceeds maximum of 32 characters: " + str);
/*     */     }
/* 102 */     this.required = paramArrayOfString1;
/* 103 */     this.optional = paramArrayOfString2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StructuredDataId makeId(StructuredDataId paramStructuredDataId)
/*     */   {
/* 112 */     if (paramStructuredDataId == null) {
/* 113 */       return this;
/*     */     }
/* 115 */     return makeId(paramStructuredDataId.getName(), paramStructuredDataId.getEnterpriseNumber());
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
/*     */   public StructuredDataId makeId(String paramString, int paramInt)
/*     */   {
/* 128 */     if (paramInt <= 0)
/* 129 */       return this;
/*     */     String str;
/* 131 */     String[] arrayOfString1; String[] arrayOfString2; if (this.name != null) {
/* 132 */       str = this.name;
/* 133 */       arrayOfString1 = this.required;
/* 134 */       arrayOfString2 = this.optional;
/*     */     } else {
/* 136 */       str = paramString;
/* 137 */       arrayOfString1 = null;
/* 138 */       arrayOfString2 = null;
/*     */     }
/*     */     
/* 141 */     return new StructuredDataId(str, paramInt, arrayOfString1, arrayOfString2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getRequired()
/*     */   {
/* 149 */     return this.required;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getOptional()
/*     */   {
/* 157 */     return this.optional;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 165 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEnterpriseNumber()
/*     */   {
/* 173 */     return this.enterpriseNumber;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isReserved()
/*     */   {
/* 181 */     return this.enterpriseNumber <= 0;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 186 */     return this.name + "@" + this.enterpriseNumber;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\StructuredDataId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */