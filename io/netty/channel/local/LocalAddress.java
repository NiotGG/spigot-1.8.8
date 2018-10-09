/*    */ package io.netty.channel.local;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import java.net.SocketAddress;
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
/*    */ public final class LocalAddress
/*    */   extends SocketAddress
/*    */   implements Comparable<LocalAddress>
/*    */ {
/*    */   private static final long serialVersionUID = 4644331421130916435L;
/* 30 */   public static final LocalAddress ANY = new LocalAddress("ANY");
/*    */   
/*    */ 
/*    */   private final String id;
/*    */   
/*    */ 
/*    */   private final String strVal;
/*    */   
/*    */ 
/*    */   LocalAddress(Channel paramChannel)
/*    */   {
/* 41 */     StringBuilder localStringBuilder = new StringBuilder(16);
/* 42 */     localStringBuilder.append("local:E");
/* 43 */     localStringBuilder.append(Long.toHexString(paramChannel.hashCode() & 0xFFFFFFFF | 0x100000000));
/* 44 */     localStringBuilder.setCharAt(7, ':');
/* 45 */     this.id = localStringBuilder.substring(6);
/* 46 */     this.strVal = localStringBuilder.toString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public LocalAddress(String paramString)
/*    */   {
/* 53 */     if (paramString == null) {
/* 54 */       throw new NullPointerException("id");
/*    */     }
/* 56 */     paramString = paramString.trim().toLowerCase();
/* 57 */     if (paramString.isEmpty()) {
/* 58 */       throw new IllegalArgumentException("empty id");
/*    */     }
/* 60 */     this.id = paramString;
/* 61 */     this.strVal = ("local:" + paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String id()
/*    */   {
/* 68 */     return this.id;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 73 */     return this.id.hashCode();
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 78 */     if (!(paramObject instanceof LocalAddress)) {
/* 79 */       return false;
/*    */     }
/*    */     
/* 82 */     return this.id.equals(((LocalAddress)paramObject).id);
/*    */   }
/*    */   
/*    */   public int compareTo(LocalAddress paramLocalAddress)
/*    */   {
/* 87 */     return this.id.compareTo(paramLocalAddress.id);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 92 */     return this.strVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\local\LocalAddress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */