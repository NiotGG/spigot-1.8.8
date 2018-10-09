/*     */ package io.netty.channel.group;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ServerChannel;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.internal.ConcurrentSet;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public class DefaultChannelGroup
/*     */   extends AbstractSet<Channel>
/*     */   implements ChannelGroup
/*     */ {
/*  42 */   private static final AtomicInteger nextId = new AtomicInteger();
/*     */   private final String name;
/*     */   private final EventExecutor executor;
/*  45 */   private final ConcurrentSet<Channel> serverChannels = new ConcurrentSet();
/*  46 */   private final ConcurrentSet<Channel> nonServerChannels = new ConcurrentSet();
/*  47 */   private final ChannelFutureListener remover = new ChannelFutureListener()
/*     */   {
/*     */     public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/*  50 */       DefaultChannelGroup.this.remove(paramAnonymousChannelFuture.channel());
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultChannelGroup(EventExecutor paramEventExecutor)
/*     */   {
/*  59 */     this("group-0x" + Integer.toHexString(nextId.incrementAndGet()), paramEventExecutor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultChannelGroup(String paramString, EventExecutor paramEventExecutor)
/*     */   {
/*  68 */     if (paramString == null) {
/*  69 */       throw new NullPointerException("name");
/*     */     }
/*  71 */     this.name = paramString;
/*  72 */     this.executor = paramEventExecutor;
/*     */   }
/*     */   
/*     */   public String name()
/*     */   {
/*  77 */     return this.name;
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/*  82 */     return (this.nonServerChannels.isEmpty()) && (this.serverChannels.isEmpty());
/*     */   }
/*     */   
/*     */   public int size()
/*     */   {
/*  87 */     return this.nonServerChannels.size() + this.serverChannels.size();
/*     */   }
/*     */   
/*     */   public boolean contains(Object paramObject)
/*     */   {
/*  92 */     if ((paramObject instanceof Channel)) {
/*  93 */       Channel localChannel = (Channel)paramObject;
/*  94 */       if ((paramObject instanceof ServerChannel)) {
/*  95 */         return this.serverChannels.contains(localChannel);
/*     */       }
/*  97 */       return this.nonServerChannels.contains(localChannel);
/*     */     }
/*     */     
/* 100 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean add(Channel paramChannel)
/*     */   {
/* 106 */     ConcurrentSet localConcurrentSet = (paramChannel instanceof ServerChannel) ? this.serverChannels : this.nonServerChannels;
/*     */     
/*     */ 
/* 109 */     boolean bool = localConcurrentSet.add(paramChannel);
/* 110 */     if (bool) {
/* 111 */       paramChannel.closeFuture().addListener(this.remover);
/*     */     }
/* 113 */     return bool;
/*     */   }
/*     */   
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 118 */     if (!(paramObject instanceof Channel)) {
/* 119 */       return false;
/*     */     }
/*     */     
/* 122 */     Channel localChannel = (Channel)paramObject;
/* 123 */     boolean bool; if ((localChannel instanceof ServerChannel)) {
/* 124 */       bool = this.serverChannels.remove(localChannel);
/*     */     } else {
/* 126 */       bool = this.nonServerChannels.remove(localChannel);
/*     */     }
/* 128 */     if (!bool) {
/* 129 */       return false;
/*     */     }
/*     */     
/* 132 */     localChannel.closeFuture().removeListener(this.remover);
/* 133 */     return true;
/*     */   }
/*     */   
/*     */   public void clear()
/*     */   {
/* 138 */     this.nonServerChannels.clear();
/* 139 */     this.serverChannels.clear();
/*     */   }
/*     */   
/*     */   public Iterator<Channel> iterator()
/*     */   {
/* 144 */     return new CombinedIterator(this.serverChannels.iterator(), this.nonServerChannels.iterator());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 151 */     ArrayList localArrayList = new ArrayList(size());
/* 152 */     localArrayList.addAll(this.serverChannels);
/* 153 */     localArrayList.addAll(this.nonServerChannels);
/* 154 */     return localArrayList.toArray();
/*     */   }
/*     */   
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 159 */     ArrayList localArrayList = new ArrayList(size());
/* 160 */     localArrayList.addAll(this.serverChannels);
/* 161 */     localArrayList.addAll(this.nonServerChannels);
/* 162 */     return localArrayList.toArray(paramArrayOfT);
/*     */   }
/*     */   
/*     */   public ChannelGroupFuture close()
/*     */   {
/* 167 */     return close(ChannelMatchers.all());
/*     */   }
/*     */   
/*     */   public ChannelGroupFuture disconnect()
/*     */   {
/* 172 */     return disconnect(ChannelMatchers.all());
/*     */   }
/*     */   
/*     */   public ChannelGroupFuture deregister()
/*     */   {
/* 177 */     return deregister(ChannelMatchers.all());
/*     */   }
/*     */   
/*     */   public ChannelGroupFuture write(Object paramObject)
/*     */   {
/* 182 */     return write(paramObject, ChannelMatchers.all());
/*     */   }
/*     */   
/*     */ 
/*     */   private static Object safeDuplicate(Object paramObject)
/*     */   {
/* 188 */     if ((paramObject instanceof ByteBuf))
/* 189 */       return ((ByteBuf)paramObject).duplicate().retain();
/* 190 */     if ((paramObject instanceof ByteBufHolder)) {
/* 191 */       return ((ByteBufHolder)paramObject).duplicate().retain();
/*     */     }
/* 193 */     return ReferenceCountUtil.retain(paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelGroupFuture write(Object paramObject, ChannelMatcher paramChannelMatcher)
/*     */   {
/* 199 */     if (paramObject == null) {
/* 200 */       throw new NullPointerException("message");
/*     */     }
/* 202 */     if (paramChannelMatcher == null) {
/* 203 */       throw new NullPointerException("matcher");
/*     */     }
/*     */     
/* 206 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap(size());
/* 207 */     for (Channel localChannel : this.nonServerChannels) {
/* 208 */       if (paramChannelMatcher.matches(localChannel)) {
/* 209 */         localLinkedHashMap.put(localChannel, localChannel.write(safeDuplicate(paramObject)));
/*     */       }
/*     */     }
/*     */     
/* 213 */     ReferenceCountUtil.release(paramObject);
/* 214 */     return new DefaultChannelGroupFuture(this, localLinkedHashMap, this.executor);
/*     */   }
/*     */   
/*     */   public ChannelGroup flush()
/*     */   {
/* 219 */     return flush(ChannelMatchers.all());
/*     */   }
/*     */   
/*     */   public ChannelGroupFuture flushAndWrite(Object paramObject)
/*     */   {
/* 224 */     return writeAndFlush(paramObject);
/*     */   }
/*     */   
/*     */   public ChannelGroupFuture writeAndFlush(Object paramObject)
/*     */   {
/* 229 */     return writeAndFlush(paramObject, ChannelMatchers.all());
/*     */   }
/*     */   
/*     */   public ChannelGroupFuture disconnect(ChannelMatcher paramChannelMatcher)
/*     */   {
/* 234 */     if (paramChannelMatcher == null) {
/* 235 */       throw new NullPointerException("matcher");
/*     */     }
/*     */     
/* 238 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap(size());
/*     */     
/*     */ 
/* 241 */     for (Iterator localIterator = this.serverChannels.iterator(); localIterator.hasNext();) { localChannel = (Channel)localIterator.next();
/* 242 */       if (paramChannelMatcher.matches(localChannel))
/* 243 */         localLinkedHashMap.put(localChannel, localChannel.disconnect());
/*     */     }
/*     */     Channel localChannel;
/* 246 */     for (localIterator = this.nonServerChannels.iterator(); localIterator.hasNext();) { localChannel = (Channel)localIterator.next();
/* 247 */       if (paramChannelMatcher.matches(localChannel)) {
/* 248 */         localLinkedHashMap.put(localChannel, localChannel.disconnect());
/*     */       }
/*     */     }
/*     */     
/* 252 */     return new DefaultChannelGroupFuture(this, localLinkedHashMap, this.executor);
/*     */   }
/*     */   
/*     */   public ChannelGroupFuture close(ChannelMatcher paramChannelMatcher)
/*     */   {
/* 257 */     if (paramChannelMatcher == null) {
/* 258 */       throw new NullPointerException("matcher");
/*     */     }
/*     */     
/* 261 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap(size());
/*     */     
/*     */ 
/* 264 */     for (Iterator localIterator = this.serverChannels.iterator(); localIterator.hasNext();) { localChannel = (Channel)localIterator.next();
/* 265 */       if (paramChannelMatcher.matches(localChannel))
/* 266 */         localLinkedHashMap.put(localChannel, localChannel.close());
/*     */     }
/*     */     Channel localChannel;
/* 269 */     for (localIterator = this.nonServerChannels.iterator(); localIterator.hasNext();) { localChannel = (Channel)localIterator.next();
/* 270 */       if (paramChannelMatcher.matches(localChannel)) {
/* 271 */         localLinkedHashMap.put(localChannel, localChannel.close());
/*     */       }
/*     */     }
/*     */     
/* 275 */     return new DefaultChannelGroupFuture(this, localLinkedHashMap, this.executor);
/*     */   }
/*     */   
/*     */   public ChannelGroupFuture deregister(ChannelMatcher paramChannelMatcher)
/*     */   {
/* 280 */     if (paramChannelMatcher == null) {
/* 281 */       throw new NullPointerException("matcher");
/*     */     }
/*     */     
/* 284 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap(size());
/*     */     
/*     */ 
/* 287 */     for (Iterator localIterator = this.serverChannels.iterator(); localIterator.hasNext();) { localChannel = (Channel)localIterator.next();
/* 288 */       if (paramChannelMatcher.matches(localChannel))
/* 289 */         localLinkedHashMap.put(localChannel, localChannel.deregister());
/*     */     }
/*     */     Channel localChannel;
/* 292 */     for (localIterator = this.nonServerChannels.iterator(); localIterator.hasNext();) { localChannel = (Channel)localIterator.next();
/* 293 */       if (paramChannelMatcher.matches(localChannel)) {
/* 294 */         localLinkedHashMap.put(localChannel, localChannel.deregister());
/*     */       }
/*     */     }
/*     */     
/* 298 */     return new DefaultChannelGroupFuture(this, localLinkedHashMap, this.executor);
/*     */   }
/*     */   
/*     */   public ChannelGroup flush(ChannelMatcher paramChannelMatcher)
/*     */   {
/* 303 */     for (Channel localChannel : this.nonServerChannels) {
/* 304 */       if (paramChannelMatcher.matches(localChannel)) {
/* 305 */         localChannel.flush();
/*     */       }
/*     */     }
/* 308 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelGroupFuture flushAndWrite(Object paramObject, ChannelMatcher paramChannelMatcher)
/*     */   {
/* 313 */     return writeAndFlush(paramObject, paramChannelMatcher);
/*     */   }
/*     */   
/*     */   public ChannelGroupFuture writeAndFlush(Object paramObject, ChannelMatcher paramChannelMatcher)
/*     */   {
/* 318 */     if (paramObject == null) {
/* 319 */       throw new NullPointerException("message");
/*     */     }
/*     */     
/* 322 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap(size());
/*     */     
/* 324 */     for (Channel localChannel : this.nonServerChannels) {
/* 325 */       if (paramChannelMatcher.matches(localChannel)) {
/* 326 */         localLinkedHashMap.put(localChannel, localChannel.writeAndFlush(safeDuplicate(paramObject)));
/*     */       }
/*     */     }
/*     */     
/* 330 */     ReferenceCountUtil.release(paramObject);
/*     */     
/* 332 */     return new DefaultChannelGroupFuture(this, localLinkedHashMap, this.executor);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 337 */     return System.identityHashCode(this);
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 342 */     return this == paramObject;
/*     */   }
/*     */   
/*     */   public int compareTo(ChannelGroup paramChannelGroup)
/*     */   {
/* 347 */     int i = name().compareTo(paramChannelGroup.name());
/* 348 */     if (i != 0) {
/* 349 */       return i;
/*     */     }
/*     */     
/* 352 */     return System.identityHashCode(this) - System.identityHashCode(paramChannelGroup);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 357 */     return StringUtil.simpleClassName(this) + "(name: " + name() + ", size: " + size() + ')';
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\group\DefaultChannelGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */