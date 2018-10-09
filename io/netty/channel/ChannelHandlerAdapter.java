/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.internal.InternalThreadLocalMap;
/*    */ import java.util.Map;
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
/*    */ public abstract class ChannelHandlerAdapter
/*    */   implements ChannelHandler
/*    */ {
/*    */   boolean added;
/*    */   
/*    */   public boolean isSharable()
/*    */   {
/* 45 */     Class localClass = getClass();
/* 46 */     Map localMap = InternalThreadLocalMap.get().handlerSharableCache();
/* 47 */     Boolean localBoolean = (Boolean)localMap.get(localClass);
/* 48 */     if (localBoolean == null) {
/* 49 */       localBoolean = Boolean.valueOf(localClass.isAnnotationPresent(ChannelHandler.Sharable.class));
/* 50 */       localMap.put(localClass, localBoolean);
/*    */     }
/* 52 */     return localBoolean.booleanValue();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext)
/*    */     throws Exception
/*    */   {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext)
/*    */     throws Exception
/*    */   {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable)
/*    */     throws Exception
/*    */   {
/* 79 */     paramChannelHandlerContext.fireExceptionCaught(paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ChannelHandlerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */