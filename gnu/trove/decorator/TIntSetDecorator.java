/*     */ package gnu.trove.decorator;
/*     */ 
/*     */ import gnu.trove.iterator.TIntIterator;
/*     */ import gnu.trove.set.TIntSet;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Iterator;
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
/*     */ public class TIntSetDecorator
/*     */   extends AbstractSet<Integer>
/*     */   implements Set<Integer>, Externalizable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected TIntSet _set;
/*     */   
/*     */   public TIntSetDecorator() {}
/*     */   
/*     */   public TIntSetDecorator(TIntSet set)
/*     */   {
/*  76 */     this._set = set;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TIntSet getSet()
/*     */   {
/*  86 */     return this._set;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean add(Integer value)
/*     */   {
/*  96 */     return (value != null) && (this._set.add(value.intValue()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object other)
/*     */   {
/* 108 */     if (this._set.equals(other))
/* 109 */       return true;
/* 110 */     if ((other instanceof Set)) {
/* 111 */       Set that = (Set)other;
/* 112 */       if (that.size() != this._set.size()) {
/* 113 */         return false;
/*     */       }
/* 115 */       Iterator it = that.iterator();
/* 116 */       for (int i = that.size(); i-- > 0;) {
/* 117 */         Object val = it.next();
/* 118 */         if ((val instanceof Integer)) {
/* 119 */           int v = ((Integer)val).intValue();
/* 120 */           if (!this._set.contains(v))
/*     */           {
/*     */ 
/* 123 */             return false;
/*     */           }
/*     */         } else {
/* 126 */           return false;
/*     */         }
/*     */       }
/* 129 */       return true;
/*     */     }
/*     */     
/* 132 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 141 */     this._set.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean remove(Object value)
/*     */   {
/* 152 */     return ((value instanceof Integer)) && (this._set.remove(((Integer)value).intValue()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<Integer> iterator()
/*     */   {
/* 162 */     new Iterator() {
/* 163 */       private final TIntIterator it = TIntSetDecorator.this._set.iterator();
/*     */       
/*     */       public Integer next() {
/* 166 */         return Integer.valueOf(this.it.next());
/*     */       }
/*     */       
/*     */       public boolean hasNext() {
/* 170 */         return this.it.hasNext();
/*     */       }
/*     */       
/*     */       public void remove() {
/* 174 */         this.it.remove();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 186 */     return this._set.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 196 */     return this._set.size() == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(Object o)
/*     */   {
/* 205 */     if (!(o instanceof Integer)) return false;
/* 206 */     return this._set.contains(((Integer)o).intValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 215 */     in.readByte();
/*     */     
/*     */ 
/* 218 */     this._set = ((TIntSet)in.readObject());
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/* 225 */     out.writeByte(0);
/*     */     
/*     */ 
/* 228 */     out.writeObject(this._set);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\decorator\TIntSetDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */