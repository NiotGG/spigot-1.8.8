/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class DefaultSpdyHeaders
/*     */   extends SpdyHeaders
/*     */ {
/*     */   private static final int BUCKET_SIZE = 17;
/*     */   
/*     */   private static int hash(String paramString)
/*     */   {
/*  33 */     int i = 0;
/*  34 */     for (int j = paramString.length() - 1; j >= 0; j--) {
/*  35 */       int k = paramString.charAt(j);
/*  36 */       if ((k >= 65) && (k <= 90)) {
/*  37 */         k = (char)(k + 32);
/*     */       }
/*  39 */       i = 31 * i + k;
/*     */     }
/*     */     
/*  42 */     if (i > 0)
/*  43 */       return i;
/*  44 */     if (i == Integer.MIN_VALUE) {
/*  45 */       return Integer.MAX_VALUE;
/*     */     }
/*  47 */     return -i;
/*     */   }
/*     */   
/*     */   private static boolean eq(String paramString1, String paramString2)
/*     */   {
/*  52 */     int i = paramString1.length();
/*  53 */     if (i != paramString2.length()) {
/*  54 */       return false;
/*     */     }
/*     */     
/*  57 */     for (int j = i - 1; j >= 0; j--) {
/*  58 */       int k = paramString1.charAt(j);
/*  59 */       int m = paramString2.charAt(j);
/*  60 */       if (k != m) {
/*  61 */         if ((k >= 65) && (k <= 90)) {
/*  62 */           k = (char)(k + 32);
/*     */         }
/*  64 */         if ((m >= 65) && (m <= 90)) {
/*  65 */           m = (char)(m + 32);
/*     */         }
/*  67 */         if (k != m) {
/*  68 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   private static int index(int paramInt) {
/*  76 */     return paramInt % 17;
/*     */   }
/*     */   
/*  79 */   private final HeaderEntry[] entries = new HeaderEntry[17];
/*  80 */   private final HeaderEntry head = new HeaderEntry(-1, null, null);
/*     */   
/*     */   DefaultSpdyHeaders() {
/*  83 */     this.head.before = (this.head.after = this.head);
/*     */   }
/*     */   
/*     */   public SpdyHeaders add(String paramString, Object paramObject)
/*     */   {
/*  88 */     String str1 = paramString.toLowerCase();
/*  89 */     SpdyCodecUtil.validateHeaderName(str1);
/*  90 */     String str2 = toString(paramObject);
/*  91 */     SpdyCodecUtil.validateHeaderValue(str2);
/*  92 */     int i = hash(str1);
/*  93 */     int j = index(i);
/*  94 */     add0(i, j, str1, str2);
/*  95 */     return this;
/*     */   }
/*     */   
/*     */   private void add0(int paramInt1, int paramInt2, String paramString1, String paramString2)
/*     */   {
/* 100 */     HeaderEntry localHeaderEntry1 = this.entries[paramInt2];
/*     */     HeaderEntry localHeaderEntry2;
/* 102 */     this.entries[paramInt2] = (localHeaderEntry2 = new HeaderEntry(paramInt1, paramString1, paramString2));
/* 103 */     localHeaderEntry2.next = localHeaderEntry1;
/*     */     
/*     */ 
/* 106 */     localHeaderEntry2.addBefore(this.head);
/*     */   }
/*     */   
/*     */   public SpdyHeaders remove(String paramString)
/*     */   {
/* 111 */     if (paramString == null) {
/* 112 */       throw new NullPointerException("name");
/*     */     }
/* 114 */     String str = paramString.toLowerCase();
/* 115 */     int i = hash(str);
/* 116 */     int j = index(i);
/* 117 */     remove0(i, j, str);
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   private void remove0(int paramInt1, int paramInt2, String paramString) {
/* 122 */     Object localObject = this.entries[paramInt2];
/* 123 */     if (localObject == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     HeaderEntry localHeaderEntry;
/* 128 */     while ((((HeaderEntry)localObject).hash == paramInt1) && (eq(paramString, ((HeaderEntry)localObject).key))) {
/* 129 */       ((HeaderEntry)localObject).remove();
/* 130 */       localHeaderEntry = ((HeaderEntry)localObject).next;
/* 131 */       if (localHeaderEntry != null) {
/* 132 */         this.entries[paramInt2] = localHeaderEntry;
/* 133 */         localObject = localHeaderEntry;
/*     */       } else {
/* 135 */         this.entries[paramInt2] = null; return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     for (;;)
/*     */     {
/* 144 */       localHeaderEntry = ((HeaderEntry)localObject).next;
/* 145 */       if (localHeaderEntry == null) {
/*     */         break;
/*     */       }
/* 148 */       if ((localHeaderEntry.hash == paramInt1) && (eq(paramString, localHeaderEntry.key))) {
/* 149 */         ((HeaderEntry)localObject).next = localHeaderEntry.next;
/* 150 */         localHeaderEntry.remove();
/*     */       } else {
/* 152 */         localObject = localHeaderEntry;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public SpdyHeaders set(String paramString, Object paramObject)
/*     */   {
/* 159 */     String str1 = paramString.toLowerCase();
/* 160 */     SpdyCodecUtil.validateHeaderName(str1);
/* 161 */     String str2 = toString(paramObject);
/* 162 */     SpdyCodecUtil.validateHeaderValue(str2);
/* 163 */     int i = hash(str1);
/* 164 */     int j = index(i);
/* 165 */     remove0(i, j, str1);
/* 166 */     add0(i, j, str1, str2);
/* 167 */     return this;
/*     */   }
/*     */   
/*     */   public SpdyHeaders set(String paramString, Iterable<?> paramIterable)
/*     */   {
/* 172 */     if (paramIterable == null) {
/* 173 */       throw new NullPointerException("values");
/*     */     }
/*     */     
/* 176 */     String str1 = paramString.toLowerCase();
/* 177 */     SpdyCodecUtil.validateHeaderName(str1);
/*     */     
/* 179 */     int i = hash(str1);
/* 180 */     int j = index(i);
/*     */     
/* 182 */     remove0(i, j, str1);
/* 183 */     for (Object localObject : paramIterable) {
/* 184 */       if (localObject == null) {
/*     */         break;
/*     */       }
/* 187 */       String str2 = toString(localObject);
/* 188 */       SpdyCodecUtil.validateHeaderValue(str2);
/* 189 */       add0(i, j, str1, str2);
/*     */     }
/* 191 */     return this;
/*     */   }
/*     */   
/*     */   public SpdyHeaders clear()
/*     */   {
/* 196 */     for (int i = 0; i < this.entries.length; i++) {
/* 197 */       this.entries[i] = null;
/*     */     }
/* 199 */     this.head.before = (this.head.after = this.head);
/* 200 */     return this;
/*     */   }
/*     */   
/*     */   public String get(String paramString)
/*     */   {
/* 205 */     if (paramString == null) {
/* 206 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 209 */     int i = hash(paramString);
/* 210 */     int j = index(i);
/* 211 */     HeaderEntry localHeaderEntry = this.entries[j];
/* 212 */     while (localHeaderEntry != null) {
/* 213 */       if ((localHeaderEntry.hash == i) && (eq(paramString, localHeaderEntry.key))) {
/* 214 */         return localHeaderEntry.value;
/*     */       }
/*     */       
/* 217 */       localHeaderEntry = localHeaderEntry.next;
/*     */     }
/* 219 */     return null;
/*     */   }
/*     */   
/*     */   public List<String> getAll(String paramString)
/*     */   {
/* 224 */     if (paramString == null) {
/* 225 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 228 */     LinkedList localLinkedList = new LinkedList();
/*     */     
/* 230 */     int i = hash(paramString);
/* 231 */     int j = index(i);
/* 232 */     HeaderEntry localHeaderEntry = this.entries[j];
/* 233 */     while (localHeaderEntry != null) {
/* 234 */       if ((localHeaderEntry.hash == i) && (eq(paramString, localHeaderEntry.key))) {
/* 235 */         localLinkedList.addFirst(localHeaderEntry.value);
/*     */       }
/* 237 */       localHeaderEntry = localHeaderEntry.next;
/*     */     }
/* 239 */     return localLinkedList;
/*     */   }
/*     */   
/*     */   public List<Map.Entry<String, String>> entries()
/*     */   {
/* 244 */     LinkedList localLinkedList = new LinkedList();
/*     */     
/*     */ 
/* 247 */     HeaderEntry localHeaderEntry = this.head.after;
/* 248 */     while (localHeaderEntry != this.head) {
/* 249 */       localLinkedList.add(localHeaderEntry);
/* 250 */       localHeaderEntry = localHeaderEntry.after;
/*     */     }
/* 252 */     return localLinkedList;
/*     */   }
/*     */   
/*     */   public Iterator<Map.Entry<String, String>> iterator()
/*     */   {
/* 257 */     return new HeaderIterator(null);
/*     */   }
/*     */   
/*     */   public boolean contains(String paramString)
/*     */   {
/* 262 */     return get(paramString) != null;
/*     */   }
/*     */   
/*     */   public Set<String> names()
/*     */   {
/* 267 */     TreeSet localTreeSet = new TreeSet();
/*     */     
/* 269 */     HeaderEntry localHeaderEntry = this.head.after;
/* 270 */     while (localHeaderEntry != this.head) {
/* 271 */       localTreeSet.add(localHeaderEntry.key);
/* 272 */       localHeaderEntry = localHeaderEntry.after;
/*     */     }
/* 274 */     return localTreeSet;
/*     */   }
/*     */   
/*     */   public SpdyHeaders add(String paramString, Iterable<?> paramIterable)
/*     */   {
/* 279 */     SpdyCodecUtil.validateHeaderValue(paramString);
/* 280 */     int i = hash(paramString);
/* 281 */     int j = index(i);
/* 282 */     for (Object localObject : paramIterable) {
/* 283 */       String str = toString(localObject);
/* 284 */       SpdyCodecUtil.validateHeaderValue(str);
/* 285 */       add0(i, j, paramString, str);
/*     */     }
/* 287 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 292 */     return this.head == this.head.after;
/*     */   }
/*     */   
/*     */   private static String toString(Object paramObject) {
/* 296 */     if (paramObject == null) {
/* 297 */       return null;
/*     */     }
/* 299 */     return paramObject.toString();
/*     */   }
/*     */   
/*     */   private final class HeaderIterator implements Iterator<Map.Entry<String, String>>
/*     */   {
/* 304 */     private DefaultSpdyHeaders.HeaderEntry current = DefaultSpdyHeaders.this.head;
/*     */     
/*     */     private HeaderIterator() {}
/*     */     
/* 308 */     public boolean hasNext() { return this.current.after != DefaultSpdyHeaders.this.head; }
/*     */     
/*     */ 
/*     */     public Map.Entry<String, String> next()
/*     */     {
/* 313 */       this.current = this.current.after;
/*     */       
/* 315 */       if (this.current == DefaultSpdyHeaders.this.head) {
/* 316 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 319 */       return this.current;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 324 */     public void remove() { throw new UnsupportedOperationException(); }
/*     */   }
/*     */   
/*     */   private static final class HeaderEntry implements Map.Entry<String, String> {
/*     */     final int hash;
/*     */     final String key;
/*     */     String value;
/*     */     HeaderEntry next;
/*     */     HeaderEntry before;
/*     */     HeaderEntry after;
/*     */     
/*     */     HeaderEntry(int paramInt, String paramString1, String paramString2) {
/* 336 */       this.hash = paramInt;
/* 337 */       this.key = paramString1;
/* 338 */       this.value = paramString2;
/*     */     }
/*     */     
/*     */     void remove() {
/* 342 */       this.before.after = this.after;
/* 343 */       this.after.before = this.before;
/*     */     }
/*     */     
/*     */     void addBefore(HeaderEntry paramHeaderEntry) {
/* 347 */       this.after = paramHeaderEntry;
/* 348 */       this.before = paramHeaderEntry.before;
/* 349 */       this.before.after = this;
/* 350 */       this.after.before = this;
/*     */     }
/*     */     
/*     */     public String getKey()
/*     */     {
/* 355 */       return this.key;
/*     */     }
/*     */     
/*     */     public String getValue()
/*     */     {
/* 360 */       return this.value;
/*     */     }
/*     */     
/*     */     public String setValue(String paramString)
/*     */     {
/* 365 */       if (paramString == null) {
/* 366 */         throw new NullPointerException("value");
/*     */       }
/* 368 */       SpdyCodecUtil.validateHeaderValue(paramString);
/* 369 */       String str = this.value;
/* 370 */       this.value = paramString;
/* 371 */       return str;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 376 */       return this.key + '=' + this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\DefaultSpdyHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */