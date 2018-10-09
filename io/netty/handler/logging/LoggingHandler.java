/*     */ package io.netty.handler.logging;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogLevel;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.SocketAddress;
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
/*     */ 
/*     */ 
/*     */ @ChannelHandler.Sharable
/*     */ public class LoggingHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  39 */   private static final LogLevel DEFAULT_LEVEL = LogLevel.DEBUG;
/*     */   
/*  41 */   private static final String NEWLINE = StringUtil.NEWLINE;
/*     */   
/*  43 */   private static final String[] BYTE2HEX = new String['Ā'];
/*  44 */   private static final String[] HEXPADDING = new String[16];
/*  45 */   private static final String[] BYTEPADDING = new String[16];
/*  46 */   private static final char[] BYTE2CHAR = new char['Ā'];
/*     */   protected final InternalLogger logger;
/*     */   protected final InternalLogLevel internalLevel;
/*     */   private final LogLevel level;
/*     */   
/*     */   static {
/*  52 */     for (int i = 0; i < BYTE2HEX.length; i++)
/*  53 */       BYTE2HEX[i] = (' ' + StringUtil.byteToHexStringPadded(i));
/*     */     int j;
/*     */     StringBuilder localStringBuilder;
/*     */     int k;
/*  57 */     for (i = 0; i < HEXPADDING.length; i++) {
/*  58 */       j = HEXPADDING.length - i;
/*  59 */       localStringBuilder = new StringBuilder(j * 3);
/*  60 */       for (k = 0; k < j; k++) {
/*  61 */         localStringBuilder.append("   ");
/*     */       }
/*  63 */       HEXPADDING[i] = localStringBuilder.toString();
/*     */     }
/*     */     
/*     */ 
/*  67 */     for (i = 0; i < BYTEPADDING.length; i++) {
/*  68 */       j = BYTEPADDING.length - i;
/*  69 */       localStringBuilder = new StringBuilder(j);
/*  70 */       for (k = 0; k < j; k++) {
/*  71 */         localStringBuilder.append(' ');
/*     */       }
/*  73 */       BYTEPADDING[i] = localStringBuilder.toString();
/*     */     }
/*     */     
/*     */ 
/*  77 */     for (i = 0; i < BYTE2CHAR.length; i++) {
/*  78 */       if ((i <= 31) || (i >= 127)) {
/*  79 */         BYTE2CHAR[i] = '.';
/*     */       } else {
/*  81 */         BYTE2CHAR[i] = ((char)i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggingHandler()
/*     */   {
/*  96 */     this(DEFAULT_LEVEL);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggingHandler(LogLevel paramLogLevel)
/*     */   {
/* 106 */     if (paramLogLevel == null) {
/* 107 */       throw new NullPointerException("level");
/*     */     }
/*     */     
/* 110 */     this.logger = InternalLoggerFactory.getInstance(getClass());
/* 111 */     this.level = paramLogLevel;
/* 112 */     this.internalLevel = paramLogLevel.toInternalLevel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggingHandler(Class<?> paramClass)
/*     */   {
/* 120 */     this(paramClass, DEFAULT_LEVEL);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggingHandler(Class<?> paramClass, LogLevel paramLogLevel)
/*     */   {
/* 129 */     if (paramClass == null) {
/* 130 */       throw new NullPointerException("clazz");
/*     */     }
/* 132 */     if (paramLogLevel == null) {
/* 133 */       throw new NullPointerException("level");
/*     */     }
/* 135 */     this.logger = InternalLoggerFactory.getInstance(paramClass);
/* 136 */     this.level = paramLogLevel;
/* 137 */     this.internalLevel = paramLogLevel.toInternalLevel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public LoggingHandler(String paramString)
/*     */   {
/* 144 */     this(paramString, DEFAULT_LEVEL);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggingHandler(String paramString, LogLevel paramLogLevel)
/*     */   {
/* 153 */     if (paramString == null) {
/* 154 */       throw new NullPointerException("name");
/*     */     }
/* 156 */     if (paramLogLevel == null) {
/* 157 */       throw new NullPointerException("level");
/*     */     }
/* 159 */     this.logger = InternalLoggerFactory.getInstance(paramString);
/* 160 */     this.level = paramLogLevel;
/* 161 */     this.internalLevel = paramLogLevel.toInternalLevel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public LogLevel level()
/*     */   {
/* 168 */     return this.level;
/*     */   }
/*     */   
/*     */   protected String format(ChannelHandlerContext paramChannelHandlerContext, String paramString) {
/* 172 */     String str = paramChannelHandlerContext.channel().toString();
/* 173 */     StringBuilder localStringBuilder = new StringBuilder(str.length() + paramString.length() + 1);
/* 174 */     localStringBuilder.append(str);
/* 175 */     localStringBuilder.append(' ');
/* 176 */     localStringBuilder.append(paramString);
/* 177 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 183 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 184 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, "REGISTERED"));
/*     */     }
/* 186 */     super.channelRegistered(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelUnregistered(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 192 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 193 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, "UNREGISTERED"));
/*     */     }
/* 195 */     super.channelUnregistered(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelActive(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 201 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 202 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, "ACTIVE"));
/*     */     }
/* 204 */     super.channelActive(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 210 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 211 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, "INACTIVE"));
/*     */     }
/* 213 */     super.channelInactive(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable)
/*     */     throws Exception
/*     */   {
/* 219 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 220 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, "EXCEPTION: " + paramThrowable), paramThrowable);
/*     */     }
/* 222 */     super.exceptionCaught(paramChannelHandlerContext, paramThrowable);
/*     */   }
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext paramChannelHandlerContext, Object paramObject)
/*     */     throws Exception
/*     */   {
/* 228 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 229 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, "USER_EVENT: " + paramObject));
/*     */     }
/* 231 */     super.userEventTriggered(paramChannelHandlerContext, paramObject);
/*     */   }
/*     */   
/*     */   public void bind(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/* 237 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 238 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, "BIND(" + paramSocketAddress + ')'));
/*     */     }
/* 240 */     super.bind(paramChannelHandlerContext, paramSocketAddress, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */   public void connect(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/* 247 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 248 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, "CONNECT(" + paramSocketAddress1 + ", " + paramSocketAddress2 + ')'));
/*     */     }
/* 250 */     super.connect(paramChannelHandlerContext, paramSocketAddress1, paramSocketAddress2, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void disconnect(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/* 256 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 257 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, "DISCONNECT()"));
/*     */     }
/* 259 */     super.disconnect(paramChannelHandlerContext, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void close(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/* 265 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 266 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, "CLOSE()"));
/*     */     }
/* 268 */     super.close(paramChannelHandlerContext, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void deregister(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/* 274 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 275 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, "DEREGISTER()"));
/*     */     }
/* 277 */     super.deregister(paramChannelHandlerContext, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*     */   {
/* 282 */     logMessage(paramChannelHandlerContext, "RECEIVED", paramObject);
/* 283 */     paramChannelHandlerContext.fireChannelRead(paramObject);
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 288 */     logMessage(paramChannelHandlerContext, "WRITE", paramObject);
/* 289 */     paramChannelHandlerContext.write(paramObject, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void flush(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 294 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 295 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, "FLUSH"));
/*     */     }
/* 297 */     paramChannelHandlerContext.flush();
/*     */   }
/*     */   
/*     */   private void logMessage(ChannelHandlerContext paramChannelHandlerContext, String paramString, Object paramObject) {
/* 301 */     if (this.logger.isEnabled(this.internalLevel)) {
/* 302 */       this.logger.log(this.internalLevel, format(paramChannelHandlerContext, formatMessage(paramString, paramObject)));
/*     */     }
/*     */   }
/*     */   
/*     */   protected String formatMessage(String paramString, Object paramObject) {
/* 307 */     if ((paramObject instanceof ByteBuf))
/* 308 */       return formatByteBuf(paramString, (ByteBuf)paramObject);
/* 309 */     if ((paramObject instanceof ByteBufHolder)) {
/* 310 */       return formatByteBufHolder(paramString, (ByteBufHolder)paramObject);
/*     */     }
/* 312 */     return formatNonByteBuf(paramString, paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String formatByteBuf(String paramString, ByteBuf paramByteBuf)
/*     */   {
/* 320 */     int i = paramByteBuf.readableBytes();
/* 321 */     int j = i / 16 + (i % 15 == 0 ? 0 : 1) + 4;
/* 322 */     StringBuilder localStringBuilder = new StringBuilder(j * 80 + paramString.length() + 16);
/*     */     
/* 324 */     localStringBuilder.append(paramString).append('(').append(i).append('B').append(')');
/* 325 */     localStringBuilder.append(NEWLINE + "         +-------------------------------------------------+" + NEWLINE + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |" + NEWLINE + "+--------+-------------------------------------------------+----------------+");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 330 */     int k = paramByteBuf.readerIndex();
/* 331 */     int m = paramByteBuf.writerIndex();
/*     */     int i1;
/*     */     int i2;
/* 334 */     for (int n = k; n < m; n++) {
/* 335 */       i1 = n - k;
/* 336 */       i2 = i1 & 0xF;
/* 337 */       if (i2 == 0) {
/* 338 */         localStringBuilder.append(NEWLINE);
/* 339 */         localStringBuilder.append(Long.toHexString(i1 & 0xFFFFFFFF | 0x100000000));
/* 340 */         localStringBuilder.setCharAt(localStringBuilder.length() - 9, '|');
/* 341 */         localStringBuilder.append('|');
/*     */       }
/* 343 */       localStringBuilder.append(BYTE2HEX[paramByteBuf.getUnsignedByte(n)]);
/* 344 */       if (i2 == 15) {
/* 345 */         localStringBuilder.append(" |");
/* 346 */         for (int i3 = n - 15; i3 <= n; i3++) {
/* 347 */           localStringBuilder.append(BYTE2CHAR[paramByteBuf.getUnsignedByte(i3)]);
/*     */         }
/* 349 */         localStringBuilder.append('|');
/*     */       }
/*     */     }
/*     */     
/* 353 */     if ((n - k & 0xF) != 0) {
/* 354 */       i1 = i & 0xF;
/* 355 */       localStringBuilder.append(HEXPADDING[i1]);
/* 356 */       localStringBuilder.append(" |");
/* 357 */       for (i2 = n - i1; i2 < n; i2++) {
/* 358 */         localStringBuilder.append(BYTE2CHAR[paramByteBuf.getUnsignedByte(i2)]);
/*     */       }
/* 360 */       localStringBuilder.append(BYTEPADDING[i1]);
/* 361 */       localStringBuilder.append('|');
/*     */     }
/*     */     
/* 364 */     localStringBuilder.append(NEWLINE + "+--------+-------------------------------------------------+----------------+");
/*     */     
/*     */ 
/* 367 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected String formatNonByteBuf(String paramString, Object paramObject)
/*     */   {
/* 374 */     return paramString + ": " + paramObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String formatByteBufHolder(String paramString, ByteBufHolder paramByteBufHolder)
/*     */   {
/* 384 */     return formatByteBuf(paramString, paramByteBufHolder.content());
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\logging\LoggingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */