/*    */ package io.netty.channel.group;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelException;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map.Entry;
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
/*    */ public class ChannelGroupException
/*    */   extends ChannelException
/*    */   implements Iterable<Map.Entry<Channel, Throwable>>
/*    */ {
/*    */   private static final long serialVersionUID = -4093064295562629453L;
/*    */   private final Collection<Map.Entry<Channel, Throwable>> failed;
/*    */   
/*    */   public ChannelGroupException(Collection<Map.Entry<Channel, Throwable>> paramCollection)
/*    */   {
/* 35 */     if (paramCollection == null) {
/* 36 */       throw new NullPointerException("causes");
/*    */     }
/* 38 */     if (paramCollection.isEmpty()) {
/* 39 */       throw new IllegalArgumentException("causes must be non empty");
/*    */     }
/* 41 */     this.failed = Collections.unmodifiableCollection(paramCollection);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Iterator<Map.Entry<Channel, Throwable>> iterator()
/*    */   {
/* 50 */     return this.failed.iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\group\ChannelGroupException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */