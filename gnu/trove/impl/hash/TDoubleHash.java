/*     */ package gnu.trove.impl.hash;
/*     */ 
/*     */ import gnu.trove.impl.Constants;
/*     */ import gnu.trove.impl.HashFunctions;
/*     */ import gnu.trove.procedure.TDoubleProcedure;
/*     */ import java.util.Arrays;
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
/*     */ public abstract class TDoubleHash
/*     */   extends TPrimitiveHash
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   public transient double[] _set;
/*     */   protected double no_entry_value;
/*     */   protected boolean consumeFreeSlot;
/*     */   
/*     */   public TDoubleHash()
/*     */   {
/*  66 */     this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
/*     */     
/*  68 */     if (this.no_entry_value != 0.0D) {
/*  69 */       Arrays.fill(this._set, this.no_entry_value);
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
/*     */   public TDoubleHash(int initialCapacity)
/*     */   {
/*  82 */     super(initialCapacity);
/*  83 */     this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
/*     */     
/*  85 */     if (this.no_entry_value != 0.0D) {
/*  86 */       Arrays.fill(this._set, this.no_entry_value);
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
/*     */   public TDoubleHash(int initialCapacity, float loadFactor)
/*     */   {
/* 100 */     super(initialCapacity, loadFactor);
/* 101 */     this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
/*     */     
/* 103 */     if (this.no_entry_value != 0.0D) {
/* 104 */       Arrays.fill(this._set, this.no_entry_value);
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
/*     */   public TDoubleHash(int initialCapacity, float loadFactor, double no_entry_value)
/*     */   {
/* 119 */     super(initialCapacity, loadFactor);
/* 120 */     this.no_entry_value = no_entry_value;
/*     */     
/* 122 */     if (no_entry_value != 0.0D) {
/* 123 */       Arrays.fill(this._set, no_entry_value);
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
/*     */   public double getNoEntryValue()
/*     */   {
/* 136 */     return this.no_entry_value;
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
/*     */   protected int setUp(int initialCapacity)
/*     */   {
/* 150 */     int capacity = super.setUp(initialCapacity);
/* 151 */     this._set = new double[capacity];
/* 152 */     return capacity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(double val)
/*     */   {
/* 163 */     return index(val) >= 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean forEach(TDoubleProcedure procedure)
/*     */   {
/* 175 */     byte[] states = this._states;
/* 176 */     double[] set = this._set;
/* 177 */     for (int i = set.length; i-- > 0;) {
/* 178 */       if ((states[i] == 1) && (!procedure.execute(set[i]))) {
/* 179 */         return false;
/*     */       }
/*     */     }
/* 182 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void removeAt(int index)
/*     */   {
/* 192 */     this._set[index] = this.no_entry_value;
/* 193 */     super.removeAt(index);
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
/*     */   protected int index(double val)
/*     */   {
/* 206 */     byte[] states = this._states;
/* 207 */     double[] set = this._set;
/* 208 */     int length = states.length;
/* 209 */     int hash = HashFunctions.hash(val) & 0x7FFFFFFF;
/* 210 */     int index = hash % length;
/* 211 */     byte state = states[index];
/*     */     
/* 213 */     if (state == 0) {
/* 214 */       return -1;
/*     */     }
/* 216 */     if ((state == 1) && (set[index] == val)) {
/* 217 */       return index;
/*     */     }
/* 219 */     return indexRehashed(val, index, hash, state);
/*     */   }
/*     */   
/*     */   int indexRehashed(double key, int index, int hash, byte state)
/*     */   {
/* 224 */     int length = this._set.length;
/* 225 */     int probe = 1 + hash % (length - 2);
/* 226 */     int loopIndex = index;
/*     */     do
/*     */     {
/* 229 */       index -= probe;
/* 230 */       if (index < 0) {
/* 231 */         index += length;
/*     */       }
/* 233 */       state = this._states[index];
/*     */       
/* 235 */       if (state == 0) {
/* 236 */         return -1;
/*     */       }
/*     */       
/* 239 */       if ((key == this._set[index]) && (state != 2))
/* 240 */         return index;
/* 241 */     } while (index != loopIndex);
/*     */     
/* 243 */     return -1;
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
/*     */   protected int insertKey(double val)
/*     */   {
/* 257 */     int hash = HashFunctions.hash(val) & 0x7FFFFFFF;
/* 258 */     int index = hash % this._states.length;
/* 259 */     byte state = this._states[index];
/*     */     
/* 261 */     this.consumeFreeSlot = false;
/*     */     
/* 263 */     if (state == 0) {
/* 264 */       this.consumeFreeSlot = true;
/* 265 */       insertKeyAt(index, val);
/*     */       
/* 267 */       return index;
/*     */     }
/*     */     
/* 270 */     if ((state == 1) && (this._set[index] == val)) {
/* 271 */       return -index - 1;
/*     */     }
/*     */     
/*     */ 
/* 275 */     return insertKeyRehash(val, index, hash, state);
/*     */   }
/*     */   
/*     */   int insertKeyRehash(double val, int index, int hash, byte state)
/*     */   {
/* 280 */     int length = this._set.length;
/* 281 */     int probe = 1 + hash % (length - 2);
/* 282 */     int loopIndex = index;
/* 283 */     int firstRemoved = -1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 290 */       if ((state == 2) && (firstRemoved == -1)) {
/* 291 */         firstRemoved = index;
/*     */       }
/* 293 */       index -= probe;
/* 294 */       if (index < 0) {
/* 295 */         index += length;
/*     */       }
/* 297 */       state = this._states[index];
/*     */       
/*     */ 
/* 300 */       if (state == 0) {
/* 301 */         if (firstRemoved != -1) {
/* 302 */           insertKeyAt(firstRemoved, val);
/* 303 */           return firstRemoved;
/*     */         }
/* 305 */         this.consumeFreeSlot = true;
/* 306 */         insertKeyAt(index, val);
/* 307 */         return index;
/*     */       }
/*     */       
/*     */ 
/* 311 */       if ((state == 1) && (this._set[index] == val)) {
/* 312 */         return -index - 1;
/*     */       }
/*     */       
/*     */     }
/* 316 */     while (index != loopIndex);
/*     */     
/*     */ 
/*     */ 
/* 320 */     if (firstRemoved != -1) {
/* 321 */       insertKeyAt(firstRemoved, val);
/* 322 */       return firstRemoved;
/*     */     }
/*     */     
/*     */ 
/* 326 */     throw new IllegalStateException("No free or removed slots available. Key set full?!!");
/*     */   }
/*     */   
/*     */   void insertKeyAt(int index, double val) {
/* 330 */     this._set[index] = val;
/* 331 */     this._states[index] = 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\hash\TDoubleHash.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */