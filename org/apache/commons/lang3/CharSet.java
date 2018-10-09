/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
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
/*     */ public class CharSet
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5947847346149275958L;
/*  48 */   public static final CharSet EMPTY = new CharSet(new String[] { (String)null });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  54 */   public static final CharSet ASCII_ALPHA = new CharSet(new String[] { "a-zA-Z" });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  60 */   public static final CharSet ASCII_ALPHA_LOWER = new CharSet(new String[] { "a-z" });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  66 */   public static final CharSet ASCII_ALPHA_UPPER = new CharSet(new String[] { "A-Z" });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  72 */   public static final CharSet ASCII_NUMERIC = new CharSet(new String[] { "0-9" });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */   protected static final Map<String, CharSet> COMMON = Collections.synchronizedMap(new HashMap());
/*     */   
/*     */   static {
/*  82 */     COMMON.put(null, EMPTY);
/*  83 */     COMMON.put("", EMPTY);
/*  84 */     COMMON.put("a-zA-Z", ASCII_ALPHA);
/*  85 */     COMMON.put("A-Za-z", ASCII_ALPHA);
/*  86 */     COMMON.put("a-z", ASCII_ALPHA_LOWER);
/*  87 */     COMMON.put("A-Z", ASCII_ALPHA_UPPER);
/*  88 */     COMMON.put("0-9", ASCII_NUMERIC);
/*     */   }
/*     */   
/*     */ 
/*  92 */   private final Set<CharRange> set = Collections.synchronizedSet(new HashSet());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CharSet getInstance(String... paramVarArgs)
/*     */   {
/* 139 */     if (paramVarArgs == null) {
/* 140 */       return null;
/*     */     }
/* 142 */     if (paramVarArgs.length == 1) {
/* 143 */       CharSet localCharSet = (CharSet)COMMON.get(paramVarArgs[0]);
/* 144 */       if (localCharSet != null) {
/* 145 */         return localCharSet;
/*     */       }
/*     */     }
/* 148 */     return new CharSet(paramVarArgs);
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
/*     */   protected CharSet(String... paramVarArgs)
/*     */   {
/* 161 */     int i = paramVarArgs.length;
/* 162 */     for (int j = 0; j < i; j++) {
/* 163 */       add(paramVarArgs[j]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void add(String paramString)
/*     */   {
/* 174 */     if (paramString == null) {
/* 175 */       return;
/*     */     }
/*     */     
/* 178 */     int i = paramString.length();
/* 179 */     int j = 0;
/* 180 */     while (j < i) {
/* 181 */       int k = i - j;
/* 182 */       if ((k >= 4) && (paramString.charAt(j) == '^') && (paramString.charAt(j + 2) == '-'))
/*     */       {
/* 184 */         this.set.add(CharRange.isNotIn(paramString.charAt(j + 1), paramString.charAt(j + 3)));
/* 185 */         j += 4;
/* 186 */       } else if ((k >= 3) && (paramString.charAt(j + 1) == '-'))
/*     */       {
/* 188 */         this.set.add(CharRange.isIn(paramString.charAt(j), paramString.charAt(j + 2)));
/* 189 */         j += 3;
/* 190 */       } else if ((k >= 2) && (paramString.charAt(j) == '^'))
/*     */       {
/* 192 */         this.set.add(CharRange.isNot(paramString.charAt(j + 1)));
/* 193 */         j += 2;
/*     */       }
/*     */       else {
/* 196 */         this.set.add(CharRange.is(paramString.charAt(j)));
/* 197 */         j++;
/*     */       }
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
/*     */   CharRange[] getCharRanges()
/*     */   {
/* 212 */     return (CharRange[])this.set.toArray(new CharRange[this.set.size()]);
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
/* 224 */     for (CharRange localCharRange : this.set) {
/* 225 */       if (localCharRange.contains(paramChar)) {
/* 226 */         return true;
/*     */       }
/*     */     }
/* 229 */     return false;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 247 */     if (paramObject == this) {
/* 248 */       return true;
/*     */     }
/* 250 */     if (!(paramObject instanceof CharSet)) {
/* 251 */       return false;
/*     */     }
/* 253 */     CharSet localCharSet = (CharSet)paramObject;
/* 254 */     return this.set.equals(localCharSet.set);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 265 */     return 89 + this.set.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 275 */     return this.set.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\CharSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */