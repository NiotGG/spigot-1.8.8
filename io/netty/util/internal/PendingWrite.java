/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import io.netty.util.Recycler;
/*    */ import io.netty.util.Recycler.Handle;
/*    */ import io.netty.util.ReferenceCountUtil;
/*    */ import io.netty.util.concurrent.Promise;
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
/*    */ public final class PendingWrite
/*    */ {
/* 26 */   private static final Recycler<PendingWrite> RECYCLER = new Recycler()
/*    */   {
/*    */     protected PendingWrite newObject(Recycler.Handle paramAnonymousHandle) {
/* 29 */       return new PendingWrite(paramAnonymousHandle, null);
/*    */     }
/*    */   };
/*    */   private final Recycler.Handle handle;
/*    */   private Object msg;
/*    */   private Promise<Void> promise;
/*    */   
/*    */   public static PendingWrite newInstance(Object paramObject, Promise<Void> paramPromise) {
/* 37 */     PendingWrite localPendingWrite = (PendingWrite)RECYCLER.get();
/* 38 */     localPendingWrite.msg = paramObject;
/* 39 */     localPendingWrite.promise = paramPromise;
/* 40 */     return localPendingWrite;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private PendingWrite(Recycler.Handle paramHandle)
/*    */   {
/* 48 */     this.handle = paramHandle;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean recycle()
/*    */   {
/* 55 */     this.msg = null;
/* 56 */     this.promise = null;
/* 57 */     return RECYCLER.recycle(this, this.handle);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean failAndRecycle(Throwable paramThrowable)
/*    */   {
/* 64 */     ReferenceCountUtil.release(this.msg);
/* 65 */     if (this.promise != null) {
/* 66 */       this.promise.setFailure(paramThrowable);
/*    */     }
/* 68 */     return recycle();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean successAndRecycle()
/*    */   {
/* 75 */     if (this.promise != null) {
/* 76 */       this.promise.setSuccess(null);
/*    */     }
/* 78 */     return recycle();
/*    */   }
/*    */   
/*    */   public Object msg() {
/* 82 */     return this.msg;
/*    */   }
/*    */   
/*    */   public Promise<Void> promise() {
/* 86 */     return this.promise;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Promise<Void> recycleAndGet()
/*    */   {
/* 93 */     Promise localPromise = this.promise;
/* 94 */     recycle();
/* 95 */     return localPromise;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\PendingWrite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */