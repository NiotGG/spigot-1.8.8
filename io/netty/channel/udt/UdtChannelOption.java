/*    */ package io.netty.channel.udt;
/*    */ 
/*    */ import io.netty.channel.ChannelOption;
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
/*    */ public final class UdtChannelOption<T>
/*    */   extends ChannelOption<T>
/*    */ {
/* 29 */   public static final UdtChannelOption<Integer> PROTOCOL_RECEIVE_BUFFER_SIZE = new UdtChannelOption("PROTOCOL_RECEIVE_BUFFER_SIZE");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 35 */   public static final UdtChannelOption<Integer> PROTOCOL_SEND_BUFFER_SIZE = new UdtChannelOption("PROTOCOL_SEND_BUFFER_SIZE");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 41 */   public static final UdtChannelOption<Integer> SYSTEM_RECEIVE_BUFFER_SIZE = new UdtChannelOption("SYSTEM_RECEIVE_BUFFER_SIZE");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 47 */   public static final UdtChannelOption<Integer> SYSTEM_SEND_BUFFER_SIZE = new UdtChannelOption("SYSTEM_SEND_BUFFER_SIZE");
/*    */   
/*    */ 
/*    */   private UdtChannelOption(String paramString)
/*    */   {
/* 52 */     super(paramString);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\udt\UdtChannelOption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */