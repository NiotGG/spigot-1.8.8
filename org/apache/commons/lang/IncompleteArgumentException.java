/*    */ package org.apache.commons.lang;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ public class IncompleteArgumentException
/*    */   extends IllegalArgumentException
/*    */ {
/*    */   private static final long serialVersionUID = 4954193403612068178L;
/*    */   
/*    */   public IncompleteArgumentException(String argName)
/*    */   {
/* 63 */     super(argName + " is incomplete.");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IncompleteArgumentException(String argName, String[] items)
/*    */   {
/* 73 */     super(argName + " is missing the following items: " + safeArrayToString(items));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private static final String safeArrayToString(Object[] array)
/*    */   {
/* 86 */     return array == null ? null : Arrays.asList(array).toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang\IncompleteArgumentException.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */