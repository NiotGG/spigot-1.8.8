/*     */ package gnu.trove.list.linked;
/*     */ 
/*     */ import gnu.trove.list.TLinkable;
/*     */ import gnu.trove.procedure.TObjectProcedure;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.AbstractSequentialList;
/*     */ import java.util.ListIterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TLinkedList<T extends TLinkable<T>>
/*     */   extends AbstractSequentialList<T>
/*     */   implements Externalizable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected T _head;
/*     */   protected T _tail;
/*  78 */   protected int _size = 0;
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
/*     */   public ListIterator<T> listIterator(int index)
/*     */   {
/* 100 */     return new IteratorImpl(index);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 110 */     return this._size;
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
/*     */   public void add(int index, T linkable)
/*     */   {
/* 123 */     if ((index < 0) || (index > size())) {
/* 124 */       throw new IndexOutOfBoundsException("index:" + index);
/*     */     }
/* 126 */     insert(index, linkable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean add(T linkable)
/*     */   {
/* 137 */     insert(this._size, linkable);
/* 138 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addFirst(T linkable)
/*     */   {
/* 148 */     insert(0, linkable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addLast(T linkable)
/*     */   {
/* 158 */     insert(size(), linkable);
/*     */   }
/*     */   
/*     */ 
/*     */   public void clear()
/*     */   {
/* 164 */     if (null != this._head) {
/* 165 */       for (TLinkable<T> link = this._head.getNext(); 
/* 166 */           link != null; 
/* 167 */           link = link.getNext()) {
/* 168 */         TLinkable<T> prev = link.getPrevious();
/* 169 */         prev.setNext(null);
/* 170 */         link.setPrevious(null);
/*     */       }
/* 172 */       this._head = (this._tail = null);
/*     */     }
/* 174 */     this._size = 0;
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
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 190 */     Object[] o = new Object[this._size];
/* 191 */     int i = 0;
/* 192 */     for (TLinkable link = this._head; link != null; link = link.getNext()) {
/* 193 */       o[(i++)] = link;
/*     */     }
/* 195 */     return o;
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
/*     */   public Object[] toUnlinkedArray()
/*     */   {
/* 210 */     Object[] o = new Object[this._size];
/* 211 */     int i = 0;
/* 212 */     for (TLinkable<T> link = this._head; link != null; i++) {
/* 213 */       o[i] = link;
/* 214 */       TLinkable<T> tmp = link;
/* 215 */       link = link.getNext();
/* 216 */       tmp.setNext(null);
/* 217 */       tmp.setPrevious(null);
/*     */     }
/* 219 */     this._size = 0;
/* 220 */     this._head = (this._tail = null);
/* 221 */     return o;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T[] toUnlinkedArray(T[] a)
/*     */   {
/* 233 */     int size = size();
/* 234 */     if (a.length < size) {
/* 235 */       a = (TLinkable[])Array.newInstance(a.getClass().getComponentType(), size);
/*     */     }
/*     */     
/* 238 */     int i = 0;
/* 239 */     for (T link = this._head; link != null; i++) {
/* 240 */       a[i] = link;
/* 241 */       T tmp = link;
/* 242 */       link = link.getNext();
/* 243 */       tmp.setNext(null);
/* 244 */       tmp.setPrevious(null);
/*     */     }
/* 246 */     this._size = 0;
/* 247 */     this._head = (this._tail = null);
/* 248 */     return a;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(Object o)
/*     */   {
/* 259 */     for (TLinkable<T> link = this._head; link != null; link = link.getNext()) {
/* 260 */       if (o.equals(link)) {
/* 261 */         return true;
/*     */       }
/*     */     }
/* 264 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T get(int index)
/*     */   {
/* 273 */     if ((index < 0) || (index >= this._size)) {
/* 274 */       throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this._size);
/*     */     }
/*     */     
/*     */ 
/* 278 */     if (index > this._size >> 1) {
/* 279 */       int position = this._size - 1;
/* 280 */       T node = this._tail;
/*     */       
/* 282 */       while (position > index) {
/* 283 */         node = node.getPrevious();
/* 284 */         position--;
/*     */       }
/*     */       
/* 287 */       return node;
/*     */     }
/* 289 */     int position = 0;
/* 290 */     T node = this._head;
/*     */     
/* 292 */     while (position < index) {
/* 293 */       node = node.getNext();
/* 294 */       position++;
/*     */     }
/*     */     
/* 297 */     return node;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T getFirst()
/*     */   {
/* 308 */     return this._head;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T getLast()
/*     */   {
/* 318 */     return this._tail;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T getNext(T current)
/*     */   {
/* 339 */     return current.getNext();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T getPrevious(T current)
/*     */   {
/* 360 */     return current.getPrevious();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T removeFirst()
/*     */   {
/* 371 */     T o = this._head;
/*     */     
/* 373 */     if (o == null) {
/* 374 */       return null;
/*     */     }
/*     */     
/* 377 */     T n = o.getNext();
/* 378 */     o.setNext(null);
/*     */     
/* 380 */     if (null != n) {
/* 381 */       n.setPrevious(null);
/*     */     }
/*     */     
/* 384 */     this._head = n;
/* 385 */     if (--this._size == 0) {
/* 386 */       this._tail = null;
/*     */     }
/* 388 */     return o;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T removeLast()
/*     */   {
/* 399 */     T o = this._tail;
/*     */     
/* 401 */     if (o == null) {
/* 402 */       return null;
/*     */     }
/*     */     
/* 405 */     T prev = o.getPrevious();
/* 406 */     o.setPrevious(null);
/*     */     
/* 408 */     if (null != prev) {
/* 409 */       prev.setNext(null);
/*     */     }
/* 411 */     this._tail = prev;
/* 412 */     if (--this._size == 0) {
/* 413 */       this._head = null;
/*     */     }
/* 415 */     return o;
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
/*     */   protected void insert(int index, T linkable)
/*     */   {
/* 428 */     if (this._size == 0) {
/* 429 */       this._head = (this._tail = linkable);
/* 430 */     } else if (index == 0) {
/* 431 */       linkable.setNext(this._head);
/* 432 */       this._head.setPrevious(linkable);
/* 433 */       this._head = linkable;
/* 434 */     } else if (index == this._size) {
/* 435 */       this._tail.setNext(linkable);
/* 436 */       linkable.setPrevious(this._tail);
/* 437 */       this._tail = linkable;
/*     */     } else {
/* 439 */       T node = get(index);
/*     */       
/* 441 */       T before = node.getPrevious();
/* 442 */       if (before != null) {
/* 443 */         before.setNext(linkable);
/*     */       }
/*     */       
/* 446 */       linkable.setPrevious(before);
/* 447 */       linkable.setNext(node);
/* 448 */       node.setPrevious(linkable);
/*     */     }
/* 450 */     this._size += 1;
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
/*     */   public boolean remove(Object o)
/*     */   {
/* 465 */     if ((o instanceof TLinkable))
/*     */     {
/* 467 */       TLinkable<T> link = (TLinkable)o;
/*     */       
/* 469 */       T p = link.getPrevious();
/* 470 */       T n = link.getNext();
/*     */       
/* 472 */       if ((n == null) && (p == null))
/*     */       {
/*     */ 
/*     */ 
/* 476 */         if (o != this._head) {
/* 477 */           return false;
/*     */         }
/*     */         
/* 480 */         this._head = (this._tail = null);
/* 481 */       } else if (n == null)
/*     */       {
/* 483 */         link.setPrevious(null);
/* 484 */         p.setNext(null);
/* 485 */         this._tail = p;
/* 486 */       } else if (p == null)
/*     */       {
/* 488 */         link.setNext(null);
/* 489 */         n.setPrevious(null);
/* 490 */         this._head = n;
/*     */       } else {
/* 492 */         p.setNext(n);
/* 493 */         n.setPrevious(p);
/* 494 */         link.setNext(null);
/* 495 */         link.setPrevious(null);
/*     */       }
/*     */       
/* 498 */       this._size -= 1;
/* 499 */       return true;
/*     */     }
/* 501 */     return false;
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
/*     */   public void addBefore(T current, T newElement)
/*     */   {
/* 516 */     if (current == this._head) {
/* 517 */       addFirst(newElement);
/* 518 */     } else if (current == null) {
/* 519 */       addLast(newElement);
/*     */     } else {
/* 521 */       T p = current.getPrevious();
/* 522 */       newElement.setNext(current);
/* 523 */       p.setNext(newElement);
/* 524 */       newElement.setPrevious(p);
/* 525 */       current.setPrevious(newElement);
/* 526 */       this._size += 1;
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
/*     */   public void addAfter(T current, T newElement)
/*     */   {
/* 541 */     if (current == this._tail) {
/* 542 */       addLast(newElement);
/* 543 */     } else if (current == null) {
/* 544 */       addFirst(newElement);
/*     */     } else {
/* 546 */       T n = current.getNext();
/* 547 */       newElement.setPrevious(current);
/* 548 */       newElement.setNext(n);
/* 549 */       current.setNext(newElement);
/* 550 */       n.setPrevious(newElement);
/* 551 */       this._size += 1;
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
/*     */   public boolean forEachValue(TObjectProcedure<T> procedure)
/*     */   {
/* 565 */     T node = this._head;
/* 566 */     while (node != null) {
/* 567 */       boolean keep_going = procedure.execute(node);
/* 568 */       if (!keep_going) {
/* 569 */         return false;
/*     */       }
/*     */       
/* 572 */       node = node.getNext();
/*     */     }
/*     */     
/* 575 */     return true;
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/* 581 */     out.writeByte(0);
/*     */     
/*     */ 
/* 584 */     out.writeInt(this._size);
/*     */     
/*     */ 
/* 587 */     out.writeObject(this._head);
/*     */     
/*     */ 
/* 590 */     out.writeObject(this._tail);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 599 */     in.readByte();
/*     */     
/*     */ 
/* 602 */     this._size = in.readInt();
/*     */     
/*     */ 
/* 605 */     this._head = ((TLinkable)in.readObject());
/*     */     
/*     */ 
/* 608 */     this._tail = ((TLinkable)in.readObject());
/*     */   }
/*     */   
/*     */ 
/*     */   protected final class IteratorImpl
/*     */     implements ListIterator<T>
/*     */   {
/* 615 */     private int _nextIndex = 0;
/*     */     
/*     */ 
/*     */ 
/*     */     private T _next;
/*     */     
/*     */ 
/*     */     private T _lastReturned;
/*     */     
/*     */ 
/*     */ 
/*     */     IteratorImpl(int position)
/*     */     {
/* 628 */       if ((position < 0) || (position > TLinkedList.this._size)) {
/* 629 */         throw new IndexOutOfBoundsException();
/*     */       }
/*     */       
/* 632 */       this._nextIndex = position;
/* 633 */       if (position == 0) {
/* 634 */         this._next = TLinkedList.this._head;
/* 635 */       } else if (position == TLinkedList.this._size) {
/* 636 */         this._next = null; } else { int pos;
/* 637 */         if (position < TLinkedList.this._size >> 1) {
/* 638 */           pos = 0;
/* 639 */           for (this._next = TLinkedList.this._head; pos < position; pos++) {
/* 640 */             this._next = this._next.getNext();
/*     */           }
/*     */         } else {
/* 643 */           int pos = TLinkedList.this._size - 1;
/* 644 */           for (this._next = TLinkedList.this._tail; pos > position; pos--) {
/* 645 */             this._next = this._next.getPrevious();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public final void add(T linkable)
/*     */     {
/* 658 */       this._lastReturned = null;
/* 659 */       this._nextIndex += 1;
/*     */       
/* 661 */       if (TLinkedList.this._size == 0) {
/* 662 */         TLinkedList.this.add(linkable);
/*     */       } else {
/* 664 */         TLinkedList.this.addBefore(this._next, linkable);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public final boolean hasNext()
/*     */     {
/* 675 */       return this._nextIndex != TLinkedList.this._size;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public final boolean hasPrevious()
/*     */     {
/* 685 */       return this._nextIndex != 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public final T next()
/*     */     {
/* 698 */       if (this._nextIndex == TLinkedList.this._size) {
/* 699 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 702 */       this._lastReturned = this._next;
/* 703 */       this._next = this._next.getNext();
/* 704 */       this._nextIndex += 1;
/* 705 */       return this._lastReturned;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public final int nextIndex()
/*     */     {
/* 716 */       return this._nextIndex;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public final T previous()
/*     */     {
/* 729 */       if (this._nextIndex == 0) {
/* 730 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 733 */       if (this._nextIndex == TLinkedList.this._size) {
/* 734 */         this._lastReturned = (this._next = TLinkedList.this._tail);
/*     */       } else {
/* 736 */         this._lastReturned = (this._next = this._next.getPrevious());
/*     */       }
/*     */       
/* 739 */       this._nextIndex -= 1;
/* 740 */       return this._lastReturned;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public final int previousIndex()
/*     */     {
/* 750 */       return this._nextIndex - 1;
/*     */     }
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
/*     */     public final void remove()
/*     */     {
/* 764 */       if (this._lastReturned == null) {
/* 765 */         throw new IllegalStateException("must invoke next or previous before invoking remove");
/*     */       }
/*     */       
/* 768 */       if (this._lastReturned != this._next) {
/* 769 */         this._nextIndex -= 1;
/*     */       }
/* 771 */       this._next = this._lastReturned.getNext();
/* 772 */       TLinkedList.this.remove(this._lastReturned);
/* 773 */       this._lastReturned = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public final void set(T linkable)
/*     */     {
/* 784 */       if (this._lastReturned == null) {
/* 785 */         throw new IllegalStateException();
/*     */       }
/*     */       
/* 788 */       swap(this._lastReturned, linkable);
/* 789 */       this._lastReturned = linkable;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private void swap(T from, T to)
/*     */     {
/* 800 */       T from_p = from.getPrevious();
/* 801 */       T from_n = from.getNext();
/*     */       
/* 803 */       T to_p = to.getPrevious();
/* 804 */       T to_n = to.getNext();
/*     */       
/*     */ 
/* 807 */       if (from_n == to) {
/* 808 */         if (from_p != null) from_p.setNext(to);
/* 809 */         to.setPrevious(from_p);
/* 810 */         to.setNext(from);
/* 811 */         from.setPrevious(to);
/* 812 */         from.setNext(to_n);
/* 813 */         if (to_n != null) { to_n.setPrevious(from);
/*     */         }
/*     */       }
/* 816 */       else if (to_n == from) {
/* 817 */         if (to_p != null) to_p.setNext(to);
/* 818 */         to.setPrevious(from);
/* 819 */         to.setNext(from_n);
/* 820 */         from.setPrevious(to_p);
/* 821 */         from.setNext(to);
/* 822 */         if (from_n != null) from_n.setPrevious(to);
/*     */       }
/*     */       else {
/* 825 */         from.setNext(to_n);
/* 826 */         from.setPrevious(to_p);
/* 827 */         if (to_p != null) to_p.setNext(from);
/* 828 */         if (to_n != null) { to_n.setPrevious(from);
/*     */         }
/* 830 */         to.setNext(from_n);
/* 831 */         to.setPrevious(from_p);
/* 832 */         if (from_p != null) from_p.setNext(to);
/* 833 */         if (from_n != null) { from_n.setPrevious(to);
/*     */         }
/*     */       }
/* 836 */       if (TLinkedList.this._head == from) { TLinkedList.this._head = to;
/* 837 */       } else if (TLinkedList.this._head == to) { TLinkedList.this._head = from;
/*     */       }
/* 839 */       if (TLinkedList.this._tail == from) { TLinkedList.this._tail = to;
/* 840 */       } else if (TLinkedList.this._tail == to) { TLinkedList.this._tail = from;
/*     */       }
/* 842 */       if (this._lastReturned == from) { this._lastReturned = to;
/* 843 */       } else if (this._lastReturned == to) { this._lastReturned = from;
/*     */       }
/* 845 */       if (this._next == from) { this._next = to;
/* 846 */       } else if (this._next == to) this._next = from;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\list\linked\TLinkedList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */