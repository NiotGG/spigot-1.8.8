/*     */ package gnu.trove.stack.array;
/*     */ 
/*     */ import gnu.trove.list.array.TFloatArrayList;
/*     */ import gnu.trove.stack.TFloatStack;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
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
/*     */ public class TFloatArrayStack
/*     */   implements TFloatStack, Externalizable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected TFloatArrayList _list;
/*     */   public static final int DEFAULT_CAPACITY = 10;
/*     */   
/*     */   public TFloatArrayStack()
/*     */   {
/*  56 */     this(10);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TFloatArrayStack(int capacity)
/*     */   {
/*  67 */     this._list = new TFloatArrayList(capacity);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TFloatArrayStack(int capacity, float no_entry_value)
/*     */   {
/*  79 */     this._list = new TFloatArrayList(capacity, no_entry_value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TFloatArrayStack(TFloatStack stack)
/*     */   {
/*  90 */     if ((stack instanceof TFloatArrayStack)) {
/*  91 */       TFloatArrayStack array_stack = (TFloatArrayStack)stack;
/*  92 */       this._list = new TFloatArrayList(array_stack._list);
/*     */     } else {
/*  94 */       throw new UnsupportedOperationException("Only support TFloatArrayStack");
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
/*     */   public float getNoEntryValue()
/*     */   {
/* 107 */     return this._list.getNoEntryValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void push(float val)
/*     */   {
/* 117 */     this._list.add(val);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float pop()
/*     */   {
/* 127 */     return this._list.removeAt(this._list.size() - 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float peek()
/*     */   {
/* 137 */     return this._list.get(this._list.size() - 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 145 */     return this._list.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 153 */     this._list.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float[] toArray()
/*     */   {
/* 164 */     float[] retval = this._list.toArray();
/* 165 */     reverse(retval, 0, size());
/* 166 */     return retval;
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
/*     */   public void toArray(float[] dest)
/*     */   {
/* 182 */     int size = size();
/* 183 */     int start = size - dest.length;
/* 184 */     if (start < 0) {
/* 185 */       start = 0;
/*     */     }
/*     */     
/* 188 */     int length = Math.min(size, dest.length);
/* 189 */     this._list.toArray(dest, start, length);
/* 190 */     reverse(dest, 0, length);
/* 191 */     if (dest.length > size) {
/* 192 */       dest[size] = this._list.getNoEntryValue();
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
/*     */   private void reverse(float[] dest, int from, int to)
/*     */   {
/* 205 */     if (from == to) {
/* 206 */       return;
/*     */     }
/* 208 */     if (from > to) {
/* 209 */       throw new IllegalArgumentException("from cannot be greater than to");
/*     */     }
/* 211 */     int i = from; for (int j = to - 1; i < j; j--) {
/* 212 */       swap(dest, i, j);i++;
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
/*     */   private void swap(float[] dest, int i, int j)
/*     */   {
/* 225 */     float tmp = dest[i];
/* 226 */     dest[i] = dest[j];
/* 227 */     dest[j] = tmp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 237 */     StringBuilder buf = new StringBuilder("{");
/* 238 */     for (int i = this._list.size() - 1; i > 0; i--) {
/* 239 */       buf.append(this._list.get(i));
/* 240 */       buf.append(", ");
/*     */     }
/* 242 */     if (size() > 0) {
/* 243 */       buf.append(this._list.get(0));
/*     */     }
/* 245 */     buf.append("}");
/* 246 */     return buf.toString();
/*     */   }
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/* 251 */     if (this == o) {
/* 252 */       return true;
/*     */     }
/* 254 */     if ((o == null) || (getClass() != o.getClass())) {
/* 255 */       return false;
/*     */     }
/*     */     
/* 258 */     TFloatArrayStack that = (TFloatArrayStack)o;
/*     */     
/* 260 */     return this._list.equals(that._list);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 265 */     return this._list.hashCode();
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/* 271 */     out.writeByte(0);
/*     */     
/*     */ 
/* 274 */     out.writeObject(this._list);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 282 */     in.readByte();
/*     */     
/*     */ 
/* 285 */     this._list = ((TFloatArrayList)in.readObject());
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\stack\array\TFloatArrayStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */