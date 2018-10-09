/*    */ package io.netty.channel.epoll;
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
/*    */ public final class EpollChannelOption<T>
/*    */   extends ChannelOption<T>
/*    */ {
/* 22 */   public static final ChannelOption<Boolean> TCP_CORK = valueOf("TCP_CORK");
/* 23 */   public static final ChannelOption<Integer> TCP_KEEPIDLE = valueOf("TCP_KEEPIDLE");
/* 24 */   public static final ChannelOption<Integer> TCP_KEEPINTVL = valueOf("TCP_KEEPINTVL");
/* 25 */   public static final ChannelOption<Integer> TCP_KEEPCNT = valueOf("TCP_KEEPCNT");
/*    */   
/* 27 */   public static final ChannelOption<Boolean> SO_REUSEPORT = valueOf("SO_REUSEPORT");
/*    */   
/*    */   private EpollChannelOption(String paramString)
/*    */   {
/* 31 */     super(paramString);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\EpollChannelOption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */