/*     */ package org.apache.commons.lang3.exception;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.tuple.ImmutablePair;
/*     */ import org.apache.commons.lang3.tuple.Pair;
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
/*     */ public class DefaultExceptionContext
/*     */   implements ExceptionContext, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20110706L;
/*  47 */   private final List<Pair<String, Object>> contextValues = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultExceptionContext addContextValue(String paramString, Object paramObject)
/*     */   {
/*  54 */     this.contextValues.add(new ImmutablePair(paramString, paramObject));
/*  55 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultExceptionContext setContextValue(String paramString, Object paramObject)
/*     */   {
/*  63 */     for (Iterator localIterator = this.contextValues.iterator(); localIterator.hasNext();) {
/*  64 */       Pair localPair = (Pair)localIterator.next();
/*  65 */       if (StringUtils.equals(paramString, (CharSequence)localPair.getKey())) {
/*  66 */         localIterator.remove();
/*     */       }
/*     */     }
/*  69 */     addContextValue(paramString, paramObject);
/*  70 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Object> getContextValues(String paramString)
/*     */   {
/*  78 */     ArrayList localArrayList = new ArrayList();
/*  79 */     for (Pair localPair : this.contextValues) {
/*  80 */       if (StringUtils.equals(paramString, (CharSequence)localPair.getKey())) {
/*  81 */         localArrayList.add(localPair.getValue());
/*     */       }
/*     */     }
/*  84 */     return localArrayList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getFirstContextValue(String paramString)
/*     */   {
/*  92 */     for (Pair localPair : this.contextValues) {
/*  93 */       if (StringUtils.equals(paramString, (CharSequence)localPair.getKey())) {
/*  94 */         return localPair.getValue();
/*     */       }
/*     */     }
/*  97 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<String> getContextLabels()
/*     */   {
/* 105 */     HashSet localHashSet = new HashSet();
/* 106 */     for (Pair localPair : this.contextValues) {
/* 107 */       localHashSet.add(localPair.getKey());
/*     */     }
/* 109 */     return localHashSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Pair<String, Object>> getContextEntries()
/*     */   {
/* 117 */     return this.contextValues;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedExceptionMessage(String paramString)
/*     */   {
/* 128 */     StringBuilder localStringBuilder = new StringBuilder(256);
/* 129 */     if (paramString != null) {
/* 130 */       localStringBuilder.append(paramString);
/*     */     }
/*     */     
/* 133 */     if (this.contextValues.size() > 0) {
/* 134 */       if (localStringBuilder.length() > 0) {
/* 135 */         localStringBuilder.append('\n');
/*     */       }
/* 137 */       localStringBuilder.append("Exception Context:\n");
/*     */       
/* 139 */       int i = 0;
/* 140 */       for (Pair localPair : this.contextValues) {
/* 141 */         localStringBuilder.append("\t[");
/* 142 */         localStringBuilder.append(++i);
/* 143 */         localStringBuilder.append(':');
/* 144 */         localStringBuilder.append((String)localPair.getKey());
/* 145 */         localStringBuilder.append("=");
/* 146 */         Object localObject = localPair.getValue();
/* 147 */         if (localObject == null) {
/* 148 */           localStringBuilder.append("null");
/*     */         } else {
/*     */           String str;
/*     */           try {
/* 152 */             str = localObject.toString();
/*     */           } catch (Exception localException) {
/* 154 */             str = "Exception thrown on toString(): " + ExceptionUtils.getStackTrace(localException);
/*     */           }
/* 156 */           localStringBuilder.append(str);
/*     */         }
/* 158 */         localStringBuilder.append("]\n");
/*     */       }
/* 160 */       localStringBuilder.append("---------------------------------");
/*     */     }
/* 162 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\exception\DefaultExceptionContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */