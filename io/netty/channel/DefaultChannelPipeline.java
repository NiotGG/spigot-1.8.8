/*      */ package io.netty.channel;
/*      */ 
/*      */ import io.netty.util.ReferenceCountUtil;
/*      */ import io.netty.util.concurrent.EventExecutor;
/*      */ import io.netty.util.concurrent.EventExecutorGroup;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.net.SocketAddress;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class DefaultChannelPipeline
/*      */   implements ChannelPipeline
/*      */ {
/*      */   static final InternalLogger logger;
/*      */   private static final WeakHashMap<Class<?>, String>[] nameCaches;
/*      */   final AbstractChannel channel;
/*      */   final AbstractChannelHandlerContext head;
/*      */   final AbstractChannelHandlerContext tail;
/*      */   
/*      */   static
/*      */   {
/*   46 */     logger = InternalLoggerFactory.getInstance(DefaultChannelPipeline.class);
/*      */     
/*      */ 
/*   49 */     nameCaches = new WeakHashMap[Runtime.getRuntime().availableProcessors()];
/*      */     
/*      */ 
/*      */ 
/*   53 */     for (int i = 0; i < nameCaches.length; i++) {
/*   54 */       nameCaches[i] = new WeakHashMap();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   63 */   private final Map<String, AbstractChannelHandlerContext> name2ctx = new HashMap(4);
/*      */   
/*      */ 
/*   66 */   final Map<EventExecutorGroup, EventExecutor> childExecutors = new IdentityHashMap();
/*      */   
/*      */   public DefaultChannelPipeline(AbstractChannel paramAbstractChannel)
/*      */   {
/*   70 */     if (paramAbstractChannel == null) {
/*   71 */       throw new NullPointerException("channel");
/*      */     }
/*   73 */     this.channel = paramAbstractChannel;
/*      */     
/*   75 */     this.tail = new TailContext(this);
/*   76 */     this.head = new HeadContext(this);
/*      */     
/*   78 */     this.head.next = this.tail;
/*   79 */     this.tail.prev = this.head;
/*      */   }
/*      */   
/*      */   public Channel channel()
/*      */   {
/*   84 */     return this.channel;
/*      */   }
/*      */   
/*      */   public ChannelPipeline addFirst(String paramString, ChannelHandler paramChannelHandler)
/*      */   {
/*   89 */     return addFirst(null, paramString, paramChannelHandler);
/*      */   }
/*      */   
/*      */   public ChannelPipeline addFirst(EventExecutorGroup paramEventExecutorGroup, String paramString, ChannelHandler paramChannelHandler)
/*      */   {
/*   94 */     synchronized (this) {
/*   95 */       checkDuplicateName(paramString);
/*   96 */       DefaultChannelHandlerContext localDefaultChannelHandlerContext = new DefaultChannelHandlerContext(this, paramEventExecutorGroup, paramString, paramChannelHandler);
/*   97 */       addFirst0(paramString, localDefaultChannelHandlerContext);
/*      */     }
/*      */     
/*  100 */     return this;
/*      */   }
/*      */   
/*      */   private void addFirst0(String paramString, AbstractChannelHandlerContext paramAbstractChannelHandlerContext) {
/*  104 */     checkMultiplicity(paramAbstractChannelHandlerContext);
/*      */     
/*  106 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this.head.next;
/*  107 */     paramAbstractChannelHandlerContext.prev = this.head;
/*  108 */     paramAbstractChannelHandlerContext.next = localAbstractChannelHandlerContext;
/*  109 */     this.head.next = paramAbstractChannelHandlerContext;
/*  110 */     localAbstractChannelHandlerContext.prev = paramAbstractChannelHandlerContext;
/*      */     
/*  112 */     this.name2ctx.put(paramString, paramAbstractChannelHandlerContext);
/*      */     
/*  114 */     callHandlerAdded(paramAbstractChannelHandlerContext);
/*      */   }
/*      */   
/*      */   public ChannelPipeline addLast(String paramString, ChannelHandler paramChannelHandler)
/*      */   {
/*  119 */     return addLast(null, paramString, paramChannelHandler);
/*      */   }
/*      */   
/*      */   public ChannelPipeline addLast(EventExecutorGroup paramEventExecutorGroup, String paramString, ChannelHandler paramChannelHandler)
/*      */   {
/*  124 */     synchronized (this) {
/*  125 */       checkDuplicateName(paramString);
/*      */       
/*  127 */       DefaultChannelHandlerContext localDefaultChannelHandlerContext = new DefaultChannelHandlerContext(this, paramEventExecutorGroup, paramString, paramChannelHandler);
/*  128 */       addLast0(paramString, localDefaultChannelHandlerContext);
/*      */     }
/*      */     
/*  131 */     return this;
/*      */   }
/*      */   
/*      */   private void addLast0(String paramString, AbstractChannelHandlerContext paramAbstractChannelHandlerContext) {
/*  135 */     checkMultiplicity(paramAbstractChannelHandlerContext);
/*      */     
/*  137 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this.tail.prev;
/*  138 */     paramAbstractChannelHandlerContext.prev = localAbstractChannelHandlerContext;
/*  139 */     paramAbstractChannelHandlerContext.next = this.tail;
/*  140 */     localAbstractChannelHandlerContext.next = paramAbstractChannelHandlerContext;
/*  141 */     this.tail.prev = paramAbstractChannelHandlerContext;
/*      */     
/*  143 */     this.name2ctx.put(paramString, paramAbstractChannelHandlerContext);
/*      */     
/*  145 */     callHandlerAdded(paramAbstractChannelHandlerContext);
/*      */   }
/*      */   
/*      */   public ChannelPipeline addBefore(String paramString1, String paramString2, ChannelHandler paramChannelHandler)
/*      */   {
/*  150 */     return addBefore(null, paramString1, paramString2, paramChannelHandler);
/*      */   }
/*      */   
/*      */ 
/*      */   public ChannelPipeline addBefore(EventExecutorGroup paramEventExecutorGroup, String paramString1, String paramString2, ChannelHandler paramChannelHandler)
/*      */   {
/*  156 */     synchronized (this) {
/*  157 */       AbstractChannelHandlerContext localAbstractChannelHandlerContext = getContextOrDie(paramString1);
/*  158 */       checkDuplicateName(paramString2);
/*  159 */       DefaultChannelHandlerContext localDefaultChannelHandlerContext = new DefaultChannelHandlerContext(this, paramEventExecutorGroup, paramString2, paramChannelHandler);
/*  160 */       addBefore0(paramString2, localAbstractChannelHandlerContext, localDefaultChannelHandlerContext);
/*      */     }
/*  162 */     return this;
/*      */   }
/*      */   
/*      */   private void addBefore0(String paramString, AbstractChannelHandlerContext paramAbstractChannelHandlerContext1, AbstractChannelHandlerContext paramAbstractChannelHandlerContext2)
/*      */   {
/*  167 */     checkMultiplicity(paramAbstractChannelHandlerContext2);
/*      */     
/*  169 */     paramAbstractChannelHandlerContext2.prev = paramAbstractChannelHandlerContext1.prev;
/*  170 */     paramAbstractChannelHandlerContext2.next = paramAbstractChannelHandlerContext1;
/*  171 */     paramAbstractChannelHandlerContext1.prev.next = paramAbstractChannelHandlerContext2;
/*  172 */     paramAbstractChannelHandlerContext1.prev = paramAbstractChannelHandlerContext2;
/*      */     
/*  174 */     this.name2ctx.put(paramString, paramAbstractChannelHandlerContext2);
/*      */     
/*  176 */     callHandlerAdded(paramAbstractChannelHandlerContext2);
/*      */   }
/*      */   
/*      */   public ChannelPipeline addAfter(String paramString1, String paramString2, ChannelHandler paramChannelHandler)
/*      */   {
/*  181 */     return addAfter(null, paramString1, paramString2, paramChannelHandler);
/*      */   }
/*      */   
/*      */ 
/*      */   public ChannelPipeline addAfter(EventExecutorGroup paramEventExecutorGroup, String paramString1, String paramString2, ChannelHandler paramChannelHandler)
/*      */   {
/*  187 */     synchronized (this) {
/*  188 */       AbstractChannelHandlerContext localAbstractChannelHandlerContext = getContextOrDie(paramString1);
/*  189 */       checkDuplicateName(paramString2);
/*  190 */       DefaultChannelHandlerContext localDefaultChannelHandlerContext = new DefaultChannelHandlerContext(this, paramEventExecutorGroup, paramString2, paramChannelHandler);
/*      */       
/*  192 */       addAfter0(paramString2, localAbstractChannelHandlerContext, localDefaultChannelHandlerContext);
/*      */     }
/*      */     
/*  195 */     return this;
/*      */   }
/*      */   
/*      */   private void addAfter0(String paramString, AbstractChannelHandlerContext paramAbstractChannelHandlerContext1, AbstractChannelHandlerContext paramAbstractChannelHandlerContext2) {
/*  199 */     checkDuplicateName(paramString);
/*  200 */     checkMultiplicity(paramAbstractChannelHandlerContext2);
/*      */     
/*  202 */     paramAbstractChannelHandlerContext2.prev = paramAbstractChannelHandlerContext1;
/*  203 */     paramAbstractChannelHandlerContext2.next = paramAbstractChannelHandlerContext1.next;
/*  204 */     paramAbstractChannelHandlerContext1.next.prev = paramAbstractChannelHandlerContext2;
/*  205 */     paramAbstractChannelHandlerContext1.next = paramAbstractChannelHandlerContext2;
/*      */     
/*  207 */     this.name2ctx.put(paramString, paramAbstractChannelHandlerContext2);
/*      */     
/*  209 */     callHandlerAdded(paramAbstractChannelHandlerContext2);
/*      */   }
/*      */   
/*      */   public ChannelPipeline addFirst(ChannelHandler... paramVarArgs)
/*      */   {
/*  214 */     return addFirst(null, paramVarArgs);
/*      */   }
/*      */   
/*      */   public ChannelPipeline addFirst(EventExecutorGroup paramEventExecutorGroup, ChannelHandler... paramVarArgs)
/*      */   {
/*  219 */     if (paramVarArgs == null) {
/*  220 */       throw new NullPointerException("handlers");
/*      */     }
/*  222 */     if ((paramVarArgs.length == 0) || (paramVarArgs[0] == null)) {
/*  223 */       return this;
/*      */     }
/*      */     
/*      */ 
/*  227 */     for (int i = 1; i < paramVarArgs.length; i++) {
/*  228 */       if (paramVarArgs[i] == null) {
/*      */         break;
/*      */       }
/*      */     }
/*      */     
/*  233 */     for (int j = i - 1; j >= 0; j--) {
/*  234 */       ChannelHandler localChannelHandler = paramVarArgs[j];
/*  235 */       addFirst(paramEventExecutorGroup, generateName(localChannelHandler), localChannelHandler);
/*      */     }
/*      */     
/*  238 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelPipeline addLast(ChannelHandler... paramVarArgs)
/*      */   {
/*  243 */     return addLast(null, paramVarArgs);
/*      */   }
/*      */   
/*      */   public ChannelPipeline addLast(EventExecutorGroup paramEventExecutorGroup, ChannelHandler... paramVarArgs)
/*      */   {
/*  248 */     if (paramVarArgs == null) {
/*  249 */       throw new NullPointerException("handlers");
/*      */     }
/*      */     
/*  252 */     for (ChannelHandler localChannelHandler : paramVarArgs) {
/*  253 */       if (localChannelHandler == null) {
/*      */         break;
/*      */       }
/*  256 */       addLast(paramEventExecutorGroup, generateName(localChannelHandler), localChannelHandler);
/*      */     }
/*      */     
/*  259 */     return this;
/*      */   }
/*      */   
/*      */   private String generateName(ChannelHandler paramChannelHandler) {
/*  263 */     WeakHashMap localWeakHashMap = nameCaches[((int)(Thread.currentThread().getId() % nameCaches.length))];
/*  264 */     Class localClass = paramChannelHandler.getClass();
/*      */     Object localObject1;
/*  266 */     synchronized (localWeakHashMap) {
/*  267 */       localObject1 = (String)localWeakHashMap.get(localClass);
/*  268 */       if (localObject1 == null) {
/*  269 */         localObject1 = generateName0(localClass);
/*  270 */         localWeakHashMap.put(localClass, localObject1);
/*      */       }
/*      */     }
/*      */     
/*  274 */     synchronized (this)
/*      */     {
/*      */ 
/*  277 */       if (this.name2ctx.containsKey(localObject1)) {
/*  278 */         String str1 = ((String)localObject1).substring(0, ((String)localObject1).length() - 1);
/*  279 */         for (int i = 1;; i++) {
/*  280 */           String str2 = str1 + i;
/*  281 */           if (!this.name2ctx.containsKey(str2)) {
/*  282 */             localObject1 = str2;
/*  283 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  289 */     return (String)localObject1;
/*      */   }
/*      */   
/*      */   private static String generateName0(Class<?> paramClass) {
/*  293 */     return StringUtil.simpleClassName(paramClass) + "#0";
/*      */   }
/*      */   
/*      */   public ChannelPipeline remove(ChannelHandler paramChannelHandler)
/*      */   {
/*  298 */     remove(getContextOrDie(paramChannelHandler));
/*  299 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelHandler remove(String paramString)
/*      */   {
/*  304 */     return remove(getContextOrDie(paramString)).handler();
/*      */   }
/*      */   
/*      */ 
/*      */   public <T extends ChannelHandler> T remove(Class<T> paramClass)
/*      */   {
/*  310 */     return remove(getContextOrDie(paramClass)).handler();
/*      */   }
/*      */   
/*      */   private AbstractChannelHandlerContext remove(final AbstractChannelHandlerContext paramAbstractChannelHandlerContext) {
/*  314 */     assert ((paramAbstractChannelHandlerContext != this.head) && (paramAbstractChannelHandlerContext != this.tail));
/*      */     
/*      */     io.netty.util.concurrent.Future localFuture;
/*      */     
/*      */     AbstractChannelHandlerContext localAbstractChannelHandlerContext;
/*  319 */     synchronized (this) {
/*  320 */       if ((!paramAbstractChannelHandlerContext.channel().isRegistered()) || (paramAbstractChannelHandlerContext.executor().inEventLoop())) {
/*  321 */         remove0(paramAbstractChannelHandlerContext);
/*  322 */         return paramAbstractChannelHandlerContext;
/*      */       }
/*  324 */       localFuture = paramAbstractChannelHandlerContext.executor().submit(new Runnable()
/*      */       {
/*      */         public void run() {
/*  327 */           synchronized (DefaultChannelPipeline.this) {
/*  328 */             DefaultChannelPipeline.this.remove0(paramAbstractChannelHandlerContext);
/*      */           }
/*      */         }
/*  331 */       });
/*  332 */       localAbstractChannelHandlerContext = paramAbstractChannelHandlerContext;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  339 */     waitForFuture(localFuture);
/*      */     
/*  341 */     return localAbstractChannelHandlerContext;
/*      */   }
/*      */   
/*      */   void remove0(AbstractChannelHandlerContext paramAbstractChannelHandlerContext) {
/*  345 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext1 = paramAbstractChannelHandlerContext.prev;
/*  346 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext2 = paramAbstractChannelHandlerContext.next;
/*  347 */     localAbstractChannelHandlerContext1.next = localAbstractChannelHandlerContext2;
/*  348 */     localAbstractChannelHandlerContext2.prev = localAbstractChannelHandlerContext1;
/*  349 */     this.name2ctx.remove(paramAbstractChannelHandlerContext.name());
/*  350 */     callHandlerRemoved(paramAbstractChannelHandlerContext);
/*      */   }
/*      */   
/*      */   public ChannelHandler removeFirst()
/*      */   {
/*  355 */     if (this.head.next == this.tail) {
/*  356 */       throw new NoSuchElementException();
/*      */     }
/*  358 */     return remove(this.head.next).handler();
/*      */   }
/*      */   
/*      */   public ChannelHandler removeLast()
/*      */   {
/*  363 */     if (this.head.next == this.tail) {
/*  364 */       throw new NoSuchElementException();
/*      */     }
/*  366 */     return remove(this.tail.prev).handler();
/*      */   }
/*      */   
/*      */   public ChannelPipeline replace(ChannelHandler paramChannelHandler1, String paramString, ChannelHandler paramChannelHandler2)
/*      */   {
/*  371 */     replace(getContextOrDie(paramChannelHandler1), paramString, paramChannelHandler2);
/*  372 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelHandler replace(String paramString1, String paramString2, ChannelHandler paramChannelHandler)
/*      */   {
/*  377 */     return replace(getContextOrDie(paramString1), paramString2, paramChannelHandler);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public <T extends ChannelHandler> T replace(Class<T> paramClass, String paramString, ChannelHandler paramChannelHandler)
/*      */   {
/*  384 */     return replace(getContextOrDie(paramClass), paramString, paramChannelHandler);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ChannelHandler replace(final AbstractChannelHandlerContext paramAbstractChannelHandlerContext, final String paramString, ChannelHandler paramChannelHandler)
/*      */   {
/*  391 */     assert ((paramAbstractChannelHandlerContext != this.head) && (paramAbstractChannelHandlerContext != this.tail));
/*      */     
/*      */     io.netty.util.concurrent.Future localFuture;
/*  394 */     synchronized (this) {
/*  395 */       boolean bool = paramAbstractChannelHandlerContext.name().equals(paramString);
/*  396 */       if (!bool) {
/*  397 */         checkDuplicateName(paramString);
/*      */       }
/*      */       
/*  400 */       final DefaultChannelHandlerContext localDefaultChannelHandlerContext = new DefaultChannelHandlerContext(this, paramAbstractChannelHandlerContext.executor, paramString, paramChannelHandler);
/*      */       
/*      */ 
/*  403 */       if ((!localDefaultChannelHandlerContext.channel().isRegistered()) || (localDefaultChannelHandlerContext.executor().inEventLoop())) {
/*  404 */         replace0(paramAbstractChannelHandlerContext, paramString, localDefaultChannelHandlerContext);
/*  405 */         return paramAbstractChannelHandlerContext.handler();
/*      */       }
/*  407 */       localFuture = localDefaultChannelHandlerContext.executor().submit(new Runnable()
/*      */       {
/*      */         public void run() {
/*  410 */           synchronized (DefaultChannelPipeline.this) {
/*  411 */             DefaultChannelPipeline.this.replace0(paramAbstractChannelHandlerContext, paramString, localDefaultChannelHandlerContext);
/*      */           }
/*      */         }
/*      */       });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  421 */     waitForFuture(localFuture);
/*      */     
/*  423 */     return paramAbstractChannelHandlerContext.handler();
/*      */   }
/*      */   
/*      */   private void replace0(AbstractChannelHandlerContext paramAbstractChannelHandlerContext1, String paramString, AbstractChannelHandlerContext paramAbstractChannelHandlerContext2)
/*      */   {
/*  428 */     checkMultiplicity(paramAbstractChannelHandlerContext2);
/*      */     
/*  430 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext1 = paramAbstractChannelHandlerContext1.prev;
/*  431 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext2 = paramAbstractChannelHandlerContext1.next;
/*  432 */     paramAbstractChannelHandlerContext2.prev = localAbstractChannelHandlerContext1;
/*  433 */     paramAbstractChannelHandlerContext2.next = localAbstractChannelHandlerContext2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  439 */     localAbstractChannelHandlerContext1.next = paramAbstractChannelHandlerContext2;
/*  440 */     localAbstractChannelHandlerContext2.prev = paramAbstractChannelHandlerContext2;
/*      */     
/*  442 */     if (!paramAbstractChannelHandlerContext1.name().equals(paramString)) {
/*  443 */       this.name2ctx.remove(paramAbstractChannelHandlerContext1.name());
/*      */     }
/*  445 */     this.name2ctx.put(paramString, paramAbstractChannelHandlerContext2);
/*      */     
/*      */ 
/*  448 */     paramAbstractChannelHandlerContext1.prev = paramAbstractChannelHandlerContext2;
/*  449 */     paramAbstractChannelHandlerContext1.next = paramAbstractChannelHandlerContext2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  454 */     callHandlerAdded(paramAbstractChannelHandlerContext2);
/*  455 */     callHandlerRemoved(paramAbstractChannelHandlerContext1);
/*      */   }
/*      */   
/*      */   private static void checkMultiplicity(ChannelHandlerContext paramChannelHandlerContext) {
/*  459 */     ChannelHandler localChannelHandler = paramChannelHandlerContext.handler();
/*  460 */     if ((localChannelHandler instanceof ChannelHandlerAdapter)) {
/*  461 */       ChannelHandlerAdapter localChannelHandlerAdapter = (ChannelHandlerAdapter)localChannelHandler;
/*  462 */       if ((!localChannelHandlerAdapter.isSharable()) && (localChannelHandlerAdapter.added)) {
/*  463 */         throw new ChannelPipelineException(localChannelHandlerAdapter.getClass().getName() + " is not a @Sharable handler, so can't be added or removed multiple times.");
/*      */       }
/*      */       
/*      */ 
/*  467 */       localChannelHandlerAdapter.added = true;
/*      */     }
/*      */   }
/*      */   
/*      */   private void callHandlerAdded(final ChannelHandlerContext paramChannelHandlerContext) {
/*  472 */     if ((paramChannelHandlerContext.channel().isRegistered()) && (!paramChannelHandlerContext.executor().inEventLoop())) {
/*  473 */       paramChannelHandlerContext.executor().execute(new Runnable()
/*      */       {
/*      */         public void run() {
/*  476 */           DefaultChannelPipeline.this.callHandlerAdded0(paramChannelHandlerContext);
/*      */         }
/*  478 */       });
/*  479 */       return;
/*      */     }
/*  481 */     callHandlerAdded0(paramChannelHandlerContext);
/*      */   }
/*      */   
/*      */   private void callHandlerAdded0(ChannelHandlerContext paramChannelHandlerContext) {
/*      */     try {
/*  486 */       paramChannelHandlerContext.handler().handlerAdded(paramChannelHandlerContext);
/*      */     } catch (Throwable localThrowable1) {
/*  488 */       int i = 0;
/*      */       try {
/*  490 */         remove((AbstractChannelHandlerContext)paramChannelHandlerContext);
/*  491 */         i = 1;
/*      */       } catch (Throwable localThrowable2) {
/*  493 */         if (logger.isWarnEnabled()) {
/*  494 */           logger.warn("Failed to remove a handler: " + paramChannelHandlerContext.name(), localThrowable2);
/*      */         }
/*      */       }
/*      */       
/*  498 */       if (i != 0) {
/*  499 */         fireExceptionCaught(new ChannelPipelineException(paramChannelHandlerContext.handler().getClass().getName() + ".handlerAdded() has thrown an exception; removed.", localThrowable1));
/*      */       }
/*      */       else
/*      */       {
/*  503 */         fireExceptionCaught(new ChannelPipelineException(paramChannelHandlerContext.handler().getClass().getName() + ".handlerAdded() has thrown an exception; also failed to remove.", localThrowable1));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void callHandlerRemoved(final AbstractChannelHandlerContext paramAbstractChannelHandlerContext)
/*      */   {
/*  511 */     if ((paramAbstractChannelHandlerContext.channel().isRegistered()) && (!paramAbstractChannelHandlerContext.executor().inEventLoop())) {
/*  512 */       paramAbstractChannelHandlerContext.executor().execute(new Runnable()
/*      */       {
/*      */         public void run() {
/*  515 */           DefaultChannelPipeline.this.callHandlerRemoved0(paramAbstractChannelHandlerContext);
/*      */         }
/*  517 */       });
/*  518 */       return;
/*      */     }
/*  520 */     callHandlerRemoved0(paramAbstractChannelHandlerContext);
/*      */   }
/*      */   
/*      */   private void callHandlerRemoved0(AbstractChannelHandlerContext paramAbstractChannelHandlerContext)
/*      */   {
/*      */     try {
/*  526 */       paramAbstractChannelHandlerContext.handler().handlerRemoved(paramAbstractChannelHandlerContext);
/*  527 */       paramAbstractChannelHandlerContext.setRemoved();
/*      */     } catch (Throwable localThrowable) {
/*  529 */       fireExceptionCaught(new ChannelPipelineException(paramAbstractChannelHandlerContext.handler().getClass().getName() + ".handlerRemoved() has thrown an exception.", localThrowable));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void waitForFuture(java.util.concurrent.Future<?> paramFuture)
/*      */   {
/*      */     try
/*      */     {
/*  551 */       paramFuture.get();
/*      */     }
/*      */     catch (ExecutionException localExecutionException) {
/*  554 */       PlatformDependent.throwException(localExecutionException.getCause());
/*      */     }
/*      */     catch (InterruptedException localInterruptedException) {
/*  557 */       Thread.currentThread().interrupt();
/*      */     }
/*      */   }
/*      */   
/*      */   public ChannelHandler first()
/*      */   {
/*  563 */     ChannelHandlerContext localChannelHandlerContext = firstContext();
/*  564 */     if (localChannelHandlerContext == null) {
/*  565 */       return null;
/*      */     }
/*  567 */     return localChannelHandlerContext.handler();
/*      */   }
/*      */   
/*      */   public ChannelHandlerContext firstContext()
/*      */   {
/*  572 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this.head.next;
/*  573 */     if (localAbstractChannelHandlerContext == this.tail) {
/*  574 */       return null;
/*      */     }
/*  576 */     return this.head.next;
/*      */   }
/*      */   
/*      */   public ChannelHandler last()
/*      */   {
/*  581 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this.tail.prev;
/*  582 */     if (localAbstractChannelHandlerContext == this.head) {
/*  583 */       return null;
/*      */     }
/*  585 */     return localAbstractChannelHandlerContext.handler();
/*      */   }
/*      */   
/*      */   public ChannelHandlerContext lastContext()
/*      */   {
/*  590 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this.tail.prev;
/*  591 */     if (localAbstractChannelHandlerContext == this.head) {
/*  592 */       return null;
/*      */     }
/*  594 */     return localAbstractChannelHandlerContext;
/*      */   }
/*      */   
/*      */   public ChannelHandler get(String paramString)
/*      */   {
/*  599 */     ChannelHandlerContext localChannelHandlerContext = context(paramString);
/*  600 */     if (localChannelHandlerContext == null) {
/*  601 */       return null;
/*      */     }
/*  603 */     return localChannelHandlerContext.handler();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public <T extends ChannelHandler> T get(Class<T> paramClass)
/*      */   {
/*  610 */     ChannelHandlerContext localChannelHandlerContext = context(paramClass);
/*  611 */     if (localChannelHandlerContext == null) {
/*  612 */       return null;
/*      */     }
/*  614 */     return localChannelHandlerContext.handler();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ChannelHandlerContext context(ChannelHandler paramChannelHandler)
/*      */   {
/*  631 */     if (paramChannelHandler == null) {
/*  632 */       throw new NullPointerException("handler");
/*      */     }
/*      */     
/*  635 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this.head.next;
/*      */     for (;;)
/*      */     {
/*  638 */       if (localAbstractChannelHandlerContext == null) {
/*  639 */         return null;
/*      */       }
/*      */       
/*  642 */       if (localAbstractChannelHandlerContext.handler() == paramChannelHandler) {
/*  643 */         return localAbstractChannelHandlerContext;
/*      */       }
/*      */       
/*  646 */       localAbstractChannelHandlerContext = localAbstractChannelHandlerContext.next;
/*      */     }
/*      */   }
/*      */   
/*      */   public ChannelHandlerContext context(Class<? extends ChannelHandler> paramClass)
/*      */   {
/*  652 */     if (paramClass == null) {
/*  653 */       throw new NullPointerException("handlerType");
/*      */     }
/*      */     
/*  656 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this.head.next;
/*      */     for (;;) {
/*  658 */       if (localAbstractChannelHandlerContext == null) {
/*  659 */         return null;
/*      */       }
/*  661 */       if (paramClass.isAssignableFrom(localAbstractChannelHandlerContext.handler().getClass())) {
/*  662 */         return localAbstractChannelHandlerContext;
/*      */       }
/*  664 */       localAbstractChannelHandlerContext = localAbstractChannelHandlerContext.next;
/*      */     }
/*      */   }
/*      */   
/*      */   public List<String> names()
/*      */   {
/*  670 */     ArrayList localArrayList = new ArrayList();
/*  671 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this.head.next;
/*      */     for (;;) {
/*  673 */       if (localAbstractChannelHandlerContext == null) {
/*  674 */         return localArrayList;
/*      */       }
/*  676 */       localArrayList.add(localAbstractChannelHandlerContext.name());
/*  677 */       localAbstractChannelHandlerContext = localAbstractChannelHandlerContext.next;
/*      */     }
/*      */   }
/*      */   
/*      */   public Map<String, ChannelHandler> toMap()
/*      */   {
/*  683 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap();
/*  684 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this.head.next;
/*      */     for (;;) {
/*  686 */       if (localAbstractChannelHandlerContext == this.tail) {
/*  687 */         return localLinkedHashMap;
/*      */       }
/*  689 */       localLinkedHashMap.put(localAbstractChannelHandlerContext.name(), localAbstractChannelHandlerContext.handler());
/*  690 */       localAbstractChannelHandlerContext = localAbstractChannelHandlerContext.next;
/*      */     }
/*      */   }
/*      */   
/*      */   public Iterator<Map.Entry<String, ChannelHandler>> iterator()
/*      */   {
/*  696 */     return toMap().entrySet().iterator();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/*  704 */     StringBuilder localStringBuilder = new StringBuilder();
/*  705 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/*  706 */     localStringBuilder.append('{');
/*  707 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this.head.next;
/*      */     
/*  709 */     while (localAbstractChannelHandlerContext != this.tail)
/*      */     {
/*      */ 
/*      */ 
/*  713 */       localStringBuilder.append('(');
/*  714 */       localStringBuilder.append(localAbstractChannelHandlerContext.name());
/*  715 */       localStringBuilder.append(" = ");
/*  716 */       localStringBuilder.append(localAbstractChannelHandlerContext.handler().getClass().getName());
/*  717 */       localStringBuilder.append(')');
/*      */       
/*  719 */       localAbstractChannelHandlerContext = localAbstractChannelHandlerContext.next;
/*  720 */       if (localAbstractChannelHandlerContext == this.tail) {
/*      */         break;
/*      */       }
/*      */       
/*  724 */       localStringBuilder.append(", ");
/*      */     }
/*  726 */     localStringBuilder.append('}');
/*  727 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */   public ChannelPipeline fireChannelRegistered()
/*      */   {
/*  732 */     this.head.fireChannelRegistered();
/*  733 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelPipeline fireChannelUnregistered()
/*      */   {
/*  738 */     this.head.fireChannelUnregistered();
/*      */     
/*      */ 
/*  741 */     if (!this.channel.isOpen()) {
/*  742 */       teardownAll();
/*      */     }
/*  744 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void teardownAll()
/*      */   {
/*  753 */     this.tail.prev.teardown();
/*      */   }
/*      */   
/*      */   public ChannelPipeline fireChannelActive()
/*      */   {
/*  758 */     this.head.fireChannelActive();
/*      */     
/*  760 */     if (this.channel.config().isAutoRead()) {
/*  761 */       this.channel.read();
/*      */     }
/*      */     
/*  764 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelPipeline fireChannelInactive()
/*      */   {
/*  769 */     this.head.fireChannelInactive();
/*  770 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelPipeline fireExceptionCaught(Throwable paramThrowable)
/*      */   {
/*  775 */     this.head.fireExceptionCaught(paramThrowable);
/*  776 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelPipeline fireUserEventTriggered(Object paramObject)
/*      */   {
/*  781 */     this.head.fireUserEventTriggered(paramObject);
/*  782 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelPipeline fireChannelRead(Object paramObject)
/*      */   {
/*  787 */     this.head.fireChannelRead(paramObject);
/*  788 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelPipeline fireChannelReadComplete()
/*      */   {
/*  793 */     this.head.fireChannelReadComplete();
/*  794 */     if (this.channel.config().isAutoRead()) {
/*  795 */       read();
/*      */     }
/*  797 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelPipeline fireChannelWritabilityChanged()
/*      */   {
/*  802 */     this.head.fireChannelWritabilityChanged();
/*  803 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelFuture bind(SocketAddress paramSocketAddress)
/*      */   {
/*  808 */     return this.tail.bind(paramSocketAddress);
/*      */   }
/*      */   
/*      */   public ChannelFuture connect(SocketAddress paramSocketAddress)
/*      */   {
/*  813 */     return this.tail.connect(paramSocketAddress);
/*      */   }
/*      */   
/*      */   public ChannelFuture connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*      */   {
/*  818 */     return this.tail.connect(paramSocketAddress1, paramSocketAddress2);
/*      */   }
/*      */   
/*      */   public ChannelFuture disconnect()
/*      */   {
/*  823 */     return this.tail.disconnect();
/*      */   }
/*      */   
/*      */   public ChannelFuture close()
/*      */   {
/*  828 */     return this.tail.close();
/*      */   }
/*      */   
/*      */   public ChannelFuture deregister()
/*      */   {
/*  833 */     return this.tail.deregister();
/*      */   }
/*      */   
/*      */   public ChannelPipeline flush()
/*      */   {
/*  838 */     this.tail.flush();
/*  839 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelFuture bind(SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise)
/*      */   {
/*  844 */     return this.tail.bind(paramSocketAddress, paramChannelPromise);
/*      */   }
/*      */   
/*      */   public ChannelFuture connect(SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise)
/*      */   {
/*  849 */     return this.tail.connect(paramSocketAddress, paramChannelPromise);
/*      */   }
/*      */   
/*      */   public ChannelFuture connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*      */   {
/*  854 */     return this.tail.connect(paramSocketAddress1, paramSocketAddress2, paramChannelPromise);
/*      */   }
/*      */   
/*      */   public ChannelFuture disconnect(ChannelPromise paramChannelPromise)
/*      */   {
/*  859 */     return this.tail.disconnect(paramChannelPromise);
/*      */   }
/*      */   
/*      */   public ChannelFuture close(ChannelPromise paramChannelPromise)
/*      */   {
/*  864 */     return this.tail.close(paramChannelPromise);
/*      */   }
/*      */   
/*      */   public ChannelFuture deregister(ChannelPromise paramChannelPromise)
/*      */   {
/*  869 */     return this.tail.deregister(paramChannelPromise);
/*      */   }
/*      */   
/*      */   public ChannelPipeline read()
/*      */   {
/*  874 */     this.tail.read();
/*  875 */     return this;
/*      */   }
/*      */   
/*      */   public ChannelFuture write(Object paramObject)
/*      */   {
/*  880 */     return this.tail.write(paramObject);
/*      */   }
/*      */   
/*      */   public ChannelFuture write(Object paramObject, ChannelPromise paramChannelPromise)
/*      */   {
/*  885 */     return this.tail.write(paramObject, paramChannelPromise);
/*      */   }
/*      */   
/*      */   public ChannelFuture writeAndFlush(Object paramObject, ChannelPromise paramChannelPromise)
/*      */   {
/*  890 */     return this.tail.writeAndFlush(paramObject, paramChannelPromise);
/*      */   }
/*      */   
/*      */   public ChannelFuture writeAndFlush(Object paramObject)
/*      */   {
/*  895 */     return this.tail.writeAndFlush(paramObject);
/*      */   }
/*      */   
/*      */   private void checkDuplicateName(String paramString) {
/*  899 */     if (this.name2ctx.containsKey(paramString)) {
/*  900 */       throw new IllegalArgumentException("Duplicate handler name: " + paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   private AbstractChannelHandlerContext getContextOrDie(String paramString) {
/*  905 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = (AbstractChannelHandlerContext)context(paramString);
/*  906 */     if (localAbstractChannelHandlerContext == null) {
/*  907 */       throw new NoSuchElementException(paramString);
/*      */     }
/*  909 */     return localAbstractChannelHandlerContext;
/*      */   }
/*      */   
/*      */   private AbstractChannelHandlerContext getContextOrDie(ChannelHandler paramChannelHandler)
/*      */   {
/*  914 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = (AbstractChannelHandlerContext)context(paramChannelHandler);
/*  915 */     if (localAbstractChannelHandlerContext == null) {
/*  916 */       throw new NoSuchElementException(paramChannelHandler.getClass().getName());
/*      */     }
/*  918 */     return localAbstractChannelHandlerContext;
/*      */   }
/*      */   
/*      */   private AbstractChannelHandlerContext getContextOrDie(Class<? extends ChannelHandler> paramClass)
/*      */   {
/*  923 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = (AbstractChannelHandlerContext)context(paramClass);
/*  924 */     if (localAbstractChannelHandlerContext == null) {
/*  925 */       throw new NoSuchElementException(paramClass.getName());
/*      */     }
/*  927 */     return localAbstractChannelHandlerContext;
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public ChannelHandlerContext context(String paramString)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: ifnonnull +14 -> 15
/*      */     //   4: new 62	java/lang/NullPointerException
/*      */     //   7: dup
/*      */     //   8: ldc_w 418
/*      */     //   11: invokespecial 66	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
/*      */     //   14: athrow
/*      */     //   15: aload_0
/*      */     //   16: dup
/*      */     //   17: astore_2
/*      */     //   18: monitorenter
/*      */     //   19: aload_0
/*      */     //   20: getfield 55	io/netty/channel/DefaultChannelPipeline:name2ctx	Ljava/util/Map;
/*      */     //   23: aload_1
/*      */     //   24: invokeinterface 419 2 0
/*      */     //   29: checkcast 315	io/netty/channel/ChannelHandlerContext
/*      */     //   32: aload_2
/*      */     //   33: monitorexit
/*      */     //   34: areturn
/*      */     //   35: astore_3
/*      */     //   36: aload_2
/*      */     //   37: monitorexit
/*      */     //   38: aload_3
/*      */     //   39: athrow
/*      */     // Line number table:
/*      */     //   Java source line #620	-> byte code offset #0
/*      */     //   Java source line #621	-> byte code offset #4
/*      */     //   Java source line #624	-> byte code offset #15
/*      */     //   Java source line #625	-> byte code offset #19
/*      */     //   Java source line #626	-> byte code offset #35
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	40	0	this	DefaultChannelPipeline
/*      */     //   0	40	1	paramString	String
/*      */     //   17	20	2	Ljava/lang/Object;	Object
/*      */     //   35	4	3	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   19	34	35	finally
/*      */     //   35	38	35	finally
/*      */   }
/*      */   
/*      */   static final class TailContext
/*      */     extends AbstractChannelHandlerContext
/*      */     implements ChannelInboundHandler
/*      */   {
/*  934 */     private static final String TAIL_NAME = DefaultChannelPipeline.generateName0(TailContext.class);
/*      */     
/*      */     TailContext(DefaultChannelPipeline paramDefaultChannelPipeline) {
/*  937 */       super(null, TAIL_NAME, true, false);
/*      */     }
/*      */     
/*      */     public ChannelHandler handler()
/*      */     {
/*  942 */       return this;
/*      */     }
/*      */     
/*      */     public void channelRegistered(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*      */     {}
/*      */     
/*      */     public void channelUnregistered(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*      */     {}
/*      */     
/*      */     public void channelActive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*      */     {}
/*      */     
/*      */     public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*      */     {}
/*      */     
/*      */     public void channelWritabilityChanged(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*      */     {}
/*      */     
/*      */     public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*      */     {}
/*      */     
/*      */     public void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*      */     {}
/*      */     
/*      */     public void userEventTriggered(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*      */     {}
/*      */     
/*      */     public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable) throws Exception
/*      */     {
/*  971 */       DefaultChannelPipeline.logger.warn("An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.", paramThrowable);
/*      */     }
/*      */     
/*      */     public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject)
/*      */       throws Exception
/*      */     {
/*      */       try
/*      */       {
/*  979 */         DefaultChannelPipeline.logger.debug("Discarded inbound message {} that reached at the tail of the pipeline. Please check your pipeline configuration.", paramObject);
/*      */       }
/*      */       finally
/*      */       {
/*  983 */         ReferenceCountUtil.release(paramObject);
/*      */       }
/*      */     }
/*      */     
/*      */     public void channelReadComplete(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*      */     {}
/*      */   }
/*      */   
/*      */   static final class HeadContext extends AbstractChannelHandlerContext implements ChannelOutboundHandler
/*      */   {
/*  993 */     private static final String HEAD_NAME = DefaultChannelPipeline.generateName0(HeadContext.class);
/*      */     protected final Channel.Unsafe unsafe;
/*      */     
/*      */     HeadContext(DefaultChannelPipeline paramDefaultChannelPipeline)
/*      */     {
/*  998 */       super(null, HEAD_NAME, false, true);
/*  999 */       this.unsafe = paramDefaultChannelPipeline.channel().unsafe();
/*      */     }
/*      */     
/*      */     public ChannelHandler handler()
/*      */     {
/* 1004 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */     public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext)
/*      */       throws Exception
/*      */     {}
/*      */     
/*      */ 
/*      */     public void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext)
/*      */       throws Exception
/*      */     {}
/*      */     
/*      */ 
/*      */     public void bind(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise)
/*      */       throws Exception
/*      */     {
/* 1021 */       this.unsafe.bind(paramSocketAddress, paramChannelPromise);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void connect(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*      */       throws Exception
/*      */     {
/* 1029 */       this.unsafe.connect(paramSocketAddress1, paramSocketAddress2, paramChannelPromise);
/*      */     }
/*      */     
/*      */     public void disconnect(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception
/*      */     {
/* 1034 */       this.unsafe.disconnect(paramChannelPromise);
/*      */     }
/*      */     
/*      */     public void close(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception
/*      */     {
/* 1039 */       this.unsafe.close(paramChannelPromise);
/*      */     }
/*      */     
/*      */     public void deregister(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception
/*      */     {
/* 1044 */       this.unsafe.deregister(paramChannelPromise);
/*      */     }
/*      */     
/*      */     public void read(ChannelHandlerContext paramChannelHandlerContext)
/*      */     {
/* 1049 */       this.unsafe.beginRead();
/*      */     }
/*      */     
/*      */     public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*      */     {
/* 1054 */       this.unsafe.write(paramObject, paramChannelPromise);
/*      */     }
/*      */     
/*      */     public void flush(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*      */     {
/* 1059 */       this.unsafe.flush();
/*      */     }
/*      */     
/*      */     public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable) throws Exception
/*      */     {
/* 1064 */       paramChannelHandlerContext.fireExceptionCaught(paramThrowable);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\DefaultChannelPipeline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */