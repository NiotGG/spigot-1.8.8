/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import com.jcraft.jzlib.Deflater;
/*    */ import com.jcraft.jzlib.Inflater;
/*    */ import com.jcraft.jzlib.JZlib;
/*    */ import com.jcraft.jzlib.JZlib.WrapperType;
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
/*    */ final class ZlibUtil
/*    */ {
/*    */   static void fail(Inflater paramInflater, String paramString, int paramInt)
/*    */   {
/* 28 */     throw inflaterException(paramInflater, paramString, paramInt);
/*    */   }
/*    */   
/*    */   static void fail(Deflater paramDeflater, String paramString, int paramInt) {
/* 32 */     throw deflaterException(paramDeflater, paramString, paramInt);
/*    */   }
/*    */   
/*    */   static DecompressionException inflaterException(Inflater paramInflater, String paramString, int paramInt) {
/* 36 */     return new DecompressionException(paramString + " (" + paramInt + ')' + (paramInflater.msg != null ? ": " + paramInflater.msg : ""));
/*    */   }
/*    */   
/*    */   static CompressionException deflaterException(Deflater paramDeflater, String paramString, int paramInt) {
/* 40 */     return new CompressionException(paramString + " (" + paramInt + ')' + (paramDeflater.msg != null ? ": " + paramDeflater.msg : ""));
/*    */   }
/*    */   
/*    */   static JZlib.WrapperType convertWrapperType(ZlibWrapper paramZlibWrapper) {
/*    */     JZlib.WrapperType localWrapperType;
/* 45 */     switch (paramZlibWrapper) {
/*    */     case NONE: 
/* 47 */       localWrapperType = JZlib.W_NONE;
/* 48 */       break;
/*    */     case ZLIB: 
/* 50 */       localWrapperType = JZlib.W_ZLIB;
/* 51 */       break;
/*    */     case GZIP: 
/* 53 */       localWrapperType = JZlib.W_GZIP;
/* 54 */       break;
/*    */     case ZLIB_OR_NONE: 
/* 56 */       localWrapperType = JZlib.W_ANY;
/* 57 */       break;
/*    */     default: 
/* 59 */       throw new Error();
/*    */     }
/* 61 */     return localWrapperType;
/*    */   }
/*    */   
/*    */   static int wrapperOverhead(ZlibWrapper paramZlibWrapper) {
/*    */     int i;
/* 66 */     switch (paramZlibWrapper) {
/*    */     case NONE: 
/* 68 */       i = 0;
/* 69 */       break;
/*    */     case ZLIB: 
/*    */     case ZLIB_OR_NONE: 
/* 72 */       i = 2;
/* 73 */       break;
/*    */     case GZIP: 
/* 75 */       i = 10;
/* 76 */       break;
/*    */     default: 
/* 78 */       throw new Error();
/*    */     }
/* 80 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\compression\ZlibUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */