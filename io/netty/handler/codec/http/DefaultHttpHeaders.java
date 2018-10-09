/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
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
/*     */ public class DefaultHttpHeaders
/*     */   extends HttpHeaders
/*     */ {
/*     */   private static final int BUCKET_SIZE = 17;
/*     */   
/*     */   private static int index(int paramInt)
/*     */   {
/*  37 */     return paramInt % 17;
/*     */   }
/*     */   
/*  40 */   private final HeaderEntry[] entries = new HeaderEntry[17];
/*  41 */   private final HeaderEntry head = new HeaderEntry();
/*     */   protected final boolean validate;
/*     */   
/*     */   public DefaultHttpHeaders() {
/*  45 */     this(true);
/*     */   }
/*     */   
/*     */   public DefaultHttpHeaders(boolean paramBoolean) {
/*  49 */     this.validate = paramBoolean;
/*  50 */     this.head.before = (this.head.after = this.head);
/*     */   }
/*     */   
/*     */   void validateHeaderName0(CharSequence paramCharSequence) {
/*  54 */     validateHeaderName(paramCharSequence);
/*     */   }
/*     */   
/*     */   public HttpHeaders add(HttpHeaders paramHttpHeaders)
/*     */   {
/*  59 */     if ((paramHttpHeaders instanceof DefaultHttpHeaders)) {
/*  60 */       DefaultHttpHeaders localDefaultHttpHeaders = (DefaultHttpHeaders)paramHttpHeaders;
/*  61 */       HeaderEntry localHeaderEntry = localDefaultHttpHeaders.head.after;
/*  62 */       while (localHeaderEntry != localDefaultHttpHeaders.head) {
/*  63 */         add(localHeaderEntry.key, localHeaderEntry.value);
/*  64 */         localHeaderEntry = localHeaderEntry.after;
/*     */       }
/*  66 */       return this;
/*     */     }
/*  68 */     return super.add(paramHttpHeaders);
/*     */   }
/*     */   
/*     */ 
/*     */   public HttpHeaders set(HttpHeaders paramHttpHeaders)
/*     */   {
/*  74 */     if ((paramHttpHeaders instanceof DefaultHttpHeaders)) {
/*  75 */       clear();
/*  76 */       DefaultHttpHeaders localDefaultHttpHeaders = (DefaultHttpHeaders)paramHttpHeaders;
/*  77 */       HeaderEntry localHeaderEntry = localDefaultHttpHeaders.head.after;
/*  78 */       while (localHeaderEntry != localDefaultHttpHeaders.head) {
/*  79 */         add(localHeaderEntry.key, localHeaderEntry.value);
/*  80 */         localHeaderEntry = localHeaderEntry.after;
/*     */       }
/*  82 */       return this;
/*     */     }
/*  84 */     return super.set(paramHttpHeaders);
/*     */   }
/*     */   
/*     */ 
/*     */   public HttpHeaders add(String paramString, Object paramObject)
/*     */   {
/*  90 */     return add(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public HttpHeaders add(CharSequence paramCharSequence, Object paramObject)
/*     */   {
/*     */     CharSequence localCharSequence;
/*  96 */     if (this.validate) {
/*  97 */       validateHeaderName0(paramCharSequence);
/*  98 */       localCharSequence = toCharSequence(paramObject);
/*  99 */       validateHeaderValue(localCharSequence);
/*     */     } else {
/* 101 */       localCharSequence = toCharSequence(paramObject);
/*     */     }
/* 103 */     int i = hash(paramCharSequence);
/* 104 */     int j = index(i);
/* 105 */     add0(i, j, paramCharSequence, localCharSequence);
/* 106 */     return this;
/*     */   }
/*     */   
/*     */   public HttpHeaders add(String paramString, Iterable<?> paramIterable)
/*     */   {
/* 111 */     return add(paramString, paramIterable);
/*     */   }
/*     */   
/*     */   public HttpHeaders add(CharSequence paramCharSequence, Iterable<?> paramIterable)
/*     */   {
/* 116 */     if (this.validate) {
/* 117 */       validateHeaderName0(paramCharSequence);
/*     */     }
/* 119 */     int i = hash(paramCharSequence);
/* 120 */     int j = index(i);
/* 121 */     for (Object localObject : paramIterable) {
/* 122 */       CharSequence localCharSequence = toCharSequence(localObject);
/* 123 */       if (this.validate) {
/* 124 */         validateHeaderValue(localCharSequence);
/*     */       }
/* 126 */       add0(i, j, paramCharSequence, localCharSequence);
/*     */     }
/* 128 */     return this;
/*     */   }
/*     */   
/*     */   private void add0(int paramInt1, int paramInt2, CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*     */   {
/* 133 */     HeaderEntry localHeaderEntry1 = this.entries[paramInt2];
/*     */     HeaderEntry localHeaderEntry2;
/* 135 */     this.entries[paramInt2] = (localHeaderEntry2 = new HeaderEntry(paramInt1, paramCharSequence1, paramCharSequence2));
/* 136 */     localHeaderEntry2.next = localHeaderEntry1;
/*     */     
/*     */ 
/* 139 */     localHeaderEntry2.addBefore(this.head);
/*     */   }
/*     */   
/*     */   public HttpHeaders remove(String paramString)
/*     */   {
/* 144 */     return remove(paramString);
/*     */   }
/*     */   
/*     */   public HttpHeaders remove(CharSequence paramCharSequence)
/*     */   {
/* 149 */     if (paramCharSequence == null) {
/* 150 */       throw new NullPointerException("name");
/*     */     }
/* 152 */     int i = hash(paramCharSequence);
/* 153 */     int j = index(i);
/* 154 */     remove0(i, j, paramCharSequence);
/* 155 */     return this;
/*     */   }
/*     */   
/*     */   private void remove0(int paramInt1, int paramInt2, CharSequence paramCharSequence) {
/* 159 */     Object localObject = this.entries[paramInt2];
/* 160 */     if (localObject == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     HeaderEntry localHeaderEntry;
/* 165 */     while ((((HeaderEntry)localObject).hash == paramInt1) && (equalsIgnoreCase(paramCharSequence, ((HeaderEntry)localObject).key))) {
/* 166 */       ((HeaderEntry)localObject).remove();
/* 167 */       localHeaderEntry = ((HeaderEntry)localObject).next;
/* 168 */       if (localHeaderEntry != null) {
/* 169 */         this.entries[paramInt2] = localHeaderEntry;
/* 170 */         localObject = localHeaderEntry;
/*     */       } else {
/* 172 */         this.entries[paramInt2] = null; return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     for (;;)
/*     */     {
/* 181 */       localHeaderEntry = ((HeaderEntry)localObject).next;
/* 182 */       if (localHeaderEntry == null) {
/*     */         break;
/*     */       }
/* 185 */       if ((localHeaderEntry.hash == paramInt1) && (equalsIgnoreCase(paramCharSequence, localHeaderEntry.key))) {
/* 186 */         ((HeaderEntry)localObject).next = localHeaderEntry.next;
/* 187 */         localHeaderEntry.remove();
/*     */       } else {
/* 189 */         localObject = localHeaderEntry;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public HttpHeaders set(String paramString, Object paramObject)
/*     */   {
/* 196 */     return set(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public HttpHeaders set(CharSequence paramCharSequence, Object paramObject)
/*     */   {
/*     */     CharSequence localCharSequence;
/* 202 */     if (this.validate) {
/* 203 */       validateHeaderName0(paramCharSequence);
/* 204 */       localCharSequence = toCharSequence(paramObject);
/* 205 */       validateHeaderValue(localCharSequence);
/*     */     } else {
/* 207 */       localCharSequence = toCharSequence(paramObject);
/*     */     }
/* 209 */     int i = hash(paramCharSequence);
/* 210 */     int j = index(i);
/* 211 */     remove0(i, j, paramCharSequence);
/* 212 */     add0(i, j, paramCharSequence, localCharSequence);
/* 213 */     return this;
/*     */   }
/*     */   
/*     */   public HttpHeaders set(String paramString, Iterable<?> paramIterable)
/*     */   {
/* 218 */     return set(paramString, paramIterable);
/*     */   }
/*     */   
/*     */   public HttpHeaders set(CharSequence paramCharSequence, Iterable<?> paramIterable)
/*     */   {
/* 223 */     if (paramIterable == null) {
/* 224 */       throw new NullPointerException("values");
/*     */     }
/* 226 */     if (this.validate) {
/* 227 */       validateHeaderName0(paramCharSequence);
/*     */     }
/*     */     
/* 230 */     int i = hash(paramCharSequence);
/* 231 */     int j = index(i);
/*     */     
/* 233 */     remove0(i, j, paramCharSequence);
/* 234 */     for (Object localObject : paramIterable) {
/* 235 */       if (localObject == null) {
/*     */         break;
/*     */       }
/* 238 */       CharSequence localCharSequence = toCharSequence(localObject);
/* 239 */       if (this.validate) {
/* 240 */         validateHeaderValue(localCharSequence);
/*     */       }
/* 242 */       add0(i, j, paramCharSequence, localCharSequence);
/*     */     }
/*     */     
/* 245 */     return this;
/*     */   }
/*     */   
/*     */   public HttpHeaders clear()
/*     */   {
/* 250 */     Arrays.fill(this.entries, null);
/* 251 */     this.head.before = (this.head.after = this.head);
/* 252 */     return this;
/*     */   }
/*     */   
/*     */   public String get(String paramString)
/*     */   {
/* 257 */     return get(paramString);
/*     */   }
/*     */   
/*     */   public String get(CharSequence paramCharSequence)
/*     */   {
/* 262 */     if (paramCharSequence == null) {
/* 263 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 266 */     int i = hash(paramCharSequence);
/* 267 */     int j = index(i);
/* 268 */     HeaderEntry localHeaderEntry = this.entries[j];
/* 269 */     CharSequence localCharSequence = null;
/*     */     
/* 271 */     while (localHeaderEntry != null) {
/* 272 */       if ((localHeaderEntry.hash == i) && (equalsIgnoreCase(paramCharSequence, localHeaderEntry.key))) {
/* 273 */         localCharSequence = localHeaderEntry.value;
/*     */       }
/*     */       
/* 276 */       localHeaderEntry = localHeaderEntry.next;
/*     */     }
/* 278 */     if (localCharSequence == null) {
/* 279 */       return null;
/*     */     }
/* 281 */     return localCharSequence.toString();
/*     */   }
/*     */   
/*     */   public List<String> getAll(String paramString)
/*     */   {
/* 286 */     return getAll(paramString);
/*     */   }
/*     */   
/*     */   public List<String> getAll(CharSequence paramCharSequence)
/*     */   {
/* 291 */     if (paramCharSequence == null) {
/* 292 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 295 */     LinkedList localLinkedList = new LinkedList();
/*     */     
/* 297 */     int i = hash(paramCharSequence);
/* 298 */     int j = index(i);
/* 299 */     HeaderEntry localHeaderEntry = this.entries[j];
/* 300 */     while (localHeaderEntry != null) {
/* 301 */       if ((localHeaderEntry.hash == i) && (equalsIgnoreCase(paramCharSequence, localHeaderEntry.key))) {
/* 302 */         localLinkedList.addFirst(localHeaderEntry.getValue());
/*     */       }
/* 304 */       localHeaderEntry = localHeaderEntry.next;
/*     */     }
/* 306 */     return localLinkedList;
/*     */   }
/*     */   
/*     */   public List<Map.Entry<String, String>> entries()
/*     */   {
/* 311 */     LinkedList localLinkedList = new LinkedList();
/*     */     
/*     */ 
/* 314 */     HeaderEntry localHeaderEntry = this.head.after;
/* 315 */     while (localHeaderEntry != this.head) {
/* 316 */       localLinkedList.add(localHeaderEntry);
/* 317 */       localHeaderEntry = localHeaderEntry.after;
/*     */     }
/* 319 */     return localLinkedList;
/*     */   }
/*     */   
/*     */   public Iterator<Map.Entry<String, String>> iterator()
/*     */   {
/* 324 */     return new HeaderIterator(null);
/*     */   }
/*     */   
/*     */   public boolean contains(String paramString)
/*     */   {
/* 329 */     return get(paramString) != null;
/*     */   }
/*     */   
/*     */   public boolean contains(CharSequence paramCharSequence)
/*     */   {
/* 334 */     return get(paramCharSequence) != null;
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 339 */     return this.head == this.head.after;
/*     */   }
/*     */   
/*     */   public boolean contains(String paramString1, String paramString2, boolean paramBoolean)
/*     */   {
/* 344 */     return contains(paramString1, paramString2, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean contains(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
/*     */   {
/* 349 */     if (paramCharSequence1 == null) {
/* 350 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/* 353 */     int i = hash(paramCharSequence1);
/* 354 */     int j = index(i);
/* 355 */     HeaderEntry localHeaderEntry = this.entries[j];
/* 356 */     while (localHeaderEntry != null) {
/* 357 */       if ((localHeaderEntry.hash == i) && (equalsIgnoreCase(paramCharSequence1, localHeaderEntry.key))) {
/* 358 */         if (paramBoolean) {
/* 359 */           if (equalsIgnoreCase(localHeaderEntry.value, paramCharSequence2)) {
/* 360 */             return true;
/*     */           }
/*     */         }
/* 363 */         else if (localHeaderEntry.value.equals(paramCharSequence2)) {
/* 364 */           return true;
/*     */         }
/*     */       }
/*     */       
/* 368 */       localHeaderEntry = localHeaderEntry.next;
/*     */     }
/* 370 */     return false;
/*     */   }
/*     */   
/*     */   public Set<String> names()
/*     */   {
/* 375 */     LinkedHashSet localLinkedHashSet = new LinkedHashSet();
/* 376 */     HeaderEntry localHeaderEntry = this.head.after;
/* 377 */     while (localHeaderEntry != this.head) {
/* 378 */       localLinkedHashSet.add(localHeaderEntry.getKey());
/* 379 */       localHeaderEntry = localHeaderEntry.after;
/*     */     }
/* 381 */     return localLinkedHashSet;
/*     */   }
/*     */   
/*     */   private static CharSequence toCharSequence(Object paramObject) {
/* 385 */     if (paramObject == null) {
/* 386 */       return null;
/*     */     }
/* 388 */     if ((paramObject instanceof CharSequence)) {
/* 389 */       return (CharSequence)paramObject;
/*     */     }
/* 391 */     if ((paramObject instanceof Number)) {
/* 392 */       return paramObject.toString();
/*     */     }
/* 394 */     if ((paramObject instanceof Date)) {
/* 395 */       return HttpHeaderDateFormat.get().format((Date)paramObject);
/*     */     }
/* 397 */     if ((paramObject instanceof Calendar)) {
/* 398 */       return HttpHeaderDateFormat.get().format(((Calendar)paramObject).getTime());
/*     */     }
/* 400 */     return paramObject.toString();
/*     */   }
/*     */   
/*     */   void encode(ByteBuf paramByteBuf) {
/* 404 */     HeaderEntry localHeaderEntry = this.head.after;
/* 405 */     while (localHeaderEntry != this.head) {
/* 406 */       localHeaderEntry.encode(paramByteBuf);
/* 407 */       localHeaderEntry = localHeaderEntry.after;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class HeaderIterator implements Iterator<Map.Entry<String, String>>
/*     */   {
/* 413 */     private DefaultHttpHeaders.HeaderEntry current = DefaultHttpHeaders.this.head;
/*     */     
/*     */     private HeaderIterator() {}
/*     */     
/* 417 */     public boolean hasNext() { return this.current.after != DefaultHttpHeaders.this.head; }
/*     */     
/*     */ 
/*     */     public Map.Entry<String, String> next()
/*     */     {
/* 422 */       this.current = this.current.after;
/*     */       
/* 424 */       if (this.current == DefaultHttpHeaders.this.head) {
/* 425 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 428 */       return this.current;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 433 */     public void remove() { throw new UnsupportedOperationException(); }
/*     */   }
/*     */   
/*     */   private final class HeaderEntry implements Map.Entry<String, String> {
/*     */     final int hash;
/*     */     final CharSequence key;
/*     */     CharSequence value;
/*     */     HeaderEntry next;
/*     */     HeaderEntry before;
/*     */     HeaderEntry after;
/*     */     
/*     */     HeaderEntry(int paramInt, CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
/* 445 */       this.hash = paramInt;
/* 446 */       this.key = paramCharSequence1;
/* 447 */       this.value = paramCharSequence2;
/*     */     }
/*     */     
/*     */     HeaderEntry() {
/* 451 */       this.hash = -1;
/* 452 */       this.key = null;
/* 453 */       this.value = null;
/*     */     }
/*     */     
/*     */     void remove() {
/* 457 */       this.before.after = this.after;
/* 458 */       this.after.before = this.before;
/*     */     }
/*     */     
/*     */     void addBefore(HeaderEntry paramHeaderEntry) {
/* 462 */       this.after = paramHeaderEntry;
/* 463 */       this.before = paramHeaderEntry.before;
/* 464 */       this.before.after = this;
/* 465 */       this.after.before = this;
/*     */     }
/*     */     
/*     */     public String getKey()
/*     */     {
/* 470 */       return this.key.toString();
/*     */     }
/*     */     
/*     */     public String getValue()
/*     */     {
/* 475 */       return this.value.toString();
/*     */     }
/*     */     
/*     */     public String setValue(String paramString)
/*     */     {
/* 480 */       if (paramString == null) {
/* 481 */         throw new NullPointerException("value");
/*     */       }
/* 483 */       HttpHeaders.validateHeaderValue(paramString);
/* 484 */       CharSequence localCharSequence = this.value;
/* 485 */       this.value = paramString;
/* 486 */       return localCharSequence.toString();
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 491 */       return this.key.toString() + '=' + this.value.toString();
/*     */     }
/*     */     
/*     */     void encode(ByteBuf paramByteBuf) {
/* 495 */       HttpHeaders.encode(this.key, this.value, paramByteBuf);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\DefaultHttpHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */