/*    */ package io.netty.util;
/*    */ 
/*    */ import io.netty.util.internal.PlatformDependent;
/*    */ import java.util.concurrent.ConcurrentMap;
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
/*    */ public final class AttributeKey<T>
/*    */   extends UniqueName
/*    */ {
/* 32 */   private static final ConcurrentMap<String, Boolean> names = ;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static <T> AttributeKey<T> valueOf(String paramString)
/*    */   {
/* 39 */     return new AttributeKey(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public AttributeKey(String paramString)
/*    */   {
/* 47 */     super(names, paramString, new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\AttributeKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */