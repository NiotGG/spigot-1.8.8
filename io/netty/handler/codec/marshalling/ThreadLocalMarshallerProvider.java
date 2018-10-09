/*    */ package io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.util.concurrent.FastThreadLocal;
/*    */ import org.jboss.marshalling.Marshaller;
/*    */ import org.jboss.marshalling.MarshallerFactory;
/*    */ import org.jboss.marshalling.MarshallingConfiguration;
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
/*    */ public class ThreadLocalMarshallerProvider
/*    */   implements MarshallerProvider
/*    */ {
/* 31 */   private final FastThreadLocal<Marshaller> marshallers = new FastThreadLocal();
/*    */   
/*    */ 
/*    */   private final MarshallerFactory factory;
/*    */   
/*    */ 
/*    */   private final MarshallingConfiguration config;
/*    */   
/*    */ 
/*    */ 
/*    */   public ThreadLocalMarshallerProvider(MarshallerFactory paramMarshallerFactory, MarshallingConfiguration paramMarshallingConfiguration)
/*    */   {
/* 43 */     this.factory = paramMarshallerFactory;
/* 44 */     this.config = paramMarshallingConfiguration;
/*    */   }
/*    */   
/*    */   public Marshaller getMarshaller(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*    */   {
/* 49 */     Marshaller localMarshaller = (Marshaller)this.marshallers.get();
/* 50 */     if (localMarshaller == null) {
/* 51 */       localMarshaller = this.factory.createMarshaller(this.config);
/* 52 */       this.marshallers.set(localMarshaller);
/*    */     }
/* 54 */     return localMarshaller;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\marshalling\ThreadLocalMarshallerProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */