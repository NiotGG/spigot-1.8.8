/*     */ package io.netty.channel.group;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ServerChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ChannelMatchers
/*     */ {
/*  26 */   private static final ChannelMatcher ALL_MATCHER = new ChannelMatcher()
/*     */   {
/*     */     public boolean matches(Channel paramAnonymousChannel) {
/*  29 */       return true;
/*     */     }
/*     */   };
/*     */   
/*  33 */   private static final ChannelMatcher SERVER_CHANNEL_MATCHER = isInstanceOf(ServerChannel.class);
/*  34 */   private static final ChannelMatcher NON_SERVER_CHANNEL_MATCHER = isNotInstanceOf(ServerChannel.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ChannelMatcher all()
/*     */   {
/*  44 */     return ALL_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ChannelMatcher isNot(Channel paramChannel)
/*     */   {
/*  51 */     return invert(is(paramChannel));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ChannelMatcher is(Channel paramChannel)
/*     */   {
/*  58 */     return new InstanceMatcher(paramChannel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ChannelMatcher isInstanceOf(Class<? extends Channel> paramClass)
/*     */   {
/*  66 */     return new ClassMatcher(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ChannelMatcher isNotInstanceOf(Class<? extends Channel> paramClass)
/*     */   {
/*  74 */     return invert(isInstanceOf(paramClass));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ChannelMatcher isServerChannel()
/*     */   {
/*  81 */     return SERVER_CHANNEL_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ChannelMatcher isNonServerChannel()
/*     */   {
/*  89 */     return NON_SERVER_CHANNEL_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ChannelMatcher invert(ChannelMatcher paramChannelMatcher)
/*     */   {
/*  96 */     return new InvertMatcher(paramChannelMatcher);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ChannelMatcher compose(ChannelMatcher... paramVarArgs)
/*     */   {
/* 104 */     if (paramVarArgs.length < 1) {
/* 105 */       throw new IllegalArgumentException("matchers must at least contain one element");
/*     */     }
/* 107 */     if (paramVarArgs.length == 1) {
/* 108 */       return paramVarArgs[0];
/*     */     }
/* 110 */     return new CompositeMatcher(paramVarArgs);
/*     */   }
/*     */   
/*     */   private static final class CompositeMatcher implements ChannelMatcher {
/*     */     private final ChannelMatcher[] matchers;
/*     */     
/*     */     CompositeMatcher(ChannelMatcher... paramVarArgs) {
/* 117 */       this.matchers = paramVarArgs;
/*     */     }
/*     */     
/*     */     public boolean matches(Channel paramChannel)
/*     */     {
/* 122 */       for (ChannelMatcher localChannelMatcher : this.matchers) {
/* 123 */         if (!localChannelMatcher.matches(paramChannel)) {
/* 124 */           return false;
/*     */         }
/*     */       }
/* 127 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class InvertMatcher implements ChannelMatcher {
/*     */     private final ChannelMatcher matcher;
/*     */     
/*     */     InvertMatcher(ChannelMatcher paramChannelMatcher) {
/* 135 */       this.matcher = paramChannelMatcher;
/*     */     }
/*     */     
/*     */     public boolean matches(Channel paramChannel)
/*     */     {
/* 140 */       return !this.matcher.matches(paramChannel);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class InstanceMatcher implements ChannelMatcher {
/*     */     private final Channel channel;
/*     */     
/*     */     InstanceMatcher(Channel paramChannel) {
/* 148 */       this.channel = paramChannel;
/*     */     }
/*     */     
/*     */     public boolean matches(Channel paramChannel)
/*     */     {
/* 153 */       return this.channel == paramChannel;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ClassMatcher implements ChannelMatcher {
/*     */     private final Class<? extends Channel> clazz;
/*     */     
/*     */     ClassMatcher(Class<? extends Channel> paramClass) {
/* 161 */       this.clazz = paramClass;
/*     */     }
/*     */     
/*     */     public boolean matches(Channel paramChannel)
/*     */     {
/* 166 */       return this.clazz.isInstance(paramChannel);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\group\ChannelMatchers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */