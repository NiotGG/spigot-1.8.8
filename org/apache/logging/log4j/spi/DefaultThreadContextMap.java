/*     */ package org.apache.logging.log4j.spi;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class DefaultThreadContextMap
/*     */   implements ThreadContextMap
/*     */ {
/*     */   private final boolean useMap;
/*  33 */   private final ThreadLocal<Map<String, String>> localMap = new InheritableThreadLocal()
/*     */   {
/*     */     protected Map<String, String> childValue(Map<String, String> paramAnonymousMap)
/*     */     {
/*  37 */       return (paramAnonymousMap == null) || (!DefaultThreadContextMap.this.useMap) ? null : Collections.unmodifiableMap(new HashMap(paramAnonymousMap));
/*     */     }
/*     */   };
/*     */   
/*     */   public DefaultThreadContextMap(boolean paramBoolean)
/*     */   {
/*  43 */     this.useMap = paramBoolean;
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
/*     */   public void put(String paramString1, String paramString2)
/*     */   {
/*  58 */     if (!this.useMap) {
/*  59 */       return;
/*     */     }
/*  61 */     Object localObject = (Map)this.localMap.get();
/*  62 */     localObject = localObject == null ? new HashMap() : new HashMap((Map)localObject);
/*  63 */     ((Map)localObject).put(paramString1, paramString2);
/*  64 */     this.localMap.set(Collections.unmodifiableMap((Map)localObject));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String get(String paramString)
/*     */   {
/*  76 */     Map localMap1 = (Map)this.localMap.get();
/*  77 */     return localMap1 == null ? null : (String)localMap1.get(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void remove(String paramString)
/*     */   {
/*  87 */     Map localMap1 = (Map)this.localMap.get();
/*  88 */     if (localMap1 != null) {
/*  89 */       HashMap localHashMap = new HashMap(localMap1);
/*  90 */       localHashMap.remove(paramString);
/*  91 */       this.localMap.set(Collections.unmodifiableMap(localHashMap));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 100 */     this.localMap.remove();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean containsKey(String paramString)
/*     */   {
/* 110 */     Map localMap1 = (Map)this.localMap.get();
/* 111 */     return (localMap1 != null) && (localMap1.containsKey(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, String> getCopy()
/*     */   {
/* 120 */     Map localMap1 = (Map)this.localMap.get();
/* 121 */     return localMap1 == null ? new HashMap() : new HashMap(localMap1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, String> getImmutableMapOrNull()
/*     */   {
/* 130 */     return (Map)this.localMap.get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 139 */     Map localMap1 = (Map)this.localMap.get();
/* 140 */     return (localMap1 == null) || (localMap1.size() == 0);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 145 */     Map localMap1 = (Map)this.localMap.get();
/* 146 */     return localMap1 == null ? "{}" : localMap1.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\DefaultThreadContextMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */