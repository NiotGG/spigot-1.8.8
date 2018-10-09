/*      */ package io.netty.handler.codec.http.multipart;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.handler.codec.DecoderException;
/*      */ import io.netty.handler.codec.http.HttpConstants;
/*      */ import io.netty.handler.codec.http.HttpContent;
/*      */ import io.netty.handler.codec.http.HttpHeaders;
/*      */ import io.netty.handler.codec.http.HttpMethod;
/*      */ import io.netty.handler.codec.http.HttpRequest;
/*      */ import io.netty.handler.codec.http.LastHttpContent;
/*      */ import io.netty.handler.codec.http.QueryStringDecoder;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import java.io.IOException;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TreeMap;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HttpPostRequestDecoder
/*      */ {
/*      */   private static final int DEFAULT_DISCARD_THRESHOLD = 10485760;
/*      */   private final HttpDataFactory factory;
/*      */   private final HttpRequest request;
/*      */   private final Charset charset;
/*      */   private boolean bodyToDecode;
/*      */   private boolean isLastChunk;
/*   78 */   private final List<InterfaceHttpData> bodyListHttpData = new ArrayList();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   83 */   private final Map<String, List<InterfaceHttpData>> bodyMapHttpData = new TreeMap(CaseIgnoringComparator.INSTANCE);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ByteBuf undecodedChunk;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isMultipart;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int bodyListHttpDataRank;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String multipartDataBoundary;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String multipartMixedBoundary;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  115 */   private MultiPartStatus currentStatus = MultiPartStatus.NOTSTARTED;
/*      */   
/*      */ 
/*      */ 
/*      */   private Map<String, Attribute> currentFieldAttributes;
/*      */   
/*      */ 
/*      */ 
/*      */   private FileUpload currentFileUpload;
/*      */   
/*      */ 
/*      */ 
/*      */   private Attribute currentAttribute;
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean destroyed;
/*      */   
/*      */ 
/*  134 */   private int discardThreshold = 10485760;
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
/*      */   public HttpPostRequestDecoder(HttpRequest paramHttpRequest)
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException, HttpPostRequestDecoder.IncompatibleDataDecoderException
/*      */   {
/*  150 */     this(new DefaultHttpDataFactory(16384L), paramHttpRequest, HttpConstants.DEFAULT_CHARSET);
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
/*      */   public HttpPostRequestDecoder(HttpDataFactory paramHttpDataFactory, HttpRequest paramHttpRequest)
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException, HttpPostRequestDecoder.IncompatibleDataDecoderException
/*      */   {
/*  169 */     this(paramHttpDataFactory, paramHttpRequest, HttpConstants.DEFAULT_CHARSET);
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
/*      */ 
/*      */   public HttpPostRequestDecoder(HttpDataFactory paramHttpDataFactory, HttpRequest paramHttpRequest, Charset paramCharset)
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException, HttpPostRequestDecoder.IncompatibleDataDecoderException
/*      */   {
/*  190 */     if (paramHttpDataFactory == null) {
/*  191 */       throw new NullPointerException("factory");
/*      */     }
/*  193 */     if (paramHttpRequest == null) {
/*  194 */       throw new NullPointerException("request");
/*      */     }
/*  196 */     if (paramCharset == null) {
/*  197 */       throw new NullPointerException("charset");
/*      */     }
/*  199 */     this.request = paramHttpRequest;
/*  200 */     HttpMethod localHttpMethod = paramHttpRequest.getMethod();
/*  201 */     if ((localHttpMethod.equals(HttpMethod.POST)) || (localHttpMethod.equals(HttpMethod.PUT)) || (localHttpMethod.equals(HttpMethod.PATCH))) {
/*  202 */       this.bodyToDecode = true;
/*      */     }
/*  204 */     this.charset = paramCharset;
/*  205 */     this.factory = paramHttpDataFactory;
/*      */     
/*      */ 
/*  208 */     String str = this.request.headers().get("Content-Type");
/*  209 */     if (str != null) {
/*  210 */       checkMultipart(str);
/*      */     } else {
/*  212 */       this.isMultipart = false;
/*      */     }
/*  214 */     if (!this.bodyToDecode) {
/*  215 */       throw new IncompatibleDataDecoderException("No Body to decode");
/*      */     }
/*  217 */     if ((paramHttpRequest instanceof HttpContent))
/*      */     {
/*      */ 
/*  220 */       offer((HttpContent)paramHttpRequest);
/*      */     } else {
/*  222 */       this.undecodedChunk = Unpooled.buffer();
/*  223 */       parseBody();
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
/*      */   private static enum MultiPartStatus
/*      */   {
/*  258 */     NOTSTARTED,  PREAMBLE,  HEADERDELIMITER,  DISPOSITION,  FIELD,  FILEUPLOAD,  MIXEDPREAMBLE,  MIXEDDELIMITER, 
/*  259 */     MIXEDDISPOSITION,  MIXEDFILEUPLOAD,  MIXEDCLOSEDELIMITER,  CLOSEDELIMITER,  PREEPILOGUE,  EPILOGUE;
/*      */     
/*      */ 
/*      */     private MultiPartStatus() {}
/*      */   }
/*      */   
/*      */   private void checkMultipart(String paramString)
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/*  268 */     String[] arrayOfString1 = splitHeaderContentType(paramString);
/*  269 */     if ((arrayOfString1[0].toLowerCase().startsWith("multipart/form-data")) && (arrayOfString1[1].toLowerCase().startsWith("boundary")))
/*      */     {
/*  271 */       String[] arrayOfString2 = StringUtil.split(arrayOfString1[1], '=');
/*  272 */       if (arrayOfString2.length != 2) {
/*  273 */         throw new ErrorDataDecoderException("Needs a boundary value");
/*      */       }
/*  275 */       if (arrayOfString2[1].charAt(0) == '"') {
/*  276 */         String str = arrayOfString2[1].trim();
/*  277 */         int i = str.length() - 1;
/*  278 */         if (str.charAt(i) == '"') {
/*  279 */           arrayOfString2[1] = str.substring(1, i);
/*      */         }
/*      */       }
/*  282 */       this.multipartDataBoundary = ("--" + arrayOfString2[1]);
/*  283 */       this.isMultipart = true;
/*  284 */       this.currentStatus = MultiPartStatus.HEADERDELIMITER;
/*      */     } else {
/*  286 */       this.isMultipart = false;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkDestroyed() {
/*  291 */     if (this.destroyed) {
/*  292 */       throw new IllegalStateException(HttpPostRequestDecoder.class.getSimpleName() + " was destroyed already");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isMultipart()
/*      */   {
/*  302 */     checkDestroyed();
/*  303 */     return this.isMultipart;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDiscardThreshold(int paramInt)
/*      */   {
/*  312 */     if (paramInt < 0) {
/*  313 */       throw new IllegalArgumentException("discardThreshold must be >= 0");
/*      */     }
/*  315 */     this.discardThreshold = paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getDiscardThreshold()
/*      */   {
/*  322 */     return this.discardThreshold;
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
/*      */   public List<InterfaceHttpData> getBodyHttpDatas()
/*      */     throws HttpPostRequestDecoder.NotEnoughDataDecoderException
/*      */   {
/*  336 */     checkDestroyed();
/*      */     
/*  338 */     if (!this.isLastChunk) {
/*  339 */       throw new NotEnoughDataDecoderException();
/*      */     }
/*  341 */     return this.bodyListHttpData;
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
/*      */   public List<InterfaceHttpData> getBodyHttpDatas(String paramString)
/*      */     throws HttpPostRequestDecoder.NotEnoughDataDecoderException
/*      */   {
/*  356 */     checkDestroyed();
/*      */     
/*  358 */     if (!this.isLastChunk) {
/*  359 */       throw new NotEnoughDataDecoderException();
/*      */     }
/*  361 */     return (List)this.bodyMapHttpData.get(paramString);
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
/*      */   public InterfaceHttpData getBodyHttpData(String paramString)
/*      */     throws HttpPostRequestDecoder.NotEnoughDataDecoderException
/*      */   {
/*  377 */     checkDestroyed();
/*      */     
/*  379 */     if (!this.isLastChunk) {
/*  380 */       throw new NotEnoughDataDecoderException();
/*      */     }
/*  382 */     List localList = (List)this.bodyMapHttpData.get(paramString);
/*  383 */     if (localList != null) {
/*  384 */       return (InterfaceHttpData)localList.get(0);
/*      */     }
/*  386 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HttpPostRequestDecoder offer(HttpContent paramHttpContent)
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/*  399 */     checkDestroyed();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  404 */     ByteBuf localByteBuf = paramHttpContent.content();
/*  405 */     if (this.undecodedChunk == null) {
/*  406 */       this.undecodedChunk = localByteBuf.copy();
/*      */     } else {
/*  408 */       this.undecodedChunk.writeBytes(localByteBuf);
/*      */     }
/*  410 */     if ((paramHttpContent instanceof LastHttpContent)) {
/*  411 */       this.isLastChunk = true;
/*      */     }
/*  413 */     parseBody();
/*  414 */     if ((this.undecodedChunk != null) && (this.undecodedChunk.writerIndex() > this.discardThreshold)) {
/*  415 */       this.undecodedChunk.discardReadBytes();
/*      */     }
/*  417 */     return this;
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
/*      */   public boolean hasNext()
/*      */     throws HttpPostRequestDecoder.EndOfDataDecoderException
/*      */   {
/*  431 */     checkDestroyed();
/*      */     
/*  433 */     if (this.currentStatus == MultiPartStatus.EPILOGUE)
/*      */     {
/*  435 */       if (this.bodyListHttpDataRank >= this.bodyListHttpData.size()) {
/*  436 */         throw new EndOfDataDecoderException();
/*      */       }
/*      */     }
/*  439 */     return (!this.bodyListHttpData.isEmpty()) && (this.bodyListHttpDataRank < this.bodyListHttpData.size());
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
/*      */   public InterfaceHttpData next()
/*      */     throws HttpPostRequestDecoder.EndOfDataDecoderException
/*      */   {
/*  455 */     checkDestroyed();
/*      */     
/*  457 */     if (hasNext()) {
/*  458 */       return (InterfaceHttpData)this.bodyListHttpData.get(this.bodyListHttpDataRank++);
/*      */     }
/*  460 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void parseBody()
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/*  471 */     if ((this.currentStatus == MultiPartStatus.PREEPILOGUE) || (this.currentStatus == MultiPartStatus.EPILOGUE)) {
/*  472 */       if (this.isLastChunk) {
/*  473 */         this.currentStatus = MultiPartStatus.EPILOGUE;
/*      */       }
/*  475 */       return;
/*      */     }
/*  477 */     if (this.isMultipart) {
/*  478 */       parseBodyMultipart();
/*      */     } else {
/*  480 */       parseBodyAttributes();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void addHttpData(InterfaceHttpData paramInterfaceHttpData)
/*      */   {
/*  488 */     if (paramInterfaceHttpData == null) {
/*  489 */       return;
/*      */     }
/*  491 */     Object localObject = (List)this.bodyMapHttpData.get(paramInterfaceHttpData.getName());
/*  492 */     if (localObject == null) {
/*  493 */       localObject = new ArrayList(1);
/*  494 */       this.bodyMapHttpData.put(paramInterfaceHttpData.getName(), localObject);
/*      */     }
/*  496 */     ((List)localObject).add(paramInterfaceHttpData);
/*  497 */     this.bodyListHttpData.add(paramInterfaceHttpData);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void parseBodyAttributesStandard()
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/*  509 */     int i = this.undecodedChunk.readerIndex();
/*  510 */     int j = i;
/*      */     
/*      */ 
/*  513 */     if (this.currentStatus == MultiPartStatus.NOTSTARTED) {
/*  514 */       this.currentStatus = MultiPartStatus.DISPOSITION;
/*      */     }
/*  516 */     int k = 1;
/*      */     try { int i1;
/*  518 */       while ((this.undecodedChunk.isReadable()) && (k != 0)) {
/*  519 */         int m = (char)this.undecodedChunk.readUnsignedByte();
/*  520 */         j++;
/*  521 */         switch (this.currentStatus) {
/*      */         case DISPOSITION:  String str;
/*  523 */           if (m == 61) {
/*  524 */             this.currentStatus = MultiPartStatus.FIELD;
/*  525 */             int n = j - 1;
/*  526 */             str = decodeAttribute(this.undecodedChunk.toString(i, n - i, this.charset), this.charset);
/*      */             
/*  528 */             this.currentAttribute = this.factory.createAttribute(this.request, str);
/*  529 */             i = j;
/*  530 */           } else if (m == 38) {
/*  531 */             this.currentStatus = MultiPartStatus.DISPOSITION;
/*  532 */             i1 = j - 1;
/*  533 */             str = decodeAttribute(this.undecodedChunk.toString(i, i1 - i, this.charset), this.charset);
/*      */             
/*  535 */             this.currentAttribute = this.factory.createAttribute(this.request, str);
/*  536 */             this.currentAttribute.setValue("");
/*  537 */             addHttpData(this.currentAttribute);
/*  538 */             this.currentAttribute = null;
/*  539 */             i = j;
/*  540 */             k = 1; }
/*  541 */           break;
/*      */         
/*      */         case FIELD: 
/*  544 */           if (m == 38) {
/*  545 */             this.currentStatus = MultiPartStatus.DISPOSITION;
/*  546 */             i1 = j - 1;
/*  547 */             setFinalBuffer(this.undecodedChunk.copy(i, i1 - i));
/*  548 */             i = j;
/*  549 */             k = 1;
/*  550 */           } else if (m == 13) {
/*  551 */             if (this.undecodedChunk.isReadable()) {
/*  552 */               m = (char)this.undecodedChunk.readUnsignedByte();
/*  553 */               j++;
/*  554 */               if (m == 10) {
/*  555 */                 this.currentStatus = MultiPartStatus.PREEPILOGUE;
/*  556 */                 i1 = j - 2;
/*  557 */                 setFinalBuffer(this.undecodedChunk.copy(i, i1 - i));
/*  558 */                 i = j;
/*  559 */                 k = 0;
/*      */               }
/*      */               else {
/*  562 */                 throw new ErrorDataDecoderException("Bad end of line");
/*      */               }
/*      */             } else {
/*  565 */               j--;
/*      */             }
/*  567 */           } else if (m == 10) {
/*  568 */             this.currentStatus = MultiPartStatus.PREEPILOGUE;
/*  569 */             i1 = j - 1;
/*  570 */             setFinalBuffer(this.undecodedChunk.copy(i, i1 - i));
/*  571 */             i = j;
/*  572 */             k = 0;
/*      */           }
/*      */           
/*      */           break;
/*      */         default: 
/*  577 */           k = 0;
/*      */         }
/*      */       }
/*  580 */       if ((this.isLastChunk) && (this.currentAttribute != null))
/*      */       {
/*  582 */         i1 = j;
/*  583 */         if (i1 > i) {
/*  584 */           setFinalBuffer(this.undecodedChunk.copy(i, i1 - i));
/*  585 */         } else if (!this.currentAttribute.isCompleted()) {
/*  586 */           setFinalBuffer(Unpooled.EMPTY_BUFFER);
/*      */         }
/*  588 */         i = j;
/*  589 */         this.currentStatus = MultiPartStatus.EPILOGUE;
/*  590 */         this.undecodedChunk.readerIndex(i);
/*  591 */         return;
/*      */       }
/*  593 */       if ((k != 0) && (this.currentAttribute != null))
/*      */       {
/*  595 */         if (this.currentStatus == MultiPartStatus.FIELD) {
/*  596 */           this.currentAttribute.addContent(this.undecodedChunk.copy(i, j - i), false);
/*      */           
/*  598 */           i = j;
/*      */         }
/*  600 */         this.undecodedChunk.readerIndex(i);
/*      */       }
/*      */       else {
/*  603 */         this.undecodedChunk.readerIndex(i);
/*      */       }
/*      */     }
/*      */     catch (ErrorDataDecoderException localErrorDataDecoderException) {
/*  607 */       this.undecodedChunk.readerIndex(i);
/*  608 */       throw localErrorDataDecoderException;
/*      */     }
/*      */     catch (IOException localIOException) {
/*  611 */       this.undecodedChunk.readerIndex(i);
/*  612 */       throw new ErrorDataDecoderException(localIOException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void parseBodyAttributes()
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/*      */     HttpPostBodyUtil.SeekAheadOptimize localSeekAheadOptimize;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  627 */       localSeekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/*      */     } catch (HttpPostBodyUtil.SeekAheadNoBackArrayException localSeekAheadNoBackArrayException) {
/*  629 */       parseBodyAttributesStandard();
/*  630 */       return;
/*      */     }
/*  632 */     int i = this.undecodedChunk.readerIndex();
/*  633 */     int j = i;
/*      */     
/*      */ 
/*  636 */     if (this.currentStatus == MultiPartStatus.NOTSTARTED) {
/*  637 */       this.currentStatus = MultiPartStatus.DISPOSITION;
/*      */     }
/*  639 */     int k = 1;
/*      */     try { int i1;
/*  641 */       while (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/*  642 */         int m = (char)(localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)] & 0xFF);
/*  643 */         j++;
/*  644 */         switch (this.currentStatus) {
/*      */         case DISPOSITION:  String str;
/*  646 */           if (m == 61) {
/*  647 */             this.currentStatus = MultiPartStatus.FIELD;
/*  648 */             int n = j - 1;
/*  649 */             str = decodeAttribute(this.undecodedChunk.toString(i, n - i, this.charset), this.charset);
/*      */             
/*  651 */             this.currentAttribute = this.factory.createAttribute(this.request, str);
/*  652 */             i = j;
/*  653 */           } else if (m == 38) {
/*  654 */             this.currentStatus = MultiPartStatus.DISPOSITION;
/*  655 */             i1 = j - 1;
/*  656 */             str = decodeAttribute(this.undecodedChunk.toString(i, i1 - i, this.charset), this.charset);
/*      */             
/*  658 */             this.currentAttribute = this.factory.createAttribute(this.request, str);
/*  659 */             this.currentAttribute.setValue("");
/*  660 */             addHttpData(this.currentAttribute);
/*  661 */             this.currentAttribute = null;
/*  662 */             i = j;
/*  663 */             k = 1; }
/*  664 */           break;
/*      */         
/*      */         case FIELD: 
/*  667 */           if (m == 38) {
/*  668 */             this.currentStatus = MultiPartStatus.DISPOSITION;
/*  669 */             i1 = j - 1;
/*  670 */             setFinalBuffer(this.undecodedChunk.copy(i, i1 - i));
/*  671 */             i = j;
/*  672 */             k = 1;
/*  673 */           } else if (m == 13) {
/*  674 */             if (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/*  675 */               m = (char)(localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)] & 0xFF);
/*  676 */               j++;
/*  677 */               if (m == 10) {
/*  678 */                 this.currentStatus = MultiPartStatus.PREEPILOGUE;
/*  679 */                 i1 = j - 2;
/*  680 */                 localSeekAheadOptimize.setReadPosition(0);
/*  681 */                 setFinalBuffer(this.undecodedChunk.copy(i, i1 - i));
/*  682 */                 i = j;
/*  683 */                 k = 0;
/*      */                 
/*      */                 break label514;
/*      */               }
/*  687 */               localSeekAheadOptimize.setReadPosition(0);
/*  688 */               throw new ErrorDataDecoderException("Bad end of line");
/*      */             }
/*      */             
/*  691 */             if (localSeekAheadOptimize.limit > 0) {
/*  692 */               j--;
/*      */             }
/*      */           }
/*  695 */           else if (m == 10) {
/*  696 */             this.currentStatus = MultiPartStatus.PREEPILOGUE;
/*  697 */             i1 = j - 1;
/*  698 */             localSeekAheadOptimize.setReadPosition(0);
/*  699 */             setFinalBuffer(this.undecodedChunk.copy(i, i1 - i));
/*  700 */             i = j;
/*  701 */             k = 0; }
/*  702 */           break;
/*      */         
/*      */ 
/*      */ 
/*      */         default: 
/*  707 */           localSeekAheadOptimize.setReadPosition(0);
/*  708 */           k = 0;
/*      */           break label514; }
/*      */       }
/*      */       label514:
/*  712 */       if ((this.isLastChunk) && (this.currentAttribute != null))
/*      */       {
/*  714 */         i1 = j;
/*  715 */         if (i1 > i) {
/*  716 */           setFinalBuffer(this.undecodedChunk.copy(i, i1 - i));
/*  717 */         } else if (!this.currentAttribute.isCompleted()) {
/*  718 */           setFinalBuffer(Unpooled.EMPTY_BUFFER);
/*      */         }
/*  720 */         i = j;
/*  721 */         this.currentStatus = MultiPartStatus.EPILOGUE;
/*  722 */         this.undecodedChunk.readerIndex(i);
/*  723 */         return;
/*      */       }
/*  725 */       if ((k != 0) && (this.currentAttribute != null))
/*      */       {
/*  727 */         if (this.currentStatus == MultiPartStatus.FIELD) {
/*  728 */           this.currentAttribute.addContent(this.undecodedChunk.copy(i, j - i), false);
/*      */           
/*  730 */           i = j;
/*      */         }
/*  732 */         this.undecodedChunk.readerIndex(i);
/*      */       }
/*      */       else {
/*  735 */         this.undecodedChunk.readerIndex(i);
/*      */       }
/*      */     }
/*      */     catch (ErrorDataDecoderException localErrorDataDecoderException) {
/*  739 */       this.undecodedChunk.readerIndex(i);
/*  740 */       throw localErrorDataDecoderException;
/*      */     }
/*      */     catch (IOException localIOException) {
/*  743 */       this.undecodedChunk.readerIndex(i);
/*  744 */       throw new ErrorDataDecoderException(localIOException);
/*      */     }
/*      */   }
/*      */   
/*      */   private void setFinalBuffer(ByteBuf paramByteBuf) throws HttpPostRequestDecoder.ErrorDataDecoderException, IOException {
/*  749 */     this.currentAttribute.addContent(paramByteBuf, true);
/*  750 */     String str = decodeAttribute(this.currentAttribute.getByteBuf().toString(this.charset), this.charset);
/*  751 */     this.currentAttribute.setValue(str);
/*  752 */     addHttpData(this.currentAttribute);
/*  753 */     this.currentAttribute = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static String decodeAttribute(String paramString, Charset paramCharset)
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/*      */     try
/*      */     {
/*  763 */       return QueryStringDecoder.decodeComponent(paramString, paramCharset);
/*      */     } catch (IllegalArgumentException localIllegalArgumentException) {
/*  765 */       throw new ErrorDataDecoderException("Bad string: '" + paramString + '\'', localIllegalArgumentException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void parseBodyMultipart()
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/*  777 */     if ((this.undecodedChunk == null) || (this.undecodedChunk.readableBytes() == 0))
/*      */     {
/*  779 */       return;
/*      */     }
/*  781 */     InterfaceHttpData localInterfaceHttpData = decodeMultipart(this.currentStatus);
/*  782 */     while (localInterfaceHttpData != null) {
/*  783 */       addHttpData(localInterfaceHttpData);
/*  784 */       if ((this.currentStatus == MultiPartStatus.PREEPILOGUE) || (this.currentStatus == MultiPartStatus.EPILOGUE)) {
/*      */         break;
/*      */       }
/*  787 */       localInterfaceHttpData = decodeMultipart(this.currentStatus);
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
/*      */   private InterfaceHttpData decodeMultipart(MultiPartStatus paramMultiPartStatus)
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/*  808 */     switch (paramMultiPartStatus) {
/*      */     case NOTSTARTED: 
/*  810 */       throw new ErrorDataDecoderException("Should not be called with the current getStatus");
/*      */     
/*      */     case PREAMBLE: 
/*  813 */       throw new ErrorDataDecoderException("Should not be called with the current getStatus");
/*      */     
/*      */     case HEADERDELIMITER: 
/*  816 */       return findMultipartDelimiter(this.multipartDataBoundary, MultiPartStatus.DISPOSITION, MultiPartStatus.PREEPILOGUE);
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
/*      */     case DISPOSITION: 
/*  829 */       return findMultipartDisposition();
/*      */     
/*      */ 
/*      */     case FIELD: 
/*  833 */       Charset localCharset = null;
/*  834 */       Attribute localAttribute1 = (Attribute)this.currentFieldAttributes.get("charset");
/*  835 */       if (localAttribute1 != null) {
/*      */         try {
/*  837 */           localCharset = Charset.forName(localAttribute1.getValue());
/*      */         } catch (IOException localIOException1) {
/*  839 */           throw new ErrorDataDecoderException(localIOException1);
/*      */         }
/*      */       }
/*  842 */       Attribute localAttribute2 = (Attribute)this.currentFieldAttributes.get("name");
/*  843 */       if (this.currentAttribute == null) {
/*      */         try {
/*  845 */           this.currentAttribute = this.factory.createAttribute(this.request, cleanString(localAttribute2.getValue()));
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*  848 */           throw new ErrorDataDecoderException(localNullPointerException);
/*      */         } catch (IllegalArgumentException localIllegalArgumentException) {
/*  850 */           throw new ErrorDataDecoderException(localIllegalArgumentException);
/*      */         } catch (IOException localIOException2) {
/*  852 */           throw new ErrorDataDecoderException(localIOException2);
/*      */         }
/*  854 */         if (localCharset != null) {
/*  855 */           this.currentAttribute.setCharset(localCharset);
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/*  860 */         loadFieldMultipart(this.multipartDataBoundary);
/*      */       } catch (NotEnoughDataDecoderException localNotEnoughDataDecoderException) {
/*  862 */         return null;
/*      */       }
/*  864 */       Attribute localAttribute3 = this.currentAttribute;
/*  865 */       this.currentAttribute = null;
/*  866 */       this.currentFieldAttributes = null;
/*      */       
/*  868 */       this.currentStatus = MultiPartStatus.HEADERDELIMITER;
/*  869 */       return localAttribute3;
/*      */     
/*      */ 
/*      */     case FILEUPLOAD: 
/*  873 */       return getFileUpload(this.multipartDataBoundary);
/*      */     
/*      */ 
/*      */ 
/*      */     case MIXEDDELIMITER: 
/*  878 */       return findMultipartDelimiter(this.multipartMixedBoundary, MultiPartStatus.MIXEDDISPOSITION, MultiPartStatus.HEADERDELIMITER);
/*      */     
/*      */ 
/*      */     case MIXEDDISPOSITION: 
/*  882 */       return findMultipartDisposition();
/*      */     
/*      */ 
/*      */     case MIXEDFILEUPLOAD: 
/*  886 */       return getFileUpload(this.multipartMixedBoundary);
/*      */     
/*      */     case PREEPILOGUE: 
/*  889 */       return null;
/*      */     case EPILOGUE: 
/*  891 */       return null;
/*      */     }
/*  893 */     throw new ErrorDataDecoderException("Shouldn't reach here.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void skipControlCharacters()
/*      */     throws HttpPostRequestDecoder.NotEnoughDataDecoderException
/*      */   {
/*      */     HttpPostBodyUtil.SeekAheadOptimize localSeekAheadOptimize;
/*      */     
/*      */     try
/*      */     {
/*  905 */       localSeekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/*      */     } catch (HttpPostBodyUtil.SeekAheadNoBackArrayException localSeekAheadNoBackArrayException) {
/*      */       try {
/*  908 */         skipControlCharactersStandard();
/*      */       } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/*  910 */         throw new NotEnoughDataDecoderException(localIndexOutOfBoundsException);
/*      */       }
/*  912 */       return;
/*      */     }
/*      */     
/*  915 */     while (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/*  916 */       char c = (char)(localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)] & 0xFF);
/*  917 */       if ((!Character.isISOControl(c)) && (!Character.isWhitespace(c))) {
/*  918 */         localSeekAheadOptimize.setReadPosition(1);
/*  919 */         return;
/*      */       }
/*      */     }
/*  922 */     throw new NotEnoughDataDecoderException("Access out of bounds");
/*      */   }
/*      */   
/*      */   void skipControlCharactersStandard() {
/*      */     for (;;) {
/*  927 */       char c = (char)this.undecodedChunk.readUnsignedByte();
/*  928 */       if ((!Character.isISOControl(c)) && (!Character.isWhitespace(c))) {
/*  929 */         this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/*  930 */         break;
/*      */       }
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
/*      */   private InterfaceHttpData findMultipartDelimiter(String paramString, MultiPartStatus paramMultiPartStatus1, MultiPartStatus paramMultiPartStatus2)
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/*  950 */     int i = this.undecodedChunk.readerIndex();
/*      */     try {
/*  952 */       skipControlCharacters();
/*      */     } catch (NotEnoughDataDecoderException localNotEnoughDataDecoderException1) {
/*  954 */       this.undecodedChunk.readerIndex(i);
/*  955 */       return null;
/*      */     }
/*  957 */     skipOneLine();
/*      */     String str;
/*      */     try {
/*  960 */       str = readDelimiter(paramString);
/*      */     } catch (NotEnoughDataDecoderException localNotEnoughDataDecoderException2) {
/*  962 */       this.undecodedChunk.readerIndex(i);
/*  963 */       return null;
/*      */     }
/*  965 */     if (str.equals(paramString)) {
/*  966 */       this.currentStatus = paramMultiPartStatus1;
/*  967 */       return decodeMultipart(paramMultiPartStatus1);
/*      */     }
/*  969 */     if (str.equals(paramString + "--"))
/*      */     {
/*  971 */       this.currentStatus = paramMultiPartStatus2;
/*  972 */       if (this.currentStatus == MultiPartStatus.HEADERDELIMITER)
/*      */       {
/*      */ 
/*  975 */         this.currentFieldAttributes = null;
/*  976 */         return decodeMultipart(MultiPartStatus.HEADERDELIMITER);
/*      */       }
/*  978 */       return null;
/*      */     }
/*  980 */     this.undecodedChunk.readerIndex(i);
/*  981 */     throw new ErrorDataDecoderException("No Multipart delimiter found");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private InterfaceHttpData findMultipartDisposition()
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/*  991 */     int i = this.undecodedChunk.readerIndex();
/*  992 */     if (this.currentStatus == MultiPartStatus.DISPOSITION) {
/*  993 */       this.currentFieldAttributes = new TreeMap(CaseIgnoringComparator.INSTANCE);
/*      */     }
/*      */     
/*  996 */     while (!skipOneLine())
/*      */     {
/*      */       try {
/*  999 */         skipControlCharacters();
/* 1000 */         localObject1 = readLine();
/*      */       } catch (NotEnoughDataDecoderException localNotEnoughDataDecoderException) {
/* 1002 */         this.undecodedChunk.readerIndex(i);
/* 1003 */         return null;
/*      */       }
/* 1005 */       String[] arrayOfString = splitMultipartHeader((String)localObject1);
/* 1006 */       Object localObject4; if (arrayOfString[0].equalsIgnoreCase("Content-Disposition")) {
/*      */         boolean bool;
/* 1008 */         if (this.currentStatus == MultiPartStatus.DISPOSITION) {
/* 1009 */           bool = arrayOfString[1].equalsIgnoreCase("form-data");
/*      */         } else {
/* 1011 */           bool = (arrayOfString[1].equalsIgnoreCase("attachment")) || (arrayOfString[1].equalsIgnoreCase("file"));
/*      */         }
/*      */         
/* 1014 */         if (bool)
/*      */         {
/* 1016 */           for (int k = 2; k < arrayOfString.length; k++) {
/* 1017 */             localObject4 = StringUtil.split(arrayOfString[k], '=');
/*      */             Attribute localAttribute;
/*      */             try {
/* 1020 */               String str1 = cleanString(localObject4[0]);
/* 1021 */               String str2 = localObject4[1];
/*      */               
/*      */ 
/* 1024 */               if ("filename".equals(str1))
/*      */               {
/* 1026 */                 str2 = str2.substring(1, str2.length() - 1);
/*      */               }
/*      */               else {
/* 1029 */                 str2 = cleanString(str2);
/*      */               }
/* 1031 */               localAttribute = this.factory.createAttribute(this.request, str1, str2);
/*      */             } catch (NullPointerException localNullPointerException4) {
/* 1033 */               throw new ErrorDataDecoderException(localNullPointerException4);
/*      */             } catch (IllegalArgumentException localIllegalArgumentException4) {
/* 1035 */               throw new ErrorDataDecoderException(localIllegalArgumentException4);
/*      */             }
/* 1037 */             this.currentFieldAttributes.put(localAttribute.getName(), localAttribute);
/*      */           } }
/*      */       } else { Object localObject2;
/* 1040 */         if (arrayOfString[0].equalsIgnoreCase("Content-Transfer-Encoding"))
/*      */         {
/*      */           try {
/* 1043 */             localObject2 = this.factory.createAttribute(this.request, "Content-Transfer-Encoding", cleanString(arrayOfString[1]));
/*      */           }
/*      */           catch (NullPointerException localNullPointerException1) {
/* 1046 */             throw new ErrorDataDecoderException(localNullPointerException1);
/*      */           } catch (IllegalArgumentException localIllegalArgumentException1) {
/* 1048 */             throw new ErrorDataDecoderException(localIllegalArgumentException1);
/*      */           }
/* 1050 */           this.currentFieldAttributes.put("Content-Transfer-Encoding", localObject2);
/* 1051 */         } else if (arrayOfString[0].equalsIgnoreCase("Content-Length"))
/*      */         {
/*      */           try {
/* 1054 */             localObject2 = this.factory.createAttribute(this.request, "Content-Length", cleanString(arrayOfString[1]));
/*      */           }
/*      */           catch (NullPointerException localNullPointerException2) {
/* 1057 */             throw new ErrorDataDecoderException(localNullPointerException2);
/*      */           } catch (IllegalArgumentException localIllegalArgumentException2) {
/* 1059 */             throw new ErrorDataDecoderException(localIllegalArgumentException2);
/*      */           }
/* 1061 */           this.currentFieldAttributes.put("Content-Length", localObject2);
/* 1062 */         } else if (arrayOfString[0].equalsIgnoreCase("Content-Type"))
/*      */         {
/* 1064 */           if (arrayOfString[1].equalsIgnoreCase("multipart/mixed")) {
/* 1065 */             if (this.currentStatus == MultiPartStatus.DISPOSITION) {
/* 1066 */               localObject2 = StringUtil.split(arrayOfString[2], '=');
/* 1067 */               this.multipartMixedBoundary = ("--" + localObject2[1]);
/* 1068 */               this.currentStatus = MultiPartStatus.MIXEDDELIMITER;
/* 1069 */               return decodeMultipart(MultiPartStatus.MIXEDDELIMITER);
/*      */             }
/* 1071 */             throw new ErrorDataDecoderException("Mixed Multipart found in a previous Mixed Multipart");
/*      */           }
/*      */           
/* 1074 */           for (int j = 1; j < arrayOfString.length; j++) { Object localObject3;
/* 1075 */             if (arrayOfString[j].toLowerCase().startsWith("charset")) {
/* 1076 */               localObject3 = StringUtil.split(arrayOfString[j], '=');
/*      */               try
/*      */               {
/* 1079 */                 localObject4 = this.factory.createAttribute(this.request, "charset", cleanString(localObject3[1]));
/*      */               }
/*      */               catch (NullPointerException localNullPointerException5) {
/* 1082 */                 throw new ErrorDataDecoderException(localNullPointerException5);
/*      */               } catch (IllegalArgumentException localIllegalArgumentException5) {
/* 1084 */                 throw new ErrorDataDecoderException(localIllegalArgumentException5);
/*      */               }
/* 1086 */               this.currentFieldAttributes.put("charset", localObject4);
/*      */             }
/*      */             else {
/*      */               try {
/* 1090 */                 localObject3 = this.factory.createAttribute(this.request, cleanString(arrayOfString[0]), arrayOfString[j]);
/*      */               }
/*      */               catch (NullPointerException localNullPointerException3) {
/* 1093 */                 throw new ErrorDataDecoderException(localNullPointerException3);
/*      */               } catch (IllegalArgumentException localIllegalArgumentException3) {
/* 1095 */                 throw new ErrorDataDecoderException(localIllegalArgumentException3);
/*      */               }
/* 1097 */               this.currentFieldAttributes.put(((Attribute)localObject3).getName(), localObject3);
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 1102 */           throw new ErrorDataDecoderException("Unknown Params: " + (String)localObject1);
/*      */         }
/*      */       }
/*      */     }
/* 1106 */     Object localObject1 = (Attribute)this.currentFieldAttributes.get("filename");
/* 1107 */     if (this.currentStatus == MultiPartStatus.DISPOSITION) {
/* 1108 */       if (localObject1 != null)
/*      */       {
/* 1110 */         this.currentStatus = MultiPartStatus.FILEUPLOAD;
/*      */         
/* 1112 */         return decodeMultipart(MultiPartStatus.FILEUPLOAD);
/*      */       }
/*      */       
/* 1115 */       this.currentStatus = MultiPartStatus.FIELD;
/*      */       
/* 1117 */       return decodeMultipart(MultiPartStatus.FIELD);
/*      */     }
/*      */     
/* 1120 */     if (localObject1 != null)
/*      */     {
/* 1122 */       this.currentStatus = MultiPartStatus.MIXEDFILEUPLOAD;
/*      */       
/* 1124 */       return decodeMultipart(MultiPartStatus.MIXEDFILEUPLOAD);
/*      */     }
/*      */     
/* 1127 */     throw new ErrorDataDecoderException("Filename not found");
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
/*      */   protected InterfaceHttpData getFileUpload(String paramString)
/*      */     throws HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/* 1143 */     Attribute localAttribute1 = (Attribute)this.currentFieldAttributes.get("Content-Transfer-Encoding");
/* 1144 */     Charset localCharset = this.charset;
/*      */     
/* 1146 */     HttpPostBodyUtil.TransferEncodingMechanism localTransferEncodingMechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT7;
/* 1147 */     if (localAttribute1 != null)
/*      */     {
/*      */       try {
/* 1150 */         localObject = localAttribute1.getValue().toLowerCase();
/*      */       } catch (IOException localIOException1) {
/* 1152 */         throw new ErrorDataDecoderException(localIOException1);
/*      */       }
/* 1154 */       if (((String)localObject).equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT7.value())) {
/* 1155 */         localCharset = HttpPostBodyUtil.US_ASCII;
/* 1156 */       } else if (((String)localObject).equals(HttpPostBodyUtil.TransferEncodingMechanism.BIT8.value())) {
/* 1157 */         localCharset = HttpPostBodyUtil.ISO_8859_1;
/* 1158 */         localTransferEncodingMechanism = HttpPostBodyUtil.TransferEncodingMechanism.BIT8;
/* 1159 */       } else if (((String)localObject).equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value()))
/*      */       {
/* 1161 */         localTransferEncodingMechanism = HttpPostBodyUtil.TransferEncodingMechanism.BINARY;
/*      */       } else {
/* 1163 */         throw new ErrorDataDecoderException("TransferEncoding Unknown: " + (String)localObject);
/*      */       }
/*      */     }
/* 1166 */     Object localObject = (Attribute)this.currentFieldAttributes.get("charset");
/* 1167 */     if (localObject != null) {
/*      */       try {
/* 1169 */         localCharset = Charset.forName(((Attribute)localObject).getValue());
/*      */       } catch (IOException localIOException2) {
/* 1171 */         throw new ErrorDataDecoderException(localIOException2);
/*      */       }
/*      */     }
/* 1174 */     if (this.currentFileUpload == null) {
/* 1175 */       Attribute localAttribute2 = (Attribute)this.currentFieldAttributes.get("filename");
/* 1176 */       Attribute localAttribute3 = (Attribute)this.currentFieldAttributes.get("name");
/* 1177 */       Attribute localAttribute4 = (Attribute)this.currentFieldAttributes.get("Content-Type");
/* 1178 */       if (localAttribute4 == null) {
/* 1179 */         throw new ErrorDataDecoderException("Content-Type is absent but required");
/*      */       }
/* 1181 */       Attribute localAttribute5 = (Attribute)this.currentFieldAttributes.get("Content-Length");
/*      */       long l;
/*      */       try {
/* 1184 */         l = localAttribute5 != null ? Long.parseLong(localAttribute5.getValue()) : 0L;
/*      */       } catch (IOException localIOException3) {
/* 1186 */         throw new ErrorDataDecoderException(localIOException3);
/*      */       } catch (NumberFormatException localNumberFormatException) {
/* 1188 */         l = 0L;
/*      */       }
/*      */       try {
/* 1191 */         this.currentFileUpload = this.factory.createFileUpload(this.request, cleanString(localAttribute3.getValue()), cleanString(localAttribute2.getValue()), localAttribute4.getValue(), localTransferEncodingMechanism.value(), localCharset, l);
/*      */ 
/*      */       }
/*      */       catch (NullPointerException localNullPointerException)
/*      */       {
/* 1196 */         throw new ErrorDataDecoderException(localNullPointerException);
/*      */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 1198 */         throw new ErrorDataDecoderException(localIllegalArgumentException);
/*      */       } catch (IOException localIOException4) {
/* 1200 */         throw new ErrorDataDecoderException(localIOException4);
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/* 1205 */       readFileUploadByteMultipart(paramString);
/*      */ 
/*      */     }
/*      */     catch (NotEnoughDataDecoderException localNotEnoughDataDecoderException)
/*      */     {
/* 1210 */       return null;
/*      */     }
/* 1212 */     if (this.currentFileUpload.isCompleted())
/*      */     {
/* 1214 */       if (this.currentStatus == MultiPartStatus.FILEUPLOAD) {
/* 1215 */         this.currentStatus = MultiPartStatus.HEADERDELIMITER;
/* 1216 */         this.currentFieldAttributes = null;
/*      */       } else {
/* 1218 */         this.currentStatus = MultiPartStatus.MIXEDDELIMITER;
/* 1219 */         cleanMixedAttributes();
/*      */       }
/* 1221 */       FileUpload localFileUpload = this.currentFileUpload;
/* 1222 */       this.currentFileUpload = null;
/* 1223 */       return localFileUpload;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1228 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void destroy()
/*      */   {
/* 1236 */     checkDestroyed();
/* 1237 */     cleanFiles();
/* 1238 */     this.destroyed = true;
/*      */     
/* 1240 */     if ((this.undecodedChunk != null) && (this.undecodedChunk.refCnt() > 0)) {
/* 1241 */       this.undecodedChunk.release();
/* 1242 */       this.undecodedChunk = null;
/*      */     }
/*      */     
/*      */ 
/* 1246 */     for (int i = this.bodyListHttpDataRank; i < this.bodyListHttpData.size(); i++) {
/* 1247 */       ((InterfaceHttpData)this.bodyListHttpData.get(i)).release();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void cleanFiles()
/*      */   {
/* 1255 */     checkDestroyed();
/*      */     
/* 1257 */     this.factory.cleanRequestHttpDatas(this.request);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void removeHttpDataFromClean(InterfaceHttpData paramInterfaceHttpData)
/*      */   {
/* 1264 */     checkDestroyed();
/*      */     
/* 1266 */     this.factory.removeHttpDataFromClean(this.request, paramInterfaceHttpData);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void cleanMixedAttributes()
/*      */   {
/* 1274 */     this.currentFieldAttributes.remove("charset");
/* 1275 */     this.currentFieldAttributes.remove("Content-Length");
/* 1276 */     this.currentFieldAttributes.remove("Content-Transfer-Encoding");
/* 1277 */     this.currentFieldAttributes.remove("Content-Type");
/* 1278 */     this.currentFieldAttributes.remove("filename");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String readLineStandard()
/*      */     throws HttpPostRequestDecoder.NotEnoughDataDecoderException
/*      */   {
/* 1290 */     int i = this.undecodedChunk.readerIndex();
/*      */     try {
/* 1292 */       ByteBuf localByteBuf = Unpooled.buffer(64);
/*      */       
/* 1294 */       while (this.undecodedChunk.isReadable()) {
/* 1295 */         int j = this.undecodedChunk.readByte();
/* 1296 */         if (j == 13)
/*      */         {
/* 1298 */           j = this.undecodedChunk.getByte(this.undecodedChunk.readerIndex());
/* 1299 */           if (j == 10)
/*      */           {
/* 1301 */             this.undecodedChunk.skipBytes(1);
/* 1302 */             return localByteBuf.toString(this.charset);
/*      */           }
/*      */           
/* 1305 */           localByteBuf.writeByte(13);
/*      */         } else {
/* 1307 */           if (j == 10) {
/* 1308 */             return localByteBuf.toString(this.charset);
/*      */           }
/* 1310 */           localByteBuf.writeByte(j);
/*      */         }
/*      */       }
/*      */     } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/* 1314 */       this.undecodedChunk.readerIndex(i);
/* 1315 */       throw new NotEnoughDataDecoderException(localIndexOutOfBoundsException);
/*      */     }
/* 1317 */     this.undecodedChunk.readerIndex(i);
/* 1318 */     throw new NotEnoughDataDecoderException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String readLine()
/*      */     throws HttpPostRequestDecoder.NotEnoughDataDecoderException
/*      */   {
/*      */     HttpPostBodyUtil.SeekAheadOptimize localSeekAheadOptimize;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1332 */       localSeekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/*      */     } catch (HttpPostBodyUtil.SeekAheadNoBackArrayException localSeekAheadNoBackArrayException) {
/* 1334 */       return readLineStandard();
/*      */     }
/* 1336 */     int i = this.undecodedChunk.readerIndex();
/*      */     try {
/* 1338 */       ByteBuf localByteBuf = Unpooled.buffer(64);
/*      */       
/* 1340 */       while (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1341 */         int j = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1342 */         if (j == 13) {
/* 1343 */           if (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1344 */             j = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1345 */             if (j == 10) {
/* 1346 */               localSeekAheadOptimize.setReadPosition(0);
/* 1347 */               return localByteBuf.toString(this.charset);
/*      */             }
/*      */             
/* 1350 */             localSeekAheadOptimize.pos -= 1;
/* 1351 */             localByteBuf.writeByte(13);
/*      */           }
/*      */           else {
/* 1354 */             localByteBuf.writeByte(j);
/*      */           }
/* 1356 */         } else { if (j == 10) {
/* 1357 */             localSeekAheadOptimize.setReadPosition(0);
/* 1358 */             return localByteBuf.toString(this.charset);
/*      */           }
/* 1360 */           localByteBuf.writeByte(j);
/*      */         }
/*      */       }
/*      */     } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/* 1364 */       this.undecodedChunk.readerIndex(i);
/* 1365 */       throw new NotEnoughDataDecoderException(localIndexOutOfBoundsException);
/*      */     }
/* 1367 */     this.undecodedChunk.readerIndex(i);
/* 1368 */     throw new NotEnoughDataDecoderException();
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
/*      */   private String readDelimiterStandard(String paramString)
/*      */     throws HttpPostRequestDecoder.NotEnoughDataDecoderException
/*      */   {
/* 1387 */     int i = this.undecodedChunk.readerIndex();
/*      */     try {
/* 1389 */       StringBuilder localStringBuilder = new StringBuilder(64);
/* 1390 */       int j = 0;
/* 1391 */       int k = paramString.length();
/* 1392 */       int m; while ((this.undecodedChunk.isReadable()) && (j < k)) {
/* 1393 */         m = this.undecodedChunk.readByte();
/* 1394 */         if (m == paramString.charAt(j)) {
/* 1395 */           j++;
/* 1396 */           localStringBuilder.append((char)m);
/*      */         }
/*      */         else {
/* 1399 */           this.undecodedChunk.readerIndex(i);
/* 1400 */           throw new NotEnoughDataDecoderException();
/*      */         }
/*      */       }
/*      */       
/* 1404 */       if (this.undecodedChunk.isReadable()) {
/* 1405 */         m = this.undecodedChunk.readByte();
/*      */         
/* 1407 */         if (m == 13) {
/* 1408 */           m = this.undecodedChunk.readByte();
/* 1409 */           if (m == 10) {
/* 1410 */             return localStringBuilder.toString();
/*      */           }
/*      */           
/*      */ 
/* 1414 */           this.undecodedChunk.readerIndex(i);
/* 1415 */           throw new NotEnoughDataDecoderException();
/*      */         }
/* 1417 */         if (m == 10)
/* 1418 */           return localStringBuilder.toString();
/* 1419 */         if (m == 45) {
/* 1420 */           localStringBuilder.append('-');
/*      */           
/* 1422 */           m = this.undecodedChunk.readByte();
/* 1423 */           if (m == 45) {
/* 1424 */             localStringBuilder.append('-');
/*      */             
/* 1426 */             if (this.undecodedChunk.isReadable()) {
/* 1427 */               m = this.undecodedChunk.readByte();
/* 1428 */               if (m == 13) {
/* 1429 */                 m = this.undecodedChunk.readByte();
/* 1430 */                 if (m == 10) {
/* 1431 */                   return localStringBuilder.toString();
/*      */                 }
/*      */                 
/*      */ 
/* 1435 */                 this.undecodedChunk.readerIndex(i);
/* 1436 */                 throw new NotEnoughDataDecoderException();
/*      */               }
/* 1438 */               if (m == 10) {
/* 1439 */                 return localStringBuilder.toString();
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 1444 */               this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/* 1445 */               return localStringBuilder.toString();
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1452 */             return localStringBuilder.toString();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
/*      */     {
/* 1459 */       this.undecodedChunk.readerIndex(i);
/* 1460 */       throw new NotEnoughDataDecoderException(localIndexOutOfBoundsException);
/*      */     }
/* 1462 */     this.undecodedChunk.readerIndex(i);
/* 1463 */     throw new NotEnoughDataDecoderException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String readDelimiter(String paramString)
/*      */     throws HttpPostRequestDecoder.NotEnoughDataDecoderException
/*      */   {
/*      */     HttpPostBodyUtil.SeekAheadOptimize localSeekAheadOptimize;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1483 */       localSeekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/*      */     } catch (HttpPostBodyUtil.SeekAheadNoBackArrayException localSeekAheadNoBackArrayException) {
/* 1485 */       return readDelimiterStandard(paramString);
/*      */     }
/* 1487 */     int i = this.undecodedChunk.readerIndex();
/* 1488 */     int j = 0;
/* 1489 */     int k = paramString.length();
/*      */     try {
/* 1491 */       StringBuilder localStringBuilder = new StringBuilder(64);
/*      */       int m;
/* 1493 */       while ((localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) && (j < k)) {
/* 1494 */         m = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1495 */         if (m == paramString.charAt(j)) {
/* 1496 */           j++;
/* 1497 */           localStringBuilder.append((char)m);
/*      */         }
/*      */         else {
/* 1500 */           this.undecodedChunk.readerIndex(i);
/* 1501 */           throw new NotEnoughDataDecoderException();
/*      */         }
/*      */       }
/*      */       
/* 1505 */       if (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1506 */         m = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1507 */         if (m == 13)
/*      */         {
/* 1509 */           if (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1510 */             m = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1511 */             if (m == 10) {
/* 1512 */               localSeekAheadOptimize.setReadPosition(0);
/* 1513 */               return localStringBuilder.toString();
/*      */             }
/*      */             
/*      */ 
/* 1517 */             this.undecodedChunk.readerIndex(i);
/* 1518 */             throw new NotEnoughDataDecoderException();
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 1523 */           this.undecodedChunk.readerIndex(i);
/* 1524 */           throw new NotEnoughDataDecoderException();
/*      */         }
/* 1526 */         if (m == 10)
/*      */         {
/*      */ 
/* 1529 */           localSeekAheadOptimize.setReadPosition(0);
/* 1530 */           return localStringBuilder.toString(); }
/* 1531 */         if (m == 45) {
/* 1532 */           localStringBuilder.append('-');
/*      */           
/* 1534 */           if (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1535 */             m = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1536 */             if (m == 45) {
/* 1537 */               localStringBuilder.append('-');
/*      */               
/* 1539 */               if (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1540 */                 m = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1541 */                 if (m == 13) {
/* 1542 */                   if (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1543 */                     m = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1544 */                     if (m == 10) {
/* 1545 */                       localSeekAheadOptimize.setReadPosition(0);
/* 1546 */                       return localStringBuilder.toString();
/*      */                     }
/*      */                     
/*      */ 
/* 1550 */                     this.undecodedChunk.readerIndex(i);
/* 1551 */                     throw new NotEnoughDataDecoderException();
/*      */                   }
/*      */                   
/*      */ 
/*      */ 
/* 1556 */                   this.undecodedChunk.readerIndex(i);
/* 1557 */                   throw new NotEnoughDataDecoderException();
/*      */                 }
/* 1559 */                 if (m == 10) {
/* 1560 */                   localSeekAheadOptimize.setReadPosition(0);
/* 1561 */                   return localStringBuilder.toString();
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/* 1567 */                 localSeekAheadOptimize.setReadPosition(1);
/* 1568 */                 return localStringBuilder.toString();
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1575 */               localSeekAheadOptimize.setReadPosition(0);
/* 1576 */               return localStringBuilder.toString();
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
/*      */     {
/* 1585 */       this.undecodedChunk.readerIndex(i);
/* 1586 */       throw new NotEnoughDataDecoderException(localIndexOutOfBoundsException);
/*      */     }
/* 1588 */     this.undecodedChunk.readerIndex(i);
/* 1589 */     throw new NotEnoughDataDecoderException();
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
/*      */   private void readFileUploadByteMultipartStandard(String paramString)
/*      */     throws HttpPostRequestDecoder.NotEnoughDataDecoderException, HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/* 1604 */     int i = this.undecodedChunk.readerIndex();
/*      */     
/* 1606 */     int j = 1;
/* 1607 */     int k = 0;
/* 1608 */     int m = this.undecodedChunk.readerIndex();
/* 1609 */     int n = 0;
/* 1610 */     while (this.undecodedChunk.isReadable()) {
/* 1611 */       int i1 = this.undecodedChunk.readByte();
/* 1612 */       if (j != 0)
/*      */       {
/* 1614 */         if (i1 == paramString.codePointAt(k)) {
/* 1615 */           k++;
/* 1616 */           if (paramString.length() == k) {
/* 1617 */             n = 1;
/* 1618 */             break;
/*      */           }
/*      */         }
/*      */         else {
/* 1622 */           j = 0;
/* 1623 */           k = 0;
/*      */           
/* 1625 */           if (i1 == 13) {
/* 1626 */             if (this.undecodedChunk.isReadable()) {
/* 1627 */               i1 = this.undecodedChunk.readByte();
/* 1628 */               if (i1 == 10) {
/* 1629 */                 j = 1;
/* 1630 */                 k = 0;
/* 1631 */                 m = this.undecodedChunk.readerIndex() - 2;
/*      */               }
/*      */               else {
/* 1634 */                 m = this.undecodedChunk.readerIndex() - 1;
/*      */                 
/*      */ 
/* 1637 */                 this.undecodedChunk.readerIndex(m);
/*      */               }
/*      */             }
/* 1640 */           } else if (i1 == 10) {
/* 1641 */             j = 1;
/* 1642 */             k = 0;
/* 1643 */             m = this.undecodedChunk.readerIndex() - 1;
/*      */           }
/*      */           else {
/* 1646 */             m = this.undecodedChunk.readerIndex();
/*      */           }
/*      */           
/*      */         }
/*      */       }
/* 1651 */       else if (i1 == 13) {
/* 1652 */         if (this.undecodedChunk.isReadable()) {
/* 1653 */           i1 = this.undecodedChunk.readByte();
/* 1654 */           if (i1 == 10) {
/* 1655 */             j = 1;
/* 1656 */             k = 0;
/* 1657 */             m = this.undecodedChunk.readerIndex() - 2;
/*      */           }
/*      */           else {
/* 1660 */             m = this.undecodedChunk.readerIndex() - 1;
/*      */             
/*      */ 
/* 1663 */             this.undecodedChunk.readerIndex(m);
/*      */           }
/*      */         }
/* 1666 */       } else if (i1 == 10) {
/* 1667 */         j = 1;
/* 1668 */         k = 0;
/* 1669 */         m = this.undecodedChunk.readerIndex() - 1;
/*      */       }
/*      */       else {
/* 1672 */         m = this.undecodedChunk.readerIndex();
/*      */       }
/*      */     }
/*      */     
/* 1676 */     ByteBuf localByteBuf = this.undecodedChunk.copy(i, m - i);
/* 1677 */     if (n != 0) {
/*      */       try
/*      */       {
/* 1680 */         this.currentFileUpload.addContent(localByteBuf, true);
/*      */         
/* 1682 */         this.undecodedChunk.readerIndex(m);
/*      */       } catch (IOException localIOException1) {
/* 1684 */         throw new ErrorDataDecoderException(localIOException1);
/*      */       }
/*      */       
/*      */     } else {
/*      */       try
/*      */       {
/* 1690 */         this.currentFileUpload.addContent(localByteBuf, false);
/*      */         
/* 1692 */         this.undecodedChunk.readerIndex(m);
/* 1693 */         throw new NotEnoughDataDecoderException();
/*      */       } catch (IOException localIOException2) {
/* 1695 */         throw new ErrorDataDecoderException(localIOException2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readFileUploadByteMultipart(String paramString)
/*      */     throws HttpPostRequestDecoder.NotEnoughDataDecoderException, HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/*      */     HttpPostBodyUtil.SeekAheadOptimize localSeekAheadOptimize;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1714 */       localSeekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/*      */     } catch (HttpPostBodyUtil.SeekAheadNoBackArrayException localSeekAheadNoBackArrayException) {
/* 1716 */       readFileUploadByteMultipartStandard(paramString);
/* 1717 */       return;
/*      */     }
/* 1719 */     int i = this.undecodedChunk.readerIndex();
/*      */     
/* 1721 */     int j = 1;
/* 1722 */     int k = 0;
/* 1723 */     int m = localSeekAheadOptimize.pos;
/*      */     
/* 1725 */     int n = 0;
/*      */     
/* 1727 */     while (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1728 */       int i1 = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1729 */       if (j != 0)
/*      */       {
/* 1731 */         if (i1 == paramString.codePointAt(k)) {
/* 1732 */           k++;
/* 1733 */           if (paramString.length() == k) {
/* 1734 */             n = 1;
/* 1735 */             break;
/*      */           }
/*      */         }
/*      */         else {
/* 1739 */           j = 0;
/* 1740 */           k = 0;
/*      */           
/* 1742 */           if (i1 == 13) {
/* 1743 */             if (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1744 */               i1 = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1745 */               if (i1 == 10) {
/* 1746 */                 j = 1;
/* 1747 */                 k = 0;
/* 1748 */                 m = localSeekAheadOptimize.pos - 2;
/*      */               }
/*      */               else {
/* 1751 */                 localSeekAheadOptimize.pos -= 1;
/*      */                 
/*      */ 
/* 1754 */                 m = localSeekAheadOptimize.pos;
/*      */               }
/*      */             }
/* 1757 */           } else if (i1 == 10) {
/* 1758 */             j = 1;
/* 1759 */             k = 0;
/* 1760 */             m = localSeekAheadOptimize.pos - 1;
/*      */           }
/*      */           else {
/* 1763 */             m = localSeekAheadOptimize.pos;
/*      */           }
/*      */           
/*      */         }
/*      */       }
/* 1768 */       else if (i1 == 13) {
/* 1769 */         if (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1770 */           i1 = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1771 */           if (i1 == 10) {
/* 1772 */             j = 1;
/* 1773 */             k = 0;
/* 1774 */             m = localSeekAheadOptimize.pos - 2;
/*      */           }
/*      */           else {
/* 1777 */             localSeekAheadOptimize.pos -= 1;
/*      */             
/*      */ 
/* 1780 */             m = localSeekAheadOptimize.pos;
/*      */           }
/*      */         }
/* 1783 */       } else if (i1 == 10) {
/* 1784 */         j = 1;
/* 1785 */         k = 0;
/* 1786 */         m = localSeekAheadOptimize.pos - 1;
/*      */       }
/*      */       else {
/* 1789 */         m = localSeekAheadOptimize.pos;
/*      */       }
/*      */     }
/*      */     
/* 1793 */     int i2 = localSeekAheadOptimize.getReadPosition(m);
/* 1794 */     ByteBuf localByteBuf = this.undecodedChunk.copy(i, i2 - i);
/* 1795 */     if (n != 0) {
/*      */       try
/*      */       {
/* 1798 */         this.currentFileUpload.addContent(localByteBuf, true);
/*      */         
/* 1800 */         this.undecodedChunk.readerIndex(i2);
/*      */       } catch (IOException localIOException1) {
/* 1802 */         throw new ErrorDataDecoderException(localIOException1);
/*      */       }
/*      */       
/*      */     } else {
/*      */       try
/*      */       {
/* 1808 */         this.currentFileUpload.addContent(localByteBuf, false);
/*      */         
/* 1810 */         this.undecodedChunk.readerIndex(i2);
/* 1811 */         throw new NotEnoughDataDecoderException();
/*      */       } catch (IOException localIOException2) {
/* 1813 */         throw new ErrorDataDecoderException(localIOException2);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void loadFieldMultipartStandard(String paramString)
/*      */     throws HttpPostRequestDecoder.NotEnoughDataDecoderException, HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/* 1827 */     int i = this.undecodedChunk.readerIndex();
/*      */     try
/*      */     {
/* 1830 */       int j = 1;
/* 1831 */       int k = 0;
/* 1832 */       int m = this.undecodedChunk.readerIndex();
/* 1833 */       int n = 0;
/* 1834 */       while (this.undecodedChunk.isReadable()) {
/* 1835 */         int i1 = this.undecodedChunk.readByte();
/* 1836 */         if (j != 0)
/*      */         {
/* 1838 */           if (i1 == paramString.codePointAt(k)) {
/* 1839 */             k++;
/* 1840 */             if (paramString.length() == k) {
/* 1841 */               n = 1;
/* 1842 */               break;
/*      */             }
/*      */           }
/*      */           else {
/* 1846 */             j = 0;
/* 1847 */             k = 0;
/*      */             
/* 1849 */             if (i1 == 13) {
/* 1850 */               if (this.undecodedChunk.isReadable()) {
/* 1851 */                 i1 = this.undecodedChunk.readByte();
/* 1852 */                 if (i1 == 10) {
/* 1853 */                   j = 1;
/* 1854 */                   k = 0;
/* 1855 */                   m = this.undecodedChunk.readerIndex() - 2;
/*      */                 }
/*      */                 else {
/* 1858 */                   m = this.undecodedChunk.readerIndex() - 1;
/* 1859 */                   this.undecodedChunk.readerIndex(m);
/*      */                 }
/*      */               } else {
/* 1862 */                 m = this.undecodedChunk.readerIndex() - 1;
/*      */               }
/* 1864 */             } else if (i1 == 10) {
/* 1865 */               j = 1;
/* 1866 */               k = 0;
/* 1867 */               m = this.undecodedChunk.readerIndex() - 1;
/*      */             } else {
/* 1869 */               m = this.undecodedChunk.readerIndex();
/*      */             }
/*      */             
/*      */           }
/*      */         }
/* 1874 */         else if (i1 == 13) {
/* 1875 */           if (this.undecodedChunk.isReadable()) {
/* 1876 */             i1 = this.undecodedChunk.readByte();
/* 1877 */             if (i1 == 10) {
/* 1878 */               j = 1;
/* 1879 */               k = 0;
/* 1880 */               m = this.undecodedChunk.readerIndex() - 2;
/*      */             }
/*      */             else {
/* 1883 */               m = this.undecodedChunk.readerIndex() - 1;
/* 1884 */               this.undecodedChunk.readerIndex(m);
/*      */             }
/*      */           } else {
/* 1887 */             m = this.undecodedChunk.readerIndex() - 1;
/*      */           }
/* 1889 */         } else if (i1 == 10) {
/* 1890 */           j = 1;
/* 1891 */           k = 0;
/* 1892 */           m = this.undecodedChunk.readerIndex() - 1;
/*      */         } else {
/* 1894 */           m = this.undecodedChunk.readerIndex();
/*      */         }
/*      */       }
/*      */       
/* 1898 */       if (n != 0)
/*      */       {
/*      */ 
/*      */         try
/*      */         {
/*      */ 
/* 1904 */           this.currentAttribute.addContent(this.undecodedChunk.copy(i, m - i), true);
/*      */         }
/*      */         catch (IOException localIOException1) {
/* 1907 */           throw new ErrorDataDecoderException(localIOException1);
/*      */         }
/* 1909 */         this.undecodedChunk.readerIndex(m);
/*      */       } else {
/*      */         try {
/* 1912 */           this.currentAttribute.addContent(this.undecodedChunk.copy(i, m - i), false);
/*      */         }
/*      */         catch (IOException localIOException2) {
/* 1915 */           throw new ErrorDataDecoderException(localIOException2);
/*      */         }
/* 1917 */         this.undecodedChunk.readerIndex(m);
/* 1918 */         throw new NotEnoughDataDecoderException();
/*      */       }
/*      */     } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/* 1921 */       this.undecodedChunk.readerIndex(i);
/* 1922 */       throw new NotEnoughDataDecoderException(localIndexOutOfBoundsException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void loadFieldMultipart(String paramString)
/*      */     throws HttpPostRequestDecoder.NotEnoughDataDecoderException, HttpPostRequestDecoder.ErrorDataDecoderException
/*      */   {
/*      */     HttpPostBodyUtil.SeekAheadOptimize localSeekAheadOptimize;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1936 */       localSeekAheadOptimize = new HttpPostBodyUtil.SeekAheadOptimize(this.undecodedChunk);
/*      */     } catch (HttpPostBodyUtil.SeekAheadNoBackArrayException localSeekAheadNoBackArrayException) {
/* 1938 */       loadFieldMultipartStandard(paramString);
/* 1939 */       return;
/*      */     }
/* 1941 */     int i = this.undecodedChunk.readerIndex();
/*      */     try
/*      */     {
/* 1944 */       int j = 1;
/* 1945 */       int k = 0;
/*      */       
/* 1947 */       int m = localSeekAheadOptimize.pos;
/* 1948 */       int n = 0;
/*      */       
/* 1950 */       while (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1951 */         int i1 = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1952 */         if (j != 0)
/*      */         {
/* 1954 */           if (i1 == paramString.codePointAt(k)) {
/* 1955 */             k++;
/* 1956 */             if (paramString.length() == k) {
/* 1957 */               n = 1;
/* 1958 */               break;
/*      */             }
/*      */           }
/*      */           else {
/* 1962 */             j = 0;
/* 1963 */             k = 0;
/*      */             
/* 1965 */             if (i1 == 13) {
/* 1966 */               if (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1967 */                 i1 = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1968 */                 if (i1 == 10) {
/* 1969 */                   j = 1;
/* 1970 */                   k = 0;
/* 1971 */                   m = localSeekAheadOptimize.pos - 2;
/*      */                 }
/*      */                 else {
/* 1974 */                   localSeekAheadOptimize.pos -= 1;
/* 1975 */                   m = localSeekAheadOptimize.pos;
/*      */                 }
/*      */               }
/* 1978 */             } else if (i1 == 10) {
/* 1979 */               j = 1;
/* 1980 */               k = 0;
/* 1981 */               m = localSeekAheadOptimize.pos - 1;
/*      */             } else {
/* 1983 */               m = localSeekAheadOptimize.pos;
/*      */             }
/*      */             
/*      */           }
/*      */         }
/* 1988 */         else if (i1 == 13) {
/* 1989 */           if (localSeekAheadOptimize.pos < localSeekAheadOptimize.limit) {
/* 1990 */             i1 = localSeekAheadOptimize.bytes[(localSeekAheadOptimize.pos++)];
/* 1991 */             if (i1 == 10) {
/* 1992 */               j = 1;
/* 1993 */               k = 0;
/* 1994 */               m = localSeekAheadOptimize.pos - 2;
/*      */             }
/*      */             else {
/* 1997 */               localSeekAheadOptimize.pos -= 1;
/* 1998 */               m = localSeekAheadOptimize.pos;
/*      */             }
/*      */           }
/* 2001 */         } else if (i1 == 10) {
/* 2002 */           j = 1;
/* 2003 */           k = 0;
/* 2004 */           m = localSeekAheadOptimize.pos - 1;
/*      */         } else {
/* 2006 */           m = localSeekAheadOptimize.pos;
/*      */         }
/*      */       }
/*      */       
/* 2010 */       int i2 = localSeekAheadOptimize.getReadPosition(m);
/* 2011 */       if (n != 0)
/*      */       {
/*      */ 
/*      */         try
/*      */         {
/*      */ 
/* 2017 */           this.currentAttribute.addContent(this.undecodedChunk.copy(i, i2 - i), true);
/*      */         }
/*      */         catch (IOException localIOException1) {
/* 2020 */           throw new ErrorDataDecoderException(localIOException1);
/*      */         }
/* 2022 */         this.undecodedChunk.readerIndex(i2);
/*      */       } else {
/*      */         try {
/* 2025 */           this.currentAttribute.addContent(this.undecodedChunk.copy(i, i2 - i), false);
/*      */         }
/*      */         catch (IOException localIOException2) {
/* 2028 */           throw new ErrorDataDecoderException(localIOException2);
/*      */         }
/* 2030 */         this.undecodedChunk.readerIndex(i2);
/* 2031 */         throw new NotEnoughDataDecoderException();
/*      */       }
/*      */     } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/* 2034 */       this.undecodedChunk.readerIndex(i);
/* 2035 */       throw new NotEnoughDataDecoderException(localIndexOutOfBoundsException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String cleanString(String paramString)
/*      */   {
/* 2045 */     StringBuilder localStringBuilder = new StringBuilder(paramString.length());
/* 2046 */     for (int i = 0; i < paramString.length(); i++) {
/* 2047 */       char c = paramString.charAt(i);
/* 2048 */       if (c == ':') {
/* 2049 */         localStringBuilder.append(32);
/* 2050 */       } else if (c == ',') {
/* 2051 */         localStringBuilder.append(32);
/* 2052 */       } else if (c == '=') {
/* 2053 */         localStringBuilder.append(32);
/* 2054 */       } else if (c == ';') {
/* 2055 */         localStringBuilder.append(32);
/* 2056 */       } else if (c == '\t') {
/* 2057 */         localStringBuilder.append(32);
/* 2058 */       } else if (c != '"')
/*      */       {
/*      */ 
/* 2061 */         localStringBuilder.append(c);
/*      */       }
/*      */     }
/* 2064 */     return localStringBuilder.toString().trim();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean skipOneLine()
/*      */   {
/* 2073 */     if (!this.undecodedChunk.isReadable()) {
/* 2074 */       return false;
/*      */     }
/* 2076 */     int i = this.undecodedChunk.readByte();
/* 2077 */     if (i == 13) {
/* 2078 */       if (!this.undecodedChunk.isReadable()) {
/* 2079 */         this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/* 2080 */         return false;
/*      */       }
/* 2082 */       i = this.undecodedChunk.readByte();
/* 2083 */       if (i == 10) {
/* 2084 */         return true;
/*      */       }
/* 2086 */       this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 2);
/* 2087 */       return false;
/*      */     }
/* 2089 */     if (i == 10) {
/* 2090 */       return true;
/*      */     }
/* 2092 */     this.undecodedChunk.readerIndex(this.undecodedChunk.readerIndex() - 1);
/* 2093 */     return false;
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
/*      */   private static String[] splitHeaderContentType(String paramString)
/*      */   {
/* 2106 */     int i = HttpPostBodyUtil.findNonWhitespace(paramString, 0);
/* 2107 */     int j = paramString.indexOf(';');
/* 2108 */     if (j == -1) {
/* 2109 */       return new String[] { paramString, "" };
/*      */     }
/* 2111 */     if (paramString.charAt(j - 1) == ' ') {
/* 2112 */       j--;
/*      */     }
/* 2114 */     int k = HttpPostBodyUtil.findNonWhitespace(paramString, j + 1);
/* 2115 */     int m = HttpPostBodyUtil.findEndOfString(paramString);
/* 2116 */     return new String[] { paramString.substring(i, j), paramString.substring(k, m) };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String[] splitMultipartHeader(String paramString)
/*      */   {
/* 2126 */     ArrayList localArrayList = new ArrayList(1);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2132 */     int i = HttpPostBodyUtil.findNonWhitespace(paramString, 0);
/* 2133 */     for (int j = i; j < paramString.length(); j++) {
/* 2134 */       char c = paramString.charAt(j);
/* 2135 */       if ((c == ':') || (Character.isWhitespace(c))) {
/*      */         break;
/*      */       }
/*      */     }
/* 2139 */     for (int k = j; k < paramString.length(); k++) {
/* 2140 */       if (paramString.charAt(k) == ':') {
/* 2141 */         k++;
/* 2142 */         break;
/*      */       }
/*      */     }
/* 2145 */     int m = HttpPostBodyUtil.findNonWhitespace(paramString, k);
/* 2146 */     int n = HttpPostBodyUtil.findEndOfString(paramString);
/* 2147 */     localArrayList.add(paramString.substring(i, j));
/* 2148 */     String str1 = paramString.substring(m, n);
/*      */     String[] arrayOfString1;
/* 2150 */     if (str1.indexOf(';') >= 0) {
/* 2151 */       arrayOfString1 = StringUtil.split(str1, ';');
/*      */     } else {
/* 2153 */       arrayOfString1 = StringUtil.split(str1, ',');
/*      */     }
/* 2155 */     for (String str2 : arrayOfString1) {
/* 2156 */       localArrayList.add(str2.trim());
/*      */     }
/* 2158 */     ??? = new String[localArrayList.size()];
/* 2159 */     for (??? = 0; ??? < localArrayList.size(); ???++) {
/* 2160 */       ???[???] = ((String)localArrayList.get(???));
/*      */     }
/* 2162 */     return (String[])???;
/*      */   }
/*      */   
/*      */ 
/*      */   public static class NotEnoughDataDecoderException
/*      */     extends DecoderException
/*      */   {
/*      */     private static final long serialVersionUID = -7846841864603865638L;
/*      */     
/*      */ 
/*      */     public NotEnoughDataDecoderException() {}
/*      */     
/*      */     public NotEnoughDataDecoderException(String paramString)
/*      */     {
/* 2176 */       super();
/*      */     }
/*      */     
/*      */     public NotEnoughDataDecoderException(Throwable paramThrowable) {
/* 2180 */       super();
/*      */     }
/*      */     
/*      */     public NotEnoughDataDecoderException(String paramString, Throwable paramThrowable) {
/* 2184 */       super(paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static class EndOfDataDecoderException
/*      */     extends DecoderException
/*      */   {
/*      */     private static final long serialVersionUID = 1336267941020800769L;
/*      */   }
/*      */   
/*      */ 
/*      */   public static class ErrorDataDecoderException
/*      */     extends DecoderException
/*      */   {
/*      */     private static final long serialVersionUID = 5020247425493164465L;
/*      */     
/*      */     public ErrorDataDecoderException() {}
/*      */     
/*      */     public ErrorDataDecoderException(String paramString)
/*      */     {
/* 2205 */       super();
/*      */     }
/*      */     
/*      */     public ErrorDataDecoderException(Throwable paramThrowable) {
/* 2209 */       super();
/*      */     }
/*      */     
/*      */     public ErrorDataDecoderException(String paramString, Throwable paramThrowable) {
/* 2213 */       super(paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static class IncompatibleDataDecoderException
/*      */     extends DecoderException
/*      */   {
/*      */     private static final long serialVersionUID = -953268047926250267L;
/*      */     
/*      */     public IncompatibleDataDecoderException() {}
/*      */     
/*      */     public IncompatibleDataDecoderException(String paramString)
/*      */     {
/* 2227 */       super();
/*      */     }
/*      */     
/*      */     public IncompatibleDataDecoderException(Throwable paramThrowable) {
/* 2231 */       super();
/*      */     }
/*      */     
/*      */     public IncompatibleDataDecoderException(String paramString, Throwable paramThrowable) {
/* 2235 */       super(paramThrowable);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\HttpPostRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */