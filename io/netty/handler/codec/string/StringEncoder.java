/*    */ package io.netty.handler.codec.string;
/*    */ 
/*    */ import io.netty.buffer.ByteBufUtil;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageEncoder;
/*    */ import java.nio.CharBuffer;
/*    */ import java.nio.charset.Charset;
/*    */ import java.util.List;
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
/*    */ @ChannelHandler.Sharable
/*    */ public class StringEncoder
/*    */   extends MessageToMessageEncoder<CharSequence>
/*    */ {
/*    */   private final Charset charset;
/*    */   
/*    */   public StringEncoder()
/*    */   {
/* 61 */     this(Charset.defaultCharset());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public StringEncoder(Charset paramCharset)
/*    */   {
/* 68 */     if (paramCharset == null) {
/* 69 */       throw new NullPointerException("charset");
/*    */     }
/* 71 */     this.charset = paramCharset;
/*    */   }
/*    */   
/*    */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, CharSequence paramCharSequence, List<Object> paramList) throws Exception
/*    */   {
/* 76 */     if (paramCharSequence.length() == 0) {
/* 77 */       return;
/*    */     }
/*    */     
/* 80 */     paramList.add(ByteBufUtil.encodeString(paramChannelHandlerContext.alloc(), CharBuffer.wrap(paramCharSequence), this.charset));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\string\StringEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */