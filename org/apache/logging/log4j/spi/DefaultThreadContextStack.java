/*     */ package org.apache.logging.log4j.spi;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class DefaultThreadContextStack
/*     */   implements ThreadContextStack
/*     */ {
/*     */   private static final long serialVersionUID = 5050501L;
/*  36 */   private static ThreadLocal<List<String>> stack = new ThreadLocal();
/*     */   private final boolean useStack;
/*     */   
/*     */   public DefaultThreadContextStack(boolean paramBoolean)
/*     */   {
/*  41 */     this.useStack = paramBoolean;
/*     */   }
/*     */   
/*     */   public String pop()
/*     */   {
/*  46 */     if (!this.useStack) {
/*  47 */       return "";
/*     */     }
/*  49 */     List localList = (List)stack.get();
/*  50 */     if ((localList == null) || (localList.size() == 0)) {
/*  51 */       throw new NoSuchElementException("The ThreadContext stack is empty");
/*     */     }
/*  53 */     ArrayList localArrayList = new ArrayList(localList);
/*  54 */     int i = localArrayList.size() - 1;
/*  55 */     String str = (String)localArrayList.remove(i);
/*  56 */     stack.set(Collections.unmodifiableList(localArrayList));
/*  57 */     return str;
/*     */   }
/*     */   
/*     */   public String peek()
/*     */   {
/*  62 */     List localList = (List)stack.get();
/*  63 */     if ((localList == null) || (localList.size() == 0)) {
/*  64 */       return null;
/*     */     }
/*  66 */     int i = localList.size() - 1;
/*  67 */     return (String)localList.get(i);
/*     */   }
/*     */   
/*     */   public void push(String paramString)
/*     */   {
/*  72 */     if (!this.useStack) {
/*  73 */       return;
/*     */     }
/*  75 */     add(paramString);
/*     */   }
/*     */   
/*     */   public int getDepth()
/*     */   {
/*  80 */     List localList = (List)stack.get();
/*  81 */     return localList == null ? 0 : localList.size();
/*     */   }
/*     */   
/*     */   public List<String> asList()
/*     */   {
/*  86 */     List localList = (List)stack.get();
/*  87 */     if (localList == null) {
/*  88 */       return Collections.emptyList();
/*     */     }
/*  90 */     return localList;
/*     */   }
/*     */   
/*     */   public void trim(int paramInt)
/*     */   {
/*  95 */     if (paramInt < 0) {
/*  96 */       throw new IllegalArgumentException("Maximum stack depth cannot be negative");
/*     */     }
/*     */     
/*  99 */     List localList = (List)stack.get();
/* 100 */     if (localList == null) {
/* 101 */       return;
/*     */     }
/* 103 */     ArrayList localArrayList = new ArrayList();
/* 104 */     int i = Math.min(paramInt, localList.size());
/* 105 */     for (int j = 0; j < i; j++) {
/* 106 */       localArrayList.add(localList.get(j));
/*     */     }
/* 108 */     stack.set(localArrayList);
/*     */   }
/*     */   
/*     */   public ThreadContextStack copy()
/*     */   {
/* 113 */     List localList = null;
/* 114 */     if ((!this.useStack) || ((localList = (List)stack.get()) == null)) {
/* 115 */       return new MutableThreadContextStack(new ArrayList());
/*     */     }
/* 117 */     return new MutableThreadContextStack(localList);
/*     */   }
/*     */   
/*     */   public void clear()
/*     */   {
/* 122 */     stack.remove();
/*     */   }
/*     */   
/*     */   public int size()
/*     */   {
/* 127 */     List localList = (List)stack.get();
/* 128 */     return localList == null ? 0 : localList.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 133 */     List localList = (List)stack.get();
/* 134 */     return (localList == null) || (localList.isEmpty());
/*     */   }
/*     */   
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 139 */     List localList = (List)stack.get();
/* 140 */     return (localList != null) && (localList.contains(paramObject));
/*     */   }
/*     */   
/*     */   public Iterator<String> iterator()
/*     */   {
/* 145 */     List localList1 = (List)stack.get();
/* 146 */     if (localList1 == null) {
/* 147 */       List localList2 = Collections.emptyList();
/* 148 */       return localList2.iterator();
/*     */     }
/* 150 */     return localList1.iterator();
/*     */   }
/*     */   
/*     */   public Object[] toArray()
/*     */   {
/* 155 */     List localList = (List)stack.get();
/* 156 */     if (localList == null) {
/* 157 */       return new String[0];
/*     */     }
/* 159 */     return localList.toArray(new Object[localList.size()]);
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 164 */     List localList = (List)stack.get();
/* 165 */     if (localList == null) {
/* 166 */       if (paramArrayOfT.length > 0) {
/* 167 */         paramArrayOfT[0] = null;
/*     */       }
/* 169 */       return paramArrayOfT;
/*     */     }
/* 171 */     return localList.toArray(paramArrayOfT);
/*     */   }
/*     */   
/*     */   public boolean add(String paramString)
/*     */   {
/* 176 */     if (!this.useStack) {
/* 177 */       return false;
/*     */     }
/* 179 */     List localList = (List)stack.get();
/* 180 */     ArrayList localArrayList = localList == null ? new ArrayList() : new ArrayList(localList);
/*     */     
/* 182 */     localArrayList.add(paramString);
/* 183 */     stack.set(Collections.unmodifiableList(localArrayList));
/* 184 */     return true;
/*     */   }
/*     */   
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 189 */     if (!this.useStack) {
/* 190 */       return false;
/*     */     }
/* 192 */     List localList = (List)stack.get();
/* 193 */     if ((localList == null) || (localList.size() == 0)) {
/* 194 */       return false;
/*     */     }
/* 196 */     ArrayList localArrayList = new ArrayList(localList);
/* 197 */     boolean bool = localArrayList.remove(paramObject);
/* 198 */     stack.set(Collections.unmodifiableList(localArrayList));
/* 199 */     return bool;
/*     */   }
/*     */   
/*     */   public boolean containsAll(Collection<?> paramCollection)
/*     */   {
/* 204 */     if (paramCollection.isEmpty()) {
/* 205 */       return true;
/*     */     }
/*     */     
/* 208 */     List localList = (List)stack.get();
/* 209 */     return (localList != null) && (localList.containsAll(paramCollection));
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends String> paramCollection)
/*     */   {
/* 214 */     if ((!this.useStack) || (paramCollection.isEmpty())) {
/* 215 */       return false;
/*     */     }
/* 217 */     List localList = (List)stack.get();
/* 218 */     ArrayList localArrayList = localList == null ? new ArrayList() : new ArrayList(localList);
/*     */     
/* 220 */     localArrayList.addAll(paramCollection);
/* 221 */     stack.set(Collections.unmodifiableList(localArrayList));
/* 222 */     return true;
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> paramCollection)
/*     */   {
/* 227 */     if ((!this.useStack) || (paramCollection.isEmpty())) {
/* 228 */       return false;
/*     */     }
/* 230 */     List localList = (List)stack.get();
/* 231 */     if ((localList == null) || (localList.isEmpty())) {
/* 232 */       return false;
/*     */     }
/* 234 */     ArrayList localArrayList = new ArrayList(localList);
/* 235 */     boolean bool = localArrayList.removeAll(paramCollection);
/* 236 */     stack.set(Collections.unmodifiableList(localArrayList));
/* 237 */     return bool;
/*     */   }
/*     */   
/*     */   public boolean retainAll(Collection<?> paramCollection)
/*     */   {
/* 242 */     if ((!this.useStack) || (paramCollection.isEmpty())) {
/* 243 */       return false;
/*     */     }
/* 245 */     List localList = (List)stack.get();
/* 246 */     if ((localList == null) || (localList.isEmpty())) {
/* 247 */       return false;
/*     */     }
/* 249 */     ArrayList localArrayList = new ArrayList(localList);
/* 250 */     boolean bool = localArrayList.retainAll(paramCollection);
/* 251 */     stack.set(Collections.unmodifiableList(localArrayList));
/* 252 */     return bool;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 257 */     List localList = (List)stack.get();
/* 258 */     return localList == null ? "[]" : localList.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\DefaultThreadContextStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */