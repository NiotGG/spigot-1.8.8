/*    */ package org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Sort
/*    */ {
/*  9 */   private static final CompareFunc comparableCompareFunc = new CompareFunc() {
/* 10 */     public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2) { return ((Sort.Comparable)paramAnonymousObject1).compareTo(paramAnonymousObject2); }
/*    */   };
/*    */   
/* 13 */   public static void sort(Comparable[] paramArrayOfComparable) { sort(paramArrayOfComparable, comparableCompareFunc); }
/* 14 */   public static void sort(Object[] paramArrayOfObject, CompareFunc paramCompareFunc) { sort(paramArrayOfObject, paramCompareFunc, 0, paramArrayOfObject.length - 1); }
/*    */   
/*    */   private static void sort(Object[] paramArrayOfObject, CompareFunc paramCompareFunc, int paramInt1, int paramInt2)
/*    */   {
/* 18 */     if (paramInt1 >= paramInt2) return;
/* 19 */     if (paramInt2 - paramInt1 <= 6) {
/* 20 */       for (int i = paramInt1 + 1; i <= paramInt2; i++) {
/* 21 */         localObject2 = paramArrayOfObject[i];
/*    */         
/* 23 */         for (j = i - 1; j >= paramInt1; j--) {
/* 24 */           if (paramCompareFunc.compare(paramArrayOfObject[j], localObject2) <= 0) break;
/* 25 */           paramArrayOfObject[(j + 1)] = paramArrayOfObject[j];
/*    */         }
/* 27 */         paramArrayOfObject[(j + 1)] = localObject2;
/*    */       }
/* 29 */       return;
/*    */     }
/*    */     
/* 32 */     Object localObject1 = paramArrayOfObject[paramInt2];
/* 33 */     int j = paramInt1 - 1;
/* 34 */     break label105; int k = paramInt2;
/*    */     label105:
/*    */     do {
/* 37 */       while ((j < k) && (paramCompareFunc.compare(paramArrayOfObject[(++j)], localObject1) < 0)) {}
/* 38 */       while ((k > j) && (paramCompareFunc.compare(paramArrayOfObject[(--k)], localObject1) > 0)) {}
/* 39 */       localObject2 = paramArrayOfObject[j];paramArrayOfObject[j] = paramArrayOfObject[k];paramArrayOfObject[k] = localObject2;
/* 40 */     } while (j < k);
/*    */     
/* 42 */     Object localObject2 = paramArrayOfObject[j];paramArrayOfObject[j] = paramArrayOfObject[paramInt2];paramArrayOfObject[paramInt2] = localObject2;
/*    */     
/* 44 */     sort(paramArrayOfObject, paramCompareFunc, paramInt1, j - 1);
/* 45 */     sort(paramArrayOfObject, paramCompareFunc, j + 1, paramInt2);
/*    */   }
/*    */   
/*    */   public static abstract interface CompareFunc
/*    */   {
/*    */     public abstract int compare(Object paramObject1, Object paramObject2);
/*    */   }
/*    */   
/*    */   public static abstract interface Comparable
/*    */   {
/*    */     public abstract int compareTo(Object paramObject);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\org\ibex\nestedvm\util\Sort.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */