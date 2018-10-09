/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
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
/*     */ public class DefaultHttpDataFactory
/*     */   implements HttpDataFactory
/*     */ {
/*     */   public static final long MINSIZE = 16384L;
/*     */   private final boolean useDisk;
/*     */   private final boolean checkSize;
/*     */   private long minSize;
/*  52 */   private final Map<HttpRequest, List<HttpData>> requestFileDeleteMap = PlatformDependent.newConcurrentHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultHttpDataFactory()
/*     */   {
/*  59 */     this.useDisk = false;
/*  60 */     this.checkSize = true;
/*  61 */     this.minSize = 16384L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DefaultHttpDataFactory(boolean paramBoolean)
/*     */   {
/*  68 */     this.useDisk = paramBoolean;
/*  69 */     this.checkSize = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultHttpDataFactory(long paramLong)
/*     */   {
/*  77 */     this.useDisk = false;
/*  78 */     this.checkSize = true;
/*  79 */     this.minSize = paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private List<HttpData> getList(HttpRequest paramHttpRequest)
/*     */   {
/*  86 */     Object localObject = (List)this.requestFileDeleteMap.get(paramHttpRequest);
/*  87 */     if (localObject == null) {
/*  88 */       localObject = new ArrayList();
/*  89 */       this.requestFileDeleteMap.put(paramHttpRequest, localObject);
/*     */     }
/*  91 */     return (List<HttpData>)localObject;
/*     */   }
/*     */   
/*     */   public Attribute createAttribute(HttpRequest paramHttpRequest, String paramString) { Object localObject;
/*     */     List localList;
/*  96 */     if (this.useDisk) {
/*  97 */       localObject = new DiskAttribute(paramString);
/*  98 */       localList = getList(paramHttpRequest);
/*  99 */       localList.add(localObject);
/* 100 */       return (Attribute)localObject;
/*     */     }
/* 102 */     if (this.checkSize) {
/* 103 */       localObject = new MixedAttribute(paramString, this.minSize);
/* 104 */       localList = getList(paramHttpRequest);
/* 105 */       localList.add(localObject);
/* 106 */       return (Attribute)localObject;
/*     */     }
/* 108 */     return new MemoryAttribute(paramString);
/*     */   }
/*     */   
/*     */   public Attribute createAttribute(HttpRequest paramHttpRequest, String paramString1, String paramString2) { Object localObject;
/*     */     List localList;
/* 113 */     if (this.useDisk)
/*     */     {
/*     */       try {
/* 116 */         localObject = new DiskAttribute(paramString1, paramString2);
/*     */       }
/*     */       catch (IOException localIOException2) {
/* 119 */         localObject = new MixedAttribute(paramString1, paramString2, this.minSize);
/*     */       }
/* 121 */       localList = getList(paramHttpRequest);
/* 122 */       localList.add(localObject);
/* 123 */       return (Attribute)localObject;
/*     */     }
/* 125 */     if (this.checkSize) {
/* 126 */       localObject = new MixedAttribute(paramString1, paramString2, this.minSize);
/* 127 */       localList = getList(paramHttpRequest);
/* 128 */       localList.add(localObject);
/* 129 */       return (Attribute)localObject;
/*     */     }
/*     */     try {
/* 132 */       return new MemoryAttribute(paramString1, paramString2);
/*     */     } catch (IOException localIOException1) {
/* 134 */       throw new IllegalArgumentException(localIOException1);
/*     */     }
/*     */   }
/*     */   
/*     */   public FileUpload createFileUpload(HttpRequest paramHttpRequest, String paramString1, String paramString2, String paramString3, String paramString4, Charset paramCharset, long paramLong)
/*     */   {
/*     */     Object localObject;
/*     */     List localList;
/* 142 */     if (this.useDisk) {
/* 143 */       localObject = new DiskFileUpload(paramString1, paramString2, paramString3, paramString4, paramCharset, paramLong);
/*     */       
/* 145 */       localList = getList(paramHttpRequest);
/* 146 */       localList.add(localObject);
/* 147 */       return (FileUpload)localObject;
/*     */     }
/* 149 */     if (this.checkSize) {
/* 150 */       localObject = new MixedFileUpload(paramString1, paramString2, paramString3, paramString4, paramCharset, paramLong, this.minSize);
/*     */       
/* 152 */       localList = getList(paramHttpRequest);
/* 153 */       localList.add(localObject);
/* 154 */       return (FileUpload)localObject;
/*     */     }
/* 156 */     return new MemoryFileUpload(paramString1, paramString2, paramString3, paramString4, paramCharset, paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */   public void removeHttpDataFromClean(HttpRequest paramHttpRequest, InterfaceHttpData paramInterfaceHttpData)
/*     */   {
/* 162 */     if ((paramInterfaceHttpData instanceof HttpData)) {
/* 163 */       List localList = getList(paramHttpRequest);
/* 164 */       localList.remove(paramInterfaceHttpData);
/*     */     }
/*     */   }
/*     */   
/*     */   public void cleanRequestHttpDatas(HttpRequest paramHttpRequest)
/*     */   {
/* 170 */     List localList = (List)this.requestFileDeleteMap.remove(paramHttpRequest);
/* 171 */     if (localList != null) {
/* 172 */       for (HttpData localHttpData : localList) {
/* 173 */         localHttpData.delete();
/*     */       }
/* 175 */       localList.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public void cleanAllHttpDatas()
/*     */   {
/* 181 */     Iterator localIterator1 = this.requestFileDeleteMap.entrySet().iterator();
/* 182 */     while (localIterator1.hasNext()) {
/* 183 */       Map.Entry localEntry = (Map.Entry)localIterator1.next();
/* 184 */       localIterator1.remove();
/*     */       
/* 186 */       List localList = (List)localEntry.getValue();
/* 187 */       if (localList != null) {
/* 188 */         for (HttpData localHttpData : localList) {
/* 189 */           localHttpData.delete();
/*     */         }
/* 191 */         localList.clear();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\DefaultHttpDataFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */