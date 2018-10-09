/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.io.ByteOrderMark;
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
/*     */ public class XmlStreamReader
/*     */   extends Reader
/*     */ {
/*     */   private static final int BUFFER_SIZE = 4096;
/*     */   private static final String UTF_8 = "UTF-8";
/*     */   private static final String US_ASCII = "US-ASCII";
/*     */   private static final String UTF_16BE = "UTF-16BE";
/*     */   private static final String UTF_16LE = "UTF-16LE";
/*     */   private static final String UTF_32BE = "UTF-32BE";
/*     */   private static final String UTF_32LE = "UTF-32LE";
/*     */   private static final String UTF_16 = "UTF-16";
/*     */   private static final String UTF_32 = "UTF-32";
/*     */   private static final String EBCDIC = "CP1047";
/*  87 */   private static final ByteOrderMark[] BOMS = { ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32LE };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  96 */   private static final ByteOrderMark[] XML_GUESS_BYTES = { new ByteOrderMark("UTF-8", new int[] { 60, 63, 120, 109 }), new ByteOrderMark("UTF-16BE", new int[] { 0, 60, 0, 63 }), new ByteOrderMark("UTF-16LE", new int[] { 60, 0, 63, 0 }), new ByteOrderMark("UTF-32BE", new int[] { 0, 0, 0, 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109 }), new ByteOrderMark("UTF-32LE", new int[] { 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109, 0, 0, 0 }), new ByteOrderMark("CP1047", new int[] { 76, 111, 167, 148 }) };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Reader reader;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String encoding;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String defaultEncoding;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDefaultEncoding()
/*     */   {
/* 122 */     return this.defaultEncoding;
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
/*     */ 
/*     */ 
/*     */   public XmlStreamReader(File paramFile)
/*     */     throws IOException
/*     */   {
/* 138 */     this(new FileInputStream(paramFile));
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
/*     */ 
/*     */   public XmlStreamReader(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 153 */     this(paramInputStream, true);
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
/*     */   public XmlStreamReader(InputStream paramInputStream, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 184 */     this(paramInputStream, paramBoolean, null);
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
/*     */   public XmlStreamReader(InputStream paramInputStream, boolean paramBoolean, String paramString)
/*     */     throws IOException
/*     */   {
/* 216 */     this.defaultEncoding = paramString;
/* 217 */     BOMInputStream localBOMInputStream1 = new BOMInputStream(new BufferedInputStream(paramInputStream, 4096), false, BOMS);
/* 218 */     BOMInputStream localBOMInputStream2 = new BOMInputStream(localBOMInputStream1, true, XML_GUESS_BYTES);
/* 219 */     this.encoding = doRawStream(localBOMInputStream1, localBOMInputStream2, paramBoolean);
/* 220 */     this.reader = new InputStreamReader(localBOMInputStream2, this.encoding);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XmlStreamReader(URL paramURL)
/*     */     throws IOException
/*     */   {
/* 241 */     this(paramURL.openConnection(), null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XmlStreamReader(URLConnection paramURLConnection, String paramString)
/*     */     throws IOException
/*     */   {
/* 264 */     this.defaultEncoding = paramString;
/* 265 */     boolean bool = true;
/* 266 */     String str = paramURLConnection.getContentType();
/* 267 */     InputStream localInputStream = paramURLConnection.getInputStream();
/* 268 */     BOMInputStream localBOMInputStream1 = new BOMInputStream(new BufferedInputStream(localInputStream, 4096), false, BOMS);
/* 269 */     BOMInputStream localBOMInputStream2 = new BOMInputStream(localBOMInputStream1, true, XML_GUESS_BYTES);
/* 270 */     if (((paramURLConnection instanceof HttpURLConnection)) || (str != null)) {
/* 271 */       this.encoding = doHttpStream(localBOMInputStream1, localBOMInputStream2, str, bool);
/*     */     } else {
/* 273 */       this.encoding = doRawStream(localBOMInputStream1, localBOMInputStream2, bool);
/*     */     }
/* 275 */     this.reader = new InputStreamReader(localBOMInputStream2, this.encoding);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XmlStreamReader(InputStream paramInputStream, String paramString)
/*     */     throws IOException
/*     */   {
/* 297 */     this(paramInputStream, paramString, true);
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
/*     */   public XmlStreamReader(InputStream paramInputStream, String paramString1, boolean paramBoolean, String paramString2)
/*     */     throws IOException
/*     */   {
/* 336 */     this.defaultEncoding = paramString2;
/* 337 */     BOMInputStream localBOMInputStream1 = new BOMInputStream(new BufferedInputStream(paramInputStream, 4096), false, BOMS);
/* 338 */     BOMInputStream localBOMInputStream2 = new BOMInputStream(localBOMInputStream1, true, XML_GUESS_BYTES);
/* 339 */     this.encoding = doHttpStream(localBOMInputStream1, localBOMInputStream2, paramString1, paramBoolean);
/* 340 */     this.reader = new InputStreamReader(localBOMInputStream2, this.encoding);
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
/*     */   public XmlStreamReader(InputStream paramInputStream, String paramString, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 378 */     this(paramInputStream, paramString, paramBoolean, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getEncoding()
/*     */   {
/* 387 */     return this.encoding;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 400 */     return this.reader.read(paramArrayOfChar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 410 */     this.reader.close();
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
/*     */ 
/*     */   private String doRawStream(BOMInputStream paramBOMInputStream1, BOMInputStream paramBOMInputStream2, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 425 */     String str1 = paramBOMInputStream1.getBOMCharsetName();
/* 426 */     String str2 = paramBOMInputStream2.getBOMCharsetName();
/* 427 */     String str3 = getXmlProlog(paramBOMInputStream2, str2);
/*     */     try {
/* 429 */       return calculateRawEncoding(str1, str2, str3);
/*     */     } catch (XmlStreamReaderException localXmlStreamReaderException) {
/* 431 */       if (paramBoolean) {
/* 432 */         return doLenientDetection(null, localXmlStreamReaderException);
/*     */       }
/* 434 */       throw localXmlStreamReaderException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   private String doHttpStream(BOMInputStream paramBOMInputStream1, BOMInputStream paramBOMInputStream2, String paramString, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 452 */     String str1 = paramBOMInputStream1.getBOMCharsetName();
/* 453 */     String str2 = paramBOMInputStream2.getBOMCharsetName();
/* 454 */     String str3 = getXmlProlog(paramBOMInputStream2, str2);
/*     */     try {
/* 456 */       return calculateHttpEncoding(paramString, str1, str2, str3, paramBoolean);
/*     */     }
/*     */     catch (XmlStreamReaderException localXmlStreamReaderException) {
/* 459 */       if (paramBoolean) {
/* 460 */         return doLenientDetection(paramString, localXmlStreamReaderException);
/*     */       }
/* 462 */       throw localXmlStreamReaderException;
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
/*     */ 
/*     */   private String doLenientDetection(String paramString, XmlStreamReaderException paramXmlStreamReaderException)
/*     */     throws IOException
/*     */   {
/* 478 */     if ((paramString != null) && (paramString.startsWith("text/html"))) {
/* 479 */       paramString = paramString.substring("text/html".length());
/* 480 */       paramString = "text/xml" + paramString;
/*     */       try {
/* 482 */         return calculateHttpEncoding(paramString, paramXmlStreamReaderException.getBomEncoding(), paramXmlStreamReaderException.getXmlGuessEncoding(), paramXmlStreamReaderException.getXmlEncoding(), true);
/*     */       }
/*     */       catch (XmlStreamReaderException localXmlStreamReaderException) {
/* 485 */         paramXmlStreamReaderException = localXmlStreamReaderException;
/*     */       }
/*     */     }
/* 488 */     String str = paramXmlStreamReaderException.getXmlEncoding();
/* 489 */     if (str == null) {
/* 490 */       str = paramXmlStreamReaderException.getContentTypeEncoding();
/*     */     }
/* 492 */     if (str == null) {
/* 493 */       str = this.defaultEncoding == null ? "UTF-8" : this.defaultEncoding;
/*     */     }
/* 495 */     return str;
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
/*     */ 
/*     */ 
/*     */   String calculateRawEncoding(String paramString1, String paramString2, String paramString3)
/*     */     throws IOException
/*     */   {
/* 511 */     if (paramString1 == null) {
/* 512 */       if ((paramString2 == null) || (paramString3 == null)) {
/* 513 */         return this.defaultEncoding == null ? "UTF-8" : this.defaultEncoding;
/*     */       }
/* 515 */       if ((paramString3.equals("UTF-16")) && ((paramString2.equals("UTF-16BE")) || (paramString2.equals("UTF-16LE"))))
/*     */       {
/* 517 */         return paramString2;
/*     */       }
/* 519 */       return paramString3;
/*     */     }
/*     */     
/*     */ 
/* 523 */     if (paramString1.equals("UTF-8")) {
/* 524 */       if ((paramString2 != null) && (!paramString2.equals("UTF-8"))) {
/* 525 */         str = MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", new Object[] { paramString1, paramString2, paramString3 });
/* 526 */         throw new XmlStreamReaderException(str, paramString1, paramString2, paramString3);
/*     */       }
/* 528 */       if ((paramString3 != null) && (!paramString3.equals("UTF-8"))) {
/* 529 */         str = MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", new Object[] { paramString1, paramString2, paramString3 });
/* 530 */         throw new XmlStreamReaderException(str, paramString1, paramString2, paramString3);
/*     */       }
/* 532 */       return paramString1;
/*     */     }
/*     */     
/*     */ 
/* 536 */     if ((paramString1.equals("UTF-16BE")) || (paramString1.equals("UTF-16LE"))) {
/* 537 */       if ((paramString2 != null) && (!paramString2.equals(paramString1))) {
/* 538 */         str = MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", new Object[] { paramString1, paramString2, paramString3 });
/* 539 */         throw new XmlStreamReaderException(str, paramString1, paramString2, paramString3);
/*     */       }
/* 541 */       if ((paramString3 != null) && (!paramString3.equals("UTF-16")) && (!paramString3.equals(paramString1))) {
/* 542 */         str = MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", new Object[] { paramString1, paramString2, paramString3 });
/* 543 */         throw new XmlStreamReaderException(str, paramString1, paramString2, paramString3);
/*     */       }
/* 545 */       return paramString1;
/*     */     }
/*     */     
/*     */ 
/* 549 */     if ((paramString1.equals("UTF-32BE")) || (paramString1.equals("UTF-32LE"))) {
/* 550 */       if ((paramString2 != null) && (!paramString2.equals(paramString1))) {
/* 551 */         str = MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", new Object[] { paramString1, paramString2, paramString3 });
/* 552 */         throw new XmlStreamReaderException(str, paramString1, paramString2, paramString3);
/*     */       }
/* 554 */       if ((paramString3 != null) && (!paramString3.equals("UTF-32")) && (!paramString3.equals(paramString1))) {
/* 555 */         str = MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch", new Object[] { paramString1, paramString2, paramString3 });
/* 556 */         throw new XmlStreamReaderException(str, paramString1, paramString2, paramString3);
/*     */       }
/* 558 */       return paramString1;
/*     */     }
/*     */     
/*     */ 
/* 562 */     String str = MessageFormat.format("Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] unknown BOM", new Object[] { paramString1, paramString2, paramString3 });
/* 563 */     throw new XmlStreamReaderException(str, paramString1, paramString2, paramString3);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   String calculateHttpEncoding(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 584 */     if ((paramBoolean) && (paramString4 != null)) {
/* 585 */       return paramString4;
/*     */     }
/*     */     
/*     */ 
/* 589 */     String str1 = getContentTypeMime(paramString1);
/* 590 */     String str2 = getContentTypeEncoding(paramString1);
/* 591 */     boolean bool1 = isAppXml(str1);
/* 592 */     boolean bool2 = isTextXml(str1);
/*     */     
/*     */     String str3;
/* 595 */     if ((!bool1) && (!bool2)) {
/* 596 */       str3 = MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], Invalid MIME", new Object[] { str1, str2, paramString2, paramString3, paramString4 });
/* 597 */       throw new XmlStreamReaderException(str3, str1, str2, paramString2, paramString3, paramString4);
/*     */     }
/*     */     
/*     */ 
/* 601 */     if (str2 == null) {
/* 602 */       if (bool1) {
/* 603 */         return calculateRawEncoding(paramString2, paramString3, paramString4);
/*     */       }
/* 605 */       return this.defaultEncoding == null ? "US-ASCII" : this.defaultEncoding;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 610 */     if ((str2.equals("UTF-16BE")) || (str2.equals("UTF-16LE"))) {
/* 611 */       if (paramString2 != null) {
/* 612 */         str3 = MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL", new Object[] { str1, str2, paramString2, paramString3, paramString4 });
/* 613 */         throw new XmlStreamReaderException(str3, str1, str2, paramString2, paramString3, paramString4);
/*     */       }
/* 615 */       return str2;
/*     */     }
/*     */     
/*     */ 
/* 619 */     if (str2.equals("UTF-16")) {
/* 620 */       if ((paramString2 != null) && (paramString2.startsWith("UTF-16"))) {
/* 621 */         return paramString2;
/*     */       }
/* 623 */       str3 = MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch", new Object[] { str1, str2, paramString2, paramString3, paramString4 });
/* 624 */       throw new XmlStreamReaderException(str3, str1, str2, paramString2, paramString3, paramString4);
/*     */     }
/*     */     
/*     */ 
/* 628 */     if ((str2.equals("UTF-32BE")) || (str2.equals("UTF-32LE"))) {
/* 629 */       if (paramString2 != null) {
/* 630 */         str3 = MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL", new Object[] { str1, str2, paramString2, paramString3, paramString4 });
/* 631 */         throw new XmlStreamReaderException(str3, str1, str2, paramString2, paramString3, paramString4);
/*     */       }
/* 633 */       return str2;
/*     */     }
/*     */     
/*     */ 
/* 637 */     if (str2.equals("UTF-32")) {
/* 638 */       if ((paramString2 != null) && (paramString2.startsWith("UTF-32"))) {
/* 639 */         return paramString2;
/*     */       }
/* 641 */       str3 = MessageFormat.format("Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch", new Object[] { str1, str2, paramString2, paramString3, paramString4 });
/* 642 */       throw new XmlStreamReaderException(str3, str1, str2, paramString2, paramString3, paramString4);
/*     */     }
/*     */     
/* 645 */     return str2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static String getContentTypeMime(String paramString)
/*     */   {
/* 655 */     String str = null;
/* 656 */     if (paramString != null) {
/* 657 */       int i = paramString.indexOf(";");
/* 658 */       if (i >= 0) {
/* 659 */         str = paramString.substring(0, i);
/*     */       } else {
/* 661 */         str = paramString;
/*     */       }
/* 663 */       str = str.trim();
/*     */     }
/* 665 */     return str;
/*     */   }
/*     */   
/* 668 */   private static final Pattern CHARSET_PATTERN = Pattern.compile("charset=[\"']?([.[^; \"']]*)[\"']?");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static String getContentTypeEncoding(String paramString)
/*     */   {
/* 679 */     String str1 = null;
/* 680 */     if (paramString != null) {
/* 681 */       int i = paramString.indexOf(";");
/* 682 */       if (i > -1) {
/* 683 */         String str2 = paramString.substring(i + 1);
/* 684 */         Matcher localMatcher = CHARSET_PATTERN.matcher(str2);
/* 685 */         str1 = localMatcher.find() ? localMatcher.group(1) : null;
/* 686 */         str1 = str1 != null ? str1.toUpperCase(Locale.US) : null;
/*     */       }
/*     */     }
/* 689 */     return str1;
/*     */   }
/*     */   
/* 692 */   public static final Pattern ENCODING_PATTERN = Pattern.compile("<\\?xml.*encoding[\\s]*=[\\s]*((?:\".[^\"]*\")|(?:'.[^']*'))", 8);
/*     */   
/*     */   private static final String RAW_EX_1 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch";
/*     */   
/*     */   private static final String RAW_EX_2 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] unknown BOM";
/*     */   
/*     */   private static final String HTTP_EX_1 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL";
/*     */   
/*     */   private static final String HTTP_EX_2 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch";
/*     */   private static final String HTTP_EX_3 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], Invalid MIME";
/*     */   
/*     */   private static String getXmlProlog(InputStream paramInputStream, String paramString)
/*     */     throws IOException
/*     */   {
/* 706 */     String str1 = null;
/* 707 */     if (paramString != null) {
/* 708 */       byte[] arrayOfByte = new byte['က'];
/* 709 */       paramInputStream.mark(4096);
/* 710 */       int i = 0;
/* 711 */       int j = 4096;
/* 712 */       int k = paramInputStream.read(arrayOfByte, i, j);
/* 713 */       int m = -1;
/* 714 */       String str2 = null;
/* 715 */       while ((k != -1) && (m == -1) && (i < 4096)) {
/* 716 */         i += k;
/* 717 */         j -= k;
/* 718 */         k = paramInputStream.read(arrayOfByte, i, j);
/* 719 */         str2 = new String(arrayOfByte, 0, i, paramString);
/* 720 */         m = str2.indexOf('>');
/*     */       }
/* 722 */       if (m == -1) {
/* 723 */         if (k == -1) {
/* 724 */           throw new IOException("Unexpected end of XML stream");
/*     */         }
/* 726 */         throw new IOException("XML prolog or ROOT element not found on first " + i + " bytes");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 731 */       int n = i;
/* 732 */       if (n > 0) {
/* 733 */         paramInputStream.reset();
/* 734 */         BufferedReader localBufferedReader = new BufferedReader(new StringReader(str2.substring(0, m + 1)));
/*     */         
/* 736 */         StringBuffer localStringBuffer = new StringBuffer();
/* 737 */         String str3 = localBufferedReader.readLine();
/* 738 */         while (str3 != null) {
/* 739 */           localStringBuffer.append(str3);
/* 740 */           str3 = localBufferedReader.readLine();
/*     */         }
/* 742 */         Matcher localMatcher = ENCODING_PATTERN.matcher(localStringBuffer);
/* 743 */         if (localMatcher.find()) {
/* 744 */           str1 = localMatcher.group(1).toUpperCase();
/* 745 */           str1 = str1.substring(1, str1.length() - 1);
/*     */         }
/*     */       }
/*     */     }
/* 749 */     return str1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static boolean isAppXml(String paramString)
/*     */   {
/* 760 */     return (paramString != null) && ((paramString.equals("application/xml")) || (paramString.equals("application/xml-dtd")) || (paramString.equals("application/xml-external-parsed-entity")) || ((paramString.startsWith("application/")) && (paramString.endsWith("+xml"))));
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
/*     */ 
/*     */ 
/*     */   static boolean isTextXml(String paramString)
/*     */   {
/* 775 */     return (paramString != null) && ((paramString.equals("text/xml")) || (paramString.equals("text/xml-external-parsed-entity")) || ((paramString.startsWith("text/")) && (paramString.endsWith("+xml"))));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\XmlStreamReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */