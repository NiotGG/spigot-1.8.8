/*    */ package io.netty.handler.codec;
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
/*    */ public class UnsupportedMessageTypeException
/*    */   extends CodecException
/*    */ {
/*    */   private static final long serialVersionUID = 2799598826487038726L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public UnsupportedMessageTypeException(Object paramObject, Class<?>... paramVarArgs)
/*    */   {
/* 27 */     super(message(paramObject == null ? "null" : paramObject.getClass().getName(), paramVarArgs));
/*    */   }
/*    */   
/*    */   public UnsupportedMessageTypeException() {}
/*    */   
/*    */   public UnsupportedMessageTypeException(String paramString, Throwable paramThrowable)
/*    */   {
/* 34 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */   public UnsupportedMessageTypeException(String paramString) {
/* 38 */     super(paramString);
/*    */   }
/*    */   
/*    */   public UnsupportedMessageTypeException(Throwable paramThrowable) {
/* 42 */     super(paramThrowable);
/*    */   }
/*    */   
/*    */   private static String message(String paramString, Class<?>... paramVarArgs)
/*    */   {
/* 47 */     StringBuilder localStringBuilder = new StringBuilder(paramString);
/*    */     
/* 49 */     if ((paramVarArgs != null) && (paramVarArgs.length > 0)) {
/* 50 */       localStringBuilder.append(" (expected: ").append(paramVarArgs[0].getName());
/* 51 */       for (int i = 1; i < paramVarArgs.length; i++) {
/* 52 */         Class<?> localClass = paramVarArgs[i];
/* 53 */         if (localClass == null) {
/*    */           break;
/*    */         }
/* 56 */         localStringBuilder.append(", ").append(localClass.getName());
/*    */       }
/* 58 */       localStringBuilder.append(')');
/*    */     }
/*    */     
/* 61 */     return localStringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\UnsupportedMessageTypeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */