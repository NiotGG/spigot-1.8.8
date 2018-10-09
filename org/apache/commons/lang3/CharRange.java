/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
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
/*     */ final class CharRange
/*     */   implements Iterable<Character>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8270183163158333422L;
/*     */   private final char start;
/*     */   private final char end;
/*     */   private final boolean negated;
/*     */   private transient String iToString;
/*     */   
/*     */   private CharRange(char paramChar1, char paramChar2, boolean paramBoolean)
/*     */   {
/*  69 */     if (paramChar1 > paramChar2) {
/*  70 */       char c = paramChar1;
/*  71 */       paramChar1 = paramChar2;
/*  72 */       paramChar2 = c;
/*     */     }
/*     */     
/*  75 */     this.start = paramChar1;
/*  76 */     this.end = paramChar2;
/*  77 */     this.negated = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CharRange is(char paramChar)
/*     */   {
/*  89 */     return new CharRange(paramChar, paramChar, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CharRange isNot(char paramChar)
/*     */   {
/* 101 */     return new CharRange(paramChar, paramChar, true);
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
/*     */   public static CharRange isIn(char paramChar1, char paramChar2)
/*     */   {
/* 114 */     return new CharRange(paramChar1, paramChar2, false);
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
/*     */   public static CharRange isNotIn(char paramChar1, char paramChar2)
/*     */   {
/* 127 */     return new CharRange(paramChar1, paramChar2, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getStart()
/*     */   {
/* 138 */     return this.start;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char getEnd()
/*     */   {
/* 147 */     return this.end;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isNegated()
/*     */   {
/* 159 */     return this.negated;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(char paramChar)
/*     */   {
/* 171 */     return ((paramChar >= this.start) && (paramChar <= this.end)) != this.negated;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(CharRange paramCharRange)
/*     */   {
/* 183 */     if (paramCharRange == null) {
/* 184 */       throw new IllegalArgumentException("The Range must not be null");
/*     */     }
/* 186 */     if (this.negated) {
/* 187 */       if (paramCharRange.negated) {
/* 188 */         return (this.start >= paramCharRange.start) && (this.end <= paramCharRange.end);
/*     */       }
/* 190 */       return (paramCharRange.end < this.start) || (paramCharRange.start > this.end);
/*     */     }
/* 192 */     if (paramCharRange.negated) {
/* 193 */       return (this.start == 0) && (this.end == 65535);
/*     */     }
/* 195 */     return (this.start <= paramCharRange.start) && (this.end >= paramCharRange.end);
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 209 */     if (paramObject == this) {
/* 210 */       return true;
/*     */     }
/* 212 */     if (!(paramObject instanceof CharRange)) {
/* 213 */       return false;
/*     */     }
/* 215 */     CharRange localCharRange = (CharRange)paramObject;
/* 216 */     return (this.start == localCharRange.start) && (this.end == localCharRange.end) && (this.negated == localCharRange.negated);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 226 */     return 'S' + this.start + '\007' * this.end + (this.negated ? 1 : 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 236 */     if (this.iToString == null) {
/* 237 */       StringBuilder localStringBuilder = new StringBuilder(4);
/* 238 */       if (isNegated()) {
/* 239 */         localStringBuilder.append('^');
/*     */       }
/* 241 */       localStringBuilder.append(this.start);
/* 242 */       if (this.start != this.end) {
/* 243 */         localStringBuilder.append('-');
/* 244 */         localStringBuilder.append(this.end);
/*     */       }
/* 246 */       this.iToString = localStringBuilder.toString();
/*     */     }
/* 248 */     return this.iToString;
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
/*     */   public Iterator<Character> iterator()
/*     */   {
/* 262 */     return new CharacterIterator(this, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class CharacterIterator
/*     */     implements Iterator<Character>
/*     */   {
/*     */     private char current;
/*     */     
/*     */ 
/*     */     private final CharRange range;
/*     */     
/*     */ 
/*     */     private boolean hasNext;
/*     */     
/*     */ 
/*     */ 
/*     */     private CharacterIterator(CharRange paramCharRange)
/*     */     {
/* 282 */       this.range = paramCharRange;
/* 283 */       this.hasNext = true;
/*     */       
/* 285 */       if (this.range.negated) {
/* 286 */         if (this.range.start == 0) {
/* 287 */           if (this.range.end == 65535)
/*     */           {
/* 289 */             this.hasNext = false;
/*     */           } else {
/* 291 */             this.current = ((char)(this.range.end + '\001'));
/*     */           }
/*     */         } else {
/* 294 */           this.current = '\000';
/*     */         }
/*     */       } else {
/* 297 */         this.current = this.range.start;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private void prepareNext()
/*     */     {
/* 305 */       if (this.range.negated) {
/* 306 */         if (this.current == 65535) {
/* 307 */           this.hasNext = false;
/* 308 */         } else if (this.current + '\001' == this.range.start) {
/* 309 */           if (this.range.end == 65535) {
/* 310 */             this.hasNext = false;
/*     */           } else {
/* 312 */             this.current = ((char)(this.range.end + '\001'));
/*     */           }
/*     */         } else {
/* 315 */           this.current = ((char)(this.current + '\001'));
/*     */         }
/* 317 */       } else if (this.current < this.range.end) {
/* 318 */         this.current = ((char)(this.current + '\001'));
/*     */       } else {
/* 320 */         this.hasNext = false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean hasNext()
/*     */     {
/* 331 */       return this.hasNext;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Character next()
/*     */     {
/* 341 */       if (!this.hasNext) {
/* 342 */         throw new NoSuchElementException();
/*     */       }
/* 344 */       char c = this.current;
/* 345 */       prepareNext();
/* 346 */       return Character.valueOf(c);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void remove()
/*     */     {
/* 357 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\CharRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */