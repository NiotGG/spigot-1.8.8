/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ public class DefaultCookie
/*     */   implements Cookie
/*     */ {
/*     */   private final String name;
/*     */   private String value;
/*     */   private String domain;
/*     */   private String path;
/*     */   private String comment;
/*     */   private String commentUrl;
/*     */   private boolean discard;
/*  36 */   private Set<Integer> ports = Collections.emptySet();
/*  37 */   private Set<Integer> unmodifiablePorts = this.ports;
/*  38 */   private long maxAge = Long.MIN_VALUE;
/*     */   
/*     */   private int version;
/*     */   
/*     */   private boolean secure;
/*     */   private boolean httpOnly;
/*     */   
/*     */   public DefaultCookie(String paramString1, String paramString2)
/*     */   {
/*  47 */     if (paramString1 == null) {
/*  48 */       throw new NullPointerException("name");
/*     */     }
/*  50 */     paramString1 = paramString1.trim();
/*  51 */     if (paramString1.isEmpty()) {
/*  52 */       throw new IllegalArgumentException("empty name");
/*     */     }
/*     */     
/*  55 */     for (int i = 0; i < paramString1.length(); i++) {
/*  56 */       int j = paramString1.charAt(i);
/*  57 */       if (j > 127) {
/*  58 */         throw new IllegalArgumentException("name contains non-ascii character: " + paramString1);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  63 */       switch (j) {
/*     */       case 9: case 10: case 11: case 12: case 13: 
/*     */       case 32: case 44: case 59: case 61: 
/*  66 */         throw new IllegalArgumentException("name contains one of the following prohibited characters: =,; \\t\\r\\n\\v\\f: " + paramString1);
/*     */       }
/*     */       
/*     */     }
/*     */     
/*     */ 
/*  72 */     if (paramString1.charAt(0) == '$') {
/*  73 */       throw new IllegalArgumentException("name starting with '$' not allowed: " + paramString1);
/*     */     }
/*     */     
/*  76 */     this.name = paramString1;
/*  77 */     setValue(paramString2);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  82 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getValue()
/*     */   {
/*  87 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setValue(String paramString)
/*     */   {
/*  92 */     if (paramString == null) {
/*  93 */       throw new NullPointerException("value");
/*     */     }
/*  95 */     this.value = paramString;
/*     */   }
/*     */   
/*     */   public String getDomain()
/*     */   {
/* 100 */     return this.domain;
/*     */   }
/*     */   
/*     */   public void setDomain(String paramString)
/*     */   {
/* 105 */     this.domain = validateValue("domain", paramString);
/*     */   }
/*     */   
/*     */   public String getPath()
/*     */   {
/* 110 */     return this.path;
/*     */   }
/*     */   
/*     */   public void setPath(String paramString)
/*     */   {
/* 115 */     this.path = validateValue("path", paramString);
/*     */   }
/*     */   
/*     */   public String getComment()
/*     */   {
/* 120 */     return this.comment;
/*     */   }
/*     */   
/*     */   public void setComment(String paramString)
/*     */   {
/* 125 */     this.comment = validateValue("comment", paramString);
/*     */   }
/*     */   
/*     */   public String getCommentUrl()
/*     */   {
/* 130 */     return this.commentUrl;
/*     */   }
/*     */   
/*     */   public void setCommentUrl(String paramString)
/*     */   {
/* 135 */     this.commentUrl = validateValue("commentUrl", paramString);
/*     */   }
/*     */   
/*     */   public boolean isDiscard()
/*     */   {
/* 140 */     return this.discard;
/*     */   }
/*     */   
/*     */   public void setDiscard(boolean paramBoolean)
/*     */   {
/* 145 */     this.discard = paramBoolean;
/*     */   }
/*     */   
/*     */   public Set<Integer> getPorts()
/*     */   {
/* 150 */     if (this.unmodifiablePorts == null) {
/* 151 */       this.unmodifiablePorts = Collections.unmodifiableSet(this.ports);
/*     */     }
/* 153 */     return this.unmodifiablePorts;
/*     */   }
/*     */   
/*     */   public void setPorts(int... paramVarArgs)
/*     */   {
/* 158 */     if (paramVarArgs == null) {
/* 159 */       throw new NullPointerException("ports");
/*     */     }
/*     */     
/* 162 */     int[] arrayOfInt1 = (int[])paramVarArgs.clone();
/* 163 */     if (arrayOfInt1.length == 0) {
/* 164 */       this.unmodifiablePorts = (this.ports = Collections.emptySet());
/*     */     } else {
/* 166 */       TreeSet localTreeSet = new TreeSet();
/* 167 */       for (int k : arrayOfInt1) {
/* 168 */         if ((k <= 0) || (k > 65535)) {
/* 169 */           throw new IllegalArgumentException("port out of range: " + k);
/*     */         }
/* 171 */         localTreeSet.add(Integer.valueOf(k));
/*     */       }
/* 173 */       this.ports = localTreeSet;
/* 174 */       this.unmodifiablePorts = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPorts(Iterable<Integer> paramIterable)
/*     */   {
/* 180 */     TreeSet localTreeSet = new TreeSet();
/* 181 */     for (Iterator localIterator = paramIterable.iterator(); localIterator.hasNext();) { int i = ((Integer)localIterator.next()).intValue();
/* 182 */       if ((i <= 0) || (i > 65535)) {
/* 183 */         throw new IllegalArgumentException("port out of range: " + i);
/*     */       }
/* 185 */       localTreeSet.add(Integer.valueOf(i));
/*     */     }
/* 187 */     if (localTreeSet.isEmpty()) {
/* 188 */       this.unmodifiablePorts = (this.ports = Collections.emptySet());
/*     */     } else {
/* 190 */       this.ports = localTreeSet;
/* 191 */       this.unmodifiablePorts = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public long getMaxAge()
/*     */   {
/* 197 */     return this.maxAge;
/*     */   }
/*     */   
/*     */   public void setMaxAge(long paramLong)
/*     */   {
/* 202 */     this.maxAge = paramLong;
/*     */   }
/*     */   
/*     */   public int getVersion()
/*     */   {
/* 207 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setVersion(int paramInt)
/*     */   {
/* 212 */     this.version = paramInt;
/*     */   }
/*     */   
/*     */   public boolean isSecure()
/*     */   {
/* 217 */     return this.secure;
/*     */   }
/*     */   
/*     */   public void setSecure(boolean paramBoolean)
/*     */   {
/* 222 */     this.secure = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean isHttpOnly()
/*     */   {
/* 227 */     return this.httpOnly;
/*     */   }
/*     */   
/*     */   public void setHttpOnly(boolean paramBoolean)
/*     */   {
/* 232 */     this.httpOnly = paramBoolean;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 237 */     return getName().hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 242 */     if (!(paramObject instanceof Cookie)) {
/* 243 */       return false;
/*     */     }
/*     */     
/* 246 */     Cookie localCookie = (Cookie)paramObject;
/* 247 */     if (!getName().equalsIgnoreCase(localCookie.getName())) {
/* 248 */       return false;
/*     */     }
/*     */     
/* 251 */     if (getPath() == null) {
/* 252 */       if (localCookie.getPath() != null)
/* 253 */         return false;
/*     */     } else {
/* 255 */       if (localCookie.getPath() == null)
/* 256 */         return false;
/* 257 */       if (!getPath().equals(localCookie.getPath())) {
/* 258 */         return false;
/*     */       }
/*     */     }
/* 261 */     if (getDomain() == null) {
/* 262 */       if (localCookie.getDomain() != null)
/* 263 */         return false;
/*     */     } else {
/* 265 */       if (localCookie.getDomain() == null) {
/* 266 */         return false;
/*     */       }
/* 268 */       return getDomain().equalsIgnoreCase(localCookie.getDomain());
/*     */     }
/*     */     
/* 271 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public int compareTo(Cookie paramCookie)
/*     */   {
/* 277 */     int i = getName().compareToIgnoreCase(paramCookie.getName());
/* 278 */     if (i != 0) {
/* 279 */       return i;
/*     */     }
/*     */     
/* 282 */     if (getPath() == null) {
/* 283 */       if (paramCookie.getPath() != null)
/* 284 */         return -1;
/*     */     } else {
/* 286 */       if (paramCookie.getPath() == null) {
/* 287 */         return 1;
/*     */       }
/* 289 */       i = getPath().compareTo(paramCookie.getPath());
/* 290 */       if (i != 0) {
/* 291 */         return i;
/*     */       }
/*     */     }
/*     */     
/* 295 */     if (getDomain() == null) {
/* 296 */       if (paramCookie.getDomain() != null)
/* 297 */         return -1;
/*     */     } else {
/* 299 */       if (paramCookie.getDomain() == null) {
/* 300 */         return 1;
/*     */       }
/* 302 */       i = getDomain().compareToIgnoreCase(paramCookie.getDomain());
/* 303 */       return i;
/*     */     }
/*     */     
/* 306 */     return 0;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 311 */     StringBuilder localStringBuilder = new StringBuilder();
/* 312 */     localStringBuilder.append(getName());
/* 313 */     localStringBuilder.append('=');
/* 314 */     localStringBuilder.append(getValue());
/* 315 */     if (getDomain() != null) {
/* 316 */       localStringBuilder.append(", domain=");
/* 317 */       localStringBuilder.append(getDomain());
/*     */     }
/* 319 */     if (getPath() != null) {
/* 320 */       localStringBuilder.append(", path=");
/* 321 */       localStringBuilder.append(getPath());
/*     */     }
/* 323 */     if (getComment() != null) {
/* 324 */       localStringBuilder.append(", comment=");
/* 325 */       localStringBuilder.append(getComment());
/*     */     }
/* 327 */     if (getMaxAge() >= 0L) {
/* 328 */       localStringBuilder.append(", maxAge=");
/* 329 */       localStringBuilder.append(getMaxAge());
/* 330 */       localStringBuilder.append('s');
/*     */     }
/* 332 */     if (isSecure()) {
/* 333 */       localStringBuilder.append(", secure");
/*     */     }
/* 335 */     if (isHttpOnly()) {
/* 336 */       localStringBuilder.append(", HTTPOnly");
/*     */     }
/* 338 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static String validateValue(String paramString1, String paramString2) {
/* 342 */     if (paramString2 == null) {
/* 343 */       return null;
/*     */     }
/* 345 */     paramString2 = paramString2.trim();
/* 346 */     if (paramString2.isEmpty()) {
/* 347 */       return null;
/*     */     }
/* 349 */     for (int i = 0; i < paramString2.length(); i++) {
/* 350 */       int j = paramString2.charAt(i);
/* 351 */       switch (j) {
/*     */       case 10: case 11: case 12: case 13: case 59: 
/* 353 */         throw new IllegalArgumentException(paramString1 + " contains one of the following prohibited characters: " + ";\\r\\n\\f\\v (" + paramString2 + ')');
/*     */       }
/*     */       
/*     */     }
/*     */     
/* 358 */     return paramString2;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\DefaultCookie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */