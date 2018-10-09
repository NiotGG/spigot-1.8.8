/*     */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ public class HashTreeSet<V> implements Set<V>
/*     */ {
/*  11 */   private HashSet<V> hash = new HashSet();
/*  12 */   private TreeSet<V> tree = new TreeSet();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/*  20 */     return this.hash.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/*  25 */     return this.hash.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean contains(Object o)
/*     */   {
/*  30 */     return this.hash.contains(o);
/*     */   }
/*     */   
/*     */   public Iterator<V> iterator()
/*     */   {
/*  35 */     new Iterator()
/*     */     {
/*  37 */       private Iterator<V> it = HashTreeSet.this.tree.iterator();
/*     */       private V last;
/*     */       
/*     */       public boolean hasNext()
/*     */       {
/*  42 */         return this.it.hasNext();
/*     */       }
/*     */       
/*     */       public V next()
/*     */       {
/*  47 */         return (V)(this.last = this.it.next());
/*     */       }
/*     */       
/*     */       public void remove()
/*     */       {
/*  52 */         if (this.last == null) {
/*  53 */           throw new IllegalStateException();
/*     */         }
/*  55 */         this.it.remove();
/*  56 */         HashTreeSet.this.hash.remove(this.last);
/*  57 */         this.last = null;
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public Object[] toArray()
/*     */   {
/*  64 */     return this.hash.toArray();
/*     */   }
/*     */   
/*     */   public Object[] toArray(Object[] a)
/*     */   {
/*  69 */     return this.hash.toArray(a);
/*     */   }
/*     */   
/*     */   public boolean add(V e)
/*     */   {
/*  74 */     this.hash.add(e);
/*  75 */     return this.tree.add(e);
/*     */   }
/*     */   
/*     */   public boolean remove(Object o)
/*     */   {
/*  80 */     this.hash.remove(o);
/*  81 */     return this.tree.remove(o);
/*     */   }
/*     */   
/*     */   public boolean containsAll(Collection c)
/*     */   {
/*  86 */     return this.hash.containsAll(c);
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection c)
/*     */   {
/*  91 */     this.tree.addAll(c);
/*  92 */     return this.hash.addAll(c);
/*     */   }
/*     */   
/*     */   public boolean retainAll(Collection c)
/*     */   {
/*  97 */     this.tree.retainAll(c);
/*  98 */     return this.hash.retainAll(c);
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection c)
/*     */   {
/* 103 */     this.tree.removeAll(c);
/* 104 */     return this.hash.removeAll(c);
/*     */   }
/*     */   
/*     */   public void clear()
/*     */   {
/* 109 */     this.hash.clear();
/* 110 */     this.tree.clear();
/*     */   }
/*     */   
/*     */   public V first() {
/* 114 */     return (V)this.tree.first();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\HashTreeSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */