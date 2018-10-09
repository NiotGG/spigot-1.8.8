/*    */ package io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.util.Attribute;
/*    */ import io.netty.util.AttributeKey;
/*    */ import org.jboss.marshalling.MarshallerFactory;
/*    */ import org.jboss.marshalling.MarshallingConfiguration;
/*    */ import org.jboss.marshalling.Unmarshaller;
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
/*    */ public class ContextBoundUnmarshallerProvider
/*    */   extends DefaultUnmarshallerProvider
/*    */ {
/* 37 */   private static final AttributeKey<Unmarshaller> UNMARSHALLER = AttributeKey.valueOf(ContextBoundUnmarshallerProvider.class.getName() + ".UNMARSHALLER");
/*    */   
/*    */   public ContextBoundUnmarshallerProvider(MarshallerFactory paramMarshallerFactory, MarshallingConfiguration paramMarshallingConfiguration)
/*    */   {
/* 41 */     super(paramMarshallerFactory, paramMarshallingConfiguration);
/*    */   }
/*    */   
/*    */   public Unmarshaller getUnmarshaller(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*    */   {
/* 46 */     Attribute localAttribute = paramChannelHandlerContext.attr(UNMARSHALLER);
/* 47 */     Unmarshaller localUnmarshaller = (Unmarshaller)localAttribute.get();
/* 48 */     if (localUnmarshaller == null) {
/* 49 */       localUnmarshaller = super.getUnmarshaller(paramChannelHandlerContext);
/* 50 */       localAttribute.set(localUnmarshaller);
/*    */     }
/* 52 */     return localUnmarshaller;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\marshalling\ContextBoundUnmarshallerProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */