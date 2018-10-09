/*     */ package io.netty.handler.codec.serialization;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufOutputStream;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.AttributeKey;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
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
/*     */ public class CompatibleObjectEncoder
/*     */   extends MessageToByteEncoder<Serializable>
/*     */ {
/*  39 */   private static final AttributeKey<ObjectOutputStream> OOS = AttributeKey.valueOf(CompatibleObjectEncoder.class.getName() + ".OOS");
/*     */   
/*     */ 
/*     */   private final int resetInterval;
/*     */   
/*     */   private int writtenObjects;
/*     */   
/*     */ 
/*     */   public CompatibleObjectEncoder()
/*     */   {
/*  49 */     this(16);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CompatibleObjectEncoder(int paramInt)
/*     */   {
/*  62 */     if (paramInt < 0) {
/*  63 */       throw new IllegalArgumentException("resetInterval: " + paramInt);
/*     */     }
/*     */     
/*  66 */     this.resetInterval = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ObjectOutputStream newObjectOutputStream(OutputStream paramOutputStream)
/*     */     throws Exception
/*     */   {
/*  75 */     return new ObjectOutputStream(paramOutputStream);
/*     */   }
/*     */   
/*     */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, Serializable paramSerializable, ByteBuf paramByteBuf) throws Exception
/*     */   {
/*  80 */     Attribute localAttribute = paramChannelHandlerContext.attr(OOS);
/*  81 */     Object localObject1 = (ObjectOutputStream)localAttribute.get();
/*  82 */     if (localObject1 == null) {
/*  83 */       localObject1 = newObjectOutputStream(new ByteBufOutputStream(paramByteBuf));
/*  84 */       ObjectOutputStream localObjectOutputStream = (ObjectOutputStream)localAttribute.setIfAbsent(localObject1);
/*  85 */       if (localObjectOutputStream != null) {
/*  86 */         localObject1 = localObjectOutputStream;
/*     */       }
/*     */     }
/*     */     
/*  90 */     synchronized (localObject1) {
/*  91 */       if (this.resetInterval != 0)
/*     */       {
/*  93 */         this.writtenObjects += 1;
/*  94 */         if (this.writtenObjects % this.resetInterval == 0) {
/*  95 */           ((ObjectOutputStream)localObject1).reset();
/*     */         }
/*     */       }
/*     */       
/*  99 */       ((ObjectOutputStream)localObject1).writeObject(paramSerializable);
/* 100 */       ((ObjectOutputStream)localObject1).flush();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\serialization\CompatibleObjectEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */