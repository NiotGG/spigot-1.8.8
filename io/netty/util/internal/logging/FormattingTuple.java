/*    */ package io.netty.util.internal.logging;
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
/*    */ class FormattingTuple
/*    */ {
/* 47 */   static final FormattingTuple NULL = new FormattingTuple(null);
/*    */   private final String message;
/*    */   private final Throwable throwable;
/*    */   private final Object[] argArray;
/*    */   
/*    */   FormattingTuple(String paramString)
/*    */   {
/* 54 */     this(paramString, null, null);
/*    */   }
/*    */   
/*    */   FormattingTuple(String paramString, Object[] paramArrayOfObject, Throwable paramThrowable) {
/* 58 */     this.message = paramString;
/* 59 */     this.throwable = paramThrowable;
/* 60 */     if (paramThrowable == null) {
/* 61 */       this.argArray = paramArrayOfObject;
/*    */     } else {
/* 63 */       this.argArray = trimmedCopy(paramArrayOfObject);
/*    */     }
/*    */   }
/*    */   
/*    */   static Object[] trimmedCopy(Object[] paramArrayOfObject) {
/* 68 */     if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0)) {
/* 69 */       throw new IllegalStateException("non-sensical empty or null argument array");
/*    */     }
/* 71 */     int i = paramArrayOfObject.length - 1;
/* 72 */     Object[] arrayOfObject = new Object[i];
/* 73 */     System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, i);
/* 74 */     return arrayOfObject;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 78 */     return this.message;
/*    */   }
/*    */   
/*    */   public Object[] getArgArray() {
/* 82 */     return this.argArray;
/*    */   }
/*    */   
/*    */   public Throwable getThrowable() {
/* 86 */     return this.throwable;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\logging\FormattingTuple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */