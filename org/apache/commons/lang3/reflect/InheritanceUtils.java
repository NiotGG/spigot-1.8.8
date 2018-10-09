/*    */ package org.apache.commons.lang3.reflect;
/*    */ 
/*    */ import org.apache.commons.lang3.BooleanUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InheritanceUtils
/*    */ {
/*    */   public static int distance(Class<?> paramClass1, Class<?> paramClass2)
/*    */   {
/* 50 */     if ((paramClass1 == null) || (paramClass2 == null)) {
/* 51 */       return -1;
/*    */     }
/*    */     
/* 54 */     if (paramClass1.equals(paramClass2)) {
/* 55 */       return 0;
/*    */     }
/*    */     
/* 58 */     Class localClass = paramClass1.getSuperclass();
/* 59 */     int i = BooleanUtils.toInteger(paramClass2.equals(localClass));
/*    */     
/* 61 */     if (i == 1) {
/* 62 */       return i;
/*    */     }
/* 64 */     i += distance(localClass, paramClass2);
/* 65 */     return i > 0 ? i + 1 : -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\reflect\InheritanceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */