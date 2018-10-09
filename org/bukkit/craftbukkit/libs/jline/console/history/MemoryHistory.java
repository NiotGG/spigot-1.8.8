/*     */ package org.bukkit.craftbukkit.libs.jline.console.history;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Preconditions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MemoryHistory
/*     */   implements History
/*     */ {
/*     */   public static final int DEFAULT_MAX_SIZE = 500;
/*     */   private final LinkedList<CharSequence> items;
/*     */   private int maxSize;
/*     */   private boolean ignoreDuplicates;
/*     */   private boolean autoTrim;
/*     */   private int offset;
/*     */   private int index;
/*     */   
/*     */   public MemoryHistory()
/*     */   {
/*  30 */     this.items = new LinkedList();
/*     */     
/*  32 */     this.maxSize = 500;
/*     */     
/*  34 */     this.ignoreDuplicates = true;
/*     */     
/*  36 */     this.autoTrim = false;
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
/*  48 */     this.offset = 0;
/*     */     
/*  50 */     this.index = 0;
/*     */   }
/*     */   
/*  53 */   public void setMaxSize(int maxSize) { this.maxSize = maxSize;
/*  54 */     maybeResize();
/*     */   }
/*     */   
/*     */   public int getMaxSize() {
/*  58 */     return this.maxSize;
/*     */   }
/*     */   
/*     */   public boolean isIgnoreDuplicates() {
/*  62 */     return this.ignoreDuplicates;
/*     */   }
/*     */   
/*     */   public void setIgnoreDuplicates(boolean flag) {
/*  66 */     this.ignoreDuplicates = flag;
/*     */   }
/*     */   
/*     */   public boolean isAutoTrim() {
/*  70 */     return this.autoTrim;
/*     */   }
/*     */   
/*     */   public void setAutoTrim(boolean flag) {
/*  74 */     this.autoTrim = flag;
/*     */   }
/*     */   
/*     */   public int size() {
/*  78 */     return this.items.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  82 */     return this.items.isEmpty();
/*     */   }
/*     */   
/*     */   public int index() {
/*  86 */     return this.offset + this.index;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  90 */     this.items.clear();
/*  91 */     this.offset = 0;
/*  92 */     this.index = 0;
/*     */   }
/*     */   
/*     */   public CharSequence get(int index) {
/*  96 */     return (CharSequence)this.items.get(index - this.offset);
/*     */   }
/*     */   
/*     */   public void set(int index, CharSequence item) {
/* 100 */     this.items.set(index - this.offset, item);
/*     */   }
/*     */   
/*     */   public void add(CharSequence item) {
/* 104 */     Preconditions.checkNotNull(item);
/*     */     
/* 106 */     if (isAutoTrim()) {
/* 107 */       item = String.valueOf(item).trim();
/*     */     }
/*     */     
/* 110 */     if ((isIgnoreDuplicates()) && 
/* 111 */       (!this.items.isEmpty()) && (item.equals(this.items.getLast()))) {
/* 112 */       return;
/*     */     }
/*     */     
/*     */ 
/* 116 */     internalAdd(item);
/*     */   }
/*     */   
/*     */   public CharSequence remove(int i) {
/* 120 */     return (CharSequence)this.items.remove(i);
/*     */   }
/*     */   
/*     */   public CharSequence removeFirst() {
/* 124 */     return (CharSequence)this.items.removeFirst();
/*     */   }
/*     */   
/*     */   public CharSequence removeLast() {
/* 128 */     return (CharSequence)this.items.removeLast();
/*     */   }
/*     */   
/*     */   protected void internalAdd(CharSequence item) {
/* 132 */     this.items.add(item);
/*     */     
/* 134 */     maybeResize();
/*     */   }
/*     */   
/*     */   public void replace(CharSequence item) {
/* 138 */     this.items.removeLast();
/* 139 */     add(item);
/*     */   }
/*     */   
/*     */   private void maybeResize() {
/* 143 */     while (size() > getMaxSize()) {
/* 144 */       this.items.removeFirst();
/* 145 */       this.offset += 1;
/*     */     }
/*     */     
/* 148 */     this.index = size();
/*     */   }
/*     */   
/*     */   public ListIterator<History.Entry> entries(int index) {
/* 152 */     return new EntriesIterator(index - this.offset, null);
/*     */   }
/*     */   
/*     */   public ListIterator<History.Entry> entries() {
/* 156 */     return entries(this.offset);
/*     */   }
/*     */   
/*     */   public Iterator<History.Entry> iterator() {
/* 160 */     return entries();
/*     */   }
/*     */   
/*     */   private static class EntryImpl
/*     */     implements History.Entry
/*     */   {
/*     */     private final int index;
/*     */     private final CharSequence value;
/*     */     
/*     */     public EntryImpl(int index, CharSequence value)
/*     */     {
/* 171 */       this.index = index;
/* 172 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int index() {
/* 176 */       return this.index;
/*     */     }
/*     */     
/*     */     public CharSequence value() {
/* 180 */       return this.value;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 185 */       return String.format("%d: %s", new Object[] { Integer.valueOf(this.index), this.value });
/*     */     }
/*     */   }
/*     */   
/*     */   private class EntriesIterator implements ListIterator<History.Entry>
/*     */   {
/*     */     private final ListIterator<CharSequence> source;
/*     */     
/*     */     private EntriesIterator(int index)
/*     */     {
/* 195 */       this.source = MemoryHistory.this.items.listIterator(index);
/*     */     }
/*     */     
/*     */     public History.Entry next() {
/* 199 */       if (!this.source.hasNext()) {
/* 200 */         throw new NoSuchElementException();
/*     */       }
/* 202 */       return new MemoryHistory.EntryImpl(MemoryHistory.this.offset + this.source.nextIndex(), (CharSequence)this.source.next());
/*     */     }
/*     */     
/*     */     public History.Entry previous() {
/* 206 */       if (!this.source.hasPrevious()) {
/* 207 */         throw new NoSuchElementException();
/*     */       }
/* 209 */       return new MemoryHistory.EntryImpl(MemoryHistory.this.offset + this.source.previousIndex(), (CharSequence)this.source.previous());
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 213 */       return MemoryHistory.this.offset + this.source.nextIndex();
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 217 */       return MemoryHistory.this.offset + this.source.previousIndex();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 221 */       return this.source.hasNext();
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 225 */       return this.source.hasPrevious();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 229 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void set(History.Entry entry) {
/* 233 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void add(History.Entry entry) {
/* 237 */       throw new UnsupportedOperationException();
/*     */     }
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
/*     */   public boolean moveToLast()
/*     */   {
/* 253 */     int lastEntry = size() - 1;
/* 254 */     if ((lastEntry >= 0) && (lastEntry != this.index)) {
/* 255 */       this.index = (size() - 1);
/* 256 */       return true;
/*     */     }
/*     */     
/* 259 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean moveTo(int index)
/*     */   {
/* 268 */     index -= this.offset;
/* 269 */     if ((index >= 0) && (index < size())) {
/* 270 */       this.index = index;
/* 271 */       return true;
/*     */     }
/* 273 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean moveToFirst()
/*     */   {
/* 283 */     if ((size() > 0) && (this.index != 0)) {
/* 284 */       this.index = 0;
/* 285 */       return true;
/*     */     }
/*     */     
/* 288 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void moveToEnd()
/*     */   {
/* 296 */     this.index = size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CharSequence current()
/*     */   {
/* 303 */     if (this.index >= size()) {
/* 304 */       return "";
/*     */     }
/*     */     
/* 307 */     return (CharSequence)this.items.get(this.index);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean previous()
/*     */   {
/* 316 */     if (this.index <= 0) {
/* 317 */       return false;
/*     */     }
/*     */     
/* 320 */     this.index -= 1;
/*     */     
/* 322 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean next()
/*     */   {
/* 331 */     if (this.index >= size()) {
/* 332 */       return false;
/*     */     }
/*     */     
/* 335 */     this.index += 1;
/*     */     
/* 337 */     return true;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 342 */     StringBuilder sb = new StringBuilder();
/* 343 */     for (History.Entry e : this) {
/* 344 */       sb.append(e.toString() + "\n");
/*     */     }
/* 346 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\history\MemoryHistory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */