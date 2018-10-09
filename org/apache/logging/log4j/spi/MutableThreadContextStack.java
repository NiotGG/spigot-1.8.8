/*     */ package org.apache.logging.log4j.spi;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class MutableThreadContextStack
/*     */   implements ThreadContextStack
/*     */ {
/*     */   private static final long serialVersionUID = 50505011L;
/*     */   private final List<String> list;
/*     */   
/*     */   public MutableThreadContextStack(List<String> paramList)
/*     */   {
/*  37 */     this.list = new ArrayList(paramList);
/*     */   }
/*     */   
/*     */   private MutableThreadContextStack(MutableThreadContextStack paramMutableThreadContextStack) {
/*  41 */     this.list = new ArrayList(paramMutableThreadContextStack.list);
/*     */   }
/*     */   
/*     */   public String pop()
/*     */   {
/*  46 */     if (this.list.isEmpty()) {
/*  47 */       return null;
/*     */     }
/*  49 */     int i = this.list.size() - 1;
/*  50 */     String str = (String)this.list.remove(i);
/*  51 */     return str;
/*     */   }
/*     */   
/*     */   public String peek()
/*     */   {
/*  56 */     if (this.list.isEmpty()) {
/*  57 */       return null;
/*     */     }
/*  59 */     int i = this.list.size() - 1;
/*  60 */     return (String)this.list.get(i);
/*     */   }
/*     */   
/*     */   public void push(String paramString)
/*     */   {
/*  65 */     this.list.add(paramString);
/*     */   }
/*     */   
/*     */   public int getDepth()
/*     */   {
/*  70 */     return this.list.size();
/*     */   }
/*     */   
/*     */   public List<String> asList()
/*     */   {
/*  75 */     return this.list;
/*     */   }
/*     */   
/*     */   public void trim(int paramInt)
/*     */   {
/*  80 */     if (paramInt < 0) {
/*  81 */       throw new IllegalArgumentException("Maximum stack depth cannot be negative");
/*     */     }
/*  83 */     if (this.list == null) {
/*  84 */       return;
/*     */     }
/*  86 */     ArrayList localArrayList = new ArrayList(this.list.size());
/*  87 */     int i = Math.min(paramInt, this.list.size());
/*  88 */     for (int j = 0; j < i; j++) {
/*  89 */       localArrayList.add(this.list.get(j));
/*     */     }
/*  91 */     this.list.clear();
/*  92 */     this.list.addAll(localArrayList);
/*     */   }
/*     */   
/*     */   public ThreadContextStack copy()
/*     */   {
/*  97 */     return new MutableThreadContextStack(this);
/*     */   }
/*     */   
/*     */   public void clear()
/*     */   {
/* 102 */     this.list.clear();
/*     */   }
/*     */   
/*     */   public int size()
/*     */   {
/* 107 */     return this.list.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 112 */     return this.list.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 117 */     return this.list.contains(paramObject);
/*     */   }
/*     */   
/*     */   public Iterator<String> iterator()
/*     */   {
/* 122 */     return this.list.iterator();
/*     */   }
/*     */   
/*     */   public Object[] toArray()
/*     */   {
/* 127 */     return this.list.toArray();
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 132 */     return this.list.toArray(paramArrayOfT);
/*     */   }
/*     */   
/*     */   public boolean add(String paramString)
/*     */   {
/* 137 */     return this.list.add(paramString);
/*     */   }
/*     */   
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 142 */     return this.list.remove(paramObject);
/*     */   }
/*     */   
/*     */   public boolean containsAll(Collection<?> paramCollection)
/*     */   {
/* 147 */     return this.list.containsAll(paramCollection);
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends String> paramCollection)
/*     */   {
/* 152 */     return this.list.addAll(paramCollection);
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> paramCollection)
/*     */   {
/* 157 */     return this.list.removeAll(paramCollection);
/*     */   }
/*     */   
/*     */   public boolean retainAll(Collection<?> paramCollection)
/*     */   {
/* 162 */     return this.list.retainAll(paramCollection);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 167 */     return String.valueOf(this.list);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\MutableThreadContextStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */