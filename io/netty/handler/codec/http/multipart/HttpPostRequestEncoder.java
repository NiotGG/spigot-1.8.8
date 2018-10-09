/*      */ package io.netty.handler.codec.http.multipart;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.channel.ChannelHandlerContext;
/*      */ import io.netty.handler.codec.DecoderResult;
/*      */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*      */ import io.netty.handler.codec.http.DefaultHttpContent;
/*      */ import io.netty.handler.codec.http.FullHttpRequest;
/*      */ import io.netty.handler.codec.http.HttpConstants;
/*      */ import io.netty.handler.codec.http.HttpContent;
/*      */ import io.netty.handler.codec.http.HttpHeaders;
/*      */ import io.netty.handler.codec.http.HttpMethod;
/*      */ import io.netty.handler.codec.http.HttpRequest;
/*      */ import io.netty.handler.codec.http.HttpVersion;
/*      */ import io.netty.handler.codec.http.LastHttpContent;
/*      */ import io.netty.handler.stream.ChunkedInput;
/*      */ import io.netty.util.internal.ThreadLocalRandom;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.URLEncoder;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
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
/*      */ public class HttpPostRequestEncoder
/*      */   implements ChunkedInput<HttpContent>
/*      */ {
/*      */   public static enum EncoderMode
/*      */   {
/*   61 */     RFC1738, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*   66 */     RFC3986;
/*      */     
/*      */     private EncoderMode() {} }
/*   69 */   private static final Map<Pattern, String> percentEncodings = new HashMap();
/*      */   private final HttpDataFactory factory;
/*      */   
/*   72 */   static { percentEncodings.put(Pattern.compile("\\*"), "%2A");
/*   73 */     percentEncodings.put(Pattern.compile("\\+"), "%20");
/*   74 */     percentEncodings.put(Pattern.compile("%7E"), "~");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final HttpRequest request;
/*      */   
/*      */ 
/*      */ 
/*      */   private final Charset charset;
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean isChunked;
/*      */   
/*      */ 
/*      */   private final List<InterfaceHttpData> bodyListDatas;
/*      */   
/*      */ 
/*      */   final List<InterfaceHttpData> multipartHttpDatas;
/*      */   
/*      */ 
/*      */   private final boolean isMultipart;
/*      */   
/*      */ 
/*      */   String multipartDataBoundary;
/*      */   
/*      */ 
/*      */   String multipartMixedBoundary;
/*      */   
/*      */ 
/*      */   private boolean headerFinalized;
/*      */   
/*      */ 
/*      */   private final EncoderMode encoderMode;
/*      */   
/*      */ 
/*      */   private boolean isLastChunk;
/*      */   
/*      */ 
/*      */   private boolean isLastChunkSent;
/*      */   
/*      */ 
/*      */   private FileUpload currentFileUpload;
/*      */   
/*      */ 
/*      */   private boolean duringMixedMode;
/*      */   
/*      */ 
/*      */   private long globalBodySize;
/*      */   
/*      */ 
/*      */   private ListIterator<InterfaceHttpData> iterator;
/*      */   
/*      */ 
/*      */   private ByteBuf currentBuffer;
/*      */   
/*      */ 
/*      */   private InterfaceHttpData currentData;
/*      */   
/*      */ 
/*      */   public HttpPostRequestEncoder(HttpRequest paramHttpRequest, boolean paramBoolean)
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  139 */     this(new DefaultHttpDataFactory(16384L), paramHttpRequest, paramBoolean, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
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
/*      */   public HttpPostRequestEncoder(HttpDataFactory paramHttpDataFactory, HttpRequest paramHttpRequest, boolean paramBoolean)
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  158 */     this(paramHttpDataFactory, paramHttpRequest, paramBoolean, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
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
/*      */   public HttpPostRequestEncoder(HttpDataFactory paramHttpDataFactory, HttpRequest paramHttpRequest, boolean paramBoolean, Charset paramCharset, EncoderMode paramEncoderMode)
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  182 */     if (paramHttpDataFactory == null) {
/*  183 */       throw new NullPointerException("factory");
/*      */     }
/*  185 */     if (paramHttpRequest == null) {
/*  186 */       throw new NullPointerException("request");
/*      */     }
/*  188 */     if (paramCharset == null) {
/*  189 */       throw new NullPointerException("charset");
/*      */     }
/*  191 */     if (paramHttpRequest.getMethod() != HttpMethod.POST) {
/*  192 */       throw new ErrorDataEncoderException("Cannot create a Encoder if not a POST");
/*      */     }
/*  194 */     this.request = paramHttpRequest;
/*  195 */     this.charset = paramCharset;
/*  196 */     this.factory = paramHttpDataFactory;
/*      */     
/*  198 */     this.bodyListDatas = new ArrayList();
/*      */     
/*  200 */     this.isLastChunk = false;
/*  201 */     this.isLastChunkSent = false;
/*  202 */     this.isMultipart = paramBoolean;
/*  203 */     this.multipartHttpDatas = new ArrayList();
/*  204 */     this.encoderMode = paramEncoderMode;
/*  205 */     if (this.isMultipart) {
/*  206 */       initDataMultipart();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void cleanFiles()
/*      */   {
/*  214 */     this.factory.cleanRequestHttpDatas(this.request);
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
/*      */   public boolean isMultipart()
/*      */   {
/*  245 */     return this.isMultipart;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void initDataMultipart()
/*      */   {
/*  252 */     this.multipartDataBoundary = getNewMultipartDelimiter();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void initMixedMultipart()
/*      */   {
/*  259 */     this.multipartMixedBoundary = getNewMultipartDelimiter();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String getNewMultipartDelimiter()
/*      */   {
/*  268 */     return Long.toHexString(ThreadLocalRandom.current().nextLong()).toLowerCase();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<InterfaceHttpData> getBodyListAttributes()
/*      */   {
/*  277 */     return this.bodyListDatas;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setBodyHttpDatas(List<InterfaceHttpData> paramList)
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  289 */     if (paramList == null) {
/*  290 */       throw new NullPointerException("datas");
/*      */     }
/*  292 */     this.globalBodySize = 0L;
/*  293 */     this.bodyListDatas.clear();
/*  294 */     this.currentFileUpload = null;
/*  295 */     this.duringMixedMode = false;
/*  296 */     this.multipartHttpDatas.clear();
/*  297 */     for (InterfaceHttpData localInterfaceHttpData : paramList) {
/*  298 */       addBodyHttpData(localInterfaceHttpData);
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
/*      */   public void addBodyAttribute(String paramString1, String paramString2)
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  315 */     if (paramString1 == null) {
/*  316 */       throw new NullPointerException("name");
/*      */     }
/*  318 */     String str = paramString2;
/*  319 */     if (paramString2 == null) {
/*  320 */       str = "";
/*      */     }
/*  322 */     Attribute localAttribute = this.factory.createAttribute(this.request, paramString1, str);
/*  323 */     addBodyHttpData(localAttribute);
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
/*      */   public void addBodyFileUpload(String paramString1, File paramFile, String paramString2, boolean paramBoolean)
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  344 */     if (paramString1 == null) {
/*  345 */       throw new NullPointerException("name");
/*      */     }
/*  347 */     if (paramFile == null) {
/*  348 */       throw new NullPointerException("file");
/*      */     }
/*  350 */     String str1 = paramString2;
/*  351 */     String str2 = null;
/*  352 */     if (paramString2 == null) {
/*  353 */       if (paramBoolean) {
/*  354 */         str1 = "text/plain";
/*      */       } else {
/*  356 */         str1 = "application/octet-stream";
/*      */       }
/*      */     }
/*  359 */     if (!paramBoolean) {
/*  360 */       str2 = HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value();
/*      */     }
/*  362 */     FileUpload localFileUpload = this.factory.createFileUpload(this.request, paramString1, paramFile.getName(), str1, str2, null, paramFile.length());
/*      */     try
/*      */     {
/*  365 */       localFileUpload.setContent(paramFile);
/*      */     } catch (IOException localIOException) {
/*  367 */       throw new ErrorDataEncoderException(localIOException);
/*      */     }
/*  369 */     addBodyHttpData(localFileUpload);
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
/*      */   public void addBodyFileUploads(String paramString, File[] paramArrayOfFile, String[] paramArrayOfString, boolean[] paramArrayOfBoolean)
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  390 */     if ((paramArrayOfFile.length != paramArrayOfString.length) && (paramArrayOfFile.length != paramArrayOfBoolean.length)) {
/*  391 */       throw new NullPointerException("Different array length");
/*      */     }
/*  393 */     for (int i = 0; i < paramArrayOfFile.length; i++) {
/*  394 */       addBodyFileUpload(paramString, paramArrayOfFile[i], paramArrayOfString[i], paramArrayOfBoolean[i]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addBodyHttpData(InterfaceHttpData paramInterfaceHttpData)
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  407 */     if (this.headerFinalized) {
/*  408 */       throw new ErrorDataEncoderException("Cannot add value once finalized");
/*      */     }
/*  410 */     if (paramInterfaceHttpData == null) {
/*  411 */       throw new NullPointerException("data");
/*      */     }
/*  413 */     this.bodyListDatas.add(paramInterfaceHttpData);
/*  414 */     Object localObject1; Object localObject3; Object localObject4; Object localObject2; if (!this.isMultipart) {
/*  415 */       if ((paramInterfaceHttpData instanceof Attribute)) {
/*  416 */         localObject1 = (Attribute)paramInterfaceHttpData;
/*      */         try
/*      */         {
/*  419 */           String str = encodeAttribute(((Attribute)localObject1).getName(), this.charset);
/*  420 */           localObject3 = encodeAttribute(((Attribute)localObject1).getValue(), this.charset);
/*  421 */           localObject4 = this.factory.createAttribute(this.request, str, (String)localObject3);
/*  422 */           this.multipartHttpDatas.add(localObject4);
/*  423 */           this.globalBodySize += ((Attribute)localObject4).getName().length() + 1 + ((Attribute)localObject4).length() + 1L;
/*      */         } catch (IOException localIOException) {
/*  425 */           throw new ErrorDataEncoderException(localIOException);
/*      */         }
/*  427 */       } else if ((paramInterfaceHttpData instanceof FileUpload))
/*      */       {
/*  429 */         localObject1 = (FileUpload)paramInterfaceHttpData;
/*      */         
/*  431 */         localObject2 = encodeAttribute(((FileUpload)localObject1).getName(), this.charset);
/*  432 */         localObject3 = encodeAttribute(((FileUpload)localObject1).getFilename(), this.charset);
/*  433 */         localObject4 = this.factory.createAttribute(this.request, (String)localObject2, (String)localObject3);
/*  434 */         this.multipartHttpDatas.add(localObject4);
/*  435 */         this.globalBodySize += ((Attribute)localObject4).getName().length() + 1 + ((Attribute)localObject4).length() + 1L;
/*      */       }
/*  437 */       return;
/*      */     }
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
/*  471 */     if ((paramInterfaceHttpData instanceof Attribute)) {
/*  472 */       if (this.duringMixedMode) {
/*  473 */         localObject1 = new InternalAttribute(this.charset);
/*  474 */         ((InternalAttribute)localObject1).addValue("\r\n--" + this.multipartMixedBoundary + "--");
/*  475 */         this.multipartHttpDatas.add(localObject1);
/*  476 */         this.multipartMixedBoundary = null;
/*  477 */         this.currentFileUpload = null;
/*  478 */         this.duringMixedMode = false;
/*      */       }
/*  480 */       localObject1 = new InternalAttribute(this.charset);
/*  481 */       if (!this.multipartHttpDatas.isEmpty())
/*      */       {
/*  483 */         ((InternalAttribute)localObject1).addValue("\r\n");
/*      */       }
/*  485 */       ((InternalAttribute)localObject1).addValue("--" + this.multipartDataBoundary + "\r\n");
/*      */       
/*  487 */       localObject2 = (Attribute)paramInterfaceHttpData;
/*  488 */       ((InternalAttribute)localObject1).addValue("Content-Disposition: form-data; name=\"" + ((Attribute)localObject2).getName() + "\"\r\n");
/*      */       
/*  490 */       localObject3 = ((Attribute)localObject2).getCharset();
/*  491 */       if (localObject3 != null)
/*      */       {
/*  493 */         ((InternalAttribute)localObject1).addValue("Content-Type: text/plain; charset=" + localObject3 + "\r\n");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  499 */       ((InternalAttribute)localObject1).addValue("\r\n");
/*  500 */       this.multipartHttpDatas.add(localObject1);
/*  501 */       this.multipartHttpDatas.add(paramInterfaceHttpData);
/*  502 */       this.globalBodySize += ((Attribute)localObject2).length() + ((InternalAttribute)localObject1).size();
/*  503 */     } else if ((paramInterfaceHttpData instanceof FileUpload)) {
/*  504 */       localObject1 = (FileUpload)paramInterfaceHttpData;
/*  505 */       localObject2 = new InternalAttribute(this.charset);
/*  506 */       if (!this.multipartHttpDatas.isEmpty())
/*      */       {
/*  508 */         ((InternalAttribute)localObject2).addValue("\r\n");
/*      */       }
/*      */       int i;
/*  511 */       if (this.duringMixedMode) {
/*  512 */         if ((this.currentFileUpload != null) && (this.currentFileUpload.getName().equals(((FileUpload)localObject1).getName())))
/*      */         {
/*      */ 
/*  515 */           i = 1;
/*      */ 
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  522 */           ((InternalAttribute)localObject2).addValue("--" + this.multipartMixedBoundary + "--");
/*  523 */           this.multipartHttpDatas.add(localObject2);
/*  524 */           this.multipartMixedBoundary = null;
/*      */           
/*      */ 
/*  527 */           localObject2 = new InternalAttribute(this.charset);
/*  528 */           ((InternalAttribute)localObject2).addValue("\r\n");
/*  529 */           i = 0;
/*      */           
/*  531 */           this.currentFileUpload = ((FileUpload)localObject1);
/*  532 */           this.duringMixedMode = false;
/*      */         }
/*      */       }
/*  535 */       else if ((this.currentFileUpload != null) && (this.currentFileUpload.getName().equals(((FileUpload)localObject1).getName())))
/*      */       {
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
/*  556 */         initMixedMultipart();
/*  557 */         localObject4 = (InternalAttribute)this.multipartHttpDatas.get(this.multipartHttpDatas.size() - 2);
/*      */         
/*      */ 
/*  560 */         this.globalBodySize -= ((InternalAttribute)localObject4).size();
/*  561 */         StringBuilder localStringBuilder = new StringBuilder(139 + this.multipartDataBoundary.length() + this.multipartMixedBoundary.length() * 2 + ((FileUpload)localObject1).getFilename().length() + ((FileUpload)localObject1).getName().length());
/*      */         
/*      */ 
/*      */ 
/*  565 */         localStringBuilder.append("--");
/*  566 */         localStringBuilder.append(this.multipartDataBoundary);
/*  567 */         localStringBuilder.append("\r\n");
/*      */         
/*  569 */         localStringBuilder.append("Content-Disposition");
/*  570 */         localStringBuilder.append(": ");
/*  571 */         localStringBuilder.append("form-data");
/*  572 */         localStringBuilder.append("; ");
/*  573 */         localStringBuilder.append("name");
/*  574 */         localStringBuilder.append("=\"");
/*  575 */         localStringBuilder.append(((FileUpload)localObject1).getName());
/*  576 */         localStringBuilder.append("\"\r\n");
/*      */         
/*  578 */         localStringBuilder.append("Content-Type");
/*  579 */         localStringBuilder.append(": ");
/*  580 */         localStringBuilder.append("multipart/mixed");
/*  581 */         localStringBuilder.append("; ");
/*  582 */         localStringBuilder.append("boundary");
/*  583 */         localStringBuilder.append('=');
/*  584 */         localStringBuilder.append(this.multipartMixedBoundary);
/*  585 */         localStringBuilder.append("\r\n\r\n");
/*      */         
/*  587 */         localStringBuilder.append("--");
/*  588 */         localStringBuilder.append(this.multipartMixedBoundary);
/*  589 */         localStringBuilder.append("\r\n");
/*      */         
/*  591 */         localStringBuilder.append("Content-Disposition");
/*  592 */         localStringBuilder.append(": ");
/*  593 */         localStringBuilder.append("attachment");
/*  594 */         localStringBuilder.append("; ");
/*  595 */         localStringBuilder.append("filename");
/*  596 */         localStringBuilder.append("=\"");
/*  597 */         localStringBuilder.append(((FileUpload)localObject1).getFilename());
/*  598 */         localStringBuilder.append("\"\r\n");
/*      */         
/*  600 */         ((InternalAttribute)localObject4).setValue(localStringBuilder.toString(), 1);
/*  601 */         ((InternalAttribute)localObject4).setValue("", 2);
/*      */         
/*      */ 
/*  604 */         this.globalBodySize += ((InternalAttribute)localObject4).size();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  610 */         i = 1;
/*  611 */         this.duringMixedMode = true;
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  616 */         i = 0;
/*  617 */         this.currentFileUpload = ((FileUpload)localObject1);
/*  618 */         this.duringMixedMode = false;
/*      */       }
/*      */       
/*      */ 
/*  622 */       if (i != 0)
/*      */       {
/*      */ 
/*  625 */         ((InternalAttribute)localObject2).addValue("--" + this.multipartMixedBoundary + "\r\n");
/*      */         
/*  627 */         ((InternalAttribute)localObject2).addValue("Content-Disposition: attachment; filename=\"" + ((FileUpload)localObject1).getFilename() + "\"\r\n");
/*      */       }
/*      */       else {
/*  630 */         ((InternalAttribute)localObject2).addValue("--" + this.multipartDataBoundary + "\r\n");
/*      */         
/*      */ 
/*  633 */         ((InternalAttribute)localObject2).addValue("Content-Disposition: form-data; name=\"" + ((FileUpload)localObject1).getName() + "\"; " + "filename" + "=\"" + ((FileUpload)localObject1).getFilename() + "\"\r\n");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  640 */       ((InternalAttribute)localObject2).addValue("Content-Type: " + ((FileUpload)localObject1).getContentType());
/*  641 */       localObject4 = ((FileUpload)localObject1).getContentTransferEncoding();
/*  642 */       if ((localObject4 != null) && (((String)localObject4).equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())))
/*      */       {
/*  644 */         ((InternalAttribute)localObject2).addValue("\r\nContent-Transfer-Encoding: " + HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value() + "\r\n\r\n");
/*      */       }
/*  646 */       else if (((FileUpload)localObject1).getCharset() != null) {
/*  647 */         ((InternalAttribute)localObject2).addValue("; charset=" + ((FileUpload)localObject1).getCharset() + "\r\n\r\n");
/*      */       } else {
/*  649 */         ((InternalAttribute)localObject2).addValue("\r\n\r\n");
/*      */       }
/*  651 */       this.multipartHttpDatas.add(localObject2);
/*  652 */       this.multipartHttpDatas.add(paramInterfaceHttpData);
/*  653 */       this.globalBodySize += ((FileUpload)localObject1).length() + ((InternalAttribute)localObject2).size();
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
/*      */   public HttpRequest finalizeRequest()
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  674 */     if (!this.headerFinalized) {
/*  675 */       if (this.isMultipart) {
/*  676 */         localObject1 = new InternalAttribute(this.charset);
/*  677 */         if (this.duringMixedMode) {
/*  678 */           ((InternalAttribute)localObject1).addValue("\r\n--" + this.multipartMixedBoundary + "--");
/*      */         }
/*  680 */         ((InternalAttribute)localObject1).addValue("\r\n--" + this.multipartDataBoundary + "--\r\n");
/*  681 */         this.multipartHttpDatas.add(localObject1);
/*  682 */         this.multipartMixedBoundary = null;
/*  683 */         this.currentFileUpload = null;
/*  684 */         this.duringMixedMode = false;
/*  685 */         this.globalBodySize += ((InternalAttribute)localObject1).size();
/*      */       }
/*  687 */       this.headerFinalized = true;
/*      */     } else {
/*  689 */       throw new ErrorDataEncoderException("Header already encoded");
/*      */     }
/*      */     
/*  692 */     Object localObject1 = this.request.headers();
/*  693 */     List localList1 = ((HttpHeaders)localObject1).getAll("Content-Type");
/*  694 */     List localList2 = ((HttpHeaders)localObject1).getAll("Transfer-Encoding");
/*  695 */     Object localObject2; if (localList1 != null) {
/*  696 */       ((HttpHeaders)localObject1).remove("Content-Type");
/*  697 */       for (localObject2 = localList1.iterator(); ((Iterator)localObject2).hasNext();) { String str = (String)((Iterator)localObject2).next();
/*      */         
/*  699 */         localObject3 = str.toLowerCase();
/*  700 */         if ((!((String)localObject3).startsWith("multipart/form-data")) && (!((String)localObject3).startsWith("application/x-www-form-urlencoded")))
/*      */         {
/*      */ 
/*      */ 
/*  704 */           ((HttpHeaders)localObject1).add("Content-Type", str);
/*      */         }
/*      */       }
/*      */     }
/*  708 */     if (this.isMultipart) {
/*  709 */       localObject2 = "multipart/form-data; boundary=" + this.multipartDataBoundary;
/*      */       
/*  711 */       ((HttpHeaders)localObject1).add("Content-Type", localObject2);
/*      */     }
/*      */     else {
/*  714 */       ((HttpHeaders)localObject1).add("Content-Type", "application/x-www-form-urlencoded");
/*      */     }
/*      */     
/*  717 */     long l = this.globalBodySize;
/*  718 */     if (this.isMultipart) {
/*  719 */       this.iterator = this.multipartHttpDatas.listIterator();
/*      */     } else {
/*  721 */       l -= 1L;
/*  722 */       this.iterator = this.multipartHttpDatas.listIterator();
/*      */     }
/*  724 */     ((HttpHeaders)localObject1).set("Content-Length", String.valueOf(l));
/*  725 */     Object localObject4; if ((l > 8096L) || (this.isMultipart)) {
/*  726 */       this.isChunked = true;
/*  727 */       if (localList2 != null) {
/*  728 */         ((HttpHeaders)localObject1).remove("Transfer-Encoding");
/*  729 */         for (localObject3 = localList2.iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = (String)((Iterator)localObject3).next();
/*  730 */           if (!((String)localObject4).equalsIgnoreCase("chunked"))
/*      */           {
/*      */ 
/*  733 */             ((HttpHeaders)localObject1).add("Transfer-Encoding", localObject4);
/*      */           }
/*      */         }
/*      */       }
/*  737 */       HttpHeaders.setTransferEncodingChunked(this.request);
/*      */       
/*      */ 
/*  740 */       return new WrappedHttpRequest(this.request);
/*      */     }
/*      */     
/*  743 */     Object localObject3 = nextChunk();
/*  744 */     if ((this.request instanceof FullHttpRequest)) {
/*  745 */       localObject4 = (FullHttpRequest)this.request;
/*  746 */       ByteBuf localByteBuf = ((HttpContent)localObject3).content();
/*  747 */       if (((FullHttpRequest)localObject4).content() != localByteBuf) {
/*  748 */         ((FullHttpRequest)localObject4).content().clear().writeBytes(localByteBuf);
/*  749 */         localByteBuf.release();
/*      */       }
/*  751 */       return (HttpRequest)localObject4;
/*      */     }
/*  753 */     return new WrappedFullHttpRequest(this.request, (HttpContent)localObject3, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isChunked()
/*      */   {
/*  762 */     return this.isChunked;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String encodeAttribute(String paramString, Charset paramCharset)
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  773 */     if (paramString == null) {
/*  774 */       return "";
/*      */     }
/*      */     try {
/*  777 */       String str1 = URLEncoder.encode(paramString, paramCharset.name());
/*  778 */       if (this.encoderMode == EncoderMode.RFC3986) {
/*  779 */         for (Map.Entry localEntry : percentEncodings.entrySet()) {
/*  780 */           String str2 = (String)localEntry.getValue();
/*  781 */           str1 = ((Pattern)localEntry.getKey()).matcher(str1).replaceAll(str2);
/*      */         }
/*      */       }
/*  784 */       return str1;
/*      */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/*  786 */       throw new ErrorDataEncoderException(paramCharset.name(), localUnsupportedEncodingException);
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
/*  801 */   private boolean isKey = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private ByteBuf fillByteBuf()
/*      */   {
/*  808 */     int i = this.currentBuffer.readableBytes();
/*  809 */     if (i > 8096) {
/*  810 */       localByteBuf = this.currentBuffer.slice(this.currentBuffer.readerIndex(), 8096);
/*  811 */       this.currentBuffer.skipBytes(8096);
/*  812 */       return localByteBuf;
/*      */     }
/*      */     
/*  815 */     ByteBuf localByteBuf = this.currentBuffer;
/*  816 */     this.currentBuffer = null;
/*  817 */     return localByteBuf;
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
/*      */   private HttpContent encodeNextChunkMultipart(int paramInt)
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  832 */     if (this.currentData == null) {
/*  833 */       return null;
/*      */     }
/*      */     
/*  836 */     if ((this.currentData instanceof InternalAttribute)) {
/*  837 */       localByteBuf = ((InternalAttribute)this.currentData).toByteBuf();
/*  838 */       this.currentData = null;
/*      */     } else {
/*  840 */       if ((this.currentData instanceof Attribute)) {
/*      */         try {
/*  842 */           localByteBuf = ((Attribute)this.currentData).getChunk(paramInt);
/*      */         } catch (IOException localIOException1) {
/*  844 */           throw new ErrorDataEncoderException(localIOException1);
/*      */         }
/*      */       } else {
/*      */         try {
/*  848 */           localByteBuf = ((HttpData)this.currentData).getChunk(paramInt);
/*      */         } catch (IOException localIOException2) {
/*  850 */           throw new ErrorDataEncoderException(localIOException2);
/*      */         }
/*      */       }
/*  853 */       if (localByteBuf.capacity() == 0)
/*      */       {
/*  855 */         this.currentData = null;
/*  856 */         return null;
/*      */       }
/*      */     }
/*  859 */     if (this.currentBuffer == null) {
/*  860 */       this.currentBuffer = localByteBuf;
/*      */     } else {
/*  862 */       this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, localByteBuf });
/*      */     }
/*  864 */     if (this.currentBuffer.readableBytes() < 8096) {
/*  865 */       this.currentData = null;
/*  866 */       return null;
/*      */     }
/*  868 */     ByteBuf localByteBuf = fillByteBuf();
/*  869 */     return new DefaultHttpContent(localByteBuf);
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
/*      */   private HttpContent encodeNextChunkUrlEncoded(int paramInt)
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  883 */     if (this.currentData == null) {
/*  884 */       return null;
/*      */     }
/*  886 */     int i = paramInt;
/*      */     
/*      */ 
/*      */ 
/*  890 */     if (this.isKey) {
/*  891 */       String str = this.currentData.getName();
/*  892 */       localByteBuf2 = Unpooled.wrappedBuffer(str.getBytes());
/*  893 */       this.isKey = false;
/*  894 */       if (this.currentBuffer == null) {
/*  895 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { localByteBuf2, Unpooled.wrappedBuffer("=".getBytes()) });
/*      */         
/*  897 */         i -= localByteBuf2.readableBytes() + 1;
/*      */       } else {
/*  899 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, localByteBuf2, Unpooled.wrappedBuffer("=".getBytes()) });
/*      */         
/*  901 */         i -= localByteBuf2.readableBytes() + 1;
/*      */       }
/*  903 */       if (this.currentBuffer.readableBytes() >= 8096) {
/*  904 */         localByteBuf2 = fillByteBuf();
/*  905 */         return new DefaultHttpContent(localByteBuf2);
/*      */       }
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  911 */       localByteBuf2 = ((HttpData)this.currentData).getChunk(i);
/*      */     } catch (IOException localIOException) {
/*  913 */       throw new ErrorDataEncoderException(localIOException);
/*      */     }
/*      */     
/*      */ 
/*  917 */     ByteBuf localByteBuf1 = null;
/*  918 */     if (localByteBuf2.readableBytes() < i) {
/*  919 */       this.isKey = true;
/*  920 */       localByteBuf1 = this.iterator.hasNext() ? Unpooled.wrappedBuffer("&".getBytes()) : null;
/*      */     }
/*      */     
/*      */ 
/*  924 */     if (localByteBuf2.capacity() == 0) {
/*  925 */       this.currentData = null;
/*  926 */       if (this.currentBuffer == null) {
/*  927 */         this.currentBuffer = localByteBuf1;
/*      */       }
/*  929 */       else if (localByteBuf1 != null) {
/*  930 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, localByteBuf1 });
/*      */       }
/*      */       
/*  933 */       if (this.currentBuffer.readableBytes() >= 8096) {
/*  934 */         localByteBuf2 = fillByteBuf();
/*  935 */         return new DefaultHttpContent(localByteBuf2);
/*      */       }
/*  937 */       return null;
/*      */     }
/*      */     
/*      */ 
/*  941 */     if (this.currentBuffer == null) {
/*  942 */       if (localByteBuf1 != null) {
/*  943 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { localByteBuf2, localByteBuf1 });
/*      */       } else {
/*  945 */         this.currentBuffer = localByteBuf2;
/*      */       }
/*      */     }
/*  948 */     else if (localByteBuf1 != null) {
/*  949 */       this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, localByteBuf2, localByteBuf1 });
/*      */     } else {
/*  951 */       this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, localByteBuf2 });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  956 */     if (this.currentBuffer.readableBytes() < 8096) {
/*  957 */       this.currentData = null;
/*  958 */       this.isKey = true;
/*  959 */       return null;
/*      */     }
/*      */     
/*  962 */     ByteBuf localByteBuf2 = fillByteBuf();
/*  963 */     return new DefaultHttpContent(localByteBuf2);
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
/*      */   public HttpContent readChunk(ChannelHandlerContext paramChannelHandlerContext)
/*      */     throws Exception
/*      */   {
/*  982 */     if (this.isLastChunkSent) {
/*  983 */       return null;
/*      */     }
/*  985 */     return nextChunk();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private HttpContent nextChunk()
/*      */     throws HttpPostRequestEncoder.ErrorDataEncoderException
/*      */   {
/*  998 */     if (this.isLastChunk) {
/*  999 */       this.isLastChunkSent = true;
/* 1000 */       return LastHttpContent.EMPTY_LAST_CONTENT;
/*      */     }
/*      */     
/* 1003 */     int i = 8096;
/*      */     
/* 1005 */     if (this.currentBuffer != null) {
/* 1006 */       i -= this.currentBuffer.readableBytes();
/*      */     }
/* 1008 */     if (i <= 0)
/*      */     {
/* 1010 */       localByteBuf = fillByteBuf();
/* 1011 */       return new DefaultHttpContent(localByteBuf);
/*      */     }
/*      */     HttpContent localHttpContent;
/* 1014 */     if (this.currentData != null)
/*      */     {
/* 1016 */       if (this.isMultipart) {
/* 1017 */         localHttpContent = encodeNextChunkMultipart(i);
/* 1018 */         if (localHttpContent != null) {
/* 1019 */           return localHttpContent;
/*      */         }
/*      */       } else {
/* 1022 */         localHttpContent = encodeNextChunkUrlEncoded(i);
/* 1023 */         if (localHttpContent != null)
/*      */         {
/* 1025 */           return localHttpContent;
/*      */         }
/*      */       }
/* 1028 */       i = 8096 - this.currentBuffer.readableBytes();
/*      */     }
/* 1030 */     if (!this.iterator.hasNext()) {
/* 1031 */       this.isLastChunk = true;
/*      */       
/* 1033 */       localByteBuf = this.currentBuffer;
/* 1034 */       this.currentBuffer = null;
/* 1035 */       return new DefaultHttpContent(localByteBuf);
/*      */     }
/* 1037 */     while ((i > 0) && (this.iterator.hasNext())) {
/* 1038 */       this.currentData = ((InterfaceHttpData)this.iterator.next());
/*      */       
/* 1040 */       if (this.isMultipart) {
/* 1041 */         localHttpContent = encodeNextChunkMultipart(i);
/*      */       } else {
/* 1043 */         localHttpContent = encodeNextChunkUrlEncoded(i);
/*      */       }
/* 1045 */       if (localHttpContent == null)
/*      */       {
/* 1047 */         i = 8096 - this.currentBuffer.readableBytes();
/*      */       }
/*      */       else
/*      */       {
/* 1051 */         return localHttpContent;
/*      */       }
/*      */     }
/* 1054 */     this.isLastChunk = true;
/* 1055 */     if (this.currentBuffer == null) {
/* 1056 */       this.isLastChunkSent = true;
/*      */       
/* 1058 */       return LastHttpContent.EMPTY_LAST_CONTENT;
/*      */     }
/*      */     
/* 1061 */     ByteBuf localByteBuf = this.currentBuffer;
/* 1062 */     this.currentBuffer = null;
/* 1063 */     return new DefaultHttpContent(localByteBuf);
/*      */   }
/*      */   
/*      */   public boolean isEndOfInput() throws Exception
/*      */   {
/* 1068 */     return this.isLastChunkSent;
/*      */   }
/*      */   
/*      */   public void close() throws Exception
/*      */   {}
/*      */   
/*      */   public static class ErrorDataEncoderException extends Exception
/*      */   {
/*      */     private static final long serialVersionUID = 5020247425493164465L;
/*      */     
/*      */     public ErrorDataEncoderException() {}
/*      */     
/*      */     public ErrorDataEncoderException(String paramString) {
/* 1081 */       super();
/*      */     }
/*      */     
/*      */     public ErrorDataEncoderException(Throwable paramThrowable) {
/* 1085 */       super();
/*      */     }
/*      */     
/*      */     public ErrorDataEncoderException(String paramString, Throwable paramThrowable) {
/* 1089 */       super(paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class WrappedHttpRequest implements HttpRequest {
/*      */     private final HttpRequest request;
/*      */     
/* 1096 */     WrappedHttpRequest(HttpRequest paramHttpRequest) { this.request = paramHttpRequest; }
/*      */     
/*      */ 
/*      */     public HttpRequest setProtocolVersion(HttpVersion paramHttpVersion)
/*      */     {
/* 1101 */       this.request.setProtocolVersion(paramHttpVersion);
/* 1102 */       return this;
/*      */     }
/*      */     
/*      */     public HttpRequest setMethod(HttpMethod paramHttpMethod)
/*      */     {
/* 1107 */       this.request.setMethod(paramHttpMethod);
/* 1108 */       return this;
/*      */     }
/*      */     
/*      */     public HttpRequest setUri(String paramString)
/*      */     {
/* 1113 */       this.request.setUri(paramString);
/* 1114 */       return this;
/*      */     }
/*      */     
/*      */     public HttpMethod getMethod()
/*      */     {
/* 1119 */       return this.request.getMethod();
/*      */     }
/*      */     
/*      */     public String getUri()
/*      */     {
/* 1124 */       return this.request.getUri();
/*      */     }
/*      */     
/*      */     public HttpVersion getProtocolVersion()
/*      */     {
/* 1129 */       return this.request.getProtocolVersion();
/*      */     }
/*      */     
/*      */     public HttpHeaders headers()
/*      */     {
/* 1134 */       return this.request.headers();
/*      */     }
/*      */     
/*      */     public DecoderResult getDecoderResult()
/*      */     {
/* 1139 */       return this.request.getDecoderResult();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1144 */     public void setDecoderResult(DecoderResult paramDecoderResult) { this.request.setDecoderResult(paramDecoderResult); }
/*      */   }
/*      */   
/*      */   private static final class WrappedFullHttpRequest extends HttpPostRequestEncoder.WrappedHttpRequest implements FullHttpRequest {
/*      */     private final HttpContent content;
/*      */     
/*      */     private WrappedFullHttpRequest(HttpRequest paramHttpRequest, HttpContent paramHttpContent) {
/* 1151 */       super();
/* 1152 */       this.content = paramHttpContent;
/*      */     }
/*      */     
/*      */     public FullHttpRequest setProtocolVersion(HttpVersion paramHttpVersion)
/*      */     {
/* 1157 */       super.setProtocolVersion(paramHttpVersion);
/* 1158 */       return this;
/*      */     }
/*      */     
/*      */     public FullHttpRequest setMethod(HttpMethod paramHttpMethod)
/*      */     {
/* 1163 */       super.setMethod(paramHttpMethod);
/* 1164 */       return this;
/*      */     }
/*      */     
/*      */     public FullHttpRequest setUri(String paramString)
/*      */     {
/* 1169 */       super.setUri(paramString);
/* 1170 */       return this;
/*      */     }
/*      */     
/*      */     public FullHttpRequest copy()
/*      */     {
/* 1175 */       DefaultFullHttpRequest localDefaultFullHttpRequest = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().copy());
/*      */       
/* 1177 */       localDefaultFullHttpRequest.headers().set(headers());
/* 1178 */       localDefaultFullHttpRequest.trailingHeaders().set(trailingHeaders());
/* 1179 */       return localDefaultFullHttpRequest;
/*      */     }
/*      */     
/*      */     public FullHttpRequest duplicate()
/*      */     {
/* 1184 */       DefaultFullHttpRequest localDefaultFullHttpRequest = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().duplicate());
/*      */       
/* 1186 */       localDefaultFullHttpRequest.headers().set(headers());
/* 1187 */       localDefaultFullHttpRequest.trailingHeaders().set(trailingHeaders());
/* 1188 */       return localDefaultFullHttpRequest;
/*      */     }
/*      */     
/*      */     public FullHttpRequest retain(int paramInt)
/*      */     {
/* 1193 */       this.content.retain(paramInt);
/* 1194 */       return this;
/*      */     }
/*      */     
/*      */     public FullHttpRequest retain()
/*      */     {
/* 1199 */       this.content.retain();
/* 1200 */       return this;
/*      */     }
/*      */     
/*      */     public ByteBuf content()
/*      */     {
/* 1205 */       return this.content.content();
/*      */     }
/*      */     
/*      */     public HttpHeaders trailingHeaders()
/*      */     {
/* 1210 */       if ((this.content instanceof LastHttpContent)) {
/* 1211 */         return ((LastHttpContent)this.content).trailingHeaders();
/*      */       }
/* 1213 */       return HttpHeaders.EMPTY_HEADERS;
/*      */     }
/*      */     
/*      */ 
/*      */     public int refCnt()
/*      */     {
/* 1219 */       return this.content.refCnt();
/*      */     }
/*      */     
/*      */     public boolean release()
/*      */     {
/* 1224 */       return this.content.release();
/*      */     }
/*      */     
/*      */     public boolean release(int paramInt)
/*      */     {
/* 1229 */       return this.content.release(paramInt);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\HttpPostRequestEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */