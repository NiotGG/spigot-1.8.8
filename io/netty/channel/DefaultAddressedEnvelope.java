/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.net.SocketAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultAddressedEnvelope<M, A extends SocketAddress>
/*     */   implements AddressedEnvelope<M, A>
/*     */ {
/*     */   private final M message;
/*     */   private final A sender;
/*     */   private final A recipient;
/*     */   
/*     */   public DefaultAddressedEnvelope(M paramM, A paramA1, A paramA2)
/*     */   {
/*  42 */     if (paramM == null) {
/*  43 */       throw new NullPointerException("message");
/*     */     }
/*     */     
/*  46 */     this.message = paramM;
/*  47 */     this.sender = paramA2;
/*  48 */     this.recipient = paramA1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultAddressedEnvelope(M paramM, A paramA)
/*     */   {
/*  56 */     this(paramM, paramA, null);
/*     */   }
/*     */   
/*     */   public M content()
/*     */   {
/*  61 */     return (M)this.message;
/*     */   }
/*     */   
/*     */   public A sender()
/*     */   {
/*  66 */     return this.sender;
/*     */   }
/*     */   
/*     */   public A recipient()
/*     */   {
/*  71 */     return this.recipient;
/*     */   }
/*     */   
/*     */   public int refCnt()
/*     */   {
/*  76 */     if ((this.message instanceof ReferenceCounted)) {
/*  77 */       return ((ReferenceCounted)this.message).refCnt();
/*     */     }
/*  79 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */   public AddressedEnvelope<M, A> retain()
/*     */   {
/*  85 */     ReferenceCountUtil.retain(this.message);
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public AddressedEnvelope<M, A> retain(int paramInt)
/*     */   {
/*  91 */     ReferenceCountUtil.retain(this.message, paramInt);
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   public boolean release()
/*     */   {
/*  97 */     return ReferenceCountUtil.release(this.message);
/*     */   }
/*     */   
/*     */   public boolean release(int paramInt)
/*     */   {
/* 102 */     return ReferenceCountUtil.release(this.message, paramInt);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 107 */     if (this.sender != null) {
/* 108 */       return StringUtil.simpleClassName(this) + '(' + this.sender + " => " + this.recipient + ", " + this.message + ')';
/*     */     }
/*     */     
/* 111 */     return StringUtil.simpleClassName(this) + "(=> " + this.recipient + ", " + this.message + ')';
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\DefaultAddressedEnvelope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */