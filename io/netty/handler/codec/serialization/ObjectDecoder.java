/*    */ package io.netty.handler.codec.serialization;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufInputStream;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
/*    */ import java.io.ObjectInputStream;
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
/*    */ public class ObjectDecoder
/*    */   extends LengthFieldBasedFrameDecoder
/*    */ {
/*    */   private final ClassResolver classResolver;
/*    */   
/*    */   public ObjectDecoder(ClassResolver paramClassResolver)
/*    */   {
/* 49 */     this(1048576, paramClassResolver);
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
/*    */ 
/*    */   public ObjectDecoder(int paramInt, ClassResolver paramClassResolver)
/*    */   {
/* 63 */     super(paramInt, 0, 4, 0, 4);
/* 64 */     this.classResolver = paramClassResolver;
/*    */   }
/*    */   
/*    */   protected Object decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf) throws Exception
/*    */   {
/* 69 */     ByteBuf localByteBuf = (ByteBuf)super.decode(paramChannelHandlerContext, paramByteBuf);
/* 70 */     if (localByteBuf == null) {
/* 71 */       return null;
/*    */     }
/*    */     
/* 74 */     CompactObjectInputStream localCompactObjectInputStream = new CompactObjectInputStream(new ByteBufInputStream(localByteBuf), this.classResolver);
/* 75 */     Object localObject = localCompactObjectInputStream.readObject();
/* 76 */     localCompactObjectInputStream.close();
/* 77 */     return localObject;
/*    */   }
/*    */   
/*    */   protected ByteBuf extractFrame(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*    */   {
/* 82 */     return paramByteBuf.slice(paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\serialization\ObjectDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */