/*    */ package io.netty.channel.rxtx;
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
/*    */ public final class RxtxChannelOption<T>
/*    */   extends ChannelOption<T>
/*    */ {
/* 27 */   public static final RxtxChannelOption<Integer> BAUD_RATE = new RxtxChannelOption("BAUD_RATE");
/*    */   
/*    */ 
/* 30 */   public static final RxtxChannelOption<Boolean> DTR = new RxtxChannelOption("DTR");
/*    */   
/*    */ 
/* 33 */   public static final RxtxChannelOption<Boolean> RTS = new RxtxChannelOption("RTS");
/*    */   
/*    */ 
/* 36 */   public static final RxtxChannelOption<RxtxChannelConfig.Stopbits> STOP_BITS = new RxtxChannelOption("STOP_BITS");
/*    */   
/*    */ 
/* 39 */   public static final RxtxChannelOption<RxtxChannelConfig.Databits> DATA_BITS = new RxtxChannelOption("DATA_BITS");
/*    */   
/*    */ 
/* 42 */   public static final RxtxChannelOption<RxtxChannelConfig.Paritybit> PARITY_BIT = new RxtxChannelOption("PARITY_BIT");
/*    */   
/*    */ 
/* 45 */   public static final RxtxChannelOption<Integer> WAIT_TIME = new RxtxChannelOption("WAIT_TIME");
/*    */   
/*    */ 
/* 48 */   public static final RxtxChannelOption<Integer> READ_TIMEOUT = new RxtxChannelOption("READ_TIMEOUT");
/*    */   
/*    */ 
/*    */   private RxtxChannelOption(String paramString)
/*    */   {
/* 53 */     super(paramString);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\rxtx\RxtxChannelOption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */