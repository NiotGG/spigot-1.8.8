/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufProcessor;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.DecoderResult;
/*     */ import io.netty.handler.codec.ReplayingDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.util.internal.AppendableCharSequence;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HttpObjectDecoder
/*     */   extends ReplayingDecoder<State>
/*     */ {
/*     */   private final int maxInitialLineLength;
/*     */   private final int maxHeaderSize;
/*     */   private final int maxChunkSize;
/*     */   private final boolean chunkedSupported;
/*     */   protected final boolean validateHeaders;
/* 111 */   private final AppendableCharSequence seq = new AppendableCharSequence(128);
/* 112 */   private final HeaderParser headerParser = new HeaderParser(this.seq);
/* 113 */   private final LineParser lineParser = new LineParser(this.seq);
/*     */   
/*     */   private HttpMessage message;
/*     */   private long chunkSize;
/*     */   private int headerSize;
/* 118 */   private long contentLength = Long.MIN_VALUE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static enum State
/*     */   {
/* 125 */     SKIP_CONTROL_CHARS, 
/* 126 */     READ_INITIAL, 
/* 127 */     READ_HEADER, 
/* 128 */     READ_VARIABLE_LENGTH_CONTENT, 
/* 129 */     READ_FIXED_LENGTH_CONTENT, 
/* 130 */     READ_CHUNK_SIZE, 
/* 131 */     READ_CHUNKED_CONTENT, 
/* 132 */     READ_CHUNK_DELIMITER, 
/* 133 */     READ_CHUNK_FOOTER, 
/* 134 */     BAD_MESSAGE, 
/* 135 */     UPGRADED;
/*     */     
/*     */ 
/*     */     private State() {}
/*     */   }
/*     */   
/*     */ 
/*     */   protected HttpObjectDecoder()
/*     */   {
/* 144 */     this(4096, 8192, 8192, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected HttpObjectDecoder(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
/*     */   {
/* 152 */     this(paramInt1, paramInt2, paramInt3, paramBoolean, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected HttpObjectDecoder(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 162 */     super(State.SKIP_CONTROL_CHARS);
/*     */     
/* 164 */     if (paramInt1 <= 0) {
/* 165 */       throw new IllegalArgumentException("maxInitialLineLength must be a positive integer: " + paramInt1);
/*     */     }
/*     */     
/*     */ 
/* 169 */     if (paramInt2 <= 0) {
/* 170 */       throw new IllegalArgumentException("maxHeaderSize must be a positive integer: " + paramInt2);
/*     */     }
/*     */     
/*     */ 
/* 174 */     if (paramInt3 <= 0) {
/* 175 */       throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + paramInt3);
/*     */     }
/*     */     
/*     */ 
/* 179 */     this.maxInitialLineLength = paramInt1;
/* 180 */     this.maxHeaderSize = paramInt2;
/* 181 */     this.maxChunkSize = paramInt3;
/* 182 */     this.chunkedSupported = paramBoolean1;
/* 183 */     this.validateHeaders = paramBoolean2; }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception { int i;
/*     */     int m;
/*     */     int j;
/* 188 */     switch ((State)state()) {
/*     */     case SKIP_CONTROL_CHARS: 
/*     */       try {
/* 191 */         skipControlCharacters(paramByteBuf);
/* 192 */         checkpoint(State.READ_INITIAL);
/*     */       } finally {
/* 194 */         checkpoint();
/*     */       }
/*     */     case READ_INITIAL: 
/*     */       try {
/* 198 */         String[] arrayOfString = splitInitialLine(this.lineParser.parse(paramByteBuf));
/* 199 */         if (arrayOfString.length < 3)
/*     */         {
/* 201 */           checkpoint(State.SKIP_CONTROL_CHARS);
/* 202 */           return;
/*     */         }
/*     */         
/* 205 */         this.message = createMessage(arrayOfString);
/* 206 */         checkpoint(State.READ_HEADER);
/*     */       }
/*     */       catch (Exception localException1) {
/* 209 */         paramList.add(invalidMessage(localException1));
/* 210 */         return;
/*     */       }
/*     */     case READ_HEADER:  try {
/* 213 */         State localState = readHeaders(paramByteBuf);
/* 214 */         checkpoint(localState);
/* 215 */         if (localState == State.READ_CHUNK_SIZE) {
/* 216 */           if (!this.chunkedSupported) {
/* 217 */             throw new IllegalArgumentException("Chunked messages not supported");
/*     */           }
/*     */           
/* 220 */           paramList.add(this.message);
/* 221 */           return;
/*     */         }
/* 223 */         if (localState == State.SKIP_CONTROL_CHARS)
/*     */         {
/* 225 */           paramList.add(this.message);
/* 226 */           paramList.add(LastHttpContent.EMPTY_LAST_CONTENT);
/* 227 */           reset();
/* 228 */           return;
/*     */         }
/* 230 */         long l = contentLength();
/* 231 */         if ((l == 0L) || ((l == -1L) && (isDecodingRequest()))) {
/* 232 */           paramList.add(this.message);
/* 233 */           paramList.add(LastHttpContent.EMPTY_LAST_CONTENT);
/* 234 */           reset();
/* 235 */           return;
/*     */         }
/*     */         
/* 238 */         assert ((localState == State.READ_FIXED_LENGTH_CONTENT) || (localState == State.READ_VARIABLE_LENGTH_CONTENT));
/*     */         
/* 240 */         paramList.add(this.message);
/*     */         
/* 242 */         if (localState == State.READ_FIXED_LENGTH_CONTENT)
/*     */         {
/* 244 */           this.chunkSize = l;
/*     */         }
/*     */         
/*     */ 
/* 248 */         return;
/*     */       } catch (Exception localException2) {
/* 250 */         paramList.add(invalidMessage(localException2));
/* 251 */         return;
/*     */       }
/*     */     
/*     */     case READ_VARIABLE_LENGTH_CONTENT: 
/* 255 */       i = Math.min(actualReadableBytes(), this.maxChunkSize);
/* 256 */       if (i > 0) {
/* 257 */         ByteBuf localByteBuf1 = ByteBufUtil.readBytes(paramChannelHandlerContext.alloc(), paramByteBuf, i);
/* 258 */         if (paramByteBuf.isReadable()) {
/* 259 */           paramList.add(new DefaultHttpContent(localByteBuf1));
/*     */         }
/*     */         else {
/* 262 */           paramList.add(new DefaultLastHttpContent(localByteBuf1, this.validateHeaders));
/* 263 */           reset();
/*     */         }
/* 265 */       } else if (!paramByteBuf.isReadable())
/*     */       {
/* 267 */         paramList.add(LastHttpContent.EMPTY_LAST_CONTENT);
/* 268 */         reset();
/*     */       }
/* 270 */       return;
/*     */     
/*     */     case READ_FIXED_LENGTH_CONTENT: 
/* 273 */       i = actualReadableBytes();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 281 */       if (i == 0) {
/* 282 */         return;
/*     */       }
/*     */       
/* 285 */       m = Math.min(i, this.maxChunkSize);
/* 286 */       if (m > this.chunkSize) {
/* 287 */         m = (int)this.chunkSize;
/*     */       }
/* 289 */       ByteBuf localByteBuf2 = ByteBufUtil.readBytes(paramChannelHandlerContext.alloc(), paramByteBuf, m);
/* 290 */       this.chunkSize -= m;
/*     */       
/* 292 */       if (this.chunkSize == 0L)
/*     */       {
/* 294 */         paramList.add(new DefaultLastHttpContent(localByteBuf2, this.validateHeaders));
/* 295 */         reset();
/*     */       } else {
/* 297 */         paramList.add(new DefaultHttpContent(localByteBuf2));
/*     */       }
/* 299 */       return;
/*     */     
/*     */ 
/*     */ 
/*     */     case READ_CHUNK_SIZE: 
/*     */       try
/*     */       {
/* 306 */         AppendableCharSequence localAppendableCharSequence = this.lineParser.parse(paramByteBuf);
/* 307 */         m = getChunkSize(localAppendableCharSequence.toString());
/* 308 */         this.chunkSize = m;
/* 309 */         if (m == 0) {
/* 310 */           checkpoint(State.READ_CHUNK_FOOTER);
/* 311 */           return;
/*     */         }
/* 313 */         checkpoint(State.READ_CHUNKED_CONTENT);
/*     */       }
/*     */       catch (Exception localException3) {
/* 316 */         paramList.add(invalidChunk(localException3));
/* 317 */         return;
/*     */       }
/*     */     case READ_CHUNKED_CONTENT: 
/* 320 */       assert (this.chunkSize <= 2147483647L);
/* 321 */       j = Math.min((int)this.chunkSize, this.maxChunkSize);
/*     */       
/* 323 */       DefaultHttpContent localDefaultHttpContent = new DefaultHttpContent(ByteBufUtil.readBytes(paramChannelHandlerContext.alloc(), paramByteBuf, j));
/* 324 */       this.chunkSize -= j;
/*     */       
/* 326 */       paramList.add(localDefaultHttpContent);
/*     */       
/* 328 */       if (this.chunkSize == 0L)
/*     */       {
/* 330 */         checkpoint(State.READ_CHUNK_DELIMITER);
/*     */       } else {
/*     */         return;
/*     */       }
/*     */     case READ_CHUNK_DELIMITER: 
/*     */       for (;;)
/*     */       {
/* 337 */         j = paramByteBuf.readByte();
/* 338 */         if (j == 13) {
/* 339 */           if (paramByteBuf.readByte() == 10) {
/* 340 */             checkpoint(State.READ_CHUNK_SIZE);
/*     */           }
/*     */         } else {
/* 343 */           if (j == 10) {
/* 344 */             checkpoint(State.READ_CHUNK_SIZE);
/* 345 */             return;
/*     */           }
/* 347 */           checkpoint();
/*     */         }
/*     */       }
/*     */     case READ_CHUNK_FOOTER: 
/*     */       try {
/* 352 */         LastHttpContent localLastHttpContent = readTrailingHeaders(paramByteBuf);
/* 353 */         paramList.add(localLastHttpContent);
/* 354 */         reset();
/* 355 */         return;
/*     */       } catch (Exception localException4) {
/* 357 */         paramList.add(invalidChunk(localException4));
/* 358 */         return;
/*     */       }
/*     */     
/*     */     case BAD_MESSAGE: 
/* 362 */       paramByteBuf.skipBytes(actualReadableBytes());
/* 363 */       break;
/*     */     
/*     */     case UPGRADED: 
/* 366 */       int k = actualReadableBytes();
/* 367 */       if (k > 0)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 372 */         paramList.add(paramByteBuf.readBytes(actualReadableBytes()));
/*     */       }
/*     */       break;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void decodeLast(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*     */     throws Exception
/*     */   {
/* 381 */     decode(paramChannelHandlerContext, paramByteBuf, paramList);
/*     */     
/*     */ 
/* 384 */     if (this.message != null)
/*     */     {
/*     */       int i;
/*     */       
/* 388 */       if (isDecodingRequest())
/*     */       {
/* 390 */         i = 1;
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 395 */         i = contentLength() > 0L ? 1 : 0;
/*     */       }
/* 397 */       reset();
/*     */       
/* 399 */       if (i == 0) {
/* 400 */         paramList.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean isContentAlwaysEmpty(HttpMessage paramHttpMessage) {
/* 406 */     if ((paramHttpMessage instanceof HttpResponse)) {
/* 407 */       HttpResponse localHttpResponse = (HttpResponse)paramHttpMessage;
/* 408 */       int i = localHttpResponse.getStatus().code();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 415 */       if ((i >= 100) && (i < 200))
/*     */       {
/* 417 */         return (i != 101) || (localHttpResponse.headers().contains("Sec-WebSocket-Accept"));
/*     */       }
/*     */       
/* 420 */       switch (i) {
/*     */       case 204: case 205: case 304: 
/* 422 */         return true;
/*     */       }
/*     */     }
/* 425 */     return false;
/*     */   }
/*     */   
/*     */   private void reset() {
/* 429 */     HttpMessage localHttpMessage = this.message;
/* 430 */     this.message = null;
/* 431 */     this.contentLength = Long.MIN_VALUE;
/* 432 */     if (!isDecodingRequest()) {
/* 433 */       HttpResponse localHttpResponse = (HttpResponse)localHttpMessage;
/* 434 */       if ((localHttpResponse != null) && (localHttpResponse.getStatus().code() == 101)) {
/* 435 */         checkpoint(State.UPGRADED);
/* 436 */         return;
/*     */       }
/*     */     }
/*     */     
/* 440 */     checkpoint(State.SKIP_CONTROL_CHARS);
/*     */   }
/*     */   
/*     */   private HttpMessage invalidMessage(Exception paramException) {
/* 444 */     checkpoint(State.BAD_MESSAGE);
/* 445 */     if (this.message != null) {
/* 446 */       this.message.setDecoderResult(DecoderResult.failure(paramException));
/*     */     } else {
/* 448 */       this.message = createInvalidMessage();
/* 449 */       this.message.setDecoderResult(DecoderResult.failure(paramException));
/*     */     }
/*     */     
/* 452 */     HttpMessage localHttpMessage = this.message;
/* 453 */     this.message = null;
/* 454 */     return localHttpMessage;
/*     */   }
/*     */   
/*     */   private HttpContent invalidChunk(Exception paramException) {
/* 458 */     checkpoint(State.BAD_MESSAGE);
/* 459 */     DefaultLastHttpContent localDefaultLastHttpContent = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
/* 460 */     localDefaultLastHttpContent.setDecoderResult(DecoderResult.failure(paramException));
/* 461 */     this.message = null;
/* 462 */     return localDefaultLastHttpContent;
/*     */   }
/*     */   
/*     */   private static void skipControlCharacters(ByteBuf paramByteBuf) {
/*     */     for (;;) {
/* 467 */       char c = (char)paramByteBuf.readUnsignedByte();
/* 468 */       if ((!Character.isISOControl(c)) && (!Character.isWhitespace(c)))
/*     */       {
/* 470 */         paramByteBuf.readerIndex(paramByteBuf.readerIndex() - 1);
/* 471 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private State readHeaders(ByteBuf paramByteBuf) {
/* 477 */     this.headerSize = 0;
/* 478 */     HttpMessage localHttpMessage = this.message;
/* 479 */     HttpHeaders localHttpHeaders = localHttpMessage.headers();
/*     */     
/* 481 */     AppendableCharSequence localAppendableCharSequence = this.headerParser.parse(paramByteBuf);
/* 482 */     String str1 = null;
/* 483 */     String str2 = null;
/* 484 */     if (localAppendableCharSequence.length() > 0) {
/* 485 */       localHttpHeaders.clear();
/*     */       do {
/* 487 */         int i = localAppendableCharSequence.charAt(0);
/* 488 */         if ((str1 != null) && ((i == 32) || (i == 9))) {
/* 489 */           str2 = str2 + ' ' + localAppendableCharSequence.toString().trim();
/*     */         } else {
/* 491 */           if (str1 != null) {
/* 492 */             localHttpHeaders.add(str1, str2);
/*     */           }
/* 494 */           String[] arrayOfString = splitHeader(localAppendableCharSequence);
/* 495 */           str1 = arrayOfString[0];
/* 496 */           str2 = arrayOfString[1];
/*     */         }
/*     */         
/* 499 */         localAppendableCharSequence = this.headerParser.parse(paramByteBuf);
/* 500 */       } while (localAppendableCharSequence.length() > 0);
/*     */       
/*     */ 
/* 503 */       if (str1 != null) {
/* 504 */         localHttpHeaders.add(str1, str2);
/*     */       }
/*     */     }
/*     */     
/*     */     State localState;
/*     */     
/* 510 */     if (isContentAlwaysEmpty(localHttpMessage)) {
/* 511 */       HttpHeaders.removeTransferEncodingChunked(localHttpMessage);
/* 512 */       localState = State.SKIP_CONTROL_CHARS;
/* 513 */     } else if (HttpHeaders.isTransferEncodingChunked(localHttpMessage)) {
/* 514 */       localState = State.READ_CHUNK_SIZE;
/* 515 */     } else if (contentLength() >= 0L) {
/* 516 */       localState = State.READ_FIXED_LENGTH_CONTENT;
/*     */     } else {
/* 518 */       localState = State.READ_VARIABLE_LENGTH_CONTENT;
/*     */     }
/* 520 */     return localState;
/*     */   }
/*     */   
/*     */   private long contentLength() {
/* 524 */     if (this.contentLength == Long.MIN_VALUE) {
/* 525 */       this.contentLength = HttpHeaders.getContentLength(this.message, -1L);
/*     */     }
/* 527 */     return this.contentLength;
/*     */   }
/*     */   
/*     */   private LastHttpContent readTrailingHeaders(ByteBuf paramByteBuf) {
/* 531 */     this.headerSize = 0;
/* 532 */     AppendableCharSequence localAppendableCharSequence = this.headerParser.parse(paramByteBuf);
/* 533 */     Object localObject1 = null;
/* 534 */     if (localAppendableCharSequence.length() > 0) {
/* 535 */       DefaultLastHttpContent localDefaultLastHttpContent = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER, this.validateHeaders);
/*     */       do {
/* 537 */         int i = localAppendableCharSequence.charAt(0);
/* 538 */         Object localObject2; if ((localObject1 != null) && ((i == 32) || (i == 9))) {
/* 539 */           localObject2 = localDefaultLastHttpContent.trailingHeaders().getAll((String)localObject1);
/* 540 */           if (!((List)localObject2).isEmpty()) {
/* 541 */             int j = ((List)localObject2).size() - 1;
/* 542 */             String str = (String)((List)localObject2).get(j) + localAppendableCharSequence.toString().trim();
/* 543 */             ((List)localObject2).set(j, str);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 548 */           localObject2 = splitHeader(localAppendableCharSequence);
/* 549 */           CharSequence localCharSequence = localObject2[0];
/* 550 */           if ((!HttpHeaders.equalsIgnoreCase(localCharSequence, "Content-Length")) && (!HttpHeaders.equalsIgnoreCase(localCharSequence, "Transfer-Encoding")) && (!HttpHeaders.equalsIgnoreCase(localCharSequence, "Trailer")))
/*     */           {
/*     */ 
/* 553 */             localDefaultLastHttpContent.trailingHeaders().add(localCharSequence, localObject2[1]);
/*     */           }
/* 555 */           localObject1 = localCharSequence;
/*     */         }
/*     */         
/* 558 */         localAppendableCharSequence = this.headerParser.parse(paramByteBuf);
/* 559 */       } while (localAppendableCharSequence.length() > 0);
/*     */       
/* 561 */       return localDefaultLastHttpContent;
/*     */     }
/*     */     
/* 564 */     return LastHttpContent.EMPTY_LAST_CONTENT; }
/*     */   
/*     */   protected abstract boolean isDecodingRequest();
/*     */   
/*     */   protected abstract HttpMessage createMessage(String[] paramArrayOfString) throws Exception;
/*     */   
/*     */   protected abstract HttpMessage createInvalidMessage();
/*     */   
/* 572 */   private static int getChunkSize(String paramString) { paramString = paramString.trim();
/* 573 */     for (int i = 0; i < paramString.length(); i++) {
/* 574 */       char c = paramString.charAt(i);
/* 575 */       if ((c == ';') || (Character.isWhitespace(c)) || (Character.isISOControl(c))) {
/* 576 */         paramString = paramString.substring(0, i);
/* 577 */         break;
/*     */       }
/*     */     }
/*     */     
/* 581 */     return Integer.parseInt(paramString, 16);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String[] splitInitialLine(AppendableCharSequence paramAppendableCharSequence)
/*     */   {
/* 592 */     int i = findNonWhitespace(paramAppendableCharSequence, 0);
/* 593 */     int j = findWhitespace(paramAppendableCharSequence, i);
/*     */     
/* 595 */     int k = findNonWhitespace(paramAppendableCharSequence, j);
/* 596 */     int m = findWhitespace(paramAppendableCharSequence, k);
/*     */     
/* 598 */     int n = findNonWhitespace(paramAppendableCharSequence, m);
/* 599 */     int i1 = findEndOfString(paramAppendableCharSequence);
/*     */     
/* 601 */     return new String[] { paramAppendableCharSequence.substring(i, j), paramAppendableCharSequence.substring(k, m), n < i1 ? paramAppendableCharSequence.substring(n, i1) : "" };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static String[] splitHeader(AppendableCharSequence paramAppendableCharSequence)
/*     */   {
/* 608 */     int i = paramAppendableCharSequence.length();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 615 */     int j = findNonWhitespace(paramAppendableCharSequence, 0);
/* 616 */     for (int k = j; k < i; k++) {
/* 617 */       char c = paramAppendableCharSequence.charAt(k);
/* 618 */       if ((c == ':') || (Character.isWhitespace(c))) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/* 623 */     for (int m = k; m < i; m++) {
/* 624 */       if (paramAppendableCharSequence.charAt(m) == ':') {
/* 625 */         m++;
/* 626 */         break;
/*     */       }
/*     */     }
/*     */     
/* 630 */     int n = findNonWhitespace(paramAppendableCharSequence, m);
/* 631 */     if (n == i) {
/* 632 */       return new String[] { paramAppendableCharSequence.substring(j, k), "" };
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 638 */     int i1 = findEndOfString(paramAppendableCharSequence);
/* 639 */     return new String[] { paramAppendableCharSequence.substring(j, k), paramAppendableCharSequence.substring(n, i1) };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int findNonWhitespace(CharSequence paramCharSequence, int paramInt)
/*     */   {
/* 647 */     for (int i = paramInt; i < paramCharSequence.length(); i++) {
/* 648 */       if (!Character.isWhitespace(paramCharSequence.charAt(i))) {
/*     */         break;
/*     */       }
/*     */     }
/* 652 */     return i;
/*     */   }
/*     */   
/*     */   private static int findWhitespace(CharSequence paramCharSequence, int paramInt)
/*     */   {
/* 657 */     for (int i = paramInt; i < paramCharSequence.length(); i++) {
/* 658 */       if (Character.isWhitespace(paramCharSequence.charAt(i))) {
/*     */         break;
/*     */       }
/*     */     }
/* 662 */     return i;
/*     */   }
/*     */   
/*     */   private static int findEndOfString(CharSequence paramCharSequence)
/*     */   {
/* 667 */     for (int i = paramCharSequence.length(); i > 0; i--) {
/* 668 */       if (!Character.isWhitespace(paramCharSequence.charAt(i - 1))) {
/*     */         break;
/*     */       }
/*     */     }
/* 672 */     return i;
/*     */   }
/*     */   
/*     */   private final class HeaderParser implements ByteBufProcessor {
/*     */     private final AppendableCharSequence seq;
/*     */     
/*     */     HeaderParser(AppendableCharSequence paramAppendableCharSequence) {
/* 679 */       this.seq = paramAppendableCharSequence;
/*     */     }
/*     */     
/*     */     public AppendableCharSequence parse(ByteBuf paramByteBuf) {
/* 683 */       this.seq.reset();
/* 684 */       HttpObjectDecoder.this.headerSize = 0;
/* 685 */       int i = paramByteBuf.forEachByte(this);
/* 686 */       paramByteBuf.readerIndex(i + 1);
/* 687 */       return this.seq;
/*     */     }
/*     */     
/*     */     public boolean process(byte paramByte) throws Exception
/*     */     {
/* 692 */       char c = (char)paramByte;
/* 693 */       HttpObjectDecoder.access$008(HttpObjectDecoder.this);
/* 694 */       if (c == '\r') {
/* 695 */         return true;
/*     */       }
/* 697 */       if (c == '\n') {
/* 698 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 702 */       if (HttpObjectDecoder.this.headerSize >= HttpObjectDecoder.this.maxHeaderSize)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 707 */         throw new TooLongFrameException("HTTP header is larger than " + HttpObjectDecoder.this.maxHeaderSize + " bytes.");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 712 */       this.seq.append(c);
/* 713 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class LineParser implements ByteBufProcessor {
/*     */     private final AppendableCharSequence seq;
/*     */     private int size;
/*     */     
/*     */     LineParser(AppendableCharSequence paramAppendableCharSequence) {
/* 722 */       this.seq = paramAppendableCharSequence;
/*     */     }
/*     */     
/*     */     public AppendableCharSequence parse(ByteBuf paramByteBuf) {
/* 726 */       this.seq.reset();
/* 727 */       this.size = 0;
/* 728 */       int i = paramByteBuf.forEachByte(this);
/* 729 */       paramByteBuf.readerIndex(i + 1);
/* 730 */       return this.seq;
/*     */     }
/*     */     
/*     */     public boolean process(byte paramByte) throws Exception
/*     */     {
/* 735 */       char c = (char)paramByte;
/* 736 */       if (c == '\r')
/* 737 */         return true;
/* 738 */       if (c == '\n') {
/* 739 */         return false;
/*     */       }
/* 741 */       if (this.size >= HttpObjectDecoder.this.maxInitialLineLength)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 746 */         throw new TooLongFrameException("An HTTP line is larger than " + HttpObjectDecoder.this.maxInitialLineLength + " bytes.");
/*     */       }
/*     */       
/*     */ 
/* 750 */       this.size += 1;
/* 751 */       this.seq.append(c);
/* 752 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpObjectDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */