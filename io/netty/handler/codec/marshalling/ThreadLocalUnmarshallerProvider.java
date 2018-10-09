/*    */ package io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.util.concurrent.FastThreadLocal;
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
/*    */ public class ThreadLocalUnmarshallerProvider
/*    */   implements UnmarshallerProvider
/*    */ {
/* 31 */   private final FastThreadLocal<Unmarshaller> unmarshallers = new FastThreadLocal();
/*    */   
/*    */ 
/*    */   private final MarshallerFactory factory;
/*    */   
/*    */ 
/*    */   private final MarshallingConfiguration config;
/*    */   
/*    */ 
/*    */ 
/*    */   public ThreadLocalUnmarshallerProvider(MarshallerFactory paramMarshallerFactory, MarshallingConfiguration paramMarshallingConfiguration)
/*    */   {
/* 43 */     this.factory = paramMarshallerFactory;
/* 44 */     this.config = paramMarshallingConfiguration;
/*    */   }
/*    */   
/*    */   public Unmarshaller getUnmarshaller(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*    */   {
/* 49 */     Unmarshaller localUnmarshaller = (Unmarshaller)this.unmarshallers.get();
/* 50 */     if (localUnmarshaller == null) {
/* 51 */       localUnmarshaller = this.factory.createUnmarshaller(this.config);
/* 52 */       this.unmarshallers.set(localUnmarshaller);
/*    */     }
/* 54 */     return localUnmarshaller;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\marshalling\ThreadLocalUnmarshallerProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */