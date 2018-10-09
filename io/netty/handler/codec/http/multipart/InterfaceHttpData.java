/*    */ package io.netty.handler.codec.http.multipart;
/*    */ 
/*    */ import io.netty.util.ReferenceCounted;
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
/*    */ public abstract interface InterfaceHttpData
/*    */   extends Comparable<InterfaceHttpData>, ReferenceCounted
/*    */ {
/*    */   public abstract String getName();
/*    */   
/*    */   public abstract HttpDataType getHttpDataType();
/*    */   
/*    */   public static enum HttpDataType
/*    */   {
/* 25 */     Attribute,  FileUpload,  InternalAttribute;
/*    */     
/*    */     private HttpDataType() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\InterfaceHttpData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */